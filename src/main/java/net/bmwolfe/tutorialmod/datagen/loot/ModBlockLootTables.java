package net.bmwolfe.tutorialmod.datagen.loot;

import net.bmwolfe.tutorialmod.block.ModBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {

    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.SOUND_BLOCK.get());
        this.dropSelf(ModBlocks.NEW_ENTITY.get());
        this.dropSelf(ModBlocks.INPUT_BLOCK.get());
        this.dropSelf(ModBlocks.TRANSITION_BLOCK.get());
        this.dropSelf(ModBlocks.STATE_BLOCK.get());
        this.dropSelf(ModBlocks.OUTPUT_BLOCK.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
