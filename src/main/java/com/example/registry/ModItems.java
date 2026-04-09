package com.example.registry;

import com.example.SeedPackets;
import com.example.item.SeedPacketItem;
import com.example.item.ScatterPacketItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItems {

    // -------------------------------------------------------------------------
    // Food components
    // -------------------------------------------------------------------------

    private static final FoodComponent WHEATROOT_FOOD = new FoodComponent.Builder()
            .hunger(3).saturationModifier(0.4f)
            .statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 0), 1.0f)
            .build();

    private static final FoodComponent VINEBLOOM_FOOD = new FoodComponent.Builder()
            .hunger(4).saturationModifier(0.6f)
            .statusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 240, 0), 1.0f)
            .build();

    private static final FoodComponent HARVEST_CROP_FOOD = new FoodComponent.Builder()
            .hunger(5).saturationModifier(0.8f)
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 160, 0), 1.0f)
            .build();

    private static final FoodComponent GOLDEN_SPUD_FOOD = new FoodComponent.Builder()
            .hunger(5).saturationModifier(0.8f)
            .statusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 300, 0), 1.0f)
            .build();

    private static final FoodComponent SUPREME_HARVEST_FOOD = new FoodComponent.Builder()
            .hunger(7).saturationModifier(1.2f)
            .statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 400, 0), 1.0f)
            .statusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 400, 0), 1.0f)
            .build();

    // -------------------------------------------------------------------------
    // Food items
    // -------------------------------------------------------------------------

    public static final Item WHEATROOT = new Item(new Item.Settings().food(WHEATROOT_FOOD));
    public static final Item VINEBLOOM = new Item(new Item.Settings().food(VINEBLOOM_FOOD));
    public static final Item HARVEST_CROP = new Item(new Item.Settings().food(HARVEST_CROP_FOOD));
    public static final Item GOLDEN_SPUD = new Item(new Item.Settings().food(GOLDEN_SPUD_FOOD));
    public static final Item SUPREME_HARVEST = new Item(new Item.Settings().food(SUPREME_HARVEST_FOOD));

    // -------------------------------------------------------------------------
    // Seed packet items
    // -------------------------------------------------------------------------

    public static final SeedPacketItem WHEATROOT_PACKET =
            new SeedPacketItem(ModBlocks.WHEATROOT_CROP, new Item.Settings().maxCount(1));

    public static final SeedPacketItem VINEBLOOM_PACKET =
            new SeedPacketItem(ModBlocks.VINEBLOOM_CROP, new Item.Settings().maxCount(1));

    public static final SeedPacketItem HARVEST_PACKET =
            new SeedPacketItem(ModBlocks.HARVEST_CROP, new Item.Settings().maxCount(1));

    public static final SeedPacketItem GOLDEN_SPUD_PACKET =
            new SeedPacketItem(ModBlocks.GOLDEN_SPUD_CROP, new Item.Settings().maxCount(1));

    public static final SeedPacketItem SUPREME_HARVEST_PACKET =
            new SeedPacketItem(ModBlocks.SUPREME_HARVEST_CROP, new Item.Settings().maxCount(1));

    public static final ScatterPacketItem SCATTER_PACKET =
            new ScatterPacketItem(new Item.Settings().maxCount(1));

    // -------------------------------------------------------------------------
    // Registration
    // -------------------------------------------------------------------------

    private static Item register(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(SeedPackets.MOD_ID, name), item);
    }

    public static void register() {
        // Food items first
        register("wheatroot", WHEATROOT);
        register("vinebloom", VINEBLOOM);
        register("harvest_crop", HARVEST_CROP);
        register("golden_spud", GOLDEN_SPUD);
        register("supreme_harvest", SUPREME_HARVEST);

        // Seed packet items
        register("wheatroot_packet", WHEATROOT_PACKET);
        register("vinebloom_packet", VINEBLOOM_PACKET);
        register("harvest_packet", HARVEST_PACKET);
        register("golden_spud_packet", GOLDEN_SPUD_PACKET);
        register("supreme_harvest_packet", SUPREME_HARVEST_PACKET);
        register("scatter_packet", SCATTER_PACKET);

        // Custom creative tab
        Registry.register(Registries.ITEM_GROUP,
                new Identifier(SeedPackets.MOD_ID, "seed_packets"),
                FabricItemGroup.builder()
                        .displayName(Text.translatable("itemGroup.seed-packets.seed_packets"))
                        .icon(() -> new ItemStack(SUPREME_HARVEST_PACKET))
                        .entries((context, entries) -> {
                            // Tier 1 packets + food
                            entries.add(WHEATROOT_PACKET);
                            entries.add(WHEATROOT);
                            entries.add(VINEBLOOM_PACKET);
                            entries.add(VINEBLOOM);
                            // Tier 2 packets + food
                            entries.add(HARVEST_PACKET);
                            entries.add(HARVEST_CROP);
                            entries.add(GOLDEN_SPUD_PACKET);
                            entries.add(GOLDEN_SPUD);
                            // Tier 3 packet + food
                            entries.add(SUPREME_HARVEST_PACKET);
                            entries.add(SUPREME_HARVEST);
                            // Scatter packet
                            entries.add(SCATTER_PACKET);
                        })
                        .build());
    }
}
