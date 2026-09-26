package com.synergy.machines.api;

import static com.synergy.machines.Main.MODULE_ID;

import com.devdyna.cakesticklib.api.utils.x;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class DebugButton extends Button {

    private boolean flag = false;

    public DebugButton(int x, int y, int width, int height, OnPress onPress) {
        super(x, y, width, height, CommonComponents.EMPTY, onPress, DEFAULT_NARRATION);
    }

    @Override
    protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {

        super.extractDefaultSprite(graphics);

        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                x.rl(MODULE_ID, "textures/gui/container/debug/" + (flag ? "on" : "off") + ".png"),
                getX() + 2,
                getY() + 2,
                0, 0,
                getWidth() - 4, getHeight() - 4,
                getWidth() - 4, getHeight() - 4);
    }

    @Override
    public void onPress(InputWithModifiers input) {
        super.onPress(input);
        this.setFocused(false);
    }

    public void update(boolean f) {
        this.flag = f;
    }

    public void updateTooltip( String v) {
    setTooltip(Tooltip.create(Component.translatable(MODULE_ID + ".gui.button.debug." + v)));
    }

}
