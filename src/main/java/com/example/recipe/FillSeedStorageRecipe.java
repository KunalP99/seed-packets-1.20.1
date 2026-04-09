package com.example.recipe;

import com.example.item.ScatterPacketItem;
import com.example.item.SeedPacketItem;
import com.example.item.SeedStorageItem;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class FillSeedStorageRecipe extends SpecialCraftingRecipe {

    public FillSeedStorageRecipe(Identifier id, CraftingRecipeCategory category) {
        super(id, category);
    }

    /** Returns true if this item can be deposited into a storage packet. */
    private static boolean isValidInput(ItemStack stack) {
        if (stack.getItem() instanceof SeedPacketItem && !(stack.getItem() instanceof ScatterPacketItem)) {
            return true;
        }
        String id = Registries.ITEM.getId(stack.getItem()).toString();
        return SeedStorageItem.VALID_SEEDS.contains(id);
    }

    /** How many uses/seeds this stack contributes when consumed (whole mod packet = its uses). */
    private static int usesContributed(ItemStack stack) {
        if (stack.getItem() instanceof SeedPacketItem) {
            return SeedPacketItem.getUses(stack);
        }
        return 1; // vanilla seeds: 1 per slot consumed
    }

    @Override
    public boolean matches(RecipeInputInventory inventory, World world) {
        ItemStack storageStack = ItemStack.EMPTY;
        String seedType = null;

        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (stack.isEmpty()) continue;

            if (stack.getItem() instanceof SeedStorageItem) {
                if (!storageStack.isEmpty()) return false;
                storageStack = stack;
            } else {
                if (!isValidInput(stack)) return false;
                String id = Registries.ITEM.getId(stack.getItem()).toString();
                if (seedType == null) {
                    seedType = id;
                } else if (!seedType.equals(id)) {
                    return false; // mixed types
                }
            }
        }

        if (storageStack.isEmpty() || seedType == null) return false;

        // If already has a type, must match
        String existing = SeedStorageItem.getStoredSeed(storageStack);
        if (existing != null && !existing.isEmpty() && !existing.equals(seedType)) return false;

        // Must have room
        int currentCount = SeedStorageItem.getSeedCount(storageStack);
        int capacity = ((SeedStorageItem) storageStack.getItem()).capacity;
        return currentCount < capacity;
    }

    @Override
    public ItemStack craft(RecipeInputInventory inventory, DynamicRegistryManager registryManager) {
        ItemStack storageStack = ItemStack.EMPTY;
        String seedType = null;
        int usesToAdd = 0;

        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (stack.isEmpty()) continue;
            if (stack.getItem() instanceof SeedStorageItem) {
                storageStack = stack;
            } else {
                seedType = Registries.ITEM.getId(stack.getItem()).toString();
                usesToAdd += usesContributed(stack);
            }
        }

        if (storageStack.isEmpty() || seedType == null) return ItemStack.EMPTY;

        ItemStack result = storageStack.copy();
        int capacity = ((SeedStorageItem) result.getItem()).capacity;
        int currentCount = SeedStorageItem.getSeedCount(result);
        SeedStorageItem.setStoredSeed(result, seedType);
        SeedStorageItem.setSeedCount(result, Math.min(currentCount + usesToAdd, capacity));
        return result;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.FILL_SEED_STORAGE;
    }
}
