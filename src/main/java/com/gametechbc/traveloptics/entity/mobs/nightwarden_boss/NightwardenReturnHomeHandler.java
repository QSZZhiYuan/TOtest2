/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.client.particle.LightningParticle$OrbData
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 */
package com.gametechbc.traveloptics.entity.mobs.nightwarden_boss;

import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.NightwardenBossEntity;
import com.gametechbc.traveloptics.init.TravelopticsMessages;
import com.gametechbc.traveloptics.util.TOColors;
import com.github.L_Ender.cataclysm.client.particle.LightningParticle;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class NightwardenReturnHomeHandler {
    public static final int HOME_RADIUS = 60;
    public static final int RETURN_COUNTDOWN = 100;
    public static final int RETURN_ANIMATION_DURATION = 200;
    private int homeTimer = 0;
    private boolean returningHome = false;

    public void tick(NightwardenBossEntity boss, BlockPos homePos) {
        if (homePos.equals((Object)BlockPos.f_121853_)) {
            return;
        }
        Vec3 bossPos = boss.m_20182_();
        double distance = bossPos.m_82531_((double)homePos.m_123341_() + 0.5, (double)homePos.m_123342_(), (double)homePos.m_123343_() + 0.5);
        if (distance > 3600.0) {
            if (!this.returningHome) {
                if (this.homeTimer == 0) {
                    this.homeTimer = 100;
                } else if (this.homeTimer > 0) {
                    --this.homeTimer;
                }
                if (this.homeTimer <= 0) {
                    this.initiateReturnSequence(boss);
                }
            } else {
                this.spawnReturnParticles(boss);
                if (--this.homeTimer <= 0) {
                    this.teleportHome(boss, homePos);
                }
            }
        } else {
            this.homeTimer = 0;
            this.returningHome = false;
        }
    }

    private void initiateReturnSequence(NightwardenBossEntity boss) {
        this.returningHome = true;
        this.homeTimer = 200;
        TravelopticsMessages.sendBossMessageToRange((Entity)boss, (Component)Component.m_237115_((String)"entity.traveloptics.message.nightwarden_returning"), TOColors.rgbToARGB(6619356, 0.5f), 60, 32.0, false);
    }

    private void teleportHome(NightwardenBossEntity boss, BlockPos homePos) {
        TravelopticsMessages.sendBossMessageToRange((Entity)boss, (Component)Component.m_237115_((String)"entity.traveloptics.message.nightwarden_returned"), TOColors.rgbToARGB(6619356, 0.5f), 60, 32.0, false);
        boss.m_7678_((double)homePos.m_123341_() + 0.5, homePos.m_123342_(), (double)homePos.m_123343_() + 0.5, boss.m_146908_(), boss.m_146909_());
        this.returningHome = false;
    }

    private void spawnReturnParticles(NightwardenBossEntity boss) {
        Level level = boss.m_9236_();
        if (!level.f_46443_) {
            for (int i = 0; i < 1; ++i) {
                double offsetX = (boss.m_217043_().m_188500_() - 0.5) * (double)boss.m_20205_();
                double offsetY = boss.m_217043_().m_188500_() * (double)boss.m_20206_();
                double offsetZ = (boss.m_217043_().m_188500_() - 0.5) * (double)boss.m_20205_();
                MagicManager.spawnParticles((Level)level, (ParticleOptions)new LightningParticle.OrbData(178, 35, 255), (double)(boss.m_20185_() + offsetX), (double)(boss.m_20186_() + offsetY), (double)(boss.m_20189_() + offsetZ), (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.3, (boolean)false);
            }
        }
    }
}

