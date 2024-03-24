package com.jacksstuff.magicoverhaul.spell.util;

import com.jacksstuff.magicoverhaul.spell.ModEffects;
import com.jacksstuff.magicoverhaul.spell.util.register.SpellEffectRegister;
import com.jacksstuff.magicoverhaul.spell.util.register.SpellEffectRegistryObject;
import net.minecraft.network.chat.Component;

import java.util.HashMap;

public abstract class SpellEffect {


    public EffectType TYPE;

    public HashMap<SpellEffect, SpellEffect> LEVEL_UPS = new HashMap<>();

    private Boolean isBaseLevel = null;

    public boolean isBaseLevel() {
        if (isBaseLevel == null) {
            this.init();
            for (SpellEffectRegistryObject ro : SpellEffectRegister.EFFECTS) {
                if (ro.get().LEVEL_UPS.values().contains(this)) {
                    isBaseLevel = Boolean.FALSE;
                    return isBaseLevel.booleanValue();
                }
            }
            isBaseLevel = Boolean.TRUE;
        }
        return isBaseLevel.booleanValue();
    }

    public enum EffectType {
        EFFECT,
        MODE
    }

    public void init(){}

    


    public String getName() {
        return this.getClass().getName().split("\\.")[this.getClass().getName().split("\\.").length - 1].toLowerCase();
    }

    public Component getComponentName() {
        return Component.translatable("spell_effect.magic_overhaul." + this.getName() + ".name");
    }

    public String getDisplayName() {
        return this.getComponentName().getString();
    }



    @Override
    public String toString() {
        return "Effect: " + this.getName().toUpperCase() + ", type: " + this.TYPE.toString();
    }

    @Override
    public boolean equals(Object obj) {
        try {
            SpellEffect other = (SpellEffect) obj;
            return this.getName().equals(other.getName()) && this.TYPE.equals(other.TYPE);
        }
        catch (Exception e) {
            return false;
        }

    }


    public void addLevelUp(SpellEffect other, SpellEffect result) {

        this.LEVEL_UPS.put(other, result);
        for (SpellEffect key : other.LEVEL_UPS.keySet()) {
            if (key.equals(this)) {
                return;
            }
        }
        other.LEVEL_UPS.put(this, result);
    }
}
