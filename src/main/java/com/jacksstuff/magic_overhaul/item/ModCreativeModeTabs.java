package com.jacksstuff.magic_overhaul.item;

import com.jacksstuff.magic_overhaul.MagicOverhaul;
import com.jacksstuff.magic_overhaul.block.ModBlocks;
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

                //Wands
                ModItems.forEachWand(pOutput::accept);

                pOutput.accept(ModItems.METAL_DETECTOR.get());

                //Runes
                ModItems.forEachRune(pOutput::accept);


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
