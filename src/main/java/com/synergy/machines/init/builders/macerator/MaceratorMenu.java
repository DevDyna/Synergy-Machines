package com.synergy.machines.init.builders.macerator;


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
public class MaceratorMenu extends BaseMachineMenu {

    public MaceratorMenu(int c, Inventory i, FriendlyByteBuf d) {
        this(c, i, i.player.level().getBlockEntity(d.readBlockPos()), MACHINE_ITEM_DATA);
    }

    public MaceratorMenu(int i, Inventory inv, BlockEntity be, ContainerData data) {
        super(zMachines.MACERATOR.menu().get(), i, be, inv, data);
        addMachineInputSlot(blockEntity.getItemStorage(), MaceratorBE.INPUT_SLOT, 47, 33);
        addMachineOutputSlot(blockEntity.getItemStorage(), MaceratorBE.OUTPUT_SLOT, 119, 25);
        addMachineOutputSlot(blockEntity.getItemStorage(), MaceratorBE.SECONDARY_SLOT, 119, 50);
    }

    @Override
    public MachineType<? extends BaseMachineBlock, ? extends BaseMachineBE, ? extends BaseMachineMenu, ? extends BaseMachineRecipeType<?>> getMachine() {
        return zMachines.MACERATOR;
    }

}
