package com.synergy.machines.init.builders.extractor.recipe;

import java.util.Optional;

import com.devdyna.cakesticklib.api.recipe.ChanceOutputItem;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.synergy.machines.api.MachineType;
import com.synergy.machines.api.machine.*;
import com.synergy.machines.api.machine.recipe.BaseMachineRecipeType;
import com.synergy.machines.api.recipeinputs.MonoItemInput;
import com.synergy.machines.init.types.zMachines;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.fluids.FluidStackTemplate;

@SuppressWarnings("null")
public class ExtractorRecipeType extends BaseMachineRecipeType<MonoItemInput> {

    public ExtractorRecipeType(int ticks, int energy, SizedIngredient input,
            ChanceOutputItem secondary, FluidStackTemplate fluid) {
        this.input = input;
        this.ticks = ticks;
        this.optional_output_item = secondary;
        this.energy = energy;
        this.fluid_output = fluid;
    }

    public static ExtractorRecipeType of(int ticks, int energy, SizedIngredient input,
            ChanceOutputItem secondary, FluidStackTemplate fluid) {
        return new ExtractorRecipeType(ticks, energy, input, secondary, fluid);
    }

    // @Override
    // public boolean hasSecondaryOutput() {
    //     return true;
    // }

    @Override
    public MachineType<? extends BaseMachineBlock, ? extends BaseMachineBE, ? extends BaseMachineMenu, ? extends BaseMachineRecipeType<MonoItemInput>> getMachine() {
        return zMachines.EXTRACTOR;
    }

    @Override
    public ItemStack getRecipeInput(MonoItemInput recipe) {
        return recipe.input();
    }

    public static final RecipeSerializer<ExtractorRecipeType> serializer() {
        return new RecipeSerializer<>(CODEC, STREAM_CODEC);
    }

        public static final MapCodec<ExtractorRecipeType> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Codec.intRange(1, Integer.MAX_VALUE).fieldOf("ticks").forGetter(ExtractorRecipeType::getTime),
                Codec.intRange(1, Integer.MAX_VALUE).fieldOf("energy").forGetter(ExtractorRecipeType::getEnergy),

                SizedIngredient.NESTED_CODEC.fieldOf("input").forGetter(ExtractorRecipeType::getInputItem),

                ChanceOutputItem.CODEC.optionalFieldOf("secondary_item")
                        .forGetter(r -> ChanceOutputItem.optional(r.getSecondaryOutputItem())),
                FluidStackTemplate.CODEC.optionalFieldOf("optional_fluid", null)
                        .forGetter(r -> optionalCodec(r.getFluidOutput())))
                .apply(inst, (ticks, energy, input, secondary,fluid) -> new ExtractorRecipeType(
                        ticks,
                        energy,
                        input,
                        secondary.orElse(null),
                        fluid)));

        public static final StreamCodec<RegistryFriendlyByteBuf, ExtractorRecipeType> STREAM_CODEC = StreamCodec
                .composite(

                        ByteBufCodecs.INT, ExtractorRecipeType::getTime,
                        ByteBufCodecs.INT, ExtractorRecipeType::getEnergy,
                        SizedIngredient.STREAM_CODEC, ExtractorRecipeType::getInputItem,

                        ByteBufCodecs.optional(ChanceOutputItem.STREAM_CODEC),
                        r -> ChanceOutputItem.optional(r.getSecondaryOutputItem()),
                        ByteBufCodecs.optional(FluidStackTemplate.STREAM_CODEC),
                        r -> (r.getFluidOutput() == null || r.getFluidOutput().fluid() == null)
                                ? Optional.empty()
                                : Optional.of(r.getFluidOutput()),
                        (ticks, energy, input, secondary,fluid) -> new ExtractorRecipeType(
                        ticks,
                        energy,
                        input,
                        secondary.orElse(null),
                        fluid.orElse(null)));

       

}
