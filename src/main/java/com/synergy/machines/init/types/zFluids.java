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

        public static final FluidRegister OIL = FluidRegister.create("oil", 0xFF202020);

        public static final FluidRegister SAP = FluidRegister.create("sap", 0xFFF2A619);

        public static final FluidRegister GLUE = FluidRegister.create("glue", 0xDAF3EFE6);

        public static final FluidRegister IRONBERRY_JUICE = FluidRegister
                        .create("ironberry_juice", ColorUtils.LIGHT_GRAY.brighter().brighter());

        public static final FluidRegister LIQUID_GLASS = FluidRegister
                        .create("liquid_glass", ColorUtils.WHITE.brighter());

        public static final FluidRegister HONEY = FluidRegister.create("honey", ColorUtils.YELLOW);

        // ---------------------------------------------------------------------------------------//
        public static final FluidRegister MOLTEN_IRON = FluidRegister
                        .create("iron", ColorUtils.LIGHT_GRAY.brighter());

        public static final FluidRegister MOLTEN_COPPER = FluidRegister
                        .create("copper", ColorUtils.ORANGE.darker());

        public static final FluidRegister MOLTEN_GOLD = FluidRegister
                        .create("gold", ColorUtils.YELLOW.brighter());

        public static final FluidRegister MOLTEN_STEEL = FluidRegister.create("steel", ColorUtils.GRAY.darker())

        ;

        public static final FluidRegister MOLTEN_URANIUM = FluidRegister
                        .create("uranium", ColorUtils.GREEN.brighter().brighter());

        public static final FluidRegister MOLTEN_NICKEL = FluidRegister
                        .create("nickel", ColorUtils.YELLOW.darker());

        public static final FluidRegister MOLTEN_SILVER = FluidRegister
                        .create("silver", ColorUtils.CYAN.brighter());

        public static final FluidRegister MOLTEN_TIN = FluidRegister
                        .create("tin", ColorUtils.LIGHT_GRAY.darker());

        public static final FluidRegister MOLTEN_ALUMINUM = FluidRegister
                        .create("aluminum", ColorUtils.WHITE.darker());

        public static final FluidRegister MOLTEN_IRIDIUM = FluidRegister
                        .create("iridium", ColorUtils.MAGENTA.darker());

        public static final FluidRegister MOLTEN_PLATINUM = FluidRegister
                        .create("platinum", ColorUtils.CYAN.darker());

        public static final FluidRegister MOLTEN_OSMIUM = FluidRegister.create("osmium", ColorUtils.CYAN)

        ;

        public static final FluidRegister MOLTEN_LEAD = FluidRegister
                        .create("lead", ColorUtils.PINK.darker().darker());

        public static final FluidRegister MOLTEN_ANCIENT_DEBRIS = FluidRegister
                        .create("ancient_debris", ColorUtils.PINK.darker().darker());

        public static final FluidRegister MOLTEN_BLAZE = FluidRegister
                        .create("blaze", ColorUtils.YELLOW.brighter().brighter());

        public static final FluidRegister RUBBER = FluidRegister
                        .create("rubber", 0xDAF3EFE6);

        public static final FluidRegister SULFURIC_ACID = FluidRegister
                        .create("sulfuric_acid", ColorUtils.YELLOW).drown();

        public static final FluidRegister MOLTEN_BRONZE = FluidRegister
                        .create("bronze", ColorUtils.ORANGE.darker());

        public static final FluidRegister STEAM = FluidRegister.create("steam", ColorUtils.WHITE);

}