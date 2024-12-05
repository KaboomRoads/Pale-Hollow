package com.kaboomroads.palehollow.item;

import com.kaboomroads.palehollow.PaleHollow;
import com.kaboomroads.palehollow.block.ModBlocks;
import com.kaboomroads.palehollow.entity.ModEntityType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class ModItems {
    public static final Item MUTE_LOG = registerBlock(ModBlocks.MUTE_LOG);
    public static final Item MUTE_WOOD = registerBlock(ModBlocks.MUTE_WOOD);
    public static final Item STRIPPED_MUTE_LOG = registerBlock(ModBlocks.STRIPPED_MUTE_LOG);
    public static final Item STRIPPED_MUTE_WOOD = registerBlock(ModBlocks.STRIPPED_MUTE_WOOD);
    public static final Item MUTE_PLANKS = registerBlock(ModBlocks.MUTE_PLANKS);
    public static final Item MUTE_BUTTON = registerBlock(ModBlocks.MUTE_BUTTON);
    public static final Item MUTE_FENCE = registerBlock(ModBlocks.MUTE_FENCE);
    public static final Item MUTE_FENCE_GATE = registerBlock(ModBlocks.MUTE_FENCE_GATE);
    public static final Item MUTE_PRESSURE_PLATE = registerBlock(ModBlocks.MUTE_PRESSURE_PLATE);
    public static final Item MUTE_SIGN = registerBlock(
            ModBlocks.MUTE_SIGN, (block, properties) -> new SignItem(block, ModBlocks.MUTE_WALL_SIGN, properties), new Item.Properties().stacksTo(16)
    );
    public static final Item MUTE_SLAB = registerBlock(ModBlocks.MUTE_SLAB);
    public static final Item MUTE_STAIRS = registerBlock(ModBlocks.MUTE_STAIRS);
    public static final Item MUTE_DOOR = registerBlock(ModBlocks.MUTE_DOOR, DoubleHighBlockItem::new);
    public static final Item MUTE_TRAPDOOR = registerBlock(ModBlocks.MUTE_TRAPDOOR);
    public static final Item MUTE_HANGING_SIGN = registerBlock(
            ModBlocks.MUTE_HANGING_SIGN,
            (block, properties) -> new HangingSignItem(block, ModBlocks.MUTE_WALL_HANGING_SIGN, properties),
            new Item.Properties().stacksTo(16)
    );
    public static final Item MUTE_BOAT = registerItem(
            "mute_boat",
            properties -> new BoatItem(ModEntityType.MUTE_BOAT, properties),
            new Item.Properties().stacksTo(1)
    );
    public static final Item MUTE_CHEST_BOAT = registerItem(
            "mute_chest_boat",
            properties -> new BoatItem(ModEntityType.MUTE_CHEST_BOAT, properties),
            new Item.Properties().stacksTo(1)
    );
    public static final Item PALETHORN = registerBlock(ModBlocks.PALETHORN);
    public static final Item VOIDGRASS = registerBlock(ModBlocks.VOIDGRASS);
    public static final Item VOIDGRASS_BLOCK = registerBlock(ModBlocks.VOIDGRASS_BLOCK);
    public static final Item PALE_DIRT = registerBlock(ModBlocks.PALE_DIRT);
    public static final Item MUTE_SAPLING = registerBlock(ModBlocks.MUTE_SAPLING);
    public static final Item RAW_TAR = registerBlock(ModBlocks.RAW_TAR);
    public static final Item RAW_TAR_CHUNK = registerItem("raw_tar_chunk");
    public static final Item TAR = registerItem("tar");
    public static final Item TARFLOWER = registerBlock(ModBlocks.TARFLOWER);
    public static final Item PALEFRUIT = registerItem("palefruit", createBlockItemWithCustomItemName(ModBlocks.PALEFRUIT_PLANT), new Item.Properties().food(ModFoods.PALEFRUIT));
    public static final Item ETERNAL_LANTERN = registerBlock(ModBlocks.ETERNAL_LANTERN);

    private static Function<Item.Properties, Item> createBlockItemWithCustomItemName(Block block) {
        return properties -> new BlockItem(block, properties.useItemDescriptionPrefix());
    }

    public static Item registerBlock(Block block) {
        return registerBlock(block, BlockItem::new);
    }

    public static Item registerBlock(Block block, Item.Properties properties) {
        return registerBlock(block, BlockItem::new, properties);
    }

    public static Item registerBlock(Block block, UnaryOperator<Item.Properties> unaryOperator) {
        return registerBlock(
                block, (blockx, properties) -> new BlockItem(blockx, unaryOperator.apply(properties))
        );
    }

    public static Item registerBlock(Block block, Block... blocks) {
        Item item = registerBlock(block);

        for (Block block2 : blocks) {
            Item.BY_BLOCK.put(block2, item);
        }

        return item;
    }

    private static ResourceKey<Item> itemId(String string) {
        return ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(PaleHollow.MOD_ID, string));
    }

    private static ResourceKey<Item> blockIdToItemId(ResourceKey<Block> resourceKey) {
        return ResourceKey.create(Registries.ITEM, resourceKey.location());
    }

    public static Item registerBlock(Block block, BiFunction<Block, Item.Properties, Item> biFunction) {
        return registerBlock(block, biFunction, new Item.Properties());
    }

    public static Item registerBlock(Block block, BiFunction<Block, Item.Properties, Item> biFunction, Item.Properties properties) {
        return registerItem(
                blockIdToItemId(block.builtInRegistryHolder().key()), propertiesx -> biFunction.apply(block, propertiesx), properties.useBlockDescriptionPrefix()
        );
    }

    public static Item registerItem(String string, Function<Item.Properties, Item> function) {
        return registerItem(itemId(string), function, new Item.Properties());
    }

    public static Item registerItem(String string, Function<Item.Properties, Item> function, Item.Properties properties) {
        return registerItem(itemId(string), function, properties);
    }

    public static Item registerItem(String string, Item.Properties properties) {
        return registerItem(itemId(string), Item::new, properties);
    }

    public static Item registerItem(String string) {
        return registerItem(itemId(string), Item::new, new Item.Properties());
    }

    public static Item registerItem(ResourceKey<Item> resourceKey, Function<Item.Properties, Item> function) {
        return registerItem(resourceKey, function, new Item.Properties());
    }

    public static Item registerItem(ResourceKey<Item> resourceKey, Function<Item.Properties, Item> function, Item.Properties properties) {
        Item item = function.apply(properties.setId(resourceKey));
        if (item instanceof BlockItem blockItem) blockItem.registerBlocks(Item.BY_BLOCK, item);
        return Registry.register(BuiltInRegistries.ITEM, resourceKey, item);
    }

    public static void init() {
    }
}
