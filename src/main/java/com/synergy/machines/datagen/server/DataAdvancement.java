package com.synergy.machines.datagen.server;

import static com.synergy.machines.Main.MODULE_ID;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import com.devdyna.cakesticklib.api.datagen.AdvancementsUtils;
import com.devdyna.cakesticklib.api.utils.x;
import com.synergy.machines.api.MachineType;
import com.synergy.machines.init.types.zBlocks;
import com.synergy.machines.init.types.zMachines;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;

public class DataAdvancement extends AdvancementProvider {

        public DataAdvancement(PackOutput output, CompletableFuture<Provider> registries,
                        List<AdvancementSubProvider> subProviders) {
                super(output, registries, subProviders);
        }

        public static class DataAdvancementGenerator implements AdvancementSubProvider {

                @SuppressWarnings("unused")
                @Override
                public void generate(Provider p, Consumer<AdvancementHolder> c) {

                        // TODO API : API advancements
                        // TODO IMP : API adv connections

                        // TODO IMP : solar advancements

                        var root_machine_frame = Advancement.Builder.advancement()
                                        .display(zBlocks.MACHINE_FRAME.get(),
                                                        Component.translatable(
                                                                        MODULE_ID + ".advancement.root." + MODULE_ID),
                                                        Component.translatable(MODULE_ID + ".advancement.root."
                                                                        + MODULE_ID + ".desc"),
                                                        x.rl(MODULE_ID, "block/machine/frame/basic/side"),
                                                        AdvancementType.TASK, false, false, false)
                                        .addCriterion("machine_frame",
                                                        InventoryChangeTrigger.TriggerInstance
                                                                        .hasItems(zBlocks.MACHINE_FRAME.get()))
                                        .requirements(AdvancementRequirements
                                                        .allOf(List.of("machine_frame")))
                                        .save(c, MODULE_ID + ":main/root");

                        var alloy_smelter = machineAdvancement(root_machine_frame, c,
                                        zMachines.ALLOY_SMELTER);
                        var casting = machineAdvancement(root_machine_frame, c, zMachines.CASTING_FACTORY);
                        var compressor = machineAdvancement(root_machine_frame, c, zMachines.COMPRESSOR);
                        var furnace = machineAdvancement(root_machine_frame, c,
                                        zMachines.ELECTRIC_FURNACE);
                        var extractor = machineAdvancement(root_machine_frame, c, zMachines.EXTRACTOR);
                        var macerator = machineAdvancement(root_machine_frame, c, zMachines.MACERATOR);
                        var melter = machineAdvancement(root_machine_frame, c, zMachines.ELECTRIC_MELTER);
                        var rock_crusher = machineAdvancement(root_machine_frame, c,
                                        zMachines.ROCK_CRUSHER);

                }

                public static AdvancementHolder machineAdvancement(AdvancementHolder p, Consumer<AdvancementHolder> c,
                                MachineType<?, ?, ?, ?> machine) {
                        return AdvancementsUtils.getExistingParent(p, machine.block().get(),
                                        MODULE_ID, machine.id(), AdvancementType.TASK, true, true, false)
                                        .addCriterion("craft_" + machine.id(),
                                                        InventoryChangeTrigger.TriggerInstance
                                                                        .hasItems(machine.block().get()))
                                        .requirements(AdvancementRequirements.allOf(List.of("craft_" + machine.id())))
                                        .save(c, MODULE_ID + ":main/" + machine.id());
                }

        }

}
