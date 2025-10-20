/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.math.Axis
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.world.entity.Entity
 *  software.bernie.geckolib.model.GeoModel
 *  software.bernie.geckolib.renderer.GeoEntityRenderer
 *  software.bernie.geckolib.renderer.GeoRenderer
 */
package com.gametechbc.traveloptics.entity.projectiles.dimensional_spike;

import com.gametechbc.traveloptics.entity.projectiles.dimensional_spike.DimensionalSpikeEntity;
import com.gametechbc.traveloptics.entity.projectiles.dimensional_spike.DimensionalSpikeEntityLayer;
import com.gametechbc.traveloptics.entity.projectiles.dimensional_spike.DimensionalSpikeEntityModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;

public class DimensionalSpikeEntityRenderer
extends GeoEntityRenderer<DimensionalSpikeEntity> {
    public DimensionalSpikeEntityRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, (GeoModel)new DimensionalSpikeEntityModel());
        this.addRenderLayer(new DimensionalSpikeEntityLayer((GeoRenderer<DimensionalSpikeEntity>)this));
    }

    public void render(DimensionalSpikeEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.m_85836_();
        float scale = 1.3f;
        poseStack.m_85841_(scale, scale, scale);
        poseStack.m_252781_(Axis.f_252436_.m_252977_(-entityYaw));
        super.m_7392_((Entity)entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        poseStack.m_85849_();
    }
}

