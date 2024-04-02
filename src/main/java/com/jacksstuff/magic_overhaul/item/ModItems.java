package com.jacksstuff.magic_overhaul.item;

import com.jacksstuff.magic_overhaul.MagicOverhaul;
import com.jacksstuff.magic_overhaul.item.custom.AbstractWandItem;
import com.jacksstuff.magic_overhaul.item.custom.DebugWandItem;
import com.jacksstuff.magic_overhaul.item.custom.MetalDetectorItem;
import com.jacksstuff.magic_overhaul.item.custom.RuneItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModItems {


    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MagicOverhaul.MOD_ID);




    public static final RegistryObject<Item> DEBUG_WAND = ITEMS.register("debug_wand",
            () -> new DebugWandItem(new Item.Properties()));

    public static final RegistryObject<Item> METAL_DETECTOR = ITEMS.register("metal_detector",
            () -> new MetalDetectorItem(new Item.Properties().durability(100)));


    //Runes

    public static final RegistryObject<Item> RUNE_ACNAR = ITEMS.register("rune_acnar", () -> new RuneItem("rune_acnar", new Item.Properties()));
    public static final RegistryObject<Item> RUNE_BRIMVYORA = ITEMS.register("rune_brimvyora", () -> new RuneItem("rune_brimvyora", new Item.Properties()));
    public static final RegistryObject<Item> RUNE_CASCARIS = ITEMS.register("rune_cascaris", () -> new RuneItem("rune_cascaris", new Item.Properties()));
    public static final RegistryObject<Item> RUNE_DUSPHOR = ITEMS.register("rune_dusphor", () -> new RuneItem("rune_dusphor", new Item.Properties()));
    public static final RegistryObject<Item> RUNE_ETHELUX = ITEMS.register("rune_ethelux", () -> new RuneItem("rune_ethelux", new Item.Properties()));
    public static final RegistryObject<Item> RUNE_FAERD = ITEMS.register("rune_faerd", () -> new RuneItem("rune_faerd", new Item.Properties()));
    public static final RegistryObject<Item> RUNE_GALTHARA = ITEMS.register("rune_galthara", () -> new RuneItem("rune_galthara", new Item.Properties()));
    public static final RegistryObject<Item> RUNE_HUXIS = ITEMS.register("rune_huxis", () -> new RuneItem("rune_huxis", new Item.Properties()));
    public static final RegistryObject<Item> RUNE_INQYORE = ITEMS.register("rune_inqyore", () -> new RuneItem("rune_inqyore", new Item.Properties()));
    public static final RegistryObject<Item> RUNE_JURNIX = ITEMS.register("rune_jurnix", () -> new RuneItem("rune_jurnix", new Item.Properties()));
    public static final RegistryObject<Item> RUNE_KYMARA = ITEMS.register("rune_kymara", () -> new RuneItem("rune_kymara", new Item.Properties()));
    public static final RegistryObject<Item> RUNE_LUXAAR = ITEMS.register("rune_luxaar", () -> new RuneItem("rune_luxaar", new Item.Properties()));
    public static final RegistryObject<Item> RUNE_MORSTROM = ITEMS.register("rune_morstrom", () -> new RuneItem("rune_morstrom", new Item.Properties()));
    public static final RegistryObject<Item> RUNE_NEXUMIS = ITEMS.register("rune_nexumis", () -> new RuneItem("rune_nexumis", new Item.Properties()));
    public static final RegistryObject<Item> RUNE_ORRED = ITEMS.register("rune_orred", () -> new RuneItem("rune_orred", new Item.Properties()));
    public static final RegistryObject<Item> RUNE_PARSINEA = ITEMS.register("rune_parsinea", () -> new RuneItem("rune_parsinea", new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }



    public static void forEachRune(Consumer<RuneItem> function) {
        for (Item item : ForgeRegistries.ITEMS) {
            if (item instanceof RuneItem runeItem) {
                function.accept(runeItem);
            }
        }
    }


    public static void forEachWand(Consumer<AbstractWandItem> function) {
        for (Item item : ForgeRegistries.ITEMS) {
            if (item instanceof AbstractWandItem wandItem) {
                function.accept(wandItem);
            }
        }
    }
}
