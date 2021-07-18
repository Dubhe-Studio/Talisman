package org.dubhe.talisman.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class TIngredient extends Ingredient {

    public TIngredient(Stream<? extends IItemList> itemLists) {
        super(itemLists);
    }

    @Override
    public boolean test(@Nullable ItemStack stack) {
        if (stack == null) {
            return false;
        } else {
            ItemStack[] matchingStacks = this.getMatchingStacks();
            if (matchingStacks.length == 0) {
                return stack.isEmpty();
            } else {
                for(ItemStack itemstack : matchingStacks) {
                    if (ItemStack.areItemStackTagsEqual(itemstack, stack)) {
                        return true;
                    }
                }

                return false;
            }
        }
    }
}
