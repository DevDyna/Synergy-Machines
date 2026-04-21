package com.synergy.machines.init.builders.caster.recipe;

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
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

@SuppressWarnings({ "null" })
public class CasterRecipeBuilder extends BaseMachineRecipeBuilder<CasterRecipeBuilder>
        implements ItemAttach.Input.OptionalConsume<CasterRecipeBuilder>, FluidAttach.Input.SizedFluid<CasterRecipeBuilder> {

    private CasterRecipeBuilder() {
        this.criteria = new LinkedHashMap<String, Criterion<?>>();
        this.ticks = 10;
    }

    public static CasterRecipeBuilder of() {
        return new CasterRecipeBuilder();
    }

    @Override
    public Recipe<?> createRecipe() {
        return new CasterRecipeType(ticks, energy, fluid_input, input, consumeCatalyst, output);
    }

    @Override
    public MachineType<? extends BaseMachineBlock, ? extends BaseMachineBE, ? extends BaseMachineMenu, ? extends BaseMachineRecipeType<? extends RecipeInput>> getMachine() {
        return zMachines.CASTING_FACTORY;
    }

    @Override
    public CasterRecipeBuilder getBuilder() {
        return this;
    }

    @Override
    public CasterRecipeBuilder consumeItemInput() {
        this.consumeCatalyst = true;
        return getBuilder();
    }

    @Override
    public CasterRecipeBuilder fluid(SizedFluidIngredient fluid) {
        this.fluid_input = fluid;
        return getBuilder();
    }

}
