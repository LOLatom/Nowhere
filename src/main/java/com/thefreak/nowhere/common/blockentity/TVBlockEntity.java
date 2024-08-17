package com.thefreak.nowhere.common.blockentity;

import com.thefreak.nowhere.common.initiation.BlockEntityInitiation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TVBlockEntity extends BlockEntity {
    public TVBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }
    public TVBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        this(BlockEntityInitiation.TV_BE.get(), pWorldPosition, pBlockState);
    }

}
