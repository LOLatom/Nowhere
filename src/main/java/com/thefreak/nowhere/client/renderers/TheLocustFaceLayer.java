package com.thefreak.nowhere.client.renderers;

import com.thefreak.nowhere.Nowhere;
import com.thefreak.nowhere.common.entities.TheLocust;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class TheLocustFaceLayer extends AutoGlowingGeoLayer<TheLocust> {
    public TheLocustFaceLayer(GeoRenderer<TheLocust> renderer) {
        super(renderer);
    }

    @Override
    protected RenderType getRenderType(TheLocust animatable) {
        return RenderType.eyes(new ResourceLocation(Nowhere.MODID,"textures/entity/the_locust_glow.png"));
    }
}
