package com.synergy.machines.init.types;

import static com.synergy.machines.Main.MODULE_ID;

import com.synergy.machines.init.builders.IndustrialUpgrade;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class zItems {

    public static void register(IEventBus bus) {
        zItem.register(bus);
        zBlockItem.register(bus);
        zBucketItems.register(bus);
    }

    public static final DeferredRegister.Items zBucketItems = DeferredRegister.createItems(MODULE_ID);
    public static final DeferredRegister.Items zItem = DeferredRegister.createItems(MODULE_ID);
    public static final DeferredRegister.Items zBlockItem = DeferredRegister.createItems(MODULE_ID);

    
    public static final DeferredHolder<Item,IndustrialUpgrade> SPEED_UPGRADE = zItem.registerItem("speed_upgrade", p-> new IndustrialUpgrade(p));
    public static final DeferredHolder<Item,IndustrialUpgrade> ENERGY_UPGRADE = zItem.registerItem("energy_upgrade", p-> new IndustrialUpgrade(p));
    public static final DeferredHolder<Item,IndustrialUpgrade> LUCK_UPGRADE = zItem.registerItem("luck_upgrade", p-> new IndustrialUpgrade(p));
    public static final DeferredHolder<Item,IndustrialUpgrade> FLUID_UPGRADE = zItem.registerItem("fluid_upgrade", p-> new IndustrialUpgrade(p));


}
