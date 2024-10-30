package com.kaboomroads.palehollow.data;

import com.kaboomroads.palehollow.data.tags.ModBiomeTagsProvider;
import com.kaboomroads.palehollow.data.tags.ModBlockTagProvider;
import com.kaboomroads.palehollow.data.tags.ModItemTagProvider;
import com.kaboomroads.palehollow.data.worldgen.biome.ModBiomes;
import com.kaboomroads.palehollow.worldgen.feature.ModConfigurations;
import com.kaboomroads.palehollow.worldgen.feature.ModPlacements;
import com.kaboomroads.palehollow.worldgen.feature.ModTreeFeatures;
import com.kaboomroads.palehollow.worldgen.feature.ModTreePlacements;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

public class PaleHollowDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();
        ModBlockTagProvider blockTagProvider = pack.addProvider(ModBlockTagProvider::new);
        pack.addProvider((output, lookup) -> new ModItemTagProvider(output, lookup, blockTagProvider));
        pack.addProvider(ModBiomeTagsProvider::new);
        pack.addProvider(ModLootTableProvider::new);
        pack.addProvider(ModModelProvider::new);
        pack.addProvider(ModRecipeProvider::new);
        pack.addProvider(ModDynamicRegistryProvider::new);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder builder) {
        builder.add(Registries.BIOME, ModBiomes::bootstrap);
        builder.add(Registries.CONFIGURED_FEATURE, bootstrapContext -> {
            ModConfigurations.bootstrap(bootstrapContext);
            ModTreeFeatures.bootstrap(bootstrapContext);
        });
        builder.add(Registries.PLACED_FEATURE, bootstrapContext -> {
            ModPlacements.bootstrap(bootstrapContext);
            ModTreePlacements.bootstrap(bootstrapContext);
        });
    }
}
