package net.devuser.finiteautomatamod;

import com.mojang.logging.LogUtils;
import net.devuser.finiteautomatamod.block.ModBlocks;
import net.devuser.finiteautomatamod.block.entity.MenuInit;
import net.devuser.finiteautomatamod.block.entity.ModBlockEntities;
import net.devuser.finiteautomatamod.item.ModCreativeModeTabs;
import net.devuser.finiteautomatamod.item.ModItems;
import net.devuser.finiteautomatamod.screen.ModMenuTypes;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(FiniteAutomataMod.MOD_ID)
public class FiniteAutomataMod {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "finiteautomatamod";
    public static DFA dfa = new DFA();
    public static int numberOfStates = 0;

    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public FiniteAutomataMod() {


        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        //This was turty's change
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        MenuInit.MENU_TYPES.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);

    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey()== CreativeModeTabs.INGREDIENTS){
            event.accept(ModBlocks.STATE_BLOCK);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

            //MenuScreens.register(ModMenuTypes.NEW_ENTITY_MENU.get(), NewEntityScreen::new);

        }
    }
}
