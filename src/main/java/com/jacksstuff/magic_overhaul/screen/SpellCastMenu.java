package com.jacksstuff.magic_overhaul.screen;

import com.jacksstuff.magic_overhaul.item.custom.AbstractWandItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;


public class SpellCastMenu extends AbstractContainerMenu {

    public Player player;
    public Inventory playerInventory;
    public ItemStack holder;

    public ActiveRunesProvider runeStorage;



    private static boolean active = true;

    public static SpellCastMenu create(int id, Inventory inv, ItemStack stack, ActiveRunesProvider runeStorage) {
        SpellCastMenu menu = new SpellCastMenu(id, inv, stack);
        if (runeStorage.activeRunes == null)
            runeStorage.activeRunes = new HashSet<>();
        menu.runeStorage = runeStorage;
        return menu;
    }


    private SpellCastMenu (int pContainerId, Inventory inv, ItemStack stack) {
        super(ModMenuTypes.SPELL_CAST_MENU.get(), pContainerId);
        if (stack.getItem() instanceof ActiveRunesProvider activeRunesProvider) {
            if (activeRunesProvider.activeRunes == null)
                activeRunesProvider.activeRunes = new HashSet<>();
            this.runeStorage = activeRunesProvider;
        }
        init(inv, stack);
    }
    public SpellCastMenu (int pContainerId, Inventory inv, FriendlyByteBuf buffer) {
        this(pContainerId, inv, buffer.readItem());
    }

    private void init(Inventory inv, ItemStack stack) {
        if (inv != null && inv.player != null) {
            player = inv.player;
            playerInventory = inv;
        }
        if (player.level().isClientSide)
            return;
        holder = stack;

        //TODO: add slots
    }

    @Override
    public void removed(Player pPlayer) {

    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return player.getInventory().getItem(0);
    }

    @Override
    public boolean stillValid(Player player) {
        return playerInventory.getSelected() == holder;
    }


    private void createPlayerInventory (Inventory playerInventory) {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                this.addSlot(new Slot(playerInventory, 9 + column + row * 9, 8 + column * 18, 84 + row * 18));
            }
        }
    }


    private void createPlayerHotbar (Inventory playerInventory) {
        for (int column = 0; column < 9; column++) {
            this.addSlot(new Slot(playerInventory, column, 8 + column * 18, 142));
        }
    }



}
