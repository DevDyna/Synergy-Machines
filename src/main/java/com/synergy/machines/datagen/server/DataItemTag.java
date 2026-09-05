package com.synergy.machines.datagen.server;

import static com.synergy.machines.Main.MODULE_ID;

import java.util.concurrent.CompletableFuture;

import com.synergy.machines.api.MachineType;
import com.synergy.machines.init.Material;
import com.synergy.machines.init.types.zBlocks;
import com.synergy.machines.init.types.zTags;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import net.neoforged.neoforge.registries.DeferredHolder;

public class DataItemTag extends ItemTagsProvider {

        public DataItemTag(PackOutput o, CompletableFuture<HolderLookup.Provider> p,
                        CompletableFuture<TagLookup<Block>> b) {
                super(o, p, MODULE_ID);
        }

        @Override
        protected void addTags(Provider p) {

                tag(zTags.Items.MACHINES)
                                .add(Material.getAllMachineTypes()
                                                .stream()
                                                .map(MachineType::item)
                                                .map(DeferredHolder::get)
                                                .toArray(Item[]::new));

                tag(zTags.Items.SOLAR_PANELS)
                                .add(zBlocks.LUNAR_PANEL.get().asItem(),
                                                zBlocks.SOLAR_PANEL.get().asItem());

        }

}