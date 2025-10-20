/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.api.spells.AbstractSpell
 *  io.redspace.ironsspellbooks.api.util.CameraShakeData
 *  io.redspace.ironsspellbooks.api.util.CameraShakeManager
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  io.redspace.ironsspellbooks.damage.DamageSources
 *  javax.annotation.Nullable
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.tags.TagKey
 *  net.minecraft.util.Mth
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.MoverType
 *  net.minecraft.world.entity.TraceableEntity
 *  net.minecraft.world.level.ClipContext
 *  net.minecraft.world.level.ClipContext$Block
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult$Type
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
package com.gametechbc.traveloptics.entity.projectiles.dragon_spirit_spell_entity;

import com.gametechbc.traveloptics.entity.projectiles.StellarTrailAoeEntity;
import com.gametechbc.traveloptics.init.TravelopticsEntities;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import com.gametechbc.traveloptics.init.TravelopticsSpells;
import com.gametechbc.traveloptics.util.TravelopticsParticleHelper;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.util.CameraShakeData;
import io.redspace.ironsspellbooks.api.util.CameraShakeManager;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
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

public class DragonSpiritSpellEntity
extends Entity
implements GeoEntity,
TraceableEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    private float damage = 10.0f;
    private float stellarTrailDamage = 2.5f;
    private float hpBasedDamagePercent = 0.0f;
    private int age;
    private Vec3 direction = Vec3.f_82478_;
    private float speed = 0.27f;
    @Nullable
    private UUID ownerUUID;
    @Nullable
    private Entity cachedOwner;
    private final RawAnimation ANIMATION_LOOP = RawAnimation.begin().thenLoop("dragon_spirit_projectile_move_1");
    private final AnimationController<DragonSpiritSpellEntity> controller = new AnimationController((GeoAnimatable)this, "dragon_controller", 0, this::animationPredicate);

    public DragonSpiritSpellEntity(EntityType<? extends DragonSpiritSpellEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.m_20242_(true);
        this.f_19811_ = true;
    }

    public DragonSpiritSpellEntity(Level pLevel, LivingEntity pShooter) {
        this((EntityType<? extends DragonSpiritSpellEntity>)((EntityType)TravelopticsEntities.DRAGON_SPIRIT_SPELL_ENTITY.get()), pLevel);
        this.setOwner((Entity)pShooter);
        this.f_19811_ = true;
    }

    protected void m_8097_() {
    }

    public void shoot(Vec3 direction) {
        this.direction = direction.m_82541_();
        this.m_20256_(this.direction.m_82490_((double)this.speed));
        double horizontalDistance = direction.m_165924_();
        this.m_146922_((float)(Mth.m_14136_((double)direction.f_82479_, (double)direction.f_82481_) * 57.29577951308232));
        this.m_146926_((float)(Mth.m_14136_((double)direction.f_82480_, (double)horizontalDistance) * 57.29577951308232));
        this.f_19859_ = this.m_146908_();
        this.f_19860_ = this.m_146909_();
    }

    public void shootFromRotation(Entity shooter, float pitch, float yaw, float roll, float velocity, float inaccuracy) {
        float f = -Mth.m_14031_((float)(yaw * ((float)Math.PI / 180))) * Mth.m_14089_((float)(pitch * ((float)Math.PI / 180)));
        float f1 = -Mth.m_14031_((float)((pitch + roll) * ((float)Math.PI / 180)));
        float f2 = Mth.m_14089_((float)(yaw * ((float)Math.PI / 180))) * Mth.m_14089_((float)(pitch * ((float)Math.PI / 180)));
        Vec3 shootDirection = new Vec3((double)f, (double)f1, (double)f2);
        if (inaccuracy > 0.0f) {
            shootDirection = shootDirection.m_82520_(this.f_19796_.m_216328_(0.0, 0.0172275 * (double)inaccuracy), this.f_19796_.m_216328_(0.0, 0.0172275 * (double)inaccuracy), this.f_19796_.m_216328_(0.0, 0.0172275 * (double)inaccuracy));
        }
        this.shoot(shootDirection.m_82490_((double)velocity));
        Vec3 shooterMotion = shooter.m_20184_();
        this.m_20256_(this.m_20184_().m_82520_(shooterMotion.f_82479_, shooter.m_20096_() ? 0.0 : shooterMotion.f_82480_, shooterMotion.f_82481_));
    }

    public void m_8119_() {
        float pitch;
        super.m_8119_();
        ++this.age;
        if (this.age == 1) {
            this.m_5496_((SoundEvent)TravelopticsSounds.DRAGON_SPIRIT_AMBIENT.get(), 2.0f, 1.0f);
        }
        if (this.age == 100) {
            pitch = 0.9f + this.f_19796_.m_188501_() * 0.3f;
            this.m_5496_((SoundEvent)TravelopticsSounds.DRAGON_SPIRIT_SPAWN.get(), 2.0f, pitch);
        }
        if (this.age == 199) {
            pitch = 0.9f + this.f_19796_.m_188501_() * 0.3f;
            this.m_5496_((SoundEvent)TravelopticsSounds.DRAGON_SPIRIT_AMBIENT.get(), 3.0f, pitch);
            if (!this.m_9236_().f_46443_) {
                MagicManager.spawnParticles((Level)this.m_9236_(), (ParticleOptions)TravelopticsParticleHelper.ABYSS_SPIKE_PARTICLE, (double)this.m_20185_(), (double)this.m_20186_(), (double)this.m_20189_(), (int)100, (double)0.5, (double)0.5, (double)0.5, (double)0.02, (boolean)true);
            }
        }
        if (!this.m_9236_().f_46443_ && this.age % 30 == 0) {
            CameraShakeManager.addCameraShake((CameraShakeData)new CameraShakeData(30, this.m_20182_(), 15.0f));
        }
        if (this.age >= 200) {
            this.m_146870_();
            return;
        }
        this.customMovement();
        if (!this.m_9236_().f_46443_) {
            if (this.f_19797_ % 2 == 0) {
                this.customHitDetection();
                for (Entity entity : this.m_9236_().m_45933_((Entity)this, this.m_20191_())) {
                    LivingEntity target;
                    if (!(entity instanceof LivingEntity) || (target = (LivingEntity)entity) == this.m_19749_()) continue;
                    float baseDamage = this.damage;
                    float bonusDamage = target.m_21233_() * this.getHpBasedDamagePercent();
                    float totalDamage = baseDamage + bonusDamage;
                    DamageSources.applyDamage((Entity)target, (float)totalDamage, (DamageSource)((AbstractSpell)TravelopticsSpells.SHEAR_OF_THE_STARS_SPELL.get()).getDamageSource((Entity)this, this.m_19749_()));
                    DamageSources.ignoreNextKnockback((LivingEntity)target);
                    target.f_19802_ = 0;
                }
            }
            if (this.f_19797_ % 10 == 0) {
                AABB box = this.m_20191_();
                double randomX = box.f_82288_ + this.f_19796_.m_188500_() * (box.f_82291_ - box.f_82288_);
                double randomY = box.f_82289_ + this.f_19796_.m_188500_() * (box.f_82292_ - box.f_82289_);
                double randomZ = box.f_82290_ + this.f_19796_.m_188500_() * (box.f_82293_ - box.f_82290_);
                StellarTrailAoeEntity stellarTrail = new StellarTrailAoeEntity(this.m_9236_());
                stellarTrail.m_6034_(randomX, randomY, randomZ);
                stellarTrail.m_5602_(this.m_19749_());
                stellarTrail.setDamage(this.stellarTrailDamage);
                stellarTrail.setRadius(4.0f);
                stellarTrail.setDuration(200);
                this.m_9236_().m_7967_((Entity)stellarTrail);
            }
        }
    }

    private void customMovement() {
        Vec3 motion = this.m_20184_();
        this.m_146884_(this.m_20182_().m_82549_(motion));
        this.updateRotation();
        if (!this.m_20068_()) {
            this.m_20334_(motion.f_82479_, motion.f_82480_ - (double)0.05f, motion.f_82481_);
        }
    }

    private void customHitDetection() {
        Vec3 start = this.m_20182_();
        Vec3 end = start.m_82549_(this.m_20184_());
        BlockHitResult blockHit = this.m_9236_().m_45547_(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, (Entity)this));
        if (blockHit.m_6662_() == HitResult.Type.BLOCK) {
            this.onHitBlock(blockHit);
        }
    }

    private void updateRotation() {
        Vec3 motion = this.m_20184_();
        if (motion.m_82556_() > 0.0) {
            double horizontalDistance = motion.m_165924_();
            this.m_146926_(DragonSpiritSpellEntity.lerpRotation(this.f_19860_, (float)(Mth.m_14136_((double)motion.f_82480_, (double)horizontalDistance) * 57.29577951308232)));
            this.m_146922_(DragonSpiritSpellEntity.lerpRotation(this.f_19859_, (float)(Mth.m_14136_((double)motion.f_82479_, (double)motion.f_82481_) * 57.29577951308232)));
        }
    }

    private static float lerpRotation(float current, float target) {
        while (target - current < -180.0f) {
            current -= 360.0f;
        }
        while (target - current >= 180.0f) {
            current += 360.0f;
        }
        return Mth.m_14179_((float)0.2f, (float)current, (float)target);
    }

    protected void onHitBlock(BlockHitResult blockHitResult) {
    }

    public void setOwner(@Nullable Entity owner) {
        if (owner != null) {
            this.ownerUUID = owner.m_20148_();
            this.cachedOwner = owner;
        }
    }

    @Nullable
    public Entity m_19749_() {
        Level level;
        if (this.cachedOwner != null && !this.cachedOwner.m_213877_()) {
            return this.cachedOwner;
        }
        if (this.ownerUUID != null && (level = this.m_9236_()) instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)level;
            this.cachedOwner = serverLevel.m_8791_(this.ownerUUID);
            return this.cachedOwner;
        }
        return null;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getDamage() {
        return this.damage;
    }

    public void setStellarTrailDamage(float damage) {
        this.stellarTrailDamage = damage;
    }

    public float getStellarTrailDamage() {
        return this.stellarTrailDamage;
    }

    public void setHpBasedDamagePercent(float percent) {
        this.hpBasedDamagePercent = percent;
    }

    public float getHpBasedDamagePercent() {
        return this.hpBasedDamagePercent;
    }

    public boolean m_6094_() {
        return false;
    }

    public boolean m_5829_() {
        return false;
    }

    public boolean m_5825_() {
        return true;
    }

    public boolean m_6087_() {
        return false;
    }

    public boolean m_142079_() {
        return false;
    }

    public boolean m_6469_(DamageSource pSource, float pAmount) {
        return false;
    }

    public void m_20254_(int pSeconds) {
    }

    public boolean m_6060_() {
        return false;
    }

    protected void m_20101_() {
    }

    public void m_20321_(boolean pDownwards) {
    }

    public boolean m_204031_(TagKey<Fluid> pFluidTag, double pMotionScale) {
        return false;
    }

    protected void m_20157_() {
    }

    public void m_7334_(Entity pEntity) {
    }

    public void m_5997_(double pX, double pY, double pZ) {
    }

    public void m_6478_(MoverType pType, Vec3 pPos) {
    }

    protected void m_7380_(CompoundTag tag) {
        tag.m_128350_("HpBonusPercent", this.hpBasedDamagePercent);
        tag.m_128405_("Age", this.age);
        tag.m_128350_("Damage", this.damage);
        tag.m_128350_("StellarTrailDamage", this.stellarTrailDamage);
        tag.m_128350_("Speed", this.speed);
        if (this.ownerUUID != null) {
            tag.m_128362_("Owner", this.ownerUUID);
        }
        tag.m_128347_("DirectionX", this.direction.f_82479_);
        tag.m_128347_("DirectionY", this.direction.f_82480_);
        tag.m_128347_("DirectionZ", this.direction.f_82481_);
    }

    protected void m_7378_(CompoundTag tag) {
        this.hpBasedDamagePercent = tag.m_128457_("HpBonusPercent");
        this.age = tag.m_128451_("Age");
        this.damage = tag.m_128457_("Damage");
        this.stellarTrailDamage = tag.m_128457_("StellarTrailDamage");
        this.speed = tag.m_128457_("Speed");
        if (tag.m_128403_("Owner")) {
            this.ownerUUID = tag.m_128342_("Owner");
            this.cachedOwner = null;
        }
        this.direction = new Vec3(tag.m_128459_("DirectionX"), tag.m_128459_("DirectionY"), tag.m_128459_("DirectionZ"));
    }

    private PlayState animationPredicate(AnimationState<DragonSpiritSpellEntity> event) {
        AnimationController controller = event.getController();
        controller.setAnimation(this.ANIMATION_LOOP);
        return PlayState.CONTINUE;
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController[]{this.controller});
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}

