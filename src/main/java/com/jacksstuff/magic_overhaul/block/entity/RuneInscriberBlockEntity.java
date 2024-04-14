package com.jacksstuff.magic_overhaul.block.entity;

import com.jacksstuff.magic_overhaul.MagicOverhaul;
import com.jacksstuff.magic_overhaul.screen.RuneInscriberMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class RuneInscriberBlockEntity extends BlockEntity implements MenuProvider {

    public static final Component TITLE = Component.translatable("container." + MagicOverhaul.MOD_ID + ".rune_inscriber");

    private final ItemStackHandler inventory = new ItemStackHandler(27) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            RuneInscriberBlockEntity.this.setChanged();
        }
    };

    private final LazyOptional<IItemHandler> optional = LazyOptional.of(() -> this.inventory);



    public RuneInscriberBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.RUNE_INSCRIBER_BE.get(), pPos, pBlockState);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        CompoundTag magicOverhaulData = nbt.getCompound(MagicOverhaul.MOD_ID);

        this.inventory.deserializeNBT(magicOverhaulData.getCompound("Inventory"));
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        CompoundTag magicOverhaulData = nbt.getCompound(MagicOverhaul.MOD_ID);
        magicOverhaulData.put("Inventory", this.inventory.serializeNBT());


        nbt.put(MagicOverhaul.MOD_ID, magicOverhaulData);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return this.optional.cast();
        }
        return super.getCapability(cap);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.optional.invalidate();
    }

    public ItemStackHandler getInventory() {
        return this.inventory;
    }

    public LazyOptional<IItemHandler> getOptional() {
        return this.optional;
    }


    @Override
    public Component getDisplayName() {
        return TITLE;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new RuneInscriberMenu(i, inventory, this);
    }

}
