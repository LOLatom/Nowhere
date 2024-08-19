package com.thefreak.nowhere.client.renderers;

import com.thefreak.nowhere.client.models.TVGeoModel;
import com.thefreak.nowhere.common.blockentity.TVBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class GeoTVRenderer extends GeoBlockRenderer<TVBlockEntity> {
    public GeoTVRenderer(BlockEntityRendererProvider.Context rendererProvider) {
        super(new TVGeoModel());
        this.addRenderLayer(new GeoTVGlowLayer(this));
    }
}
