package com.kaboomroads.palehollow.worldgen;

import com.kaboomroads.palehollow.PaleHollow;
import com.kaboomroads.palehollow.worldgen.feature.ModPlacements;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModBiomes {
    public static final ResourceKey<Biome> PALE_HOLLOW = createKey("pale_hollow");

    public static ResourceKey<Biome> createKey(String string) {
        return ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PaleHollow.MOD_ID, string));
    }

    public static void register(BootstrapContext<Biome> bootstrapContext, String string, Biome biome) {
        bootstrapContext.register(createKey(string), biome);
    }

    public static void bootstrap(BootstrapContext<Biome> bootstrapContext) {
        HolderGetter<PlacedFeature> holderGetter = bootstrapContext.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> holderGetter2 = bootstrapContext.lookup(Registries.CONFIGURED_CARVER);
        bootstrapContext.register(PALE_HOLLOW, paleHollow(holderGetter, holderGetter2));
    }

    public static Biome paleHollow(HolderGetter<PlacedFeature> holderGetter, HolderGetter<ConfiguredWorldCarver<?>> holderGetter2) {
        MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();

        BiomeDefaultFeatures.commonSpawns(builder);
        BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(holderGetter, holderGetter2);
        builder2.addCarver(Carvers.CAVE);
        builder2.addCarver(Carvers.CAVE_EXTRA_UNDERGROUND);
        builder2.addCarver(Carvers.CANYON);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(builder2);
        BiomeDefaultFeatures.addSurfaceFreezing(builder2);
        builder2.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacements.TREES_PALE_HOLLOW);
        builder2.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacements.PATCH_GRASS_PALE_HOLLOW);
        builder2.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModPlacements.PALE_HOLLOW_VOIDGRASS_PATCH);
        builder2.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModPlacements.PALE_HOLLOW_MOSS_PATCH);
        builder2.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModPlacements.TAR_POOL);
        builder2.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacements.PALE_HANGING_MOSS_PATCH);
        builder2.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacements.PALEFRUIT_PLANT_PATCH);
        BiomeDefaultFeatures.addPlainGrass(builder2);
        BiomeDefaultFeatures.addPlainVegetation(builder2);
        BiomeDefaultFeatures.addDefaultMushrooms(builder2);
        BiomeDefaultFeatures.addDefaultOres(builder2);
        BiomeDefaultFeatures.addDefaultSoftDisks(builder2);
        BiomeDefaultFeatures.addDefaultExtraVegetation(builder2);

        Music music = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_DEEP_DARK);
        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.7F)
                .downfall(0.8F)
                .specialEffects(
                        new BiomeSpecialEffects.Builder()
                                .waterColor(0x76889d)
                                .waterFogColor(0x556980)
                                .fogColor(0x827e7c)
                                .skyColor(0xb9b9b9)
                                .grassColorOverride(0x7e827c)
                                .foliageColorOverride(0x8a8c85)
                                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                                .backgroundMusic(music)
                                .build()
                )
                .mobSpawnSettings(builder.build())
                .generationSettings(builder2.build())
                .build();
    }
}