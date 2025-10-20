/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.api.spells.AbstractSpell
 *  io.redspace.ironsspellbooks.api.util.Utils
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  io.redspace.ironsspellbooks.damage.DamageSources
 *  io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile
 *  io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions
 *  javax.annotation.Nullable
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientGamePacketListener
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.util.Mth
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.TamableAnimal
 *  net.minecraft.world.entity.projectile.Projectile
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.EntityHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.entity.IEntityAdditionalSpawnData
 *  net.minecraftforge.network.NetworkHooks
 *  org.joml.Vector3f
 */
package com.gametechbc.traveloptics.entity.projectiles.coral_bolt;

import com.gametechbc.traveloptics.init.TravelopticsEntities;
import com.gametechbc.traveloptics.init.TravelopticsSpells;
import com.gametechbc.traveloptics.util.TravelopticsParticleHelper;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import org.joml.Vector3f;

public class PinkCoralBoltProjectile
extends AbstractMagicProjectile
implements IEntityAdditionalSpawnData {
    private int age;
    private static final int EXPIRE_TIME = 60;
    @Nullable
    Entity cachedHomingTarget;
    @Nullable
    UUID homingTargetUUID;

    public PinkCoralBoltProjectile(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.m_20242_(true);
    }

    public PinkCoralBoltProjectile(Level pLevel, LivingEntity pShooter) {
        this((EntityType<? extends Projectile>)((EntityType)TravelopticsEntities.PINK_CORAL_BOLT_PROJECTILE.get()), pLevel);
        this.m_5602_((Entity)pShooter);
    }

    public void shoot(Vec3 rotation, float inaccuracy) {
        double speed = rotation.m_82553_();
        Vec3 offset = Utils.getRandomVec3((double)1.0).m_82541_().m_82490_((double)inaccuracy);
        Vec3 motion = rotation.m_82541_().m_82549_(offset).m_82541_().m_82490_(speed);
        super.shoot(motion);
    }

    @Nullable
    public Entity getHomingTarget() {
        if (this.cachedHomingTarget != null && !this.cachedHomingTarget.m_213877_()) {
            return this.cachedHomingTarget;
        }
        if (this.homingTargetUUID != null && this.m_9236_() instanceof ServerLevel) {
            this.cachedHomingTarget = ((ServerLevel)this.m_9236_()).m_8791_(this.homingTargetUUID);
            return this.cachedHomingTarget;
        }
        return null;
    }

    public void setHomingTarget(LivingEntity entity) {
        this.homingTargetUUID = entity.m_20148_();
        this.cachedHomingTarget = entity;
    }

    public void m_8119_() {
        super.m_8119_();
        if (++this.age > 60) {
            this.m_146870_();
            return;
        }
        Entity homingTarget = this.getHomingTarget();
        if (homingTarget != null && !this.doHomingTowards(homingTarget)) {
            this.homingTargetUUID = null;
            this.cachedHomingTarget = null;
        }
    }

    private boolean doHomingTowards(Entity entity) {
        if (entity.m_213877_()) {
            return false;
        }
        Vec3 motion = this.m_20184_();
        double speed = this.m_20184_().m_82553_();
        Vec3 delta = entity.m_20191_().m_82399_().m_82546_(this.m_20182_()).m_82549_(entity.m_20184_());
        float f = 0.08f;
        Vec3 newMotion = new Vec3(Mth.m_14139_((double)f, (double)motion.f_82479_, (double)delta.f_82479_), Mth.m_14139_((double)f, (double)motion.f_82480_, (double)delta.f_82480_), Mth.m_14139_((double)f, (double)motion.f_82481_, (double)delta.f_82481_)).m_82541_().m_82490_(speed);
        this.m_20256_(newMotion);
        return this.f_19797_ <= 10 || !(newMotion.m_82526_(delta) < 0.0);
    }

    protected void m_5790_(EntityHitResult pResult) {
        if (!this.m_9236_().f_46443_) {
            Entity target = pResult.m_82443_();
            Entity owner = this.m_19749_();
            double radius = 2.0;
            DamageSources.applyDamage((Entity)target, (float)this.damage, (DamageSource)((AbstractSpell)TravelopticsSpells.CORAL_BARRAGE_SPELL.get()).getDamageSource((Entity)this, owner));
            if (target.m_20148_().equals(this.homingTargetUUID)) {
                target.f_19802_ = 0;
            }
            AABB aoeRegion = new AABB(this.m_20185_() - radius, this.m_20186_() - radius, this.m_20189_() - radius, this.m_20185_() + radius, this.m_20186_() + radius, this.m_20189_() + radius);
            List nearbyEntities = this.m_9236_().m_6249_((Entity)this, aoeRegion, entity -> entity instanceof LivingEntity);
            for (Entity entity2 : nearbyEntities) {
                LivingEntity livingEntity;
                if (entity2 == target || entity2 == owner || !(entity2 instanceof LivingEntity) || this.isAlly((LivingEntity)owner, livingEntity = (LivingEntity)entity2) || this.isTamed(livingEntity)) continue;
                DamageSources.applyDamage((Entity)entity2, (float)this.damage, (DamageSource)((AbstractSpell)TravelopticsSpells.CORAL_BARRAGE_SPELL.get()).getDamageSource((Entity)this, owner));
            }
            MagicManager.spawnParticles((Level)this.m_9236_(), (ParticleOptions)new BlastwaveParticleOptions(new Vector3f(0.8392f, 0.3294f, 0.6078f), 2.0f), (double)this.m_20185_(), (double)(this.m_20186_() + (double)0.165f), (double)this.m_20189_(), (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)true);
        }
    }

    protected void m_8060_(BlockHitResult pResult) {
        super.m_8060_(pResult);
        if (!this.m_9236_().f_46443_) {
            this.m_146870_();
        }
    }

    protected void m_6532_(HitResult pResult) {
        super.m_6532_(pResult);
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

    public void trailParticles() {
        Vec3 vec3 = this.m_20184_();
        double d0 = this.m_20185_() - vec3.f_82479_;
        double d1 = this.m_20186_() - vec3.f_82480_;
        double d2 = this.m_20189_() - vec3.f_82481_;
        int count = Mth.m_14045_((int)((int)(vec3.m_82556_() * 4.0)), (int)1, (int)5);
        for (int i = 0; i < count; ++i) {
            Vec3 random = Utils.getRandomVec3((double)0.1);
            float f = (float)i / (float)count;
            double x = Mth.m_14139_((double)f, (double)d0, (double)this.m_20185_());
            double y = Mth.m_14139_((double)f, (double)d1, (double)this.m_20186_());
            double z = Mth.m_14139_((double)f, (double)d2, (double)this.m_20189_());
            this.m_9236_().m_7106_(TravelopticsParticleHelper.PINK_CORAL_BUBBLE, x - random.f_82479_, y + 0.5 - random.f_82480_, z - random.f_82481_, random.f_82479_ * 0.5, random.f_82480_ * 0.5, random.f_82481_ * 0.5);
        }
    }

    public void impactParticles(double x, double y, double z) {
        MagicManager.spawnParticles((Level)this.m_9236_(), (ParticleOptions)TravelopticsParticleHelper.PINK_CORAL_SPARKS, (double)x, (double)y, (double)z, (int)15, (double)0.1, (double)0.1, (double)0.1, (double)0.25, (boolean)true);
    }

    public float getSpeed() {
        return 1.85f;
    }

    public Optional<SoundEvent> getImpactSound() {
        return Optional.empty();
    }

    protected void m_7380_(CompoundTag tag) {
        super.m_7380_(tag);
        if (this.homingTargetUUID != null) {
            tag.m_128362_("homingTarget", this.homingTargetUUID);
        }
    }

    protected void m_7378_(CompoundTag tag) {
        super.m_7378_(tag);
        if (tag.m_128425_("homingTarget", 11)) {
            this.homingTargetUUID = tag.m_128342_("homingTarget");
        }
    }

    public void writeSpawnData(FriendlyByteBuf buffer) {
        Entity owner = this.m_19749_();
        buffer.writeInt(owner == null ? 0 : owner.m_19879_());
        Entity homingTarget = this.getHomingTarget();
        buffer.writeInt(homingTarget == null ? 0 : homingTarget.m_19879_());
    }

    public void readSpawnData(FriendlyByteBuf additionalData) {
        Entity homingTarget;
        Entity owner = this.m_9236_().m_6815_(additionalData.readInt());
        if (owner != null) {
            this.m_5602_(owner);
        }
        if ((homingTarget = this.m_9236_().m_6815_(additionalData.readInt())) != null) {
            this.cachedHomingTarget = homingTarget;
            this.homingTargetUUID = homingTarget.m_20148_();
        }
    }

    public Packet<ClientGamePacketListener> m_5654_() {
        return NetworkHooks.getEntitySpawningPacket((Entity)this);
    }
}

