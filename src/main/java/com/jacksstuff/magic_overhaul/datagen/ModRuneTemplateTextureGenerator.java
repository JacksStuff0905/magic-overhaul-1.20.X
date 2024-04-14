package com.jacksstuff.magic_overhaul.datagen;


import com.jacksstuff.magic_overhaul.MagicOverhaul;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;


public class ModRuneTemplateTextureGenerator {

    //String generated_directory = ".." + File.separator + Path.of("src", "generated", "resources", "assets", MagicOverhaul.MOD_ID, "textures", "item").toString() + File.separator;

    static final int IMAGE_SIZE = 16;
    private static int NEW_COLOR = 0x6b2c5b;

    public ModRuneTemplateTextureGenerator(String rune_template_background_file_name) {
        mergeRuneTemplateFiles(rune_template_background_file_name);
    }


    public void mergeRuneTemplateFiles(String rune_background_file_name) {
        String path = ".." + File.separator + Path.of("src", "main", "resources", "assets", MagicOverhaul.MOD_ID, "textures", "item", "runes").toString() + File.separator;
        String output_path = ".." + File.separator + Path.of("src", "main", "resources", "assets", MagicOverhaul.MOD_ID, "textures", "item").toString() + File.separator;
        String rune_background_path = ".." + File.separator + Path.of("src", "main", "resources", "assets", MagicOverhaul.MOD_ID, "textures", "item", rune_background_file_name).toString();
        File[] allFiles = new File(path).listFiles();
        for (File f : allFiles)
        {
            try {
                BufferedImage rune_background = ImageIO.read(new File(rune_background_path));
                BufferedImage rune_symbol = ImageIO.read(f);


                BufferedImage edited_symbol = Mask(rune_symbol, NEW_COLOR);

                BufferedImage combined = new BufferedImage(IMAGE_SIZE, IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB);

                // paint both images, preserving the alpha channels
                Graphics g = combined.getGraphics();
                g.drawImage(rune_background, 0, 0, null);
                g.drawImage(edited_symbol, 0, 1, null);

                ImageIO.write(combined, "PNG", new File(output_path + "rune_template_" + f.getName()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }



    private static BufferedImage Mask(BufferedImage image, int newColor) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                if (image.getRaster().getPixel(x, y, new int[4])[3] > 0) {
                    Color color = new Color(newColor);
                    result.getRaster().setPixel(x, y, new int[] {color.getRed(), color.getGreen(), color.getBlue(), 255});
                } else {
                    result.getRaster().setPixel(x, y, new int[4]);
                }
            }
        }

        return result;
    }



    static int Truncate(int value) {

        if (value < 0) {
            value = 0;
        }
        else if (value > 255) {
            value = 255;
        }
        return value;
    }

    static float Average(float[] input) {
        float return_value = 0;
        for (float f : input) {
            return_value += f;
        }
        return return_value / input.length;
    }

    static float Lerp(float a, float b, float f) {
        return a + f * (b - a);
    }


    static float[] getAverageColor(BufferedImage image) {
        float[] sum = new float[4];
        int count = 0;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                if (image.getRaster().getPixel(x, y, new float[4])[3] != 0) {
                    sum[0] += image.getRaster().getPixel(x, y, new float[4])[0];
                    sum[1] += image.getRaster().getPixel(x, y, new float[4])[1];
                    sum[2] += image.getRaster().getPixel(x, y, new float[4])[2];
                    sum[3] += image.getRaster().getPixel(x, y, new float[4])[3];
                    count++;
                }
            }
        }

        sum[0] /= count;
        sum[1] /= count;
        sum[2] /= count;
        sum[3] /= count;
        return sum;
    }
}
