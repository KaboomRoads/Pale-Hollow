package com.kaboomroads.palehollow.mixin;

import com.kaboomroads.palehollow.mixinimpl.BlockBehaviourPropertiesExt;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockBehaviour.Properties.class)
public abstract class BlockBehaviourPropertiesMixin implements BlockBehaviourPropertiesExt {
    @Shadow
    boolean canOcclude;

    @Override
    public BlockBehaviour.Properties palehollow$enableOcclusion() {
        canOcclude = true;
        return (BlockBehaviour.Properties) (Object) this;
    }
}
