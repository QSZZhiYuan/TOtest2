/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.api.magic.MagicData
 *  io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible
 *  javax.annotation.Nullable
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.util.Mth
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.shapes.VoxelShape
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
package com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.nightwarden_clone.nightwarden_slam_clone;

import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.misc.NightwardenScytheSlamAnimatedParticle;
import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.nightwarden_clone.NightwardenCloneBase;
import com.gametechbc.traveloptics.entity.projectiles.dimensional_spike.DimensionalSpikeEntity;
import com.gametechbc.traveloptics.init.TravelopticsEntities;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class NightwardenSlamCloneEntity
extends NightwardenCloneBase
implements GeoEntity,
AntiMagicSusceptible {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    private static final EntityDataAccessor<Float> DATA_LINES = SynchedEntityData.m_135353_(NightwardenSlamCloneEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135029_);
    private static final EntityDataAccessor<Float> DATA_DAMAGE = SynchedEntityData.m_135353_(NightwardenSlamCloneEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135029_);
    private static final EntityDataAccessor<Boolean> DATA_SPAWNS_RUNES = SynchedEntityData.m_135353_(NightwardenSlamCloneEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private boolean playSoundOnRuneSpawn = false;
    private final RawAnimation EXPLODE_ANIMATION = RawAnimation.begin().thenPlay("nightwarden_clone_scythe_slam");
    private final AnimationController<NightwardenSlamCloneEntity> controller = new AnimationController((GeoAnimatable)this, "nightwarden_clone_slam_controller", 0, this::animationPredicate);

    public NightwardenSlamCloneEntity(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public NightwardenSlamCloneEntity(Level level, LivingEntity entityToCopy) {
        this((EntityType<? extends LivingEntity>)((EntityType)TravelopticsEntities.NIGHTWARDEN_SLAM_CLONE.get()), level);
        this.m_7678_(entityToCopy.m_20185_(), entityToCopy.m_20186_(), entityToCopy.m_20189_(), entityToCopy.m_146908_(), entityToCopy.m_146909_());
        this.m_5618_(entityToCopy.f_20883_);
        this.f_20884_ = this.f_20883_;
        this.m_5616_(entityToCopy.m_6080_());
        this.f_20886_ = this.f_20885_;
        this.setSummoner(entityToCopy);
    }

    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(DATA_LINES, (Object)Float.valueOf(10.0f));
        this.f_19804_.m_135372_(DATA_DAMAGE, (Object)Float.valueOf(5.0f));
        this.f_19804_.m_135372_(DATA_SPAWNS_RUNES, (Object)false);
    }

    @Override
    public void m_7380_(CompoundTag tag) {
        super.m_7380_(tag);
        tag.m_128350_("Lines", this.getLines());
        tag.m_128350_("Damage", this.getDamage());
        tag.m_128379_("SpawnsRunes", this.doesSpawnRunes());
        tag.m_128379_("PlaySoundOnRuneSpawn", this.playSoundOnRuneSpawn);
    }

    @Override
    public void m_7378_(CompoundTag tag) {
        super.m_7378_(tag);
        if (tag.m_128441_("Lines")) {
            this.setLines(tag.m_128457_("Lines"));
        }
        if (tag.m_128441_("Damage")) {
            this.setDamage(tag.m_128457_("Damage"));
        }
        if (tag.m_128441_("SpawnsRunes")) {
            this.setSpawnsRunes(tag.m_128471_("SpawnsRunes"));
        }
        if (tag.m_128441_("PlaySoundOnRuneSpawn")) {
            this.playSoundOnRuneSpawn = tag.m_128471_("PlaySoundOnRuneSpawn");
        }
    }

    public void setPlaySoundOnRuneSpawn(boolean play) {
        this.playSoundOnRuneSpawn = play;
    }

    public boolean shouldPlaySoundOnRuneSpawn() {
        return this.playSoundOnRuneSpawn;
    }

    public void setSpawnsRunes(boolean spawns) {
        this.f_19804_.m_135381_(DATA_SPAWNS_RUNES, (Object)spawns);
    }

    public boolean doesSpawnRunes() {
        return (Boolean)this.f_19804_.m_135370_(DATA_SPAWNS_RUNES);
    }

    public void setLines(float lines) {
        this.f_19804_.m_135381_(DATA_LINES, (Object)Float.valueOf(lines));
    }

    public float getLines() {
        return ((Float)this.f_19804_.m_135370_(DATA_LINES)).floatValue();
    }

    public void setDamage(float damage) {
        this.f_19804_.m_135381_(DATA_DAMAGE, (Object)Float.valueOf(damage));
    }

    public float getDamage() {
        return ((Float)this.f_19804_.m_135370_(DATA_DAMAGE)).floatValue();
    }

    public void m_8119_() {
        super.m_8119_();
        NightwardenScytheSlamAnimatedParticle.spawnScytheSwingParticles(this, this.f_19797_, 26, 33);
        if (this.f_19797_ == 33 && this.doesSpawnRunes()) {
            LivingEntity summoner = this.getSummoner();
            if (this.shouldPlaySoundOnRuneSpawn()) {
                this.m_5496_((SoundEvent)TravelopticsSounds.NIGHTWARDEN_BIG_SLAM_CLONES.get(), 1.0f, 1.0f);
            }
            if (!this.m_9236_().m_5776_()) {
                int runeCount = Mth.m_14143_((float)this.getLines());
                float spacing = 1.2f;
                float initialOffset = 6.0f;
                float yawRad = (float)Math.toRadians(this.m_146908_());
                double dx = -Mth.m_14031_((float)yawRad);
                double dz = Mth.m_14089_((float)yawRad);
                for (int i = 0; i < runeCount; ++i) {
                    double distance = initialOffset + (float)i * spacing;
                    double x = this.m_20185_() + dx * distance;
                    double z = this.m_20189_() + dz * distance;
                    double y = this.m_20186_();
                    int warmup = i * 2;
                    this.spawnDimensionalSpike(x, y, z, this.m_146908_(), warmup, this.m_9236_(), summoner, this.getDamage());
                }
            }
        }
        if (this.f_19797_ >= 50) {
            this.m_146870_();
        }
    }

    private boolean spawnDimensionalSpike(double x, double y, double z, float yRot, int warmupDelayTicks, Level world, LivingEntity summoner, float damage) {
        BlockPos blockpos = BlockPos.m_274561_((double)x, (double)y, (double)z);
        boolean foundGround = false;
        double surfaceY = 0.0;
        while (blockpos.m_123342_() > world.m_141937_()) {
            BlockPos below = blockpos.m_7495_();
            BlockState stateBelow = world.m_8055_(below);
            if (stateBelow.m_60783_((BlockGetter)world, below, Direction.UP)) {
                BlockState stateAt;
                VoxelShape shape;
                if (!world.m_46859_(blockpos) && !(shape = (stateAt = world.m_8055_(blockpos)).m_60812_((BlockGetter)world, blockpos)).m_83281_()) {
                    surfaceY = shape.m_83297_(Direction.Axis.Y);
                }
                foundGround = true;
                break;
            }
            blockpos = below;
        }
        if (foundGround) {
            double finalY = (double)blockpos.m_123342_() + surfaceY;
            DimensionalSpikeEntity spike = new DimensionalSpikeEntity(world, x, finalY, z, yRot, warmupDelayTicks, damage, summoner);
            world.m_7967_((Entity)spike);
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

    public boolean m_6469_(DamageSource pSource, float pAmount) {
        return false;
    }

    private PlayState animationPredicate(AnimationState<NightwardenSlamCloneEntity> event) {
        event.getController().setAnimation(this.EXPLODE_ANIMATION);
        return PlayState.CONTINUE;
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController[]{this.controller});
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}

