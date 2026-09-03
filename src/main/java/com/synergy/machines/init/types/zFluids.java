package com.synergy.machines.init.types;

import static com.synergy.machines.Main.MODULE_ID;

import com.devdyna.cakesticklib.api.utils.ColorUtils;
import com.synergy.machines.api.FluidRegister;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;

public class zFluids {
        public static void register(IEventBus bus) {
                zFluids.register(bus);
                zFluidTypes.register(bus);
        }

        public static final DeferredRegister<Fluid> zFluids = DeferredRegister.create(BuiltInRegistries.FLUID,
                        MODULE_ID);
        public static final DeferredRegister<FluidType> zFluidTypes = DeferredRegister.create(Keys.FLUID_TYPES,
                        MODULE_ID);

        public static final FluidRegister CRUDE_OIL = FluidRegister.create("crude_oil", 0xFF202020);

        public static final FluidRegister LIQUID_GLASS = FluidRegister
                        .create("liquid_glass", ColorUtils.WHITE.brighter());

        // ---------------------------------------------------------------------------------------//
        public static final FluidRegister MOLTEN_IRON = FluidRegister
                        .create("iron", ColorUtils.LIGHT_GRAY.brighter());

        public static final FluidRegister MOLTEN_COPPER = FluidRegister
                        .create("copper", ColorUtils.ORANGE.darker());

        public static final FluidRegister MOLTEN_GOLD = FluidRegister
                        .create("gold", ColorUtils.YELLOW.brighter());

        public static final FluidRegister MOLTEN_STEEL = FluidRegister.create("steel", ColorUtils.GRAY.darker());

        public static final FluidRegister MOLTEN_ANCIENT_DEBRIS = FluidRegister
                        .create("ancient_debris", ColorUtils.PINK.darker().darker());

        public static final FluidRegister SULFURIC_ACID = FluidRegister
                        .create("sulfuric_acid", ColorUtils.YELLOW).drown();

}