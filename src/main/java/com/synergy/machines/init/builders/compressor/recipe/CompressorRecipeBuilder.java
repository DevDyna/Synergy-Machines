package com.synergy.machines.init.builders.compressor.recipe;

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
public class CompressorRecipeBuilder extends BaseMachineRecipeBuilder<CompressorRecipeBuilder>
        implements ItemAttach.Input.CatalystItem<CompressorRecipeBuilder> {

    private CompressorRecipeBuilder() {
        this.criteria = new LinkedHashMap<String, Criterion<?>>();
    }

    public static CompressorRecipeBuilder of() {
        return new CompressorRecipeBuilder();
    }

    @Override
    public Recipe<?> createRecipe() {
        return new CompressorRecipeType(ticks, energy, input, optional_input, consumeCatalyst, output);
    }

    @Override
    public MachineType<? extends BaseMachineBlock, ? extends BaseMachineBE, ? extends BaseMachineMenu, ? extends BaseMachineRecipeType<? extends RecipeInput>> getMachine() {
        return zMachines.COMPRESSOR;
    }

    @Override
    public CompressorRecipeBuilder catalyst(SizedIngredient catalyst) {
        this.optional_input = catalyst;
        return getBuilder();
    }

    public CompressorRecipeBuilder consumeCatalyst() {
        this.consumeCatalyst = true;
        return getBuilder();
    }

    @Override
    public CompressorRecipeBuilder getBuilder() {
        return this;
    }

}
