package com.synergy.machines.init.builders.caster;

import java.util.List;

import javax.annotation.Nullable;

import com.devdyna.cakesticklib.api.aspect.logic.ResourceRestricted;
import com.devdyna.cakesticklib.setup.registry.LibHandlers;
import com.synergy.machines.api.machine.BaseMachineBE;
import com.synergy.machines.api.recipeinputs.ItemFluidInput;
import com.synergy.machines.init.types.zMachines;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.transfer.fluid.FluidStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;

public class CasterBE extends BaseMachineBE implements ResourceRestricted.Fluid {

    public static final int INPUT_SLOT = 4;
    public static final int OUTPUT_SLOT = 5;

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

    public ItemStack getInput() {
        return getStackInSlot(INPUT_SLOT);
    }

    public ItemStack getOutput() {
        return getStackInSlot(OUTPUT_SLOT);
    }

    @Override
    public List<Integer> getInputSlotIndex() {
        return List.of(INPUT_SLOT);
    }

    @Override
    public List<Integer> getOutputSlotIndex() {
        return List.of(OUTPUT_SLOT);
    }

    @Override
    @Nullable
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new CasterMenu(i, inventory, this, getContainerData());
    }

    @Override
    public boolean init() {

        if (getFluidStorage() == null)
            return cancel();

        if (getFluidStorage().getAmountAsInt(0) <= 0)
            return cancel();

        enableProgress();

        var r = getRecipes(level, zMachines.CASTING_FACTORY,
                new ItemFluidInput(getFluidStorage().getResource(0).toStack(getFluidStorage().getAmountAsInt(0)),
                        getInput()));

        // no recipe
        if (r.isEmpty())
            return cancel();

        var recipe = r.get().value();

        if (getFluidStorage().getAmountAsInt(0) < recipe.getFluid().amount()) {
            return cancel();
        }

        if (!(checkSlot(getOutput(), recipe.getOutput().create()))) {
            return cancel();
        }

        if (!calculateAndConsumeFE(recipe.getEnergy()))
            return cancel();

        update(true);

        setMaxProgress(calculateMaxProgress(recipe.getTime()));

        return true;

    }

    @Override
    public void result() {

        var recipe = getUnsafeRecipes(level, zMachines.CASTING_FACTORY,
                new ItemFluidInput(getFluidStorage().getResource(0).toStack(getFluidStorage().getAmountAsInt(0)),
                        getInput()));

        updateResource(ItemResource.of(recipe.getOutput()), OUTPUT_SLOT, recipe.getOutput().count(), false);

        updateResource(getFluidStorage().getResource(0), 0, recipe.getFluid().amount(), true);

        if (!getInput().isEmpty() && recipe.consumeCatalyst())
            updateResource(getItemStorage().getResource(INPUT_SLOT), INPUT_SLOT,
                    recipe.getMold().count(), true);
    }

    @Override
    public FluidStacksResourceHandler getFluidStorage() {
        return getData(LibHandlers.FLUID_STORAGE);
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
    public List<Integer> getOutputTankIndex() {
        return List.of();
    }
}
