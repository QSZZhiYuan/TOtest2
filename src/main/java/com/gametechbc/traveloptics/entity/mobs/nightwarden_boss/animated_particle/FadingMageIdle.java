/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.world.phys.AABB
 */
package com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.animated_particle;

import com.gametechbc.traveloptics.entity.mobs.fading_mage.FadingMageEntity;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.phys.AABB;

public class FadingMageIdle {
    public static void spawnFadingParticles(FadingMageEntity entity, double particleSpeed, double upwardTilt, double sidewaysTilt, double backwardOffset, int particleCount, int nextInt) {
        AABB boundingBox = entity.m_20191_();
        double width = boundingBox.m_82362_();
        double height = boundingBox.m_82376_();
        double depth = boundingBox.m_82385_();
        float entityYRot = entity.m_146908_();
        float flowAngle = entityYRot + (float)backwardOffset;
        double flowRadians = Math.toRadians(flowAngle);
        double baseVelX = -Math.sin(flowRadians) * particleSpeed;
        double baseVelY = upwardTilt;
        double baseVelZ = Math.cos(flowRadians) * particleSpeed;
        if (sidewaysTilt != 0.0) {
            double sidewaysRadians = Math.toRadians((double)entityYRot + 90.0);
            baseVelX += -Math.sin(sidewaysRadians) * sidewaysTilt;
            baseVelZ += Math.cos(sidewaysRadians) * sidewaysTilt;
        }
        int spawnedParticle = entity.m_217043_().m_188503_(nextInt) + particleCount;
        for (int i = 0; i < spawnedParticle; ++i) {
            double offsetX = (entity.m_217043_().m_188500_() - 0.5) * width;
            double offsetY = entity.m_217043_().m_188500_() * height;
            double offsetZ = (entity.m_217043_().m_188500_() - 0.5) * depth;
            double particleX = entity.m_20185_() + offsetX;
            double particleY = entity.m_20186_() + offsetY;
            double particleZ = entity.m_20189_() + offsetZ;
            double velX = baseVelX + (entity.m_217043_().m_188500_() - 0.5) * 0.08;
            double velY = baseVelY + (entity.m_217043_().m_188500_() - 0.5) * 0.06;
            double velZ = baseVelZ + (entity.m_217043_().m_188500_() - 0.5) * 0.08;
            entity.m_9236_().m_7106_((ParticleOptions)ParticleTypes.f_123762_, particleX, particleY, particleZ, velX, velY, velZ);
            if (!(entity.m_217043_().m_188501_() < 0.25f)) continue;
            entity.m_9236_().m_7106_((ParticleOptions)ParticleTypes.f_235898_, particleX + (entity.m_217043_().m_188500_() - 0.5) * 0.2, particleY + (entity.m_217043_().m_188500_() - 0.5) * 0.2, particleZ + (entity.m_217043_().m_188500_() - 0.5) * 0.2, velX * 0.8, velY * 0.9, velZ * 0.8);
        }
    }
}

