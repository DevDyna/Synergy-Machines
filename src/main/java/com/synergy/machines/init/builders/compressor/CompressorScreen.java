package com.synergy.machines.init.builders.compressor;

import static com.synergy.machines.Main.MODULE_ID;

import com.devdyna.cakesticklib.api.utils.x;
import com.synergy.machines.api.machine.BaseMachineScreen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class CompressorScreen extends BaseMachineScreen<CompressorMenu> {

    public CompressorScreen(CompressorMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {

        super.extractBackground(graphics, mouseX, mouseY, a);

        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                x.rl(MODULE_ID, "textures/gui/sprite/compressor_arrow/off.png"),
                getLeftPos() + 47,
                getTopPos() + 36,
                0, 0,
                16, 9,
                16, 9);

        if (whenAnimateArrow()
                && !menu.getSlot(CompressorBE.PLATE_SLOT).getItem().isEmpty()) {

            graphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    x.rl(MODULE_ID, "textures/gui/sprite/compressor_arrow/on.png"),
                    getLeftPos() + 47,
                    getTopPos() + 36,
                    0, 0,
                    16, 9,
                    16, 9);
        }

        renderInputSlot(graphics, 47, 15, mouseX, mouseY);
        renderExtraSlot(graphics, 47, 51, mouseX, mouseY);
        renderLargeOutputSlot(graphics, 119, 34, mouseX, mouseY);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {

        super.extractRenderState(graphics, mouseX, mouseY, a);

        renderInputSlotTooltip(graphics, 47, 15, mouseX, mouseY);
        renderExtraSlotTooltip(graphics, 47, 51, mouseX, mouseY);
        renderLargeOutputSlotTooltip(graphics, 119, 34, mouseX, mouseY);
    }

}
