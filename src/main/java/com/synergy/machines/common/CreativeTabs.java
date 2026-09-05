package com.synergy.machines.common;

import com.devdyna.cakesticklib.api.CreativeTabUtils;
import com.synergy.machines.init.Material;
import com.synergy.machines.init.types.zBlocks;
import com.synergy.machines.init.types.zCreativeTab;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

public class CreativeTabs {

    @SubscribeEvent
    public static void register(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == zCreativeTab.MACHINES_TAB.getKey()) {

            CreativeTabUtils.accept(event,
                    zBlocks.MACHINE_FRAME.get(),
                    zBlocks.SOLAR_PANEL.get(),
                    zBlocks.LUNAR_PANEL.get());

            Material.getAllMachineTypes().forEach(m -> event.accept(m.item().get()));

        }

    }

}
