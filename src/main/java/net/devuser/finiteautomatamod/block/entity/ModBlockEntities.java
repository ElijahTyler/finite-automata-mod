package net.devuser.finiteautomatamod.block.entity;

import net.devuser.finiteautomatamod.FiniteAutomataMod;
import net.devuser.finiteautomatamod.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, FiniteAutomataMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<NewEntityBlockEntity>> NEW_ENTITY_BE =
            BLOCK_ENTITIES.register("new_entity_be", ()->
                    BlockEntityType.Builder.of(NewEntityBlockEntity::new,
                            ModBlocks.NEW_ENTITY.get()).build(null));

    public static final RegistryObject<BlockEntityType<InputBlockEntity>> INPUT_BE =
            BLOCK_ENTITIES.register("input_be", ()->
                    BlockEntityType.Builder.of(InputBlockEntity::new,
                            ModBlocks.INPUT_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<TransitionBlockEntity>> TRANSITION_BE =
            BLOCK_ENTITIES.register("transition_be", ()->
                    BlockEntityType.Builder.of(TransitionBlockEntity::new,
                            ModBlocks.TRANSITION_BLOCK.get()).build(null));




    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }

}
