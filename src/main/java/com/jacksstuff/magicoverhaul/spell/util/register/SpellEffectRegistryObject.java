package com.jacksstuff.magicoverhaul.spell.util.register;

import com.jacksstuff.magicoverhaul.spell.util.SpellEffect;

import java.lang.reflect.InvocationTargetException;

public class SpellEffectRegistryObject<T extends SpellEffect> {
    private T effect;


    public SpellEffectRegistryObject(Class<T> classT) {
        try {
            this.effect = classT.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public SpellEffect get() {
        effect.init();
        return effect;
    }
}
