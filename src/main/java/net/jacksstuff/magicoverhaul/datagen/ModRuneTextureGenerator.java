package net.jacksstuff.magicoverhaul.datagen;




import net.jacksstuff.magicoverhaul.MagicOverhaul;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import java.nio.file.Path;
import java.util.ArrayList;


public class ModRuneTextureGenerator {

    //String generated_directory = ".." + File.separator + Path.of("src", "generated", "resources", "assets", MagicOverhaul.MOD_ID, "textures", "item").toString() + File.separator;

    static final int IMAGE_SIZE = 16;
    static final int IMAGE_BLUR_RADIUS = 1;
    static final float IMAGE_BLUR_OPACITY = 0.7f;

    public ModRuneTextureGenerator (String rune_background_file_name) {
        mergeRuneFiles(rune_background_file_name);
    }


    public void mergeRuneFiles(String rune_background_file_name) {
        String path = ".." + File.separator + Path.of("src", "main", "resources", "assets", MagicOverhaul.MOD_ID, "textures", "item", "runes").toString() + File.separator;
        String output_path = ".." + File.separator + Path.of("src", "main", "resources", "assets", MagicOverhaul.MOD_ID, "textures", "item").toString() + File.separator;
        String rune_background_path = ".." + File.separator + Path.of("src", "main", "resources", "assets", MagicOverhaul.MOD_ID, "textures", "item", rune_background_file_name).toString();
        File[] allFiles = new File(path).listFiles();
        for (File f : allFiles)
        {
            try {
                BufferedImage rune_background = ImageIO.read(new File(rune_background_path));
                BufferedImage rune_symbol = ImageIO.read(f);


                BufferedImage blurred_symbol = Brightness(Blur(rune_symbol, IMAGE_BLUR_RADIUS, IMAGE_BLUR_OPACITY), 50);

                BufferedImage combined = new BufferedImage(IMAGE_SIZE, IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB);

                // paint both images, preserving the alpha channels
                Graphics g = combined.getGraphics();
                g.drawImage(rune_background, 0, 0, null);
                g.drawImage(blurred_symbol, 0, 0, null);
                g.drawImage(rune_symbol, 0, 0, null);

                ImageIO.write(combined, "PNG", new File(output_path + "rune_" + f.getName()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }


    static BufferedImage Blur(BufferedImage image, int radius, float opacity){

        BufferedImage return_image = new BufferedImage(IMAGE_SIZE, IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB);

        float sigma = 1.5f;
        float[][] matrix = new float[(radius * 2 + 1)][(radius * 2 + 1)];

        for (int x = 0; x < (radius * 2 + 1); x++) {
            for (int y = 0; y < (radius * 2 + 1); y++) {
                matrix[x][y] = 1f / ((float)Math.sqrt((Math.abs(x - radius)) * Math.abs(y - radius) + 1)) * (opacity * radius * 2);
            }
        }


        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {

                ArrayList<float[]> around = new ArrayList<>();

                for (int mx = 0; mx < matrix.length; mx++) {
                    for (int my = 0; my < matrix[mx].length; my++) {
                        int offsetX = mx - radius;
                        int offsetY = my - radius;

                        if (offsetX + x >= 0 && offsetX + x < image.getWidth() && offsetY + y >= 0 && offsetY + y < image.getHeight()) {
                            int rgba[] = image.getRaster().getPixel(offsetX + x, offsetY + y, new int[4]);
                            float[] weighted = new float[4];

                            for (int i = 0; i < rgba.length; i++) {
                                weighted[i] = rgba[i] * matrix[mx][my];

                            }



                            around.add(weighted);
                        }
                        else {
                            around.add(new float[] {0, 0, 0, 0});
                        }

                    }
                }


                float[] color = new float[4];
                for (float[] f : around) {
                    color[0] += f[0];
                    color[1] += f[1];
                    color[2] += f[2];
                    color[3] += f[3];
                }
                color[0] /= around.size();
                color[1] /= around.size();
                color[2] /= around.size();
                color[3] /= around.size();

                return_image.getRaster().setPixel(x, y, new int[] {Math.round(color[0]), Math.round(color[1]), Math.round(color[2]), Math.round(color[3])});
            }
        }



        return return_image;
    }

    static BufferedImage Brightness (BufferedImage image, int brightnessValue){
        // Declaring an array for spectrum of colors
        int rgb[];
        BufferedImage return_image = image;

        // Outer loop for width of image
        for (int i = 0; i < image.getWidth(); i++) {

            // Inner loop for height of image
            for (int j = 0; j < image.getHeight(); j++) {

                rgb = image.getRaster().getPixel(i, j, new int[4]);

                // Using(calling) method 1
                int red = Truncate(rgb[0] + brightnessValue);
                int green = Truncate(rgb[1] + brightnessValue);
                int blue = Truncate(rgb[2] + brightnessValue);

                int arr[] = { red, green, blue, rgb[3] };

                // Using setPixel() method
                return_image.getRaster().setPixel(i, j, arr);
            }
        }

        return return_image;
    }

    static BufferedImage Resize (BufferedImage image, int size){

        int stored[][][] = new int[image.getWidth()][image.getHeight()][4];
        BufferedImage return_image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < image.getWidth(); x++) {

            for (int y = 0; y < image.getHeight(); y++) {

                stored[x][y] = image.getRaster().getPixel(x, y, new int[4]);



            }
        }

        int differenceX = (int)Math.floor(Math.abs(size - image.getWidth()) / 2);
        int differenceY = (int)Math.floor(Math.abs(size - image.getHeight()) / 2);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (x >= differenceX && y >= differenceY && x < differenceX + image.getWidth() && y < differenceY + image.getHeight()) {
                    return_image.getRaster().setPixel(x, y, stored[x - differenceX][y - differenceY]);
                }
                else {
                    return_image.getRaster().setPixel(x, y, new int[]{0, 0, 0, 0});
                }
            }
        }
        return return_image;
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

    static float Average(int[] input) {
        float return_value = 0;
        for (int f : input) {
            return_value += f;
        }
        return return_value / input.length;
    }

    static float Lerp(float a, float b, float f) {
        return a + f * (b - a);
    }
}
