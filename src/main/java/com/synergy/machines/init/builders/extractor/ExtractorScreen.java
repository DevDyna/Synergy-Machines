package com.synergy.machines.init.builders.extractor;

import java.util.List;

import com.devdyna.cakesticklib.api.upgrades.UpgradeComponents.UpgradeType;
import com.devdyna.cakesticklib.api.utils.ArrayUtils;
import com.synergy.machines.api.machine.BaseMachineScreen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ExtractorScreen extends BaseMachineScreen<ExtractorMenu> {

    public ExtractorScreen(ExtractorMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);
        renderFluidTank(graphics, 0, 150, 5, mouseX, mouseY);

        renderInputSlot(graphics, 47, 33, mouseX, mouseY);
        renderLargeOutputSlot(graphics, 119, 34, mouseX, mouseY);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractRenderState(graphics, mouseX, mouseY, a);

        renderFluidTooltip(graphics, 0, 150, 5, 18, 72, mouseX, mouseY);
        renderInputSlotTooltip(graphics, 47, 33, mouseX, mouseY);
        renderLargeOutputSlotTooltip(graphics, 119, 34, mouseX, mouseY);

    }

    @Override
    public List<UpgradeType> validUpgrades() {
        return ArrayUtils.concat(DEFAULT_UPGRADES, UpgradeType.LUCK);
    }

}
