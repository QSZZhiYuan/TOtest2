/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.api.magic.MagicData
 *  io.redspace.ironsspellbooks.damage.DamageSources
 *  io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible
 *  io.redspace.ironsspellbooks.entity.spells.AoeEntity
 *  io.redspace.ironsspellbooks.util.ParticleHelper
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.projectile.Projectile
 *  net.minecraft.world.level.Level
 */
package com.gametechbc.traveloptics.entity.projectiles;

import com.gametechbc.traveloptics.init.TravelopticsEntities;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import com.gametechbc.traveloptics.util.TravelopticsDamageTypes;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import io.redspace.ironsspellbooks.entity.spells.AoeEntity;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import java.util.Optional;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

public class BlizzardAoe
extends AoeEntity
implements AntiMagicSusceptible {
    private int slownessAmplifier = 0;
    private DamageSource damageSource;

    public BlizzardAoe(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public BlizzardAoe(Level level) {
        this((EntityType<? extends Projectile>)((EntityType)TravelopticsEntities.BLIZZARD_ENTITY.get()), level);
    }

    public int getSlownessAmplifier() {
        return this.slownessAmplifier;
    }

    public void setSlownessAmplifier(int slownessAmplifier) {
        this.slownessAmplifier = slownessAmplifier;
    }

    public void applyEffect(LivingEntity target) {
        if (this.damageSource == null) {
            this.damageSource = new DamageSource(DamageSources.getHolderFromResource((Entity)target, TravelopticsDamageTypes.BLIZZARD_DAMAGE), (Entity)this, this.m_19749_());
        }
        DamageSources.ignoreNextKnockback((LivingEntity)target);
        target.m_6469_(this.damageSource, this.getDamage());
        target.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 20, this.getSlownessAmplifier()));
    }

    public void m_8119_() {
        super.m_8119_();
        if (!this.m_9236_().f_46443_ && this.f_19797_ == 1) {
            this.m_5496_((SoundEvent)TravelopticsSounds.BLIZZARD_AOE_IDLE.get(), 1.0f, 1.0f);
        }
        this.spawnRainParticles();
        if (this.f_19797_ % 5 == 0) {
            this.spawnCloudParticles();
        }
        if (!this.m_9236_().f_46443_ && this.f_19797_ >= 100) {
            this.m_146870_();
        }
    }

    private void spawnRainParticles() {
        double radius = this.getRadius();
        double snowStartY = this.m_20186_() + 4.0;
        int snowParticleCount = (int)(radius * radius);
        for (int i = 0; i < snowParticleCount; ++i) {
            double angle = this.f_19796_.m_188500_() * Math.PI * 2.0;
            double distance = this.f_19796_.m_188500_() * radius;
            double randomX = this.m_20185_() + Math.cos(angle) * distance;
            double randomZ = this.m_20189_() + Math.sin(angle) * distance;
            double randomY = snowStartY + this.f_19796_.m_188500_() * 3.0;
            double velocityX = (this.f_19796_.m_188500_() - 0.5) * 0.2;
            double velocityY = -0.3 - this.f_19796_.m_188500_() * 0.4;
            double velocityZ = (this.f_19796_.m_188500_() - 0.5) * 0.2;
            this.m_9236_().m_7106_((ParticleOptions)ParticleTypes.f_123790_, randomX, randomY, randomZ, velocityX, velocityY, velocityZ);
        }
        int additionalSnow = (int)(radius * radius);
        for (int i = 0; i < additionalSnow; ++i) {
            double angle = this.f_19796_.m_188500_() * Math.PI * 2.0;
            double distance = this.f_19796_.m_188500_() * radius;
            double randomX = this.m_20185_() + Math.cos(angle) * distance;
            double randomZ = this.m_20189_() + Math.sin(angle) * distance;
            double randomY = snowStartY + this.f_19796_.m_188500_() * 4.0;
            double velocityX = (this.f_19796_.m_188500_() - 0.5) * 0.3;
            double velocityY = -0.1 - this.f_19796_.m_188500_() * 0.2;
            double velocityZ = (this.f_19796_.m_188500_() - 0.5) * 0.3;
            this.m_9236_().m_7106_((ParticleOptions)ParticleTypes.f_175821_, randomX, randomY, randomZ, velocityX, velocityY, velocityZ);
        }
        if (this.f_19797_ % 3 == 0) {
            int windParticles = (int)(radius * 2.0);
            for (int i = 0; i < windParticles; ++i) {
                double angle = this.f_19796_.m_188500_() * Math.PI * 2.0;
                double distance = this.f_19796_.m_188500_() * radius;
                double randomX = this.m_20185_() + Math.cos(angle) * distance;
                double randomZ = this.m_20189_() + Math.sin(angle) * distance;
                double randomY = this.m_20186_() + this.f_19796_.m_188500_() * 1.5;
                double velocityX = (this.f_19796_.m_188500_() - 0.5) * 0.1;
                double velocityY = 0.02 + this.f_19796_.m_188500_() * 0.03;
                double velocityZ = (this.f_19796_.m_188500_() - 0.5) * 0.1;
                this.m_9236_().m_7106_((ParticleOptions)ParticleTypes.f_123796_, randomX, randomY, randomZ, velocityX, velocityY, velocityZ);
            }
        }
    }

    private void spawnCloudParticles() {
        double radius = this.getRadius();
        int particleCount = (int)(1.0f * this.getRadius());
        for (int i = 0; i < particleCount; ++i) {
            double randomX = this.m_20185_() + (this.f_19796_.m_188500_() * 2.0 * radius - radius);
            double randomY = this.m_20186_() + 8.0;
            double randomZ = this.m_20189_() + (this.f_19796_.m_188500_() * 2.0 * radius - radius);
            double velocityY = -0.5 - this.f_19796_.m_188500_() * 0.5;
            this.m_9236_().m_7106_(ParticleHelper.FOG, randomX, randomY, randomZ, 0.0, velocityY, 0.0);
        }
    }

    public float getParticleCount() {
        return 0.0f;
    }

    protected float particleYOffset() {
        return 0.25f;
    }

    protected float getParticleSpeedModifier() {
        return 1.4f;
    }

    public Optional<ParticleOptions> getParticle() {
        return Optional.empty();
    }

    public void onAntiMagic(MagicData magicData) {
        this.m_146870_();
    }

    protected void m_7380_(CompoundTag compound) {
        super.m_7380_(compound);
        compound.m_128405_("SlownessAmplifier", this.slownessAmplifier);
    }

    protected void m_7378_(CompoundTag compound) {
        super.m_7378_(compound);
        if (compound.m_128425_("SlownessAmplifier", 3)) {
            this.slownessAmplifier = compound.m_128451_("SlownessAmplifier");
        }
    }
}

