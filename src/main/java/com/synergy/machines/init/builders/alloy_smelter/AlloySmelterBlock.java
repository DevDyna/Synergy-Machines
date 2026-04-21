package com.synergy.machines.init.builders.alloy_smelter;

import java.util.function.Function;

import javax.annotation.Nullable;

import com.synergy.machines.api.machine.BaseMachineBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings("null")
public class AlloySmelterBlock extends BaseMachineBlock {

    public AlloySmelterBlock(Properties p) {
        super(p);
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos arg0, BlockState arg1) {
        return new AlloySmelterBE(arg0, arg1);
    }

    @Override
    protected Function<Properties, Block> getFactory() {
        return AlloySmelterBlock::new;
    }

}
