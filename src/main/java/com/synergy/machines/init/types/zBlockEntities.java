package com.synergy.machines.init.types;

import static com.synergy.machines.Main.MODULE_ID;

import com.devdyna.cakesticklib.api.RegistryUtils;
import com.synergy.machines.init.builders.solar_panel.day.DaySolarPanelBE;
import com.synergy.machines.init.builders.solar_panel.night.NightSolarPanelBE;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class zBlockEntities {

    public static void register(IEventBus bus) {
        zTiles.register(bus);
    }

    public static final DeferredRegister<BlockEntityType<?>> zTiles = DeferredRegister
            .create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MODULE_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DaySolarPanelBE>> SOLAR_PANEL_DAY = RegistryUtils
            .createBlockEntity("solar_panel_day", zTiles, DaySolarPanelBE::new, zBlocks.SOLAR_PANEL_DAY);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<NightSolarPanelBE>> SOLAR_PANEL_NIGHT = RegistryUtils
            .createBlockEntity("solar_panel_night", zTiles, NightSolarPanelBE::new, zBlocks.SOLAR_PANEL_NIGHT);

}
