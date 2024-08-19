package com.thefreak.nowhere.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class ConnectedTextureWalls extends Block {
    public static final BooleanProperty C_UP = BooleanProperty.create("c_up");
    public static final BooleanProperty C_DOWN = BooleanProperty.create("c_down");

    public ConnectedTextureWalls(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.stateDefinition.any().setValue(C_UP, false).setValue(C_DOWN, false));
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos currentPos, BlockPos neighborPos) {
        boolean smthUp = world.getBlockState(currentPos.above()).getBlock() == state.getBlock();
        boolean smthDown = world.getBlockState(currentPos.below()).getBlock() == state.getBlock();

        state = state.setValue(C_UP, smthUp).setValue(C_DOWN, smthDown);

        return state;
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        boolean smthUp = pLevel.getBlockState(pPos.above()).getBlock() == pState.getBlock();
        boolean smthDown = pLevel.getBlockState(pPos.below()).getBlock() == pState.getBlock();

        BlockState newState = pState.setValue(C_UP, smthUp).setValue(C_DOWN, smthDown);

        if (newState != pState) {
            pLevel.setBlock(pPos, newState, 2);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(C_DOWN).add(C_UP);
    }
}