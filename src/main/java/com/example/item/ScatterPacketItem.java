package com.example.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.List;

public class ScatterPacketItem extends SeedPacketItem {

    private static final List<Block> VANILLA_CROPS = List.of(
            Blocks.WHEAT,
            Blocks.CARROTS,
            Blocks.POTATOES,
            Blocks.BEETROOTS,
            Blocks.MELON_STEM,
            Blocks.PUMPKIN_STEM
    );

    public ScatterPacketItem(Settings settings) {
        super(Blocks.WHEAT, settings); // cropBlock unused — useOnBlock is overridden
    }

    @Override
    public void onCraft(ItemStack stack, World world, PlayerEntity player) {
        setUses(stack, 15);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState clicked = world.getBlockState(pos);

        if (!clicked.isOf(Blocks.FARMLAND)) {
            return ActionResult.PASS;
        }

        BlockPos above = pos.up();
        if (!world.getBlockState(above).isAir()) {
            return ActionResult.PASS;
        }

        world.playSound(context.getPlayer(), pos, SoundEvents.ITEM_CROP_PLANT,
                SoundCategory.BLOCKS, 1.0f, 1.0f);

        if (!world.isClient) {
            Random random = world.getRandom();
            Block crop = VANILLA_CROPS.get(random.nextInt(VANILLA_CROPS.size()));
            world.setBlockState(above, crop.getDefaultState());

            ItemStack stack = context.getStack();
            int remaining = getUses(stack) - 1;
            if (remaining <= 0) {
                stack.decrement(1);
            } else {
                setUses(stack, remaining);
            }
        }

        return ActionResult.SUCCESS;
    }
}
