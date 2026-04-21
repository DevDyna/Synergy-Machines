package com.synergy.machines.init.types;



import static com.synergy.machines.Main.MODULE_ID;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class zContainer {
        public static void register(IEventBus bus) {
                zMenu.register(bus);
        }

    public static final DeferredRegister<MenuType<?>> zMenu = DeferredRegister.create(Registries.MENU, MODULE_ID);

}