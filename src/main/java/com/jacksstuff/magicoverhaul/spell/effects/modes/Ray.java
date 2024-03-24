package com.jacksstuff.magicoverhaul.spell.effects.modes;

import com.jacksstuff.magicoverhaul.spell.util.SpellEffect;

import java.util.HashMap;

public class Ray extends SpellEffect {
    public Ray() {
        this.TYPE = EffectType.MODE;
        this.LEVEL_UPS = new HashMap<>();
    }
}
