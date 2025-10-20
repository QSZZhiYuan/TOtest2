/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.event.CustomizeGuiOverlayEvent$BossEventProgress
 *  net.minecraftforge.client.event.RenderGuiOverlayEvent$Post
 *  net.minecraftforge.client.event.ViewportEvent$ComputeCameraAngles
 *  net.minecraftforge.client.event.ViewportEvent$ComputeFogColor
 *  net.minecraftforge.client.gui.overlay.VanillaGuiOverlay
 *  net.minecraftforge.event.TickEvent$ClientTickEvent
 *  net.minecraftforge.event.TickEvent$Phase
 *  net.minecraftforge.eventbus.api.EventPriority
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber$Bus
 */
package com.gametechbc.traveloptics.events;

import com.gametechbc.traveloptics.ClientProxy;
import com.gametechbc.traveloptics.config.ClientConfig;
import com.gametechbc.traveloptics.entity.misc.TOFollowingScreenShakeEntity;
import com.gametechbc.traveloptics.entity.misc.TOPowerInversionEntity;
import com.gametechbc.traveloptics.entity.misc.TOScreenFlashEntity;
import com.gametechbc.traveloptics.entity.misc.TOScreenShakeEntity;
import com.gametechbc.traveloptics.init.TravelopticsEffects;
import com.gametechbc.traveloptics.overlay.ScreenEffectOverlayHelper;
import com.gametechbc.traveloptics.util.TravelopticsKeybindManager;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid="traveloptics", bus=Mod.EventBusSubscriber.Bus.FORGE, value={Dist.CLIENT})
public class ForgeClientEvents {
    private static final float[] VIOLET_SKY_COLOR = new float[]{0.25f, 0.0f, 0.45f};
    private static final ResourceLocation NIGHTWARDEN_BOSS_BAR = new ResourceLocation("traveloptics", "textures/gui/boss_bar/nightwarden_boss_bar.png");
    private static final ResourceLocation ZAEVORATH_NATURE_BOSS_BAR = new ResourceLocation("traveloptics", "textures/gui/boss_bar/zaevorath_boss_bar_nature.png");
    private static final ResourceLocation ZAEVORATH_FIRE_BOSS_BAR = new ResourceLocation("traveloptics", "textures/gui/boss_bar/zaevorath_boss_bar_fire.png");
    private static final ResourceLocation ZAEVORATH_AQUA_BOSS_BAR = new ResourceLocation("traveloptics", "textures/gui/boss_bar/zaevorath_boss_bar_aqua.png");

    @SubscribeEvent
    public static void onSkyRender(ViewportEvent.ComputeFogColor event) {
        MobEffectInstance effect;
        Minecraft mc = Minecraft.m_91087_();
        if (mc.f_91074_ != null && (effect = mc.f_91074_.m_21124_((MobEffect)TravelopticsEffects.ASTRAL_SENSE_TREASURE.get())) != null) {
            event.setRed(VIOLET_SKY_COLOR[0]);
            event.setGreen(VIOLET_SKY_COLOR[1]);
            event.setBlue(VIOLET_SKY_COLOR[2]);
        }
        if (mc.f_91074_ != null && (effect = mc.f_91074_.m_21124_((MobEffect)TravelopticsEffects.ASTRAL_SENSE.get())) != null) {
            event.setRed(VIOLET_SKY_COLOR[0]);
            event.setGreen(VIOLET_SKY_COLOR[1]);
            event.setBlue(VIOLET_SKY_COLOR[2]);
        }
    }

    @SubscribeEvent(priority=EventPriority.HIGHEST)
    public static void renderBossOverlay(CustomizeGuiOverlayEvent.BossEventProgress event) {
        if (ClientProxy.bossBarRenderTypes.containsKey(event.getBossEvent().m_18860_())) {
            int shieldProgressWidth;
            int shieldBarY;
            int shieldBarX;
            PoseStack poseStack;
            int j1;
            int i1;
            int l;
            int progressWidth;
            int renderTypeFor = ClientProxy.bossBarRenderTypes.get(event.getBossEvent().m_18860_());
            int i = event.getGuiGraphics().m_280182_();
            int j = event.getY();
            Component component = event.getBossEvent().m_18861_();
            if (renderTypeFor == 0) {
                event.setCanceled(true);
                event.getGuiGraphics().m_280163_(NIGHTWARDEN_BOSS_BAR, event.getX(), event.getY() + 1, 26.0f, 30.0f, 188, 27, 241, 115);
                progressWidth = (int)(event.getBossEvent().m_142717_() * 188.0f);
                event.getGuiGraphics().m_280163_(NIGHTWARDEN_BOSS_BAR, event.getX(), event.getY() + 1, 26.0f, 1.0f, progressWidth, 27, 241, 115);
                l = Minecraft.m_91087_().f_91062_.m_92852_((FormattedText)component);
                i1 = i / 2 - l / 2;
                j1 = j - 9;
                poseStack = event.getGuiGraphics().m_280168_();
                poseStack.m_85836_();
                poseStack.m_252880_((float)i1, (float)j1, 0.0f);
                Minecraft.m_91087_().f_91062_.m_168645_(component.m_7532_(), 0.0f, 0.0f, 9978623, 2557773, poseStack.m_85850_().m_252922_(), (MultiBufferSource)event.getGuiGraphics().m_280091_(), 240);
                poseStack.m_85849_();
                event.setIncrement(event.getIncrement() + 7);
            }
            if (renderTypeFor == 1) {
                event.setCanceled(true);
                int adaptationBarX = event.getX() + 69;
                int adaptationBarY = event.getY() + 1;
                event.getGuiGraphics().m_280163_(NIGHTWARDEN_BOSS_BAR, adaptationBarX, adaptationBarY, 95.0f, 87.0f, 50, 13, 241, 115);
                float adaptationProgress = event.getBossEvent().m_142717_();
                int adaptationProgressWidth = (int)(adaptationProgress * 50.0f);
                event.getGuiGraphics().m_280163_(NIGHTWARDEN_BOSS_BAR, adaptationBarX, adaptationBarY, 95.0f, 64.0f, adaptationProgressWidth, 13, 241, 115);
                event.setIncrement(event.getIncrement() + 5);
            }
            if (renderTypeFor == 2) {
                event.setCanceled(true);
                event.getGuiGraphics().m_280163_(ZAEVORATH_NATURE_BOSS_BAR, event.getX(), event.getY() + 1, 26.0f, 0.0f, 188, 23, 241, 115);
                progressWidth = (int)(event.getBossEvent().m_142717_() * 188.0f);
                event.getGuiGraphics().m_280163_(ZAEVORATH_NATURE_BOSS_BAR, event.getX(), event.getY() + 1, 26.0f, 30.0f, progressWidth, 23, 241, 115);
                l = Minecraft.m_91087_().f_91062_.m_92852_((FormattedText)component);
                i1 = i / 2 - l / 2;
                j1 = j - 9;
                poseStack = event.getGuiGraphics().m_280168_();
                poseStack.m_85836_();
                poseStack.m_252880_((float)i1, (float)j1, 0.0f);
                Minecraft.m_91087_().f_91062_.m_168645_(component.m_7532_(), 0.0f, 0.0f, 9978623, 2557773, poseStack.m_85850_().m_252922_(), (MultiBufferSource)event.getGuiGraphics().m_280091_(), 240);
                poseStack.m_85849_();
                event.setIncrement(event.getIncrement() + 7);
            }
            if (renderTypeFor == 3) {
                event.setCanceled(true);
                shieldBarX = event.getX() + 4;
                shieldBarY = event.getY() - 2;
                event.getGuiGraphics().m_280163_(ZAEVORATH_NATURE_BOSS_BAR, shieldBarX, shieldBarY, 30.0f, 65.0f, 181, 4, 241, 115);
                shieldProgressWidth = (int)(event.getBossEvent().m_142717_() * 181.0f);
                event.getGuiGraphics().m_280163_(ZAEVORATH_NATURE_BOSS_BAR, shieldBarX, shieldBarY, 30.0f, 85.0f, shieldProgressWidth, 4, 241, 115);
                event.setIncrement(event.getIncrement() + 3);
            }
            if (renderTypeFor == 4) {
                event.setCanceled(true);
                event.getGuiGraphics().m_280163_(ZAEVORATH_FIRE_BOSS_BAR, event.getX(), event.getY() + 1, 26.0f, 0.0f, 188, 23, 241, 115);
                progressWidth = (int)(event.getBossEvent().m_142717_() * 188.0f);
                event.getGuiGraphics().m_280163_(ZAEVORATH_FIRE_BOSS_BAR, event.getX(), event.getY() + 1, 26.0f, 30.0f, progressWidth, 23, 241, 115);
                l = Minecraft.m_91087_().f_91062_.m_92852_((FormattedText)component);
                i1 = i / 2 - l / 2;
                j1 = j - 9;
                poseStack = event.getGuiGraphics().m_280168_();
                poseStack.m_85836_();
                poseStack.m_252880_((float)i1, (float)j1, 0.0f);
                Minecraft.m_91087_().f_91062_.m_168645_(component.m_7532_(), 0.0f, 0.0f, 9978623, 2557773, poseStack.m_85850_().m_252922_(), (MultiBufferSource)event.getGuiGraphics().m_280091_(), 240);
                poseStack.m_85849_();
                event.setIncrement(event.getIncrement() + 7);
            }
            if (renderTypeFor == 5) {
                event.setCanceled(true);
                shieldBarX = event.getX() + 4;
                shieldBarY = event.getY() - 2;
                event.getGuiGraphics().m_280163_(ZAEVORATH_FIRE_BOSS_BAR, shieldBarX, shieldBarY, 30.0f, 65.0f, 181, 4, 241, 115);
                shieldProgressWidth = (int)(event.getBossEvent().m_142717_() * 181.0f);
                event.getGuiGraphics().m_280163_(ZAEVORATH_FIRE_BOSS_BAR, shieldBarX, shieldBarY, 30.0f, 85.0f, shieldProgressWidth, 4, 241, 115);
                event.setIncrement(event.getIncrement() + 3);
            }
            if (renderTypeFor == 6) {
                event.setCanceled(true);
                event.getGuiGraphics().m_280163_(ZAEVORATH_AQUA_BOSS_BAR, event.getX(), event.getY() + 1, 26.0f, 0.0f, 188, 23, 241, 115);
                progressWidth = (int)(event.getBossEvent().m_142717_() * 188.0f);
                event.getGuiGraphics().m_280163_(ZAEVORATH_AQUA_BOSS_BAR, event.getX(), event.getY() + 1, 26.0f, 30.0f, progressWidth, 23, 241, 115);
                l = Minecraft.m_91087_().f_91062_.m_92852_((FormattedText)component);
                i1 = i / 2 - l / 2;
                j1 = j - 9;
                poseStack = event.getGuiGraphics().m_280168_();
                poseStack.m_85836_();
                poseStack.m_252880_((float)i1, (float)j1, 0.0f);
                Minecraft.m_91087_().f_91062_.m_168645_(component.m_7532_(), 0.0f, 0.0f, 9978623, 2557773, poseStack.m_85850_().m_252922_(), (MultiBufferSource)event.getGuiGraphics().m_280091_(), 240);
                poseStack.m_85849_();
                event.setIncrement(event.getIncrement() + 7);
            }
            if (renderTypeFor == 7) {
                event.setCanceled(true);
                shieldBarX = event.getX() + 4;
                shieldBarY = event.getY() - 2;
                event.getGuiGraphics().m_280163_(ZAEVORATH_AQUA_BOSS_BAR, shieldBarX, shieldBarY, 30.0f, 65.0f, 181, 4, 241, 115);
                shieldProgressWidth = (int)(event.getBossEvent().m_142717_() * 181.0f);
                event.getGuiGraphics().m_280163_(ZAEVORATH_AQUA_BOSS_BAR, shieldBarX, shieldBarY, 30.0f, 85.0f, shieldProgressWidth, 4, 241, 115);
                event.setIncrement(event.getIncrement() + 3);
            }
        }
    }

    @SubscribeEvent
    @OnlyIn(value=Dist.CLIENT)
    public static void onCameraSetup(ViewportEvent.ComputeCameraAngles event) {
        LocalPlayer player = Minecraft.m_91087_().f_91074_;
        if (player != null && !Minecraft.m_91087_().m_91104_() && ((Boolean)ClientConfig.activateScreenShake.get()).booleanValue()) {
            float delta = Minecraft.m_91087_().m_91296_();
            float ticksExistedDelta = (float)player.f_19797_ + delta;
            float shakeAmplitude = 0.0f;
            for (Entity screenShake : player.m_9236_().m_45976_(TOScreenShakeEntity.class, player.m_20191_().m_82377_(50.0, 50.0, 50.0))) {
                if (!(screenShake.m_20270_((Entity)player) <= screenShake.getRadius())) continue;
                shakeAmplitude += screenShake.getShakeAmount((Player)player, delta);
            }
            for (Entity screenShake : player.m_9236_().m_45976_(TOFollowingScreenShakeEntity.class, player.m_20191_().m_82377_(50.0, 50.0, 50.0))) {
                if (!(screenShake.m_20270_((Entity)player) <= screenShake.getRadius())) continue;
                shakeAmplitude += screenShake.getShakeAmount((Player)player, delta);
            }
            if (shakeAmplitude > 1.0f) {
                shakeAmplitude = 1.0f;
            }
            if (shakeAmplitude > 0.0f) {
                event.setPitch((float)((double)event.getPitch() + (double)shakeAmplitude * Math.cos(ticksExistedDelta * 3.0f + 2.0f) * 25.0));
                event.setYaw((float)((double)event.getYaw() + (double)shakeAmplitude * Math.cos(ticksExistedDelta * 5.0f + 1.0f) * 25.0));
                event.setRoll((float)((double)event.getRoll() + (double)shakeAmplitude * Math.cos(ticksExistedDelta * 4.0f) * 25.0));
            }
        }
    }

    @SubscribeEvent
    @OnlyIn(value=Dist.CLIENT)
    public static void onRenderOverlay(RenderGuiOverlayEvent.Post event) {
        LocalPlayer player;
        if (event.getOverlay() == VanillaGuiOverlay.CROSSHAIR.type() && (player = Minecraft.m_91087_().f_91074_) != null && !Minecraft.m_91087_().m_91104_() && ((Boolean)ClientConfig.activateScreenFlash.get()).booleanValue()) {
            float delta = Minecraft.m_91087_().m_91296_();
            GuiGraphics guiGraphics = event.getGuiGraphics();
            ForgeClientEvents.processScreenFlashEffects((Player)player, delta, guiGraphics);
            ForgeClientEvents.processPowerInversionEffects((Player)player, delta, guiGraphics);
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    private static void processScreenFlashEffects(Player player, float delta, GuiGraphics guiGraphics) {
        float totalFlashAlpha = 0.0f;
        int flashColor = 0xFFFFFF;
        for (TOScreenFlashEntity flash : player.m_9236_().m_45976_(TOScreenFlashEntity.class, player.m_20191_().m_82377_(50.0, 50.0, 50.0))) {
            float alpha;
            if (!(flash.m_20270_((Entity)player) <= flash.getRadius()) || !((alpha = flash.getFlashAlpha(player, delta)) > totalFlashAlpha)) continue;
            totalFlashAlpha = alpha;
            flashColor = flash.getFlashColor();
        }
        if (totalFlashAlpha > 1.0f) {
            totalFlashAlpha = 1.0f;
        }
        if (totalFlashAlpha > 0.0f) {
            ScreenEffectOverlayHelper.renderFlashOverlay(guiGraphics, totalFlashAlpha, flashColor);
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    private static void processPowerInversionEffects(Player player, float delta, GuiGraphics guiGraphics) {
        TOPowerInversionEntity.PowerEffectData combinedEffect = new TOPowerInversionEntity.PowerEffectData(0.0f, 0.0f, TOPowerInversionEntity.EffectPhase.NONE);
        boolean shouldInvert = false;
        int flashColor = 0xF8F8F8;
        for (TOPowerInversionEntity powerEffect : player.m_9236_().m_45976_(TOPowerInversionEntity.class, player.m_20191_().m_82377_(50.0, 50.0, 50.0))) {
            if (!(powerEffect.m_20270_((Entity)player) <= powerEffect.getRadius())) continue;
            TOPowerInversionEntity.PowerEffectData effectData = powerEffect.getEffectData(player, delta);
            if (effectData.phase == TOPowerInversionEntity.EffectPhase.NONE || !(effectData.flashIntensity > combinedEffect.flashIntensity) && !(effectData.effectIntensity > combinedEffect.effectIntensity)) continue;
            combinedEffect = effectData;
            shouldInvert = powerEffect.shouldInvertColors();
            flashColor = powerEffect.getFlashColor();
        }
        if (combinedEffect.phase != TOPowerInversionEntity.EffectPhase.NONE) {
            ScreenEffectOverlayHelper.renderPowerInversionOverlay(guiGraphics, combinedEffect, shouldInvert, flashColor);
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            TravelopticsKeybindManager.updateKeyStates();
        }
    }
}

