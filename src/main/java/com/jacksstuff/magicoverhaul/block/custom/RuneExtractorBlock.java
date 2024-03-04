package com.jacksstuff.magicoverhaul.block.custom;


import com.jacksstuff.magicoverhaul.block.entity.RuneExtractorBlockEntity;
import com.jacksstuff.magicoverhaul.block.entity.util.TickableBlockEntity;
import com.jacksstuff.magicoverhaul.client.ClientHooks;
import com.jacksstuff.magicoverhaul.screen.RuneExtractorScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class RuneExtractorBlock extends Block implements EntityBlock {

    public static final VoxelShape SHAPE = Block.box(0,0,0, 16, 9, 16);

    public RuneExtractorBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    /*@Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide() && pHand == InteractionHand.MAIN_HAND) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if (entity instanceof RuneExtractorBlockEntity runeExEntity) {
                ItemStack stack = pPlayer.getItemInHand(pHand);
                ItemStackHandler inventory = runeExEntity.getInventory();
                if (stack.isEmpty()) {
                    if (inventory.getStackInSlot(0).isEmpty()) {
                        pPlayer.sendSystemMessage(Component.literal("No item in block entity!"));
                        return InteractionResult.SUCCESS;
                    }

                    ItemStack extracted = inventory.extractItem(0, pPlayer.isCrouching() ? inventory.getSlotLimit(0) : 1, false);
                    pPlayer.setItemInHand(pHand, extracted);
                }
                else {
                    ItemStack toInsert = stack.copy();
                    toInsert.setCount(1);

                    ItemStack leftover = inventory.insertItem(0, toInsert, false);

                    ItemStack remainder = stack.copy();
                    remainder.setCount(remainder.getCount() - 1);
                    remainder.grow(leftover.getCount());
                    pPlayer.setItemInHand(pHand, remainder);
                }

                return InteractionResult.SUCCESS;
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }
    */

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        BlockEntity be = pLevel.getBlockEntity(pPos);
        if (!(be instanceof RuneExtractorBlockEntity blockEntity)) {
            return InteractionResult.PASS;
        }

        if (pLevel.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        //open screen
        if (pPlayer instanceof ServerPlayer sPlayer) {
            sPlayer.openMenu(blockEntity, pPos);
        }
        return InteractionResult.CONSUME;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new RuneExtractorBlockEntity(blockPos, blockState);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if (entity instanceof RuneExtractorBlockEntity runeExBlockEntity) {
                ItemStackHandler inventory = runeExBlockEntity.getInventory();
                for (int i = 0; i < inventory.getSlots(); i++) {
                    ItemStack stack = inventory.getStackInSlot(i);
                    var itemEntity = new ItemEntity(pLevel, pPos.getX() + 0.5, pPos.getY() + 0.5, pPos.getZ() + 0.5, stack);
                    pLevel.addFreshEntity(itemEntity);
                }
            }
        }

        //must be last
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }


    /*
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return TickableBlockEntity.getTickerHelper(pLevel);
    }
     */
}
