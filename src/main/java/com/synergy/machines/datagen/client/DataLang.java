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

                add(MODULE_ID + ".advancement.root." + MODULE_ID, "Synergy : Machines");
                add(MODULE_ID + ".advancement.root." + MODULE_ID + ".desc",
                                "The keywords are simplify , automate and improve");

                add(MODULE_ID + ".creative_tab." + MODULE_ID, "Synergy : Machines");

        }

}
