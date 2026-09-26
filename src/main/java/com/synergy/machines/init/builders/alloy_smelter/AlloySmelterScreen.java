package com.synergy.machines.init.builders.alloy_smelter;

import com.synergy.machines.api.machine.BaseMachineScreen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class AlloySmelterScreen extends BaseMachineScreen<AlloySmelterMenu> {

    public AlloySmelterScreen(AlloySmelterMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {

        super.extractBackground(graphics, mouseX, mouseY, a);

        renderInputSlot(graphics, 34, 33, mouseX, mouseY);
        renderInputSlot(graphics, 54, 33, mouseX, mouseY);
        renderLargeOutputSlot(graphics, 119, 34, mouseX, mouseY);

    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractRenderState(graphics, mouseX, mouseY, a);

        renderInputSlotTooltip(graphics, 34, 33, mouseX, mouseY);
        renderInputSlotTooltip(graphics, 54, 33, mouseX, mouseY);
        renderLargeOutputSlotTooltip(graphics, 119, 34, mouseX, mouseY);
    }

}
