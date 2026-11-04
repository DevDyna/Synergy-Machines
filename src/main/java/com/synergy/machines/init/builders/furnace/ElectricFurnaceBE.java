package com.synergy.machines.init.builders.furnace;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;

import com.devdyna.cakesticklib.api.recipe.recipeInput.ItemInput;
import com.synergy.machines.Config;
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

public class ElectricFurnaceBE extends BaseMachineBE {

    public static final int INPUT_SLOT = 4;
    public static final int OUTPUT_SLOT = 5;

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

    private MixedRecipeHolder holder;

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
            if (r2.isEmpty() || Config.DISABLE_MACHINE_FURNACE_PROCESS_VANILLA.get())
                return cancel();

            smelting = r2.get().value();
        } else
            electric = r.get().value();

        holder = new MixedRecipeHolder(

                (r.isEmpty()
                        ? getCalculatedDelay(smelting.cookingTime())
                        : electric.getTime()),

                (r.isEmpty()
                        ? Config.MACHINE_FURNACE_PROCESS_VANILLA_FE_COST.get()
                        : electric.getEnergy()),

                (r.isEmpty()
                        ? 1
                        : electric.getInput().count()),

                (r.isEmpty()
                        ? smelting.assemble(new SingleRecipeInput(getInput())).copy()
                        : electric.getOutput().create()));

        if (!checkSlot(getOutput(), holder.output)) 
            return cancel();
        

        if (!calculateAndConsumeFE(holder.fe))
            return cancel();

        update(true);

        setMaxProgress(calculateMaxProgress(holder.tick));

        return true;

    }

    @Override
    public void result() {

        updateResource(ItemResource.of(holder.output), OUTPUT_SLOT, holder.output.count(), false);
        updateResource(getItemStorage().getResource(INPUT_SLOT), INPUT_SLOT, holder.amount_consumed, true);
    }

    @Override
    public ContainerData getContainerData() {
        return networkData;
    }

    public static int getCalculatedDelay(int time) {
        return Config.DISABLE_MACHINE_FURNACE_VANILLA_TICK_REDUCER.get() ? time
                : Math.max(Config.MACHINE_FURNACE_PROCESS_VANILLA_MIN_TICK_DELAY.get(), time
                        * Config.MACHINE_FURNACE_PROCESS_VANILLA_PERCENTUAGE_TICK_DELAY.get() / 100);
    }

    private record MixedRecipeHolder(int tick, int fe, int amount_consumed, ItemStack output) {

    }

}
