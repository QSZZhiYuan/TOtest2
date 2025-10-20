/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Holder
 *  net.minecraft.core.Holder$Reference
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectCategory
 *  net.minecraft.world.entity.LivingEntity
 */
package com.gametechbc.traveloptics.effects.nullflare;

import com.gametechbc.traveloptics.util.TravelopticsDamageTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class NullflareFireEffect
extends MobEffect {
    public NullflareFireEffect() {
        super(MobEffectCategory.HARMFUL, 0xFF0000);
    }

    public void m_6742_(LivingEntity entity, int amplifier) {
        if (!entity.m_9236_().m_5776_() && entity.f_19797_ % 40 == 0) {
            Holder.Reference damageTypeHolder = entity.m_9236_().m_9598_().m_175515_(Registries.f_268580_).m_246971_(TravelopticsDamageTypes.NULLFLARE_FIRE);
            DamageSource damageSource = new DamageSource((Holder)damageTypeHolder);
            entity.m_6469_(damageSource, 1.0f + (float)amplifier);
        }
        super.m_6742_(entity, amplifier);
    }

    public boolean m_6584_(int duration, int amplifier) {
        return true;
    }
}

