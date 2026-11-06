package com.synergy.machines.init.builders.extractor;

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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.fluid.FluidStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;

public class ExtractorBE extends BaseMachineBE implements ResourceRestricted.Fluid {

    public static final int INPUT_SLOT = 4;
    public static final int OUTPUT_SLOT = 5;

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
        return new ExtractorMenu(i, inventory, this, getContainerData());
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
    public boolean init() {

        // if (getFluidStorage() == null)
        // return cancel();

        if (getInput().isEmpty())
            return cancel();

        enableProgress();

        var r = getRecipes(level, zMachines.EXTRACTOR, new ItemInput.simple(getInput()));

        // no recipe
        if (r.isEmpty())
            return cancel();

        var recipe = r.get().value();

        if (recipe.getOutput() != null)
            if (!checkSlot(getOutput(), recipe.getOutput().item().create()))
                return cancel();

        if (recipe.getFluid() != null)
            if (!checkTank(getFluidStorage().getResource(0).toStack(getFluidStorage().getAmountAsInt(0)),
                    recipe.getFluid().create(), getTankCapacity()))
                return cancel();

        if (!calculateAndConsumeFE(recipe.getEnergy()))
            return cancel();

        update(true);

        setMaxProgress(calculateMaxProgress(recipe.getTime()));

        return true;

    }

    @Override
    public void result() {

        var recipe = getUnsafeRecipes(level, zMachines.EXTRACTOR, new ItemInput.simple(getInput()));

        if (recipe.getOutput() != null)
            if (!recipe.getOutput().item().create().isEmpty()
                    && calculateSecondarySuccess(recipe.getOutput().chance()))
                updateResource(ItemResource.of(recipe.getOutput().item()), OUTPUT_SLOT,
                        recipe.getOutput().item().count(), false);

        if (recipe.getFluid() != null)
            updateResource(FluidResource.of(recipe.getFluid().create()), 0, recipe.getFluid().amount(), false);

        updateResource(getItemStorage().getResource(INPUT_SLOT), INPUT_SLOT,
                recipe.getInput().count(), true);

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
