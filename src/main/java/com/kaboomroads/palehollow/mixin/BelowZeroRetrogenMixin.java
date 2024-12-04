package com.kaboomroads.palehollow.mixin;

import com.google.common.collect.ImmutableSet;
import com.kaboomroads.palehollow.worldgen.ModBiomes;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.BelowZeroRetrogen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Set;

@Mixin(BelowZeroRetrogen.class)
public abstract class BelowZeroRetrogenMixin {
    @ModifyExpressionValue(method = "<clinit>", at = @At(value = "INVOKE", target = "Ljava/util/Set;of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/Set;", ordinal = 0))
    private static Set<ResourceKey<Biome>> addToRetrogen(Set<ResourceKey<Biome>> original) {
        return ImmutableSet.<ResourceKey<Biome>>builder().addAll(original).add(ModBiomes.PALE_HOLLOW).build();
    }
}
