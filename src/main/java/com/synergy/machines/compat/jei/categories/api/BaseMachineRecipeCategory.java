package com.synergy.machines.compat.jei.categories.api;

import static com.synergy.machines.Main.MODULE_ID;

import com.devdyna.cakesticklib.api.compat.jei.BaseRecipeCategory;
import com.devdyna.cakesticklib.api.utils.x;
import com.synergy.machines.api.MachineType;
import com.synergy.machines.api.machine.recipe.BaseMachineRecipeType;

import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class BaseMachineRecipeCategory<R extends BaseMachineRecipeType<?>> extends BaseRecipeCategory<R> {

    protected IDrawableAnimated arrow;

    public BaseMachineRecipeCategory(IGuiHelper h) {
        super(h);
    }

    public abstract MachineType<? extends Block, ? extends BlockEntity, ? extends AbstractContainerMenu, ? extends Recipe<?>> getMachine();

    @Override
    public ItemLike getIconItem() {
        return getMachine().item().get();
    }

    @Override
    public String getTraslationKey() {
        return MODULE_ID + ".jei." + getMachine().id();
    }

    @Override
    public Identifier setBackGround() {
        return x.rl(MODULE_ID, "textures/gui/jei/" + getMachine().id() + ".png");
    }

}
