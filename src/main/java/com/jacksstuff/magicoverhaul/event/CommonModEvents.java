package com.jacksstuff.magicoverhaul.event;

import com.jacksstuff.magicoverhaul.MagicOverhaul;
import com.jacksstuff.magicoverhaul.commands.CastSpellCommand;
import com.jacksstuff.magicoverhaul.network.PacketHandler;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.server.command.ConfigCommand;

@Mod.EventBusSubscriber(modid = MagicOverhaul.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEvents {
    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            PacketHandler.register();
        });
    }
}
