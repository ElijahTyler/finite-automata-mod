package net.devuser.finiteautomatamod.block.custom;

import net.devuser.finiteautomatamod.FiniteAutomataMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class OutputBlock extends Block {

    public static final BooleanProperty POWERED;


    public OutputBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState((BlockState)this.defaultBlockState().setValue(POWERED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(POWERED);

    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                 Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        pLevel.playSound(pPlayer, pPos, SoundEvents.NOTE_BLOCK_FLUTE.get(), SoundSource.BLOCKS,
                1f, 1f);

        return InteractionResult.SUCCESS;
    }

    public void outputRedstoneSignal(BlockState pState, Level pLevel, BlockPos pPos) {
        int tickCounter = 0;

        System.out.println("begin");
        pLevel.setBlock(pPos, pState.cycle(POWERED), 3);

        if (tickCounter < 20) {
            tickCounter++;
        } else {
            System.out.println("end");
            pLevel.setBlock(pPos, pState.cycle(POWERED), 3);

            tickCounter = 0;
        }
    }

    public int getSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
       return (Boolean)pBlockState.getValue(POWERED) ? 15 : 0;
    }

    public boolean isSignalSource(BlockState pState) {
        return true;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
    }

    private void updateNeighbours(BlockState pState, Level pLevel, BlockPos pPos) {
        pLevel.updateNeighborsAt(pPos, this);
        pLevel.updateNeighborsAt(pPos.above(), this);
    }

    static {
        POWERED = BlockStateProperties.POWERED;
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);

        FiniteAutomataMod.dfa.setOutputBlockPos(pPos);
    }
}
