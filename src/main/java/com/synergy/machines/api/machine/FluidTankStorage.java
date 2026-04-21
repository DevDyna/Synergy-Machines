package com.synergy.machines.api.machine;

import com.devdyna.cakesticklib.api.aspect.logic.SimpleFluidStorage;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public interface FluidTankStorage extends SimpleFluidStorage {

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


}
