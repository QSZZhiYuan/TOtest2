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

import com.gametechbc.traveloptics.api.particle.ParticleDirection;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import java.util.Random;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class CircleParticleManager {
    private static final Random RANDOM = new Random();

    public static void spawnParticles(Level level, LivingEntity entity, int particleCount, ParticleOptions particleType, ParticleDirection direction, double parameter, boolean force) {
        if (!level.f_46443_) {
            double centerY = entity.m_20186_();
            for (int i = 0; i < particleCount; ++i) {
                Vec3 directionVector;
                double theta = Math.PI * 2 * RANDOM.nextDouble();
                double xOffset = Math.cos(theta);
                double zOffset = Math.sin(theta);
                double yOffset = 0.0;
                if (direction == ParticleDirection.INWARD) {
                    directionVector = new Vec3(entity.m_20185_() - (entity.m_20185_() + (xOffset *= parameter)), 0.0, entity.m_20189_() - (entity.m_20189_() + (zOffset *= parameter))).m_82541_();
                    MagicManager.spawnParticles((Level)level, (ParticleOptions)particleType, (double)(entity.m_20185_() + xOffset), (double)centerY, (double)(entity.m_20189_() + zOffset), (int)0, (double)directionVector.f_82479_, (double)0.0, (double)directionVector.f_82481_, (double)0.1, (boolean)force);
                    continue;
                }
                directionVector = new Vec3(xOffset, yOffset, zOffset).m_82541_().m_82490_(parameter);
                MagicManager.spawnParticles((Level)level, (ParticleOptions)particleType, (double)entity.m_20185_(), (double)centerY, (double)entity.m_20189_(), (int)0, (double)directionVector.f_82479_, (double)0.0, (double)directionVector.f_82481_, (double)0.1, (boolean)force);
            }
        }
    }

    public static void spawnParticles(Level level, Vec3 centerPosition, int particleCount, ParticleOptions particleType, ParticleDirection direction, double parameter, boolean force) {
        if (!level.f_46443_) {
            double centerX = centerPosition.f_82479_;
            double centerY = centerPosition.f_82480_;
            double centerZ = centerPosition.f_82481_;
            for (int i = 0; i < particleCount; ++i) {
                Vec3 directionVector;
                double theta = Math.PI * 2 * RANDOM.nextDouble();
                double xOffset = Math.cos(theta);
                double zOffset = Math.sin(theta);
                double yOffset = 0.0;
                if (direction == ParticleDirection.INWARD) {
                    directionVector = new Vec3(centerX - (centerX + (xOffset *= parameter)), 0.0, centerZ - (centerZ + (zOffset *= parameter))).m_82541_();
                    MagicManager.spawnParticles((Level)level, (ParticleOptions)particleType, (double)(centerX + xOffset), (double)centerY, (double)(centerZ + zOffset), (int)0, (double)directionVector.f_82479_, (double)0.0, (double)directionVector.f_82481_, (double)0.1, (boolean)force);
                    continue;
                }
                directionVector = new Vec3(xOffset, yOffset, zOffset).m_82541_().m_82490_(parameter);
                MagicManager.spawnParticles((Level)level, (ParticleOptions)particleType, (double)centerX, (double)centerY, (double)centerZ, (int)0, (double)directionVector.f_82479_, (double)0.0, (double)directionVector.f_82481_, (double)0.1, (boolean)force);
            }
        }
    }

    public static void spawnParticles(Level level, LivingEntity entity, Vec3 offset, int particleCount, ParticleOptions particleType, ParticleDirection direction, double parameter, boolean force) {
        Vec3 centerPosition = entity.m_20182_().m_82549_(offset);
        CircleParticleManager.spawnParticles(level, centerPosition, particleCount, particleType, direction, parameter, force);
    }
}

