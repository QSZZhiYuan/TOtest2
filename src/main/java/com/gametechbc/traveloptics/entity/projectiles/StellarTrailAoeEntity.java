/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.damage.DamageSources
 *  io.redspace.ironsspellbooks.damage.ISSDamageTypes
 *  io.redspace.ironsspellbooks.entity.spells.AoeEntity
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.util.Mth
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityDimensions
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.entity.projectile.Projectile
 *  net.minecraft.world.level.Level
 */
package com.gametechbc.traveloptics.entity.projectiles;

import com.gametechbc.traveloptics.init.TravelopticsEntities;
import com.gametechbc.traveloptics.util.TravelopticsParticleHelper;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.damage.ISSDamageTypes;
import io.redspace.ironsspellbooks.entity.spells.AoeEntity;
import java.util.Optional;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

public class StellarTrailAoeEntity
extends AoeEntity {
    private DamageSource damageSource;

    public StellarTrailAoeEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public StellarTrailAoeEntity(Level level) {
        this((EntityType<? extends Projectile>)((EntityType)TravelopticsEntities.STELLAR_TRAIL_AOE.get()), level);
    }

    public EntityDimensions m_6972_(Pose pPose) {
        return EntityDimensions.m_20395_((float)(this.getRadius() * 2.0f), (float)(this.getRadius() * 1.2f));
    }

    public void applyEffect(LivingEntity target) {
        if (this.damageSource == null) {
            this.damageSource = new DamageSource(DamageSources.getHolderFromResource((Entity)target, (ResourceKey)ISSDamageTypes.ENDER_MAGIC), (Entity)this, this.m_19749_());
        }
        DamageSources.ignoreNextKnockback((LivingEntity)target);
        target.m_6469_(this.damageSource, this.getDamage());
    }

    public void m_8119_() {
        super.m_8119_();
        if (this.m_9236_().f_46443_) {
            this.createStellarTrailParticles();
        }
    }

    private void createStellarTrailParticles() {
        float radius = this.getRadius();
        int age = this.f_19797_;
        this.createConstellationStars(radius, age);
        this.createStellarDust(radius, age);
        this.createShootingStars(radius, age);
    }

    private void createConstellationStars(float radius, int age) {
        if (age % 5 == 0) {
            for (int i = 0; i < 9; ++i) {
                float x = (float)this.m_20185_() + (this.f_19796_.m_188501_() - 0.5f) * radius * 2.0f;
                float y = (float)this.m_20186_() + (this.f_19796_.m_188501_() - 0.5f) * radius * 2.0f;
                float z = (float)this.m_20189_() + (this.f_19796_.m_188501_() - 0.5f) * radius * 2.0f;
                float twinkle = 0.6f + 0.4f * Mth.m_14031_((float)((float)age * 0.1f + (float)i * 0.8f));
                if (!(twinkle > 0.8f)) continue;
                ParticleOptions particle = i % 3 == 0 ? TravelopticsParticleHelper.LIGHT_GOLD_GLOWING_ENCHANT : TravelopticsParticleHelper.LIGHT_PURPLE_GLOWING_ENCHANT;
                this.m_9236_().m_7106_(particle, (double)x, (double)y, (double)z, 0.0, 0.0, 0.0);
            }
        }
    }

    private void createStellarDust(float radius, int age) {
        if (age % 3 == 0) {
            for (int i = 0; i < 6; ++i) {
                float x = (float)this.m_20185_() + (this.f_19796_.m_188501_() - 0.5f) * radius * 1.8f;
                float y = (float)this.m_20186_() + (this.f_19796_.m_188501_() - 0.5f) * radius * 1.8f;
                float z = (float)this.m_20189_() + (this.f_19796_.m_188501_() - 0.5f) * radius * 1.8f;
                float driftX = (this.f_19796_.m_188501_() - 0.5f) * 0.01f;
                float driftY = this.f_19796_.m_188501_() * 0.008f;
                float driftZ = (this.f_19796_.m_188501_() - 0.5f) * 0.01f;
                ParticleOptions particle = this.f_19796_.m_188499_() ? TravelopticsParticleHelper.LIGHT_PURPLE_GLOWING_ENCHANT : TravelopticsParticleHelper.LIGHT_GOLD_GLOWING_ENCHANT;
                this.m_9236_().m_7106_(particle, (double)x, (double)y, (double)z, (double)driftX, (double)driftY, (double)driftZ);
            }
        }
    }

    private void createShootingStars(float radius, int age) {
        if (age % 20 == 0) {
            for (int streak = 0; streak < 2; ++streak) {
                float startX = (float)(this.m_20185_() + (double)((this.f_19796_.m_188501_() - 0.5f) * radius * 2.0f));
                float startY = (float)this.m_20186_() + (this.f_19796_.m_188501_() - 0.5f) * radius * 2.0f;
                float startZ = (float)this.m_20189_() + (this.f_19796_.m_188501_() - 0.5f) * radius * 2.0f;
                float dirX = (this.f_19796_.m_188501_() - 0.5f) * 0.4f;
                float dirY = (this.f_19796_.m_188501_() - 0.5f) * 0.2f;
                float dirZ = (this.f_19796_.m_188501_() - 0.5f) * 0.4f;
                for (int i = 0; i < 4; ++i) {
                    float trailX = startX + dirX * (float)i * 0.5f;
                    float trailY = startY + dirY * (float)i * 0.5f;
                    float trailZ = startZ + dirZ * (float)i * 0.5f;
                    ParticleOptions particle = streak == 0 ? TravelopticsParticleHelper.LIGHT_GOLD_GLOWING_ENCHANT : TravelopticsParticleHelper.LIGHT_PURPLE_GLOWING_ENCHANT;
                    this.m_9236_().m_7106_(particle, (double)trailX, (double)trailY, (double)trailZ, (double)dirX, (double)dirY, (double)dirZ);
                }
            }
        }
    }

    public float getParticleCount() {
        return 1.2f * this.getRadius();
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
}

