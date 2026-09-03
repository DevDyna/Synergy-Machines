package com.synergy.machines.init.builders.melter;

import java.util.List;

import javax.annotation.Nullable;

import com.devdyna.cakesticklib.api.aspect.logic.ResourceRestricted;
import com.devdyna.cakesticklib.api.recipe.recipeInput.ItemInput;
import com.devdyna.cakesticklib.setup.registry.LibHandlers;
import com.synergy.machines.api.machine.BaseMachineBE;
import com.synergy.machines.init.types.zMachines;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.fluid.FluidStacksResourceHandler;

public class MelterBE extends BaseMachineBE implements ResourceRestricted.Fluid {

    public MelterBE(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    public int getMachineSlots() {
        return 5;
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
    public boolean init() {

        // if (getFluidStorage() == null)
        // return cancel();

        if (getInput().isEmpty())
            return cancel();

        enableProgress();

        var r = getRecipes(level, zMachines.ELECTRIC_MELTER, new ItemInput.simple(getInput()));

        // no recipe
        if (r.isEmpty())
            return cancel();

        var recipe = r.get().value();

        if (!checkTank(getAsStack(0),
                recipe.getFluidOutput().create(), getTankCapacity())) {
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

        var recipe = getUnsafeRecipes(level, zMachines.ELECTRIC_MELTER, new ItemInput.simple(getInput()));

        if (!recipe.getFluidOutput().create().isEmpty())
            updateResource(FluidResource.of(recipe.getFluidOutput().create()), 0, recipe.getFluidOutput().amount(),
                    false);

        updateResource(getItemStorage().getResource(INPUT_SLOT), INPUT_SLOT, recipe.getInputItem().count(), true);

        
    }

    @Override
    public ContainerData getContainerData() {
        return networkData;
    }

    @Override
    public FluidStacksResourceHandler getFluidStorage() {
        return getData(LibHandlers.FLUID_STORAGE);
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
    public List<Integer> getInputTankIndex() {
        return List.of();
    }

}
