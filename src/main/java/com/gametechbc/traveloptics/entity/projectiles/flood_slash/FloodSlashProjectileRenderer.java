/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.PoseStack$Pose
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.math.Axis
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.entity.EntityRenderer
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  org.joml.Matrix3f
 *  org.joml.Matrix4f
 */
package com.gametechbc.traveloptics.entity.projectiles.flood_slash;

import com.gametechbc.traveloptics.TravelopticsMod;
import com.gametechbc.traveloptics.entity.projectiles.flood_slash.FloodSlashProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class FloodSlashProjectileRenderer
extends EntityRenderer<FloodSlashProjectile> {
    private static ResourceLocation[] TEXTURES = new ResourceLocation[]{TravelopticsMod.id("textures/entity/flood_slash/flood_slash_projectile.png"), TravelopticsMod.id("textures/entity/flood_slash/flood_slash_projectile.png")};

    public FloodSlashProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    public void render(FloodSlashProjectile entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
        poseStack.m_85836_();
        PoseStack.Pose pose = poseStack.m_85850_();
        Matrix4f poseMatrix = pose.m_252922_();
        Matrix3f normalMatrix = pose.m_252943_();
        poseStack.m_252781_(Axis.f_252436_.m_252977_(Mth.m_14179_((float)partialTicks, (float)entity.f_19859_, (float)entity.m_146908_())));
        poseStack.m_252781_(Axis.f_252529_.m_252977_(-Mth.m_14179_((float)partialTicks, (float)entity.f_19860_, (float)entity.m_146909_())));
        float oldWidth = (float)entity.oldBB.m_82362_();
        float width = entity.m_20205_();
        width = oldWidth + (width - oldWidth) * Math.min(partialTicks, 1.0f);
        this.drawSlash(pose, entity, bufferSource, light, width, 0);
        poseStack.m_85849_();
        super.m_7392_((Entity)entity, yaw, partialTicks, poseStack, bufferSource, light);
    }

    private void drawSlash(PoseStack.Pose pose, FloodSlashProjectile entity, MultiBufferSource bufferSource, int light, float width, int offset) {
        Matrix4f poseMatrix = pose.m_252922_();
        Matrix3f normalMatrix = pose.m_252943_();
        VertexConsumer consumer = bufferSource.m_6299_(RenderType.m_110458_((ResourceLocation)this.getTextureLocation(entity, offset)));
        float halfWidth = width * 0.5f;
        consumer.m_252986_(poseMatrix, -halfWidth, -0.1f, -halfWidth).m_6122_(255, 255, 255, 255).m_7421_(0.0f, 1.0f).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_252939_(normalMatrix, 0.0f, 1.0f, 0.0f).m_5752_();
        consumer.m_252986_(poseMatrix, halfWidth, -0.1f, -halfWidth).m_6122_(255, 255, 255, 255).m_7421_(1.0f, 1.0f).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_252939_(normalMatrix, 0.0f, 1.0f, 0.0f).m_5752_();
        consumer.m_252986_(poseMatrix, halfWidth, -0.1f, halfWidth).m_6122_(255, 255, 255, 255).m_7421_(1.0f, 0.0f).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_252939_(normalMatrix, 0.0f, 1.0f, 0.0f).m_5752_();
        consumer.m_252986_(poseMatrix, -halfWidth, -0.1f, halfWidth).m_6122_(255, 255, 255, 255).m_7421_(0.0f, 0.0f).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_252939_(normalMatrix, 0.0f, 1.0f, 0.0f).m_5752_();
    }

    public ResourceLocation getTextureLocation(FloodSlashProjectile entity) {
        int frame = entity.animationTime / 4 % TEXTURES.length;
        return TEXTURES[frame];
    }

    private ResourceLocation getTextureLocation(FloodSlashProjectile entity, int offset) {
        int frame = (entity.animationTime / 6 + offset) % TEXTURES.length;
        return TEXTURES[frame];
    }
}

