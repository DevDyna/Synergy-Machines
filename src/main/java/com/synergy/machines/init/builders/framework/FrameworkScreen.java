package com.synergy.machines.init.builders.framework;

import static com.synergy.machines.Main.MODULE_ID;

import com.devdyna.cakesticklib.api.primitive.Size;
import com.devdyna.cakesticklib.api.utils.x;
import com.synergy.machines.api.machine.BaseMachineScreen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class FrameworkScreen extends BaseMachineScreen<FrameworkMenu> {

        public FrameworkScreen(FrameworkMenu menu, Inventory playerInventory, Component title) {
                super(menu, playerInventory, title);
        }

        @Override
        public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {

                super.extractBackground(graphics, mouseX, mouseY, a);

                renderInputSlot(graphics, 43, 8, mouseX, mouseY);

                // renderExtraSlot(graphics, 20 + 43, 8, mouseX, mouseY);

                renderStaticTexture(graphics,
                                x.rl(MODULE_ID, "textures/gui/container/slot/tiny_arrow.png"),
                                4 + 43, 31 - 4 - 2,
                                7, 7);

                for (var i = 0; i < FrameworkBE.MACHINE_SLOTS.size(); i++) {
                        renderExtraSlot(graphics,
                                        43 + i * 27,
                                        35,
                                        mouseX, mouseY);

                        renderStaticTexture(graphics,
                                        x.rl(MODULE_ID, "textures/gui/container/slot/tiny_arrow.png"),
                                        4 + 43 + i * 27, 35 - 4 + 20,
                                        7, 7);

                        // ignore last slot
                        if (i + 1 >= FrameworkBE.MACHINE_SLOTS.size())
                                break;

                        renderStaticTexture(graphics,
                                        x.rl(MODULE_ID, "textures/gui/container/slot/small_arrow.png"),
                                        20 - 2 + 43 + i * 27, 60 - 21,
                                        7, 7);

                        if (!menu.getSlot(FrameworkBE.MACHINE_SLOTS.get(i) + 40 - 3).hasItem())
                                renderStaticTexture(graphics,
                                                x.rl(MODULE_ID, "textures/gui/container/slot/red_cross.png"),
                                                20 - 2 + 43 + i * 27, 60 - 21,
                                                7, 7);

                }

                for (var i = 0; i < FrameworkBE.OUTPUT_SLOTS.size(); i++) {
                        renderOutputSlot(graphics,
                                        43 + i * 27,
                                        60,
                                        mouseX, mouseY);

                }

                renderProgressBar(graphics, mouseX, mouseY, 32 - 6, 5, ProgressColor.BLUE,
                                getMenu().isCrafting(),
                                getMenu().getScaledProgress(72),
                                "recipe",
                                menu.getRemainProgress());

                renderProgressBar(graphics, mouseX, mouseY, 32 - 6 + 8, 5, ProgressColor.GREEN,
                                menu.isCrafting(menu.getGenProgress()),
                                menu.getScaledProgress(72, menu.getMaxGenProgress(), menu.getGenProgress()),
                                "gen", menu.getRemainGenProgress());

                renderFluidTank(graphics, 0, -22, 6, 2, mouseX, mouseY);
                renderFluidTank(graphics, 1, -22, 44, 2, mouseX, mouseY);

        }

        @Override
        public boolean enableLeftLabel(int x, int y) {
                return true;
        }

        @Override
        public Size getContainerTitlePos() {
                return Size.of(80, 0);
        }

        @Override
        public Size getTickDelayPos() {
                return Size.of(90, 20);
        }

        // TODO API : Size.move(x,y)
        @Override
        public Size getEnergyStoragePos() {
                return Size.of(7, 5);
        }

        @Override
        protected void renderArrow(GuiGraphicsExtractor guiGraphics) {

        }

}
