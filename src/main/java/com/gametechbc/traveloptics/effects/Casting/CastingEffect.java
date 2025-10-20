/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectCategory
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation
 *  net.minecraft.world.entity.ai.attributes.Attributes
 */
package com.gametechbc.traveloptics.effects.Casting;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class CastingEffect
extends MobEffect {
    public CastingEffect() {
        super(MobEffectCategory.NEUTRAL, 0);
        this.m_19472_(Attributes.f_22279_, "68078724-8653-42D5-A245-9D14A1F54685", -0.2, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    public void m_6742_(LivingEntity LivingEntityIn, int amplifier) {
    }

    public boolean m_6584_(int duration, int amplifier) {
        return true;
    }
}

