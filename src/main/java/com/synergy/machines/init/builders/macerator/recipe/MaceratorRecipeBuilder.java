package com.synergy.machines.init.builders.macerator.recipe;

import java.util.LinkedHashMap;

import com.synergy.machines.api.MachineType;
import com.synergy.machines.api.machine.*;
import com.synergy.machines.api.machine.recipe.BaseMachineRecipeBuilder;
import com.synergy.machines.api.machine.recipe.BaseMachineRecipeType;
import com.synergy.machines.init.types.zMachines;

import net.minecraft.advancements.Criterion;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;

import com.devdyna.cakesticklib.api.recipe.recipeBuilder.*;
import com.devdyna.cakesticklib.api.recipe.recipeOutput.ChanceOutput;

public class MaceratorRecipeBuilder extends BaseMachineRecipeBuilder<MaceratorRecipeBuilder>
        implements ItemAttach.Output.ItemOutputChance<MaceratorRecipeBuilder> {

    private MaceratorRecipeBuilder(HolderLookup.Provider p) {
        super(p);
        this.criteria = new LinkedHashMap<String, Criterion<?>>();
    }

    public static MaceratorRecipeBuilder of(HolderLookup.Provider p) {
        return new MaceratorRecipeBuilder(p);
    }

    @Override
    public Recipe<?> createRecipe() {
        return new MaceratorRecipeType(ticks, energy, input, output, optional_output_item);
    }

    @Override
    public MachineType<? extends BaseMachineBlock, ? extends BaseMachineBE, ? extends BaseMachineMenu, ? extends BaseMachineRecipeType<? extends RecipeInput>> getMachine() {
        return zMachines.MACERATOR;
    }

    //TODO
    @Override
    public MaceratorRecipeBuilder output(ItemStackTemplate item) {
        this.output = item;
        return this;
    }

    // TODO API : revert .output(chance.item) ->
    // .chance(chance.item)
    @Override
    public MaceratorRecipeBuilder output(ChanceOutput.Item item) {
        this.optional_output_item = item;
        return this;
    }

    @Override
    public MaceratorRecipeBuilder getBuilder() {
        return this;
    }

}
