package com.synergy.machines.init.builders.caster.recipe;

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
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

public class CasterRecipeBuilder extends BaseMachineRecipeBuilder<CasterRecipeBuilder>
        implements ItemAttach.Input.OptionalConsume<CasterRecipeBuilder>,
        FluidAttach.Input.SizedFluid<CasterRecipeBuilder>,
        ItemAttach.Input.ItemCounted<CasterRecipeBuilder>,
        ItemAttach.Output.SimpleOutputItem<CasterRecipeBuilder> {

    private boolean consumeCatalyst;
    private SizedFluidIngredient fluid;
    private SizedIngredient input;
    private ItemStackTemplate output;

    private CasterRecipeBuilder(HolderLookup.Provider p) {
        super(p);
        this.criteria = new LinkedHashMap<String, Criterion<?>>();
        this.ticks = 10;
    }

    public static CasterRecipeBuilder of(HolderLookup.Provider p) {
        return new CasterRecipeBuilder(p);
    }

    @Override
    public Recipe<?> createRecipe() {
        return new CasterRecipeType(ticks, energy, fluid, input, consumeCatalyst, output);
    }

    @Override
    public MachineType<? extends BaseMachineBlock, ? extends BaseMachineBE, ? extends BaseMachineMenu, ? extends BaseMachineRecipeType<? extends RecipeInput>> getMachine() {
        return zMachines.CASTING_FACTORY;
    }

    @Override
    public CasterRecipeBuilder getBuilder() {
        return this;
    }

    public CasterRecipeBuilder consumeItemInput() {
        this.consumeCatalyst = true;
        return getBuilder();
    }

    public CasterRecipeBuilder fluid(SizedFluidIngredient fluid) {
        this.fluid = fluid;
        return getBuilder();
    }

    @Override
    public CasterRecipeBuilder output(ItemStackTemplate output) {
        this.output = output;
        return getBuilder();
    }

    @Override
    public CasterRecipeBuilder input(SizedIngredient input) {
        this.input = input;
        return getBuilder();
    }

    @Override
    public String defineID() {
        return x.name(output) + "_from_" + name(fluid);
    }

}
