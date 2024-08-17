package com.thefreak.nowhere.common.advanced;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseCoralPlantBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.GrassBlock;

import java.util.HashMap;
import java.util.List;

public class RoomAnalyzer {

    public static final int ITTERATION_CAP = 7;

    public static Room scanFromPos(BlockPos pos, Level level, int itteration, List<BlockPos> blockedList) {
        System.out.println("STARTING");
        if (blockedList.contains(pos)) {
            System.out.println("BLOCKED");
            return null;
        }

        boolean isARoom = false;
        if (level.getBlockState(pos.west()).isAir() &&
            level.getBlockState(pos.north()).isAir() &&
            level.getBlockState(pos.east()).isAir() &&
            level.getBlockState(pos.south()).isAir()) {
            System.out.println("AIR EVERYWHERE");
            return null;
        }
        HashMap<Integer, BlockPos> positions = new HashMap<>();
        HashMap<Integer, DoorWay> entrance = new HashMap<>();
        HashMap<Integer, DoorWay> exits = new HashMap<>();
        positions.put(0,pos);
        for (int i = 0; i <= 100; i++) {
            //System.out.println("STARTING DETECTION");
            if (whichWayToGo(level,positions.get(i)) == Direction.EAST) {
                    if (level.getBlockState(positions.get(i).south()).getBlock() instanceof DoorBlock && i != 0) {
                        //blockedList.add(positions.get(i).south().south());
                        if (itteration != 0) {
                            blockedList.add(pos);
                        }

                        Room room = scanFromPos(positions.get(i).south().south(), level, itteration + 1, blockedList);
                        System.out.println(positions.get(i).south().south());
                        if (room != null) {

                            DoorWay newDoorWay = new DoorWay(positions.get(i), Direction.SOUTH);
                            if (isHallway(room.getWidth(), room.getLength())) {
                                exits.put(exits.size(), newDoorWay);
                            } else {
                                entrance.put(entrance.size(), newDoorWay);
                            }
                        }
                    }

                if (positions.get(0).equals(positions.get(i).east()) && i != 0) {
                    isARoom = true;
                    break;
                }
                positions.put(i + 1,positions.get(i).east());
            }else if (whichWayToGo(level,positions.get(i)) == Direction.WEST) {

                    if (level.getBlockState(positions.get(i).north()).getBlock() instanceof DoorBlock && i != 0) {
                        //blockedList.add(positions.get(i).north().north());
                        if (itteration != 0) {
                            blockedList.add(pos);
                        }

                        Room room = scanFromPos(positions.get(i).north().north(), level, itteration + 1, blockedList);
                        System.out.println(positions.get(i).north().north());
                        if (room != null) {
                            DoorWay newDoorWay = new DoorWay(positions.get(i), Direction.NORTH);
                            if (isHallway(room.getWidth(), room.getLength())) {
                                exits.put(exits.size(), newDoorWay);
                            } else {
                                entrance.put(entrance.size(), newDoorWay);
                            }
                        }

                }
                if (positions.get(0).equals(positions.get(i).west()) && i != 0) {
                    isARoom = true;
                    break;
                }
                positions.put(i + 1,positions.get(i).west());
            }else if (whichWayToGo(level,positions.get(i)) == Direction.SOUTH) {

                    if (level.getBlockState(positions.get(i).west()).getBlock() instanceof DoorBlock && i != 0) {
                        //blockedList.add(positions.get(i).west().west());
                        if (itteration != 0) {
                            blockedList.add(pos);
                        }
                        Room room = scanFromPos(positions.get(i).west().west(), level, itteration + 1, blockedList);
                        System.out.println(positions.get(i).west().west());
                        if (room != null) {
                            DoorWay newDoorWay = new DoorWay(positions.get(i), Direction.WEST);
                            if (isHallway(room.getWidth(), room.getLength())) {
                                exits.put(exits.size(), newDoorWay);
                            } else {
                                entrance.put(entrance.size(), newDoorWay);
                            }
                        }

                }
                if (positions.get(0).equals(positions.get(i).south()) && i != 0) {
                    isARoom = true;
                    break;
                }
                positions.put(i + 1,positions.get(i).south());
            }else if (whichWayToGo(level,positions.get(i)) == Direction.NORTH) {

                    if (level.getBlockState(positions.get(i).east()).getBlock() instanceof DoorBlock && i != 0) {
                        //blockedList.add(positions.get(i).east().east());
                        if (itteration != 0) {
                            blockedList.add(pos);
                        }
                        Room room = scanFromPos(positions.get(i).east().east(), level, itteration + 1, blockedList);
                        System.out.println(positions.get(i).east().east());
                        if (room != null) {
                            DoorWay newDoorWay = new DoorWay(positions.get(i), Direction.EAST);
                            if (isHallway(room.getWidth(), room.getLength())) {
                                exits.put(exits.size(), newDoorWay);
                            } else {
                                entrance.put(entrance.size(), newDoorWay);
                            }
                        }

                }
                if (positions.get(0).equals(positions.get(i).north()) && i != 0) {
                    isARoom = true;
                    break;
                }
                positions.put(i + 1,positions.get(i).north());
            }
        }
        BlockPos positionA = positions.get(0);
        BlockPos positionB = positions.get(0);
        for (BlockPos posis: positions.values()) {
            for (BlockPos posis2: positions.values()) {
                if (posis.distSqr(new Vec3i(posis2.getX(),posis2.getY(),posis2.getZ())) >
                        positionA.distSqr(new Vec3i(positionB.getX(),positionB.getY(),positionB.getZ()))) {
                    positionA = posis;
                    positionB = posis2;

                }
            }
        }
        int width = 0;
        int length = 0;

        double middleX = 0;
        double middleZ = 0;

        if (positionA.getX() > positionB.getX()) {
            width = positionA.getX() - positionB.getX();
            middleX = positionB.getX() + (width/2);
        } else if (positionA.getX() < positionB.getX()) {
            width = positionB.getX() - positionA.getX();
            middleX = positionA.getX() + (width/2);
        }
        if (positionA.getZ() > positionB.getZ()) {
            length = positionA.getZ() - positionB.getZ();
            middleZ = positionB.getZ() + (length/2);
        } else if (positionA.getZ() < positionB.getZ()) {
            length = positionB.getZ() - positionA.getZ();
            middleZ = positionA.getZ() + (length/2);
        }

        BlockPos middlePosition = new BlockPos((int) middleX,positionA.getY(),(int) middleZ);



        width++;
        length++;
        System.out.println("Width : " + width + " Length : " + length);
        System.out.println("Perimeter : " + positions.size());
        System.out.println("Center Position : " + middlePosition);
        for (DoorWay doorWay: entrance.values()) {
            System.out.println("Entrance : " + doorWay.getPosition() + " Direction : " + doorWay.getDirection());
        }
        for (DoorWay doorWay: exits.values()) {
            System.out.println("Exits : " + doorWay.getPosition() + " Direction : " + doorWay.getDirection());
        }
        //System.out.println(blockedList);

        return isARoom ? new Room(pos,width,length,middlePosition,exits,entrance) : null;
    }


    public static boolean isHallway(int width, int length) {
        if (width > 2.3 * length) {
            return true;
        } else if (length > 2.3 * width) {
            return true;
        } else {
            return false;
        }
    }

    public static Room scanFromAnyPos(BlockPos pos, Level level, int itteration, List<BlockPos> blockedPositions) {

        for (int i = 0; i <= 60; i++) {
            BlockPos pos1 = pos.west(i);
            if (!isAirOrMore(level,pos1)) {
                return scanFromPos(pos1.east(),level, itteration, blockedPositions);
            }
        }
        return null;

    }

    public static boolean isAirOrMore(Level level, BlockPos pos) {
        if (level.getBlockState(pos).isAir() || ((!(level.getBlockState(pos).isCollisionShapeFullBlock((BlockGetter) level,pos)) && !(level.getBlockState(pos).getBlock() instanceof DoorBlock))) ) {
            if ((level.getBlockState(pos.below()).getBlock() instanceof GrassBlock)) return false;
            return true;
        } else {
            return false;
        }
    }

    public static Direction outwardCornersWWTG(Level level, BlockPos pos) {
        if (isAirOrMore(level,pos.east()) &&
                isAirOrMore(level,pos.west()) &&
                isAirOrMore(level,pos.south()) &&
                isAirOrMore(level,pos.north()) &&
                !isAirOrMore(level,pos.north().east())) {
            return Direction.NORTH;
        } else if (isAirOrMore(level,pos.east()) &&
                isAirOrMore(level,pos.west()) &&
                isAirOrMore(level,pos.south()) &&
                isAirOrMore(level,pos.north()) &&
                !isAirOrMore(level,pos.north().west())) {
            return Direction.WEST;
        } else if (isAirOrMore(level,pos.east()) &&
                isAirOrMore(level,pos.west()) &&
                isAirOrMore(level,pos.south()) &&
                isAirOrMore(level,pos.north()) &&
                !isAirOrMore(level,pos.south().east())) {
            return Direction.EAST;
        } else if (isAirOrMore(level,pos.east()) &&
                isAirOrMore(level,pos.west()) &&
                isAirOrMore(level,pos.south()) &&
                isAirOrMore(level,pos.north()) &&
                !isAirOrMore(level,pos.south().west())) {
            return Direction.SOUTH;
        }
        return Direction.NORTH;
    }
    public static Direction whichWayToGo(Level level, BlockPos pos) {
        if (isAirOrMore(level,pos.east()) &&
                isAirOrMore(level,pos.west()) &&
                isAirOrMore(level,pos.south()) &&
                !isAirOrMore(level,pos.north()) ) {
            return Direction.WEST;
        } else if (isAirOrMore(level,pos.east()) &&
                isAirOrMore(level,pos.west()) &&
                !isAirOrMore(level,pos.south()) &&
                isAirOrMore(level,pos.north()) ) {
            return Direction.EAST;
        } else if (isAirOrMore(level,pos.east()) &&
                !isAirOrMore(level,pos.west()) &&
                isAirOrMore(level,pos.south()) &&
                isAirOrMore(level,pos.north()) ) {
            return Direction.SOUTH;
        } else if (!isAirOrMore(level,pos.east()) &&
                isAirOrMore(level,pos.west()) &&
                isAirOrMore(level,pos.south()) &&
                isAirOrMore(level,pos.north()) ) {
            return Direction.NORTH;
        } else if (isAirOrMore(level,pos.east()) &&
                !isAirOrMore(level,pos.west()) &&
                isAirOrMore(level,pos.south()) &&
                !isAirOrMore(level,pos.north()) ) {
            return Direction.SOUTH;
        } else if (!isAirOrMore(level,pos.east()) &&
                isAirOrMore(level,pos.west()) &&
                !isAirOrMore(level,pos.south()) &&
                isAirOrMore(level,pos.north()) ) {
            return Direction.NORTH;
        } else if (isAirOrMore(level,pos.east()) &&
                !isAirOrMore(level,pos.west()) &&
                !isAirOrMore(level,pos.south()) &&
                isAirOrMore(level,pos.north()) ) {
            return Direction.EAST;
        } else if (!isAirOrMore(level,pos.east()) &&
                isAirOrMore(level,pos.west()) &&
                isAirOrMore(level,pos.south()) &&
                !isAirOrMore(level,pos.north()) ) {
            return Direction.WEST;
        } else {
            return outwardCornersWWTG(level,pos);
        }
    }


}
