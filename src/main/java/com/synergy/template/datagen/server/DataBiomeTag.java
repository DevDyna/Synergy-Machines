package com.synergy.template.datagen.server;

import static com.synergy.template.Main.MODULE_ID;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;

@SuppressWarnings("null")
public class DataBiomeTag extends BiomeTagsProvider {

        public DataBiomeTag(PackOutput o, CompletableFuture<Provider> p) {
                super(o, p, MODULE_ID);
        }

        @Override
        protected void addTags(Provider p) {

        }

}
