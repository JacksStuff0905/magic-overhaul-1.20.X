package com.jacksstuff.magicoverhaul.spell.effects.modes;

import com.jacksstuff.magicoverhaul.spell.ModEffects;
import com.jacksstuff.magicoverhaul.spell.util.SpellEffect;

import java.util.HashMap;

public class Bolt extends SpellEffect {
    public Bolt() {
        this.TYPE = EffectType.MODE;
        this.LEVEL_UPS = new HashMap<>();
    }

    @Override
    public void init() {
        this.addLevelUp(ModEffects.DOME.get(), ModEffects.STORM.get());
    }
}
