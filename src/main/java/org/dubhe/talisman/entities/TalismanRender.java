package org.dubhe.talisman.entities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import org.dubhe.talisman.ModInitializer;

@SuppressWarnings("NullableProblems")
public class TalismanRender extends EntityRenderer<TalismanEntity> {

    private final EntityModel<TalismanEntity> talismanModel;

    public TalismanRender(EntityRendererManager renderManager) {
        super(renderManager);
        this.talismanModel = new TalismanModel();
    }

    @Override
    public ResourceLocation getEntityTexture(TalismanEntity entity) {
        return new ResourceLocation(ModInitializer.MODID, "textures/entity/talisman.png");
    }

    @Override
    public void render(TalismanEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLightIn) {
        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLightIn);
        matrixStack.push();
        matrixStack.rotate(Vector3f.YN.rotationDegrees(45));
        IVertexBuilder ivertexbuilder = buffer.getBuffer(this.talismanModel.getRenderType(this.getEntityTexture(entity)));
        this.talismanModel.render(matrixStack, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.pop();
    }
}
