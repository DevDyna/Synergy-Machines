package com.synergy.machines.init.builders.extractor.recipe;

import java.util.List;
import java.util.Optional;

import com.devdyna.cakesticklib.api.recipe.recipeInput.ItemInput;
import com.devdyna.cakesticklib.api.recipe.recipeOutput.ChanceOutput;
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

public class ExtractorRecipeType extends BaseMachineRecipeType<ItemInput.simple> {

    private SizedIngredient input;

    private ChanceOutput.Item output;
    private FluidStackTemplate fluid;

    public ExtractorRecipeType(int ticks, int energy, SizedIngredient input,
            ChanceOutput.Item output, FluidStackTemplate fluid) {
        this.input = input;
        this.ticks = ticks;
        this.output = output;
        this.energy = energy;
        this.fluid = fluid;
    }

    public static ExtractorRecipeType of(int ticks, int energy, SizedIngredient input,
            ChanceOutput.Item output, FluidStackTemplate fluid) {
        return new ExtractorRecipeType(ticks, energy, input, output, fluid);
    }

    // @Override
    // public boolean hasSecondaryOutput() {
    // return true;
    // }

    @Override
    public MachineType<? extends BaseMachineBlock, ? extends BaseMachineBE, ? extends BaseMachineMenu, ? extends BaseMachineRecipeType<ItemInput.simple>> getMachine() {
        return zMachines.EXTRACTOR;
    }

    public SizedIngredient getInput() {
        return input;
    }

    public FluidStackTemplate getFluid() {
        return fluid;
    }

    public ChanceOutput.Item getOutput() {
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
        return List.of(getOutput().item());
    }

    @Override
    public List<FluidStackTemplate> getRecipeFluidsOutput() {
        return List.of(getFluid());
    }

    public static final RecipeSerializer<ExtractorRecipeType> serializer() {
        return new RecipeSerializer<>(CODEC, STREAM_CODEC);
    }

    public static final MapCodec<ExtractorRecipeType> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Codec.intRange(1, Integer.MAX_VALUE).fieldOf("ticks").forGetter(ExtractorRecipeType::getTime),
            Codec.intRange(1, Integer.MAX_VALUE).fieldOf("energy").forGetter(ExtractorRecipeType::getEnergy),

            SizedIngredient.NESTED_CODEC.fieldOf("input").forGetter(ExtractorRecipeType::getInput),

            ChanceOutput.Item.CODEC.optionalFieldOf("secondary_item")
                    .forGetter(r -> ChanceOutput.Item.optional(r.getOutput())),
            FluidStackTemplate.CODEC.optionalFieldOf("optional_fluid")
                    .forGetter(r -> Optional.ofNullable(r.getFluid())))
            .apply(inst, (ticks, energy, input, secondary, fluid) -> new ExtractorRecipeType(
                    ticks,
                    energy,
                    input,
                    secondary.orElse(null),
                    fluid.orElse(null))));

    public static final StreamCodec<RegistryFriendlyByteBuf, ExtractorRecipeType> STREAM_CODEC = StreamCodec
            .composite(

                    ByteBufCodecs.INT, ExtractorRecipeType::getTime,
                    ByteBufCodecs.INT, ExtractorRecipeType::getEnergy,
                    SizedIngredient.STREAM_CODEC, ExtractorRecipeType::getInput,

                    ByteBufCodecs.optional(ChanceOutput.Item.STREAM_CODEC),
                    r -> ChanceOutput.Item.optional(r.getOutput()),
                    ByteBufCodecs.optional(FluidStackTemplate.STREAM_CODEC),
                    r -> Optional.ofNullable(r.getFluid()),
                    (t, e, i, o, f) -> new ExtractorRecipeType(t, e, i, o.orElse(null), f.orElse(null)));

}
