package com.kaboomroads.palehollow.data.tags;

import com.kaboomroads.palehollow.data.worldgen.biome.ModBiomes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;

import java.util.concurrent.CompletableFuture;

public class ModBiomeTagsProvider extends FabricTagProvider<Biome> {
    public ModBiomeTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.BIOME, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        getOrCreateTagBuilder(BiomeTags.MINESHAFT_BLOCKING).add(ModBiomes.PALE_HOLLOW);
        getOrCreateTagBuilder(BiomeTags.IS_OVERWORLD).add(ModBiomes.PALE_HOLLOW);
        getOrCreateTagBuilder(BiomeTags.SPAWNS_COLD_VARIANT_FROGS).add(ModBiomes.PALE_HOLLOW);
    }
}