package com.thefreak.nowhere.client.renderers;

import com.thefreak.nowhere.Nowhere;
import com.thefreak.nowhere.common.blockentity.TVBlockEntity;
import net.minecraft.client.renderer.RenderType;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class GeoTVGlowLayer extends AutoGlowingGeoLayer<TVBlockEntity> {
    public GeoTVGlowLayer(GeoRenderer<TVBlockEntity> renderer) {
        super(renderer);
    }

    @Override
    protected RenderType getRenderType(TVBlockEntity animatable) {
        return RenderType.eyes(Nowhere.path("textures/blockentity/tv_texture_glow.png"));
    }
}
