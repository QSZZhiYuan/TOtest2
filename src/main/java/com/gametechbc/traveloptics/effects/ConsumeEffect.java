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
package com.gametechbc.traveloptics.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ConsumeEffect
extends MobEffect {
    public ConsumeEffect() {
        super(MobEffectCategory.BENEFICIAL, 9636843);
        this.m_19472_(Attributes.f_22283_, "68078724-8653-42D5-A245-9D14A1F54685", 0.05, AttributeModifier.Operation.MULTIPLY_BASE);
    }

    public void m_6742_(LivingEntity LivingEntityIn, int amplifier) {
    }

    public boolean m_6584_(int duration, int amplifier) {
        return true;
    }
}

