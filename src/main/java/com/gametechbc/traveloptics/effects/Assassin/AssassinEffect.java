/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.registries.MobEffectRegistry
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectCategory
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation
 *  net.minecraft.world.entity.ai.attributes.Attributes
 */
package com.gametechbc.traveloptics.effects.Assassin;

import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class AssassinEffect
extends MobEffect {
    public AssassinEffect() {
        super(MobEffectCategory.BENEFICIAL, 0);
        this.m_19472_(Attributes.f_22279_, "68078724-8653-42D5-A245-9D14A1F54685", 0.05, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.m_19472_(Attributes.f_22281_, "68078724-8653-42D5-A245-9D14A1F54685", 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    public void m_6742_(LivingEntity entity, int amplifier) {
        entity.m_7292_(new MobEffectInstance((MobEffect)MobEffectRegistry.TRUE_INVISIBILITY.get(), 10, 0, false, false, false));
        if (!entity.m_9236_().f_46443_ && entity.f_19797_ % 30 == 0) {
            ServerLevel serverLevel = (ServerLevel)entity.m_9236_();
            serverLevel.m_8767_((ParticleOptions)((SimpleParticleType)ParticleTypes.f_123787_), entity.m_20185_(), entity.m_20186_() + (double)entity.m_20192_(), entity.m_20189_(), 1, 0.0, 0.0, 0.0, 0.0);
        }
    }

    public boolean m_6584_(int duration, int amplifier) {
        return true;
    }
}

