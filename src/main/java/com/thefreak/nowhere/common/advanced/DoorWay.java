package com.thefreak.nowhere.common.advanced;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public class DoorWay {
    private final BlockPos position;

    private final Direction direction;
    private boolean blocked;

    public DoorWay(BlockPos position, Direction direction) {
        this.position = position;
        this.direction = direction;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public BlockPos getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }
}
