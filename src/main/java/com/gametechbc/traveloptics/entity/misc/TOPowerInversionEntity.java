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

public class TOPowerInversionEntity
extends Entity {
    private static final EntityDataAccessor<Float> RADIUS = SynchedEntityData.m_135353_(TOPowerInversionEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135029_);
    private static final EntityDataAccessor<Float> INTENSITY = SynchedEntityData.m_135353_(TOPowerInversionEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135029_);
    private static final EntityDataAccessor<Integer> FADE_DURATION = SynchedEntityData.m_135353_(TOPowerInversionEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Boolean> INVERT_COLORS = SynchedEntityData.m_135353_(TOPowerInversionEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Integer> FLASH_COLOR = SynchedEntityData.m_135353_(TOPowerInversionEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);

    public TOPowerInversionEntity(EntityType<?> type, Level world) {
        super(type, world);
    }

    public TOPowerInversionEntity(Level world, Vec3 position, float radius, float intensity, int fadeDuration, boolean invertColors, int flashColor) {
        super((EntityType)TravelopticsEntities.SCREEN_POWER_INVERSION.get(), world);
        this.setRadius(radius);
        this.setIntensity(intensity);
        this.setFadeDuration(fadeDuration);
        this.setInvertColors(invertColors);
        this.setFlashColor(flashColor);
        this.m_6034_(position.f_82479_, position.f_82480_, position.f_82481_);
    }

    @OnlyIn(value=Dist.CLIENT)
    public PowerEffectData getEffectData(Player player, float delta) {
        float ticksDelta = (float)this.f_19797_ + delta;
        Vec3 playerPos = player.m_20299_(delta);
        double distance = this.m_20182_().m_82554_(playerPos);
        if (distance > (double)this.getRadius()) {
            return new PowerEffectData(0.0f, 0.0f, EffectPhase.NONE);
        }
        float distanceFalloff = (float)Math.max(0.0, 1.0 - distance / (double)this.getRadius());
        float baseIntensity = this.getIntensity() * distanceFalloff;
        if (ticksDelta < 2.0f) {
            return new PowerEffectData(baseIntensity, 0.0f, EffectPhase.FIRST_FLASH);
        }
        if (ticksDelta < 4.0f) {
            return new PowerEffectData(0.0f, baseIntensity, EffectPhase.SECOND_FLASH);
        }
        if (ticksDelta < 4.0f + (float)this.getFadeDuration()) {
            float fadeProgress = (ticksDelta - 4.0f) / (float)this.getFadeDuration();
            fadeProgress = Math.max(0.0f, Math.min(1.0f, fadeProgress));
            float fadeIntensity = baseIntensity * (1.0f - fadeProgress);
            return new PowerEffectData(0.0f, fadeIntensity, EffectPhase.FADE);
        }
        return new PowerEffectData(0.0f, 0.0f, EffectPhase.NONE);
    }

    @OnlyIn(value=Dist.CLIENT)
    public int getFlashColor() {
        return this.getFlashColorValue();
    }

    @OnlyIn(value=Dist.CLIENT)
    public boolean shouldInvertColors() {
        return this.getInvertColors();
    }

    public void m_8119_() {
        super.m_8119_();
        int totalDuration = 4 + this.getFadeDuration();
        if (this.f_19797_ > totalDuration) {
            this.m_146870_();
        }
    }

    protected void m_8097_() {
        this.f_19804_.m_135372_(RADIUS, (Object)Float.valueOf(15.0f));
        this.f_19804_.m_135372_(INTENSITY, (Object)Float.valueOf(1.0f));
        this.f_19804_.m_135372_(FADE_DURATION, (Object)20);
        this.f_19804_.m_135372_(INVERT_COLORS, (Object)true);
        this.f_19804_.m_135372_(FLASH_COLOR, (Object)0xF8F8F8);
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

    public int getFadeDuration() {
        return (Integer)this.f_19804_.m_135370_(FADE_DURATION);
    }

    public void setFadeDuration(int duration) {
        this.f_19804_.m_135381_(FADE_DURATION, (Object)duration);
    }

    public boolean getInvertColors() {
        return (Boolean)this.f_19804_.m_135370_(INVERT_COLORS);
    }

    public void setInvertColors(boolean invert) {
        this.f_19804_.m_135381_(INVERT_COLORS, (Object)invert);
    }

    public int getFlashColorValue() {
        return (Integer)this.f_19804_.m_135370_(FLASH_COLOR);
    }

    public void setFlashColor(int color) {
        this.f_19804_.m_135381_(FLASH_COLOR, (Object)color);
    }

    protected void m_7378_(CompoundTag compound) {
        this.setRadius(compound.m_128457_("radius"));
        this.setIntensity(compound.m_128457_("intensity"));
        this.setFadeDuration(compound.m_128451_("fade_duration"));
        this.setInvertColors(compound.m_128471_("invert_colors"));
        this.setFlashColor(compound.m_128451_("flash_color"));
        this.f_19797_ = compound.m_128451_("ticks_existed");
    }

    protected void m_7380_(CompoundTag compound) {
        compound.m_128350_("radius", this.getRadius());
        compound.m_128350_("intensity", this.getIntensity());
        compound.m_128405_("fade_duration", this.getFadeDuration());
        compound.m_128379_("invert_colors", this.getInvertColors());
        compound.m_128405_("flash_color", this.getFlashColorValue());
        compound.m_128405_("ticks_existed", this.f_19797_);
    }

    public Packet<ClientGamePacketListener> m_5654_() {
        return NetworkHooks.getEntitySpawningPacket((Entity)this);
    }

    public static void createPowerInversion(Level world, Vec3 position, float radius, float intensity, int fadeDuration, boolean invertColors, int flashColor) {
        if (!world.f_46443_) {
            TOPowerInversionEntity effect = new TOPowerInversionEntity(world, position, radius, intensity, fadeDuration, invertColors, flashColor);
            world.m_7967_((Entity)effect);
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    public static class PowerEffectData {
        public final float flashIntensity;
        public final float effectIntensity;
        public final EffectPhase phase;

        public PowerEffectData(float flashIntensity, float effectIntensity, EffectPhase phase) {
            this.flashIntensity = flashIntensity;
            this.effectIntensity = effectIntensity;
            this.phase = phase;
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    public static enum EffectPhase {
        NONE,
        FIRST_FLASH,
        SECOND_FLASH,
        FADE;

    }
}

