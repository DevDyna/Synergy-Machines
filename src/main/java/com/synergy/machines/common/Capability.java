package com.synergy.machines.common;

import com.devdyna.cakesticklib.api.CapabilityUtils;
import com.synergy.machines.api.MachineType;
import com.synergy.machines.init.Material;
import com.synergy.machines.init.types.zBlocks;
import com.synergy.machines.init.types.zMachines;

import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

public class Capability {

    @SubscribeEvent
    public static void register(RegisterCapabilitiesEvent event) {

        var machines = Material.getAllMachineTypes()
                                .stream()
                                .map(MachineType::block)
                                .map(DeferredHolder::get)
                                .toArray(Block[]::new);


        CapabilityUtils.registerEnergyBlock(event, zBlocks.SOLAR_PANEL.get(), zBlocks.LUNAR_PANEL.get());

        CapabilityUtils.registerEnergyBlock(event, machines);
        CapabilityUtils.registerItemBlock(event, machines);

        CapabilityUtils.registerFluidBlocks(event,
                zMachines.CASTING_FACTORY.block().get(),
                zMachines.ELECTRIC_MELTER.block().get(),
                zMachines.EXTRACTOR.block().get(),
                zMachines.ROCK_CRUSHER.block().get(),
                zMachines.FRAMEWORK.block().get()
                );
    }
}
