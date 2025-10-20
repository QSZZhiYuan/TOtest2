/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.init.ModEffect
 *  io.redspace.ironsspellbooks.api.magic.MagicData
 *  io.redspace.ironsspellbooks.api.registry.SchoolRegistry
 *  io.redspace.ironsspellbooks.api.spells.SchoolType
 *  io.redspace.ironsspellbooks.api.util.CameraShakeData
 *  io.redspace.ironsspellbooks.api.util.CameraShakeManager
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible
 *  io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions
 *  javax.annotation.Nullable
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
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
package com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.nightwarden_clone.nightwarden_explode_clone;

import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.nightwarden_clone.NightwardenCloneBase;
import com.gametechbc.traveloptics.init.TravelopticsEntities;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import com.gametechbc.traveloptics.util.TravelopticsParticleHelper;
import com.github.L_Ender.cataclysm.init.ModEffect;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.api.util.CameraShakeData;
import io.redspace.ironsspellbooks.api.util.CameraShakeManager;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
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

public class NightwardenExplodeCloneEntity
extends NightwardenCloneBase
implements GeoEntity,
AntiMagicSusceptible {
    private static final EntityDataAccessor<Float> DATA_RADIUS = SynchedEntityData.m_135353_(NightwardenExplodeCloneEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135029_);
    private static final EntityDataAccessor<Float> DATA_DAMAGE = SynchedEntityData.m_135353_(NightwardenExplodeCloneEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135029_);
    private static final EntityDataAccessor<Boolean> IS_SPIN_CLONE = SynchedEntityData.m_135353_(NightwardenExplodeCloneEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Integer> EXPLODE_DELAY_TICKS = SynchedEntityData.m_135353_(NightwardenExplodeCloneEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    private float hpBasedDamagePercent = 0.0f;
    private int disappearAnimTick = -1;
    private boolean useMagicDamage = false;
    private final RawAnimation BASIC_ANIMATION = RawAnimation.begin().thenPlay("nightwarden_clone_basic");
    private final RawAnimation SPIN_ANIMATION = RawAnimation.begin().thenPlay("nightwarden_clone_spin_pose");
    private final RawAnimation DISAPPEAR_ANIMATION = RawAnimation.begin().thenPlay("nightwarden_clone_disappear_poof");
    private final AnimationController<NightwardenExplodeCloneEntity> controller = new AnimationController((GeoAnimatable)this, "nightwarden_clone_explode_controller", 0, this::animationPredicate);
    private final AnimationController<NightwardenExplodeCloneEntity> squeezeController = new AnimationController((GeoAnimatable)this, "disappear", 0, this::disappearPredicate);

    public NightwardenExplodeCloneEntity(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public NightwardenExplodeCloneEntity(Level level, LivingEntity entityToCopy, float yawOffset) {
        this((EntityType<? extends LivingEntity>)((EntityType)TravelopticsEntities.NIGHTWARDEN_EXPLODE_CLONE.get()), level);
        float baseYaw = entityToCopy.m_146908_();
        float adjustedYaw = baseYaw + yawOffset;
        this.m_7678_(entityToCopy.m_20185_(), entityToCopy.m_20186_(), entityToCopy.m_20189_(), adjustedYaw, entityToCopy.m_146909_());
        this.m_5618_(adjustedYaw);
        this.f_20884_ = adjustedYaw;
        this.m_5616_(adjustedYaw);
        this.f_20886_ = adjustedYaw;
        this.setSummoner(entityToCopy);
    }

    public void setExplodeDelayTicks(int ticks) {
        this.f_19804_.m_135381_(EXPLODE_DELAY_TICKS, (Object)ticks);
    }

    public int getExplodeDelayTicks() {
        return (Integer)this.f_19804_.m_135370_(EXPLODE_DELAY_TICKS);
    }

    public void setHpBasedDamagePercent(float percent) {
        this.hpBasedDamagePercent = percent;
    }

    public float getHpBasedDamagePercent() {
        return this.hpBasedDamagePercent;
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

    public void setSpinClone(boolean spin) {
        this.f_19804_.m_135381_(IS_SPIN_CLONE, (Object)spin);
    }

    public boolean isSpinClone() {
        return (Boolean)this.f_19804_.m_135370_(IS_SPIN_CLONE);
    }

    public void setMagicDamage(boolean useMagic) {
        this.useMagicDamage = useMagic;
    }

    public boolean isMagicDamage() {
        return this.useMagicDamage;
    }

    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(DATA_RADIUS, (Object)Float.valueOf(2.0f));
        this.f_19804_.m_135372_(DATA_DAMAGE, (Object)Float.valueOf(5.0f));
        this.f_19804_.m_135372_(IS_SPIN_CLONE, (Object)false);
        this.f_19804_.m_135372_(EXPLODE_DELAY_TICKS, (Object)20);
    }

    @Override
    public void m_7380_(CompoundTag tag) {
        super.m_7380_(tag);
        tag.m_128350_("HpBonusPercent", this.hpBasedDamagePercent);
        tag.m_128350_("Radius", this.getRadius());
        tag.m_128350_("Damage", this.getDamage());
        tag.m_128379_("SpinClone", this.isSpinClone());
        tag.m_128405_("ExplodeDelay", this.getExplodeDelayTicks());
        tag.m_128379_("UseMagicDamage", this.useMagicDamage);
    }

    @Override
    public void m_7378_(CompoundTag tag) {
        super.m_7378_(tag);
        this.hpBasedDamagePercent = tag.m_128457_("HpBonusPercent");
        if (tag.m_128441_("Radius")) {
            this.setRadius(tag.m_128457_("Radius"));
        }
        if (tag.m_128441_("Damage")) {
            this.setDamage(tag.m_128457_("Damage"));
        }
        if (tag.m_128441_("SpinClone")) {
            this.setSpinClone(tag.m_128471_("SpinClone"));
        }
        if (tag.m_128441_("ExplodeDelay")) {
            this.setExplodeDelayTicks(tag.m_128451_("ExplodeDelay"));
        }
        if (tag.m_128441_("UseMagicDamage")) {
            this.useMagicDamage = tag.m_128471_("UseMagicDamage");
        }
    }

    public void m_8119_() {
        super.m_8119_();
        int explodeDelay = this.getExplodeDelayTicks();
        if (this.f_19797_ == explodeDelay - 5) {
            this.disappearAnimTick = 0;
        }
        if (this.f_19797_ == explodeDelay - 1) {
            LivingEntity owner = this.getSummoner();
            double radius = this.getRadius();
            AABB region = new AABB(this.m_20185_() - radius, this.m_20186_() - radius, this.m_20189_() - radius, this.m_20185_() + radius, this.m_20186_() + radius, this.m_20189_() + radius);
            this.m_9236_().m_45976_(LivingEntity.class, region).stream().filter(entity -> entity.m_6084_() && entity != owner).filter(entity -> owner == null || !this.isAlly(owner, (LivingEntity)entity) && !this.isTamed((LivingEntity)entity)).forEach(entity -> {
                float baseDamage = this.getDamage();
                float bonusDamage = entity.m_21233_() * this.getHpBasedDamagePercent();
                float totalDamage = baseDamage + bonusDamage;
                DamageSource source = this.useMagicDamage ? this.m_269291_().m_269104_((Entity)this, (Entity)(this.getSummoner() != null ? this.getSummoner() : this)) : this.m_269291_().m_269333_((LivingEntity)this);
                entity.m_6469_(source, totalDamage);
                entity.m_7292_(new MobEffectInstance((MobEffect)ModEffect.EFFECTABYSSAL_FEAR.get(), 200, 0, false, false, true));
            });
            if (!this.m_9236_().f_46443_) {
                MagicManager.spawnParticles((Level)this.m_9236_(), (ParticleOptions)new BlastwaveParticleOptions(((SchoolType)SchoolRegistry.ENDER.get()).getTargetingColor(), this.getRadius() + 1.5f), (double)this.m_20185_(), (double)(this.m_20186_() + (double)1.2f), (double)this.m_20189_(), (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)false);
                MagicManager.spawnParticles((Level)this.m_9236_(), (ParticleOptions)TravelopticsParticleHelper.ABYSS_SPIKE_PARTICLE, (double)this.m_20185_(), (double)this.m_20186_(), (double)this.m_20189_(), (int)50, (double)0.4, (double)0.8, (double)0.4, (double)0.03, (boolean)false);
                CameraShakeManager.addCameraShake((CameraShakeData)new CameraShakeData(20, this.m_20182_(), this.getRadius()));
            }
            this.m_216990_((SoundEvent)TravelopticsSounds.ORBITAL_VOID_PULSE.get());
        }
        if (this.f_19797_ >= explodeDelay) {
            this.m_146870_();
        }
    }

    public void onAntiMagic(MagicData playerMagicData) {
        this.m_146870_();
    }

    @Nullable
    protected SoundEvent m_7975_(DamageSource pDamageSource) {
        return null;
    }

    @Nullable
    protected SoundEvent m_5592_() {
        return null;
    }

    public boolean m_6469_(DamageSource pSource, float pAmount) {
        return false;
    }

    private PlayState animationPredicate(AnimationState<NightwardenExplodeCloneEntity> event) {
        event.getController().setAnimation(this.isSpinClone() ? this.SPIN_ANIMATION : this.BASIC_ANIMATION);
        return PlayState.CONTINUE;
    }

    private PlayState disappearPredicate(AnimationState<NightwardenExplodeCloneEntity> event) {
        if (this.disappearAnimTick >= 0) {
            event.getController().setAnimation(this.DISAPPEAR_ANIMATION);
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController[]{this.controller});
        controllerRegistrar.add(new AnimationController[]{this.squeezeController});
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}

