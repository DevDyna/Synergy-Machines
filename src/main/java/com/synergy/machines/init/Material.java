package com.synergy.machines.init;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.synergy.machines.api.MachineType;
import com.synergy.machines.init.types.*;

import net.minecraft.world.level.block.Block;
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

        public static Block[] getBlocks(){
                return getAllMachineTypes().stream().map(MachineType::block).map(DeferredHolder::get).toArray(Block[]::new);
        }

}
