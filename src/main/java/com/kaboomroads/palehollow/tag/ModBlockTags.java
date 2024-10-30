package com.kaboomroads.palehollow.tag;

import com.kaboomroads.palehollow.PaleHollow;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModBlockTags {
    public static final TagKey<Block> MUTE_LOGS = create("mute_logs");

    private static TagKey<Block> create(String name) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(PaleHollow.MOD_ID, name));
    }
}
