package com.synergy.machines.init.builders.macerator;

import java.util.List;

import com.devdyna.cakesticklib.api.utils.ArrayUtils;
import com.devdyna.cakesticklib.api.utils.UpgradeComponents.UpgradeType;
import com.synergy.machines.api.machine.BaseMachineScreen;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

@SuppressWarnings("null")
public class MaceratorScreen extends BaseMachineScreen<MaceratorMenu> {

    public MaceratorScreen(MaceratorMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    public List<UpgradeType> validUpgrades() {
        return ArrayUtils.concat(DEFAULT_UPGRADES, UpgradeType.LUCK);
    }

}
