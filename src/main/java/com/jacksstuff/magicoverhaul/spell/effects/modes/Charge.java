package com.jacksstuff.magicoverhaul.spell.effects.modes;

import com.jacksstuff.magicoverhaul.spell.ModEffects;
import com.jacksstuff.magicoverhaul.spell.util.SpellEffect;

import java.util.HashMap;

public class Charge extends SpellEffect {
    public Charge() {
        this.TYPE = EffectType.MODE;
        this.LEVEL_UPS = new HashMap<>();
    }

    @Override
    public void init() {
        this.addLevelUp(ModEffects.BOLT.get(), ModEffects.RAY.get());
        this.addLevelUp(ModEffects.DOME.get(), ModEffects.WAVE.get());
    }
}
