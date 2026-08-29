package com.synergy.machines.init.builders.alloy_smelter;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import com.synergy.machines.api.machine.BaseMachineBE;
import com.synergy.machines.api.machine.ExtraMachineSlots;
import com.synergy.machines.api.recipeinputs.BiItemInput;
import com.synergy.machines.init.builders.alloy_smelter.recipe.AlloySmelterRecipeType;
import com.synergy.machines.init.types.zMachines;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.transfer.item.ItemResource;

public class AlloySmelterBE extends BaseMachineBE implements ExtraMachineSlots {

    public static final int SECONDARY_INPUT = 6;

    public AlloySmelterBE(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    public int getMachineSlots() {
        return 7;
    }

    @Override
    public List<Integer> getInputSlotIndex() {
        return List.of(INPUT_SLOT, SECONDARY_INPUT);
    }

    public AlloySmelterBE(BlockPos pos, BlockState blockState) {
        this(zMachines.ALLOY_SMELTER.blockentity().get(), pos, blockState);
    }

    @Override
    @Nullable
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new AlloySmelterMenu(i, inventory, this, this.networkData);
    }

    Optional<RecipeHolder<AlloySmelterRecipeType>> saved_recipe = null;

    @Override
    public ItemStack getInput() {
        return invertedRecipe
                ? super.getInput()
                : getStackInSlot(SECONDARY_INPUT);
    }

    public ItemStack getSecondaryInput() {
        return invertedRecipe
                ? getStackInSlot(SECONDARY_INPUT)
                : super.getInput();
    }

    boolean invertedRecipe = false;

    @Override
    public boolean init() {

        if (getInput().isEmpty())
            return cancel();

        enableProgress();

        saved_recipe = getRecipes(level, zMachines.ALLOY_SMELTER,
                new BiItemInput(getInput(), getSecondaryInput()));

        if (saved_recipe.isEmpty()) {
            invertedRecipe = !invertedRecipe;
            return cancel();
        }

        AlloySmelterRecipeType recipe;

        recipe = saved_recipe.get().value();

        if (!checkSlot(getOutput(), recipe.getOutputItem().create()))
            return cancel();

        if (!calculateAndConsumeFE(recipe.getEnergy()))
            return cancel();

        update(true);

        setMaxProgress(calculateMaxProgress(recipe.getTime()));

        return true;

    }

    @Override
    public void result() {

        AlloySmelterRecipeType recipe;

        recipe = saved_recipe.get().value();

        updateResource(ItemResource.of(recipe.getOutputItem()), OUTPUT_SLOT,
                recipe.getOutputItem().count(), false);

        updateResource(getItemStorage().getResource(INPUT_SLOT), INPUT_SLOT,
                recipe.getInputItem().count(), true);

        updateResource(getItemStorage().getResource(SECONDARY_INPUT), SECONDARY_INPUT,
                recipe.getCatalystItem().count(), true);

    }

    @Override
    public ContainerData getContainerData() {
        return networkData;
    }

    @Override
    public SlotBuilder getSlotTypes() {
        return SlotBuilder.of(1).set(SECONDARY_INPUT, SlotType.INPUT);
    }

}
