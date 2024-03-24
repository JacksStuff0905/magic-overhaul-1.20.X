package com.jacksstuff.magicoverhaul.item;

import com.jacksstuff.magicoverhaul.MagicOverhaul;
import com.jacksstuff.magicoverhaul.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MagicOverhaul.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MAGIC_TAB = CREATIVE_MODE_TABS.register("magic_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.RUNE_ACNAR.get()))
            .title(Component.translatable("creativetab.magic_tab"))
            .displayItems((pParameters, pOutput) -> {
                //Items
                pOutput.accept(ModItems.DEBUG_WAND.get());

                pOutput.accept(ModItems.METAL_DETECTOR.get());

                //Runes
                pOutput.accept(ModItems.RUNE_ACNAR.get());
                pOutput.accept(ModItems.RUNE_BRIMVYORA.get());
                pOutput.accept(ModItems.RUNE_CASCARIS.get());
                pOutput.accept(ModItems.RUNE_DUSPHOR.get());
                pOutput.accept(ModItems.RUNE_ETHELUX.get());
                pOutput.accept(ModItems.RUNE_FAERD.get());
                pOutput.accept(ModItems.RUNE_GALTHARA.get());
                pOutput.accept(ModItems.RUNE_HUXIS.get());
                pOutput.accept(ModItems.RUNE_INQYORE.get());
                pOutput.accept(ModItems.RUNE_JURNIX.get());
                pOutput.accept(ModItems.RUNE_KYMARA.get());
                pOutput.accept(ModItems.RUNE_LUXAAR.get());


                //Blocks
                pOutput.accept(ModBlocks.TEST_BLOCK.get());

                pOutput.accept(ModBlocks.TEST_BLOCK_ORE.get());

                pOutput.accept(ModBlocks.SOUND_BLOCK.get());

                pOutput.accept(ModBlocks.RUNE_EXTRACTOR.get());
            })
            .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);

    }
}
