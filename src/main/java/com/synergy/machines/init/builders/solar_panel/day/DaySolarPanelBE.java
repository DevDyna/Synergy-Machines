package com.synergy.machines.init.builders.solar_panel.day;

import com.synergy.machines.api.solar_panel.SolarPanelBE;
import com.synergy.machines.init.types.zBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class DaySolarPanelBE extends SolarPanelBE{

    public DaySolarPanelBE( BlockPos arg1, BlockState arg2) {
        super(zBlockEntities.SOLAR_PANEL_DAY.get(), arg1, arg2);
    }

    @Override
    public boolean checkDay() {
        return level.isBrightOutside();
    }
    
}
