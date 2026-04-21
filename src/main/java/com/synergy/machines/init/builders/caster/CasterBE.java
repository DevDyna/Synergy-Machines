package com.synergy.machines.init.builders.caster;

import javax.annotation.Nullable;

import com.devdyna.cakesticklib.setup.registry.zLibrary.zHandlers;
import com.synergy.machines.api.machine.BaseMachineBE;
import com.synergy.machines.api.machine.FluidTankStorage;
import com.synergy.machines.api.recipeinputs.ItemFluidInput;
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
public class CasterBE extends BaseMachineBE implements FluidTankStorage {

    public CasterBE(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    public int getMachineSlots() {
        return 6;
    }

    public CasterBE(BlockPos pos, BlockState blockState) {
        this(zMachines.CASTING_FACTORY.blockentity().get(), pos, blockState);
    }

    @Override
    @Nullable
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new CasterMenu(i, inventory, this, this.networkData);
    }

    @Override
    public boolean initProgress() {

        if (getFluidStorage() == null)
            return cancel();

        if (getFluidStorage().getAmountAsInt(0) <= 0)
            return cancel();

        progress_cancel = false;

        var r = getRecipes(level, zMachines.CASTING_FACTORY,
                new ItemFluidInput(getFluidStorage().getResource(0).toStack(getFluidStorage().getAmountAsInt(0)),
                        getInput()));

        // no recipe
        if (r.isEmpty())
            return cancel();

        var recipe = r.get().value();

        if (getFluidStorage().getAmountAsInt(0) < recipe.getFluidInput().amount()) {
            return cancel();
        }

        if (!(checkSlot(getOutput(), recipe.getOutputItem().create()))) {
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

        var recipe = getUnsafeRecipes(level, zMachines.CASTING_FACTORY,
                new ItemFluidInput(getFluidStorage().getResource(0).toStack(getFluidStorage().getAmountAsInt(0)),
                        getInput()));

        updateOutputSlot(getOutput(), recipe.getOutputItem().create(), OUTPUT_SLOT);

        // getFluidStorage().drain(recipe.getFluidInput().amount(),
        // FluidAction.EXECUTE);

        try (var tx = Transaction.openRoot()) {
            getFluidStorage().extract(getFluidStorage().getResource(0), recipe.getFluidInput().amount(), tx);
            tx.commit();
        }

        if (!getInput().isEmpty() && recipe.consumeCatalyst())
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
    public int getTankCapacity() {
        return 10_000;
    }

    @Override
    public int getTanks() {
        return 1;
    }

    @Override
    public FluidTankType getTankIOType() {
        return FluidTankType.INPUT;
    }
}
