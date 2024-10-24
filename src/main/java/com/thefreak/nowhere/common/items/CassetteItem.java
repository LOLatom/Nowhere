package com.thefreak.nowhere.common.items;

import com.thefreak.nowhere.common.items.cassettestuff.CassetteRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;

public class CassetteItem extends Item {
    private final int tapeID;
    public CassetteItem(int tapeID,Properties pProperties) {
        super(pProperties);
        this.tapeID= tapeID;
    }

    public int getTapeID() {
        return this.tapeID;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(
                Component.nullToEmpty(CassetteRegistry.cassetteNames.get(this.tapeID))
                        .copy().withStyle(Style.EMPTY.withColor(new Color(0x525252).getRGB())));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
