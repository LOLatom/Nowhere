package com.thefreak.nowhere.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.thefreak.nowhere.Nowhere;
import com.thefreak.nowhere.client.models.TheLocustModel;
import com.thefreak.nowhere.common.entities.TheLocust;
import com.thefreak.nowhere.common.initiation.NowhereRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TheLocustRenderer extends GeoEntityRenderer<TheLocust> {
    public TheLocustRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TheLocustModel());
        this.addRenderLayer(new TheLocustFaceLayer(this));
    }

    @Override
    public void render(TheLocust entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.scale(0.6F,0.6F,0.6F);
        ResourceLocation texture = new ResourceLocation(Nowhere.MODID,"textures/entity/the_locust.png");
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);

        reRender(model.getBakedModel(new ResourceLocation(Nowhere.MODID, "geo/the_locust.geo.json"))
                , poseStack, bufferSource, animatable, NowhereRenderTypes.staticNoCull(texture),
                bufferSource.getBuffer(NowhereRenderTypes.staticNoCull(texture)), partialTick
                , 15728640, OverlayTexture.NO_OVERLAY,
                1, 1, 1, 1);

    }
}
