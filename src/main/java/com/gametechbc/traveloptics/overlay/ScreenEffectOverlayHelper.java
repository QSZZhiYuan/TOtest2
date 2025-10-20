/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.GlStateManager$DestFactor
 *  com.mojang.blaze3d.platform.GlStateManager$SourceFactor
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.BufferBuilder
 *  com.mojang.blaze3d.vertex.BufferBuilder$RenderedBuffer
 *  com.mojang.blaze3d.vertex.BufferUploader
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.Tesselator
 *  com.mojang.blaze3d.vertex.VertexFormat$Mode
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  org.joml.Matrix4f
 */
package com.gametechbc.traveloptics.overlay;

import com.gametechbc.traveloptics.entity.misc.TOPowerInversionEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;

@OnlyIn(value=Dist.CLIENT)
public class ScreenEffectOverlayHelper {
    public static void renderFlashOverlay(GuiGraphics guiGraphics, float alpha, int color) {
        Minecraft mc = Minecraft.m_91087_();
        int screenWidth = mc.m_91268_().m_85445_();
        int screenHeight = mc.m_91268_().m_85446_();
        float red = (float)(color >> 16 & 0xFF) / 255.0f;
        float green = (float)(color >> 8 & 0xFF) / 255.0f;
        float blue = (float)(color & 0xFF) / 255.0f;
        PoseStack poseStack = guiGraphics.m_280168_();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::m_172811_);
        BufferBuilder buffer = Tesselator.m_85913_().m_85915_();
        buffer.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
        Matrix4f matrix = poseStack.m_85850_().m_252922_();
        buffer.m_252986_(matrix, 0.0f, (float)screenHeight, 0.0f).m_85950_(red, green, blue, alpha).m_5752_();
        buffer.m_252986_(matrix, (float)screenWidth, (float)screenHeight, 0.0f).m_85950_(red, green, blue, alpha).m_5752_();
        buffer.m_252986_(matrix, (float)screenWidth, 0.0f, 0.0f).m_85950_(red, green, blue, alpha).m_5752_();
        buffer.m_252986_(matrix, 0.0f, 0.0f, 0.0f).m_85950_(red, green, blue, alpha).m_5752_();
        BufferUploader.m_231202_((BufferBuilder.RenderedBuffer)buffer.m_231175_());
        RenderSystem.disableBlend();
    }

    public static void renderPowerInversionOverlay(GuiGraphics guiGraphics, TOPowerInversionEntity.PowerEffectData effectData, boolean shouldInvert, int flashColor) {
        Minecraft mc = Minecraft.m_91087_();
        int screenWidth = mc.m_91268_().m_85445_();
        int screenHeight = mc.m_91268_().m_85446_();
        if (effectData.flashIntensity > 0.0f) {
            float red = (float)(flashColor >> 16 & 0xFF) / 255.0f;
            float green = (float)(flashColor >> 8 & 0xFF) / 255.0f;
            float blue = (float)(flashColor & 0xFF) / 255.0f;
            float alpha = effectData.flashIntensity;
            PoseStack poseStack = guiGraphics.m_280168_();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShader(GameRenderer::m_172811_);
            BufferBuilder buffer = Tesselator.m_85913_().m_85915_();
            buffer.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
            Matrix4f matrix = poseStack.m_85850_().m_252922_();
            buffer.m_252986_(matrix, 0.0f, (float)screenHeight, 0.0f).m_85950_(red, green, blue, alpha).m_5752_();
            buffer.m_252986_(matrix, (float)screenWidth, (float)screenHeight, 0.0f).m_85950_(red, green, blue, alpha).m_5752_();
            buffer.m_252986_(matrix, (float)screenWidth, 0.0f, 0.0f).m_85950_(red, green, blue, alpha).m_5752_();
            buffer.m_252986_(matrix, 0.0f, 0.0f, 0.0f).m_85950_(red, green, blue, alpha).m_5752_();
            BufferUploader.m_231202_((BufferBuilder.RenderedBuffer)buffer.m_231175_());
            RenderSystem.disableBlend();
        }
        if (effectData.effectIntensity > 0.0f) {
            if (shouldInvert) {
                ScreenEffectOverlayHelper.renderEnhancedInversionPattern(guiGraphics, screenWidth, screenHeight, effectData.effectIntensity);
            } else {
                ScreenEffectOverlayHelper.renderEnhancedDesaturation(guiGraphics, screenWidth, screenHeight, effectData.effectIntensity);
            }
        }
    }

    public static void renderEnhancedInversionPattern(GuiGraphics guiGraphics, int screenWidth, int screenHeight, float intensity) {
        if (intensity <= 0.0f) {
            return;
        }
        PoseStack poseStack = guiGraphics.m_280168_();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc((GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        RenderSystem.setShader(GameRenderer::m_172811_);
        BufferBuilder buffer = Tesselator.m_85913_().m_85915_();
        buffer.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
        Matrix4f matrix = poseStack.m_85850_().m_252922_();
        float inversionAlpha = Math.min(intensity * 1.5f, 0.95f);
        inversionAlpha *= inversionAlpha;
        buffer.m_252986_(matrix, 0.0f, (float)screenHeight, 0.0f).m_85950_(0.97f, 0.97f, 0.97f, inversionAlpha).m_5752_();
        buffer.m_252986_(matrix, (float)screenWidth, (float)screenHeight, 0.0f).m_85950_(0.97f, 0.97f, 0.97f, inversionAlpha).m_5752_();
        buffer.m_252986_(matrix, (float)screenWidth, 0.0f, 0.0f).m_85950_(0.97f, 0.97f, 0.97f, inversionAlpha).m_5752_();
        buffer.m_252986_(matrix, 0.0f, 0.0f, 0.0f).m_85950_(0.97f, 0.97f, 0.97f, inversionAlpha).m_5752_();
        BufferUploader.m_231202_((BufferBuilder.RenderedBuffer)buffer.m_231175_());
        RenderSystem.blendFunc((GlStateManager.SourceFactor)GlStateManager.SourceFactor.DST_COLOR, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        buffer.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
        float contrastAlpha = intensity * 0.7f;
        contrastAlpha *= contrastAlpha;
        float contrastDarkness = 0.25f;
        buffer.m_252986_(matrix, 0.0f, (float)screenHeight, 0.0f).m_85950_(contrastDarkness, contrastDarkness, contrastDarkness, contrastAlpha).m_5752_();
        buffer.m_252986_(matrix, (float)screenWidth, (float)screenHeight, 0.0f).m_85950_(contrastDarkness, contrastDarkness, contrastDarkness, contrastAlpha).m_5752_();
        buffer.m_252986_(matrix, (float)screenWidth, 0.0f, 0.0f).m_85950_(contrastDarkness, contrastDarkness, contrastDarkness, contrastAlpha).m_5752_();
        buffer.m_252986_(matrix, 0.0f, 0.0f, 0.0f).m_85950_(contrastDarkness, contrastDarkness, contrastDarkness, contrastAlpha).m_5752_();
        BufferUploader.m_231202_((BufferBuilder.RenderedBuffer)buffer.m_231175_());
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
    }

    public static void renderEnhancedDesaturation(GuiGraphics guiGraphics, int screenWidth, int screenHeight, float intensity) {
        PoseStack poseStack = guiGraphics.m_280168_();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc((GlStateManager.SourceFactor)GlStateManager.SourceFactor.DST_COLOR, (GlStateManager.DestFactor)GlStateManager.DestFactor.SRC_COLOR);
        RenderSystem.setShader(GameRenderer::m_172811_);
        BufferBuilder buffer = Tesselator.m_85913_().m_85915_();
        buffer.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
        Matrix4f matrix = poseStack.m_85850_().m_252922_();
        float desatAlpha = Math.min(intensity * 1.4f, 1.0f);
        float grayValue = 0.4f;
        buffer.m_252986_(matrix, 0.0f, (float)screenHeight, 0.0f).m_85950_(grayValue, grayValue, grayValue, desatAlpha).m_5752_();
        buffer.m_252986_(matrix, (float)screenWidth, (float)screenHeight, 0.0f).m_85950_(grayValue, grayValue, grayValue, desatAlpha).m_5752_();
        buffer.m_252986_(matrix, (float)screenWidth, 0.0f, 0.0f).m_85950_(grayValue, grayValue, grayValue, desatAlpha).m_5752_();
        buffer.m_252986_(matrix, 0.0f, 0.0f, 0.0f).m_85950_(grayValue, grayValue, grayValue, desatAlpha).m_5752_();
        BufferUploader.m_231202_((BufferBuilder.RenderedBuffer)buffer.m_231175_());
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
    }

    public static void renderFullScreenQuad(PoseStack poseStack, int screenWidth, int screenHeight, float red, float green, float blue, float alpha) {
        BufferBuilder buffer = Tesselator.m_85913_().m_85915_();
        buffer.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
        Matrix4f matrix = poseStack.m_85850_().m_252922_();
        buffer.m_252986_(matrix, 0.0f, (float)screenHeight, 0.0f).m_85950_(red, green, blue, alpha).m_5752_();
        buffer.m_252986_(matrix, (float)screenWidth, (float)screenHeight, 0.0f).m_85950_(red, green, blue, alpha).m_5752_();
        buffer.m_252986_(matrix, (float)screenWidth, 0.0f, 0.0f).m_85950_(red, green, blue, alpha).m_5752_();
        buffer.m_252986_(matrix, 0.0f, 0.0f, 0.0f).m_85950_(red, green, blue, alpha).m_5752_();
        BufferUploader.m_231202_((BufferBuilder.RenderedBuffer)buffer.m_231175_());
    }
}

