package com.thefreak.nowhere.common.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class TheLocust extends Mob implements GeoEntity {
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    BlockPos tvBlockPos;

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

    private PlayState TalkingController(AnimationState<TheLocust> theLocustAnimationState) {

        return PlayState.STOP;
    }

    private PlayState MovementController(AnimationState<TheLocust> theLocustAnimationState) {
        if (theLocustAnimationState.isMoving()) {

        } else {
            theLocustAnimationState.getController()
                    .setAnimation(RawAnimation.begin().thenLoop("animation.the_locust.idle"));
            return PlayState.CONTINUE;
        }

        return PlayState.STOP;
    }

    @Override
    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return false;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

    public class SearchForNearbyTV extends Goal {

        @Override
        public boolean canUse() {
            return false;
        }
    }

    public class SearchForPlayer extends Goal {

        @Override
        public boolean canUse() {
            return false;
        }
    }


}
