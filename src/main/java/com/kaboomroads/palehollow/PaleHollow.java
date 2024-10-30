package com.kaboomroads.palehollow;

import com.kaboomroads.palehollow.block.ModBlockFamilies;
import com.kaboomroads.palehollow.block.ModBlocks;
import com.kaboomroads.palehollow.block.ModWoodType;
import com.kaboomroads.palehollow.entity.ModEntityType;
import com.kaboomroads.palehollow.item.ModCreativeModeTabs;
import com.kaboomroads.palehollow.item.ModItems;
import com.kaboomroads.palehollow.worldgen.feature.ModFeatures;
import com.kaboomroads.palehollow.worldgen.feature.ModFoliagePlacerType;
import com.kaboomroads.palehollow.worldgen.feature.ModTreeGrower;
import com.kaboomroads.palehollow.worldgen.feature.ModTrunkPlacerType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;

public class PaleHollow implements ModInitializer {
    public static final String MOD_ID = "palehollow";

    @Override
    public void onInitialize() {
        ModWoodType.init();
        ModBlocks.init();
        ModEntityType.init();
        ModItems.init();
        ModBlockFamilies.init();
        ModCreativeModeTabs.init();
        ModTreeGrower.init();
        ModTrunkPlacerType.init();
        ModFoliagePlacerType.init();
        ModFeatures.init();
        StrippableBlockRegistry.register(ModBlocks.MUTE_LOG, ModBlocks.STRIPPED_MUTE_LOG);
        StrippableBlockRegistry.register(ModBlocks.MUTE_WOOD, ModBlocks.STRIPPED_MUTE_WOOD);
        FuelRegistryEvents.BUILD.register((builder, context) -> builder.add(ModItems.TAR, context.baseSmeltTime() * 6));
    }
}
