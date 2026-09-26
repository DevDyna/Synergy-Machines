package com.synergy.machines.init.builders.macerator;

import java.util.List;

import com.devdyna.cakesticklib.api.upgrades.UpgradeComponents.UpgradeType;
import com.devdyna.cakesticklib.api.utils.ArrayUtils;
import com.synergy.machines.api.machine.BaseMachineScreen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class MaceratorScreen extends BaseMachineScreen<MaceratorMenu> {

    public MaceratorScreen(MaceratorMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {

        super.extractBackground(graphics, mouseX, mouseY, a);

        renderInputSlot(graphics, 47, 33, mouseX, mouseY);
        renderLargeOutputSlot(graphics, 119, 25, mouseX, mouseY);
        renderOutputSlot(graphics, 119, 50, mouseX, mouseY);

    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {

        super.extractRenderState(graphics, mouseX, mouseY, a);

        renderInputSlotTooltip(graphics, 47, 33, mouseX, mouseY);
        renderLargeOutputSlotTooltip(graphics, 119, 25, mouseX, mouseY);
        renderOutputSlotTooltip(graphics, 119, 50, mouseX, mouseY);
    }

    @Override
    public List<UpgradeType> validUpgrades() {
        return ArrayUtils.concat(DEFAULT_UPGRADES, UpgradeType.LUCK);
    }

}
