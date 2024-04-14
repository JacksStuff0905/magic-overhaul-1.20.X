package com.jacksstuff.magic_overhaul.item.custom;

import com.jacksstuff.magic_overhaul.MagicOverhaul;
import com.jacksstuff.magic_overhaul.datagen.RuneEffectCombinationGenerator;
import com.jacksstuff.magic_overhaul.spell.util.RuneEffects;
import com.jacksstuff.magic_overhaul.spell.util.SpellEffect;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RuneItem extends Item {
    private RuneEffects EFFECTS;
    private final String name;


    public String getStringName() {
        return name;
    }


    public RuneItem(String name, Properties pProperties) {
        super(pProperties.stacksTo(1).rarity(Rarity.EPIC));
        this.name = name;
        EFFECTS = RuneEffectCombinationGenerator.getEffectsFromJSON(name);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip." + MagicOverhaul.MOD_ID + ".rune_item_effects.tooltip")
                .withStyle(ChatFormatting.AQUA));
        for (SpellEffect effect : EFFECTS.positiveEffects) {
            pTooltipComponents.add(
                    Component.literal(" + " + effect.getDisplayName())
                            .withStyle(ChatFormatting.GREEN));
        }
        for (SpellEffect effect : EFFECTS.negativeEffects) {
            pTooltipComponents.add(
                    Component.literal(" - " + effect.getDisplayName())
                            .withStyle(ChatFormatting.RED));
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    public SpellEffect getEffect(int i) {
        EFFECTS = RuneEffectCombinationGenerator.getEffectsFromJSON(name);

        return EFFECTS.getAllEffects()[i];
    }

    public SpellEffect[] getIncreasedEffects() {
        EFFECTS = RuneEffectCombinationGenerator.getEffectsFromJSON(name);

        return EFFECTS.positiveEffects;
    }

    public SpellEffect[] getDecreasedEffects() {
        EFFECTS = RuneEffectCombinationGenerator.getEffectsFromJSON(name);

        return EFFECTS.negativeEffects;
    }
}
