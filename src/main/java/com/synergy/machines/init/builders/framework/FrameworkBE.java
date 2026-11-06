package com.synergy.machines.init.builders.framework;

import java.util.ArrayList;
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
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.transfer.item.ItemResource;

public class FrameworkBE extends BaseMachineBE {

    public static final int INPUT_SLOT = 4;

    public static final int MACHINE_1 = 5;
    public static final int MACHINE_2 = 6;
    public static final int MACHINE_3 = 7;
    public static final int MACHINE_4 = 8;
    public static final int MACHINE_5 = 9;

    public static final int OUTPUT_1 = 10;
    public static final int OUTPUT_2 = 11;
    public static final int OUTPUT_3 = 12;
    public static final int OUTPUT_4 = 13;
    public static final int OUTPUT_5 = 14;

    public static final List<Integer> MACHINE_SLOTS = List.of(
            MACHINE_1, MACHINE_2, MACHINE_3,
            MACHINE_4, MACHINE_5);

    public static final List<Integer> OUTPUT_SLOTS = List.of(
            OUTPUT_1, OUTPUT_2, OUTPUT_3,
            OUTPUT_4, OUTPUT_5);

    public FrameworkBE(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    public int getMachineSlots() {
        return 15;
    }

    public FrameworkBE(BlockPos pos, BlockState blockState) {
        this(zMachines.FRAMEWORK.blockentity().get(), pos, blockState);
    }

    @Override
    @Nullable
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new FrameworkMenu(i, inventory, this, this.networkData);
        return new FrameworkMenu(i, inventory, this, this.getContainerData());
    }

    public ItemStack getInput() {
        return getStackInSlot(INPUT_SLOT);
    }

    @Override
    public List<Integer> getInputSlotIndex() {
        return List.of(INPUT_SLOT);
    }

    @Override
    public List<Integer> getOutputSlotIndex() {
        return OUTPUT_SLOTS;
    }

    // TODO config
    public static final int BASE_FE_COST = 100;
    public static final int BASE_TICK_DELAY = 40;

    private List<ItemStackTemplate> results = new ArrayList<>(5);

    @Override
    public boolean init() {

        if (getInput().isEmpty())
            return cancel();

        if (getStackInSlot(MACHINE_1).isEmpty())
            return cancel();

        int i = 0;
        var fe = BASE_FE_COST;
        var tick = BASE_TICK_DELAY;

        for (var _ : OUTPUT_SLOTS)
            results.add(null);

        while (i < MACHINE_SLOTS.size() && !getStackInSlot(i+4).isEmpty()) {

            var input = i == 0 ? getInput() : getStackInSlot(OUTPUT_SLOTS.get(i - 1));
            var machine = getStackInSlot(MACHINE_SLOTS.get(i));
            var output = getStackInSlot(OUTPUT_SLOTS.get(i));

            var r = getRecipes(level, zMachines.FRAMEWORK,
                    new ItemInput.dual(input, machine));

            if (r.isEmpty())
                break;

            var recipe = r.get().value();

            if (!checkSlot(output, recipe.getOutput().create()))
                break;

            results.set(i, recipe.getOutput());
            fe += recipe.getEnergy();
            tick += recipe.getTime();
            i++;
        }

        if (i == 0)
            return cancel();

        enableProgress();

        if (!calculateAndConsumeFE(fe))
            return cancel();

        update(true);

        setMaxProgress(calculateMaxProgress(tick));

        return true;
    }

    @Override
    public void result() {

        if (!results.isEmpty() && results.size() > 0)
            for (int i = 0; i < results.size(); i++)
                if (results.get(i) != null)
                    updateResource(ItemResource.of(results.get(i)), OUTPUT_SLOTS.get(i),
                            results.get(i).count(), false);

        // updateResource(getItemStorage().getResource(INPUT_SLOT), INPUT_SLOT,
        // recipe.getInput().count(), true);

    }

}
