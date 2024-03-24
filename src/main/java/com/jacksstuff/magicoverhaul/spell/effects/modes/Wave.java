package com.jacksstuff.magicoverhaul.spell.effects.modes;

import com.jacksstuff.magicoverhaul.spell.util.SpellEffect;

import java.util.HashMap;

public class Wave extends SpellEffect {
    public Wave() {
        this.TYPE = EffectType.MODE;
        this.LEVEL_UPS = new HashMap<>();
    }
}
