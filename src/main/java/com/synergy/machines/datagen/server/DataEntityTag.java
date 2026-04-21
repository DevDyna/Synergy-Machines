package com.synergy.machines.datagen.server;


import static com.synergy.machines.Main.MODULE_ID;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;

@SuppressWarnings("null")
public class DataEntityTag extends EntityTypeTagsProvider {

        public DataEntityTag(PackOutput output, CompletableFuture<Provider> provider) {
                super(output, provider, MODULE_ID);
        }

        @Override
        protected void addTags(Provider provider) {
               
        }

}