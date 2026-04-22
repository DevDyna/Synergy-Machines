package com.synergy.machines.init.builders.solar_panel.night;

import com.synergy.machines.api.solar_panel.SolarPanelBE;
import com.synergy.machines.init.types.zBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class NightSolarPanelBE extends SolarPanelBE{

    public NightSolarPanelBE( BlockPos arg1, BlockState arg2) {
        super(zBlockEntities.SOLAR_PANEL_NIGHT.get(), arg1, arg2);
    }

    @Override
    public boolean checkDay() {
        return level.isDarkOutside();
    }
    
}
