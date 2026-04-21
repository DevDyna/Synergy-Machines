package com.synergy.machines.init.types;

import com.synergy.machines.api.MachineType;
import com.synergy.machines.init.builders.alloy_smelter.*;
import com.synergy.machines.init.builders.alloy_smelter.recipe.*;
import com.synergy.machines.init.builders.caster.*;
import com.synergy.machines.init.builders.caster.recipe.*;
import com.synergy.machines.init.builders.compressor.*;
import com.synergy.machines.init.builders.compressor.recipe.*;
import com.synergy.machines.init.builders.extractor.*;
import com.synergy.machines.init.builders.extractor.recipe.*;
import com.synergy.machines.init.builders.furnace.*;
import com.synergy.machines.init.builders.furnace.recipe.*;
import com.synergy.machines.init.builders.macerator.*;
import com.synergy.machines.init.builders.macerator.recipe.*;
import com.synergy.machines.init.builders.melter.*;
import com.synergy.machines.init.builders.melter.recipe.*;
import com.synergy.machines.init.builders.rock_crusher.*;
import com.synergy.machines.init.builders.rock_crusher.recipe.*;

import net.neoforged.bus.api.IEventBus;

public class zMachines {
  public static void register(IEventBus bus) {

  }

  public static final MachineType<MaceratorBlock, MaceratorBE, MaceratorMenu, MaceratorRecipeType> MACERATOR = new MachineType<>(
      "macerator",
      MaceratorBlock::new,
      MaceratorBE::new,
      MaceratorMenu::new,
      () -> MaceratorRecipeType.serializer());

  public static final MachineType<AlloySmelterBlock, AlloySmelterBE, AlloySmelterMenu, AlloySmelterRecipeType> ALLOY_SMELTER = new MachineType<>(
      "alloy_smelter",
      AlloySmelterBlock::new,
      AlloySmelterBE::new,
      AlloySmelterMenu::new,
      () -> AlloySmelterRecipeType.serializer());
  public static final MachineType<CompressorBlock, CompressorBE, CompressorMenu, CompressorRecipeType> COMPRESSOR = new MachineType<>(
      "compressor",
      CompressorBlock::new,
      CompressorBE::new,
      CompressorMenu::new,
      () -> CompressorRecipeType.serializer());

  public static final MachineType<ElectricFurnaceBlock, ElectricFurnaceBE, ElectricFurnaceMenu, ElectricFurnaceRecipeType> ELECTRIC_FURNACE = new MachineType<>(
      "electric_furnace",
      ElectricFurnaceBlock::new,
      ElectricFurnaceBE::new,
      ElectricFurnaceMenu::new,
      () -> ElectricFurnaceRecipeType.serializer());

  public static final MachineType<ExtractorBlock, ExtractorBE, ExtractorMenu, ExtractorRecipeType> EXTRACTOR = new MachineType<>(
      "extractor",
      ExtractorBlock::new,
      ExtractorBE::new,
      ExtractorMenu::new,
      () -> ExtractorRecipeType.serializer());

  public static final MachineType<CasterBlock, CasterBE, CasterMenu, CasterRecipeType> CASTING_FACTORY = new MachineType<>(
      "casting_factory",
      CasterBlock::new,
      CasterBE::new,
      CasterMenu::new,
      () -> CasterRecipeType.serializer());

  public static final MachineType<MelterBlock, MelterBE, MelterMenu, MelterRecipeType> ELECTRIC_MELTER = new MachineType<>(
      "electric_melter",
      MelterBlock::new,
      MelterBE::new,
      MelterMenu::new,
      () -> MelterRecipeType.serializer());

  public static final MachineType<RockCrusherBlock, RockCrusherBE, RockCrusherMenu, RockCrusherRecipeType> ROCK_CRUSHER = new MachineType<>(
      "rock_crusher",
      RockCrusherBlock::new,
      RockCrusherBE::new,
      RockCrusherMenu::new,
      () -> RockCrusherRecipeType.serializer());

}
