package com.synergy.machines.init.types;

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