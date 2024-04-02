package com.jacksstuff.magic_overhaul.spell.util;

import com.jacksstuff.magic_overhaul.spell.util.register.SpellEffectRegister;
import com.jacksstuff.magic_overhaul.spell.util.register.SpellEffectRegistryObject;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;

public abstract class SpellEffect {


    public SpellEffect() {}
    private HashMap<SpellEffect, SpellEffect> levelUps = new HashMap<>();

    public abstract EffectType getType();

    public final HashMap<SpellEffect, SpellEffect> getLevelUps() {
        return levelUps;
    }

    private Boolean isBaseLevel = null;

    public boolean isBaseLevel() {
        if (isBaseLevel == null) {
            this.init();
            for (SpellEffectRegistryObject ro : SpellEffectRegister.EFFECTS) {
                if (ro.get().getLevelUps().values().contains(this)) {
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


    public void Cast(int level, Player player) {
        player.sendSystemMessage(Component.literal("Effect cast: " + this.getName() + "; at level: " + level));
    }

    


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
        return "Effect: " + this.getName().toUpperCase() + ", type: " + this.getType().toString();
    }

    @Override
    public boolean equals(Object obj) {
        try {
            SpellEffect other = (SpellEffect) obj;
            return this.getName().equals(other.getName()) && this.getType().equals(other.getType());
        }
        catch (Exception e) {
            return false;
        }

    }


    public void addLevelUp(SpellEffect other, SpellEffect result) {
        if (this.levelUps == null)
            this.levelUps = new HashMap<>();
        if (other.levelUps == null)
            other.levelUps = new HashMap<>();

        this.levelUps.put(other, result);
        for (SpellEffect key : other.levelUps.keySet()) {
            if (key.equals(this)) {
                return;
            }
        }
        other.levelUps.put(this, result);
    }
}
