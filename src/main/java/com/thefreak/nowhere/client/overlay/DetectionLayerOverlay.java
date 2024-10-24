package com.thefreak.nowhere.client.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.thefreak.nowhere.Nowhere;
import com.thefreak.nowhere.util.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;

public class DetectionLayerOverlay {
    public static void render(ForgeGui forgeGui, GuiGraphics guiGraphics, float partialTicks, int screenWidth, int screenHeight) {

        ResourceLocation texture = Constants.detectionState == 0 ? Nowhere.path("textures/ui/detection/detection0.png") :
                Constants.detectionState == 1 ?  Nowhere.path("textures/ui/detection/detection1.png") :
                        Constants.detectionState == 2 ?  Nowhere.path("textures/ui/detection/detection2.png") :
                                Constants.detectionState == 3 ?  Nowhere.path("textures/ui/detection/detection3.png") :
                                        Constants.detectionState == 4 ?  Nowhere.path("textures/ui/detection/detection4.png") :
                                                Nowhere.path("textures/ui/detection/detection0.png");



        guiGraphics.pose().pushPose();
        //double miniPartialTicks = partialTicks * 0.1;
        double yAmount = Math.sin((forgeGui.getGuiTicks() * 0.1));
        guiGraphics.pose().translate(-1,yAmount,0);
        RenderSystem.setShaderColor(0,0,0,0.1f);

        guiGraphics.blit(texture,screenWidth / 2 - 9,
                screenHeight / 2 + (forgeGui.getMinecraft().player.isCreative() ? 85 : 70),
                0,0,19,19,19,19);
        RenderSystem.setShaderColor(1,1,1,1f);

        guiGraphics.pose().translate(0,-1,0);
        guiGraphics.blit(texture,screenWidth / 2 - 9,
                screenHeight / 2 + (forgeGui.getMinecraft().player.isCreative() ? 85 : 70),
                0,0,19,19,19,19);

        guiGraphics.pose().popPose();

    }
}
