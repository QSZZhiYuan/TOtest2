/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.damage.DamageSources
 *  io.redspace.ironsspellbooks.entity.mobs.MagicSummon
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 */
package com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.anti_cheese;

import com.gametechbc.traveloptics.api.particle.ConnectedLineAnimatedParticle;
import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.NightwardenBossEntity;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.mobs.MagicSummon;
import java.util.Comparator;
import java.util.List;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class NightwardenSummonCheeseHandler {
    public static void tick(NightwardenBossEntity nightwarden, double radius, float damagePercent, float lifestealRatio) {
        Level level = nightwarden.m_9236_();
        if (level.m_5776_() && nightwarden.f_19797_ % 2 == 0) {
            AABB aoeVisual = nightwarden.m_20191_().m_82400_(radius + 4.0);
            List visuals = level.m_6443_(LivingEntity.class, aoeVisual, entity -> entity instanceof MagicSummon && entity.m_6084_());
            visuals.stream().sorted(Comparator.comparingDouble(arg_0 -> ((NightwardenBossEntity)nightwarden).m_20280_(arg_0))).limit(3L).forEach(target -> ConnectedLineAnimatedParticle.createParticleLineTo(level, (LivingEntity)nightwarden, target, 2, nightwarden.f_19797_));
        }
        if (level.m_5776_() || nightwarden.f_19797_ % 20 != 0) {
            return;
        }
        AABB aoe = nightwarden.m_20191_().m_82400_(radius);
        List targets = level.m_6443_(LivingEntity.class, aoe, entity -> {
            MagicSummon summon;
            return entity instanceof MagicSummon && (summon = (MagicSummon)entity).getSummoner() != null && summon.getSummoner() != nightwarden && entity.m_6084_();
        });
        float[] totalDamage = new float[]{0.0f};
        targets.stream().sorted(Comparator.comparingDouble(arg_0 -> ((NightwardenBossEntity)nightwarden).m_20280_(arg_0))).limit(3L).forEach(target -> {
            float damage = target.m_21233_() * damagePercent;
            boolean didDamage = DamageSources.applyDamage((Entity)target, (float)damage, (DamageSource)nightwarden.m_269291_().m_269333_((LivingEntity)nightwarden));
            DamageSources.ignoreNextKnockback((LivingEntity)target);
            if (didDamage) {
                totalDamage[0] = totalDamage[0] + damage;
            }
        });
        if (totalDamage[0] > 0.0f) {
            nightwarden.m_5634_(totalDamage[0] * lifestealRatio);
        }
    }
}

