package com.synergy.machines.init.builders.macerator;

import java.util.List;
import javax.annotation.Nullable;

import com.devdyna.cakesticklib.api.recipe.recipeInput.ItemInput;
import com.synergy.machines.api.machine.BaseMachineBE;
import com.synergy.machines.init.types.zMachines;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.transfer.item.ItemResource;

public class MaceratorBE extends BaseMachineBE {

    public static final int INPUT_SLOT = 4;
    public static final int OUTPUT_SLOT = 5;

    public static final int SECONDARY_SLOT = 6;

    public MaceratorBE(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    public int getMachineSlots() {
        return 7;
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
        return List.of(OUTPUT_SLOT, SECONDARY_SLOT);
    }

    public MaceratorBE(BlockPos pos, BlockState blockState) {
        this(zMachines.MACERATOR.blockentity().get(), pos, blockState);
    }

    @Override
    @Nullable
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new MaceratorMenu(i, inventory, this, getContainerData());
    }

    @Override
    public boolean init() {

        if (getInput().isEmpty())
            return cancel();

        enableProgress();

        var r = getRecipes(level, zMachines.MACERATOR, new ItemInput.simple(getInput()));

        // no recipe
        if (r.isEmpty())
            return cancel();

        var recipe = r.get().value();

        if (!checkSlot(getOutput(), recipe.getOutput().create()))
            return cancel();

        if (recipe.getSecondaryOutput() != null)
            if (!checkSlot(getSecondarySlot(), recipe.getSecondaryOutput().item().create()))
                return cancel();

        if (!calculateAndConsumeFE(recipe.getEnergy()))
            return cancel();

        update(true);

        setMaxProgress(calculateMaxProgress(recipe.getTime()));

        return true;

    }

    @Override
    public void result() {

        var recipe = getUnsafeRecipes(level, zMachines.MACERATOR, new ItemInput.simple(getInput()));

        updateResource(ItemResource.of(recipe.getOutput()), OUTPUT_SLOT, recipe.getOutput().count(), false);

        if (recipe.getSecondaryOutput() != null)
            if (!recipe.getSecondaryOutput().item().create().isEmpty()
                    && calculateSecondarySuccess(recipe.getSecondaryOutput().chance()))
                updateResource(ItemResource.of(recipe.getSecondaryOutput().item()), SECONDARY_SLOT,
                        recipe.getSecondaryOutput().item().count(), false);

        updateResource(getItemStorage().getResource(INPUT_SLOT), INPUT_SLOT, recipe.getInput().count(), true);
    }

    public ItemStack getSecondarySlot() {
        return getStackInSlot(SECONDARY_SLOT);
    }
}
