package com.synergy.machines.init.builders.compressor.recipe;

import java.util.LinkedHashMap;

import com.synergy.machines.api.MachineType;
import com.synergy.machines.api.machine.*;
import com.synergy.machines.api.machine.recipe.BaseMachineRecipeBuilder;
import com.synergy.machines.api.machine.recipe.BaseMachineRecipeType;
import com.synergy.machines.init.types.zMachines;
import com.devdyna.cakesticklib.api.recipe.recipeBuilder.*;
import com.devdyna.cakesticklib.api.utils.x;

import net.minecraft.advancements.Criterion;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

public class CompressorRecipeBuilder extends BaseMachineRecipeBuilder<CompressorRecipeBuilder>
        implements ItemAttach.Input.CatalystItem<CompressorRecipeBuilder>,
        ItemAttach.Input.ItemCounted<CompressorRecipeBuilder>,
        ItemAttach.Output.SimpleOutputItem<CompressorRecipeBuilder> {

    private boolean consumeCatalyst;
    private SizedIngredient catalyst;
    private ItemStackTemplate output;
    private SizedIngredient input;

    private CompressorRecipeBuilder(HolderLookup.Provider p) {
        super(p);
        this.criteria = new LinkedHashMap<String, Criterion<?>>();
    }

    public static CompressorRecipeBuilder of(HolderLookup.Provider p) {
        return new CompressorRecipeBuilder(p);
    }

    @Override
    public Recipe<?> createRecipe() {
        return new CompressorRecipeType(ticks, energy, input, catalyst, consumeCatalyst, output);
    }

    @Override
    public MachineType<? extends BaseMachineBlock, ? extends BaseMachineBE, ? extends BaseMachineMenu, ? extends BaseMachineRecipeType<? extends RecipeInput>> getMachine() {
        return zMachines.COMPRESSOR;
    }

    @Override
    public CompressorRecipeBuilder catalyst(SizedIngredient catalyst) {
        this.catalyst = catalyst;
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

    @Override
    public CompressorRecipeBuilder output(ItemStackTemplate output) {
        this.output = output;
        return getBuilder();
    }

    @Override
    public CompressorRecipeBuilder input(SizedIngredient input) {
        this.input = input;
        return getBuilder();
    }

    @Override
    public String defineID() {
        return x.name(output) + "_from_" + name(input);
    }

}
