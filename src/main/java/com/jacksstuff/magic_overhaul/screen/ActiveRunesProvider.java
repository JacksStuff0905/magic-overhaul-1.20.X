package com.jacksstuff.magic_overhaul.screen;

import com.jacksstuff.magic_overhaul.item.custom.RuneItem;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class ActiveRunesProvider extends Item {
    public HashSet<RuneItem> activeRunes = new HashSet<>();


    public ActiveRunesProvider(Properties pProperties) {
        super(pProperties);
    }
}
