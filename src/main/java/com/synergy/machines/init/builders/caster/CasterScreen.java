package com.synergy.machines.init.builders.caster;

import com.devdyna.cakesticklib.api.primitive.Size;
import com.synergy.machines.api.machine.BaseMachineScreen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class CasterScreen extends BaseMachineScreen<CasterMenu> {

        public CasterScreen(CasterMenu menu, Inventory playerInventory, Component title) {
                super(menu, playerInventory, title);
        }

        @Override
        public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
                super.extractBackground(graphics, mouseX, mouseY, a);
                renderFluidTank(graphics, 0, -22, +6, mouseX, mouseY);

                renderInputSlot(graphics, 47, 33, mouseX, mouseY);
                renderLargeOutputSlot(graphics, 119, 34, mouseX, mouseY);
        }

        @Override
        public boolean enableLeftLabel(int x, int y) {
                return true;
        }

        @Override
        public Size getContainerTitlePos() {
                return Size.of(47, 0);
        }

}
