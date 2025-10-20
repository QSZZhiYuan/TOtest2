/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.core.Vec3i
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientGamePacketListener
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.projectile.Projectile
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.EntityHitResult
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.VoxelShape
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
package com.gametechbc.traveloptics.entity.projectiles.end_eruption_bomb;

import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.misc.EndEruptionEntity;
import com.gametechbc.traveloptics.init.TravelopticsEntities;
import com.gametechbc.traveloptics.init.TravelopticsParticles;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
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

public class EruptionBombProjectileEntity
extends AbstractMagicProjectile
implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    private static final EntityDataAccessor<Boolean> IS_FUSING = SynchedEntityData.m_135353_(EruptionBombProjectileEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private int animationTimer = 20;
    private boolean hasBounced = false;
    private int bounceCooldown = 0;
    private static final RawAnimation TRAVEL_ANIM = RawAnimation.begin().thenLoop("eruption_bomb_travel");
    private static final RawAnimation FUSE_ANIM = RawAnimation.begin().thenPlay("eruption_bomb_fuse");
    private final AnimationController<EruptionBombProjectileEntity> controller = new AnimationController((GeoAnimatable)this, "bomb_controller", 0, this::animationPredicate);

    public EruptionBombProjectileEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public EruptionBombProjectileEntity(Level pLevel, LivingEntity pShooter) {
        this((EntityType<? extends Projectile>)((EntityType)TravelopticsEntities.END_ERUPTION_BOMB.get()), pLevel);
        this.m_5602_((Entity)pShooter);
    }

    public Packet<ClientGamePacketListener> m_5654_() {
        return NetworkHooks.getEntitySpawningPacket((Entity)this);
    }

    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(IS_FUSING, (Object)true);
    }

    protected void m_7380_(CompoundTag tag) {
        super.m_7380_(tag);
        tag.m_128379_("IsFusing", this.isFusing());
        tag.m_128379_("HasBounced", this.hasBounced);
        tag.m_128405_("BounceCooldown", this.bounceCooldown);
        tag.m_128405_("AnimTimer", this.animationTimer);
    }

    protected void m_7378_(CompoundTag tag) {
        super.m_7378_(tag);
        if (tag.m_128441_("IsFusing")) {
            this.setFusing(tag.m_128471_("IsFusing"));
        }
        if (tag.m_128441_("HasBounced")) {
            this.hasBounced = tag.m_128471_("HasBounced");
        }
        if (tag.m_128441_("BounceCooldown")) {
            this.bounceCooldown = tag.m_128451_("BounceCooldown");
        }
        if (tag.m_128441_("AnimTimer")) {
            this.animationTimer = tag.m_128451_("AnimTimer");
        }
    }

    public boolean isFusing() {
        return (Boolean)this.f_19804_.m_135370_(IS_FUSING);
    }

    private void setFusing(boolean fusing) {
        this.f_19804_.m_135381_(IS_FUSING, (Object)fusing);
    }

    public void m_8119_() {
        super.m_8119_();
        if (!this.m_9236_().f_46443_ && this.animationTimer > 0) {
            --this.animationTimer;
            if (this.animationTimer <= 0) {
                this.setFusing(false);
            }
        }
        if (this.bounceCooldown > 0) {
            --this.bounceCooldown;
        }
    }

    protected void m_8060_(BlockHitResult result) {
        super.m_8060_(result);
        if (!this.m_9236_().f_46443_) {
            if (result.m_82434_() == Direction.UP) {
                this.spawnGroundedEruption(this.m_20185_(), this.m_20189_(), this.m_20186_() - 10.0, this.m_20186_() + 5.0);
                this.m_146870_();
            } else if (this.bounceCooldown <= 0 && !this.hasBounced) {
                Vec3 currentMovement = this.m_20184_();
                Vec3 normal = Vec3.m_82528_((Vec3i)result.m_82434_().m_122436_());
                Vec3 reflection = currentMovement.m_82546_(normal.m_82490_(2.0 * currentMovement.m_82526_(normal)));
                Vec3 newMovement = new Vec3(reflection.f_82479_ * 0.3, Math.min(reflection.f_82480_ * 0.5, -0.1), reflection.f_82481_ * 0.3);
                this.m_20256_(newMovement);
                this.m_20242_(false);
                this.hasBounced = true;
                this.bounceCooldown = 10;
                this.m_5496_(SoundEvents.f_12391_, 0.6f, 1.2f);
            }
        }
    }

    private void spawnGroundedEruption(double x, double z, double minY, double maxY) {
        BlockPos pos = new BlockPos((int)x, (int)maxY, (int)z);
        boolean flag = false;
        double d0 = 0.0;
        do {
            BlockState blockState1;
            VoxelShape voxelShape;
            BlockPos pos1 = pos.m_7495_();
            BlockState blockState = this.m_9236_().m_8055_(pos1);
            if (!blockState.m_60783_((BlockGetter)this.m_9236_(), pos1, Direction.UP)) continue;
            if (!this.m_9236_().m_46859_(pos) && !(voxelShape = (blockState1 = this.m_9236_().m_8055_(pos)).m_60812_((BlockGetter)this.m_9236_(), pos)).m_83281_()) {
                d0 = voxelShape.m_83297_(Direction.Axis.Y);
            }
            flag = true;
            break;
        } while ((pos = pos.m_7495_()).m_123342_() >= Mth.m_14107_((double)minY) - 1);
        if (flag) {
            double finalX = x;
            double finalY = (double)pos.m_123342_() + d0;
            double finalZ = z;
            if (!this.m_9236_().f_46443_) {
                MagicManager.spawnParticles((Level)this.m_9236_(), (ParticleOptions)ParticleTypes.f_123812_, (double)finalX, (double)(finalY + 1.0), (double)finalZ, (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)true);
                MagicManager.spawnParticles((Level)this.m_9236_(), (ParticleOptions)((ParticleOptions)TravelopticsParticles.PURPLE_STAR_OUTWARD_PARTICLE.get()), (double)finalX, (double)(finalY + 0.75), (double)finalZ, (int)25, (double)1.5, (double)0.75, (double)1.5, (double)0.05, (boolean)true);
            }
            this.m_5496_(SoundEvents.f_11913_, 1.0f, 1.0f);
            EndEruptionEntity eruption = new EndEruptionEntity(this.m_9236_());
            eruption.m_6027_(finalX, finalY, finalZ);
            eruption.m_5602_(this.m_19749_());
            eruption.setWindupDuration(30);
            eruption.setRadius(4.0f);
            eruption.setDamage(this.getDamage());
            this.m_9236_().m_7967_((Entity)eruption);
        }
    }

    protected void m_5790_(EntityHitResult hitResult) {
        if (!this.m_9236_().f_46443_ && this.bounceCooldown <= 0 && !this.hasBounced) {
            Vec3 currentMovement = this.m_20184_();
            Entity hitEntity = hitResult.m_82443_();
            Vec3 entityPos = hitEntity.m_20182_();
            Vec3 projectilePos = this.m_20182_();
            Vec3 bounceDirection = projectilePos.m_82546_(entityPos).m_82541_();
            Vec3 newMovement = new Vec3(bounceDirection.f_82479_ * Math.abs(currentMovement.f_82479_) * 0.4 + (Math.random() - 0.5) * 0.2, Math.min(currentMovement.f_82480_ * 0.3, -0.1), bounceDirection.f_82481_ * Math.abs(currentMovement.f_82481_) * 0.4 + (Math.random() - 0.5) * 0.2);
            this.m_20256_(newMovement);
            this.m_20242_(false);
            this.hasBounced = true;
            this.bounceCooldown = 10;
            this.m_5496_(SoundEvents.f_12393_, 0.6f, 1.5f);
        }
    }

    public void trailParticles() {
        int i;
        double x = this.m_20185_();
        double y = this.m_20186_() + 0.1;
        double z = this.m_20189_();
        for (i = 0; i < 3; ++i) {
            double offsetX = (this.f_19796_.m_188500_() - 0.5) * 0.3;
            double offsetY = (this.f_19796_.m_188500_() - 0.5) * 0.3;
            double offsetZ = (this.f_19796_.m_188500_() - 0.5) * 0.3;
            double velX = (this.f_19796_.m_188500_() - 0.5) * 0.02;
            double velY = (this.f_19796_.m_188500_() - 0.5) * 0.02;
            double velZ = (this.f_19796_.m_188500_() - 0.5) * 0.02;
            if (this.isFusing()) {
                if (this.f_19796_.m_188501_() < 0.25f) {
                    this.m_9236_().m_7106_((ParticleOptions)TravelopticsParticles.ABYSS_SPIKE_PARTICLE.get(), x + offsetX, y + offsetY, z + offsetZ, velX, velY, velZ);
                    continue;
                }
                this.m_9236_().m_7106_((ParticleOptions)ParticleTypes.f_123762_, x + offsetX, y + offsetY, z + offsetZ, velX * 0.5, velY * 0.5, velZ * 0.5);
                continue;
            }
            this.m_9236_().m_7106_((ParticleOptions)ParticleTypes.f_123762_, x + offsetX, y + offsetY, z + offsetZ, velX * 0.5, velY * 0.5, velZ * 0.5);
        }
        if (this.m_5842_()) {
            for (i = 0; i < 2; ++i) {
                double bubbleOffsetX = (this.f_19796_.m_188500_() - 0.5) * 0.4;
                double bubbleOffsetY = (this.f_19796_.m_188500_() - 0.5) * 0.2;
                double bubbleOffsetZ = (this.f_19796_.m_188500_() - 0.5) * 0.4;
                double bubbleVelX = (this.f_19796_.m_188500_() - 0.5) * 0.03;
                double bubbleVelY = this.f_19796_.m_188500_() * 0.05;
                double bubbleVelZ = (this.f_19796_.m_188500_() - 0.5) * 0.03;
                this.m_9236_().m_7106_((ParticleOptions)ParticleTypes.f_123795_, x + bubbleOffsetX, y + bubbleOffsetY, z + bubbleOffsetZ, bubbleVelX, bubbleVelY, bubbleVelZ);
            }
        }
    }

    public void impactParticles(double v, double v1, double v2) {
    }

    public float getSpeed() {
        return 0.0f;
    }

    public Optional<SoundEvent> getImpactSound() {
        return Optional.empty();
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController[]{this.controller});
    }

    private PlayState animationPredicate(AnimationState<EruptionBombProjectileEntity> state) {
        if (this.isFusing()) {
            state.getController().setAnimation(FUSE_ANIM);
        } else {
            state.getController().setAnimation(TRAVEL_ANIM);
        }
        return PlayState.CONTINUE;
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}

