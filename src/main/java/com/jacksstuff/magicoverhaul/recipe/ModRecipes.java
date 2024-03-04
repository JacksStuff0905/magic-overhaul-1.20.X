package com.jacksstuff.magicoverhaul.recipe;

import com.jacksstuff.magicoverhaul.MagicOverhaul;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModRecipes {
    public static final DeferredRegister<RecipeType<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, MagicOverhaul.MOD_ID);


    public static void register(IEventBus eventBus) {
        RECIPES.register(eventBus);
    }

}
