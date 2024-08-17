package com.thefreak.nowhere.client.events;

import com.thefreak.nowhere.Nowhere;
import com.thefreak.nowhere.common.advanced.RoomAnalyzer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = Nowhere.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class CheckIfInRoom {

    @SubscribeEvent
    public static void onSneak(PlayerEvent.ItemPickupEvent event) {
       boolean isRoomExisting = RoomAnalyzer.scanFromAnyPos(Minecraft.getInstance().player.blockPosition(), Minecraft.getInstance().level,0, new ArrayList<>()) != null;
       if (isRoomExisting) {
            System.out.println("ROOM EXIST");
       }
    }
}
