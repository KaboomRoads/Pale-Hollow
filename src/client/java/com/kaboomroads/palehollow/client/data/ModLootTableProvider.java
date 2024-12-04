package com.kaboomroads.palehollow.client.data;

import com.kaboomroads.palehollow.block.ModBlocks;
import com.kaboomroads.palehollow.block.custom.PalefruitPlantBlock;
import com.kaboomroads.palehollow.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    protected ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        HolderLookup.RegistryLookup<Enchantment> enchantLookup = registries.lookupOrThrow(Registries.ENCHANTMENT);
        dropSelf(ModBlocks.MUTE_LOG);
        dropSelf(ModBlocks.MUTE_WOOD);
        dropSelf(ModBlocks.STRIPPED_MUTE_LOG);
        dropSelf(ModBlocks.STRIPPED_MUTE_WOOD);
        dropSelf(ModBlocks.MUTE_PLANKS);
        dropSelf(ModBlocks.MUTE_BUTTON);
        dropSelf(ModBlocks.MUTE_FENCE);
        dropSelf(ModBlocks.MUTE_FENCE_GATE);
        dropSelf(ModBlocks.MUTE_PRESSURE_PLATE);
        dropSelf(ModBlocks.MUTE_SIGN);
        add(ModBlocks.MUTE_SLAB, this::createSlabItemTable);
        dropSelf(ModBlocks.MUTE_STAIRS);
        add(ModBlocks.MUTE_DOOR, this::createDoorTable);
        dropSelf(ModBlocks.MUTE_TRAPDOOR);
        dropSelf(ModBlocks.MUTE_HANGING_SIGN);
        add(ModBlocks.VOIDGRASS, this::createShearsOnlyDrop);
        add(ModBlocks.VOIDGRASS_BLOCK, block -> createSingleItemTableWithSilkTouch(block, ModBlocks.PALE_DIRT));
        dropSelf(ModBlocks.PALE_DIRT);
        dropSelf(ModBlocks.MUTE_SAPLING);
        dropPottedContents(ModBlocks.POTTED_MUTE_SAPLING);
        add(ModBlocks.RAW_TAR, block -> createOreDrop(block, ModItems.RAW_TAR_CHUNK));
        dropSelf(ModBlocks.TARFLOWER);
        dropPottedContents(ModBlocks.POTTED_TARFLOWER);
        add(ModBlocks.PALETHORN, block -> createLeavesDrops(block, ModBlocks.MUTE_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));
        add(
                ModBlocks.PALEFRUIT_PLANT,
                block -> applyExplosionDecay(
                        block,
                        LootTable.lootTable()
                                .withPool(
                                        LootPool.lootPool()
                                                .when(
                                                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.PALEFRUIT_PLANT)
                                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PalefruitPlantBlock.AGE, 3).hasProperty(PalefruitPlantBlock.HALF, DoubleBlockHalf.UPPER))
                                                )
                                                .add(LootItem.lootTableItem(ModItems.PALEFRUIT))
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F)))
                                                .apply(ApplyBonusCount.addUniformBonusCount(enchantLookup.getOrThrow(Enchantments.FORTUNE)))
                                )
                                .withPool(
                                        LootPool.lootPool()
                                                .when(
                                                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.PALEFRUIT_PLANT)
                                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PalefruitPlantBlock.AGE, 2).hasProperty(PalefruitPlantBlock.HALF, DoubleBlockHalf.UPPER))
                                                )
                                                .add(LootItem.lootTableItem(ModItems.PALEFRUIT))
                                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                )
                )
        );
    }
}
