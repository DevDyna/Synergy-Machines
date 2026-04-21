package com.synergy.machines.init.builders.melter;



import com.synergy.machines.api.MachineType;
import com.synergy.machines.api.machine.*;
import com.synergy.machines.api.machine.BaseMachineMenu;
import com.synergy.machines.api.machine.recipe.BaseMachineRecipeType;
import com.synergy.machines.init.types.zMachines;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.level.block.entity.BlockEntity;

@SuppressWarnings("null")
public class MelterMenu extends BaseMachineMenu {

    public MelterMenu(int c, Inventory i, FriendlyByteBuf d) {
        this(c, i, i.player.level().getBlockEntity(d.readBlockPos()), MACHINE_FLUID_DATA);
    }

    public MelterMenu(int i, Inventory inv, BlockEntity be, ContainerData data) {
        super(zMachines.ELECTRIC_MELTER.menu().get(), i, be, inv, data);
        addMachineInputSlot(blockEntity.getItemStorage(), MelterBE.INPUT_SLOT, 47, 33);
    }

    @Override
    public MachineType<? extends BaseMachineBlock, ? extends BaseMachineBE, ? extends BaseMachineMenu, ? extends BaseMachineRecipeType<?>> getMachine() {
        return zMachines.ELECTRIC_MELTER;
    }

}
