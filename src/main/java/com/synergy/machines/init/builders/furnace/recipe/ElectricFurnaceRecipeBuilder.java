package com.synergy.machines.init.builders.furnace.recipe;

import java.util.LinkedHashMap;

import com.synergy.machines.api.MachineType;
import com.synergy.machines.api.machine.*;
import com.synergy.machines.api.machine.recipe.BaseMachineRecipeBuilder;
import com.synergy.machines.api.machine.recipe.BaseMachineRecipeType;
import com.synergy.machines.init.types.zMachines;

import net.minecraft.advancements.Criterion;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;

@SuppressWarnings({ "null" })
public class ElectricFurnaceRecipeBuilder extends BaseMachineRecipeBuilder<ElectricFurnaceRecipeBuilder> {

    private ElectricFurnaceRecipeBuilder() {
        this.criteria = new LinkedHashMap<String, Criterion<?>>();
    }

    public static ElectricFurnaceRecipeBuilder of() {
        return new ElectricFurnaceRecipeBuilder();
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

}
