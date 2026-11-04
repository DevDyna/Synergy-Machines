package com.synergy.machines.init.builders.melter.recipe;

import java.util.List;

import com.devdyna.cakesticklib.api.recipe.recipeInput.ItemInput;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.synergy.machines.api.MachineType;
import com.synergy.machines.api.machine.BaseMachineBE;
import com.synergy.machines.api.machine.BaseMachineBlock;
import com.synergy.machines.api.machine.BaseMachineMenu;
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

public class MelterRecipeType extends BaseMachineRecipeType<ItemInput.simple> {

    private SizedIngredient input;
    private FluidStackTemplate fluid;

    public MelterRecipeType(int ticks, int energy, SizedIngredient input, FluidStackTemplate fluid) {
        this.input = input;
        this.ticks = ticks;
        this.energy = energy;
        this.fluid = fluid;
    }

    public static MelterRecipeType of(int ticks, int energy, SizedIngredient input,
            FluidStackTemplate fluid) {
        return new MelterRecipeType(ticks, energy, input, fluid);
    }

    public SizedIngredient getInput() {
        return input;
    }

    public FluidStackTemplate getFluid() {
        return fluid;
    }

    @Override
    public MachineType<? extends BaseMachineBlock, ? extends BaseMachineBE, ? extends BaseMachineMenu, ? extends BaseMachineRecipeType<ItemInput.simple>> getMachine() {
        return zMachines.ELECTRIC_MELTER;
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
        return List.of();
    }

    @Override
    public List<FluidStackTemplate> getRecipeFluidsOutput() {
        return List.of(getFluid());
    }

    public static final RecipeSerializer<MelterRecipeType> serializer() {
        return new RecipeSerializer<>(CODEC, STREAM_CODEC);
    }

    public static final MapCodec<MelterRecipeType> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Codec.intRange(1, Integer.MAX_VALUE).fieldOf("ticks").forGetter(MelterRecipeType::getTime),
            Codec.intRange(1, Integer.MAX_VALUE).fieldOf("energy").forGetter(MelterRecipeType::getEnergy),

            SizedIngredient.NESTED_CODEC.fieldOf("input").forGetter(MelterRecipeType::getInput),

            FluidStackTemplate.CODEC.fieldOf("fluid").forGetter(MelterRecipeType::getFluid))
            .apply(inst, MelterRecipeType::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, MelterRecipeType> STREAM_CODEC = StreamCodec
            .composite(
                    ByteBufCodecs.INT, MelterRecipeType::getTime,
                    ByteBufCodecs.INT, MelterRecipeType::getEnergy,
                    SizedIngredient.STREAM_CODEC, MelterRecipeType::getInput,
                    FluidStackTemplate.STREAM_CODEC, MelterRecipeType::getFluid,
                    (t, e, i, c) -> new MelterRecipeType(t, e, i, c));

}
