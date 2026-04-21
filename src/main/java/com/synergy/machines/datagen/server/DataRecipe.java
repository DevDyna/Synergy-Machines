package com.synergy.machines.datagen.server;

import static com.synergy.machines.Main.MODULE_ID;

import java.util.concurrent.CompletableFuture;

import com.devdyna.cakesticklib.setup.registry.zLibrary;
import com.synergy.machines.init.builders.alloy_smelter.recipe.AlloySmelterRecipeBuilder;
import com.synergy.machines.init.builders.compressor.recipe.CompressorRecipeBuilder;
import com.synergy.machines.init.builders.furnace.recipe.ElectricFurnaceRecipeBuilder;
import com.synergy.machines.init.builders.macerator.recipe.MaceratorRecipeBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;

public class DataRecipe extends RecipeProvider {

        protected DataRecipe(Provider registries, RecipeOutput output) {
                super(registries, output);
        }

        @Override
        protected void buildRecipes() {

                MaceratorRecipeBuilder.of()
                                .input(Items.RAW_IRON)
                                .output(zLibrary.zItems.IRON_DUST, 2)
                                .secondary(zLibrary.zItems.IRON_DUST, 1, 0.25f)
                                .unlockedBy(MODULE_ID, has(Items.RAW_IRON))
                                .save(output);

                AlloySmelterRecipeBuilder.of()
                                .inputs(Items.NETHERITE_SCRAP, Items.GOLD_INGOT)
                                .output(Items.NETHERITE_INGOT)
                                .unlockedBy(MODULE_ID, has(Items.NETHERITE_SCRAP))
                                .save(output);

                // TODO fluid tags

                // CasterRecipeBuilder.of()
                // .fluid(Fluids.WATER)
                // .input(Items.COBBLESTONE)
                // .output(Items.MOSSY_COBBLESTONE)
                // .unlockedBy(MODULE_ID,has(Items.COBBLESTONE))
                // .save(output);

                CompressorRecipeBuilder.of()
                                .input(Items.NETHERITE_SCRAP)
                                .catalyst(Items.GOLD_INGOT)
                                .output(Items.NETHERITE_INGOT)
                                .unlockedBy(MODULE_ID, has(Items.NETHERITE_SCRAP))
                                .save(output);

                ElectricFurnaceRecipeBuilder.of()
                                .input(Items.RAW_IRON)
                                .output(Items.IRON_INGOT)
                                .unlockedBy(MODULE_ID, has(Items.RAW_IRON))
                                .save(output);

                // ExtractorRecipeBuilder.of()
                // .fluid(Fluids.LAVA,250)
                // .input(Items.MAGMA_BLOCK)
                // .secondary(Items.NETHERRACK,0.5f)
                // .unlockedBy(MODULE_ID,has(Items.MAGMA_BLOCK))
                // .save(output);

                // MelterRecipeBuilder.of()
                // .fluid(Fluids.LAVA,125)
                // .input(Items.COBBLESTONE)
                // .unlockedBy(MODULE_ID,has(Items.COBBLESTONE))
                // .save(output);

                // RockCrusherRecipeBuilder.of()
                // .fluid(Fluids.WATER,125)
                // .input(Items.STONE)
                // .addResult(Items.RAW_COPPER, 0.25f)
                // .addResult(Items.RAW_GOLD, 0.15f)
                // .addResult(Items.RAW_IRON, 0.28f)
                // .unlockedBy(MODULE_ID,has(Items.STONE))
                // .save(output);

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

}