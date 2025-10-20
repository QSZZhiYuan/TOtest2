/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.util.ParticleHelper
 *  net.minecraft.core.Holder
 *  net.minecraft.core.Holder$Reference
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectCategory
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.LivingEntity
 */
package com.gametechbc.traveloptics.effects;

import com.gametechbc.traveloptics.config.SpellsConfig;
import com.gametechbc.traveloptics.util.TravelopticsDamageTypes;
import com.gametechbc.traveloptics.util.TravelopticsTags;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import java.util.Random;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class AerialCollapseEffect
extends MobEffect {
    private static final Random RANDOM = new Random();

    public AerialCollapseEffect() {
        super(MobEffectCategory.HARMFUL, 0);
    }

    public void m_6742_(LivingEntity entity, int amplifier) {
        MobEffectInstance effectInstance = entity.m_21124_((MobEffect)this);
        if (effectInstance != null) {
            int duration = effectInstance.m_19557_();
            if (duration > 60) {
                entity.m_21195_((MobEffect)this);
                entity.m_7292_(new MobEffectInstance((MobEffect)this, 60, amplifier, false, true));
            }
            if (duration > 20) {
                entity.m_20334_(entity.m_20184_().f_82479_, 0.3, entity.m_20184_().f_82481_);
            } else if (duration <= 10) {
                entity.m_20334_(entity.m_20184_().f_82479_, -3.0, entity.m_20184_().f_82481_);
                if (duration == 5) {
                    float currentHealth = entity.m_21223_();
                    float damagePercent = 0.01f * (float)(amplifier + 1);
                    float damageAmount = currentHealth * damagePercent;
                    if (entity.m_6095_().m_204039_(TravelopticsTags.AERIAL_COLLAPSE_DR)) {
                        double damageReductionMultiplier = (Double)SpellsConfig.aerialCollapseDamageReductionModifier.get();
                        damageAmount *= (float)damageReductionMultiplier;
                    }
                    Holder.Reference damageTypeHolder = entity.m_9236_().m_9598_().m_175515_(Registries.f_268580_).m_246971_(TravelopticsDamageTypes.AERIAL_COLLAPSE);
                    DamageSource damageSource = new DamageSource((Holder)damageTypeHolder);
                    entity.m_6469_(damageSource, damageAmount);
                }
            }
            if (!entity.m_9236_().f_46443_ && entity.f_19797_ % 2 == 0) {
                for (int i = 0; i < 5; ++i) {
                    double offsetX = (RANDOM.nextDouble() - 0.5) * (double)entity.m_20205_();
                    double offsetY = RANDOM.nextDouble() * (double)entity.m_20206_();
                    double offsetZ = (RANDOM.nextDouble() - 0.5) * (double)entity.m_20205_();
                    ((ServerLevel)entity.m_9236_()).m_8767_(ParticleHelper.ACID_BUBBLE, entity.m_20185_() + offsetX, entity.m_20186_() + offsetY, entity.m_20189_() + offsetZ, 1, 0.0, 0.0, 0.0, 0.0);
                }
            }
        }
    }

    public boolean m_6584_(int duration, int amplifier) {
        return true;
    }
}

