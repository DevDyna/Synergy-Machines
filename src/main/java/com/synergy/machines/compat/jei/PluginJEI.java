package com.synergy.machines.compat.jei;

import static com.synergy.machines.Main.MODULE_ID;

import java.util.List;
import com.devdyna.cakesticklib.api.utils.x;
import com.synergy.machines.Client;
import com.synergy.machines.api.machine.BaseMachineScreen;
import com.synergy.machines.compat.jei.categories.AlloySmelterCategory;
import com.synergy.machines.compat.jei.categories.CasterCategory;
import com.synergy.machines.compat.jei.categories.CompressorCategory;
import com.synergy.machines.compat.jei.categories.ElectricFurnaceCategory;
import com.synergy.machines.compat.jei.categories.ExtractorCategory;
import com.synergy.machines.compat.jei.categories.MaceratorCategory;
import com.synergy.machines.compat.jei.categories.MelterCategory;
import com.synergy.machines.compat.jei.categories.RockCrusherCategory;
import com.synergy.machines.init.builders.alloy_smelter.AlloySmelterScreen;
import com.synergy.machines.init.builders.caster.CasterScreen;
import com.synergy.machines.init.builders.compressor.CompressorScreen;
import com.synergy.machines.init.builders.extractor.ExtractorScreen;
import com.synergy.machines.init.builders.furnace.ElectricFurnaceScreen;
import com.synergy.machines.init.builders.macerator.MaceratorScreen;
import com.synergy.machines.init.builders.melter.MelterScreen;
import com.synergy.machines.init.builders.rock_crusher.RockCrusherScreen;
import com.synergy.machines.init.types.zMachines;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.renderer.Rect2i;
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

                r.addCraftingStation(AlloySmelterCategory.TYPE, x.item(zMachines.ALLOY_SMELTER.item().get()));
                r.addCraftingStation(CasterCategory.TYPE, x.item(zMachines.CASTING_FACTORY.item().get()));
                r.addCraftingStation(CompressorCategory.TYPE, x.item(zMachines.COMPRESSOR.item().get()));
                r.addCraftingStation(ElectricFurnaceCategory.TYPE, x.item(zMachines.ELECTRIC_FURNACE.item().get()));
                r.addCraftingStation(ExtractorCategory.TYPE, x.item(zMachines.EXTRACTOR.item().get()));
                r.addCraftingStation(MaceratorCategory.TYPE, x.item(zMachines.MACERATOR.item().get()));
                r.addCraftingStation(MelterCategory.TYPE, x.item(zMachines.ELECTRIC_MELTER.item().get()));
                r.addCraftingStation(RockCrusherCategory.TYPE, x.item(zMachines.ROCK_CRUSHER.item().get()));

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
                                new RockCrusherCategory(helper)

                );
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

                // if (!Common.DISABLE_MACHINE_FURNACE_PROCESS_VANILLA.get())
                //         r.addRecipes(ElectricFurnaceCategory.TYPE,
                //                         (List<RecipeHolder<ElectricFurnaceRecipeType>>) getRecipes(RecipeType.SMELTING)
                //                                         .stream()
                //                                         .map(s -> {

                //                                             return    new RecipeHolder<>(Registries.RECIPE_TYPE.,null);

                //                                         }));

                // if (!Common.DISABLE_MACHINE_FURNACE_PROCESS_VANILLA.get())
                // r.addRecipes(ElectricFurnaceCategory.TYPE,
                // getRecipes(RecipeType.SMELTING).stream()
                // .map(s -> new RecipeHolder<>(
                // x.rl(MODULE_ID,zMachines.ELECTRIC_FURNACE.id()
                // + "_generated_"
                // + s.id().identifier().getPath().replace("/",
                // "")),
                // (ElectricFurnaceRecipeType) ElectricFurnaceRecipeBuilder
                // .of()
                // .delay(ElectricFurnaceBE
                // .getCalculatedDelay(
                // s.value()))
                // .energy(Common.MACHINE_FURNACE_PROCESS_VANILLA_FE_COST
                // .get())
                // .input(x.itemSized(((BaseMachineRecipeType<MonoItemInput>) s.value())
                // .getIngredients()
                // .getFirst()))
                // .output(s.value().getResultItem(
                // ServerLifecycleHooks
                // .getCurrentServer()
                // .registryAccess()))
                // .createRecipe())

                // )

                // .toList());

                r.addRecipes(ExtractorCategory.TYPE,
                                getRecipes(zMachines.EXTRACTOR.recipe().getType()));

                r.addRecipes(MaceratorCategory.TYPE,
                                getRecipes(zMachines.MACERATOR.recipe().getType()));

                r.addRecipes(MelterCategory.TYPE,
                                getRecipes(zMachines.ELECTRIC_MELTER.recipe().getType()));

                r.addRecipes(RockCrusherCategory.TYPE,
                                getRecipes(zMachines.ROCK_CRUSHER.recipe().getType()));
        }

        @SuppressWarnings("unchecked")
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

                r.addGuiContainerHandler(
                                (Class<? extends BaseMachineScreen<?>>) (Class<?>) BaseMachineScreen.class,
                                new IGuiContainerHandler<BaseMachineScreen<?>>() {
                                        @Override
                                        public List<Rect2i> getGuiExtraAreas(BaseMachineScreen<?> screen) {
                                                return List.of(
                                                                new Rect2i(
                                                                                screen.getLeftPos() + 172,
                                                                                screen.getTopPos(),
                                                                                32,
                                                                                86));
                                        }
                                });
        }

        @SuppressWarnings("unused")
        private <C extends RecipeInput, T extends Recipe<C>> List<RecipeHolder<T>> getRecipes(RecipeType<T> type) {
                return List.copyOf(Client.getRecipeCollector().byType(type));
        }

}
