package com.synergy.machines.init.builders.caster.recipe;

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
import com.synergy.machines.api.recipeinputs.ItemFluidInput;
import com.synergy.machines.init.types.zMachines;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

@SuppressWarnings("null")
public class CasterRecipeType extends BaseMachineRecipeType<ItemFluidInput> {

    public CasterRecipeType(int ticks, int energy, SizedFluidIngredient fluid, SizedIngredient input,
            boolean consumeCatalyst, ItemStackTemplate output) {
        this.input = input;
        this.ticks = ticks;
        this.energy = energy;
        this.fluid_input = fluid;
        this.output = output;
        this.consumeCatalyst = consumeCatalyst;
    }

    public static CasterRecipeType of(int ticks, int energy, SizedFluidIngredient fluid, SizedIngredient input,
            boolean consumeCatalyst, ItemStackTemplate output) {
        return new CasterRecipeType(ticks, energy, fluid, input, consumeCatalyst, output);
    }

    @Override
    public boolean hasSecondaryOutput() {
        return false;
    }

    @Override
    public MachineType<? extends BaseMachineBlock, ? extends BaseMachineBE, ? extends BaseMachineMenu, ? extends BaseMachineRecipeType<ItemFluidInput>> getMachine() {
        return zMachines.CASTING_FACTORY;
    }

    @Override
    public ItemStack getRecipeInput(ItemFluidInput recipe) {
        return recipe.item();
    }

    @Override
    public SizedFluidIngredient getRecipeFluidInput(ItemFluidInput recipe) {
        return x.fluidSized(recipe.input());
    }

    public static final RecipeSerializer<CasterRecipeType> serializer() {
        return new RecipeSerializer<>(CODEC, STREAM_CODEC);
    }


        public static final MapCodec<CasterRecipeType> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Codec.intRange(1, Integer.MAX_VALUE).fieldOf("ticks").forGetter(CasterRecipeType::getTime),
                Codec.intRange(1, Integer.MAX_VALUE).fieldOf("energy").forGetter(CasterRecipeType::getEnergy),

                SizedFluidIngredient.CODEC.fieldOf("input_fluid").forGetter(CasterRecipeType::getFluidInput),

                SizedIngredient.NESTED_CODEC.optionalFieldOf("input_item",null)
                        .forGetter(r->(r.getInputItem() == null || x.getItems(r.getInputItem()).isEmpty()) ? null : r.getInputItem()),
                Codec.BOOL.fieldOf("consume_item").forGetter(CasterRecipeType::consumeCatalyst),
                ItemStackTemplate.CODEC.fieldOf("output").forGetter(CasterRecipeType::getOutputItem)

        )
                .apply(inst, CasterRecipeType::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, CasterRecipeType> STREAM_CODEC = StreamCodec
                .composite(

                        ByteBufCodecs.INT, CasterRecipeType::getTime,
                        ByteBufCodecs.INT, CasterRecipeType::getEnergy,
                        SizedFluidIngredient.STREAM_CODEC, CasterRecipeType::getFluidInput,

                        ByteBufCodecs.optional(SizedIngredient.STREAM_CODEC),
                        r -> (r.getInputItem() == null || x.getItems(r.getInputItem()).isEmpty())
                                ? Optional.empty()
                                : Optional.of(r.getInputItem()),

                        ByteBufCodecs.BOOL, CasterRecipeType::consumeCatalyst,

                        ItemStackTemplate.STREAM_CODEC, CasterRecipeType::getOutputItem,

                        (ticks, energy, f, i, o, c) -> new CasterRecipeType(
                                ticks,
                                energy,
                                f,
                                i.orElse(null),
                                o, c));

       

}
