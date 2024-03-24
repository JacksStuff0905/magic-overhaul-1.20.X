package com.jacksstuff.magicoverhaul.spell.effects.modes;

import com.jacksstuff.magicoverhaul.spell.util.SpellEffect;

import java.util.HashMap;

public class Storm extends SpellEffect {
    public Storm() {
        this.TYPE = EffectType.MODE;
        this.LEVEL_UPS = new HashMap<>();
    }
}
