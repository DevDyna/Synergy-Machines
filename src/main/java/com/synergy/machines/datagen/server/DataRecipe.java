package com.synergy.machines.datagen.server;

import static com.synergy.machines.Main.MODULE_ID;

import java.util.concurrent.CompletableFuture;

import com.devdyna.cakesticklib.api.datagen.RecipeGenerators;
import com.devdyna.cakesticklib.api.utils.x;
import com.devdyna.cakesticklib.setup.registry.LibBlocks;
import com.devdyna.cakesticklib.setup.registry.LibItems;
import com.devdyna.cakesticklib.setup.registry.LibTags;
import com.synergy.machines.api.FluidRegister;
import com.synergy.machines.init.builders.alloy_smelter.recipe.AlloySmelterRecipeBuilder;
import com.synergy.machines.init.builders.caster.recipe.CasterRecipeBuilder;
import com.synergy.machines.init.builders.compressor.recipe.CompressorRecipeBuilder;
import com.synergy.machines.init.builders.extractor.recipe.ExtractorRecipeBuilder;
import com.synergy.machines.init.builders.macerator.recipe.MaceratorRecipeBuilder;
import com.synergy.machines.init.builders.melter.recipe.MelterRecipeBuilder;
import com.synergy.machines.init.builders.rock_crusher.recipe.RockCrusherRecipeBuilder;
import com.synergy.machines.init.types.zBlocks;
import com.synergy.machines.init.types.zFluids;
import com.synergy.machines.init.types.zMachines;
import com.synergy.machines.init.types.zTags;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
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

        protected DataRecipe(Provider registries, RecipeOutput output) {
                super(registries, output);
        }

        @Override
        protected void buildRecipes() {

                MaceratorRecipeBuilder.of(registries)
                                .input(Tags.Items.SANDSTONE_UNCOLORED_BLOCKS)
                                .output(x.itemTemplate(Items.SAND, 2)) // TODO
                                .output(LibItems.SILICON_SHARD, 1, 0.5f)
                                .unlockedBy(Tags.Items.SANDSTONE_UNCOLORED_BLOCKS, items)
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
                                .define('R', zTags.Items.LEGACY_STONES)
                                .unlockedBy(getHasName(zTags.Items.LEGACY_STONES), has(zTags.Items.LEGACY_STONES))
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
                                .output(x.itemTemplate(LibItems.SAWDUST, 4)) // TODO
                                .output(LibItems.SAWDUST, 2, 0.75f)
                                .unlockedBy(ItemTags.LOGS, items)
                                .save(output);

                MaceratorRecipeBuilder.of(registries)
                                .input(ItemTags.COALS)
                                .output(x.itemTemplate(LibItems.CARBON_DUST, 2)) // TODO
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
                                .output(LibItems.SILICON_SHARD, 0.15f)
                                .unlockedBy(Items.NETHERRACK)
                                .save(output);

                ExtractorRecipeBuilder.of(registries)
                                .input(Tags.Items.SLIME_BALLS)
                                .output(zFluids.PLASTIC.getFluid(), 25)
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

                MelterRecipeBuilder.of(registries)
                                .input(Tags.Items.GLASS_BLOCKS_CHEAP)
                                .output(zFluids.LIQUID_GLASS.getFluid(), MoltenValues.INGREDIENT_BARS)
                                .unlockedBy(Tags.Items.GLASS_BLOCKS_CHEAP, items)
                                .save(output, "_from_glass_blocks");

                // TODO API : change itemtag on glass to dust recipe!

                MelterRecipeBuilder.of(registries)
                                .input(Tags.Items.GLASS_PANES)
                                .output(zFluids.LIQUID_GLASS.getFluid(), MoltenValues.BARS)
                                .unlockedBy(Tags.Items.GLASS_PANES, items)
                                .save(output, "_from_glass_panes");

                CasterRecipeBuilder.of(registries)
                                .fluid(zFluids.LIQUID_GLASS.getFluid(), MoltenValues.BARS)
                                .input(LibItems.MOLD_FOIL)
                                .output(Items.GLASS_PANE)
                                .unlockedBy(LibItems.MOLD_FOIL.get())
                                .save(output);

                CasterRecipeBuilder.of(registries)
                                .fluid(zFluids.PLASTIC.getFluid(), 25)
                                .input(LibItems.MOLD_FOIL)
                                .output(LibItems.PLASTIC, 2)
                                .unlockedBy(LibItems.MOLD_FOIL.get())
                                .save(output);

                CasterRecipeBuilder.of(registries)
                                .fluid(zFluids.LIQUID_GLASS.getFluid(), MoltenValues.INGREDIENT_BARS)
                                .input(LibItems.MOLD_BLOCK)
                                .output(Items.GLASS)
                                .unlockedBy(LibItems.MOLD_BLOCK.get())
                                .save(output);

                // TODO API : ENERGY UPGRADES MUST USE BLUE BATTERIES

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

                oreProcessing(
                                LibItems.STEEL_INGOT.get(),
                                LibItems.STEEL_NUGGET.get(),
                                LibBlocks.STEEL_BLOCK.get(),
                                LibItems.STEEL_PLATE.get(),
                                LibItems.STEEL_GEAR.get(),
                                zFluids.MOLTEN_STEEL);

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
        public Provider getProvider() {
                return registries;
        }

        private void oreProcessing(ItemLike raw, ItemLike dust, ItemLike ingot, ItemLike nugget, ItemLike block,
                        ItemLike gear,
                        ItemLike plate, ItemLike foil, ItemLike coil, FluidRegister molten) {

                MaceratorRecipeBuilder.of(registries)
                                .input(raw)
                                .output(x.itemTemplate(dust.asItem(), 2)) // TODO
                                .output(dust.asItem(), 1, 0.25f)
                                .unlockedBy(raw)
                                .save(output, "_from_raw");

                MaceratorRecipeBuilder.of(registries)
                                .input(ingot)
                                .output(x.itemTemplate(dust.asItem())) // TODO
                                .unlockedBy(ingot)
                                .save(output, "_from_ingot");

                // TODO API : input(ItemLike input, int c)
                // TODO API : output(ItemLike output,int c)
                // TODO API : nugget mold & coil mold

                CompressorRecipeBuilder.of(registries)
                                .input(plate)
                                .catalyst(LibItems.MOLD_FOIL.get())
                                .output(foil)
                                .unlockedBy(plate)
                                .save(output, "_from_plate");

                MelterRecipeBuilder.of(registries)
                                .input(raw)
                                .output(molten.getFluid(), MoltenValues.INGOT * 3)
                                .unlockedBy(raw)
                                .save(output, "_from_raw");

                CasterRecipeBuilder.of(registries)
                                .fluid(molten.getFluid(), MoltenValues.INGOT)
                                .input(LibItems.MOLD_FOIL)
                                .output(foil)
                                .unlockedBy(LibItems.MOLD_FOIL.get())
                                .save(output);

                // TODO API : unlockedBy(DeferredHolder<Item, Item>)

                oreProcessing(ingot, nugget, block, plate, gear, molten);

        }

        private void oreProcessing(ItemLike ingot, ItemLike nugget, ItemLike block, ItemLike plate,
                        ItemLike gear, FluidRegister molten) {

                CompressorRecipeBuilder.of(registries)
                                .input(ingot.asItem(), 9)
                                .delay(10)
                                .overrideBaseEnergy()
                                .catalyst(LibItems.MOLD_BLOCK.get()) // TODO API : DEPRECATED
                                .output(block)
                                .unlockedBy(ingot)
                                .save(output, "_from_ingot");

                if (gear != null)
                        CompressorRecipeBuilder.of(registries)
                                        .input(ingot.asItem(), 4)
                                        .catalyst(LibItems.MOLD_GEAR.get())
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
                                .catalyst(LibItems.MOLD_INGOT.get())
                                .output(ingot)
                                .unlockedBy(nugget)
                                .save(output, "_from_nugget");

                CompressorRecipeBuilder.of(registries)
                                .input(ingot.asItem())
                                .delay(10)
                                .overrideBaseEnergy()
                                .catalyst(LibItems.STEEL_NUGGET.get())
                                .output(nugget.asItem(), 9)
                                .unlockedBy(ingot)
                                .save(output, "_from_ingot");

                if (molten != null)
                        CasterRecipeBuilder.of(registries)
                                        .fluid(molten.getFluid(), MoltenValues.BLOCK)
                                        .input(LibItems.MOLD_BLOCK)
                                        .output(block)
                                        .unlockedBy(LibItems.MOLD_BLOCK.get())
                                        .save(output);

                if (molten != null)
                        CasterRecipeBuilder.of(registries)
                                        .fluid(molten.getFluid(), MoltenValues.NUGGET)
                                        .input(LibItems.STEEL_NUGGET)// TODO API : DEPRECATED
                                        .output(nugget)
                                        .unlockedBy(LibItems.STEEL_NUGGET.get())
                                        .save(output);

                if (gear != null)
                        if (molten != null)
                                CasterRecipeBuilder.of(registries)
                                                .fluid(molten.getFluid(), MoltenValues.INGOT * 4)
                                                .input(LibItems.MOLD_GEAR)
                                                .output(gear)
                                                .unlockedBy(LibItems.MOLD_GEAR.get())
                                                .save(output);

                if (molten != null)
                        CasterRecipeBuilder.of(registries)
                                        .fluid(molten.getFluid(), MoltenValues.INGOT)
                                        .input(LibItems.MOLD_PLATE)
                                        .output(plate)
                                        .unlockedBy(LibItems.MOLD_PLATE.get())
                                        .save(output);

                if (molten != null)
                        CasterRecipeBuilder.of(registries)
                                        .fluid(molten.getFluid(), MoltenValues.INGOT)
                                        .input(LibItems.MOLD_INGOT)
                                        .output(ingot)
                                        .unlockedBy(LibItems.MOLD_INGOT.get())
                                        .save(output);

                if (molten != null)
                        MelterRecipeBuilder.of(registries)
                                        .input(ingot)
                                        .output(molten.getFluid(), MoltenValues.INGOT)
                                        .unlockedBy(ingot)
                                        .save(output);

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