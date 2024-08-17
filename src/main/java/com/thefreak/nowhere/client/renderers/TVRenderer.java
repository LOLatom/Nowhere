package com.thefreak.nowhere.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.thefreak.nowhere.Nowhere;
import com.thefreak.nowhere.client.models.TVScreenModel;
import com.thefreak.nowhere.common.blockentity.TVBlockEntity;
import com.thefreak.nowhere.common.blocks.TVBlock;
import com.thefreak.nowhere.common.initiation.LayerInitiation;
import com.thefreak.nowhere.common.initiation.LayerRegistry;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.state.BlockState;

public class TVRenderer implements BlockEntityRenderer<TVBlockEntity> {

    private final TVScreenModel<?> model;

    public TVRenderer(BlockEntityRendererProvider.Context pContext) {
        this.model = new TVScreenModel<>(pContext.bakeLayer(LayerInitiation.TV_LAYER));
    }

    @Override
    public void render(TVBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        VertexConsumer vertexConsumer = pBuffer.getBuffer(RenderType.entityCutoutNoCull(new ResourceLocation(Nowhere.MODID,"textures/blockentity/tv_texture.png")));
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
    }
}
