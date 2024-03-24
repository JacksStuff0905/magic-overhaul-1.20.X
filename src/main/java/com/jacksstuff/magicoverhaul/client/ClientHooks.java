package com.jacksstuff.magicoverhaul.client;

import com.jacksstuff.magicoverhaul.screen.RuneExtractorScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

public class ClientHooks {
    public static void openRuneExtractorScreen(BlockPos pos) {
        //Minecraft.getInstance().setScreen(new RuneExtractorScreen(pos));
    }
}
