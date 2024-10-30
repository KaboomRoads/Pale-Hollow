package com.kaboomroads.palehollow.mixin;

import com.kaboomroads.palehollow.data.worldgen.biome.ModBiomes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(OverworldBiomeBuilder.class)
public abstract class OverworldBiomeBuilderMixin {
    @Shadow
    @Final
    private Climate.Parameter FULL_RANGE;

    @Shadow
    protected abstract void addBottomBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, Climate.Parameter parameter, Climate.Parameter parameter2, Climate.Parameter parameter3, Climate.Parameter parameter4, Climate.Parameter parameter5, float f, ResourceKey<Biome> resourceKey);

    @Shadow
    @Final
    private Climate.Parameter[] erosions;

    @Inject(method = "addUndergroundBiomes", at = @At(value = "HEAD"))
    private void addCustomUndergoundBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, CallbackInfo ci) {
        addBottomBiome(
                consumer,
                this.FULL_RANGE,
                this.FULL_RANGE,
                Climate.Parameter.span(-0.5F, 1.0F),
                Climate.Parameter.span(erosions[1], erosions[2]),
                this.FULL_RANGE,
                0.0F,
                ModBiomes.PALE_HOLLOW
        );
    }
}
