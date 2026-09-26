package com.synergy.machines.init.builders.rock_crusher;

import java.util.List;

import com.devdyna.cakesticklib.api.primitive.Size;
import com.devdyna.cakesticklib.api.upgrades.UpgradeComponents.UpgradeType;
import com.devdyna.cakesticklib.api.utils.ArrayUtils;
import com.synergy.machines.api.machine.BaseMachineScreen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class RockCrusherScreen extends BaseMachineScreen<RockCrusherMenu> {

        public RockCrusherScreen(RockCrusherMenu menu, Inventory playerInventory, Component title) {
                super(menu, playerInventory, title);
        }

        @Override
        public List<UpgradeType> validUpgrades() {
                return ArrayUtils.concat(DEFAULT_UPGRADES, UpgradeType.LUCK, UpgradeType.FLUID);
        }

        @Override
        public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
                renderLeftLabel(graphics);
                super.extractBackground(graphics, mouseX, mouseY, a);
                renderFluidTank(graphics, 0, -22, +6, mouseX, mouseY);

                renderInputSlot(graphics, 47, 33, mouseX, mouseY);

                for (var slot : RockCrusherBE.OUTPUT_SLOTS)
                        renderOutputSlot(graphics,
                                        108 + (RockCrusherBE.OUTPUT_SLOTS.indexOf(slot) % 3 * 19),
                                        15 + (RockCrusherBE.OUTPUT_SLOTS.indexOf(slot) / 3 * 19),
                                        mouseX, mouseY);

        }

        @Override
        public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {

                super.extractRenderState(graphics, mouseX, mouseY, a);

                renderFluidTooltip(graphics, 0, -22, +6, 18, 72, mouseX, mouseY);

                renderInputSlotTooltip(graphics, 47, 33, mouseX, mouseY);

                for (var slot : RockCrusherBE.OUTPUT_SLOTS)
                        renderOutputSlotTooltip(graphics,
                                        108 + (RockCrusherBE.OUTPUT_SLOTS.indexOf(slot) % 3 * 19),
                                        15 + (RockCrusherBE.OUTPUT_SLOTS.indexOf(slot) / 3 * 19),
                                        mouseX, mouseY);

        }

        @Override
        public Size getContainerTitlePos() {
                return Size.of(47, 0);
        }

}
