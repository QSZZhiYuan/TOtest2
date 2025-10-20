/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.init.ModEffect
 *  io.redspace.ironsspellbooks.api.magic.MagicData
 *  io.redspace.ironsspellbooks.api.registry.SchoolRegistry
 *  io.redspace.ironsspellbooks.api.spells.SchoolType
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
 *  net.minecraft.world.effect.MobEffectCategory
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
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
package com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.nightwarden_clone.nightwarden_drop_slam_clone;

import com.gametechbc.traveloptics.api.particle.AdvancedSphereParticleManager;
import com.gametechbc.traveloptics.api.particle.ParticleDirection;
import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.nightwarden_clone.NightwardenCloneBase;
import com.gametechbc.traveloptics.init.TravelopticsEntities;
import com.gametechbc.traveloptics.init.TravelopticsParticles;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import com.github.L_Ender.cataclysm.init.ModEffect;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import java.util.List;
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
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
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

public class NightwardenDropSlamCloneEntity
extends NightwardenCloneBase
implements GeoEntity,
AntiMagicSusceptible {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    private float hpBasedDamagePercent = 0.0f;
    private static final EntityDataAccessor<Float> DATA_RADIUS = SynchedEntityData.m_135353_(NightwardenDropSlamCloneEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135029_);
    private static final EntityDataAccessor<Float> DATA_DAMAGE = SynchedEntityData.m_135353_(NightwardenDropSlamCloneEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135029_);
    private static final EntityDataAccessor<Boolean> MAGIC_DAMAGE_MODE = SynchedEntityData.m_135353_(NightwardenDropSlamCloneEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private boolean shouldApplyEffect = false;
    private float downwardSpeed = -0.35f;
    private final RawAnimation SPIN_ANIMATION = RawAnimation.begin().thenLoop("nightwarden_clone_ground_slam");
    private final AnimationController<NightwardenDropSlamCloneEntity> controller = new AnimationController((GeoAnimatable)this, "nightwarden_clone_spin_controller", 0, this::animationPredicate);

    public NightwardenDropSlamCloneEntity(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.m_20242_(true);
    }

    public NightwardenDropSlamCloneEntity(Level level, LivingEntity entityToCopy) {
        this((EntityType<? extends LivingEntity>)((EntityType)TravelopticsEntities.NIGHTWARDEN_DROP_SLAM_CLONE.get()), level);
        this.m_7678_(entityToCopy.m_20185_(), entityToCopy.m_20186_(), entityToCopy.m_20189_(), entityToCopy.m_146908_(), entityToCopy.m_146909_());
        this.m_5618_(entityToCopy.f_20883_);
        this.f_20884_ = this.f_20883_;
        this.m_5616_(entityToCopy.m_6080_());
        this.f_20886_ = this.f_20885_;
        this.setSummoner(entityToCopy);
        this.m_20242_(true);
    }

    public void setShouldApplyEffect(boolean applyEffect) {
        this.shouldApplyEffect = applyEffect;
    }

    public boolean shouldApplyEffect() {
        return this.shouldApplyEffect;
    }

    public void setDownwardSpeed(float speed) {
        this.downwardSpeed = speed;
    }

    public float getDownwardSpeed() {
        return this.downwardSpeed;
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

    public boolean isMagicDamageMode() {
        return (Boolean)this.f_19804_.m_135370_(MAGIC_DAMAGE_MODE);
    }

    public void setMagicDamageMode(boolean value) {
        this.f_19804_.m_135381_(MAGIC_DAMAGE_MODE, (Object)value);
    }

    public void m_8119_() {
        super.m_8119_();
        Vec3 currentMotion = this.m_20184_();
        this.m_20334_(currentMotion.f_82479_, this.downwardSpeed, currentMotion.f_82481_);
        this.f_19812_ = true;
        if (this.m_20096_()) {
            this.triggerImpactLogic();
        }
        if (this.f_19797_ >= 200) {
            this.m_146870_();
        }
    }

    private void triggerImpactLogic() {
        if (!this.m_9236_().f_46443_) {
            LivingEntity owner = this.getSummoner();
            double radius = this.getRadius();
            AABB region = new AABB(this.m_20185_() - radius, this.m_20186_() - 1.0, this.m_20189_() - radius, this.m_20185_() + radius, this.m_20186_() + 2.0, this.m_20189_() + radius);
            this.m_9236_().m_45976_(LivingEntity.class, region).stream().filter(entity -> entity.m_6084_() && entity != this && entity != owner).filter(entity -> owner == null || !this.isAlly(owner, (LivingEntity)entity) && !this.isTamed((LivingEntity)entity)).forEach(entity -> {
                float baseDamage = this.getDamage();
                float bonusDamage = entity.m_21233_() * this.getHpBasedDamagePercent();
                float totalDamage = baseDamage + bonusDamage;
                DamageSource source = this.isMagicDamageMode() ? (owner != null ? this.m_269291_().m_269104_((Entity)this, (Entity)owner) : this.m_269291_().m_269425_()) : this.m_269291_().m_269333_((LivingEntity)this);
                entity.m_6469_(source, totalDamage);
                if (entity instanceof LivingEntity) {
                    boolean cleansed = this.cleanseBeneficialEffects((LivingEntity)entity);
                    if (this.shouldApplyEffect && cleansed) {
                        entity.m_7292_(new MobEffectInstance((MobEffect)ModEffect.EFFECTABYSSAL_FEAR.get(), 60, 1));
                    }
                }
            });
            MagicManager.spawnParticles((Level)this.m_9236_(), (ParticleOptions)new BlastwaveParticleOptions(((SchoolType)SchoolRegistry.ENDER.get()).getTargetingColor(), this.getRadius()), (double)this.m_20185_(), (double)(this.m_20186_() + (double)0.15f), (double)this.m_20189_(), (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)false);
            AdvancedSphereParticleManager.spawnParticles(this.m_9236_(), this.m_20185_(), this.m_20186_() + 0.5, this.m_20189_(), 50, (ParticleOptions)TravelopticsParticles.PURPLE_STAR_OUTWARD_PARTICLE.get(), ParticleDirection.OUTWARD, 5.0, false);
        }
        this.m_5496_((SoundEvent)TravelopticsSounds.CRIMSON_DESCEND_IMPACT.get(), 0.8f, 1.0f);
        this.m_146870_();
    }

    private boolean cleanseBeneficialEffects(LivingEntity entity) {
        List<MobEffect> beneficialEffects = entity.m_21220_().stream().map(MobEffectInstance::m_19544_).filter(effect -> effect.m_19483_() == MobEffectCategory.BENEFICIAL).toList();
        if (!beneficialEffects.isEmpty()) {
            MobEffect randomEffect = beneficialEffects.get(this.f_19796_.m_188503_(beneficialEffects.size()));
            entity.m_21195_(randomEffect);
            return true;
        }
        return false;
    }

    public void onAntiMagic(MagicData playerMagicData) {
        this.m_146870_();
    }

    @Nullable
    protected SoundEvent m_7975_(DamageSource pDamageSource) {
        return (SoundEvent)TravelopticsSounds.CRIMSON_DESCEND_IMPACT.get();
    }

    @Nullable
    protected SoundEvent m_5592_() {
        return (SoundEvent)TravelopticsSounds.CRIMSON_DESCEND_IMPACT.get();
    }

    public boolean m_6469_(DamageSource source, float amount) {
        return false;
    }

    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(DATA_RADIUS, (Object)Float.valueOf(4.0f));
        this.f_19804_.m_135372_(DATA_DAMAGE, (Object)Float.valueOf(5.0f));
        this.f_19804_.m_135372_(MAGIC_DAMAGE_MODE, (Object)false);
    }

    @Override
    public void m_7380_(CompoundTag tag) {
        super.m_7380_(tag);
        tag.m_128350_("HpBonusPercent", this.hpBasedDamagePercent);
        tag.m_128350_("Radius", this.getRadius());
        tag.m_128350_("Damage", this.getDamage());
        tag.m_128350_("DownwardSpeed", this.downwardSpeed);
        tag.m_128379_("ShouldApplyEffect", this.shouldApplyEffect);
        tag.m_128379_("MagicDamageMode", this.isMagicDamageMode());
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
        if (tag.m_128441_("DownwardSpeed")) {
            this.downwardSpeed = tag.m_128457_("DownwardSpeed");
        }
        if (tag.m_128441_("ShouldApplyEffect")) {
            this.shouldApplyEffect = tag.m_128471_("ShouldApplyEffect");
        }
        if (tag.m_128441_("MagicDamageMode")) {
            this.setMagicDamageMode(tag.m_128471_("MagicDamageMode"));
        }
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return LivingEntity.m_21183_().m_22268_(Attributes.f_22281_, 0.0).m_22268_(Attributes.f_22276_, 1.0).m_22268_(Attributes.f_22277_, 0.0).m_22268_(Attributes.f_22278_, 100.0).m_22268_(Attributes.f_22279_, 0.0);
    }

    private PlayState animationPredicate(AnimationState<NightwardenDropSlamCloneEntity> event) {
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

