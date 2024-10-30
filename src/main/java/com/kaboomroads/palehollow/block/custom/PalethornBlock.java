package com.kaboomroads.palehollow.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedList;

public class PalethornBlock extends PipeBlock implements SimpleWaterloggedBlock {
    public static final MapCodec<PalethornBlock> CODEC = simpleCodec(PalethornBlock::new);

    @NotNull
    @Override
    public MapCodec<PalethornBlock> codec() {
        return CODEC;
    }

    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    public static final BooleanProperty INACTIVE = BooleanProperty.create("inactive");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final LinkedList<BlockPos> CHECK_SAME = new LinkedList<>();

    static {
        for (Direction dir : Direction.values()) CHECK_SAME.add(BlockPos.ZERO.relative(dir));
    }

    public PalethornBlock(Properties properties) {
        super(0.25F, properties);
        registerDefaultState(stateDefinition.any().setValue(AGE, 0).setValue(WATERLOGGED, false).setValue(INACTIVE, false).setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(UP, false).setValue(DOWN, false));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return getStateForPlacement(context.getLevel(), context.getClickedPos());
    }

    public BlockState getStateForPlacement(BlockGetter blockGetter, BlockPos blockPos) {
        BlockState below = blockGetter.getBlockState(blockPos.below());
        BlockState above = blockGetter.getBlockState(blockPos.above());
        BlockState north = blockGetter.getBlockState(blockPos.north());
        BlockState east = blockGetter.getBlockState(blockPos.east());
        BlockState south = blockGetter.getBlockState(blockPos.south());
        BlockState west = blockGetter.getBlockState(blockPos.west());
        return defaultBlockState()
                .setValue(WATERLOGGED, blockGetter.getFluidState(blockPos).getType() == Fluids.WATER)
                .setValue(DOWN, connects(below, Direction.DOWN))
                .setValue(UP, connects(above, Direction.UP))
                .setValue(NORTH, connects(north, Direction.NORTH))
                .setValue(EAST, connects(east, Direction.EAST))
                .setValue(SOUTH, connects(south, Direction.SOUTH))
                .setValue(WEST, connects(west, Direction.WEST));
    }

    @NotNull
    @Override
    protected BlockState updateShape(BlockState blockState, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos blockPos, Direction direction, BlockPos blockPos2, BlockState blockState2, RandomSource randomSource) {
        if (blockState.getValue(WATERLOGGED))
            scheduledTickAccess.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
//        return blockState.setValue(PROPERTY_BY_DIRECTION.get(direction), connects(blockState2, direction));
        BlockState below = levelReader.getBlockState(blockPos.below());
        BlockState above = levelReader.getBlockState(blockPos.above());
        BlockState north = levelReader.getBlockState(blockPos.north());
        BlockState east = levelReader.getBlockState(blockPos.east());
        BlockState south = levelReader.getBlockState(blockPos.south());
        BlockState west = levelReader.getBlockState(blockPos.west());
        return blockState.setValue(DOWN, connects(below, Direction.DOWN))
                .setValue(UP, connects(above, Direction.UP))
                .setValue(NORTH, connects(north, Direction.NORTH))
                .setValue(EAST, connects(east, Direction.EAST))
                .setValue(SOUTH, connects(south, Direction.SOUTH))
                .setValue(WEST, connects(west, Direction.WEST));
    }

    public boolean connects(BlockState state, Direction direction) {
        return state.is(this) || (direction == Direction.DOWN && state.is(BlockTags.SCULK_REPLACEABLE_WORLD_GEN));
    }

    @NotNull
    @Override
    public FluidState getFluidState(BlockState blockState) {
        return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(AGE, WATERLOGGED, INACTIVE, NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    @Override
    protected boolean isPathfindable(BlockState blockState, PathComputationType pathComputationType) {
        return false;
    }

    @Override
    public void entityInside(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, Entity entity) {
        if (level instanceof ServerLevel serverLevel && entity instanceof LivingEntity)
            entity.hurtServer(serverLevel, serverLevel.damageSources().cactus(), 1.0F);
        entity.makeStuckInBlock(blockState, new Vec3(0.75F, 0.75F, 0.75F));
    }

    private boolean canReplace(BlockState blockState) {
        return (blockState.isAir() || blockState.is(Blocks.WATER) || blockState.canBeReplaced()) && !blockState.is(Blocks.LAVA);
    }

    protected boolean canPlace(@NotNull LevelAccessor level, @NotNull BlockPos blockPos) {
        if (!canReplace(level.getBlockState(blockPos))) return false;
        int i = 0;
        BlockPos.MutableBlockPos cursor = blockPos.mutable();
        for (BlockPos pos : CHECK_SAME) {
            BlockState relState = level.getBlockState(cursor.set(blockPos).move(pos));
            if (relState.is(this) && ++i >= 2) return false;
        }
        return true;
    }

    @Override
    protected void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, @Nullable Orientation orientation, boolean bl) {
        if (blockState.getValue(INACTIVE)) {
            for (Direction dir : Direction.values())
                if (canPlace(level, blockPos.relative(dir))) {
                    level.setBlock(blockPos, blockState.setValue(INACTIVE, false), 3);
                    break;
                }
        }
        super.neighborChanged(blockState, level, blockPos, block, orientation, bl);
    }

    @Override
    public boolean isRandomlyTicking(@NotNull BlockState blockState) {
        return true;
    }

    @Override
    public void randomTick(@NotNull BlockState blockState, @NotNull ServerLevel level, @NotNull BlockPos blockPos, @NotNull RandomSource random) {
        super.randomTick(blockState, level, blockPos, random);
        if (!blockState.getValue(INACTIVE)) {
            grow(blockState, level, blockPos, random);
        }
    }

    public void grow(@NotNull BlockState blockState, @NotNull ServerLevel level, @NotNull BlockPos blockPos, @NotNull RandomSource random) {
        int age = blockState.getValue(AGE);
        if (age < AGE.max) {
            if (random.nextFloat() < 0.25F) level.setBlock(blockPos, blockState.setValue(AGE, ++age), 3);
            return;
        }
        ArrayList<BlockPos> possibilities = new ArrayList<>(5);
        for (Direction dir : Direction.values()) {
            BlockPos rel = blockPos.relative(dir);
            if (canPlace(level, rel)) possibilities.add(rel);
        }
        if (possibilities.isEmpty()) {
            level.setBlock(blockPos, blockState.setValue(INACTIVE, true), 3);
            return;
        }
        BlockPos relative = possibilities.get(random.nextInt(possibilities.size()));
        BlockState relState = getStateForPlacement(level, relative);
        level.setBlock(relative, relState, 3);
        BlockState newState = getStateForPlacement(level, blockPos);
        if (possibilities.size() <= 1 && !blockState.getValue(INACTIVE)) newState.setValue(INACTIVE, true);
        level.setBlock(blockPos, newState, 3);
        level.neighborChanged(newState, relative, this, null, false);
    }
}