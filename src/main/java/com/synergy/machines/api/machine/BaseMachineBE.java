
package com.synergy.machines.api.machine;

import com.devdyna.cakesticklib.api.aspect.logic.ResourceRestricted;
import com.devdyna.cakesticklib.api.utils.FluidUtils;
import com.synergy.machines.api.machine.templates.BaseUpgradableBE;

import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BaseMachineBE extends BaseUpgradableBE {

    public BaseMachineBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public ContainerData networkData = new ContainerData() {

        @Override
        public int getCount() {
            return ENERGY_DATA_SIZE + PROGRESS_DATA_SIZE
                    + (BaseMachineBE.this instanceof ResourceRestricted.Fluid
                            ? FLUID_DATA_SIZE
                            : 0);
        }

        @Override
        public int get(int i) {
            return switch (i) {
                case BaseMachineMenu.PROGRESS_INDEX -> getProgress();
                case BaseMachineMenu.MAX_PROGRESS_INDEX -> getMaxProgress();
                case BaseMachineMenu.ENERGY_INDEX -> getEnergyStorage().getAmountAsInt();
                case BaseMachineMenu.MAX_ENERGY_INDEX -> getMaxEnergy();
                case BaseMachineMenu.ENERGY_USAGE -> getEnergyUsage();
                case BaseMachineMenu.FLUID_INDEX,
                        BaseMachineMenu.MAX_FLUID_INDEX,
                        BaseMachineMenu.ID_FLUID_INDEX ->
                    (BaseMachineBE.this instanceof ResourceRestricted.Fluid fluid) ? switch (i) {
                        case BaseMachineMenu.FLUID_INDEX -> fluid.getFluidStorage().getAmountAsInt(0);
                        case BaseMachineMenu.MAX_FLUID_INDEX -> fluid.getTankCapacity();
                        case BaseMachineMenu.ID_FLUID_INDEX -> FluidUtils.getFluidToID(fluid.getAsStack(0));
                        default -> 0;
                    } : 0;

                default -> 0;
            };
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
