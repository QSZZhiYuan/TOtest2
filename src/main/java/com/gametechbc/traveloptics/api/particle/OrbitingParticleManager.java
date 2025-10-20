/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.Level
 */
package com.gametechbc.traveloptics.api.particle;

import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class OrbitingParticleManager {
    public static void spawnOrbitingParticle(Entity entity, ParticleOptions particle, float rotationSpeed, double radius, double yOffset, OrbitDirection direction, boolean force, boolean magicManagerMode) {
        Level level = entity.m_9236_();
        float time = level.m_46467_() % 360L;
        float angle = time * rotationSpeed * ((float)Math.PI / 180);
        if (direction == OrbitDirection.COUNTERCLOCKWISE) {
            angle = -angle;
        }
        double offsetX = Math.cos(angle) * radius;
        double offsetZ = Math.sin(angle) * radius;
        double posY = entity.m_20186_() + yOffset;
        double x = entity.m_20185_() + offsetX;
        double z = entity.m_20189_() + offsetZ;
        if (magicManagerMode) {
            MagicManager.spawnParticles((Level)level, (ParticleOptions)particle, (double)x, (double)posY, (double)z, (int)0, (double)0.0, (double)0.0, (double)0.0, (double)0.05, (boolean)force);
        } else {
            level.m_6493_(particle, force, x, posY, z, 0.0, 0.0, 0.0);
        }
    }

    public static enum OrbitDirection {
        CLOCKWISE,
        COUNTERCLOCKWISE;

    }
}

