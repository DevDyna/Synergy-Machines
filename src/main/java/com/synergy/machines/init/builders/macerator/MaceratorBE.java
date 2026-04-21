package com.synergy.machines.init.builders.macerator;

import java.util.List;
import javax.annotation.Nullable;

import com.synergy.machines.api.machine.BaseMachineBE;
import com.synergy.machines.api.machine.ExtraMachineSlots;
import com.synergy.machines.api.recipeinputs.MonoItemInput;
import com.synergy.machines.init.types.zMachines;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings("null")
public class MaceratorBE extends BaseMachineBE implements ExtraMachineSlots {

    public static final int SECONDARY_SLOT = 6;

    public MaceratorBE(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    public int getMachineSlots() {
        return 7;
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
        return new MaceratorMenu(i, inventory, this, this.networkData);
    }

    @Override
    public boolean initProgress() {

        if (getInput().isEmpty())
            return cancel();

        progress_cancel = false;

        var r = getRecipes(level, zMachines.MACERATOR, new MonoItemInput(getInput()));

        // no recipe
        if (r.isEmpty())
            return cancel();

        var recipe = r.get().value();

        if (recipe.getSecondaryOutputItem() != null)
            if (!checkSlot(getSecondarySlot(), recipe.getSecondaryOutputItem().item().create()))
                return cancel();

        if (!checkSlot(getOutput(), recipe.getOutputItem().create()))
            return cancel();

        if (!calculateAndConsumeFE(recipe.getEnergy()))
            return cancel();

        update(true);

        this.maxProgress = calculateMaxProgress(recipe.getTime());

        return true;

    }

    @Override
    public void endProgress() {

        var recipe = getUnsafeRecipes(level, zMachines.MACERATOR, new MonoItemInput(getInput()));

        updateOutputSlot(getOutput(), recipe.getOutputItem().create(), OUTPUT_SLOT);

        if (recipe.getSecondaryOutputItem() != null)
            if (!recipe.getSecondaryOutputItem().item().create().isEmpty()
                    && calculateSecondarySuccess(recipe.getSecondaryOutputItem().chance()))
                updateOutputSlot(getSecondarySlot(), recipe.getSecondaryOutputItem().item().create(), SECONDARY_SLOT);

        getInput().shrink(recipe.getInputItem().count());
    }

    @Override
    public ContainerData getContainerData() {
        return networkData;
    }

    @Override
    public SlotBuilder getSlotTypes() {
        return SlotBuilder.of(1).set(SECONDARY_SLOT, SlotType.OUTPUT);
    }

    public ItemStack getSecondarySlot() {
        return getStackInSlot(SECONDARY_SLOT);
    }
}
