package org.dubhe.talisman.block.screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import org.dubhe.talisman.ModInitializer;
import org.dubhe.talisman.block.container.TalismanCraftingTableContainer;
import org.dubhe.talisman.registry.TBaseValue;
import org.lwjgl.opengl.GL11;

import java.util.List;

@SuppressWarnings("NullableProblems")
public class TalismanCraftingTableScreen extends ContainerScreen<TalismanCraftingTableContainer> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ModInitializer.MODID, "textures/gui/container/talisman_crafting_table.png");
    int texWidth = 176;
    int texHeight = 166;
    private float alpha = 1.0F;
    private boolean positive = true;

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
    public void tick() {
        super.tick();
        if (this.alpha >= 1.0F) this.positive = false;
        if (this.alpha <= 0.0F) this.positive = true;
        if (positive) this.alpha += 0.1F;
        else this.alpha -= 0.1F;
    }

    @Override
    @SuppressWarnings({"ConstantConditions", "deprecation"})
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrix, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        int xp = this.container.getExperience();
        int needExp = this.container.getNeedExp();
        int width = (int) ((double) xp / TBaseValue.MAX_EXP * 40);
        this.blit(matrix, i, j, 0, 0, this.xSize, this.ySize);
        if (needExp > -1) {
            int last = (int) ((double) (xp - needExp) / TBaseValue.MAX_EXP * 40);
            this.blit(matrix, i + 116, j + 66, 0, 166, last, 5);
            RenderSystem.enableBlend();
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
            RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            this.blit(matrix, i + last + 116, j + 66, last, 166, width - last, 5);
            RenderSystem.disableBlend();
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        }else {
            this.blit(matrix, i + 116, j + 66, 0, 166, width, 5);
        }
        if (x > i + 116 && x < i + 116 + 40 && y > j + 66 && y < j + 66 + 5) {
            List<ITextComponent> texts = Lists.newArrayList();
            texts.add(new TranslationTextComponent("container.talisman.xp", xp, TBaseValue.MAX_EXP));
            if (needExp > -1) texts.add(new TranslationTextComponent("container.talisman.xp.requirement", new TranslationTextComponent("text.all", needExp).mergeStyle(TextFormatting.GREEN)));
            this.func_243308_b(matrix, texts, x, y);
        }
    }
}
