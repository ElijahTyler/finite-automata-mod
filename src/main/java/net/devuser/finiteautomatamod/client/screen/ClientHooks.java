package net.devuser.finiteautomatamod.client.screen;

import net.devuser.finiteautomatamod.screen.InputScreen;
import net.devuser.finiteautomatamod.screen.NewEntityScreen;
import net.devuser.finiteautomatamod.screen.TransitionScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

public class ClientHooks {
    public static void openExampleBlockScreen(BlockPos pos) {
        Minecraft.getInstance().setScreen(new NewEntityScreen(pos));
    }

    public static void openInputBlockScreen(BlockPos pos){
        Minecraft.getInstance().setScreen(new InputScreen(pos));
    }

    public static void openTransitionBlockScreen(BlockPos pos){
        Minecraft.getInstance().setScreen(new TransitionScreen(pos));
    }


}
