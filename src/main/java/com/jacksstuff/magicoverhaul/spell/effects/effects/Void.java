package com.jacksstuff.magicoverhaul.spell.effects.effects;

import com.jacksstuff.magicoverhaul.spell.util.SpellEffect;

import java.util.HashMap;

public class Void extends SpellEffect {
    public Void() {
        this.TYPE = EffectType.EFFECT;
        this.LEVEL_UPS = new HashMap<>();
    }
}