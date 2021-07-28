package org.dubhe.talisman.screen;

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
import org.dubhe.talisman.container.TCTContainer;
import org.dubhe.talisman.registry.TBaseValue;
import org.lwjgl.opengl.GL11;

import java.util.List;

@SuppressWarnings("NullableProblems")
public class TCTScreen extends ContainerScreen<TCTContainer> {
    private static final ResourceLocation TEXTURE = ModInitializer.getIdentifier("textures/gui/container/talisman_crafting_table.png");
    private static final ITextComponent NO_PEN = new TranslationTextComponent("tooltip.talisman.nopen").mergeStyle(TextFormatting.RED);
    private static final ITextComponent NO_INK = new TranslationTextComponent("tooltip.talisman.noink").mergeStyle(TextFormatting.RED);
    int texWidth = 176;
    int texHeight = 166;
    private double alpha = 0;

    public TCTScreen(TCTContainer container, PlayerInventory inv, ITextComponent title) {
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
        if (this.container.getNeedXp() > -1) {
            this.alpha += Math.PI / 15; // flash (10/value) times/s
            this.alpha %= Math.PI * 2;
        }else {
            this.alpha = 0.0F;
        }
    }

    @Override
    @SuppressWarnings({"ConstantConditions", "deprecation"})
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrix, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        int xp = this.container.getExperience();
        int needXp = this.container.getNeedXp();
        int lack = this.container.getLack();
        int width = (int) ((double) xp / TBaseValue.MAX_EXP * 40);
        this.blit(matrix, i, j, 0, 0, this.xSize, this.ySize);
        switch (lack) {
            case 1: this.blit(matrix, i + 15, j + 18, 176, 0, 16, 16);
            case 2: this.blit(matrix, i + 15, j + 54, 176, 16, 16, 16);break;
            case 3: this.blit(matrix, i + 15, j + 18, 176, 0, 16, 16);
        }
        if (needXp > -1) {
            int last = (int) ((double) (xp - needXp) / TBaseValue.MAX_EXP * 40);
            this.blit(matrix, i + 116, j + 66, 176, 32, last, 5);
            RenderSystem.enableBlend();
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, (float) (Math.cos(this.alpha) / 2 + 0.5D));
            RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            this.blit(matrix, i + last + 116, j + 66, last + 176, 32, width - last, 5);
            RenderSystem.disableBlend();
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        }else {
            this.blit(matrix, i + 116, j + 66, 176, 32, width, 5);
        }
        if (x > i + 116 && x < i + 116 + 40 && y > j + 66 && y < j + 66 + 5) {
            List<ITextComponent> texts = Lists.newArrayList();
            texts.add(new TranslationTextComponent("container.talisman.xp", xp, TBaseValue.MAX_EXP));
            if (needXp > -1) {
                texts.add(new TranslationTextComponent("container.talisman.xp.requirement",
                        new TranslationTextComponent("text.all", needXp)
                                .mergeStyle(xp >= needXp ? TextFormatting.GREEN : TextFormatting.RED)));
                switch (lack) {
                    case 1: texts.add(NO_PEN);
                    case 2: texts.add(NO_INK);break;
                    case 3: texts.add(NO_PEN);
                }
            }
            this.func_243308_b(matrix, texts, x, y);
        }
    }
}
