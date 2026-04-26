package com.synergy.machines.init.builders.extractor;

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
public class ExtractorBE extends BaseMachineBE implements FluidTankStorage {

    public ExtractorBE(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    public int getMachineSlots() {
        return 6;
    }

    public ExtractorBE(BlockPos pos, BlockState blockState) {
        this(zMachines.EXTRACTOR.blockentity().get(), pos, blockState);
    }

    @Override
    @Nullable
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new ExtractorMenu(i, inventory, this, this.networkData);
    }

    @Override
    public boolean initProgress() {

        // if (getFluidStorage() == null)
        //     return cancel();

        if (getInput().isEmpty())
            return cancel();

        progress_cancel = false;

        var r = getRecipes(level, zMachines.EXTRACTOR, new MonoItemInput(getInput()));

        // no recipe
        if (r.isEmpty())
            return cancel();

        var recipe = r.get().value();

        if (recipe.getSecondaryOutputItem() != null)
            if (!checkSlot(getOutput(), recipe.getSecondaryOutputItem().item().create()))
                return cancel();

        if (!(checkTank(getFluidStorage().getResource(0).toStack(getFluidStorage().getAmountAsInt(0)),
                recipe.getFluidOutput().create(), getTankCapacity())))
            return cancel();

        if (!calculateAndConsumeFE(recipe.getEnergy()))
            return cancel();

        update(true);

        this.maxProgress = calculateMaxProgress(recipe.getTime());

        return true;

    }

    @Override
    public void endProgress() {

        var recipe = getUnsafeRecipes(level, zMachines.EXTRACTOR, new MonoItemInput(getInput()));

        if (recipe.getSecondaryOutputItem() != null)
            if (!recipe.getSecondaryOutputItem().item().create().isEmpty()
                    && calculateSecondarySuccess(recipe.getSecondaryOutputItem().chance()))
                updateOutputSlot(getOutput(), recipe.getSecondaryOutputItem().item().create(), OUTPUT_SLOT);

        if (recipe.getFluidOutput() != null) {

            try (var tx = Transaction.openRoot()) {
                getFluidStorage().insert(getFluidStorage().getResource(0), recipe.getFluidInput().amount(), tx);
                tx.commit();
            }

            // if (getFluidStorage().isEmpty())
            // getFluidStorage().setFluid(recipe.getFluidOutput().copy());
            // else if (FluidStack.isSameFluidSameComponents(recipe.getFluidOutput().copy(),
            // getFluidStorage().getFluid()))
            // getFluidStorage().fill(recipe.getFluidOutput().copy(), FluidAction.EXECUTE);
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
