package com.kaboomroads.palehollow.tag;

import com.kaboomroads.palehollow.PaleHollow;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {
    public static final TagKey<Item> MUTE_LOGS = create("mute_logs");

    private static TagKey<Item> create(String name) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(PaleHollow.MOD_ID, name));
    }
}
