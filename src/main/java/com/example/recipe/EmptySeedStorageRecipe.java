package com.example.recipe;

import com.example.item.SeedStorageItem;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class EmptySeedStorageRecipe extends SpecialCraftingRecipe {

    public EmptySeedStorageRecipe(Identifier id, CraftingRecipeCategory category) {
        super(id, category);
    }

    @Override
    public boolean matches(RecipeInputInventory inventory, World world) {
        ItemStack storageStack = ItemStack.EMPTY;

        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (stack.isEmpty()) continue;
            if (!(stack.getItem() instanceof SeedStorageItem)) return false;
            if (!storageStack.isEmpty()) return false; // more than one item
            storageStack = stack;
        }

        return !storageStack.isEmpty() && !SeedStorageItem.isEmpty(storageStack);
    }

    @Override
    public ItemStack craft(RecipeInputInventory inventory, DynamicRegistryManager registryManager) {
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (stack.isEmpty() || !(stack.getItem() instanceof SeedStorageItem)) continue;

            String seedType = SeedStorageItem.getStoredSeed(stack);
            int count = SeedStorageItem.getSeedCount(stack);
            int extracted = Math.min(count, 64);

            Item seedItem = Registries.ITEM.get(Identifier.tryParse(seedType));
            return new ItemStack(seedItem, extracted);
        }
        return ItemStack.EMPTY;
    }

    // Returns the storage packet back to its slot, with remaining seeds (or empty if fully drained).
    @Override
    public DefaultedList<ItemStack> getRemainder(RecipeInputInventory inventory) {
        DefaultedList<ItemStack> remainders = DefaultedList.ofSize(inventory.size(), ItemStack.EMPTY);

        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (stack.isEmpty() || !(stack.getItem() instanceof SeedStorageItem)) continue;

            int count = SeedStorageItem.getSeedCount(stack);
            int extracted = Math.min(count, 64);
            int remaining = count - extracted;

            ItemStack remainder = new ItemStack(stack.getItem());
            if (remaining > 0) {
                SeedStorageItem.setStoredSeed(remainder, SeedStorageItem.getStoredSeed(stack));
                SeedStorageItem.setSeedCount(remainder, remaining);
            }
            // If remaining == 0, the remainder is a clean empty packet (no NBT)
            remainders.set(i, remainder);
        }

        return remainders;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 1;
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.EMPTY_SEED_STORAGE;
    }
}
