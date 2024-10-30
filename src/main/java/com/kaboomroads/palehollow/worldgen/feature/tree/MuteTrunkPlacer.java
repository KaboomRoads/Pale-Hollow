package com.kaboomroads.palehollow.worldgen.feature.tree;

import com.google.common.collect.Lists;
import com.kaboomroads.palehollow.worldgen.feature.ModTrunkPlacerType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class MuteTrunkPlacer extends TrunkPlacer {
    public static final MapCodec<MuteTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            trunkPlacerParts(instance).t1(),
            trunkPlacerParts(instance).t2(),
            trunkPlacerParts(instance).t3(),
            Codec.intRange(1, 32).fieldOf("base_width").forGetter(trunkPlacer -> trunkPlacer.baseWidth),
            IntProvider.NON_NEGATIVE_CODEC.fieldOf("branch_seperation").forGetter(trunkPlacer -> trunkPlacer.branchSeperation),
            IntProvider.POSITIVE_CODEC.fieldOf("branch_length").forGetter(trunkPlacer -> trunkPlacer.branchLength),
            IntProvider.NON_NEGATIVE_CODEC.fieldOf("upward_branch_length").forGetter(trunkPlacer -> trunkPlacer.upwardBranchLength),
            Codec.intRange(0, 32).fieldOf("branch_height_min").forGetter(trunkPlacer -> trunkPlacer.minBranchHeight),
            Codec.intRange(0, 32).fieldOf("branch_height_max").forGetter(trunkPlacer -> trunkPlacer.maxBranchHeight)
    ).apply(instance, MuteTrunkPlacer::new));
    private final IntProvider branchLength;
    private final IntProvider upwardBranchLength;
    protected final IntProvider branchSeperation;
    protected final int baseWidth;
    protected final int minBranchHeight;
    protected final int maxBranchHeight;

    public MuteTrunkPlacer(int baseHeight, int heightRandA, int heightRandB, int baseWidth, IntProvider branchSeperation, IntProvider branchLength, IntProvider upwardBranchLength, int minBranchHeight, int maxBranchHeight) {
        super(baseHeight, heightRandA, heightRandB);
        this.baseWidth = baseWidth;
        this.branchSeperation = branchSeperation;
        this.branchLength = branchLength;
        this.upwardBranchLength = upwardBranchLength;
        this.minBranchHeight = minBranchHeight;
        this.maxBranchHeight = maxBranchHeight;
    }

    @NotNull
    @Override
    protected TrunkPlacerType<?> type() {
        return ModTrunkPlacerType.MUTE_TRUNK_PLACER;
    }

    @NotNull
    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(@NotNull LevelSimulatedReader reader, @NotNull BiConsumer<BlockPos, BlockState> biConsumer, @NotNull RandomSource random, int height, @NotNull BlockPos pos, @NotNull TreeConfiguration config) {
        List<FoliagePlacer.FoliageAttachment> foliage = Lists.newArrayList();
        BlockPos below = pos.below();
        int offset = Mth.floor(baseWidth / -2.0F) + 1;
        BlockPos dirtStartPos = below.offset(offset, 0, offset);
        BlockPos dirtEndPos = dirtStartPos.offset(baseWidth - 1, 0, baseWidth - 1);
        for (BlockPos blockPos : BlockPos.betweenClosed(dirtStartPos, dirtEndPos))
            setDirtAt(reader, biConsumer, random, blockPos, config);
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        int lastBranchPos = Integer.MIN_VALUE;
        for (int current = 0; current < height; ++current) {
            int j = pos.getY() + current;
            mutablePos.set(pos.getX(), j, pos.getZ());
            BlockPos startPos = mutablePos.immutable().offset(offset, 0, offset);
            BlockPos endPos = startPos.offset(baseWidth - 1, 0, baseWidth - 1);
            ArrayList<BlockPos> possiblePositions = new ArrayList<>(baseWidth * baseWidth - (baseWidth - 2) * (baseWidth - 2));
            for (BlockPos blockPos : BlockPos.betweenClosed(startPos, endPos)) {
                placeLog(reader, biConsumer, random, blockPos, config);
                if (blockPos.getX() == startPos.getX() || blockPos.getX() == endPos.getX() || blockPos.getZ() == startPos.getZ() || blockPos.getZ() == endPos.getZ())
                    possiblePositions.add(blockPos.immutable());
            }
            BlockPos.MutableBlockPos branchPos = possiblePositions.get(random.nextInt(possiblePositions.size())).mutable();
            int sample = branchSeperation.sample(random);
            if (current >= lastBranchPos + sample && current < height - maxBranchHeight && current >= minBranchHeight) {
                lastBranchPos = current;
                Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
                int upwardLength = upwardBranchLength.sample(random);
                int length = branchLength.sample(random);
                placeBranch(reader, biConsumer, random, config, foliage, branchPos, direction, length, upwardLength);
            }
            if (current == height - 1)
                foliage.add(new FoliagePlacer.FoliageAttachment(mutablePos.set(pos.getX(), j + 1, pos.getZ()), 0, false));
        }
        return foliage;
    }

    private void placeBranch(LevelSimulatedReader reader, BiConsumer<BlockPos, BlockState> biConsumer, RandomSource random, TreeConfiguration config, List<FoliagePlacer.FoliageAttachment> foliage, BlockPos.MutableBlockPos mutablePos, Direction direction, int length, int upwardLength) {
        BlockPos.MutableBlockPos current = mutablePos.mutable();
        for (int i = 0; i < length; i++) {
            current.move(direction);
            placeLog(reader, biConsumer, random, current, config, state -> state.setValue(RotatedPillarBlock.AXIS, direction.getAxis()));
        }
        for (int i = 0; i < upwardLength; i++) {
            if (i >= upwardLength - 1)
                foliage.add(new FoliagePlacer.FoliageAttachment(current.offset(0, 0, 0).immutable(), 0, false));
            current.move(Direction.UP);
            placeLog(reader, biConsumer, random, current, config);
        }
    }
}

