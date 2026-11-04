package com.synergy.machines.init.builders.framework.recipe;

import java.util.*;

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
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import com.devdyna.cakesticklib.api.recipe.recipeBuilder.*;
import com.devdyna.cakesticklib.api.utils.x;

public class FrameworkRecipeBuilder extends BaseMachineRecipeBuilder<FrameworkRecipeBuilder>
        implements ItemAttach.Input.ItemCounted<FrameworkRecipeBuilder>,
        ItemAttach.Output.SimpleOutputItem<FrameworkRecipeBuilder> {

    private SizedIngredient machine;
    private SizedIngredient input;
    private ItemStackTemplate output;

    private FrameworkRecipeBuilder(HolderLookup.Provider p) {
        super(p);
        this.criteria = new LinkedHashMap<String, Criterion<?>>();
    }

    public static FrameworkRecipeBuilder of(HolderLookup.Provider p) {
        return new FrameworkRecipeBuilder(p);
    }

    @Override
    public Recipe<?> createRecipe() {
        return new FrameworkRecipeType(ticks, energy, input, machine, output);
    }

    @Override
    public MachineType<? extends BaseMachineBlock, ? extends BaseMachineBE, ? extends BaseMachineMenu, ? extends BaseMachineRecipeType<? extends RecipeInput>> getMachine() {
        return zMachines.FRAMEWORK;
    }

    public FrameworkRecipeBuilder machine(
            MachineType<? extends BaseMachineBlock, ? extends BaseMachineBE, ? extends BaseMachineMenu, ? extends BaseMachineRecipeType<? extends RecipeInput>> machine) {
        return machine(machine.item().get(), 1);
    }

    public FrameworkRecipeBuilder machine(SizedIngredient machine) {
        this.machine = machine;
        return getBuilder();
    }

    public FrameworkRecipeBuilder machine(ItemLike machine) {
        return machine(x.itemSized(machine));
    }

    public FrameworkRecipeBuilder machine(ItemLike machine, int count) {
        return machine(x.itemSized(machine, count));
    }

    @Override
    public FrameworkRecipeBuilder getBuilder() {
        return this;
    }

    @Override
    public FrameworkRecipeBuilder output(ItemStackTemplate output) {
        this.output = output;
        return getBuilder();

    }

    @Override
    public FrameworkRecipeBuilder input(SizedIngredient input) {
        this.input = input;
        return getBuilder();
    }

    @Override
    public String defineID() {
        return x.name(output) + "_from_" + name(input);
    }

}