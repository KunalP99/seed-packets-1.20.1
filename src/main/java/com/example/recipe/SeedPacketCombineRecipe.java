package com.example.recipe;

import com.example.item.SeedPacketItem;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class SeedPacketCombineRecipe extends SpecialCraftingRecipe {

    public SeedPacketCombineRecipe(Identifier id, CraftingRecipeCategory category) {
        super(id, category);
    }

    @Override
    public boolean matches(CraftingInventory inventory, World world) {
        ItemStack first = ItemStack.EMPTY;
        ItemStack second = ItemStack.EMPTY;

        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (stack.isEmpty()) continue;
            if (!(stack.getItem() instanceof SeedPacketItem)) return false;

            if (first.isEmpty()) {
                first = stack;
            } else if (second.isEmpty()) {
                second = stack;
            } else {
                return false; // More than 2 non-empty slots
            }
        }

        return !first.isEmpty() && !second.isEmpty() && first.isOf(second.getItem());
    }

    @Override
    public ItemStack craft(CraftingInventory inventory, DynamicRegistryManager registryManager) {
        ItemStack first = ItemStack.EMPTY;
        ItemStack second = ItemStack.EMPTY;

        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (stack.isEmpty()) continue;
            if (first.isEmpty()) {
                first = stack;
            } else {
                second = stack;
                break;
            }
        }

        if (first.isEmpty() || second.isEmpty()) return ItemStack.EMPTY;

        int combined = Math.min(
                SeedPacketItem.getUses(first) + SeedPacketItem.getUses(second),
                SeedPacketItem.MAX_USES
        );

        ItemStack result = new ItemStack(first.getItem());
        SeedPacketItem.setUses(result, combined);
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
        return ModRecipes.COMBINE_SEED_PACKETS;
    }
}
