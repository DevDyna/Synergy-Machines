package com.synergy.machines;

import com.synergy.machines.init.Material;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

@Mod(Main.MODULE_ID)
public class Main {

    public static final String MODULE_ID = "machines";

    public Main(IEventBus bus, ModContainer c) {
        Material.register(bus);
        GameEvents.build(bus, c);
        Common.register(c);
    }

}
