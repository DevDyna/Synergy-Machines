package com.synergy.machines.init.builders.macerator.recipe;


import com.devdyna.cakesticklib.api.recipe.ChanceOutputItem;
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
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

@SuppressWarnings("null")
public class MaceratorRecipeType extends BaseMachineRecipeType<MonoItemInput> {

    public MaceratorRecipeType(int ticks, int energy, SizedIngredient input,
            ItemStackTemplate output, ChanceOutputItem secondary) {
        this.input = input;
        this.ticks = ticks;
        this.output = output;
        this.energy = energy;
        this.optional_output_item = secondary;
    }

    public static MaceratorRecipeType of(int ticks, int energy, SizedIngredient input,
            ItemStackTemplate output, ChanceOutputItem secondary) {
        return new MaceratorRecipeType(ticks, energy, input, output, secondary);
    }

    // @Override
    // public boolean hasSecondaryOutput() {
    //     return true;
    // }

    @Override
    public MachineType<? extends BaseMachineBlock, ? extends BaseMachineBE, ? extends BaseMachineMenu, ? extends BaseMachineRecipeType<MonoItemInput>> getMachine() {
        return zMachines.MACERATOR;
    }

    @Override
    public ItemStack getRecipeInput(MonoItemInput recipe) {
        return recipe.input();
    }

    public static final RecipeSerializer<MaceratorRecipeType> serializer() {
        return new RecipeSerializer<>(CODEC, STREAM_CODEC);
    }

        public static final MapCodec<MaceratorRecipeType> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Codec.intRange(1, Integer.MAX_VALUE).fieldOf("ticks").forGetter(MaceratorRecipeType::getTime),
                Codec.intRange(1, Integer.MAX_VALUE).fieldOf("energy").forGetter(MaceratorRecipeType::getEnergy),

                SizedIngredient.NESTED_CODEC.fieldOf("input").forGetter(MaceratorRecipeType::getInputItem),
                ItemStackTemplate.CODEC.fieldOf("output").forGetter(MaceratorRecipeType::getOutputItem),
                ChanceOutputItem.CODEC.optionalFieldOf("secondary_item")
                        .forGetter(r -> ChanceOutputItem.optional(r.getSecondaryOutputItem())))
                .apply(inst, (ticks, energy, input, output, secondary) -> new MaceratorRecipeType(
                        ticks,
                        energy,
                        input,
                        output,
                        secondary.orElse(null))));

        public static final StreamCodec<RegistryFriendlyByteBuf, MaceratorRecipeType> STREAM_CODEC = StreamCodec
                .composite(
                        ByteBufCodecs.INT, MaceratorRecipeType::getTime,
                        ByteBufCodecs.INT, MaceratorRecipeType::getEnergy,
                        SizedIngredient.STREAM_CODEC, MaceratorRecipeType::getInputItem,
                        ItemStackTemplate.STREAM_CODEC, MaceratorRecipeType::getOutputItem,
                        ByteBufCodecs.optional(ChanceOutputItem.STREAM_CODEC),
                        r -> ChanceOutputItem.optional(r.getSecondaryOutputItem()),
                        (ticks, energy, input, output, secondary) -> new MaceratorRecipeType(
                                ticks,
                                energy,
                                input,
                                output,
                                secondary.orElse(null)));

       

    

}
