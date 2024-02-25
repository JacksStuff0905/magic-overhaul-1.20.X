package net.jacksstuff.magicoverhaul.datagen;

import net.jacksstuff.magicoverhaul.MagicOverhaul;
import net.jacksstuff.magicoverhaul.item.ModItems;
import net.jacksstuff.magicoverhaul.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_, CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, MagicOverhaul.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ModTags.Items.RUNES)
                .add(ModItems.RUNE_ACNAR.get())
                .add(ModItems.RUNE_BRIMVYORA.get())
                .add(ModItems.RUNE_CASCARIS.get())
                .add(ModItems.RUNE_DUSPHOR.get())
                .add(ModItems.RUNE_ETHELUX.get())
                .add(ModItems.RUNE_FAERD.get())
        ;
    }
}
