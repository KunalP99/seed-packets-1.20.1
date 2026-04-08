package com.example.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
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

    private final Block cropBlock;

    public SeedPacketItem(Block cropBlock, Settings settings) {
        super(settings);
        this.cropBlock = cropBlock;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        int usesLeft = stack.getMaxDamage() - stack.getDamage();
        String label = usesLeft == 1 ? "1 use left" : usesLeft + " uses left";
        tooltip.add(Text.literal(label).formatted(Formatting.GRAY));
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
            PlayerEntity player = context.getPlayer();
            if (player != null) {
                stack.damage(1, player, p -> p.sendToolBreakStatus(context.getHand()));
            } else {
                // Fallback for non-player use
                stack.setDamage(stack.getDamage() + 1);
                if (stack.getDamage() >= stack.getMaxDamage()) {
                    stack.decrement(1);
                }
            }
        }

        return ActionResult.SUCCESS;
    }
}
