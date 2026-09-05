package com.synergy.machines.init.types;

import static com.synergy.machines.Main.MODULE_ID;

import com.devdyna.cakesticklib.api.RegistryUtils;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
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

                public static final TagKey<Block> MACHINES = RegistryUtils.tagBlock(MODULE_ID, "machines");
                public static final TagKey<Block> SOLAR_PANELS = RegistryUtils.tagBlock(MODULE_ID, "solar_panels");

        }

        public class Items {

                public static void register(IEventBus bus) {

                }

                public static final TagKey<Item> MACHINES = RegistryUtils.tagItem(MODULE_ID, "machines");
                public static final TagKey<Item> SOLAR_PANELS = RegistryUtils.tagItem(MODULE_ID, "solar_panels");

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