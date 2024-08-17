package com.thefreak.nowhere.common.advanced;

import net.minecraft.core.BlockPos;

import java.util.HashMap;

public class Room {
    private final HashMap<Integer, DoorWay> exitList;
    private final HashMap<Integer, DoorWay> entranceList;

    private final BlockPos middlePosition;

    private final int width;
    private final int length;
    private int height = 0;
    private final BlockPos initialPos;

    public Room(BlockPos initialPos, int width, int length, BlockPos middlePos, HashMap<Integer, DoorWay> exitList, HashMap<Integer, DoorWay> entranceList) {
        this.initialPos = initialPos;
        this.width = width;
        this.length = length;
        this.middlePosition = middlePos;
        this.exitList = exitList;
        this.entranceList = entranceList;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public BlockPos getMiddlePosition() {
        return middlePosition;
    }

    public BlockPos getInitialPos() {
        return initialPos;
    }

    public HashMap<Integer, DoorWay> getEntranceList() {
        return entranceList;
    }

    public HashMap<Integer, DoorWay> getExitList() {
        return exitList;
    }

}
