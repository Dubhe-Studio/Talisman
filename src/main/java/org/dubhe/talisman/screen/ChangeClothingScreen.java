package org.dubhe.talisman.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import org.dubhe.talisman.container.ChangeClothingContainer;

public class ChangeClothingScreen extends ContainerScreen<ChangeClothingContainer> {

    public ChangeClothingScreen(ChangeClothingContainer container, PlayerInventory inv, ITextComponent title) {
        super(container, inv, title);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrix, float partialTicks, int x, int y) {

    }
}
