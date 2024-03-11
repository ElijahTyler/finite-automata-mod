package net.devuser.finiteautomatamod.screen;

import net.devuser.finiteautomatamod.FiniteAutomataMod;
import net.devuser.finiteautomatamod.block.custom.OutputBlock;
import net.devuser.finiteautomatamod.block.custom.StateBlock;
import net.devuser.finiteautomatamod.block.entity.InputBlockEntity;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.io.*;
import java.util.HashMap;

public class InputScreen extends Screen {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(FiniteAutomataMod.MOD_ID, "textures/gui/input_gui.png"); //change this for our new texture

    private final BlockPos position;

    private InputBlockEntity blockEntity;
    private final int imageWidth, imageHeight;

    private int leftPos, topPos;

    private Button button;

    private EditBox editBox;

    private EditBox editBox1;

    private static final Component Title = Component.translatable("gui." + FiniteAutomataMod.MOD_ID + "input_block_screen");
    private static final Component COMPUTE_BUTTON =
            Component.translatable("gui." + FiniteAutomataMod.MOD_ID + "input_block_screen.button.compute_button");
    private static final Component INPUT_EDIT_BOX =
            Component.translatable("gui." + FiniteAutomataMod.MOD_ID + "input_block_screen.edit_box.input_edit_box");
    private static final Component INITIAL_STATE_FIELD =
            Component.translatable("gui." + FiniteAutomataMod.MOD_ID + "input_block_screen.edit_box.initial_state_field");

    public InputScreen(BlockPos pPos) {
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
        if(be instanceof InputBlockEntity blockEntity){
            this.blockEntity = blockEntity;
        } else {
            System.err.printf("BlockEntity at %s is not of type InputBlockEntity", this.position);
            return;
        }

        this.button = addRenderableWidget(
                Button.builder(
                        COMPUTE_BUTTON,
                        this::handleComputeButton)
                        .bounds(this.leftPos + 75, this.topPos + 135, 50, 20)
                        .tooltip(Tooltip.create(COMPUTE_BUTTON))
                        .build());

        this.editBox = addRenderableWidget(new EditBox(this.font, this.leftPos + getImageWidth()/2-62, this.topPos + 90, 150, 30, Component.literal("Hello")));
        this.editBox1 = addRenderableWidget(new EditBox(this.font, this.leftPos + 150, this.topPos + 10, 20, 20, Component.literal("Hello")));


        editBox.setCanLoseFocus(false);
        editBox.active = true;
        editBox.setFocused(true);

    }

    private void handleComputeButton(Button button){
        Integer initial_state_ID = Integer.valueOf(getEditBoxText(editBox1)); // editBox1 holds the initial state ID
        String input_string = getEditBoxText(editBox); // editBox holds the input string
        HashMap<Integer, BlockPos> stateBlockIDCoords = FiniteAutomataMod.dfa.getStateBlockIDCoords();
        if (!stateBlockIDCoords.containsKey(initial_state_ID)) {
            System.out.println("Invalid initial state");
            return;
        }
        System.out.println("Performing computation...");

        BlockPos result = FiniteAutomataMod.dfa.doComputation(input_string, initial_state_ID);
        Level world = this.minecraft.level;
        Block block = world.getBlockState(result).getBlock();
        if (block instanceof StateBlock) {
            StateBlock sb = (StateBlock) block;
            if (sb.getIsAccepting()) {
                BlockPos blockPos = FiniteAutomataMod.dfa.getOutputBlockPos(); /// BAD BOY
                BlockState blockState = world.getBlockState(blockPos);
                Block finalBlockEver = blockState.getBlock();
                if (finalBlockEver instanceof OutputBlock) {
                    OutputBlock actuallyFinalBlock = (OutputBlock) finalBlockEver;
                    System.out.println("Accepted!");
                    actuallyFinalBlock.outputRedstoneSignal(blockState, world, blockPos);
                }
            } else {
                return;
            }
        }
    }

    private String getEditBoxText(EditBox editBox){
        return editBox.getValue();
    }


    public int i = 0;

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        guiGraphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, 200, 180);

        super.render(guiGraphics, mouseX, mouseY, delta);

        guiGraphics.drawString(this.font, "Input your string to test below", this.leftPos + getImageWidth()/2-65, this.topPos + 70, 0, false);
        guiGraphics.drawString(this.font, "Choose initial state:", this.leftPos + 40, this.topPos + 15, 0, false);

        if(i == 0){
            i++;
            loadText();
        }

    }

    public int getImageWidth() {
        return imageWidth;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public void onClose(){
        i = 0;
        saveText();
        super.onClose();
    }

    private void saveText() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("input.txt"))) {
            // Write text from EditBoxes to the file
            writer.write(editBox.getValue());
            writer.newLine();
            writer.write(editBox1.getValue());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadText() {
        try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
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