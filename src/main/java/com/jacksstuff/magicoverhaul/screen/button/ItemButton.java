package com.jacksstuff.magicoverhaul.screen.button;

import com.jacksstuff.magicoverhaul.item.custom.RuneItem;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

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
    }


    @Override
    public void onPress() {
        selected = !selected;
        super.onPress();
    }


}
