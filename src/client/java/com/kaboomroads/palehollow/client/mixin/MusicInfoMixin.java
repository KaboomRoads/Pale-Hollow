package com.kaboomroads.palehollow.client.mixin;

import com.kaboomroads.palehollow.client.mixinimpl.ModMusicInfo;
import net.minecraft.client.sounds.MusicInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(MusicInfo.class)
public abstract class MusicInfoMixin implements ModMusicInfo {
    @Unique
    private float pitch = 1.0F;

    @Override
    public float palehollow$getPitch() {
        return pitch;
    }

    @Override
    public void palehollow$setPitch(float pitch) {
        this.pitch = pitch;
    }
}
