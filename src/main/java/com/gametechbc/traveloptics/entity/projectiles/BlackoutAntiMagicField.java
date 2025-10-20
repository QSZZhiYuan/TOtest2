/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  io.redspace.ironsspellbooks.damage.DamageSources
 *  io.redspace.ironsspellbooks.entity.spells.AoeEntity
 *  io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.projectile.Projectile
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 *  org.joml.Vector3f
 */
package com.gametechbc.traveloptics.entity.projectiles;

import com.gametechbc.traveloptics.init.TravelopticsEffects;
import com.gametechbc.traveloptics.init.TravelopticsEntities;
import com.gametechbc.traveloptics.util.TravelopticsDamageTypes;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AoeEntity;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class BlackoutAntiMagicField
extends AoeEntity {
    private DamageSource movementDamageSource;
    private final Map<LivingEntity, Vec3> entityLastPositions = new HashMap<LivingEntity, Vec3>();
    private float damage = 2.0f;

    public BlackoutAntiMagicField(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public BlackoutAntiMagicField(Level level) {
        this((EntityType<? extends Projectile>)((EntityType)TravelopticsEntities.BLACKOUT_ANTI_MAGIC_FIELD.get()), level);
    }

    public void applyEffect(LivingEntity target) {
        target.m_7292_(new MobEffectInstance((MobEffect)TravelopticsEffects.BLACKOUT.get(), 50, 0, false, false, false));
    }

    public void m_8119_() {
        super.m_8119_();
        if (!this.m_9236_().f_46443_ && this.f_19797_ % 20 == 0) {
            MagicManager.spawnParticles((Level)this.m_9236_(), (ParticleOptions)new BlastwaveParticleOptions(new Vector3f(0.1f, 0.1f, 0.1f), this.getRadius()), (double)this.m_20185_(), (double)(this.m_20186_() + (double)0.165f), (double)this.m_20189_(), (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)true);
            for (LivingEntity entity2 : this.getEntitiesInsideRadius(LivingEntity.class)) {
                Vec3 lastPos;
                if (entity2 == this.m_19749_()) continue;
                Vec3 currentPos = entity2.m_20182_();
                if (!currentPos.equals((Object)(lastPos = this.entityLastPositions.getOrDefault(entity2, currentPos)))) {
                    if (this.movementDamageSource == null) {
                        this.movementDamageSource = new DamageSource(DamageSources.getHolderFromResource((Entity)entity2, TravelopticsDamageTypes.BLACKOUT), (Entity)this, this.m_19749_());
                    }
                    DamageSources.ignoreNextKnockback((LivingEntity)entity2);
                    entity2.m_6469_(this.movementDamageSource, this.damage);
                }
                this.entityLastPositions.put(entity2, currentPos);
            }
            this.entityLastPositions.keySet().removeIf(entity -> !this.isInsideRadius(entity.m_20182_()));
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
        return Optional.of(ParticleTypes.f_123762_);
    }

    private <T extends Entity> List<T> getEntitiesInsideRadius(Class<T> entityType) {
        return this.m_9236_().m_45976_(entityType, this.m_20191_());
    }

    private boolean isInsideRadius(Vec3 position) {
        return position.m_82554_(this.m_20182_()) <= (double)this.getRadius();
    }

    public float getDamage() {
        return this.damage;
    }

    public void setMovementDamage(float damage) {
        this.damage = damage;
    }
}

