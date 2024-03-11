package net.devuser.finiteautomatamod.screen;

import net.devuser.finiteautomatamod.FiniteAutomataMod;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, FiniteAutomataMod.MOD_ID);

    public static final RegistryObject<MenuType<NewEntityMenu>> NEW_ENTITY_MENU =
            registerMenuType("new_entity_menu", NewEntityMenu::new);
    public static final RegistryObject<MenuType<InputMenu>> INPUT_BLOCK_MENU =
            registerMenuType("input_block_menu", InputMenu::new);

    public static final RegistryObject<MenuType<TransitionMenu>> TRANSITION_BLOCK_MENU =
            registerMenuType("transition_block_menu", TransitionMenu::new);


    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
