/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.entity.effect.Boltstrike_Entity
 *  io.redspace.ironsspellbooks.api.magic.MagicData
 *  io.redspace.ironsspellbooks.api.spells.AbstractSpell
 *  io.redspace.ironsspellbooks.api.util.CameraShakeData
 *  io.redspace.ironsspellbooks.api.util.CameraShakeManager
 *  io.redspace.ironsspellbooks.damage.DamageSources
 *  io.redspace.ironsspellbooks.damage.SpellDamageSource
 *  io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible
 *  io.redspace.ironsspellbooks.entity.spells.AoeEntity
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityDimensions
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LightningBolt
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Pose
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
package com.gametechbc.traveloptics.entity.projectiles.aqua_vortex;

import com.gametechbc.traveloptics.entity.projectiles.aqua_vortex.AquaVortexPullParticleEffect;
import com.gametechbc.traveloptics.entity.projectiles.aqua_vortex.AquaVortexSwirlParticleEffect;
import com.gametechbc.traveloptics.init.TravelopticsEffects;
import com.gametechbc.traveloptics.init.TravelopticsEntities;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import com.gametechbc.traveloptics.init.TravelopticsSpells;
import com.gametechbc.traveloptics.util.TravelopticsParticleHelper;
import com.github.L_Ender.cataclysm.entity.effect.Boltstrike_Entity;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.util.CameraShakeData;
import io.redspace.ironsspellbooks.api.util.CameraShakeManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.damage.SpellDamageSource;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import io.redspace.ironsspellbooks.entity.spells.AoeEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
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

public class AquaVortexEntity
extends AoeEntity
implements GeoEntity,
AntiMagicSusceptible {
    private int tickCounter = 0;
    private float boltStrikeDamage = 5.0f;
    private final RawAnimation SPIN_ANIMATION = RawAnimation.begin().thenPlay("animation.vortex.idle");
    private final AnimationController controller = new AnimationController((GeoAnimatable)this, "aqua_vortex_controller", 0, this::animationPredicate);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);

    public AquaVortexEntity(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
        this.reapplicationDelay = 30;
        this.setCircular();
    }

    public AquaVortexEntity(Level level) {
        this((EntityType<? extends Projectile>)((EntityType)TravelopticsEntities.AQUA_VORTEX_ENTITY.get()), level);
    }

    public EntityDimensions m_6972_(Pose pPose) {
        return EntityDimensions.m_20395_((float)(this.getRadius() * 2.0f), (float)(this.getRadius() * 1.2f));
    }

    public float getBoltStrikeDamage() {
        return this.boltStrikeDamage;
    }

    public void setBoltStrikeDamage(float boltStrikeDamage) {
        this.boltStrikeDamage = boltStrikeDamage;
    }

    public void m_8119_() {
        super.m_8119_();
        ++this.tickCounter;
        if (this.f_19797_ == 2) {
            this.createScreenShake();
            this.m_5496_((SoundEvent)TravelopticsSounds.AQUA_VORTEX_ACTIVE.get(), 2.0f, 1.0f);
        }
        if (this.f_19797_ == 396) {
            this.m_5496_((SoundEvent)TravelopticsSounds.AQUA_VORTEX_END.get(), 2.0f, 1.0f);
        }
        if (this.f_19797_ == 398) {
            LightningBolt lightningBolt = (LightningBolt)EntityType.f_20465_.m_20615_(this.m_9236_());
            assert (lightningBolt != null);
            lightningBolt.m_20874_(true);
            lightningBolt.setDamage(0.0f);
            lightningBolt.m_146884_(this.m_20182_());
            this.m_9236_().m_7967_((Entity)lightningBolt);
        }
        if (!this.m_9236_().f_46443_ && this.f_19797_ >= 400) {
            this.m_146870_();
        }
        if (this.m_9236_().f_46443_) {
            AquaVortexPullParticleEffect pullParticleEffect = new AquaVortexPullParticleEffect((Entity)this);
            pullParticleEffect.spawnPullParticles();
            AquaVortexSwirlParticleEffect swirlParticleEffect = new AquaVortexSwirlParticleEffect((Entity)this);
            swirlParticleEffect.setBaseRadius(this.getRadius());
            swirlParticleEffect.setParticleCount((int)(this.getRadius() * 2.0f));
            swirlParticleEffect.spawnSwirlParticles();
        }
        if (!this.m_9236_().f_46443_) {
            if (this.tickCounter % 30 == 0) {
                this.spawnBoltstrike();
            }
            if (this.tickCounter % 20 == 0) {
                this.extinguishEntitiesOnFire();
            }
            this.pullEntitiesTowardsCenter();
        }
    }

    private void extinguishEntitiesOnFire() {
        double radius = this.getRadius();
        AABB region = new AABB(this.m_20185_() - radius, this.m_20186_() - radius, this.m_20189_() - radius, this.m_20185_() + radius, this.m_20186_() + radius, this.m_20189_() + radius);
        List entitiesInRadius = this.m_9236_().m_45976_(LivingEntity.class, region);
        for (LivingEntity entity : entitiesInRadius) {
            if (!entity.m_6060_()) continue;
            entity.m_20254_(0);
            entity.m_20095_();
        }
    }

    private void pullEntitiesTowardsCenter() {
        double radius = this.getRadius();
        AABB region = new AABB(this.m_20185_() - radius, this.m_20186_() - radius, this.m_20189_() - radius, this.m_20185_() + radius, this.m_20186_() + radius, this.m_20189_() + radius);
        List entitiesInRadius = this.m_9236_().m_45976_(LivingEntity.class, region);
        for (LivingEntity entity : entitiesInRadius) {
            if (entity == this.m_19749_()) continue;
            Vec3 entityPosition = entity.m_20182_();
            Vec3 vortexCenter = new Vec3(this.m_20185_(), this.m_20186_(), this.m_20189_());
            Vec3 directionToCenter = vortexCenter.m_82546_(entityPosition).m_82541_();
            Vec3 spinDirection = new Vec3(Math.cos(entity.m_146908_()), 0.0, Math.sin(entity.m_146908_()));
            Vec3 pullAndSpinMovement = directionToCenter.m_82542_(0.05, 0.05, 0.05).m_82549_(spinDirection.m_82542_(0.05, 0.0, 0.05));
            entity.m_20256_(entity.m_20184_().m_82549_(pullAndSpinMovement));
            double distanceToCenter = entityPosition.m_82554_(vortexCenter);
            if (!(distanceToCenter < 2.0)) continue;
            this.liftAndThrowEntity(entity);
        }
    }

    private void liftAndThrowEntity(LivingEntity entity) {
        double liftHeight = 1.5;
        double throwStrength = 1.0;
        double spinStrength = 0.2;
        Vec3 spinDirection = new Vec3(Math.cos(entity.m_146908_()), 0.0, Math.sin(entity.m_146908_()));
        Vec3 liftMovement = new Vec3(0.0, liftHeight, 0.0).m_82549_(spinDirection.m_82542_(spinStrength, 0.0, spinStrength));
        Vec3 throwDirection = spinDirection.m_82542_(throwStrength, 0.0, throwStrength);
        entity.m_20256_(liftMovement.m_82549_(throwDirection));
        entity.m_20256_(entity.m_20184_().m_82520_((Math.random() - 0.5) * 0.1, 0.0, (Math.random() - 0.5) * 0.1));
    }

    private void spawnBoltstrike() {
        LivingEntity owner = this.m_19749_() instanceof LivingEntity ? (LivingEntity)this.m_19749_() : null;
        double radius = this.getRadius();
        AABB region = new AABB(this.m_20185_() - radius, this.m_20186_() - radius, this.m_20189_() - radius, this.m_20185_() + radius, this.m_20186_() + radius, this.m_20189_() + radius);
        List entitiesInRadius = this.m_9236_().m_45976_(LivingEntity.class, region);
        if (!entitiesInRadius.isEmpty()) {
            ArrayList<LivingEntity> validTargets = new ArrayList<LivingEntity>(entitiesInRadius.stream().filter(entity -> entity != owner).filter(entity -> owner == null || !this.isAlly(owner, (LivingEntity)entity)).filter(entity -> !this.isTamed((LivingEntity)entity)).toList());
            if (!validTargets.isEmpty()) {
                Collections.shuffle(validTargets);
                int targetCount = Math.min(5, validTargets.size());
                for (int i = 0; i < targetCount; ++i) {
                    LivingEntity targetEntity = (LivingEntity)validTargets.get(i);
                    Vec3 targetPosition = targetEntity.m_20182_();
                    this.spawnBoltAt(targetPosition);
                }
            }
        } else {
            double randomX = this.m_20185_() + this.f_19796_.m_188500_() * 2.0 * radius - radius;
            double randomY = this.m_20186_();
            double randomZ = this.m_20189_() + this.f_19796_.m_188500_() * 2.0 * radius - radius;
            Vec3 randomPosition = new Vec3(randomX, randomY, randomZ);
            this.spawnBoltAt(randomPosition);
        }
    }

    private void spawnBoltAt(Vec3 position) {
        LivingEntity owner = this.m_19749_() instanceof LivingEntity ? (LivingEntity)this.m_19749_() : null;
        Boltstrike_Entity bolt = new Boltstrike_Entity(this.m_9236_(), position.f_82479_, position.f_82480_, position.f_82481_, 0.0f, 0, this.getBoltStrikeDamage(), owner);
        bolt.setR(0);
        bolt.setG(66);
        bolt.setB(106);
        this.m_9236_().m_7967_((Entity)bolt);
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

    public void applyEffect(LivingEntity target) {
        SpellDamageSource damageSource = ((AbstractSpell)TravelopticsSpells.VORTEX_OF_THE_DEEP_SPELL.get()).getDamageSource((Entity)this, this.m_19749_());
        DamageSources.ignoreNextKnockback((LivingEntity)target);
        target.m_6469_((DamageSource)damageSource, this.getDamage());
        target.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 30, 1, false, false, false));
        target.m_7292_(new MobEffectInstance((MobEffect)TravelopticsEffects.WET.get(), 30, 1, false, false, false));
    }

    protected void createScreenShake() {
        if (!this.m_9236_().f_46443_ && !this.m_213877_()) {
            CameraShakeData cameraShakeData = new CameraShakeData(this.duration - this.f_19797_, this.m_20182_(), this.getRadius() + 4.0f);
            CameraShakeManager.addCameraShake((CameraShakeData)cameraShakeData);
        }
    }

    public float getParticleCount() {
        return 0.1f * this.getRadius();
    }

    protected float particleYOffset() {
        return 0.25f;
    }

    protected float getParticleSpeedModifier() {
        return 1.4f;
    }

    public Optional<ParticleOptions> getParticle() {
        return Optional.of(TravelopticsParticleHelper.WATER_FOG);
    }

    protected boolean canHitTargetForGroundContext(LivingEntity target) {
        return true;
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

    protected void m_7380_(CompoundTag compound) {
        super.m_7380_(compound);
        compound.m_128350_("BoltStrikeDamage", this.boltStrikeDamage);
    }

    protected void m_7378_(CompoundTag compound) {
        super.m_7378_(compound);
        if (compound.m_128441_("BoltStrikeDamage")) {
            this.boltStrikeDamage = compound.m_128457_("BoltStrikeDamage");
        }
    }
}

