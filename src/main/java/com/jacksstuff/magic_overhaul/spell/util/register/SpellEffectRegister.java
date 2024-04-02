package com.jacksstuff.magic_overhaul.spell.util.register;

import com.jacksstuff.magic_overhaul.spell.util.SpellEffect;

import java.util.ArrayList;

public class SpellEffectRegister {
    public static final ArrayList<SpellEffectRegistryObject> EFFECTS = new ArrayList<>();


    public static <T extends SpellEffect> SpellEffectRegistryObject register(Class<T> classT) {
        SpellEffectRegistryObject ro = new SpellEffectRegistryObject(classT);
        EFFECTS.add(ro);
        return ro;
    }

    public static SpellEffect getByName(String name) {
        for (SpellEffectRegistryObject ro : EFFECTS) {
            if (ro.get().getName().equals(name)) {
                return ro.get();
            }
        }

        return null;
    }

    public static int getByEffect(SpellEffect effect) {
        for (int i = 0; i < EFFECTS.size(); i++) {
            if (EFFECTS.get(i).get().equals(effect)) {
                return i;
            }
        }

        return -1;
    }


    public static void init() {
        for (SpellEffectRegistryObject ro : EFFECTS) {
            ro.get();
        }
    }

    public static ArrayList<SpellEffectRegistryObject> getBaseLevelEffects() {
        ArrayList<SpellEffectRegistryObject> result = new ArrayList<>();
        for (SpellEffectRegistryObject ro : EFFECTS) {
            if (ro.get().isBaseLevel()) {
                result.add(ro);
            }
        }

        return result;
    }
}
