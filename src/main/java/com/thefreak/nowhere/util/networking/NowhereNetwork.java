package com.thefreak.nowhere.util.networking;

import com.thefreak.nowhere.util.networking.toserver.SendUpdateDetectionStatus;
import com.thefreak.nowhere.util.networking.toserver.SendUpdateVoiceStatePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NowhereNetwork {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("nowhere", "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void init() {
        int ID = 0;

        INSTANCE.messageBuilder(SendUpdateVoiceStatePacket.class,ID++, NetworkDirection.PLAY_TO_SERVER)
                .encoder(SendUpdateVoiceStatePacket::encode)
                .decoder(SendUpdateVoiceStatePacket::decode)
                .consumerMainThread(SendUpdateVoiceStatePacket::handle)
                .add();

        INSTANCE.messageBuilder(SendUpdateDetectionStatus.class,ID++, NetworkDirection.PLAY_TO_SERVER)
                .encoder(SendUpdateDetectionStatus::encode)
                .decoder(SendUpdateDetectionStatus::decode)
                .consumerMainThread(SendUpdateDetectionStatus::handle)
                .add();


    }
}
