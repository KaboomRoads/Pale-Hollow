package com.kaboomroads.palehollow.mixin;

import com.kaboomroads.palehollow.block.custom.PalefruitPlantBlock;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SimpleBlockFeature.class)
public abstract class SimpleBlockFeatureMixin {
    @WrapOperation(method = "place", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/WorldGenLevel;isEmptyBlock(Lnet/minecraft/core/BlockPos;)Z", ordinal = 0))
    private boolean addPalefruitPlantPlacement(WorldGenLevel instance, BlockPos blockPos, Operation<Boolean> original, @Local(ordinal = 0) BlockState blockState) {
        if (blockState.getBlock() instanceof PalefruitPlantBlock) {
            BlockPos below = blockPos.below();
            if (instance.isEmptyBlock(below.below())) PalefruitPlantBlock.placeAt(instance, blockState, below, 2);
            return false;
        } else return original.call(instance, blockPos);
    }
}
