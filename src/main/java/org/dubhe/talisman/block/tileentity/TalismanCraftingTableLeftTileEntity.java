package org.dubhe.talisman.block.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.dubhe.talisman.block.TalismanCraftingTableBlock;
import org.dubhe.talisman.registry.TBaseValue;
import org.dubhe.talisman.inventory.TalismanCraftingInventory;
import org.dubhe.talisman.recipe.OutputAndDemand;
import org.dubhe.talisman.recipe.TalismanRecipe;
import org.dubhe.talisman.registry.TRecipes;
import org.dubhe.talisman.registry.TTileEntityTypes;
import org.dubhe.talisman.block.container.TalismanCraftingTableContainer;

import java.util.Optional;

@SuppressWarnings("NullableProblems")
public class TalismanCraftingTableLeftTileEntity extends LockableTileEntity implements ITickableTileEntity {
    private final TalismanCraftingInventory craftingInventory = new TalismanCraftingInventory();
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(3, ItemStack.EMPTY);
    private int experience = 0;

    public TalismanCraftingTableLeftTileEntity() {
        super(TTileEntityTypes.TALISMAN_CRAFTING_TABLE.get());
    }

    public void setContainer(Container container) {
        this.craftingInventory.setHandler(container);
    }

    public TalismanCraftingInventory getCraftingInventory() {
        return this.craftingInventory;
    }

    @Override
    public void clear() {
        this.inventory.clear();
        this.craftingInventory.clear();
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
    @SuppressWarnings("ConstantConditions")
    public boolean isUsableByPlayer(PlayerEntity player) {
        if (this.world.getTileEntity(this.pos) != this) {
            return false;
        } else {
            return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        if (index < 9) return this.craftingInventory.removeStackFromSlot(index);
        else return this.inventory.set(index - 9, ItemStack.EMPTY);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        if (index < 9) return this.craftingInventory.decrStackSize(index, count);
        else {
            ItemStack itemStack = this.inventory.get(index - 9);
            int i = Math.min(count, itemStack.getCount());
            ItemStack stack = itemStack.copy();
            stack.setCount(i);
            itemStack.shrink(i);
            return stack;
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
    public boolean isEmpty() {
        for (int i = 0; i < this.craftingInventory.getSizeInventory(); i++) {
            if (!this.craftingInventory.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }

        for(ItemStack stack : this.inventory) {
            if (!stack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        NonNullList<ItemStack> inv = NonNullList.withSize(12, ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, inv);
        this.setItems(inv);
        this.experience = nbt.getInt("experience");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        ItemStackHelper.saveAllItems(compound, this.getItems());
        compound.putInt("experience", this.experience);
        return compound;
    }

    private NonNullList<ItemStack> getItems() {
        NonNullList<ItemStack> inv = NonNullList.withSize(12, ItemStack.EMPTY);

        for (int i = 0; i < this.craftingInventory.getSizeInventory(); i++) {
            inv.set(i, this.craftingInventory.getStackInSlot(i));
        }
        for (int i = 0; i < this.inventory.size(); i++) {
            inv.set(i + this.craftingInventory.getSizeInventory(), this.inventory.get(i));
        }
        return inv;
    }

    private void setItems(NonNullList<ItemStack> items) {
        for (int i = 0; i < items.size(); i++) {
            if (i < 9) this.craftingInventory.setInventorySlotContents(i, items.get(i));
            else this.inventory.set(i - 9, items.get(i));
        }
    }

    @SuppressWarnings("ConstantConditions")
    public OutputAndDemand getResult() {
        if (!this.world.isRemote) {
            Optional<TalismanRecipe> optional = getWorld().getServer().getRecipeManager().getRecipe(TRecipes.TALISMAN_CRAFTING_TYPE, this.craftingInventory, this.world);
            if (optional.isPresent()) {
                OutputAndDemand output = optional.get().getOutput();
                if (output.getExperience() <= this.experience && !this.inventory.get(0).isEmpty() && !this.inventory.get(2).isEmpty())
                    return output;
            }
        }
        return OutputAndDemand.EMPTY;
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void tick() {
        if (this.inventory.get(1).getItem() == Items.EXPERIENCE_BOTTLE && this.experience <= TBaseValue.MAX_EXP - 5) {
            this.inventory.get(1).shrink(1);
            this.addExperience(5);
        }
        this.craftingInventory.onCraftMatrixChanged();

        if (!this.world.isRemote) {
            boolean pen = this.world.getBlockState(this.pos).get(TalismanCraftingTableBlock.PEN);
            boolean ink = this.world.getBlockState(this.pos).get(TalismanCraftingTableBlock.INK);
            boolean flag = false;
            if (pen == this.inventory.get(0).isEmpty()) {
                pen = !pen;
                flag = true;
            }
            if (ink == this.inventory.get(2).isEmpty()) {
                ink = !ink;
                flag = true;
            }
            if (flag) {
                BlockState left = this.world.getBlockState(this.pos);
                BlockPos rightPos = this.pos.offset(TalismanCraftingTableBlock.getRightDirection(left.get(TalismanCraftingTableBlock.HORIZONTAL_FACING)));
                BlockState right = this.world.getBlockState(rightPos);
                this.world.setBlockState(this.pos, left.with(TalismanCraftingTableBlock.PEN, pen).with(TalismanCraftingTableBlock.INK, ink), 3);
                this.world.setBlockState(rightPos, right.with(TalismanCraftingTableBlock.PEN, pen).with(TalismanCraftingTableBlock.INK, ink), 3);
                this.markDirty();
            }

        }
    }

    public int getExperience() {
        return this.experience;
    }

    public void setExperience(int value) {
        this.experience = value;
    }

    public void addExperience(int count) {
        this.experience += count;
    }

    public void shrinkExperience(int count) {
        this.addExperience(-count);
    }

}
