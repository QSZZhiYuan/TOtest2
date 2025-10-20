/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.entity.EntityRenderer
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  org.joml.Matrix4f
 */
package com.gametechbc.traveloptics.entity.misc.stack_entity;

import com.gametechbc.traveloptics.entity.misc.stack_entity.StackEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;

@OnlyIn(value=Dist.CLIENT)
public class StackEntityRenderer
extends EntityRenderer<StackEntity> {
    public StackEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.f_114477_ = 0.0f;
    }

    public void render(StackEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        int stacks = entity.getStackCount();
        if (stacks <= 0) {
            return;
        }
        String numberStr = Integer.toString(stacks);
        Minecraft mc = Minecraft.m_91087_();
        float distance = mc.f_91074_ != null ? entity.m_20270_((Entity)mc.f_91074_) : 5.0f;
        float baseScale = 0.015f;
        float scalingFactor = 0.00125f;
        float dynamicScale = baseScale + distance * scalingFactor;
        float scale = Math.min(dynamicScale, 0.05f);
        float spacing = -4.0f;
        poseStack.m_85836_();
        poseStack.m_252880_(0.0f, 0.25f, 0.0f);
        poseStack.m_252781_(this.f_114476_.m_253208_());
        poseStack.m_85841_(-scale, scale, scale);
        int color = entity.getColor();
        float r = (float)(color >> 16 & 0xFF) / 255.0f;
        float g = (float)(color >> 8 & 0xFF) / 255.0f;
        float b = (float)(color & 0xFF) / 255.0f;
        for (int i = 0; i < numberStr.length(); ++i) {
            int digit = Character.getNumericValue(numberStr.charAt(i));
            ResourceLocation tex = ResourceLocation.fromNamespaceAndPath((String)"traveloptics", (String)("textures/gui/numbers/" + digit + ".png"));
            poseStack.m_85836_();
            poseStack.m_252880_(((float)i - (float)(numberStr.length() - 1) / 2.0f) * (16.0f + spacing), 0.0f, 0.0f);
            VertexConsumer builder = buffer.m_6299_(RenderType.m_110458_((ResourceLocation)tex));
            this.renderQuad(poseStack, builder, r, g, b, 0xF000F0);
            poseStack.m_85849_();
        }
        poseStack.m_85849_();
        super.m_7392_((Entity)entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    private void renderQuad(PoseStack poseStack, VertexConsumer builder, float r, float g, float b, int light) {
        Matrix4f matrix = poseStack.m_85850_().m_252922_();
        float size = 16.0f;
        builder.m_252986_(matrix, -size / 2.0f, -size / 2.0f, 0.0f).m_85950_(r, g, b, 1.0f).m_7421_(0.0f, 1.0f).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5601_(0.0f, 0.0f, 1.0f).m_5752_();
        builder.m_252986_(matrix, size / 2.0f, -size / 2.0f, 0.0f).m_85950_(r, g, b, 1.0f).m_7421_(1.0f, 1.0f).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5601_(0.0f, 0.0f, 1.0f).m_5752_();
        builder.m_252986_(matrix, size / 2.0f, size / 2.0f, 0.0f).m_85950_(r, g, b, 1.0f).m_7421_(1.0f, 0.0f).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5601_(0.0f, 0.0f, 1.0f).m_5752_();
        builder.m_252986_(matrix, -size / 2.0f, size / 2.0f, 0.0f).m_85950_(r, g, b, 1.0f).m_7421_(0.0f, 0.0f).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5601_(0.0f, 0.0f, 1.0f).m_5752_();
    }

    public ResourceLocation getTextureLocation(StackEntity entity) {
        return null;
    }
}

