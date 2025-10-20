/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 */
package com.gametechbc.traveloptics.api.particle;

import com.gametechbc.traveloptics.api.particle.ParticleDirection;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import java.util.Random;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class AdvancedLineParticleManager {
    private static final Random RANDOM = new Random();

    public static void spawnParticles(Level level, Entity entity, int particleCount, ParticleOptions particleType, ParticleDirection direction, double length, double height, double frontOffset, double speed, boolean force) {
        if (!level.f_46443_) {
            Vec3 lookVector = entity.m_20154_().m_82541_();
            Vec3 rightVector = new Vec3(-lookVector.f_82481_, 0.0, lookVector.f_82479_).m_82541_();
            Vec3 startPosition = entity.m_20182_().m_82549_(lookVector.m_82490_(frontOffset));
            double halfLength = length / 2.0;
            Vec3 lineStart = startPosition.m_82546_(rightVector.m_82490_(halfLength));
            Vec3 lineEnd = startPosition.m_82549_(rightVector.m_82490_(halfLength));
            for (int i = 0; i < particleCount; ++i) {
                double t = RANDOM.nextDouble();
                Vec3 spawnPos = lineStart.m_165921_(lineEnd, t);
                double yPosition = spawnPos.f_82480_ + RANDOM.nextDouble() * height;
                Vec3 directionVector = direction == ParticleDirection.UPWARD ? new Vec3(0.0, 1.0, 0.0).m_82541_() : (direction == ParticleDirection.DOWNWARD ? new Vec3(0.0, -1.0, 0.0).m_82541_() : new Vec3(0.0, 0.0, 0.0));
                directionVector = directionVector.m_82490_(speed);
                MagicManager.spawnParticles((Level)level, (ParticleOptions)particleType, (double)spawnPos.f_82479_, (double)yPosition, (double)spawnPos.f_82481_, (int)0, (double)directionVector.f_82479_, (double)directionVector.f_82480_, (double)directionVector.f_82481_, (double)0.1, (boolean)force);
            }
        }
    }
}

