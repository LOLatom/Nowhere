package com.thefreak.nowhere.common.advanced;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Room {
    private final HashMap<Integer, DoorWay> exitList;
    private final HashMap<Integer, DoorWay> entranceList;

    private final BlockPos middlePosition;

    private final int width;
    private final int length;
    private int height = 0;
    private final BlockPos initialPos;

    private final boolean hallway;

    public Room(BlockPos initialPos, int width, int length, BlockPos middlePos, HashMap<Integer, DoorWay> exitList, HashMap<Integer, DoorWay> entranceList) {
        this.initialPos = initialPos;
        this.width = width;
        this.length = length;
        this.middlePosition = middlePos;
        this.exitList = exitList;
        this.entranceList = entranceList;
        this.hallway = this.isHallway(width,length);
    }

    public List<Vec3> getExtremities(int offset) {
        List<Vec3> list = new ArrayList<>();
        if (width >= length) {
            Vec3 firstVec = new Vec3(this.middlePosition.east(width / 2 - offset).getX() , this.middlePosition.getY(),this.middlePosition.getZ() + 0.5);
            Vec3 secondVec = new Vec3(this.middlePosition.west(width / 2 - offset).getX() , this.middlePosition.getY(),this.middlePosition.getZ() + 0.5);
            list.add(firstVec);
            list.add(secondVec);
            return list;
        } else if (length >= width) {
            Vec3 firstVec = new Vec3(this.middlePosition.getX() + 0.5 , this.middlePosition.getY(),this.middlePosition.south(length / 2 - offset).getZ());
            Vec3 secondVec = new Vec3(this.middlePosition.getX() + 0.5 , this.middlePosition.getY(),this.middlePosition.north(length / 2 - offset).getZ());
            list.add(firstVec);
            list.add(secondVec);
            return list;
        }


        return list;
    }

    public List<BlockPos> getPathToRoom(BlockPos centerTargetPosition, Level level) {
        return this.getPathToRoom(centerTargetPosition,level,0);
    }

    public List<BlockPos> getPathToRoom(BlockPos centerTargetPosition, Level level, int iterations) {
        return this.getPathToRoom(centerTargetPosition,level,iterations,new ArrayList<>(),new ArrayList<>());
    }

    public List<BlockPos> getPathToRoom(BlockPos centerTargetPosition, Level level, int iterations, List<BlockPos> path, List<BlockPos> visitedRooms) {
        // Base case: If iterations exceed a cap, return an empty list
        if (iterations >= 10) return new ArrayList<>();

        // Add the current room's middle position to visited rooms to avoid revisiting it
        visitedRooms.add(this.getMiddlePosition());

        // Check entrances to see if any lead directly to the target room
        for (DoorWay doorWay : this.entranceList.values()) {
            BlockPos doorOutPos = doorWay.getOutPosition();

            // Scan the room that this door leads to
            Room nextRoom = RoomAnalyzer.scanFromAnyPos(doorOutPos, level, 0, visitedRooms);

            // If this room is the target room, return the path leading to it
            if (nextRoom != null && nextRoom.getMiddlePosition().equals(centerTargetPosition)) {
                path.add(doorWay.getPosition()); // Add the doorway leading to the target room
                return path; // Return the path to the target
            }
        }

        // Check exits as well for the target room
        for (DoorWay doorWay : this.exitList.values()) {
            BlockPos doorOutPos = doorWay.getOutPosition();

            // Scan the room that this door leads to
            Room nextRoom = RoomAnalyzer.scanFromAnyPos(doorOutPos, level, 0, visitedRooms);

            // If this room is the target room, return the path leading to it
            if (nextRoom != null && nextRoom.getMiddlePosition().equals(centerTargetPosition)) {
                path.add(doorWay.getPosition()); // Add the doorway leading to the target room
                return path; // Return the path to the target
            }
        }

        // If no direct path is found, recursively search through entrances and exits of connected rooms
        for (DoorWay doorWay : this.entranceList.values()) {
            BlockPos doorOutPos = doorWay.getOutPosition();

            // Scan the next room through the current entrance
            Room nextRoom = RoomAnalyzer.scanFromAnyPos(doorOutPos, level, 0, visitedRooms);

            // Recursively search if the next room is not null and has not been visited yet
            if (nextRoom != null && !visitedRooms.contains(nextRoom.getMiddlePosition())) {
                path.add(doorWay.getPosition()); // Add the door to the path

                // Recursively search in the next room
                List<BlockPos> result = nextRoom.getPathToRoom(centerTargetPosition, level, iterations + 1, path, visitedRooms);

                // If a valid path is found, return it
                if (!result.isEmpty()) {
                    return result;
                } else {
                    // Backtrack if the path is a dead end
                    path.remove(path.size() - 1);
                }
            }
        }

        // Same recursive search for exits
        for (DoorWay doorWay : this.exitList.values()) {
            BlockPos doorOutPos = doorWay.getOutPosition();

            // Scan the next room through the current exit
            Room nextRoom = RoomAnalyzer.scanFromAnyPos(doorOutPos, level, 0, visitedRooms);

            // Recursively search if the next room is not null and has not been visited yet
            if (nextRoom != null && !visitedRooms.contains(nextRoom.getMiddlePosition())) {
                path.add(doorWay.getPosition()); // Add the door to the path

                // Recursively search in the next room
                List<BlockPos> result = nextRoom.getPathToRoom(centerTargetPosition, level, iterations + 1, path, visitedRooms);

                // If a valid path is found, return it
                if (!result.isEmpty()) {
                    return result;
                } else {
                    // Backtrack if the path is a dead end
                    path.remove(path.size() - 1);
                }
            }
        }

        // If no path is found, return an empty list
        return new ArrayList<>();
    }


    public boolean isHallway(int width, int length) {
        if (width > 2.3 * length) {
            return true;
        } else if (length > 2.3 * width) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isHallway() {
        return this.hallway;
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
