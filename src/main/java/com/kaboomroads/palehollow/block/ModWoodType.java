package com.kaboomroads.palehollow.block;

import com.kaboomroads.palehollow.PaleHollow;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.WoodType;

public class ModWoodType {
    public static final WoodType MUTE = WoodType.register(
            new WoodType(
                    PaleHollow.MOD_ID + ":mute",
                    ModBlockSetType.MUTE,
                    SoundType.NETHER_WOOD,
                    SoundType.NETHER_WOOD_HANGING_SIGN,
                    SoundEvents.NETHER_WOOD_FENCE_GATE_CLOSE,
                    SoundEvents.NETHER_WOOD_FENCE_GATE_OPEN
            )
    );

    public static void init() {
    }
}
