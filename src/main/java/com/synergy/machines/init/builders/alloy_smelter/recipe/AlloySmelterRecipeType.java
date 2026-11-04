package com.synergy.machines.init.builders.alloy_smelter.recipe;

import java.util.List;

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

public class AlloySmelterRecipeType extends BaseMachineRecipeType<BiItemInput> {

    private SizedIngredient right;
    private SizedIngredient left;

    private ItemStackTemplate output;

    public AlloySmelterRecipeType(int ticks, int energy, SizedIngredient right, SizedIngredient left,
            ItemStackTemplate output) {
        this.right = right;
        this.left = left;
        this.ticks = ticks;
        this.output = output;
        this.energy = energy;
    }

    public SizedIngredient getLeftItem() {
        return left;
    }

    public SizedIngredient getRightItem() {
        return right;
    }

    public ItemStackTemplate getOutputItem() {
        return output;
    }

    public static AlloySmelterRecipeType of(int ticks, int energy, SizedIngredient right,
            SizedIngredient left, ItemStackTemplate output) {
        return new AlloySmelterRecipeType(ticks, energy, right, left, output);
    }

    @Override
    public MachineType<? extends BaseMachineBlock, ? extends BaseMachineBE, ? extends BaseMachineMenu, ? extends BaseMachineRecipeType<BiItemInput>> getMachine() {
        return zMachines.ALLOY_SMELTER;
    }

    // TODO verify if is necessary
    // @Override
    // public boolean matches(BiItemInput r, Level l) {
    // return getLeftItem() != null && getRightItem() != null
    // ? (getLeftItem().test(r.first()) && getRightItem().test(r.second()))
    // || (getRightItem().test(r.first()) && getLeftItem().test(r.second()))
    // : false;
    // }

    @Override
    public List<SizedIngredient> getMachineItems() {
        return List.of(getLeftItem(), getRightItem());
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
        return List.of(output);
    }

    @Override
    public List<FluidStackTemplate> getRecipeFluidsOutput() {
        return List.of();
    }

    public static final RecipeSerializer<AlloySmelterRecipeType> serializer() {
        return new RecipeSerializer<>(CODEC, STREAM_CODEC);
    }

    public static final MapCodec<AlloySmelterRecipeType> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Codec.intRange(1, Integer.MAX_VALUE).fieldOf("ticks").forGetter(AlloySmelterRecipeType::getTime),
            Codec.intRange(1, Integer.MAX_VALUE).fieldOf("energy").forGetter(AlloySmelterRecipeType::getEnergy),
            SizedIngredient.NESTED_CODEC.fieldOf("right").forGetter(AlloySmelterRecipeType::getRightItem),
            SizedIngredient.NESTED_CODEC.fieldOf("left").forGetter(AlloySmelterRecipeType::getLeftItem),
            ItemStackTemplate.CODEC.fieldOf("output").forGetter(AlloySmelterRecipeType::getOutputItem))
            .apply(inst, AlloySmelterRecipeType::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, AlloySmelterRecipeType> STREAM_CODEC = StreamCodec
            .composite(
                    ByteBufCodecs.INT, AlloySmelterRecipeType::getTime,
                    ByteBufCodecs.INT, AlloySmelterRecipeType::getEnergy,
                    SizedIngredient.STREAM_CODEC, AlloySmelterRecipeType::getRightItem,
                    SizedIngredient.STREAM_CODEC, AlloySmelterRecipeType::getLeftItem,
                    ItemStackTemplate.STREAM_CODEC, AlloySmelterRecipeType::getOutputItem,
                    AlloySmelterRecipeType::new);

}
