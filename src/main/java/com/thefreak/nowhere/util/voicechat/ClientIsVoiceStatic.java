package com.thefreak.nowhere.util.voicechat;

import com.thefreak.nowhere.Nowhere;
import com.thefreak.nowhere.util.Constants;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Nowhere.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientIsVoiceStatic {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (Constants.ticksSinceLastSpoken < 6) {
            Constants.ticksSinceLastSpoken++;
        } else {
            if (Constants.speachState != Constants.MUTE) {
                Constants.speachState = Constants.MUTE;
            }
        }
        String state = Constants.speachState == 0 ? "mute" : Constants.speachState == 1 ? "wishpering" : Constants.speachState == 2 ? "normal Voice" : "loud Voice";
        //System.out.println(state);
    }

}
