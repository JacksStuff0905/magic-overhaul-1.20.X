package com.jacksstuff.magicoverhaul.spell.util;

import java.util.Arrays;

public class SpellResult {
    public enum ResultType {
        VALID,
        PARTIAL,
        INVALID
    }
    public final Spell[] SPELLS;
    public final ResultType TYPE;

    public SpellResult (Spell[] spells, ResultType type) {
        this.SPELLS = spells;
        this.TYPE = type;
    }

    @Override
    public String toString() {
        return "SpellResult { " +
                "SPELLS = " + Arrays.toString(SPELLS) +
                ", TYPE = " + TYPE +
                " }";
    }
}
