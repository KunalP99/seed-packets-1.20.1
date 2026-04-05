package com.example.registry;

import com.example.SeedPackets;
import com.example.block.SeedPacketCropBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final SeedPacketCropBlock WHEATROOT_CROP = new SeedPacketCropBlock(
            FabricBlockSettings.copyOf(Blocks.WHEAT));

    public static final SeedPacketCropBlock PUMPKIN_MELON_CROP = new SeedPacketCropBlock(
            FabricBlockSettings.copyOf(Blocks.WHEAT));

    public static final SeedPacketCropBlock HARVEST_CROP = new SeedPacketCropBlock(
            FabricBlockSettings.copyOf(Blocks.WHEAT));

    public static final SeedPacketCropBlock GOLDEN_SPUD_CROP = new SeedPacketCropBlock(
            FabricBlockSettings.copyOf(Blocks.WHEAT));

    public static final SeedPacketCropBlock SUPREME_HARVEST_CROP = new SeedPacketCropBlock(
            FabricBlockSettings.copyOf(Blocks.WHEAT));

    private static <T extends Block> T register(String name, T block) {
        return Registry.register(Registries.BLOCK, new Identifier(SeedPackets.MOD_ID, name), block);
    }

    public static void register() {
        register("wheatroot_crop", WHEATROOT_CROP);
        register("pumpkin_melon_crop", PUMPKIN_MELON_CROP);
        register("harvest_crop", HARVEST_CROP);
        register("golden_spud_crop", GOLDEN_SPUD_CROP);
        register("supreme_harvest_crop", SUPREME_HARVEST_CROP);
    }
}
