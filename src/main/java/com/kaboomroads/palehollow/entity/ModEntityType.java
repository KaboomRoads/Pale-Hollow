package com.kaboomroads.palehollow.entity;

import com.kaboomroads.palehollow.PaleHollow;
import com.kaboomroads.palehollow.item.ModItems;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;

public class ModEntityType {
    public static final EntityType<Boat> MUTE_BOAT = register(
            "mute_boat",
            EntityType.Builder.of(EntityType.boatFactory(() -> ModItems.MUTE_BOAT), MobCategory.MISC)
                    .noLootTable()
                    .sized(1.375F, 0.5625F)
                    .eyeHeight(0.5625F)
                    .clientTrackingRange(10)
    );
    public static final EntityType<ChestBoat> MUTE_CHEST_BOAT = register(
            "mute_chest_boat",
            EntityType.Builder.of(EntityType.chestBoatFactory(() -> ModItems.MUTE_CHEST_BOAT), MobCategory.MISC)
                    .noLootTable()
                    .sized(1.375F, 0.5625F)
                    .eyeHeight(0.5625F)
                    .clientTrackingRange(10)
    );

    private static <T extends Entity> EntityType<T> register(ResourceKey<EntityType<?>> resourceKey, EntityType.Builder<T> builder) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, resourceKey, builder.build(resourceKey));
    }

    private static <T extends Entity> EntityType<T> register(String string, EntityType.Builder<T> builder) {
        return register(entityId(string), builder);
    }

    private static ResourceKey<EntityType<?>> entityId(String string) {
        return ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(PaleHollow.MOD_ID, string));
    }

    public static void init() {
    }
}
