package com.synergy.machines.api.machine;

import static com.synergy.machines.Main.MODULE_ID;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.devdyna.cakesticklib.api.gui.BaseScreen;
import com.devdyna.cakesticklib.api.primitive.Pos;
import com.devdyna.cakesticklib.api.primitive.Size;
import com.devdyna.cakesticklib.api.upgrades.ScreenUpgradable;
import com.devdyna.cakesticklib.api.upgrades.UpgradeComponents;
import com.devdyna.cakesticklib.api.utils.ClientUtils;
import com.devdyna.cakesticklib.api.utils.UpgradeSlotBuilder;
import com.devdyna.cakesticklib.api.utils.x;
import com.synergy.machines.api.DebugButton;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;

public abstract class BaseMachineScreen<T extends BaseMachineMenu> extends BaseScreen<T>
                implements MachineWidgets, ScreenUpgradable {

        private boolean DEBUG = false;

        public BaseMachineScreen(T menu, Inventory playerInventory, Component title) {
                super(menu, playerInventory, title);
        }

        @Override
        protected void init() {

                super.init();

                DEBUG = false;

                var button = new DebugButton(getLeftPos() + 176, getTopPos() + 83 + 5,
                                21, 21,
                                b -> {
                                        DEBUG = !DEBUG;
                                        ((DebugButton) b).update(DEBUG);

                                        ((DebugButton) b).updateTooltip( (DEBUG ? "active" : "deactive"));
                                });

                        button.updateTooltip( (DEBUG ? "active" : "deactive"));

                addRenderableWidget(button);

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
        protected Identifier background() {// TODO TO FIX
                return x.rl(MODULE_ID, "textures/gui/container/" + menu.getMachine().id() + ".png");
        }

        @Override
        @Nullable
        protected Identifier arrow() {// TODO blitSprite -> blit
                return x.rl(MODULE_ID, "textures/gui/container/progress/on.png");
        }

        protected boolean whenAnimateArrow() {
                return menu.isCrafting();
        }

        protected int getScaledArrowProgress() {
                return menu.getScaledArrowProgress();
        }

        public int getEnergyStored() {
                return menu.getEnergyStored();
        }

        public int getMaxEnergy() {
                return menu.getMaxEnergy();
        }

        public int getRemainProgress() {
                return menu.getRemainProgress();
        }

        public int getFluidAmount(int i) {
                return menu.getFluidAmount(i);
        }

        public Fluid getFluid(int i) {
                return menu.getFluid(i);
        }

        public FluidStack getFluidStack(int i) {
                return menu.getFluidStack(i);
        }

        public int getMaxFluidAmount(int i) {
                return menu.getMaxFluidAmount(i);
        }

        public int getEnergyUsage() {
                return menu.getEnergyUsage();
        }

        @Override
        public Font getFont() {
                return font;
        }

        @Override
        public boolean getDebug() {
                return DEBUG;
        }

        @Override
        public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float a) {
                renderRightLabel(guiGraphics, 172, 0, mouseX, mouseY);
                renderUpgradeSlots(guiGraphics, 172, 0, mouseX, mouseY);
                renderMachineInventory(guiGraphics, 0, 0, mouseX, mouseY);

                this.renderArrow(guiGraphics);

                renderTickProgress(guiGraphics, 68, 70, mouseX, mouseY);
                renderEnergyStorage(guiGraphics, 8, 5, mouseX, mouseY);
        }

        @Override
        protected void renderArrow(GuiGraphicsExtractor guiGraphics) {

                guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                                x.rl(MODULE_ID, "textures/gui/container/progress/off.png"),
                                this.getLeftPos() + 73, this.getTopPos() + 35,
                                0, 0,
                                24, 16,
                                24, 16);

                if (this.whenAnimateArrow()) {
                        guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                                        x.rl(MODULE_ID, "textures/gui/container/progress/on.png"),
                                        this.getLeftPos() + 73, this.getTopPos() + 35,
                                        0, 0,
                                        getScaledArrowProgress(), 16,
                                        24, 16);
                }
        }

        @Override
        public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {

                this.extractContents(graphics, mouseX, mouseY, a);
                this.extractCarriedItem(graphics, mouseX, mouseY);
                this.extractSnapbackItem(graphics);

                if (!DEBUG)
                        this.extractTooltip(graphics, mouseX, mouseY);

                renderEnergyTooltip(graphics, 8, 5, 18, 72, mouseX, mouseY);

                // if (DEBUG)
                // renderToolTips(graphics, mouseX, mouseY);

                if (DEBUG)
                        renderToolTips(graphics, mouseX, mouseY, true);

                renderMachineInventoryTooltip(graphics, 0, 0, mouseX, mouseY);

        }

        // TODO flag to empty slot

        public void renderToolTips(GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean flag) {
                this.getSlotBuilder().getAll().forEach((k, v) -> {
                        if (flag && v.getPos().test(mouseX, mouseY)) {
                                graphics.setComponentTooltipForNextFrame(this.getFont(),
                                                this.calculateTooltipUpgrades(), mouseX, mouseY);
                        }

                });
        }

        // TODO API : private -> default
        private List<Component> calculateTooltipUpgrades() {
                List<Component> result = new ArrayList<>();
                result.add(Component.translatable("cakesticklib.screen.upgrades"));
                var var2 = this.validUpgrades().iterator();

                while (var2.hasNext()) {
                        UpgradeComponents.UpgradeType upgrade = (UpgradeComponents.UpgradeType) var2.next();
                        result.add(Component
                                        .translatable("cakesticklib.screen.modifier." + upgrade.name().toLowerCase(),
                                                        new Object[] { this.getConfigLimits(upgrade) })
                                        .withStyle(this.getConfigLimits(upgrade) > this
                                                        .getInstalledUpgradesOnSlots(upgrade)
                                                                        ? ChatFormatting.GREEN
                                                                        : (this.getConfigLimits(upgrade) < this
                                                                                        .getInstalledUpgradesOnSlots(
                                                                                                        upgrade) ? ChatFormatting.RED
                                                                                                                        : ChatFormatting.YELLOW)));
                }

                return result;
        }

        @Override
        protected void extractLabels(GuiGraphicsExtractor graphics, int xm, int ym) {
                graphics.text(this.font, this.title,
                                this.titleLabelX + getContainerTitlePos().getX(),
                                this.titleLabelY + getContainerTitlePos().getY(),
                                ClientUtils.defaultToolTipColor.getRGB(), false);
        }

        public Size getContainerTitlePos() {
                return Size.of(57, 0);
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
