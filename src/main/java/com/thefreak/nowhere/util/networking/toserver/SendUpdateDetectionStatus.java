package com.thefreak.nowhere.util.networking.toserver;

import com.thefreak.nowhere.API.IDetecteable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SendUpdateDetectionStatus {
    private final int detectionState;

    public SendUpdateDetectionStatus(int detectionState) {
        this.detectionState = detectionState;
    }


    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(this.detectionState);
    }

    public static SendUpdateDetectionStatus decode(FriendlyByteBuf buffer) {
        return new SendUpdateDetectionStatus(buffer.readInt());
    }


    public void handle(Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
            ctx.get().enqueueWork(() -> {
                if (ctx.get().getSender() instanceof IDetecteable detecteable) {
                    detecteable.setDetectionLevel(this.detectionState);
                    //System.out.println(detecteable.getDetectionLevel());
                }

            });}
        ctx.get().setPacketHandled(true);
    }
}
