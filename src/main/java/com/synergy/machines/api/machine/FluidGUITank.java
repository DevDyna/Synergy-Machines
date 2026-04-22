package com.synergy.machines.api.machine;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
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

        if (fluid == null || max <= 0 || amount <= 0)
            return;

        var fluidStack = com.devdyna.cakesticklib.api.utils.x.fluid(fluid);

        var model = Minecraft.getInstance()
                .getModelManager()
                .getFluidStateModelSet()
                .get(fluid.defaultFluidState());

        TextureAtlasSprite sprite = model.stillMaterial().sprite();
        int color = model.fluidTintSource().colorAsStack(fluidStack);

        if (sprite == null)
            return;

        int filled = (int) ((amount / (float) max) * h);
        if (filled <= 0)
            return;

        int xTank = x;
        int yTank = y + (h - filled);

        final int tileSize = 16;

        float u0 = sprite.getU0();

        for (int dy = 0; dy < filled; dy += tileSize) {

            int renderH = Math.min(tileSize, filled - dy);

            float v0 = sprite.getV0();

            guiGraphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    sprite.atlasLocation(),
                    xTank,
                    yTank + dy,
                    u0,
                    v0,
                    w,
                    renderH,
                    tileSize,
                    tileSize,
                    color
            );
        }
    }
}