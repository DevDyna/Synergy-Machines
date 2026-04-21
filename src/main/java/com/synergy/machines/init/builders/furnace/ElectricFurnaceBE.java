package com.synergy.machines.init.builders.furnace;

import java.util.Optional;
import javax.annotation.Nullable;

import com.synergy.machines.Common;
import com.synergy.machines.api.machine.BaseMachineBE;
import com.synergy.machines.api.recipeinputs.MonoItemInput;
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
    public boolean initProgress() {

        if (getInput().isEmpty())
            return cancel();

        progress_cancel = false;

        Optional<RecipeHolder<ElectricFurnaceRecipeType>> r = level.getServer().getRecipeManager()
                .getRecipeFor(zMachines.ELECTRIC_FURNACE.recipe().getType(),
                        new MonoItemInput(getInput()), level);

        Optional<RecipeHolder<SmeltingRecipe>> r2 = level.getServer().getRecipeManager()
                .getRecipeFor(RecipeType.SMELTING, new SingleRecipeInput(getInput()), level);

        ElectricFurnaceRecipeType electric = null;
        SmeltingRecipe smelting = null;

        if (r.isEmpty()) {
            if (r2.isEmpty() || Common.DISABLE_MACHINE_FURNACE_PROCESS_VANILLA.get() 
            )
                return cancel();

            smelting = r2.get().value();
        } else
            electric = r.get().value();

        recipeHolder = new MixedRecipeHolder(

                (r.isEmpty()
                        ? getCalculatedDelay(smelting)
                        : electric.getTime()),

                (r.isEmpty()
                        ? Common.MACHINE_FURNACE_PROCESS_VANILLA_FE_COST.get()
                    : 
                        electric.getEnergy()
                        ),

                (r.isEmpty()
                        ? smelting.assemble(new SingleRecipeInput(getInput())).copy()
                        : electric.getOutputItem().create()));

        if (!checkSlot(getOutput(), recipeHolder.result_item)) {
            return cancel();
        }

        if (!calculateAndConsumeFE(recipeHolder.energy_every_tick))
            return cancel();

        update(true);

        this.maxProgress = calculateMaxProgress(recipeHolder.tick_delay);

        return true;

    }

    @Override
    public void endProgress() {

        updateOutputSlot(getOutput(), recipeHolder.result_item, OUTPUT_SLOT);

        getInput().shrink(1);
    }

    @Override
    public ContainerData getContainerData() {
        return networkData;
    }

    public static int getCalculatedDelay(SmeltingRecipe recipe) {
        return Common.DISABLE_MACHINE_FURNACE_VANILLA_TICK_REDUCER.get() ? recipe.cookingTime()
                : Math.max(Common.MACHINE_FURNACE_PROCESS_VANILLA_MIN_TICK_DELAY.get(), recipe.cookingTime()
                        * Common.MACHINE_FURNACE_PROCESS_VANILLA_PERCENTUAGE_TICK_DELAY.get() / 100);
    }

    private record MixedRecipeHolder(int tick_delay, int energy_every_tick, ItemStack result_item) {

    }

}
