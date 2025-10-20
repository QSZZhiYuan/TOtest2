/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.world.entity.Entity
 *  software.bernie.geckolib.model.GeoModel
 *  software.bernie.geckolib.renderer.GeoEntityRenderer
 *  software.bernie.geckolib.renderer.GeoRenderer
 */
package com.gametechbc.traveloptics.entity.projectiles.supernova.dying_star;

import com.gametechbc.traveloptics.entity.projectiles.supernova.dying_star.DyingStarEntity;
import com.gametechbc.traveloptics.entity.projectiles.supernova.dying_star.DyingStarLayer;
import com.gametechbc.traveloptics.entity.projectiles.supernova.dying_star.DyingStarModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;

public class DyingStarRenderer
extends GeoEntityRenderer<DyingStarEntity> {
    public DyingStarRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, (GeoModel)new DyingStarModel());
        this.addRenderLayer(new DyingStarLayer((GeoRenderer<DyingStarEntity>)this));
        this.f_114477_ = 0.0f;
    }

    public void render(DyingStarEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        float scale = 1.8f;
        poseStack.m_85836_();
        poseStack.m_85841_(scale, scale, scale);
        super.m_7392_((Entity)entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        poseStack.m_85849_();
    }
}

