package com.synergy.machines.datagen;

import static com.synergy.machines.Main.MODULE_ID;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import com.synergy.machines.datagen.client.*;
import com.synergy.machines.datagen.server.*;
import com.synergy.machines.datagen.server.DataAdvancement.DataAdvancementGenerator;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.*;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = MODULE_ID)
public class Controller {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client e) {
        DataGenerator gen = e.getGenerator();
        CompletableFuture<HolderLookup.Provider> provider = e.getLookupProvider();
        var output = gen.getPackOutput();

        // e.createDatapackRegistryObjects(new RegistrySetBuilder(),
        //         Set.of("minecraft", MODULE_ID, CakeStickLib.MODULE_ID));

        // client

        // providerGen(e, g, new DataBlockModelState(po, f));
        e.addProvider(new DataModel(output));
        e.addProvider(new DataLang(output));

        // server

        e.addProvider(new DataAdvancement(output, provider, List.of(new DataAdvancementGenerator())));

        e.addProvider(new DataBiomeTag(output, provider));
        e.addProvider(new DataEntityTag(output, provider));
        e.addProvider(new DataFluidTag(output, provider));
        e.addProvider(new DataMaps(output, provider));
        e.addProvider(new DataWorldgen(output, provider));

        e.createBlockAndItemTags(DataBlockTag::new, DataItemTag::new);

        e.addProvider(new LootTableProvider(output, Set.of(),
                List.of(
                        new LootTableProvider.SubProviderEntry(DataLootBlock::new, LootContextParamSets.BLOCK)),
                provider));

        e.createProvider(DataRecipe.RecipeRunner::new);

    }

}