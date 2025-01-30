package com.kaboomroads.palehollow.worldgen.terrablender;

import com.kaboomroads.palehollow.PaleHollow;
import net.minecraft.resources.ResourceLocation;
import terrablender.api.Regions;
import terrablender.api.TerraBlenderApi;

public class ModTerraBlenderApi implements TerraBlenderApi {
    @Override
    public void onTerraBlenderInitialized() {
        Regions.register(new ModRegion(ResourceLocation.fromNamespaceAndPath(PaleHollow.MOD_ID, "overworld"), 5));
    }
}
