package com.jacksstuff.magic_overhaul.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jacksstuff.magic_overhaul.MagicOverhaul;
import com.jacksstuff.magic_overhaul.item.custom.RuneItem;
import com.jacksstuff.magic_overhaul.spell.ModEffects;
import com.jacksstuff.magic_overhaul.spell.util.*;
import com.jacksstuff.magic_overhaul.spell.util.register.SpellEffectRegister;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.IntStream;

public class RuneEffectCombinationGenerator {
    public static final byte EFFECTS_PER_RUNE = 2;
    public static final byte OVERRIDE_POSITIVE_EFFECT_COUNT = -1;

    private static RuneItem[] allRuneItems;

    private static final int SEED = 0;

    public static final Path PATH = Path.of("..","src", "main", "resources", "data", MagicOverhaul.MOD_ID, "rune_effects");

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public RuneEffectCombinationGenerator() {
        init();
    }

    private static void init() {
        System.out.println("[" + MagicOverhaul.MOD_ID + "/RuneEffectCombinationGenerator]: Distributing effects to runes:");
        Boolean[][] booleans = getCombinations(false, true);
        ArrayList<RuneEffects> result = new ArrayList<>();
        for (Boolean[] b : booleans) {
            result.add(convertToEffects(b.clone()));
        }


        result = distributeEffectSets(result, SEED, 0);

        System.out.println("\teffect distribution: " + Arrays.toString(countEffectsArray(result)));

        generateJSONFiles(result);

    }



    private static ArrayList<RuneEffects> distributeEffectSets(ArrayList<RuneEffects> in, int seed, int type) {
        switch (type) {
            case 0:
                return randomDistribution(in, seed);
            case 2:
                return countedDistribution(in, seed);
            default:
                return diversifiedDistribution(in, seed);
        }
    }

    private static ArrayList<RuneEffects> randomDistribution(ArrayList<RuneEffects> in, int seed) {
        ArrayList<RuneEffects> result = new ArrayList<>();
        ArrayList<Integer> availableIndices = new ArrayList<>(IntStream.range(0, in.size()).boxed().toList());

        for (int i = 0; i < getAllRuneItems().length; i++) {
            Random random = new Random(seed);
            int index = random.nextInt(availableIndices.size());
            result.add(in.get(availableIndices.get(index)));
            availableIndices.remove(index);
        }
        return result;
    }

    private static ArrayList<RuneEffects> diversifiedDistribution(ArrayList<RuneEffects> in, int seed) {
        Map<Float, RuneEffects> weighted = new HashMap<>();
        Random random = new Random(seed);
        for (RuneEffects re : in) {
            weighted.put(uniqueness(in, re) + (random.nextFloat() - 0.5f) / 1000, re);
        }


        TreeMap<Float, RuneEffects> sorted = new TreeMap<>(weighted);

        ArrayList<RuneEffects> result = new ArrayList<>();
        for (int i = 0; i < getAllRuneItems().length; i++) {
            result.add(sorted.get(sorted.keySet().toArray()[i]));
        }
        return result;
    }

    private static ArrayList<RuneEffects> countedDistribution(ArrayList<RuneEffects> in, int seed) {
        Map<Float, RuneEffects> weighted = new HashMap<>();
        Random random = new Random(seed);
        for (RuneEffects re : in) {
            weighted.put(uniqueness(in, re) + (random.nextFloat() - 0.5f) / 1000, re);
        }



        TreeMap<Float, RuneEffects> sorted = new TreeMap<>(weighted);
        HashMap<SpellEffect, Integer> usages = new HashMap<>();



        ArrayList<RuneEffects> result = new ArrayList<>();
        for (int i = 0; i < getAllRuneItems().length; i++) {
            RuneEffects tmp = sorted.get(sorted.keySet().toArray()[i]);
            result.add(tmp);
            for (SpellEffect effect : tmp.getAllEffects()) {
                usages.put(effect, usages.containsKey(effect) ? usages.get(effect) + 1 : 1);
            }
            TreeMap<Float, RuneEffects> newMap = new TreeMap<>();
            for (Map.Entry<Float, RuneEffects> entry : sorted.entrySet()) {
                float newWeight = entry.getKey();
                for (SpellEffect effect : entry.getValue().getAllEffects()) {
                    if (usages.containsKey(effect)) {
                        newWeight += 1000 * Math.pow(usages.get(effect), 2);
                    }
                }
                newMap.put(newWeight, entry.getValue());
            }

            sorted = newMap;
        }
        return result;
    }


    private static int findSmallest(ArrayList<Float> weights) {
        Float min = 0f;
        int result = 0;
        for (int i = 0; i < weights.size(); i++) {
            if (i == 0 || weights.get(i) < min) {
                result = i;
            }
        }
        return result;
    }

    private static RuneItem[] getAllRuneItems() {
        if(allRuneItems != null) {
            return allRuneItems;
        }

        ArrayList<RuneItem> runes = new ArrayList<>();
        for (Item item : ForgeRegistries.ITEMS.getValues()) {
            if (item instanceof RuneItem runeItem)
                runes.add(runeItem);
        }
        allRuneItems = runes.toArray(new RuneItem[]{});
        return allRuneItems;
    }

    private static void generateJSONFiles(ArrayList<RuneEffects> list) {
        Path output_path = PATH;

        for (int i = 0; i < list.size(); i++) {
            String name = getAllRuneItems()[i].name;
            File file = new File(output_path.toString() + File.separator + File.separator + name + ".json");

            try {
                Files.createDirectories(output_path);

                file.createNewFile();
                FileWriter fw = new FileWriter(file);
                fw.write(gson.toJson(list.get(i).getToStrings()));
                fw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }





    private static Boolean[][] getCombinations(boolean useLimits, boolean addNegatives) {

        byte posCount = OVERRIDE_POSITIVE_EFFECT_COUNT == -1 ? EFFECTS_PER_RUNE / 2 : OVERRIDE_POSITIVE_EFFECT_COUNT;
        byte negCount = OVERRIDE_POSITIVE_EFFECT_COUNT == -1 ? EFFECTS_PER_RUNE / 2 : EFFECTS_PER_RUNE - OVERRIDE_POSITIVE_EFFECT_COUNT;

        new ModEffects();
        new SpellEffectRegister();
        byte EFFECT_COUNT = (byte) SpellEffectRegister.getBaseLevelEffects().size();


        ArrayList<Boolean[]> allCombinations = new ArrayList<>();

        Boolean[] combination = new Boolean[EFFECT_COUNT];

        for (int i = 0; i < Math.pow(3, EFFECT_COUNT); i++) {
            combination = flip(combination);
            if (((!repeats(allCombinations, combination)) || !useLimits) && count(combination, true) == (addNegatives ? posCount : EFFECTS_PER_RUNE) && (count(combination, false) == (addNegatives ? negCount : 0)) && (hasEffectsAndModes(combination, addNegatives))) {
                allCombinations.add(combination.clone());
            }

        }

        Boolean[][] result = new Boolean[allCombinations.size()][];
        for (int i = 0; i < allCombinations.size(); i++) {
            result[i] = allCombinations.get(i);
        }
        System.out.println("\tavailable combinations: " + result.length);
        System.out.println("\tused combinations: " + getAllRuneItems().length);
        return result;
    }


    private static Boolean[] flip(Boolean[] in) {
        Boolean[] tmp = in;
        for (int i = 0; i < in.length; i++) {
            if (in[i] == null) {
                tmp[i] = false;
                break;
            }
            else if (in[i] == false) {
                tmp[i] = true;
                break;
            }
            else if (in[i] == true) {
                tmp[i] = null;
            }
        }
        return tmp;
    }

    private static boolean repeats(ArrayList<Boolean[]> list, Boolean[] value) {
        for (Boolean[] b : list) {
            boolean trueMatch = true;
            boolean falseMatch = true;
            for (int i = 0; i < b.length; i++) {
                if (b[i] == Boolean.TRUE && b[i] != value[i]) {
                    trueMatch = false;
                }
                if (b[i] == Boolean.FALSE && b[i] != value[i]) {
                    falseMatch = false;
                }
            }

            if (trueMatch || falseMatch) {
                return true;
            }
        }
        return false;
    }

    private static float uniqueness(ArrayList<RuneEffects> list, RuneEffects value) {
        float matches = 0;
        for (RuneEffects re : list) {
            int repetitions = 0;
            for (int i = 0; i < re.positiveEffects.length; i++) {
                if (re.positiveEffects[i].equals(value.positiveEffects[i])) {
                    repetitions++;
                }
            }
            for (int i = 0; i < re.negativeEffects.length; i++) {
                if (re.negativeEffects[i].equals(value.negativeEffects[i])) {
                    repetitions++;
                }
            }

            matches += (float)repetitions / (float)(list.size());
        }

        return matches;
    }



    private static boolean hasEffectsAndModes(Boolean[] values, boolean addNegatives) {
        byte minCount = 1;
        byte posEffects = 0;
        byte posModes = 0;
        byte negEffects = 0;
        byte negModes = 0;
        for (int i = 0; i < values.length; i++) {
            if (i < values.length / 2) {
                if (values[i] == Boolean.TRUE) {
                    //Positive
                    posEffects++;
                } else if (values[i] == Boolean.FALSE) {
                    //Negative
                    negEffects++;
                }
            } else {
                if (values[i] == Boolean.TRUE) {
                    //Positive
                    posModes++;
                } else if (values[i] == Boolean.FALSE) {
                    //Negative
                    negModes++;
                }
            }
        }
        return addNegatives && EFFECTS_PER_RUNE < 4 ? (posEffects == 1 && negModes == 1) || (posModes == 1 && negEffects == 1) : (posEffects >= minCount && posModes >= minCount && ((negEffects >= minCount && negModes >= minCount) || !addNegatives));
    }

    private static int count(Boolean[] array, Boolean value) {
        int count = 0;
        for (Boolean b : array) {
            if (b == value)
                count++;
        }
        return count;
    }

    private static long factorial(byte n) {
        long fact = 1;
        for (long i = 2; i <= n; i++) {
            fact = fact * i;
        }
        return fact;
    }


    private static RuneEffects convertToEffects(Boolean [] in) {
        ArrayList<SpellEffect>[] result = new ArrayList[2];
        result[0] = new ArrayList<>();
        result[1] = new ArrayList<>();
        for (int i = 0; i < in.length; i++) {
            if (in[i] == null)
                continue;
            if (in[i] == true) {
                result[0].add(SpellEffectRegister.getBaseLevelEffects().get(i).get());
            }
            if (in[i] == false) {
                result[1].add(SpellEffectRegister.getBaseLevelEffects().get(i).get());
            }
        }

        SpellEffect[][] arrayResult = new SpellEffect[2][];
        arrayResult[0] = result[0].toArray(new SpellEffect[]{});
        arrayResult[1] = result[1].toArray(new SpellEffect[]{});
        RuneEffects re = new RuneEffects(arrayResult[0], arrayResult[1]);
        return re;
    }


    public static RuneEffects getEffectsFromJSON(String name) {
        Path path = PATH;
        File file = new File(path.toString() + File.separator + name + ".json");
        if (!file.exists()) {
            return new RuneEffects(new SpellEffect[]{}, new SpellEffect[]{});
        }
        String content = "";
        try {
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                content += fileScanner.nextLine();
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        RuneEffects result = gson.fromJson(content, RuneEffects.class).getFromStrings();

        return result;
    }


    public static int[] countEffectsArray(ArrayList<RuneEffects> runes) {
        int[] counts = new int[SpellEffectRegister.EFFECTS.size()];
        for (RuneEffects re : runes) {
            for (SpellEffect effect : re.positiveEffects) {
                int index = SpellEffectRegister.getByEffect(effect);
                counts[index]++;
            }
            for (SpellEffect effect : re.negativeEffects) {
                int index = SpellEffectRegister.getByEffect(effect);
                counts[index]++;
            }
        }

        return counts;
    }

    public static int[] countEffects(RuneEffects rune) {
        int[] counts = new int[SpellEffectRegister.EFFECTS.size()];
        for (SpellEffect effect : rune.positiveEffects) {
            int index = SpellEffectRegister.EFFECTS.indexOf(effect);
            counts[index]++;
        }
        for (SpellEffect effect : rune.negativeEffects) {
            int index = SpellEffectRegister.EFFECTS.indexOf(effect);
            counts[index]++;
        }

        return counts;
    }
}