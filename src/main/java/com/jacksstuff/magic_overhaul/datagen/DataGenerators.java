package com.jacksstuff.magic_overhaul.datagen;


import com.jacksstuff.magic_overhaul.MagicOverhaul;
import com.jacksstuff.magic_overhaul.item.custom.RuneItem;
import com.jacksstuff.magic_overhaul.item.custom.RuneTemplateItem;
import com.jacksstuff.magic_overhaul.spell.ModEffects;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = MagicOverhaul.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        //Server
        generator.addProvider(event.includeServer(), new ModRecipeProvider(packOutput));
        generator.addProvider(event.includeServer(), ModLootTableProvider.create(packOutput));

        ModBlockTagGenerator blockTagGenerator = generator.addProvider(event.includeServer(),
                new ModBlockTagGenerator(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModItemTagGenerator(packOutput, lookupProvider, blockTagGenerator.contentsGetter(), existingFileHelper));

        //Client
        generator.addProvider(event.includeClient(), new ModBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new ModItemModelProvider(packOutput, existingFileHelper));

        //Rune generator
        new ModEffects();
        ModRuneTextureGenerator runeTextureGenerator = new ModRuneTextureGenerator("rune_background.png");
        ModRuneTemplateTextureGenerator runeTemplateTextureGenerator = new ModRuneTemplateTextureGenerator("rune_template_background.png");
        RuneEffectCombinationGenerator combinationGenerator = new RuneEffectCombinationGenerator();
        RuneNameGenerator runeNameGenerator = new RuneNameGenerator();
    }
}
