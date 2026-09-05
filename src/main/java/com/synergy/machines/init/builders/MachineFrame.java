package com.synergy.machines.init.builders;

import com.mojang.serialization.MapCodec;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.MapColor;

public class MachineFrame extends DirectionalBlock {

    public MachineFrame(Properties p) {
        super(p.strength(1.0f)
                .destroyTime(1.0f)
                .sound(SoundType.METAL)
                .mapColor(MapColor.METAL));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext c) {
        return defaultBlockState().setValue(FACING, c.getClickedFace());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> b) {
        b.add(FACING);
    }

    @Override
    protected MapCodec<? extends DirectionalBlock> codec() {
        return simpleCodec((p) -> this);
    }

}