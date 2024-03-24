package com.jacksstuff.magicoverhaul.spell.util;

import com.jacksstuff.magicoverhaul.spell.ModEffects;
import com.jacksstuff.magicoverhaul.spell.util.register.SpellEffectRegister;

import java.util.Arrays;

public class RuneEffects {
    public transient SpellEffect[] positiveEffects;
    public transient SpellEffect[] negativeEffects;

    private String[] positive;
    private String[] negative;

    public RuneEffects(SpellEffect[] positiveEffects, SpellEffect[] negativeEffects) {
        this.positiveEffects = positiveEffects;
        this.negativeEffects = negativeEffects;
    }

    @Override
    public String toString() {
        return "RuneEffects {" +
                "positiveEffects=" + Arrays.toString(positiveEffects) +
                ", negativeEffects=" + Arrays.toString(negativeEffects) +
                '}';
    }


    public SpellEffect[] getAllEffects() {
        SpellEffect[] result = Arrays.copyOf(positiveEffects, positiveEffects.length + negativeEffects.length);
        System.arraycopy(negativeEffects, 0, result, positiveEffects.length, negativeEffects.length);
        return result;
    }

    public RuneEffects getToStrings() {
        RuneEffects result = new RuneEffects(null, null);
        result.positive = new String[this.positiveEffects.length];
        result.negative = new String[this.negativeEffects.length];
        for (int i = 0; i < result.positive.length; i++) {
            result.positive[i] = this.positiveEffects[i].getName();
        }
        for (int i = 0; i < result.negative.length; i++) {
            result.negative[i] = this.negativeEffects[i].getName();
        }
        return result;
    }

    public RuneEffects getFromStrings() {
        new ModEffects();
        SpellEffect[] positive = new SpellEffect[this.positive.length];
        SpellEffect[] negative = new SpellEffect[this.negative.length];

        for (int i = 0; i < positive.length; i++) {
            positive[i] = SpellEffectRegister.getByName(this.positive[i]);
        }
        for (int i = 0; i < negative.length; i++) {
            negative[i] = SpellEffectRegister.getByName(this.negative[i]);
        }

        return new RuneEffects(positive, negative);
    }
}
