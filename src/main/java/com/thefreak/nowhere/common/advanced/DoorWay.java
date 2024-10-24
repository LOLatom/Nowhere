package com.thefreak.nowhere.common.advanced;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.List;

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

    public BlockPos getOutPosition() {
        BlockPos pos = this.position;
        switch (direction) {
            case SOUTH ->pos = pos.south(2);
            case WEST ->pos = pos.west(2);
            case NORTH -> pos = pos.north(2);
            case EAST -> pos = pos.north(2);
        }
        return pos;
    }

    public BlockPos getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }

    public static CompoundTag encodeToNBT(DoorWay doorWay) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("xPos",doorWay.position.getX());
        tag.putInt("yPos",doorWay.position.getY());
        tag.putInt("zPos",doorWay.position.getZ());

        int direction = 0;

        switch (doorWay.getDirection()) {
            case NORTH -> direction = 0;
            case SOUTH -> direction = 1;
            case EAST -> direction = 2;
            case WEST -> direction = 3;
        }
        tag.putInt("direction", direction);

        return tag;
    }

    public static DoorWay decodeFromNBT(CompoundTag tag) {
        if (!tag.contains("direction") && !tag.contains("xPos") && !tag.contains("yPos") && !tag.contains("zPos")) {
            return new DoorWay(new BlockPos(0,0,0), Direction.NORTH);
        }

        Direction directionOfTag = tag.getInt("direction") == 0 ? Direction.NORTH :
                tag.getInt("direction") == 1 ? Direction.SOUTH :
                        tag.getInt("direction") == 2 ? Direction.EAST :
                                Direction.WEST;
        DoorWay doorWay = new DoorWay(new BlockPos(tag.getInt("xPos"),tag.getInt("yPos"),tag.getInt("zPos")), directionOfTag);
        return doorWay;
    }

    public static List<DoorWay> getListFromNBT(CompoundTag tag) {
        List<DoorWay> doorWayList = new ArrayList<>();
        for (int i = 0; i < tag.size(); i++) {
            String idString = String.valueOf(i);
            doorWayList.add(decodeFromNBT(tag.getCompound(idString)));

        }
        return doorWayList;
    }
    public static CompoundTag encodeListFromArray(List<DoorWay> doorWayList) {
        CompoundTag tag = new CompoundTag();
        for (int i = 0; i < doorWayList.size(); i++) {
            String idString = String.valueOf(i);
            tag.put(idString,encodeToNBT(doorWayList.get(i)));
        }

        return tag;
    }

}
