package com.example.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class VinebloomStemBlock extends CropBlock {

    private Block fruitBlock;
    private Block attachedStemBlock;

    public VinebloomStemBlock(Settings settings) {
        super(settings);
    }

    public void setFruitBlock(Block fruitBlock) {
        this.fruitBlock = fruitBlock;
    }

    public void setAttachedStemBlock(Block attachedStemBlock) {
        this.attachedStemBlock = attachedStemBlock;
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        // Planting is handled exclusively by SeedPacketItem — this satisfies the abstract method.
        return Items.WHEAT_SEEDS;
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(Blocks.FARMLAND);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!this.isMature(state)) {
            super.randomTick(state, world, pos, random);
        } else {
            // Try to spawn the fruit at the same probability as normal growth.
            float f = CropBlock.getAvailableMoisture(this, world, pos);
            if (random.nextInt((int)(25.0F / f) + 1) == 0) {
                trySpawnFruit(world, pos, random);
            }
        }
    }

    private void trySpawnFruit(ServerWorld world, BlockPos pos, Random random) {
        if (fruitBlock == null || attachedStemBlock == null) return;

        Direction[] dirs = { Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST };
        Direction dir = dirs[random.nextInt(dirs.length)];
        BlockPos targetPos = pos.offset(dir);

        // Mirrors vanilla melon: air above any solid block.
        if (world.getBlockState(targetPos).isAir()
                && world.getBlockState(targetPos.down()).isSolidBlock(world, targetPos.down())) {
            world.setBlockState(targetPos, fruitBlock.getDefaultState());
            world.setBlockState(pos, attachedStemBlock.getDefaultState()
                    .with(AttachedVinebloomStemBlock.FACING, dir));
        }
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        if (this.isMature(state)) {
            if (fruitBlock == null || attachedStemBlock == null) return false;
            // Bone meal allowed only if there is no adjacent fruit already.
            for (Direction dir : new Direction[]{ Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST }) {
                if (world.getBlockState(pos.offset(dir)).isOf(fruitBlock)) return false;
            }
            return true;
        }
        return super.canGrow(world, random, pos, state);
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        if (this.isMature(state)) {
            trySpawnFruit(world, pos, random);
        } else {
            super.grow(world, random, pos, state);
        }
    }
}
