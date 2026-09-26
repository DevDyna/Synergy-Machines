package com.synergy.machines.init.builders.melter;

import com.devdyna.cakesticklib.api.primitive.Size;
import com.synergy.machines.api.machine.BaseMachineScreen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class MelterScreen extends BaseMachineScreen<MelterMenu> {

        public MelterScreen(MelterMenu menu, Inventory playerInventory, Component title) {
                super(menu, playerInventory, title);
        }

        @Override
        public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {

                super.extractBackground(graphics, mouseX, mouseY, a);
                renderFluidTank(graphics, 0, 150, 5, mouseX, mouseY);

                renderInputSlot(graphics, 47, 33, mouseX, mouseY);

        }

        @Override
        public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {

                super.extractRenderState(graphics, mouseX, mouseY, a);
                renderFluidTooltip(graphics, 0, 150, 5, 18, 72, mouseX, mouseY);

                renderInputSlotTooltip(graphics, 47, 33, mouseX, mouseY);
        }

        @Override
        public Size getContainerTitlePos() {
                return Size.of(47, 0);
        }

}
