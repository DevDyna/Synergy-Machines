package com.synergy.machines.init.builders.framework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;

import com.devdyna.cakesticklib.api.aspect.logic.ResourceRestricted;
import com.devdyna.cakesticklib.api.primitive.Ticker;
import com.devdyna.cakesticklib.api.recipe.recipeInput.ItemInput;
import com.devdyna.cakesticklib.api.utils.FluidUtils;
import com.devdyna.cakesticklib.api.utils.x;
import com.devdyna.cakesticklib.setup.registry.LibHandlers;
import com.synergy.machines.api.machine.BaseMachineBE;
import com.synergy.machines.api.machine.BaseMachineMenu;
import com.synergy.machines.init.types.zMachines;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.fluid.FluidStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;

public class FrameworkBE extends BaseMachineBE implements ResourceRestricted.Fluid {

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
        return new FrameworkMenu(i, inventory, this, this.getContainerData());
    }

    public static final int EXTRA_DATA_SIZE = 2;

    public static final int FRAMEWORK_FLUID_DATA_SIZE = FLUID_DATA_SIZE * 2;

    @Override
    public ContainerData getContainerData() {
        return new ContainerData() {

            @Override
            public int getCount() {
                return ENERGY_DATA_SIZE + PROGRESS_DATA_SIZE + EXTRA_DATA_SIZE + FRAMEWORK_FLUID_DATA_SIZE;
            }

            @Override
            public int get(int i) {

                var fluid = ((ResourceRestricted.Fluid) FrameworkBE.this);

                return switch (i) {
                    case BaseMachineMenu.PROGRESS_INDEX -> getProgress();
                    case BaseMachineMenu.MAX_PROGRESS_INDEX -> getMaxProgress();
                    case BaseMachineMenu.STORED_ENERGY_INDEX -> getEnergyStorage().getAmountAsInt();
                    case BaseMachineMenu.MAX_ENERGY_INDEX -> getMaxEnergy();
                    case BaseMachineMenu.RECIPE_ENERGY_USAGE -> getEnergyUsage();

                    case 5 -> fluid.getFluidStorage().getAmountAsInt(0);
                    case 6 -> fluid.getTankCapacity();
                    case 7 -> FluidUtils.getFluidToID(fluid.getAsStack(0));

                    case 8 -> fluid.getFluidStorage().getAmountAsInt(1);
                    case 9 -> fluid.getTankCapacity();
                    case 10 -> FluidUtils.getFluidToID(fluid.getAsStack(1));

                    case FrameworkMenu.MAX_GEN_PROGRESS_INDEX -> getGenTicker() == null ? 0 : getGenTicker().max();
                    case FrameworkMenu.GEN_PROGRESS_INDEX -> getGenTicker() == null ? 0 : getGenTicker().value();

                    default -> 0;
                };

            }

            @Override
            public void set(int index, int value) {
            }
        };
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
    public static final int BASE_TICK_DELAY = 80;

    private List<ItemStackTemplate> items;

    private Ticker genTicker = null;

    public Ticker getGenTicker() {
        return genTicker;
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        output.putInt("gen_progress", genTicker == null ? 0 : genTicker.value());
        super.saveAdditional(output);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        if (genTicker != null)
            input.getInt("gen_progress").ifPresent(genTicker::setValue);
        super.loadAdditional(input);
    }

    // TODO rework
    @Override
    public boolean init() {

        if (checkSlot(getInput(), x.item(Items.COBBLESTONE))) {
            if (genTicker == null)
                genTicker = Ticker.of(calculateMaxProgress(40));// TODO recipe

            if (genTicker.commit()) {
                updateResource(ItemResource.of(Items.COBBLESTONE), INPUT_SLOT, 1, false);
                genTicker = null;
            }
        }

        if (getInput().isEmpty())
            return cancel();

        if (getStackInSlot(MACHINE_1).isEmpty())
            return cancel();

        int i = 0;
        var fe = BASE_FE_COST;
        var tick = BASE_TICK_DELAY;

        items = new ArrayList<>(Collections.nCopies(5, null));

        while (i < MACHINE_SLOTS.size() && !getStackInSlot(i + 4).isEmpty()) {

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

            items.set(i, recipe.getOutput());
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
    public boolean cancel() {
        items = new ArrayList<>(Collections.nCopies(5, null));
        return super.cancel();
    }

    @Override
    public void onContentsChanged(int index, ItemStack previousContents) {
        cancel();
        super.onContentsChanged(index, previousContents);
    }

    @Override
    public void result() {

        if (!items.isEmpty() && items.size() > 0)
            for (int i = 0; i < items.size(); i++)
                if (items.get(i) != null)
                    updateResource(ItemResource.of(items.get(i)), OUTPUT_SLOTS.get(i),
                            items.get(i).count(), false);

        updateResource(getItemStorage().getResource(INPUT_SLOT), INPUT_SLOT,
                1, true);

    }

    @Override
    public FluidStacksResourceHandler getFluidStorage() {
        return getData(LibHandlers.FLUID_STORAGE);
    }

    @Override
    public int getTankCapacity() {
        return 1000;
    }

    @Override
    public int getTanks() {
        return 2;
    }

}
