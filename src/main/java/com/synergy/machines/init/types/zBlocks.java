package com.synergy.machines.init.types;

import static com.synergy.machines.Main.MODULE_ID;

import com.synergy.machines.init.Material;
import com.synergy.machines.init.builders.MachineFrame;
import com.synergy.machines.init.builders.solar_panel.day.DaySolarPanelBlock;
import com.synergy.machines.init.builders.solar_panel.night.NightSolarPanelBlock;

import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class zBlocks {

    public static void register(IEventBus bus) {
        zBlock.register(bus);
        zBlockItem.register(bus);
        zBlockFluids.register(bus);

    }

    public static final DeferredRegister.Blocks zBlock = DeferredRegister.createBlocks(MODULE_ID);
    public static final DeferredRegister.Blocks zBlockItem = DeferredRegister.createBlocks(MODULE_ID);
    public static final DeferredRegister.Blocks zBlockFluids = DeferredRegister.createBlocks(MODULE_ID);

    public static final DeferredHolder<Block, Block> SOLAR_PANEL = Material.registerItemBlock("solar_panel",
            p -> new DaySolarPanelBlock(p));
    public static final DeferredHolder<Block, Block> LUNAR_PANEL = Material.registerItemBlock("lunar_panel",
            p -> new NightSolarPanelBlock(p));

    public static final DeferredHolder<Block, Block> MACHINE_FRAME = Material.registerItemBlock("machine_frame",
            p -> new MachineFrame(p));

}
