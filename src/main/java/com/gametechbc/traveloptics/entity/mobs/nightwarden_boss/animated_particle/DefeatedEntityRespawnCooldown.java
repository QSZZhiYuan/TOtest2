/*
 * Decompiled with CFR 0.152.
 */
package com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.animated_particle;

import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.nightwarden_defeated.NightwardenDefeatedEntity;
import com.gametechbc.traveloptics.util.TravelopticsParticleHelper;

public class DefeatedEntityRespawnCooldown {
    public static void drawCooldownRuneCircle(NightwardenDefeatedEntity boss, boolean shouldShowParticle, int getCooldown, int getMaxCooldown) {
        if (!shouldShowParticle) {
            return;
        }
        float cooldownProgress = (float)getCooldown / (float)getMaxCooldown;
        float radius = boss.m_20205_() * 1.5f;
        float height = 0.1f;
        int totalPoints = 36;
        int visiblePoints = Math.round((float)totalPoints * cooldownProgress);
        double timeOffset = (double)boss.m_9236_().m_46467_() / 80.0;
        for (int i = 0; i < visiblePoints; ++i) {
            double angle = Math.PI * 2 * (double)i / (double)totalPoints + timeOffset;
            double x = boss.m_20185_() + (double)radius * Math.cos(angle);
            double z = boss.m_20189_() + (double)radius * Math.sin(angle);
            double y = boss.m_20186_() + (double)height;
            if (i % 4 == 0) {
                for (int j = 0; j < 3; ++j) {
                    double runeHeight = (double)height + (double)j * 0.15;
                    boss.m_9236_().m_6493_(TravelopticsParticleHelper.SHORT_LIGHT_PURPLE_GLOWING_ENCHANT, false, x, boss.m_20186_() + runeHeight, z, 0.0, 0.0, 0.0);
                }
                if (i % 8 == 0) {
                    double runeLength = 0.3;
                    double outwardX = x + runeLength * Math.cos(angle);
                    double outwardZ = z + runeLength * Math.sin(angle);
                    boss.m_9236_().m_6493_(TravelopticsParticleHelper.SHORT_LIGHT_PURPLE_GLOWING_ENCHANT, false, outwardX, y, outwardZ, 0.0, 0.0, 0.0);
                    double glyphAngle = angle + 1.5707963267948966;
                    double glyphX1 = outwardX + 0.1 * Math.cos(glyphAngle);
                    double glyphZ1 = outwardZ + 0.1 * Math.sin(glyphAngle);
                    double glyphX2 = outwardX - 0.1 * Math.cos(glyphAngle);
                    double glyphZ2 = outwardZ - 0.1 * Math.sin(glyphAngle);
                    boss.m_9236_().m_6493_(TravelopticsParticleHelper.SHORT_LIGHT_PURPLE_GLOWING_ENCHANT, false, glyphX1, y, glyphZ1, 0.0, 0.0, 0.0);
                    boss.m_9236_().m_6493_(TravelopticsParticleHelper.SHORT_LIGHT_PURPLE_GLOWING_ENCHANT, false, glyphX2, y, glyphZ2, 0.0, 0.0, 0.0);
                }
            }
            double particleY = y + Math.sin((double)(boss.m_9236_().m_46467_() + (long)(i * 2)) / 10.0) * 0.03;
            boss.m_9236_().m_6493_(TravelopticsParticleHelper.SHORT_LIGHT_PURPLE_GLOWING_ENCHANT, false, x, particleY, z, 0.0, 0.0, 0.0);
        }
        if ((double)cooldownProgress < 0.5) {
            float innerRadius = radius * 0.6f;
            int innerPoints = 12;
            double innerTimeOffset = (double)(-boss.m_9236_().m_46467_()) / 60.0;
            for (int i = 0; i < innerPoints; ++i) {
                double angle = Math.PI * 2 * (double)i / (double)innerPoints + innerTimeOffset;
                double x = boss.m_20185_() + (double)innerRadius * Math.cos(angle);
                double z = boss.m_20189_() + (double)innerRadius * Math.sin(angle);
                double y = boss.m_20186_() + (double)height * 1.2;
                boss.m_9236_().m_6493_(TravelopticsParticleHelper.SHORT_LIGHT_PURPLE_GLOWING_ENCHANT, false, x, y, z, 0.0, 0.0, 0.0);
                if (i % 3 != 0 || !((double)cooldownProgress < 0.3)) continue;
                double outerX = boss.m_20185_() + (double)radius * Math.cos(angle);
                double outerZ = boss.m_20189_() + (double)radius * Math.sin(angle);
                int connectionPoints = 3;
                for (int j = 1; j < connectionPoints; ++j) {
                    double lerpFactor = (double)j / (double)connectionPoints;
                    double connectionX = x + (outerX - x) * lerpFactor;
                    double connectionZ = z + (outerZ - z) * lerpFactor;
                    double connectionY = y + (boss.m_20186_() + (double)height - y) * lerpFactor;
                    boss.m_9236_().m_6493_(TravelopticsParticleHelper.ABYSS_SPIKE_PARTICLE, false, connectionX, connectionY, connectionZ, 0.0, 0.0, 0.0);
                }
            }
        }
        if ((double)cooldownProgress < 0.1) {
            int surgeParticles = 8;
            double surgeHeight = 1.0;
            for (int i = 0; i < surgeParticles; ++i) {
                double angle = Math.PI * 2 * (double)i / (double)surgeParticles;
                double x = boss.m_20185_() + (double)radius * 0.5 * Math.cos(angle);
                double z = boss.m_20189_() + (double)radius * 0.5 * Math.sin(angle);
                boss.m_9236_().m_6493_(TravelopticsParticleHelper.ABYSS_SPIKE_PARTICLE, false, x, boss.m_20186_() + (double)height, z, 0.0, surgeHeight * 0.05, 0.0);
            }
        }
    }
}

