package com.jacksstuff.magicoverhaul.screen;

import com.jacksstuff.magicoverhaul.block.ModBlocks;
import com.jacksstuff.magicoverhaul.block.entity.RuneExtractorBlockEntity;
import com.jacksstuff.magicoverhaul.item.ModItems;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RuneExtractorMenu extends AbstractContainerMenu {
    private final RuneExtractorBlockEntity blockEntity;
    private final ContainerLevelAccess levelAccess;

    private static final int INPUT_SLOT = 0;
    private static final int[] OUTPUT_SLOTS = new int[] {1, 2, 3};

    private ArrayList<ItemStack> stored = new ArrayList<>();



    //Client Constructor
    public RuneExtractorMenu (int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    //Server Constructor
    public RuneExtractorMenu (int pContainerId, Inventory inv, BlockEntity entity) {
        super(ModMenuTypes.RUNE_EXTRACTOR_MENU.get(), pContainerId);
        if (entity instanceof RuneExtractorBlockEntity be) {
            this.blockEntity = be;
        }
        else {
            throw new IllegalStateException("Incorrect block entity class (%s) passed into RuneExtractorMenu".formatted(entity.getClass().getCanonicalName()));
        }

        this.levelAccess = ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos());

        createPlayerHotbar(inv);
        createPlayerInventory(inv);
        createBlockEntityInventory(blockEntity);
    }





    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot fromSlot = getSlot(pIndex);
        ItemStack fromStack = fromSlot.getItem();

        if(fromStack.getCount() <= 0)
            fromSlot.set(ItemStack.EMPTY);

        if(!fromSlot.hasItem())
            return ItemStack.EMPTY;

        ItemStack copyFromStack = fromStack.copy();

        if(pIndex < 36) {
            // We are inside of the player's inventory
            if(!moveItemStackTo(fromStack, 36, 37, false))
                return ItemStack.EMPTY;
        } else if (pIndex < 63) {
            // We are inside of the block entity inventory
            if(!moveItemStackTo(fromStack, 0, 36, false))
                return ItemStack.EMPTY;
        } else {
            System.err.println("Invalid slot index: " + pIndex);
            return ItemStack.EMPTY;
        }

        fromSlot.setChanged();
        fromSlot.onTake(playerIn, fromStack);

        return copyFromStack;
    }

    private boolean isEmpty() {
        boolean out = true;
        for (int i = 0; i < OUTPUT_SLOTS.length; i++) {
            out &= blockEntity.getInventory().getStackInSlot(OUTPUT_SLOTS[i]).isEmpty();
        }
        if (out) {
            isCrafted = false;
        }
        return out;
    }

    private boolean isCrafted = false;

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.levelAccess, player, ModBlocks.RUNE_EXTRACTOR.get());
    }


    public RuneExtractorBlockEntity getBlockEntity() {
        return blockEntity;
    }

    private void suggestCraft() {
        blockEntity.getInventory().setStackInSlot(OUTPUT_SLOTS[0], new ItemStack(ModItems.RUNE_ACNAR.get()));
        blockEntity.getInventory().setStackInSlot(OUTPUT_SLOTS[1], new ItemStack(ModItems.RUNE_BRIMVYORA.get()));
        blockEntity.getInventory().setStackInSlot(OUTPUT_SLOTS[2], new ItemStack(ModItems.RUNE_CASCARIS.get()));
    }

    private void Craft() {
        blockEntity.getInventory().setStackInSlot(INPUT_SLOT, new ItemStack(Items.AIR));
        isCrafted = true;
    }



    private void unsuggestCraft() {
        blockEntity.getInventory().setStackInSlot(OUTPUT_SLOTS[0], new ItemStack(Items.AIR));
        blockEntity.getInventory().setStackInSlot(OUTPUT_SLOTS[1], new ItemStack(Items.AIR));
        blockEntity.getInventory().setStackInSlot(OUTPUT_SLOTS[2], new ItemStack(Items.AIR));
    }

    @Override
    public void removed(Player pPlayer) {
        clear(pPlayer, blockEntity.getInventory().getStackInSlot(INPUT_SLOT));
        isEmpty();
        if (isCrafted) {
            clear(pPlayer, blockEntity.getInventory().getStackInSlot(OUTPUT_SLOTS[0]));
            clear(pPlayer, blockEntity.getInventory().getStackInSlot(OUTPUT_SLOTS[1]));
            clear(pPlayer, blockEntity.getInventory().getStackInSlot(OUTPUT_SLOTS[2]));
        }
        unsuggestCraft();

        super.removed(pPlayer);
    }


    private void clear(Player pPlayer, ItemStack stack) {
        if (pPlayer instanceof ServerPlayer) {
            if (!stack.isEmpty()) {
                if (pPlayer.isAlive() && !((ServerPlayer) pPlayer).hasDisconnected()) {
                    pPlayer.getInventory().placeItemBackInInventory(stack);
                } else {
                    pPlayer.drop(stack, false);
                }
                stored.clear();
            }
        }
    }



    private void createBlockEntityInventory(RuneExtractorBlockEntity be) {
        be.getOptional().ifPresent(inventory -> {
            /*for (int row = 0; row < 3; row++) {
                for (int column = 0; column < 9; column++) {
                    this.addSlot(new SlotItemHandler(inventory, column + row * 9, 8 + column * 18, 18 + row * 18) {
                        @Override
                        public boolean mayPlace(@NotNull ItemStack stack) {
                            return stack.is(ModTags.Items.RUNES);
                        }
                    });
                }
            }*/

            this.addSlot(new SlotItemHandler(inventory, INPUT_SLOT, 80, 36) {
                @Override
                public void setChanged() {
                    if (getItem().is(Items.FIRE_CHARGE)) {
                        suggestCraft();
                    }
                    else {
                        unsuggestCraft();
                    }
                    super.setChanged();
                }


                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {


                    if (!isEmpty()) {
                        return true;
                    }

                    return super.mayPlace(stack);
                }


                @Override
                public int getMaxStackSize() {
                    return 1;
                }

                @Override
                public int getMaxStackSize(@NotNull ItemStack stack) {
                    return 1;
                }
            });

            this.addSlot(new RuneOutputSlot(inventory, OUTPUT_SLOTS[0], 80, 5));
            this.addSlot(new RuneOutputSlot(inventory, OUTPUT_SLOTS[1], 107, 59));
            this.addSlot(new RuneOutputSlot(inventory, OUTPUT_SLOTS[2], 53, 59));
        });
    }

    private void createPlayerInventory (Inventory playerInventory) {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                this.addSlot(new Slot(playerInventory, 9 + column + row * 9, 8 + column * 18, 84 + row * 18));
            }
        }
    }


    private void createPlayerHotbar (Inventory playerInventory) {
        for (int column = 0; column < 9; column++) {
            this.addSlot(new Slot(playerInventory, column, 8 + column * 18, 142));
        }
    }


    public class CircularSlot extends SlotItemHandler {

        public CircularSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public boolean isHighlightable() {
            return false;
        }

        @Override
        public boolean mayPlace(@NotNull ItemStack stack) {
            return false;
        }


    }

    public class RuneOutputSlot extends CircularSlot {

        public RuneOutputSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public void setChanged() {
            Craft();
            super.setChanged();
        }

        @Override
        public void onTake(Player pPlayer, ItemStack pStack) {
            super.onTake(pPlayer, pStack);
        }
    }
}
