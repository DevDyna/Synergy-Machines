package com.synergy.machines.common;

import com.devdyna.cakesticklib.api.CapabilityUtils;
import com.synergy.machines.init.Material;
import com.synergy.machines.init.types.zMachines;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class Capability {

    @SubscribeEvent
    public static void register(RegisterCapabilitiesEvent event) {
        CapabilityUtils.registerEnergyBlock(event, Material.getBlocks());
        CapabilityUtils.registerItemBlock(event, Material.getBlocks());

        CapabilityUtils.registerFluidBlocks(event, zMachines.CASTING_FACTORY.block().get(),zMachines.ELECTRIC_MELTER.block().get(),zMachines.EXTRACTOR.block().get(),zMachines.ROCK_CRUSHER.block().get());
    }
}
