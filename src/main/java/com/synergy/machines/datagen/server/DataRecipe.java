package com.synergy.machines.datagen.server;

import static com.synergy.machines.Main.MODULE_ID;

import java.util.concurrent.CompletableFuture;

import com.devdyna.cakesticklib.api.datagen.RecipeGenerators;
import com.devdyna.cakesticklib.setup.registry.LibItems;
import com.synergy.machines.init.builders.alloy_smelter.recipe.AlloySmelterRecipeBuilder;
import com.synergy.machines.init.builders.caster.recipe.CasterRecipeBuilder;
import com.synergy.machines.init.builders.compressor.recipe.CompressorRecipeBuilder;
import com.synergy.machines.init.builders.extractor.recipe.ExtractorRecipeBuilder;
import com.synergy.machines.init.builders.furnace.recipe.ElectricFurnaceRecipeBuilder;
import com.synergy.machines.init.builders.macerator.recipe.MaceratorRecipeBuilder;
import com.synergy.machines.init.builders.melter.recipe.MelterRecipeBuilder;
import com.synergy.machines.init.builders.rock_crusher.recipe.RockCrusherRecipeBuilder;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;

public class DataRecipe extends RecipeProvider implements RecipeGenerators {

        protected DataRecipe(Provider registries, RecipeOutput output) {
                super(registries, output);
        }

        @Override
        protected void buildRecipes() {

                MaceratorRecipeBuilder.of(registries)
                                .input(Items.RAW_IRON)
                                .output(LibItems.IRON_DUST, 2)
                                .output(LibItems.IRON_DUST, 1, 0.25f)
                                .unlockedBy(Items.RAW_IRON)
                                .save(output);

                AlloySmelterRecipeBuilder.of(registries)
                                .inputs(Items.NETHERITE_SCRAP, Items.GOLD_INGOT)
                                .output(Items.NETHERITE_INGOT)
                                .unlockedBy(Items.NETHERITE_SCRAP)
                                .save(output);

                CasterRecipeBuilder.of(registries)
                                .fluid(Fluids.WATER)
                                .input(Items.COBBLESTONE)
                                .output(Items.MOSSY_COBBLESTONE)
                                .unlockedBy(Items.COBBLESTONE)
                                .save(output);

                CompressorRecipeBuilder.of(registries)
                                .input(Items.NETHERITE_SCRAP)
                                .catalyst(Items.GOLD_INGOT)
                                .output(Items.NETHERITE_INGOT)
                                .unlockedBy(Items.NETHERITE_SCRAP)
                                .save(output);

                ElectricFurnaceRecipeBuilder.of(registries)
                                .input(Items.RAW_IRON)
                                .output(Items.IRON_INGOT)
                                .unlockedBy(Items.RAW_IRON)
                                .save(output);

                ExtractorRecipeBuilder.of(registries)
                                .input(Items.MAGMA_BLOCK)
                                .output(Fluids.LAVA, 250)
                                .output(Items.NETHERRACK, 0.5f)
                                .unlockedBy(Items.MAGMA_BLOCK)
                                .save(output);

                MelterRecipeBuilder.of(registries)
                                .input(Items.COBBLESTONE)
                                .output(Fluids.LAVA, 125)
                                .unlockedBy(Items.COBBLESTONE)
                                .save(output);

                RockCrusherRecipeBuilder.of(registries)
                                .fluid(Fluids.WATER, 125)
                                .input(Items.STONE)
                                .addResult(Items.COBBLESTONE, 2, 1f)
                                .addResult(Items.RAW_COPPER, 0.25f)
                                .addResult(Items.RAW_GOLD, 0.15f)
                                .addResult(Items.RAW_IRON, 0.28f)
                                .unlockedBy(Items.STONE)
                                .save(output);

        }

        public static final class RecipeRunner extends RecipeProvider.Runner {
                public RecipeRunner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
                        super(output, lookupProvider);
                }

                @Override
                protected RecipeProvider createRecipeProvider(
                                HolderLookup.Provider lookupProvider,
                                RecipeOutput output) {
                        return new DataRecipe(lookupProvider, output);
                }

                @Override
                public String getName() {
                        return "Synergy Machines";
                }
        }

        @Override
        public HolderGetter<Item> getItems() {
                return items;
        }

        @Override
        public String getModName() {
                return MODULE_ID;
        }

        @Override
        public Provider getProvider() {
                return registries;
        }

}