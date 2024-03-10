package net.bmwolfe.tutorialmod.block.entity;

import com.google.common.collect.Lists;
import net.bmwolfe.tutorialmod.TutorialMod;
import net.bmwolfe.tutorialmod.item.ModItems;
import net.bmwolfe.tutorialmod.screen.NewEntityMenu;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.CommandBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NewEntityBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(2);

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;
    private int counter;


    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;

    private EditBox machineIdentifier;
    private EditBox machineInput;
    private String newInput;


    public NewEntityBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.NEW_ENTITY_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch (i){
                    case 0 -> NewEntityBlockEntity.this.progress;
                    case 1 -> NewEntityBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int i1) {
                switch (i){
                    case 0 -> NewEntityBlockEntity.this.progress = i;
                    case 1 -> NewEntityBlockEntity.this.maxProgress = i;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        };

    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER){
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(()-> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    public void drops(){
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++){
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.tutorialmod.new_entity");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new NewEntityMenu(i, inventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("new_entity.progress", progress);
        pTag.putInt("Counter", this.counter);

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        //This is to avoid conflicts with other mods only
        //CompoundTag tutroialmodData = pTag.getCompound(TutorialMod.MOD_ID);

        this.counter=pTag.getInt("Counter");

        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("new_entity.progress");
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {


    }


    public EditBox changeBox(){
        //this.machineInput = EditBox.newInput;
        return this.machineInput;
    }



    public EditBox getInput(){
        return this.machineInput;
    }

    public int incrementCounter(){
        this.counter++;
        //setChanged() -----> to save data after save and quit, but it already saves?
        return this.counter;
    }

    public int getCounter(){
        return this.counter;
    }



}
