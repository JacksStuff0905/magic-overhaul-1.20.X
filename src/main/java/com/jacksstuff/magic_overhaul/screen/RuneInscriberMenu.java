package com.jacksstuff.magic_overhaul.screen;

import com.jacksstuff.magic_overhaul.block.ModBlocks;
import com.jacksstuff.magic_overhaul.block.entity.RuneInscriberBlockEntity;
import com.jacksstuff.magic_overhaul.recipe.ModRecipes;
import com.jacksstuff.magic_overhaul.recipe.RuneInscribingRecipe;
import com.jacksstuff.magic_overhaul.util.ModTags;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.Tags;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RuneInscriberMenu extends AbstractContainerMenu {
    private final RuneInscriberBlockEntity blockEntity;
    private final ContainerLevelAccess levelAccess;
    private final ItemStackHandler inventory;

    public static final int BASE_INPUT_SLOT = 0;
    public static final int TEMPLATE_INPUT_SLOT = 1;
    public static final int OUTPUT_SLOT = 2;

    private ArrayList<ItemStack> stored = new ArrayList<>();



    //Client Constructor
    public RuneInscriberMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    //Server Constructor
    public RuneInscriberMenu(int pContainerId, Inventory inv, BlockEntity entity) {
        super(ModMenuTypes.RUNE_INSCRIBER_MENU.get(), pContainerId);
        if (entity instanceof RuneInscriberBlockEntity be) {
            this.blockEntity = be;
        }
        else {
            throw new IllegalStateException("Incorrect block entity class (%s) passed into RuneInscriberMenu".formatted(entity.getClass().getCanonicalName()));
        }

        this.levelAccess = ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos());
        this.inventory = blockEntity.getInventory();
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
            // We are inside of the player's playerInventory
            if(!moveItemStackTo(fromStack, 36, 37, false))
                return ItemStack.EMPTY;
        } else if (pIndex < 63) {
            // We are inside of the block entity playerInventory
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
        return inventory.getStackInSlot(BASE_INPUT_SLOT).isEmpty() &&
                inventory.getStackInSlot(TEMPLATE_INPUT_SLOT).isEmpty() &&
                inventory.getStackInSlot(OUTPUT_SLOT).isEmpty();
    }

    private boolean isCrafted = false;

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.levelAccess, player, ModBlocks.RUNE_INSCRIBER.get());
    }


    public RuneInscriberBlockEntity getBlockEntity() {
        return blockEntity;
    }

    private boolean hasRecipe() {
        Optional<RecipeHolder<RuneInscribingRecipe>> recipe = getCurrentRecipe();

        if (recipe.isEmpty()) {
            return false;
        }

        //ItemStack result = recipe.get().value().getResultItem(this.blockEntity.getLevel().registryAccess());

        return !isEmpty() &&
                recipe.get().value().getBase().test(inventory.getStackInSlot(RuneInscriberMenu.BASE_INPUT_SLOT)) &&
                recipe.get().value().getTemplate().test(inventory.getStackInSlot(RuneInscriberMenu.TEMPLATE_INPUT_SLOT));
    }

    private void suggestCraft()
    {

        if (!hasRecipe()) {
            clearOutput();
            return;
        }
        Optional<RecipeHolder<RuneInscribingRecipe>> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().value().getResultItem(this.blockEntity.getLevel().registryAccess());


        blockEntity.getInventory().setStackInSlot(OUTPUT_SLOT, result);
    }

    private void craft() {
        Optional<RecipeHolder<RuneInscribingRecipe>> recipe = getCurrentRecipe();
        blockEntity.getInventory().setStackInSlot(BASE_INPUT_SLOT, new ItemStack(Items.AIR));
        isCrafted = true;
    }



    private void clearOutput() {
        ItemStack stack = inventory.getStackInSlot(OUTPUT_SLOT);
        stack.shrink(1);
        inventory.setStackInSlot(OUTPUT_SLOT, stack);
    }

    @Override
    public void removed(Player pPlayer) {
        clear(pPlayer, blockEntity.getInventory().getStackInSlot(BASE_INPUT_SLOT));
        clear(pPlayer, blockEntity.getInventory().getStackInSlot(TEMPLATE_INPUT_SLOT));
        isEmpty();
        if (isCrafted) {
            clear(pPlayer, blockEntity.getInventory().getStackInSlot(OUTPUT_SLOT));
        }
        clearOutput();

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



    private Optional<RecipeHolder<RuneInscribingRecipe>> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(2);
        inventory.setItem(BASE_INPUT_SLOT, this.inventory.getStackInSlot(BASE_INPUT_SLOT));
        inventory.setItem(TEMPLATE_INPUT_SLOT, this.inventory.getStackInSlot(TEMPLATE_INPUT_SLOT));
        List<RecipeHolder<RuneInscribingRecipe>> list = this.blockEntity.getLevel().getRecipeManager().getRecipesFor(RuneInscribingRecipe.RECIPE_TYPE, inventory, this.blockEntity.getLevel());
        if (list.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(list.get(0));
    }


    private void createBlockEntityInventory(RuneInscriberBlockEntity be) {
        be.getOptional().ifPresent(inventory -> {
            this.addSlot(new SlotItemHandler(inventory, BASE_INPUT_SLOT, 26, 35) {
                @Override
                public void setChanged() {
                    suggestCraft();
                    super.setChanged();
                }


                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return stack.is(Tags.Items.STONE);
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

            this.addSlot(new SlotItemHandler(inventory, TEMPLATE_INPUT_SLOT, 80, 35) {
                @Override
                public void setChanged() {
                    suggestCraft();
                    super.setChanged();
                }


                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return stack.is(ModTags.Items.RUNE_TEMPLATES);
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

            this.addSlot(new RuneOutputSlot(inventory, OUTPUT_SLOT, 133, 35));
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

    public class RuneOutputSlot extends SlotItemHandler {

        public RuneOutputSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public void setChanged() {
            if (this.hasItem())
                craft();
            super.setChanged();
        }

        @Override
        public void onTake(Player pPlayer, ItemStack pStack) {
            super.onTake(pPlayer, pStack);
        }


        @Override
        public boolean mayPlace(@NotNull ItemStack stack) {
            return false;
        }
    }

}
