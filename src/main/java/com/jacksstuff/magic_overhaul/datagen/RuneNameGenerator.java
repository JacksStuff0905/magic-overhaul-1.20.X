package com.jacksstuff.magic_overhaul.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jacksstuff.magic_overhaul.MagicOverhaul;
import com.jacksstuff.magic_overhaul.item.ModItems;
import com.jacksstuff.magic_overhaul.item.custom.RuneItem;
import net.minecraft.network.chat.Component;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RuneNameGenerator {
    private static final Path LANG_FOLDER_PATH = Path.of("..","src", "main", "resources", "assets", MagicOverhaul.MOD_ID, "lang");
    private static final String RUNE_ENTRY = "item." + MagicOverhaul.MOD_ID + ".";

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();


    public RuneNameGenerator() {
        generateRuneTranslations();
    }


    public void generateRuneTranslations() {

        HashMap<String, String> tmp = new HashMap<>(Map.of("abcd", "efgh", "ijkl", "1234"));
        System.out.println("jsoned: " + gson.toJson(tmp));

        generateTranslation("en_us");
    }



    public void generateTranslation(String language) {
        ArrayList<RuneItem> runes = new ArrayList<>();
        ModItems.forEachRune(runes::add);
        System.out.println("path: " + LANG_FOLDER_PATH.toString() + File.separator + language + ".json");
        File file = new File(LANG_FOLDER_PATH.toString() + File.separator + language + ".json");

        String content = "";
        ArrayList<String> lines = new ArrayList<>();
        HashMap<String, String> hashMap = new HashMap<>();
        try {
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                content += line;
                if (line == null || line.equals("")) {
                    lines.add("");
                    continue;
                }

                if (line.toCharArray()[0] == '{') {
                    if (line.length() > 1)
                        lines.add(line.substring(1));
                    continue;
                }
                if (line.toCharArray()[line.length() - 1] == '}') {
                    if (line.length() > 1)
                        lines.add(line.substring(0, line.length() - 1));
                    continue;
                }

                lines.add(line);

            }
            fileScanner.close();
            hashMap = gson.fromJson(content, HashMap.class);
            int suffixLineIndex = 0;
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).contains(RUNE_ENTRY + "rune.suffix")) {
                    suffixLineIndex = i;
                }
            }



            OUTER_LOOP : for (int i = 0; i < runes.size(); i++) {
                String name = runes.get(i).name.replaceAll("rune_", "");
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                String entryKey = "  \"" + RUNE_ENTRY + runes.get(i).name + "\":\"";
                String entry = entryKey + hashMap.get(RUNE_ENTRY + "rune.prefix") + name + hashMap.get(RUNE_ENTRY + "rune.suffix") + "\"";
                for (String line : lines) {
                    if (line.startsWith(entryKey)) {
                        continue OUTER_LOOP;
                    }
                }
                lines.add(suffixLineIndex + i + 1, entry);
            }

            System.out.println("stored: " + ", " + content);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            FileWriter writer = new FileWriter(file);

            String string = "{\n";
            for (int i = 0; i < lines.size(); i++) {
                string += lines.get(i) == null ? "" : lines.get(i) + (i == lines.size() - 1 || lines.get(i).lastIndexOf(',') == lines.get(i).length() - 1 ? "" : ",") + "\n";
            }
            writer.write(string + "}");

            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
