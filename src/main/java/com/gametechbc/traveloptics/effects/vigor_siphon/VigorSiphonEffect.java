/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.entity.mobs.MagicSummon
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectCategory
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 */
package com.gametechbc.traveloptics.effects.vigor_siphon;

import com.gametechbc.traveloptics.api.particle.ConnectedLineAnimatedParticle;
import io.redspace.ironsspellbooks.entity.mobs.MagicSummon;
import java.util.Comparator;
import java.util.List;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class VigorSiphonEffect
extends MobEffect {
    public VigorSiphonEffect() {
        super(MobEffectCategory.BENEFICIAL, 10423267);
    }

    public static double getConnectionRange() {
        return 15.0;
    }

    public void m_6742_(LivingEntity livingEntity, int amplifier) {
        Level level = livingEntity.m_9236_();
        if (level.f_46443_) {
            return;
        }
        AABB searchArea = livingEntity.m_20191_().m_82400_(VigorSiphonEffect.getConnectionRange());
        List ownedSummons = level.m_6443_(LivingEntity.class, searchArea, entity -> {
            if (!(entity instanceof MagicSummon)) return false;
            MagicSummon magicSummon = (MagicSummon)entity;
            if (!entity.m_6084_()) return false;
            if (magicSummon.getSummoner() == null) return false;
            if (!magicSummon.getSummoner().m_20148_().equals(livingEntity.m_20148_())) return false;
            return true;
        });
        ownedSummons.stream().min(Comparator.comparingDouble(arg_0 -> ((LivingEntity)livingEntity).m_20280_(arg_0))).ifPresent(summon -> ConnectedLineAnimatedParticle.createParticleLineTo(level, livingEntity, summon, ConnectedLineAnimatedParticle.AnimationType.SPIRAL_HELIX, livingEntity.f_19797_, true, ConnectedLineAnimatedParticle.ParticleColor.BLOOD_RED, ConnectedLineAnimatedParticle.ParticleColor.DARK_ORANGE));
    }

    public boolean m_6584_(int duration, int amplifier) {
        return true;
    }
}

