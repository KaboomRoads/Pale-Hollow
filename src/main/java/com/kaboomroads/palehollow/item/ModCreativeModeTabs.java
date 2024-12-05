package com.kaboomroads.palehollow.item;

import com.kaboomroads.palehollow.PaleHollow;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTabs {
    public static final ResourceKey<CreativeModeTab> PALE_HOLLOW_KEY = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), ResourceLocation.fromNamespaceAndPath(PaleHollow.MOD_ID, "pale_hollow"));
    public static final CreativeModeTab PALE_HOLLOW = register(PALE_HOLLOW_KEY,
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(ModItems.MUTE_LOG))
                    .displayItems((parameters, output) -> {
                        output.accept(new ItemStack(ModItems.MUTE_LOG));
                        output.accept(new ItemStack(ModItems.MUTE_WOOD));
                        output.accept(new ItemStack(ModItems.STRIPPED_MUTE_LOG));
                        output.accept(new ItemStack(ModItems.STRIPPED_MUTE_WOOD));
                        output.accept(new ItemStack(ModItems.MUTE_PLANKS));
                        output.accept(new ItemStack(ModItems.MUTE_STAIRS));
                        output.accept(new ItemStack(ModItems.MUTE_SLAB));
                        output.accept(new ItemStack(ModItems.MUTE_FENCE));
                        output.accept(new ItemStack(ModItems.MUTE_FENCE_GATE));
                        output.accept(new ItemStack(ModItems.MUTE_DOOR));
                        output.accept(new ItemStack(ModItems.MUTE_TRAPDOOR));
                        output.accept(new ItemStack(ModItems.MUTE_PRESSURE_PLATE));
                        output.accept(new ItemStack(ModItems.MUTE_BUTTON));
                        output.accept(new ItemStack(ModItems.MUTE_SIGN));
                        output.accept(new ItemStack(ModItems.MUTE_HANGING_SIGN));
                        output.accept(new ItemStack(ModItems.MUTE_BOAT));
                        output.accept(new ItemStack(ModItems.MUTE_CHEST_BOAT));
                        output.accept(new ItemStack(ModItems.PALETHORN));
                        output.accept(new ItemStack(ModItems.VOIDGRASS_BLOCK));
                        output.accept(new ItemStack(ModItems.VOIDGRASS));
                        output.accept(new ItemStack(ModItems.TARFLOWER));
                        output.accept(new ItemStack(ModItems.PALE_DIRT));
                        output.accept(new ItemStack(ModItems.MUTE_SAPLING));
                        output.accept(new ItemStack(ModItems.RAW_TAR));
                        output.accept(new ItemStack(ModItems.RAW_TAR_CHUNK));
                        output.accept(new ItemStack(ModItems.TAR));
                        output.accept(new ItemStack(ModItems.PALEFRUIT));
                        output.accept(new ItemStack(ModItems.ETERNAL_LANTERN));
                    })
                    .title(Component.translatable("itemGroup.pale_hollow.pale_hollow"))
                    .build()
    );

    public static CreativeModeTab register(ResourceKey<CreativeModeTab> key, CreativeModeTab tab) {
        return Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, key, tab);
    }

    public static void init() {
    }
}
