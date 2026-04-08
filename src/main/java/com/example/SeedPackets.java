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
		LOGGER.info("Seed Packets mod initialized.");
	}
}