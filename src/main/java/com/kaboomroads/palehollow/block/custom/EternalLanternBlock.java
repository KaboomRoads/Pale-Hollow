package com.kaboomroads.palehollow.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class EternalLanternBlock extends LanternBlock {
    protected static final VoxelShape AABB = Shapes.or(
            Block.box(5, 0, 5, 11, 1, 11),
            Block.box(5, 8, 5, 11, 9, 11),
            Block.box(5.5, 1, 5.5, 10.5, 8, 10.5)
    );
    protected static final VoxelShape HANGING_AABB = Shapes.or(
            Block.box(5, 1, 5, 11, 2, 11),
            Block.box(5, 9, 5, 11, 10, 11),
            Block.box(5.5, 2, 5.5, 10.5, 9, 10.5)
    );

    public EternalLanternBlock(Properties properties) {
        super(properties);
    }

    @NotNull
    @Override
    protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return blockState.getValue(HANGING) ? HANGING_AABB : AABB;
    }
}
