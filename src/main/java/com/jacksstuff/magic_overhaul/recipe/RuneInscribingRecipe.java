package com.jacksstuff.magic_overhaul.recipe;

import com.jacksstuff.magic_overhaul.MagicOverhaul;
import com.jacksstuff.magic_overhaul.screen.RuneInscriberMenu;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;


public class RuneInscribingRecipe implements Recipe<SimpleContainer> {
    private final Ingredient base;
    private final Ingredient template;
    private final ItemStack output;


    public static final RecipeType<RuneInscribingRecipe> RECIPE_TYPE = new RecipeType<>(){};

    public RuneInscribingRecipe(Ingredient base, Ingredient template, ItemStack output) {
        this.base = base;
        this.template = template;
        this.output = output;
    }

    @Override
    public boolean matches(SimpleContainer simpleContainer, Level level) {
        if (level.isClientSide()) {
            return false;
        }

        return base.test(simpleContainer.getItem(RuneInscriberMenu.BASE_INPUT_SLOT)) && template.test(simpleContainer.getItem(RuneInscriberMenu.TEMPLATE_INPUT_SLOT));
    }

    @Override
    public ItemStack assemble(SimpleContainer simpleContainer, RegistryAccess registryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return output.copy();
    }


    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.RUNE_INSCRIBING_SERIALIZER.get();
    }


    public Ingredient getTemplate() {
        return this.template;
    }

    public Ingredient getBase() {
        return this.base;
    }


    @Override
    public RecipeType<?> getType() {
        return RECIPE_TYPE;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }



    public static class Serializer implements RecipeSerializer<RuneInscribingRecipe> {
        private static final Codec<RuneInscribingRecipe> CODEC = RecordCodecBuilder.create(
                (builder) -> builder.group(
                        Ingredient.CODEC.fieldOf("template").forGetter(RuneInscribingRecipe::getTemplate),
                        Ingredient.CODEC.fieldOf("base").forGetter(RuneInscribingRecipe::getBase),
                        ItemStack.CODEC.fieldOf("output").forGetter((RuneInscribingRecipe recipe) -> recipe.output)
                ).apply(builder, RuneInscribingRecipe::new));


        public Serializer() {

        }

        @Override
        public Codec<RuneInscribingRecipe> codec() {
            return CODEC;
        }


        @Override
        public @Nullable RuneInscribingRecipe fromNetwork(FriendlyByteBuf friendlyByteBuf) {
            Ingredient base = Ingredient.fromNetwork(friendlyByteBuf);
            Ingredient template = Ingredient.fromNetwork(friendlyByteBuf);

            ItemStack output = friendlyByteBuf.readItem();

            return new RuneInscribingRecipe(base, template, output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf friendlyByteBuf, RuneInscribingRecipe runeInscribingRecipe) {
            runeInscribingRecipe.base.toNetwork(friendlyByteBuf);
            runeInscribingRecipe.template.toNetwork(friendlyByteBuf);

            friendlyByteBuf.writeItemStack(runeInscribingRecipe.output, false);
        }
    }
}
