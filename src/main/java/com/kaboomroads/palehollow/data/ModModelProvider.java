package com.kaboomroads.palehollow.data;

import com.kaboomroads.palehollow.block.ModBlockFamilies;
import com.kaboomroads.palehollow.block.ModBlocks;
import com.kaboomroads.palehollow.block.custom.PalefruitPlantBlock;
import com.kaboomroads.palehollow.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.*;
import net.minecraft.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators generator) {
        generator.woodProvider(ModBlocks.MUTE_LOG).log(ModBlocks.MUTE_LOG).wood(ModBlocks.MUTE_WOOD);
        generator.woodProvider(ModBlocks.STRIPPED_MUTE_LOG).log(ModBlocks.STRIPPED_MUTE_LOG).wood(ModBlocks.STRIPPED_MUTE_WOOD);
        generator.family(ModBlockFamilies.MUTE_PLANKS.getBaseBlock()).generateFor(ModBlockFamilies.MUTE_PLANKS);
        generator.createHangingSign(ModBlocks.STRIPPED_MUTE_LOG, ModBlocks.MUTE_HANGING_SIGN, ModBlocks.MUTE_WALL_HANGING_SIGN);
        createPalethorn(generator);
        generator.createCrossBlockWithDefaultItem(ModBlocks.VOIDGRASS, BlockModelGenerators.TintState.NOT_TINTED);
        createVoidgrassBlock(generator);
        generator.createTrivialCube(ModBlocks.PALE_DIRT);
        generator.createPlant(ModBlocks.MUTE_SAPLING, ModBlocks.POTTED_MUTE_SAPLING, BlockModelGenerators.TintState.NOT_TINTED);
        generator.createPlant(ModBlocks.TARFLOWER, ModBlocks.POTTED_TARFLOWER, BlockModelGenerators.TintState.NOT_TINTED);
        generator.createRotatedVariantBlock(ModBlocks.RAW_TAR);
        createPalefruitPlant(generator);
    }

    @Override
    public void generateItemModels(ItemModelGenerators generator) {
        generator.generateFlatItem(ModItems.MUTE_BOAT, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(ModItems.MUTE_CHEST_BOAT, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(ModItems.RAW_TAR_CHUNK, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(ModItems.TAR, ModelTemplates.FLAT_ITEM);
    }

    private void createPalefruitPlant(BlockModelGenerators generator) {
        Block block = ModBlocks.PALEFRUIT_PLANT;
        generator.createSimpleFlatItemModel(block.asItem());
        PropertyDispatch propertyDispatch = PropertyDispatch.properties(PalefruitPlantBlock.AGE, BlockStateProperties.DOUBLE_BLOCK_HALF)
                .generate((age, half) -> switch (half) {
                    case UPPER ->
                            Variant.variant().with(VariantProperties.MODEL, generator.createSuffixedVariant(block, "_upper_stage_" + age, ModelTemplates.CROSS, TextureMapping::cross));
                    case LOWER -> PalefruitPlantBlock.isDouble(age)
                            ? Variant.variant().with(VariantProperties.MODEL, generator.createSuffixedVariant(block, "_lower_stage_" + age, ModelTemplates.CROSS, TextureMapping::cross))
                            : Variant.variant().with(VariantProperties.MODEL, generator.createSuffixedVariant(block, "_lower_stage_" + age, ModelTemplates.PARTICLE_ONLY,
                            resourceLocation -> TextureMapping.particle(TextureMapping.getBlockTexture(block, "_lower_stage_2"))));
                });
        generator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block).with(propertyDispatch));
    }

    private void createVoidgrassBlock(BlockModelGenerators generator) {
        ResourceLocation paleDirt = TextureMapping.getBlockTexture(ModBlocks.PALE_DIRT);
        Variant snowy = Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.GRASS_BLOCK, "_snow"));
        ResourceLocation resourceLocation2 = TexturedModel.CUBE_TOP_BOTTOM
                .get(ModBlocks.VOIDGRASS_BLOCK)
                .updateTextures(textureMappingx -> textureMappingx.put(TextureSlot.BOTTOM, paleDirt))
                .create(ModBlocks.VOIDGRASS_BLOCK, generator.modelOutput);
        generator.createGrassLikeBlock(ModBlocks.VOIDGRASS_BLOCK, resourceLocation2, snowy);
    }

    private void createPalethorn(BlockModelGenerators generator) {
        ResourceLocation resourceLocation = ModelLocationUtils.getModelLocation(ModBlocks.PALETHORN, "_side");
        ResourceLocation resourceLocation2 = ModelLocationUtils.getModelLocation(ModBlocks.PALETHORN, "_noside");
        generator.blockStateOutput
                .accept(
                        MultiPartGenerator.multiPart(ModBlocks.PALETHORN)
                                .with(Condition.condition().term(BlockStateProperties.NORTH, true), Variant.variant().with(VariantProperties.MODEL, resourceLocation))
                                .with(
                                        Condition.condition().term(BlockStateProperties.EAST, true),
                                        Variant.variant().with(VariantProperties.MODEL, resourceLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true)
                                )
                                .with(
                                        Condition.condition().term(BlockStateProperties.SOUTH, true),
                                        Variant.variant().with(VariantProperties.MODEL, resourceLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true)
                                )
                                .with(
                                        Condition.condition().term(BlockStateProperties.WEST, true),
                                        Variant.variant().with(VariantProperties.MODEL, resourceLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, true)
                                )
                                .with(
                                        Condition.condition().term(BlockStateProperties.UP, true),
                                        Variant.variant().with(VariantProperties.MODEL, resourceLocation).with(VariantProperties.X_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, true)
                                )
                                .with(
                                        Condition.condition().term(BlockStateProperties.DOWN, true),
                                        Variant.variant().with(VariantProperties.MODEL, resourceLocation).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true)
                                )
                                .with(
                                        Condition.condition().term(BlockStateProperties.NORTH, false),
                                        Variant.variant().with(VariantProperties.MODEL, resourceLocation2)
                                )
                                .with(
                                        Condition.condition().term(BlockStateProperties.EAST, false),
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, resourceLocation2)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .with(
                                        Condition.condition().term(BlockStateProperties.SOUTH, false),
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, resourceLocation2)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .with(
                                        Condition.condition().term(BlockStateProperties.WEST, false),
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, resourceLocation2)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .with(
                                        Condition.condition().term(BlockStateProperties.UP, false),
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, resourceLocation2)
                                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .with(
                                        Condition.condition().term(BlockStateProperties.DOWN, false),
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, resourceLocation2)
                                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                );
    }
}
