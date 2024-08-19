package com.thefreak.nowhere.client.renderers;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.thefreak.nowhere.Nowhere;
import com.thefreak.nowhere.client.models.TVScreenModel;
import com.thefreak.nowhere.common.blockentity.TVBlockEntity;
import com.thefreak.nowhere.common.blocks.TVBlock;
import com.thefreak.nowhere.common.initiation.LayerInitiation;
import foundry.veil.api.client.render.rendertype.VeilRenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.List;

public class TVRenderer implements BlockEntityRenderer<TVBlockEntity> {

    private final TVScreenModel<?> model;

    public TVRenderer(BlockEntityRendererProvider.Context pContext) {
        this.model = new TVScreenModel<>(pContext.bakeLayer(LayerInitiation.TV_LAYER));
    }

    @Override
    public void render(TVBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        VertexConsumer vertexConsumer = pBuffer.getBuffer(VeilRenderType.armorCutoutNoCull(new ResourceLocation(Nowhere.MODID,"textures/blockentity/tv_texture.png")));

        Direction direction = Direction.NORTH;
        if (pBlockEntity.hasLevel()) {
            BlockState blockstate = pBlockEntity.getLevel().getBlockState(pBlockEntity.getBlockPos());
            if (blockstate.getBlock() instanceof TVBlock) {
                direction = blockstate.getValue(TVBlock.FACING);
            }
        }
        pPoseStack.pushPose();
        pPoseStack.translate(0.5F, 0.5F, 0.5F);
        float f = 0.9995F;
        pPoseStack.scale(0.9995F, 0.9995F, 0.9995F);
        //pPoseStack.mulPose(Direction.EAST.getRotation());
        //pPoseStack.mulPose(direction.getRotation());
        if (direction == Direction.EAST) {
            pPoseStack.mulPose(Direction.WEST.getRotation());

            pPoseStack.mulPose(Direction.NORTH.getRotation());
        } else if (direction == Direction.WEST) {
            pPoseStack.mulPose(Direction.EAST.getRotation());

            pPoseStack.mulPose(Direction.NORTH.getRotation());
        } else if (direction == Direction.NORTH) {
            pPoseStack.mulPose(Direction.SOUTH.getRotation());
            pPoseStack.mulPose(Direction.NORTH.getRotation());

        } else if (direction == Direction.SOUTH) {

        }

        pPoseStack.scale(1.0F, -1.0F, -1.0F);
        pPoseStack.translate(0.0F, -1.0F, 0.0F);
        this.model.renderToBuffer(pPoseStack,vertexConsumer,pPackedLight,pPackedOverlay,1,1,1,1);
        pPoseStack.popPose();

        PoseStack torsoPose = new PoseStack();


        VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.armorCutoutNoCull(Nowhere.path("textures/entity/screen/locust_torso.png")));
        PoseStack.Pose posestack$pose = torsoPose.last();
        torsoPose.pushPose();


        Matrix4f matrix4f = posestack$pose.pose();
        Matrix3f matrix3f = posestack$pose.normal();
        vertex(vertexconsumer, matrix4f, matrix3f, -0.5F, -0.25F, 255, 255, 255, 0, 0, pPackedLight);
        vertex(vertexconsumer, matrix4f, matrix3f, 0.5F, -0.25F, 255, 255, 255, 0, 697, pPackedLight);
        vertex(vertexconsumer, matrix4f, matrix3f, 0.5F, 0.75F, 255, 255, 255, 670, 0, pPackedLight);
        vertex(vertexconsumer, matrix4f, matrix3f, -0.5F, 0.75F, 255, 255, 255, 670, 697, pPackedLight);
        torsoPose.popPose();

    }

    private static void vertex(VertexConsumer pConsumer, Matrix4f pMatrix, Matrix3f pMatrixNormal, float pX, float pY, int pRed, int pGreen, int pBlue, float pTexU, float pTexV, int pPackedLight) {
        pConsumer.vertex(pMatrix, pX, pY, 0.0F).color(pRed, pGreen, pBlue, 128).uv(pTexU, pTexV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(pMatrixNormal, 0.0F, 1.0F, 0.0F).endVertex();
    }

}
