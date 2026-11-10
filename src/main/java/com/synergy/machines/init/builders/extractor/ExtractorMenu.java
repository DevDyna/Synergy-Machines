package com.synergy.machines.init.builders.extractor;



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

public class ExtractorMenu extends BaseMachineMenu {

    public ExtractorMenu(int c, Inventory i, FriendlyByteBuf d) {
        this(c, i, i.player.level().getBlockEntity(d.readBlockPos()), DataStorage.fluid(1));
    }

    public ExtractorMenu(int i, Inventory inv, BlockEntity be, ContainerData data) {
        super(zMachines.EXTRACTOR.menu().get(), i, be, inv, data);
        addMachineInputSlot(blockEntity.getItemStorage(), ExtractorBE.INPUT_SLOT, 47, 33);
        addMachineOutputSlot(blockEntity.getItemStorage(), ExtractorBE.OUTPUT_SLOT, 119, 34);
    }

    @Override
    public MachineType<? extends BaseMachineBlock, ? extends BaseMachineBE, ? extends BaseMachineMenu, ? extends BaseMachineRecipeType<?>> getMachine() {
        return zMachines.EXTRACTOR;
    }

    @Override
    public SlotBuilder getSlots() {
        return SlotBuilder.of(blockEntity.getMachineSlots())
                .set(ExtractorBE.INPUT_SLOT - 4, ResourceType.INPUT)
                .set(ExtractorBE.OUTPUT_SLOT - 4, ResourceType.OUTPUT);
    }

}
