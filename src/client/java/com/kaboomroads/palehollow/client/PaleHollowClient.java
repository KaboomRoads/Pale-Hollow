package com.kaboomroads.palehollow.client;

import com.kaboomroads.palehollow.block.ModBlocks;
import com.kaboomroads.palehollow.client.model.ModModelLayers;
import com.kaboomroads.palehollow.entity.ModEntityType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.BoatRenderer;

public class PaleHollowClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntityType.MUTE_BOAT, context -> new BoatRenderer(context, ModModelLayers.MUTE_BOAT));
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.MUTE_BOAT, BoatModel::createBoatModel);
        EntityRendererRegistry.register(ModEntityType.MUTE_CHEST_BOAT, context -> new BoatRenderer(context, ModModelLayers.MUTE_CHEST_BOAT));
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.MUTE_CHEST_BOAT, BoatModel::createChestBoatModel);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout()
                , ModBlocks.PALETHORN
                , ModBlocks.VOIDGRASS
                , ModBlocks.MUTE_SAPLING
                , ModBlocks.POTTED_MUTE_SAPLING
                , ModBlocks.TARFLOWER
                , ModBlocks.POTTED_TARFLOWER
                , ModBlocks.PALEFRUIT_PLANT
        );
    }
}
