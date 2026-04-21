package com.synergy.machines.compat.jei.categories;

import com.devdyna.cakesticklib.api.primitive.Size;
import com.devdyna.cakesticklib.api.utils.x;
import com.synergy.machines.api.MachineType;
import com.synergy.machines.compat.jei.categories.api.BaseMachineRecipeCategory;
import com.synergy.machines.compat.jei.categories.api.JEIFluidTankHelper;
import com.synergy.machines.init.builders.melter.recipe.MelterRecipeType;
import com.synergy.machines.init.types.zMachines;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

@SuppressWarnings("null")
public class MelterCategory extends BaseMachineRecipeCategory<MelterRecipeType> {

        public MelterCategory(IGuiHelper h) {
                super(h);
                this.arrow = helper
                                .drawableBuilder(x.rl("minecraft",
                                                "textures/gui/sprites/container/furnace/burn_progress.png"),
                                                0, 0, 24, 16)
                                .setTextureSize(24, 16).buildAnimated(60,
                                                IDrawableAnimated.StartDirection.LEFT, false);
        }

        public static final IRecipeType<RecipeHolder<MelterRecipeType>> TYPE = IRecipeType
                        .create(zMachines.ELECTRIC_MELTER.recipe().getType());

        @Override
        public IRecipeType<RecipeHolder<MelterRecipeType>> getRecipeType() {
                return TYPE;
        }

        @Override
        public void setRecipe(IRecipeLayoutBuilder builder, MelterRecipeType recipe, IFocusGroup focuses) {

                builder.addInputSlot(2, 5).addItemStacks(x.getItems(recipe.getInputItem()));

                JEIFluidTankHelper.of()
                                .fluid(recipe.getFluidOutput())
                                .offset(74, 21)
                                .build((x,y)->builder.addOutputSlot(x,y));

        }

        @Override
        public void draw(MelterRecipeType recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics,
                        double mouseX,
                        double mouseY) {
                super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);

                arrow.draw(guiGraphics, 29, 6);

                guiGraphics.text(font,
                                Component.literal(
                                                recipe.getTime() + " ticks"),
                                24, -2,
                                defaultToolTipColor.getRGB(), false);

        }

        @Override
        public Size setXY() {
                return Size.of(96, 28);
        }

        @Override
        public MachineType<? extends Block, ? extends BlockEntity, ? extends AbstractContainerMenu, ? extends Recipe<?>> getMachine() {
                return zMachines.ELECTRIC_MELTER;
        }

}
