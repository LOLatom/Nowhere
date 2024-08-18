package com.thefreak.nowhere.client.renderers.util;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.thefreak.nowhere.client.models.TVScreenModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;


public class BlockEntityRendererWithLayers<T extends BlockEntity> implements BlockEntityRenderer<T> {

    private final EntityModel<?> model;
    protected final List<BlockEntityRenderLayer<T, ?>> layers = Lists.newArrayList();

    public BlockEntityRendererWithLayers(BlockEntityRendererProvider.Context renderManager, EntityModel<?> model) {
        this.model = model;
    }

    public final boolean addLayer(BlockEntityRenderLayer<T, ?> pLayer) {
        return this.layers.add(pLayer);
    }

    public EntityModel<?> getModel() {
        return this.model;
    }

    @Override
    public void render(T pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {

    }
}
