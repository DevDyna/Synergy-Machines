package com.synergy.machines.init.builders.furnace;

import java.util.Optional;
import javax.annotation.Nullable;

import com.devdyna.cakesticklib.api.recipe.recipeInput.ItemInput;
import com.synergy.machines.Common;
import com.synergy.machines.api.machine.BaseMachineBE;
import com.synergy.machines.init.builders.furnace.recipe.ElectricFurnaceRecipeType;
import com.synergy.machines.init.types.zMachines;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.transfer.item.ItemResource;

@SuppressWarnings("null")
public class ElectricFurnaceBE extends BaseMachineBE {

    public ElectricFurnaceBE(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    public int getMachineSlots() {
        return 6;
    }

    public ElectricFurnaceBE(BlockPos pos, BlockState blockState) {
        this(zMachines.ELECTRIC_FURNACE.blockentity().get(), pos, blockState);
    }

    @Override
    @Nullable
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new ElectricFurnaceMenu(i, inventory, this, this.networkData);
    }

    private MixedRecipeHolder recipeHolder;

    @Override
    public boolean init() {

        if (getInput().isEmpty())
            return cancel();

        enableProgress();

        Optional<RecipeHolder<ElectricFurnaceRecipeType>> r = level.getServer().getRecipeManager()
                .getRecipeFor(zMachines.ELECTRIC_FURNACE.recipe().getType(),
                        new ItemInput.simple(getInput()), level);

        Optional<RecipeHolder<SmeltingRecipe>> r2 = level.getServer().getRecipeManager()
                .getRecipeFor(RecipeType.SMELTING, new SingleRecipeInput(getInput()), level);

        ElectricFurnaceRecipeType electric = null;
        SmeltingRecipe smelting = null;

        if (r.isEmpty()) {
            if (r2.isEmpty() || Common.DISABLE_MACHINE_FURNACE_PROCESS_VANILLA.get())
                return cancel();

            smelting = r2.get().value();
        } else
            electric = r.get().value();

        recipeHolder = new MixedRecipeHolder(

                (r.isEmpty()
                        ? getCalculatedDelay(smelting.cookingTime())
                        : electric.getTime()),

                (r.isEmpty()
                        ? Common.MACHINE_FURNACE_PROCESS_VANILLA_FE_COST.get()
                        : electric.getEnergy()),

                (r.isEmpty()
                        ? smelting.assemble(new SingleRecipeInput(getInput())).copy()
                        : electric.getOutputItem().create()));

        if (!checkSlot(getOutput(), recipeHolder.result_item)) {
            return cancel();
        }

        if (!calculateAndConsumeFE(recipeHolder.energy_every_tick))
            return cancel();

        update(true);

        setMaxProgress(calculateMaxProgress(recipeHolder.tick_delay));

        return true;

    }

    @Override
    public void result() {

        updateResource(ItemResource.of(recipeHolder.result_item), OUTPUT_SLOT, recipeHolder.result_item.count(), false);
        updateResource(getItemStorage().getResource(INPUT_SLOT), INPUT_SLOT, 1, true);
    }

    @Override
    public ContainerData getContainerData() {
        return networkData;
    }

    public static int getCalculatedDelay(int time) {
        return Common.DISABLE_MACHINE_FURNACE_VANILLA_TICK_REDUCER.get() ? time
                : Math.max(Common.MACHINE_FURNACE_PROCESS_VANILLA_MIN_TICK_DELAY.get(), time
                        * Common.MACHINE_FURNACE_PROCESS_VANILLA_PERCENTUAGE_TICK_DELAY.get() / 100);
    }

    private record MixedRecipeHolder(int tick_delay, int energy_every_tick, ItemStack result_item) {

    }

}
