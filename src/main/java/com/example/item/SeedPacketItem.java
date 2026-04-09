package com.example.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;
import java.util.List;

public class SeedPacketItem extends Item {

    public static final int DEFAULT_USES = 5;
    public static final int MAX_USES = 64;
    private static final String USES_KEY = "SeedUses";

    private final Block cropBlock;

    public SeedPacketItem(Block cropBlock, Settings settings) {
        super(settings);
        this.cropBlock = cropBlock;
    }

    public static int getUses(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        if (nbt != null && nbt.contains(USES_KEY)) {
            return nbt.getInt(USES_KEY);
        }
        return DEFAULT_USES;
    }

    public static void setUses(ItemStack stack, int uses) {
        stack.getOrCreateNbt().putInt(USES_KEY, uses);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        int uses = getUses(stack);
        String label = uses == 1 ? "1 use left" : uses + " uses left";
        tooltip.add(Text.literal(label).formatted(Formatting.GRAY));
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return false;
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
            world.setBlockState(above, cropBlock.getDefaultState());
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
