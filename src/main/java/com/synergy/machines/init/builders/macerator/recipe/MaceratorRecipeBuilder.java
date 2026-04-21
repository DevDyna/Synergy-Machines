package com.synergy.machines.init.builders.macerator.recipe;

import java.util.LinkedHashMap;

import com.synergy.machines.api.MachineType;
import com.synergy.machines.api.machine.*;
import com.synergy.machines.api.machine.recipe.BaseMachineRecipeBuilder;
import com.synergy.machines.api.machine.recipe.BaseMachineRecipeType;
import com.synergy.machines.init.types.zMachines;

import net.minecraft.advancements.Criterion;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;

import com.devdyna.cakesticklib.api.recipe.ChanceOutputItem;
import com.devdyna.cakesticklib.api.recipe.recipeBuilder.*;


@SuppressWarnings({ "null" })
public class MaceratorRecipeBuilder extends BaseMachineRecipeBuilder<MaceratorRecipeBuilder>
        implements ItemAttach.Output.SecondaryOutputItem<MaceratorRecipeBuilder> {

    private MaceratorRecipeBuilder() {
        this.criteria = new LinkedHashMap<String, Criterion<?>>();
    }

    public static MaceratorRecipeBuilder of() {
        return new MaceratorRecipeBuilder();
    }

    @Override
    public Recipe<?> createRecipe() {
        return new MaceratorRecipeType(ticks, energy, input, output, optional_output_item);
    }

    @Override
    public MachineType<? extends BaseMachineBlock, ? extends BaseMachineBE, ? extends BaseMachineMenu, ? extends BaseMachineRecipeType<? extends RecipeInput>> getMachine() {
        return zMachines.MACERATOR;
    }

    public MaceratorRecipeBuilder secondary(ItemStackTemplate secondary, float chance) {
        this.optional_output_item = ChanceOutputItem.of(secondary, chance);
        return getBuilder();
    }

    @Override
    public MaceratorRecipeBuilder getBuilder() {
        return this;
    }

}
