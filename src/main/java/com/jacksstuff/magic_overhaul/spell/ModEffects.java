package com.jacksstuff.magic_overhaul.spell;

import com.jacksstuff.magic_overhaul.spell.spell_effects.effects.Void;
import com.jacksstuff.magic_overhaul.spell.spell_effects.effects.*;
import com.jacksstuff.magic_overhaul.spell.spell_effects.modes.*;
import com.jacksstuff.magic_overhaul.spell.util.register.SpellEffectRegister;
import com.jacksstuff.magic_overhaul.spell.util.register.SpellEffectRegistryObject;

public class ModEffects {
    public static final SpellEffectRegistryObject FIRE = SpellEffectRegister.register(Fire.class);
    public static final SpellEffectRegistryObject AIR = SpellEffectRegister.register(Air.class);
    public static final SpellEffectRegistryObject ICE = SpellEffectRegister.register(Ice.class);
    public static final SpellEffectRegistryObject VOID = SpellEffectRegister.register(Void.class);
    public static final SpellEffectRegistryObject EARTH = SpellEffectRegister.register(Earth.class);
    public static final SpellEffectRegistryObject THUNDER = SpellEffectRegister.register(Thunder.class);
    public static final SpellEffectRegistryObject WATER = SpellEffectRegister.register(Water.class);


    public static final SpellEffectRegistryObject CHARGE = SpellEffectRegister.register(Charge.class);
    public static final SpellEffectRegistryObject RAY = SpellEffectRegister.register(Ray.class);
    public static final SpellEffectRegistryObject DOME = SpellEffectRegister.register(Dome.class);
    public static final SpellEffectRegistryObject STORM = SpellEffectRegister.register(Storm.class);
    public static final SpellEffectRegistryObject BOLT = SpellEffectRegister.register(Bolt.class);
    public static final SpellEffectRegistryObject WAVE = SpellEffectRegister.register(Wave.class);


    static {
        SpellEffectRegister.init();
    }
}
