package com.jacksstuff.magic_overhaul.screen;

import com.jacksstuff.magic_overhaul.MagicOverhaul;
import com.jacksstuff.magic_overhaul.item.ModItems;
import com.jacksstuff.magic_overhaul.item.custom.RuneItem;
import com.jacksstuff.magic_overhaul.screen.button.ItemButton;
import com.jacksstuff.magic_overhaul.util.ModTags;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;

public class SpellCastScreen extends AbstractContainerScreen<SpellCastMenu> {
    private static final ResourceLocation BACKGROUND =
            new ResourceLocation(MagicOverhaul.MOD_ID, "textures/gui/spell_cast_gui.png");

    private static final int MAX_RUNES = 36;

    private static final int BUTTON_SIZE = 18;


    public static boolean is_active = false;

    private ItemButton[] buttons;

    private Inventory playerInventory;

    private final int imageWidth, imageHeight;
    private int leftPos, topPos;

    //private ArrayList<RuneItem> activeRunes = new ArrayList<>();
    private static final int IMAGE_SIZE = 16;

    private static final int MAX_RADIUS = 20;


    public SpellCastScreen(SpellCastMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageWidth = 176;
        this.imageHeight = 166;
        playerInventory = pPlayerInventory;
    }


    @Override
    protected void init() {
        super.init();


        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        createRuneButtons();
    }


    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        renderTransparentBackground(guiGraphics);
        guiGraphics.blit(BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        createActiveRuneDisplay(guiGraphics, this.width / 2, this.topPos + 40);


    }


    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void removed() {
        is_active = false;
        super.removed();
    }
    @Override
    public void onClose() {
        is_active = false;
        super.onClose();
    }


    public void createRuneButtons() {
        ArrayList<RuneItem> allRunes = new ArrayList<>();
        ModItems.forEachRune(allRunes::add);

        buttons = new ItemButton[playerInventory.items.size()];
        int runeCount = allRunes.size() > MAX_RUNES ? MAX_RUNES : allRunes.size();

        ArrayList<RuneItem> runesInInventory = new ArrayList<>();
        for (int i = 0; i < allRunes.size(); i++) {
            if (playerInventory.contains(new ItemStack(allRunes.get(i)))) {
                runesInInventory.add(allRunes.get(i));
            }
        }

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 9; col++) {
                Item item = playerInventory.getItem(row * 9 + col).getItem();
                buttons[row * 9 + col] = addRenderableWidget(
                        new ItemButton(
                                playerInventory.getItem(row * 9 + col).getItem(),
                                this.leftPos + 7 + BUTTON_SIZE * col,
                                row != 0 ? this.topPos + 83 + BUTTON_SIZE * (row - 1) : this.topPos + 142,
                                BUTTON_SIZE,
                                BUTTON_SIZE,
                                this::handleButton,
                                item instanceof RuneItem runeItem && menu.runeStorage.activeRunes.contains(runeItem)
                        ));
                /*
                if (!runesInInventory.contains(item)) {
                    buttons[row * 9 + col].active = false;
                    Component component = Component.literal(item.getName(new ItemStack(item)).getString()).withStyle(ChatFormatting.RED);
                    buttons[row * 9 + col].setTooltip(Tooltip.create(
                            component
                    ));
                }
                else {
                    buttons[row * 9 + col].setTooltip(Tooltip.create(
                            item.getName(new ItemStack(item))));
                }

                 */
            }
        }



        /*
        RuneItem[][] map = new RuneItem[(int)Math.ceil((double)runeCount / 9f)][];
        for (int r = 0; r < map.length - 1; r++) {
            map[r] = new RuneItem[9];
        }
        map[map.length - 1] = new RuneItem[runeCount % 9];




        int index = 0;
        for (int row = 0; row < map.length; row++) {
            for (int column = 0; column < map[row].length; column++) {
                map[row][column] = allRunes.get(index);
                buttons[index] = addRenderableWidget(
                        new ItemButton(
                                map[row][column],
                                this.leftPos + 7 + BUTTON_SIZE * column,
                                this.topPos + 83 + BUTTON_SIZE * row,
                                BUTTON_SIZE,
                                BUTTON_SIZE,
                                this::handleButton,
                                menu.runeStorage.activeRunes.contains(map[row][column])
                        ));
                if (!runesInInventory.contains(map[row][column])) {
                    buttons[index].active = false;
                    Component component = Component.literal(map[row][column].getName(new ItemStack(map[row][column])).getString()).withStyle(ChatFormatting.RED);
                    buttons[index].setTooltip(Tooltip.create(
                            component
                    ));
                }
                else {
                    buttons[index].setTooltip(Tooltip.create(
                            map[row][column].getName(new ItemStack(map[row][column]))));
                }
                index++;
            }
        }
         */
    }


    public void createActiveRuneDisplay(GuiGraphics guiGraphics, int centerX, int centerY) {
        double angleChange = (2f * Math.PI) / (double)menu.runeStorage.activeRunes.size();
        double radius = menu.runeStorage.activeRunes.size() > 1 ? ((double)IMAGE_SIZE / 2f) / Math.sin(angleChange / 2) : 0;
        radius = Math.min(radius, MAX_RADIUS);
        double angle = Math.toRadians(-90);
        for (RuneItem runeItem : menu.runeStorage.activeRunes) {
            double x = (radius * Math.cos(angle)) + centerX;
            double y = (radius * Math.sin(angle)) + centerY;
            guiGraphics.renderItem(new ItemStack(runeItem),
                    (int)x - IMAGE_SIZE / 2,
                    (int)y - IMAGE_SIZE / 2);

            angle += angleChange;
        }
    }

    private void toggleRune(RuneItem rune) {
        if (menu.runeStorage.activeRunes.contains(rune)) {
            menu.runeStorage.activeRunes.remove(rune);
        }
        else {
            menu.runeStorage.activeRunes.add(rune);
        }
    }

    private void handleButton(Button button) {
        if (button instanceof ItemButton itemButton) {
            if (itemButton.item instanceof RuneItem runeItem) {
                toggleRune(runeItem);
            }

            for (ItemButton b : buttons) {
                if (itemButton.item.equals(b.item)) {
                    b.selected = itemButton.selected;
                }
            }
        }

    }
}
