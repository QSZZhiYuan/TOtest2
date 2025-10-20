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
 *  net.minecraft.server.level.ServerLevel
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
import java.util.Optional;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

public class TOFollowingScreenShakeEntity
extends Entity {
    private static final EntityDataAccessor<Float> RADIUS = SynchedEntityData.m_135353_(TOFollowingScreenShakeEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135029_);
    private static final EntityDataAccessor<Float> MAGNITUDE = SynchedEntityData.m_135353_(TOFollowingScreenShakeEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135029_);
    private static final EntityDataAccessor<Integer> DURATION = SynchedEntityData.m_135353_(TOFollowingScreenShakeEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Integer> FADE_DURATION = SynchedEntityData.m_135353_(TOFollowingScreenShakeEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Integer> FADE_IN_DURATION = SynchedEntityData.m_135353_(TOFollowingScreenShakeEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Boolean> FADE_OUT_IN_DISTANCE = SynchedEntityData.m_135353_(TOFollowingScreenShakeEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Optional<UUID>> TARGET_UUID = SynchedEntityData.m_135353_(TOFollowingScreenShakeEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135041_);
    private static final EntityDataAccessor<Boolean> IS_FORCE_FADING = SynchedEntityData.m_135353_(TOFollowingScreenShakeEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private Entity cachedTarget = null;
    private int forceFadeStartTick = -1;
    private int lastPositionUpdateTick = -1;

    public TOFollowingScreenShakeEntity(EntityType<?> type, Level world) {
        super(type, world);
    }

    public TOFollowingScreenShakeEntity(Level world, Entity targetEntity, float radius, float magnitude, int duration, int fadeInDuration, int fadeOutDuration, boolean fadeOutInDistance) {
        super((EntityType)TravelopticsEntities.FOLLOWING_SCREEN_SHAKE.get(), world);
        this.setRadius(radius);
        this.setMagnitude(magnitude);
        this.setDuration(duration);
        this.setFadeInDuration(fadeInDuration);
        this.setFadeDuration(fadeOutDuration);
        this.setFadeOutInDistance(fadeOutInDistance);
        this.setTargetUUID(targetEntity.m_20148_());
        this.setForceFading(false);
        this.m_6034_(targetEntity.m_20185_(), targetEntity.m_20186_(), targetEntity.m_20189_());
        this.cachedTarget = targetEntity;
        this.lastPositionUpdateTick = 0;
    }

    @OnlyIn(value=Dist.CLIENT)
    public float getShakeAmount(Player player, float delta) {
        float baseAmount;
        float ticksDelta = (float)this.f_19797_ + delta;
        float fadeInDur = this.getFadeInDuration();
        float duration = this.getDuration();
        float fadeDur = this.getFadeDuration();
        float magnitude = this.getMagnitude();
        if (this.isForceFading()) {
            float timeSinceForceFade = ticksDelta - (float)this.forceFadeStartTick;
            if (timeSinceForceFade >= fadeDur) {
                baseAmount = 0.0f;
            } else {
                float timeFrac = 1.0f - timeSinceForceFade / (fadeDur + 1.0f);
                baseAmount = timeFrac * timeFrac * magnitude;
            }
        } else if (ticksDelta < fadeInDur) {
            float progress = ticksDelta / fadeInDur;
            baseAmount = progress * progress * magnitude;
        } else if (ticksDelta < fadeInDur + duration) {
            baseAmount = magnitude;
        } else if (ticksDelta < fadeInDur + duration + fadeDur) {
            float timeFrac = 1.0f - (ticksDelta - fadeInDur - duration) / (fadeDur + 1.0f);
            baseAmount = timeFrac * timeFrac * magnitude;
        } else {
            baseAmount = 0.0f;
        }
        Vec3 playerPos = player.m_20299_(delta);
        double distance = this.m_20182_().m_82554_(playerPos);
        float minStrengthFactor = 0.1f;
        float falloffStart = 0.7f * this.getRadius();
        float distanceFactor = this.getFadeOutInDistance() ? (distance <= (double)falloffStart ? 1.0f : (distance >= (double)this.getRadius() ? minStrengthFactor : minStrengthFactor + (float)((double)this.getRadius() - distance) / (this.getRadius() - falloffStart) * (1.0f - minStrengthFactor))) : (distance <= (double)this.getRadius() ? 1.0f : 0.0f);
        return distance <= (double)this.getRadius() ? baseAmount * distanceFactor : 0.0f;
    }

    public void m_8119_() {
        super.m_8119_();
        boolean shouldUpdatePosition = this.f_19797_ - this.lastPositionUpdateTick >= 2;
        Entity target = this.getTargetEntity();
        if (target == null || target.m_213877_()) {
            if (!this.isForceFading()) {
                this.startForceFading();
            }
        } else if (shouldUpdatePosition) {
            this.m_6034_(target.m_20185_(), target.m_20186_(), target.m_20189_());
            this.cachedTarget = target;
            this.lastPositionUpdateTick = this.f_19797_;
        }
        if (this.isForceFading()) {
            float timeSinceForceFade = this.f_19797_ - this.forceFadeStartTick;
            if (timeSinceForceFade > (float)this.getFadeDuration()) {
                this.m_146870_();
            }
        } else {
            int totalDuration = this.getFadeInDuration() + this.getDuration() + this.getFadeDuration();
            if (this.f_19797_ > totalDuration) {
                this.m_146870_();
            }
        }
    }

    private Entity getTargetEntity() {
        if (this.cachedTarget != null && !this.cachedTarget.m_213877_()) {
            return this.cachedTarget;
        }
        Optional<UUID> targetUUID = this.getTargetUUID();
        if (targetUUID.isPresent()) {
            Level level = this.m_9236_();
            if (level instanceof ServerLevel) {
                ServerLevel serverLevel = (ServerLevel)level;
                Entity entity = serverLevel.m_8791_(targetUUID.get());
                if (entity != null) {
                    this.cachedTarget = entity;
                    return entity;
                }
            } else {
                for (Entity entity : this.m_9236_().m_45976_(Entity.class, this.m_20191_().m_82400_((double)(this.getRadius() * 2.0f)))) {
                    if (!entity.m_20148_().equals(targetUUID.get())) continue;
                    this.cachedTarget = entity;
                    return entity;
                }
            }
        }
        this.cachedTarget = null;
        return null;
    }

    private void startForceFading() {
        this.setForceFading(true);
        this.forceFadeStartTick = this.f_19797_;
    }

    protected void m_8097_() {
        this.f_19804_.m_135372_(RADIUS, (Object)Float.valueOf(10.0f));
        this.f_19804_.m_135372_(MAGNITUDE, (Object)Float.valueOf(1.0f));
        this.f_19804_.m_135372_(DURATION, (Object)0);
        this.f_19804_.m_135372_(FADE_DURATION, (Object)5);
        this.f_19804_.m_135372_(FADE_IN_DURATION, (Object)0);
        this.f_19804_.m_135372_(FADE_OUT_IN_DISTANCE, (Object)false);
        this.f_19804_.m_135372_(TARGET_UUID, Optional.empty());
        this.f_19804_.m_135372_(IS_FORCE_FADING, (Object)false);
    }

    public float getRadius() {
        return ((Float)this.f_19804_.m_135370_(RADIUS)).floatValue();
    }

    public void setRadius(float radius) {
        this.f_19804_.m_135381_(RADIUS, (Object)Float.valueOf(radius));
    }

    public float getMagnitude() {
        return ((Float)this.f_19804_.m_135370_(MAGNITUDE)).floatValue();
    }

    public void setMagnitude(float magnitude) {
        this.f_19804_.m_135381_(MAGNITUDE, (Object)Float.valueOf(magnitude));
    }

    public int getDuration() {
        return (Integer)this.f_19804_.m_135370_(DURATION);
    }

    public void setDuration(int duration) {
        this.f_19804_.m_135381_(DURATION, (Object)duration);
    }

    public int getFadeDuration() {
        return (Integer)this.f_19804_.m_135370_(FADE_DURATION);
    }

    public void setFadeDuration(int fadeDuration) {
        this.f_19804_.m_135381_(FADE_DURATION, (Object)fadeDuration);
    }

    public int getFadeInDuration() {
        return (Integer)this.f_19804_.m_135370_(FADE_IN_DURATION);
    }

    public void setFadeInDuration(int fadeInDuration) {
        this.f_19804_.m_135381_(FADE_IN_DURATION, (Object)fadeInDuration);
    }

    public boolean getFadeOutInDistance() {
        return (Boolean)this.f_19804_.m_135370_(FADE_OUT_IN_DISTANCE);
    }

    public void setFadeOutInDistance(boolean fadeOutInDistance) {
        this.f_19804_.m_135381_(FADE_OUT_IN_DISTANCE, (Object)fadeOutInDistance);
    }

    public Optional<UUID> getTargetUUID() {
        return (Optional)this.f_19804_.m_135370_(TARGET_UUID);
    }

    public void setTargetUUID(UUID targetUUID) {
        this.f_19804_.m_135381_(TARGET_UUID, Optional.of(targetUUID));
    }

    public boolean isForceFading() {
        return (Boolean)this.f_19804_.m_135370_(IS_FORCE_FADING);
    }

    public void setForceFading(boolean forceFading) {
        this.f_19804_.m_135381_(IS_FORCE_FADING, (Object)forceFading);
    }

    protected void m_7378_(CompoundTag compound) {
        this.setRadius(compound.m_128457_("radius"));
        this.setMagnitude(compound.m_128457_("magnitude"));
        this.setDuration(compound.m_128451_("duration"));
        this.setFadeDuration(compound.m_128451_("fade_duration"));
        this.setFadeInDuration(compound.m_128451_("fade_in_duration"));
        this.setFadeOutInDistance(compound.m_128471_("fade_out_in_distance"));
        if (compound.m_128403_("target_uuid")) {
            this.setTargetUUID(compound.m_128342_("target_uuid"));
        }
        this.setForceFading(compound.m_128471_("is_force_fading"));
        this.forceFadeStartTick = compound.m_128451_("force_fade_start_tick");
        this.lastPositionUpdateTick = compound.m_128451_("last_position_update_tick");
        this.f_19797_ = compound.m_128451_("ticks_existed");
    }

    protected void m_7380_(CompoundTag compound) {
        compound.m_128350_("radius", this.getRadius());
        compound.m_128350_("magnitude", this.getMagnitude());
        compound.m_128405_("duration", this.getDuration());
        compound.m_128405_("fade_duration", this.getFadeDuration());
        compound.m_128405_("fade_in_duration", this.getFadeInDuration());
        compound.m_128379_("fade_out_in_distance", this.getFadeOutInDistance());
        Optional<UUID> targetUUID = this.getTargetUUID();
        targetUUID.ifPresent(value -> compound.m_128362_("target_uuid", value));
        compound.m_128379_("is_force_fading", this.isForceFading());
        compound.m_128405_("force_fade_start_tick", this.forceFadeStartTick);
        compound.m_128405_("last_position_update_tick", this.lastPositionUpdateTick);
        compound.m_128405_("ticks_existed", this.f_19797_);
    }

    public Packet<ClientGamePacketListener> m_5654_() {
        return NetworkHooks.getEntitySpawningPacket((Entity)this);
    }

    public static void createFollowingScreenShake(Level world, Entity targetEntity, float radius, float magnitude, int duration, int fadeInDuration, int fadeOutDuration, boolean fadeOutInDistance) {
        if (!world.f_46443_) {
            TOFollowingScreenShakeEntity screenShake = new TOFollowingScreenShakeEntity(world, targetEntity, radius, magnitude, duration, fadeInDuration, fadeOutDuration, fadeOutInDistance);
            world.m_7967_((Entity)screenShake);
        }
    }
}

