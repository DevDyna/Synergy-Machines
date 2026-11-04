package com.synergy.machines.init.builders.furnace.recipe;

import java.util.LinkedHashMap;

import com.devdyna.cakesticklib.api.recipe.recipeBuilder.ItemAttach;
import com.devdyna.cakesticklib.api.utils.x;
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

public class ElectricFurnaceRecipeBuilder extends BaseMachineRecipeBuilder<ElectricFurnaceRecipeBuilder>
        implements ItemAttach.Input.ItemCounted<ElectricFurnaceRecipeBuilder>,
        ItemAttach.Output.SimpleOutputItem<ElectricFurnaceRecipeBuilder> {

    private ItemStackTemplate output;
    private SizedIngredient input;

    private ElectricFurnaceRecipeBuilder(HolderLookup.Provider p) {
        super(p);
        this.criteria = new LinkedHashMap<String, Criterion<?>>();
    }

    public static ElectricFurnaceRecipeBuilder of(HolderLookup.Provider p) {
        return new ElectricFurnaceRecipeBuilder(p);
    }

    @Override
    public Recipe<?> createRecipe() {
        return new ElectricFurnaceRecipeType(ticks, energy, input, output);
    }

    @Override
    public MachineType<? extends BaseMachineBlock, ? extends BaseMachineBE, ? extends BaseMachineMenu, ? extends BaseMachineRecipeType<? extends RecipeInput>> getMachine() {
        return zMachines.ELECTRIC_FURNACE;
    }

    @Override
    public ElectricFurnaceRecipeBuilder getBuilder() {
        return this;
    }

    @Override
    public ElectricFurnaceRecipeBuilder output(ItemStackTemplate output) {
        this.output = output;
        return getBuilder();
    }

    @Override
    public ElectricFurnaceRecipeBuilder input(SizedIngredient input) {
        this.input = input;
        return getBuilder();
    }

    @Override
    public String defineID() {
        return x.name(output) + "_from_" + name(input);
    }

}
