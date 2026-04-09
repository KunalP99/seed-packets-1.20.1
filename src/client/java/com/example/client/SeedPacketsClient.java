package com.example.client;

import com.example.registry.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class SeedPacketsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
				ModBlocks.WHEATROOT_CROP,
				ModBlocks.VINEBLOOM_CROP,
				ModBlocks.HARVEST_CROP,
				ModBlocks.GOLDEN_SPUD_CROP,
				ModBlocks.SUPREME_HARVEST_CROP
		);
	}
}