package com.synergy.machines.init.builders.extractor.recipe;

import java.util.LinkedHashMap;

import net.minecraft.advancements.Criterion;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.fluids.FluidStackTemplate;

import com.devdyna.cakesticklib.api.recipe.recipeBuilder.*;
import com.devdyna.cakesticklib.api.recipe.recipeOutput.ChanceOutput;
import com.devdyna.cakesticklib.api.utils.x;
import com.synergy.machines.api.MachineType;
import com.synergy.machines.api.machine.*;
import com.synergy.machines.api.machine.recipe.BaseMachineRecipeBuilder;
import com.synergy.machines.api.machine.recipe.BaseMachineRecipeType;
import com.synergy.machines.init.types.zMachines;

public class ExtractorRecipeBuilder extends BaseMachineRecipeBuilder<ExtractorRecipeBuilder>
        implements FluidAttach.Output.OutputFluid<ExtractorRecipeBuilder>,
        ItemAttach.Output.ItemOutputChance<ExtractorRecipeBuilder>,
        ItemAttach.Input.ItemCounted<ExtractorRecipeBuilder> {

    private SizedIngredient input;
    private ChanceOutput.Item output;
    private FluidStackTemplate fluid;

    private ExtractorRecipeBuilder(HolderLookup.Provider p) {
        super(p);
        this.criteria = new LinkedHashMap<String, Criterion<?>>();
    }

    public static ExtractorRecipeBuilder of(HolderLookup.Provider p) {
        return new ExtractorRecipeBuilder(p);
    }

    @Override
    public Recipe<?> createRecipe() {
        return new ExtractorRecipeType(ticks, energy, input, output, fluid);
    }

    @Override
    public MachineType<? extends BaseMachineBlock, ? extends BaseMachineBE, ? extends BaseMachineMenu, ? extends BaseMachineRecipeType<? extends RecipeInput>> getMachine() {
        return zMachines.EXTRACTOR;
    }

    @Override
    public ExtractorRecipeBuilder getBuilder() {
        return this;
    }

    @Override
    public ExtractorRecipeBuilder outputChance(ChanceOutput.Item output) {
        this.output = output;
        return getBuilder();
    }

    @Override
    public ExtractorRecipeBuilder output(FluidStackTemplate fluid) {
        this.fluid = fluid;
        return getBuilder();
    }

    @Override
    public ExtractorRecipeBuilder input(SizedIngredient input) {
        this.input = input;
        return getBuilder();
    }

    @Override
    public String defineID() {
        return x.name(fluid) + "_from_" + name(input);
    }

}
