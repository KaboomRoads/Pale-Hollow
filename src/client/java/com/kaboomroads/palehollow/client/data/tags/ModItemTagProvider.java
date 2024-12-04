package com.kaboomroads.palehollow.client.data.tags;

import com.kaboomroads.palehollow.item.ModItems;
import com.kaboomroads.palehollow.tag.ModBlockTags;
import com.kaboomroads.palehollow.tag.ModItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture, BlockTagProvider blockTagProvider) {
        super(output, registriesFuture, blockTagProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        copy(ModBlockTags.MUTE_LOGS, ModItemTags.MUTE_LOGS);
        copy(BlockTags.LOGS, ItemTags.LOGS);
        copy(BlockTags.PLANKS, ItemTags.PLANKS);
        copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
        copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
        copy(BlockTags.FENCE_GATES, ItemTags.FENCE_GATES);
        copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
        copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
        copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
        copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
        copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
        copy(BlockTags.STANDING_SIGNS, ItemTags.SIGNS);
        copy(BlockTags.CEILING_HANGING_SIGNS, ItemTags.HANGING_SIGNS);
        getOrCreateTagBuilder(ItemTags.NON_FLAMMABLE_WOOD)
                .add(ModItems.MUTE_LOG)
                .add(ModItems.MUTE_WOOD)
                .add(ModItems.STRIPPED_MUTE_LOG)
                .add(ModItems.STRIPPED_MUTE_WOOD)
                .add(ModItems.MUTE_PLANKS)
                .add(ModItems.MUTE_BUTTON)
                .add(ModItems.MUTE_FENCE)
                .add(ModItems.MUTE_FENCE_GATE)
                .add(ModItems.MUTE_PRESSURE_PLATE)
                .add(ModItems.MUTE_SIGN)
                .add(ModItems.MUTE_SLAB)
                .add(ModItems.MUTE_STAIRS)
                .add(ModItems.MUTE_DOOR)
                .add(ModItems.MUTE_TRAPDOOR)
                .add(ModItems.MUTE_HANGING_SIGN)
        ;
        getOrCreateTagBuilder(ItemTags.BOATS)
                .add(ModItems.MUTE_BOAT)
        ;
        getOrCreateTagBuilder(ItemTags.CHEST_BOATS)
                .add(ModItems.MUTE_CHEST_BOAT)
        ;
    }
}
