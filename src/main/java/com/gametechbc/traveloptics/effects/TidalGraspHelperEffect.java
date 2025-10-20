/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectCategory
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 */
package com.gametechbc.traveloptics.effects;

import com.gametechbc.traveloptics.util.TravelopticsParticleHelper;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class TidalGraspHelperEffect
extends MobEffect {
    public TidalGraspHelperEffect() {
        super(MobEffectCategory.HARMFUL, 9636843);
    }

    public void m_6742_(LivingEntity entity, int amplifier) {
        if (!entity.m_9236_().f_46443_) {
            ParticleOptions[] particleTypes = new ParticleOptions[]{ParticleTypes.f_123796_, TravelopticsParticleHelper.WATER, TravelopticsParticleHelper.WATER_BUBBLE};
            int[] spawnPercentages = new int[]{30, 30, 40};
            int totalParticles = 8;
            for (int i = 0; i < totalParticles; ++i) {
                int randomValue = entity.m_217043_().m_188503_(100);
                ParticleOptions selectedParticle = null;
                int cumulativePercentage = 0;
                for (int j = 0; j < particleTypes.length; ++j) {
                    if (randomValue >= (cumulativePercentage += spawnPercentages[j])) continue;
                    selectedParticle = particleTypes[j];
                    break;
                }
                if (selectedParticle == null) continue;
                AABB boundingBox = entity.m_20191_();
                double x = boundingBox.f_82288_ + (boundingBox.f_82291_ - boundingBox.f_82288_) * entity.m_217043_().m_188500_();
                double y = boundingBox.f_82289_ + (boundingBox.f_82292_ - boundingBox.f_82289_) * entity.m_217043_().m_188500_();
                double z = boundingBox.f_82290_ + (boundingBox.f_82293_ - boundingBox.f_82290_) * entity.m_217043_().m_188500_();
                MagicManager.spawnParticles((Level)entity.m_9236_(), (ParticleOptions)selectedParticle, (double)x, (double)y, (double)z, (int)1, (double)0.2, (double)0.2, (double)0.2, (double)0.1, (boolean)true);
            }
        }
    }

    public boolean m_6584_(int duration, int amplifier) {
        return true;
    }
}

