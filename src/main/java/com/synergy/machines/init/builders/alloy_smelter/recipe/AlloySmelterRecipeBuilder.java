package com.synergy.machines.init.builders.alloy_smelter.recipe;

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
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

public class AlloySmelterRecipeBuilder extends BaseMachineRecipeBuilder<AlloySmelterRecipeBuilder>
        implements // ItemAttach.Input.DoubleItemCounted<AlloySmelterRecipeBuilder>,
        // TODO API : remove extends ItemCounted
        ItemAttach.Output.SimpleOutputItem<AlloySmelterRecipeBuilder> {

    private SizedIngredient right;
    private SizedIngredient left;
    private ItemStackTemplate output;

    private AlloySmelterRecipeBuilder(HolderLookup.Provider p) {
        super(p);
        this.criteria = new LinkedHashMap<String, Criterion<?>>();
    }

    public static AlloySmelterRecipeBuilder of(HolderLookup.Provider p) {
        return new AlloySmelterRecipeBuilder(p);
    }

    @Override
    public Recipe<?> createRecipe() {
        return new AlloySmelterRecipeType(ticks, energy, right, left, output);
    }

    @Override
    public MachineType<? extends BaseMachineBlock, ? extends BaseMachineBE, ? extends BaseMachineMenu, ? extends BaseMachineRecipeType<? extends RecipeInput>> getMachine() {
        return zMachines.ALLOY_SMELTER;
    }

    @Override
    public AlloySmelterRecipeBuilder getBuilder() {
        return this;
    }

    @Override
    public AlloySmelterRecipeBuilder output(ItemStackTemplate output) {
        this.output = output;
        return getBuilder();
    }

    public AlloySmelterRecipeBuilder inputs(SizedIngredient right, SizedIngredient left) {
        this.right = right;
        this.left = left;
        return getBuilder();
    }

    public AlloySmelterRecipeBuilder inputs(Ingredient right, int a, Ingredient left, int b) {
        return this.inputs(x.itemSized(right, a), x.itemSized(left, b));
    }

    public AlloySmelterRecipeBuilder inputs(ItemLike right, int a, ItemLike left, int b) {
        return this.inputs(x.itemSized(right, a), x.itemSized(left, b));
    }

    public AlloySmelterRecipeBuilder inputs(Ingredient right, Ingredient left) {
        return this.inputs((Ingredient) right, 1, (Ingredient) left, 1);
    }

    public AlloySmelterRecipeBuilder inputs(ItemLike right, ItemLike left) {
        return this.inputs((ItemLike) right, 1, (ItemLike) left, 1);
    }

    public AlloySmelterRecipeBuilder inputs(TagKey<Item> right, TagKey<Item> left) {
        return this.inputs(right, 1, left, 1);
    }

    public AlloySmelterRecipeBuilder inputs(TagKey<Item> right, ItemLike left) {
        return this.inputs(x.itemIngredient(right, this.getProvider()), x.itemIngredient(new ItemLike[] { left }));
    }

    public AlloySmelterRecipeBuilder inputs(ItemLike right, TagKey<Item> left) {
        return this.inputs(x.itemIngredient(new ItemLike[] { right }), x.itemIngredient(left, this.getProvider()));
    }

    public AlloySmelterRecipeBuilder inputs(TagKey<Item> right, int a, TagKey<Item> left, int b) {
        return this.inputs(x.itemIngredient(right, this.getProvider()), a, x.itemIngredient(left, this.getProvider()),
                b);
    }

    public AlloySmelterRecipeBuilder inputs(TagKey<Item> right, int a, Item left, int b) {
        return this.inputs(x.itemIngredient(right, this.getProvider()), a, x.itemIngredient(left), b);
    }

    public AlloySmelterRecipeBuilder inputs(ItemLike right, int a, TagKey<Item> left, int b) {
        return this.inputs(x.itemIngredient(new ItemLike[] { right }), a, x.itemIngredient(left, this.getProvider()),
                b);
    }

    @Override
    public String defineID() {
        return x.name(output) + "_from_" + name(right) + name(left);
    }

}
