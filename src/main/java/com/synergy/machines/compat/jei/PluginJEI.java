package com.synergy.machines.compat.jei;

import static com.synergy.machines.Main.MODULE_ID;

import java.util.List;
import com.devdyna.cakesticklib.api.utils.x;
import com.synergy.machines.*;
import com.synergy.machines.compat.jei.categories.*;
import com.synergy.machines.init.builders.alloy_smelter.AlloySmelterScreen;
import com.synergy.machines.init.builders.caster.CasterScreen;
import com.synergy.machines.init.builders.compressor.CompressorScreen;
import com.synergy.machines.init.builders.extractor.ExtractorScreen;
import com.synergy.machines.init.builders.furnace.ElectricFurnaceScreen;
import com.synergy.machines.init.builders.macerator.MaceratorScreen;
import com.synergy.machines.init.builders.melter.MelterScreen;
import com.synergy.machines.init.builders.rock_crusher.RockCrusherScreen;
import com.synergy.machines.init.types.zMachines;
import mezz.jei.api.*;
import mezz.jei.api.registration.*;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.*;

@JeiPlugin
public class PluginJEI implements IModPlugin {

        @Override
        public Identifier getPluginUid() {
                return x.rl(MODULE_ID, "jei_plugin");
        }

        @Override
        public void registerRecipeCatalysts(IRecipeCatalystRegistration r) {
                r.addCraftingStation(AlloySmelterCategory.TYPE, x.item(zMachines.ALLOY_SMELTER.item().get()));
                r.addCraftingStation(CasterCategory.TYPE, x.item(zMachines.CASTING_FACTORY.item().get()));
                r.addCraftingStation(CompressorCategory.TYPE, x.item(zMachines.COMPRESSOR.item().get()));
                r.addCraftingStation(ElectricFurnaceCategory.TYPE, x.item(zMachines.ELECTRIC_FURNACE.item().get()));
                r.addCraftingStation(ExtractorCategory.TYPE, x.item(zMachines.EXTRACTOR.item().get()));
                r.addCraftingStation(MaceratorCategory.TYPE, x.item(zMachines.MACERATOR.item().get()));
                r.addCraftingStation(MelterCategory.TYPE, x.item(zMachines.ELECTRIC_MELTER.item().get()));
                r.addCraftingStation(RockCrusherCategory.TYPE, x.item(zMachines.ROCK_CRUSHER.item().get()));
                r.addCraftingStation(FrameworkCategory.TYPE, x.item(zMachines.FRAMEWORK.item().get()));
        }

        @Override
        public void registerCategories(IRecipeCategoryRegistration r) {
                var helper = r.getJeiHelpers().getGuiHelper();

                r.addRecipeCategories(
                                new AlloySmelterCategory(helper),
                                new CasterCategory(helper),
                                new CompressorCategory(helper),
                                new ElectricFurnaceCategory(helper),
                                new ExtractorCategory(helper),
                                new MaceratorCategory(helper),
                                new MelterCategory(helper),
                                new RockCrusherCategory(helper),
                                new FrameworkCategory(helper));
        }

        @Override
        public void registerRecipes(IRecipeRegistration r) {
                r.addRecipes(AlloySmelterCategory.TYPE,
                                getRecipes(zMachines.ALLOY_SMELTER.recipe().getType()));

                r.addRecipes(CasterCategory.TYPE,
                                getRecipes(zMachines.CASTING_FACTORY.recipe().getType()));

                r.addRecipes(CompressorCategory.TYPE,
                                getRecipes(zMachines.COMPRESSOR.recipe().getType()));

                r.addRecipes(ElectricFurnaceCategory.TYPE,
                                getRecipes(zMachines.ELECTRIC_FURNACE.recipe().getType()));

                if (!Config.DISABLE_MACHINE_FURNACE_PROCESS_VANILLA.get())
                        r.addRecipes(ElectricFurnaceCategory.TYPE,
                                        ElectricFurnaceCategory.fromSmelting(getRecipes(RecipeType.SMELTING)));

                r.addRecipes(ExtractorCategory.TYPE,
                                getRecipes(zMachines.EXTRACTOR.recipe().getType()));

                r.addRecipes(MaceratorCategory.TYPE,
                                getRecipes(zMachines.MACERATOR.recipe().getType()));

                r.addRecipes(MelterCategory.TYPE,
                                getRecipes(zMachines.ELECTRIC_MELTER.recipe().getType()));

                r.addRecipes(RockCrusherCategory.TYPE,
                                getRecipes(zMachines.ROCK_CRUSHER.recipe().getType()));

                r.addRecipes(FrameworkCategory.TYPE,
                                getRecipes(zMachines.FRAMEWORK.recipe().getType()));

        }

        @Override
        public void registerGuiHandlers(IGuiHandlerRegistration r) {

                r.addRecipeClickArea(MaceratorScreen.class, 75, 35, 22, 15,
                                MaceratorCategory.TYPE);

                r.addRecipeClickArea(CompressorScreen.class, 75, 35, 22, 15,
                                CompressorCategory.TYPE);

                r.addRecipeClickArea(AlloySmelterScreen.class, 75, 35, 22, 15,
                                AlloySmelterCategory.TYPE);

                r.addRecipeClickArea(ElectricFurnaceScreen.class, 75, 35, 22, 15,
                                ElectricFurnaceCategory.TYPE);

                r.addRecipeClickArea(ExtractorScreen.class, 75, 35, 22, 15,
                                ExtractorCategory.TYPE);
                r.addRecipeClickArea(CasterScreen.class, 75, 35, 22, 15,
                                CasterCategory.TYPE);
                r.addRecipeClickArea(MelterScreen.class, 75, 35, 22, 15,
                                MelterCategory.TYPE);
                r.addRecipeClickArea(RockCrusherScreen.class, 75, 35, 22, 15,
                                RockCrusherCategory.TYPE);

        }

        private <C extends RecipeInput, T extends Recipe<C>> List<RecipeHolder<T>> getRecipes(RecipeType<T> type) {
                return List.copyOf(Client.getRecipeCollector().byType(type));
        }

}
