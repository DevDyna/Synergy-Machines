package com.synergy.machines.init.builders.rock_crusher;

import java.util.List;

import com.devdyna.cakesticklib.api.utils.ArrayUtils;
import com.devdyna.cakesticklib.api.utils.UpgradeComponents.UpgradeType;
import com.synergy.machines.api.machine.BaseMachineScreen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

@SuppressWarnings("null")
public class RockCrusherScreen extends BaseMachineScreen<RockCrusherMenu> {

        public RockCrusherScreen(RockCrusherMenu menu, Inventory playerInventory, Component title) {
                super(menu, playerInventory, title);
        }

        @Override
        public List<UpgradeType> validUpgrades() {
                return ArrayUtils.concat(DEFAULT_UPGRADES, UpgradeType.LUCK, UpgradeType.FLUID);
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

                renderFluidTooltip(graphics, - 22, + 6, 18, 72, mouseX, mouseY);
        }
        


        @Override
        protected void extractLabels(GuiGraphicsExtractor graphics, int xm, int ym) {
        graphics.text(this.font, this.title, this.titleLabelX + 47, this.titleLabelY,
                                defaultToolTipColor.getRGB(), false);
        }

        

}
