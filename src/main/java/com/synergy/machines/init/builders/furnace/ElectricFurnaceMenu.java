package com.synergy.machines.init.builders.furnace;



import com.synergy.machines.api.MachineType;
import com.synergy.machines.api.machine.BaseMachineBE;
import com.synergy.machines.api.machine.BaseMachineBlock;
import com.synergy.machines.api.machine.BaseMachineMenu;
import com.synergy.machines.api.machine.recipe.BaseMachineRecipeType;
import com.synergy.machines.init.types.zMachines;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.level.block.entity.BlockEntity;

@SuppressWarnings("null")
public class ElectricFurnaceMenu extends BaseMachineMenu {

    public ElectricFurnaceMenu(int c, Inventory i, FriendlyByteBuf d) {
        this(c, i, i.player.level().getBlockEntity(d.readBlockPos()), MACHINE_ITEM_DATA);
    }

    public ElectricFurnaceMenu(int i, Inventory inv, BlockEntity be, ContainerData data) {
        super(zMachines.ELECTRIC_FURNACE.menu().get(), i, be, inv, data);
        addMachineInputSlot(blockEntity.getItemStorage(), ElectricFurnaceBE.INPUT_SLOT, 47, 33);
        addMachineOutputSlot(blockEntity.getItemStorage(), ElectricFurnaceBE.OUTPUT_SLOT, 119, 34);
    }

    @Override
    public MachineType<? extends BaseMachineBlock, ? extends BaseMachineBE, ? extends BaseMachineMenu, ? extends BaseMachineRecipeType<?>> getMachine() {
        return zMachines.ELECTRIC_FURNACE;
    }

}
