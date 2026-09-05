package com.synergy.machines.datagen.server;

import java.util.*;

import com.devdyna.cakesticklib.api.utils.ArrayUtils;
import com.synergy.machines.api.MachineType;
import com.synergy.machines.init.Material;
import com.synergy.machines.init.types.zBlocks;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;

public class DataLootBlock extends BlockLootSubProvider {

        public DataLootBlock(HolderLookup.Provider l) {
                super(Set.of(), FeatureFlags.DEFAULT_FLAGS, l);
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {

                List<Block> blocks = new ArrayList<>();

                Material.getAllMachineTypes().stream()
                                .map(MachineType::block)
                                .map(DeferredHolder::get)
                                .forEach(blocks::add);

                return ArrayUtils.concat(blocks, zBlocks.LUNAR_PANEL.get(), zBlocks.SOLAR_PANEL.get(),zBlocks.MACHINE_FRAME.get());
        }

        @Override
        protected void generate() {
                Material.getAllMachineTypes().stream()
                                .map(MachineType::block)
                                .map(DeferredHolder::get)
                                .forEach(this::dropSelf);

                dropSelf(zBlocks.SOLAR_PANEL.get());
                dropSelf(zBlocks.LUNAR_PANEL.get());
                dropSelf(zBlocks.MACHINE_FRAME.get());
        }

}
