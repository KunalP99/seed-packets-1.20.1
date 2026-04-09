package com.example.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public class VinebloomFruitBlock extends Block {

    public VinebloomFruitBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        // Can sit on any solid block — mirrors vanilla melon behaviour.
        return world.getBlockState(pos.down()).isSolidBlock(world, pos.down());
    }
}
