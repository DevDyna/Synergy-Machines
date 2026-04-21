package com.synergy.machines.init.builders.rock_crusher;

import java.util.function.Function;

import javax.annotation.Nullable;

import com.devdyna.cakesticklib.api.aspect.logic.BucketInteraction;
import com.devdyna.cakesticklib.api.aspect.templates.menu.BlockMenu;
import com.synergy.machines.api.machine.BaseMachineBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

@SuppressWarnings("null")
public class RockCrusherBlock extends BaseMachineBlock implements BucketInteraction {

    public RockCrusherBlock(Properties p) {
        super(p);
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos arg0, BlockState arg1) {
        return new RockCrusherBE(arg0, arg1);
    }

    @Override
    protected Function<Properties, Block> getFactory() {
        return RockCrusherBlock::new;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
            Player player, InteractionHand hand, BlockHitResult hitResult) {
        return bucketAction(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    public InteractionResult executeWhenEmpty(ItemStack stack, BlockState state, Level level, BlockPos pos,
            Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (this instanceof BlockMenu bmv)
            bmv.useWithoutItem(state, level, pos, player, hitResult);
        return InteractionResult.SUCCESS;
    }

}
