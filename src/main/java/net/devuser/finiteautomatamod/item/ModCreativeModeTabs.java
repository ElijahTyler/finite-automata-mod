package net.devuser.finiteautomatamod.item;

import net.devuser.finiteautomatamod.FiniteAutomataMod;
import net.devuser.finiteautomatamod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FiniteAutomataMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> FINITE_AUTOMATA_TAB = CREATIVE_MODE_TABS.register("finite_automata_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.STATE_BLOCK.get())).title(Component.translatable("creativetab.finite_automata_tab"))
                    .displayItems((pParameters, pOutput) -> {

//                        pOutput.accept(ModBlocks.SOUND_BLOCK.get());
//
//                        pOutput.accept(ModBlocks.NEW_ENTITY.get());

                        pOutput.accept(ModBlocks.INPUT_BLOCK.get());

                        pOutput.accept(ModBlocks.TRANSITION_BLOCK.get());

                        pOutput.accept(ModBlocks.STATE_BLOCK.get());

                        pOutput.accept(ModBlocks.OUTPUT_BLOCK.get());

                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
