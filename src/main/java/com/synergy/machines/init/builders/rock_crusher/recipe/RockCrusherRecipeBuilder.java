package com.synergy.machines.init.builders.rock_crusher.recipe;

import java.util.*;

import com.devdyna.cakesticklib.api.recipe.ChanceOutputItem;
import com.synergy.machines.api.MachineType;
import com.synergy.machines.api.machine.*;
import com.synergy.machines.api.machine.recipe.BaseMachineRecipeBuilder;
import com.synergy.machines.api.machine.recipe.BaseMachineRecipeType;
import com.synergy.machines.init.types.zMachines;

import net.minecraft.advancements.Criterion;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import com.devdyna.cakesticklib.api.recipe.recipeBuilder.*;
import com.devdyna.cakesticklib.api.utils.x;

@SuppressWarnings({ "null" })
public class RockCrusherRecipeBuilder extends BaseMachineRecipeBuilder<RockCrusherRecipeBuilder>
        implements FluidAttach.Input.SizedFluid<RockCrusherRecipeBuilder> {

    private List<ChanceOutputItem> result;

    private RockCrusherRecipeBuilder() {
        this.energy = 1500;
        this.ticks = 120;
        this.criteria = new LinkedHashMap<String, Criterion<?>>();
        this.result = new ArrayList<>(9);
    }

    public static RockCrusherRecipeBuilder of() {
        return new RockCrusherRecipeBuilder();
    }

    @Override
    public Recipe<?> createRecipe() {
        return new RockCrusherRecipeType(ticks, energy, fluid_input, input, result);
    }

    @Override
    public MachineType<? extends BaseMachineBlock, ? extends BaseMachineBE, ? extends BaseMachineMenu, ? extends BaseMachineRecipeType<? extends RecipeInput>> getMachine() {
        return zMachines.ROCK_CRUSHER;
    }

    @Override
    public RockCrusherRecipeBuilder getBuilder() {
        return this;
    }

    @Override
    public RockCrusherRecipeBuilder fluid(SizedFluidIngredient fluid) {
        this.fluid_input = fluid;
        return getBuilder();
    }

    public RockCrusherRecipeBuilder addResult(ItemStack item, float chance) {
        if (this.result.size() < 9)
            this.result.add(ChanceOutputItem.of(x.itemTemplate(item.getItem(),item.count()), chance));
        return this;
    }

    public RockCrusherRecipeBuilder addResult(Item item, float chance) {
        return addResult(x.item(item), chance);
    }
    public RockCrusherRecipeBuilder addResult(Item item,int count, float chance) {
        return addResult(x.item(item,count), chance);
    }



}
