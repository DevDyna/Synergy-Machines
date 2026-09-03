package com.synergy.machines.datagen.server;

import static com.synergy.machines.Main.MODULE_ID;

import java.util.concurrent.CompletableFuture;

import com.devdyna.cakesticklib.api.datagen.RecipeGenerators;
import com.devdyna.cakesticklib.api.utils.x;
import com.devdyna.cakesticklib.setup.registry.LibItems;
import com.synergy.machines.api.FluidRegister;
import com.synergy.machines.init.builders.alloy_smelter.recipe.AlloySmelterRecipeBuilder;
import com.synergy.machines.init.builders.caster.recipe.CasterRecipeBuilder;
import com.synergy.machines.init.builders.compressor.recipe.CompressorRecipeBuilder;
import com.synergy.machines.init.builders.extractor.recipe.ExtractorRecipeBuilder;
import com.synergy.machines.init.builders.furnace.recipe.ElectricFurnaceRecipeBuilder;
import com.synergy.machines.init.builders.macerator.recipe.MaceratorRecipeBuilder;
import com.synergy.machines.init.builders.melter.recipe.MelterRecipeBuilder;
import com.synergy.machines.init.builders.rock_crusher.recipe.RockCrusherRecipeBuilder;
import com.synergy.machines.init.types.zFluids;
import com.synergy.machines.init.types.zTags;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.Tags;

public class DataRecipe extends RecipeProvider implements RecipeGenerators {

        // TODO API : move to api
        public class MoltenValues {
                public static final int NUGGET = 16;
                public static final int INGOT = 144;
                public static final int BLOCK = 144 * 9;
        }

        protected DataRecipe(Provider registries, RecipeOutput output) {
                super(registries, output);
        }

        @Override
        protected void buildRecipes() {

                MaceratorRecipeBuilder.of(registries)
                                .input(Tags.Items.SANDSTONE_UNCOLORED_BLOCKS)
                                .output(x.itemTemplate(Items.SAND), 2) // TODO
                                .output(LibItems.SILICON_SHARD, 1, 0.5f)
                                .unlockedBy(Tags.Items.SANDSTONE_UNCOLORED_BLOCKS, items)
                                .save(output);

                AlloySmelterRecipeBuilder.of(registries)
                                .inputs(Items.NETHERITE_SCRAP, Items.GOLD_INGOT)
                                .output(Items.NETHERITE_INGOT)
                                .unlockedBy(Items.NETHERITE_SCRAP)
                                .save(output);

                // CasterRecipeBuilder.of(registries)
                // .fluid(Fluids.WATER)
                // .input(Items.COBBLESTONE)
                // .output(Items.MOSSY_COBBLESTONE)
                // .unlockedBy(Items.COBBLESTONE)
                // .save(output);

                // CompressorRecipeBuilder.of(registries)
                // .input(Items.NETHERITE_SCRAP)
                // .catalyst(Items.GOLD_INGOT)
                // .output(Items.NETHERITE_INGOT)
                // .unlockedBy(Items.NETHERITE_SCRAP)
                // .save(output);

                // ElectricFurnaceRecipeBuilder.of(registries)
                // .input(Items.RAW_IRON)
                // .output(Items.IRON_INGOT)
                // .unlockedBy(Items.RAW_IRON)
                // .save(output);

                ExtractorRecipeBuilder.of(registries)
                                .input(Items.MAGMA_BLOCK)
                                .output(Fluids.LAVA, 250)
                                .output(Items.NETHERRACK, 0.5f)
                                .unlockedBy(Items.MAGMA_BLOCK)
                                .save(output);

                // MelterRecipeBuilder.of(registries)
                // .input(Items.COBBLESTONE)
                // .output(Fluids.LAVA, 125)
                // .unlockedBy(Items.COBBLESTONE)
                // .save(output);

                RockCrusherRecipeBuilder.of(registries)
                                .fluid(Fluids.WATER, 125)
                                .input(Items.STONE)
                                .addResult(Items.COBBLESTONE, 2, 1f)
                                .addResult(Items.RAW_COPPER, 0.25f)
                                .addResult(Items.RAW_GOLD, 0.15f)
                                .addResult(Items.RAW_IRON, 0.28f)
                                .unlockedBy(Items.STONE)
                                .save(output);

                oreProcessing(
                                Items.RAW_IRON,
                                LibItems.IRON_DUST.get(),
                                Items.IRON_INGOT,
                                Items.IRON_NUGGET,
                                Items.IRON_BLOCK,
                                LibItems.IRON_GEAR.get(),
                                LibItems.IRON_PLATE.get(),
                                LibItems.IRON_FOIL.get(),
                                LibItems.IRON_COIL.get(),
                                zFluids.MOLTEN_IRON);

                oreProcessing(
                                Items.RAW_COPPER,
                                LibItems.COPPER_DUST.get(),
                                Items.COPPER_INGOT,
                                Items.COPPER_NUGGET,
                                Items.COPPER_BLOCK,
                                LibItems.COPPER_GEAR.get(),
                                LibItems.COPPER_PLATE.get(),
                                LibItems.COPPER_FOIL.get(),
                                LibItems.COPPER_COIL.get(),
                                zFluids.MOLTEN_COPPER);
                oreProcessing(
                                Items.RAW_GOLD,
                                LibItems.GOLD_DUST.get(),
                                Items.GOLD_INGOT,
                                Items.GOLD_NUGGET,
                                Items.GOLD_BLOCK,
                                LibItems.GOLD_GEAR.get(),
                                LibItems.GOLD_PLATE.get(),
                                LibItems.GOLD_FOIL.get(),
                                LibItems.GOLD_COIL.get(),
                                zFluids.MOLTEN_GOLD);

                melterRecycle(
                                zFluids.MOLTEN_COPPER,
                                zTags.Items.RECYCLE_COPPER_1,
                                zTags.Items.RECYCLE_COPPER_2,
                                zTags.Items.RECYCLE_COPPER_3,
                                zTags.Items.RECYCLE_COPPER_4,
                                zTags.Items.RECYCLE_COPPER_5,
                                zTags.Items.RECYCLE_COPPER_6,
                                zTags.Items.RECYCLE_COPPER_7,
                                zTags.Items.RECYCLE_COPPER_8,
                                zTags.Items.RECYCLE_COPPER_9);
                melterRecycle(
                                zFluids.MOLTEN_GOLD,
                                zTags.Items.RECYCLE_GOLD_1,
                                zTags.Items.RECYCLE_GOLD_2,
                                zTags.Items.RECYCLE_GOLD_3,
                                zTags.Items.RECYCLE_GOLD_4,
                                zTags.Items.RECYCLE_GOLD_5,
                                zTags.Items.RECYCLE_GOLD_6,
                                zTags.Items.RECYCLE_GOLD_7,
                                zTags.Items.RECYCLE_GOLD_8,
                                zTags.Items.RECYCLE_GOLD_9);
                melterRecycle(
                                zFluids.MOLTEN_IRON,
                                zTags.Items.RECYCLE_IRON_1,
                                zTags.Items.RECYCLE_IRON_2,
                                zTags.Items.RECYCLE_IRON_3,
                                zTags.Items.RECYCLE_IRON_4,
                                zTags.Items.RECYCLE_IRON_5,
                                zTags.Items.RECYCLE_IRON_6,
                                zTags.Items.RECYCLE_IRON_7,
                                zTags.Items.RECYCLE_IRON_8,
                                zTags.Items.RECYCLE_IRON_9);

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

        private void oreProcessing(ItemLike raw, ItemLike dust, ItemLike ingot, ItemLike nugget, ItemLike block,
                        ItemLike gear,
                        ItemLike plate, ItemLike foil, ItemLike coil, FluidRegister molten) {

                MaceratorRecipeBuilder.of(registries)
                                .input(raw)
                                .output(x.itemTemplate(dust), 2) //TODO
                                .output(dust.asItem(), 1, 0.05f)
                                .unlockedBy(raw)
                                .save(output, "_from_raw");

                // TODO API : input(ItemLike input, int c)
                // TODO API : output(ItemLike output,int c)
                // TODO API : nugget mold & coil mold

                CompressorRecipeBuilder.of(registries)
                                .input(ingot.asItem(), 9)
                                .catalyst(LibItems.MOLD_BLOCK.get())// TODO API : BUG cannot be cast to class
                                .output(block)
                                .unlockedBy(ingot)
                                .save(output, "_from_ingot");

                CompressorRecipeBuilder.of(registries)
                                .input(plate)
                                .catalyst(LibItems.MOLD_FOIL.get())
                                .output(foil)
                                .unlockedBy(plate)
                                .save(output, "_from_plate");

                CompressorRecipeBuilder.of(registries)
                                .input(ingot.asItem(), 4)
                                .catalyst(LibItems.MOLD_GEAR.get())
                                .output(gear)
                                .unlockedBy(ingot)
                                .save(output, "_from_ingot");

                CompressorRecipeBuilder.of(registries)
                                .input(ingot)
                                .catalyst(LibItems.MOLD_PLATE.get())
                                .output(plate)
                                .unlockedBy(ingot)
                                .save(output, "_from_ingot");

                CompressorRecipeBuilder.of(registries)
                                .input(nugget.asItem(), 9)
                                .catalyst(LibItems.MOLD_INGOT.get())
                                .output(ingot)
                                .unlockedBy(nugget)
                                .save(output, "_from_nugget");

                ElectricFurnaceRecipeBuilder.of(registries)
                                .input(dust)
                                .output(ingot)
                                .unlockedBy(dust)
                                .save(output);

                MelterRecipeBuilder.of(registries)
                                .input(raw)
                                .output(molten.getFluid(), MoltenValues.INGOT * 3)
                                .unlockedBy(raw)
                                .save(output, "_from_raw");

                CompressorRecipeBuilder.of(registries)
                                .input(ingot.asItem())
                                .catalyst(LibItems.STEEL_NUGGET.get())// TODO API : DEPRECATED
                                .output(nugget.asItem(), 9)
                                .unlockedBy(ingot)
                                .save(output, "_from_ingot");

                CasterRecipeBuilder.of(registries)
                                .fluid(molten.getFluid(), MoltenValues.BLOCK)
                                .input(LibItems.MOLD_BLOCK)
                                .output(block)
                                .unlockedBy(LibItems.MOLD_BLOCK.get())
                                .save(output);

                CasterRecipeBuilder.of(registries)
                                .fluid(molten.getFluid(), MoltenValues.NUGGET)
                                .input(LibItems.STEEL_NUGGET)// TODO API : DEPRECATED
                                .output(nugget)
                                .unlockedBy(LibItems.STEEL_NUGGET.get())
                                .save(output);

                CasterRecipeBuilder.of(registries)
                                .fluid(molten.getFluid(), MoltenValues.INGOT)
                                .input(LibItems.MOLD_FOIL)
                                .output(foil)
                                .unlockedBy(LibItems.MOLD_FOIL.get())
                                .save(output);

                CasterRecipeBuilder.of(registries)
                                .fluid(molten.getFluid(), MoltenValues.INGOT * 4)
                                .input(LibItems.MOLD_GEAR)
                                .output(gear)
                                .unlockedBy(LibItems.MOLD_GEAR.get())
                                .save(output);

                CasterRecipeBuilder.of(registries)
                                .fluid(molten.getFluid(), MoltenValues.INGOT)
                                .input(LibItems.MOLD_PLATE)
                                .output(plate)
                                .unlockedBy(LibItems.MOLD_PLATE.get())
                                .save(output);

                // TODO API : unlockedBy(DeferredHolder<Item, Item>)

        }

        private void melterRecycle(FluidRegister fluid, TagKey<Item> item1, TagKey<Item> item2, TagKey<Item> item3,
                        TagKey<Item> item4, TagKey<Item> item5, TagKey<Item> item6, TagKey<Item> item7,
                        TagKey<Item> item8, TagKey<Item> item9) {

                MelterRecipeBuilder.of(registries)
                                .input(item1)
                                .output(fluid.getFluid(), MoltenValues.INGOT)
                                .unlockedBy(item1, items)
                                .save(output);

                MelterRecipeBuilder.of(registries)
                                .input(item2)
                                .output(fluid.getFluid(), MoltenValues.INGOT * 2)
                                .unlockedBy(item2, items)
                                .save(output);

                MelterRecipeBuilder.of(registries)
                                .input(item3)
                                .output(fluid.getFluid(), MoltenValues.INGOT * 3)
                                .unlockedBy(item3, items)
                                .save(output);

                MelterRecipeBuilder.of(registries)
                                .input(item4)
                                .output(fluid.getFluid(), MoltenValues.INGOT * 4)
                                .unlockedBy(item4, items)
                                .save(output);

                MelterRecipeBuilder.of(registries)
                                .input(item5)
                                .output(fluid.getFluid(), MoltenValues.INGOT * 5)
                                .unlockedBy(item5, items)
                                .save(output);

                MelterRecipeBuilder.of(registries)
                                .input(item6)
                                .output(fluid.getFluid(), MoltenValues.INGOT * 6)
                                .unlockedBy(item6, items)
                                .save(output);

                MelterRecipeBuilder.of(registries)
                                .input(item7)
                                .output(fluid.getFluid(), MoltenValues.INGOT * 7)
                                .unlockedBy(item7, items)
                                .save(output);

                MelterRecipeBuilder.of(registries)
                                .input(item8)
                                .output(fluid.getFluid(), MoltenValues.INGOT * 8)
                                .unlockedBy(item8, items)
                                .save(output);

                MelterRecipeBuilder.of(registries)
                                .input(item9)
                                .output(fluid.getFluid(), MoltenValues.INGOT * 9)
                                .unlockedBy(item9, items)
                                .save(output);

        }

}