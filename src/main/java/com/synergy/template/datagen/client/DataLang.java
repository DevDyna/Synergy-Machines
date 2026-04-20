package com.synergy.template.datagen.client;

import static com.synergy.template.Main.MODULE_ID;

import static com.devdyna.cakesticklib.api.datagen.LangUtils.*;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredHolder;

@SuppressWarnings("unused")
public class DataLang extends LanguageProvider {

        public DataLang(PackOutput o) {
                super(o, MODULE_ID, "en_us");
        }

        @Override
        protected void addTranslations() {

        }

}
