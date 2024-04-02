package com.jacksstuff.magic_overhaul.spell.spell_effects.modes;

import com.jacksstuff.magic_overhaul.spell.ModEffects;
import com.jacksstuff.magic_overhaul.spell.util.SpellEffect;

import java.util.HashMap;

public class Charge extends SpellEffect {
    @Override
    public EffectType getType() {
        return EffectType.MODE;
    }

    @Override
    public void init() {
        this.addLevelUp(ModEffects.BOLT.get(), ModEffects.RAY.get());
        this.addLevelUp(ModEffects.DOME.get(), ModEffects.WAVE.get());
    }
}
