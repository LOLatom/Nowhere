package com.thefreak.nowhere.util.networking.toserver;

import com.thefreak.nowhere.common.blockentity.TVBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SendUpdateVoiceStatePacket {

    private final BlockPos blockEntityPos;
    private final int speechState;

    public SendUpdateVoiceStatePacket(BlockPos blockEntityPos, int speechState) {
        this.blockEntityPos = blockEntityPos;
        this.speechState = speechState;
    }


    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(this.blockEntityPos);
        buffer.writeInt(this.speechState);
    }

    public static SendUpdateVoiceStatePacket decode(FriendlyByteBuf buffer) {
        return new SendUpdateVoiceStatePacket(buffer.readBlockPos(), buffer.readInt());
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
            ctx.get().enqueueWork(() -> {
                if (ctx.get().getSender().level().getBlockEntity(this.blockEntityPos) instanceof TVBlockEntity tvBlockEntity) {
                    tvBlockEntity.setNearestPlayerSpeechState(this.speechState);
                }

            });}
        ctx.get().setPacketHandled(true);
    }
}
