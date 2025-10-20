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

public class FadingMageDespawnSequence {
    public static void spawnDespawnTornado(FadingMageEntity fadingMage, int despawnTimer, int maxDespawnTime) {
        AABB boundingBox = fadingMage.m_20191_();
        double width = boundingBox.m_82362_();
        double height = boundingBox.m_82376_();
        double despawnProgress = 1.0 - (double)despawnTimer / (double)maxDespawnTime;
        FadingMageDespawnSequence.spawnFallingPetals(fadingMage, width, height, despawnProgress);
        FadingMageDespawnSequence.spawnAscendingSouls(fadingMage, width, height, despawnProgress);
        if (despawnProgress > 0.7) {
            FadingMageDespawnSequence.spawnEtherealFade(fadingMage, width, height, despawnProgress);
        }
    }

    private static void spawnFallingPetals(FadingMageEntity fadingMage, double width, double height, double despawnProgress) {
        int petalCount = (int)(1.0 + despawnProgress * 3.0);
        for (int i = 0; i < petalCount; ++i) {
            double petalX = fadingMage.m_20185_() + (fadingMage.m_217043_().m_188500_() - 0.5) * width * 2.0;
            double petalY = fadingMage.m_20186_() + height * 0.8 + fadingMage.m_217043_().m_188500_() * height * 0.5;
            double petalZ = fadingMage.m_20189_() + (fadingMage.m_217043_().m_188500_() - 0.5) * width * 2.0;
            double driftX = (fadingMage.m_217043_().m_188500_() - 0.5) * 0.03;
            double fallSpeed = -0.05 - fadingMage.m_217043_().m_188500_() * 0.08;
            double driftZ = (fadingMage.m_217043_().m_188500_() - 0.5) * 0.03;
            double swayOffset = Math.sin((double)fadingMage.f_19797_ * 0.1 + (double)i) * 0.02;
            driftX += swayOffset;
            driftZ += Math.cos((double)fadingMage.f_19797_ * 0.1 + (double)i) * 0.02;
            if (!(fadingMage.m_217043_().m_188501_() < 0.85f)) continue;
            fadingMage.m_9236_().m_7106_((ParticleOptions)ParticleTypes.f_123762_, petalX, petalY, petalZ, driftX, fallSpeed, driftZ);
        }
    }

    private static void spawnAscendingSouls(FadingMageEntity fadingMage, double width, double height, double despawnProgress) {
        double soulChance = 0.04 + despawnProgress * 0.12;
        if ((double)fadingMage.m_217043_().m_188501_() < soulChance) {
            int soulCount = 1 + (int)(despawnProgress * 1.0);
            for (int i = 0; i < soulCount; ++i) {
                double soulX = fadingMage.m_20185_() + (fadingMage.m_217043_().m_188500_() - 0.5) * width * 0.8;
                double soulY = fadingMage.m_20186_() + fadingMage.m_217043_().m_188500_() * height * 0.6;
                double soulZ = fadingMage.m_20189_() + (fadingMage.m_217043_().m_188500_() - 0.5) * width * 0.8;
                double wanderX = (fadingMage.m_217043_().m_188500_() - 0.5) * 0.02;
                double riseSpeed = 0.08 + fadingMage.m_217043_().m_188500_() * 0.06;
                double wanderZ = (fadingMage.m_217043_().m_188500_() - 0.5) * 0.02;
                if (fadingMage.m_217043_().m_188501_() < 0.3f) {
                    double spiralAngle = (double)fadingMage.f_19797_ * 0.05 + (double)i;
                    wanderX += Math.cos(spiralAngle) * 0.015;
                    wanderZ += Math.sin(spiralAngle) * 0.015;
                }
                fadingMage.m_9236_().m_7106_((ParticleOptions)ParticleTypes.f_235898_, soulX, soulY, soulZ, wanderX, riseSpeed, wanderZ);
            }
        }
    }

    private static void spawnEtherealFade(FadingMageEntity fadingMage, double width, double height, double despawnProgress) {
        double fadeIntensity = (despawnProgress - 0.7) / 0.3;
        int fadeParticles = (int)(1.0 + fadeIntensity * 2.0);
        for (int i = 0; i < fadeParticles; ++i) {
            double angle = fadingMage.m_217043_().m_188500_() * Math.PI * 2.0;
            double radius = fadingMage.m_217043_().m_188500_() * width * 0.6;
            double fadeHeight = fadingMage.m_217043_().m_188500_() * height;
            double fadeX = fadingMage.m_20185_() + Math.cos(angle) * radius;
            double fadeY = fadingMage.m_20186_() + fadeHeight;
            double fadeZ = fadingMage.m_20189_() + Math.sin(angle) * radius;
            double velX = Math.cos(angle) * 0.01 + (fadingMage.m_217043_().m_188500_() - 0.5) * 0.02;
            double velY = 0.02 + fadingMage.m_217043_().m_188500_() * 0.03;
            double velZ = Math.sin(angle) * 0.01 + (fadingMage.m_217043_().m_188500_() - 0.5) * 0.02;
            if (fadingMage.m_217043_().m_188501_() < 0.6f) {
                fadingMage.m_9236_().m_7106_((ParticleOptions)ParticleTypes.f_235898_, fadeX, fadeY, fadeZ, velX, velY, velZ);
                continue;
            }
            fadingMage.m_9236_().m_7106_((ParticleOptions)ParticleTypes.f_123762_, fadeX, fadeY, fadeZ, velX * 0.5, velY * 0.7, velZ * 0.5);
        }
        if (despawnProgress > 0.95) {
            int finalBurstCount = 3 + fadingMage.m_217043_().m_188503_(2);
            for (int i = 0; i < finalBurstCount; ++i) {
                double burstAngle = Math.PI * 2 * (double)i / (double)finalBurstCount + fadingMage.m_217043_().m_188500_() * 0.5;
                double burstRadius = width * 0.4 + fadingMage.m_217043_().m_188500_() * width * 0.6;
                double burstX = fadingMage.m_20185_() + Math.cos(burstAngle) * burstRadius;
                double burstY = fadingMage.m_20186_() + height * 0.5 + (fadingMage.m_217043_().m_188500_() - 0.5) * height * 0.3;
                double burstZ = fadingMage.m_20189_() + Math.sin(burstAngle) * burstRadius;
                double burstVelX = Math.cos(burstAngle) * 0.06;
                double burstVelY = 0.08 + fadingMage.m_217043_().m_188500_() * 0.04;
                double burstVelZ = Math.sin(burstAngle) * 0.06;
                if (fadingMage.m_217043_().m_188501_() < 0.75f) {
                    fadingMage.m_9236_().m_7106_((ParticleOptions)ParticleTypes.f_235898_, burstX, burstY, burstZ, burstVelX, burstVelY, burstVelZ);
                    continue;
                }
                fadingMage.m_9236_().m_7106_((ParticleOptions)ParticleTypes.f_123762_, burstX, burstY, burstZ, burstVelX * 0.6, burstVelY * 0.8, burstVelZ * 0.6);
            }
        }
    }
}

