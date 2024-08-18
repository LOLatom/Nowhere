package com.thefreak.nowhere.common.blockentity;

import com.thefreak.nowhere.common.initiation.BlockEntityInitiation;
import foundry.veil.api.client.color.Color;
import foundry.veil.api.client.color.ColorTheme;
import foundry.veil.api.client.tooltip.Tooltippable;
import foundry.veil.api.client.tooltip.VeilUIItemTooltipDataHolder;
import foundry.veil.api.client.tooltip.anim.TooltipTimeline;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TVBlockEntity extends BlockEntity implements Tooltippable {

    private boolean isOn = false;
    private int cassetteID = 0;

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
    public void load(CompoundTag pTag) {
        this.isOn = pTag.contains("isOn") ? pTag.getBoolean("isOn") : false;
        if (pTag.contains("isOn")) {
            System.out.println(pTag.getBoolean("isOn"));
        }
        this.cassetteID = pTag.contains("cassetteID") ? pTag.getInt("cassetteID") : 0;
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
        setTooltip(components);
        super.load(pTag);
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
        this.setTooltip(components);
        initTheme();
        this.isOn = on;
    }

    public int getCassetteID() {
        return this.cassetteID;
    }

    public void setCassetteID(int cassetteID) {
        this.cassetteID = cassetteID;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        this.writeData(pTag);
        super.saveAdditional(pTag);
    }

    public void writeData(CompoundTag nbt) {
        nbt.putBoolean("isOn", this.isOn);
        nbt.putInt("cassetteID", this.cassetteID);
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

}
