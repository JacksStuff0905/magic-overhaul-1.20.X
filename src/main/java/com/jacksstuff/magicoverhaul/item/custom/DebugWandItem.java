package com.jacksstuff.magicoverhaul.item.custom;

import com.jacksstuff.magicoverhaul.MagicOverhaul;
import com.jacksstuff.magicoverhaul.network.PacketHandler;
import com.jacksstuff.magicoverhaul.network.SWandUseSpawnEntityPacket;
import com.jacksstuff.magicoverhaul.screen.IActiveRunes;
import com.jacksstuff.magicoverhaul.screen.SpellCastMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class DebugWandItem extends AbstractWand {


    public DebugWandItem(Properties pProperties) {
        super(pProperties);
    }


}
