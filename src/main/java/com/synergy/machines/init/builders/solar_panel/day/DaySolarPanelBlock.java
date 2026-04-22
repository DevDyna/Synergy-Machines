package com.synergy.machines.init.builders.solar_panel.day;

import org.jspecify.annotations.Nullable;

import com.synergy.machines.api.solar_panel.SolarPanelBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DaySolarPanelBlock extends SolarPanelBlock{

    public DaySolarPanelBlock(Properties p) {
        super(p);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos arg0, BlockState arg1) {
        return new DaySolarPanelBE(arg0, arg1);
    }
    
}
