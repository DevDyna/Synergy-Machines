package com.synergy.machines.datagen.server;

import java.util.*;

import com.synergy.machines.api.MachineType;
import com.synergy.machines.init.Material;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;

public class DataLootBlock extends BlockLootSubProvider {

        public DataLootBlock(HolderLookup.Provider l) {
                super(Set.of(), FeatureFlags.DEFAULT_FLAGS, l);
        }

        @SuppressWarnings("unchecked")
        @Override
        protected Iterable<Block> getKnownBlocks() {

                return (List<Block>) Material.getAllMachineTypes()
                                .stream()
                                .map(MachineType::block)
                                .map(DeferredHolder::get)
                                .toList();
        }

        @Override
        protected void generate() {
                Material.getAllMachineTypes().stream()
                                .map(MachineType::block)
                                .map(DeferredHolder::get)
                                .forEach(this::dropSelf);
        }

}
