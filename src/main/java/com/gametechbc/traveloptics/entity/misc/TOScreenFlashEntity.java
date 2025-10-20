/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientGamePacketListener
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.network.NetworkHooks
 */
package com.gametechbc.traveloptics.entity.misc;

import com.gametechbc.traveloptics.init.TravelopticsEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

public class TOScreenFlashEntity
extends Entity {
    private static final EntityDataAccessor<Float> RADIUS = SynchedEntityData.m_135353_(TOScreenFlashEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135029_);
    private static final EntityDataAccessor<Float> INTENSITY = SynchedEntityData.m_135353_(TOScreenFlashEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135029_);
    private static final EntityDataAccessor<Integer> FADE_IN_DURATION = SynchedEntityData.m_135353_(TOScreenFlashEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Integer> PEAK_DURATION = SynchedEntityData.m_135353_(TOScreenFlashEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Integer> FADE_OUT_DURATION = SynchedEntityData.m_135353_(TOScreenFlashEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.m_135353_(TOScreenFlashEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Boolean> LESSEN_DISTANCE_FALLOFF = SynchedEntityData.m_135353_(TOScreenFlashEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);

    public TOScreenFlashEntity(EntityType<?> type, Level world) {
        super(type, world);
    }

    public TOScreenFlashEntity(Level world, Vec3 position, float radius, float intensity, int fadeInDuration, int peakDuration, int fadeOutDuration, int color, boolean lessenDistanceFalloff) {
        super((EntityType)TravelopticsEntities.SCREEN_FLASH.get(), world);
        this.setRadius(radius);
        this.setIntensity(intensity);
        this.setFadeInDuration(fadeInDuration);
        this.setPeakDuration(peakDuration);
        this.setFadeOutDuration(fadeOutDuration);
        this.setColor(color);
        this.setLessenDistanceFalloff(lessenDistanceFalloff);
        this.m_6034_(position.f_82479_, position.f_82480_, position.f_82481_);
    }

    @OnlyIn(value=Dist.CLIENT)
    public float getFlashAlpha(Player player, float delta) {
        float ticksElapsed = (float)this.f_19797_ + delta;
        int fadeInDur = this.getFadeInDuration();
        int peakDur = this.getPeakDuration();
        int fadeOutDur = this.getFadeOutDuration();
        Vec3 playerPos = player.m_20299_(delta);
        double distance = this.m_20182_().m_82554_(playerPos);
        if (distance > (double)this.getRadius()) {
            return 0.0f;
        }
        float baseIntensity = this.getIntensity();
        if (this.isLessenDistanceFalloff()) {
            float distanceFalloff = (float)Math.max(0.0, 1.0 - distance / (double)this.getRadius());
            baseIntensity *= distanceFalloff;
        }
        if (ticksElapsed < (float)fadeInDur) {
            if (fadeInDur == 0) {
                return baseIntensity;
            }
            float fadeInProgress = ticksElapsed / (float)fadeInDur;
            return baseIntensity * fadeInProgress;
        }
        if (ticksElapsed < (float)(fadeInDur + peakDur)) {
            return baseIntensity;
        }
        if (ticksElapsed < (float)(fadeInDur + peakDur + fadeOutDur)) {
            if (fadeOutDur == 0) {
                return 0.0f;
            }
            float fadeOutStart = fadeInDur + peakDur;
            float timeInFadeOut = ticksElapsed - fadeOutStart;
            float fadeOutProgress = timeInFadeOut / (float)fadeOutDur;
            return baseIntensity * (1.0f - fadeOutProgress);
        }
        return 0.0f;
    }

    @OnlyIn(value=Dist.CLIENT)
    public int getFlashColor() {
        return this.getColor();
    }

    public void m_8119_() {
        super.m_8119_();
        int totalDuration = this.getFadeInDuration() + this.getPeakDuration() + this.getFadeOutDuration();
        if (this.f_19797_ > totalDuration) {
            this.m_146870_();
        }
    }

    protected void m_8097_() {
        this.f_19804_.m_135372_(RADIUS, (Object)Float.valueOf(10.0f));
        this.f_19804_.m_135372_(INTENSITY, (Object)Float.valueOf(0.8f));
        this.f_19804_.m_135372_(FADE_IN_DURATION, (Object)3);
        this.f_19804_.m_135372_(PEAK_DURATION, (Object)2);
        this.f_19804_.m_135372_(FADE_OUT_DURATION, (Object)10);
        this.f_19804_.m_135372_(COLOR, (Object)0xFFFFFF);
        this.f_19804_.m_135372_(LESSEN_DISTANCE_FALLOFF, (Object)false);
    }

    public float getRadius() {
        return ((Float)this.f_19804_.m_135370_(RADIUS)).floatValue();
    }

    public void setRadius(float radius) {
        this.f_19804_.m_135381_(RADIUS, (Object)Float.valueOf(radius));
    }

    public float getIntensity() {
        return ((Float)this.f_19804_.m_135370_(INTENSITY)).floatValue();
    }

    public void setIntensity(float intensity) {
        this.f_19804_.m_135381_(INTENSITY, (Object)Float.valueOf(intensity));
    }

    public int getFadeInDuration() {
        return (Integer)this.f_19804_.m_135370_(FADE_IN_DURATION);
    }

    public void setFadeInDuration(int duration) {
        this.f_19804_.m_135381_(FADE_IN_DURATION, (Object)duration);
    }

    public int getPeakDuration() {
        return (Integer)this.f_19804_.m_135370_(PEAK_DURATION);
    }

    public void setPeakDuration(int duration) {
        this.f_19804_.m_135381_(PEAK_DURATION, (Object)duration);
    }

    public int getFadeOutDuration() {
        return (Integer)this.f_19804_.m_135370_(FADE_OUT_DURATION);
    }

    public void setFadeOutDuration(int duration) {
        this.f_19804_.m_135381_(FADE_OUT_DURATION, (Object)duration);
    }

    public int getColor() {
        return (Integer)this.f_19804_.m_135370_(COLOR);
    }

    public void setColor(int color) {
        this.f_19804_.m_135381_(COLOR, (Object)color);
    }

    public boolean isLessenDistanceFalloff() {
        return (Boolean)this.f_19804_.m_135370_(LESSEN_DISTANCE_FALLOFF);
    }

    public void setLessenDistanceFalloff(boolean lessen) {
        this.f_19804_.m_135381_(LESSEN_DISTANCE_FALLOFF, (Object)lessen);
    }

    protected void m_7378_(CompoundTag compound) {
        this.setRadius(compound.m_128457_("radius"));
        this.setIntensity(compound.m_128457_("intensity"));
        this.setFadeInDuration(compound.m_128451_("fade_in_duration"));
        this.setPeakDuration(compound.m_128451_("peak_duration"));
        this.setFadeOutDuration(compound.m_128451_("fade_out_duration"));
        this.setColor(compound.m_128451_("color"));
        this.setLessenDistanceFalloff(compound.m_128471_("lessen_distance_falloff"));
        this.f_19797_ = compound.m_128451_("ticks_existed");
    }

    protected void m_7380_(CompoundTag compound) {
        compound.m_128350_("radius", this.getRadius());
        compound.m_128350_("intensity", this.getIntensity());
        compound.m_128405_("fade_in_duration", this.getFadeInDuration());
        compound.m_128405_("peak_duration", this.getPeakDuration());
        compound.m_128405_("fade_out_duration", this.getFadeOutDuration());
        compound.m_128405_("color", this.getColor());
        compound.m_128379_("lessen_distance_falloff", this.isLessenDistanceFalloff());
        compound.m_128405_("ticks_existed", this.f_19797_);
    }

    public Packet<ClientGamePacketListener> m_5654_() {
        return NetworkHooks.getEntitySpawningPacket((Entity)this);
    }

    public static void createScreenFlash(Level world, Vec3 position, float radius, float intensity, int fadeInDuration, int peakDuration, int fadeOutDuration, int color, boolean lessenDistanceFalloff) {
        if (!world.f_46443_) {
            TOScreenFlashEntity flash = new TOScreenFlashEntity(world, position, radius, intensity, fadeInDuration, peakDuration, fadeOutDuration, color, lessenDistanceFalloff);
            world.m_7967_((Entity)flash);
        }
    }

    public static void createWhiteFlash(Level world, Vec3 position, float radius, float intensity, int fadeInDuration, int peakDuration, int fadeOutDuration, boolean lessenDistanceFalloff) {
        TOScreenFlashEntity.createScreenFlash(world, position, radius, intensity, fadeInDuration, peakDuration, fadeOutDuration, 0xFFFFFF, lessenDistanceFalloff);
    }

    public static void createFlashbang(Level world, Vec3 position, float radius, boolean lessenDistanceFalloff) {
        TOScreenFlashEntity.createScreenFlash(world, position, radius, 0.9f, 1, 3, 20, 0xFFFFFF, lessenDistanceFalloff);
    }
}

