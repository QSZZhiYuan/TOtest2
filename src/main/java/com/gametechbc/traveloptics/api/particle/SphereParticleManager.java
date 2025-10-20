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

public abstract class SphereParticleManager {
    private static final Random RANDOM = new Random();

    public static void spawnParticles(Level level, Entity entity, int particleCount, ParticleOptions particleType, ParticleDirection direction, double parameter) {
        if (!level.f_46443_) {
            double centerY = entity.m_20186_() + (double)entity.m_20206_() * 0.5;
            for (int i = 0; i < particleCount; ++i) {
                Vec3 directionVector;
                double theta = Math.PI * 2 * RANDOM.nextDouble();
                double phi = Math.acos(2.0 * RANDOM.nextDouble() - 1.0);
                double xOffset = Math.sin(phi) * Math.cos(theta);
                double yOffset = Math.sin(phi) * Math.sin(theta);
                double zOffset = Math.cos(phi);
                if (direction == ParticleDirection.INWARD) {
                    directionVector = new Vec3(entity.m_20185_() - (entity.m_20185_() + (xOffset *= parameter)), centerY - (centerY + (yOffset *= parameter)), entity.m_20189_() - (entity.m_20189_() + (zOffset *= parameter))).m_82541_();
                    MagicManager.spawnParticles((Level)level, (ParticleOptions)particleType, (double)(entity.m_20185_() + xOffset), (double)(centerY + yOffset), (double)(entity.m_20189_() + zOffset), (int)0, (double)directionVector.f_82479_, (double)directionVector.f_82480_, (double)directionVector.f_82481_, (double)0.1, (boolean)true);
                    continue;
                }
                directionVector = new Vec3(xOffset, yOffset, zOffset).m_82541_().m_82490_(parameter);
                MagicManager.spawnParticles((Level)level, (ParticleOptions)particleType, (double)entity.m_20185_(), (double)centerY, (double)entity.m_20189_(), (int)0, (double)directionVector.f_82479_, (double)directionVector.f_82480_, (double)directionVector.f_82481_, (double)0.1, (boolean)true);
            }
        }
    }
}

