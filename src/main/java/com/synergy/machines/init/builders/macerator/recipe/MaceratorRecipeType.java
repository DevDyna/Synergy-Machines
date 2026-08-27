package com.synergy.machines.init.builders.macerator.recipe;


import com.devdyna.cakesticklib.api.recipe.recipeInput.ItemInput;
import com.devdyna.cakesticklib.api.recipe.recipeOutput.ChanceOutput;
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

public class MaceratorRecipeType extends BaseMachineRecipeType<ItemInput.simple> {

    public MaceratorRecipeType(int ticks, int energy, SizedIngredient input,
            ItemStackTemplate output, ChanceOutput.Item secondary) {
        this.input = input;
        this.ticks = ticks;
        this.output = output;
        this.energy = energy;
        this.optional_output_item = secondary;
    }

    public static MaceratorRecipeType of(int ticks, int energy, SizedIngredient input,
            ItemStackTemplate output, ChanceOutput.Item secondary) {
        return new MaceratorRecipeType(ticks, energy, input, output, secondary);
    }

    // @Override
    // public boolean hasSecondaryOutput() {
    //     return true;
    // }

    @Override
    public MachineType<? extends BaseMachineBlock, ? extends BaseMachineBE, ? extends BaseMachineMenu, ? extends BaseMachineRecipeType<ItemInput.simple>> getMachine() {
        return zMachines.MACERATOR;
    }

    @Override
    public ItemStack getRecipeInput(ItemInput.simple recipe) {
        return recipe.item();
    }

    public static final RecipeSerializer<MaceratorRecipeType> serializer() {
        return new RecipeSerializer<>(CODEC, STREAM_CODEC);
    }

        public static final MapCodec<MaceratorRecipeType> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Codec.intRange(1, Integer.MAX_VALUE).fieldOf("ticks").forGetter(MaceratorRecipeType::getTime),
                Codec.intRange(1, Integer.MAX_VALUE).fieldOf("energy").forGetter(MaceratorRecipeType::getEnergy),

                SizedIngredient.NESTED_CODEC.fieldOf("input").forGetter(MaceratorRecipeType::getInputItem),
                ItemStackTemplate.CODEC.fieldOf("output").forGetter(MaceratorRecipeType::getOutputItem),
                ChanceOutput.Item.CODEC.optionalFieldOf("secondary_item")
                        .forGetter(r -> ChanceOutput.Item.optional(r.getSecondaryOutputItem())))
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
                        ByteBufCodecs.optional(ChanceOutput.Item.STREAM_CODEC),
                        r -> ChanceOutput.Item.optional(r.getSecondaryOutputItem()),
                        (ticks, energy, input, output, secondary) -> new MaceratorRecipeType(
                                ticks,
                                energy,
                                input,
                                output,
                                secondary.orElse(null)));

       

    

}
