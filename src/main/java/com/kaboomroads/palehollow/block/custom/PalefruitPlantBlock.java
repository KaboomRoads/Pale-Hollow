package com.kaboomroads.palehollow.block.custom;

import com.kaboomroads.palehollow.item.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PalefruitPlantBlock extends DoublePlantBlock implements BonemealableBlock {
    public static final MapCodec<PalefruitPlantBlock> CODEC = simpleCodec(PalefruitPlantBlock::new);
    private static final VoxelShape FULL_UPPER_SHAPE = Block.box(3.0, 0.0, 3.0, 13.0, 16.0, 13.0);
    private static final VoxelShape FULL_LOWER_SHAPE = Block.box(3.0, 2.0, 3.0, 13.0, 16.0, 13.0);
    private static final VoxelShape[] LOWER_SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(3.0, 12.0, 3.0, 13.0, 16.0, 13.0),
            FULL_LOWER_SHAPE,
            FULL_LOWER_SHAPE
    };
    private static final VoxelShape[] UPPER_SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(3.0, 6.0, 3.0, 13.0, 16.0, 13.0),
            FULL_UPPER_SHAPE,
            FULL_UPPER_SHAPE,
            FULL_UPPER_SHAPE
    };
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;

    @NotNull
    @Override
    public MapCodec<PalefruitPlantBlock> codec() {
        return CODEC;
    }

    public PalefruitPlantBlock(BlockBehaviour.Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(HALF, DoubleBlockHalf.UPPER).setValue(AGE, 0));
    }

    @NotNull
    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return blockState.getValue(HALF) == DoubleBlockHalf.UPPER
                ? UPPER_SHAPE_BY_AGE[blockState.getValue(AGE)]
                : LOWER_SHAPE_BY_AGE[Math.abs(blockState.getValue(AGE) - 1)];
    }

    public static void placeAt(LevelAccessor levelAccessor, BlockState blockState, BlockPos blockPos, int i) {
        BlockPos below = blockPos.below();
        levelAccessor.setBlock(blockPos, copyWaterloggedFrom(levelAccessor, blockPos, blockState.setValue(HALF, DoubleBlockHalf.UPPER)), i);
        levelAccessor.setBlock(below, copyWaterloggedFrom(levelAccessor, below, blockState.setValue(HALF, DoubleBlockHalf.LOWER)), i);
    }

    @Override
    public boolean canBeReplaced(BlockState blockState, BlockPlaceContext blockPlaceContext) {
        return false;
    }

    @Override
    public boolean isRandomlyTicking(BlockState blockState) {
        return blockState.getValue(HALF) == DoubleBlockHalf.UPPER && !isMaxAge(blockState);
    }

    public boolean isMaxAge(BlockState blockState) {
        return blockState.getValue(AGE) >= AGE.max;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return defaultBlockState();
    }

    @NotNull
    @Override
    public BlockState updateShape(BlockState blockState, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos blockPos, Direction direction, BlockPos blockPos2, BlockState blockState2, RandomSource randomSource) {
        if (isDouble(blockState.getValue(AGE)))
            return super.updateShape(blockState, levelReader, scheduledTickAccess, blockPos, direction, blockPos2, blockState2, randomSource);
        else return blockState.canSurvive(levelReader, blockPos) ? blockState : Blocks.AIR.defaultBlockState();
    }

    @Override
    protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return super.mayPlaceOn(blockState, blockGetter, blockPos) || blockState.is(BlockTags.SCULK_REPLACEABLE);
    }

    @Override
    protected boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        if (blockState.getValue(HALF) == DoubleBlockHalf.UPPER) {
            BlockPos blockPos2 = blockPos.above();
            return mayPlaceOn(levelReader.getBlockState(blockPos2), levelReader, blockPos2);
        } else {
            BlockState blockState2 = levelReader.getBlockState(blockPos.above());
            return blockState2.is(this) && blockState2.getValue(HALF) == DoubleBlockHalf.UPPER;
        }
    }

    @Override
    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack itemStack) {
    }

    public static boolean isDouble(int i) {
        return i >= 1;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(AGE);
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        float f = CropBlock.getGrowthSpeed(this, serverLevel, blockPos);
        boolean bl = randomSource.nextInt((int) (25.0F / f) + 1) == 0;
        if (bl) grow(serverLevel, blockState, blockPos, 1);
    }

    private void grow(ServerLevel serverLevel, BlockState blockState, BlockPos blockPos, int i) {
        int j = Math.min(blockState.getValue(AGE) + i, 3);
        if (canGrow(serverLevel, blockPos, blockState, j)) {
            BlockState blockState2 = blockState.setValue(AGE, j);
            serverLevel.setBlock(blockPos, blockState2, 2);
            if (isDouble(j))
                serverLevel.setBlock(blockPos.below(), blockState2.setValue(HALF, DoubleBlockHalf.LOWER), 3);
        }
    }

    private boolean canGrowInto(LevelReader levelReader, BlockPos blockPos) {
        BlockState blockState = levelReader.getBlockState(blockPos);
        return blockState.isAir() || blockState.is(this);
    }

    private boolean canGrow(LevelReader levelReader, BlockPos blockPos, BlockState blockState, int i) {
        return !isMaxAge(blockState) && (!isDouble(i) || canGrowInto(levelReader, blockPos.below()));
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        PosAndState posAndState = getUpperHalf(levelReader, blockPos, blockState);
        return posAndState != null && canGrow(levelReader, posAndState.pos, posAndState.state, posAndState.state.getValue(AGE) + 1);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        PosAndState posAndState = getUpperHalf(serverLevel, blockPos, blockState);
        if (posAndState != null) grow(serverLevel, posAndState.state, posAndState.pos, 1);
    }

    @Nullable
    private PosAndState getUpperHalf(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        if (blockState.getValue(HALF) == DoubleBlockHalf.UPPER) return new PosAndState(blockPos, blockState);
        else {
            BlockPos blockPos2 = blockPos.above();
            BlockState blockState2 = levelReader.getBlockState(blockPos2);
            return blockState2.getValueOrElse(HALF, DoubleBlockHalf.LOWER) == DoubleBlockHalf.UPPER ? new PosAndState(blockPos2, blockState2) : null;
        }
    }

    @Nullable
    private PosAndState getLowerHalf(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        if (blockState.getValue(HALF) == DoubleBlockHalf.LOWER) return new PosAndState(blockPos, blockState);
        else {
            BlockPos blockPos2 = blockPos.below();
            BlockState blockState2 = levelReader.getBlockState(blockPos2);
            return blockState2.getValueOrElse(HALF, DoubleBlockHalf.UPPER) == DoubleBlockHalf.LOWER ? new PosAndState(blockPos2, blockState2) : null;
        }
    }

    record PosAndState(BlockPos pos, BlockState state) {
    }

    @NotNull
    @Override
    protected InteractionResult useItemOn(
            ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult
    ) {
        boolean fruit = blockState.getValue(AGE) >= 3;
        return !fruit && itemStack.is(Items.BONE_MEAL)
                ? InteractionResult.PASS
                : super.useItemOn(itemStack, blockState, level, blockPos, player, interactionHand, blockHitResult);
    }

    @NotNull
    @Override
    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        PosAndState lower = getLowerHalf(level, blockPos, blockState);
        if (lower != null) {
            BlockState lowerState = lower.state;
            BlockPos lowerPos = lower.pos;
            int age = lowerState.getValue(AGE);
            boolean fruit = age >= 3;
            if (fruit) {
                int j = 1 + level.random.nextInt(2);
                popResource(level, lowerPos, new ItemStack(ModItems.PALEFRUIT, j));
                level.playSound(null, lowerPos, SoundEvents.CAVE_VINES_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
                BlockState newLower = lowerState.setValue(AGE, 2);
                level.setBlock(lowerPos, newLower, 2);
                level.gameEvent(GameEvent.BLOCK_CHANGE, lowerPos, GameEvent.Context.of(player, newLower));
                BlockPos above = lowerPos.above();
                BlockState upperState = level.getBlockState(above);
                BlockState newUpper = upperState.setValue(AGE, 2);
                level.setBlock(above, newUpper, 2);
                level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(player, newUpper));
                return InteractionResult.SUCCESS;
            }
        }
        return super.useWithoutItem(blockState, level, blockPos, player, blockHitResult);
    }

    @NotNull
    @Override
    public BlockState playerWillDestroy(Level level, BlockPos blockPos, BlockState blockState, Player player) {
        if (!level.isClientSide) {
            if (player.isCreative()) preventDropFromTopPart(level, blockPos, blockState, player);
            else dropResources(blockState, level, blockPos, null, player, player.getMainHandItem());
        }
        return super_playerWillDestroy(level, blockPos, blockState, player);
    }

    public BlockState super_playerWillDestroy(Level level, BlockPos blockPos, BlockState blockState, Player player) {
        this.spawnDestroyParticles(level, player, blockPos, blockState);
        if (blockState.is(BlockTags.GUARDED_BY_PIGLINS) && level instanceof ServerLevel serverLevel)
            PiglinAi.angerNearbyPiglins(serverLevel, player, false);
        level.gameEvent(GameEvent.BLOCK_DESTROY, blockPos, GameEvent.Context.of(player, blockState));
        return blockState;
    }

    protected static void preventDropFromTopPart(Level level, BlockPos blockPos, BlockState blockState, Player player) {
        DoubleBlockHalf doubleBlockHalf = blockState.getValue(HALF);
        if (doubleBlockHalf == DoubleBlockHalf.LOWER) {
            BlockPos blockPos2 = blockPos.above();
            BlockState blockState2 = level.getBlockState(blockPos2);
            if (blockState2.is(blockState.getBlock()) && blockState2.getValue(HALF) == DoubleBlockHalf.UPPER) {
                BlockState blockState3 = blockState2.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
                level.setBlock(blockPos2, blockState3, 35);
                level.levelEvent(player, 2001, blockPos2, Block.getId(blockState2));
            }
        }
    }
}
