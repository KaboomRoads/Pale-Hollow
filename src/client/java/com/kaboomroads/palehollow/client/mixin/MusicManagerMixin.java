package com.kaboomroads.palehollow.client.mixin;

import com.kaboomroads.palehollow.client.mixinimpl.ModMusicInfo;
import com.kaboomroads.palehollow.client.mixinimpl.ModSoundManager;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.MusicInfo;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MusicManager.class)
public abstract class MusicManagerMixin {
    @Shadow
    private @Nullable SoundInstance currentMusic;
    @Shadow
    @Final
    private Minecraft minecraft;
    @Unique
    private float currentPitch = 1.0F;

    @Inject(method = "startPlaying", at = @At("TAIL"))
    private void inject_startPlaying(MusicInfo musicInfo, CallbackInfo ci) {
        currentPitch = ((ModMusicInfo) (Object) musicInfo).palehollow$getPitch();
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sounds/MusicInfo;music()Lnet/minecraft/sounds/Music;", ordinal = 0))
    private void tickPitch(CallbackInfo ci, @Local(ordinal = 0) MusicInfo musicInfo) {
        float f = ((ModMusicInfo) (Object) musicInfo).palehollow$getPitch();
        if (fadingCooldown > 0) fadingCooldown--;
        if (currentMusic != null && currentPitch != f) fadePitch(f);
    }

    @Unique
    private boolean fading = false;
    @Unique
    private int fadingCooldown = 0;
    @Unique
    private float fadingPitch = 1.0F;

    @Unique
    private void fadePitch(float f) {
        if (currentPitch != f) {
            if (fadingCooldown <= 0 && !fading) {
                fading = true;
                fadingPitch = f;
                fadingCooldown = 40;
            }
            if (currentPitch < fadingPitch) {
                currentPitch = currentPitch + Mth.clamp(currentPitch, 5.0E-4F, 0.025F);
                if (currentPitch > fadingPitch) {
                    currentPitch = fadingPitch;
                    fading = false;
                }
            } else {
                currentPitch = currentPitch - Mth.clamp(currentPitch, 5.0E-4F, 0.025F);
                if (currentPitch < fadingPitch) {
                    currentPitch = fadingPitch;
                    fading = false;
                }
            }
            currentPitch = Mth.clamp(currentPitch, 0.0F, 1.0F);
            ((ModSoundManager) minecraft.getSoundManager()).palehollow$setPitch(currentMusic, currentPitch);
        }
    }
}
