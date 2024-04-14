package com.jacksstuff.magic_overhaul.screen;

import com.jacksstuff.magic_overhaul.MagicOverhaul;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, MagicOverhaul.MOD_ID);

    public static final RegistryObject<MenuType<RuneInscriberMenu>> RUNE_INSCRIBER_MENU =
            registerMenuType("rune_inscriber_menu", RuneInscriberMenu::new);

    public static final RegistryObject<MenuType<SpellCastMenu>> SPELL_CAST_MENU =
            registerMenuType("spell_cast_menu", SpellCastMenu::new);


    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }


    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
