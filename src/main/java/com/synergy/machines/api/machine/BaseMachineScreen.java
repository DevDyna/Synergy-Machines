package com.synergy.machines.api.machine;

import static com.synergy.machines.Main.MODULE_ID;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

import com.devdyna.cakesticklib.api.gui.BaseScreen;
import com.devdyna.cakesticklib.api.primitive.Pos;
import com.devdyna.cakesticklib.api.utils.StringUtil;
import com.devdyna.cakesticklib.api.utils.UpgradeComponents;
import com.devdyna.cakesticklib.api.utils.UpgradeComponents.UpgradeType;
import com.devdyna.cakesticklib.api.utils.x;
import com.synergy.machines.Common;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

@SuppressWarnings("null")
public abstract class BaseMachineScreen<T extends BaseMachineMenu> extends BaseScreen<T> {

        public BaseMachineScreen(T menu, Inventory playerInventory, Component title) {
                super(menu, playerInventory, title);
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
                                x.rl(MODULE_ID, "textures/gui/container/left_label.png"),
                                getLeftPos() - 30,
                                getTopPos(),
                                0, 0,
                                32, 86,
                                32, 86);
        }

        protected void renderEnergyStorage(GuiGraphicsExtractor guiGraphics, int xo, int yo) {

                guiGraphics.blit(
                                x.rl(MODULE_ID, "textures/gui/container/energy.png"),
                                getLeftPos() + xo,
                                getTopPos() + yo,
                                0, 0,
                                18, 72,
                                36, 72);

                if (getMaxEnergy() > 0 && getEnergyStored() > 0) {

                        int slice = (getEnergyStored() * 72) / getMaxEnergy();

                        guiGraphics.blit(
                                        x.rl(MODULE_ID, "textures/gui/container/energy.png"),
                                        getLeftPos() + xo,
                                        getTopPos() + yo + (72 - slice),
                                        18, 72 - slice,
                                        18, slice,
                                        36, 72);
                }

        }

        protected void renderFluidTank(GuiGraphicsExtractor guiGraphics, int xo, int yo) {

                guiGraphics.blit(x.rl(MODULE_ID, "textures/gui/container/fluid_widget.png"),
                                getLeftPos() + xo,
                                getTopPos() + yo,
                                0, 0,
                                18, 72,
                                36, 72);

                if (getMaxFluidAmount() > 0 && getFluidAmount() > 0)
                        FluidGUITank.of()
                                        .setFluid(getFluid())
                                        .setMaxCapacity(getMaxFluidAmount())
                                        .setAmount(getFluidAmount())
                                        .size(72, 16)
                                        .offset(getLeftPos() + xo + 1, getTopPos() + yo - 1)
                                        .render(guiGraphics);

                guiGraphics.blit(x.rl(MODULE_ID, "textures/gui/container/fluid_widget.png"),
                                getLeftPos() + xo,
                                getTopPos() + yo,
                                18, 0,
                                18, 72,
                                36, 72);

        }

        @Override
        public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float a) {

                renderUpgradesLabel(guiGraphics, 172, 0);

                super.extractBackground(guiGraphics, mouseX, mouseY, a);

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

        public boolean hasShiftDown() {
                return Minecraft.getInstance().hasShiftDown();
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

                if (menu.getSlot(BaseMachineBE.SLOT_UPGRADE_1).getItem().isEmpty())
                        if (Pos.of(getLeftPos() + 179, getTopPos() + 7).setSize(18, 18).test(mouseX, mouseY))
                                graphics.setComponentTooltipForNextFrame(font, calculateTooltipUpgrades(), mouseX,
                                                mouseY);

                if (menu.getSlot(BaseMachineBE.SLOT_UPGRADE_2).getItem().isEmpty())
                        if (Pos.of(getLeftPos() + 179, getTopPos() + 7 + 18).setSize(18, 18).test(mouseX, mouseY))
                                graphics.setComponentTooltipForNextFrame(font, calculateTooltipUpgrades(), mouseX,
                                                mouseY);

                if (menu.getSlot(BaseMachineBE.SLOT_UPGRADE_3).getItem().isEmpty())
                        if (Pos.of(getLeftPos() + 179, getTopPos() + 7 + 18 + 18).setSize(18, 18).test(mouseX, mouseY))
                                graphics.setComponentTooltipForNextFrame(font, calculateTooltipUpgrades(), mouseX,
                                                mouseY);

                if (menu.getSlot(BaseMachineBE.SLOT_UPGRADE_4).getItem().isEmpty())
                        if (Pos.of(getLeftPos() + 179, getTopPos() + 7 + 18 + 18 + 18).setSize(18, 18).test(mouseX,
                                        mouseY))
                                graphics.setComponentTooltipForNextFrame(font, calculateTooltipUpgrades(), mouseX,
                                                mouseY);

        }

        @Override
        protected void extractLabels(GuiGraphicsExtractor graphics, int xm, int ym) {
                graphics.text(this.font, this.title, this.titleLabelX + 57, this.titleLabelY,
                                defaultToolTipColor.getRGB(), false);
        }

        private List<Component> calculateTooltipUpgrades() {
                List<Component> result = new ArrayList<>();

                result.add(Component.translatable(MODULE_ID + ".screen.upgrades"));
                for (UpgradeType upgrade : validUpgrades())
                        result.add(Component
                                        .translatable(MODULE_ID + ".screen.modifier." + upgrade.name().toLowerCase(),
                                                        getConfigLimits(upgrade))
                                        .withStyle(getConfigLimits(upgrade) > getInstalledUpgradesOnSlots(upgrade)
                                                        ? ChatFormatting.GREEN
                                                        : (getConfigLimits(upgrade) < getInstalledUpgradesOnSlots(
                                                                        upgrade) ? ChatFormatting.RED
                                                                                        : ChatFormatting.YELLOW)));

                return result;
        }

        public List<UpgradeType> validUpgrades() {
                return DEFAULT_UPGRADES;
        }

        public static final List<UpgradeType> DEFAULT_UPGRADES = List.of(UpgradeType.ENERGY, UpgradeType.SPEED);

        public int getConfigLimits(UpgradeType type) {
                return switch (type) {
                        case UpgradeType.ENERGY ->   Common.MACHINE_MAX_ENERGY_EFFICIENCY_UPGRADES_TYPE.get();
                        case UpgradeType.SPEED ->  Common.MACHINE_MAX_SPEED_UPGRADES_TYPE.get();
                        case UpgradeType.LUCK ->  Common.MACHINE_MAX_LUCK_UPGRADES_TYPE.get();
                        case UpgradeType.FLUID ->  Common.MACHINE_MAX_FLUID_UPGRADES_TYPE.get();
                        default -> 0;
                };
        }

        public int getInstalledUpgradesOnSlots(UpgradeType type) {
                return List.of(
                                BaseMachineBE.SLOT_UPGRADE_1,
                                BaseMachineBE.SLOT_UPGRADE_2,
                                BaseMachineBE.SLOT_UPGRADE_3,
                                BaseMachineBE.SLOT_UPGRADE_4)
                                .stream()
                                .map(menu::getSlot)
                                .map(Slot::getItem)
                                .filter(item -> UpgradeComponents.has(item, type))
                                .mapToInt(ItemStack::getCount)
                                .sum();
        }

}
