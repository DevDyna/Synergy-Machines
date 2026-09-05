package com.synergy.machines.datagen.server;

import static com.synergy.machines.Main.MODULE_ID;

import java.util.concurrent.CompletableFuture;
import com.devdyna.cakesticklib.api.datagen.RecipeGenerators;
import com.devdyna.cakesticklib.api.utils.x;
import com.devdyna.cakesticklib.setup.registry.*;
import com.synergy.machines.init.builders.alloy_smelter.recipe.AlloySmelterRecipeBuilder;
import com.synergy.machines.init.builders.caster.recipe.CasterRecipeBuilder;
import com.synergy.machines.init.builders.compressor.recipe.CompressorRecipeBuilder;
import com.synergy.machines.init.builders.extractor.recipe.ExtractorRecipeBuilder;
import com.synergy.machines.init.builders.macerator.recipe.MaceratorRecipeBuilder;
import com.synergy.machines.init.builders.melter.recipe.MelterRecipeBuilder;
import com.synergy.machines.init.builders.rock_crusher.recipe.RockCrusherRecipeBuilder;
import com.synergy.machines.init.types.*;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.Tags;

public class DataRecipe extends RecipeProvider implements RecipeGenerators {

        // TODO API : move to api
        public class MoltenValues {
                public static final int NUGGET = 10;
                public static final int INGOT = 90;
                public static final int BLOCK = INGOT * 9;

                public static final int BARS = 9;
                public static final int INGREDIENT_BARS = BARS * 16;
        }

        protected DataRecipe(HolderLookup.Provider registries, RecipeOutput output) {
                super(registries, output);
        }

        @Override
        protected void buildRecipes() {

                MaceratorRecipeBuilder.of(registries)
                                .input(Tags.Items.SANDSTONE_UNCOLORED_BLOCKS)
                                .output(Items.SAND, 2)
                                .outputChance(LibItems.SILICON_SHARD, 1, 0.5f)
                                .unlockedBy(Tags.Items.SANDSTONE_UNCOLORED_BLOCKS, items)
                                .save(output);

                shaped(RecipeCategory.REDSTONE, zMachines.CASTING_FACTORY.block().get())
                                .pattern("CRC")
                                .pattern("GMG")
                                .pattern(" B ")
                                .define('R', LibItems.RESISTOR.get())
                                .define('G', LibItems.STEEL_GEAR.get())
                                .define('C', LibItems.CHIP.get())
                                .define('B', LibItems.BLUE_BATTERY.get())
                                .define('M', zBlocks.MACHINE_FRAME.get())
                                .unlockedBy(getHasName(zBlocks.MACHINE_FRAME.get()), has(zBlocks.MACHINE_FRAME.get()))
                                .save(output);

                shaped(RecipeCategory.REDSTONE, zMachines.ROCK_CRUSHER.block().get())
                                .pattern("RLR")
                                .pattern("MFM")
                                .pattern(" B ")
                                .define('M', zMachines.MACERATOR.item().get())
                                .define('F', zBlocks.MACHINE_FRAME.get())
                                .define('L', LibItems.ELECTRON_TUBE.get())
                                .define('R', LibItems.RESISTOR.get())
                                .define('B', LibItems.BLUE_BATTERY.get())
                                .unlockedBy(getHasName(zBlocks.MACHINE_FRAME.get()),
                                                has(zMachines.MACERATOR.item().get()))
                                .save(output);

                shaped(RecipeCategory.REDSTONE, zMachines.EXTRACTOR.block().get())
                                .pattern("RFR")
                                .pattern("EME")
                                .pattern(" B ")
                                .define('E', LibItems.ELECTRON_TUBE.get())
                                .define('R', LibItems.RESISTOR.get())
                                .define('F', LibItems.GOLD_FOIL.get())
                                .define('B', LibItems.BLUE_BATTERY.get())
                                .define('M', zBlocks.MACHINE_FRAME.get())
                                .unlockedBy(getHasName(zBlocks.MACHINE_FRAME.get()), has(zBlocks.MACHINE_FRAME.get()))
                                .save(output);

                shaped(RecipeCategory.REDSTONE, zMachines.ELECTRIC_MELTER.block().get())
                                .pattern("CRC")
                                .pattern("DMD")
                                .pattern(" B ")
                                .define('C', LibItems.COPPER_COIL.get())
                                .define('R', LibItems.CHIP.get())
                                .define('D', LibItems.CONDENSER.get())
                                .define('B', LibItems.BLUE_BATTERY.get())
                                .define('M', zBlocks.MACHINE_FRAME.get())
                                .unlockedBy(getHasName(zBlocks.MACHINE_FRAME.get()), has(zBlocks.MACHINE_FRAME.get()))
                                .save(output);

                shaped(RecipeCategory.REDSTONE, zMachines.MACERATOR.block().get())
                                .pattern("FFF")
                                .pattern("RMR")
                                .pattern(" B ")
                                .define('F', Items.FLINT)
                                .define('R', LibItems.RESISTOR.get())
                                .define('B', LibItems.BLUE_BATTERY.get())
                                .define('M', zBlocks.MACHINE_FRAME.get())
                                .unlockedBy(getHasName(zBlocks.MACHINE_FRAME.get()), has(zBlocks.MACHINE_FRAME.get()))
                                .save(output);

                shaped(RecipeCategory.REDSTONE, zMachines.COMPRESSOR.block().get())
                                .pattern("CPC")
                                .pattern("RMR")
                                .pattern(" B ")
                                .define('C', LibItems.CHIP.get())
                                .define('R', LibItems.RESISTOR.get())
                                .define('P', Items.PISTON)
                                .define('B', LibItems.BLUE_BATTERY.get())
                                .define('M', zBlocks.MACHINE_FRAME.get())
                                .unlockedBy(getHasName(zBlocks.MACHINE_FRAME.get()), has(zBlocks.MACHINE_FRAME.get()))
                                .save(output);

                shaped(RecipeCategory.REDSTONE, zMachines.ALLOY_SMELTER.block().get())
                                .pattern("FCF")
                                .pattern("RMR")
                                .pattern(" B ")
                                .define('F', zMachines.ELECTRIC_FURNACE.item().get())
                                .define('R', LibItems.RESISTOR.get())
                                .define('C', LibItems.CONDENSER.get())
                                .define('B', LibItems.BLUE_BATTERY.get())
                                .define('M', zBlocks.MACHINE_FRAME.get())
                                .unlockedBy(getHasName(zBlocks.MACHINE_FRAME.get()), has(zBlocks.MACHINE_FRAME.get()))
                                .save(output);

                shaped(RecipeCategory.REDSTONE, zMachines.ELECTRIC_FURNACE.block().get())
                                .pattern("CFC")
                                .pattern("RMR")
                                .pattern(" B ")
                                .define('F', Items.FURNACE)
                                .define('R', LibItems.RESISTOR.get())
                                .define('C', LibItems.CHIP.get())
                                .define('B', LibItems.BLUE_BATTERY.get())
                                .define('M', zBlocks.MACHINE_FRAME.get())
                                .unlockedBy(getHasName(zBlocks.MACHINE_FRAME.get()), has(zBlocks.MACHINE_FRAME.get()))
                                .save(output);

                shaped(RecipeCategory.MISC, zBlocks.MACHINE_FRAME.get())
                                .pattern(" C ")
                                .pattern("FRF")
                                .pattern(" C ")
                                .define('C', LibItems.CHIP.get())
                                .define('F', Items.IRON_NUGGET)
                                .define('R', LibTags.Items.LEGACY_STONES)
                                .unlockedBy(getHasName(LibTags.Items.LEGACY_STONES), has(LibTags.Items.LEGACY_STONES))
                                .save(output);

                shaped(RecipeCategory.MISC, zBlocks.MACHINE_FRAME.get(), 4)
                                .pattern(" S ")
                                .pattern("PWP")
                                .pattern(" S ")
                                .define('S', LibItems.SILICON_GEM.get())
                                .define('W', LibBlocks.WROUGHT_IRON_BLOCK.get())
                                .define('P', LibItems.PLASTIC.get())
                                .unlockedBy(getHasName(LibItems.PLASTIC.get()), has(LibItems.PLASTIC.get()))
                                .save(output, asRecipeID(zBlocks.MACHINE_FRAME.get(), "_advanced"));

                shaped(RecipeCategory.MISC, zBlocks.SOLAR_PANEL.get(), 3)
                                .pattern("LLL")
                                .pattern("WRW")
                                .define('R', Items.REDSTONE)
                                .define('W', LibItems.WROUGHT_IRON_INGOT.get())
                                .define('L', Items.LAPIS_LAZULI)
                                .unlockedBy(getHasName(LibItems.WROUGHT_IRON_INGOT.get()),
                                                has(LibItems.WROUGHT_IRON_INGOT.get()))
                                .save(output);

                shaped(RecipeCategory.MISC, zBlocks.LUNAR_PANEL.get(), 3)
                                .pattern("AAA")
                                .pattern("WRW")
                                .define('R', Items.REDSTONE)
                                .define('W', LibItems.WROUGHT_IRON_INGOT.get())
                                .define('A', Items.AMETHYST_SHARD)
                                .unlockedBy(getHasName(LibItems.WROUGHT_IRON_INGOT.get()),
                                                has(LibItems.WROUGHT_IRON_INGOT.get()))
                                .save(output);

                MaceratorRecipeBuilder.of(registries)
                                .input(ItemTags.LOGS)
                                .output(LibItems.SAWDUST, 4)
                                .outputChance(LibItems.SAWDUST, 2, 0.75f)
                                .unlockedBy(ItemTags.LOGS, items)
                                .save(output, "_from_logs");

                MaceratorRecipeBuilder.of(registries)
                                .input(ItemTags.PLANKS)
                                .output(LibItems.SAWDUST, 1)
                                .outputChance(LibItems.SAWDUST, 1, 0.35f)
                                .unlockedBy(ItemTags.PLANKS, items)
                                .save(output, "_from_planks");

                MaceratorRecipeBuilder.of(registries)
                                .input(Items.STICK)
                                .outputChance(LibItems.SAWDUST, 1, 0.25f)
                                .unlockedBy(ItemTags.PLANKS, items)
                                .save(output, "_from_sticks");

                MaceratorRecipeBuilder.of(registries)
                                .input(ItemTags.COALS)
                                .output(LibItems.CARBON_DUST, 2)
                                .unlockedBy(ItemTags.COALS, items)
                                .save(output);

                CompressorRecipeBuilder.of(registries)
                                .input(LibItems.CARBON_DUST)
                                .catalyst(LibItems.CARBON_DUST.get())
                                .consumeCatalyst()
                                .output(LibItems.CARBON_FIBER)
                                .unlockedBy(LibItems.CARBON_DUST.get())
                                .save(output);

                CompressorRecipeBuilder.of(registries)
                                .input(LibItems.CARBON_FIBER)
                                .output(LibItems.CARBON_PLATE)
                                .unlockedBy(LibItems.CARBON_FIBER.get())
                                .save(output);

                ExtractorRecipeBuilder.of(registries)
                                .input(Items.NETHERRACK)
                                .output(Fluids.LAVA, 150)
                                .outputChance(LibItems.SULFUR_DUST, 0.15f)
                                .unlockedBy(Items.NETHERRACK)
                                .save(output, "_from_netherrack");

                ExtractorRecipeBuilder.of(registries)
                                .input(Items.MAGMA_BLOCK)
                                .output(Fluids.LAVA, 250)
                                .outputChance(LibItems.SULFUR_DUST, 0.95f)
                                .unlockedBy(Items.MAGMA_BLOCK)
                                .save(output, "_from_magma_block");

                ExtractorRecipeBuilder.of(registries)
                                .input(Items.MAGMA_CREAM)
                                .output(Fluids.LAVA, 50)
                                .outputChance(LibItems.SULFUR_DUST, 0.25f)
                                .unlockedBy(Items.MAGMA_CREAM)
                                .save(output, "_from_magma_cream");

                ExtractorRecipeBuilder.of(registries)
                                .input(Tags.Items.SLIME_BALLS)
                                .output(LibFluids.PLASTIC.getFluid(), 25)
                                .unlockedBy(Tags.Items.SLIME_BALLS, items)
                                .save(output);

                AlloySmelterRecipeBuilder.of(registries)
                                .inputs(Items.NETHERITE_SCRAP, 2, Items.GOLD_INGOT, 2)
                                .output(Items.NETHERITE_INGOT)
                                .unlockedBy(Items.NETHERITE_SCRAP)
                                .save(output);

                AlloySmelterRecipeBuilder.of(registries)
                                .inputs(Items.IRON_INGOT, 2, LibTags.Items.CARBON_DUST, 1)
                                .output(LibItems.WROUGHT_IRON_INGOT, 2)
                                .unlockedBy(Items.IRON_INGOT)
                                .save(output);

                CompressorRecipeBuilder.of(registries)
                                .input(ItemTags.PLANKS, 2)
                                .catalyst(LibItems.MOLD_GEAR.get())
                                .output(LibItems.WOODEN_GEAR)
                                .unlockedBy(ItemTags.PLANKS, items)
                                .save(output);

                CompressorRecipeBuilder.of(registries)
                                .input(ItemTags.PLANKS)
                                .catalyst(LibItems.MOLD_ROD.get())
                                .output(Items.STICK, 8)
                                .unlockedBy(ItemTags.PLANKS, items)
                                .save(output);

                MelterRecipeBuilder.of(registries)
                                .input(Tags.Items.GLASS_BLOCKS_CHEAP)
                                .output(LibFluids.LIQUID_GLASS.getFluid(), MoltenValues.INGREDIENT_BARS)
                                .unlockedBy(Tags.Items.GLASS_BLOCKS_CHEAP, items)
                                .save(output, "_from_glass_blocks");

                MelterRecipeBuilder.of(registries)
                                .input(Tags.Items.GLASS_PANES)
                                .output(LibFluids.LIQUID_GLASS.getFluid(), MoltenValues.BARS)
                                .unlockedBy(Tags.Items.GLASS_PANES, items)
                                .save(output, "_from_glass_panes");

                CasterRecipeBuilder.of(registries)
                                .fluid(LibFluids.LIQUID_GLASS.getFluid(), MoltenValues.BARS)
                                .input(LibItems.MOLD_FOIL)
                                .output(Items.GLASS_PANE)
                                .unlockedBy(LibItems.MOLD_FOIL.get())
                                .save(output);

                CasterRecipeBuilder.of(registries)
                                .fluid(LibFluids.PLASTIC.getFluid(), 25)
                                .input(LibItems.MOLD_FOIL)
                                .output(LibItems.PLASTIC, 2)
                                .unlockedBy(LibItems.MOLD_FOIL.get())
                                .save(output);

                CasterRecipeBuilder.of(registries)
                                .fluid(Fluids.LAVA, 250)
                                .input(Items.NETHERRACK)
                                .output(Items.MAGMA_BLOCK)
                                .unlockedBy(Items.NETHERRACK)
                                .save(output);

                CasterRecipeBuilder.of(registries)
                                .fluid(Fluids.LAVA, 250)
                                .input(LibItems.MOLD_BLOCK)
                                .output(Items.OBSIDIAN)
                                .unlockedBy(LibItems.MOLD_BLOCK)
                                .save(output);

                CasterRecipeBuilder.of(registries)
                                .fluid(LibFluids.LIQUID_GLASS.getFluid(), MoltenValues.INGREDIENT_BARS)
                                .input(LibItems.MOLD_BLOCK)
                                .output(Items.GLASS)
                                .unlockedBy(LibItems.MOLD_BLOCK.get())
                                .save(output);

                RockCrusherRecipeBuilder.of(registries)
                                .fluid(LibFluids.SULFURIC_ACID.getFluid(), 50)
                                .input(LibTags.Items.LEGACY_STONES)
                                .addResult(LibItems.STONE_PEBBLE, 2, 0.9f)
                                .addResult(LibItems.STONE_PEBBLE, 1, 0.75f)
                                .addResult(LibItems.CARBON_DUST, 0.45f)
                                .addResult(LibItems.COPPER_DUST, 0.30f)
                                .addResult(LibItems.LAPIS_DUST, 0.15f)
                                .addResult(LibItems.IRON_DUST, 0.10f)
                                .addResult(LibItems.GOLD_DUST, 0.02f)
                                .addResult(LibItems.DIAMOND_DUST, 0.01f)
                                .unlockedBy(LibTags.Items.LEGACY_STONES, items)
                                .save(output);

                // TODO API : TAG BUG

                RockCrusherRecipeBuilder.of(registries)
                                .fluid(LibFluids.SULFURIC_ACID.getFluid(), 125)
                                .input(LibTags.Items.MODERN_STONES)
                                .addResult(LibItems.STONE_PEBBLE, 3, 0.75f)
                                .addResult(LibItems.STONE_PEBBLE, 1, 0.5f)
                                .addResult(LibItems.LAPIS_DUST, 0.25f)
                                .addResult(LibItems.IRON_DUST, 0.17f)
                                .addResult(LibItems.COPPER_DUST, 0.10f)
                                .addResult(LibItems.GOLD_DUST, 0.08f)
                                .addResult(LibItems.AMETHYST_DUST, 0.05f)
                                .addResult(LibItems.DIAMOND_DUST, 0.05f)
                                .unlockedBy(LibTags.Items.MODERN_STONES, items)
                                .save(output);

                // TODO IMP : diorite quartz dust and other specific stones?

                RockCrusherRecipeBuilder.of(registries)
                                .fluid(LibFluids.SULFURIC_ACID.getFluid(), 125)
                                .input(Items.BLACKSTONE)
                                .addResult(LibItems.NETHERRACK_PEBBLE, 1, 0.7f)
                                .addResult(Items.GOLD_NUGGET, 0.30f)
                                .addResult(LibItems.IRON_DUST, 0.10f)
                                .addResult(Items.NETHERITE_SCRAP, 0.02f)
                                .unlockedBy(Items.BLACKSTONE)
                                .save(output);

                MelterRecipeBuilder.of(registries)
                                .input(LibItems.SULFUR_DUST)
                                .output(LibFluids.SULFURIC_ACID.getFluid(), 25)
                                .unlockedBy(LibItems.SULFUR_DUST)
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
                                LibFluids.MOLTEN_IRON.getFluid());

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
                                LibFluids.MOLTEN_COPPER.getFluid());
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
                                LibFluids.MOLTEN_GOLD.getFluid());

                melterRecycle(
                                LibFluids.MOLTEN_COPPER.getFluid(),
                                LibTags.Items.RECYCLE_COPPER_1,
                                LibTags.Items.RECYCLE_COPPER_2,
                                LibTags.Items.RECYCLE_COPPER_3,
                                LibTags.Items.RECYCLE_COPPER_4,
                                LibTags.Items.RECYCLE_COPPER_5,
                                LibTags.Items.RECYCLE_COPPER_6,
                                LibTags.Items.RECYCLE_COPPER_7,
                                LibTags.Items.RECYCLE_COPPER_8,
                                LibTags.Items.RECYCLE_COPPER_9);
                melterRecycle(
                                LibFluids.MOLTEN_GOLD.getFluid(),
                                LibTags.Items.RECYCLE_GOLD_1,
                                LibTags.Items.RECYCLE_GOLD_2,
                                LibTags.Items.RECYCLE_GOLD_3,
                                LibTags.Items.RECYCLE_GOLD_4,
                                LibTags.Items.RECYCLE_GOLD_5,
                                LibTags.Items.RECYCLE_GOLD_6,
                                LibTags.Items.RECYCLE_GOLD_7,
                                LibTags.Items.RECYCLE_GOLD_8,
                                LibTags.Items.RECYCLE_GOLD_9);
                melterRecycle(
                                LibFluids.MOLTEN_IRON.getFluid(),
                                LibTags.Items.RECYCLE_IRON_1,
                                LibTags.Items.RECYCLE_IRON_2,
                                LibTags.Items.RECYCLE_IRON_3,
                                LibTags.Items.RECYCLE_IRON_4,
                                LibTags.Items.RECYCLE_IRON_5,
                                LibTags.Items.RECYCLE_IRON_6,
                                LibTags.Items.RECYCLE_IRON_7,
                                LibTags.Items.RECYCLE_IRON_8,
                                LibTags.Items.RECYCLE_IRON_9);

                oreProcessing(
                                LibItems.STEEL_INGOT.get(),
                                LibItems.STEEL_NUGGET.get(),
                                LibBlocks.STEEL_BLOCK.get(),
                                LibItems.STEEL_PLATE.get(),
                                LibItems.STEEL_GEAR.get(),
                                LibFluids.MOLTEN_STEEL.getFluid());

                oreProcessing(
                                LibItems.WROUGHT_IRON_INGOT.get(),
                                LibItems.WROUGHT_IRON_NUGGET.get(),
                                LibBlocks.WROUGHT_IRON_BLOCK.get(),
                                LibItems.WROUGHT_IRON_PLATE.get(),
                                null,
                                null);

                oreProcessing(
                                LibItems.ADVANCED_ALLOY_INGOT.get(),
                                LibItems.ADVANCED_ALLOY_NUGGET.get(),
                                LibBlocks.ADVANCED_ALLOY_BLOCK.get(),
                                LibItems.ADVANCED_ALLOY_PLATE.get(),
                                null,
                                null);

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
        public HolderLookup.Provider getProvider() {
                return registries;
        }

        private void oreProcessing(ItemLike raw, ItemLike dust, ItemLike ingot, ItemLike nugget, ItemLike block,
                        ItemLike gear,
                        ItemLike plate, ItemLike foil, ItemLike coil, Fluid molten) {

                MaceratorRecipeBuilder.of(registries)
                                .input(raw)
                                .output(dust.asItem(), 2) // TODO API : SimpleOutputItem#output ItemLike
                                .outputChance(dust.asItem(), 1, 0.25f)
                                .unlockedBy(raw)
                                .save(output, "_from_raw");

                MaceratorRecipeBuilder.of(registries)
                                .input(ingot)
                                .output(dust.asItem())
                                .unlockedBy(ingot)
                                .save(output, "_from_ingot");

                CompressorRecipeBuilder.of(registries)
                                .input(plate)
                                .catalyst(LibItems.MOLD_FOIL.get())
                                .output(foil)
                                .unlockedBy(plate)
                                .save(output, "_from_plate");

                MelterRecipeBuilder.of(registries)
                                .input(raw)
                                .output(molten, MoltenValues.INGOT * 3)
                                .unlockedBy(raw)
                                .save(output, "_from_raw");

                CasterRecipeBuilder.of(registries)
                                .fluid(molten, MoltenValues.INGOT)
                                .input(LibItems.MOLD_FOIL)
                                .output(foil)
                                .unlockedBy(LibItems.MOLD_FOIL.get())
                                .save(output);

                oreProcessing(ingot, nugget, block, plate, gear, molten);

        }

        private void oreProcessing(ItemLike ingot, ItemLike nugget, ItemLike block, ItemLike plate,
                        ItemLike gear, Fluid molten) {

                CompressorRecipeBuilder.of(registries)
                                .input(ingot.asItem(), 9)
                                .delay(10)
                                .overrideBaseEnergy()
                                .catalyst(LibItems.MOLD_BLOCK.get())
                                .output(block)
                                .unlockedBy(ingot)
                                .save(output, "_from_ingot");

                if (gear != null)
                        CompressorRecipeBuilder.of(registries)
                                        .input(ingot.asItem(), 4)
                                        .catalyst(LibItems.MOLD_GEAR.get())// TODO API: remove deprecation
                                        .output(gear)
                                        .unlockedBy(ingot)
                                        .save(output, "_from_ingot");

                CompressorRecipeBuilder.of(registries)
                                .input(ingot)
                                .delay(10)
                                .overrideBaseEnergy()
                                .catalyst(LibItems.MOLD_PLATE.get())
                                .output(plate)
                                .unlockedBy(ingot)
                                .save(output, "_from_ingot");

                CompressorRecipeBuilder.of(registries)
                                .input(nugget.asItem(), 9)
                                .delay(10)
                                .overrideBaseEnergy()
                                .catalyst(LibItems.MOLD_INGOT.get())// TODO API : deprecate DefferedHolder<Item,Item> ->
                                                                    // cause bugs!
                                .output(ingot)
                                .unlockedBy(nugget)
                                .save(output, "_from_nugget");

                CompressorRecipeBuilder.of(registries)
                                .input(ingot.asItem())
                                .delay(10)
                                .overrideBaseEnergy()
                                .catalyst(LibItems.MOLD_NUGGET.get())
                                .output(nugget.asItem(), 9)
                                .unlockedBy(ingot)
                                .save(output, "_from_ingot");

                if (molten != null)
                        CasterRecipeBuilder.of(registries)
                                        .fluid(molten, MoltenValues.BLOCK)
                                        .input(LibItems.MOLD_BLOCK)
                                        .output(block)
                                        .unlockedBy(LibItems.MOLD_BLOCK.get())
                                        .save(output);

                if (molten != null)
                        CasterRecipeBuilder.of(registries)
                                        .fluid(molten, MoltenValues.NUGGET)
                                        .input(LibItems.MOLD_NUGGET)
                                        .output(nugget)
                                        .unlockedBy(LibItems.STEEL_NUGGET.get())
                                        .save(output);

                if (gear != null)
                        if (molten != null)
                                CasterRecipeBuilder.of(registries)
                                                .fluid(molten, MoltenValues.INGOT * 4)
                                                .input(LibItems.MOLD_GEAR)
                                                .output(gear)
                                                .unlockedBy(LibItems.MOLD_GEAR.get())
                                                .save(output);

                if (molten != null)
                        CasterRecipeBuilder.of(registries)
                                        .fluid(molten, MoltenValues.INGOT)
                                        .input(LibItems.MOLD_PLATE)
                                        .output(plate)
                                        .unlockedBy(LibItems.MOLD_PLATE.get())
                                        .save(output);

                if (molten != null)
                        CasterRecipeBuilder.of(registries)
                                        .fluid(molten, MoltenValues.INGOT)
                                        .input(LibItems.MOLD_INGOT)
                                        .output(ingot)
                                        .unlockedBy(LibItems.MOLD_INGOT.get())
                                        .save(output);

                if (molten != null)
                        MelterRecipeBuilder.of(registries)
                                        .input(ingot)
                                        .output(molten, MoltenValues.INGOT)
                                        .unlockedBy(ingot)
                                        .save(output, "_from_ingot");

        }

        private void melterRecycle(Fluid fluid, TagKey<Item> item1, TagKey<Item> item2, TagKey<Item> item3,
                        TagKey<Item> item4, TagKey<Item> item5, TagKey<Item> item6, TagKey<Item> item7,
                        TagKey<Item> item8, TagKey<Item> item9) {

                MelterRecipeBuilder.of(registries)
                                .input(item1)
                                .output(fluid, MoltenValues.INGOT)
                                .unlockedBy(item1, items)
                                .save(output, overrideID("recycle/" + x.name(fluid) + "/1"));

                MelterRecipeBuilder.of(registries)
                                .input(item2)
                                .output(fluid, MoltenValues.INGOT * 2)
                                .unlockedBy(item2, items)
                                .save(output, overrideID("recycle/" + x.name(fluid) + "/2"));

                MelterRecipeBuilder.of(registries)
                                .input(item3)
                                .output(fluid, MoltenValues.INGOT * 3)
                                .unlockedBy(item3, items)
                                .save(output, overrideID("recycle/" + x.name(fluid) + "/3"));

                MelterRecipeBuilder.of(registries)
                                .input(item4)
                                .output(fluid, MoltenValues.INGOT * 4)
                                .unlockedBy(item4, items)
                                .save(output, overrideID("recycle/" + x.name(fluid) + "/4"));

                MelterRecipeBuilder.of(registries)
                                .input(item5)
                                .output(fluid, MoltenValues.INGOT * 5)
                                .unlockedBy(item5, items)
                                .save(output, overrideID("recycle/" + x.name(fluid) + "/5"));

                MelterRecipeBuilder.of(registries)
                                .input(item6)
                                .output(fluid, MoltenValues.INGOT * 6)
                                .unlockedBy(item6, items)
                                .save(output, overrideID("recycle/" + x.name(fluid) + "/6"));

                MelterRecipeBuilder.of(registries)
                                .input(item7)
                                .output(fluid, MoltenValues.INGOT * 7)
                                .unlockedBy(item7, items)
                                .save(output, overrideID("recycle/" + x.name(fluid) + "/7"));

                MelterRecipeBuilder.of(registries)
                                .input(item8)
                                .output(fluid, MoltenValues.INGOT * 8)
                                .unlockedBy(item8, items)
                                .save(output, overrideID("recycle/" + x.name(fluid) + "/8"));

                MelterRecipeBuilder.of(registries)
                                .input(item9)
                                .output(fluid, MoltenValues.INGOT * 9)
                                .unlockedBy(item9, items)
                                .save(output, overrideID("recycle/" + x.name(fluid) + "/9"));

        }

}