package com.kaboomroads.palehollow.worldgen.feature;

import com.kaboomroads.palehollow.PaleHollow;
import com.kaboomroads.palehollow.worldgen.feature.tree.MuteTrunkPlacer;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class ModTrunkPlacerType {
    public static final TrunkPlacerType<MuteTrunkPlacer> MUTE_TRUNK_PLACER = register("mute_trunk_placer", MuteTrunkPlacer.CODEC);

    private static <P extends TrunkPlacer> TrunkPlacerType<P> register(String string, MapCodec<P> mapCodec) {
        return Registry.register(BuiltInRegistries.TRUNK_PLACER_TYPE, ResourceLocation.fromNamespaceAndPath(PaleHollow.MOD_ID, string), new TrunkPlacerType<>(mapCodec));
    }

    public static void init() {
    }
}
