package com.example;

import com.example.recipe.ModRecipes;
import com.example.registry.ModBlocks;
import com.example.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeedPackets implements ModInitializer {
	public static final String MOD_ID = "seed-packets";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModBlocks.register();
		ModItems.register();
		ModRecipes.register();

		// Wire packet items into crop blocks for the 1-use seed drop on harvest.
		// Done here (after both registries init) to avoid circular static init.
		ModBlocks.WHEATROOT_CROP.setPacketItem(ModItems.WHEATROOT_PACKET);
		ModBlocks.VINEBLOOM_CROP.setPacketItem(ModItems.VINEBLOOM_PACKET);
		ModBlocks.HARVEST_CROP.setPacketItem(ModItems.HARVEST_PACKET);
		ModBlocks.GOLDEN_SPUD_CROP.setPacketItem(ModItems.GOLDEN_SPUD_PACKET);
		ModBlocks.SUPREME_HARVEST_CROP.setPacketItem(ModItems.SUPREME_HARVEST_PACKET);

		LOGGER.info("Seed Packets mod initialized.");
	}
}