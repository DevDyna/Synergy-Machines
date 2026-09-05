package com.synergy.machines.api;

import java.util.List;
import java.util.function.Consumer;

import com.devdyna.cakesticklib.api.datagen.AdvancementsUtils;
import com.devdyna.cakesticklib.api.utils.x;
import com.mojang.datafixers.util.Pair;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.ItemLike;

//TODO API : move to api and unify with RecipeGenerator when possible
public interface AdvancementGenerator {

        // abstract HolderGetter<Item> getItems();

        abstract String getModName();

        default Criterion<InventoryChangeTrigger.TriggerInstance> has(ItemLike item) {
                return InventoryChangeTrigger.TriggerInstance.hasItems(item);
        }

        default String getCraftItem(String i) {
                return "craft_" + i;
        }

        default String getCraftItem(ItemLike i) {
                return getCraftItem(x.name(i));
        }

        default AdvancementRequirements getRequireCraft(String i) {
                return AdvancementRequirements.allOf(List.of(getCraftItem(i)));
        }

        default AdvancementRequirements getRequireCraft(ItemLike i) {
                return AdvancementRequirements.allOf(List.of(getCraftItem(i)));
        }

        default AdvancementHolder simpleTask(AdvancementHolder parent, ItemLike item, String path,
                        Consumer<AdvancementHolder> c) {
                return AdvancementsUtils.getExistingParent(parent, item,
                                getModName(), x.name(item), AdvancementType.TASK, true, true, false)
                                .addCriterion(getCraftItem(item), has(item))
                                .requirements(getRequireCraft(item))
                                .save(c, path + x.name(item));
        }

        default AdvancementHolder simpleTask(Pair<AdvancementHolder,AdvancementHolder> parent, ItemLike item, String path,
                        Consumer<AdvancementHolder> c) {
                return AdvancementsUtils.getExistingParent(parent.getFirst(), item,
                                getModName(), x.name(item), AdvancementType.TASK, true, true, false)
                                .addCriterion(getCraftItem(item), has(item))
                                .requirements(getRequireCraft(item))
                                .save(c, path + x.name(item));
        }

        default AdvancementHolder simpleTask(String parent, ItemLike item, String path,
                        Consumer<AdvancementHolder> c) {
                return AdvancementsUtils.getExistingParent(parent, item,
                                getModName(), x.name(item), AdvancementType.TASK, true, true, false)
                                .addCriterion(getCraftItem(item), has(item))
                                .requirements(getRequireCraft(item))
                                .save(c, path + x.name(item));
        }

        default AdvancementHolder simpleRoot(ItemLike item, String path, Identifier bg,
                        String name,
                        Consumer<AdvancementHolder> c) {
                return Advancement.Builder.advancement().display(item,
                                Component.translatable(getModName() + ".advancement.root." + name),
                                Component.translatable(getModName() + ".advancement.root." + name + ".desc"),
                                bg,
                                AdvancementType.TASK, false, false, false)
                                .addCriterion(getCraftItem(item), has(item))
                                .requirements(getRequireCraft(item))
                                .save(c, path + "root");
        }

        /**
         * return {@code <Root,Task>}
         * <br/>
         * <br/>
         * Use the {@code .getSecond()} (Task) to reference on same tab<br/>
         * <br/>
         * Use the {@code .getFirst()} (Root) to reference to the new tab
         */
        default Pair<AdvancementHolder, AdvancementHolder> simpleDependRoot(String require,
                        ItemLike item, String path, Identifier bg, String name, Consumer<AdvancementHolder> c) {
                return Pair.of(simpleRoot(item, path, bg, name, c), simpleTask(require, item, path, c));
        }

        /**
         * return {@code <Root,Task>}
         * <br/>
         * <br/>
         * Use the {@code Task} to reference on same tab<br/>
         * <br/>
         * Use the {@code Root} to reference to the new tab
         */
        default Pair<AdvancementHolder, AdvancementHolder> simpleDependRoot(AdvancementHolder require,
                        ItemLike item, String path, Identifier bg, String name, Consumer<AdvancementHolder> c) {
                return Pair.of(simpleRoot(item, path, bg, name, c), simpleTask(require, item, path, c));
        }

}
