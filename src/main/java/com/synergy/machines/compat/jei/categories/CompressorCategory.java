package com.synergy.machines.compat.jei.categories;

import static com.synergy.machines.Main.MODULE_ID;

import com.devdyna.cakesticklib.CakeStickLib;
import com.devdyna.cakesticklib.api.compat.jei.ImageJei;
import com.devdyna.cakesticklib.api.primitive.Size;
import com.devdyna.cakesticklib.api.utils.x;
import com.synergy.machines.api.MachineType;
import com.synergy.machines.compat.jei.categories.api.BaseMachineRecipeCategory;
import com.synergy.machines.init.builders.compressor.recipe.CompressorRecipeType;
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
public class CompressorCategory extends BaseMachineRecipeCategory<CompressorRecipeType> {

        public CompressorCategory(IGuiHelper h) {
                super(h);
                this.arrow = helper
                                .drawableBuilder(x.rl("minecraft",
                                                "textures/gui/sprites/container/furnace/burn_progress.png"),
                                                0, 0, 24, 16)
                                .setTextureSize(24, 16).buildAnimated(60,
                                                IDrawableAnimated.StartDirection.LEFT, false);
        }

        public static final IRecipeType<RecipeHolder<CompressorRecipeType>> TYPE = IRecipeType
                        .create(zMachines.COMPRESSOR.recipe().getType());

        @Override
        public IRecipeType<RecipeHolder<CompressorRecipeType>> getRecipeType() {
                return TYPE;
        }

        @Override
        public void setRecipe(IRecipeLayoutBuilder builder, CompressorRecipeType recipe, IFocusGroup focuses) {

                builder.addInputSlot(2, 2).addItemStacks(x.getItems(recipe.getInputItem()));

    if(recipe.getCatalystItem() != null) {           var catalyst = builder.addInputSlot(2, 38).addItemStacks(x.getItems(recipe.getCatalystItem()));

                if (!recipe.consumeCatalyst())
                        catalyst.addRichTooltipCallback(
                                        (v, t) -> t.add(Component.translatable(CakeStickLib.MODULE_ID + ".ui.dont_consume")));
}
                builder.addOutputSlot(74, 21).add(recipe.getOutputItem());

        }

        @Override
        public void draw(CompressorRecipeType recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics,
                        double mouseX,
                        double mouseY) {
                super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);

                arrow.draw(guiGraphics, 29, 21);

                guiGraphics.text(font,
                                Component.literal(
                                                recipe.getTime() + " ticks"),
                                25, 2,
                                defaultToolTipColor.getRGB(), false);

                ImageJei.of()
                                .rl(x.rl(MODULE_ID,"textures/gui/sprite/compressor_arrow.png"))
                                .size(16, 9)
                                .offset(2, 23)
                                .render(helper, guiGraphics);

        }

        @Override
        public Size setXY() {
                return Size.of(96, 56);
        }

        @Override
        public MachineType<? extends Block, ? extends BlockEntity, ? extends AbstractContainerMenu, ? extends Recipe<?>> getMachine() {
                return zMachines.COMPRESSOR;
        }

}
