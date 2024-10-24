package com.thefreak.nowhere.common.systems.detectionmeter;

import com.thefreak.nowhere.Nowhere;
import com.thefreak.nowhere.client.overlay.DetectionLayerOverlay;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Nowhere.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientRegistry {

    @SubscribeEvent
    public static void onOverlayRegistry(RegisterGuiOverlaysEvent event) {

        event.registerBelowAll("detection_overlay", DetectionLayerOverlay::render);

    }
}
