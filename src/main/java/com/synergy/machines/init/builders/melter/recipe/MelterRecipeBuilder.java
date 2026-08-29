package com.synergy.machines.init.builders.melter.recipe;

import java.util.LinkedHashMap;

import com.synergy.machines.api.MachineType;
import com.synergy.machines.api.machine.*;
import com.synergy.machines.api.machine.recipe.BaseMachineRecipeBuilder;
import com.synergy.machines.api.machine.recipe.BaseMachineRecipeType;
import com.synergy.machines.init.types.zMachines;

import net.minecraft.advancements.Criterion;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.fluids.FluidStackTemplate;

import com.devdyna.cakesticklib.api.recipe.recipeBuilder.*;

public class MelterRecipeBuilder extends BaseMachineRecipeBuilder<MelterRecipeBuilder>
        implements FluidAttach.Output.OutputFluid<MelterRecipeBuilder> {

    private MelterRecipeBuilder(HolderLookup.Provider p) {
        super(p);
        this.criteria = new LinkedHashMap<String, Criterion<?>>();
        this.energy = DEFAULT_FE_COST * 10;
        this.ticks = DEFAULT_TICK_DURATION * 4;
    }

    public static MelterRecipeBuilder of(HolderLookup.Provider p) {
        return new MelterRecipeBuilder(p);
    }

    @Override
    public Recipe<?> createRecipe() {
        return new MelterRecipeType(ticks, energy, input, fluid_output);
    }

    @Override
    public MachineType<? extends BaseMachineBlock, ? extends BaseMachineBE, ? extends BaseMachineMenu, ? extends BaseMachineRecipeType<? extends RecipeInput>> getMachine() {
        return zMachines.ELECTRIC_MELTER;
    }

    @Override
    public MelterRecipeBuilder getBuilder() {
        return this;
    }

    @Override
    public MelterRecipeBuilder output(FluidStackTemplate fluid) {
        this.fluid_output = fluid;
        return getBuilder();
    }

}
