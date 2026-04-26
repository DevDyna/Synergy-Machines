package com.synergy.machines.api.machine;

import static com.synergy.machines.Main.MODULE_ID;

import java.util.List;
import javax.annotation.Nullable;

import com.devdyna.cakesticklib.api.gui.BaseScreen;
import com.devdyna.cakesticklib.api.gui.ScreenUpgradable;
import com.devdyna.cakesticklib.api.primitive.Pos;
import com.devdyna.cakesticklib.api.utils.StringUtil;
import com.devdyna.cakesticklib.api.utils.UpgradeSlotBuilder;
import com.devdyna.cakesticklib.api.utils.x;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.material.Fluid;

@SuppressWarnings("null")
public abstract class BaseMachineScreen<T extends BaseMachineMenu> extends BaseScreen<T> implements ScreenUpgradable {

        public BaseMachineScreen(T menu, Inventory playerInventory, Component title) {
                super(menu, playerInventory, title);
        }

        @Override
        protected void containerTick() {
                super.containerTick();
                var be = this.menu.getBlockEntity();
                if (be == null || be.getLevel() == null)
                        return;

                be.setChanged();

                if (!be.getLevel().isClientSide())
                        be.getLevel().sendBlockUpdated(
                                        be.getBlockPos(),
                                        be.getBlockState(),
                                        be.getBlockState(),
                                        3);

        }

        @Override
        protected Identifier background() {
                return x.rl(MODULE_ID, "textures/gui/container/" + menu.getMachine().id() + ".png");
        }

        @Override
        protected @Nullable Identifier arrow() {
                return x.rl("minecraft", "textures/gui/sprites/container/furnace/burn_progress.png");
        }

        protected boolean whenAnimateArrow() {
                return menu.isCrafting();
        }

        protected int getScaledArrowProgress() {
                return menu.getScaledArrowProgress();
        }

        protected int getEnergyStored() {
                return menu.getEnergyStored();
        }

        protected int getMaxEnergy() {
                return menu.getMaxEnergy();
        }

        protected int getRemainProgress() {
                return menu.getRemainProgress();
        }

        protected int getFluidAmount() {
                return menu.getFluidAmount();
        }

        protected Fluid getFluid() {
                return menu.getFluid();
        }

        protected int getMaxFluidAmount() {
                return menu.getMaxFluidAmount();
        }

        protected int getEnergyUsage() {
                return menu.getEnergyUsage();
        }

        protected void renderUpgradesLabel(GuiGraphicsExtractor guiGraphics, int xo, int yo) {
                guiGraphics.blit(
                                RenderPipelines.GUI_TEXTURED,
                                x.rl(MODULE_ID, "textures/gui/container/upgrade_slots.png"),
                                getLeftPos() + xo,
                                getTopPos() + yo,
                                0, 0,
                                32, 86,
                                32, 86);
        }

        protected void renderTickProgress(GuiGraphicsExtractor guiGraphics, int xo, int yo) {
                if (getRemainProgress() > 0)
                        guiGraphics.text(font, Component.literal((1 + getRemainProgress()) + " ticks"),
                                        getLeftPos() + xo,
                                        getTopPos() + yo,
                                        defaultToolTipColor.getRGB(), false);
        }

        protected void renderLeftLabel(GuiGraphicsExtractor guiGraphics) {
                guiGraphics.blit(
                                RenderPipelines.GUI_TEXTURED,
                                x.rl(MODULE_ID, "textures/gui/container/left_label.png"),
                                getLeftPos() - 30,
                                getTopPos(),
                                0, 0,
                                32, 86,
                                32, 86);
        }

        protected void renderEnergyStorage(GuiGraphicsExtractor guiGraphics, int xo, int yo) {

                int x0 = getLeftPos() + xo;
                int y0 = getTopPos() + yo;

                guiGraphics.blit(
                                RenderPipelines.GUI_TEXTURED,
                                x.rl(MODULE_ID, "textures/gui/container/energy.png"),
                                x0, y0,
                                0, 0,
                                18, 72,
                                36, 72);

                if (getMaxEnergy() > 0 && getEnergyStored() > 0) {

                        int slice = Math.min(72, (getEnergyStored() * 72) / getMaxEnergy());

                        guiGraphics.blit(
                                        RenderPipelines.GUI_TEXTURED,
                                        x.rl(MODULE_ID, "textures/gui/container/energy.png"),
                                        x0,
                                        y0 + (72 - slice),
                                        18,
                                        72 - slice,
                                        18,
                                        slice,
                                        36,
                                        72);
                }
        }

        protected void renderFluidTank(GuiGraphicsExtractor guiGraphics, int xo, int yo) {

                int x0 = getLeftPos() + xo;
                int y0 = getTopPos() + yo;

                guiGraphics.blit(
                                RenderPipelines.GUI_TEXTURED,
                                x.rl(MODULE_ID, "textures/gui/container/fluid_widget.png"),
                                x0, y0,
                                0, 0,
                                18, 72,
                                36, 72);

                if (getMaxFluidAmount() > 0 && getFluidAmount() > 0) {
                        FluidGUITank.of()
                                        .setFluid(getFluid())
                                        .setMaxCapacity(getMaxFluidAmount())
                                        .setAmount(getFluidAmount())
                                        .size(16, 70)
                                        .offset(x0 + 1, y0 + 1)
                                        .render(guiGraphics);
                }

                guiGraphics.blit(
                                RenderPipelines.GUI_TEXTURED,
                                x.rl(MODULE_ID, "textures/gui/container/fluid_widget.png"),
                                x0, y0,
                                18, 0,
                                18, 72,
                                36, 72);
        }

        @Override
        public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float a) {

                guiGraphics.blit(
                                RenderPipelines.GUI_TEXTURED,
                                background(),
                                getLeftPos(),
                                getTopPos(),
                                0, 0,
                                175, 165,
                                256, 256);
                this.renderArrow(guiGraphics);

                renderUpgradesLabel(guiGraphics, 172, 0);
                renderTickProgress(guiGraphics, 68, 70);
                renderEnergyStorage(guiGraphics, 8, 5);
        }

        public void renderFluidTooltip(GuiGraphicsExtractor graphics, int x, int y, int x0, int y0, int mouseX,
                        int mouseY) {

                renderDualTooltip(graphics, (hasShiftDown() ? getFluidAmount()
                                : StringUtil.getFormatNoRound()
                                                .format(getFluidAmount()))
                                + " mB / " +
                                (hasShiftDown() ? getMaxFluidAmount()
                                                : StringUtil.getFormatNoRound()
                                                                .format(getMaxFluidAmount()))
                                + " mB",
                                "Fluid: " + getFluid().getFluidType()
                                                .getDescription().getString(),
                                x, y, x0, y0, mouseX, mouseY);

        }

        private void renderDualTooltip(GuiGraphicsExtractor graphics, String first, String second, int x, int y, int x0,
                        int y0, int mouseX,
                        int mouseY) {
                if (Pos.of(getLeftPos() + x, getTopPos() + y).setSize(x0, y0).test(mouseX, mouseY))

                        graphics.setComponentTooltipForNextFrame(font,
                                        List.of(
                                                        Component.literal(first),
                                                        Component.literal(second)),
                                        mouseX,
                                        mouseY);
        }

        public void renderEnergyTooltip(GuiGraphicsExtractor graphics, int x, int y, int x0, int y0, int mouseX,
                        int mouseY) {

                renderDualTooltip(graphics,
                                (Minecraft.getInstance().hasShiftDown()
                                                ? getEnergyStored()
                                                : StringUtil.getFormatNoRound()
                                                                .format(getEnergyStored()))
                                                + " FE / " +
                                                (Minecraft.getInstance()
                                                                .hasShiftDown()
                                                                                ? getMaxEnergy()
                                                                                : StringUtil.getFormatNoRound()
                                                                                                .format(getMaxEnergy()))
                                                + " FE",
                                getEnergyUsage() <= 0 ? "No valid recipe found"
                                                : ("Usage : " + (getMaxEnergy() <= getEnergyUsage()
                                                                ? "§c"
                                                                : "")
                                                                + getEnergyUsage()
                                                                + "§f FE/tick"),
                                x, y, x0, y0, mouseX, mouseY);

        }

        @Override
        public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
                super.extractRenderState(graphics, mouseX, mouseY, a);

                renderEnergyTooltip(graphics, 8, 5, 18, 72, mouseX, mouseY);

                renderToolTips(graphics, mouseX, mouseY);

        }

        @Override
        protected void extractLabels(GuiGraphicsExtractor graphics, int xm, int ym) {
                graphics.text(this.font, this.title, this.titleLabelX + 57, this.titleLabelY,
                                defaultToolTipColor.getRGB(), false);
        }

        @Override
        public UpgradeSlotBuilder getSlotBuilder() {
                return UpgradeSlotBuilder
                                .of()
                                .set(0, menu.getSlot(BaseMachineBE.SLOT_UPGRADE_1).getItem(),
                                                Pos.of(getLeftPos() + 179, getTopPos() + 7)
                                                                .setSize(18, 18))
                                .set(1, menu.getSlot(BaseMachineBE.SLOT_UPGRADE_2).getItem(),
                                                Pos.of(getLeftPos() + 179, getTopPos() + 25)
                                                                .setSize(18, 18))
                                .set(2, menu.getSlot(BaseMachineBE.SLOT_UPGRADE_3).getItem(),
                                                Pos.of(getLeftPos() + 179, getTopPos() + 43)
                                                                .setSize(18, 18))
                                .set(3, menu.getSlot(BaseMachineBE.SLOT_UPGRADE_4).getItem(),
                                                Pos.of(getLeftPos() + 179, getTopPos() + 61)
                                                                .setSize(18, 18));
        }

}
