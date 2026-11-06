package com.synergy.machines.api.machine;

import static com.synergy.machines.Main.MODULE_ID;

import java.util.List;

import com.devdyna.cakesticklib.api.datagen.LangUtils.TipColors;
import com.devdyna.cakesticklib.api.primitive.Pos;
import com.devdyna.cakesticklib.api.utils.ClientUtils;
import com.devdyna.cakesticklib.api.utils.StringUtil;
import com.devdyna.cakesticklib.api.utils.x;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public interface MachineWidgets<T extends BaseMachineMenu> {

        int getLeftPos();

        int getTopPos();

        Font getFont();

        T getMenu();

        boolean getDebug();

        // ticker render

        default void renderTickProgress(GuiGraphicsExtractor guiGraphics, int xo, int yo, int mouseX, int mouseY) {
                if (getDebug())
                        if (getMenu().getRemainProgress() > 0)
                                guiGraphics.text(getFont(),
                                                Component.literal((1 + getMenu().getRemainProgress()) + " ticks"),
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

                renderDebugSlotTooltip(guiGraphics, mouseX, mouseY, xo + 5, yo + 81, 167, 82, "inventory.debug");

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

                if (isDebugHover(xo + 7 - 3, yo + 7 - 3, 24, 78, mouseX, mouseY))
                        guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                                        x.rl(MODULE_ID, "textures/gui/container/slot/upgrade/debug.png"),
                                        getLeftPos() + 7 - 3 + xo,
                                        getTopPos() + 7 - 3 + yo,
                                        0, 0,
                                        24, 78,
                                        24, 78);

                renderDebugSlotTooltip(guiGraphics, mouseX, mouseY, xo + 7 - 3, yo + 7 - 3, 24, 78, "upgrade.debug");

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

                renderSlotTooltip(guiGraphics, xo, yo, "slot.input.debug", mouseX, mouseY);

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

                renderLargeSlotTooltip(guiGraphics, xo, yo, "slot.input.debug", mouseX, mouseY);

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

                renderSlotTooltip(guiGraphics, xo, yo, "slot.output.debug", mouseX, mouseY);

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

                renderLargeSlotTooltip(guiGraphics, xo, yo, "slot.output.debug", mouseX, mouseY);

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

                renderSlotTooltip(guiGraphics, xo, yo, "slot.extra.debug", mouseX, mouseY);

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

                renderLargeSlotTooltip(guiGraphics, xo, yo, "slot.extra.debug", mouseX, mouseY);

        }

        // energy bar

        default void renderEnergyStorage(GuiGraphicsExtractor guiGraphics, int xo, int yo, int mouseX, int mouseY) {
                guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                                x.rl(MODULE_ID, "textures/gui/container/slot/energy/base.png"),
                                getLeftPos() + xo, getTopPos() + yo,
                                0, 0,
                                18, 72,
                                36, 72);

                if (getMenu().getMaxEnergy() > 0 && getMenu().getEnergyStored() > 0) {
                        var slice = Math.min(72, (getMenu().getEnergyStored() * 72) / getMenu().getMaxEnergy());
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

                if (getDebug())
                        renderDebugSlotTooltip(guiGraphics, mouseX, mouseY, xo, yo, 18, 72, "energy.debug");
                else if (Pos.of(getLeftPos() + xo, getTopPos() + yo).setSize(18, 72).test(mouseX, mouseY))
                        guiGraphics.setComponentTooltipForNextFrame(
                                        getFont(), List.of(Component.literal(
                                                        (Minecraft.getInstance().hasShiftDown()
                                                                        ? String.valueOf(
                                                                                        getMenu().getEnergyStored())
                                                                        : StringUtil.getFormatNoRound()
                                                                                        .format(getMenu()
                                                                                                        .getEnergyStored()))
                                                                        + " FE / "
                                                                        + (Minecraft.getInstance()
                                                                                        .hasShiftDown()
                                                                                                        ? String.valueOf(
                                                                                                                        getMenu().getMaxEnergy())
                                                                                                        : StringUtil.getFormatNoRound()
                                                                                                                        .format(getMenu()
                                                                                                                                        .getMaxEnergy()))
                                                                        + " FE"),
                                                        Component.literal(
                                                                        getMenu().getEnergyUsage() <= 0
                                                                                        ? "No valid recipe found"
                                                                                        : "Usage : "
                                                                                                        + (getMenu().getMaxEnergy() <= getMenu()
                                                                                                                        .getEnergyUsage()
                                                                                                                                        ? "§c"
                                                                                                                                        : "")
                                                                                                        + getMenu().getEnergyUsage()
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

                if (getMenu().getMaxFluidAmount(index) > 0 && getMenu().getFluidAmount(index) > 0
                                && getMenu().getFluid(index) != null)
                        FluidGUITank.of()
                                        .setFluid(getMenu().getFluid(index))
                                        .setMaxCapacity(getMenu().getMaxFluidAmount(index))
                                        .setAmount(getMenu().getFluidAmount(index))
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

                if (getDebug())
                        renderDebugSlotTooltip(guiGraphics, mouseX, mouseY, xo, yo, 18, 72, "fluid.debug");
                else if (Pos.of(getLeftPos() + xo, getTopPos() + yo).setSize(18, 72).test(mouseX, mouseY))
                        guiGraphics.setComponentTooltipForNextFrame(getFont(), List.of(Component.literal(
                                        (ClientUtils.hasShiftDown()
                                                        ? String.valueOf(getMenu().getFluidAmount(
                                                                        index))
                                                        : StringUtil.getFormatNoRound()
                                                                        .format(getMenu().getFluidAmount(
                                                                                        index)))
                                                        + " mB / "
                                                        + (ClientUtils.hasShiftDown()
                                                                        ? String.valueOf(
                                                                                        getMenu().getFluidAmount(
                                                                                                        index))
                                                                        : StringUtil.getFormatNoRound()
                                                                                        .format(getMenu()
                                                                                                        .getFluidAmount(
                                                                                                                        index)))
                                                        + " mB"),
                                        Component.literal("Fluid: ")
                                                        .append(getMenu().getFluidStack(index).getHoverName())),
                                        mouseX,
                                        mouseY);
        }

        public enum ProgressColor {
                BLUE(0),
                GREEN(1),
                ORANGE(2),
                PINK(3),
                RED(4);

                private int v;

                private ProgressColor(int v) {
                        this.v = v;
                }

                public int getUV() {
                        return v;
                }
        }

        default void renderProgressBar(GuiGraphicsExtractor graphics, int mouseX, int mouseY, int xo, int yo,
                        ProgressColor c, boolean whenRender, int scaledProgress, String id, int getRemainProgress) {

                graphics.blit(RenderPipelines.GUI_TEXTURED,
                                x.rl(MODULE_ID, "textures/gui/container/slot/progress/base.png"),
                                getLeftPos() + xo, getTopPos() + yo,
                                0, 0,
                                6, 72,
                                36, 72);

                if (whenRender) {

                        graphics.blit(RenderPipelines.GUI_TEXTURED,
                                        x.rl(MODULE_ID, "textures/gui/container/slot/progress/base.png"),
                                        getLeftPos() + xo, getTopPos() + yo + (72 - scaledProgress),
                                        (c.getUV() + 1) * 6, 72 - scaledProgress,
                                        6, scaledProgress,
                                        36, 72);

                }

                if (isDebugHover(xo - 1, yo, 8, 72, mouseX, mouseY))
                        graphics.blit(RenderPipelines.GUI_TEXTURED,
                                        x.rl(MODULE_ID, "textures/gui/container/slot/progress/debug.png"),
                                        getLeftPos() + xo - 1, getTopPos() + yo,
                                        c.getUV() * 8, 72,
                                        8, 72,
                                        40, 72);

                renderDebugSlotTooltip(graphics, mouseX, mouseY, 8, 72, xo, yo, List.of(
                                Component.translatable(MODULE_ID + ".gui.progress." + id + ".title"),
                                Component.translatable(MODULE_ID + ".gui.progress.eta",
                                                (getRemainProgress <= 0
                                                                ? TipColors.YELLOW + "Standby"
                                                                : TipColors.GREEN + getRemainProgress
                                                                                + " ticks"))),
                                "progress." + id + ".debug");
        }

        // misc

        private void renderDebugSlotTooltip(GuiGraphicsExtractor graphics, int mouseX, int mouseY, int width,
                        int height, int xo, int yo, List<Component> tooltip, String debug) {
                if (getDebug())
                        renderDebugSlotTooltip(graphics, mouseX, mouseY, xo, yo, width, height, debug);
                else if (Pos.of(getLeftPos() + xo, getTopPos() + yo).setSize(width, height).test(mouseX, mouseY))
                        graphics.setComponentTooltipForNextFrame(getFont(), tooltip, mouseX, mouseY);
        }

        private void renderDebugSlotTooltip(GuiGraphicsExtractor graphics, int mouseX, int mouseY, int xo, int yo,
                        int width, int height,
                        String tooltipKey) {

                if (getDebug())
                        if (Pos.of(getLeftPos() + xo, getTopPos() + yo).setSize(width, height).test(mouseX, mouseY))
                                graphics.setComponentTooltipForNextFrame(getFont(),
                                                List.of(Component.translatable(MODULE_ID + ".gui." + tooltipKey)),
                                                mouseX, mouseY);
        }

        private void renderSlotTooltip(GuiGraphicsExtractor graphics, int xo, int yo, String tooltipKey, int mouseX,
                        int mouseY) {
                renderDebugSlotTooltip(graphics, mouseX, mouseY, xo + 7 - 8, yo + 7 - 8, 18, 18, tooltipKey);
        }

        private void renderLargeSlotTooltip(GuiGraphicsExtractor graphics, int xo, int yo, String tooltipKey,
                        int mouseX,
                        int mouseY) {
                renderDebugSlotTooltip(graphics, mouseX, mouseY, xo + 7 + 4 - 16, yo + 7 + 4 - 16, 26, 26, tooltipKey);
        }

        default boolean isDebugHover(int xo, int yo, int width, int height, int mouseX, int mouseY) {
                return getDebug() && Pos.of(getLeftPos() + xo, getTopPos() + yo).setSize(width, height).test(mouseX,
                                mouseY);
        }

        default void renderStaticTexture(GuiGraphicsExtractor graphics, Identifier rl, int xo, int yo, int width,
                        int height) {
                graphics.blit(RenderPipelines.GUI_TEXTURED, rl, getLeftPos() + xo, getTopPos() + yo, 0, 0, width,
                                height, width, height);
        }

}
