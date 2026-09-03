package com.synergy.machines.datagen.client;

import static com.synergy.machines.Main.MODULE_ID;

import java.util.List;
import com.devdyna.cakesticklib.api.datagen.ModelUtils;
import com.devdyna.cakesticklib.api.utils.x;
import com.synergy.machines.api.MachineType;
import com.synergy.machines.api.machine.BaseMachineBlock;
import com.synergy.machines.api.solar_panel.SolarPanelBlock;
import com.synergy.machines.init.types.zBlocks;
import com.synergy.machines.init.types.zItems;
import com.synergy.machines.init.types.zMachines;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;

public class DataModel extends ModelProvider {

        public DataModel(PackOutput output) {
                super(output, MODULE_ID);
        }

        @Override
        protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {

                zItems.zBucketItems.getEntries().forEach(b -> ModelUtils.createBucketItem(itemModels, b.get()));
                zBlocks.zBlockFluids.getEntries().forEach(b -> ModelUtils.fluid(blockModels, b.get(), MODULE_ID));

                var machines = List.of(zMachines.ALLOY_SMELTER, zMachines.CASTING_FACTORY, zMachines.COMPRESSOR,
                                zMachines.ELECTRIC_FURNACE, zMachines.ELECTRIC_MELTER, zMachines.EXTRACTOR,
                                zMachines.MACERATOR, zMachines.ROCK_CRUSHER);

                machines.forEach(m -> createBasicMachineBlock(blockModels, m));

                machines.forEach(b -> {
                        itemModels.itemModelOutput.accept(b.block().get().asItem(),
                                        ItemModelUtils.plainModel(x.rl(MODULE_ID, "block/" + b.id() + "/off")));
                });

                itemModels.itemModelOutput.accept(zBlocks.SOLAR_PANEL.get().asItem(),
                                ItemModelUtils.plainModel(x.rl(MODULE_ID, "block/solar_panel/day/item_model")));

                itemModels.itemModelOutput.accept(zBlocks.LUNAR_PANEL.get().asItem(),
                                ItemModelUtils.plainModel(x.rl(MODULE_ID, "block/solar_panel/night/item_model")));

                createSolarPanel(blockModels, zBlocks.SOLAR_PANEL.get(), "day");
                createSolarPanel(blockModels, zBlocks.LUNAR_PANEL.get(), "night");

        }

        private void createBasicMachineBlock(BlockModelGenerators b, MachineType<?, ?, ?, ?> machine) {
                var block = machine.block().get();

                var baseMapping = new TextureMapping()
                                .put(TextureSlot.DOWN, new Material(modLocation(
                                                "block/machine/frame/basic/bottom")))
                                .put(TextureSlot.UP, new Material(modLocation(
                                                "block/machine/frame/basic/top")))
                                .put(TextureSlot.SIDE, new Material(modLocation(
                                                "block/machine/frame/basic/side")))
                                .put(TextureSlot.PARTICLE, new Material(modLocation(
                                                "block/machine/frame/basic/side")));

                var off = baseMapping.copy()
                                .put(
                                                TextureSlot.NORTH,
                                                new Material(modLocation(
                                                                "block/machine/processing/" + machine.id() + "/off"))

                                );

                var on = baseMapping.copy()
                                .put(
                                                TextureSlot.NORTH,
                                                new Material(modLocation(
                                                                "block/machine/processing/" + machine.id() + "/on")));

                b.blockStateOutput.accept(
                                MultiVariantGenerator.dispatch(block)
                                                .with(
                                                                PropertyDispatch.initial(BaseMachineBlock.ENABLED)
                                                                                .select(false, BlockModelGenerators
                                                                                                .plainVariant(
                                                                                                                ModelTemplates.CUBE
                                                                                                                                .createWithSuffix(
                                                                                                                                                block,
                                                                                                                                                "/off",
                                                                                                                                                off,
                                                                                                                                                b.modelOutput)))
                                                                                .select(true, BlockModelGenerators
                                                                                                .plainVariant(
                                                                                                                ModelTemplates.CUBE
                                                                                                                                .createWithSuffix(
                                                                                                                                                block,
                                                                                                                                                "/on",
                                                                                                                                                on,
                                                                                                                                                b.modelOutput))))
                                                .with(BlockModelGenerators.ROTATION_FACING));
        }

        private void createSolarPanel(
                        BlockModelGenerators b,
                        Block block,
                        String suffix) {

                MultiVariant core = BlockModelGenerators.plainVariant(
                                x.rl(MODULE_ID, "block/solar_panel/" + suffix + "/core_cell"));

                MultiVariant side = BlockModelGenerators.plainVariant(
                                x.rl(MODULE_ID, "block/solar_panel/side"));

                MultiVariant sideHalf = BlockModelGenerators.plainVariant(
                                x.rl(MODULE_ID, "block/solar_panel/side_half"));

                MultiVariant sideHalfAlt = BlockModelGenerators.plainVariant(
                                x.rl(MODULE_ID, "block/solar_panel/side_half_alt"));

                MultiVariant cellNorth = BlockModelGenerators.plainVariant(
                                x.rl(MODULE_ID, "block/solar_panel/" + suffix + "/cell/north"));

                MultiVariant cellEast = BlockModelGenerators.plainVariant(
                                x.rl(MODULE_ID, "block/solar_panel/" + suffix + "/cell/east"));

                MultiVariant cellSouth = BlockModelGenerators.plainVariant(
                                x.rl(MODULE_ID, "block/solar_panel/" + suffix + "/cell/south"));

                MultiVariant cellWest = BlockModelGenerators.plainVariant(
                                x.rl(MODULE_ID, "block/solar_panel/" + suffix + "/cell/west"));

                MultiVariant angleNorthEast = BlockModelGenerators.plainVariant(
                                x.rl(MODULE_ID, "block/solar_panel/" + suffix + "/angle/north_east"));

                MultiVariant angleNorthWest = BlockModelGenerators.plainVariant(
                                x.rl(MODULE_ID, "block/solar_panel/" + suffix + "/angle/north_west"));

                MultiVariant angleSouthEast = BlockModelGenerators.plainVariant(
                                x.rl(MODULE_ID, "block/solar_panel/" + suffix + "/angle/south_east"));

                MultiVariant angleSouthWest = BlockModelGenerators.plainVariant(
                                x.rl(MODULE_ID, "block/solar_panel/" + suffix + "/angle/south_west"));

                b.blockStateOutput.accept(
                                MultiPartGenerator.multiPart(block)

                                                // Core
                                                .with(core)

                                                // Full sides
                                                .with(
                                                                BlockModelGenerators.condition()
                                                                                .term(SolarPanelBlock.NORTH, false),
                                                                side)
                                                .with(
                                                                BlockModelGenerators.condition()
                                                                                .term(SolarPanelBlock.EAST, false),
                                                                side.with(BlockModelGenerators.Y_ROT_90))
                                                .with(
                                                                BlockModelGenerators.condition()
                                                                                .term(SolarPanelBlock.SOUTH, false),
                                                                side.with(BlockModelGenerators.Y_ROT_180))
                                                .with(
                                                                BlockModelGenerators.condition()
                                                                                .term(SolarPanelBlock.WEST, false),
                                                                side.with(BlockModelGenerators.Y_ROT_270))

                                                // Half sides
                                                .with(
                                                                BlockModelGenerators.condition()
                                                                                .term(SolarPanelBlock.NORTH, true)
                                                                                .term(SolarPanelBlock.EAST, false),
                                                                sideHalfAlt)
                                                .with(
                                                                BlockModelGenerators.condition()
                                                                                .term(SolarPanelBlock.EAST, true)
                                                                                .term(SolarPanelBlock.NORTH, false),
                                                                sideHalf.with(BlockModelGenerators.Y_ROT_90))
                                                .with(
                                                                BlockModelGenerators.condition()
                                                                                .term(SolarPanelBlock.SOUTH, true)
                                                                                .term(SolarPanelBlock.EAST, false),
                                                                sideHalf.with(BlockModelGenerators.Y_ROT_180))
                                                .with(
                                                                BlockModelGenerators.condition()
                                                                                .term(SolarPanelBlock.WEST, true)
                                                                                .term(SolarPanelBlock.NORTH, false),
                                                                sideHalfAlt.with(BlockModelGenerators.Y_ROT_270))
                                                .with(
                                                                BlockModelGenerators.condition()
                                                                                .term(SolarPanelBlock.NORTH, true)
                                                                                .term(SolarPanelBlock.WEST, false),
                                                                sideHalf)
                                                .with(
                                                                BlockModelGenerators.condition()
                                                                                .term(SolarPanelBlock.EAST, true)
                                                                                .term(SolarPanelBlock.SOUTH, false),
                                                                sideHalfAlt.with(BlockModelGenerators.Y_ROT_90))
                                                .with(
                                                                BlockModelGenerators.condition()
                                                                                .term(SolarPanelBlock.SOUTH, true)
                                                                                .term(SolarPanelBlock.WEST, false),
                                                                sideHalfAlt.with(BlockModelGenerators.Y_ROT_180))
                                                .with(
                                                                BlockModelGenerators.condition()
                                                                                .term(SolarPanelBlock.WEST, true)
                                                                                .term(SolarPanelBlock.SOUTH, false),
                                                                sideHalf.with(BlockModelGenerators.Y_ROT_270))

                                                // Cells
                                                .with(
                                                                BlockModelGenerators.condition()
                                                                                .term(SolarPanelBlock.NORTH, true),
                                                                cellNorth)
                                                .with(
                                                                BlockModelGenerators.condition()
                                                                                .term(SolarPanelBlock.EAST, true),
                                                                cellEast)
                                                .with(
                                                                BlockModelGenerators.condition()
                                                                                .term(SolarPanelBlock.SOUTH, true),
                                                                cellSouth)
                                                .with(
                                                                BlockModelGenerators.condition()
                                                                                .term(SolarPanelBlock.WEST, true),
                                                                cellWest)

                                                // Angles
                                                .with(
                                                                BlockModelGenerators.condition()
                                                                                .term(SolarPanelBlock.NORTH, true)
                                                                                .term(SolarPanelBlock.EAST, true),
                                                                angleNorthEast)
                                                .with(
                                                                BlockModelGenerators.condition()
                                                                                .term(SolarPanelBlock.NORTH, true)
                                                                                .term(SolarPanelBlock.WEST, true),
                                                                angleNorthWest)
                                                .with(
                                                                BlockModelGenerators.condition()
                                                                                .term(SolarPanelBlock.SOUTH, true)
                                                                                .term(SolarPanelBlock.EAST, true),
                                                                angleSouthEast)
                                                .with(
                                                                BlockModelGenerators.condition()
                                                                                .term(SolarPanelBlock.SOUTH, true)
                                                                                .term(SolarPanelBlock.WEST, true),
                                                                angleSouthWest));
        }

}
