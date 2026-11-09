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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ResourceHandlerSlot;

public class FrameworkMenu extends BaseMachineMenu {

    public static final int MAX_GEN_PROGRESS_INDEX = 5;
    public static final int GEN_PROGRESS_INDEX = 6;

    public FrameworkMenu(int c, Inventory i, FriendlyByteBuf d) {
        this(c, i, i.player.level().getBlockEntity(d.readBlockPos()), framework());
    }

    public static final SimpleContainerData framework() {
        return new SimpleContainerData(
                BaseMachineBE.PROGRESS_DATA_SIZE + BaseMachineBE.ENERGY_DATA_SIZE + FrameworkBE.EXTRA);
    }

    public FrameworkMenu(int i, Inventory inv, BlockEntity be, ContainerData data) {
        super(zMachines.FRAMEWORK.menu().get(), i, be, inv, data);

        // addMachineSlot(blockEntity.getItemStorage(),
        // FrameworkBE.GENERATOR_UPGRADE_SLOT, 1, 20 + 43, 8);

        addMachineInputSlot(blockEntity.getItemStorage(), FrameworkBE.INPUT_SLOT, 43, 8);

        for (var slot : FrameworkBE.MACHINE_SLOTS)
            addMachineSlot(blockEntity.getItemStorage(), slot, 1,
                    43 + (FrameworkBE.MACHINE_SLOTS.indexOf(slot) * 27),
                    35);

        for (var slot : FrameworkBE.OUTPUT_SLOTS)
            addMachineOutputSlot(blockEntity.getItemStorage(), slot,
                    43 + (FrameworkBE.OUTPUT_SLOTS.indexOf(slot) * 27),
                    60);
    }

    public int getGenProgress() {
        return getData().get(GEN_PROGRESS_INDEX);
    }

    public int getMaxGenProgress() {
        return getData().get(MAX_GEN_PROGRESS_INDEX);
    }

    public int getRemainGenProgress() {
        return isCrafting(data.get(GEN_PROGRESS_INDEX))
                ? getMaxGenProgress() - getGenProgress()
                : 0;
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

    protected void addMachineInputSlot(ItemStacksResourceHandler h, int id, int limit, int x, int y) {
        addSlot(new ResourceHandlerSlot(h, h::set, id, x, y) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return true;
            }

            @Override
            public boolean mayPickup(Player playerIn) {
                return true;
            }

            @Override
            public int getMaxStackSize() {
                return limit;
            }

            @Override
            public int getMaxStackSize(ItemStack i) {
                return limit;
            }
        });
    }

}
