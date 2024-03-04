package com.jacksstuff.magicoverhaul.block.entity;

import com.jacksstuff.magicoverhaul.MagicOverhaul;
import com.jacksstuff.magicoverhaul.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MagicOverhaul.MOD_ID);

    public static final RegistryObject<BlockEntityType<RuneExtractorBlockEntity>> RUNE_EXTRACTOR_BE =
            BLOCK_ENTITIES.register("rune_extractor_be", () -> BlockEntityType.Builder.of(RuneExtractorBlockEntity::new,
                    ModBlocks.RUNE_EXTRACTOR.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
