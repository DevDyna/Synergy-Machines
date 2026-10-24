package com.synergy.machines.api.solar_panel;

import java.util.*;

import javax.annotation.Nullable;

import com.devdyna.cakesticklib.api.templates.TickingBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

//TODO API : maybe move to api as template?
public abstract class SolarPanelBlock extends TickingBlock {

    public static final BooleanProperty ENABLED = BlockStateProperties.ENABLED;
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;

    public SolarPanelBlock(Properties p) {
        super(p
                .strength(1.0f)
                .destroyTime(1.0f)
                .sound(SoundType.METAL)
                .mapColor(MapColor.METAL));
    }

    @Override
    protected VoxelShape getShape(BlockState s, BlockGetter l, BlockPos p, CollisionContext c) {
        return Block.box(0, 0, 0, 16, 4, 16);
    }

    public Map<Direction, BooleanProperty> getStateMapping() {
        return Map.of(
                Direction.NORTH, NORTH,
                Direction.EAST, EAST,
                Direction.SOUTH, SOUTH,
                Direction.WEST, WEST);
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> b) {
        b.add(ENABLED).add(getStateMapping().values().toArray(Property<?>[]::new));
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState()
                .setValue(ENABLED, false)
                .setValue(NORTH, false)
                .setValue(SOUTH, false)
                .setValue(EAST, false)
                .setValue(WEST, false);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (level.isClientSide())
            return;

        var cache = state;
        for (var face : getStateMapping().keySet()) {
            var offset = pos.relative(face);
            var offstate = level.getBlockState(offset);

            if (offstate.is(this)) {

                level.setBlockAndUpdate(offset,
                        offstate.setValue(getStateMapping().get(face.getOpposite()), true));

                cache = cache.setValue(getStateMapping().get(face), true);
            }

        }
        level.setBlockAndUpdate(pos, cache);
    }

    @Override
    public void destroy(LevelAccessor l, BlockPos p, BlockState s) {
        for (var face : getStateMapping().keySet()) {
            var offset = l.getBlockState(p.relative(face));
            if (offset.is(this))
                l.setBlock(p.relative(face),
                        offset.setValue(getStateMapping().get(face.getOpposite()), false), UPDATE_ALL);

        }
    }

    @Override
    public void onNeighborChange(BlockState state, LevelReader l, BlockPos pos, BlockPos neighbor) {

        var level = (Level) l;

        var cache = state;

        for (var face : getStateMapping().keySet()) {

            var offset = level.getBlockState(pos.relative(face));

            if (offset.is(this)) {

                level.setBlockAndUpdate(pos.relative(face),
                        offset.setValue(getStateMapping().get(face.getOpposite()),
                                true));

                state = state.setValue(getStateMapping().get(face), true);

            } else
                state = state.setValue(getStateMapping().get(face), false);

        }

        if (state != cache)
            level.setBlockAndUpdate(pos, state);

        super.onNeighborChange(state, level, pos, neighbor);
    }

}