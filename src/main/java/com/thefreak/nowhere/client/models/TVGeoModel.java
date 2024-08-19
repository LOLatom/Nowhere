package com.thefreak.nowhere.client.models;

import com.thefreak.nowhere.Nowhere;
import com.thefreak.nowhere.common.blockentity.TVBlockEntity;
import com.thefreak.nowhere.common.entities.TheLocust;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TVGeoModel extends GeoModel<TVBlockEntity> {
    @Override
    public ResourceLocation getModelResource(TVBlockEntity animatable) {
        return new ResourceLocation(Nowhere.MODID, "geo/tv_model.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TVBlockEntity animatable) {
        return new ResourceLocation(Nowhere.MODID, "textures/blockentity/tv_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TVBlockEntity animatable) {
        return new ResourceLocation(Nowhere.MODID, "animations/tv.animation.json");
    }
}
