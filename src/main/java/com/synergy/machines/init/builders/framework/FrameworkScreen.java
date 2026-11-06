package com.synergy.machines.init.builders.framework;

import static com.synergy.machines.Main.MODULE_ID;

import com.devdyna.cakesticklib.api.primitive.Size;
import com.devdyna.cakesticklib.api.utils.x;
import com.synergy.machines.api.machine.BaseMachineScreen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

//TODO maybe i can unify screen-menu handling (?)
public class FrameworkScreen extends BaseMachineScreen<FrameworkMenu> {

        public FrameworkScreen(FrameworkMenu menu, Inventory playerInventory, Component title) {
                super(menu, playerInventory, title);
        }

        @Override
        public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {

                super.extractBackground(graphics, mouseX, mouseY, a);
                // renderFluidTank(graphics, 0, -22, +6, mouseX, mouseY);

                renderInputSlot(graphics, 43, 8, mouseX, mouseY);

                renderStaticTexture(graphics,
                                x.rl(MODULE_ID, "textures/gui/container/slot/tiny_arrow.png"),
                                4 + 43, 31-4-2,
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

                renderProgressBar(graphics, mouseX, mouseY, 32, 5);

        }

        public void renderStaticTexture(GuiGraphicsExtractor graphics, Identifier rl, int xo, int yo, int width,
                        int height) {
                graphics.blit(RenderPipelines.GUI_TEXTURED, rl, getLeftPos() + xo, getTopPos() + yo, 0, 0, width,
                                height, width, height);
        }

        public void renderProgressBar(GuiGraphicsExtractor graphics, int mouseX, int mouseY, int xo, int yo) {

                graphics.blit(RenderPipelines.GUI_TEXTURED,
                                x.rl(MODULE_ID, "textures/gui/container/slot/progress/base.png"),
                                getLeftPos() + xo, getTopPos() + yo,
                                0, 0,
                                6, 72,
                                36, 72);

                if (this.whenAnimateArrow()) {
                        var slice = getScaledProgress(72);
                        graphics.blit(RenderPipelines.GUI_TEXTURED,
                                        x.rl(MODULE_ID, "textures/gui/container/slot/progress/base.png"),
                                        getLeftPos() + xo, getTopPos() + yo + (72 - slice),
                                        18, 72 - slice,
                                        6, slice,
                                        36, 72);

                }

                if (isDebugHover(xo, yo, 6, 72, mouseX, mouseY))
                        graphics.blit(RenderPipelines.GUI_TEXTURED,
                                        x.rl(MODULE_ID, "textures/gui/container/slot/progress/debug.png"),
                                        getLeftPos() + xo, getTopPos() + yo,
                                        0, 0,
                                        6, 72,
                                        6, 72);
        }

        @Override
        public Size getContainerTitlePos() {
                return Size.of(80, 0);
        }

        @Override
        public Size getTickDelayPos() {
                return Size.of(90, 10);
        }

        @Override
        protected void renderArrow(GuiGraphicsExtractor guiGraphics) {

        }

}
