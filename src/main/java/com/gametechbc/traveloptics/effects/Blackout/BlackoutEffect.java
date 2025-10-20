/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectCategory
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 */
package com.gametechbc.traveloptics.effects.Blackout;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class BlackoutEffect
extends MobEffect {
    public BlackoutEffect() {
        super(MobEffectCategory.NEUTRAL, 0);
    }

    public void m_6742_(LivingEntity entity, int amplifier) {
        Level level;
        entity.m_7292_(new MobEffectInstance(MobEffects.f_216964_, 60, 0, false, false, false));
        if (!entity.m_9236_().m_5776_() && (level = entity.m_9236_()) instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)level;
            int particleCount = 3;
            for (int i = 0; i < particleCount; ++i) {
                double offsetX = (entity.m_217043_().m_188500_() - 0.5) * (double)entity.m_20205_();
                double offsetY = entity.m_217043_().m_188500_() * (double)entity.m_20206_();
                double offsetZ = (entity.m_217043_().m_188500_() - 0.5) * (double)entity.m_20205_();
                double velocityX = (entity.m_217043_().m_188500_() - 0.5) * 0.2;
                double velocityY = (entity.m_217043_().m_188500_() - 0.5) * 0.2;
                double velocityZ = (entity.m_217043_().m_188500_() - 0.5) * 0.2;
                serverLevel.m_8767_((ParticleOptions)ParticleTypes.f_123762_, entity.m_20185_() + offsetX, entity.m_20186_() + offsetY, entity.m_20189_() + offsetZ, 1, velocityX, velocityY, velocityZ, 0.0);
            }
        }
    }

    public boolean m_6584_(int duration, int amplifier) {
        return true;
    }
}

