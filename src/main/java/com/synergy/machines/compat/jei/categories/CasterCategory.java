package com.synergy.machines.compat.jei.categories;
import static com.synergy.machines.Main.MODULE_ID;

import com.devdyna.cakesticklib.api.primitive.Size;
import com.devdyna.cakesticklib.api.utils.x;
import com.synergy.machines.api.MachineType;
import com.synergy.machines.compat.jei.categories.api.BaseMachineRecipeCategory;
import com.synergy.machines.compat.jei.categories.api.JEIFluidTankHelper;
import com.synergy.machines.init.builders.caster.recipe.CasterRecipeType;
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
public class CasterCategory extends BaseMachineRecipeCategory<CasterRecipeType> {

        public CasterCategory(IGuiHelper h) {
                super(h);
                this.arrow = helper
                                .drawableBuilder(x.rl("minecraft",
                                                "textures/gui/sprites/container/furnace/burn_progress.png"),
                                                0, 0, 24, 16)
                                .setTextureSize(24, 16).buildAnimated(60,
                                                IDrawableAnimated.StartDirection.LEFT, false);
        }

        public static final IRecipeType<RecipeHolder<CasterRecipeType>> TYPE = IRecipeType
                        .create(zMachines.CASTING_FACTORY.recipe().getType());

        @Override
        public IRecipeType<RecipeHolder<CasterRecipeType>> getRecipeType() {
                return TYPE;
        }

        @Override
        public void setRecipe(IRecipeLayoutBuilder builder, CasterRecipeType recipe, IFocusGroup focuses) {

                if (recipe.getInputItem() != null && !x.getItems(recipe.getInputItem()).isEmpty()) {
                        var item = builder.addInputSlot(2 + 21, 5).addItemStacks(x.getItems(recipe.getInputItem()));

                        if (!recipe.consumeCatalyst())
                                item.addRichTooltipCallback(
                                                (v, t) -> t.add(Component.translatable(MODULE_ID + ".jei.tip.dont_consume")));
                }

                builder.addOutputSlot(81 + 21, 5).add(recipe.getOutputItem());

                JEIFluidTankHelper.of()
                                .fluid(recipe.getFluidInput())
                                .offset(3, 21)
                                .build((x, y) -> builder.addInputSlot(x, y));

        }

        @Override
        public void draw(CasterRecipeType recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics,
                        double mouseX,
                        double mouseY) {
                super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);

                arrow.draw(guiGraphics, 29 + 21, 6);

                guiGraphics.text(font,
                                Component.literal(
                                                recipe.getTime() + " ticks"),
                                24 + 21, -2,
                                defaultToolTipColor.getRGB(), false);

        }

        @Override
        public Size setXY() {
                return Size.of(124, 28);
        }

        @Override
        public MachineType<? extends Block, ? extends BlockEntity, ? extends AbstractContainerMenu, ? extends Recipe<?>> getMachine() {
                return zMachines.CASTING_FACTORY;
        }

}
