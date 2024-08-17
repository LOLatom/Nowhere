package com.thefreak.nowhere.client.models;

import com.thefreak.nowhere.Nowhere;
import com.thefreak.nowhere.common.entities.TheLocust;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TheLocustModel extends GeoModel<TheLocust> {
    @Override
    public ResourceLocation getModelResource(TheLocust animatable) {
        return new ResourceLocation(Nowhere.MODID, "geo/the_locust.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TheLocust animatable) {
        return new ResourceLocation(Nowhere.MODID, "textures/entity/the_locust.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TheLocust animatable) {
        return new ResourceLocation(Nowhere.MODID, "animations/the_locust.animation.json");
    }
}
