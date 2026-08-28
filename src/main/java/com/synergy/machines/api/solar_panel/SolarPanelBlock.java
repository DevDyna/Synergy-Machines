package com.synergy.machines.api.solar_panel;

import java.util.*;

import javax.annotation.Nullable;

import com.devdyna.cakesticklib.api.aspect.logic.Connectable;
import com.devdyna.cakesticklib.api.aspect.templates.TickingBlock;
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
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public abstract class SolarPanelBlock extends TickingBlock implements Connectable {

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

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> b) {
        b.add(ENABLED);
        PropByDir().values().forEach(b::add);
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
        updateOnPlace(state, level, pos, oldState, isMoving);
    }

    @Override
    public void destroy(LevelAccessor l, BlockPos p, BlockState s) {
        updateOnDestroy(l, p, s);
    }

    @Override
    public void onNeighborChange(BlockState state, LevelReader level, BlockPos pos, BlockPos neighbor) {
        updateOnNeighborChanged(state, (Level) level, pos, level.getBlockState(neighbor).getBlock(), neighbor, false);
        super.onNeighborChange(state, level, pos, neighbor);
    }

    @Override
    public Map<Direction, BooleanProperty> PropByDir() {
        return Map.of(
                Direction.NORTH, NORTH,
                Direction.EAST, EAST,
                Direction.SOUTH, SOUTH,
                Direction.WEST, WEST);
    }

    @Override
    public Boolean whenConnect(@Nullable Level level, BlockPos basePos, BlockPos neighborPos, BlockState baseState,
            BlockState neighborState) {
        return neighborState.is(this);
    }

}