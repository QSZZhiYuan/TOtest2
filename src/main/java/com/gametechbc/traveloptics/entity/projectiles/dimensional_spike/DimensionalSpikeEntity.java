/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.BlockParticleOption
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientGamePacketListener
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.RenderShape
 *  net.minecraft.world.level.block.state.BlockState
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
package com.gametechbc.traveloptics.entity.projectiles.dimensional_spike;

import com.gametechbc.traveloptics.init.TravelopticsEntities;
import com.gametechbc.traveloptics.util.TravelopticsTags;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
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

public class DimensionalSpikeEntity
extends Entity
implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    private static final EntityDataAccessor<Boolean> ACTIVATE = SynchedEntityData.m_135353_(DimensionalSpikeEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.m_135353_(DimensionalSpikeEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135029_);
    private static final EntityDataAccessor<Integer> WARMUP_TICKS = SynchedEntityData.m_135353_(DimensionalSpikeEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Boolean> IS_RISING = SynchedEntityData.m_135353_(DimensionalSpikeEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> IS_GOING_DOWN = SynchedEntityData.m_135353_(DimensionalSpikeEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> IS_END_STONE = SynchedEntityData.m_135353_(DimensionalSpikeEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private int warmupDelayTicks;
    private LivingEntity caster;
    private UUID casterUuid;
    private boolean hasDamaged = false;
    private int riseTickCount = 0;
    private boolean hasDetectedBlock = false;
    private static final RawAnimation BLANK_ANIM = RawAnimation.begin().thenLoop("blank");
    private static final RawAnimation SPIKE_RISE_ANIM = RawAnimation.begin().thenPlay("spike_rise");
    private final AnimationController<DimensionalSpikeEntity> controller = new AnimationController((GeoAnimatable)this, "spike_controller", 0, this::animationPredicate);

    public DimensionalSpikeEntity(EntityType<? extends DimensionalSpikeEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public DimensionalSpikeEntity(Level worldIn, double x, double y, double z, float yRot, int warmupDelay, float damage, LivingEntity casterIn) {
        this((EntityType<? extends DimensionalSpikeEntity>)((EntityType)TravelopticsEntities.DIMENSIONAL_SPIKE.get()), worldIn);
        this.warmupDelayTicks = warmupDelay;
        this.setCaster(casterIn);
        this.setDamage(damage);
        this.setWarmupTicks(warmupDelay);
        this.m_146922_(yRot);
        this.m_6034_(x, y, z);
        this.detectBlockBeneath();
    }

    public Packet<ClientGamePacketListener> m_5654_() {
        return NetworkHooks.getEntitySpawningPacket((Entity)this);
    }

    protected void m_8097_() {
        this.f_19804_.m_135372_(ACTIVATE, (Object)false);
        this.f_19804_.m_135372_(DAMAGE, (Object)Float.valueOf(0.0f));
        this.f_19804_.m_135372_(WARMUP_TICKS, (Object)0);
        this.f_19804_.m_135372_(IS_RISING, (Object)false);
        this.f_19804_.m_135372_(IS_GOING_DOWN, (Object)false);
        this.f_19804_.m_135372_(IS_END_STONE, (Object)false);
    }

    protected void m_7380_(CompoundTag tag) {
        tag.m_128405_("Warmup", this.warmupDelayTicks);
        if (this.casterUuid != null) {
            tag.m_128362_("Owner", this.casterUuid);
        }
        tag.m_128350_("damage", this.getDamage());
        tag.m_128379_("isEndStone", this.isEndStone());
    }

    protected void m_7378_(CompoundTag tag) {
        this.warmupDelayTicks = tag.m_128451_("Warmup");
        if (tag.m_128403_("Owner")) {
            this.casterUuid = tag.m_128342_("Owner");
        }
        this.setDamage(tag.m_128457_("damage"));
        if (tag.m_128441_("isEndStone")) {
            this.setEndStone(tag.m_128471_("isEndStone"));
        }
    }

    public float getDamage() {
        return ((Float)this.f_19804_.m_135370_(DAMAGE)).floatValue();
    }

    public void setDamage(float damage) {
        this.f_19804_.m_135381_(DAMAGE, (Object)Float.valueOf(damage));
    }

    public int getWarmupTicks() {
        return (Integer)this.f_19804_.m_135370_(WARMUP_TICKS);
    }

    public void setWarmupTicks(int ticks) {
        this.f_19804_.m_135381_(WARMUP_TICKS, (Object)ticks);
    }

    public void setCaster(@Nullable LivingEntity caster) {
        this.caster = caster;
        this.casterUuid = caster == null ? null : caster.m_20148_();
    }

    @Nullable
    public LivingEntity getCaster() {
        Entity entity;
        if (this.caster == null && this.casterUuid != null && this.m_9236_() instanceof ServerLevel && (entity = ((ServerLevel)this.m_9236_()).m_8791_(this.casterUuid)) instanceof LivingEntity) {
            this.caster = (LivingEntity)entity;
        }
        return this.caster;
    }

    public boolean isActivate() {
        return (Boolean)this.f_19804_.m_135370_(ACTIVATE);
    }

    public void setActivate(boolean activate) {
        this.f_19804_.m_135381_(ACTIVATE, (Object)activate);
    }

    public boolean isRising() {
        return (Boolean)this.f_19804_.m_135370_(IS_RISING);
    }

    public void setRising(boolean rising) {
        this.f_19804_.m_135381_(IS_RISING, (Object)rising);
    }

    public boolean isGoingDown() {
        return (Boolean)this.f_19804_.m_135370_(IS_GOING_DOWN);
    }

    public void setGoingDown(boolean goingDown) {
        this.f_19804_.m_135381_(IS_GOING_DOWN, (Object)goingDown);
    }

    public boolean isEndStone() {
        return (Boolean)this.f_19804_.m_135370_(IS_END_STONE);
    }

    public void setEndStone(boolean endStone) {
        this.f_19804_.m_135381_(IS_END_STONE, (Object)endStone);
    }

    private void detectBlockBeneath() {
        BlockPos posBelow = this.m_20183_().m_7495_();
        BlockState blockBelow = this.m_9236_().m_8055_(posBelow);
        boolean isEndStoneBlock = blockBelow.m_204336_(TravelopticsTags.IS_ENDSTONE_CATEGORY);
        this.setEndStone(isEndStoneBlock);
        this.hasDetectedBlock = true;
    }

    public void m_8119_() {
        super.m_8119_();
        if (this.f_19797_ == 1 && !this.hasDetectedBlock && !this.m_9236_().f_46443_) {
            this.detectBlockBeneath();
        }
        if (this.m_9236_().f_46443_) {
            if (this.isRising()) {
                this.spawnBlockDiggingParticles();
            }
            if (this.isGoingDown()) {
                this.spawnReversePortalParticles();
            }
        } else {
            int currentWarmup = this.getWarmupTicks();
            if (currentWarmup > 0) {
                this.setWarmupTicks(currentWarmup - 1);
            } else if (!this.isActivate()) {
                this.setActivate(true);
                this.setRising(true);
            }
            if (this.isActivate()) {
                ++this.riseTickCount;
                if (this.riseTickCount == 7) {
                    this.setRising(false);
                    if (!this.hasDamaged) {
                        this.dealDamageOnce();
                        this.hasDamaged = true;
                    }
                    this.setGoingDown(true);
                }
                if (this.riseTickCount >= 20) {
                    this.m_146870_();
                }
            }
        }
    }

    private void dealDamageOnce() {
        for (LivingEntity livingentity : this.m_9236_().m_45976_(LivingEntity.class, this.m_20191_().m_82377_(0.2, 0.0, 0.2))) {
            this.damage(livingentity);
        }
    }

    private void damage(LivingEntity hitEntity) {
        LivingEntity livingentity = this.getCaster();
        if (hitEntity.m_6084_() && !hitEntity.m_20147_() && hitEntity != livingentity) {
            if (livingentity == null) {
                hitEntity.m_6469_(this.m_269291_().m_269425_(), this.getDamage());
            } else if (!livingentity.m_7307_((Entity)hitEntity) && !hitEntity.m_7307_((Entity)livingentity)) {
                hitEntity.m_6469_(this.m_269291_().m_269104_((Entity)this, (Entity)livingentity), this.getDamage());
            }
        }
    }

    private void spawnBlockDiggingParticles() {
        for (int i = 0; i < 4; ++i) {
            BlockState block = this.m_9236_().m_8055_(this.m_20183_().m_7495_());
            double d0 = this.m_20185_() + (this.f_19796_.m_188500_() * 2.0 - 1.0) * (double)this.m_20205_() * 0.5;
            double d1 = this.m_20186_() + 0.03;
            double d2 = this.m_20189_() + (this.f_19796_.m_188500_() * 2.0 - 1.0) * (double)this.m_20205_() * 0.5;
            double d3 = this.f_19796_.m_188583_() * 0.07;
            double d4 = this.f_19796_.m_188583_() * 0.07;
            double d5 = this.f_19796_.m_188583_() * 0.07;
            if (block.m_60799_() == RenderShape.INVISIBLE) continue;
            this.m_9236_().m_7106_((ParticleOptions)new BlockParticleOption(ParticleTypes.f_123794_, block), d0, d1, d2, d3, d4, d5);
        }
    }

    private void spawnReversePortalParticles() {
        for (int i = 0; i < 3; ++i) {
            double d0 = this.m_20185_() + (this.f_19796_.m_188500_() * 2.0 - 1.0) * (double)this.m_20205_() * 0.5;
            double d1 = this.m_20186_() + 0.05 + this.f_19796_.m_188500_();
            double d2 = this.m_20189_() + (this.f_19796_.m_188500_() * 2.0 - 1.0) * (double)this.m_20205_() * 0.5;
            double d3 = (this.f_19796_.m_188500_() * 2.0 - 1.0) * 0.3;
            double d4 = 0.3 + this.f_19796_.m_188500_() * 0.3;
            double d5 = (this.f_19796_.m_188500_() * 2.0 - 1.0) * 0.3;
            this.m_9236_().m_7106_((ParticleOptions)ParticleTypes.f_123789_, d0, d1, d2, d3, d4, d5);
        }
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController[]{this.controller});
    }

    private PlayState animationPredicate(AnimationState<DimensionalSpikeEntity> state) {
        if (this.isActivate()) {
            state.getController().setAnimation(SPIKE_RISE_ANIM);
        } else {
            state.getController().setAnimation(BLANK_ANIM);
        }
        return PlayState.CONTINUE;
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}

