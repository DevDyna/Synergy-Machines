package com.synergy.machines.init.builders.macerator.recipe;

import java.util.List;
import java.util.Optional;

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
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidStackTemplate;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

public class MaceratorRecipeType extends BaseMachineRecipeType<ItemInput.simple> {

    private SizedIngredient input;
    private ItemStackTemplate output;
    private ChanceOutput.Item secondary;
   

    public MaceratorRecipeType(int ticks, int energy, SizedIngredient input,
            ItemStackTemplate output, ChanceOutput.Item secondary) {
        this.input = input;
        this.ticks = ticks;
        this.output = output;
        this.energy = energy;
        this.secondary = secondary;
    }

    public static MaceratorRecipeType of(int ticks, int energy, SizedIngredient input,
            ItemStackTemplate output, ChanceOutput.Item secondary) {
        return new MaceratorRecipeType(ticks, energy, input, output, secondary);
    }

    public SizedIngredient getInput() {
        return input;
    }

    public ItemStackTemplate getOutput() {
        return output;
    }

    public ChanceOutput.Item getSecondaryOutput() {
        return secondary;
    }

    @Override
    public MachineType<? extends BaseMachineBlock, ? extends BaseMachineBE, ? extends BaseMachineMenu, ? extends BaseMachineRecipeType<ItemInput.simple>> getMachine() {
        return zMachines.MACERATOR;
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
        return List.of(getOutput(), getSecondaryOutput().item());
    }

    @Override
    public List<FluidStackTemplate> getRecipeFluidsOutput() {
        return List.of();
    }

    public static final RecipeSerializer<MaceratorRecipeType> serializer() {
        return new RecipeSerializer<>(CODEC, STREAM_CODEC);
    }

    public static final MapCodec<MaceratorRecipeType> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Codec.intRange(1, Integer.MAX_VALUE).fieldOf("ticks").forGetter(MaceratorRecipeType::getTime),
            Codec.intRange(1, Integer.MAX_VALUE).fieldOf("energy").forGetter(MaceratorRecipeType::getEnergy),

            SizedIngredient.NESTED_CODEC.fieldOf("input").forGetter(MaceratorRecipeType::getInput),
            ItemStackTemplate.CODEC.optionalFieldOf("output").forGetter(r -> Optional.ofNullable(r.getOutput())),
            ChanceOutput.Item.CODEC.optionalFieldOf("secondary_item")
                    .forGetter(r -> ChanceOutput.Item.optional(r.getSecondaryOutput())))
            .apply(inst, (t, e, i, o, s) -> new MaceratorRecipeType(t, e, i, o.orElse(null), s.orElse(null))));

    public static final StreamCodec<RegistryFriendlyByteBuf, MaceratorRecipeType> STREAM_CODEC = StreamCodec
            .composite(
                    ByteBufCodecs.INT, MaceratorRecipeType::getTime,
                    ByteBufCodecs.INT, MaceratorRecipeType::getEnergy,
                    SizedIngredient.STREAM_CODEC, MaceratorRecipeType::getInput,
                    ByteBufCodecs.optional(ItemStackTemplate.STREAM_CODEC),
                    r -> Optional.ofNullable(r.getOutput()),
                    ByteBufCodecs.optional(ChanceOutput.Item.STREAM_CODEC),
                    r -> ChanceOutput.Item.optional(r.getSecondaryOutput()),
                    (t, e, i, o, s) -> new MaceratorRecipeType(t, e, i, o.orElse(null), s.orElse(null)));

}
