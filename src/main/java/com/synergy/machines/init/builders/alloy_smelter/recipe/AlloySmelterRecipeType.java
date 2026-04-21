package com.synergy.machines.init.builders.alloy_smelter.recipe;


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
public class AlloySmelterRecipeType extends BaseMachineRecipeType<BiItemInput> {

    public AlloySmelterRecipeType(int ticks, int energy, SizedIngredient  right,SizedIngredient  left,
            ItemStackTemplate output ) {
        this.input = right;
        this.optional_input = left;
        this.ticks = ticks;
        this.output = output;
        this.energy = energy;
  
    }

    public static AlloySmelterRecipeType of(int ticks, int energy, SizedIngredient  right,
            SizedIngredient  left, ItemStackTemplate output) {
        return new AlloySmelterRecipeType(ticks, energy, right, left, output);
    }
    
    @Override
    public MachineType<? extends BaseMachineBlock, ? extends BaseMachineBE, ? extends BaseMachineMenu, ? extends BaseMachineRecipeType<BiItemInput>> getMachine() {
        return zMachines.ALLOY_SMELTER;
    }

    @Override
    public ItemStack getRecipeInput(BiItemInput recipe) {
        return recipe.first();
    }

    @Override
    public ItemStack getRecipeInput2(BiItemInput recipe) {
        return recipe.second();
    }

    public static final RecipeSerializer<AlloySmelterRecipeType> serializer() {
        return new RecipeSerializer<>(CODEC, STREAM_CODEC);
    }


        public static final MapCodec<AlloySmelterRecipeType> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Codec.intRange(1, Integer.MAX_VALUE).fieldOf("ticks").forGetter(AlloySmelterRecipeType::getTime),
                Codec.intRange(1, Integer.MAX_VALUE).fieldOf("energy").forGetter(AlloySmelterRecipeType::getEnergy),
                SizedIngredient.NESTED_CODEC.fieldOf("right").forGetter(AlloySmelterRecipeType::getInputItem),
                SizedIngredient.NESTED_CODEC.fieldOf("left").forGetter(AlloySmelterRecipeType::getCatalystItem),
                ItemStackTemplate.CODEC.fieldOf("output").forGetter(AlloySmelterRecipeType::getOutputItem))
                .apply(inst, AlloySmelterRecipeType::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, AlloySmelterRecipeType> STREAM_CODEC = StreamCodec
                .composite(
                        ByteBufCodecs.INT, AlloySmelterRecipeType::getTime,
                        ByteBufCodecs.INT, AlloySmelterRecipeType::getEnergy,
                        SizedIngredient.STREAM_CODEC, AlloySmelterRecipeType::getInputItem,
                        SizedIngredient.STREAM_CODEC,AlloySmelterRecipeType::getCatalystItem,
                        ItemStackTemplate.STREAM_CODEC, AlloySmelterRecipeType::getOutputItem,
                        AlloySmelterRecipeType::new);

       

}
