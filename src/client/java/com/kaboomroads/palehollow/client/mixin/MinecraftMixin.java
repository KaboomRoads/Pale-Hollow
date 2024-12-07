package com.kaboomroads.palehollow.client.mixin;

import com.kaboomroads.palehollow.PaleHollow;
import com.kaboomroads.palehollow.client.mixinimpl.ModMusicInfo;
import com.kaboomroads.palehollow.worldgen.ModBiomes;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sounds.MusicInfo;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Inject(method = "getSituationalMusic", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;getBackgroundMusicVolume()F", ordinal = 0))
    private void biomePitch(CallbackInfoReturnable<MusicInfo> cir, @Local(ordinal = 0) Holder<Biome> biomeHolder, @Share(value = "pitch", namespace = PaleHollow.MOD_ID) LocalFloatRef pitchRef) {
        if (biomeHolder.is(ModBiomes.PALE_HOLLOW)) {
            pitchRef.set(0.5F);
        } else pitchRef.set(1.0F);
    }

    @ModifyReturnValue(method = "getSituationalMusic", at = @At(value = "RETURN", ordinal = 2))
    private MusicInfo biomePitch0(MusicInfo original, @Share(value = "pitch", namespace = PaleHollow.MOD_ID) LocalFloatRef pitch) {
        ((ModMusicInfo) (Object) original).palehollow$setPitch(pitch.get());
        return original;
    }

    @ModifyReturnValue(method = "getSituationalMusic", at = @At(value = "RETURN", ordinal = 3))
    private MusicInfo biomePitch1(MusicInfo original, @Share(value = "pitch", namespace = PaleHollow.MOD_ID) LocalFloatRef pitch) {
        ((ModMusicInfo) (Object) original).palehollow$setPitch(pitch.get());
        return original;
    }

    @ModifyReturnValue(method = "getSituationalMusic", at = @At(value = "RETURN", ordinal = 4))
    private MusicInfo biomePitch2(MusicInfo original, @Share(value = "pitch", namespace = PaleHollow.MOD_ID) LocalFloatRef pitch) {
        ((ModMusicInfo) (Object) original).palehollow$setPitch(pitch.get());
        return original;
    }

    @ModifyReturnValue(method = "getSituationalMusic", at = @At(value = "RETURN", ordinal = 5))
    private MusicInfo biomePitch3(MusicInfo original, @Share(value = "pitch", namespace = PaleHollow.MOD_ID) LocalFloatRef pitch) {
        ((ModMusicInfo) (Object) original).palehollow$setPitch(pitch.get());
        return original;
    }
}
