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
package com.gametechbc.traveloptics.entity.projectiles.void_slash;

import com.gametechbc.traveloptics.TravelopticsMod;
import com.gametechbc.traveloptics.entity.projectiles.void_slash.VoidSlashProjectile;
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

public class VoidSlashProjectileRenderer
extends EntityRenderer<VoidSlashProjectile> {
    private static ResourceLocation[] TEXTURES = new ResourceLocation[]{TravelopticsMod.id("textures/entity/void_slash/void_slash.png"), TravelopticsMod.id("textures/entity/void_slash/void_slash.png")};

    public VoidSlashProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    public void render(VoidSlashProjectile entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
        poseStack.m_85836_();
        poseStack.m_252781_(Axis.f_252436_.m_252977_(Mth.m_14179_((float)partialTicks, (float)entity.f_19859_, (float)entity.m_146908_())));
        poseStack.m_252781_(Axis.f_252529_.m_252977_(-Mth.m_14179_((float)partialTicks, (float)entity.f_19860_, (float)entity.m_146909_())));
        float oldWidth = (float)entity.oldBB.m_82362_();
        float width = entity.m_20205_();
        width = oldWidth + (width - oldWidth) * Math.min(partialTicks, 1.0f);
        if (entity.isCross()) {
            poseStack.m_85836_();
            poseStack.m_252781_(Axis.f_252436_.m_252977_(-35.0f));
            poseStack.m_252781_(Axis.f_252403_.m_252977_(-25.0f));
            this.drawSlash(poseStack.m_85850_(), entity, bufferSource, light, width, 4);
            poseStack.m_85849_();
            poseStack.m_85836_();
            poseStack.m_252781_(Axis.f_252436_.m_252977_(35.0f));
            poseStack.m_252781_(Axis.f_252403_.m_252977_(25.0f));
            this.drawSlash(poseStack.m_85850_(), entity, bufferSource, light, width, 0);
            poseStack.m_85849_();
        } else {
            this.drawSlash(poseStack.m_85850_(), entity, bufferSource, 0xF000F0, width, 0);
        }
        poseStack.m_85849_();
        super.m_7392_((Entity)entity, yaw, partialTicks, poseStack, bufferSource, light);
    }

    private void drawSlash(PoseStack.Pose pose, VoidSlashProjectile entity, MultiBufferSource bufferSource, int light, float width, int offset) {
        Matrix4f poseMatrix = pose.m_252922_();
        Matrix3f normalMatrix = pose.m_252943_();
        VertexConsumer consumer = bufferSource.m_6299_(RenderType.m_234338_((ResourceLocation)this.getTextureLocation(entity, offset)));
        float halfWidth = width * 0.5f;
        consumer.m_252986_(poseMatrix, -halfWidth, -0.1f, -halfWidth).m_6122_(255, 255, 255, 255).m_7421_(0.0f, 1.0f).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_252939_(normalMatrix, 0.0f, 1.0f, 0.0f).m_5752_();
        consumer.m_252986_(poseMatrix, halfWidth, -0.1f, -halfWidth).m_6122_(255, 255, 255, 255).m_7421_(1.0f, 1.0f).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_252939_(normalMatrix, 0.0f, 1.0f, 0.0f).m_5752_();
        consumer.m_252986_(poseMatrix, halfWidth, -0.1f, halfWidth).m_6122_(255, 255, 255, 255).m_7421_(1.0f, 0.0f).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_252939_(normalMatrix, 0.0f, 1.0f, 0.0f).m_5752_();
        consumer.m_252986_(poseMatrix, -halfWidth, -0.1f, halfWidth).m_6122_(255, 255, 255, 255).m_7421_(0.0f, 0.0f).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_252939_(normalMatrix, 0.0f, 1.0f, 0.0f).m_5752_();
    }

    public ResourceLocation getTextureLocation(VoidSlashProjectile entity) {
        int frame = entity.animationTime / 4 % TEXTURES.length;
        return TEXTURES[frame];
    }

    private ResourceLocation getTextureLocation(VoidSlashProjectile entity, int offset) {
        int frame = (entity.animationTime / 6 + offset) % TEXTURES.length;
        return TEXTURES[frame];
    }
}

