package com.synergy.machines.init.builders.rock_crusher;

import javax.annotation.Nullable;

import com.synergy.machines.api.machine.BaseMachineBlock;
import com.synergy.machines.api.machine.attachments.BucketAction;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class RockCrusherBlock extends BaseMachineBlock implements BucketAction {

    public RockCrusherBlock(Properties p) {
        super(p);
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos arg0, BlockState arg1) {
        return new RockCrusherBE(arg0, arg1);
    }

    @Override
    public InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
            Player player, InteractionHand hand, BlockHitResult hitResult) {
        return bucketAction(stack, state, level, pos, player, hand, hitResult);
    }

}
