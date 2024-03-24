package com.jacksstuff.magicoverhaul.spell.effects.effects;

import com.jacksstuff.magicoverhaul.spell.ModEffects;
import com.jacksstuff.magicoverhaul.spell.util.SpellEffect;

import java.util.HashMap;
import java.util.Map;

public class Fire extends SpellEffect {
    public Fire() {
        this.TYPE = EffectType.EFFECT;
    }

    @Override
    public void init() {
        this.addLevelUp(ModEffects.AIR.get(), ModEffects.THUNDER.get());
        this.addLevelUp(ModEffects.EARTH.get(), ModEffects.VOID.get());
    }
}
