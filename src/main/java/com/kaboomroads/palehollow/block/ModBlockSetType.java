package com.kaboomroads.palehollow.block;

import com.kaboomroads.palehollow.PaleHollow;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class ModBlockSetType {
    public static final BlockSetType MUTE = BlockSetType.register(
            new BlockSetType(
                    PaleHollow.MOD_ID + ":mute",
                    true,
                    true,
                    true,
                    BlockSetType.PressurePlateSensitivity.EVERYTHING,
                    SoundType.NETHER_WOOD,
                    SoundEvents.NETHER_WOOD_DOOR_CLOSE,
                    SoundEvents.NETHER_WOOD_DOOR_OPEN,
                    SoundEvents.NETHER_WOOD_TRAPDOOR_CLOSE,
                    SoundEvents.NETHER_WOOD_TRAPDOOR_OPEN,
                    SoundEvents.NETHER_WOOD_PRESSURE_PLATE_CLICK_OFF,
                    SoundEvents.NETHER_WOOD_PRESSURE_PLATE_CLICK_ON,
                    SoundEvents.NETHER_WOOD_BUTTON_CLICK_OFF,
                    SoundEvents.NETHER_WOOD_BUTTON_CLICK_ON
            )
    );
}
