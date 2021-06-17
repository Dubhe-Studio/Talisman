package org.dubhe.talisman.block.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.dubhe.talisman.inventory.TalismanCraftingInventory;
import org.dubhe.talisman.recipe.TalismanRecipe;
import org.dubhe.talisman.registry.RecipeRegistry;
import org.dubhe.talisman.registry.TileEntityTypeRegistry;
import org.dubhe.talisman.container.TalismanCraftingTableContainer;

import java.util.Optional;

@SuppressWarnings("NullableProblems")
public class TalismanCraftingTableLeftTileEntity extends LockableLootTileEntity implements ITickableTileEntity {

    private final NonNullList<ItemStack> inventory = NonNullList.withSize(3, ItemStack.EMPTY);
    private final TalismanCraftingInventory craftingInventory = new TalismanCraftingInventory();

    public TalismanCraftingTableLeftTileEntity() {
        super(TileEntityTypeRegistry.TALISMAN_CRAFTING_TABLE.get());
    }

    public void setContainer(Container container) {
        this.craftingInventory.setHandler(container);
    }

    public TalismanCraftingInventory getCraftingInventory() {
        return this.craftingInventory;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        NonNullList<ItemStack> inv = NonNullList.withSize(12, ItemStack.EMPTY);

        for (int i = 0; i < this.craftingInventory.getSizeInventory(); i++) {
            inv.set(i, this.craftingInventory.getStackInSlot(i));
        }
        for (int i = 0; i < this.inventory.size(); i++) {
            inv.set(i + this.craftingInventory.getSizeInventory(), this.inventory.get(i));
        }
        return inv;
    }

    @Override
    public void clear() {
        this.inventory.clear();
        this.craftingInventory.clear();
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        for (int i = 0; i < items.size(); i++) {
            if (i < 9) this.craftingInventory.setInventorySlotContents(i, items.get(i));
            else this.inventory.set(i - 9, items.get(i));
        }
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        if (index < 9) return this.craftingInventory.getStackInSlot(index);
        else return this.inventory.get(index - 9);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        if (index < 9) this.craftingInventory.setInventorySlotContents(index, stack);
        else this.inventory.set(index - 9, stack);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        if (index < 9) return this.craftingInventory.removeStackFromSlot(index);
        else return this.inventory.remove(index - 9);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        if (index < 9) return this.craftingInventory.decrStackSize(index, count);
        else {
            ItemStack itemStack = this.inventory.get(index - 9);
            itemStack.setCount(itemStack.getCount() - count);
            return itemStack;
        }
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
        return 12;
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        NonNullList<ItemStack> inv = NonNullList.withSize(12, ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, inv);
        this.setItems(inv);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        ItemStackHelper.saveAllItems(compound, this.getItems());
        return compound;
    }

    @SuppressWarnings("ConstantConditions")
    public ItemStack getResult() {
        ItemStack itemStack = ItemStack.EMPTY;
        if (!this.world.isRemote) {
            Optional<TalismanRecipe> optional = getWorld().getServer().getRecipeManager().getRecipe(RecipeRegistry.TALISMAN_CRAFTING_TYPE, this.craftingInventory, this.world);
            if (optional.isPresent()) {
                TalismanRecipe craftingRecipe = optional.get();
                itemStack = craftingRecipe.getCraftingResult(this.craftingInventory).copy();
            }
        }
        return itemStack;
    }

    @Override
    public void tick() {

    }
}
