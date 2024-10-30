package com.kaboomroads.palehollow.worldgen.feature;

import com.kaboomroads.palehollow.PaleHollow;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModPlacements {
    public static final ResourceKey<PlacedFeature> PALE_HOLLOW_MOSS_PATCH = createKey("pale_hollow_moss_patch");
    public static final ResourceKey<PlacedFeature> PATCH_GRASS_PALE_HOLLOW = createKey("patch_grass_pale_hollow");
    public static final ResourceKey<PlacedFeature> PALE_HOLLOW_VOIDGRASS_PATCH = createKey("pale_hollow_voidgrass_patch");
    public static final ResourceKey<PlacedFeature> TREES_PALE_HOLLOW = createKey("trees_pale_hollow");
    public static final ResourceKey<PlacedFeature> TAR_POOL = createKey("tar_pool");

    public static void bootstrap(BootstrapContext<PlacedFeature> bootstrapContext) {
        HolderGetter<ConfiguredFeature<?, ?>> holderGetter = bootstrapContext.lookup(Registries.CONFIGURED_FEATURE);
        PlacementUtils.register(
                bootstrapContext,
                PALE_HOLLOW_MOSS_PATCH,
                holderGetter.getOrThrow(ModConfigurations.PALE_HOLLOW_MOSS_PATCH),
                CountPlacement.of(16),
                InSquarePlacement.spread(),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                BiomeFilter.biome()
        );
        PlacementUtils.register(
                bootstrapContext,
                PATCH_GRASS_PALE_HOLLOW,
                holderGetter.getOrThrow(ModConfigurations.PATCH_GRASS_PALE_HOLLOW),
                CountPlacement.of(4),
                InSquarePlacement.spread(),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                BiomeFilter.biome()
        );
        PlacementUtils.register(
                bootstrapContext,
                PALE_HOLLOW_VOIDGRASS_PATCH,
                holderGetter.getOrThrow(ModConfigurations.PALE_HOLLOW_VOIDGRASS_PATCH),
                CountPlacement.of(32),
                InSquarePlacement.spread(),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                BiomeFilter.biome()
        );
        PlacementUtils.register(
                bootstrapContext,
                TREES_PALE_HOLLOW,
                holderGetter.getOrThrow(ModConfigurations.TREES_PALE_HOLLOW),
                CountPlacement.of(UniformInt.of(192, 256)),
                InSquarePlacement.spread(),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                BiomeFilter.biome()
        );
        PlacementUtils.register(
                bootstrapContext,
                TAR_POOL,
                holderGetter.getOrThrow(ModConfigurations.TAR_POOL),
                CountPlacement.of(4),
                InSquarePlacement.spread(),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                BiomeFilter.biome()
        );
    }

    public static ResourceKey<PlacedFeature> createKey(String string) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(PaleHollow.MOD_ID, string));
    }
}
