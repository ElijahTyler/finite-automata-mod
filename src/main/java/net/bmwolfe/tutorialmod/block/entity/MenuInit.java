package net.bmwolfe.tutorialmod.block.entity;

import net.bmwolfe.tutorialmod.TutorialMod;
import net.bmwolfe.tutorialmod.screen.NewEntityMenu;
import net.bmwolfe.tutorialmod.screen.NewEntityScreen;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuInit {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, TutorialMod.MOD_ID);

    public static final RegistryObject<MenuType<NewEntityMenu>> EXAMPLE_MENU = MENU_TYPES.register("example_menu",
            () -> IForgeMenuType.create(NewEntityMenu::new));

}
