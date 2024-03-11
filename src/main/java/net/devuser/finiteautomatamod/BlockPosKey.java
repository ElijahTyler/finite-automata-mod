package net.devuser.finiteautomatamod;

import net.minecraft.core.BlockPos;

import java.util.Objects;

public class BlockPosKey {
    private BlockPos position;
    private char identifier;

    public BlockPosKey(BlockPos position, char identifier) {
        this.position = position;
        this.identifier = identifier;
    }

    public BlockPos getPosition() {
        return position;
    }

    public char getIdentifier() {
        return identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockPosKey that = (BlockPosKey) o;
        return Objects.equals(position, that.position) &&
                Objects.equals(identifier, that.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, identifier);
    }

    // Optional: toString for debugging
    @Override
    public String toString() {
        return "BlockPosKey{" +
                "position=" + position +
                ", identifier='" + identifier + '\'' +
                '}';
    }
}