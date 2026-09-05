package com.synergy.machines.datagen.server;

import static com.synergy.machines.Main.MODULE_ID;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import com.devdyna.cakesticklib.api.utils.x;
import com.devdyna.cakesticklib.setup.registry.LibItems;
import com.synergy.machines.api.AdvancementGenerator;
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

                @SuppressWarnings("unused")
                @Override
                public void generate(Provider p, Consumer<AdvancementHolder> c) {

                        // TODO API : API advancements

                        var wrought_iron_task = simpleTask("minecraft:story/smelt_iron",
                                        LibItems.WROUGHT_IRON_INGOT.get(),
                                        MODULE_ID + ":extend/story/smelt_iron/", c);

                        // TODO IMP : API adv connections

                        // TODO IMP : solar advancements

                        var machine_frame_root = simpleRoot(
                                        zBlocks.MACHINE_FRAME.get(),
                                        MODULE_ID + ":extend/story/smelt_iron/",
                                        x.rl(MODULE_ID, "block/machine/frame/basic/side"),
                                        MODULE_ID, c);

                        var machine_frame_task = simpleTask(wrought_iron_task,
                                        zBlocks.MACHINE_FRAME.get(),
                                        MODULE_ID + ":main/", c);

                        var solar_panel = simpleTask(machine_frame_root,
                                        zBlocks.SOLAR_PANEL.get(),
                                        MODULE_ID + ":main/", c);

                        var lunar_panel = simpleTask(machine_frame_root,
                                        zBlocks.LUNAR_PANEL.get(),
                                        MODULE_ID + ":main/", c);

                        var alloy_smelter = simpleTask(machine_frame_root,
                                        zMachines.ALLOY_SMELTER.block().get(),
                                        MODULE_ID + ":main/", c);

                        var casting_factory = simpleTask(machine_frame_root,
                                        zMachines.CASTING_FACTORY.block().get(),
                                        MODULE_ID + ":main/", c);

                        var compressor = simpleTask(machine_frame_root,
                                        zMachines.COMPRESSOR.block().get(),
                                        MODULE_ID + ":main/", c);

                        var electric_furnace = simpleTask(machine_frame_root,
                                        zMachines.ELECTRIC_FURNACE.block().get(),
                                        MODULE_ID + ":main/", c);

                        var electric_melter = simpleTask(machine_frame_root,
                                        zMachines.ELECTRIC_MELTER.block().get(),
                                        MODULE_ID + ":main/", c);

                        var extractor = simpleTask(machine_frame_root,
                                        zMachines.EXTRACTOR.block().get(),
                                        MODULE_ID + ":main/", c);

                        var macerator = simpleTask(machine_frame_root,
                                        zMachines.MACERATOR.block().get(),
                                        MODULE_ID + ":main/", c);

                        var rock_crusher = simpleTask(machine_frame_root,
                                        zMachines.ROCK_CRUSHER.block().get(),
                                        MODULE_ID + ":main/", c);

                }

                @Override
                public String getModName() {
                        return MODULE_ID;
                }

        }

}
