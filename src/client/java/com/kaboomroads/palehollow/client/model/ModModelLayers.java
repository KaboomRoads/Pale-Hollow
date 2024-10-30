package com.kaboomroads.palehollow.client.model;

import com.kaboomroads.palehollow.PaleHollow;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModModelLayers {
    public static final ModelLayerLocation MUTE_BOAT = register("boat/mute");
    public static final ModelLayerLocation MUTE_CHEST_BOAT = register("chest_boat/mute");

    private static ModelLayerLocation register(String name) {
        return register(name, "main");
    }

    private static ModelLayerLocation register(String string, String string2) {
        return createLocation(string, string2);
    }

    private static ModelLayerLocation createLocation(String string, String string2) {
        return new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(PaleHollow.MOD_ID, string), string2);
    }
}
