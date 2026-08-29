package com.synergy.machines.api.machine;

import com.devdyna.cakesticklib.api.gui.BaseMenu;
import com.devdyna.cakesticklib.api.utils.x;
import com.synergy.machines.api.MachineType;
import com.synergy.machines.api.machine.recipe.BaseMachineRecipeType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;

public abstract class BaseMachineMenu extends BaseMenu {

    protected static final int PROGRESS_INDEX = 0;
    protected static final int MAX_PROGRESS_INDEX = 1;
    protected static final int ENERGY_INDEX = 2;
    protected static final int MAX_ENERGY_INDEX = 3;
    protected static final int ENERGY_USAGE = 4;
    protected static final int FLUID_INDEX = 5;
    protected static final int MAX_FLUID_INDEX = 6;
    protected static final int ID_FLUID_INDEX = 7;

    protected final ContainerData data;
    protected final Level level;
    protected final BaseMachineBE blockEntity;

    /**
     * A simple container data used on machines that DONT USE FLUIDS
     */
    public static final SimpleContainerData MACHINE_ITEM_DATA = new SimpleContainerData(
            BaseMachineBE.PROGRESS_DATA_SIZE + BaseMachineBE.ENERGY_DATA_SIZE);
    /**
     * A simple container data used on machines that USE FLUIDS
     */
    public static final SimpleContainerData MACHINE_FLUID_DATA = new SimpleContainerData(
            BaseMachineBE.PROGRESS_DATA_SIZE + BaseMachineBE.ENERGY_DATA_SIZE + BaseMachineBE.FLUID_DATA_SIZE);

    protected BaseMachineMenu(MenuType<?> menuType, int containerId, BlockEntity be, Inventory inv,
            ContainerData data) {
        super(menuType, containerId, be);
        this.blockEntity = (BaseMachineBE) be;
        this.level = inv.player.level();
        this.data = data;

        addMachineUpgradeSlot(blockEntity.getItemStorage(), BaseMachineBE.SLOT_UPGRADE_1, 180, 8);
        addMachineUpgradeSlot(blockEntity.getItemStorage(), BaseMachineBE.SLOT_UPGRADE_2, 180, 26);
        addMachineUpgradeSlot(blockEntity.getItemStorage(), BaseMachineBE.SLOT_UPGRADE_3, 180, 44);
        addMachineUpgradeSlot(blockEntity.getItemStorage(), BaseMachineBE.SLOT_UPGRADE_4, 180, 62);

        addDataSlots(data);
        addPlayerSlots(inv);
    }

    @Override
    public BlockEntity getBlockEntity() {
        return blockEntity;
    }

    public boolean isCrafting() {
        return data.get(PROGRESS_INDEX) > 0;
    }

    public int getScaledArrowProgress() {
        int progress = data.get(PROGRESS_INDEX);
        int maxProgress = data.get(MAX_PROGRESS_INDEX);
        return maxProgress != 0 && progress != 0
                ? progress * 24 / maxProgress
                : 0;
    }

    public int getEnergyStored() {
        blockEntity.setChanged();
        return data.get(ENERGY_INDEX);
    }

    public int getEnergyUsage() {
        return data.get(ENERGY_USAGE);
    }

    public int getMaxEnergy() {
        return data.get(MAX_ENERGY_INDEX);
    }

    public int getFluidAmount() {
        return (getBlockEntity() instanceof TypedFluidStorage)
                ? data.get(FLUID_INDEX)
                : 0;
    }

    public int getMaxFluidAmount() {
        return (getBlockEntity() instanceof TypedFluidStorage)
                ? data.get(MAX_FLUID_INDEX)
                : 0;
    }

    public Fluid getFluid() {
        return (getBlockEntity() instanceof TypedFluidStorage)
                ? TypedFluidStorage.getFluidFromID(data.get(ID_FLUID_INDEX))
                : null;
    }

    public FluidStack getFluidStack() {
        return (getBlockEntity() instanceof TypedFluidStorage)
                ? x.fluid(getFluid(), getFluidAmount())
                : null;
    }

    public Level getLevel() {
        return level;
    }

    public int getRemainProgress() {
        return isCrafting()
                ? data.get(MAX_PROGRESS_INDEX) - data.get(PROGRESS_INDEX)
                : 0;
    }

    public abstract MachineType<? extends BaseMachineBlock, ? extends BaseMachineBE, ? extends BaseMachineMenu, ? extends BaseMachineRecipeType<?>> getMachine();

}
