package com.synergy.machines.init.types;

import static com.synergy.machines.Main.MODULE_ID;

import java.util.function.Function;

import com.synergy.machines.init.builders.solar_panel.day.DaySolarPanelBlock;
import com.synergy.machines.init.builders.solar_panel.night.NightSolarPanelBlock;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
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

    public static DeferredHolder<Block,Block> SOLAR_PANEL_DAY = registerItemBlock("solar_panel_day", p -> new DaySolarPanelBlock(p));
    public static DeferredHolder<Block,Block> SOLAR_PANEL_NIGHT = registerItemBlock("solar_panel_night", p -> new NightSolarPanelBlock(p));

    public static DeferredHolder<Block, Block> registerItemBlock(String blockname,
            Function<BlockBehaviour.Properties, ? extends Block> sup) {
        DeferredHolder<Block, Block> block = zBlockItem.registerBlock(blockname, sup);
        zItems.zBlockItem.registerSimpleBlockItem(block);
        return block;
    }

}
