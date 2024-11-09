package com.kaboomroads.palehollow.worldgen.feature;

import com.kaboomroads.palehollow.PaleHollow;
import com.kaboomroads.palehollow.block.ModBlocks;
import com.kaboomroads.palehollow.block.custom.PalefruitPlantBlock;
import com.kaboomroads.palehollow.worldgen.feature.custom.PoolFeature;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HangingMossBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;

public class ModConfigurations {
    public static final ResourceKey<ConfiguredFeature<?, ?>> PALE_HOLLOW_MOSS_PATCH = createKey("pale_hollow_moss_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PALE_HOLLOW_MOSS_VEGETATION = createKey("pale_hollow_moss_vegetation");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_GRASS_PALE_HOLLOW = createKey("patch_grass_pale_hollow");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PALE_HOLLOW_VOIDGRASS_PATCH = createKey("pale_hollow_voidgrass_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_PALE_HOLLOW = createKey("trees_pale_hollow");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TAR_POOL = createKey("tar_pool");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TAR_POOL_VEGETATION = createKey("tar_pool_vegetation");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PALE_HANGING_MOSS_PATCH = createKey("pale_hanging_moss_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PALEFRUIT_PLANT_PATCH = createKey("palefruit_plant_patch");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> bootstrapContext) {
        HolderGetter<ConfiguredFeature<?, ?>> holderGetter = bootstrapContext.lookup(Registries.CONFIGURED_FEATURE);
        HolderGetter<PlacedFeature> holderGetter2 = bootstrapContext.lookup(Registries.PLACED_FEATURE);
        FeatureUtils.register(
                bootstrapContext,
                PATCH_GRASS_PALE_HOLLOW,
                Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(
                        16,
                        7,
                        3,
                        PlacementUtils.filtered(
                                Feature.SIMPLE_BLOCK,
                                new SimpleBlockConfiguration(
                                        new WeightedStateProvider(
                                                SimpleWeightedRandomList.<BlockState>builder()
                                                        .add(Blocks.TALL_GRASS.defaultBlockState(), 1)
                                                        .add(Blocks.SHORT_GRASS.defaultBlockState(), 3)
                                                        .add(ModBlocks.VOIDGRASS.defaultBlockState(), 2)
                                        )
                                ),
                                BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.not(BlockPredicate.matchesBlocks(Direction.DOWN.getUnitVec3i(), Blocks.PODZOL)))
                        )
                )
        );
        FeatureUtils.register(
                bootstrapContext,
                PALE_HOLLOW_MOSS_VEGETATION,
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(
                        new WeightedStateProvider(
                                SimpleWeightedRandomList.<BlockState>builder()
                                        .add(Blocks.PALE_MOSS_CARPET.defaultBlockState(), 3)
                                        .add(Blocks.SHORT_GRASS.defaultBlockState(), 3)
                                        .add(ModBlocks.VOIDGRASS.defaultBlockState(), 2)
                        )
                )
        );
        FeatureUtils.register(
                bootstrapContext,
                PALE_HOLLOW_MOSS_PATCH,
                Feature.VEGETATION_PATCH,
                new VegetationPatchConfiguration(
                        BlockTags.MOSS_REPLACEABLE,
                        BlockStateProvider.simple(Blocks.PALE_MOSS_BLOCK),
                        PlacementUtils.inlinePlaced(holderGetter.getOrThrow(PALE_HOLLOW_MOSS_VEGETATION)),
                        CaveSurface.FLOOR,
                        ConstantInt.of(1),
                        0.0F,
                        5,
                        0.6F,
                        UniformInt.of(1, 3),
                        0.75F
                )
        );
        FeatureUtils.register(
                bootstrapContext,
                PALE_HOLLOW_VOIDGRASS_PATCH,
                Feature.VEGETATION_PATCH,
                new VegetationPatchConfiguration(
                        BlockTags.MOSS_REPLACEABLE,
                        BlockStateProvider.simple(ModBlocks.VOIDGRASS_BLOCK),
                        PlacementUtils.inlinePlaced(holderGetter.getOrThrow(PATCH_GRASS_PALE_HOLLOW)),
                        CaveSurface.FLOOR,
                        ConstantInt.of(1),
                        0.0F,
                        5,
                        0.1F,
                        UniformInt.of(5, 8),
                        0.75F
                )
        );
        FeatureUtils.register(
                bootstrapContext, TREES_PALE_HOLLOW, Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(holderGetter2.getOrThrow(ModTreePlacements.MUTE_CHECKED)))
        );
        FeatureUtils.register(
                bootstrapContext,
                TAR_POOL_VEGETATION,
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(
                        new WeightedStateProvider(
                                SimpleWeightedRandomList.<BlockState>builder()
                                        .add(ModBlocks.TARFLOWER.defaultBlockState(), 2)
                                        .add(ModBlocks.VOIDGRASS.defaultBlockState(), 1)
                        )
                )
        );
        FeatureUtils.register(
                bootstrapContext,
                TAR_POOL,
                ModFeatures.POOL,
                new PoolFeature.Configuration(
                        BlockStateProvider.simple(Blocks.AIR.defaultBlockState()),
                        new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                                .add(Blocks.DEEPSLATE.defaultBlockState(), 5)
                                .add(ModBlocks.RAW_TAR.defaultBlockState(), 1)
                        ),
                        ConstantInt.of(-3),
                        PlacementUtils.inlinePlaced(holderGetter.getOrThrow(TAR_POOL_VEGETATION)),
                        0.1F
                )
        );
        FeatureUtils.register(
                bootstrapContext,
                PALE_HANGING_MOSS_PATCH,
                Feature.BLOCK_COLUMN,
                new BlockColumnConfiguration(
                        List.of(
                                BlockColumnConfiguration.layer(UniformInt.of(1, 7),
                                        BlockStateProvider.simple(Blocks.PALE_HANGING_MOSS.defaultBlockState().setValue(HangingMossBlock.TIP, false))
                                ),
                                BlockColumnConfiguration.layer(ConstantInt.of(1),
                                        BlockStateProvider.simple(Blocks.PALE_HANGING_MOSS.defaultBlockState())
                                )
                        ),
                        Direction.DOWN,
                        BlockPredicate.ONLY_IN_AIR_PREDICATE,
                        true
                )
        );
        FeatureUtils.register(
                bootstrapContext,
                PALEFRUIT_PLANT_PATCH,
                Feature.SIMPLE_RANDOM_SELECTOR,
                new SimpleRandomFeatureConfiguration(HolderSet.direct(makePalefruitPlant()))
        );
    }

    private static Holder<PlacedFeature> makePalefruitPlant() {
        return PlacementUtils.inlinePlaced(
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(
                        new WeightedStateProvider(
                                SimpleWeightedRandomList.<BlockState>builder()
                                        .add(ModBlocks.PALEFRUIT_PLANT.defaultBlockState().setValue(PalefruitPlantBlock.AGE, 3), 1)
                                        .add(ModBlocks.PALEFRUIT_PLANT.defaultBlockState().setValue(PalefruitPlantBlock.AGE, 2), 2)
                        )
                )
        );
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> createKey(String string) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PaleHollow.MOD_ID, string));
    }
}
