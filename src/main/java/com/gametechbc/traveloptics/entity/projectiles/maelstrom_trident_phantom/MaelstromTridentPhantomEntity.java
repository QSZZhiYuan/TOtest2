/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.client.particle.Custom_Poof_Particle$PoofData
 *  com.github.L_Ender.cataclysm.client.particle.LightningParticle$OrbData
 *  com.github.L_Ender.cataclysm.client.particle.Not_Spin_TrailParticle$NSTData
 *  com.github.L_Ender.cataclysm.init.ModSounds
 *  io.redspace.ironsspellbooks.api.magic.MagicData
 *  io.redspace.ironsspellbooks.api.spells.AbstractSpell
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  io.redspace.ironsspellbooks.damage.DamageSources
 *  io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible
 *  io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions
 *  javax.annotation.Nullable
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientGamePacketListener
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.util.Mth
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.MoverType
 *  net.minecraft.world.entity.TamableAnimal
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.network.NetworkHooks
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
package com.gametechbc.traveloptics.entity.projectiles.maelstrom_trident_phantom;

import com.gametechbc.traveloptics.api.utils.TOGeneralUtils;
import com.gametechbc.traveloptics.entity.misc.TOScreenShakeEntity;
import com.gametechbc.traveloptics.init.TravelopticsEffects;
import com.gametechbc.traveloptics.init.TravelopticsEntities;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import com.gametechbc.traveloptics.init.TravelopticsSpells;
import com.gametechbc.traveloptics.particle.colorful_bubble.ColorfulBubbleParticleOptions;
import com.github.L_Ender.cataclysm.client.particle.Custom_Poof_Particle;
import com.github.L_Ender.cataclysm.client.particle.LightningParticle;
import com.github.L_Ender.cataclysm.client.particle.Not_Spin_TrailParticle;
import com.github.L_Ender.cataclysm.init.ModSounds;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class MaelstromTridentPhantomEntity
extends Entity
implements GeoEntity,
AntiMagicSusceptible {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    private UUID summonerUUID;
    private LivingEntity cachedSummoner;
    private static final EntityDataAccessor<Float> DATA_RADIUS = SynchedEntityData.m_135353_(MaelstromTridentPhantomEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135029_);
    private static final EntityDataAccessor<Float> DATA_FIRST_IMPACT_DAMAGE = SynchedEntityData.m_135353_(MaelstromTridentPhantomEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135029_);
    private static final EntityDataAccessor<Float> DATA_PULSE_DAMAGE = SynchedEntityData.m_135353_(MaelstromTridentPhantomEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135029_);
    private static final EntityDataAccessor<Integer> DATA_MAX_AGE_TICKS = SynchedEntityData.m_135353_(MaelstromTridentPhantomEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Boolean> DATA_IS_DROPPING = SynchedEntityData.m_135353_(MaelstromTridentPhantomEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private boolean hasDealtImpactDamage = false;
    private int pulseTicker = 0;
    private static final int DROP_DURATION = 14;
    private static final int PULSE_INTERVAL = 40;
    private float customGravity = -1.5f;
    private boolean shouldSpawnDelayedBlastwave = false;
    private int delayedBlastwaveTimer = 0;
    private boolean applyReplenish = false;
    private final RawAnimation TRIDENT_DROP_ANIMATION = RawAnimation.begin().thenPlay("trident_drop");
    private final RawAnimation IDLE_ANIMATION = RawAnimation.begin().thenLoop("idle");
    private final AnimationController<?> controller = new AnimationController((GeoAnimatable)this, "maelstrom_phantom_controller", 0, this::animationPredicate);

    public MaelstromTridentPhantomEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.m_20242_(true);
    }

    public MaelstromTridentPhantomEntity(Level level, LivingEntity owner, float yRot) {
        this((EntityType)TravelopticsEntities.MAELSTROM_TRIDENT_PHANTOM.get(), level);
        this.setSummoner(owner);
        this.m_146922_(yRot);
        this.m_20242_(true);
    }

    public void setSummoner(@Nullable LivingEntity owner) {
        if (owner != null) {
            this.summonerUUID = owner.m_20148_();
            this.cachedSummoner = owner;
        }
    }

    public LivingEntity getSummoner() {
        Entity entity;
        if (this.cachedSummoner != null && this.cachedSummoner.m_6084_()) {
            return this.cachedSummoner;
        }
        if (this.summonerUUID != null && this.m_9236_() instanceof ServerLevel && (entity = ((ServerLevel)this.m_9236_()).m_8791_(this.summonerUUID)) instanceof LivingEntity) {
            LivingEntity livingEntity;
            this.cachedSummoner = livingEntity = (LivingEntity)entity;
            return this.cachedSummoner;
        }
        return null;
    }

    protected boolean isAlly(LivingEntity owner, LivingEntity target) {
        return owner.m_5647_() != null && owner.m_5647_().m_83536_(target.m_5647_());
    }

    protected boolean isTamed(LivingEntity target) {
        if (target instanceof TamableAnimal) {
            TamableAnimal tamableAnimal = (TamableAnimal)target;
            return tamableAnimal.m_21824_();
        }
        return false;
    }

    public void setRadius(float radius) {
        this.f_19804_.m_135381_(DATA_RADIUS, (Object)Float.valueOf(radius));
    }

    public float getRadius() {
        return ((Float)this.f_19804_.m_135370_(DATA_RADIUS)).floatValue();
    }

    public void setFirstImpactDamage(float damage) {
        this.f_19804_.m_135381_(DATA_FIRST_IMPACT_DAMAGE, (Object)Float.valueOf(damage));
    }

    public float getFirstImpactDamage() {
        return ((Float)this.f_19804_.m_135370_(DATA_FIRST_IMPACT_DAMAGE)).floatValue();
    }

    public void setPulseDamage(float damage) {
        this.f_19804_.m_135381_(DATA_PULSE_DAMAGE, (Object)Float.valueOf(damage));
    }

    public float getPulseDamage() {
        return ((Float)this.f_19804_.m_135370_(DATA_PULSE_DAMAGE)).floatValue();
    }

    public void setMaxAgeTicks(int ticks) {
        this.f_19804_.m_135381_(DATA_MAX_AGE_TICKS, (Object)ticks);
    }

    public int getMaxAgeTicks() {
        return (Integer)this.f_19804_.m_135370_(DATA_MAX_AGE_TICKS);
    }

    public void setIsDropping(boolean dropping) {
        this.f_19804_.m_135381_(DATA_IS_DROPPING, (Object)dropping);
    }

    public boolean isDropping() {
        return (Boolean)this.f_19804_.m_135370_(DATA_IS_DROPPING);
    }

    public void setCustomGravity(float gravity) {
        this.customGravity = gravity;
    }

    public float getCustomGravity() {
        return this.customGravity;
    }

    private void scheduleDelayedBlastwave() {
        this.shouldSpawnDelayedBlastwave = true;
        this.delayedBlastwaveTimer = 3;
    }

    public void setApplyReplenish(boolean applyReplenish) {
        this.applyReplenish = applyReplenish;
    }

    public boolean shouldApplyReplenish() {
        return this.applyReplenish;
    }

    public void m_8119_() {
        super.m_8119_();
        if (this.f_19797_ >= this.getMaxAgeTicks()) {
            this.m_146870_();
            return;
        }
        if (!this.m_20096_()) {
            Vec3 currentMotion = this.m_20184_();
            Vec3 newMotion = new Vec3(currentMotion.f_82479_, (double)this.customGravity, currentMotion.f_82481_);
            this.m_20256_(newMotion);
            this.m_6478_(MoverType.SELF, this.m_20184_());
        }
        if (this.isDropping() && this.f_19797_ >= 14) {
            this.setIsDropping(false);
            if (!this.hasDealtImpactDamage) {
                this.dealImpactDamage();
                this.hasDealtImpactDamage = true;
            }
        }
        if (!this.isDropping()) {
            ++this.pulseTicker;
            if (this.pulseTicker >= 40) {
                this.dealPulseDamage();
                this.pulseTicker = 0;
            }
            this.spawnPassiveParticles();
        }
        if (this.shouldSpawnDelayedBlastwave) {
            --this.delayedBlastwaveTimer;
            if (this.delayedBlastwaveTimer <= 0) {
                if (!this.m_9236_().f_46443_) {
                    MagicManager.spawnParticles((Level)this.m_9236_(), (ParticleOptions)new BlastwaveParticleOptions(TOGeneralUtils.hexToVector3f("#57acdd"), this.getRadius()), (double)this.m_20185_(), (double)(this.m_20186_() + (double)0.15f), (double)this.m_20189_(), (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)true);
                }
                this.shouldSpawnDelayedBlastwave = false;
            }
        }
    }

    private void dealImpactDamage() {
        if (!this.m_9236_().f_46443_) {
            float damage = this.getFirstImpactDamage();
            float radius = this.getRadius();
            this.damageEntitiesInRadius(damage, radius, false);
            MagicManager.spawnParticles((Level)this.m_9236_(), (ParticleOptions)new BlastwaveParticleOptions(TOGeneralUtils.hexToVector3f("#57acdd"), radius), (double)this.m_20185_(), (double)(this.m_20186_() + (double)0.15f), (double)this.m_20189_(), (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)true);
            this.scheduleDelayedBlastwave();
            TOScreenShakeEntity.createScreenShake(this.m_9236_(), this.m_20182_(), this.getRadius() * 2.0f, 0.02f, 10, 0, 5, false);
            this.m_5496_(SoundEvents.f_12521_, 3.0f, 0.8f);
            this.m_5496_((SoundEvent)ModSounds.HEAVY_SMASH.get(), 2.0f, 1.0f);
        }
        if (this.m_9236_().f_46443_) {
            this.spawnImpactParticles();
        }
    }

    private void dealPulseDamage() {
        if (!this.m_9236_().f_46443_) {
            float damage = this.getPulseDamage();
            float radius = this.getRadius();
            this.damageEntitiesInRadius(damage, radius, true);
            MagicManager.spawnParticles((Level)this.m_9236_(), (ParticleOptions)new BlastwaveParticleOptions(TOGeneralUtils.hexToVector3f("#57acdd"), this.getRadius()), (double)this.m_20185_(), (double)(this.m_20186_() + (double)0.15f), (double)this.m_20189_(), (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)true);
            TOScreenShakeEntity.createScreenShake(this.m_9236_(), this.m_20182_(), this.getRadius() * 2.0f, 0.008f, 5, 0, 2, false);
            this.m_5496_((SoundEvent)TravelopticsSounds.AQUA_CAST_2.get(), 1.5f, 0.8f);
            this.m_5496_(SoundEvents.f_12521_, 2.0f, 1.0f);
        }
        if (this.m_9236_().f_46443_) {
            this.spawnImpactBubblesAndFoam(this.getRadius());
        }
    }

    private void damageEntitiesInRadius(float damage, float radius, boolean applyTidalTorment) {
        LivingEntity owner = this.getSummoner();
        AABB region = new AABB(this.m_20185_() - (double)radius, this.m_20186_() - 1.0, this.m_20189_() - (double)radius, this.m_20185_() + (double)radius, this.m_20186_() + 2.0, this.m_20189_() + (double)radius);
        List<LivingEntity> targets = this.m_9236_().m_45976_(LivingEntity.class, region).stream().filter(entity -> entity.m_6084_() && entity != owner).filter(entity -> owner == null || !this.isAlly(owner, (LivingEntity)entity) && !this.isTamed((LivingEntity)entity)).toList();
        for (LivingEntity target : targets) {
            DamageSources.applyDamage((Entity)target, (float)damage, (DamageSource)((AbstractSpell)TravelopticsSpells.SKYPIERCER_SPELL.get()).getDamageSource((Entity)this, (Entity)owner));
            if (applyTidalTorment) {
                target.m_7292_(new MobEffectInstance((MobEffect)TravelopticsEffects.TIDAL_TORMENT.get(), 60, 1, false, true));
            }
            if (!this.shouldApplyReplenish() || owner == null) continue;
            int currentAmplifier = owner.m_21023_((MobEffect)TravelopticsEffects.REPLENISH_EFFECT.get()) ? owner.m_21124_((MobEffect)TravelopticsEffects.REPLENISH_EFFECT.get()).m_19564_() + 1 : 0;
            int cappedAmplifier = Math.min(currentAmplifier, 7);
            owner.m_7292_(new MobEffectInstance((MobEffect)TravelopticsEffects.REPLENISH_EFFECT.get(), 50, cappedAmplifier, false, false, false));
        }
    }

    private void spawnPassiveParticles() {
        if (this.m_9236_().f_46443_) {
            int i;
            float radius = this.getRadius();
            if (this.f_19797_ % 4 == 0) {
                int sparkAmount = 1 + this.f_19796_.m_188503_(2);
                for (i = 0; i < sparkAmount; ++i) {
                    float velocity = 1.2f + this.f_19796_.m_188501_() * 0.6f;
                    float yaw = (float)((double)(this.f_19796_.m_188501_() * 2.0f) * Math.PI);
                    float motionY = 0.3f + this.f_19796_.m_188501_() * 0.5f;
                    float motionX = velocity * Mth.m_14089_((float)yaw);
                    float motionZ = velocity * Mth.m_14031_((float)yaw);
                    double collidePosX = this.m_20185_();
                    double collidePosY = this.m_20186_();
                    double collidePosZ = this.m_20189_();
                    this.m_9236_().m_7106_((ParticleOptions)new LightningParticle.OrbData(TOGeneralUtils.hexToRed("#57acdd"), TOGeneralUtils.hexToGreen("#57acdd"), TOGeneralUtils.hexToBlue("#57acdd")), collidePosX, collidePosY + 0.1, collidePosZ, (double)motionX, (double)motionY, (double)motionZ);
                }
            }
            int bubbleCount = Math.max(1, Math.round(radius * 0.22f));
            for (i = 0; i < bubbleCount; ++i) {
                double angle = this.f_19796_.m_188500_() * 2.0 * Math.PI;
                double distance = this.f_19796_.m_188500_() * (double)radius;
                double particleX = this.m_20185_() + Math.cos(angle) * distance;
                double particleY = this.m_20186_() + 0.1 + this.f_19796_.m_188500_() * 0.2;
                double particleZ = this.m_20189_() + Math.sin(angle) * distance;
                double motionX = (this.f_19796_.m_188500_() - 0.5) * 0.05;
                double motionY = 0.15 + this.f_19796_.m_188500_() * 0.1;
                double motionZ = (this.f_19796_.m_188500_() - 0.5) * 0.05;
                this.m_9236_().m_7106_((ParticleOptions)new ColorfulBubbleParticleOptions(TOGeneralUtils.hexToVector3f("#57acdd"), 1.0f, 10 + (int)(Math.random() * 5.0), true), particleX, particleY, particleZ, motionX, motionY, motionZ);
            }
            if (this.f_19797_ % 2 == 0) {
                int foamCount = Math.max(1, Math.round(radius * 0.2f));
                for (int i2 = 0; i2 < foamCount; ++i2) {
                    double angle = this.f_19796_.m_188500_() * 2.0 * Math.PI;
                    double distance = this.f_19796_.m_188500_() * (double)radius;
                    double particleX = this.m_20185_() + Math.cos(angle) * distance;
                    double particleY = this.m_20186_() + 0.1 + this.f_19796_.m_188500_() * 0.2;
                    double particleZ = this.m_20189_() + Math.sin(angle) * distance;
                    int rand = this.f_19796_.m_188503_(20) - 10;
                    int r = Math.max(0, Math.min(255, TOGeneralUtils.hexToRed("#57acdd") + rand));
                    int g = Math.max(0, Math.min(255, TOGeneralUtils.hexToGreen("#57acdd") + rand));
                    int b = Math.max(0, Math.min(255, TOGeneralUtils.hexToBlue("#57acdd") + rand));
                    double motionX = (this.f_19796_.m_188500_() - 0.5) * 0.1;
                    double motionY = -0.01 - this.f_19796_.m_188500_() * 0.02;
                    double motionZ = (this.f_19796_.m_188500_() - 0.5) * 0.1;
                    this.m_9236_().m_7106_((ParticleOptions)new Custom_Poof_Particle.PoofData(r, g, b, 0.1f), particleX, particleY, particleZ, motionX, motionY, motionZ);
                }
            }
        }
    }

    private void spawnImpactParticles() {
        float radius = this.getRadius();
        for (int i = 0; i < 12; ++i) {
            float angle = (float)((double)i * 0.5235987755982988);
            float velocity = 2.0f + this.f_19796_.m_188501_() * 1.5f;
            double d0 = this.m_20185_();
            double d1 = this.m_20186_() + 0.1;
            double d2 = this.m_20189_();
            double extraX = d0 + (double)(velocity * Mth.m_14089_((float)angle));
            double extraY = d1 + 1.5 + (double)(this.f_19796_.m_188501_() * 1.5f);
            double extraZ = d2 + (double)(velocity * Mth.m_14031_((float)angle));
            float red = (float)TOGeneralUtils.hexToRed("#57acdd") / 255.0f;
            float green = (float)TOGeneralUtils.hexToGreen("#57acdd") / 255.0f;
            float blue = (float)TOGeneralUtils.hexToBlue("#57acdd") / 255.0f;
            this.m_9236_().m_7106_((ParticleOptions)new Not_Spin_TrailParticle.NSTData(red, green, blue, 0.05f, 0.75f, 0.5f, 0.0, 80 + this.f_19796_.m_188503_(40)), d0, d1, d2, extraX, extraY, extraZ);
        }
        this.spawnImpactBubblesAndFoam(radius);
    }

    private void spawnImpactBubblesAndFoam(float radius) {
        int bubbleCount = Math.round(radius * 2.0f);
        for (int i = 0; i < bubbleCount; ++i) {
            double angle = this.f_19796_.m_188500_() * 2.0 * Math.PI;
            double distance = this.f_19796_.m_188500_() * (double)radius;
            double particleX = this.m_20185_() + Math.cos(angle) * distance;
            double particleY = this.m_20186_() + 0.1 + this.f_19796_.m_188500_() * 0.3;
            double particleZ = this.m_20189_() + Math.sin(angle) * distance;
            double motionX = (this.f_19796_.m_188500_() - 0.5) * 0.3;
            double motionY = 0.2 + this.f_19796_.m_188500_() * 0.3;
            double motionZ = (this.f_19796_.m_188500_() - 0.5) * 0.3;
            this.m_9236_().m_7106_((ParticleOptions)new ColorfulBubbleParticleOptions(TOGeneralUtils.hexToVector3f("#57acdd"), 1.0f, 10 + (int)(Math.random() * 5.0), true), particleX, particleY, particleZ, motionX, motionY, motionZ);
        }
        int foamCount = Math.round(radius);
        for (int i = 0; i < foamCount; ++i) {
            double angle = this.f_19796_.m_188500_() * 2.0 * Math.PI;
            double distance = this.f_19796_.m_188500_() * (double)radius;
            double particleX = this.m_20185_() + Math.cos(angle) * distance;
            double particleY = this.m_20186_() + 0.2 + this.f_19796_.m_188500_() * 0.4;
            double particleZ = this.m_20189_() + Math.sin(angle) * distance;
            int rand = this.f_19796_.m_188503_(30) - 15;
            int r = Math.max(0, Math.min(255, TOGeneralUtils.hexToRed("#57acdd") + rand));
            int g = Math.max(0, Math.min(255, TOGeneralUtils.hexToGreen("#57acdd") + rand));
            int b = Math.max(0, Math.min(255, TOGeneralUtils.hexToBlue("#57acdd") + rand));
            double motionX = (this.f_19796_.m_188500_() - 0.5) * 0.4;
            double motionY = 0.1 + this.f_19796_.m_188500_() * 0.2;
            double motionZ = (this.f_19796_.m_188500_() - 0.5) * 0.4;
            this.m_9236_().m_7106_((ParticleOptions)new Custom_Poof_Particle.PoofData(r, g, b, 0.2f), particleX, particleY, particleZ, motionX, motionY, motionZ);
        }
    }

    public void onAntiMagic(MagicData playerMagicData) {
        this.m_146870_();
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

    protected void m_8097_() {
        this.f_19804_.m_135372_(DATA_RADIUS, (Object)Float.valueOf(12.0f));
        this.f_19804_.m_135372_(DATA_FIRST_IMPACT_DAMAGE, (Object)Float.valueOf(5.0f));
        this.f_19804_.m_135372_(DATA_PULSE_DAMAGE, (Object)Float.valueOf(3.0f));
        this.f_19804_.m_135372_(DATA_MAX_AGE_TICKS, (Object)200);
        this.f_19804_.m_135372_(DATA_IS_DROPPING, (Object)true);
    }

    public void m_7380_(CompoundTag tag) {
        if (this.summonerUUID != null) {
            tag.m_128362_("Summoner", this.summonerUUID);
        }
        tag.m_128350_("Radius", this.getRadius());
        tag.m_128350_("FirstImpactDamage", this.getFirstImpactDamage());
        tag.m_128350_("PulseDamage", this.getPulseDamage());
        tag.m_128405_("MaxAgeTicks", this.getMaxAgeTicks());
        tag.m_128379_("IsDropping", this.isDropping());
        tag.m_128379_("HasDealtImpactDamage", this.hasDealtImpactDamage);
        tag.m_128405_("PulseTicker", this.pulseTicker);
        tag.m_128350_("CustomGravity", this.customGravity);
        tag.m_128379_("ApplyReplenish", this.applyReplenish);
    }

    public void m_7378_(CompoundTag tag) {
        if (tag.m_128403_("Summoner")) {
            this.summonerUUID = tag.m_128342_("Summoner");
        }
        if (tag.m_128441_("Radius")) {
            this.setRadius(tag.m_128457_("Radius"));
        }
        if (tag.m_128441_("FirstImpactDamage")) {
            this.setFirstImpactDamage(tag.m_128457_("FirstImpactDamage"));
        }
        if (tag.m_128441_("PulseDamage")) {
            this.setPulseDamage(tag.m_128457_("PulseDamage"));
        }
        if (tag.m_128441_("MaxAgeTicks")) {
            this.setMaxAgeTicks(tag.m_128451_("MaxAgeTicks"));
        }
        if (tag.m_128441_("IsDropping")) {
            this.setIsDropping(tag.m_128471_("IsDropping"));
        }
        if (tag.m_128441_("HasDealtImpactDamage")) {
            this.hasDealtImpactDamage = tag.m_128471_("HasDealtImpactDamage");
        }
        if (tag.m_128441_("PulseTicker")) {
            this.pulseTicker = tag.m_128451_("PulseTicker");
        }
        if (tag.m_128441_("CustomGravity")) {
            this.customGravity = tag.m_128457_("CustomGravity");
        }
        if (tag.m_128441_("ApplyReplenish")) {
            this.applyReplenish = tag.m_128471_("ApplyReplenish");
        }
    }

    public Packet<ClientGamePacketListener> m_5654_() {
        return NetworkHooks.getEntitySpawningPacket((Entity)this);
    }

    private PlayState animationPredicate(AnimationState<?> event) {
        if (this.isDropping()) {
            event.getController().setAnimation(this.TRIDENT_DROP_ANIMATION);
        } else {
            event.getController().setAnimation(this.IDLE_ANIMATION);
        }
        return PlayState.CONTINUE;
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController[]{this.controller});
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}

