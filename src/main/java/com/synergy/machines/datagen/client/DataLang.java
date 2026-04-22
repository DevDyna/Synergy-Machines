package com.synergy.machines.datagen.client;

import static com.devdyna.cakesticklib.api.datagen.LangUtils.*;
import static com.synergy.machines.Main.MODULE_ID;

import com.synergy.machines.init.Material;
import com.synergy.machines.init.types.*;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredHolder;

@SuppressWarnings("unused")
public class DataLang extends LanguageProvider {

        public DataLang(PackOutput o) {
                super(o, MODULE_ID, "en_us");
        }

        @Override
        protected void addTranslations() {

                Material.getAllMachineTypes().forEach(m -> addBlock(m.block(), named(m.block(), MODULE_ID)));

                addBlock(zBlocks.SOLAR_PANEL_DAY, "Solar Panel (Daytime)");
                addBlock(zBlocks.SOLAR_PANEL_NIGHT, "Solar Panel (Nighttime)");

                addItem(zItems.ENERGY_UPGRADE, named(zItems.ENERGY_UPGRADE, MODULE_ID));
                addItem(zItems.SPEED_UPGRADE, named(zItems.SPEED_UPGRADE, MODULE_ID));
                addItem(zItems.FLUID_UPGRADE, named(zItems.FLUID_UPGRADE, MODULE_ID));
                addItem(zItems.LUCK_UPGRADE, named(zItems.LUCK_UPGRADE, MODULE_ID));

                add(MODULE_ID + ".screen.upgrades", "Supported Upgrades:");

                add(MODULE_ID + ".screen.modifier.energy", TIP_COLOR + "Energy Modifier §7[§f§a%s§7]");
                add(MODULE_ID + ".screen.modifier.speed", TIP_COLOR + "Speed Modifier §7[§f§a%s§7]");
                add(MODULE_ID + ".screen.modifier.luck", TIP_COLOR + "Luck Modifier §7[§f§a%s§7]");
                add(MODULE_ID + ".screen.modifier.fluid", TIP_COLOR + "Fluid Modifier §7[§f§a%s§7]");

        }

}
