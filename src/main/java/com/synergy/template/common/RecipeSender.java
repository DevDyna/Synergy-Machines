package com.synergy.template.common;

import com.devdyna.cakesticklib.api.utils.ModAddonUtil;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;

public class RecipeSender {
    @SubscribeEvent
    public static void onDatapackSync(OnDatapackSyncEvent event) {
        if (ModAddonUtil.checkMod("jei")) {

        }

    }
}
