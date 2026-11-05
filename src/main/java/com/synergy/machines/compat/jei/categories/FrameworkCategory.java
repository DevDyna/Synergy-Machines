package com.synergy.machines.compat.jei.categories;

import com.devdyna.cakesticklib.api.primitive.Size;
import com.devdyna.cakesticklib.api.utils.ClientUtils;
import com.devdyna.cakesticklib.api.utils.x;
import com.synergy.machines.api.MachineType;
import com.synergy.machines.compat.jei.categories.api.BaseMachineRecipeCategory;
import com.synergy.machines.init.builders.framework.recipe.FrameworkRecipeType;
import com.synergy.machines.init.types.zMachines;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

//TODO IMP : rework to make it a tree-style?
public class FrameworkCategory extends BaseMachineRecipeCategory<FrameworkRecipeType> {

    public FrameworkCategory(IGuiHelper h) {
        super(h);
        this.arrow = helper
                .drawableBuilder(x.rl("minecraft", "textures/gui/sprites/container/furnace/burn_progress.png"),
                        0, 0, 24, 16)
                .setTextureSize(24, 16).buildAnimated(60,
                        IDrawableAnimated.StartDirection.LEFT, false);
    }

    public static final IRecipeType<RecipeHolder<FrameworkRecipeType>> TYPE = IRecipeType
            .create(zMachines.FRAMEWORK.recipe().getType());

    @Override
    public IRecipeType<RecipeHolder<FrameworkRecipeType>> getRecipeType() {
        return TYPE;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FrameworkRecipeType recipe, IFocusGroup focuses) {

        builder.addInputSlot(2, 21).addItemStacks(x.getItemStacksFromIngredient(recipe.getInput()));
        builder.addSlot(RecipeIngredientRole.CRAFTING_STATION,35, 2).addItemStacks(x.getItemStacksFromIngredient(recipe.getCatalyst()));
        builder.addOutputSlot(74, 22).add(recipe.getOutput());

    }

    @Override
    public void draw(FrameworkRecipeType recipe, IRecipeSlotsView recipeSlotsView,
            GuiGraphicsExtractor guiGraphics,
            double mouseX,
            double mouseY) {
        super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);

        arrow.draw(guiGraphics, 29, 22);

        guiGraphics.text(font,
                Component.literal(
                        recipe.getTime() + " ticks"),
                24, 40,
                ClientUtils.defaultToolTipColor.getRGB(), false);

    }

    @Override
    public Size setXY() {
        return Size.of(96, 44);
    }

    @Override
    public MachineType<? extends Block, ? extends BlockEntity, ? extends AbstractContainerMenu, ? extends Recipe<?>> getMachine() {
        return zMachines.FRAMEWORK;
    }

}
