package com.thefreak.nowhere.util;

import com.thefreak.nowhere.Nowhere;
import com.thefreak.nowhere.client.renderers.TheLocustRenderer;
import com.thefreak.nowhere.common.initiation.EntityInitiation;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Nowhere.MODID,bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {

        EntityRenderers.register(EntityInitiation.THE_LOCUST.get(), TheLocustRenderer::new);

    }


}
