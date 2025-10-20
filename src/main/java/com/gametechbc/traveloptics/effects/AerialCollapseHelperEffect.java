/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.api.util.CameraShakeData
 *  io.redspace.ironsspellbooks.api.util.CameraShakeManager
 *  io.redspace.ironsspellbooks.util.ParticleHelper
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectCategory
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 */
package com.gametechbc.traveloptics.effects;

import com.gametechbc.traveloptics.api.particle.JetFlamesParticleManager;
import com.gametechbc.traveloptics.api.utils.TOGeneralUtils;
import io.redspace.ironsspellbooks.api.util.CameraShakeData;
import io.redspace.ironsspellbooks.api.util.CameraShakeManager;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class AerialCollapseHelperEffect
extends MobEffect {
    public AerialCollapseHelperEffect() {
        super(MobEffectCategory.BENEFICIAL, 0);
    }

    public void m_6742_(LivingEntity entity, int amplifier) {
        MobEffectInstance effectInstance = entity.m_21124_((MobEffect)this);
        if (effectInstance != null) {
            int duration = effectInstance.m_19557_();
            if (duration > 60) {
                entity.m_21195_((MobEffect)this);
                entity.m_7292_(new MobEffectInstance((MobEffect)this, 60, amplifier, false, true));
            }
            if (duration == 5 && !entity.m_9236_().f_46443_) {
                CameraShakeManager.addCameraShake((CameraShakeData)new CameraShakeData(20, entity.m_20182_(), 8.0f));
            }
        }
        if (!entity.m_9236_().f_46443_) {
            TOGeneralUtils.applyHovering((Entity)entity, 5.0, 0.06, 0.1, true);
            ParticleOptions[] particleTypes = new ParticleOptions[]{ParticleHelper.ACID, ParticleHelper.ACID_BUBBLE};
            int[] spawnPercentages = new int[]{10, 90};
            JetFlamesParticleManager.createJetFlamesBelowDefaulted(entity.m_9236_(), (Entity)entity, particleTypes, spawnPercentages, 8, false);
        }
    }

    public boolean m_6584_(int duration, int amplifier) {
        return true;
    }
}

