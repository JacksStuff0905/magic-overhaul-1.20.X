package com.jacksstuff.magicoverhaul.item.custom;

import com.jacksstuff.magicoverhaul.item.ModItems;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public class RuneItem extends Item {
    public RuneItem(Properties pProperties) {
        //ModItems.RUNE.AddRune(new RegistryObject<this>());

        super(pProperties.stacksTo(1));
    }
}
