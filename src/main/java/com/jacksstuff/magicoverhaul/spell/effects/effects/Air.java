package com.jacksstuff.magicoverhaul.spell.effects.effects;

import com.jacksstuff.magicoverhaul.spell.ModEffects;
import com.jacksstuff.magicoverhaul.spell.util.SpellEffect;

import java.util.HashMap;

public class Air extends SpellEffect {
    public Air() {
        this.TYPE = EffectType.EFFECT;
        this.LEVEL_UPS = new HashMap<>();
    }

    @Override
    public void init() {
        this.addLevelUp(ModEffects.EARTH.get(), ModEffects.ICE.get());
    }
}
