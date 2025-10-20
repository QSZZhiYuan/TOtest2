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
 *  net.minecraft.world.entity.Entity
 *  org.joml.Matrix3f
 *  org.joml.Matrix4f
 */
package com.gametechbc.traveloptics.entity.projectiles.reversal;

import com.gametechbc.traveloptics.TravelopticsMod;
import com.gametechbc.traveloptics.entity.projectiles.reversal.ReversalEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.Random;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class ReversalRenderer
extends EntityRenderer<ReversalEntity> {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[]{TravelopticsMod.id("textures/entity/reversal/reversal_1.png"), TravelopticsMod.id("textures/entity/reversal/reversal_2.png"), TravelopticsMod.id("textures/entity/reversal/reversal_3.png"), TravelopticsMod.id("textures/entity/reversal/reversal_4.png")};

    public ReversalRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    public void render(ReversalEntity entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
        poseStack.m_85836_();
        PoseStack.Pose pose = poseStack.m_85850_();
        poseStack.m_252781_(Axis.f_252436_.m_252977_(90.0f - entity.m_146908_()));
        poseStack.m_252781_(Axis.f_252403_.m_252977_(entity.m_146909_()));
        float randomZ = new Random(31L * (long)entity.m_19879_()).nextInt(-8, 8);
        poseStack.m_252781_(Axis.f_252529_.m_252977_(randomZ));
        this.drawSlash(pose, entity, bufferSource, entity.m_20205_() * 1.5f, entity.isMirrored());
        poseStack.m_85849_();
        super.m_7392_((Entity)entity, yaw, partialTicks, poseStack, bufferSource, light);
    }

    private void drawSlash(PoseStack.Pose pose, ReversalEntity entity, MultiBufferSource bufferSource, float width, boolean mirrored) {
        Matrix4f poseMatrix = pose.m_252922_();
        Matrix3f normalMatrix = pose.m_252943_();
        VertexConsumer consumer = bufferSource.m_6299_(RenderType.m_110458_((ResourceLocation)this.getTextureLocation(entity)));
        float halfWidth = width * 0.5f;
        float height = entity.m_20206_() * 0.5f;
        consumer.m_252986_(poseMatrix, -halfWidth, height, -halfWidth).m_6122_(255, 255, 255, 255).m_7421_(0.0f, mirrored ? 0.0f : 1.0f).m_86008_(OverlayTexture.f_118083_).m_85969_(0xF000F0).m_252939_(normalMatrix, 0.0f, 1.0f, 0.0f).m_5752_();
        consumer.m_252986_(poseMatrix, halfWidth, height, -halfWidth).m_6122_(255, 255, 255, 255).m_7421_(1.0f, mirrored ? 0.0f : 1.0f).m_86008_(OverlayTexture.f_118083_).m_85969_(0xF000F0).m_252939_(normalMatrix, 0.0f, 1.0f, 0.0f).m_5752_();
        consumer.m_252986_(poseMatrix, halfWidth, height, halfWidth).m_6122_(255, 255, 255, 255).m_7421_(1.0f, mirrored ? 1.0f : 0.0f).m_86008_(OverlayTexture.f_118083_).m_85969_(0xF000F0).m_252939_(normalMatrix, 0.0f, 1.0f, 0.0f).m_5752_();
        consumer.m_252986_(poseMatrix, -halfWidth, height, halfWidth).m_6122_(255, 255, 255, 255).m_7421_(0.0f, mirrored ? 1.0f : 0.0f).m_86008_(OverlayTexture.f_118083_).m_85969_(0xF000F0).m_252939_(normalMatrix, 0.0f, 1.0f, 0.0f).m_5752_();
    }

    public ResourceLocation getTextureLocation(ReversalEntity entity) {
        int frame = entity.f_19797_ / entity.ticksPerFrame % TEXTURES.length;
        return TEXTURES[frame];
    }
}

