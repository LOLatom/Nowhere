package com.thefreak.nowhere.common.entities;

import com.eliotlash.mclib.utils.MathHelper;
import com.thefreak.nowhere.API.IDetecteable;
import com.thefreak.nowhere.common.advanced.DoorWay;
import com.thefreak.nowhere.common.advanced.Room;
import com.thefreak.nowhere.common.advanced.RoomAnalyzer;
import com.thefreak.nowhere.common.entities.AI.LocalisationHelper;
import com.thefreak.nowhere.common.initiation.NowhereRenderTypes;
import com.thefreak.nowhere.util.Constants;
import foundry.veil.api.client.render.VeilRenderSystem;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TheLocust extends Mob implements GeoEntity {
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    private BlockPos tvBlockPos = new BlockPos(0,0,0);

    private int phase = 0;

    private int animationTicks = 0;

    private float appearPercentage = 0;

    private List<Player> possibleTarget = new ArrayList<>();

    private List<DoorWay> excludedDoorways = new ArrayList<>();


    public TheLocust(EntityType<? extends Mob> p_21368_, Level p_21369_) {
        super(p_21368_, p_21369_);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 500D)
                .add(Attributes.MOVEMENT_SPEED, (double) 1F)
                .add(Attributes.ATTACK_DAMAGE, 0.1D);
    }
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this,"MovementController",5,this::MovementController));
        controllers.add(new AnimationController<>(this,"TalkingController",5,this::TalkingController));
    }

    public BlockPos getTvBlockPos() {
        return this.tvBlockPos;
    }

    public void setTvBlockPos(BlockPos tvBlockPos) {
        this.tvBlockPos = tvBlockPos;
    }

    public int getPhase() {
        return this.phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("tvPosX", this.getTvBlockPos().getX());
        pCompound.putInt("tvPosY", this.getTvBlockPos().getY());
        pCompound.putInt("tvPosZ", this.getTvBlockPos().getZ());
        pCompound.putInt("phase", this.getPhase());
        pCompound.put("excludedDoorWays", DoorWay.encodeListFromArray(this.excludedDoorways));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setTvBlockPos(new BlockPos(pCompound.getInt("tvPosX"),
                pCompound.getInt("tvPosY"),
                pCompound.getInt("tvPosZ")));
        this.setPhase(pCompound.contains("phase") ? pCompound.getInt("phase") : 0);
        this.excludedDoorways = DoorWay.getListFromNBT(pCompound.getCompound("excludedDoorWays"));
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new LocustPatrolGoal(this));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getTarget() != null) {

        } else {
            setTarget(this.level().getNearestPlayer(blockPosition().getX(),blockPosition().getY(),blockPosition().getZ(),10, false));
        }
        if (this.getPhase() == 0) {
            this.tickCount++;
            this.appearPercentage = (float) this.tickCount / 40;
            if (this.level().isClientSide()) {
                float currentAmount = (float) (Mth.lerp(this.appearPercentage,1000,115) * 0.001);
                System.out.println(this.appearPercentage);
                NowhereRenderTypes.setStaticPercentage(currentAmount);
            }
            if (this.getTarget() != null) {
                this.yBodyRot = this.yHeadRot;
                this.getLookControl().setLookAt(this.getTarget());
            }
            if (this.tickCount >= 40) {
                this.setPhase(1);
            }
        }
        if (this.level().getBlockState(this.getTvBlockPos()).isAir()) {
            this.remove(RemovalReason.CHANGED_DIMENSION);
        }
        //System.out.println(this.tvBlockPos);
        if (this.getPhase() > 0) {
            this.playerDetector();
        }
    }


    private void playerDetector() {
        List<Player> playersNearby = this.level().getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(50));
        List<Player> playersOfIntrest = new ArrayList<>();
        List<Player> playersToFind = new ArrayList<>();

        Room entityRoom = RoomAnalyzer.scanFromAnyPos(this.blockPosition(),this.level(),0);
        if (entityRoom != null) {
            for (Player player : playersNearby) {
                if (player instanceof IDetecteable detecteable) {
                    if (detecteable.getDetectionLevel() > Constants.MINIMAL_DETECTED) {
                        Room playerRoom = RoomAnalyzer.scanFromAnyPos(player.blockPosition(),this.level(),0);
                        if (playerRoom != null) {
                            if (entityRoom.getMiddlePosition().equals(playerRoom.getMiddlePosition())) {
                                playersOfIntrest.add(player);
                            } else {
                                playersToFind.add(player);
                                System.out.println(entityRoom.getPathToRoom(playerRoom.getMiddlePosition(),this.level()));
                            }
                        }
                    }
                }
            }

            Optional<Player> selectedTarget = Optional.empty();
            if (!playersOfIntrest.isEmpty()) {
                for (int i = 0; i < playersOfIntrest.size(); i++) {
                    if (selectedTarget.isEmpty()) {
                        selectedTarget = Optional.ofNullable(playersOfIntrest.get(i));
                    } else {
                        if (this.position().distanceTo(selectedTarget.get().position()) > this.position().distanceTo(playersOfIntrest.get(i).position())) {
                            selectedTarget = Optional.ofNullable(playersOfIntrest.get(i));
                        }
                    }
                }
            }

            if (!selectedTarget.isEmpty()) {
                System.out.println(selectedTarget.get().getName());
            }
        }



    }





    private PlayState TalkingController(AnimationState<TheLocust> theLocustAnimationState) {

        return PlayState.STOP;
    }

    private PlayState MovementController(AnimationState<TheLocust> theLocustAnimationState) {
        if (this.getPhase() == 0) {
            theLocustAnimationState.getController()
                    .setAnimation(RawAnimation.begin().thenLoop("animation.the_locust.appear"));
            return PlayState.CONTINUE;
        }

        if ((xOld != getX()) || (zOld != getZ())) {
            theLocustAnimationState.getController()
                    .setAnimation(RawAnimation.begin().then("animation.the_locust.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else {
            theLocustAnimationState.getController()
                    .setAnimation(RawAnimation.begin().then("animation.the_locust.idle", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        //return PlayState.STOP;
    }

    @Override
    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return false;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }


    public class LocustPatrolGoal extends Goal {

        private Vec3 firstPatrolPos = new Vec3(0,0,0);

        private Vec3 secondPatrolPos = new Vec3(0,0,0);

        private Vec3 currentPatrolPos = new Vec3(0,0,0);

        private boolean shouldPatrol = false;

        private TheLocust entity;

        private int patrolCount = 0;

        public LocustPatrolGoal(TheLocust entity) {
        this.entity = entity;

        }

        @Override
        public boolean canUse() {
            Room room = RoomAnalyzer.scanFromAnyPos(TheLocust.this.blockPosition(),TheLocust.this.level(),0);
            if (room == null) return false;
            if (!room.isHallway()) return false;
            if (TheLocust.this.getPhase() < 1) return false;
            List<Vec3> list = room.getExtremities(1);
            if (list.isEmpty()) return false;
            this.firstPatrolPos = list.get(0);
            this.secondPatrolPos = list.get(1);
            return true;
        }


        @Override
        public void start() {
            super.start();
            //System.out.println("FirstPos : " + this.firstPatrolPos);
            //System.out.println("SecondPos : " + this.secondPatrolPos);
            this.patrolCount = 0;

        }

        @Override
        public boolean canContinueToUse() {
            if (TheLocust.this.getPhase() < 1) return false;
            return true;
        }

        @Override
        public void tick() {
            super.tick();
            if (this.patrolCount % 2 == 0) {
                this.currentPatrolPos = this.firstPatrolPos;
                TheLocust.this.getMoveControl().setWantedPosition(this.currentPatrolPos.x,this.currentPatrolPos.y,this.currentPatrolPos.z,0.2);
                //System.out.println(TheLocust.this.position().distanceTo(this.currentPatrolPos));
            } else if (this.patrolCount % 2 != 0 && this.patrolCount % 1 == 0) {
                this.currentPatrolPos = this.secondPatrolPos;
                TheLocust.this.getMoveControl().setWantedPosition(this.currentPatrolPos.x,this.currentPatrolPos.y,this.currentPatrolPos.z,0.2);
                //System.out.println(TheLocust.this.position().distanceTo(this.currentPatrolPos));

            }

            if (TheLocust.this.position().distanceTo(this.currentPatrolPos) <= 1.5 && this.patrolCount < 10) {
                this.patrolCount++;
            }



        }
    }


    private class KnockOnDoorBeforeEnteringGoal extends Goal {

        private int stage;

        public KnockOnDoorBeforeEnteringGoal() {
            this.stage = 0;
        }


        @Override
        public boolean canUse() {
            return false;
        }


    }


}
