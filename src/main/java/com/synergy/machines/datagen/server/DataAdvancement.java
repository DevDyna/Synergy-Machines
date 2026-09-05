package com.synergy.machines.datagen.server;

import static com.synergy.machines.Main.MODULE_ID;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import com.devdyna.cakesticklib.CakeStickLib;
import com.devdyna.cakesticklib.api.datagen.AdvancementGenerator;
import com.devdyna.cakesticklib.api.utils.x;
import com.synergy.machines.init.types.zBlocks;
import com.synergy.machines.init.types.zMachines;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;

public class DataAdvancement extends AdvancementProvider {

        public DataAdvancement(PackOutput output, CompletableFuture<Provider> registries,
                        List<AdvancementSubProvider> subProviders) {
                super(output, registries, subProviders);
        }

        public static class DataAdvancementGenerator implements AdvancementSubProvider, AdvancementGenerator {

                @Override
                public void generate(Provider p, Consumer<AdvancementHolder> c) {

                        var machine_frame_root = simpleRoot(
                                        zBlocks.MACHINE_FRAME.get(),
                                        MODULE_ID + ":setup/",
                                        x.rl(MODULE_ID, "block/machine/frame/basic/side"),
                                        MODULE_ID, c);

                        simpleTask(CakeStickLib.MODULE_ID + ":setup/wrought_iron_ingot",
                                        zBlocks.MACHINE_FRAME.get(),
                                        MODULE_ID + ":setup/", c);

                        simpleTask(machine_frame_root,
                                        zBlocks.SOLAR_PANEL.get(),
                                        MODULE_ID + ":setup/", c);

                        simpleTask(machine_frame_root,
                                        zBlocks.LUNAR_PANEL.get(),
                                        MODULE_ID + ":setup/", c);

                        simpleTask(machine_frame_root,
                                        zMachines.ALLOY_SMELTER.block().get(),
                                        MODULE_ID + ":setup/", c);

                        simpleTask(machine_frame_root,
                                        zMachines.CASTING_FACTORY.block().get(),
                                        MODULE_ID + ":setup/", c);

                        simpleTask(machine_frame_root,
                                        zMachines.COMPRESSOR.block().get(),
                                        MODULE_ID + ":setup/", c);

                        simpleTask(machine_frame_root,
                                        zMachines.ELECTRIC_FURNACE.block().get(),
                                        MODULE_ID + ":setup/", c);

                        simpleTask(machine_frame_root,
                                        zMachines.ELECTRIC_MELTER.block().get(),
                                        MODULE_ID + ":setup/", c);

                        simpleTask(machine_frame_root,
                                        zMachines.EXTRACTOR.block().get(),
                                        MODULE_ID + ":setup/", c);

                        simpleTask(machine_frame_root,
                                        zMachines.MACERATOR.block().get(),
                                        MODULE_ID + ":setup/", c);

                        simpleTask(machine_frame_root,
                                        zMachines.ROCK_CRUSHER.block().get(),
                                        MODULE_ID + ":setup/", c);

                }

                @Override
                public String getModName() {
                        return MODULE_ID;
                }

        }

}
