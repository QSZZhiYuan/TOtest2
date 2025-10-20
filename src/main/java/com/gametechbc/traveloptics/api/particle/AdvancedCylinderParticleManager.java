/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 */
package com.gametechbc.traveloptics.api.particle;

import com.gametechbc.traveloptics.api.particle.ParticleDirection;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import java.util.Random;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class AdvancedCylinderParticleManager {
    private static final Random RANDOM = new Random();

    public static void spawnParticles(Level level, Vec3 position, int particleCount, ParticleOptions particleType, ParticleDirection direction, double radius, double height, double xOffset, double yOffset, double zOffset, double speed, boolean force) {
        if (!level.f_46443_) {
            double baseX = position.m_7096_() + xOffset;
            double baseY = position.m_7098_() + yOffset;
            double baseZ = position.m_7094_() + zOffset;
            for (int i = 0; i < particleCount; ++i) {
                double theta = Math.PI * 2 * RANDOM.nextDouble();
                double yPosition = baseY + RANDOM.nextDouble() * height;
                double xParticleOffset = radius * Math.cos(theta);
                double zParticleOffset = radius * Math.sin(theta);
                Vec3 directionVector = direction == ParticleDirection.UPWARD ? new Vec3(0.0, 1.0, 0.0).m_82541_() : (direction == ParticleDirection.DOWNWARD ? new Vec3(0.0, -1.0, 0.0).m_82541_() : (direction == ParticleDirection.INWARD ? new Vec3(-xParticleOffset, 0.0, -zParticleOffset).m_82541_() : new Vec3(xParticleOffset, 0.0, zParticleOffset).m_82541_()));
                directionVector = directionVector.m_82490_(speed);
                MagicManager.spawnParticles((Level)level, (ParticleOptions)particleType, (double)(baseX + xParticleOffset), (double)yPosition, (double)(baseZ + zParticleOffset), (int)0, (double)directionVector.f_82479_, (double)directionVector.f_82480_, (double)directionVector.f_82481_, (double)0.1, (boolean)force);
            }
        }
    }
}

