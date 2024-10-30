package com.kaboomroads.palehollow.worldgen.feature;

import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class ModTreeGrower {
    public static final TreeGrower MUTE = new TreeGrower("mute", Optional.empty(), Optional.of(ModTreeFeatures.MUTE), Optional.empty());

    public static void init() {
    }
}
