package com.synergy.machines.init.builders.framework;

import com.devdyna.cakesticklib.api.utils.SlotBuilder;
import com.devdyna.cakesticklib.api.utils.SlotBuilder.ResourceType;
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
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;

public class FrameworkMenu extends BaseMachineMenu {

    public FrameworkMenu(int c, Inventory i, FriendlyByteBuf d) {
        this(c, i, i.player.level().getBlockEntity(d.readBlockPos()), DataStorage.simple());
    }

    public FrameworkMenu(int i, Inventory inv, BlockEntity be, ContainerData data) {
        super(zMachines.FRAMEWORK.menu().get(), i, be, inv, data);

        addMachineInputSlot(blockEntity.getItemStorage(), FrameworkBE.INPUT_SLOT, 43, 8);

        for (var slot : FrameworkBE.MACHINE_SLOTS)
            addMachineSlot(blockEntity.getItemStorage(), slot, 1,
                    43 + (FrameworkBE.MACHINE_SLOTS.indexOf(slot)  * 27),
                    35);

        for (var slot : FrameworkBE.OUTPUT_SLOTS)
            addMachineOutputSlot(blockEntity.getItemStorage(), slot,
                    43 + (FrameworkBE.OUTPUT_SLOTS.indexOf(slot)  * 27),
                    60);
    }

    @Override
    public MachineType<? extends BaseMachineBlock, ? extends BaseMachineBE, ? extends BaseMachineMenu, ? extends BaseMachineRecipeType<?>> getMachine() {
        return zMachines.FRAMEWORK;
    }

    @Override
    public SlotBuilder getSlots() {
        return SlotBuilder.of(blockEntity.getMachineSlots())
                .set(FrameworkBE.INPUT_SLOT - 4, ResourceType.INPUT)
                .setAll(ResourceType.BUFFER, 1, 2, 3, 4, 5)
                .setAll(ResourceType.OUTPUT, 6, 7, 8, 9, 10);
    }

    // TODO API : move to api
    // slot with itemcount limit
    protected void addMachineSlot(ItemStacksResourceHandler h, int index, int limit, int x, int y) {
        addMachineSlot(_ -> true, h, index, limit, x, y);
    }

}
