package com.synergy.machines.init.builders.extractor.recipe;

import java.util.LinkedHashMap;

import net.minecraft.advancements.Criterion;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.fluids.FluidStackTemplate;

import com.devdyna.cakesticklib.api.recipe.recipeBuilder.*;
import com.devdyna.cakesticklib.api.recipe.recipeOutput.ChanceOutput;
import com.synergy.machines.api.MachineType;
import com.synergy.machines.api.machine.*;
import com.synergy.machines.api.machine.recipe.BaseMachineRecipeBuilder;
import com.synergy.machines.api.machine.recipe.BaseMachineRecipeType;
import com.synergy.machines.init.types.zMachines;

public class ExtractorRecipeBuilder extends BaseMachineRecipeBuilder<ExtractorRecipeBuilder>
        implements FluidAttach.Output.OutputFluid<ExtractorRecipeBuilder>,
        ItemAttach.Output.ItemOutputChance<ExtractorRecipeBuilder> {

    private ExtractorRecipeBuilder(HolderLookup.Provider p) {
        super(p);
        this.criteria = new LinkedHashMap<String, Criterion<?>>();
    }

    public static ExtractorRecipeBuilder of(HolderLookup.Provider p) {
        return new ExtractorRecipeBuilder(p);
    }

    @Override
    public Recipe<?> createRecipe() {
        return new ExtractorRecipeType(ticks, energy, input, optional_output_item, fluid_output);
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
    public ExtractorRecipeBuilder output(ChanceOutput.Item item) {
        this.optional_output_item = item;
        return getBuilder();
    }

    @Override
    public ExtractorRecipeBuilder output(FluidStackTemplate f) {
        this.fluid_output = f;
        return getBuilder();
    }

}
