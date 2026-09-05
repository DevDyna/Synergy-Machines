package com.synergy.machines.datagen.server;

import static com.synergy.machines.Main.MODULE_ID;

import java.util.concurrent.CompletableFuture;

import com.devdyna.cakesticklib.setup.registry.LibTags;
import com.synergy.machines.init.types.zTags;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

public class DataItemTag extends ItemTagsProvider {

        public DataItemTag(PackOutput o, CompletableFuture<HolderLookup.Provider> p,
                        CompletableFuture<TagLookup<Block>> b) {
                super(o, p, MODULE_ID);
        }

        @Override
        protected void addTags(Provider p) {

                tag(zTags.Items.RECYCLE_COPPER_1).add(
                                Items.COPPER_SHOVEL,
                                Items.COPPER_SPEAR)
                                .addTags(LibTags.Items.COPPER_PLATE,
                                                LibTags.Items.COPPER_COIL,
                                                LibTags.Items.COPPER_FOIL,
                                                LibTags.Items.COPPER_DUST);

                tag(zTags.Items.RECYCLE_COPPER_2).add(
                                Items.COPPER_HOE,
                                Items.COPPER_SWORD,
                                Items.COPPER_DOOR);

                tag(zTags.Items.RECYCLE_COPPER_3).add(
                                Items.COPPER_PICKAXE,
                                Items.COPPER_AXE);

                tag(zTags.Items.RECYCLE_COPPER_4).add(
                                Items.COPPER_BOOTS,
                                Items.COPPER_TRAPDOOR)
                                .addTags(LibTags.Items.COPPER_GEAR);

                tag(zTags.Items.RECYCLE_COPPER_5).add(
                                Items.COPPER_HELMET);

                tag(zTags.Items.RECYCLE_COPPER_6).add(
                                Items.COPPER_HORSE_ARMOR);

                tag(zTags.Items.RECYCLE_COPPER_7).add(
                                Items.COPPER_LEGGINGS);

                tag(zTags.Items.RECYCLE_COPPER_8).add(
                                Items.COPPER_CHESTPLATE);

                tag(zTags.Items.RECYCLE_COPPER_9).add(
                                Items.COPPER_NAUTILUS_ARMOR);

                tag(zTags.Items.RECYCLE_IRON_1).add(
                                Items.SHIELD,
                                Items.IRON_SHOVEL,
                                Items.IRON_SPEAR)
                                .addTags(LibTags.Items.IRON_PLATE,
                                                LibTags.Items.IRON_COIL,
                                                LibTags.Items.IRON_FOIL,
                                                LibTags.Items.IRON_DUST);

                tag(zTags.Items.RECYCLE_IRON_2).add(
                                Items.IRON_HOE,
                                Items.SHEARS,
                                Items.IRON_SWORD,
                                Items.IRON_DOOR,
                                Items.HEAVY_WEIGHTED_PRESSURE_PLATE);

                tag(zTags.Items.RECYCLE_IRON_3).add(
                                Items.BUCKET,
                                Items.IRON_PICKAXE,
                                Items.IRON_AXE);

                tag(zTags.Items.RECYCLE_IRON_4).add(
                                Items.IRON_BOOTS,
                                Items.IRON_TRAPDOOR)
                                .addTags(LibTags.Items.IRON_GEAR);

                tag(zTags.Items.RECYCLE_IRON_5).add(
                                Items.IRON_HELMET);

                tag(zTags.Items.RECYCLE_IRON_6).add(
                                Items.IRON_HORSE_ARMOR);

                tag(zTags.Items.RECYCLE_IRON_7).add(
                                Items.IRON_LEGGINGS);

                tag(zTags.Items.RECYCLE_IRON_8).add(
                                Items.IRON_CHESTPLATE);

                tag(zTags.Items.RECYCLE_IRON_9).add(
                                Items.IRON_NAUTILUS_ARMOR);

                tag(zTags.Items.RECYCLE_GOLD_1).add(
                                Items.GOLDEN_SHOVEL,
                                Items.GOLDEN_SPEAR)
                                .addTags(LibTags.Items.GOLD_PLATE,
                                                LibTags.Items.GOLD_COIL,
                                                LibTags.Items.GOLD_FOIL,
                                                LibTags.Items.GOLD_DUST);

                tag(zTags.Items.RECYCLE_GOLD_2).add(
                                Items.GOLDEN_HOE,
                                Items.GOLDEN_SWORD,
                                Items.LIGHT_WEIGHTED_PRESSURE_PLATE);

                tag(zTags.Items.RECYCLE_GOLD_3).add(
                                Items.GOLDEN_PICKAXE,
                                Items.GOLDEN_AXE);

                tag(zTags.Items.RECYCLE_GOLD_4).add(
                                Items.GOLDEN_BOOTS)
                                .addTags(LibTags.Items.GOLD_GEAR);

                tag(zTags.Items.RECYCLE_GOLD_5).add(
                                Items.GOLDEN_HELMET);

                tag(zTags.Items.RECYCLE_GOLD_6).add(
                                Items.GOLDEN_HORSE_ARMOR);

                tag(zTags.Items.RECYCLE_GOLD_7).add(
                                Items.GOLDEN_LEGGINGS);

                tag(zTags.Items.RECYCLE_GOLD_8).add(
                                Items.GOLDEN_CHESTPLATE);

                tag(zTags.Items.RECYCLE_GOLD_9).add(
                                Items.GOLDEN_NAUTILUS_ARMOR);

                tag(zTags.Items.LEGACY_STONES).add(Items.STONE, Items.DIORITE, Items.ANDESITE, Items.GRANITE);

        }

}