package com.synergy.template.init;

import com.synergy.template.init.types.*;

import net.neoforged.bus.api.IEventBus;

public class Material {
        public static void register(IEventBus bus) {
                zItems.register(bus);
                zBlocks.register(bus);
                zBlockEntities.register(bus);
                zRecipeTypes.register(bus);
        }


}
