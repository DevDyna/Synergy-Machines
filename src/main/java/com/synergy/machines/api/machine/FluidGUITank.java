package com.synergy.machines.api.machine;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.world.level.material.Fluid;

public class FluidGUITank {

    private int amount;
    private int max;
    private Fluid fluid;
    private int x;
    private int y;
    private int h;
    private int w;

    public static FluidGUITank of() {
        return new FluidGUITank();
    }

    public FluidGUITank setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public FluidGUITank setMaxCapacity(int max) {
        this.max = max;
        return this;
    }

    public FluidGUITank offset(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public FluidGUITank size(int w, int h) {
        this.w = w;
        this.h = h;
        return this;
    }

    public FluidGUITank setFluid(Fluid fluid) {
        this.fluid = fluid;
        return this;
    }

    public void render(GuiGraphicsExtractor guiGraphics) {

        if (fluid == null || max <= 0 || amount <= 0 || h <= 0 || w <= 0)
            return;

        var fluidModel = Minecraft.getInstance()
                .getModelManager()
                .getFluidStateModelSet()
                .get(fluid.defaultFluidState());

        var sprite = fluidModel.stillMaterial().sprite();

        if (sprite == null)
            return;

        int filled = (int) ((amount / (float) max) * h);

        if (filled <= 0)
            return;

        int currentY = y + h;

        int tint = fluidModel.fluidTintSource() != null
                ? fluidModel.fluidTintSource().colorAsStack(
                        com.devdyna.cakesticklib.api.utils.x.fluid(fluid))
                : -1;

        int remaining = filled;

        while (remaining > 0) {

            int drawHeight = Math.min(16, remaining);

            currentY -= drawHeight;

            guiGraphics.blitSprite(
                    RenderPipelines.GUI_TEXTURED,
                    sprite,
                    x,
                    currentY,
                    w,
                    drawHeight,
                    tint | 0xFF000000);

            remaining -= drawHeight;
        }
    }
}
