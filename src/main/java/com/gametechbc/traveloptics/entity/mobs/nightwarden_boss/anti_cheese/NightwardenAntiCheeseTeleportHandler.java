/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 */
package com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.anti_cheese;

import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.NightwardenBossEntity;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import com.gametechbc.traveloptics.util.TravelopticsParticleHelper;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class NightwardenAntiCheeseTeleportHandler {
    public static void tryTeleport(NightwardenBossEntity boss, LivingEntity target, int particleCount, boolean allowTeleportBoss, boolean allowTeleportTarget) {
        boolean teleportBossBehind;
        Level level = boss.m_9236_();
        RandomSource rand = boss.m_217043_();
        boolean canDoBoth = allowTeleportBoss && allowTeleportTarget;
        boolean bl = teleportBossBehind = canDoBoth ? rand.m_188499_() : allowTeleportBoss;
        if (teleportBossBehind) {
            Vec3 sourcePos = boss.m_20182_();
            Vec3 targetLook = target.m_20154_().m_82541_();
            double teleportDistance = 3.0;
            Vec3 destinationPos = target.m_20182_().m_82546_(targetLook.m_82490_(teleportDistance));
            NightwardenAntiCheeseTeleportHandler.spawnTeleportParticles(level, sourcePos, particleCount);
            boss.m_7678_(destinationPos.f_82479_, destinationPos.f_82480_, destinationPos.f_82481_, boss.m_146908_(), boss.m_146909_());
            NightwardenAntiCheeseTeleportHandler.spawnTeleportParticles(level, destinationPos, particleCount);
        } else if (allowTeleportTarget) {
            Vec3 sourcePos = target.m_20182_();
            Vec3 destinationPos = boss.m_20182_();
            NightwardenAntiCheeseTeleportHandler.spawnTeleportParticles(level, sourcePos, particleCount);
            target.m_6021_(destinationPos.f_82479_, destinationPos.f_82480_, destinationPos.f_82481_);
            NightwardenAntiCheeseTeleportHandler.spawnTeleportParticles(level, destinationPos, particleCount);
        } else {
            return;
        }
        NightwardenAntiCheeseTeleportHandler.applyDarknessEffect(target);
        boss.m_5496_((SoundEvent)TravelopticsSounds.SPECTRAL_BLINK_SUCCESS.get(), 2.5f, 1.0f);
    }

    private static void applyDarknessEffect(LivingEntity target) {
        target.m_7292_(new MobEffectInstance(MobEffects.f_216964_, 60, 0, false, false, false));
    }

    private static void spawnTeleportParticles(Level level, Vec3 pos, int particleCount) {
        if (!level.f_46443_) {
            double width = 1.6;
            float height = 2.5f;
            for (int i = 0; i < particleCount; ++i) {
                double x = pos.f_82479_ + RandomSource.m_216327_().m_188500_() * width * 2.0 - width;
                double y = pos.f_82480_ + (double)height + RandomSource.m_216327_().m_188500_() * (double)height * 1.2 * 2.0 - (double)height * 1.2;
                double z = pos.f_82481_ + RandomSource.m_216327_().m_188500_() * width * 2.0 - width;
                double dx = RandomSource.m_216327_().m_188500_() * 0.1 * (double)(RandomSource.m_216327_().m_188499_() ? 1 : -1);
                double dy = RandomSource.m_216327_().m_188500_() * 0.1 * (double)(RandomSource.m_216327_().m_188499_() ? 1 : -1);
                double dz = RandomSource.m_216327_().m_188500_() * 0.1 * (double)(RandomSource.m_216327_().m_188499_() ? 1 : -1);
                MagicManager.spawnParticles((Level)level, (ParticleOptions)TravelopticsParticleHelper.LIGHT_PURPLE_GLOWING_ENCHANT, (double)x, (double)y, (double)z, (int)1, (double)dx, (double)dy, (double)dz, (double)0.3, (boolean)true);
            }
        }
    }
}

