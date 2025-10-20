/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.phys.Vec3
 */
package com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.misc;

import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.NightwardenBossEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class NightwardenTargetVelocityTracker {
    private static final Map<UUID, BossTrackingData> bossTrackers = new HashMap<UUID, BossTrackingData>();
    private static final int VELOCITY_HISTORY_SIZE = 3;

    public static void trackTargetMovement(NightwardenBossEntity nightwardenBoss) {
        LivingEntity target = nightwardenBoss.m_5448_();
        if (target == null || !target.m_6084_()) {
            return;
        }
        UUID bossId = nightwardenBoss.m_20148_();
        BossTrackingData data = bossTrackers.computeIfAbsent(bossId, k -> new BossTrackingData());
        ++data.velocityTrackingTicks;
        if (data.velocityTrackingTicks % 2 == 0) {
            Vec3 currentPos = target.m_20182_();
            if (!data.lastTargetPosition.equals((Object)Vec3.f_82478_)) {
                Vec3 actualVelocity = currentPos.m_82546_(data.lastTargetPosition).m_82490_(0.5);
                data.velocityHistory.add(actualVelocity);
                if (data.velocityHistory.size() > 3) {
                    data.velocityHistory.remove(0);
                }
                if (!data.velocityHistory.isEmpty()) {
                    Vec3 sum = Vec3.f_82478_;
                    for (Vec3 vel : data.velocityHistory) {
                        sum = sum.m_82549_(vel);
                    }
                    data.averageTargetVelocity = sum.m_82490_(1.0 / (double)data.velocityHistory.size());
                }
            }
            data.lastTargetPosition = currentPos;
        }
    }

    public static Vec3 getAverageTargetVelocity(NightwardenBossEntity nightwardenBoss) {
        UUID bossId = nightwardenBoss.m_20148_();
        BossTrackingData data = bossTrackers.get(bossId);
        return data != null ? data.averageTargetVelocity : Vec3.f_82478_;
    }

    public static void clearHistory(NightwardenBossEntity nightwardenBoss) {
        UUID bossId = nightwardenBoss.m_20148_();
        BossTrackingData data = bossTrackers.get(bossId);
        if (data != null) {
            data.velocityHistory.clear();
            data.averageTargetVelocity = Vec3.f_82478_;
            data.lastTargetPosition = Vec3.f_82478_;
            data.velocityTrackingTicks = 0;
            System.out.println("Cleared velocity history for boss: " + bossId);
        }
    }

    public static void removeBossTracker(NightwardenBossEntity nightwardenBoss) {
        UUID bossId = nightwardenBoss.m_20148_();
        bossTrackers.remove(bossId);
        System.out.println("Removed tracker for boss: " + bossId);
    }

    private static class BossTrackingData {
        Vec3 lastTargetPosition = Vec3.f_82478_;
        Vec3 averageTargetVelocity = Vec3.f_82478_;
        int velocityTrackingTicks = 0;
        List<Vec3> velocityHistory = new ArrayList<Vec3>();

        private BossTrackingData() {
        }
    }
}

