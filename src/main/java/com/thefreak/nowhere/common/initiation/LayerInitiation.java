package com.thefreak.nowhere.common.initiation;

import com.thefreak.nowhere.Nowhere;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class LayerInitiation {
    public static final ModelLayerLocation TV_LAYER = register("tv");

    //public static final ModelLayerLocation LOCUST_TORSO_LAYER = register("locust_torso");




    private static ModelLayerLocation register(String loc) {
        return register(loc, "main");
    }

    private static ModelLayerLocation register(String p_171301_, String p_171302_) {
        return new ModelLayerLocation(new ResourceLocation(Nowhere.MODID), p_171302_);
    }
}
