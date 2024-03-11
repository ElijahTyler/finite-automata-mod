package net.devuser.finiteautomatamod.block.custom;

import net.devuser.finiteautomatamod.block.entity.ModBlockEntities;
import net.devuser.finiteautomatamod.block.entity.NewEntityBlockEntity;
import net.devuser.finiteautomatamod.client.screen.ClientHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NewEntityBlock extends Block implements EntityBlock {
    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 12, 16);

    public NewEntityBlock(Properties pProperties) {
        super(pProperties);
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return ModBlockEntities.NEW_ENTITY_BE.get().create(pPos, pState);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
//        if (!pLevel.isClientSide()) {
//            BlockEntity entity = pLevel.getBlockEntity(pPos);
//            if(entity instanceof NewEntityBlockEntity blockEntity) {
//                int counter = pPlayer.isCrouching() ? blockEntity.getCounter() : blockEntity.incrementCounter();
//                pPlayer.sendSystemMessage(Component.literal("BlockEntity has been used %d times".formatted(counter)));
//
//                NetworkHooks.openScreen(((ServerPlayer)pPlayer), (NewEntityBlockEntity)entity, pPos);
//            } else {
//                throw new IllegalStateException("Our Container provider is missing!");
//            }
        if(pHand != InteractionHand.MAIN_HAND) return InteractionResult.PASS;
        if(!pLevel.isClientSide()) return InteractionResult.SUCCESS;

        BlockEntity be = pLevel.getBlockEntity(pPos);
        if(be instanceof NewEntityBlockEntity) {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientHooks.openExampleBlockScreen(pPos));
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }
        //}

       // return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }







//    @Override
//    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
//        super.createBlockStateDefinition(pBuilder);
//    }
//
//    @Override
//    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
//        return SHAPE;
//    }
//
//    @Override
//    public RenderShape getRenderShape(BlockState pState) {
//        return RenderShape.MODEL;
//    }
//
//    @Override
//    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
//        if (pState.getBlock() != pNewState.getBlock()) {
//            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
//            if (blockEntity instanceof NewEntityBlockEntity) {
//                ((NewEntityBlockEntity) blockEntity).drops();
//            }
//        }
//
//        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
//    }


//}