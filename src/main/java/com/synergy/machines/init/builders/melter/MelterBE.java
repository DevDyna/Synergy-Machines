package com.synergy.machines.init.builders.melter;

import javax.annotation.Nullable;

import com.devdyna.cakesticklib.setup.registry.zLibrary.zHandlers;
import com.synergy.machines.api.machine.BaseMachineBE;
import com.synergy.machines.api.machine.FluidTankStorage;
import com.synergy.machines.api.recipeinputs.MonoItemInput;
import com.synergy.machines.init.types.zMachines;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.transfer.fluid.FluidStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;

@SuppressWarnings("null")
public class MelterBE extends BaseMachineBE implements FluidTankStorage {

    public MelterBE(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    public int getMachineSlots() {
        return MAX_UPGRADE_SLOTS + 1;
    }

    public MelterBE(BlockPos pos, BlockState blockState) {
        this(zMachines.ELECTRIC_MELTER.blockentity().get(), pos, blockState);
    }

    @Override
    @Nullable
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new MelterMenu(i, inventory, this, this.networkData);
    }

    @Override
    public boolean initProgress() {

        // if (getFluidStorage() == null)
        //     return cancel();

        if (getInput().isEmpty())
            return cancel();

        progress_cancel = false;

        var r = getRecipes(level, zMachines.ELECTRIC_MELTER, new MonoItemInput(getInput()));

        // no recipe
        if (r.isEmpty())
            return cancel();

        var recipe = r.get().value();

        if (!checkTank(getFluidStorage().getResource(0).toStack(getFluidStorage().getAmountAsInt(0)),
                recipe.getFluidOutput().copy(), getTankCapacity())) {
            return cancel();
        }

        if (!calculateAndConsumeFE(recipe.getEnergy()))
            return cancel();

        update(true);

        this.maxProgress = calculateMaxProgress(recipe.getTime());

        return true;

    }

    @Override
    public void endProgress() {

        var recipe = getUnsafeRecipes(level, zMachines.ELECTRIC_MELTER, new MonoItemInput(getInput()));

        if (!recipe.getFluidOutput().copy().isEmpty()) {
            // getFluidStorage().drain(recipe.getFluidInput().amount(),
            // FluidAction.EXECUTE);

            try (var tx = Transaction.openRoot()) {
                getFluidStorage().insert(getFluidStorage().getResource(0), recipe.getFluidInput().amount(), tx);
                tx.commit();
            }
        }

        getInput().shrink(recipe.getInputItem().count());
    }

    @Override
    public ContainerData getContainerData() {
        return networkData;
    }

    @Override
    public FluidStacksResourceHandler getFluidStorage() {
        return getData(zHandlers.FLUID_STORAGE);
    }

    @Override
    public int getTanks() {
        return 1;
    }

    @Override
    public int getTankCapacity() {
        return 10_000;
    }

    @Override
    public FluidTankType getTankIOType() {
        return FluidTankType.OUTPUT;
    }
}
