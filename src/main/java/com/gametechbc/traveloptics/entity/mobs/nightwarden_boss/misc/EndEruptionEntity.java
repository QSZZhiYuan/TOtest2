/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity
 *  io.redspace.ironsspellbooks.entity.spells.AoeEntity
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.projectile.Projectile
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 */
package com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.misc;

import com.gametechbc.traveloptics.init.TravelopticsEntities;
import com.gametechbc.traveloptics.init.TravelopticsParticles;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import com.gametechbc.traveloptics.util.TravelopticsParticleHelper;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import io.redspace.ironsspellbooks.entity.spells.AoeEntity;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class EndEruptionEntity
extends AoeEntity {
    private static final EntityDataAccessor<Integer> WINDUP = SynchedEntityData.m_135353_(EndEruptionEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
    private boolean triggered = false;
    private boolean eruptionStarted = false;

    public EndEruptionEntity(EntityType<? extends Projectile> type, Level level) {
        super(type, level);
    }

    public EndEruptionEntity(Level level) {
        this((EntityType<? extends Projectile>)((EntityType)TravelopticsEntities.END_ERUPTION.get()), level);
    }

    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(WINDUP, (Object)60);
    }

    protected void m_7380_(CompoundTag tag) {
        super.m_7380_(tag);
        tag.m_128405_("WindupDuration", this.getWindupDuration());
        tag.m_128379_("Triggered", this.triggered);
        tag.m_128379_("EruptionStarted", this.eruptionStarted);
    }

    protected void m_7378_(CompoundTag tag) {
        super.m_7378_(tag);
        if (tag.m_128441_("WindupDuration")) {
            this.setWindupDuration(tag.m_128451_("WindupDuration"));
        }
        if (tag.m_128441_("Triggered")) {
            this.triggered = tag.m_128471_("Triggered");
        }
        if (tag.m_128441_("EruptionStarted")) {
            this.eruptionStarted = tag.m_128471_("EruptionStarted");
        }
    }

    public void setWindupDuration(int ticks) {
        this.f_19804_.m_135381_(WINDUP, (Object)ticks);
    }

    public int getWindupDuration() {
        return (Integer)this.f_19804_.m_135370_(WINDUP);
    }

    public void m_8119_() {
        super.m_8119_();
        if (this.m_9236_().f_46443_) {
            this.handleClientParticles();
        }
        if (!this.eruptionStarted && this.f_19797_ >= this.getWindupDuration() - 5) {
            this.eruptionStarted = true;
        }
        if (!this.triggered && this.f_19797_ >= this.getWindupDuration()) {
            this.triggerEruption();
            this.m_5496_((SoundEvent)TravelopticsSounds.END_ERUPTION_AMBIENT.get(), 1.0f, 1.0f);
            this.m_5496_((SoundEvent)TravelopticsSounds.END_ERUPTION_BLAST.get(), 1.0f, 1.0f);
        } else if (this.triggered && this.f_19797_ >= this.getWindupDuration() + 20) {
            this.m_5496_((SoundEvent)TravelopticsSounds.END_ERUPTION_BLAST.get(), 1.0f, 1.0f);
            this.triggerEruption();
            this.m_146870_();
        }
    }

    private void handleClientParticles() {
        double radius = this.getRadius();
        Vec3 center = this.m_20182_();
        if (this.f_19797_ < this.getWindupDuration() - 5) {
            if ((double)this.f_19797_ < (double)this.getWindupDuration() * 0.3) {
                this.createMagicCircleFormation(center, radius);
            } else if ((double)this.f_19797_ < (double)this.getWindupDuration() * 0.7) {
                this.createEnergyGathering(center, radius);
            } else {
                this.createIntenseBuildup(center, radius);
            }
        } else if (this.f_19797_ < this.getWindupDuration()) {
            this.createPreEruptionParticles(center, radius);
        } else if (this.f_19797_ < this.getWindupDuration() + 20) {
            this.createEruptionParticles(center, radius);
        }
    }

    private void createMagicCircleFormation(Vec3 center, double radius) {
        float progress = (float)this.f_19797_ / ((float)this.getWindupDuration() * 0.3f);
        int circleCount = 3;
        for (int circle = 0; circle < circleCount; ++circle) {
            double circleRadius = radius * (0.4 + (double)circle * 0.3);
            int particlesPerCircle = 16 * (circle + 1);
            for (int i = 0; i < particlesPerCircle; ++i) {
                double angle = Math.PI * 2 * (double)i / (double)particlesPerCircle + (double)this.f_19797_ * 0.05 * (double)(circle + 1);
                double x = center.f_82479_ + Math.cos(angle) * circleRadius;
                double z = center.f_82481_ + Math.sin(angle) * circleRadius;
                double y = center.f_82480_ + 0.1 + Math.sin((double)this.f_19797_ * 0.1 + (double)i) * 0.05;
                if (!(this.f_19796_.m_188501_() < progress * 0.8f)) continue;
                this.m_9236_().m_7106_(TravelopticsParticleHelper.VERY_SHORT_LIGHT_PURPLE_GLOWING_ENCHANT, x, y, z, 0.0, 0.01, 0.0);
            }
        }
        if (this.f_19797_ % 3 == 0) {
            for (int i = 0; i < 4; ++i) {
                double angle = 1.5707963267948966 * (double)i + (double)this.f_19797_ * 0.02;
                double x = center.f_82479_ + Math.cos(angle) * radius * 0.15;
                double z = center.f_82481_ + Math.sin(angle) * radius * 0.15;
                this.m_9236_().m_7106_(TravelopticsParticleHelper.VERY_SHORT_LIGHT_PURPLE_GLOWING_ENCHANT, x, center.f_82480_ + 0.2, z, 0.0, 0.02, 0.0);
            }
        }
    }

    private void createEnergyGathering(Vec3 center, double radius) {
        double z;
        double x;
        double angle;
        int i;
        for (i = 0; i < 8; ++i) {
            angle = (double)this.f_19797_ * 0.15 + (double)i * Math.PI * 0.25;
            double spiralRadius = radius * 1.5 * (1.0 - ((double)this.f_19797_ - (double)this.getWindupDuration() * 0.3) / ((double)this.getWindupDuration() * 0.4));
            x = center.f_82479_ + Math.cos(angle) * spiralRadius;
            z = center.f_82481_ + Math.sin(angle) * spiralRadius;
            double y = center.f_82480_ + 0.5 + Math.sin((double)this.f_19797_ * 0.1 + (double)i) * 0.3;
            double velX = (center.f_82479_ - x) * 0.05;
            double velZ = (center.f_82481_ - z) * 0.05;
            this.m_9236_().m_7106_((ParticleOptions)TravelopticsParticles.PURPLE_STAR_INWARD_PARTICLE.get(), x, y, z, velX, -0.02, velZ);
        }
        if (this.f_19797_ % 10 == 0) {
            for (i = 0; i < 6; ++i) {
                angle = this.f_19796_.m_188500_() * 2.0 * Math.PI;
                double distance = this.f_19796_.m_188500_() * radius * 0.3;
                x = center.f_82479_ + Math.cos(angle) * distance;
                z = center.f_82481_ + Math.sin(angle) * distance;
                this.m_9236_().m_7106_((ParticleOptions)ParticleTypes.f_123810_, x, center.f_82480_ + 0.1, z, 0.0, 0.05, 0.0);
            }
        }
        this.createMagicCircleFormation(center, radius);
    }

    private void createIntenseBuildup(Vec3 center, double radius) {
        double z;
        double x;
        double distance;
        double angle;
        int i;
        float intensity = (float)(((double)this.f_19797_ - (double)this.getWindupDuration() * 0.7) / ((double)this.getWindupDuration() * 0.3));
        for (i = 0; i < 8; ++i) {
            angle = this.f_19796_.m_188500_() * 2.0 * Math.PI;
            distance = radius * 2.0 * (1.0 - (double)intensity);
            x = center.f_82479_ + Math.cos(angle) * distance;
            z = center.f_82481_ + Math.sin(angle) * distance;
            double y = center.f_82480_ + this.f_19796_.m_188500_() * 2.0;
            double velX = (center.f_82479_ - x) * 0.1;
            double velZ = (center.f_82481_ - z) * 0.1;
            double velY = (center.f_82480_ - y) * 0.05;
            this.m_9236_().m_7106_((ParticleOptions)TravelopticsParticles.PURPLE_STAR_INWARD_PARTICLE.get(), x, y, z, velX, velY, velZ);
        }
        if (this.f_19797_ % 2 == 0) {
            this.createMagicCircleFormation(center, radius * (1.0 + (double)intensity * 0.5));
        }
        for (i = 0; i < (int)(intensity * 10.0f); ++i) {
            double x2 = center.f_82479_ + (this.f_19796_.m_188500_() - 0.5) * radius * 0.5;
            double z2 = center.f_82481_ + (this.f_19796_.m_188500_() - 0.5) * radius * 0.5;
            double y = center.f_82480_ + this.f_19796_.m_188500_() * 0.5;
            this.m_9236_().m_7106_(TravelopticsParticleHelper.SHORT_LIGHT_PURPLE_GLOWING_ENCHANT, x2, y, z2, 0.0, 0.02 + (double)intensity * 0.05, 0.0);
        }
        if (this.f_19797_ % 3 == 0) {
            for (i = 0; i < (int)(intensity * 8.0f); ++i) {
                angle = this.f_19796_.m_188500_() * 2.0 * Math.PI;
                distance = this.f_19796_.m_188500_() * radius * 0.4;
                x = center.f_82479_ + Math.cos(angle) * distance;
                z = center.f_82481_ + Math.sin(angle) * distance;
                this.m_9236_().m_7106_((ParticleOptions)ParticleTypes.f_123810_, x, center.f_82480_ + 0.1, z, 0.0, 0.03 + (double)intensity * 0.02, 0.0);
            }
        }
    }

    private void createPreEruptionParticles(Vec3 center, double radius) {
        int i;
        for (i = 0; i < 20; ++i) {
            double x = center.f_82479_ + (this.f_19796_.m_188500_() - 0.5) * radius;
            double z = center.f_82481_ + (this.f_19796_.m_188500_() - 0.5) * radius;
            double y = center.f_82480_ + this.f_19796_.m_188500_() * 3.0;
            this.m_9236_().m_7106_(TravelopticsParticleHelper.SHORT_LIGHT_PURPLE_GLOWING_ENCHANT, x, y, z, 0.0, -0.1, 0.0);
            if (i % 3 != 0) continue;
            this.m_9236_().m_7106_(TravelopticsParticleHelper.ABYSS_SPIKE_PARTICLE, x, y, z, 0.0, 0.15, 0.0);
        }
        for (i = 0; i < 30; ++i) {
            double angle = this.f_19796_.m_188500_() * 2.0 * Math.PI;
            double distance = this.f_19796_.m_188500_() * radius * 1.5;
            double x = center.f_82479_ + Math.cos(angle) * distance;
            double z = center.f_82481_ + Math.sin(angle) * distance;
            this.m_9236_().m_7106_(TravelopticsParticleHelper.VERY_SHORT_LIGHT_PURPLE_GLOWING_ENCHANT, x, center.f_82480_ + 0.05, z, 0.0, 0.05, 0.0);
        }
    }

    private void createEruptionParticles(Vec3 center, double radius) {
        double angle;
        double velY;
        double z;
        double x;
        int i;
        int eruptionTick = this.f_19797_ - this.getWindupDuration();
        for (i = 0; i < 40; ++i) {
            double offsetX = (this.f_19796_.m_188500_() - 0.5) * radius * 0.8;
            double offsetZ = (this.f_19796_.m_188500_() - 0.5) * radius * 0.8;
            x = center.f_82479_ + offsetX;
            z = center.f_82481_ + offsetZ;
            double y = center.f_82480_;
            velY = 0.8 + this.f_19796_.m_188500_() * 1.2;
            double velX = (this.f_19796_.m_188500_() - 0.5) * 0.1;
            double velZ = (this.f_19796_.m_188500_() - 0.5) * 0.1;
            this.m_9236_().m_7106_(TravelopticsParticleHelper.ABYSS_SPIKE_PARTICLE, x, y, z, velX, velY, velZ);
        }
        for (i = 0; i < 25; ++i) {
            angle = this.f_19796_.m_188500_() * 2.0 * Math.PI;
            double pitch = this.f_19796_.m_188500_() * Math.PI * 0.4 + 0.3141592653589793;
            double speed = 0.5 + this.f_19796_.m_188500_();
            double velX = Math.cos(angle) * Math.sin(pitch) * speed * 0.6;
            double velY2 = Math.cos(pitch) * speed;
            double velZ = Math.sin(angle) * Math.sin(pitch) * speed * 0.6;
            this.m_9236_().m_7106_((ParticleOptions)TravelopticsParticles.PURPLE_STAR_OUTWARD_PARTICLE.get(), center.f_82479_, center.f_82480_ + 0.5, center.f_82481_, velX, velY2, velZ);
        }
        if (eruptionTick < 15) {
            for (int ring = 0; ring < 3; ++ring) {
                double ringRadius = radius * (0.3 + (double)ring * 0.3);
                int spikesInRing = 8 + ring * 4;
                for (int i2 = 0; i2 < spikesInRing; ++i2) {
                    if (!(this.f_19796_.m_188501_() < 0.6f)) continue;
                    double angle2 = Math.PI * 2 * (double)i2 / (double)spikesInRing + this.f_19796_.m_188500_() * 0.5;
                    double x2 = center.f_82479_ + Math.cos(angle2) * ringRadius;
                    double z2 = center.f_82481_ + Math.sin(angle2) * ringRadius;
                    if (eruptionTick <= ring * 2) continue;
                    velY = 0.6 + this.f_19796_.m_188500_() * 0.8;
                    this.m_9236_().m_7106_(TravelopticsParticleHelper.ABYSS_SPIKE_PARTICLE, x2, center.f_82480_, z2, 0.0, velY, 0.0);
                }
            }
        }
        for (i = 0; i < 15; ++i) {
            angle = this.f_19796_.m_188500_() * 2.0 * Math.PI;
            double distance = this.f_19796_.m_188500_() * radius * 1.2;
            x = center.f_82479_ + Math.cos(angle) * distance;
            z = center.f_82481_ + Math.sin(angle) * distance;
            this.m_9236_().m_7106_(TravelopticsParticleHelper.SHORT_LIGHT_PURPLE_GLOWING_ENCHANT, x, center.f_82480_ + 0.1, z, (this.f_19796_.m_188500_() - 0.5) * 0.08, 0.15 + this.f_19796_.m_188500_() * 0.4, (this.f_19796_.m_188500_() - 0.5) * 0.08);
        }
        if (eruptionTick > 5) {
            for (i = 0; i < 8; ++i) {
                double x3 = center.f_82479_ + (this.f_19796_.m_188500_() - 0.5) * radius * 1.5;
                double z3 = center.f_82481_ + (this.f_19796_.m_188500_() - 0.5) * radius * 1.5;
                double y = center.f_82480_ + this.f_19796_.m_188500_() * 4.0;
                this.m_9236_().m_7106_((ParticleOptions)ParticleTypes.f_123810_, x3, y, z3, (this.f_19796_.m_188500_() - 0.5) * 0.05, -0.01, (this.f_19796_.m_188500_() - 0.5) * 0.05);
            }
        }
    }

    private void triggerEruption() {
        this.triggered = true;
        double radius = this.getRadius();
        double maxY = this.m_20186_() + 4.0;
        double minY = this.m_20186_() - 1.0;
        AABB searchBox = new AABB(this.m_20185_() - radius, minY, this.m_20189_() - radius, this.m_20185_() + radius, maxY, this.m_20189_() + radius);
        List targets = this.m_9236_().m_6443_(LivingEntity.class, searchBox, entity -> entity.m_6084_() && entity != this.m_19749_());
        for (LivingEntity target : targets) {
            double dz;
            double dx = target.m_20185_() - this.m_20185_();
            double horizontalDistanceSq = dx * dx + (dz = target.m_20189_() - this.m_20189_()) * dz;
            if (!(horizontalDistanceSq <= radius * radius)) continue;
            DamageSource damageSource = this.m_269291_().m_269104_((Entity)this, this.m_19749_());
            target.m_6469_(damageSource, this.getDamage());
        }
        ScreenShake_Entity.ScreenShake((Level)this.m_9236_(), (Vec3)this.m_20182_(), (float)((float)(radius + 10.0)), (float)0.05f, (int)8, (int)10);
    }

    public void applyEffect(LivingEntity target) {
    }

    public float getParticleCount() {
        return 0.15f;
    }

    public Optional<ParticleOptions> getParticle() {
        return Optional.empty();
    }
}

