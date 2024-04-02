package com.jacksstuff.magic_overhaul.spell.spell_effects.effects;

import com.jacksstuff.magic_overhaul.spell.ModEffects;
import com.jacksstuff.magic_overhaul.spell.util.SpellEffect;

import java.util.HashMap;

public class Air extends SpellEffect {
    @Override
    public EffectType getType() {
        return EffectType.EFFECT;
    }

    @Override
    public void init() {
        this.addLevelUp(ModEffects.EARTH.get(), ModEffects.ICE.get());
    }
}
