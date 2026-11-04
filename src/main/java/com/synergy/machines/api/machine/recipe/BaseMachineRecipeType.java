package com.synergy.machines.api.machine.recipe;

import java.util.ArrayList;
import java.util.List;
import com.devdyna.cakesticklib.api.utils.x;
import com.synergy.machines.api.MachineType;
import com.synergy.machines.api.machine.*;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidStackTemplate;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

public abstract class BaseMachineRecipeType<T extends RecipeInput> implements Recipe<T> {

    protected int ticks;
    protected int energy;

    public int getEnergy() {
        return energy;
    }

    public int getTime() {
        return ticks;
    }

    public abstract List<SizedIngredient> getMachineItems();

    public abstract List<SizedFluidIngredient> getMachineFluids();

    public abstract List<ItemStack> getRecipeItemsInput(T recipe);

    public abstract List<FluidStack> getRecipeFluidsInput(T recipe);

    public abstract List<ItemStackTemplate> getRecipeItemsOutput();

    public abstract List<FluidStackTemplate> getRecipeFluidsOutput();

    @Deprecated
    public NonNullList<Ingredient> getIngredients() {
        var list = new ArrayList<Ingredient>();
        if (getMachineItems() != null)
            getMachineItems().stream().map(SizedIngredient::ingredient).forEach(list::add);
        return NonNullList.copyOf(list);
    }

    @Override
    public boolean matches(T r, Level level) {

        var items = getRecipeItemsInput(r);

        if (!getMachineItems().isEmpty()) {

            if (items.isEmpty())
                return false;

            var copy = new ArrayList<Integer>(items.stream().map(ItemStack::getCount).toList());

            for (var stored : getMachineItems()) {

                var required = stored.count();

                for (var i = 0; i < items.size(); i++) {

                    if (required <= 0)
                        break;

                    if (copy.get(i) <= 0)
                        continue;

                    if (!stored.ingredient().test(items.get(i)))
                        continue;

                    var extracted = Math.min(required, copy.get(i));

                    required -= extracted;
                    copy.set(i, copy.get(i) - extracted);
                }

                if (required > 0)
                    return false;
            }
        }

        var fluids = getRecipeFluidsInput(r);

        if (!getMachineFluids().isEmpty()) {

            if (fluids.isEmpty())
                return false;

            var copy = new ArrayList<Integer>(fluids.stream().map(FluidStack::getAmount).toList());

            for (var stored : getMachineFluids()) {

                var required = stored.amount();

                for (var i = 0; i < fluids.size(); i++) {

                    if (required <= 0)
                        break;

                    if (copy.get(i) <= 0)
                        continue;

                    if (!stored.ingredient().test(fluids.get(i)))
                        continue;

                    var extracted = Math.min(required, copy.get(i));

                    required -= extracted;
                    copy.set(i, copy.get(i) - extracted);
                }

                if (required > 0)
                    return false;
            }
        }

        return true;
    }

    @Deprecated
    @Override
    public ItemStack assemble(T input) {
        return x.item(getMachine().item().get());
    }

    @Override
    public boolean showNotification() {
        return true;
    }

    @Override
    public String group() {
        return getMachine().id();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.create(x.itemIngredient(getMachine().item().get()));
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return Registry.register(BuiltInRegistries.RECIPE_BOOK_CATEGORY, getMachine().id(), new RecipeBookCategory());
    }

    public abstract MachineType<? extends BaseMachineBlock, ? extends BaseMachineBE, ? extends BaseMachineMenu, ? extends BaseMachineRecipeType<T>> getMachine();

    public RecipeType<? extends Recipe<T>> getType() {
        return getMachine().recipe().getType();
    }

    public ItemStack getToastSymbol() {
        return x.item(getMachine().block().get());
    }

    @Override
    public RecipeSerializer<? extends Recipe<T>> getSerializer() {
        return getMachine().recipe().getSerializer();
    }

}
