/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 */
package com.gametechbc.traveloptics.api.particle;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class JetpackParticleManager {
    private static final double OFFSET_X = 0.45;
    private static final double OFFSET_Y = 1.35;
    private static final double OFFSET_Z = -0.58;
    private static final double FIRE_SPEED = -0.4;
    private static final double SPREAD = 0.15;
    private static final double SPEED_VARIATION = 0.05;

    public static void spawnParticles(Player player, ParticleOptions particleOptions, int particleCount, boolean useSendParticles) {
        Level level = player.m_9236_();
        Vec3 lookVec = player.m_20154_();
        Vec3 horizontalLookVec = new Vec3(lookVec.f_82479_, 0.0, lookVec.f_82481_).m_82541_();
        Vec3 rightThruster = player.m_20182_().m_82520_(-horizontalLookVec.f_82481_ * 0.45 + horizontalLookVec.f_82479_ * -0.58, 1.35, horizontalLookVec.f_82479_ * 0.45 + horizontalLookVec.f_82481_ * -0.58);
        Vec3 leftThruster = player.m_20182_().m_82520_(horizontalLookVec.f_82481_ * 0.45 + horizontalLookVec.f_82479_ * -0.58, 1.35, -horizontalLookVec.f_82479_ * 0.45 + horizontalLookVec.f_82481_ * -0.58);
        for (int i = 0; i < particleCount; ++i) {
            double spreadX = (Math.random() - 0.5) * 0.15;
            double spreadZ = (Math.random() - 0.5) * 0.15;
            double speedVariation = Math.random() * 0.05;
            Vec3 fireMotion = new Vec3(spreadX, -0.4 - speedVariation, spreadZ);
            if (useSendParticles) {
                if (level.f_46443_ || !(level instanceof ServerLevel)) continue;
                ServerLevel serverLevel = (ServerLevel)level;
                serverLevel.m_8767_(particleOptions, rightThruster.f_82479_, rightThruster.f_82480_, rightThruster.f_82481_, 1, fireMotion.f_82479_, fireMotion.f_82480_, fireMotion.f_82481_, 0.05);
                serverLevel.m_8767_(particleOptions, leftThruster.f_82479_, leftThruster.f_82480_, leftThruster.f_82481_, 1, fireMotion.f_82479_, fireMotion.f_82480_, fireMotion.f_82481_, 0.05);
                continue;
            }
            level.m_7106_(particleOptions, rightThruster.f_82479_, rightThruster.f_82480_, rightThruster.f_82481_, fireMotion.f_82479_, fireMotion.f_82480_, fireMotion.f_82481_);
            level.m_7106_(particleOptions, leftThruster.f_82479_, leftThruster.f_82480_, leftThruster.f_82481_, fireMotion.f_82479_, fireMotion.f_82480_, fireMotion.f_82481_);
        }
    }
}

