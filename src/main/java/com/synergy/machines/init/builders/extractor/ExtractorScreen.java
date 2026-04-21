package com.synergy.machines.init.builders.extractor;

import static com.synergy.machines.Main.MODULE_ID;

import java.util.List;

import com.devdyna.cakesticklib.api.utils.ArrayUtils;
import com.devdyna.cakesticklib.api.utils.UpgradeComponents.UpgradeType;
import com.devdyna.cakesticklib.api.utils.x;
import com.synergy.machines.api.machine.BaseMachineScreen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

@SuppressWarnings("null")
public class ExtractorScreen extends BaseMachineScreen<ExtractorMenu> {

    public ExtractorScreen(ExtractorMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected Identifier background() {
        return x.rl(MODULE_ID, "textures/gui/container/simple_dual.png");
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float a) {
        super.extractBackground(guiGraphics, mouseX, mouseY, a);
        renderFluidTank(guiGraphics, 150, 5);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int pMouseX, int pMouseY, float a) {
        super.extractRenderState(graphics, pMouseX, pMouseY, a);

        renderFluidTooltip(graphics, 150, 5, 18, 72, pMouseX, pMouseY);

    }

    @Override
    public List<UpgradeType> validUpgrades() {
        return ArrayUtils.concat(DEFAULT_UPGRADES, UpgradeType.LUCK);
    }

}
