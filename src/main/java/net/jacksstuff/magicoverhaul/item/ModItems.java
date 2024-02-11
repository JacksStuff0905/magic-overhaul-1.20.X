package net.jacksstuff.magicoverhaul.item;

import net.jacksstuff.magicoverhaul.MagicOverhaul;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MagicOverhaul.MOD_ID);

    public static final RegistryObject<Item> DEBUG_WAND = ITEMS.register("debug_wand", () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
