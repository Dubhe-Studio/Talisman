package org.dubhe.talisman.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import org.dubhe.talisman.ModInitializer;

@SuppressWarnings("NullableProblems")
public class TalismanRender extends EntityRenderer<TalismanEntity> {
    public TalismanRender(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getEntityTexture(TalismanEntity entity) {
        return new ResourceLocation(ModInitializer.MODID, "textures/entity/talisman.png");
    }

    @Override
    public void render(TalismanEntity entity, float entityYaw, float partialTicks, MatrixStack matrix, IRenderTypeBuffer buffer, int packedLight) {
        matrix.push();
        // face
        matrix.rotate(Vector3f.YP.rotationDegrees(270.0F - entityYaw));
        matrix.rotate(Vector3f.ZP.rotationDegrees(-entity.rotationPitch));
        // size
        matrix.scale(0.525F, 0.525F, 0.525F);
        matrix.translate(0, 0.8D, 0);

        IVertexBuilder ivertexbuilder = buffer.getBuffer(RenderType.getEntityCutout(this.getEntityTexture(entity)));
        MatrixStack.Entry matrixStack$entry = matrix.getLast();
        Matrix4f matrix4f = matrixStack$entry.getMatrix();
        Matrix3f matrix3f = matrixStack$entry.getNormal();

        // front
        this.drawVertex(matrix4f, matrix3f, ivertexbuilder, 0, -1, -1, 0.0F, 1.0F, -1, 0, 0, packedLight);
        this.drawVertex(matrix4f, matrix3f, ivertexbuilder, 0, -1, 1, 1.0F, 1.0F, -1, 0, 0, packedLight);
        this.drawVertex(matrix4f, matrix3f, ivertexbuilder, 0, 1, 1, 1.0F, 0.0F, -1, 0, 0, packedLight);
        this.drawVertex(matrix4f, matrix3f, ivertexbuilder, 0, 1, -1, 0.0F, 0.0F, -1, 0, 0, packedLight);
        // back
        this.drawVertex(matrix4f, matrix3f, ivertexbuilder, 0, 1, -1, 0.0F, 0.0F, 1, 0, 0, packedLight);
        this.drawVertex(matrix4f, matrix3f, ivertexbuilder, 0, 1, 1, 1.0F, 0.0F, 1, 0, 0, packedLight);
        this.drawVertex(matrix4f, matrix3f, ivertexbuilder, 0, -1, 1, 1.0F, 1.0F, 1, 0, 0, packedLight);
        this.drawVertex(matrix4f, matrix3f, ivertexbuilder, 0, -1, -1, 0.0F, 1.0F, 1, 0, 0, packedLight);

        matrix.pop();
        super.render(entity, entityYaw, partialTicks, matrix, buffer, packedLight);
    }

    public void drawVertex(Matrix4f matrix, Matrix3f normals, IVertexBuilder builder, int offsetX, int offsetY, int offsetZ, float u, float v, int normalX, int normalY, int normalZ, int packedLight) {
        builder.pos(matrix, (float)offsetX, (float)offsetY, (float)offsetZ).color(255, 255, 255, 255).tex(u, v).overlay(OverlayTexture.NO_OVERLAY).lightmap(packedLight).normal(normals, (float)normalX, (float)normalY, (float)normalZ).endVertex();
    }

}
