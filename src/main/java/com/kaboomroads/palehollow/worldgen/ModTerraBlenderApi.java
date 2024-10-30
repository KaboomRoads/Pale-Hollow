//package com.kaboomroads.palehollow.worldgen;
//
//import com.kaboomroads.palehollow.PaleHollow;
//import net.minecraft.resources.ResourceLocation;
//import terrablender.api.Regions;
//import terrablender.api.SurfaceRuleManager;
//import terrablender.api.TerraBlenderApi;
//
//public class ModTerraBlenderApi implements TerraBlenderApi {
//    @Override
//    public void onTerraBlenderInitialized() {
//        Regions.register(new ModOverworldRegion(ResourceLocation.fromNamespaceAndPath(PaleHollow.MOD_ID, "overworld"), 2));
//        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, PaleHollow.MOD_ID, ModSurfaceRuleData.makeRules());
//    }
//}
