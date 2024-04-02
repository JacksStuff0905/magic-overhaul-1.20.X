package com.jacksstuff.magic_overhaul.screen.button;

import com.jacksstuff.magic_overhaul.item.ModItems;
import com.jacksstuff.magic_overhaul.item.custom.RuneItem;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.registries.RegistryObject;

import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class ItemButton extends Button {
    public final Item item;
    private final int posX, posY;

    public boolean selected = false;

    private final boolean IS_DISABLED;

    private static final float INVALID_ITEM_ALPHA = 0.25f;


    public final int[] selectedTint = new int[]{137, 215, 250};
    public ItemButton(Item item, int pX, int pY, int pWidth, int pHeight, OnPress pOnPress) {
        super(pX, pY, pWidth, pHeight, Component.empty(), pOnPress, DEFAULT_NARRATION);
        this.item = item;
        this.posX = pX + (pWidth - 16) / 2;
        this.posY = pY + (pHeight - 16) / 2;
        this.IS_DISABLED = !(item instanceof RuneItem);
        this.active = !IS_DISABLED;
    }
    public ItemButton(Item item, int pX, int pY, int pWidth, int pHeight, OnPress pOnPress, boolean isSelected) {
        super(pX, pY, pWidth, pHeight, Component.empty(), pOnPress, DEFAULT_NARRATION);
        this.item = item;
        this.posX = pX + (pWidth - 16) / 2;
        this.posY = pY + (pHeight - 16) / 2;
        this.selected = isSelected;
        this.IS_DISABLED = !(item instanceof RuneItem) || item == null;
        this.active = !IS_DISABLED;
    }


    @Override
    public Component getMessage() {
        return Component.literal("test");
    }

    @Override
    protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        float r = 1F;
        float g = 1F;
        float b = 1F;
        if (selected) {
            r = (float) selectedTint[0] / 255f;
            g = (float) selectedTint[1] / 255f;
            b = (float) selectedTint[2] / 255f;
        }

        pGuiGraphics.setColor(r, g, b, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();

        pGuiGraphics.blitSprite(SPRITES.get(this.active, this.isHovered()), this.getX(), this.getY(), this.getWidth(), this.getHeight());
        pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        if (IS_DISABLED)
            pGuiGraphics.setColor(INVALID_ITEM_ALPHA, INVALID_ITEM_ALPHA, INVALID_ITEM_ALPHA, 1);
        pGuiGraphics.renderItem(new ItemStack(item), posX, posY);
        pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);

        //Draw tooltip
        if (!super.isHovered() || IS_DISABLED) {
            return;
        }

        pGuiGraphics.renderComponentTooltip(minecraft.font, new ItemStack(item).getTooltipLines(minecraft.player, TooltipFlag.NORMAL), pMouseX, pMouseY);
    }


    @Override
    public void onPress() {
        selected = !selected;
        super.onPress();
    }


}
