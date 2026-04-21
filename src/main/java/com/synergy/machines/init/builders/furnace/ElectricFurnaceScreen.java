package com.synergy.machines.init.builders.furnace;

import static com.synergy.machines.Main.MODULE_ID;

import com.devdyna.cakesticklib.api.utils.x;
import com.synergy.machines.api.machine.BaseMachineScreen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

@SuppressWarnings("null")
public class ElectricFurnaceScreen extends BaseMachineScreen<ElectricFurnaceMenu> {

    public ElectricFurnaceScreen(ElectricFurnaceMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected Identifier background() {
        return x.rl(MODULE_ID, "textures/gui/container/simple_dual.png");
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor graphics, int xm, int ym) {
        graphics.text(this.font, this.title, this.titleLabelX + 47, this.titleLabelY,
                defaultToolTipColor.getRGB(), false);
    }

}
