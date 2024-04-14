package com.jacksstuff.magic_overhaul;

import com.jacksstuff.magic_overhaul.block.entity.ModBlockEntities;
import com.jacksstuff.magic_overhaul.recipe.ModRecipes;
import com.jacksstuff.magic_overhaul.screen.ModMenuTypes;
import com.jacksstuff.magic_overhaul.screen.RuneInscriberScreen;
import com.jacksstuff.magic_overhaul.screen.SpellCastScreen;
import com.mojang.logging.LogUtils;
import com.jacksstuff.magic_overhaul.block.ModBlocks;
import com.jacksstuff.magic_overhaul.item.ModCreativeModeTabs;
import com.jacksstuff.magic_overhaul.item.ModItems;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MagicOverhaul.MOD_ID)
public class MagicOverhaul
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "magic_overhaul";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public MagicOverhaul()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        //Registering
        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);

        ModBlocks.register(modEventBus);

        ModBlockEntities.register(modEventBus);

        ModMenuTypes.register(modEventBus);

        ModRecipes.register(modEventBus);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ModItems.DEBUG_WAND);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }


    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            event.enqueueWork(() -> {
                MenuScreens.register(ModMenuTypes.RUNE_INSCRIBER_MENU.get(), RuneInscriberScreen::new);
                MenuScreens.register(ModMenuTypes.SPELL_CAST_MENU.get(), SpellCastScreen::new);
                // TODO: Add more later
            });
        }
    }


}
