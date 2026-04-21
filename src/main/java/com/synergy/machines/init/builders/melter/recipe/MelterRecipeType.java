package com.synergy.machines.init.builders.melter.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.synergy.machines.api.MachineType;
import com.synergy.machines.api.machine.BaseMachineBE;
import com.synergy.machines.api.machine.BaseMachineBlock;
import com.synergy.machines.api.machine.BaseMachineMenu;
import com.synergy.machines.api.machine.recipe.BaseMachineRecipeType;
import com.synergy.machines.api.recipeinputs.MonoItemInput;
import com.synergy.machines.init.types.zMachines;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.fluids.FluidStack;

@SuppressWarnings("null")
public class MelterRecipeType extends BaseMachineRecipeType<MonoItemInput> {

    public MelterRecipeType(int ticks, int energy, SizedIngredient input, FluidStack fluid) {
        this.input = input;
        this.ticks = ticks;
        this.energy = energy;
        this.fluid_output = fluid;
    }

    public static MelterRecipeType of(int ticks, int energy, SizedIngredient input,
            FluidStack fluid) {
        return new MelterRecipeType(ticks, energy, input, fluid);
    }

    @Override
    public boolean hasSecondaryOutput() {
        return false;
    }

    @Override
    public MachineType<? extends BaseMachineBlock, ? extends BaseMachineBE, ? extends BaseMachineMenu, ? extends BaseMachineRecipeType<MonoItemInput>> getMachine() {
        return zMachines.ELECTRIC_MELTER;
    }

    @Override
    public ItemStack getRecipeInput(MonoItemInput recipe) {
        return recipe.input();
    }

    public static final RecipeSerializer<MelterRecipeType> serializer() {
        return new RecipeSerializer<>(CODEC, STREAM_CODEC);
    }

    public static final MapCodec<MelterRecipeType> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Codec.intRange(1, Integer.MAX_VALUE).fieldOf("ticks").forGetter(MelterRecipeType::getTime),
            Codec.intRange(1, Integer.MAX_VALUE).fieldOf("energy").forGetter(MelterRecipeType::getEnergy),

            SizedIngredient.NESTED_CODEC.fieldOf("input").forGetter(MelterRecipeType::getInputItem),

            FluidStack.CODEC.fieldOf("fluid").forGetter(MelterRecipeType::getFluidOutput))
            .apply(inst, MelterRecipeType::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, MelterRecipeType> STREAM_CODEC = StreamCodec
            .composite(

                    ByteBufCodecs.INT, MelterRecipeType::getTime,
                    ByteBufCodecs.INT, MelterRecipeType::getEnergy,
                    SizedIngredient.STREAM_CODEC, MelterRecipeType::getInputItem,
                    FluidStack.STREAM_CODEC, MelterRecipeType::getFluidOutput,
                    (ticks, energy, input, c) -> new MelterRecipeType(
                            ticks,
                            energy,
                            input,
                            c));

}
