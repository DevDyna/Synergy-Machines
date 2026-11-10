package com.synergy.machines.api.machine.recipe;

import static com.synergy.machines.Main.MODULE_ID;

import javax.annotation.Nullable;

import com.devdyna.cakesticklib.api.utils.x;
import com.synergy.machines.api.MachineType;
import com.synergy.machines.api.machine.*;

import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

import com.devdyna.cakesticklib.api.recipe.recipeBuilder.*;

public abstract class BaseMachineRecipeBuilder<T extends BaseMachineRecipeBuilder<T>> extends BaseRecipeBuilder {

    protected BaseMachineRecipeBuilder(Provider provider) {
        super(provider);
    }

    public abstract MachineType<? extends BaseMachineBlock, ? extends BaseMachineBE, ? extends BaseMachineMenu, ? extends BaseMachineRecipeType<? extends RecipeInput>> getMachine();

    public static final int DEFAULT_TICK_DURATION = 100;
    public static final int DEFAULT_FE_COST = 50;

    protected int ticks = DEFAULT_TICK_DURATION;
    protected int energy = DEFAULT_FE_COST;

    // protected SizedIngredient input;
    // protected SizedIngredient optional_input;
    // protected ItemStackTemplate output;
    // protected ChanceOutput.Item optional_output_item;
    // protected SizedIngredient extra_input;
    // protected boolean consumeCatalyst = false;
    // protected SizedFluidIngredient fluid_input;
    // protected FluidStackTemplate fluid_output;

    // public T input(SizedIngredient input) {
    // this.input = input;
    // return getBuilder();
    // }

    // public T output(ItemStackTemplate output) {
    // this.output = output;
    // return getBuilder();
    // }

    /**
     * base value -> 100t
     */
    public T delay(int ticks) {
        this.ticks = ticks;
        return getBuilder();
    }

    /**
     * base value -> 50fe/t
     */
    public T energy(int energy) {
        this.energy = energy;
        return getBuilder();
    }

    /**
     * Reset All Base Machine values to the default values
     * <br/>
     * <br/>
     * base value -> {@code 100t}
     * <br/>
     * <br/>
     * base value -> {@code 50fe/t}
     */
    public T overrideBaseMachine() {
        overrideBaseEnergy();
        overrideBaseDelay();
        return getBuilder();
    }

    /**
     * Reset Base Machine energy values to the default
     * <br/>
     * <br/>
     * base value -> {@code 50fe/t}
     */
    public T overrideBaseEnergy() {
        this.energy = DEFAULT_FE_COST;
        return getBuilder();
    }

    /**
     * Reset Base Machine ticks values to the default
     * <br/>
     * <br/>
     * base value -> {@code 100t}
     */
    public T overrideBaseDelay() {
        this.ticks = DEFAULT_TICK_DURATION;
        return getBuilder();
    }

    public T unlockedBy() {
        return unlockedBy("has_" + getMachine().id(),
                InventoryChangeTrigger.TriggerInstance.hasItems(getMachine().item().get()));

        // unlockedBy(MODULE_ID, InventoryChangeTrigger.TriggerInstance
        // .hasItems(this.input == null
        // ? new Item[] {
        // x.getFluidStacksFromIngredient(fluid_input).getFirst().getFluid().getBucket()
        // }
        // : x.getItemStacksFromIngredient(input).stream()
        // .map(ItemStack::getItem)
        // .toArray(Item[]::new)));
    }

    public T unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return getBuilder();
    }

    public T group(@Nullable String groupName) {
        return getBuilder();
    }

    private String suffix = "";

    public String getMachinePath() {
        return getMachine().id() + "/" + suffix;
    }

    public T defineSuffix(String s) {
        this.suffix = s;
        return getBuilder();
    }

    public abstract String defineID();

    @Override
    public Identifier getSuffix(String extra) {
        return x.rl(MODULE_ID, getMachinePath() + defineID() + extra);
    }

    public abstract T getBuilder();

    // TODO API : move to api
    protected String name(Ingredient i) {
        var v = i.getValues();

        if (v.isImmediatelyResolvable())
            return x.name(v.stream().findFirst().get().value());

        if (v.unwrapKey().isPresent())
            return v.unwrapKey().get().location().getPath();

        return null;
    }

    protected String name(SizedIngredient i) {
        return name(i.ingredient());
    }

    protected String name(FluidIngredient i) {
        return x.name(i.fluids().stream().findFirst().get().value());
    }

    protected String name(SizedFluidIngredient i) {
        return name(i.ingredient());
    }

}
