package com.kaboomroads.palehollow.block;

import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;

public class ModBlockFamilies {
    public static final BlockFamily MUTE_PLANKS = BlockFamilies.familyBuilder(ModBlocks.MUTE_PLANKS)
            .button(ModBlocks.MUTE_BUTTON)
            .fence(ModBlocks.MUTE_FENCE)
            .fenceGate(ModBlocks.MUTE_FENCE_GATE)
            .pressurePlate(ModBlocks.MUTE_PRESSURE_PLATE)
            .sign(ModBlocks.MUTE_SIGN, ModBlocks.MUTE_WALL_SIGN)
            .slab(ModBlocks.MUTE_SLAB)
            .stairs(ModBlocks.MUTE_STAIRS)
            .door(ModBlocks.MUTE_DOOR)
            .trapdoor(ModBlocks.MUTE_TRAPDOOR)
            .recipeGroupPrefix("wooden")
            .recipeUnlockedBy("has_planks")
            .getFamily();

    public static void init() {
    }
}
