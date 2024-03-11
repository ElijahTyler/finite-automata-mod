package net.devuser.finiteautomatamod.block.custom;

import net.devuser.finiteautomatamod.FiniteAutomataMod;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

import java.util.ArrayList;

public class StateBlock extends Block  {
    private boolean isAccepting = false;

    private ArrayList<TransitionBlock> ownedTransBlocks;

    private int ownedTransBlocksInt;

    private static final IntegerProperty MODEL = IntegerProperty.create("model", 0, 3);



    public StateBlock(Properties pProperties) {
        super(pProperties);
        ownedTransBlocks = new ArrayList<TransitionBlock>();
        ownedTransBlocksInt = 0;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(MODEL);

    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                 Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        // toggle between the shits
        if (!pLevel.isClientSide) {
            // toggle true/false
            isAccepting = !isAccepting;
            // change texture
            // 0: both lights off (not accepting, not thinking)
            // 1: red on (not accepting, thinking)
            // 2: green on, red off (accepting, not thinking)
            // 3: both on (accepting, thinking)
            int currentIndex = pState.getValue(MODEL);
            int newIndex = (currentIndex + 2) % 4;

            if (newIndex >= 2) {
                System.out.println("Accepting");
            } else {
                System.out.println("Rejecting");
            }

            BlockState newState = pState.setValue(MODEL, newIndex);
            pLevel.setBlock(pPos, newState, 3);
        }

        return InteractionResult.SUCCESS;
    }

//    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (!world.isClientSide) {
            int IDgotten = FiniteAutomataMod.dfa.addStatePos(pos);
            if (placer instanceof Player) {
                Player sans_but_jacked = (Player) placer;
                sans_but_jacked.sendSystemMessage(Component.literal("ID: " + String.valueOf(IDgotten)));
            }
        }
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);

        // three things we gotta do when removing a state Q
        // 1. remove Q from stateBlockPos itself
        // TutorialMod.dfa.removeStatePos(pPos);
        // 2. remove transitions heading into Q
        // TutorialMod.dfa.removeTransitionsTo(pPos);
        // 3. remove transitions heading out of Q

    }

    public boolean getIsAccepting() {
        return isAccepting;
    }
}
