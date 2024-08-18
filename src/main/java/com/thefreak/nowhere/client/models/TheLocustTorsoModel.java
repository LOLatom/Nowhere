package com.thefreak.nowhere.client.models;// Made with Blockbench 4.10.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class TheLocustTorsoModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	private final ModelPart Main;

	public TheLocustTorsoModel(ModelPart root) {
		this.Main = root.getChild("Main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Main = partdefinition.addOrReplaceChild("Main", CubeListBuilder.create().texOffs(58, 71).addBox(10.0F, -14.5F, -1.5F, 2.0F, 20.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(16, 87).addBox(10.0F, -12.5F, 0.0F, 5.0F, 16.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(58, 71).mirror().addBox(-12.0F, -14.5F, -1.5F, 2.0F, 20.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(16, 87).mirror().addBox(-15.0F, -12.5F, 0.0F, 5.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 20).addBox(-8.0F, -15.5F, -3.5F, 16.0F, 11.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(20, 56).addBox(-3.0F, -4.5F, -2.5F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(96, 87).addBox(2.0F, -4.5F, 0.5F, 7.0F, 11.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(96, 16).addBox(2.0F, -4.5F, 0.5F, 7.0F, 11.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(95, 45).addBox(-9.0F, -4.5F, 0.5F, 7.0F, 11.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(100, 66).addBox(-1.0F, -26.5F, 0.5F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(86, 34).addBox(0.0F, -26.5F, 1.5F, 8.0F, 11.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(68, 57).addBox(8.0F, -15.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(14, 55).addBox(-10.0F, -15.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -35.5F, 2.5F));

		PartDefinition cube_r1 = Main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(96, 98).addBox(-7.0F, -5.0F, 0.0F, 7.0F, 10.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(0, 91).addBox(-7.0F, 5.0F, 0.0F, 7.0F, 11.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(54, 94).addBox(-7.0F, 5.0F, 0.0F, 7.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.5F, 3.5F, 0.0F, 1.0036F, 0.0F));

		PartDefinition cube_r2 = Main.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(99, 56).addBox(-7.0F, -5.0F, 0.0F, 7.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.5F, 3.5F, 0.0F, 1.4835F, 0.0F));

		PartDefinition cube_r3 = Main.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(68, 98).addBox(0.0F, -11.0F, 0.0F, 7.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -15.5F, 1.5F, 0.0F, 0.6109F, 0.0F));

		PartDefinition cube_r4 = Main.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(82, 98).addBox(0.0F, -11.0F, 0.0F, 7.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -15.5F, 1.5F, 0.0F, -3.098F, 0.0F));

		PartDefinition cube_r5 = Main.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(44, 87).mirror().addBox(-4.0F, -2.0F, 0.0F, 5.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-13.0F, -10.5F, 0.0F, 0.0F, -0.6545F, 0.0F));

		PartDefinition cube_r6 = Main.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(26, 87).addBox(-1.0F, -2.0F, 0.0F, 5.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, -10.5F, 0.0F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r7 = Main.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(44, 87).addBox(-1.0F, -2.0F, 0.0F, 5.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, -10.5F, 0.0F, 0.0F, -0.829F, 0.0F));

		return LayerDefinition.create(meshdefinition, 451, 546);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}