package com.thefreak.nowhere.client.renderers.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class BlockEntityRenderLayer<T extends BlockEntity, M extends EntityModel<? extends Entity>> {
    private final BlockEntityRendererWithLayers<?> renderer;

    public BlockEntityRenderLayer(BlockEntityRendererWithLayers<?> pRenderer) {
        this.renderer = pRenderer;
    }


    public <M extends EntityModel<?>> Model getParentModel() {
        return this.renderer.getModel();
    }


    public abstract void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch);

}
