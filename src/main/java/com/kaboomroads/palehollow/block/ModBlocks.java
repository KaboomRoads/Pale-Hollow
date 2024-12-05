package com.kaboomroads.palehollow.block;

import com.kaboomroads.palehollow.PaleHollow;
import com.kaboomroads.palehollow.block.custom.*;
import com.kaboomroads.palehollow.mixinimpl.BlockBehaviourPropertiesExt;
import com.kaboomroads.palehollow.worldgen.feature.ModTreeGrower;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Function;

public class ModBlocks {
    public static final Block MUTE_PLANKS = register(
            "mute_planks",
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.WOOD)

    );
    public static final Block MUTE_WOOD = register(
            "mute_wood",
            RotatedPillarBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)

    );
    public static final Block MUTE_LOG = register(
            "mute_log",
            RotatedPillarBlock::new,
            Blocks.logProperties(MapColor.QUARTZ, MUTE_WOOD.defaultMapColor(), SoundType.WOOD)
    );
    public static final Block STRIPPED_MUTE_LOG = register(
            "stripped_mute_log",
            RotatedPillarBlock::new,
            Blocks.logProperties(MUTE_PLANKS.defaultMapColor(), MUTE_PLANKS.defaultMapColor(), SoundType.WOOD)
    );
    public static final Block STRIPPED_MUTE_WOOD = register(
            "stripped_mute_wood",
            RotatedPillarBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MUTE_PLANKS.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
    );
    public static final Block MUTE_BUTTON = register(
            "mute_button", properties -> new ButtonBlock(ModBlockSetType.MUTE, 30, properties), Blocks.buttonProperties()
    );
    public static final Block MUTE_FENCE = register(
            "mute_fence",
            FenceBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MUTE_PLANKS.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.WOOD)
    );
    public static final Block MUTE_FENCE_GATE = register(
            "mute_fence_gate",
            properties -> new FenceGateBlock(ModWoodType.MUTE, properties),
            BlockBehaviour.Properties.of()
                    .mapColor(MUTE_PLANKS.defaultMapColor())
                    .forceSolidOn()
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F, 3.0F)
    );
    public static final Block MUTE_PRESSURE_PLATE = register(
            "mute_pressure_plate",
            properties -> new PressurePlateBlock(ModBlockSetType.MUTE, properties),
            BlockBehaviour.Properties.of()
                    .mapColor(MUTE_PLANKS.defaultMapColor())
                    .forceSolidOn()
                    .instrument(NoteBlockInstrument.BASS)
                    .noCollission()
                    .strength(0.5F)
                    .pushReaction(PushReaction.DESTROY)
    );
    public static final Block MUTE_SIGN = register(
            "mute_sign",
            properties -> new StandingSignBlock(ModWoodType.MUTE, properties),
            BlockBehaviour.Properties.of()
                    .mapColor(MUTE_PLANKS.defaultMapColor())
                    .forceSolidOn()
                    .instrument(NoteBlockInstrument.BASS)
                    .noCollission()
                    .strength(1.0F)
    );
    public static final Block MUTE_WALL_SIGN = register(
            "mute_wall_sign",
            properties -> new WallSignBlock(ModWoodType.MUTE, properties),
            Blocks.wallVariant(MUTE_SIGN, true)
                    .mapColor(MUTE_PLANKS.defaultMapColor())
                    .forceSolidOn()
                    .instrument(NoteBlockInstrument.BASS)
                    .noCollission()
                    .strength(1.0F)
    );
    public static final Block MUTE_SLAB = register(
            "mute_slab",
            SlabBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MUTE_PLANKS.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.WOOD)
    );
    public static final Block MUTE_STAIRS = registerStair("mute_stairs", MUTE_PLANKS);
    public static final Block MUTE_DOOR = register(
            "mute_door",
            properties -> new DoorBlock(ModBlockSetType.MUTE, properties),
            BlockBehaviour.Properties.of()
                    .mapColor(MUTE_PLANKS.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(3.0F)
                    .noOcclusion()
                    .pushReaction(PushReaction.DESTROY)
    );
    public static final Block MUTE_TRAPDOOR = register(
            "mute_trapdoor",
            properties -> new TrapDoorBlock(ModBlockSetType.MUTE, properties),
            BlockBehaviour.Properties.of()
                    .mapColor(MUTE_PLANKS.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(3.0F)
                    .noOcclusion()
                    .isValidSpawn(Blocks::never)
    );
    public static final Block MUTE_HANGING_SIGN = register(
            "mute_hanging_sign",
            properties -> new CeilingHangingSignBlock(ModWoodType.MUTE, properties),
            BlockBehaviour.Properties.of()
                    .mapColor(MUTE_PLANKS.defaultMapColor())
                    .forceSolidOn()
                    .instrument(NoteBlockInstrument.BASS)
                    .noCollission()
                    .strength(1.0F)
    );
    public static final Block MUTE_WALL_HANGING_SIGN = register(
            "mute_wall_hanging_sign",
            properties -> new WallHangingSignBlock(ModWoodType.MUTE, properties),
            Blocks.wallVariant(MUTE_HANGING_SIGN, true)
                    .mapColor(MUTE_PLANKS.defaultMapColor())
                    .forceSolidOn()
                    .instrument(NoteBlockInstrument.BASS)
                    .noCollission()
                    .strength(1.0F)
    );
    public static final Block PALETHORN = register("palethorn",
            PalethornBlock::new,
            ((BlockBehaviourPropertiesExt) BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(0.2F)
                    .sound(SoundType.GRASS)
                    .pushReaction(PushReaction.DESTROY)
                    .randomTicks()
                    .noCollission()
            ).palehollow$enableOcclusion()
    );
    public static final Block VOIDGRASS = register(
            "voidgrass",
            UndergroundBushBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .replaceable()
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .offsetType(BlockBehaviour.OffsetType.XYZ)
                    .pushReaction(PushReaction.DESTROY)
    );
    public static final Block VOIDGRASS_BLOCK = register(
            "voidgrass_block",
            VoidgrassBlockBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .randomTicks()
                    .strength(0.6F)
                    .sound(SoundType.GRASS)
    );
    public static final Block PALE_DIRT = register("pale_dirt", BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(0.5F).sound(SoundType.GRAVEL));
    public static final Block MUTE_SAPLING = register(
            "mute_sapling",
            properties -> new MuteSaplingBlock(ModTreeGrower.MUTE, properties),
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .noCollission()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .pushReaction(PushReaction.DESTROY)
    );
    public static final Block POTTED_MUTE_SAPLING = register(
            "potted_mute_sapling", properties -> new FlowerPotBlock(MUTE_SAPLING, properties), Blocks.flowerPotProperties()
    );
    public static final Block RAW_TAR = register(
            "raw_tar",
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.DEEPSLATE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops()
                    .strength(3.0F, 6.0F)
                    .sound(SoundType.DEEPSLATE)
    );
    public static final Block TARFLOWER = register(
            "tarflower",
            properties -> new UndergroundFlowerBlock(MobEffects.BLINDNESS, 12.0F, properties),
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .offsetType(BlockBehaviour.OffsetType.XYZ)
                    .pushReaction(PushReaction.DESTROY)
    );
    public static final Block POTTED_TARFLOWER = register(
            "potted_tarflower", properties -> new FlowerPotBlock(TARFLOWER, properties), Blocks.flowerPotProperties()
    );
    public static final Block PALEFRUIT_PLANT = register(
            "palefruit_plant",
            PalefruitPlantBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .noCollission()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.CROP)
                    .pushReaction(PushReaction.DESTROY)
    );

    public static Block registerStair(String name, Block block) {
        return register(name, properties -> new StairBlock(block.defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(block));
    }

    public static Block register(String name, Function<BlockBehaviour.Properties, Block> function, BlockBehaviour.Properties properties) {
        ResourceKey<Block> resourceKey = ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(PaleHollow.MOD_ID, name));
        Block block = function.apply(properties.setId(resourceKey));
        return register(resourceKey, block);
    }

    private static Block register(String name, BlockBehaviour.Properties properties) {
        return register(name, Block::new, properties);
    }

    public static Block register(String name, Block block) {
        return register(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(PaleHollow.MOD_ID, name)), block);
    }

    public static Block register(ResourceKey<Block> resourceKey, Block block) {
        Block registered = Registry.register(BuiltInRegistries.BLOCK, resourceKey, block);
        for (BlockState blockState : block.getStateDefinition().getPossibleStates()) {
            Block.BLOCK_STATE_REGISTRY.add(blockState);
            blockState.initCache();
        }
        block.getLootTable();
        return registered;
    }

    public static void init() {
    }
}
