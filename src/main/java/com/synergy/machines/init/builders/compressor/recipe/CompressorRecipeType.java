package com.synergy.machines.init.builders.compressor.recipe;

import java.util.Optional;

import com.devdyna.cakesticklib.api.utils.x;
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

@SuppressWarnings("null")
public class CompressorRecipeType extends BaseMachineRecipeType<BiItemInput> {

    public CompressorRecipeType(int ticks, int energy, SizedIngredient input, SizedIngredient plate,
            boolean consumeCatalyst, ItemStackTemplate output) {
        this.input = input;
        this.optional_input = plate;
        this.ticks = ticks;
        this.output = output;
        this.energy = energy;
        this.consumeCatalyst = consumeCatalyst;
    }

    public static CompressorRecipeType of(int ticks, int energy, SizedIngredient input,
            SizedIngredient plate, boolean consumeCatalyst, ItemStackTemplate output) {
        return new CompressorRecipeType(ticks, energy, input, plate, consumeCatalyst, output);
    }

    @Override
    public MachineType<? extends BaseMachineBlock, ? extends BaseMachineBE, ? extends BaseMachineMenu, ? extends BaseMachineRecipeType<BiItemInput>> getMachine() {
        return zMachines.COMPRESSOR;
    }

    @Override
    public ItemStack getRecipeInput(BiItemInput recipe) {
        return recipe.first();
    }

    @Override
    public ItemStack getRecipeInput2(BiItemInput recipe) {
        return recipe.second();
    }

    public static final RecipeSerializer<CompressorRecipeType> serializer() {
        return new RecipeSerializer<>(CODEC, STREAM_CODEC);
    }


        public static final MapCodec<CompressorRecipeType> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Codec.intRange(1, Integer.MAX_VALUE).fieldOf("ticks").forGetter(CompressorRecipeType::getTime),
                Codec.intRange(1, Integer.MAX_VALUE).fieldOf("energy").forGetter(CompressorRecipeType::getEnergy),

                SizedIngredient.NESTED_CODEC.fieldOf("input").forGetter(CompressorRecipeType::getInputItem),
                SizedIngredient.NESTED_CODEC.optionalFieldOf("plate", null)
                        .forGetter(r -> (r.getCatalystItem() == null)
                                ? null
                                : r.getCatalystItem()),
                Codec.BOOL.fieldOf("consume_catalyst").forGetter(CompressorRecipeType::consumeCatalyst),
                ItemStackTemplate.CODEC.fieldOf("output").forGetter(CompressorRecipeType::getOutputItem))
                .apply(inst, CompressorRecipeType::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, CompressorRecipeType> STREAM_CODEC = StreamCodec
                .composite(
                        ByteBufCodecs.INT, CompressorRecipeType::getTime,
                        ByteBufCodecs.INT, CompressorRecipeType::getEnergy,
                        SizedIngredient.STREAM_CODEC, CompressorRecipeType::getInputItem,
                        ByteBufCodecs.optional(SizedIngredient.STREAM_CODEC),
                        r -> (r.getCatalystItem() == null || x.getItems(r.getCatalystItem()).isEmpty())
                                ? Optional.empty()
                                : Optional.of(r.getCatalystItem()),
                        ByteBufCodecs.BOOL, CompressorRecipeType::consumeCatalyst,
                        ItemStackTemplate.STREAM_CODEC, CompressorRecipeType::getOutputItem,

                        (t, e, i, o, s, c) -> new CompressorRecipeType(t, e, i, o.orElse(null), s, c));

        

}
