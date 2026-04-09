package com.example.recipe;

import com.example.item.SeedPacketItem;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import java.util.ArrayList;
import java.util.List;

public class SeedPacketCombineRecipe extends SpecialCraftingRecipe {

    public SeedPacketCombineRecipe(Identifier id, CraftingRecipeCategory category) {
        super(id, category);
    }

    @Override
    public boolean matches(RecipeInputInventory inventory, World world) {
        List<ItemStack> found = new ArrayList<>();
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (stack.isEmpty()) continue;
            if (!(stack.getItem() instanceof SeedPacketItem)) return false;
            found.add(stack);
        }
        if (found.size() < 2) return false;
        Item type = found.get(0).getItem();
        for (ItemStack stack : found) {
            if (!stack.isOf(type)) return false;
        }
        return true;
    }

    @Override
    public ItemStack craft(RecipeInputInventory inventory, DynamicRegistryManager registryManager) {
        ItemStack first = ItemStack.EMPTY;
        int totalUses = 0;
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (stack.isEmpty()) continue;
            if (first.isEmpty()) first = stack;
            totalUses += SeedPacketItem.getUses(stack);
        }
        if (first.isEmpty()) return ItemStack.EMPTY;
        ItemStack result = new ItemStack(first.getItem());
        SeedPacketItem.setUses(result, Math.min(totalUses, SeedPacketItem.MAX_USES));
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
