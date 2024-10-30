package com.kaboomroads.palehollow.mixin;

import com.kaboomroads.palehollow.block.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(BlockEntityType.class)
public abstract class BlockEntityTypeMixin {
    @ModifyArg(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/BlockEntityType;register(Ljava/lang/String;Lnet/minecraft/world/level/block/entity/BlockEntityType$BlockEntitySupplier;[Lnet/minecraft/world/level/block/Block;)Lnet/minecraft/world/level/block/entity/BlockEntityType;", ordinal = 7), index = 2)
    private static Block[] addSigns(Block[] blocks) {
        return ArrayUtils.addAll(blocks,
                ModBlocks.MUTE_SIGN,
                ModBlocks.MUTE_WALL_SIGN
        );
    }

    @ModifyArg(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/BlockEntityType;register(Ljava/lang/String;Lnet/minecraft/world/level/block/entity/BlockEntityType$BlockEntitySupplier;[Lnet/minecraft/world/level/block/Block;)Lnet/minecraft/world/level/block/entity/BlockEntityType;", ordinal = 8), index = 2)
    private static Block[] addHangingSigns(Block[] blocks) {
        return ArrayUtils.addAll(blocks,
                ModBlocks.MUTE_HANGING_SIGN,
                ModBlocks.MUTE_WALL_HANGING_SIGN
        );
    }
}
