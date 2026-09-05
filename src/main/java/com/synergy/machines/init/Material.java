package com.synergy.machines.init;

import static com.synergy.machines.Main.MODULE_ID;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import com.devdyna.cakesticklib.api.utils.x;
import com.synergy.machines.api.MachineType;
import com.synergy.machines.init.types.*;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;

public class Material {
        public static void register(IEventBus bus) {
                zItems.register(bus);
                zBlocks.register(bus);
                zBlockEntities.register(bus);
                zRecipeTypes.register(bus);
                zContainer.register(bus);
                zMachines.register(bus);
                zTags.register(bus);
                zFluids.register(bus);
        }

        
        public static List<MachineType<?, ?, ?, ?>> getAllMachineTypes() {

                List<MachineType<?, ?, ?, ?>> types = new ArrayList<>();
                Field[] fields = zMachines.class.getDeclaredFields();
                for (Field field : fields) {
                        if (field.getType() == MachineType.class) {
                                try {
                                        types.add((MachineType<?, ?, ?, ?>) field.get(null));
                                } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                }
                        }
                }
                return types;
        }

        
        public static Block[] getBlocks() {
                return getAllMachineTypes().stream().map(MachineType::block).map(DeferredHolder::get)
                                .toArray(Block[]::new);
        }


          /**
         * Create a simple blockitem
         */
        public static DeferredHolder<Block, Block> registerItemBlock(String id,
                        Function<Properties, ? extends Block> p) {
                DeferredHolder<Block, Block> block = zBlocks.zBlockItem.registerBlock(id, p);
                zItems.zBlockItem.registerSimpleBlockItem(block);
                return block;
        }

        /**
         * Create a blockitem from {@code ResourceKey<Block>}
         * <br/>
         * <br/>
         * Useful when you need to use things like {@code Properties.ofFullCopy(<?>)}
         */
        public static DeferredHolder<Block, Block> registerItemBlock(String id,
                        BiFunction<Properties, ResourceKey<Block>, ? extends Block> p) {
                return registerItemBlock(id,
                                pr -> p.apply(pr, ResourceKey.create(Registries.BLOCK, x.rl(MODULE_ID, id))));
        }

}
