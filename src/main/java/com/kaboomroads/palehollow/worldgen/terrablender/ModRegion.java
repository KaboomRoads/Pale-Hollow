package com.kaboomroads.palehollow.worldgen.terrablender;

import com.kaboomroads.palehollow.worldgen.ModBiomes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class ModRegion extends Region {
    private final OverworldBiomeBuilder overworldBuilder = new OverworldBiomeBuilder();

    public ModRegion(ResourceLocation name, int weight) {
        super(name, RegionType.OVERWORLD, weight);
    }

    private static final Climate.Parameter FULL_RANGE = Climate.Parameter.span(-1.0F, 1.0F);

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        overworldBuilder.addBiomes(mapper);
        addBiome(mapper,
                Climate.parameters(
                        FULL_RANGE,
                        FULL_RANGE,
                        FULL_RANGE,
                        Climate.Parameter.span(-0.78F, 0.2225F),
                        Climate.Parameter.point(1.1F),
                        FULL_RANGE,
                        0.0F
                ),
                ModBiomes.PALE_HOLLOW);
    }
}