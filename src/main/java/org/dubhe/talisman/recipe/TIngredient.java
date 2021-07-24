package org.dubhe.talisman.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.IIngredientSerializer;

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
                    if (stacksEqual(itemstack, stack)) {
                        return true;
                    }
                }

                return false;
            }
        }
    }

    private static boolean stacksEqual(ItemStack stack1, ItemStack stack2) {
        if (stack1.isEmpty() && stack2.isEmpty()) {
            return true;
        } else if (!stack1.isEmpty() && !stack2.isEmpty()) {
            if (stack1.getItem() != stack2.getItem()) {
                return false;
            } else if (stack1.getTag() == null && stack2.getTag() != null) {
                return false;
            } else {
                return (stack1.getTag() == null || stack1.getTag().equals(stack2.getTag())) && stack1.areCapsCompatible(stack2);
            }
        } else {
            return false;
        }
    }

    @Override
    public IIngredientSerializer<? extends Ingredient> getSerializer() {
        return net.minecraftforge.common.crafting.VanillaIngredientSerializer.INSTANCE;
    }
//
//    public static class Serializer implements IIngredientSerializer<TIngredient>
//    {
//        public static final Serializer INSTANCE = new Serializer();
//
//        @Override
//        public TIngredient parse(PacketBuffer buffer)
//        {
//            return new TIngredient(Stream.generate(() -> Ingredient.read(buffer)).limit(buffer.readVarInt()).collect(Collectors.toList()));
//        }
//
//        @Override
//        public TIngredient parse(JsonObject json)
//        {
//            throw new JsonSyntaxException("CompoundIngredient should not be directly referenced in json, just use an array of ingredients.");
//        }
//
//        @Override
//        public void write(PacketBuffer buffer, TIngredient ingredient)
//        {
//            buffer.writeVarInt(ingredient.children.size());
//            ingredient.children.forEach(c -> c.write(buffer));
//        }
//
//    }

}
