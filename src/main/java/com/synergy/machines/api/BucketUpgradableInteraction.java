package com.synergy.machines.api;

import com.devdyna.cakesticklib.api.aspect.logic.BucketInteraction;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public interface BucketUpgradableInteraction extends BucketInteraction.Simple {

    abstract boolean addUpgrade(ItemStack item, InteractionHand hand, BlockPos pos, BlockState state, Level level,
            Player player);

    @Override
    default InteractionResult executeWhenNotBucket(ItemStack stack, BlockState state, Level level, BlockPos pos,
            Player player, InteractionHand hand, BlockHitResult hitResult) {

        if (addUpgrade(stack, hand, pos, state, level, player))
            return InteractionResult.SUCCESS;

        return Simple.super.executeWhenNotBucket(stack, state, level, pos, player, hand, hitResult);
    }
}
