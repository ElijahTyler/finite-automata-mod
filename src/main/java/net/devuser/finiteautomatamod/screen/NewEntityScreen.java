package net.devuser.finiteautomatamod.screen;

import net.devuser.finiteautomatamod.FiniteAutomataMod;
import net.devuser.finiteautomatamod.block.entity.NewEntityBlockEntity;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class NewEntityScreen extends Screen {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(FiniteAutomataMod.MOD_ID, "textures/gui/new_entity_gui.png"); //change this for our new texture

    private final BlockPos position;

    private NewEntityBlockEntity blockEntity;
    private final int imageWidth, imageHeight;

    private int leftPos, topPos;

    private Button button;

    private EditBox editBox;

    private static final Component Title = Component.translatable("gui." + FiniteAutomataMod.MOD_ID + "new_entity_block_screen");
    private static final Component EXAMPLE_BUTTON =
            Component.translatable("gui." + FiniteAutomataMod.MOD_ID + "new_entity_block_screen.button.example_button");
    private static final Component EXAMPLE_EDIT_BOX =
            Component.translatable("gui." + FiniteAutomataMod.MOD_ID + "new_entity_block_screen.edit_box.example_edit_box");

    public NewEntityScreen(BlockPos pPos) {
        super(Title);

        this.position = pPos;
        this.imageHeight=166;
        this.imageWidth=176;
    }


    @Override
    protected void init() {
        super.init();
        //Put in the center
        this.leftPos = (this.width - this.imageWidth)/2;
        this.topPos = (this.height - this.imageHeight)/2;


        if(this.minecraft == null) return;

        Level level = this.minecraft.level;
        if(level == null) return;

        BlockEntity be = level.getBlockEntity(this.position);
        if(be instanceof NewEntityBlockEntity blockEntity){
            this.blockEntity = blockEntity;
        } else {
            System.err.printf("BlockEntity at %s is not of type NewEntityBlockEntity", this.position);
            return;
        }

        this.button = addRenderableWidget(
                Button.builder(
                        EXAMPLE_BUTTON,
                        this::handleExampleButton)
                        .bounds(this.leftPos + 8, this.topPos + 8, 35, 20)
                        .tooltip(Tooltip.create(EXAMPLE_BUTTON))
                        .build());

        this.editBox = addRenderableWidget(new EditBox(this.font, this.leftPos + 8, this.topPos + 30, 50, 40, Component.literal("Hello")));


        editBox.setCanLoseFocus(false);
        editBox.active = true;
        editBox.setFocused(true);

    }

    private void handleExampleButton(Button button){

    }

    private String getEditBoxText(EditBox editBox){
        return editBox.getValue();
    }



    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        guiGraphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, 200, 180);

        super.render(guiGraphics, mouseX, mouseY, delta);

        guiGraphics.drawString(this.font, Title, this.leftPos, this.topPos, 0x404040, false);

        guiGraphics.drawString(this.font, "Accepting Input", this.leftPos + 20, this.topPos + 20, 0xFF0000, false);

    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}