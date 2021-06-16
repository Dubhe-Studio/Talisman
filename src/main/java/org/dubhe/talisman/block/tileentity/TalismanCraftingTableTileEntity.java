package org.dubhe.talisman.block.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.dubhe.talisman.registry.TileEntityTypeRegistry;
import org.dubhe.talisman.container.TalismanCraftingTableContainer;

@SuppressWarnings("NullableProblems")
public class TalismanCraftingTableTileEntity extends LockableLootTileEntity {

    private NonNullList<ItemStack> inventory = NonNullList.withSize(13, ItemStack.EMPTY);

    public TalismanCraftingTableTileEntity() {
        super(TileEntityTypeRegistry.TALISMAN_CRAFTING_TABLE.get());
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.inventory = items;
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.talisman_crafting_table");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory inventory) {
        return new TalismanCraftingTableContainer(id, inventory, this);
    }

    @Override
    public int getSizeInventory() {
        return 13;
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        ItemStackHelper.loadAllItems(nbt, this.inventory);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        ItemStackHelper.saveAllItems(compound, this.inventory);
        return compound;
    }
}
