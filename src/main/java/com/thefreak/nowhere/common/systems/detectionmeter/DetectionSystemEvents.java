package com.thefreak.nowhere.common.systems.detectionmeter;

import com.thefreak.nowhere.API.IDetecteable;
import com.thefreak.nowhere.Nowhere;
import com.thefreak.nowhere.util.Constants;
import com.thefreak.nowhere.util.networking.NowhereNetwork;
import com.thefreak.nowhere.util.networking.toserver.SendUpdateDetectionStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Nowhere.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class DetectionSystemEvents {

    public static void setUndetected() {
        if (Constants.detectionState != Constants.UNDETECTED) {
            Constants.detectionState = Constants.UNDETECTED;
        }
    }
    public static void setMinimalDetected() {
        if (Constants.detectionState != Constants.MINIMAL_DETECTED) {
            Constants.detectionState = Constants.MINIMAL_DETECTED;
        }
    }
    public static void setDetected() {
        if (Constants.detectionState != Constants.DETECTED) {
            Constants.detectionState = Constants.DETECTED;
        }
    }
    public static void setFullyDetected() {
        if (Constants.detectionState != Constants.FULLY_DETECTED) {
            Constants.detectionState = Constants.FULLY_DETECTED;
        }
    }
    public static void setRun() {
        if (Constants.detectionState != Constants.RUN) {
            Constants.detectionState = Constants.RUN;
        }
    }

    public static int increaseDetectionLevel(int level) {
        if (level >= Constants.RUN) {
            return level;
        }else {
            return level + 1;
        }
    }

    public static int decreaseDetectionLevel(int level) {
        if (level <= Constants.UNDETECTED) {
            return level;
        }else {
            return level - 1;
        }
    }

    public static void setCurrentDetectionLevel(int level) {
        if (level >= Constants.UNDETECTED) {
            Constants.detectionState = level;
        }else if (level <= Constants.RUN) {
            Constants.detectionState = level;
        }
    }




    @SubscribeEvent
    public static void detectionCalculator(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player != null) {
            int level = 0;

            if (!player.isCrouching()) {
                level = increaseDetectionLevel(level);
            }
            if (player.isSprinting()) {
                level = increaseDetectionLevel(level);
            }
            if (player.getDeltaMovement().y > 0) {
                level = increaseDetectionLevel(level);
            }
            if (Constants.speachState >= Constants.NORMAL) {
                level = increaseDetectionLevel(level);
            }
            if (Constants.speachState == Constants.LOUD) {
                level = increaseDetectionLevel(level);
            }


            if (player instanceof IDetecteable detecteable) {
                detecteable.setDetectionLevel(level);
                setCurrentDetectionLevel(level);
                NowhereNetwork.INSTANCE.sendToServer(new SendUpdateDetectionStatus(level));
            }


        }
    }


}
