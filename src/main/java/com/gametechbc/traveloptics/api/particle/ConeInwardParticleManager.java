/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 */
package com.gametechbc.traveloptics.api.particle;

import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import java.util.Random;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class ConeInwardParticleManager {
    private static final Random RANDOM = new Random();

    public static void spawnInwardConeParticles(Level level, LivingEntity entity, double distance, int particleCount, double xOffset, double yOffset, double zOffset, ParticleOptions particleType) {
        if (!level.f_46443_) {
            Vec3 lookVec = entity.m_20154_().m_82541_();
            double yOffsetAdjustment = (double)entity.m_20206_() * 0.5 + yOffset;
            for (int i = 0; i < particleCount; ++i) {
                double angle = RANDOM.nextDouble() * 2.0 * Math.PI;
                double heightFactor = RANDOM.nextDouble();
                double radius = (1.0 - heightFactor) * 1.5;
                double xConeOffset = radius * Math.cos(angle);
                double zConeOffset = radius * Math.sin(angle);
                double yConeOffset = (heightFactor - 0.5) * 2.0;
                Vec3 particleBasePos = entity.m_20182_().m_82549_(lookVec.m_82490_(distance)).m_82549_(new Vec3(xConeOffset + xOffset, yConeOffset + yOffsetAdjustment, zConeOffset + zOffset));
                Vec3 casterPosAdjusted = entity.m_20182_().m_82520_(0.0, yOffsetAdjustment, 0.0);
                Vec3 directionVec = casterPosAdjusted.m_82546_(particleBasePos).m_82541_();
                MagicManager.spawnParticles((Level)level, (ParticleOptions)particleType, (double)particleBasePos.f_82479_, (double)particleBasePos.f_82480_, (double)particleBasePos.f_82481_, (int)0, (double)directionVec.f_82479_, (double)directionVec.f_82480_, (double)directionVec.f_82481_, (double)0.1, (boolean)true);
            }
        }
    }
}

