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

public class ElytraParticleManager {
    private static final double OFFSET_X = 0.4;
    private static final double OFFSET_Y = 0.7;
    private static final double OFFSET_Z = 0.2;
    private static final double PARTICLE_SPEED = -0.3;

    public static void spawnParticles(Player player, ParticleOptions particleOptions, int particleCount, boolean syncParticles) {
        Level level = player.m_9236_();
        Vec3 lookVec = player.m_20154_().m_82541_();
        Vec3 rightThruster = player.m_20182_().m_82520_(-lookVec.f_82481_ * 0.4 + lookVec.f_82479_ * 0.2, 0.7 + lookVec.f_82480_ * 0.2, lookVec.f_82479_ * 0.4 + lookVec.f_82481_ * 0.2);
        Vec3 leftThruster = player.m_20182_().m_82520_(lookVec.f_82481_ * 0.4 + lookVec.f_82479_ * 0.2, 0.7 + lookVec.f_82480_ * 0.2, -lookVec.f_82479_ * 0.4 + lookVec.f_82481_ * 0.2);
        for (int i = 0; i < particleCount; ++i) {
            double spreadX = (Math.random() - 0.5) * 0.4;
            double spreadY = (Math.random() - 0.5) * 0.15;
            double spreadZ = (Math.random() - 0.5) * 0.4;
            Vec3 motion = new Vec3(lookVec.f_82479_ * -0.3 + spreadX, lookVec.f_82480_ * -0.3 + spreadY, lookVec.f_82481_ * -0.3 + spreadZ);
            if (syncParticles) {
                if (level.f_46443_ || !(level instanceof ServerLevel)) continue;
                ServerLevel serverLevel = (ServerLevel)level;
                serverLevel.m_8767_(particleOptions, rightThruster.f_82479_, rightThruster.f_82480_, rightThruster.f_82481_, 1, motion.f_82479_, motion.f_82480_, motion.f_82481_, 0.05);
                serverLevel.m_8767_(particleOptions, leftThruster.f_82479_, leftThruster.f_82480_, leftThruster.f_82481_, 1, motion.f_82479_, motion.f_82480_, motion.f_82481_, 0.05);
                continue;
            }
            level.m_7106_(particleOptions, rightThruster.f_82479_, rightThruster.f_82480_, rightThruster.f_82481_, motion.f_82479_, motion.f_82480_, motion.f_82481_);
            level.m_7106_(particleOptions, leftThruster.f_82479_, leftThruster.f_82480_, leftThruster.f_82481_, motion.f_82479_, motion.f_82480_, motion.f_82481_);
        }
    }
}

