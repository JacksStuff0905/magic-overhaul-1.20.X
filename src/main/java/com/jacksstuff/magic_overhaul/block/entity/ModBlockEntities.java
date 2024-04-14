package com.jacksstuff.magic_overhaul.block.entity;

import com.jacksstuff.magic_overhaul.MagicOverhaul;
import com.jacksstuff.magic_overhaul.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MagicOverhaul.MOD_ID);

    public static final RegistryObject<BlockEntityType<RuneInscriberBlockEntity>> RUNE_INSCRIBER_BE =
            BLOCK_ENTITIES.register("rune_inscriber_be", () -> BlockEntityType.Builder.of(RuneInscriberBlockEntity::new,
                    ModBlocks.RUNE_INSCRIBER.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
