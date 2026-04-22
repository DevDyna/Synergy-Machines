package com.synergy.machines.api.solar_panel;

import java.util.HashMap;
import java.util.Map;

import com.devdyna.cakesticklib.api.aspect.logic.EnergyProvider;
import com.devdyna.cakesticklib.api.aspect.templates.TickingBE;
import com.devdyna.cakesticklib.setup.registry.zLibrary.zHandlers;
import com.synergy.machines.Common;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;

@SuppressWarnings("null")
public abstract class SolarPanelBE extends TickingBE implements EnergyProvider {

    private final Map<Direction, BlockCapabilityCache<EnergyHandler, Direction>> cache = new HashMap<>();

    public SolarPanelBE(BlockEntityType<?> arg0, BlockPos arg1, BlockState arg2) {
        super(arg0, arg1, arg2);
    }

    @Override
    public void tickServer() {

        level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(BlockStateProperties.ENABLED,
                canReceive() && checkDay() && checkSky() && !level.hasNeighborSignal(getBlockPos())));

        if (getBlockState().getValue(BlockStateProperties.ENABLED)) {
            try (var tx = Transaction.openRoot()) {
                getEnergyStorage().insert(getFERate(), tx);
                tx.commit();
            }
        }

        if (canExtract()) {
            providePowerAdjacent(level, getBlockPos(), cache, getFERate());
        }
    }

    private boolean checkSky() {
        return level.canSeeSkyFromBelowWater(getBlockPos().above())
                && level.getBlockState(getBlockPos().above()).isAir()
                && !Common.SOLAR_PANEL_DISABLE_CHECK_SEE_SKY.get();
    }

    public abstract boolean checkDay();

    @Override
    public ContainerData getContainerData() {
        return new SimpleContainerData(getMaxEnergy());
    }

    @Override
    public EnergyHandler getEnergyStorage() {
        return getData(zHandlers.ENERGY_STORAGE);
    }

    @Override
    public int getMaxEnergy() {
        return Common.SOLAR_PANEL_MAX_FE.get();
    }

    @Override
    public int getFERate() {
        return Common.SOLAR_PANEL_FE_GEN.get();
    }

}