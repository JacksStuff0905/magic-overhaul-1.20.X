package net.jacksstuff.magicoverhaul.item;

import net.jacksstuff.magicoverhaul.MagicOverhaul;
import net.jacksstuff.magicoverhaul.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MagicOverhaul.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MAGIC_TAB = CREATIVE_MODE_TABS.register("magic_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.DEBUG_WAND.get()))
            .title(Component.translatable("creativetab.magic_tab"))
            .displayItems((pParameters, pOutput) -> {
                pOutput.accept(ModItems.DEBUG_WAND.get());
                pOutput.accept(Items.ENCHANTED_BOOK);

                pOutput.accept(ModBlocks.TEST_BLOCK.get());

                pOutput.accept(ModBlocks.TEST_BLOCK_ORE.get());
            })
            .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);

    }
}
