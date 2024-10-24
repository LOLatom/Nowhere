package com.thefreak.nowhere.common.blockentity;

import com.thefreak.nowhere.common.advanced.Room;
import com.thefreak.nowhere.common.advanced.RoomAnalyzer;
import com.thefreak.nowhere.common.blocks.TVBlock;
import com.thefreak.nowhere.common.entities.TheLocust;
import com.thefreak.nowhere.common.initiation.BlockEntityInitiation;
import com.thefreak.nowhere.common.initiation.EntityInitiation;
import com.thefreak.nowhere.common.initiation.SoundsInitiation;
import com.thefreak.nowhere.common.items.cassettestuff.CassetteRegistry;
import com.thefreak.nowhere.util.Constants;
import foundry.veil.api.client.color.Color;
import foundry.veil.api.client.color.ColorTheme;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.deferred.light.AreaLight;
import foundry.veil.api.client.render.deferred.light.PointLight;
import foundry.veil.api.client.tooltip.Tooltippable;
import foundry.veil.api.client.tooltip.VeilUIItemTooltipDataHolder;
import foundry.veil.api.client.tooltip.anim.TooltipTimeline;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.spongepowered.asm.mixin.Unique;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class TVBlockEntity extends BlockEntity implements Tooltippable, GeoBlockEntity {
    protected static final RawAnimation ON = RawAnimation.begin().thenLoop("animation.tv.on");

    protected static final RawAnimation ARMUP = RawAnimation.begin().thenLoop("animation.tv.armup");
    protected static final RawAnimation TAPPING = RawAnimation.begin().thenLoop("animation.tv.tapping");

    protected static final RawAnimation GET_OUT = RawAnimation.begin().thenLoop("animation.tv.getout");
    protected static final RawAnimation EMPTY = RawAnimation.begin().thenLoop("animation.tv.empty");

    protected static final RawAnimation OFF = RawAnimation.begin().thenLoop("animation.tv.off");

    public AreaLight light;

    private boolean isOn = false;
    private int cassetteID = 0;
    private int phase = 0;

    private int tick = 0;

    private int animatedTicks = 0;

    private boolean isAnimationStarted = false;

    private int nearestPlayerSpeechState = 0;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);


    private Component onOrOffComp = Component.nullToEmpty("On").copy().withStyle(Style.EMPTY.withColor(new java.awt.Color(4, 255, 17,255).getRGB()));

    public TVBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
        Component turnOncmp = Component.nullToEmpty("Turn ").copy().withStyle()
                .append(Component.nullToEmpty("On").copy().withStyle(Style.EMPTY.withColor(new java.awt.Color(4, 255, 17,255).getRGB())));
        addTooltip(turnOncmp);
        initTheme();

    }
    public TVBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        this(BlockEntityInitiation.TV_BE.get(), pWorldPosition, pBlockState);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        if (this.level != null) {
            if (this.level.isClientSide) {
                if (Constants.tvPositions.contains(getBlockPos())) {
                    Constants.tvPositions.remove(getBlockPos());
                }
                if (this.getLight() != null) {
                    if (VeilRenderSystem.renderer().getDeferredRenderer().getLightRenderer()
                            .getLights(this.getLight().getType()).contains(this.getLight())) {
                        VeilRenderSystem.renderer().getDeferredRenderer().getLightRenderer().removeLight(this.getLight());
                    }
                }
            }
        }
    }

    public AreaLight getLight() {
        return this.light;
    }

    public void setLight(AreaLight light) {
        this.light = light;
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.isOn = pTag.contains("isOn") ? pTag.getBoolean("isOn") : false;
        if (pTag.contains("isOn")) {
            System.out.println(pTag.getBoolean("isOn"));
        }
        this.cassetteID = pTag.contains("cassetteID") ? pTag.getInt("cassetteID") : 0;
        this.phase = pTag.contains("phase") ? pTag.getInt("phase") : 0;
        this.loadTooltipData(pTag.getCompound("tooltipData"));
        if (!this.isOn) {
            this.onOrOffComp = Component.nullToEmpty("On").copy().withStyle(Style.EMPTY.withColor(new java.awt.Color(4, 255, 17,255).getRGB()));
        } else {
            this.onOrOffComp = Component.nullToEmpty("Off").copy().withStyle(Style.EMPTY.withColor(new java.awt.Color(255, 4, 4,255).getRGB()));
        }
        Component turnOncmp = Component.nullToEmpty("Turn ").copy().withStyle()
                .append(this.onOrOffComp);
        List<Component> components = new ArrayList<>();
        components.add(turnOncmp);
        if (this.getCassetteID() != 0) {
            components.add(Component.nullToEmpty("Playing: " + CassetteRegistry.cassetteNames.get(this.cassetteID))
                    .copy().withStyle(Style.EMPTY.withColor(new java.awt.Color(0x494949).getRGB())));
        }
        setTooltip(components);
        initTheme();
    }

    public void setNearestPlayerSpeechState(int nearestPlayerSpeechState) {
        this.nearestPlayerSpeechState = nearestPlayerSpeechState;
    }

    public int getNearestPlayerSpeechState() {
        return this.nearestPlayerSpeechState;
    }

    public void setPhase(int phase) {
        this.phase = phase;
        setChanged();
    }

    public int getPhase() {
        return this.phase;
    }

    public boolean isOn() {
        return this.isOn;
    }

    public void setOn(boolean on) {
        if (!on) {
            this.onOrOffComp = Component.nullToEmpty("On").copy().withStyle(Style.EMPTY.withColor(new java.awt.Color(4, 255, 17,255).getRGB()));
        } else {
            this.onOrOffComp = Component.nullToEmpty("Off").copy().withStyle(Style.EMPTY.withColor(new java.awt.Color(255, 4, 4,255).getRGB()));
        }
        Component turnOncmp = Component.nullToEmpty("Turn ").copy().withStyle()
                .append(this.onOrOffComp);
        List<Component> components = new ArrayList<>();
        components.add(turnOncmp);
        if (this.getCassetteID() != 0) {
            components.add(Component.nullToEmpty("Playing: " + CassetteRegistry.cassetteNames.get(this.cassetteID))
                    .copy().withStyle(Style.EMPTY.withColor(new java.awt.Color(0x494949).getRGB())));
        }
        this.setTooltip(components);
        initTheme();
        this.isOn = on;
        setChanged();
    }

    public int getCassetteID() {
        return this.cassetteID;
    }

    public void setCassetteID(int cassetteID) {
        this.cassetteID = cassetteID;
        setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        this.writeData(pTag);
        super.saveAdditional(pTag);
    }

    public void writeData(CompoundTag nbt) {
        nbt.putBoolean("isOn", this.isOn);
        nbt.putInt("cassetteID", this.cassetteID);
        nbt.putInt("phase",this.phase);
        initTheme();
        nbt.put("tooltipData", this.saveTooltipData());
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        this.writeData(tag);
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public static ColorTheme SUPERPOSITION_THEME = new ColorTheme();
    public void initTheme() {
        SUPERPOSITION_THEME.clear();
        SUPERPOSITION_THEME.addColor(new Color(51, 51, 51,200));
        SUPERPOSITION_THEME.addColor(new Color(60, 60, 60,255));
        SUPERPOSITION_THEME.addColor(new Color(72, 72, 72,255));
        setTheme(SUPERPOSITION_THEME);
        setBackgroundColor(new java.awt.Color(21,21,21,255).getRGB());
        setTopBorderColor(new java.awt.Color(121, 121, 121,255).getRGB());
        setBottomBorderColor(new java.awt.Color(49, 49, 49,255).getRGB());

    }

    public int getAnimatedTicks() {
        return this.animatedTicks;
    }

    public void setAnimatedTicks(int animatedTicks) {
        this.animatedTicks = animatedTicks;
    }

    public void incrementAnimatedTicks() {
        this.animatedTicks++;
    }

    public void setAnimationStarted(boolean animationStarted) {
        this.isAnimationStarted = animationStarted;
    }

    public boolean isAnimationStarted() {
        return this.isAnimationStarted;
    }

    private List<Component> veil$tooltip = new ArrayList<>();


    private ColorTheme veil$theme;

    
    private List<VeilUIItemTooltipDataHolder> veil$tooltipDataHolder = new ArrayList<>();

    
    private TooltipTimeline veil$timeline = null;

    
    private boolean veil$worldspace = true;

    
    private boolean veil$tooltipEnabled = false;

    
    private int veil$tooltipX = 0;

    
    private int veil$tooltipY = 0;

    
    private int veil$tooltipWidth = 0;

    
    private int veil$tooltipHeight = 0;


    @Override
    public List<Component> getTooltip() {
        return this.veil$tooltip;
    }

    @Override
    public boolean isTooltipEnabled() {
        return true;
    }


    @Override
    public CompoundTag saveTooltipData() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("tooltipEnabled", this.veil$tooltipEnabled);
        tag.putInt("tooltipX", this.veil$tooltipX);
        tag.putInt("tooltipY", this.veil$tooltipY);
        tag.putInt("tooltipWidth", this.veil$tooltipWidth);
        tag.putInt("tooltipHeight", this.veil$tooltipHeight);
        tag.putBoolean("worldspace", this.veil$worldspace);

        if (this.veil$theme != null) {
            CompoundTag themeTag = new CompoundTag();
            for (Map.Entry<String, Color> entry : this.veil$theme.getColorsMap().entrySet()) {
                String key = entry.getKey() != null ? entry.getKey() : "";
                themeTag.putInt(key, entry.getValue().getRGB());
            }
            tag.put("theme", themeTag);
        }
        return tag;
    }

    @Override
    public void loadTooltipData(CompoundTag tag) {
        this.veil$tooltipEnabled = tag.getBoolean("tooltipEnabled");
        this.veil$tooltipX = tag.getInt("tooltipX");
        this.veil$tooltipY = tag.getInt("tooltipY");
        this.veil$tooltipWidth = tag.getInt("tooltipWidth");
        this.veil$tooltipHeight = tag.getInt("tooltipHeight");
        this.veil$worldspace = tag.getBoolean("worldspace");

        if (this.veil$theme != null) {
            this.veil$theme.clear();
        }
        if (tag.contains("theme", CompoundTag.TAG_COMPOUND)) {
            if (this.veil$theme == null) {
                this.veil$theme = new ColorTheme();
            }
            CompoundTag themeTag = tag.getCompound("theme");
            for (String key : themeTag.getAllKeys()) {
                this.veil$theme.addColor(key, Color.of(themeTag.getInt(key)));
            }
        }
    }

    @Override
    public void setTooltip(List<Component> tooltip) {
        this.veil$tooltip = tooltip;
    }

    @Override
    public void addTooltip(Component tooltip) {
        this.veil$tooltip.add(tooltip);
    }

    @Override
    public void addTooltip(List<Component> tooltip) {
        this.veil$tooltip.addAll(tooltip);
    }

    @Override
    public void addTooltip(String tooltip) {
        this.veil$tooltip.add(Component.nullToEmpty(tooltip));
    }

    @Override
    public ColorTheme getTheme() {
        return this.veil$theme;
    }

    @Override
    public void setTheme(ColorTheme theme) {
        this.veil$theme = theme;
    }

    @Override
    public void setBackgroundColor(int color) {
        this.veil$theme.addColor("background", Color.of(color));
    }

    @Override
    public void setTopBorderColor(int color) {
        this.veil$theme.addColor("topBorder", Color.of(color));
    }

    @Override
    public void setBottomBorderColor(int color) {
        this.veil$theme.addColor("bottomBorder", Color.of(color));
    }

    @Override
    public boolean getWorldspace() {
        return this.veil$worldspace;
    }

    @Override
    public TooltipTimeline getTimeline() {
        return this.veil$timeline;
    }

    @Override
    public ItemStack getStack() {
        return ItemStack.EMPTY;
    }

    @Override
    public int getTooltipWidth() {
        return this.veil$tooltipWidth;
    }

    @Override
    public int getTooltipHeight() {
        return this.veil$tooltipHeight;
    }

    @Override
    public int getTooltipXOffset() {
        return this.veil$tooltipX;
    }

    @Override
    public int getTooltipYOffset() {
        return this.veil$tooltipHeight;
    }

    @Override
    public List<VeilUIItemTooltipDataHolder> getItems() {
        return this.veil$tooltipDataHolder;
    }


    public int getTick() {
        return this.tick;
    }

    public void setTick(int tick) {
        this.tick = tick;
    }

    public void increaseTick() {
        this.tick++;
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, TVBlockEntity pBlockEntity) {
        if (pLevel.isClientSide) {
            if (!Constants.tvPositions.contains(pPos)) {
                Constants.tvPositions.add(pPos);
            }
        }
        if (pBlockEntity.getAnimatedTicks() >= (4.2 * 20) && pBlockEntity.getPhase() == 2) {
            pBlockEntity.setAnimatedTicks(0);
            pBlockEntity.setPhase(3);
        }

        if (pBlockEntity.getAnimatedTicks() == (1.2 * 20) && pBlockEntity.getPhase() == 3) {
            TheLocust locust = EntityInitiation.THE_LOCUST.get().create(pLevel);
            Direction direction = pState.getValue(TVBlock.FACING);
            BlockPos pos = pPos;
            switch (direction) {
                case NORTH -> {
                    pos = pPos.north(2);
                }
                case EAST -> {
                    pos = pPos.east(2);
                }
                case WEST -> {
                    pos = pPos.west(2);
                }
                case SOUTH -> {
                    pos = pPos.south(2);
                }
            }
            locust.setPos(pos.getX(), pPos.getY(), pPos.getZ());
            locust.setTvBlockPos(pPos);
            locust.setPhase(0);
            pLevel.addFreshEntity(locust);
        }
        if (pBlockEntity.getAnimatedTicks() >= (2 * 20) && pBlockEntity.getPhase() == 3) {
            pBlockEntity.setAnimatedTicks(0);
            pBlockEntity.setAnimationStarted(false);
            pBlockEntity.setPhase(4);
        }
        if ((pBlockEntity.getPhase() == 2) && !pBlockEntity.isAnimationStarted()) {
            pBlockEntity.setAnimationStarted(true);
            pLevel.playLocalSound(pPos, SoundsInitiation.LOCUST_GET_OUT.get(), SoundSource.HOSTILE, 1.5F, 1, true);
        }
        if ((pBlockEntity.getPhase() == 3) && !pBlockEntity.isAnimationStarted()) {
            pBlockEntity.setAnimationStarted(true);
        }
        if (pBlockEntity.isAnimationStarted()) {
            pBlockEntity.incrementAnimatedTicks();
        }
        //System.out.println(pBlockEntity.getNearestPlayerSpeechState());
        if (pBlockEntity.getCassetteID() != 0) {
            pBlockEntity.increaseTick();
            if (pBlockEntity.getCassetteID() == 1 && pBlockEntity.isOn()) {

                Player player = pLevel.getNearestPlayer(pPos.getX(),pPos.getY(),pPos.getZ(),10, false);

                if (pBlockEntity.getTick() % 70 == 0 && !(pBlockEntity.getPhase() > 2)) {
                    List<SoundEvent> sounds = new ArrayList<>();
                    sounds.add(SoundsInitiation.LOCUST_GROWL.get());
                    if (player != null) {
                        sounds.add(SoundsInitiation.LOCUST_HELLO.get());
                        sounds.add(SoundsInitiation.LOCUST_HELP.get());
                    }
                    sounds.add(SoundsInitiation.LOCUST_PAIN.get());

                    Random random = new Random();
                    SoundEvent sound = sounds.get(random.nextInt(sounds.size()));


                    pLevel.playLocalSound(pPos, sound, SoundSource.HOSTILE, 1.5F, 1, true);


                }

                if (player != null && pBlockEntity.getPhase() == 0) {
                    if (!player.isCreative()) {
                        Room room = RoomAnalyzer.scanFromAnyPos(player.blockPosition(), pLevel, 0);
                        BlockPos posFromTV = new BlockPos(pPos.getX(), player.blockPosition().getY(), pPos.getZ());
                        Room roomFromTV = RoomAnalyzer.scanFromAnyPos(posFromTV, pLevel, 0);
                        if (room != null && roomFromTV != null) {
                            if (room.getMiddlePosition().equals(roomFromTV.getMiddlePosition())) {
                                //System.out.println("BOTH IN THE SAME ROOM");
                                pBlockEntity.setPhase(1);
                            }
                        }
                    }
                } else if (player != null && pBlockEntity.getPhase() == 1 && pBlockEntity.getNearestPlayerSpeechState() >= 2) {
                    if (!player.isCreative()) {
                        Room room = RoomAnalyzer.scanFromAnyPos(player.blockPosition(), pLevel, 0);
                        BlockPos posFromTV = new BlockPos(pPos.getX(), player.blockPosition().getY(), pPos.getZ());
                        Room roomFromTV = RoomAnalyzer.scanFromAnyPos(posFromTV, pLevel, 0);
                        if (room != null && roomFromTV != null) {
                            if (room.getMiddlePosition().equals(roomFromTV.getMiddlePosition())) {
                                //System.out.println("BOTH IN THE SAME ROOM");
                                pBlockEntity.setPhase(2);
                            }
                        }
                    }
                }
            }
        }
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, this::deployAnimController));
    }

    protected <E extends TVBlockEntity> PlayState deployAnimController(final AnimationState<E> state) {

        if (this.isOn()) {
            if (this.getPhase() == 1) {
                state.getController().setAnimation(ARMUP);
                return PlayState.CONTINUE;
            } else if (this.getPhase() == 2) {
                state.getController().setAnimation(TAPPING);
                return PlayState.CONTINUE;
            } else if (this.getPhase() == 3) {
                state.getController().setAnimation(GET_OUT);
                return PlayState.CONTINUE;
            }else if (this.getPhase() == 4) {
                state.getController().setAnimation(EMPTY);
                return PlayState.CONTINUE;
            }  else {
                state.getController().setAnimation(ON);
                return PlayState.CONTINUE;
            }
        } else {
            state.getController().setAnimation(OFF);
            return PlayState.CONTINUE;

        }
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

}
