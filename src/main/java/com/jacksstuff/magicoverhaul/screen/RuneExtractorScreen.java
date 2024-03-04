package com.jacksstuff.magicoverhaul.screen;

import com.jacksstuff.magicoverhaul.MagicOverhaul;
import com.jacksstuff.magicoverhaul.block.entity.RuneExtractorBlockEntity;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.client.gui.components.Button;
import net.minecraftforge.client.event.TextureStitchEvent;

public class RuneExtractorScreen extends AbstractContainerScreen<RuneExtractorMenu> {

    private static final Component TITLE = Component.translatable("gui." + MagicOverhaul.MOD_ID + ".rune_extractor_screen");
    private static final Component TEST_BUTTON =
            Component.translatable("gui." + MagicOverhaul.MOD_ID + ".rune_extractor_screen.button.test_button");
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(MagicOverhaul.MOD_ID, "textures/gui/rune_extractor_gui.png");


    public RuneExtractorScreen(RuneExtractorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);

        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    /*
    @Override
    protected void init() {
        super.init();

        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        if (this.minecraft == null) return;
        if (this.minecraft.level == null) return;

        Level level = this.minecraft.level;

        BlockEntity be = level.getBlockEntity(this.position);
        if (be instanceof RuneExtractorBlockEntity blockEntity) {
            this.blockEntity = blockEntity;
        } else {
            System.err.printf("BlockEntity at %s is not of type RuneExtractorBlockEntity!\n", this.position);
            return;
        }

        this.button = addRenderableWidget(
                Button.builder(
                    TEST_BUTTON,
                    this::handleTestButton)
                        .bounds(this.leftPos + 8, this.topPos + 20, 80, 20)
                        .tooltip(Tooltip.create(TEST_BUTTON))
                        .build());
    }
    */

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        renderTransparentBackground(guiGraphics);
        guiGraphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    /*
    private void handleTestButton(Button button) {
        //button logic
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
     */
}
