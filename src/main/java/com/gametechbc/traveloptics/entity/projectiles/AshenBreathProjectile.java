/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.api.spells.AbstractSpell
 *  io.redspace.ironsspellbooks.damage.DamageSources
 *  io.redspace.ironsspellbooks.entity.spells.AbstractConeProjectile
 *  io.redspace.ironsspellbooks.util.ParticleHelper
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.EntityHitResult
 *  net.minecraft.world.phys.Vec3
 */
package com.gametechbc.traveloptics.entity.projectiles;

import com.gametechbc.traveloptics.init.TravelopticsEntities;
import com.gametechbc.traveloptics.init.TravelopticsSpells;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AbstractConeProjectile;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class AshenBreathProjectile
extends AbstractConeProjectile {
    public AshenBreathProjectile(EntityType<? extends AbstractConeProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public AshenBreathProjectile(Level level, LivingEntity entity) {
        super((EntityType)TravelopticsEntities.ASHEN_BREATH_PROJECTILE.get(), level, entity);
    }

    public void spawnParticles() {
        Entity owner = this.m_19749_();
        if (!this.m_9236_().f_46443_ || owner == null) {
            return;
        }
        Vec3 rotation = owner.m_20154_().m_82541_();
        Vec3 pos = owner.m_20182_().m_82549_(rotation.m_82490_(1.6));
        double x = pos.f_82479_;
        double y = pos.f_82480_ + (double)(owner.m_20192_() * 0.9f);
        double z = pos.f_82481_;
        double speed = this.f_19796_.m_188500_() * 0.4 + 0.45;
        for (int i = 0; i < 24; ++i) {
            double offset = 0.25;
            double ox = Math.random() * 2.0 * offset - offset;
            double oy = Math.random() * 2.0 * offset - offset;
            double oz = Math.random() * 2.0 * offset - offset;
            double angularness = 0.8;
            Vec3 randomVec = new Vec3(Math.random() * 2.0 * angularness - angularness, Math.random() * 2.0 * angularness - angularness, Math.random() * 2.0 * angularness - angularness).m_82541_();
            Vec3 result = rotation.m_82490_(3.0).m_82549_(randomVec).m_82541_().m_82490_(speed);
            this.m_9236_().m_7106_((ParticleOptions)(this.f_19796_.m_188501_() < 0.97f ? ParticleTypes.f_123762_ : ParticleHelper.FIRE), x + ox, y + oy, z + oz, result.f_82479_, result.f_82480_, result.f_82481_);
        }
    }

    protected void m_5790_(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.m_82443_();
        if (DamageSources.applyDamage((Entity)entity, (float)this.damage, (DamageSource)((AbstractSpell)TravelopticsSpells.ASHEN_BREATH.get()).getDamageSource((Entity)this, this.m_19749_())) && entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity)entity;
            livingEntity.m_7292_(new MobEffectInstance(MobEffects.f_19610_, 60, 0));
        }
    }
}

