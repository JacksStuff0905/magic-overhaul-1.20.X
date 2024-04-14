package com.jacksstuff.magic_overhaul.item.custom;

import com.jacksstuff.magic_overhaul.MagicOverhaul;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RuneTemplateItem extends Item {

    public RuneItem rune;

    public RuneTemplateItem(RuneItem rune, Properties pProperties) {
        super(pProperties.stacksTo(1).rarity(Rarity.RARE));
        this.rune = rune;
    }

    public String getStringName() {
        return "rune_template_" + rune.getStringName().replaceFirst("rune_", "");
    }


    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        //pTooltipComponents.addAll(new ItemStack(rune).getTooltipLines(Minecraft.getInstance().player, pIsAdvanced));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
