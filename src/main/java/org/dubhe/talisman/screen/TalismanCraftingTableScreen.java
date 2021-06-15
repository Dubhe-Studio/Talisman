package org.dubhe.talisman.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import org.dubhe.talisman.ModInitializer;
import org.dubhe.talisman.container.TalismanCraftingTableContainer;

@SuppressWarnings("NullableProblems")
public class TalismanCraftingTableScreen extends ContainerScreen<TalismanCraftingTableContainer> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ModInitializer.MODID, "textures/gui/container/talisman_crafting_table.png");
    int texWidth = 176;
    int texHeight = 166;

    public TalismanCraftingTableScreen(TalismanCraftingTableContainer container, PlayerInventory inv, ITextComponent title) {
        super(container, inv, title);
        this.passEvents = false;
        this.xSize = this.texWidth;
        this.ySize = this.texHeight;
    }

    @Override
    public void render(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrix);
        super.render(matrix, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrix, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrix, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.blit(matrix , i, j, 0, 0, this.xSize, this.ySize);
    }
}
