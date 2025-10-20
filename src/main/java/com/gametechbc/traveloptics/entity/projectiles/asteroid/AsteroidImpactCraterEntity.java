/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.damage.DamageSources
 *  io.redspace.ironsspellbooks.entity.spells.AoeEntity
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityDimensions
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.entity.projectile.Projectile
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 */
package com.gametechbc.traveloptics.entity.projectiles.asteroid;

import com.gametechbc.traveloptics.entity.misc.TOScreenShakeEntity;
import com.gametechbc.traveloptics.init.TravelopticsEntities;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import com.gametechbc.traveloptics.util.TravelopticsDamageTypes;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AoeEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class AsteroidImpactCraterEntity
extends AoeEntity {
    private DamageSource damageSource;

    public AsteroidImpactCraterEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public AsteroidImpactCraterEntity(Level level) {
        this((EntityType<? extends Projectile>)((EntityType)TravelopticsEntities.ASTEROID_IMPACT_CRATER.get()), level);
    }

    public void applyEffect(LivingEntity target) {
        if (this.damageSource == null) {
            this.damageSource = new DamageSource(DamageSources.getHolderFromResource((Entity)target, TravelopticsDamageTypes.ASTEROID_IMPACT_CRATER), (Entity)this, this.m_19749_());
        }
        DamageSources.ignoreNextKnockback((LivingEntity)target);
        target.m_6469_(this.damageSource, this.getDamage());
        target.m_20254_(7);
    }

    public EntityDimensions m_6972_(Pose pPose) {
        return EntityDimensions.m_20395_((float)(this.getRadius() * 1.5f), (float)(this.getRadius() * 1.5f));
    }

    public void m_8119_() {
        super.m_8119_();
        if (this.f_19797_ == 1 && !this.m_9236_().f_46443_) {
            this.m_5496_((SoundEvent)TravelopticsSounds.TECTONIC_RIFT_ERUPTION.get(), 4.0f, 1.0f);
            TOScreenShakeEntity.createScreenShake(this.m_9236_(), this.m_20182_(), 32.0f, 0.02f, 180, 15, 20, true);
        }
        if (!this.m_9236_().f_46443_ && this.f_19797_ >= 200) {
            this.m_146870_();
        }
        if (this.m_9236_().f_46443_) {
            double radius = this.getRadius();
            double innerRadius = 5.0;
            for (int i = 0; i < 15; ++i) {
                double angle = this.m_9236_().f_46441_.m_188500_() * 2.0 * Math.PI;
                double distance = innerRadius + this.m_9236_().f_46441_.m_188500_() * (radius - innerRadius);
                double xOffset = Math.cos(angle) * distance;
                double zOffset = Math.sin(angle) * distance;
                double x = this.m_20185_() + xOffset;
                double y = this.m_20186_() + this.m_9236_().f_46441_.m_188500_();
                double z = this.m_20189_() + zOffset;
                double motionX = (this.m_9236_().f_46441_.m_188500_() - 0.5) * 0.4;
                double motionY = 0.3 + this.m_9236_().f_46441_.m_188500_() * 0.4;
                double motionZ = (this.m_9236_().f_46441_.m_188500_() - 0.5) * 0.4;
                SimpleParticleType particle = switch (this.m_9236_().f_46441_.m_188503_(3)) {
                    case 0 -> (SimpleParticleType)ParticleTypes.f_123756_;
                    case 1 -> (SimpleParticleType)ParticleTypes.f_123756_;
                    case 2 -> (SimpleParticleType)ParticleTypes.f_123756_;
                    default -> (SimpleParticleType)ParticleTypes.f_123756_;
                };
                this.m_9236_().m_6485_((ParticleOptions)particle, true, x, y, z, motionX, motionY, motionZ);
            }
        }
    }

    private BlockState getBestReplacementBlock(BlockPos pos) {
        ArrayList<BlockState> nearbyBlocks = new ArrayList<BlockState>();
        for (int dx = -1; dx <= 1; ++dx) {
            for (int dy = -2; dy <= 1; ++dy) {
                for (int dz = -1; dz <= 1; ++dz) {
                    BlockPos nearbyPos = pos.m_7918_(dx, dy, dz);
                    BlockState nearbyState = this.m_9236_().m_8055_(nearbyPos);
                    if (nearbyState.m_60795_() || nearbyState.m_60713_((Block)Blocks.f_50067_)) continue;
                    nearbyBlocks.add(nearbyState);
                }
            }
        }
        return this.getMostCommonBlock(nearbyBlocks);
    }

    private BlockState getMostCommonBlock(List<BlockState> blockStates) {
        if (blockStates.isEmpty()) {
            return Blocks.f_50069_.m_49966_();
        }
        HashMap<BlockState, Integer> frequencyMap = new HashMap<BlockState, Integer>();
        for (BlockState state : blockStates) {
            frequencyMap.put(state, frequencyMap.getOrDefault(state, 0) + 1);
        }
        return (BlockState)Collections.max(frequencyMap.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public float getParticleCount() {
        return 0.15f;
    }

    public Optional<ParticleOptions> getParticle() {
        return Optional.empty();
    }
}

