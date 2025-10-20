/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.api.magic.MagicData
 *  io.redspace.ironsspellbooks.api.registry.SchoolRegistry
 *  io.redspace.ironsspellbooks.api.spells.AbstractSpell
 *  io.redspace.ironsspellbooks.api.spells.SchoolType
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  io.redspace.ironsspellbooks.damage.DamageSources
 *  io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible
 *  io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.util.Mth
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.TamableAnimal
 *  net.minecraft.world.entity.projectile.Projectile
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
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
package com.gametechbc.traveloptics.entity.projectiles.supernova.dying_star;

import com.gametechbc.traveloptics.api.particle.AdvancedSphereParticleManager;
import com.gametechbc.traveloptics.api.particle.ParticleDirection;
import com.gametechbc.traveloptics.entity.misc.TOScreenFlashEntity;
import com.gametechbc.traveloptics.entity.misc.TOScreenShakeEntity;
import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.NightwardenBossEntity;
import com.gametechbc.traveloptics.entity.projectiles.supernova.dying_star.DyingStarParticleManager;
import com.gametechbc.traveloptics.entity.projectiles.supernova.supermassive_black_hole.SupermassiveBlackHoleEntity;
import com.gametechbc.traveloptics.init.TravelopticsEntities;
import com.gametechbc.traveloptics.init.TravelopticsParticles;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import com.gametechbc.traveloptics.init.TravelopticsSpells;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import java.util.List;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class DyingStarEntity
extends Projectile
implements GeoEntity,
AntiMagicSusceptible {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    private static final EntityDataAccessor<Float> DATA_DAMAGE = SynchedEntityData.m_135353_(DyingStarEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135029_);
    private static final EntityDataAccessor<Float> DATA_RADIUS = SynchedEntityData.m_135353_(DyingStarEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135029_);
    private static final EntityDataAccessor<Boolean> DATA_ANTIMAGIC_VULNERABLE = SynchedEntityData.m_135353_(DyingStarEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private float blackholeRadius = 20.0f;
    private float blackholeDamage = 10.0f;
    private float ownerVoidDamagePercent = 0.25f;
    private int blackholeDuration = 400;
    private final RawAnimation SUPERNOVA_ANIMATION = RawAnimation.begin().thenPlay("supernova");
    private final AnimationController<?> controller = new AnimationController((GeoAnimatable)this, "supernova_controller", 0, this::animationPredicate);

    public DyingStarEntity(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    public DyingStarEntity(Level level) {
        this((EntityType<? extends Projectile>)((EntityType)TravelopticsEntities.DYING_STAR.get()), level);
    }

    protected void m_8097_() {
        this.f_19804_.m_135372_(DATA_DAMAGE, (Object)Float.valueOf(4.0f));
        this.f_19804_.m_135372_(DATA_RADIUS, (Object)Float.valueOf(20.0f));
        this.f_19804_.m_135372_(DATA_ANTIMAGIC_VULNERABLE, (Object)false);
    }

    protected void m_7380_(CompoundTag compound) {
        super.m_7380_(compound);
        compound.m_128350_("Damage", this.getDamage());
        compound.m_128350_("Radius", this.getRadius());
        compound.m_128379_("AntiMagicVulnerable", ((Boolean)this.f_19804_.m_135370_(DATA_ANTIMAGIC_VULNERABLE)).booleanValue());
        compound.m_128350_("BlackholeRadius", this.blackholeRadius);
        compound.m_128350_("BlackholeDamage", this.blackholeDamage);
        compound.m_128350_("OwnerVoidDamagePercent", this.ownerVoidDamagePercent);
        compound.m_128405_("BlackholeDuration", this.blackholeDuration);
    }

    protected void m_7378_(CompoundTag compound) {
        super.m_7378_(compound);
        if (compound.m_128441_("Damage")) {
            this.setDamage(compound.m_128457_("Damage"));
        }
        if (compound.m_128441_("Radius")) {
            this.setRadius(compound.m_128457_("Radius"));
        }
        if (compound.m_128441_("AntiMagicVulnerable")) {
            this.f_19804_.m_135381_(DATA_ANTIMAGIC_VULNERABLE, (Object)compound.m_128471_("AntiMagicVulnerable"));
        }
        if (compound.m_128441_("BlackholeRadius")) {
            this.blackholeRadius = compound.m_128457_("BlackholeRadius");
        }
        if (compound.m_128441_("BlackholeDamage")) {
            this.blackholeDamage = compound.m_128457_("BlackholeDamage");
        }
        if (compound.m_128441_("OwnerVoidDamagePercent")) {
            this.ownerVoidDamagePercent = compound.m_128457_("OwnerVoidDamagePercent");
        }
        if (compound.m_128441_("BlackholeDuration")) {
            this.blackholeDuration = compound.m_128451_("BlackholeDuration");
        }
    }

    public int getBlackholeDuration() {
        return this.blackholeDuration;
    }

    public void setBlackholeDuration(int duration) {
        this.blackholeDuration = duration;
    }

    public float getOwnerVoidDamagePercent() {
        return this.ownerVoidDamagePercent;
    }

    public void setOwnerVoidDamagePercent(float percent) {
        this.ownerVoidDamagePercent = percent;
    }

    public float getBlackholeDamage() {
        return this.blackholeDamage;
    }

    public void setBlackholeDamage(float damage) {
        this.blackholeDamage = damage;
    }

    public void setBlackholeRadius(float radius) {
        this.blackholeRadius = radius;
    }

    public float getBlackholeRadius() {
        return this.blackholeRadius;
    }

    public void setDamage(float damage) {
        this.f_19804_.m_135381_(DATA_DAMAGE, (Object)Float.valueOf(damage));
    }

    public float getDamage() {
        return ((Float)this.f_19804_.m_135370_(DATA_DAMAGE)).floatValue();
    }

    public float getRadius() {
        return ((Float)this.f_19804_.m_135370_(DATA_RADIUS)).floatValue();
    }

    public void setRadius(float radius) {
        this.f_19804_.m_135381_(DATA_RADIUS, (Object)Float.valueOf(radius));
    }

    public boolean isAntiMagicVulnerable() {
        return (Boolean)this.f_19804_.m_135370_(DATA_ANTIMAGIC_VULNERABLE);
    }

    public void m_8119_() {
        super.m_8119_();
        if (this.f_19797_ == 1) {
            this.m_5496_((SoundEvent)TravelopticsSounds.DYING_STAR_AMBIENT.get(), 3.0f, 1.0f);
        }
        if (this.f_19797_ == 190) {
            this.m_5496_((SoundEvent)TravelopticsSounds.BOSS_ATTACK_WARNING.get(), 3.0f, 1.0f);
        }
        if (!this.m_9236_().f_46443_) {
            LivingEntity living;
            LivingEntity owner;
            boolean vulnerable = this.f_19797_ >= 190 && this.f_19797_ <= 214;
            this.f_19804_.m_135381_(DATA_ANTIMAGIC_VULNERABLE, (Object)vulnerable);
            float progress = (float)this.f_19797_ / 170.0f;
            progress = Mth.m_14036_((float)progress, (float)0.0f, (float)1.0f);
            double chargeUpRadius = Mth.m_14139_((double)progress, (double)8.0, (double)11.0);
            ParticleOptions particle = vulnerable ? (ParticleOptions)TravelopticsParticles.RED_STAR_INWARD_PARTICLE.get() : (ParticleOptions)TravelopticsParticles.PURPLE_STAR_INWARD_PARTICLE.get();
            AdvancedSphereParticleManager.spawnParticles(this.m_9236_(), this.m_20185_(), this.m_20186_() + 2.0, this.m_20189_(), 5, particle, ParticleDirection.INWARD, chargeUpRadius, true);
            Entity entity = this.m_19749_();
            LivingEntity livingEntity = owner = entity instanceof LivingEntity ? (living = (LivingEntity)entity) : null;
            if (owner instanceof NightwardenBossEntity) {
                DyingStarParticleManager.spawnAOEWarningParticles(this);
            }
            if (this.f_19797_ == 214) {
                MagicManager.spawnParticles((Level)this.m_9236_(), (ParticleOptions)new BlastwaveParticleOptions(((SchoolType)SchoolRegistry.ENDER.get()).getTargetingColor(), this.getRadius()), (double)this.m_20185_(), (double)(this.m_20186_() + 2.0), (double)this.m_20189_(), (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)true);
                AdvancedSphereParticleManager.spawnParticles(this.m_9236_(), this.m_20185_(), this.m_20186_() + 2.0, this.m_20189_(), 250, (ParticleOptions)TravelopticsParticles.PURPLE_STAR_OUTWARD_PARTICLE.get(), ParticleDirection.OUTWARD, this.getRadius(), true);
                float blastRadius = this.getRadius();
                AABB area = new AABB(this.m_20182_().m_82520_((double)(-blastRadius), (double)(-blastRadius), (double)(-blastRadius)), this.m_20182_().m_82520_((double)blastRadius, (double)blastRadius, (double)blastRadius));
                List targets = this.m_9236_().m_6443_(LivingEntity.class, area, e -> e.m_6084_() && e.m_20280_((Entity)this) <= (double)(blastRadius * blastRadius));
                for (LivingEntity target : targets) {
                    if (target == owner || owner != null && this.isAlly(owner, target) || this.isTamed(target)) continue;
                    DamageSources.applyDamage((Entity)target, (float)this.getDamage(), (DamageSource)((AbstractSpell)TravelopticsSpells.SUPERNOVA_SPELL.get()).getDamageSource((Entity)this, (Entity)owner));
                }
                SupermassiveBlackHoleEntity supermassiveBlackHole = new SupermassiveBlackHoleEntity(this.m_9236_());
                supermassiveBlackHole.m_7678_(this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_146908_(), this.m_146909_());
                supermassiveBlackHole.setRadius(this.getBlackholeRadius());
                supermassiveBlackHole.setDamage(this.getBlackholeDamage());
                supermassiveBlackHole.setMaxAge(this.getBlackholeDuration());
                if (owner != null) {
                    supermassiveBlackHole.m_5602_((Entity)owner);
                }
                this.m_9236_().m_7967_((Entity)supermassiveBlackHole);
            }
        }
        if (this.f_19797_ >= 215) {
            TOScreenShakeEntity.createScreenShake(this.m_9236_(), this.m_20182_(), this.getBlackholeRadius(), 0.1f, 15, 0, 5, false);
            TOScreenFlashEntity.createWhiteFlash(this.m_9236_(), this.m_20182_(), this.getBlackholeRadius(), 0.75f, 5, 15, 5, false);
            this.m_5496_((SoundEvent)TravelopticsSounds.SUPERNOVA_EXPLODE.get(), 3.0f, 1.0f);
            this.m_146870_();
        }
    }

    public void onAntiMagic(MagicData playerMagicData) {
        if (this.isAntiMagicVulnerable() && !this.m_9236_().f_46443_) {
            LivingEntity owner;
            AdvancedSphereParticleManager.spawnParticles(this.m_9236_(), this.m_20185_(), this.m_20186_() + 2.0, this.m_20189_(), 250, (ParticleOptions)TravelopticsParticles.RED_STAR_OUTWARD_PARTICLE.get(), ParticleDirection.OUTWARD, this.getRadius(), true);
            Entity entity = this.m_19749_();
            if (entity instanceof LivingEntity && (owner = (LivingEntity)entity).m_6084_()) {
                float percent = Mth.m_14036_((float)this.getOwnerVoidDamagePercent(), (float)0.0f, (float)1.0f);
                float damage = owner.m_21233_() * percent;
                DamageSource voidDamage = this.m_9236_().m_269111_().m_269425_();
                owner.m_6469_(voidDamage, damage);
            }
            this.m_5496_(SoundEvents.f_12031_, 2.5f, 1.0f);
            this.m_146870_();
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

    private PlayState animationPredicate(AnimationState<?> event) {
        event.getController().setAnimation(this.SUPERNOVA_ANIMATION);
        return PlayState.CONTINUE;
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController[]{this.controller});
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}

