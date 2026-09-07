
package com.synergy.machines.api.machine;

import com.devdyna.cakesticklib.api.aspect.logic.ResourceRestricted;
import com.devdyna.cakesticklib.api.utils.FluidUtils;
import com.synergy.machines.api.machine.templates.BaseUpgradableBE;

import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BaseMachineBE extends BaseUpgradableBE {

    protected static final int BASE_MACHINE_INDEX = BaseMachineBE.ENERGY_DATA_SIZE + BaseMachineBE.PROGRESS_DATA_SIZE;

    public BaseMachineBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public ContainerData networkData = new ContainerData() {

        @Override
        public int getCount() {
            return ENERGY_DATA_SIZE + PROGRESS_DATA_SIZE
                    + (BaseMachineBE.this instanceof ResourceRestricted.Fluid fluid
                            ? fluid.getTanks() * 3
                            : 0);

        }

        @Override
        public int get(int i) {

            if (i < BASE_MACHINE_INDEX)
                return switch (i) {
                    case BaseMachineMenu.PROGRESS_INDEX -> getProgress();
                    case BaseMachineMenu.MAX_PROGRESS_INDEX -> getMaxProgress();
                    case BaseMachineMenu.STORED_ENERGY_INDEX -> getEnergyStorage().getAmountAsInt();
                    case BaseMachineMenu.MAX_ENERGY_INDEX -> getMaxEnergy();
                    case BaseMachineMenu.RECIPE_ENERGY_USAGE -> getEnergyUsage();
                    default -> 0;
                };

            if (BaseMachineBE.this instanceof ResourceRestricted.Fluid fluid) {

                var fluidIndex = i - BASE_MACHINE_INDEX;

                return switch (fluidIndex % 3) {
                    case 0 -> fluid.getFluidStorage().getAmountAsInt(fluidIndex / 3);
                    case 1 -> fluid.getTankCapacity();
                    case 2 -> FluidUtils.getFluidToID(fluid.getAsStack(fluidIndex / 3));
                    default -> 0;
                };
            }

            return 0;
        }

        @Override
        public void set(int index, int value) {
        }
    };

    @Override
    public ContainerData getContainerData() {
        return networkData;
    }

    @Override
    public void update(boolean v) {

        if (level == null || isRemoved())
            return;

        if (v != getBlockState().getValue(BaseMachineBlock.ENABLED))
            level.setBlockAndUpdate(getBlockPos(),
                    getBlockState().setValue(BaseMachineBlock.ENABLED, v));
    }

}
