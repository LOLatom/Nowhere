package com.thefreak.nowhere.util.voicechat;

import com.thefreak.nowhere.common.advanced.Room;
import com.thefreak.nowhere.common.advanced.RoomAnalyzer;
import com.thefreak.nowhere.common.blockentity.TVBlockEntity;
import com.thefreak.nowhere.util.Constants;
import com.thefreak.nowhere.util.networking.NowhereNetwork;
import com.thefreak.nowhere.util.networking.toserver.SendUpdateVoiceStatePacket;
import de.maxhenkel.voicechat.api.*;
import de.maxhenkel.voicechat.api.events.*;
import de.maxhenkel.voicechat.api.opus.OpusDecoder;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

@ForgeVoicechatPlugin
public class VoiceChatInteractionPlugin implements VoicechatPlugin {
    public static VoicechatApi voicechatApi;
    @Nullable
    public static VoicechatServerApi voicechatServerApi;
    @Nullable
    private OpusDecoder decoder;

    @Override
    public String getPluginId() {
        return "interactivity";
    }

    @Override
    public void registerEvents(EventRegistration registration) {
        VoicechatPlugin.super.registerEvents(registration);
        registration.registerEvent(VoicechatServerStartedEvent.class, this::onServerStarted);
        registration.registerEvent(ClientSoundEvent.class,this::onServerEvent);
        registration.registerEvent(ClientVoicechatConnectionEvent.class, this::clientConnectionEvent);
    }

    private void onServerStarted(VoicechatServerStartedEvent event) {
        voicechatServerApi = event.getVoicechat();
        decoder = voicechatServerApi.createDecoder();
    }

    @Override
    public void initialize(VoicechatApi api) {
        VoicechatPlugin.super.initialize(api);
        //System.out.println("INITIALIZED");
    }

    public void clientConnectionEvent(ClientVoicechatConnectionEvent event) {
        System.out.println("IS CONNECTED : " + event.isConnected());
    }

    public void onServerEvent(ClientSoundEvent event) {
        //System.out.println("IS WORKING");
        /*if (senderConnection == null) {
            return;
        }

        if (event.getPacket().getOpusEncodedData().length <= 0) {
            // Don't trigger any events when stopping to talk
            return;
        }
        if (!(senderConnection.getPlayer().getPlayer() instanceof ServerPlayer player)) {
            return;
        }
        if (decoder == null) {
            decoder = event.getVoicechat().createDecoder();
        }*/

        decoder.resetState();
        short[] decoded = event.getRawAudio();
        Level level = Minecraft.getInstance().level;
        if (AudioUtils.calculateAudioLevel(decoded) < -9 && Constants.speachState != Constants.WISHPER) {

            Constants.speachState = Constants.WISHPER;
            if (!Constants.tvPositions.isEmpty()) {
                if (isInTheSameRoomAsBlockEntity(Minecraft.getInstance().player.blockPosition(), Constants.tvPositions.get(0), level)) {
                    NowhereNetwork.INSTANCE.sendToServer(new SendUpdateVoiceStatePacket(Constants.tvPositions.get(0),Constants.WISHPER));
                    if (level.getBlockEntity(Constants.tvPositions.get(0)) instanceof TVBlockEntity tvBlockEntity) {
                        tvBlockEntity.setNearestPlayerSpeechState(Constants.WISHPER);
                    }
                }
            }
        } else if (AudioUtils.calculateAudioLevel(decoded) > -6 && Constants.speachState != Constants.LOUD) {

            Constants.speachState = Constants.LOUD;
            if (!Constants.tvPositions.isEmpty()) {
                if (isInTheSameRoomAsBlockEntity(Minecraft.getInstance().player.blockPosition(), Constants.tvPositions.get(0), level)) {
                    NowhereNetwork.INSTANCE.sendToServer(new SendUpdateVoiceStatePacket(Constants.tvPositions.get(0),Constants.LOUD));
                    if (level.getBlockEntity(Constants.tvPositions.get(0)) instanceof TVBlockEntity tvBlockEntity) {
                        tvBlockEntity.setNearestPlayerSpeechState(Constants.LOUD);
                    }
                }
            }
        } else if (AudioUtils.calculateAudioLevel(decoded) > -9 && Constants.speachState != Constants.NORMAL) {

            Constants.speachState = Constants.NORMAL;
            if (!Constants.tvPositions.isEmpty()) {
                if (isInTheSameRoomAsBlockEntity(Minecraft.getInstance().player.blockPosition(), Constants.tvPositions.get(0), level)) {
                    NowhereNetwork.INSTANCE.sendToServer(new SendUpdateVoiceStatePacket(Constants.tvPositions.get(0),Constants.NORMAL));
                    if (level.getBlockEntity(Constants.tvPositions.get(0)) instanceof TVBlockEntity tvBlockEntity) {
                        tvBlockEntity.setNearestPlayerSpeechState(Constants.NORMAL);
                    }
                }
            }
        }
        Constants.ticksSinceLastSpoken = 0;
    }

    public static boolean isInTheSameRoomAsBlockEntity(BlockPos playerPos, BlockPos bePos, Level level) {
        Room playerRoom = RoomAnalyzer.scanFromAnyPos(playerPos,level,0);
        BlockPos pos = new BlockPos(bePos.getX(),playerPos.getY(),bePos.getZ());
        Room beRoom = RoomAnalyzer.scanFromAnyPos(pos,level,0);
        if (playerRoom != null && beRoom != null) {
            if (beRoom.getMiddlePosition().equals(playerRoom.getMiddlePosition())) {
                return true;
            } else return false;
        } else return false;
    }
}
