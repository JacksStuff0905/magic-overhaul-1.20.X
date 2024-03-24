package com.jacksstuff.magicoverhaul.item.custom;

import com.jacksstuff.magicoverhaul.MagicOverhaul;
import com.jacksstuff.magicoverhaul.item.ModItems;
import com.jacksstuff.magicoverhaul.screen.IActiveRunes;
import com.jacksstuff.magicoverhaul.screen.SpellCastMenu;
import com.jacksstuff.magicoverhaul.spell.SpellResultCalculator;
import com.jacksstuff.magicoverhaul.spell.util.Spell;
import com.jacksstuff.magicoverhaul.spell.util.SpellResult;
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

public abstract class AbstractWand extends Item implements MenuProvider, IActiveRunes {

    private SpellCastMenu spellCastMenu;

    public AbstractWand(Properties pProperties) {
        super(pProperties.stacksTo(1));
    }

    protected void changeSpell(ServerPlayer sPlayer) {
        sPlayer.openMenu(this, buf -> {
            buf.writeItem(new ItemStack(this));
        });
    }

    protected void castSpell(ServerPlayer sPlayer) {
        SpellResult result = getSpellResult();
        System.out.println(SpellResultCalculator.Result(ModItems.RUNE_ACNAR.get(), ModItems.RUNE_ETHELUX.get()));
        System.out.println(((RuneItem) ModItems.RUNE_ACNAR.get()).getIncreasedEffects()[0]);

        if (result.TYPE.equals(SpellResult.ResultType.INVALID)) {
            sPlayer.sendSystemMessage(Component.literal("Invalid spell result: removing used runes from inventory"));
            removeUsedRunes(sPlayer);
            return;
        }
        if (result.TYPE.equals(SpellResult.ResultType.PARTIAL)) {
            sPlayer.sendSystemMessage(Component.literal("Partial spell result: doing nothing"));
            return;
        }

        if (result.TYPE.equals(SpellResult.ResultType.VALID)) {
            sPlayer.sendSystemMessage(Component.literal("Valid spell result: showing results:"));
            for (Spell spell : result.SPELLS) {
                sPlayer.sendSystemMessage(Component.literal("   " + spell));
            }
        }
    }

    public final SpellResult getSpellResult() {
        if (activeRunes.equals(null) || activeRunes.equals(new ArrayList<RuneItem>()))
            return new SpellResult(new Spell[]{}, SpellResult.ResultType.PARTIAL);
        return SpellResultCalculator.Result(activeRunes.toArray(new RuneItem[]{}));
    }

    public final void removeUsedRunes(ServerPlayer sPlayer) {
        for (RuneItem runeItem : activeRunes) {
            for (int i = 0; i < sPlayer.getInventory().items.size(); i++) {
                if (sPlayer.getInventory().getItem(i).getItem().equals(runeItem.asItem())) {
                    sPlayer.getInventory().removeItem(i, 1);
                    break;
                }
            }

        }
        activeRunes.clear();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack heldItem = pPlayer.getItemInHand(pUsedHand);
        if (pUsedHand == InteractionHand.MAIN_HAND && !pLevel.isClientSide && pPlayer instanceof ServerPlayer sPlayer) {
            if (pPlayer.isShiftKeyDown()) {
                changeSpell(sPlayer);
            } else {
                castSpell(sPlayer);
            }
            return InteractionResultHolder.success(heldItem);
        }


        return InteractionResultHolder.pass(heldItem);
    }


    @Override
    public Component getDisplayName() {
        return Component.translatable("gui." + MagicOverhaul.MOD_ID + ".spell_cast_screen.title");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        ItemStack heldItem = player.getMainHandItem();
        this.spellCastMenu = SpellCastMenu.create(i, inventory, heldItem, this);
        this.activeRunes.clear();
        return spellCastMenu;
    }
}
