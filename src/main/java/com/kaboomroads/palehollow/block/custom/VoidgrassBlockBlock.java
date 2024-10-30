package com.kaboomroads.palehollow.block.custom;

import com.kaboomroads.palehollow.block.ModBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class VoidgrassBlockBlock extends SpreadingSnowyDirtBlock {
    public static final MapCodec<VoidgrassBlockBlock> CODEC = simpleCodec(VoidgrassBlockBlock::new);

    @NotNull
    @Override
    public MapCodec<VoidgrassBlockBlock> codec() {
        return CODEC;
    }

    public VoidgrassBlockBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        super.animateTick(blockState, level, blockPos, randomSource);
        if (randomSource.nextInt(10) == 0) {
            level.addParticle(
                    ParticleTypes.MYCELIUM,
                    (double) blockPos.getX() + randomSource.nextDouble(),
                    (double) blockPos.getY() + 1.1,
                    (double) blockPos.getZ() + randomSource.nextDouble(),
                    0.0,
                    0.0,
                    0.0
            );
        }
    }

    @Override
    protected void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (!canBeGrass(blockState, serverLevel, blockPos)) {
            serverLevel.setBlockAndUpdate(blockPos, ModBlocks.PALE_DIRT.defaultBlockState());
        } else {
            if (serverLevel.getMaxLocalRawBrightness(blockPos.above()) <= 9) {
                BlockState blockState2 = this.defaultBlockState();
                for (int i = 0; i < 4; i++) {
                    BlockPos offsetPos = blockPos.offset(randomSource.nextInt(3) - 1, randomSource.nextInt(5) - 3, randomSource.nextInt(3) - 1);
                    BlockState offsetState = serverLevel.getBlockState(offsetPos);
                    if ((offsetState.is(Blocks.DIRT) || offsetState.is(ModBlocks.PALE_DIRT)) && canPropagate(blockState2, serverLevel, offsetPos)) {
                        serverLevel.setBlockAndUpdate(offsetPos, blockState2.setValue(SNOWY, serverLevel.getBlockState(offsetPos.above()).is(Blocks.SNOW)));
                    }
                }
            }
        }
    }
}