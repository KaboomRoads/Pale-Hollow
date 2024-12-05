package com.kaboomroads.palehollow.client.data;

import com.google.common.collect.ImmutableList;
import com.kaboomroads.palehollow.block.ModBlocks;
import com.kaboomroads.palehollow.item.ModItems;
import com.kaboomroads.palehollow.tag.ModItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.SuspiciousEffectHolder;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
        return new RecipeProvider(provider, recipeOutput) {
            @Override
            public void buildRecipes() {
                hangingSign(ModItems.MUTE_HANGING_SIGN, ModBlocks.STRIPPED_MUTE_LOG);
                planksFromLog(ModBlocks.MUTE_PLANKS, ModItemTags.MUTE_LOGS, 4);
                woodFromLogs(ModBlocks.MUTE_WOOD, ModBlocks.MUTE_LOG);
                woodFromLogs(ModBlocks.STRIPPED_MUTE_WOOD, ModBlocks.STRIPPED_MUTE_LOG);
                woodenBoat(ModItems.MUTE_BOAT, ModBlocks.MUTE_PLANKS);
                chestBoat(ModItems.MUTE_CHEST_BOAT, ModItems.MUTE_BOAT);
                oneToOneConversionRecipe(Items.BLACK_DYE, ModBlocks.TARFLOWER, "black_dye");
                oreSmelting(ImmutableList.of(ModItems.RAW_TAR, ModItems.RAW_TAR_CHUNK), RecipeCategory.MISC, ModItems.TAR, 0.7F, 400, "tar");
                suspiciousStew(ModItems.TARFLOWER, SuspiciousEffectHolder.tryGet(ModItems.TARFLOWER));
                shaped(RecipeCategory.DECORATIONS, ModBlocks.ETERNAL_LANTERN)
                        .define('#', ModItems.TAR)
                        .define('X', Items.IRON_NUGGET)
                        .pattern("XXX")
                        .pattern("X X")
                        .pattern("X#X")
                        .unlockedBy("has_iron_nugget", has(Items.IRON_NUGGET))
                        .unlockedBy("has_tar", has(ModItems.TAR))
                        .save(output);
            }
        };
    }

    @NotNull
    @Override
    public String getName() {
        return "Pale Hollow Recipes";
    }
}