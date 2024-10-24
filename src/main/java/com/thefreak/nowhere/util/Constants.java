package com.thefreak.nowhere.util;

import net.minecraft.core.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class Constants {

    public static final int MUTE = 0;
    public static final int WISHPER = 1;
    public static final int NORMAL = 2;
    public static final int LOUD = 3;

    public static final int UNDETECTED = 0;
    public static final int MINIMAL_DETECTED = 1;
    public static final int DETECTED = 2;
    public static final int FULLY_DETECTED = 3;
    public static final int RUN = 4;


    public static List<BlockPos> tvPositions = new ArrayList<>();
    public static int speachState = 0;

    public static int ticksSinceLastSpoken;

    public static int detectionState = 0;
}
