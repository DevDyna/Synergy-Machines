package com.synergy.machines.init.builders.compressor;

import javax.annotation.Nullable;

import com.synergy.machines.api.machine.BaseMachineBE;
import com.synergy.machines.api.recipeinputs.BiItemInput;
import com.synergy.machines.init.types.zMachines;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.transfer.item.ItemResource;

public class CompressorBE extends BaseMachineBE {

    public static final int PLATE_SLOT = 6;

    public CompressorBE(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    public int getMachineSlots() {
        return 7;
    }

    public CompressorBE(BlockPos pos, BlockState blockState) {
        this(zMachines.COMPRESSOR.blockentity().get(), pos, blockState);
    }

    @Override
    @Nullable
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new CompressorMenu(i, inventory, this, this.networkData);
    }

    @Override
    public boolean init() {

        if (getInput().isEmpty())
            return cancel();

        enableProgress();

        var r = getRecipes(level, zMachines.COMPRESSOR, new BiItemInput(getInput(), getPlateSlot()));

        // no recipe
        if (r.isEmpty())
            return cancel();

        var recipe = r.get().value();

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

        var recipe = getUnsafeRecipes(level, zMachines.COMPRESSOR,
                new BiItemInput(getInput(), getPlateSlot()));

        updateResource(ItemResource.of(recipe.getOutputItem()), OUTPUT_SLOT,
                recipe.getOutputItem().count(), false);

        if (recipe.consumeCatalyst())
            updateResource(getItemStorage().getResource(PLATE_SLOT), PLATE_SLOT,
                    recipe.getCatalystItem().count(), true);

        updateResource(getItemStorage().getResource(INPUT_SLOT), INPUT_SLOT,
                recipe.getInputItem().count(), true);

    }

    @Override
    public ContainerData getContainerData() {
        return networkData;
    }

    public ItemStack getPlateSlot() {
        return getStackInSlot(PLATE_SLOT);
    }

}
