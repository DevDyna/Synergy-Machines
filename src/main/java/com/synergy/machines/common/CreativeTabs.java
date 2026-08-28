package com.synergy.machines.common;

import com.devdyna.cakesticklib.api.CreativeTabUtils;
import com.synergy.machines.init.Material;
import com.synergy.machines.init.types.zBlocks;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

public class CreativeTabs {

    @SubscribeEvent
    public static void register(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {

            Material.getAllMachineTypes().forEach(m -> event.accept(m.item().get()));

            CreativeTabUtils.accept(event, zBlocks.SOLAR_PANEL.get().asItem(), zBlocks.LUNAR_PANEL.get().asItem());

        }

    }
}
