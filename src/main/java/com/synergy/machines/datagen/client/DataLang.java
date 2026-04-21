package com.synergy.machines.datagen.client;

import static com.devdyna.cakesticklib.api.datagen.LangUtils.*;
import static com.synergy.machines.Main.MODULE_ID;

import com.synergy.machines.init.Material;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredHolder;

@SuppressWarnings("unused")
public class DataLang extends LanguageProvider {

        public DataLang(PackOutput o) {
                super(o, MODULE_ID, "en_us");
        }

        @Override
        protected void addTranslations() {

                Material.getAllMachineTypes().forEach(m -> addBlock(m.block(), named(m.block(), MODULE_ID)));

        }

}
