package com.synergy.machines.common;

import com.synergy.machines.init.Material;
import com.synergy.machines.init.types.zBlocks;
import com.synergy.machines.init.types.zItems;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

public class CreativeTabs {

    @SubscribeEvent
    public static void register(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {

            Material.getAllMachineTypes().forEach(m -> event.accept(m.item().get()));

            event.accept(zBlocks.SOLAR_PANEL_DAY.get());
            event.accept(zBlocks.SOLAR_PANEL_NIGHT.get());
        }

        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {

            event.accept(zItems.SPEED_UPGRADE.get().set(20, 125, 0, 0));
            event.accept(zItems.ENERGY_UPGRADE.get().set(0, -50, 0, 0));
            event.accept(zItems.LUCK_UPGRADE.get().set(0, 150, 15, 0));
            event.accept(zItems.FLUID_UPGRADE.get().set(0, 150, 0, -20));
        }

    }
}
