package com.synergy.machines.datagen.server;

import static com.synergy.machines.Main.MODULE_ID;

import java.util.concurrent.CompletableFuture;

import com.synergy.machines.api.MachineType;
import com.synergy.machines.init.Material;
import com.synergy.machines.init.types.zBlocks;
import com.synergy.machines.init.types.zTags;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.registries.DeferredHolder;

public class DataBlockTag extends BlockTagsProvider {

        public DataBlockTag(PackOutput output, CompletableFuture<Provider> lookupProvider) {
                super(output, lookupProvider, MODULE_ID);
        }

        @Override
        protected void addTags(Provider p) {

                var machines = Material.getAllMachineTypes()
                                .stream()
                                .map(MachineType::block)
                                .map(DeferredHolder::get)
                                .toArray(Block[]::new);

                tag(zTags.Blocks.MACHINES)
                                .add(machines);

                tag(BlockTags.MINEABLE_WITH_PICKAXE)
                                .add(machines)
                                .add(
                                                zBlocks.LUNAR_PANEL.get(),
                                                zBlocks.SOLAR_PANEL.get(),
                                                zBlocks.MACHINE_FRAME.get());

                tag(zTags.Blocks.SOLAR_PANELS)
                                .add(zBlocks.LUNAR_PANEL.get(),
                                                zBlocks.SOLAR_PANEL.get());
        }

}