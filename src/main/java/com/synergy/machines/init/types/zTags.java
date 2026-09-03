package com.synergy.machines.init.types;

import static com.synergy.machines.Main.MODULE_ID;

import com.devdyna.cakesticklib.api.RegistryUtils;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;

public class zTags {

        public static void register(IEventBus bus) {
                zTags.Blocks.register(bus);
                zTags.Items.register(bus);
                zTags.Entities.register(bus);
                zTags.Biomes.register(bus);
        }

        public class Blocks {

                public static void register(IEventBus bus) {
                }

        }

        public class Items {

                public static void register(IEventBus bus) {

                }

                // TODO API : move to api and remove from VintageTech

                public static final TagKey<Item> RECYCLE_COPPER_1 = RegistryUtils.tagItem(MODULE_ID,
                                "recipe_recycle/copper/1");
                public static final TagKey<Item> RECYCLE_COPPER_2 = RegistryUtils.tagItem(MODULE_ID,
                                "recipe_recycle/copper/2");
                public static final TagKey<Item> RECYCLE_COPPER_3 = RegistryUtils.tagItem(MODULE_ID,
                                "recipe_recycle/copper/3");
                public static final TagKey<Item> RECYCLE_COPPER_4 = RegistryUtils.tagItem(MODULE_ID,
                                "recipe_recycle/copper/4");
                public static final TagKey<Item> RECYCLE_COPPER_5 = RegistryUtils.tagItem(MODULE_ID,
                                "recipe_recycle/copper/5");
                public static final TagKey<Item> RECYCLE_COPPER_6 = RegistryUtils.tagItem(MODULE_ID,
                                "recipe_recycle/copper/6");
                public static final TagKey<Item> RECYCLE_COPPER_7 = RegistryUtils.tagItem(MODULE_ID,
                                "recipe_recycle/copper/7");
                public static final TagKey<Item> RECYCLE_COPPER_8 = RegistryUtils.tagItem(MODULE_ID,
                                "recipe_recycle/copper/8");
                public static final TagKey<Item> RECYCLE_COPPER_9 = RegistryUtils.tagItem(MODULE_ID,
                                "recipe_recycle/copper/9");

                public static final TagKey<Item> RECYCLE_IRON_1 = RegistryUtils.tagItem(MODULE_ID,
                                "recipe_recycle/iron/1");
                public static final TagKey<Item> RECYCLE_IRON_2 = RegistryUtils.tagItem(MODULE_ID,
                                "recipe_recycle/iron/2");
                public static final TagKey<Item> RECYCLE_IRON_3 = RegistryUtils.tagItem(MODULE_ID,
                                "recipe_recycle/iron/3");
                public static final TagKey<Item> RECYCLE_IRON_4 = RegistryUtils.tagItem(MODULE_ID,
                                "recipe_recycle/iron/4");
                public static final TagKey<Item> RECYCLE_IRON_5 = RegistryUtils.tagItem(MODULE_ID,
                                "recipe_recycle/iron/5");
                public static final TagKey<Item> RECYCLE_IRON_6 = RegistryUtils.tagItem(MODULE_ID,
                                "recipe_recycle/iron/6");
                public static final TagKey<Item> RECYCLE_IRON_7 = RegistryUtils.tagItem(MODULE_ID,
                                "recipe_recycle/iron/7");
                public static final TagKey<Item> RECYCLE_IRON_8 = RegistryUtils.tagItem(MODULE_ID,
                                "recipe_recycle/iron/8");
                public static final TagKey<Item> RECYCLE_IRON_9 = RegistryUtils.tagItem(MODULE_ID,
                                "recipe_recycle/iron/9");

                public static final TagKey<Item> RECYCLE_GOLD_1 = RegistryUtils.tagItem(MODULE_ID,
                                "recipe_recycle/gold/1");
                public static final TagKey<Item> RECYCLE_GOLD_2 = RegistryUtils.tagItem(MODULE_ID,
                                "recipe_recycle/gold/2");
                public static final TagKey<Item> RECYCLE_GOLD_3 = RegistryUtils.tagItem(MODULE_ID,
                                "recipe_recycle/gold/3");
                public static final TagKey<Item> RECYCLE_GOLD_4 = RegistryUtils.tagItem(MODULE_ID,
                                "recipe_recycle/gold/4");
                public static final TagKey<Item> RECYCLE_GOLD_5 = RegistryUtils.tagItem(MODULE_ID,
                                "recipe_recycle/gold/5");
                public static final TagKey<Item> RECYCLE_GOLD_6 = RegistryUtils.tagItem(MODULE_ID,
                                "recipe_recycle/gold/6");
                public static final TagKey<Item> RECYCLE_GOLD_7 = RegistryUtils.tagItem(MODULE_ID,
                                "recipe_recycle/gold/7");
                public static final TagKey<Item> RECYCLE_GOLD_8 = RegistryUtils.tagItem(MODULE_ID,
                                "recipe_recycle/gold/8");
                public static final TagKey<Item> RECYCLE_GOLD_9 = RegistryUtils.tagItem(MODULE_ID,
                                "recipe_recycle/gold/9");
        }

        public class Biomes {

                public static void register(IEventBus bus) {
                }

        }

        public class Entities {

                public static void register(IEventBus bus) {

                }

        }
}