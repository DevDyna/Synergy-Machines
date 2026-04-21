package com.synergy.machines.init.builders.caster;

import static com.synergy.machines.Main.MODULE_ID;

import com.devdyna.cakesticklib.api.utils.x;
import com.synergy.machines.api.machine.BaseMachineScreen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

@SuppressWarnings("null")
public class CasterScreen extends BaseMachineScreen<CasterMenu> {

        public CasterScreen(CasterMenu menu, Inventory playerInventory, Component title) {
                super(menu, playerInventory, title);
        }

        @Override
        protected Identifier background() {
                return x.rl(MODULE_ID, "textures/gui/container/simple_dual.png");
        }

        @Override
        public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float a) {
                renderLeftLabel(guiGraphics);
                super.extractBackground(guiGraphics, mouseX, mouseY, a);
                renderFluidTank(guiGraphics, -22, +6);
        }

        @Override
        public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
                super.extractRenderState(graphics, mouseX, mouseY, a);
                renderFluidTooltip(graphics, -22, 6, 18, 72, mouseX, mouseY);
        }

        @Override
        protected void extractLabels(GuiGraphicsExtractor graphics, int xm, int ym) {
                graphics.text(this.font, this.title, this.titleLabelX + 47, this.titleLabelY,
                                defaultToolTipColor.getRGB(), false);
        }

}
