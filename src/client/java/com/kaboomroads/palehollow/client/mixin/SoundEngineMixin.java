package com.kaboomroads.palehollow.client.mixin;

import com.kaboomroads.palehollow.client.mixinimpl.ModSoundEngine;
import com.kaboomroads.palehollow.worldgen.ModBiomes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.ChannelAccess;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(SoundEngine.class)
public abstract class SoundEngineMixin implements ModSoundEngine {
    @Shadow
    private boolean loaded;

    @Shadow
    @Final
    private Map<SoundInstance, ChannelAccess.ChannelHandle> instanceToChannel;

    @Shadow
    protected abstract float calculatePitch(SoundInstance soundInstance);

    @Inject(method = "play", at = @At(value = "HEAD"))
    private void modifySoundsInBiome(SoundInstance soundInstance, CallbackInfo ci) {
        if (!(soundInstance instanceof AbstractSoundInstance abstractSoundInstance)) return;
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;
        double x = soundInstance.getX();
        double y = soundInstance.getY();
        double z = soundInstance.getZ();
        Holder<Biome> biome = player.level().getBiomeManager().getBiome(new BlockPos((int) x, (int) y, (int) z));
        if (biome.is(ModBiomes.PALE_HOLLOW)) {
            abstractSoundInstance.volume *= 0.75F;
            abstractSoundInstance.pitch *= 0.5F;
        }
    }

    @Override
    public void palehollow$setPitch(SoundInstance soundInstance, float pitch) {
        if (loaded) {
            ChannelAccess.ChannelHandle channelHandle = instanceToChannel.get(soundInstance);
            if (channelHandle != null)
                channelHandle.execute(channel -> channel.setPitch(pitch * calculatePitch(soundInstance)));
        }
    }
}
