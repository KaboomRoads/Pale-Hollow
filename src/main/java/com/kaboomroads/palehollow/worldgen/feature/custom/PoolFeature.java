package com.kaboomroads.palehollow.worldgen.feature.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.BitSet;

public class PoolFeature extends Feature<PoolFeature.Configuration> {
    private static final BlockState AIR = Blocks.CAVE_AIR.defaultBlockState();

    public PoolFeature(Codec<Configuration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<Configuration> featurePlaceContext) {
        BlockPos blockPos = featurePlaceContext.origin();
        WorldGenLevel worldGenLevel = featurePlaceContext.level();
        RandomSource randomSource = featurePlaceContext.random();
        PoolFeature.Configuration configuration = featurePlaceContext.config();
        int offset = configuration.offset.sample(randomSource);
        BlockPos.MutableBlockPos cursor = blockPos.mutable();
        boolean b = false;
        if (worldGenLevel.getBlockState(cursor).isAir()) {
            for (int i = 0; i < 64; i++) {
                cursor.move(Direction.DOWN);
                if (worldGenLevel.getBlockState(cursor).is(BlockTags.BASE_STONE_OVERWORLD)) {
                    cursor.move(-8, offset, -8);
                    blockPos = cursor.immutable();
                    b = true;
                    break;
                }
            }
        } else {
            for (int i = 0; i < 64; i++) {
                cursor.move(Direction.UP);
                if (worldGenLevel.getBlockState(cursor).isAir()) {
                    cursor.move(Direction.DOWN);
                    cursor.move(-8, offset, -8);
                    blockPos = cursor.immutable();
                    b = true;
                    break;
                }
            }
        }
        if (b) {
            if (blockPos.getY() <= worldGenLevel.getMinY() || blockPos.getY() >= worldGenLevel.getMaxY()) return false;
            BitSet bls = new BitSet(2048);
            int i = randomSource.nextInt(4) + 4;
            for (int j = 0; j < i; j++) {
                double d = randomSource.nextDouble() * 6.0 + 3.0;
                double e = randomSource.nextDouble() * 4.0 + 2.0;
                double f = randomSource.nextDouble() * 6.0 + 3.0;
                double g = randomSource.nextDouble() * (16.0 - d - 2.0) + 1.0 + d / 2.0;
                double h = randomSource.nextDouble() * (8.0 - e - 4.0) + 2.0 + e / 2.0;
                double k = randomSource.nextDouble() * (16.0 - f - 2.0) + 1.0 + f / 2.0;

                for (int l = 1; l < 15; l++)
                    for (int m = 1; m < 15; m++)
                        for (int n = 1; n < 7; n++) {
                            double o = ((double) l - g) / (d / 2.0);
                            double p = ((double) n - h) / (e / 2.0);
                            double q = ((double) m - k) / (f / 2.0);
                            double r = o * o + p * p + q * q;
                            if (r < 1.0) {
                                bls.set((l * 16 + m) * 8 + n);
                            }
                        }
            }
            BlockState blockState = configuration.fluid().getState(randomSource, blockPos);
            for (int s = 0; s < 16; s++)
                for (int t = 0; t < 16; t++)
                    for (int u = 0; u < 8; u++) {
                        boolean bl = edge(bls, s, t, u);
                        if (bl) {
                            BlockState blockState2 = worldGenLevel.getBlockState(blockPos.offset(s, u, t));
                            if (u >= 4 && !blockState2.getFluidState().isEmpty()) return false;
                            if (u < 4 && !blockState2.isSolid() && worldGenLevel.getBlockState(blockPos.offset(s, u, t)) != blockState)
                                return false;
                        }
                    }
            for (int s = 0; s < 16; s++)
                for (int t = 0; t < 16; t++)
                    for (int ux = 0; ux < 8; ux++)
                        if (bls.get((s * 16 + t) * 8 + ux)) {
                            BlockPos blockPos2 = blockPos.offset(s, ux, t);
                            if (this.canReplaceBlock(worldGenLevel.getBlockState(blockPos2))) {
                                boolean bl2 = ux >= 4;
                                worldGenLevel.setBlock(blockPos2, bl2 ? AIR : blockState, 2);
                                if (bl2) {
                                    worldGenLevel.scheduleTick(blockPos2, AIR.getBlock(), 0);
                                    this.markAboveForPostProcessing(worldGenLevel, blockPos2);
                                }
                            }
                        }
            for (int t = 0; t < 16; t++)
                for (int uxx = 0; uxx < 16; uxx++)
                    for (int v = 0; v < 8; v++) {
                        boolean bl2 = edge(bls, t, uxx, v);
                        if (bl2 && (v < 4 || randomSource.nextInt(2) != 0)) {
                            BlockState blockState4 = worldGenLevel.getBlockState(blockPos.offset(t, v, uxx));
                            if (blockState4.isSolid() && !blockState4.is(BlockTags.LAVA_POOL_STONE_CANNOT_REPLACE)) {
                                BlockPos blockPos3 = blockPos.offset(t, v, uxx);
                                worldGenLevel.setBlock(blockPos3, configuration.barrier().getState(randomSource, blockPos), 2);
                                BlockPos above = blockPos3.above();
                                BlockState state = worldGenLevel.getBlockState(above);
                                if ((state.isAir()) && randomSource.nextFloat() < configuration.vegetationChance)
                                    configuration.vegetationFeature.value().place(worldGenLevel, featurePlaceContext.chunkGenerator(), randomSource, above);
                                this.markAboveForPostProcessing(worldGenLevel, blockPos3);
                            }
                        }
                    }
            if (blockState.getFluidState().is(FluidTags.WATER))
                for (int t = 0; t < 16; t++)
                    for (int uxx = 0; uxx < 16; uxx++) {
                        BlockPos blockPos4 = blockPos.offset(t, 4, uxx);
                        if (worldGenLevel.getBiome(blockPos4).value().shouldFreeze(worldGenLevel, blockPos4, false) && canReplaceBlock(worldGenLevel.getBlockState(blockPos4)))
                            worldGenLevel.setBlock(blockPos4, Blocks.ICE.defaultBlockState(), 2);
                    }
            return true;
        }
        return false;
    }

    private boolean edge(BitSet bls, int s, int t, int u) {
        return !bls.get((s * 16 + t) * 8 + u)
                && (
                s < 15 && bls.get(((s + 1) * 16 + t) * 8 + u)
                        || s > 0 && bls.get(((s - 1) * 16 + t) * 8 + u)
                        || t < 15 && bls.get((s * 16 + t + 1) * 8 + u)
                        || t > 0 && bls.get((s * 16 + (t - 1)) * 8 + u)
                        || u < 7 && bls.get((s * 16 + t) * 8 + u + 1)
                        || u > 0 && bls.get((s * 16 + t) * 8 + (u - 1))
        );
    }

    private boolean canReplaceBlock(BlockState blockState) {
        return !blockState.is(BlockTags.FEATURES_CANNOT_REPLACE);
    }

    public record Configuration(BlockStateProvider fluid, BlockStateProvider barrier, IntProvider offset,
                                Holder<PlacedFeature> vegetationFeature,
                                float vegetationChance) implements FeatureConfiguration {
        public static final Codec<PoolFeature.Configuration> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                                BlockStateProvider.CODEC.fieldOf("fluid").forGetter(PoolFeature.Configuration::fluid),
                                BlockStateProvider.CODEC.fieldOf("barrier").forGetter(PoolFeature.Configuration::barrier),
                                IntProvider.CODEC.fieldOf("offset").forGetter(PoolFeature.Configuration::offset),
                                PlacedFeature.CODEC.fieldOf("vegetation_feature").forGetter(configuration -> configuration.vegetationFeature),
                                Codec.floatRange(0.0F, 1.0F).fieldOf("vegetation_chance").forGetter(configuration -> configuration.vegetationChance)
                        )
                        .apply(instance, PoolFeature.Configuration::new)
        );
    }
}
