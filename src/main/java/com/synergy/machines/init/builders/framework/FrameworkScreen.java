package com.synergy.machines.init.builders.framework;

import com.devdyna.cakesticklib.api.primitive.Size;
import com.synergy.machines.api.machine.BaseMachineScreen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

//TODO maybe i can unify screen-menu handling (?)
public class FrameworkScreen extends BaseMachineScreen<FrameworkMenu> {

        public FrameworkScreen(FrameworkMenu menu, Inventory playerInventory, Component title) {
                super(menu, playerInventory, title);
        }

        @Override
        public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {

                super.extractBackground(graphics, mouseX, mouseY, a);
                // renderFluidTank(graphics, 0, -22, +6, mouseX, mouseY);

                renderInputSlot(graphics, 43, 8, mouseX, mouseY);

                for (var slot : FrameworkBE.MACHINE_SLOTS)
                        renderExtraSlot(graphics,
                                        43 + (FrameworkBE.MACHINE_SLOTS.indexOf(slot)  * 27),
                                        35,
                                        mouseX, mouseY);

                for (var slot : FrameworkBE.OUTPUT_SLOTS)
                        renderOutputSlot(graphics,
                                        43 + (FrameworkBE.OUTPUT_SLOTS.indexOf(slot)  * 27),
                                        60,
                                        mouseX, mouseY);

        }

        // @Override
        // public boolean enableLeftLabel(GuiGraphicsExtractor g, int x, int y, float a)
        // {
        // return true;
        // }

        @Override
        public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {

                super.extractRenderState(graphics, mouseX, mouseY, a);

                // renderFluidTooltip(graphics, 0, -22, +6, 18, 72, mouseX, mouseY);

                renderInputSlotTooltip(graphics, 43, 8, mouseX, mouseY);

                for (var slot : FrameworkBE.MACHINE_SLOTS)
                        renderExtraSlotTooltip(graphics,
                                        43 + ((FrameworkBE.MACHINE_SLOTS.indexOf(slot) - 1) * 27),
                                        35,
                                        mouseX, mouseY);

                for (var slot : FrameworkBE.OUTPUT_SLOTS)
                        renderOutputSlotTooltip(graphics,
                                        43 + ((FrameworkBE.OUTPUT_SLOTS.indexOf(slot) - 1) * 27),
                                        60,
                                        mouseX, mouseY);

        }

        @Override
        public Size getContainerTitlePos() {
                return Size.of(60, 0);
        }

        @Override
        protected void renderArrow(GuiGraphicsExtractor guiGraphics) {
        
                //TODO change
        }

}
