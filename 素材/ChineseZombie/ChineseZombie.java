// Made with Blockbench 3.8.4
// Exported for Minecraft version 1.15 - 1.16
// Paste this class into your mod and generate all required imports


public class ChineseZombie extends EntityModel<Entity> {
	private final ModelRenderer head;
	private final ModelRenderer hat;
	private final ModelRenderer body;
	private final ModelRenderer arm_left;
	private final ModelRenderer arm_right;
	private final ModelRenderer leg_right;
	private final ModelRenderer leg_left;

	public ChineseZombie() {
		textureWidth = 128;
		textureHeight = 128;

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 24.0F, 0.0F);
		head.setTextureOffset(0, 0).addBox(-4.0F, -32.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
		head.setTextureOffset(32, 0).addBox(-4.0F, -32.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.1F, false);

		hat = new ModelRenderer(this);
		hat.setRotationPoint(0.0F, -34.0F, 0.0F);
		head.addChild(hat);
		hat.setTextureOffset(0, 124).addBox(-1.0F, -2.5F, -1.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);
		hat.setTextureOffset(0, 119).addBox(-2.0F, -1.5F, -2.0F, 4.0F, 1.0F, 4.0F, 0.0F, false);
		hat.setTextureOffset(0, 112).addBox(-3.0F, -0.5F, -3.0F, 6.0F, 1.0F, 6.0F, 0.0F, false);
		hat.setTextureOffset(0, 103).addBox(-4.0F, 0.5F, -4.0F, 8.0F, 1.0F, 8.0F, 0.0F, false);
		hat.setTextureOffset(0, 92).addBox(-5.0F, 1.5F, -5.0F, 10.0F, 1.0F, 10.0F, 0.0F, false);
		hat.setTextureOffset(0, 79).addBox(-6.0F, 2.5F, -6.0F, 12.0F, 1.0F, 12.0F, 0.0F, false);
		hat.setTextureOffset(0, 64).addBox(-7.0F, 3.5F, -7.0F, 14.0F, 1.0F, 14.0F, 0.0F, false);

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 24.0F, 0.0F);
		body.setTextureOffset(16, 32).addBox(-4.0F, -24.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.1F, false);
		body.setTextureOffset(16, 16).addBox(-4.0F, -24.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);

		arm_left = new ModelRenderer(this);
		arm_left.setRotationPoint(-6.0F, 2.0F, 0.0F);
		setRotationAngle(arm_left, -1.5708F, 0.0F, 0.0F);
		arm_left.setTextureOffset(48, 48).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.1F, false);
		arm_left.setTextureOffset(32, 48).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

		arm_right = new ModelRenderer(this);
		arm_right.setRotationPoint(6.0F, 2.0F, 0.0F);
		setRotationAngle(arm_right, -1.5708F, 0.0F, 0.0F);
		arm_right.setTextureOffset(48, 32).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.1F, false);
		arm_right.setTextureOffset(40, 16).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

		leg_right = new ModelRenderer(this);
		leg_right.setRotationPoint(0.0F, 24.0F, 0.0F);
		leg_right.setTextureOffset(0, 16).addBox(-4.0F, -12.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
		leg_right.setTextureOffset(0, 32).addBox(-4.0F, -12.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.1F, false);

		leg_left = new ModelRenderer(this);
		leg_left.setRotationPoint(0.0F, 24.0F, 0.0F);
		leg_left.setTextureOffset(0, 48).addBox(0.0F, -12.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.1F, false);
		leg_left.setTextureOffset(16, 48).addBox(0.0F, -12.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		head.render(matrixStack, buffer, packedLight, packedOverlay);
		body.render(matrixStack, buffer, packedLight, packedOverlay);
		arm_left.render(matrixStack, buffer, packedLight, packedOverlay);
		arm_right.render(matrixStack, buffer, packedLight, packedOverlay);
		leg_right.render(matrixStack, buffer, packedLight, packedOverlay);
		leg_left.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}