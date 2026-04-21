package com.synergy.machines;

import com.synergy.machines.init.builders.alloy_smelter.AlloySmelterScreen;
import com.synergy.machines.init.builders.caster.CasterScreen;
import com.synergy.machines.init.builders.compressor.CompressorScreen;
import com.synergy.machines.init.builders.extractor.ExtractorScreen;
import com.synergy.machines.init.builders.furnace.ElectricFurnaceScreen;
import com.synergy.machines.init.builders.macerator.MaceratorScreen;
import com.synergy.machines.init.builders.melter.MelterScreen;
import com.synergy.machines.init.builders.rock_crusher.RockCrusherScreen;
import com.synergy.machines.init.types.zMachines;

import net.minecraft.world.item.crafting.RecipeMap;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RecipesReceivedEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = Main.MODULE_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Main.MODULE_ID, value = Dist.CLIENT)
public class Client {

    public Client(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    public static void render(EntityRenderersEvent.RegisterRenderers event) {

    }

     @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(zMachines.ALLOY_SMELTER.menu().get(), AlloySmelterScreen::new);
        event.register(zMachines.CASTING_FACTORY.menu().get(), CasterScreen::new);
        event.register(zMachines.COMPRESSOR.menu().get(), CompressorScreen::new);
        event.register(zMachines.ELECTRIC_FURNACE.menu().get(), ElectricFurnaceScreen::new);
        event.register(zMachines.ELECTRIC_MELTER.menu().get(), MelterScreen::new);
        event.register(zMachines.EXTRACTOR.menu().get(), ExtractorScreen::new);
        event.register(zMachines.MACERATOR.menu().get(), MaceratorScreen::new);
        event.register(zMachines.ROCK_CRUSHER.menu().get(), RockCrusherScreen::new);
    }



    // Recipe collector client-side

    private static RecipeMap recipeCollector = RecipeMap.EMPTY;

    @SubscribeEvent
    public static void onRecipesSynced(RecipesReceivedEvent event) {
        if (ModList.get().isLoaded("jei"))
            recipeCollector = event.getRecipeMap();
    }

    @SubscribeEvent
    public static void onClientLogout(ClientPlayerNetworkEvent.LoggingOut event) {
        recipeCollector = RecipeMap.EMPTY;
    }

    public static RecipeMap getRecipeCollector() {
        return recipeCollector;
    }

}
