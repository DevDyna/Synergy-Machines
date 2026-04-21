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

        // var still = IClientFluidTypeExtensions.of(fluid).;

        // if (still != null) {
        TextureAtlasSprite sprite = Minecraft.getInstance().getModelManager().getFluidStateModelSet()
                .get(fluid.defaultFluidState()).stillMaterial().sprite();

        int xTank = x;
        int yTank = y + h;

        int color = Minecraft.getInstance().getModelManager().getFluidStateModelSet().get(fluid.defaultFluidState())
                .fluidTintSource().color(fluid.defaultFluidState());
        // float r = ((color >> 16) & 0xFF) / 255f;
        // float g = ((color >> 8) & 0xFF) / 255f;
        // float b = (color & 0xFF) / 255f;
        // guiGraphics.setColor(r, g, b, 1f);

        int remaining = ((amount * h) / max) - 2;

        while (remaining > 0) {
            int renderHeight = Math.min(16, remaining);
            yTank -= renderHeight;

            // RenderSystem.setShader(GameRenderer::getPositionTexShader);
            // RenderSystem.setShaderColor(r, g, b, 1f);
            // RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);

            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED,
                    sprite.atlasLocation(),
                    xTank,
                    yTank,
                    w,
                    renderHeight,
                    color);

            remaining -= renderHeight;
        }

        // guiGraphics.setColor(1f, 1f, 1f, 1f);
        // }
    }
}
