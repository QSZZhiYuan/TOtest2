/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.api.registry.AttributeRegistry
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectCategory
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.Attribute
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation
 *  net.minecraft.world.level.Level
 */
package com.gametechbc.traveloptics.effects;

import com.gametechbc.traveloptics.util.TravelopticsParticleHelper;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.level.Level;

public class WetEffect
extends MobEffect {
    public WetEffect() {
        super(MobEffectCategory.HARMFUL, 0);
        this.m_19472_((Attribute)AttributeRegistry.LIGHTNING_MAGIC_RESIST.get(), "68078724-8654-42D5-A245-9D14A1F54685", -0.05, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.m_19472_((Attribute)AttributeRegistry.ICE_MAGIC_RESIST.get(), "68078724-8653-42D5-A245-9D14A1F54685", -0.025, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    public void m_6742_(LivingEntity entity, int amplifier) {
        Level level;
        if (!entity.m_9236_().m_5776_() && (level = entity.m_9236_()) instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)level;
            int particleCount = 1;
            for (int i = 0; i < particleCount; ++i) {
                double offsetX = (entity.m_217043_().m_188500_() - 0.5) * (double)entity.m_20205_();
                double offsetY = entity.m_217043_().m_188500_() * (double)entity.m_20206_();
                double offsetZ = (entity.m_217043_().m_188500_() - 0.5) * (double)entity.m_20205_();
                double velocityX = (entity.m_217043_().m_188500_() - 0.5) * 0.2;
                double velocityY = (entity.m_217043_().m_188500_() - 0.5) * 0.2;
                double velocityZ = (entity.m_217043_().m_188500_() - 0.5) * 0.2;
                serverLevel.m_8767_(TravelopticsParticleHelper.WATER_DROP, entity.m_20185_() + offsetX, entity.m_20186_() + offsetY, entity.m_20189_() + offsetZ, 1, velocityX, velocityY, velocityZ, 0.0);
            }
        }
    }

    public boolean m_6584_(int duration, int amplifier) {
        return true;
    }
}

