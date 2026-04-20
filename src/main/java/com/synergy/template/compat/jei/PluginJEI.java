package com.synergy.template.compat.jei;

import static com.synergy.template.Main.MODULE_ID;

import java.util.List;

import com.devdyna.cakesticklib.api.utils.x;
import com.synergy.template.Client;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;

@JeiPlugin
public class PluginJEI implements IModPlugin {

    @Override
    public Identifier getPluginUid() {
        return x.rl(MODULE_ID, "jei_plugin");
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration r) {

    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration r) {

    }

    @Override
    public void registerRecipes(IRecipeRegistration r) {

    }

    @SuppressWarnings("unused")
    private <C extends RecipeInput, T extends Recipe<C>> List<RecipeHolder<T>> getRecipes(RecipeType<T> type) {
        return List.copyOf(Client.getRecipeCollector().byType(type));
    }

}
