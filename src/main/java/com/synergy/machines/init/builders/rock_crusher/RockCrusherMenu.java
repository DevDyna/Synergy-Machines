package com.synergy.machines.init.builders.rock_crusher;

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

public class RockCrusherMenu extends BaseMachineMenu {

    public RockCrusherMenu(int c, Inventory i, FriendlyByteBuf d) {
        this(c, i, i.player.level().getBlockEntity(d.readBlockPos()), DataStorage.fluid(1));
    }

    public RockCrusherMenu(int i, Inventory inv, BlockEntity be, ContainerData data) {
        super(zMachines.ROCK_CRUSHER.menu().get(), i, be, inv, data);
        addMachineInputSlot(blockEntity.getItemStorage(), RockCrusherBE.INPUT_SLOT, 47, 33);

        for (var slot : RockCrusherBE.OUTPUT_SLOTS)
            addMachineOutputSlot(blockEntity.getItemStorage(),
                    slot,
                    108 + (RockCrusherBE.OUTPUT_SLOTS.indexOf(slot) % 3 * 19),
                    15 + (RockCrusherBE.OUTPUT_SLOTS.indexOf(slot) / 3 * 19));
    }

    @Override
    public MachineType<? extends BaseMachineBlock, ? extends BaseMachineBE, ? extends BaseMachineMenu, ? extends BaseMachineRecipeType<?>> getMachine() {
        return zMachines.ROCK_CRUSHER;
    }

    @Override
    public SlotBuilder getSlots() {
        return SlotBuilder.of(blockEntity.getMachineSlots())
                .set(RockCrusherBE.INPUT_SLOT - 4, ResourceType.INPUT)
                .setAll(ResourceType.OUTPUT, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        // TODO API : setAll(<Type>,start,end);
    }

}
