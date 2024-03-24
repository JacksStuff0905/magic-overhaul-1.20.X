package com.jacksstuff.magicoverhaul.spell;

import com.ibm.icu.text.ArabicShaping;
import com.jacksstuff.magicoverhaul.item.custom.RuneItem;
import com.jacksstuff.magicoverhaul.spell.util.SpellEffect;
import com.jacksstuff.magicoverhaul.spell.util.Spell;
import com.jacksstuff.magicoverhaul.spell.util.SpellResult;
import net.minecraft.world.item.Item;

import java.lang.reflect.Array;
import java.util.*;

public class SpellResultCalculator {



    public static SpellResult Result(Item... runes) {
        if (runes.length == 0) {
            return new SpellResult(new Spell[]{}, SpellResult.ResultType.PARTIAL);
        }

        if (runes.length == 1) {
            return new SpellResult(new Spell[]{}, SpellResult.ResultType.PARTIAL);
        }

        //Count effects
        ArrayList<LeveledEffect> effects = new ArrayList<>();
        for (Item rune : runes) {
            for (SpellEffect e : ((RuneItem) rune).getIncreasedEffects()) {
                int index = contains(effects, e);
                if (index == -1) {
                    effects.add(new LeveledEffect(e, 1));
                }
                else {
                    effects.get(index).level++;
                }
            }

            for (SpellEffect e : ((RuneItem) rune).getDecreasedEffects()) {
                int index = contains(effects, e);
                if (index == -1) {
                    effects.add(new LeveledEffect(e, -1));
                }
                else {
                    effects.get(index).level--;
                }
            }
        }



        //Remove effects below level 0
        ArrayList<LeveledEffect> leveledEffects = new ArrayList<>();
        for (LeveledEffect effect : effects) {
            if (effect.level > 0) {
                leveledEffects.add(effect);
            }
        }

        //Combine modes with effects
        HashMap<Integer, ArrayList<SpellEffect>> mappedEffects = new HashMap<>();
        for (LeveledEffect le : leveledEffects) {
            ArrayList<SpellEffect> tmp = mappedEffects.entrySet().size() > 0 && mappedEffects.containsKey(le.level) && mappedEffects.get(le.level).size() > 0 ? mappedEffects.get(le.level) : new ArrayList<>();

            tmp.add(le.effect);

            mappedEffects.put(le.level, tmp);
        }

        mappedEffects = mergeEffects(mappedEffects);


        SpellResult.ResultType resultType = SpellResult.ResultType.VALID;

        ArrayList<Spell> spells = new ArrayList<>();
        for (Map.Entry<Integer, ArrayList<SpellEffect>> entry : mappedEffects.entrySet()) {
            boolean skip = false;
            if (entry.getValue().size() != 2) {
                resultType = SpellResult.ResultType.PARTIAL;
                skip = true;
            }

            if (entry.getValue().size() > 2 || (entry.getValue().size() == 2 && entry.getValue().get(0).TYPE.equals(entry.getValue().get(1).TYPE))) {
                resultType = SpellResult.ResultType.INVALID;
                skip = true;
            }

            if (skip)
                continue;
            spells.add(new Spell(entry.getValue().get(0), entry.getValue().get(1), entry.getKey()));
        }


        return new SpellResult(spells.toArray(new Spell[]{}), resultType);
    }

    private static HashMap mergeEffects(HashMap<Integer, ArrayList<SpellEffect>> mappedEffects) {
        HashMap<Integer, ArrayList<SpellEffect>> result = new HashMap<>();
        for (Map.Entry<Integer, ArrayList<SpellEffect>> entry : mappedEffects.entrySet()) {
            ArrayList<SpellEffect> resultVal = new ArrayList<>();
            OUTER_LOOP : for (int a = 0; a < entry.getValue().size(); a++) {
                for (int b = 0; b < entry.getValue().size(); b++) {
                    if (a == b) {
                        continue;
                    }
                    if (containsKey(entry.getValue().get(a).LEVEL_UPS, entry.getValue().get(b))) {
                        SpellEffect ef = get(entry.getValue().get(a).LEVEL_UPS, entry.getValue().get(b));
                        resultVal.add(get(entry.getValue().get(a).LEVEL_UPS, entry.getValue().get(b)));
                        //resultVal.remove(a);
                        //resultVal.remove(b);
                        continue;
                    }

                }
                resultVal.add(entry.getValue().get(a));
            }
            result.put(entry.getKey(), resultVal);
        }

        return result;
    }

    private static boolean containsKey(HashMap<SpellEffect, SpellEffect> map, SpellEffect key) {
        for (int i = 0; i < map.keySet().size(); i++) {
            if (map.keySet().toArray()[i].equals(key)) {
                return true;
            }
        }
        return false;
    }

    private static SpellEffect get(HashMap<SpellEffect, SpellEffect> map, SpellEffect key) {
        for (int i = 0; i < map.keySet().size(); i++) {
            if (map.keySet().toArray()[i].equals(key)) {
                return map.values().toArray(new SpellEffect[]{})[i];
            }
        }
        return null;
    }

    private static int contains(ArrayList<LeveledEffect> list, SpellEffect effect) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).effect.equals(effect)) {
                return i;
            }
        }
        return -1;
    }




    private static class LeveledEffect {
        public SpellEffect effect;
        public int level;

        public LeveledEffect(SpellEffect effect, int level) {
            this.effect = effect;
            this.level = level;
        }
    }
}


