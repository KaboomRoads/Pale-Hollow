package com.kaboomroads.palehollow.worldgen.feature;

import com.kaboomroads.palehollow.PaleHollow;
import com.kaboomroads.palehollow.block.ModBlocks;
import com.kaboomroads.palehollow.block.custom.PalethornBlock;
import com.kaboomroads.palehollow.worldgen.feature.tree.MuteTrunkPlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class ModTreeFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> MUTE = createKey("mute");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> bootstrapContext) {
        FeatureUtils.register(
                bootstrapContext,
                MUTE,
                Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(ModBlocks.MUTE_LOG),
                        new MuteTrunkPlacer(2, 0, 1, 1, ConstantInt.of(2), ConstantInt.of(2), ConstantInt.of(1), 1, 0),
                        BlockStateProvider.simple(ModBlocks.PALETHORN.defaultBlockState().setValue(PalethornBlock.AGE, 3)),
                        new RandomSpreadFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(2), 64),
                        new TwoLayersFeatureSize(0, 0, 0)
                )
                        .ignoreVines()
                        .dirt(BlockStateProvider.simple(ModBlocks.PALE_DIRT))
                        .build()
        );
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> createKey(String string) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PaleHollow.MOD_ID, string));
    }
}
