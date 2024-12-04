package com.kaboomroads.palehollow.client.data.tags;

import com.kaboomroads.palehollow.block.ModBlocks;
import com.kaboomroads.palehollow.tag.ModBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.MUTE_LOG)
                .add(ModBlocks.MUTE_WOOD)
                .add(ModBlocks.MUTE_PLANKS)
                .add(ModBlocks.VOIDGRASS)
        ;
        getOrCreateTagBuilder(ModBlockTags.MUTE_LOGS)
                .add(ModBlocks.MUTE_LOG)
                .add(ModBlocks.MUTE_WOOD)
                .add(ModBlocks.STRIPPED_MUTE_LOG)
                .add(ModBlocks.STRIPPED_MUTE_WOOD)
        ;
        getOrCreateTagBuilder(BlockTags.LOGS)
                .addTag(ModBlockTags.MUTE_LOGS)
        ;
        getOrCreateTagBuilder(BlockTags.OVERWORLD_NATURAL_LOGS)
                .add(ModBlocks.MUTE_LOG)
        ;
        getOrCreateTagBuilder(BlockTags.PLANKS)
                .add(ModBlocks.MUTE_PLANKS)
        ;
        getOrCreateTagBuilder(BlockTags.WOODEN_BUTTONS)
                .add(ModBlocks.MUTE_BUTTON)
        ;
        getOrCreateTagBuilder(BlockTags.WOODEN_FENCES)
                .add(ModBlocks.MUTE_FENCE)
        ;
        getOrCreateTagBuilder(BlockTags.FENCE_GATES)
                .add(ModBlocks.MUTE_FENCE_GATE)
        ;
        getOrCreateTagBuilder(BlockTags.WOODEN_PRESSURE_PLATES)
                .add(ModBlocks.MUTE_PRESSURE_PLATE)
        ;
        getOrCreateTagBuilder(BlockTags.STANDING_SIGNS)
                .add(ModBlocks.MUTE_SIGN)
        ;
        getOrCreateTagBuilder(BlockTags.WALL_SIGNS)
                .add(ModBlocks.MUTE_WALL_SIGN)
        ;
        getOrCreateTagBuilder(BlockTags.WOODEN_SLABS)
                .add(ModBlocks.MUTE_SLAB)
        ;
        getOrCreateTagBuilder(BlockTags.WOODEN_STAIRS)
                .add(ModBlocks.MUTE_STAIRS)
        ;
        getOrCreateTagBuilder(BlockTags.WOODEN_DOORS)
                .add(ModBlocks.MUTE_DOOR)
        ;
        getOrCreateTagBuilder(BlockTags.WOODEN_TRAPDOORS)
                .add(ModBlocks.MUTE_TRAPDOOR)
        ;
        getOrCreateTagBuilder(BlockTags.CEILING_HANGING_SIGNS)
                .add(ModBlocks.MUTE_HANGING_SIGN)
        ;
        getOrCreateTagBuilder(BlockTags.WALL_HANGING_SIGNS)
                .add(ModBlocks.MUTE_WALL_HANGING_SIGN)
        ;
        getOrCreateTagBuilder(BlockTags.SWORD_EFFICIENT)
                .add(ModBlocks.PALETHORN)
                .add(ModBlocks.VOIDGRASS)
        ;
        getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_HOE)
                .add(ModBlocks.PALETHORN)
        ;
        getOrCreateTagBuilder(BlockTags.REPLACEABLE_BY_TREES)
                .add(ModBlocks.PALETHORN)
                .add(ModBlocks.VOIDGRASS)
        ;
        getOrCreateTagBuilder(BlockTags.DIRT)
                .add(ModBlocks.VOIDGRASS_BLOCK)
                .add(ModBlocks.PALE_DIRT)
        ;
        getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(ModBlocks.VOIDGRASS_BLOCK)
                .add(ModBlocks.PALE_DIRT)
        ;
        getOrCreateTagBuilder(BlockTags.FLOWER_POTS)
                .add(ModBlocks.POTTED_MUTE_SAPLING)
                .add(ModBlocks.POTTED_TARFLOWER)
        ;
        getOrCreateTagBuilder(BlockTags.SAPLINGS)
                .add(ModBlocks.MUTE_SAPLING)
        ;
        getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.RAW_TAR)
        ;
        getOrCreateTagBuilder(BlockTags.SMALL_FLOWERS)
                .add(ModBlocks.TARFLOWER)
        ;
    }
}
