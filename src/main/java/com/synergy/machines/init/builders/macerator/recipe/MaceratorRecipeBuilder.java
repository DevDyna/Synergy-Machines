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
import net.neoforged.neoforge.common.crafting.SizedIngredient;

import com.devdyna.cakesticklib.api.recipe.recipeBuilder.*;
import com.devdyna.cakesticklib.api.recipe.recipeOutput.ChanceOutput;
import com.devdyna.cakesticklib.api.utils.x;

public class MaceratorRecipeBuilder extends BaseMachineRecipeBuilder<MaceratorRecipeBuilder>
        implements ItemAttach.Output.ItemOutputChance<MaceratorRecipeBuilder>,
        ItemAttach.Input.ItemCounted<MaceratorRecipeBuilder>,
        ItemAttach.Output.SimpleOutputItem<MaceratorRecipeBuilder> {

    private ChanceOutput.Item secondary;
    private ItemStackTemplate output;
    private SizedIngredient input;

    private MaceratorRecipeBuilder(HolderLookup.Provider p) {
        super(p);
        this.criteria = new LinkedHashMap<String, Criterion<?>>();
    }

    public static MaceratorRecipeBuilder of(HolderLookup.Provider p) {
        return new MaceratorRecipeBuilder(p);
    }

    @Override
    public Recipe<?> createRecipe() {
        return new MaceratorRecipeType(ticks, energy, input, output, secondary);
    }

    @Override
    public MachineType<? extends BaseMachineBlock, ? extends BaseMachineBE, ? extends BaseMachineMenu, ? extends BaseMachineRecipeType<? extends RecipeInput>> getMachine() {
        return zMachines.MACERATOR;
    }

    @Override
    public MaceratorRecipeBuilder outputChance(ChanceOutput.Item secondary) {
        this.secondary = secondary;
        return this;
    }

    @Override
    public MaceratorRecipeBuilder getBuilder() {
        return this;
    }

    @Override
    public MaceratorRecipeBuilder output(ItemStackTemplate output) {
        this.output = output;
        return getBuilder();
    }

    @Override
    public MaceratorRecipeBuilder input(SizedIngredient input) {
        this.input = input;
        return getBuilder();
    }

    @Override
    public String defineID() {
        return x.name(output != null ? output : secondary.item()) + "_from_" + name(input);
    }

}
