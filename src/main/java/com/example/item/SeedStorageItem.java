package com.example.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public class SeedStorageItem extends Item {

    public static final Set<String> VALID_SEEDS = Set.of(
            "minecraft:wheat_seeds",
            "minecraft:beetroot_seeds",
            "minecraft:pumpkin_seeds",
            "minecraft:melon_seeds",
            "minecraft:carrot",
            "minecraft:potato"
    );

    private static final String SEED_TYPE_KEY = "StoredSeed";
    private static final String SEED_COUNT_KEY = "SeedCount";

    public final int capacity;

    public SeedStorageItem(int capacity, Settings settings) {
        super(settings);
        this.capacity = capacity;
    }

    public static @Nullable String getStoredSeed(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        if (nbt != null && nbt.contains(SEED_TYPE_KEY)) {
            return nbt.getString(SEED_TYPE_KEY);
        }
        return null;
    }

    public static void setStoredSeed(ItemStack stack, String seedId) {
        stack.getOrCreateNbt().putString(SEED_TYPE_KEY, seedId);
    }

    public static int getSeedCount(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        if (nbt != null && nbt.contains(SEED_COUNT_KEY)) {
            return nbt.getInt(SEED_COUNT_KEY);
        }
        return 0;
    }

    public static void setSeedCount(ItemStack stack, int count) {
        stack.getOrCreateNbt().putInt(SEED_COUNT_KEY, count);
    }

    public static boolean isEmpty(ItemStack stack) {
        String seed = getStoredSeed(stack);
        return seed == null || seed.isEmpty() || getSeedCount(stack) <= 0;
    }

    @Override
    public Text getName(ItemStack stack) {
        String seedType = getStoredSeed(stack);
        if (seedType == null || seedType.isEmpty() || getSeedCount(stack) <= 0) {
            return super.getName(stack); // "Empty Storage Packet" etc.
        }
        Item seedItem = Registries.ITEM.get(Identifier.tryParse(seedType));
        MutableText seedName = new ItemStack(seedItem).getName().copy();
        // Mod seed packets already end in "Packet" — use their name as-is.
        // Vanilla seeds get " Packet" appended (e.g. "Wheat Seeds Packet").
        if (seedItem instanceof SeedPacketItem) {
            return seedName;
        }
        return seedName.append(" Packet");
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        String seedType = getStoredSeed(stack);
        int count = getSeedCount(stack);
        int cap = ((SeedStorageItem) stack.getItem()).capacity;

        if (seedType == null || seedType.isEmpty() || count <= 0) {
            tooltip.add(Text.literal("Empty").formatted(Formatting.GRAY));
        } else {
            Item seedItem = Registries.ITEM.get(Identifier.tryParse(seedType));
            MutableText line = new ItemStack(seedItem).getName().copy();
            line.append(Text.literal(": " + count + " / " + cap));
            line.formatted(Formatting.GRAY);
            tooltip.add(line);
        }
        tooltip.add(Text.literal("Place with seeds in crafting grid to fill").formatted(Formatting.DARK_GRAY));
    }
}
