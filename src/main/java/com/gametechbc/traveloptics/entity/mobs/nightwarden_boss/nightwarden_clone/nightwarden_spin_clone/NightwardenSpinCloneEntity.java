/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
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
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.HumanoidArm
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.TamableAnimal
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.item.ItemStack
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
package com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.nightwarden_clone.nightwarden_spin_clone;

import com.gametechbc.traveloptics.init.TravelopticsEntities;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import java.util.Collections;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
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

public class NightwardenSpinCloneEntity
extends LivingEntity
implements GeoEntity,
AntiMagicSusceptible {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    private float hpBasedDamagePercent = 0.0f;
    private static final EntityDataAccessor<Float> DATA_RADIUS = SynchedEntityData.m_135353_(NightwardenSpinCloneEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135029_);
    private static final EntityDataAccessor<Float> DATA_DAMAGE = SynchedEntityData.m_135353_(NightwardenSpinCloneEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135029_);
    private static final EntityDataAccessor<Boolean> MAGIC_DAMAGE_MODE = SynchedEntityData.m_135353_(NightwardenSpinCloneEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private int age = 0;
    private int maxAge = 100;
    private boolean playedSqueezeAnimation = false;
    private float forwardSpeed = 0.25f;
    private boolean shouldMoveForward = true;
    private UUID summonerUUID;
    private LivingEntity cachedSummoner;
    private final RawAnimation SPIN_ANIMATION = RawAnimation.begin().thenLoop("nightwarden_clone_spin");
    private final AnimationController<NightwardenSpinCloneEntity> controller = new AnimationController((GeoAnimatable)this, "nightwarden_clone_spin_controller", 0, this::animationPredicate);
    private final RawAnimation SQUEEZE_ANIMATION = RawAnimation.begin().thenPlay("nightwarden_clone_disappear_squeeze");
    private final AnimationController<NightwardenSpinCloneEntity> squeezeController = new AnimationController((GeoAnimatable)this, "nightwarden_clone_squeeze_controller", 0, this::squeezeAnimationPredicate);

    public NightwardenSpinCloneEntity(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public NightwardenSpinCloneEntity(Level level, LivingEntity entityToCopy, float yawOffset) {
        this((EntityType<? extends LivingEntity>)((EntityType)TravelopticsEntities.NIGHTWARDEN_SPIN_CLONE.get()), level);
        float baseYaw = entityToCopy.m_146908_();
        float adjustedYaw = baseYaw + yawOffset;
        this.m_7678_(entityToCopy.m_20185_(), entityToCopy.m_20186_(), entityToCopy.m_20189_(), adjustedYaw, entityToCopy.m_146909_());
        this.m_5618_(adjustedYaw);
        this.f_20884_ = adjustedYaw;
        this.m_5616_(adjustedYaw);
        this.f_20886_ = adjustedYaw;
        this.setSummoner(entityToCopy);
    }

    public void setMovementSpeed(float speed) {
        this.forwardSpeed = speed;
    }

    public float getMovementSpeed() {
        return this.forwardSpeed;
    }

    public void setShouldMoveForward(boolean shouldMove) {
        this.shouldMoveForward = shouldMove;
    }

    public boolean shouldMoveForward() {
        return this.shouldMoveForward;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getMaxAge() {
        return this.maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
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

    public boolean isMagicDamageMode() {
        return (Boolean)this.f_19804_.m_135370_(MAGIC_DAMAGE_MODE);
    }

    public void setMagicDamageMode(boolean value) {
        this.f_19804_.m_135381_(MAGIC_DAMAGE_MODE, (Object)value);
    }

    public void setDamage(float damage) {
        this.f_19804_.m_135381_(DATA_DAMAGE, (Object)Float.valueOf(damage));
    }

    public float getDamage() {
        return ((Float)this.f_19804_.m_135370_(DATA_DAMAGE)).floatValue();
    }

    public void setSummoner(@Nullable LivingEntity owner) {
        if (owner != null) {
            this.summonerUUID = owner.m_20148_();
            this.cachedSummoner = owner;
        }
    }

    public LivingEntity getSummoner() {
        if (this.cachedSummoner != null && this.cachedSummoner.m_6084_()) {
            return this.cachedSummoner;
        }
        if (this.summonerUUID != null && this.m_9236_() instanceof ServerLevel) {
            Entity entity = ((ServerLevel)this.m_9236_()).m_8791_(this.summonerUUID);
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity;
                this.cachedSummoner = livingEntity = (LivingEntity)entity;
            }
            return this.cachedSummoner;
        }
        return null;
    }

    public void m_8119_() {
        super.m_8119_();
        ++this.age;
        if (this.shouldMoveForward) {
            Vec3 forward = this.m_20154_().m_82541_().m_82490_((double)this.forwardSpeed);
            this.m_20334_(forward.f_82479_, this.m_20184_().f_82480_, forward.f_82481_);
            this.f_19812_ = true;
        }
        if (this.f_19797_ % 5 == 0) {
            LivingEntity owner = this.getSummoner();
            double radius = this.getRadius();
            AABB region = new AABB(this.m_20185_() - radius, this.m_20186_() - radius, this.m_20189_() - radius, this.m_20185_() + radius, this.m_20186_() + radius, this.m_20189_() + radius);
            this.m_9236_().m_45976_(LivingEntity.class, region).stream().filter(entity -> entity.m_6084_() && entity != this && entity != owner).filter(entity -> owner == null || !this.isAlly(owner, (LivingEntity)entity) && !this.isTamed((LivingEntity)entity)).forEach(entity -> {
                float baseDamage = this.getDamage();
                float bonusDamage = entity.m_21233_() * this.getHpBasedDamagePercent();
                float totalDamage = baseDamage + bonusDamage;
                DamageSource source = this.isMagicDamageMode() ? (owner != null ? this.m_269291_().m_269104_((Entity)this, (Entity)owner) : this.m_269291_().m_269425_()) : this.m_269291_().m_269333_((LivingEntity)this);
                entity.m_6469_(source, totalDamage);
            });
            if (!this.m_9236_().f_46443_) {
                MagicManager.spawnParticles((Level)this.m_9236_(), (ParticleOptions)new BlastwaveParticleOptions(((SchoolType)SchoolRegistry.ENDER.get()).getTargetingColor(), this.getRadius()), (double)this.m_20185_(), (double)(this.m_20186_() + (double)0.165f), (double)this.m_20189_(), (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)false);
            }
            this.m_5496_((SoundEvent)TravelopticsSounds.NIGHTWARDEN_SWING.get(), 1.0f, 1.0f);
        }
        if (!this.playedSqueezeAnimation && this.age >= this.maxAge - 5) {
            this.playedSqueezeAnimation = true;
        }
        if (this.age >= this.maxAge) {
            this.m_146870_();
        }
    }

    public Iterable<ItemStack> m_6168_() {
        return Collections.singleton(ItemStack.f_41583_);
    }

    public ItemStack m_6844_(EquipmentSlot pSlot) {
        return ItemStack.f_41583_;
    }

    public void m_8061_(EquipmentSlot pSlot, ItemStack pStack) {
    }

    public HumanoidArm m_5737_() {
        return HumanoidArm.RIGHT;
    }

    public void onAntiMagic(MagicData playerMagicData) {
    }

    public boolean m_6094_() {
        return false;
    }

    public boolean m_5829_() {
        return true;
    }

    public boolean m_6087_() {
        return true;
    }

    public boolean m_7301_(MobEffectInstance effect) {
        return false;
    }

    public boolean m_6052_() {
        return false;
    }

    @Nullable
    protected SoundEvent m_7975_(DamageSource pDamageSource) {
        return (SoundEvent)TravelopticsSounds.CRIMSON_DESCEND_IMPACT.get();
    }

    @Nullable
    protected SoundEvent m_5592_() {
        return (SoundEvent)TravelopticsSounds.CRIMSON_DESCEND_IMPACT.get();
    }

    public boolean m_6469_(DamageSource pSource, float pAmount) {
        if (this.m_9236_().f_46443_ || this.m_6673_(pSource)) {
            return false;
        }
        this.m_146870_();
        return true;
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

    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(DATA_RADIUS, (Object)Float.valueOf(4.0f));
        this.f_19804_.m_135372_(DATA_DAMAGE, (Object)Float.valueOf(5.0f));
        this.f_19804_.m_135372_(MAGIC_DAMAGE_MODE, (Object)false);
    }

    public void m_7380_(CompoundTag tag) {
        super.m_7380_(tag);
        tag.m_128350_("HpBonusPercent", this.hpBasedDamagePercent);
        tag.m_128350_("Radius", this.getRadius());
        tag.m_128350_("Damage", this.getDamage());
        tag.m_128405_("Age", this.getAge());
        tag.m_128405_("MaxAge", this.getMaxAge());
        tag.m_128350_("ForwardSpeed", this.forwardSpeed);
        tag.m_128379_("ShouldMoveForward", this.shouldMoveForward);
        if (this.summonerUUID != null) {
            tag.m_128362_("Summoner", this.summonerUUID);
        }
        tag.m_128379_("MagicDamageMode", this.isMagicDamageMode());
    }

    public void m_7378_(CompoundTag tag) {
        super.m_7378_(tag);
        this.hpBasedDamagePercent = tag.m_128457_("HpBonusPercent");
        if (tag.m_128441_("Age")) {
            this.age = tag.m_128451_("Age");
        }
        if (tag.m_128441_("MaxAge")) {
            this.maxAge = tag.m_128451_("MaxAge");
        }
        if (tag.m_128441_("ForwardSpeed")) {
            this.forwardSpeed = tag.m_128457_("ForwardSpeed");
        }
        if (tag.m_128441_("ShouldMoveForward")) {
            this.shouldMoveForward = tag.m_128471_("ShouldMoveForward");
        }
        if (tag.m_128441_("Radius")) {
            this.setRadius(tag.m_128457_("Radius"));
        }
        if (tag.m_128441_("Damage")) {
            this.setDamage(tag.m_128457_("Damage"));
        }
        if (tag.m_128403_("Summoner")) {
            this.summonerUUID = tag.m_128342_("Summoner");
        }
        if (tag.m_128441_("MagicDamageMode")) {
            this.setMagicDamageMode(tag.m_128471_("MagicDamageMode"));
        }
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return LivingEntity.m_21183_().m_22268_(Attributes.f_22281_, 0.0).m_22268_(Attributes.f_22276_, 1.0).m_22268_(Attributes.f_22277_, 0.0).m_22268_(Attributes.f_22278_, 100.0).m_22268_(Attributes.f_22279_, 0.0);
    }

    private PlayState animationPredicate(AnimationState<NightwardenSpinCloneEntity> event) {
        event.getController().setAnimation(this.SPIN_ANIMATION);
        return PlayState.CONTINUE;
    }

    private PlayState squeezeAnimationPredicate(AnimationState<NightwardenSpinCloneEntity> event) {
        if (this.playedSqueezeAnimation) {
            event.getController().setAnimation(this.SQUEEZE_ANIMATION);
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

