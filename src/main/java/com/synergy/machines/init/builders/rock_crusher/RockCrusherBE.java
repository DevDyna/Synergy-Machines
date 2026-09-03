package com.synergy.machines.init.builders.rock_crusher;

import java.util.List;
import javax.annotation.Nullable;

import com.devdyna.cakesticklib.api.aspect.logic.ResourceRestricted;
import com.devdyna.cakesticklib.api.recipe.recipeOutput.ChanceOutput;
import com.devdyna.cakesticklib.api.utils.ArrayUtils;
import com.devdyna.cakesticklib.setup.registry.LibHandlers;
import com.synergy.machines.api.machine.BaseMachineBE;
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
import net.neoforged.neoforge.transfer.item.ItemResource;

public class RockCrusherBE extends BaseMachineBE implements ResourceRestricted.Fluid {

    public static final int OUTPUT_EXTRA_1 = 6;
    public static final int OUTPUT_EXTRA_2 = 7;
    public static final int OUTPUT_EXTRA_3 = 8;
    public static final int OUTPUT_EXTRA_4 = 9;
    public static final int OUTPUT_EXTRA_5 = 10;
    public static final int OUTPUT_EXTRA_6 = 11;
    public static final int OUTPUT_EXTRA_7 = 12;
    public static final int OUTPUT_EXTRA_8 = 13;

    public static final List<Integer> EXTRA_OUTPUT_SLOTS = List.of(OUTPUT_EXTRA_1, OUTPUT_EXTRA_2, OUTPUT_EXTRA_3,
            OUTPUT_EXTRA_4, OUTPUT_EXTRA_5, OUTPUT_EXTRA_6, OUTPUT_EXTRA_7, OUTPUT_EXTRA_8);

    public static final List<Integer> OUTPUT_SLOTS = ArrayUtils.concat(EXTRA_OUTPUT_SLOTS, OUTPUT_SLOT);

    public RockCrusherBE(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    public int getMachineSlots() {
        return 14;
    }

    public RockCrusherBE(BlockPos pos, BlockState blockState) {
        this(zMachines.ROCK_CRUSHER.blockentity().get(), pos, blockState);
    }

    @Override
    @Nullable
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new RockCrusherMenu(i, inventory, this, this.networkData);
    }

    @Override
    public boolean init() {

        if (getFluidStorage() == null)
            return cancel();

        if (getFluidStorage().getAmountAsInt(0) <= 0)
            return cancel();

        enableProgress();

        var r = getRecipes(level, zMachines.ROCK_CRUSHER,
                new ItemFluidInput(getFluidStorage().getResource(0).toStack(getFluidStorage().getAmountAsInt(0)),
                        getInput()));

        // no recipe
        if (r.isEmpty())
            return cancel();

        var recipe = r.get().value();

        if (getFluidStorage().getAmountAsInt(0) < recipe.getFluidInput().amount()) {
            return cancel();
        }

        if (!recipe.getResult().stream()
                .map(a -> recipe.getResult().indexOf(a))
                .allMatch(index -> checkSlot(
                        getStackInSlot(index + 5),
                        recipe.getResult().get(index).item().create())))
            return cancel();

        if (!calculateAndConsumeFE(recipe.getEnergy()))
            return cancel();

        update(true);

        setMaxProgress(calculateMaxProgress(recipe.getTime()));

        return true;

    }

    @Override
    public void result() {

        var recipe = getUnsafeRecipes(level, zMachines.ROCK_CRUSHER,
                new ItemFluidInput(getFluidStorage().getResource(0).toStack(getFluidStorage().getAmountAsInt(0)),
                        getInput()));

        for (ChanceOutput.Item result : recipe.getResult())
            if (result != null)
                if (!result.item().create().isEmpty() && calculateSecondarySuccess(result.chance()))
                    updateResource(ItemResource.of(result.item()), recipe.getResult().indexOf(result) + INPUT_SLOT + 1,
                            result.item().count(), false);

        updateResource(getFluidStorage().getResource(0), 0, calculateMBUsage(recipe.getFluidInput().amount()), true);

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
    public int getTankCapacity() {
        return 10_000;
    }

    @Override
    public int getTanks() {
        return 1;
    }

    @Override
    public List<Integer> getOutputSlotIndex() {
        return OUTPUT_SLOTS;
    }

    @Override
    public List<Integer> getOutputTankIndex() {
        return List.of();
    }
}
