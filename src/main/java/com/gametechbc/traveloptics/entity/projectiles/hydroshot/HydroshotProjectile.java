/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.api.spells.AbstractSpell
 *  io.redspace.ironsspellbooks.api.util.Utils
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  io.redspace.ironsspellbooks.damage.DamageSources
 *  io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.Mth
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.EntityHitResult
 *  net.minecraft.world.phys.Vec3
 */
package com.gametechbc.traveloptics.entity.projectiles.hydroshot;

import com.gametechbc.traveloptics.init.TravelopticsEffects;
import com.gametechbc.traveloptics.init.TravelopticsEntities;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import com.gametechbc.traveloptics.init.TravelopticsSpells;
import com.gametechbc.traveloptics.util.TravelopticsParticleHelper;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile;
import java.util.Optional;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class HydroshotProjectile
extends AbstractMagicProjectile {
    private int slownessAmplifier = 0;

    public HydroshotProjectile(EntityType<? extends HydroshotProjectile> entityType, Level level) {
        super(entityType, level);
        this.m_20242_(true);
    }

    public HydroshotProjectile(Level levelIn, LivingEntity shooter) {
        this((EntityType<? extends HydroshotProjectile>)((EntityType)TravelopticsEntities.HYDROSHOT_PROJECTILE.get()), levelIn);
        this.m_5602_((Entity)shooter);
    }

    public int getSlownessAmplifier() {
        return this.slownessAmplifier;
    }

    public void setSlownessAmplifier(int slownessAmplifier) {
        this.slownessAmplifier = slownessAmplifier;
    }

    public float getSpeed() {
        return 1.75f;
    }

    public Optional<SoundEvent> getImpactSound() {
        return Optional.of((SoundEvent)TravelopticsSounds.HYDROSHOT_IMPACT.get());
    }

    protected void doImpactSound(SoundEvent sound) {
        this.m_9236_().m_6263_(null, this.m_20185_(), this.m_20186_(), this.m_20189_(), sound, SoundSource.NEUTRAL, 2.0f, 1.2f + Utils.random.m_188501_() * 0.2f);
    }

    protected void m_8060_(BlockHitResult blockHitResult) {
        super.m_8060_(blockHitResult);
        this.m_146870_();
    }

    protected void m_5790_(EntityHitResult entityHitResult) {
        super.m_5790_(entityHitResult);
        Entity target = entityHitResult.m_82443_();
        DamageSources.applyDamage((Entity)target, (float)this.getDamage(), (DamageSource)((AbstractSpell)TravelopticsSpells.HYDROSHOT_SPELL.get()).getDamageSource((Entity)this, this.m_19749_()));
        if (target instanceof LivingEntity) {
            LivingEntity livingTarget = (LivingEntity)target;
            livingTarget.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 30, this.getSlownessAmplifier()));
            livingTarget.m_7292_(new MobEffectInstance((MobEffect)TravelopticsEffects.WET.get(), 40, 0));
        }
        this.m_146870_();
    }

    public void impactParticles(double x, double y, double z) {
        MagicManager.spawnParticles((Level)this.m_9236_(), (ParticleOptions)TravelopticsParticleHelper.WATER_SPARKS, (double)x, (double)y, (double)z, (int)15, (double)0.1, (double)0.1, (double)0.1, (double)0.25, (boolean)true);
    }

    public void trailParticles() {
        for (int i = 0; i < 1; ++i) {
            float yHeading = -((float)(Mth.m_14136_((double)this.m_20184_().f_82481_, (double)this.m_20184_().f_82479_) * 57.2957763671875) + 90.0f);
            float radius = 0.25f;
            int steps = 6;
            for (int j = 0; j < steps; ++j) {
                float offset = 1.0f / (float)steps * (float)i;
                double radians = ((float)this.f_19797_ + offset) / 7.5f * 360.0f * ((float)Math.PI / 180);
                Vec3 swirl = new Vec3(Math.cos(radians) * (double)radius, Math.sin(radians) * (double)radius, 0.0).m_82524_(yHeading * ((float)Math.PI / 180));
                double x = this.m_20185_() + swirl.f_82479_;
                double y = this.m_20186_() + swirl.f_82480_ + (double)(this.m_20206_() / 2.0f);
                double z = this.m_20189_() + swirl.f_82481_;
                Vec3 jitter = Utils.getRandomVec3((double)0.05f);
                this.m_9236_().m_6485_(TravelopticsParticleHelper.WATER_BUBBLE, true, x, y, z, jitter.f_82479_, jitter.f_82480_, jitter.f_82481_);
            }
        }
    }

    public void m_7380_(CompoundTag compound) {
        super.m_7380_(compound);
        compound.m_128405_("SlownessAmplifier", this.slownessAmplifier);
    }

    public void m_7378_(CompoundTag compound) {
        super.m_7378_(compound);
        if (compound.m_128425_("SlownessAmplifier", 3)) {
            this.slownessAmplifier = compound.m_128451_("SlownessAmplifier");
        }
    }
}

