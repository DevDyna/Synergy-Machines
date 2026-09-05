package com.synergy.machines.datagen.client;

import static com.devdyna.cakesticklib.api.datagen.LangUtils.*;
import static com.synergy.machines.Main.MODULE_ID;

import com.devdyna.cakesticklib.api.datagen.LangGenerators;
import com.devdyna.cakesticklib.api.datagen.LangUtils;
import com.synergy.machines.init.Material;
import com.synergy.machines.init.types.*;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class DataLang extends LanguageProvider implements LangGenerators {

        public DataLang(PackOutput o) {
                super(o, MODULE_ID, "en_us");
        }

        @Override
        protected void addTranslations() {

                zFluids.zFluidTypes.getEntries()
                                .forEach(f -> addFluid(f.get(), named(f, MODULE_ID).replace(" Type", "")));
                zItems.zBucketItems.getEntries().forEach(i -> addItem(i, named(i, MODULE_ID)));
                zBlocks.zBlockFluids.getEntries().forEach(b -> addBlock(b, LangUtils.named(b, MODULE_ID)));

                Material.getAllMachineTypes().forEach(m -> {
                        addBlock(m.block(), named(m.block(), MODULE_ID));
                        add(MODULE_ID + ".jei." + m.id(), named(m.item(), MODULE_ID) + " Recipes");
                });

                addBlock(zBlocks.SOLAR_PANEL, "Solar Panel");
                addBlock(zBlocks.LUNAR_PANEL, "Lunar Panel");

                addBlock(zBlocks.MACHINE_FRAME, "Machine Frame");

                LangUtils.advKey(this, MODULE_ID, zMachines.ALLOY_SMELTER.id(), "Mix stuff", "Craft an alloy smelter");
                LangUtils.advKey(this, MODULE_ID, zMachines.CASTING_FACTORY.id(), "Cool it down!",
                                "Craft a casting factory");
                LangUtils.advKey(this, MODULE_ID, zMachines.COMPRESSOR.id(), "Don't put your finger here!",
                                "Craft a compressor");
                LangUtils.advKey(this, MODULE_ID, zMachines.ELECTRIC_FURNACE.id(), "Not an Iron Furnace",
                                "Craft an electric furnace");
                LangUtils.advKey(this, MODULE_ID, zMachines.EXTRACTOR.id(), "Extract the essential",
                                "Craft an extractor");
                LangUtils.advKey(this, MODULE_ID, zMachines.MACERATOR.id(), "Crush into dusts",
                                "Craft a macerator to process items into dusts");
                LangUtils.advKey(this, MODULE_ID, zMachines.ELECTRIC_MELTER.id(), "High temperatures",
                                "Craft an electric melter");
                LangUtils.advKey(this, MODULE_ID, zMachines.ROCK_CRUSHER.id(), "Rock 'n' Roll", "Craft a rock crusher");

                LangUtils.advKey(this, MODULE_ID, "solar_panel", "Eco Green Energy for you", "Craft a solar panel");
                LangUtils.advKey(this, MODULE_ID, "lunar_panel", "Light up on night", "Craft a lunar panel");

                LangUtils.advKey(this, MODULE_ID, "machine_frame", "The core of everything", "Obtain a machine frame");

                LangUtils.advKey(this, MODULE_ID, "wrought_iron_ingot", "One shade of gray",
                                "Combine some carbon dust with an iron ingot");

                add(MODULE_ID + ".advancement.root." + MODULE_ID, "Industrial Machines");
                add(MODULE_ID + ".advancement.root." + MODULE_ID + ".desc",
                                "The world need something to be more nicer");

                add(MODULE_ID + ".creative_tab." + MODULE_ID, "Synergy : Machines");

                add(MODULE_ID + ".configuration.machines", "Machine Configuration");

                add(MODULE_ID + ".configuration.solar_panel_fe_max", "Solar Panels FE Capacity");
                add(MODULE_ID + ".configuration.solar_panel_fe_gen", "Solar Panels FE every tick");
                add(MODULE_ID + ".configuration.solar_panel_disable_seesky", "Solar Panels require to see the sky");

                add(MODULE_ID + ".configuration.enable_shift_invert_facing",
                                "Allow to place any machine on the opposite side when pressed shift during the placement");

                add(MODULE_ID + ".configuration.base_machine_max_fe", "Base max energy stored");

                add(MODULE_ID + ".configuration.max_speed_upgrades",
                                "Max Speed Increaser Upgrade Types usable foreach machine");
                add(MODULE_ID + ".configuration.max_energy_upgrades",
                                "Max Energy Efficiency Upgrade Types usable foreach machine");
                add(MODULE_ID + ".configuration.max_luck_upgrades",
                                "Max Secondary Output Increaser Upgrade Types usable foreach machine");
                add(MODULE_ID + ".configuration.max_fluid_upgrades",
                                "Max Fluid Efficiency Upgrade Types usable foreach machine");

                add(MODULE_ID + ".configuration.min_tick_rate", "Minimal tick delay based on upgrade installed");
                add(MODULE_ID + ".configuration.min_fe_cost", "Minimal Energy cost based on upgrade installed");
                add(MODULE_ID + ".configuration.min_mb_cost", "Minimal Fluid cost based on upgrade installed");
                add(MODULE_ID + ".configuration.max_luck", "Maximal Secondary Chance based on upgrade installed");

                add(MODULE_ID + ".configuration.disable_ms_firewall",
                                "Disable crash safer when an Industrial Machine is corrupted");

                add(MODULE_ID + ".configuration.machine_furnace_disable_vanilla", "Disable Vanilla Recipes");
                add(MODULE_ID + ".configuration.machine_furnace_vanilla_fe_cost",
                                "Vanilla Recipe Base FE consumed every tick");
                add(MODULE_ID + ".configuration.machine_furnace_vanilla_disable_tick_reducer",
                                "Disable Vanilla Recipe Tick Reducer");
                add(MODULE_ID + ".configuration.machine_furnace_vanilla_min_tick_delay",
                                "Vanilla Recipe Mininal Tick Delay");
                add(MODULE_ID + ".configuration.machine_furnace_vanilla_percentuage_tick_delay",
                                "Vanilla Recipe Tick Delay reduction of total Tick Delay");

        }

}
