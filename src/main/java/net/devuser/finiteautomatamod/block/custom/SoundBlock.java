package net.devuser.finiteautomatamod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class SoundBlock extends Block {

    public static final BooleanProperty LIT = BooleanProperty.create("lit");
    public static final BooleanProperty POWERED;


    public SoundBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState((BlockState)this.defaultBlockState().setValue(LIT, false));
        this.registerDefaultState((BlockState)this.defaultBlockState().setValue(POWERED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(LIT);
        pBuilder.add(POWERED);

    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                 Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        pLevel.playSound(pPlayer, pPos, SoundEvents.NOTE_BLOCK_FLUTE.get(), SoundSource.BLOCKS,
                1f, 1f);

        if (!pLevel.isClientSide && pHand == InteractionHand.MAIN_HAND) {
            pLevel.setBlock(pPos, pState.cycle(LIT), 3);
           // pLevel.setBlock(pPos, pState.cycle(POWERED), 3); FIGURE OUT WHY I CANT HAVE BOTH
        }

        return InteractionResult.SUCCESS;
    }

    public int getSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
       return (Boolean)pBlockState.getValue(POWERED) ? 15 : 0;
    }

    public boolean isSignalSource(BlockState pState) {
        return true;
    }

    private void updateNeighbours(BlockState pState, Level pLevel, BlockPos pPos) {
        pLevel.updateNeighborsAt(pPos, this);
        pLevel.updateNeighborsAt(pPos.above(), this);
    }

    static {
        POWERED = BlockStateProperties.POWERED;
    }
}
