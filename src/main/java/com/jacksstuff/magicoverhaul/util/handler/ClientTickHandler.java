package com.jacksstuff.magicoverhaul.util.handler;

import com.jacksstuff.magicoverhaul.MagicOverhaul;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.MouseInfo;

@Mod.EventBusSubscriber(modid = MagicOverhaul.MOD_ID, value = Dist.CLIENT)
public class ClientTickHandler {
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            //tick has passed


        }
    }

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Post event) {
        if (!(event.getOverlay().id().equals(VanillaGuiOverlay.PLAYER_LIST.id()))) {
            return;
        }



    }
}