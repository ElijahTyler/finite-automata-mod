package net.devuser.finiteautomatamod.screen;

import net.devuser.finiteautomatamod.FiniteAutomataMod;
import net.devuser.finiteautomatamod.block.entity.TransitionBlockEntity;
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

import java.io.*;

public class TransitionScreen extends Screen {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(FiniteAutomataMod.MOD_ID, "textures/gui/transition_gui.png"); //change this for our new texture

    private final BlockPos position;

    private TransitionBlockEntity blockEntity;
    private final int imageWidth, imageHeight;

    private int leftPos, topPos;

    private EditBox editBox;
    private EditBox editBox1;
    private EditBox editBox2;
    private Button button;

    private static final Component Title = Component.translatable("gui." + FiniteAutomataMod.MOD_ID + "transition_block_screen");
    private static final Component SAVE_BUTTON =
            Component.translatable("gui." + FiniteAutomataMod.MOD_ID + "transition_block_screen.button.save_button");
    private static final Component ACCEPTED_INPUT_BOX =
            Component.translatable("gui." + FiniteAutomataMod.MOD_ID + "transition_block_screen.edit_box.accepted_input_box");
    private static final Component STATE_ID_BOX =
            Component.translatable("gui." + FiniteAutomataMod.MOD_ID + "transition_block_screen.edit_box.state_id_box");

    public TransitionScreen(BlockPos pPos) {
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
        if(be instanceof TransitionBlockEntity blockEntity){
            this.blockEntity = blockEntity;
        } else {
            System.err.printf("BlockEntity at %s is not of type TransitionBlockEntity", this.position);
            return;
        }

        this.button = addRenderableWidget(
                Button.builder(
                                SAVE_BUTTON,
                                this::handleSaveButton)
                        .bounds(this.leftPos + 75, this.topPos + 145, 50, 20)
                        .tooltip(Tooltip.create(SAVE_BUTTON))
                        .build());


        this.editBox = addRenderableWidget(new EditBox(this.font, this.leftPos + 140, this.topPos + 29, 20, 20, Component.literal("Hello"))); // previous ID
        this.editBox1 = addRenderableWidget(new EditBox(this.font, this.leftPos + 140, this.topPos + 76, 20, 20, Component.literal("Hello"))); // character read
        this.editBox2 = addRenderableWidget(new EditBox(this.font, this.leftPos + 140, this.topPos + 126, 20, 20, Component.literal("Hello"))); // next ID


        editBox.setCanLoseFocus(false);
        editBox.active = true;
        editBox.setFocused(true);

    }

    private void handleSaveButton(Button button){
        Integer prev = Integer.valueOf(editBox.getValue());
        String char_read = editBox1.getValue();
        Integer next = Integer.valueOf(editBox2.getValue());
        FiniteAutomataMod.dfa.addTransition(prev, char_read, next);
        System.out.println("Successfully added transition:");
        System.out.println("(" + prev + ", " + char_read + ") -> " + next);
    }


    public int i = 0;
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        guiGraphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, 200, 180);

        super.render(guiGraphics, mouseX, mouseY, delta);

        guiGraphics.drawString(this.font, "Previous State ID:", this.leftPos + 20, this.topPos + 34, 0, false);
        guiGraphics.drawString(this.font, "Accepted Character:", this.leftPos + 20, this.topPos + 81, 0, false);
        guiGraphics.drawString(this.font, "Next State ID:", this.leftPos + 20, this.topPos + 131, 0, false);
        if(i == 0){
            i++;
            loadText();
        }

    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void onClose() {
        saveText();
        i = 0;
        super.onClose();
    }

    private void saveText() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transition.txt"))) {
            // Write text from EditBoxes to the file
            writer.write(editBox.getValue());
            writer.newLine();
            writer.write(editBox1.getValue());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadText() {
        try (BufferedReader reader = new BufferedReader(new FileReader("transition.txt"))) {
            // Read text from the file and set it to the EditBoxes
            String text1 = reader.readLine();
            String text2 = reader.readLine();

            if (text1 != null) editBox.setValue(text1);
            if (text2 != null) editBox1.setValue(text2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}