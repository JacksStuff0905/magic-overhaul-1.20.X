package net.jacksstuff.magicoverhaul.datagen;




import net.jacksstuff.magicoverhaul.MagicOverhaul;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import java.nio.file.Path;


public class ModRuneTextureGenerator {

    //String generated_directory = ".." + File.separator + Path.of("src", "generated", "resources", "assets", MagicOverhaul.MOD_ID, "textures", "item").toString() + File.separator;

    static final int IMAGE_SIZE = 16;

    public ModRuneTextureGenerator () {
        mergeRuneFiles();
    }


    public void mergeRuneFiles() {
        String path = ".." + File.separator + Path.of("src", "main", "resources", "assets", MagicOverhaul.MOD_ID, "textures", "item", "runes").toString() + File.separator;
        String output_path = ".." + File.separator + Path.of("src", "main", "resources", "assets", MagicOverhaul.MOD_ID, "textures", "item").toString() + File.separator;
        String rune_background_path = ".." + File.separator + Path.of("src", "main", "resources", "assets", MagicOverhaul.MOD_ID, "textures", "item", "rune.png").toString();
        System.out.println(path);
        File[] allFiles = new File(path).listFiles();
        for (File f : allFiles)
        {
            System.out.println("file: " + f.getPath());

            try {
                BufferedImage rune_background = ImageIO.read(new File(rune_background_path));
                BufferedImage rune_symbol = ImageIO.read(f);


                BufferedImage combined = new BufferedImage(IMAGE_SIZE, IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB);

                // paint both images, preserving the alpha channels
                Graphics g = combined.getGraphics();
                g.drawImage(rune_background, 0, 0, null);
                g.drawImage(rune_symbol, 0, 0, null);

                ImageIO.write(combined, "PNG", new File(output_path + f.getName()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
