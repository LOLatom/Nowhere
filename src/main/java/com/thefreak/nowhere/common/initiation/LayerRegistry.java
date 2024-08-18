package com.thefreak.nowhere.common.initiation;

import com.thefreak.nowhere.Nowhere;
import com.thefreak.nowhere.client.models.TVScreenModel;
import com.thefreak.nowhere.client.models.TheLocustTorsoModel;
import com.thefreak.nowhere.client.renderers.TVRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Nowhere.MODID, value = Dist.CLIENT)
public class LayerRegistry {
    @SubscribeEvent
    public static void registerLayerDef(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(LayerInitiation.TV_LAYER, TVScreenModel::createBodyLayer);
        event.registerLayerDefinition(LayerInitiation.LOCUST_TORSO_LAYER, TheLocustTorsoModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        BlockEntityRenderers.register(BlockEntityInitiation.TV_BE.get(), TVRenderer::new);
    }

}
