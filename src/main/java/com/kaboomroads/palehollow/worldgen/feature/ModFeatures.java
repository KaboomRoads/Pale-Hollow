package com.kaboomroads.palehollow.worldgen.feature;

import com.kaboomroads.palehollow.PaleHollow;
import com.kaboomroads.palehollow.worldgen.feature.custom.PoolFeature;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class ModFeatures {
    public static final Feature<PoolFeature.Configuration> POOL = register("pool", new PoolFeature(PoolFeature.Configuration.CODEC));

    private static <C extends FeatureConfiguration, F extends Feature<C>> F register(String name, F feature) {
        return Registry.register(BuiltInRegistries.FEATURE, ResourceLocation.fromNamespaceAndPath(PaleHollow.MOD_ID, name), feature);
    }

    public static void init() {
    }
}
