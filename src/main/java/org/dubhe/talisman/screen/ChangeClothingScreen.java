package org.dubhe.talisman.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dubhe.talisman.ModInitializer;
import org.dubhe.talisman.container.ChangeClothingContainer;

@OnlyIn(Dist.CLIENT)
public class ChangeClothingScreen extends ContainerScreen<ChangeClothingContainer> {
    private static final ResourceLocation[] ARMOR_SLOT_TEXTURES = new ResourceLocation[]{PlayerContainer.EMPTY_ARMOR_SLOT_BOOTS, PlayerContainer.EMPTY_ARMOR_SLOT_LEGGINGS, PlayerContainer.EMPTY_ARMOR_SLOT_CHESTPLATE, PlayerContainer.EMPTY_ARMOR_SLOT_HELMET};
    private static final ResourceLocation TEXTURE = ModInitializer.getIdentifier("textures/gui/container/change_clothing_talisman.png");

    public ChangeClothingScreen(ChangeClothingContainer container, PlayerInventory inv, ITextComponent title) {
        super(container, inv, title);
        this.passEvents = true;
        this.ySize = 133;
        this.playerInventoryTitleY = this.ySize - 94;
    }

    @Override
    public void render(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrix);
        super.render(matrix, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrix, mouseX, mouseY);
    }

    @Override
    @SuppressWarnings({"deprecation", "ConstantConditions"})
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrix, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(TEXTURE);
        int value = this.container.getArmorIndex();
        this.blit(matrix, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        for (int index = 0; index < 4; index++) {
            if ((value & 1) == 0) {
                TextureAtlasSprite textureatlassprite = this.minecraft.getAtlasSpriteGetter(PlayerContainer.LOCATION_BLOCKS_TEXTURE).apply(ARMOR_SLOT_TEXTURES[index]);
                this.minecraft.getTextureManager().bindTexture(textureatlassprite.getAtlasTexture().getTextureLocation());
                blit(matrix, this.guiLeft - index * 18 + 107, this.guiTop + 20, this.getBlitOffset(), 16, 16, textureatlassprite);
            }
            value = value >> 1;
        }
    }
}
