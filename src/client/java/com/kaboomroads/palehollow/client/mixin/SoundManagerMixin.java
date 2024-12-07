package com.kaboomroads.palehollow.client.mixin;

import com.kaboomroads.palehollow.client.mixinimpl.ModSoundEngine;
import com.kaboomroads.palehollow.client.mixinimpl.ModSoundManager;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.client.sounds.SoundManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SoundManager.class)
public abstract class SoundManagerMixin implements ModSoundManager {
    @Shadow
    @Final
    private SoundEngine soundEngine;

    @Override
    public void palehollow$setPitch(SoundInstance soundInstance, float pitch) {
        ((ModSoundEngine) soundEngine).palehollow$setPitch(soundInstance, pitch);
    }
}
