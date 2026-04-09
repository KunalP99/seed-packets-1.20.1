package com.example.recipe;

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

    @Override
    public boolean matches(RecipeInputInventory inventory, World world) {
        ItemStack storageStack = ItemStack.EMPTY;
        String seedType = null;

        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (stack.isEmpty()) continue;

            if (stack.getItem() instanceof SeedStorageItem) {
                if (!storageStack.isEmpty()) return false; // more than one storage item
                storageStack = stack;
            } else {
                String id = Registries.ITEM.getId(stack.getItem()).toString();
                if (!SeedStorageItem.VALID_SEEDS.contains(id)) return false;
                if (seedType == null) {
                    seedType = id;
                } else if (!seedType.equals(id)) {
                    return false; // mixed seed types
                }
            }
        }

        if (storageStack.isEmpty() || seedType == null) return false;

        // If packet already has a seed type, it must match
        String existing = SeedStorageItem.getStoredSeed(storageStack);
        if (existing != null && !existing.isEmpty() && !existing.equals(seedType)) return false;

        // Packet must not be full
        int currentCount = SeedStorageItem.getSeedCount(storageStack);
        int capacity = ((SeedStorageItem) storageStack.getItem()).capacity;
        return currentCount < capacity;
    }

    @Override
    public ItemStack craft(RecipeInputInventory inventory, DynamicRegistryManager registryManager) {
        ItemStack storageStack = ItemStack.EMPTY;
        String seedType = null;
        int seedSlots = 0; // 1 seed consumed per slot

        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (stack.isEmpty()) continue;
            if (stack.getItem() instanceof SeedStorageItem) {
                storageStack = stack;
            } else {
                seedType = Registries.ITEM.getId(stack.getItem()).toString();
                seedSlots++;
            }
        }

        if (storageStack.isEmpty() || seedType == null) return ItemStack.EMPTY;

        ItemStack result = storageStack.copy();
        int capacity = ((SeedStorageItem) result.getItem()).capacity;
        int currentCount = SeedStorageItem.getSeedCount(result);
        int newCount = Math.min(currentCount + seedSlots, capacity);
        SeedStorageItem.setStoredSeed(result, seedType);
        SeedStorageItem.setSeedCount(result, newCount);
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
