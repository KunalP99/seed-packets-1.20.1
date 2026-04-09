package com.example.block;

import com.example.item.SeedPacketItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;

public class SeedPacketCropBlock extends CropBlock {

    private SeedPacketItem packetItem = null;

    public SeedPacketCropBlock(Settings settings) {
        super(settings);
    }

    public void setPacketItem(SeedPacketItem item) {
        this.packetItem = item;
    }

    public SeedPacketItem getPacketItem() {
        return packetItem;
    }

    public boolean isFullyGrown(BlockState state) {
        return state.get(getAgeProperty()) == getMaxAge();
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        // Planting is handled exclusively by SeedPacketItem.useOnBlock.
        // Returning wheat seeds here satisfies the abstract method and
        // prevents players from placing this crop by any other means.
        return Items.WHEAT_SEEDS;
    }
}
