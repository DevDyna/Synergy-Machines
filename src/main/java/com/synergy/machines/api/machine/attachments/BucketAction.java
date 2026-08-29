package com.synergy.machines.api.machine.attachments;

import com.devdyna.cakesticklib.api.aspect.logic.BucketInteraction;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public interface BucketAction extends BucketInteraction {

    abstract InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos,
            Player player, InteractionHand hand, BlockHitResult hitResult);

    abstract InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player,
            BlockHitResult hitResult);

    @Override
    default InteractionResult executeWhenNotBucket(ItemStack stack, BlockState state, Level level, BlockPos pos,
            Player player, InteractionHand hand, BlockHitResult hitResult) {
        return InteractionResult.TRY_WITH_EMPTY_HAND;
    }

    @Override
    default InteractionResult executeWhenEmpty(ItemStack stack, BlockState state, Level level, BlockPos pos,
            Player player, InteractionHand hand, BlockHitResult hitResult) {
        return useWithoutItem(state, level, pos, player, hitResult);
    }

}
