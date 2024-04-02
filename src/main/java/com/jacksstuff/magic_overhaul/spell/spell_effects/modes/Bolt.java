package com.jacksstuff.magic_overhaul.spell.spell_effects.modes;

import com.jacksstuff.magic_overhaul.spell.ModEffects;
import com.jacksstuff.magic_overhaul.spell.util.SpellEffect;

import java.util.HashMap;

public class Bolt extends SpellEffect {
    @Override
    public EffectType getType() {
        return EffectType.MODE;
    }

    @Override
    public void init() {
        this.addLevelUp(ModEffects.DOME.get(), ModEffects.STORM.get());
    }
}
