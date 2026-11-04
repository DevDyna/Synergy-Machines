package com.synergy.machines.init.builders.framework.recipe;

import java.util.List;

import com.devdyna.cakesticklib.api.recipe.recipeInput.ItemInput;
import com.devdyna.cakesticklib.api.recipe.recipeInput.ItemInput.dual;
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

public class FrameworkRecipeType extends BaseMachineRecipeType<ItemInput.dual> {

    private SizedIngredient input;
    private SizedIngredient catalyst;
    private ItemStackTemplate output;

    public FrameworkRecipeType(int ticks, int energy, SizedIngredient input, SizedIngredient catalyst,
            ItemStackTemplate output) {
        this.input = input;
        this.ticks = ticks;
        this.energy = energy;
        this.catalyst = catalyst;
        this.output = output;
    }

    public static FrameworkRecipeType of(int ticks, int energy, SizedIngredient input, SizedIngredient catalyst,
            ItemStackTemplate output) {
        return new FrameworkRecipeType(ticks, energy, input, catalyst, output);
    }

    @Override
    public MachineType<? extends BaseMachineBlock, ? extends BaseMachineBE, ? extends BaseMachineMenu, ? extends BaseMachineRecipeType<ItemInput.dual>> getMachine() {
        return zMachines.FRAMEWORK;
    }

    public SizedIngredient getInput() {
        return input;
    }

    public ItemStackTemplate getOutput() {
        return output;
    }

    public SizedIngredient getCatalyst() {
        return catalyst;
    }

    @Override
    public List<SizedIngredient> getMachineItems() {
        return List.of(getInput(), getCatalyst());
    }

    @Override
    public List<SizedFluidIngredient> getMachineFluids() {
        return List.of();
    }

    @Override
    public List<ItemStack> getRecipeItemsInput(dual recipe) {
        return List.of(recipe.first(), recipe.second());
    }

    @Override
    public List<FluidStack> getRecipeFluidsInput(dual recipe) {
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

    public static final RecipeSerializer<FrameworkRecipeType> serializer() {
        return new RecipeSerializer<>(CODEC, STREAM_CODEC);
    }

    public static final MapCodec<FrameworkRecipeType> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Codec.intRange(1, Integer.MAX_VALUE).fieldOf("ticks").forGetter(FrameworkRecipeType::getTime),
            Codec.intRange(1, Integer.MAX_VALUE).fieldOf("energy").forGetter(FrameworkRecipeType::getEnergy),

            SizedIngredient.NESTED_CODEC.fieldOf("input").forGetter(FrameworkRecipeType::getInput),
            SizedIngredient.NESTED_CODEC.fieldOf("machine").forGetter(FrameworkRecipeType::getCatalyst),
            ItemStackTemplate.CODEC.fieldOf("result").forGetter(FrameworkRecipeType::getOutput))
            .apply(inst, FrameworkRecipeType::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, FrameworkRecipeType> STREAM_CODEC = StreamCodec
            .composite(
                    ByteBufCodecs.INT, FrameworkRecipeType::getTime,
                    ByteBufCodecs.INT, FrameworkRecipeType::getEnergy,
                    SizedIngredient.STREAM_CODEC, FrameworkRecipeType::getInput,
                    SizedIngredient.STREAM_CODEC, FrameworkRecipeType::getCatalyst,
                    ItemStackTemplate.STREAM_CODEC, FrameworkRecipeType::getOutput,
                    FrameworkRecipeType::new);

}
