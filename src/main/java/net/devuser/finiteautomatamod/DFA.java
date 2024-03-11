package net.devuser.finiteautomatamod;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.*;

public class DFA {
    private Set<BlockPos> stateBlockPos;
    private int numStateBlocks;
    private HashMap<Integer, BlockPos> stateBlockIDCoords;
    private HashMap<BlockPosKey, BlockPos> transitionBlockPos;
    private BlockPos inputBlockPos;
    private BlockPos outputBlockPos;

    public DFA() {
        stateBlockPos = new HashSet<>();
        transitionBlockPos = new HashMap<>();
        stateBlockIDCoords = new HashMap<Integer, BlockPos>();
        inputBlockPos = null;
        outputBlockPos = null;
        numStateBlocks = 0;
    }

    public HashMap<Integer, BlockPos> getStateBlockIDCoords() {
        return stateBlockIDCoords;
    }

    public BlockPos getInputBlockPos() {
        return inputBlockPos;
    }

    public void setInputBlockPos(BlockPos inputBlockPos) {
        this.inputBlockPos = inputBlockPos;
    }

    public BlockPos getOutputBlockPos() {
        return outputBlockPos;
    }

    public void setOutputBlockPos(BlockPos outputBlockPos) {
        this.outputBlockPos = outputBlockPos;
    }

    public int addStatePos(BlockPos pos) {
        // add to list of states
        stateBlockPos.add(pos);
        // increment number of state blocks
        numStateBlocks++;
        // add ID coords pair to hashmap
        System.out.println(String.valueOf(numStateBlocks) + " " + pos.toString());
        stateBlockIDCoords.put(numStateBlocks, pos);
        // return ID
        return numStateBlocks;
    }

    public void removeStatePos(BlockPos pos) {
        stateBlockPos.remove(pos);
    }

    public void addTransition(int prevStateID, String charToRead, int nextStateID) {
        BlockPos prevStatePos = stateBlockIDCoords.get(prevStateID);
        BlockPos nextStatePos = stateBlockIDCoords.get(nextStateID);

        // add block position to states list TODO CHECK FOR ACCEPTING STATES
        if (!stateBlockPos.contains(prevStatePos)) {
            stateBlockPos.add(prevStatePos);
        }
        if (!stateBlockPos.contains(nextStatePos)) {
            stateBlockPos.add(nextStatePos);
        }

        // add [(position, character) -> position] to transition "table"
        BlockPosKey keyToAdd = new BlockPosKey(prevStatePos, charToRead.charAt(0));
        transitionBlockPos.put(keyToAdd, nextStatePos);
    }

    public void removeTransition(BlockPos prevStatePos, char charToRead, BlockPos nextStatePos) {
        // remove [(position, character) -> position] from transition "table"
        BlockPosKey keyToAdd = new BlockPosKey(prevStatePos, charToRead);
        transitionBlockPos.remove(keyToAdd, nextStatePos);
    }

    public void removeTransitionsTo(BlockPos blockPos) {
        Iterator<Map.Entry<Integer, BlockPos>> iterator = stateBlockIDCoords.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, BlockPos> entry = iterator.next();

            // Check if the value matches the target BlockPos
            if (entry.getValue().equals(blockPos)) {
                System.out.println("Removing key: " + entry.getKey() + ", value: " + entry.getValue());
                iterator.remove(); // Removes the current entry from the HashMap
            }
        }
    }

    public void removeTransitionsFrom() {

    }

    public void viewTransitions(Player player) {
        if (transitionBlockPos.isEmpty()) {
            player.sendSystemMessage(Component.literal("No transitions set"));
            return;
        }

        for (Map.Entry<BlockPosKey, BlockPos> entry : transitionBlockPos.entrySet()) {
            BlockPosKey key = entry.getKey();
            BlockPos value = entry.getValue();

            String message = String.format("(%s, %s) -> %s",
                                            key.getPosition().toString(),
                                            key.getIdentifier(),
                                            value.toString());

            player.sendSystemMessage(Component.literal(message));
        }
    }

    public BlockPos doComputation(String inputString, int initStateID) {
        String stringWhileComputing = String.valueOf(inputString);
        BlockPos currentStatePos = stateBlockIDCoords.get(initStateID);
        BlockPos nextStatePos;

        for (int char_read = 0; char_read < inputString.length(); char_read++) {
            BlockPosKey currentBlockPosKey =
                    new BlockPosKey(currentStatePos, inputString.charAt(char_read));
            nextStatePos = transitionBlockPos.get(currentBlockPosKey);

            if (nextStatePos == null) { // no transition found, drop branch of computation
                return null;
            }

            currentStatePos = nextStatePos;
        }

        return currentStatePos;
    }
}