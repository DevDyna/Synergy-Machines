package com.synergy.machines.init.builders.furnace;

import com.devdyna.cakesticklib.api.primitive.Size;
import com.synergy.machines.api.machine.BaseMachineScreen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ElectricFurnaceScreen extends BaseMachineScreen<ElectricFurnaceMenu> {

    public ElectricFurnaceScreen(ElectricFurnaceMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);

        renderInputSlot(graphics, 47, 33, mouseX, mouseY);
        renderLargeOutputSlot(graphics, 119, 34, mouseX, mouseY);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {

        super.extractRenderState(graphics, mouseX, mouseY, a);

        renderInputSlotTooltip(graphics, 47, 33, mouseX, mouseY);
        renderLargeOutputSlotTooltip(graphics, 119, 34, mouseX, mouseY);
    }

    @Override
    public Size getContainerTitlePos() {
        return Size.of(47, 0);
    }

}
