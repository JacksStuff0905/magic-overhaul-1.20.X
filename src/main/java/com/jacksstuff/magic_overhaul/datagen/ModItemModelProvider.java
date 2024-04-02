package com.jacksstuff.magic_overhaul.datagen;

import com.jacksstuff.magic_overhaul.MagicOverhaul;
import com.jacksstuff.magic_overhaul.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MagicOverhaul.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        handheldItem(ModItems.DEBUG_WAND);
        simpleItem(ModItems.METAL_DETECTOR);

        //Runes
        simpleItem(ModItems.RUNE_ACNAR);
        simpleItem(ModItems.RUNE_BRIMVYORA);
        simpleItem(ModItems.RUNE_CASCARIS);
        simpleItem(ModItems.RUNE_DUSPHOR);
        simpleItem(ModItems.RUNE_ETHELUX);
        simpleItem(ModItems.RUNE_FAERD);
        simpleItem(ModItems.RUNE_GALTHARA);
        simpleItem(ModItems.RUNE_HUXIS);
        simpleItem(ModItems.RUNE_INQYORE);
        simpleItem(ModItems.RUNE_JURNIX);
        simpleItem(ModItems.RUNE_KYMARA);
        simpleItem(ModItems.RUNE_LUXAAR);
        simpleItem(ModItems.RUNE_MORSTROM);
        simpleItem(ModItems.RUNE_NEXUMIS);
        simpleItem(ModItems.RUNE_ORRED);
        simpleItem(ModItems.RUNE_PARSINEA);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(MagicOverhaul.MOD_ID, "item/" + item.getId().getPath()));
    }


    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(MagicOverhaul.MOD_ID, "item/" + item.getId().getPath()));
    }


}
