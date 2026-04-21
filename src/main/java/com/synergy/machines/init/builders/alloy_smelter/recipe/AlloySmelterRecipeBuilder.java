package com.synergy.machines.init.builders.alloy_smelter.recipe;

import java.util.LinkedHashMap;

import com.synergy.machines.api.MachineType;
import com.synergy.machines.api.machine.*;
import com.synergy.machines.api.machine.recipe.BaseMachineRecipeBuilder;
import com.synergy.machines.api.machine.recipe.BaseMachineRecipeType;
import com.synergy.machines.init.types.zMachines;
import com.devdyna.cakesticklib.api.recipe.recipeBuilder.*;
import net.minecraft.advancements.Criterion;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

@SuppressWarnings({ "null" })
public class AlloySmelterRecipeBuilder extends BaseMachineRecipeBuilder<AlloySmelterRecipeBuilder>
        implements ItemAttach.Input.DoubleItemCounted<AlloySmelterRecipeBuilder> {

    private AlloySmelterRecipeBuilder() {
        this.criteria = new LinkedHashMap<String, Criterion<?>>();
    }

    public static AlloySmelterRecipeBuilder of() {
        return new AlloySmelterRecipeBuilder();
    }

    @Override
    public Recipe<?> createRecipe() {
        return new AlloySmelterRecipeType(ticks, energy, input, extra_input, output);
    }

    @Override
    public MachineType<? extends BaseMachineBlock, ? extends BaseMachineBE, ? extends BaseMachineMenu, ? extends BaseMachineRecipeType<? extends RecipeInput>> getMachine() {
        return zMachines.ALLOY_SMELTER;
    }

    @Override
    public AlloySmelterRecipeBuilder getBuilder() {
        return this;
    }

    @Override
    public AlloySmelterRecipeBuilder inputs(SizedIngredient right, SizedIngredient left) {
        this.input = right;
        this.extra_input = left;
        return getBuilder();
    }

}
