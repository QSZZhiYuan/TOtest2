/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  software.bernie.geckolib.cache.object.BakedGeoModel
 *  software.bernie.geckolib.core.animatable.GeoAnimatable
 *  software.bernie.geckolib.renderer.GeoRenderer
 *  software.bernie.geckolib.renderer.layer.GeoRenderLayer
 */
package com.gametechbc.traveloptics.entity.projectiles.aqua_vortex;

import com.gametechbc.traveloptics.TravelopticsMod;
import com.gametechbc.traveloptics.entity.projectiles.aqua_vortex.AquaVortexEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

@OnlyIn(value=Dist.CLIENT)
public class AquaVortexEmissiveLayer
extends GeoRenderLayer<AquaVortexEntity> {
    public static final ResourceLocation LAYER = TravelopticsMod.id("textures/models/entity/aqua_vortex_layer.png");

    public AquaVortexEmissiveLayer(GeoRenderer<AquaVortexEntity> entityRenderer) {
        super(entityRenderer);
    }

    public void render(PoseStack poseStack, AquaVortexEntity animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        renderType = RenderType.m_110488_((ResourceLocation)LAYER);
        VertexConsumer vertexconsumer = bufferSource.m_6299_(renderType);
        poseStack.m_85836_();
        float f = Mth.m_14031_((float)((float)(((double)((float)animatable.f_19797_ + partialTick) + (animatable.m_20185_() + animatable.m_20189_()) * 500.0) * (double)0.15f))) * 0.5f + 0.5f;
        this.getRenderer().actuallyRender(poseStack, (GeoAnimatable)animatable, bakedModel, renderType, bufferSource, vertexconsumer, true, partialTick, 0xF000F0, OverlayTexture.f_118083_, f, f, f, 1.0f);
        poseStack.m_85849_();
    }
}

