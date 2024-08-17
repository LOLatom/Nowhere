package com.thefreak.nowhere.common.initiation;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.thefreak.nowhere.Nowhere;
import foundry.veil.api.client.registry.RenderTypeStageRegistry;
import foundry.veil.api.client.render.VeilRenderBridge;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BiFunction;


public class NowhereRenderTypes extends RenderType {
    private static final RenderStateShard.ShaderStateShard STATIC_SHADER = VeilRenderBridge.shaderState(Nowhere.path("statics"));
    private static final BiFunction<ResourceLocation, Boolean, RenderType> STATIC_NO_CULL = Util.memoize((location, aBoolean) -> {
        RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.builder().setShaderState(STATIC_SHADER)
                .setTextureState(NO_TEXTURE)
                .setTransparencyState(NO_TRANSPARENCY).setCullState(NO_CULL).setLightmapState(LIGHTMAP)
                .setOverlayState(OVERLAY).createCompositeState(aBoolean);
        RenderType renderType = create("static_no_cull",
                DefaultVertexFormat.NEW_ENTITY,
                VertexFormat.Mode.QUADS,
                256,
                true,
                false,
                rendertype$compositestate);
        RenderTypeStageRegistry.addStage(renderType, VeilRenderBridge.patchState(4));
        return renderType;
    });

    public NowhereRenderTypes(String pName, VertexFormat pFormat, VertexFormat.Mode pMode, int pBufferSize,
                              boolean pAffectsCrumbling, boolean pSortOnUpload, Runnable pSetupState, Runnable pClearState) {
        super(pName, pFormat, pMode, pBufferSize, pAffectsCrumbling, pSortOnUpload, pSetupState, pClearState);
    }

    public static RenderType staticNoCull(ResourceLocation texture) {
        return STATIC_NO_CULL.apply(texture,false);
    }
}
