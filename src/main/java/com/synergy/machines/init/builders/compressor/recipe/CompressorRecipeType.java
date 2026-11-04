package com.synergy.machines.init.builders.compressor.recipe;

import java.util.List;
import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.synergy.machines.api.MachineType;
import com.synergy.machines.api.machine.BaseMachineBE;
import com.synergy.machines.api.machine.BaseMachineBlock;
import com.synergy.machines.api.machine.BaseMachineMenu;
import com.synergy.machines.api.machine.recipe.BaseMachineRecipeType;
import com.synergy.machines.api.recipeinputs.BiItemInput;
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

public class CompressorRecipeType extends BaseMachineRecipeType<BiItemInput> {

    private SizedIngredient input;
    private SizedIngredient mold;
    private ItemStackTemplate output;
    private boolean consumeCatalyst;

    public CompressorRecipeType(int ticks, int energy, SizedIngredient input, SizedIngredient mold,
            boolean consumeCatalyst, ItemStackTemplate output) {
        this.input = input;
        this.mold = mold;
        this.ticks = ticks;
        this.output = output;
        this.energy = energy;
        this.consumeCatalyst = consumeCatalyst;
    }

    public static CompressorRecipeType of(int ticks, int energy, SizedIngredient input,
            SizedIngredient mold, boolean consumeCatalyst, ItemStackTemplate output) {
        return new CompressorRecipeType(ticks, energy, input, mold, consumeCatalyst, output);
    }

    @Override
    public MachineType<? extends BaseMachineBlock, ? extends BaseMachineBE, ? extends BaseMachineMenu, ? extends BaseMachineRecipeType<BiItemInput>> getMachine() {
        return zMachines.COMPRESSOR;
    }

    public SizedIngredient getInput() {
        return input;
    }

    public SizedIngredient getMold() {
        return mold;
    }

    public ItemStackTemplate getOutput() {
        return output;
    }

    public boolean consumeCatalyst() {
        return consumeCatalyst;
    }

    @Override
    public List<SizedIngredient> getMachineItems() {
        return List.of(getInput(), getMold());
    }

    @Override
    public List<SizedFluidIngredient> getMachineFluids() {
        return List.of();
    }

    @Override
    public List<ItemStack> getRecipeItemsInput(BiItemInput recipe) {
        return List.of(recipe.first(), recipe.second());
    }

    @Override
    public List<FluidStack> getRecipeFluidsInput(BiItemInput recipe) {
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

    public static final RecipeSerializer<CompressorRecipeType> serializer() {
        return new RecipeSerializer<>(CODEC, STREAM_CODEC);
    }

    public static final MapCodec<CompressorRecipeType> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Codec.intRange(1, Integer.MAX_VALUE).fieldOf("ticks").forGetter(CompressorRecipeType::getTime),
            Codec.intRange(1, Integer.MAX_VALUE).fieldOf("energy").forGetter(CompressorRecipeType::getEnergy),

            SizedIngredient.NESTED_CODEC.fieldOf("input").forGetter(CompressorRecipeType::getInput),
            SizedIngredient.NESTED_CODEC.optionalFieldOf("plate")
                    .forGetter(r -> Optional.ofNullable(r.getMold())),
            Codec.BOOL.fieldOf("consume_catalyst").forGetter(CompressorRecipeType::consumeCatalyst),
            ItemStackTemplate.CODEC.fieldOf("output").forGetter(CompressorRecipeType::getOutput))
            .apply(inst, (t, e, i, p, c, o) -> new CompressorRecipeType(t, e, i, p.orElse(null), c, o)));

    public static final StreamCodec<RegistryFriendlyByteBuf, CompressorRecipeType> STREAM_CODEC = StreamCodec
            .composite(
                    ByteBufCodecs.INT, CompressorRecipeType::getTime,
                    ByteBufCodecs.INT, CompressorRecipeType::getEnergy,
                    SizedIngredient.STREAM_CODEC, CompressorRecipeType::getInput,
                    ByteBufCodecs.optional(SizedIngredient.STREAM_CODEC),
                    r -> Optional.ofNullable(r.getMold()),
                    ByteBufCodecs.BOOL, CompressorRecipeType::consumeCatalyst,
                    ItemStackTemplate.STREAM_CODEC, CompressorRecipeType::getOutput,

                    (t, e, i, o, s, c) -> new CompressorRecipeType(t, e, i, o.orElse(null), s, c));

}
