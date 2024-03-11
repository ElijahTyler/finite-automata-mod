package net.devuser.finiteautomatamod.block;

import net.devuser.finiteautomatamod.FiniteAutomataMod;
import net.devuser.finiteautomatamod.block.custom.*;
import net.devuser.finiteautomatamod.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, FiniteAutomataMod.MOD_ID);

    public static final RegistryObject<Block> SOUND_BLOCK = registerBlock("sound_block",
            () -> new SoundBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.ANVIL)
                    .lightLevel(state -> state.getValue(SoundBlock.LIT) ? 15 : 0)));

    public static final RegistryObject<Block> NEW_ENTITY = registerBlock("new_entity",
            () -> new NewEntityBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));

    public static final RegistryObject<Block> INPUT_BLOCK = registerBlock("input_block",
            () -> new InputBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));

    public static final RegistryObject<Block> TRANSITION_BLOCK = registerBlock("transition_block",
            () -> new TransitionBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));

    public static final RegistryObject<Block> STATE_BLOCK = registerBlock("state_block",
            () -> new StateBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.ANVIL)));

    public static final RegistryObject<Block> OUTPUT_BLOCK = registerBlock("output_block",
            () -> new OutputBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.ANVIL)));



    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block){
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
