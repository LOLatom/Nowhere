package com.thefreak.nowhere.common.entities.AI;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class LocalisationHelper {

    /**
     * This method help to find The position around an Origin Position using the given distance and angle
     * @param origin The Origin position
     * @param radians The Angle of where the position is (In Radians or in Degrees if @param isAngle == true)
     * @param distance The Distance from the Origin position
     * @param isAngle Check if the given @param radians is an Angle or not
     * @return Will return the final position calculated
     */
    public static Vec3 findVecFromAngle(Vec3 origin, double radians, double distance, boolean isAngle) {
        double rads = isAngle ? Math.toRadians(radians) : radians;
        double x = origin.x() + ((-distance) * Math.sin(rads));
        double z = origin.z() + (distance * Math.cos(rads));
        return new Vec3(x,origin.y,z);
    }

    public static Vec3 getDirVec3(float yaw, float pitch) {
        double yawRadians = Math.toRadians(yaw);
        double pitchRadians = Math.toRadians(pitch);


        double directionX = -Math.sin(yawRadians) * Math.cos(pitchRadians);
        double directionY = -Math.sin(pitchRadians);
        double directionZ = Math.cos(yawRadians) * Math.cos(pitchRadians);


        double length = Math.sqrt(directionX * directionX + directionY * directionY + directionZ * directionZ);
        directionX /= length;
        directionY /= length;
        directionZ /= length;

        Vec3 playerDirection = new Vec3(directionX, directionY, directionZ);
        return playerDirection;
    }

    public static Vec3i blockPosToVec3(BlockPos pos) {
        return new Vec3i(pos.getX(),pos.getY(),pos.getZ());
    }

    public static BlockPos vec3ToBlockPos(Vec3i pos) {
        return new BlockPos(pos);
    }

    public static double getPlayerDegreesOfSight(LivingEntity currentEntity, LivingEntity target) {
        float yaw = target.getYHeadRot();
        float pitch = target.getXRot();

        Vec3 playerDirection = getDirVec3(yaw,pitch);


        Vec3 entityDirection = currentEntity.position().subtract(target.position()).normalize();


        double angle = Math.toDegrees(Math.acos(playerDirection.dot(entityDirection)));

        return angle;
    }
}
