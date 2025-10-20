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

public class AquaVortexSwirlParticleEffect {
    private final Entity entity;
    private final Level level;
    private final Random random = new Random();
    private double baseRadius = 8.0;
    private int particleCount = 20;

    public AquaVortexSwirlParticleEffect(Entity entity) {
        this.entity = entity;
        this.level = entity.m_9236_();
    }

    public void setBaseRadius(double radius) {
        this.baseRadius = radius;
    }

    public void setParticleCount(int count) {
        this.particleCount = Math.max(1, count);
    }

    public void spawnSwirlParticles() {
        double heightStep = 0.05;
        double angularSpeed = 0.15;
        for (int i = 0; i < this.particleCount; ++i) {
            double radius = this.baseRadius + this.random.nextDouble() * 1.5;
            double angle = Math.PI * 2 / (double)this.particleCount * (double)i + (double)this.entity.f_19797_ * angularSpeed + this.random.nextDouble() * Math.PI;
            double yOffset = this.random.nextDouble() * 2.0 - 1.0;
            double y = this.entity.m_20186_() + 0.5 + (double)this.entity.f_19797_ * heightStep % 2.0 + yOffset;
            double x = this.entity.m_20185_() + radius * (double)Mth.m_14089_((float)((float)angle));
            double z = this.entity.m_20189_() + radius * (double)Mth.m_14031_((float)((float)angle));
            double xVelocity = (double)(-Mth.m_14031_((float)((float)angle))) * 0.1 + this.random.nextDouble() * 0.05 - 0.025;
            double yVelocity = 0.1 + this.random.nextDouble() * 0.05;
            double zVelocity = (double)Mth.m_14089_((float)((float)angle)) * 0.1 + this.random.nextDouble() * 0.05 - 0.025;
            this.level.m_7106_((ParticleOptions)ParticleTypes.f_123796_, x, y, z, xVelocity, yVelocity, zVelocity);
        }
    }
}

