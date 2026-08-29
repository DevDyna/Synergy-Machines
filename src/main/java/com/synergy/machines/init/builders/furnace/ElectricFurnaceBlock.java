package com.synergy.machines.init.builders.furnace;

import javax.annotation.Nullable;

import com.synergy.machines.api.machine.BaseMachineBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ElectricFurnaceBlock extends BaseMachineBlock {

    public ElectricFurnaceBlock(Properties p) {
        super(p);
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos arg0, BlockState arg1) {
        return new ElectricFurnaceBE(arg0, arg1);
    }

    

}
