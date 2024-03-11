package com.jacksstuff.magicoverhaul.item;

import com.jacksstuff.magicoverhaul.MagicOverhaul;
import com.jacksstuff.magicoverhaul.item.custom.DebugWandItem;
import com.jacksstuff.magicoverhaul.item.custom.MetalDetectorItem;
import com.jacksstuff.magicoverhaul.item.custom.RuneItem;
import com.jacksstuff.magicoverhaul.spell.ModEffects;
import com.jacksstuff.magicoverhaul.spell.util.Effect;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {


    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MagicOverhaul.MOD_ID);




    public static final RegistryObject<Item> DEBUG_WAND = ITEMS.register("debug_wand", () -> new DebugWandItem(new Item.Properties()));

    public static final RegistryObject<Item> METAL_DETECTOR = ITEMS.register("metal_detector", () -> new MetalDetectorItem(new Item.Properties().durability(100)));


    //Runes

    public static final RegistryObject<Item> RUNE_ACNAR = ITEMS.register("rune_acnar", () -> new RuneItem(new Item.Properties(),
            new Effect[]{ModEffects.FIRE, ModEffects.RAY}, new Effect[]{ModEffects.STORM, ModEffects.THUNDER, ModEffects.AIR}));
    public static final RegistryObject<Item> RUNE_BRIMVYORA = ITEMS.register("rune_brimvyora", () -> new RuneItem(new Item.Properties(),
            new Effect[]{ModEffects.AIR, ModEffects.FIRE, ModEffects.CHARGE}, new Effect[]{ModEffects.EARTH, ModEffects.THUNDER, ModEffects.RAY}));
    public static final RegistryObject<Item> RUNE_CASCARIS = ITEMS.register("rune_cascaris", () -> new RuneItem(new Item.Properties(),
            new Effect[]{ModEffects.CHARGE, ModEffects.EARTH, ModEffects.THUNDER}, new Effect[]{ModEffects.ICE, ModEffects.WAVE}));
    public static final RegistryObject<Item> RUNE_DUSPHOR = ITEMS.register("rune_dusphor", () -> new RuneItem(new Item.Properties()));
    public static final RegistryObject<Item> RUNE_ETHELUX = ITEMS.register("rune_ethelux", () -> new RuneItem(new Item.Properties()));
    public static final RegistryObject<Item> RUNE_FAERD = ITEMS.register("rune_faerd", () -> new RuneItem(new Item.Properties()));
    public static final RegistryObject<Item> RUNE_GALTHARA = ITEMS.register("rune_galthara", () -> new RuneItem(new Item.Properties()));
    public static final RegistryObject<Item> RUNE_HUXIS = ITEMS.register("rune_huxis", () -> new RuneItem(new Item.Properties()));
    public static final RegistryObject<Item> RUNE_INQYORE = ITEMS.register("rune_inqyore", () -> new RuneItem(new Item.Properties()));
    public static final RegistryObject<Item> RUNE_JURNIX = ITEMS.register("rune_jurnix", () -> new RuneItem(new Item.Properties()));
    public static final RegistryObject<Item> RUNE_KYMARA = ITEMS.register("rune_kymara", () -> new RuneItem(new Item.Properties()));
    public static final RegistryObject<Item> RUNE_LUXAAR = ITEMS.register("rune_luxaar", () -> new RuneItem(new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);

    }
}
