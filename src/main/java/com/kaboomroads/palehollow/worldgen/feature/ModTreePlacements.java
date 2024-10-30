package com.kaboomroads.palehollow.worldgen.feature;

import com.kaboomroads.palehollow.PaleHollow;
import com.kaboomroads.palehollow.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModTreePlacements {
    public static final ResourceKey<PlacedFeature> MUTE_CHECKED = createKey("mute_checked");

    public static void bootstrap(BootstrapContext<PlacedFeature> bootstrapContext) {
        HolderGetter<ConfiguredFeature<?, ?>> holderGetter = bootstrapContext.lookup(Registries.CONFIGURED_FEATURE);
        Holder<ConfiguredFeature<?, ?>> holder5 = holderGetter.getOrThrow(ModTreeFeatures.MUTE);
        PlacementUtils.register(bootstrapContext, MUTE_CHECKED, holder5, PlacementUtils.filteredByBlockSurvival(ModBlocks.MUTE_SAPLING));
    }

    public static ResourceKey<PlacedFeature> createKey(String string) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(PaleHollow.MOD_ID, string));
    }
}
