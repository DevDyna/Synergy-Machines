package com.synergy.machines.init.builders.compressor;



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
public class CompressorMenu extends BaseMachineMenu {

    public CompressorMenu(int c, Inventory i, FriendlyByteBuf d) {
        this(c, i, i.player.level().getBlockEntity(d.readBlockPos()), MACHINE_ITEM_DATA);
    }

    public CompressorMenu(int i, Inventory inv, BlockEntity be, ContainerData data) {
        super(zMachines.COMPRESSOR.menu().get(), i, be, inv, data);
        addMachineInputSlot(blockEntity.getItemStorage(), CompressorBE.INPUT_SLOT, 47, 15);
        addMachineOutputSlot(blockEntity.getItemStorage(), CompressorBE.OUTPUT_SLOT, 119, 34);
        addMachineInputSlot(blockEntity.getItemStorage(), CompressorBE.PLATE_SLOT, 47, 51);
    }

    @Override
    public MachineType<? extends BaseMachineBlock, ? extends BaseMachineBE, ? extends BaseMachineMenu, ? extends BaseMachineRecipeType<?>> getMachine() {
        return zMachines.COMPRESSOR;
    }

}
