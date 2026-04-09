package com.example;

import com.example.block.SeedPacketCropBlock;
import com.example.item.SeedPacketItem;
import com.example.recipe.ModRecipes;
import com.example.registry.ModBlocks;
import com.example.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.item.ItemStack;
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

		// When a fully-grown crop is broken, merge 1 use into an existing matching
		// packet in the player's inventory rather than dropping a loose item.
		PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
			if (world.isClient) return;
			if (!(state.getBlock() instanceof SeedPacketCropBlock crop)) return;
			SeedPacketItem packetItem = crop.getPacketItem();
			if (packetItem == null || !crop.isFullyGrown(state)) return;

			var inventory = player.getInventory();
			for (int i = 0; i < inventory.size(); i++) {
				ItemStack slot = inventory.getStack(i);
				if (slot.isOf(packetItem) && SeedPacketItem.getUses(slot) < SeedPacketItem.MAX_USES) {
					SeedPacketItem.setUses(slot, Math.min(SeedPacketItem.getUses(slot) + 1, SeedPacketItem.MAX_USES));
					return;
				}
			}

			// No existing packet — give a fresh 1-use packet directly, or drop if full.
			ItemStack newPacket = new ItemStack(packetItem);
			SeedPacketItem.setUses(newPacket, 1);
			if (!inventory.insertStack(newPacket)) {
				player.dropItem(newPacket, false);
			}
		});

		LOGGER.info("Seed Packets mod initialized.");
	}
}