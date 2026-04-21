package com.synergy.machines.init.builders.rock_crusher.recipe;

import java.util.List;

import com.devdyna.cakesticklib.api.recipe.ChanceOutputItem;
import com.devdyna.cakesticklib.api.utils.x;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.synergy.machines.api.MachineType;
import com.synergy.machines.api.machine.*;
import com.synergy.machines.api.machine.recipe.BaseMachineRecipeType;
import com.synergy.machines.api.recipeinputs.ItemFluidInput;
import com.synergy.machines.init.types.zMachines;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

@SuppressWarnings("null")
public class RockCrusherRecipeType extends BaseMachineRecipeType<ItemFluidInput> {

    private List<ChanceOutputItem> result;

    public RockCrusherRecipeType(int ticks, int energy, SizedFluidIngredient fluid, SizedIngredient input,
            List<ChanceOutputItem> result) {
        this.input = input;
        this.ticks = ticks;
        this.energy = energy;
        this.fluid_input = fluid;
        this.result = result;
    }

    public static RockCrusherRecipeType of(int ticks, int energy, SizedFluidIngredient fluid, SizedIngredient input,
            List<ChanceOutputItem> result) {
        return new RockCrusherRecipeType(ticks, energy, fluid, input, result);
    }

    public List<ChanceOutputItem> getResult() {
        return result;
    }

    @Override
    public MachineType<? extends BaseMachineBlock, ? extends BaseMachineBE, ? extends BaseMachineMenu, ? extends BaseMachineRecipeType<ItemFluidInput>> getMachine() {
        return zMachines.ROCK_CRUSHER;
    }

    @Override
    public ItemStack getRecipeInput(ItemFluidInput recipe) {
        return recipe.item();
    }

    @Override
    public SizedFluidIngredient getRecipeFluidInput(ItemFluidInput recipe) {
        return x.fluidSized(recipe.input());
    }

    public static final RecipeSerializer<RockCrusherRecipeType> serializer() {
        return new RecipeSerializer<>(CODEC, STREAM_CODEC);
    }


        public static final MapCodec<RockCrusherRecipeType> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Codec.intRange(1, Integer.MAX_VALUE).fieldOf("ticks").forGetter(RockCrusherRecipeType::getTime),
                Codec.intRange(1, Integer.MAX_VALUE).fieldOf("energy").forGetter(RockCrusherRecipeType::getEnergy),

                SizedFluidIngredient.CODEC.fieldOf("input_fluid").forGetter(RockCrusherRecipeType::getFluidInput),
                SizedIngredient.NESTED_CODEC.fieldOf("input_item").forGetter(RockCrusherRecipeType::getInputItem),
                ChanceOutputItem.CODEC.listOf().fieldOf("result").forGetter(RockCrusherRecipeType::getResult))
                .apply(inst, RockCrusherRecipeType::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, RockCrusherRecipeType> STREAM_CODEC = StreamCodec
                .composite(
                        ByteBufCodecs.INT, RockCrusherRecipeType::getTime,
                        ByteBufCodecs.INT, RockCrusherRecipeType::getEnergy,
                        SizedFluidIngredient.STREAM_CODEC, RockCrusherRecipeType::getFluidInput,
                        SizedIngredient.STREAM_CODEC, RockCrusherRecipeType::getInputItem,
                        ChanceOutputItem.STREAM_CODEC.apply(ByteBufCodecs.list(9)), RockCrusherRecipeType::getResult,
                        RockCrusherRecipeType::new);

       
       

}
