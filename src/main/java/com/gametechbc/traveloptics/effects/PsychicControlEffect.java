/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.client.particle.LightningParticle$OrbData
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectCategory
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 */
package com.gametechbc.traveloptics.effects;

import com.gametechbc.traveloptics.init.TravelopticsEffects;
import com.github.L_Ender.cataclysm.client.particle.LightningParticle;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import java.util.List;
import java.util.Random;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class PsychicControlEffect
extends MobEffect {
    private static final Random RANDOM = new Random();

    public PsychicControlEffect() {
        super(MobEffectCategory.HARMFUL, 2744299);
    }

    public void m_6742_(LivingEntity entity, int amplifier) {
        if (!entity.m_9236_().f_46443_ && entity instanceof Mob) {
            Mob mob = (Mob)entity;
            Entity caster = this.getEffectSource(entity);
            if (caster instanceof LivingEntity) {
                LivingEntity newTarget;
                LivingEntity livingCaster = (LivingEntity)caster;
                if (mob.m_5448_() == livingCaster) {
                    mob.m_6710_(null);
                }
                if ((newTarget = this.findNewTarget(entity, livingCaster)) != null) {
                    mob.m_6710_(newTarget);
                }
            }
            for (int i = 0; i < 5; ++i) {
                double offsetX = (entity.m_217043_().m_188500_() - 0.5) * (double)entity.m_20205_();
                double offsetY = entity.m_217043_().m_188500_() * (double)entity.m_20206_();
                double offsetZ = (entity.m_217043_().m_188500_() - 0.5) * (double)entity.m_20205_();
                MagicManager.spawnParticles((Level)entity.m_9236_(), (ParticleOptions)new LightningParticle.OrbData(41, 223, 235), (double)(entity.m_20185_() + offsetX), (double)(entity.m_20186_() + offsetY), (double)(entity.m_20189_() + offsetZ), (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.3, (boolean)false);
            }
        }
    }

    public boolean m_6584_(int duration, int amplifier) {
        return duration % 10 == 0;
    }

    private LivingEntity findNewTarget(LivingEntity affectedEntity, LivingEntity caster) {
        Level level = affectedEntity.m_9236_();
        double range = 12.0;
        List possibleTargets = level.m_6443_(LivingEntity.class, caster.m_20191_().m_82400_(range), entity -> entity != caster && entity != affectedEntity && entity.m_6084_() && !(entity instanceof Player));
        if (!possibleTargets.isEmpty()) {
            return (LivingEntity)possibleTargets.get(RANDOM.nextInt(possibleTargets.size()));
        }
        return null;
    }

    private Entity getEffectSource(LivingEntity entity) {
        return entity.m_21124_((MobEffect)TravelopticsEffects.PSYCHIC_CONTROL.get()) != null ? entity : null;
    }
}

