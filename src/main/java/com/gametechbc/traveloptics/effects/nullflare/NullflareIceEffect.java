/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectCategory
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.AttributeMap
 */
package com.gametechbc.traveloptics.effects.nullflare;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;

public class NullflareIceEffect
extends MobEffect {
    public NullflareIceEffect() {
        super(MobEffectCategory.HARMFUL, 65535);
    }

    public void m_6742_(LivingEntity entity, int amplifier) {
        if (!entity.m_9236_().m_5776_() && entity.m_142079_()) {
            int freezeIncrease = (amplifier + 1) * 2;
            entity.m_146917_(entity.m_146888_() + freezeIncrease);
        }
    }

    public void m_6386_(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
        super.m_6386_(entity, attributeMap, amplifier);
        if (!entity.m_9236_().m_5776_()) {
            entity.m_146917_(0);
        }
    }

    public boolean m_6584_(int duration, int amplifier) {
        return true;
    }
}

