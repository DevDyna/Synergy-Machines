package com.synergy.machines.api.machine.templates;

import java.util.List;

import com.devdyna.cakesticklib.api.aspect.logic.*;
import com.devdyna.cakesticklib.setup.registry.LibHandlers;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;

public abstract class BaseEnergyBE extends BlockEntity implements EnergyBlock {

    public static final String NBT_ENERGY = "energy";

    private int energyUsed = 0;

    public static final int ENERGY_DATA_SIZE = 3;

    protected List<Integer> ENERGY_DATA = List.of(
            getEnergyStorage().getAmountAsInt(),
            getMaxEnergy(),
            getEnergyUsage()

    );

    public BaseEnergyBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public int getEnergyUsage() {
        return energyUsed;
    }

    public void setEnergyUsed(int v) {
        this.energyUsed = v;
    }

    @Override
    public EnergyHandler getEnergyStorage() {
        return getData(LibHandlers.ENERGY_STORAGE);
    }

    @Override
    public int getMaxEnergy() {
        return 16_000;
    }

    @Override
    protected void saveAdditional(ValueOutput output) {

        if (getEnergyStorage() != null)
            output.putInt(NBT_ENERGY, getEnergyStorage().getAmountAsInt());

        super.saveAdditional(output);
    }

    @Override
    protected void loadAdditional(ValueInput input) {

        input.getInt(NBT_ENERGY).ifPresent(fe -> {

            if (getEnergyStorage() != null)
                return;

            try (var tx = Transaction.openRoot()) {

                getEnergyStorage().extract(
                        getEnergyStorage().getAmountAsInt(),
                        tx);

                getEnergyStorage().insert(
                        Math.max(0, Math.min(fe, getEnergyStorage().getCapacityAsInt())),
                        tx);

                tx.commit();
            }
        });

        super.loadAdditional(input);
    }

}
