package com.jacksstuff.magic_overhaul.recipe;

import com.jacksstuff.magic_overhaul.MagicOverhaul;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MagicOverhaul.MOD_ID);

    public static final RegistryObject<RecipeSerializer<RuneInscribingRecipe>> RUNE_INSCRIBING_SERIALIZER =
            SERIALIZERS.register("rune_inscribing", () -> new RuneInscribingRecipe.Serializer());


    public static void register(IEventBus eventBus){
        SERIALIZERS.register(eventBus);
    }
}
