package com.jacksstuff.magic_overhaul.datagen;

import com.jacksstuff.magic_overhaul.MagicOverhaul;
import com.jacksstuff.magic_overhaul.block.ModBlocks;
import com.jacksstuff.magic_overhaul.item.ModItems;
import com.jacksstuff.magic_overhaul.recipe.RuneInscribingRecipe;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.Iterator;
import java.util.List;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    private static final List<ItemLike> DEBUG_WAND_SMELTABLES = List.of(ModBlocks.TEST_BLOCK_ORE.get()); //make a list of ingredients that can be smelted to a single item

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }


    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        oreSmelting(recipeOutput, DEBUG_WAND_SMELTABLES, RecipeCategory.MISC, ModItems.DEBUG_WAND.get(), 0.25f, 100, "Debug Wand");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.TEST_BLOCK.get())
                .pattern("   ")
                .pattern("DDD")
                .pattern("   ")
                .define('D', ModItems.DEBUG_WAND.get())
                .unlockedBy(getHasName(ModItems.DEBUG_WAND.get()), has(ModItems.DEBUG_WAND.get()))
                .save(recipeOutput);


        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DEBUG_WAND.get(), 9)
                .requires(ModBlocks.TEST_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.TEST_BLOCK.get()), has(ModBlocks.TEST_BLOCK.get()))
                .save(recipeOutput);
    }

    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        Iterator var9 = pIngredients.iterator();

        while(var9.hasNext()) {
            ItemLike itemlike = (ItemLike)var9.next();
            SimpleCookingRecipeBuilder.generic(
                    Ingredient.of(
                            new ItemLike[]{itemlike}), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer).group(pGroup)
                    .unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, MagicOverhaul.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }

    }

    protected static void runeInscribing(RecipeOutput recipeOutput, RecipeSerializer<? extends RuneInscribingRecipe> extractionSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        Iterator iterator = pIngredients.iterator();
        /*
        while(iterator.hasNext()) {
            ItemLike itemlike = (ItemLike)iterator.next();
            SimpleCookingRecipeBuilder.generic(
                            Ingredient.of(
                                    new ItemLike[]{itemlike}), pCategory, pResult, pExperience, pCookingTime, extractionSerializer).group(pGroup)
                    .unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, MagicOverhaul.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
        */
    }
}
