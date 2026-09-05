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
        }

}
