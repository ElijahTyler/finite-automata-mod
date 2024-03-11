package net.devuser.finiteautomatamod.datagen;

import net.devuser.finiteautomatamod.FiniteAutomataMod;
import net.devuser.finiteautomatamod.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, FiniteAutomataMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.SOUND_BLOCK);

        blockWithItem(ModBlocks.OUTPUT_BLOCK);

        blockWithItem(ModBlocks.STATE_BLOCK);

        simpleBlockWithItem(ModBlocks.NEW_ENTITY.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/new_entity")));

        simpleBlockWithItem(ModBlocks.INPUT_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/input_block")));

        simpleBlockWithItem(ModBlocks.TRANSITION_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/transition_block")));

    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject){
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
