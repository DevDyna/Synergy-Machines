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

    public FluidGUITank() {

    }

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

    public FluidGUITank size(int h, int w) {
        this.h = h;
        this.w = w;
        return this;
    }

    public FluidGUITank setFluid(Fluid fluid) {
        this.fluid = fluid;
        return this;
    }

    public void render(GuiGraphicsExtractor guiGraphics) {

        var fluidStack = com.devdyna.cakesticklib.api.utils.x.fluid(fluid);
        var color = Minecraft.getInstance().getModelManager().getFluidStateModelSet().get(fluid.defaultFluidState())
                .fluidTintSource().colorAsStack(fluidStack);
        var sprite = Minecraft.getInstance().getModelManager().getFluidStateModelSet().get(fluid.defaultFluidState())
                .stillMaterial().sprite();

        if (sprite == null)
            return;

        int filled = (int) ((amount / (float) max) * h);
        if (filled <= 0)
            return;

        int xTank = x;
        int yTank = y + h - filled;

        int texSize = 16;

        for (int dy = 0; dy < filled; dy += texSize) {

            int renderH = Math.min(texSize, filled - dy);

            float v0 = sprite.getV((float) 0 / texSize);
            float u0 = sprite.getU(0);

            guiGraphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    sprite.atlasLocation(),
                    xTank,
                    yTank + dy,
                    u0, v0,
                    w,
                    renderH,
                    texSize,
                    70,
                    color);
        }
    }

}