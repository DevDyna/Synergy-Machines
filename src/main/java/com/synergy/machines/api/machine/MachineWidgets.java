package com.synergy.machines.api.machine;

import static com.synergy.machines.Main.MODULE_ID;

import java.util.List;

import com.devdyna.cakesticklib.api.primitive.Pos;
import com.devdyna.cakesticklib.api.utils.ClientUtils;
import com.devdyna.cakesticklib.api.utils.StringUtil;
import com.devdyna.cakesticklib.api.utils.x;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;

public interface MachineWidgets {

        int getLeftPos();

        int getTopPos();

        Font getFont();

        boolean getDebug();

        int getEnergyUsage();

        int getMaxEnergy();

        int getEnergyStored();

        FluidStack getFluidStack(int index);

        Fluid getFluid(int index);

        int getMaxFluidAmount(int index);

        int getFluidAmount(int index);

        int getRemainProgress();

        // ticker render

        default void renderTickProgress(GuiGraphicsExtractor guiGraphics, int xo, int yo, int mouseX, int mouseY) {
                if (getDebug())
                        if (getRemainProgress() > 0)
                                guiGraphics.text(getFont(), Component.literal((1 + getRemainProgress()) + " ticks"),
                                                getLeftPos() + xo,
                                                getTopPos() + yo,
                                                ClientUtils.defaultToolTipColor.getRGB(), false);

        }

        // sprites gui

        default void renderLeftLabel(GuiGraphicsExtractor guiGraphics) {
                guiGraphics.blit(
                                RenderPipelines.GUI_TEXTURED,
                                x.rl(MODULE_ID, "textures/gui/container/left_label.png"),
                                getLeftPos() - 30,
                                getTopPos(),
                                0, 0,
                                32, 86,
                                32, 86);
        }

        default void renderRightLabel(GuiGraphicsExtractor guiGraphics, int xo, int yo, int mouseX, int mouseY) {
                guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                                x.rl(MODULE_ID, "textures/gui/container/right_label.png"),
                                getLeftPos() + xo,
                                getTopPos() + yo,
                                0, 0,
                                32, 86,
                                32, 86);

        }

        default void renderMachineInventory(GuiGraphicsExtractor guiGraphics, int xo, int yo, int mouseX, int mouseY) {

                guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                                x.rl(MODULE_ID, "textures/gui/container/inventory/base.png"),
                                getLeftPos() + xo,
                                getTopPos() + yo,
                                0, 0,
                                176, 166,
                                176, 166);

                if (isDebugHover(xo + 5 - 1, yo + 80, 168, 82, mouseX, mouseY))
                        guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                                        x.rl(MODULE_ID, "textures/gui/container/inventory/debug.png"),
                                        getLeftPos() + 5 + xo - 1,
                                        getTopPos() + 80 + yo,
                                        0, 0,
                                        168, 82,
                                        168, 82);
        }

        default void renderMachineInventoryTooltip(GuiGraphicsExtractor graphics, int xo, int yo, int mouseX,
                        int mouseY) {
                renderDebugSlotTooltip(graphics, xo + 5, yo + 81, 167, 82, "inventory.debug", mouseX, mouseY);
        }

        // upgrade slots

        default void renderUpgradeSlots(GuiGraphicsExtractor guiGraphics, int xo, int yo, int mouseX, int mouseY) {
                guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                                x.rl(MODULE_ID, "textures/gui/container/slot/upgrade/base.png"),
                                getLeftPos() + xo + 7,
                                getTopPos() + yo + 7,
                                0, 0,
                                18, 72,
                                18, 72);

                if (isDebugHover(xo + 7 -3, yo + 7 -3, 24, 78, mouseX, mouseY))
                        guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                                        x.rl(MODULE_ID, "textures/gui/container/slot/upgrade/debug.png"),
                                        getLeftPos() + 7 -3+ xo ,
                                        getTopPos() + 7 -3+ yo ,
                                        0, 0,
                                        24, 78,
                                        24, 78);
        }

        default void renderUpgradeTooltips(GuiGraphicsExtractor graphics, int xo, int yo, int mouseX, int mouseY) {
                renderDebugSlotTooltip(graphics, xo, yo, 32, 86, "upgrade.debug", mouseX, mouseY);
        }

        // input slots

        default void renderInputSlot(GuiGraphicsExtractor guiGraphics, int xo, int yo, int mouseX, int mouseY) {
                guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                                x.rl(MODULE_ID, "textures/gui/container/slot/input/small/base.png"),
                                getLeftPos() + xo + 7 - 8,
                                getTopPos() + yo + 7 - 8,
                                0, 0,
                                18, 18,
                                18, 18);

                if (isDebugHover(xo + 7 - 8, yo + 7 - 8, 18, 18, mouseX, mouseY))
                        guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                                        x.rl(MODULE_ID, "textures/gui/container/slot/input/small/debug.png"),
                                        getLeftPos() + xo + 7 - 8,
                                        getTopPos() + yo + 7 - 8,
                                        0, 0,
                                        18, 18,
                                        18, 18);
        }

        default void renderLargeInputSlot(GuiGraphicsExtractor guiGraphics, int xo, int yo, int mouseX, int mouseY) {
                guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                                x.rl(MODULE_ID, "textures/gui/container/slot/input/large/base.png"),
                                getLeftPos() + xo + 7 + 4 - 16,
                                getTopPos() + yo + 7 + 4 - 16,
                                0, 0,
                                26, 26,
                                26, 26);

                if (isDebugHover(xo + 7 + 4 - 16, yo + 7 + 4 - 16, 26, 26, mouseX, mouseY))
                        guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                                        x.rl(MODULE_ID, "textures/gui/container/slot/input/large/debug.png"),
                                        getLeftPos() + xo + 7 + 4 - 16,
                                        getTopPos() + yo + 7 + 4 - 16,
                                        0, 0,
                                        26, 26,
                                        26, 26);
        }

        default void renderInputSlotTooltip(GuiGraphicsExtractor graphics, int xo, int yo, int mouseX, int mouseY) {
                renderSlotTooltip(graphics, xo, yo, "slot.input.debug", mouseX, mouseY);
        }

        default void renderLargeInputSlotTooltip(GuiGraphicsExtractor graphics, int xo, int yo, int mouseX,
                        int mouseY) {
                renderLargeSlotTooltip(graphics, xo, yo, "slot.input.debug", mouseX, mouseY);
        }

        // output slots

        default void renderOutputSlot(GuiGraphicsExtractor guiGraphics, int xo, int yo, int mouseX, int mouseY) {
                guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                                x.rl(MODULE_ID, "textures/gui/container/slot/output/small/base.png"),
                                getLeftPos() + xo + 7 - 8,
                                getTopPos() + yo + 7 - 8,
                                0, 0,
                                18, 18,
                                18, 18);

                if (isDebugHover(xo + 7 - 8, yo + 7 - 8, 18, 18, mouseX, mouseY))
                        guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                                        x.rl(MODULE_ID, "textures/gui/container/slot/output/small/debug.png"),
                                        getLeftPos() + xo + 7 - 8,
                                        getTopPos() + yo + 7 - 8,
                                        0, 0,
                                        18, 18,
                                        18, 18);
        }

        default void renderLargeOutputSlot(GuiGraphicsExtractor guiGraphics, int xo, int yo, int mouseX, int mouseY) {
                guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                                x.rl(MODULE_ID, "textures/gui/container/slot/output/large/base.png"),
                                getLeftPos() + xo + 7 + 4 - 16,
                                getTopPos() + yo + 7 + 4 - 16,
                                0, 0,
                                26, 26,
                                26, 26);

                if (isDebugHover(xo + 7 + 4 - 16, yo + 7 + 4 - 16, 26, 26, mouseX, mouseY))
                        guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                                        x.rl(MODULE_ID, "textures/gui/container/slot/output/large/debug.png"),
                                        getLeftPos() + xo + 7 + 4 - 16,
                                        getTopPos() + yo + 7 + 4 - 16,
                                        0, 0,
                                        26, 26,
                                        26, 26);
        }

        default void renderOutputSlotTooltip(GuiGraphicsExtractor graphics, int xo, int yo, int mouseX, int mouseY) {
                renderSlotTooltip(graphics, xo, yo, "slot.output.debug", mouseX, mouseY);
        }

        default void renderLargeOutputSlotTooltip(GuiGraphicsExtractor graphics, int xo, int yo, int mouseX,
                        int mouseY) {
                renderLargeSlotTooltip(graphics, xo, yo, "slot.output.debug", mouseX, mouseY);
        }

        // extra slots

        default void renderExtraSlot(GuiGraphicsExtractor guiGraphics, int xo, int yo, int mouseX, int mouseY) {
                guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                                x.rl(MODULE_ID, "textures/gui/container/slot/extra/small/base.png"),
                                getLeftPos() + xo + 7 - 8,
                                getTopPos() + yo + 7 - 8,
                                0, 0,
                                18, 18,
                                18, 18);

                if (isDebugHover(xo + 7 - 8, yo + 7 - 8, 18, 18, mouseX, mouseY))
                        guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                                        x.rl(MODULE_ID, "textures/gui/container/slot/extra/small/debug.png"),
                                        getLeftPos() + xo + 7 - 8,
                                        getTopPos() + yo + 7 - 8,
                                        0, 0,
                                        18, 18,
                                        18, 18);
        }

        default void renderLargeExtraSlot(GuiGraphicsExtractor guiGraphics, int xo, int yo, int mouseX, int mouseY) {
                guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                                x.rl(MODULE_ID, "textures/gui/container/slot/extra/large/base.png"),
                                getLeftPos() + xo + 7 + 4 - 16,
                                getTopPos() + yo + 7 + 4 - 16,
                                0, 0,
                                26, 26,
                                26, 26);

                if (isDebugHover(xo + 7 + 4 - 16, yo + 7 + 4 - 16, 26, 26, mouseX, mouseY))
                        guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                                        x.rl(MODULE_ID, "textures/gui/container/slot/extra/large/debug.png"),
                                        getLeftPos() + xo + 7 + 4 - 16,
                                        getTopPos() + yo + 7 + 4 - 16,
                                        0, 0,
                                        26, 26,
                                        26, 26);
        }

        default void renderExtraSlotTooltip(GuiGraphicsExtractor graphics, int xo, int yo, int mouseX, int mouseY) {
                renderSlotTooltip(graphics, xo, yo, "slot.extra.debug", mouseX, mouseY);
        }

        default void renderLargeExtraSlotTooltip(GuiGraphicsExtractor graphics, int xo, int yo, int mouseX,
                        int mouseY) {
                renderLargeSlotTooltip(graphics, xo, yo, "slot.extra.debug", mouseX, mouseY);
        }

        // energy bar

        default void renderEnergyStorage(GuiGraphicsExtractor guiGraphics, int xo, int yo, int mouseX, int mouseY) {
                guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                                x.rl(MODULE_ID, "textures/gui/container/slot/energy/base.png"),
                                getLeftPos() + xo, getTopPos() + yo,
                                0, 0,
                                18, 72,
                                36, 72);

                if (getMaxEnergy() > 0 && getEnergyStored() > 0) {
                        var slice = Math.min(72, (getEnergyStored() * 72) / getMaxEnergy());
                        guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                                        x.rl(MODULE_ID, "textures/gui/container/slot/energy/base.png"),
                                        getLeftPos() + xo, getTopPos() + yo + (72 - slice),
                                        18, 72 - slice,
                                        18, slice,
                                        36, 72);
                }

                if (isDebugHover(xo, yo, 18, 72, mouseX, mouseY))
                        guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                                        x.rl(MODULE_ID, "textures/gui/container/slot/energy/debug.png"),
                                        getLeftPos() + xo, getTopPos() + yo,
                                        0, 0,
                                        18, 72,
                                        18, 72);

        }

        default void renderEnergyTooltip(GuiGraphicsExtractor graphics, int x, int y, int x0, int y0, int mouseX,
                        int mouseY) {
                if (getDebug())
                        renderDebugSlotTooltip(graphics, x, y, x0, y0, "energy.debug", mouseX, mouseY);
                else if (Pos.of(getLeftPos() + x, getTopPos() + y).setSize(x0, y0).test(mouseX, mouseY))
                        graphics.setComponentTooltipForNextFrame(
                                        getFont(), List.of(Component.literal(
                                                        (Minecraft.getInstance().hasShiftDown()
                                                                        ? String.valueOf(
                                                                                        getEnergyStored())
                                                                        : StringUtil.getFormatNoRound()
                                                                                        .format(getEnergyStored()))
                                                                        + " FE / "
                                                                        + (Minecraft.getInstance()
                                                                                        .hasShiftDown()
                                                                                                        ? String.valueOf(
                                                                                                                        getMaxEnergy())
                                                                                                        : StringUtil.getFormatNoRound()
                                                                                                                        .format(getMaxEnergy()))
                                                                        + " FE"),
                                                        Component.literal(
                                                                        getEnergyUsage() <= 0
                                                                                        ? "No valid recipe found"
                                                                                        : "Usage : "
                                                                                                        + (getMaxEnergy() <= getEnergyUsage()
                                                                                                                        ? "§c"
                                                                                                                        : "")
                                                                                                        + getEnergyUsage()
                                                                                                        + "§f FE/tick")),
                                        mouseX,
                                        mouseY);

        }

        // fluid bar

        default void renderFluidTank(GuiGraphicsExtractor guiGraphics, int index, int xo, int yo, int mouseX,
                        int mouseY) {
                guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                                x.rl(MODULE_ID, "textures/gui/container/slot/fluid/base.png"),
                                getLeftPos() + xo, getTopPos() + yo,
                                0, 0,
                                18, 72,
                                36, 72);

                if (getMaxFluidAmount(index) > 0 && getFluidAmount(index) > 0 && getFluid(index) != null)
                        FluidGUITank.of()
                                        .setFluid(getFluid(index))
                                        .setMaxCapacity(getMaxFluidAmount(index))
                                        .setAmount(getFluidAmount(index))
                                        .size(16, 70)
                                        .offset(getLeftPos() + xo + 1, getTopPos() + yo + 1)
                                        .render(guiGraphics);

                guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                                x.rl(MODULE_ID, "textures/gui/container/slot/fluid/base.png"),
                                getLeftPos() + xo, getTopPos() + yo,
                                18, 0,
                                18, 72,
                                36, 72);

                if (isDebugHover(xo, yo, 18, 72, mouseX, mouseY))
                        guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                                        x.rl(MODULE_ID, "textures/gui/container/slot/fluid/debug.png"),
                                        getLeftPos() + xo, getTopPos() + yo,
                                        0, 0,
                                        18, 72,
                                        18, 72);
        }

        default void renderFluidTooltip(GuiGraphicsExtractor graphics, int index, int x, int y, int x0, int y0,
                        int mouseX,
                        int mouseY) {
                if (getDebug())
                        renderDebugSlotTooltip(graphics, x, y, x0, y0, "fluid.debug", mouseX, mouseY);
                else if (Pos.of(getLeftPos() + x, getTopPos() + y).setSize(x0, y0).test(mouseX, mouseY))
                        graphics.setComponentTooltipForNextFrame(getFont(), List.of(Component.literal(
                                        (ClientUtils.hasShiftDown()
                                                        ? String.valueOf(getFluidAmount(
                                                                        index))
                                                        : StringUtil.getFormatNoRound()
                                                                        .format(getFluidAmount(
                                                                                        index)))
                                                        + " mB / "
                                                        + (ClientUtils.hasShiftDown()
                                                                        ? String.valueOf(
                                                                                        getFluidAmount(
                                                                                                        index))
                                                                        : StringUtil.getFormatNoRound()
                                                                                        .format(getFluidAmount(
                                                                                                        index)))
                                                        + " mB"),
                                        Component.literal("Fluid: ")
                                                        .append(getFluidStack(index).getHoverName())),
                                        mouseX,
                                        mouseY);

        }

        // misc

        private void renderDebugSlotTooltip(GuiGraphicsExtractor graphics, int xo, int yo, int width, int height,
                        String tooltipKey, int mouseX, int mouseY) {

                if (getDebug())
                        if (Pos.of(getLeftPos() + xo, getTopPos() + yo).setSize(width, height).test(mouseX, mouseY))
                                graphics.setComponentTooltipForNextFrame(getFont(),
                                                List.of(Component.translatable(MODULE_ID + ".gui." + tooltipKey)),
                                                mouseX, mouseY);
        }

        private void renderSlotTooltip(GuiGraphicsExtractor graphics, int xo, int yo, String tooltipKey, int mouseX,
                        int mouseY) {
                renderDebugSlotTooltip(graphics, xo + 7 - 8, yo + 7 - 8, 18, 18, tooltipKey, mouseX, mouseY);
        }

        private void renderLargeSlotTooltip(GuiGraphicsExtractor graphics, int xo, int yo, String tooltipKey,
                        int mouseX,
                        int mouseY) {
                renderDebugSlotTooltip(graphics, xo + 7 + 4 - 16, yo + 7 + 4 - 16, 26, 26, tooltipKey, mouseX, mouseY);
        }

        private boolean isDebugHover(int xo, int yo, int width, int height, int mouseX, int mouseY) {
                return getDebug() && Pos.of(getLeftPos() + xo, getTopPos() + yo).setSize(width, height).test(mouseX,
                                mouseY);
        }

}
