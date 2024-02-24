package net.jacksstuff.magicoverhaul.util;

import net.jacksstuff.magicoverhaul.MagicOverhaul;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {

    public static class Blocks {
        public static final TagKey<Block> METAL_DETECTOR_VALUABLES = tag("metal_detector_valuables");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(MagicOverhaul.MOD_ID, name));
        }
    }

    public static class Items {

        public static final TagKey<Item> RUNES = tag("runes");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(MagicOverhaul.MOD_ID, name));
        }
    }
}
