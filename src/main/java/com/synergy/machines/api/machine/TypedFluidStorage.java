package com.synergy.machines.api.machine;

import com.devdyna.cakesticklib.api.aspect.logic.SimpleFluidStorage;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;

public interface TypedFluidStorage extends SimpleFluidStorage {

    public enum FluidTankType {
        INPUT(),
        OUTPUT();

        public class Codec {
            public static final StreamCodec<ByteBuf, FluidTankType> STREAM = ByteBufCodecs.idMapper(
                    id -> FluidTankType.values()[id],
                    FluidTankType::ordinal);
        }

    }

    /**
     * atm not really used
     */
    abstract FluidTankType getTankIOType();

    // TODO API : move to api

    /**
     * Mainly used on ContainerData client-server
     */
    static int getFluidToID(Fluid f) {
        return BuiltInRegistries.FLUID.getId(f);
    }

    /**
     * Mainly used on ContainerData client-server
     */
    static int getFluidToID(FluidStack f) {
        return getFluidToID(f.getFluid());
    }

    /**
     * Mainly used on ContainerData client-server
     */
    static Fluid getFluidFromID(int index) {
        return BuiltInRegistries.FLUID.byId(index);
    }

}
