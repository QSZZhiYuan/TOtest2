/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.entity.projectile.Flame_Jet_Entity
 *  io.redspace.ironsspellbooks.util.ParticleHelper
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.util.Mth
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectCategory
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.VoxelShape
 */
package com.gametechbc.traveloptics.effects;

import com.gametechbc.traveloptics.api.particle.ParticleDirection;
import com.gametechbc.traveloptics.api.particle.SphereParticleManager;
import com.github.L_Ender.cataclysm.entity.projectile.Flame_Jet_Entity;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FlareVacuumEffect
extends MobEffect {
    public FlareVacuumEffect() {
        super(MobEffectCategory.HARMFUL, 0);
    }

    public void m_6742_(LivingEntity entity, int amplifier) {
        MobEffectInstance effectInstance = entity.m_21124_((MobEffect)this);
        if (effectInstance != null) {
            int duration = effectInstance.m_19557_();
            if (duration > 2 && !entity.m_9236_().f_46443_) {
                SphereParticleManager.spawnParticles(entity.m_9236_(), (Entity)entity, 2, ParticleHelper.EMBERS, ParticleDirection.INWARD, 2.0);
            }
            if (duration == 2) {
                if (Math.random() < 0.5) {
                    this.xStrikeRune(entity, 4, 2.0, amplifier);
                } else {
                    this.plusStrikeRune(entity, 4, 2.0, amplifier);
                }
            }
            if (duration < 4 && !entity.m_9236_().f_46443_) {
                SphereParticleManager.spawnParticles(entity.m_9236_(), (Entity)entity, 30, ParticleHelper.EMBERS, ParticleDirection.OUTWARD, 6.0);
            }
        }
        List nearbyEntities = entity.m_9236_().m_6443_(LivingEntity.class, entity.m_20191_().m_82400_(3.0), e -> e != entity && e.m_21023_((MobEffect)this));
        for (LivingEntity target : nearbyEntities) {
            this.pullEntityTowards(entity, target);
        }
    }

    private void pullEntityTowards(LivingEntity source, LivingEntity target) {
        Vec3 sourcePos = source.m_20182_();
        Vec3 targetPos = target.m_20182_();
        Vec3 direction = sourcePos.m_82546_(targetPos).m_82541_();
        double strength = 0.012;
        Vec3 pullForce = direction.m_82490_(strength);
        target.m_20256_(target.m_20184_().m_82549_(pullForce));
        target.f_19864_ = true;
    }

    private void plusStrikeRune(LivingEntity entity, int rune, double time, int amplifier) {
        for (int i = 0; i < 4; ++i) {
            float yawRadians = (float)Math.toRadians(90.0f + entity.m_146908_());
            float throwAngle = yawRadians + (float)i * (float)Math.PI / 2.0f;
            for (int k = 0; k < rune; ++k) {
                double distance = 0.8 * (double)(k + 1);
                int delay = (int)(time * (double)(k + 1));
                this.spawnFangs(entity, entity.m_20185_() + (double)Mth.m_14089_((float)throwAngle) * 1.25 * distance, entity.m_20189_() + (double)Mth.m_14031_((float)throwAngle) * 1.25 * distance, entity.m_20186_() - 2.0, entity.m_20186_() + 2.0, throwAngle, delay, amplifier);
            }
        }
    }

    private void xStrikeRune(LivingEntity entity, int rune, double time, int amplifier) {
        for (int i = 0; i < 4; ++i) {
            float yawRadians = (float)Math.toRadians(45.0f + entity.m_146908_());
            float throwAngle = yawRadians + (float)i * (float)Math.PI / 2.0f;
            for (int k = 0; k < rune; ++k) {
                double distance = 0.8 * (double)(k + 1);
                int delay = (int)(time * (double)(k + 1));
                this.spawnFangs(entity, entity.m_20185_() + (double)Mth.m_14089_((float)throwAngle) * 1.25 * distance, entity.m_20189_() + (double)Mth.m_14031_((float)throwAngle) * 1.25 * distance, entity.m_20186_() - 2.0, entity.m_20186_() + 2.0, throwAngle, delay, amplifier);
            }
        }
    }

    private void spawnFangs(LivingEntity entity, double x, double z, double minY, double maxY, float rotation, int delay, int amplifier) {
        BlockPos blockPos = BlockPos.m_274561_((double)x, (double)maxY, (double)z);
        boolean flag = false;
        double d0 = 0.0;
        do {
            BlockState blockState1;
            VoxelShape voxelShape;
            BlockPos blockPosBelow = blockPos.m_7495_();
            BlockState blockState = entity.m_9236_().m_8055_(blockPosBelow);
            if (!blockState.m_60783_((BlockGetter)entity.m_9236_(), blockPosBelow, Direction.UP)) continue;
            if (!entity.m_9236_().m_46859_(blockPos) && !(voxelShape = (blockState1 = entity.m_9236_().m_8055_(blockPos)).m_60812_((BlockGetter)entity.m_9236_(), blockPos)).m_83281_()) {
                d0 = voxelShape.m_83297_(Direction.Axis.Y);
            }
            flag = true;
            break;
        } while ((blockPos = blockPos.m_7495_()).m_123342_() >= Mth.m_14107_((double)minY) - 1);
        if (flag) {
            entity.m_9236_().m_7967_((Entity)new Flame_Jet_Entity(entity.m_9236_(), x, (double)blockPos.m_123342_() + d0, z, rotation, delay, this.getFlameJetDamage(amplifier), null));
        }
    }

    private float getFlameJetDamage(int amplifier) {
        return amplifier;
    }

    public boolean m_6584_(int duration, int amplifier) {
        return true;
    }
}

