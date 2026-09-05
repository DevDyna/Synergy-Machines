package com.synergy.machines.api.machine;

import javax.annotation.Nullable;

import com.devdyna.cakesticklib.CakeStickLib;
import com.devdyna.cakesticklib.api.upgrades.UpgradeInstallable;
import com.devdyna.cakesticklib.api.upgrades.modifiers.ModifierUtils;
import com.synergy.machines.init.builders.MachineFrame;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public abstract class BaseMachineBlock extends MachineFrame implements EntityBlock {

    public final static BooleanProperty ENABLED = BlockStateProperties.ENABLED;

    public BaseMachineBlock(Properties p) {
        super(p);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext c) {
        return defaultBlockState()
                .setValue(FACING, c.getNearestLookingDirection().getOpposite())
                .setValue(ENABLED, false);
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> b) {
        b.add(FACING, ENABLED);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(ENABLED)) {
            Direction dir = state.getValue(FACING);
            Axis axis = dir.getAxis();

            double x = pos.getX() + 0.5;
            double y = pos.getY() + random.nextDouble() * 6.0 / 16.0;
            double z = pos.getZ() + 0.5;

            double spread = random.nextDouble() * 0.6 - 0.3;
            double offX = axis == Direction.Axis.X ? dir.getStepX() * 0.52 : spread;
            double offZ = axis == Direction.Axis.Z ? dir.getStepZ() * 0.52 : spread;

            level.addParticle(ParticleTypes.SMOKE, x + offX, y, z + offZ, 0, 0, 0);

        }
    }

    public boolean addUpgrade(ItemStack item, InteractionHand hand, BlockPos pos, BlockState state, Level level,
            Player player) {
        if (!player.isCrouching())
            if (ModifierUtils.itemValid(item))
                if (level.getBlockEntity(pos) instanceof UpgradeInstallable machine)
                    if (machine.tryAddUpgrade(item)) {

                        if (!player.isCreative())
                            item.shrink(1);

                        player.swing(hand);

                        player.sendOverlayMessage(
                                Component.translatable(CakeStickLib.MODULE_ID + ".item_use.install"));

                        level.playSound(player, pos,
                                SoundEvents.SMITHING_TABLE_USE,
                                SoundSource.BLOCKS,
                                1.0F, 1.5F);

                        return true;
                    }
        return false;
    }

    @Override
    public InteractionResult useItemOn(ItemStack item, BlockState state, Level level, BlockPos pos,
            Player player, InteractionHand hand, BlockHitResult hitResult) {

        if (addUpgrade(item, hand, pos, state, level, player))
            return InteractionResult.SUCCESS;

        return InteractionResult.TRY_WITH_EMPTY_HAND;
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player,
            BlockHitResult hitResult) {

        if (level.getBlockEntity(pos) instanceof BaseMachineBE be) {
            var click = onClickAction(state, level, pos, player);
            if (click != null)
                return click;
            player.openMenu(new SimpleMenuProvider(be, be.getContainerName()), pos);
            return InteractionResult.SUCCESS;
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    /**
     * Event to allow to set animations or events when menu was opened
     * <br/>
     * <br/>
     * return true to cancel menu open
     */
    public InteractionResult onClickAction(BlockState state, Level level, BlockPos pos, Player player) {
        return null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level l, BlockState s,
            BlockEntityType<T> ty) {
        return (lvl, pos, b, t) -> {
            if (t instanceof BaseMachineBE be) {
                be.tickBoth();
                if (l.isClientSide())
                    be.tickClient();
                else
                    be.tickServer();
            }
        };
    }

}
