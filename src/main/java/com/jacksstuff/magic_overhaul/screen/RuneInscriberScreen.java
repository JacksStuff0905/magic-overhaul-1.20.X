package com.jacksstuff.magic_overhaul.screen;

import com.jacksstuff.magic_overhaul.MagicOverhaul;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class RuneInscriberScreen extends AbstractContainerScreen<RuneInscriberMenu> {

    private static final Component TITLE = Component.translatable("gui." + MagicOverhaul.MOD_ID + ".rune_inscriber_screen.title");
    private static final Component TEST_BUTTON =
            Component.translatable("gui." + MagicOverhaul.MOD_ID + ".rune_inscriber_screen.button.test_button");
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(MagicOverhaul.MOD_ID, "textures/gui/rune_inscriber_gui.png");


    public RuneInscriberScreen(RuneInscriberMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
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
