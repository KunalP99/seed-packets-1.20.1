package com.example.block;

import com.example.item.SeedPacketItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.context.LootContextParameterSet;

import java.util.ArrayList;
import java.util.List;

public class SeedPacketCropBlock extends CropBlock {

    private SeedPacketItem packetItem = null;

    public SeedPacketCropBlock(Settings settings) {
        super(settings);
    }

    public void setPacketItem(SeedPacketItem item) {
        this.packetItem = item;
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        // Planting is handled exclusively by SeedPacketItem.useOnBlock.
        // Returning wheat seeds here satisfies the abstract method and
        // prevents players from placing this crop by any other means.
        return Items.WHEAT_SEEDS;
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
        List<ItemStack> drops = new ArrayList<>(super.getDroppedStacks(state, builder));
        if (packetItem != null && state.get(getAgeProperty()) == getMaxAge()) {
            ItemStack seed = new ItemStack(packetItem);
            SeedPacketItem.setUses(seed, 1);
            drops.add(seed);
        }
        return drops;
    }
}
