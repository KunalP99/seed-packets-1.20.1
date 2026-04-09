package com.example.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public class AttachedVinebloomStemBlock extends PlantBlock {

    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    private Block stemBlock;
    private Block fruitBlock;

    public AttachedVinebloomStemBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    public void setStemBlock(Block stemBlock) {
        this.stemBlock = stemBlock;
    }

    public void setFruitBlock(Block fruitBlock) {
        this.fruitBlock = fruitBlock;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState,
                                                 WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        // When the fruit in the facing direction is removed, revert to a mature stem.
        if (direction == state.get(FACING) && fruitBlock != null && !neighborState.isOf(fruitBlock)) {
            if (stemBlock != null) {
                return stemBlock.getDefaultState().with(CropBlock.AGE, CropBlock.MAX_AGE);
            }
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(Blocks.FARMLAND);
    }
}
