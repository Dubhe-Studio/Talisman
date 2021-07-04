package org.dubhe.talisman.item;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.item.ItemStack;

public interface IWithCustomModel {
    IBakedModel getModel(ItemStack stack, ModelManager manager);
}
