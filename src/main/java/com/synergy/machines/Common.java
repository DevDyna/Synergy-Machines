package com.synergy.machines;


import com.devdyna.cakesticklib.api.utils.StringUtil;
import com.synergy.machines.api.machine.BaseMachineBE;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.*;

public class Common {

        private static final ModConfigSpec.Builder qCOMMON = new ModConfigSpec.Builder();

        public static void register(ModContainer c) {
               industrial_machines();

                c.registerConfig(ModConfig.Type.COMMON, qCOMMON.build());
        }

        // ic4
        public static IntValue MACHINE_MAX_FE;// 100k

        public static BooleanValue ENABLE_SHIFT_INVERT_MACHINE_PLACEMENT;

        public static IntValue MACHINE_MAX_SPEED_UPGRADES_TYPE;// 4
        public static IntValue MACHINE_MAX_ENERGY_EFFICIENCY_UPGRADES_TYPE;// max
        // public static IntValue MACHINE_MAX_ENERGY_CAPACITY_UPGRADES_TYPE;// max
        public static IntValue MACHINE_MAX_LUCK_UPGRADES_TYPE;// max
        public static IntValue MACHINE_MAX_FLUID_UPGRADES_TYPE;// max

        public static IntValue MACHINE_MINIMAL_TICK_DELAY;// 1
        public static IntValue MACHINE_MINIMAL_FE_COST;// 0
        // public static IntValue MACHINE_MINIMAL_FE_CAPACITY;
        // public static IntValue MACHINE_MAXIMAL_FE_CAPACITY;
        public static IntValue MACHINE_MINIMAL_FLUID_COST;// 0
        public static IntValue MACHINE_MAXIMAL_LUCK;// 100

        public static BooleanValue DISABLE_MACHINE_DROP_WHEN_CORRUPTED;

        public static BooleanValue DISABLE_MACHINE_FURNACE_PROCESS_VANILLA;// false
        public static IntValue MACHINE_FURNACE_PROCESS_VANILLA_FE_COST;// DEFAULT
        public static BooleanValue DISABLE_MACHINE_FURNACE_VANILLA_TICK_REDUCER;// false
        public static IntValue MACHINE_FURNACE_PROCESS_VANILLA_MIN_TICK_DELAY;// 20
        public static IntValue MACHINE_FURNACE_PROCESS_VANILLA_PERCENTUAGE_TICK_DELAY;// 50%

        

        private static void industrial_machines() {
                qCOMMON.comment("Industrial-Machines").push("3-industrialmachines");

                decor.simple("Base Machine and Upgrades");

                ENABLE_SHIFT_INVERT_MACHINE_PLACEMENT = bool(
                                "Allow to place any machine on the opposite side when pressed shift during the placement",
                                "enable_shift_invert_facing", false);

                MACHINE_MAX_FE = number("Base max energy stored",
                                "base_machine_max_fe", 100_000);

                MACHINE_MAX_SPEED_UPGRADES_TYPE = number("Max Speed Increaser Upgrade Types usable foreach machine",
                                "max_speed_upgrades", 4, 0, 16);

                MACHINE_MAX_ENERGY_EFFICIENCY_UPGRADES_TYPE = number(
                                "Max Energy Efficiency Upgrade Types usable foreach machine",
                                "max_energy_upgrades", 16, 0, 16);

                MACHINE_MAX_LUCK_UPGRADES_TYPE = number(
                                "Max Secondary Output Increaser Upgrade Types usable foreach machine",
                                "max_luck_upgrades", 4, 0, 16);

                MACHINE_MAX_FLUID_UPGRADES_TYPE = number("Max Fluid Efficiency Upgrade Types usable foreach machine",
                                "max_fluid_upgrades", 4, 0, 16);

                MACHINE_MINIMAL_TICK_DELAY = number("Minimal tick delay based on upgrade installed",
                                "min_tick_rate", 1);

                MACHINE_MINIMAL_FE_COST = number("Minimal Energy cost based on upgrade installed",
                                "min_fe_cost", 5);

                MACHINE_MINIMAL_FLUID_COST = number("Minimal Fluid cost based on upgrade installed",
                                "min_mb_cost", 0);
                MACHINE_MAXIMAL_LUCK = number("Maximal Secondary Chance based on upgrade installed",
                                "max_luck", 100);

                DISABLE_MACHINE_DROP_WHEN_CORRUPTED = bool(
                                "Disable crash safer when an Industrial Machine is corrupted", "disable_ms_firewall");

                decor.complex("electric_furnace");

                DISABLE_MACHINE_FURNACE_PROCESS_VANILLA = bool("Disable Vanilla Recipes",
                                "machine_furnace_disable_vanilla");

                MACHINE_FURNACE_PROCESS_VANILLA_FE_COST = number("Vanilla Recipe Base FE consumed every tick",
                                "machine_furnace_vanilla_fe_cost", BaseMachineBE.DEFAULT_FE_COST);

                DISABLE_MACHINE_FURNACE_VANILLA_TICK_REDUCER = bool("Disable Vanilla Recipe Tick Reducer",
                                "machine_furnace_vanilla_disable_tick_reducer");

                MACHINE_FURNACE_PROCESS_VANILLA_MIN_TICK_DELAY = number("Vanilla Recipe Mininal Tick Delay",
                                "machine_furnace_vanilla_min_tick_delay", BaseMachineBE.DEFAULT_TICK_DURATION);
                MACHINE_FURNACE_PROCESS_VANILLA_PERCENTUAGE_TICK_DELAY = number(
                                "Vanilla Recipe Tick Delay reduction of total Tick Delay",
                                "machine_furnace_vanilla_percentuage_tick_delay", 50, 0, 100);

                qCOMMON.pop();
        }





        private static BooleanValue bool(String c, String k, boolean b) {
                return qCOMMON
                                .comment(c)
                                .define(k, b);
        }

        /**
         * default = false
         */
        private static BooleanValue bool(String c, String k) {
                return bool(c, k, false);
        }

        private static IntValue number(String c, String k, int d, int mn, int mx) {
                return qCOMMON
                                .comment(c)
                                .defineInRange(k, d, mn, mx);
        }

        private static DoubleValue numberFloat(String c, String k, double d, double min, double max) {
                return qCOMMON
                                .comment(c)
                                .defineInRange(k, d, min, max);
        }

        /**
         * min = 0<br/>
         * <br/>
         * max = Double.MAX_VALUE
         */
        private static DoubleValue numberFloat(String c, String k, double d) {
                return numberFloat(c, k, d, 0, Integer.MAX_VALUE);
        }

        /**
         * max = Double.MAX_VALUE
         */
        @SuppressWarnings("unused")
        private static DoubleValue numberFloat(String c, String k, double d, double min) {
                return numberFloat(c, k, d, min, Integer.MAX_VALUE);
        }

        /**
         * min = 1<br/>
         * <br/>
         * max = Integer.MAX_VALUE
         */
        private static IntValue number(String c, String k, int d) {
                return number(c, k, d, 1, Integer.MAX_VALUE);
        }

        /**
         * max = Integer.MAX_VALUE
         */
        private static IntValue number(String c, String k, int d, int min) {
                return number(c, k, d, min, Integer.MAX_VALUE);
        }

        protected class coolers {
                protected static IntValue base(String i) {
                        return number(StringUtil.nameCapitalized(i) + " Cooler Base Cooling", i + "_base_cooling", 0,
                                        Integer.MIN_VALUE);
                }

                protected static IntValue active(String i, int v) {
                        return number(StringUtil.nameCapitalized(i) + " Cooler Active Cooling", i + "_active_cooling",
                                        -v,
                                        Integer.MIN_VALUE);
                }
        }

        protected class moderators {
                protected static DoubleValue fe(String i, double v) {
                        return numberFloat(StringUtil.nameCapitalized(i) + " Moderator FE Reducer", i + "_fe_reducer",
                                        v);
                }

                protected static DoubleValue heat(String i, double v) {
                        return numberFloat(StringUtil.nameCapitalized(i) + " Moderator Heat Reducer",
                                        i + "_heat_reducer", v);
                }
        }

        protected class decor {
                protected static void complex(String s) {
                        qCOMMON.comment(StringUtil.nameCapitalized(s));
                }

                protected static void simple(String s) {
                        qCOMMON.comment(s);
                }
        }

}