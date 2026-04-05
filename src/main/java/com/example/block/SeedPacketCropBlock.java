package com.example.block;

import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;

public class SeedPacketCropBlock extends CropBlock {

    public SeedPacketCropBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        // Planting is handled exclusively by SeedPacketItem.useOnBlock.
        // Returning wheat seeds here satisfies the abstract method and
        // prevents players from placing this crop by any other means.
        return Items.WHEAT_SEEDS;
    }
}
