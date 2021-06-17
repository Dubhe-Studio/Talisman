package org.dubhe.talisman.block.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import org.dubhe.talisman.registry.TileEntityTypeRegistry;

@SuppressWarnings("NullableProblems")
public class TalismanCraftingTableRightTileEntity extends TileEntity implements ITickableTileEntity {

    private TalismanCraftingTableLeftTileEntity leftTileEntity;

    public TalismanCraftingTableRightTileEntity() {
        super(TileEntityTypeRegistry.TALISMAN_CRAFTING_TABLE.get());
    }


    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        NonNullList<ItemStack> inv = NonNullList.withSize(12, ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, inv);
        leftTileEntity.setItems(inv);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        ItemStackHelper.saveAllItems(compound, leftTileEntity.getItems());
        return compound;
    }

    @Override
    public void tick() {

    }
}
