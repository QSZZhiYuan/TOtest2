/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.Level
 */
package com.gametechbc.traveloptics.entity.projectiles.supernova.dying_star;

import com.gametechbc.traveloptics.entity.projectiles.supernova.dying_star.DyingStarEntity;
import com.gametechbc.traveloptics.init.TravelopticsParticles;
import com.gametechbc.traveloptics.util.TravelopticsParticleHelper;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;

public class DyingStarParticleManager {
    public static void spawnAOEWarningParticles(DyingStarEntity entity) {
        boolean isWarningPhase;
        if (entity.m_9236_().f_46443_) {
            return;
        }
        if (entity.f_19797_ < 10 || entity.f_19797_ >= 214) {
            return;
        }
        float radius = entity.getRadius();
        double centerX = entity.m_20185_();
        double centerZ = entity.m_20189_();
        double groundY = DyingStarParticleManager.findGroundLevel(entity.m_9236_(), centerX, entity.m_20186_(), centerZ);
        boolean bl = isWarningPhase = entity.f_19797_ >= 180;
        if (isWarningPhase) {
            float warningProgress = (float)(entity.f_19797_ - 190) / 24.0f;
            DyingStarParticleManager.spawnCriticalWarningParticles(entity, centerX, groundY, centerZ, radius, warningProgress);
        } else {
            float chargingProgress = (float)entity.f_19797_ / 190.0f;
            DyingStarParticleManager.spawnChargingPhaseParticles(entity, centerX, groundY, centerZ, radius, chargingProgress);
        }
    }

    private static double findGroundLevel(Level level, double x, double y, double z) {
        for (int i = 0; i < 50; ++i) {
            double checkY = y - (double)i;
            if (level.m_8055_(new BlockPos((int)x, (int)checkY, (int)z)).m_60795_()) continue;
            return checkY + 1.0;
        }
        return y - 10.0;
    }

    private static void spawnChargingPhaseParticles(DyingStarEntity entity, double centerX, double groundY, double centerZ, float radius, float progress) {
        int circlePoints = 40;
        float visibility = 0.1f + progress * 0.4f;
        RandomSource random = RandomSource.m_216327_();
        if (entity.f_19797_ % 4 == 0) {
            for (int i = 0; i < circlePoints; ++i) {
                if (random.m_188501_() > visibility) continue;
                double angle = Math.PI * 2 * (double)i / (double)circlePoints;
                double x = centerX + Math.cos(angle) * (double)radius;
                double z = centerZ + Math.sin(angle) * (double)radius;
                double y = DyingStarParticleManager.findGroundLevel(entity.m_9236_(), x, groundY + 5.0, z) + 0.1;
                MagicManager.spawnParticles((Level)entity.m_9236_(), (ParticleOptions)((ParticleOptions)TravelopticsParticles.PURPLE_STAR_INWARD_PARTICLE.get()), (double)x, (double)y, (double)z, (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)true);
            }
        }
        if (entity.f_19797_ % 20 == 0) {
            DyingStarParticleManager.spawnGentlePulse(entity, centerX, groundY, centerZ, radius * 0.3f);
        }
    }

    private static void spawnGentlePulse(DyingStarEntity entity, double centerX, double groundY, double centerZ, float pulseRadius) {
        int pulseParticles = 12;
        for (int i = 0; i < pulseParticles; ++i) {
            double angle = Math.PI * 2 * (double)i / (double)pulseParticles;
            double x = centerX + Math.cos(angle) * (double)pulseRadius;
            double z = centerZ + Math.sin(angle) * (double)pulseRadius;
            double y = DyingStarParticleManager.findGroundLevel(entity.m_9236_(), x, groundY + 5.0, z) + 0.1;
            MagicManager.spawnParticles((Level)entity.m_9236_(), (ParticleOptions)((ParticleOptions)TravelopticsParticles.PURPLE_STAR_INWARD_PARTICLE.get()), (double)x, (double)y, (double)z, (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)true);
        }
    }

    private static void spawnCriticalWarningParticles(DyingStarEntity entity, double centerX, double groundY, double centerZ, float radius, float progress) {
        int circlePoints = 70;
        float pulseIntensity = 0.7f + 0.3f * (float)Math.sin((float)entity.f_19797_ * 0.5f);
        for (int i = 0; i < circlePoints; ++i) {
            double angle = Math.PI * 2 * (double)i / (double)circlePoints;
            double x = centerX + Math.cos(angle) * (double)radius * (double)pulseIntensity;
            double z = centerZ + Math.sin(angle) * (double)radius * (double)pulseIntensity;
            double y = DyingStarParticleManager.findGroundLevel(entity.m_9236_(), x, groundY + 5.0, z) + 0.1;
            MagicManager.spawnParticles((Level)entity.m_9236_(), (ParticleOptions)TravelopticsParticleHelper.WARNING_PATH_RED_GLOWING_ENCHANT, (double)x, (double)y, (double)z, (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)true);
            if (i % 2 != 0) continue;
            MagicManager.spawnParticles((Level)entity.m_9236_(), (ParticleOptions)TravelopticsParticleHelper.WARNING_PATH_RED_GLOWING_ENCHANT, (double)x, (double)(y + 0.5), (double)z, (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)true);
        }
        DyingStarParticleManager.spawnRapidWarningWaves(entity, centerX, groundY, centerZ, radius, progress);
        DyingStarParticleManager.spawnIntenseCenterBurst(entity, centerX, groundY, centerZ, progress);
        DyingStarParticleManager.spawnDangerZoneFill(entity, centerX, groundY, centerZ, radius, progress);
    }

    private static void spawnRapidWarningWaves(DyingStarEntity entity, double centerX, double groundY, double centerZ, float radius, float progress) {
        if (entity.f_19797_ % 3 == 0) {
            int waveParticles = 24;
            float waveRadius = radius * (0.3f + 0.7f * ((float)(entity.f_19797_ % 15) / 15.0f));
            for (int i = 0; i < waveParticles; ++i) {
                double angle = Math.PI * 2 * (double)i / (double)waveParticles;
                double x = centerX + Math.cos(angle) * (double)waveRadius;
                double z = centerZ + Math.sin(angle) * (double)waveRadius;
                double y = DyingStarParticleManager.findGroundLevel(entity.m_9236_(), x, groundY + 5.0, z) + 0.05;
                MagicManager.spawnParticles((Level)entity.m_9236_(), (ParticleOptions)TravelopticsParticleHelper.SHORT_LIGHT_RED_GLOWING_ENCHANT, (double)x, (double)y, (double)z, (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)true);
            }
        }
    }

    private static void spawnIntenseCenterBurst(DyingStarEntity entity, double centerX, double groundY, double centerZ, float progress) {
        int burstParticles = 12;
        float burstRadius = 1.0f + (float)Math.sin((float)entity.f_19797_ * 0.3f) * 2.0f;
        for (int i = 0; i < burstParticles; ++i) {
            double angle = Math.PI * 2 * (double)i / (double)burstParticles + (double)((float)entity.f_19797_ * 0.1f);
            double x = centerX + Math.cos(angle) * (double)burstRadius;
            double z = centerZ + Math.sin(angle) * (double)burstRadius;
            double y = DyingStarParticleManager.findGroundLevel(entity.m_9236_(), x, groundY + 5.0, z) + 0.1;
            MagicManager.spawnParticles((Level)entity.m_9236_(), (ParticleOptions)TravelopticsParticleHelper.SHORT_LIGHT_RED_GLOWING_ENCHANT, (double)x, (double)y, (double)z, (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)true);
        }
    }

    private static void spawnDangerZoneFill(DyingStarEntity entity, double centerX, double groundY, double centerZ, float radius, float progress) {
        if (entity.f_19797_ % 2 == 0) {
            int fillParticles = 8;
            RandomSource random = RandomSource.m_216327_();
            for (int i = 0; i < fillParticles; ++i) {
                double angle = random.m_188500_() * 2.0 * Math.PI;
                double distance = random.m_188500_() * (double)radius * (double)0.8f;
                double x = centerX + Math.cos(angle) * distance;
                double z = centerZ + Math.sin(angle) * distance;
                double y = DyingStarParticleManager.findGroundLevel(entity.m_9236_(), x, groundY + 5.0, z) + 0.05;
                MagicManager.spawnParticles((Level)entity.m_9236_(), (ParticleOptions)TravelopticsParticleHelper.SHORT_LIGHT_RED_GLOWING_ENCHANT, (double)x, (double)y, (double)z, (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)true);
            }
        }
    }
}

