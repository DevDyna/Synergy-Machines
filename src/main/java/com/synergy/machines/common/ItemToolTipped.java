package com.synergy.machines.common;

import com.devdyna.cakesticklib.CakeStickLib;
import com.synergy.machines.Common;
import com.synergy.machines.api.solar_panel.SolarPanelBlock;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

public class ItemToolTipped {

    @SubscribeEvent
    public static void tooltip(ItemTooltipEvent event) {

        var item = event.getItemStack();
        var tip = event.getToolTip();

        if (item.getItem() instanceof BlockItem bi)
            if (bi.getBlock() instanceof SolarPanelBlock)
                tip.add(1,
                        Component
                                .translatable(CakeStickLib.MODULE_ID + ".provider.every_tick",
                                        Common.SOLAR_PANEL_FE_GEN.get() + "", "FE")
                                .withStyle(ChatFormatting.GRAY));

    }
}
