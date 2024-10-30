package com.kaboomroads.palehollow.mixin;

import com.kaboomroads.palehollow.block.ModBlocks;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ShearsItem.class)
public abstract class ShearsItemMixin {
    @ModifyReturnValue(method = "mineBlock", at = @At(value = "RETURN"))
    private boolean addShearsEfficient(boolean original, @Local(ordinal = 0, argsOnly = true) BlockState blockState) {
        return original || blockState.is(ModBlocks.VOIDGRASS);
    }
}
