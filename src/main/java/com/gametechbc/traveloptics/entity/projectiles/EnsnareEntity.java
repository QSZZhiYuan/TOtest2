/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.api.registry.SchoolRegistry
 *  io.redspace.ironsspellbooks.api.spells.SchoolType
 *  io.redspace.ironsspellbooks.api.util.CameraShakeData
 *  io.redspace.ironsspellbooks.api.util.CameraShakeManager
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  io.redspace.ironsspellbooks.entity.spells.AoeEntity
 *  io.redspace.ironsspellbooks.entity.spells.root.RootEntity
 *  io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions
 *  io.redspace.ironsspellbooks.particle.ShockwaveParticleOptions
 *  io.redspace.ironsspellbooks.util.ModTags
 *  io.redspace.ironsspellbooks.util.ParticleHelper
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityDimensions
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.entity.projectile.Projectile
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 */
package com.gametechbc.traveloptics.entity.projectiles;

import com.gametechbc.traveloptics.init.TravelopticsEntities;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.api.util.CameraShakeData;
import io.redspace.ironsspellbooks.api.util.CameraShakeManager;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.entity.spells.AoeEntity;
import io.redspace.ironsspellbooks.entity.spells.root.RootEntity;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import io.redspace.ironsspellbooks.particle.ShockwaveParticleOptions;
import io.redspace.ironsspellbooks.util.ModTags;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EnsnareEntity
extends AoeEntity {
    private int tickCounter = 0;
    private static int rootDuration = 100;

    public EnsnareEntity(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
        this.setCircular();
    }

    public EnsnareEntity(Level level) {
        this((EntityType<? extends Projectile>)((EntityType)TravelopticsEntities.ENSNARE.get()), level);
    }

    public void setRootDuration(int duration) {
        rootDuration = duration;
    }

    public int getRootDuration() {
        return rootDuration;
    }

    public void m_8119_() {
        super.m_8119_();
        ++this.tickCounter;
        if (!this.m_9236_().f_46443_) {
            if (this.tickCounter == 1) {
                this.rootChangingEffects();
            }
            if (this.tickCounter == 60) {
                this.rootEntities();
                this.rootTriggerEffects();
            }
        }
    }

    private void rootChangingEffects() {
        Level level = this.m_9236_();
        if (level instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)level;
            MagicManager.spawnParticles((Level)serverLevel, (ParticleOptions)new ShockwaveParticleOptions(((SchoolType)SchoolRegistry.NATURE.get()).getTargetingColor(), -4.0f, true), (double)this.m_20185_(), (double)this.m_20186_(), (double)this.m_20189_(), (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)true);
            this.m_5496_((SoundEvent)TravelopticsSounds.ENSNARE_PREPARE.get(), 1.0f, 1.0f);
        }
    }

    private void rootTriggerEffects() {
        Level level = this.m_9236_();
        if (level instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)level;
            MagicManager.spawnParticles((Level)serverLevel, (ParticleOptions)new BlastwaveParticleOptions(((SchoolType)SchoolRegistry.NATURE.get()).getTargetingColor(), this.getRadius()), (double)this.m_20185_(), (double)(this.m_20186_() + (double)0.165f), (double)this.m_20189_(), (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)true);
            CameraShakeManager.addCameraShake((CameraShakeData)new CameraShakeData(15, this.m_20182_(), this.getRadius() + 6.0f));
            this.m_5496_((SoundEvent)TravelopticsSounds.ENSNARE_TRIGGER.get(), 1.0f, 1.0f);
        }
    }

    private void rootEntities() {
        Level level = this.m_9236_();
        if (level instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)level;
            LivingEntity owner = this.m_19749_() instanceof LivingEntity ? (LivingEntity)this.m_19749_() : null;
            List entitiesInRange = serverLevel.m_6443_(LivingEntity.class, this.m_20191_(), entity -> entity != null && !entity.m_6095_().m_204039_(ModTags.CANT_ROOT) && !entity.equals((Object)owner) && (owner == null || !this.isAlly(owner, (LivingEntity)entity)));
            for (LivingEntity target : entitiesInRange) {
                RootEntity rootEntity = new RootEntity((Level)serverLevel, target);
                rootEntity.setDuration(rootDuration);
                rootEntity.setTarget(target);
                rootEntity.m_20219_(target.m_20182_());
                serverLevel.m_7967_((Entity)rootEntity);
                target.m_8127_();
                target.m_7998_((Entity)rootEntity, true);
            }
        }
    }

    private boolean isAlly(LivingEntity owner, LivingEntity target) {
        return owner.m_5647_() != null && owner.m_5647_().m_83536_(target.m_5647_());
    }

    public void applyEffect(LivingEntity target) {
    }

    public float getParticleCount() {
        return 1.2f * this.getRadius();
    }

    protected float particleYOffset() {
        return 0.25f;
    }

    protected float getParticleSpeedModifier() {
        return 1.4f;
    }

    public Optional<ParticleOptions> getParticle() {
        return Optional.of(ParticleHelper.ACID);
    }

    protected boolean canHitTargetForGroundContext(LivingEntity target) {
        return true;
    }

    protected Vec3 getInflation() {
        return new Vec3(0.0, 5.0, 0.0);
    }

    public EntityDimensions m_6972_(Pose pPose) {
        return EntityDimensions.m_20395_((float)(this.getRadius() * 2.0f), (float)3.0f);
    }
}

