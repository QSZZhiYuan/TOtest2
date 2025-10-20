/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.init.ModSounds
 *  io.redspace.ironsspellbooks.api.magic.MagicData
 *  io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible
 *  io.redspace.ironsspellbooks.entity.spells.AoeEntity
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
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
package com.gametechbc.traveloptics.entity.projectiles.maelstrom;

import com.gametechbc.traveloptics.init.TravelopticsEffects;
import com.gametechbc.traveloptics.init.TravelopticsEntities;
import com.github.L_Ender.cataclysm.init.ModSounds;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import io.redspace.ironsspellbooks.entity.spells.AoeEntity;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
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

public class MaelstromEntity
extends AoeEntity
implements GeoEntity,
AntiMagicSusceptible {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    private final RawAnimation SPIN_ANIMATION = RawAnimation.begin().thenPlay("spin_once");
    private int age = 0;
    private double pullScale = 0.5;
    private final AnimationController controller = new AnimationController((GeoAnimatable)this, "maelstrom_controller", 0, this::animationPredicate);

    public MaelstromEntity(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
        this.reapplicationDelay = 30;
        this.setCircular();
    }

    public MaelstromEntity(Level level) {
        this((EntityType<? extends Projectile>)((EntityType)TravelopticsEntities.MAELSTROM_ENTITY.get()), level);
    }

    public double getPullScale() {
        return this.pullScale;
    }

    public void setPullScale(double pullScale) {
        this.pullScale = pullScale;
    }

    public void m_8119_() {
        super.m_8119_();
        ++this.age;
        if (this.m_9236_().f_46443_ && this.age == 22 && !this.m_20067_()) {
            this.m_9236_().m_7785_(this.m_20185_(), this.m_20186_(), this.m_20189_(), (SoundEvent)ModSounds.EMP_ACTIVATED.get(), this.m_5720_(), 2.5f, this.f_19796_.m_188501_() * 0.2f + 0.85f, false);
        }
        if (!this.m_9236_().f_46443_ && this.age >= 100) {
            this.m_146870_();
            return;
        }
        if (this.age < 5 || this.age >= 95) {
            return;
        }
        if (!this.m_9236_().f_46443_ && this.age % 2 == 0) {
            Vec3 center = this.m_20182_();
            float radius = this.getRadius();
            LivingEntity owner = this.m_19749_() instanceof LivingEntity ? (LivingEntity)this.m_19749_() : null;
            List nearbyEntities = this.m_9236_().m_6443_(LivingEntity.class, new AABB(center.f_82479_ - (double)radius, center.f_82480_ - (double)radius, center.f_82481_ - (double)radius, center.f_82479_ + (double)radius, center.f_82480_ + (double)radius, center.f_82481_ + (double)radius), entity -> {
                if (entity == owner || owner == null) {
                    return false;
                }
                return !this.isAlly(owner, (LivingEntity)entity) && !this.isTamed((LivingEntity)entity);
            });
            for (LivingEntity entity2 : nearbyEntities) {
                Vec3 entityPos = entity2.m_20182_();
                Vec3 directionToCenter = center.m_82546_(entityPos);
                double distance = directionToCenter.m_82553_();
                if (!(distance > 0.5)) continue;
                Vec3 pullForce = directionToCenter.m_82541_().m_82490_(this.getPullScale() * (distance / (double)radius));
                entity2.m_20256_(entity2.m_20184_().m_82490_(0.8).m_82549_(pullForce));
                entity2.f_19864_ = true;
            }
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

    public void applyEffect(LivingEntity target) {
        target.m_7292_(new MobEffectInstance((MobEffect)TravelopticsEffects.TIDAL_TORMENT.get(), 50, 1, false, false, false));
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
        return Optional.empty();
    }

    protected boolean canHitTargetForGroundContext(LivingEntity target) {
        return true;
    }

    protected void m_7380_(CompoundTag compound) {
        super.m_7380_(compound);
        compound.m_128347_("PullScale", this.pullScale);
        compound.m_128405_("Age", this.age);
    }

    protected void m_7378_(CompoundTag compound) {
        super.m_7378_(compound);
        if (compound.m_128441_("PullScale")) {
            this.pullScale = compound.m_128459_("PullScale");
        }
        if (compound.m_128441_("Age")) {
            this.age = compound.m_128451_("Age");
        }
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

