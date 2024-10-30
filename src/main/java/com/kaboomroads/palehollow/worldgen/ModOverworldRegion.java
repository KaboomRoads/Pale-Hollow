//package com.kaboomroads.palehollow.worldgen;
//
//import com.kaboomroads.palehollow.data.worldgen.biome.ModBiomes;
//import com.mojang.datafixers.util.Pair;
//import net.minecraft.core.Registry;
//import net.minecraft.resources.ResourceKey;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.level.biome.Biome;
//import net.minecraft.world.level.biome.Biomes;
//import net.minecraft.world.level.biome.Climate;
//import terrablender.api.Region;
//import terrablender.api.RegionType;
//
//import java.util.function.Consumer;
//
//public class ModOverworldRegion extends Region {
//    public ModOverworldRegion(ResourceLocation name, int weight) {
//        super(name, RegionType.OVERWORLD, weight);
//    }
//
//    private final Climate.Parameter FULL_RANGE = Climate.Parameter.span(-1.0F, 1.0F);
//
//    @Override
//    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
////        addBiome(mapper, Climate.parameters(FULL_RANGE, FULL_RANGE, FULL_RANGE, Climate.Parameter.span(Climate.Parameter.span(-1.0F, -0.78F), Climate.Parameter.span(-0.78F, -0.375F)), Climate.Parameter.point(1.1F), FULL_RANGE, 0.0F), ModBiomes.PALE_HOLLOW);
//        addBiomeSimilar(mapper, Biomes.DEEP_DARK, ModBiomes.PALE_HOLLOW);
////        addModifiedVanillaOverworldBiomes(mapper, builder -> builder.replaceBiome(Biomes.DEEP_DARK, ModBiomes.PALE_HOLLOW));
//    }
//}
