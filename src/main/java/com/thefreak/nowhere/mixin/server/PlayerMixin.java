package com.thefreak.nowhere.mixin.server;

import com.thefreak.nowhere.API.IDetecteable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin implements IDetecteable {

    @Shadow protected abstract void turtleHelmetTick();

    @Unique
    private int currentDetectionLevel = 0;


    @Inject(method = "readAdditionalSaveData",at = @At("TAIL"),cancellable = true)
    private void nowhere$readAdditional(CompoundTag pCompound, CallbackInfo ci) {
        this.setDetectionLevel(pCompound.contains("detectionLevel") ? pCompound.getInt("detectionLevel") : 0);
    }


    @Inject(method = "addAdditionalSaveData",at = @At("TAIL"),cancellable = true)
    private void nowhere$addAdditional(CompoundTag pCompound, CallbackInfo ci) {
        pCompound.putInt("detectionLevel", this.getDetectionLevel());
    }

    @Override
    public int getDetectionLevel() {
        return this.currentDetectionLevel;
    }

    @Override
    public void setDetectionLevel(int level) {
        this.currentDetectionLevel = level;
    }
}
