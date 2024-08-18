package com.thefreak.nowhere.client.events;

import com.thefreak.nowhere.Nowhere;
import com.thefreak.nowhere.common.initiation.NowhereRenderTypes;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Nowhere.MODID)
public class ChatEvents {

    private static boolean enableTestCommands = true;

    @SubscribeEvent
    public static void onChatMessage(ClientChatEvent event) {
        if (enableTestCommands) {
            String message = event.getMessage();
            if (message.startsWith("!setPercentage")) {
                //System.out.println("Working");
                String[] args = message.split(" ");
                if (args.length == 2) {
                    //System.out.println("Working 2");
                    try {
                        float percentage = Float.parseFloat(args[1]);
                        if (percentage >= 0 && percentage <= 1) {
                            //System.out.println("Working 3");
                            NowhereRenderTypes.setStaticPercentage(percentage);
                            event.setCanceled(true);
                        }
                    } catch (NumberFormatException e) {
                        // Do nothing
                    }
                }
            }
        }
    }
}
