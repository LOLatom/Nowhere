package com.thefreak.nowhere.common.initiation;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.thefreak.nowhere.Nowhere;
import foundry.veil.api.client.registry.RenderTypeStageRegistry;
import foundry.veil.api.client.render.VeilRenderBridge;
import foundry.veil.api.client.render.VeilRenderSystem;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;


public class NowhereRenderTypes extends RenderType {
    private static final ResourceLocation STATIC_SHADER_LOCATION = Nowhere.path("statics");
    private static final RenderStateShard.ShaderStateShard STATIC_SHADER = VeilRenderBridge.shaderState(STATIC_SHADER_LOCATION);
    private static final Function<ResourceLocation, RenderType> STATIC_CULL = Util.memoize((location) -> {
        RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.builder().setShaderState(STATIC_SHADER)
                .setTextureState(new RenderStateShard.TextureStateShard(location, false, false))
                .setTransparencyState(NO_TRANSPARENCY).setCullState(NO_CULL).setLightmapState(LIGHTMAP)
                .setOverlayState(OVERLAY).createCompositeState(true);
        RenderType renderType = create("static_cull",
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

    public static RenderType staticCull(ResourceLocation texture) {
        return STATIC_CULL.apply(texture);
    }

    public static void setStaticPercentage(float percentage) {
        Objects.requireNonNull(VeilRenderSystem.renderer().getShaderManager().getShader(STATIC_SHADER_LOCATION)).setFloat("staticPercentage", percentage);
    }
}
