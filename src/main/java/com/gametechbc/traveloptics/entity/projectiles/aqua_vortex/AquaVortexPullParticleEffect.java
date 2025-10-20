/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.Level
 */
package com.gametechbc.traveloptics.entity.projectiles.aqua_vortex;

import java.util.Random;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class AquaVortexPullParticleEffect {
    private final Entity entity;
    private final Level level;
    private final Random random = new Random();

    public AquaVortexPullParticleEffect(Entity entity) {
        this.entity = entity;
        this.level = entity.m_9236_();
    }

    public void spawnPullParticles() {
        double maxRadius = 7.5;
        double verticalPullSpeed = 0.16;
        double inwardPullSpeed = 0.05;
        int particleCount = 13;
        for (int i = 0; i < particleCount; ++i) {
            double spawnRadius = this.random.nextDouble() * maxRadius;
            double angle = this.random.nextDouble() * 2.0 * Math.PI;
            double x = this.entity.m_20185_() + spawnRadius * (double)Mth.m_14089_((float)((float)angle));
            double z = this.entity.m_20189_() + spawnRadius * (double)Mth.m_14031_((float)((float)angle));
            double y = this.entity.m_20186_() + 0.5 + this.random.nextDouble() * 2.0 - 1.0;
            double xVelocity = (this.entity.m_20185_() - x) * inwardPullSpeed;
            double zVelocity = (this.entity.m_20189_() - z) * inwardPullSpeed;
            double yVelocity = verticalPullSpeed + this.random.nextDouble() * 0.05;
            this.level.m_7106_((ParticleOptions)ParticleTypes.f_123759_, x, y, z, xVelocity, yVelocity, zVelocity);
        }
    }
}

