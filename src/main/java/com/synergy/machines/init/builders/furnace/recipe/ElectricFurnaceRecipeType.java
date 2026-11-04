package com.synergy.machines.init.builders.furnace.recipe;

import java.util.List;

import com.devdyna.cakesticklib.api.recipe.recipeInput.ItemInput;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.synergy.machines.api.MachineType;
import com.synergy.machines.api.machine.*;
import com.synergy.machines.api.machine.recipe.BaseMachineRecipeType;
import com.synergy.machines.init.types.zMachines;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidStackTemplate;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

public class ElectricFurnaceRecipeType extends BaseMachineRecipeType<ItemInput.simple> {

    private SizedIngredient input;
    private ItemStackTemplate output;
 

    public ElectricFurnaceRecipeType(int ticks, int energy, SizedIngredient input,
            ItemStackTemplate output) {
        this.input = input;
        this.ticks = ticks;
        this.output = output;
        this.energy = energy;
    }

    public static ElectricFurnaceRecipeType of(int ticks, int energy, SizedIngredient input,
            ItemStackTemplate output) {
        return new ElectricFurnaceRecipeType(ticks, energy, input, output);
    }

    @Override
    public MachineType<? extends BaseMachineBlock, ? extends BaseMachineBE, ? extends BaseMachineMenu, ? extends BaseMachineRecipeType<ItemInput.simple>> getMachine() {
        return zMachines.ELECTRIC_FURNACE;
    }

    public SizedIngredient getInput() {
        return input;
    }

    public ItemStackTemplate getOutput() {
        return output;
    }

    @Override
    public List<SizedIngredient> getMachineItems() {
        return List.of(getInput());
    }

    @Override
    public List<SizedFluidIngredient> getMachineFluids() {
        return List.of();
    }

    @Override
    public List<ItemStack> getRecipeItemsInput(ItemInput.simple recipe) {
        return List.of(recipe.item());
    }

    @Override
    public List<FluidStack> getRecipeFluidsInput(ItemInput.simple recipe) {
        return List.of();
    }

    @Override
    public List<ItemStackTemplate> getRecipeItemsOutput() {
        return List.of(getOutput());
    }

    @Override
    public List<FluidStackTemplate> getRecipeFluidsOutput() {
        return List.of();
    }


    public static final RecipeSerializer<ElectricFurnaceRecipeType> serializer() {
        return new RecipeSerializer<>(CODEC, STREAM_CODEC);
    }

    public static final MapCodec<ElectricFurnaceRecipeType> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Codec.intRange(1, Integer.MAX_VALUE).fieldOf("ticks").forGetter(ElectricFurnaceRecipeType::getTime),
            Codec.intRange(1, Integer.MAX_VALUE).fieldOf("energy").forGetter(ElectricFurnaceRecipeType::getEnergy),

            SizedIngredient.NESTED_CODEC.fieldOf("input").forGetter(ElectricFurnaceRecipeType::getInput),
            ItemStackTemplate.CODEC.fieldOf("output").forGetter(ElectricFurnaceRecipeType::getOutput))
            .apply(inst, ElectricFurnaceRecipeType::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ElectricFurnaceRecipeType> STREAM_CODEC = StreamCodec
            .composite(
                    ByteBufCodecs.INT, ElectricFurnaceRecipeType::getTime,
                    ByteBufCodecs.INT, ElectricFurnaceRecipeType::getEnergy,
                    SizedIngredient.STREAM_CODEC, ElectricFurnaceRecipeType::getInput,
                    ItemStackTemplate.STREAM_CODEC, ElectricFurnaceRecipeType::getOutput,
                    ElectricFurnaceRecipeType::new);

    

}
