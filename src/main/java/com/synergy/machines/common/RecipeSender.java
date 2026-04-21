package com.synergy.machines.common;

import com.devdyna.cakesticklib.api.utils.ModAddonUtil;
import com.synergy.machines.init.Material;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;

public class RecipeSender {
    @SubscribeEvent
    public static void onDatapackSync(OnDatapackSyncEvent event) {
        if (ModAddonUtil.checkMod("jei")) {
            Material.getAllMachineTypes().forEach(m -> event.sendRecipes(m.recipe().getType()));
        }

    }
}
