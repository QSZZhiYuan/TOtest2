/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity
 *  io.redspace.ironsspellbooks.damage.DamageSources
 *  io.redspace.ironsspellbooks.damage.ISSDamageTypes
 *  io.redspace.ironsspellbooks.entity.spells.AoeEntity
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityDimensions
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.entity.projectile.Projectile
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 */
package com.gametechbc.traveloptics.entity.projectiles;

import com.gametechbc.traveloptics.init.TravelopticsEntities;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.damage.ISSDamageTypes;
import io.redspace.ironsspellbooks.entity.spells.AoeEntity;
import java.util.Optional;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class TectonicRiftEntity
extends AoeEntity {
    private DamageSource damageSource;

    public TectonicRiftEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public TectonicRiftEntity(Level level) {
        this((EntityType<? extends Projectile>)((EntityType)TravelopticsEntities.TECTONIC_RIFT_FIELD.get()), level);
    }

    public void applyEffect(LivingEntity target) {
        if (this.damageSource == null) {
            this.damageSource = new DamageSource(DamageSources.getHolderFromResource((Entity)target, (ResourceKey)ISSDamageTypes.FIRE_FIELD), (Entity)this, this.m_19749_());
        }
        DamageSources.ignoreNextKnockback((LivingEntity)target);
        target.m_6469_(this.damageSource, this.getDamage());
        target.m_20254_(7);
    }

    public EntityDimensions m_6972_(Pose pPose) {
        return EntityDimensions.m_20395_((float)(this.getRadius() * 1.5f), (float)(this.getRadius() * 1.5f));
    }

    public void m_8119_() {
        super.m_8119_();
        if (this.f_19797_ == 1 && !this.m_9236_().f_46443_) {
            ScreenShake_Entity.ScreenShake((Level)this.m_9236_(), (Vec3)this.m_20182_(), (float)(this.getRadius() + 12.0f), (float)0.038f, (int)this.duration, (int)(this.duration + 10));
            this.m_9236_().m_5594_(null, this.m_20183_(), (SoundEvent)TravelopticsSounds.TECTONIC_RIFT_ERUPTION.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
        }
        if (!this.m_9236_().f_46443_ && this.f_19797_ >= 200) {
            this.m_146870_();
        }
        if (this.m_9236_().f_46443_) {
            double radius = this.getRadius();
            double innerRadius = 5.0;
            for (int i = 0; i < 15; ++i) {
                double angle = this.m_9236_().f_46441_.m_188500_() * 2.0 * Math.PI;
                double distance = innerRadius + this.m_9236_().f_46441_.m_188500_() * (radius - innerRadius);
                double xOffset = Math.cos(angle) * distance;
                double zOffset = Math.sin(angle) * distance;
                double x = this.m_20185_() + xOffset;
                double y = this.m_20186_() + this.m_9236_().f_46441_.m_188500_();
                double z = this.m_20189_() + zOffset;
                double motionY = 0.3 + this.m_9236_().f_46441_.m_188500_() * 0.4;
                SimpleParticleType particle = switch (this.m_9236_().f_46441_.m_188503_(3)) {
                    case 0 -> (SimpleParticleType)ParticleTypes.f_123756_;
                    case 1 -> (SimpleParticleType)ParticleTypes.f_123756_;
                    case 2 -> (SimpleParticleType)ParticleTypes.f_123756_;
                    default -> (SimpleParticleType)ParticleTypes.f_123756_;
                };
                this.m_9236_().m_7106_((ParticleOptions)particle, x, y, z, 0.0, motionY, 0.0);
            }
        }
    }

    public float getParticleCount() {
        return 0.15f;
    }

    public Optional<ParticleOptions> getParticle() {
        return Optional.empty();
    }
}

