/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.api.magic.MagicData
 *  io.redspace.ironsspellbooks.api.spells.AbstractSpell
 *  io.redspace.ironsspellbooks.api.util.CameraShakeData
 *  io.redspace.ironsspellbooks.api.util.CameraShakeManager
 *  io.redspace.ironsspellbooks.damage.DamageSources
 *  io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible
 *  io.redspace.ironsspellbooks.registries.SoundRegistry
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.TamableAnimal
 *  net.minecraft.world.entity.projectile.Projectile
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  software.bernie.geckolib.animatable.GeoEntity
 *  software.bernie.geckolib.core.animatable.GeoAnimatable
 *  software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache
 *  software.bernie.geckolib.core.animation.AnimatableManager$ControllerRegistrar
 *  software.bernie.geckolib.core.animation.AnimationController
 *  software.bernie.geckolib.core.animation.AnimationState
 *  software.bernie.geckolib.core.animation.RawAnimation
 *  software.bernie.geckolib.core.object.PlayState
 *  software.bernie.geckolib.util.GeckoLibUtil
 */
package com.gametechbc.traveloptics.entity.projectiles.supernova.supermassive_black_hole;

import com.gametechbc.traveloptics.init.TravelopticsEntities;
import com.gametechbc.traveloptics.init.TravelopticsParticles;
import com.gametechbc.traveloptics.init.TravelopticsSpells;
import com.gametechbc.traveloptics.util.TravelopticsTags;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.util.CameraShakeData;
import io.redspace.ironsspellbooks.api.util.CameraShakeManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import java.util.List;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SupermassiveBlackHoleEntity
extends Projectile
implements GeoEntity,
AntiMagicSusceptible {
    private static final EntityDataAccessor<Float> DATA_DAMAGE = SynchedEntityData.m_135353_(SupermassiveBlackHoleEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135029_);
    private static final EntityDataAccessor<Float> DATA_RADIUS = SynchedEntityData.m_135353_(SupermassiveBlackHoleEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135029_);
    private int maxAge = 200;
    private static final int loopSoundDurationInTicks = 320;
    private final RawAnimation SPIN_ANIMATION = RawAnimation.begin().thenPlay("blackhole_spin_loop");
    private final AnimationController controller = new AnimationController((GeoAnimatable)this, "blackhole_controller", 0, this::animationPredicate);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);

    public SupermassiveBlackHoleEntity(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    public SupermassiveBlackHoleEntity(Level level) {
        this((EntityType<? extends Projectile>)((EntityType)TravelopticsEntities.SUPERMASSIVE_BLACK_HOLE.get()), level);
    }

    protected void m_8097_() {
        this.f_19804_.m_135372_(DATA_DAMAGE, (Object)Float.valueOf(4.0f));
        this.f_19804_.m_135372_(DATA_RADIUS, (Object)Float.valueOf(20.0f));
    }

    protected void m_7380_(CompoundTag compound) {
        super.m_7380_(compound);
        compound.m_128350_("Damage", this.getDamage());
        compound.m_128350_("Radius", this.getRadius());
        compound.m_128405_("MaxAge", this.maxAge);
        compound.m_128405_("Age", this.f_19797_);
    }

    protected void m_7378_(CompoundTag compound) {
        super.m_7378_(compound);
        if (compound.m_128441_("Damage")) {
            this.setDamage(compound.m_128457_("Damage"));
        }
        if (compound.m_128441_("Radius")) {
            this.setRadius(compound.m_128457_("Radius"));
        }
        if (compound.m_128441_("MaxAge")) {
            this.maxAge = compound.m_128451_("MaxAge");
        }
        if (compound.m_128441_("Age")) {
            this.f_19797_ = compound.m_128451_("Age");
        }
    }

    public void setRadius(float radius) {
        this.f_19804_.m_135381_(DATA_RADIUS, (Object)Float.valueOf(radius));
    }

    public float getRadius() {
        return ((Float)this.f_19804_.m_135370_(DATA_RADIUS)).floatValue();
    }

    public void setDamage(float damage) {
        this.f_19804_.m_135381_(DATA_DAMAGE, (Object)Float.valueOf(damage));
    }

    public float getDamage() {
        return ((Float)this.f_19804_.m_135370_(DATA_DAMAGE)).floatValue();
    }

    public void setMaxAge(int ticks) {
        this.maxAge = ticks;
    }

    public int getMaxAge() {
        return this.maxAge;
    }

    public void m_8119_() {
        super.m_8119_();
        if (!this.m_9236_().f_46443_) {
            LivingEntity living;
            Entity entity;
            if (this.f_19797_ >= this.maxAge) {
                this.m_5496_((SoundEvent)SoundRegistry.BLACK_HOLE_CAST.get(), this.getRadius() / 2.0f, 1.0f);
                this.m_146870_();
                return;
            }
            if ((this.f_19797_ - 1) % 320 == 0) {
                this.m_5496_((SoundEvent)SoundRegistry.BLACK_HOLE_LOOP.get(), this.getRadius() / 3.0f, 1.0f);
            }
            if (this.f_19797_ % 30 == 0) {
                CameraShakeManager.addCameraShake((CameraShakeData)new CameraShakeData(35, this.m_20182_(), this.getRadius()));
            }
            LivingEntity owner = (entity = this.m_19749_()) instanceof LivingEntity ? (living = (LivingEntity)entity) : null;
            AABB aabb = new AABB(this.m_20183_()).m_82400_((double)this.getRadius());
            Vec3 center = this.m_20182_();
            for (Entity entity2 : this.m_9236_().m_6249_((Entity)this, aabb, e -> e != this && e != owner)) {
                Vec3 entityPos;
                double distance;
                if (entity2.m_6095_().m_204039_(TravelopticsTags.SUPERMASSIVE_BLACKHOLE_PULL_BLACKLIST) || (distance = center.m_82554_(entityPos = entity2.m_20182_())) > (double)this.getRadius()) continue;
                double deadZoneRadius = 2.0;
                if (distance <= deadZoneRadius) {
                    Vec3 centeringForce = center.m_82546_(entityPos).m_82490_(0.15);
                    Vec3 currentMotion = entity2.m_20184_();
                    Vec3 dampedMotion = currentMotion.m_82490_(0.7);
                    entity2.m_20256_(centeringForce.m_82549_(dampedMotion));
                    entity2.f_19864_ = true;
                } else {
                    double pullStrength = distance > (double)this.getRadius() * 0.7 ? 0.02 + 0.05 * (1.0 - distance / (double)this.getRadius()) : 0.08 + 0.15 * (1.0 - distance / (double)this.getRadius());
                    Vec3 pullVec = center.m_82546_(entityPos).m_82541_();
                    double verticalBoost = 1.5;
                    pullVec = entity2.m_20096_() && pullVec.f_82480_ < 0.4 ? new Vec3(pullVec.f_82479_, Math.max(pullVec.f_82480_, 0.2) * verticalBoost, pullVec.f_82481_).m_82541_() : new Vec3(pullVec.f_82479_, pullVec.f_82480_ * verticalBoost, pullVec.f_82481_).m_82541_();
                    Vec3 currentVelocity = entity2.m_20184_();
                    Vec3 newVelocity = currentVelocity.m_82549_(pullVec.m_82490_(pullStrength));
                    double speedCap = 0.6;
                    if (newVelocity.m_82553_() > speedCap) {
                        newVelocity = newVelocity.m_82541_().m_82490_(speedCap);
                    }
                    entity2.m_20256_(newVelocity);
                    entity2.f_19864_ = true;
                }
                entity2.f_19789_ = 0.0f;
                if (!(entity2 instanceof LivingEntity)) continue;
                LivingEntity target = (LivingEntity)entity2;
                if (owner != null && (this.isAlly(owner, target) || this.isTamed(target))) continue;
                if (this.f_19797_ % 10 == 0 && distance < 8.0) {
                    DamageSources.applyDamage((Entity)target, (float)this.getDamage(), (DamageSource)((AbstractSpell)TravelopticsSpells.SUPERNOVA_SPELL.get()).getDamageSource((Entity)this, this.m_19749_()));
                }
                if (this.f_19797_ % 20 != 0) continue;
                List<MobEffect> effectsToRemove = target.m_21220_().stream().map(MobEffectInstance::m_19544_).toList();
                effectsToRemove.forEach(arg_0 -> ((LivingEntity)target).m_21195_(arg_0));
            }
        }
        if (this.f_19797_ % 2 == 0) {
            this.createPulsarJets(10.0f, 2.0f, 2.0f, 0.15f);
        }
    }

    private void createPulsarJets(float jetLength, float jetDensity, float jetSpread, float jetSpeed) {
        if (!this.m_9236_().f_46443_) {
            return;
        }
        Vec3 center = this.m_20182_();
        RandomSource random = this.m_9236_().m_213780_();
        Vec3 upVector = new Vec3(0.0, 1.5, 0.0);
        Vec3 downVector = new Vec3(0.0, -1.5, 0.0);
        this.createJet(center, upVector, jetLength, jetDensity, jetSpread, jetSpeed, random);
        this.createJet(center, downVector, jetLength, jetDensity, jetSpread, jetSpeed, random);
    }

    private void createJet(Vec3 center, Vec3 direction, float jetLength, float jetDensity, float jetSpread, float jetSpeed, RandomSource random) {
        int particleCount = (int)(jetDensity * 15.0f);
        for (int i = 0; i < particleCount; ++i) {
            float distanceFactor = random.m_188501_();
            float distance = distanceFactor * jetLength;
            float coneRadius = distanceFactor * jetSpread;
            double angle = random.m_188500_() * 2.0 * Math.PI;
            double offsetX = Math.cos(angle) * (double)coneRadius;
            double offsetZ = Math.sin(angle) * (double)coneRadius;
            Vec3 pos = center.m_82520_(direction.f_82479_ * (double)distance + offsetX, direction.f_82480_ * (double)distance, direction.f_82481_ * (double)distance + offsetZ);
            Vec3 particleDirection = new Vec3(direction.f_82479_ + offsetX * 0.1, direction.f_82480_, direction.f_82481_ + offsetZ * 0.1).m_82541_();
            double speedVariation = 0.5 + random.m_188500_();
            Vec3 velocity = particleDirection.m_82490_((double)jetSpeed * speedVariation);
            this.m_9236_().m_6493_((ParticleOptions)TravelopticsParticles.PURPLE_STAR_OUTWARD_PARTICLE.get(), true, pos.f_82479_, pos.f_82480_, pos.f_82481_, velocity.f_82479_, velocity.f_82480_, velocity.f_82481_);
        }
    }

    private boolean isAlly(LivingEntity owner, LivingEntity target) {
        return owner.m_5647_() != null && owner.m_5647_().m_83536_(target.m_5647_());
    }

    private boolean isTamed(LivingEntity target) {
        if (target instanceof TamableAnimal) {
            TamableAnimal tamableAnimal = (TamableAnimal)target;
            return tamableAnimal.m_21824_();
        }
        return false;
    }

    public void onAntiMagic(MagicData playerMagicData) {
    }

    private PlayState animationPredicate(AnimationState event) {
        event.getController().setAnimation(this.SPIN_ANIMATION);
        return PlayState.CONTINUE;
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController[]{this.controller});
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}

