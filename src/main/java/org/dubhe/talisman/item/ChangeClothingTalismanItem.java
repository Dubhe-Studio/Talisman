package org.dubhe.talisman.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import org.dubhe.talisman.talisman.AbstractTalisman;

import javax.annotation.Nullable;

public class ChangeClothingTalismanItem extends TalismanItem {

    public ChangeClothingTalismanItem(@Nullable AbstractTalisman execute, Properties properties) {
        super(false, execute, properties);
    }

    @Override
    public CompoundNBT defaultNbt(CompoundNBT nbt) {
        nbt.put("Armors", new ListNBT());
        return super.defaultNbt(nbt);
    }

    @Override
    protected void onTalismanUsed(World world, PlayerEntity player, ItemStack item) {
    }

    @SuppressWarnings("ConstantConditions")
    public static void read(NonNullList<ItemStack> armors, ItemStack item) {
        if (item.isEmpty() || !item.hasTag() || !item.getTag().contains("Armors")) return;
        ListNBT listnbt = item.getTag().getList("Armors", 10);
        for(int i = 0; i < listnbt.size(); ++i) {
            CompoundNBT compoundnbt = listnbt.getCompound(i);
            int j = compoundnbt.getByte("Slot") & 255;
            if (j >= 0 && j < armors.size()) armors.set(j, ItemStack.read(compoundnbt));
        }
    }

    public static void write(NonNullList<ItemStack> armors, ItemStack item) {
        write(armors, item, false);
    }

    public static void write(NonNullList<ItemStack> armors, ItemStack item, boolean reverse) {
        if (item.isEmpty()) return;
        ListNBT listnbt = new ListNBT();
        for(int i = 0; i < armors.size(); ++i) {
            ItemStack itemstack = armors.get(reverse ? armors.size() - i - 1 : i);
            if (!itemstack.isEmpty()) {
                CompoundNBT nbt = new CompoundNBT();
                nbt.putByte("Slot", (byte)i);
                itemstack.write(nbt);
                listnbt.add(nbt);
            }
        }
        item.getOrCreateTag().put("Armors", listnbt);
    }

    public static boolean isEmpty(NonNullList<ItemStack> armors) {
        for (ItemStack armor : armors) {
            if (!armor.isEmpty()) return false;
        }
        return true;

    }

    public static boolean canUse(ItemStack item) {
        return !item.isDamageable() || item.getDamage() < item.getMaxDamage();
    }
}
