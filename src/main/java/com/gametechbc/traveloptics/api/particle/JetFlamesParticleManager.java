/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 */
package com.gametechbc.traveloptics.api.particle;

import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class JetFlamesParticleManager {
    public static void createJetFlamesBelow(Level level, Entity entity, ParticleOptions[] particleOptions, int[] spawnPercentages, int totalParticles, double baseMotionY, double motionYVariation, boolean force) {
        if (!level.f_46443_) {
            if (particleOptions.length != spawnPercentages.length) {
                throw new IllegalArgumentException("Particle options and spawn percentages arrays must have same length");
            }
            if (particleOptions.length < 1 || particleOptions.length > 3) {
                throw new IllegalArgumentException("Must have 1-3 particle types");
            }
            RandomSource random = level.m_213780_();
            for (int i = 0; i < totalParticles; ++i) {
                int randomValue = random.m_188503_(100);
                ParticleOptions selectedParticle = null;
                int cumulativePercentage = 0;
                for (int j = 0; j < particleOptions.length; ++j) {
                    if (randomValue >= (cumulativePercentage += spawnPercentages[j])) continue;
                    selectedParticle = particleOptions[j];
                    break;
                }
                if (selectedParticle == null) continue;
                AABB boundingBox = entity.m_20191_();
                double x = boundingBox.f_82288_ + (boundingBox.f_82291_ - boundingBox.f_82288_) * random.m_188500_();
                double y = boundingBox.f_82289_ + (boundingBox.f_82292_ - boundingBox.f_82289_) * random.m_188500_();
                double z = boundingBox.f_82290_ + (boundingBox.f_82293_ - boundingBox.f_82290_) * random.m_188500_();
                double motionX = 0.0;
                double motionY = baseMotionY - random.m_188500_() * motionYVariation;
                double motionZ = 0.0;
                MagicManager.spawnParticles((Level)level, (ParticleOptions)selectedParticle, (double)entity.m_20185_(), (double)entity.m_20186_(), (double)entity.m_20189_(), (int)1, (double)motionX, (double)motionY, (double)motionZ, (double)0.03, (boolean)force);
            }
        }
    }

    public static void createJetFlamesBelowDefaulted(Level level, Entity entity, ParticleOptions[] particleOptions, int[] spawnPercentages, int totalParticles, boolean force) {
        JetFlamesParticleManager.createJetFlamesBelow(level, entity, particleOptions, spawnPercentages, totalParticles, -0.4, 0.2, force);
    }
}

