/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.api.magic.MagicData
 *  io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible
 *  javax.annotation.Nullable
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
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
package com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.nightwarden_clone.nightwarden_slash_clone;

import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.nightwarden_clone.NightwardenCloneBase;
import com.gametechbc.traveloptics.entity.projectiles.void_slash.VoidSlashProjectile;
import com.gametechbc.traveloptics.init.TravelopticsEntities;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
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

public class NightwardenSlashCloneEntity
extends NightwardenCloneBase
implements GeoEntity,
AntiMagicSusceptible {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    private float hpBasedDamagePercent = 0.0f;
    private float lifestealPercent = 1.0f;
    private static final EntityDataAccessor<Float> DATA_DAMAGE = SynchedEntityData.m_135353_(NightwardenSlashCloneEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135029_);
    private static final EntityDataAccessor<Boolean> IS_LEFT_SLASH = SynchedEntityData.m_135353_(NightwardenSlashCloneEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> MAGIC_DAMAGE_MODE = SynchedEntityData.m_135353_(NightwardenSlashCloneEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private final RawAnimation LEFT_ANIMATION = RawAnimation.begin().thenPlay("nightwarden_clone_left_swing");
    private final RawAnimation RIGHT_ANIMATION = RawAnimation.begin().thenPlay("nightwarden_clone_right_swing");
    private final AnimationController<NightwardenSlashCloneEntity> controller = new AnimationController((GeoAnimatable)this, "nightwarden_slash_controller", 0, this::animationPredicate);

    public NightwardenSlashCloneEntity(EntityType<? extends LivingEntity> type, Level level) {
        super(type, level);
    }

    public NightwardenSlashCloneEntity(Level level, LivingEntity entityToCopy, boolean isLeftSlash, float yawOffset) {
        this((EntityType<? extends LivingEntity>)((EntityType)TravelopticsEntities.NIGHTWARDEN_SLASH_CLONE.get()), level);
        float baseYaw = entityToCopy.m_146908_();
        float adjustedYaw = baseYaw + yawOffset;
        this.m_7678_(entityToCopy.m_20185_(), entityToCopy.m_20186_(), entityToCopy.m_20189_(), adjustedYaw, entityToCopy.m_146909_());
        this.m_5618_(adjustedYaw);
        this.f_20884_ = adjustedYaw;
        this.m_5616_(adjustedYaw);
        this.f_20886_ = adjustedYaw;
        this.setSummoner(entityToCopy);
        this.setLeftSlash(isLeftSlash);
    }

    public void setHpBasedDamagePercent(float percent) {
        this.hpBasedDamagePercent = percent;
    }

    public float getHpBasedDamagePercent() {
        return this.hpBasedDamagePercent;
    }

    public void setDamage(float damage) {
        this.f_19804_.m_135381_(DATA_DAMAGE, (Object)Float.valueOf(damage));
    }

    public float getDamage() {
        return ((Float)this.f_19804_.m_135370_(DATA_DAMAGE)).floatValue();
    }

    public void setLifestealPercent(float percent) {
        this.lifestealPercent = percent;
    }

    public float getLifestealPercent() {
        return this.lifestealPercent;
    }

    public void setLeftSlash(boolean left) {
        this.f_19804_.m_135381_(IS_LEFT_SLASH, (Object)left);
    }

    public boolean isLeftSlash() {
        return (Boolean)this.f_19804_.m_135370_(IS_LEFT_SLASH);
    }

    public boolean isMagicDamageMode() {
        return (Boolean)this.f_19804_.m_135370_(MAGIC_DAMAGE_MODE);
    }

    public void setMagicDamageMode(boolean value) {
        this.f_19804_.m_135381_(MAGIC_DAMAGE_MODE, (Object)value);
    }

    public void m_8119_() {
        LivingEntity summoner;
        super.m_8119_();
        if (this.f_19797_ == 2 && (summoner = this.getSummoner()) != null) {
            VoidSlashProjectile slash = new VoidSlashProjectile(this.m_9236_(), summoner);
            Vec3 origin = this.m_146892_();
            slash.m_146884_(origin);
            Vec3 look = this.m_20154_();
            Vec3 modifiedLook = new Vec3(look.f_82479_, look.f_82480_ - 0.05, look.f_82481_).m_82541_();
            slash.m_6686_(modifiedLook.f_82479_, modifiedLook.f_82480_, modifiedLook.f_82481_, 1.2f, 0.0f);
            slash.setMagicDamageMode(this.isMagicDamageMode());
            slash.setDamage(this.getDamage());
            slash.setLifestealPercent(this.getLifestealPercent());
            this.m_9236_().m_7967_((Entity)slash);
        }
        if (this.f_19797_ >= 17) {
            this.m_146870_();
        }
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

    public boolean m_6469_(DamageSource pSource, float pAmount) {
        return false;
    }

    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(DATA_DAMAGE, (Object)Float.valueOf(5.0f));
        this.f_19804_.m_135372_(IS_LEFT_SLASH, (Object)false);
        this.f_19804_.m_135372_(MAGIC_DAMAGE_MODE, (Object)false);
    }

    @Override
    public void m_7380_(CompoundTag tag) {
        super.m_7380_(tag);
        tag.m_128350_("HpBonusPercent", this.hpBasedDamagePercent);
        tag.m_128350_("Damage", this.getDamage());
        tag.m_128379_("IsLeftSlash", this.isLeftSlash());
        tag.m_128379_("MagicDamageMode", this.isMagicDamageMode());
        tag.m_128350_("LifestealPercent", this.lifestealPercent);
    }

    @Override
    public void m_7378_(CompoundTag tag) {
        super.m_7378_(tag);
        this.hpBasedDamagePercent = tag.m_128457_("HpBonusPercent");
        if (tag.m_128441_("Damage")) {
            this.setDamage(tag.m_128457_("Damage"));
        }
        if (tag.m_128441_("IsLeftSlash")) {
            this.setLeftSlash(tag.m_128471_("IsLeftSlash"));
        }
        if (tag.m_128441_("MagicDamageMode")) {
            this.setMagicDamageMode(tag.m_128471_("MagicDamageMode"));
        }
        if (tag.m_128441_("LifestealPercent")) {
            this.lifestealPercent = tag.m_128457_("LifestealPercent");
        }
    }

    private PlayState animationPredicate(AnimationState<NightwardenSlashCloneEntity> event) {
        event.getController().setAnimation(this.isLeftSlash() ? this.LEFT_ANIMATION : this.RIGHT_ANIMATION);
        return PlayState.CONTINUE;
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar registrar) {
        registrar.add(new AnimationController[]{this.controller});
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}

