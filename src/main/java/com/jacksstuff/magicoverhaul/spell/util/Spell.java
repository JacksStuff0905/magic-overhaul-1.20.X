package com.jacksstuff.magicoverhaul.spell.util;

public class Spell {
    public SpellEffect EFFECT;
    public SpellEffect MODE;

    public int LEVEL;

    public Spell(SpellEffect a, SpellEffect b, int level) {
        if (a.TYPE == SpellEffect.EffectType.MODE) {
            this.MODE = a;
            this.EFFECT = b;
        } else {
            this.MODE = b;
            this.EFFECT = a;
        }
        this.LEVEL = level;
    }

    @Override
    public String toString() {
        return "Effect { " + EFFECT.toString() + " }; Mode { " + MODE.toString() + " }; Level: " + LEVEL;
    }
}
