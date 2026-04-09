package com.example.recipe;

import com.example.SeedPackets;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {

    public static final RecipeSerializer<SeedPacketCombineRecipe> COMBINE_SEED_PACKETS =
            new SpecialRecipeSerializer<>(SeedPacketCombineRecipe::new);

    public static final RecipeSerializer<FillSeedStorageRecipe> FILL_SEED_STORAGE =
            new SpecialRecipeSerializer<>(FillSeedStorageRecipe::new);

    public static final RecipeSerializer<EmptySeedStorageRecipe> EMPTY_SEED_STORAGE =
            new SpecialRecipeSerializer<>(EmptySeedStorageRecipe::new);

    public static void register() {
        Registry.register(Registries.RECIPE_SERIALIZER,
                new Identifier(SeedPackets.MOD_ID, "combine_seed_packets"),
                COMBINE_SEED_PACKETS);
        Registry.register(Registries.RECIPE_SERIALIZER,
                new Identifier(SeedPackets.MOD_ID, "fill_seed_storage"),
                FILL_SEED_STORAGE);
        Registry.register(Registries.RECIPE_SERIALIZER,
                new Identifier(SeedPackets.MOD_ID, "empty_seed_storage"),
                EMPTY_SEED_STORAGE);
    }
}
