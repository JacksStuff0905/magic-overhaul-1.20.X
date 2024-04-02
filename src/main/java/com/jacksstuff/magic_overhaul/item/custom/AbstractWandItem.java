package com.jacksstuff.magic_overhaul.item.custom;

import com.jacksstuff.magic_overhaul.MagicOverhaul;
import com.jacksstuff.magic_overhaul.anim.item.WandUseExtensionsAnim;
import com.jacksstuff.magic_overhaul.item.ModItems;
import com.jacksstuff.magic_overhaul.screen.ActiveRunesProvider;
import com.jacksstuff.magic_overhaul.screen.SpellCastMenu;
import com.jacksstuff.magic_overhaul.spell.SpellResultCalculator;
import com.jacksstuff.magic_overhaul.spell.util.Spell;
import com.jacksstuff.magic_overhaul.spell.util.SpellEffect;
import com.jacksstuff.magic_overhaul.spell.util.SpellResult;
import com.jacksstuff.magic_overhaul.spell.util.SpellResult.ResultType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.function.Consumer;

public abstract class AbstractWandItem extends ActiveRunesProvider implements MenuProvider {

    private static final int DEFAULT_CHARGE_DURATION = 10;
    private static final boolean DISPLAY_DEBUG_INFO = false;

    private int chargingTime = 0;

    private SpellCastMenu spellCastMenu;

    private static final int DURABILITY_DAMAGE = 1;

    private IClientItemExtensions extension = new WandUseExtensionsAnim(() -> (float)getChargeDuration());

    public AbstractWandItem(Properties pProperties) {
        super(pProperties);
    }


    private void updateRunes(Player player) {
        HashSet<RuneItem> newRunes = new HashSet<>();
        for (RuneItem runeItem : activeRunes) {
            if (player.getInventory().contains(new ItemStack(runeItem))) {
                newRunes.add(runeItem);
            }
        }
        activeRunes = newRunes;
    }

    protected void changeSpell(ServerPlayer sPlayer) {
        updateRunes(sPlayer);
        sPlayer.openMenu(this, buf -> {
            buf.writeItem(new ItemStack(this));
        });
    }

    protected void castSpell(Player pPlayer, ItemStack stack, InteractionHand hand, float chargeTime) {
        updateRunes(pPlayer);

        stack.hurtAndBreak(Math.round(DURABILITY_DAMAGE * chargeTime), pPlayer,
                player -> player.broadcastBreakEvent(hand));
        SpellResult result = getSpellResult();


        if (result.TYPE.equals(ResultType.INVALID)) {
            removeUsedRunes(pPlayer);
            return;
        }

        castEffects(result, pPlayer);

        if (DISPLAY_DEBUG_INFO)
            debugDisplaySpellResult(result, pPlayer);
    }

    private void castEffects(SpellResult result, Player player) {
        for (Spell spell : result.SPELLS) {
            spell.EFFECT.Cast(spell.LEVEL, player);
            spell.MODE.Cast(spell.LEVEL, player);
        }
    }

    private void debugDisplaySpellResult(SpellResult result, Player player) {
        if (result.TYPE.equals(ResultType.INVALID)) {
            player.sendSystemMessage(Component.literal("Invalid spell result: removing used runes from inventory"));
            return;
        }
        if (result.TYPE.equals(ResultType.PARTIAL)) {
            player.sendSystemMessage(Component.literal("Partial spell result: doing nothing"));
            return;
        }

        if (result.TYPE.equals(ResultType.VALID)) {
            player.sendSystemMessage(Component.literal("Valid spell result: showing results:"));
            for (Spell spell : result.SPELLS) {
                player.sendSystemMessage(Component.literal("   " + spell));
            }
        }
    }

    public final SpellResult getSpellResult() {

        if (activeRunes == null || activeRunes.equals(new ArrayList<RuneItem>()))
            return new SpellResult(new Spell[]{}, ResultType.PARTIAL);

        return SpellResultCalculator.Result(activeRunes.toArray(new RuneItem[]{}));
    }

    public final void removeUsedRunes(Player Player) {
        for (RuneItem runeItem : activeRunes) {
            for (int i = 0; i < Player.getInventory().items.size(); i++) {
                if (Player.getInventory().getItem(i).getItem().equals(runeItem.asItem())) {
                    Player.getInventory().removeItem(i, 1);
                    break;
                }
            }

        }
        activeRunes = new HashSet<>();
    }



    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        ItemStack heldItem = pPlayer.getItemInHand(pUsedHand);

        if (!pLevel.isClientSide() && pPlayer instanceof ServerPlayer sPlayer) {
            updateRunes(pPlayer);
            if (pPlayer.isShiftKeyDown()) {
                changeSpell(sPlayer);
            } else {
                if (!pPlayer.isUsingItem()) {
                    pPlayer.startUsingItem(pUsedHand);
                }
            }
            System.out.println("used");
        }

        return InteractionResultHolder.success(heldItem);
    }



    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        if (pLivingEntity instanceof Player player) {
            updateRunes(player);
            ItemStack heldItem = player.getItemInHand(player.getUsedItemHand());
            if (!pLevel.isClientSide() && player instanceof ServerPlayer sPlayer) {
                System.out.println(getPowerForTime(pTimeCharged) + ", " + pTimeCharged);
                if (getPowerForTime(pTimeCharged) >= 0.95F) {
                    System.out.println("cast");
                    castSpell(sPlayer, heldItem, player.getUsedItemHand(), getPowerForTime(pTimeCharged));
                }
            }
        }


    }



    private float getPowerForTime(int pUseTime) {
        float f = (float)(getUseDuration(null) - pUseTime) / (float)getChargeDuration();
        System.out.println("power: " + (getUseDuration(null) - pUseTime) + ", " + getChargeDuration());
        return f;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    public int getChargeDuration() {
        if (this.spellCastMenu == null || this.activeRunes == null) {
            //extension.setTotalTime(DEFAULT_CHARGE_DURATION);
            return DEFAULT_CHARGE_DURATION;
        }

        //extension.setTotalTime(this.spellCastMenu.runeStorage.activeRunes.size() * 5 + DEFAULT_CHARGE_DURATION);
        return this.activeRunes.size() * 5 + DEFAULT_CHARGE_DURATION;
    }


    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.CUSTOM;
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
        if (activeRunes == null)
            this.activeRunes = new HashSet<>();
        return spellCastMenu;
    }


    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        extension = new WandUseExtensionsAnim(() -> (float)getChargeDuration());
        consumer.accept(extension);
    }

}
