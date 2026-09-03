package com.synergy.machines.api.machine.recipe;

import static com.synergy.machines.Main.MODULE_ID;

import javax.annotation.Nullable;

import com.devdyna.cakesticklib.api.recipe.recipeBuilder.FluidAttach.Any.SimpleFluidAttach;
import com.devdyna.cakesticklib.api.recipe.recipeOutput.ChanceOutput;
import com.devdyna.cakesticklib.api.utils.x;
import com.synergy.machines.api.MachineType;
import com.synergy.machines.api.machine.*;

import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.fluids.FluidStackTemplate;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import com.devdyna.cakesticklib.api.recipe.recipeBuilder.*;


public abstract class BaseMachineRecipeBuilder<T extends BaseMachineRecipeBuilder<T>> extends BaseRecipeBuilder
        implements ItemAttach.Input.ItemCounted<T>, ItemAttach.Output.SimpleOutputItem<T> {

    protected BaseMachineRecipeBuilder(Provider provider) {
        super(provider);
    }

    public abstract MachineType<? extends BaseMachineBlock, ? extends BaseMachineBE, ? extends BaseMachineMenu, ? extends BaseMachineRecipeType<? extends RecipeInput>> getMachine();

    public static final int DEFAULT_TICK_DURATION = 100;
    public static final int DEFAULT_FE_COST = 500;

    protected int ticks = DEFAULT_TICK_DURATION;
    protected int energy = DEFAULT_FE_COST;
    protected SizedIngredient input;
    protected SizedIngredient optional_input;
    protected ItemStackTemplate output;
    // @Deprecated
    // protected ItemStack optional_output = ItemStack.EMPTY;
    protected @Nullable ChanceOutput.Item optional_output_item;
    protected SizedIngredient extra_input;
    // @Deprecated
    // protected float chance;
    protected boolean consumeCatalyst = false;
    protected SizedFluidIngredient fluid_input;
    protected FluidStackTemplate fluid_output;

    public T input(SizedIngredient input) {
        this.input = input;
        return getBuilder();
    }

    public T output(ItemStackTemplate output) {
        this.output = output;
        return getBuilder();
    }

    /**
     * default value -> 60t
     */
    public T delay(int ticks) {
        this.ticks = ticks;
        return getBuilder();
    }

    /**
     * default value -> 1kfe | 1000fe
     */
    public T energy(int energy) {
        this.energy = energy;
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

    @SuppressWarnings({ "deprecation", "null" })
    @Override
    public Identifier getSuffix(String extra) {

        if (output != null && output.item().value() != null)
            return x.rl(MODULE_ID, getMachinePath() + x.name(output.item().value()) + extra);

        if (this instanceof SimpleFluidAttach
                && fluid_output != null
                && fluid_output.fluid().value() != null)
            return x.rl(MODULE_ID, getMachinePath() + x.name(fluid_output.fluid().value()) + extra);

        if (ChanceOutput.Item.itemValid(optional_output_item))
            return x.rl(MODULE_ID, getMachinePath() + x.name(optional_output_item.item().item().value()) + extra);

        if (optional_input != null)
            if (optional_input.ingredient().getValues().unwrapKey().isPresent())
                return x.rl(MODULE_ID, getMachinePath()
                        + optional_input.ingredient().getValues().unwrapKey().get().location().getPath() + extra);

        if (input != null) {

            var a = input.ingredient().getValues();

            if (a.isImmediatelyResolvable())
                return x.rl(MODULE_ID, getMachinePath()
                        + x.name(a.stream().findFirst().get().value()) + extra);

            if (a.unwrapKey().isPresent())
                return x.rl(MODULE_ID, getMachinePath()
                        + a.unwrapKey().get().location().getPath() + extra);

        }

        throw new IllegalStateException("No valid ID found for " + getMachine().id() + "details: " + output + " - "
                + optional_output_item + " - " + optional_input + " - " + input);

    }

    public abstract T getBuilder();

}
