/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 */
package com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.animated_particle;

import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.NightwardenBossEntity;
import com.gametechbc.traveloptics.util.TravelopticsParticleHelper;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class NightwardenPhaseThreeSwapAnimatedParticle {
    public static void handlePhaseTwoToThreeAnimatedParticles(NightwardenBossEntity boss, int elapsed) {
        if (!boss.m_9236_().f_46443_) {
            double yDir;
            double phi;
            Vec3 forward = boss.m_20154_().m_82541_();
            Vec3 particleCenter = boss.m_20182_().m_82549_(forward.m_82490_(1.4)).m_82520_(0.0, 3.0, 0.0);
            RandomSource random = boss.m_217043_();
            if (elapsed >= 165 && elapsed <= 220) {
                int i;
                for (i = 0; i < 8; ++i) {
                    double angle = (double)i * Math.PI / 4.0 + (double)(elapsed - 165) * 0.02;
                    double radius = 7.0 - (double)(elapsed - 165) * 0.06;
                    if (radius < 1.0) {
                        radius = 1.0;
                    }
                    double xPos = boss.m_20185_() + Math.cos(angle) * radius;
                    double zPos = boss.m_20189_() + Math.sin(angle) * radius;
                    BlockPos groundPos = new BlockPos((int)xPos, (int)boss.m_20186_() - 1, (int)zPos);
                    while (boss.m_9236_().m_46859_(groundPos) && groundPos.m_123342_() > boss.m_9236_().m_141937_()) {
                        groundPos = groundPos.m_7495_();
                    }
                    double startY = (double)groundPos.m_123342_() + 1.0;
                    double yOffset = Math.min(3.0, (double)(elapsed - 165) * 0.15);
                    Vec3 particlePos = new Vec3(xPos, startY + yOffset, zPos);
                    Vec3 direction = particleCenter.m_82546_(particlePos).m_82541_().m_82490_(0.18);
                    if (i % 3 == 0) {
                        MagicManager.spawnParticles((Level)boss.m_9236_(), (ParticleOptions)TravelopticsParticleHelper.SHORT_LIGHT_GOLD_GLOWING_ENCHANT, (double)particlePos.f_82479_, (double)particlePos.f_82480_, (double)particlePos.f_82481_, (int)0, (double)direction.f_82479_, (double)direction.f_82480_, (double)direction.f_82481_, (double)0.02, (boolean)true);
                        continue;
                    }
                    if (i % 3 == 1) {
                        MagicManager.spawnParticles((Level)boss.m_9236_(), (ParticleOptions)TravelopticsParticleHelper.SHORT_LIGHT_PURPLE_GLOWING_ENCHANT, (double)particlePos.f_82479_, (double)particlePos.f_82480_, (double)particlePos.f_82481_, (int)0, (double)direction.f_82479_, (double)direction.f_82480_, (double)direction.f_82481_, (double)0.02, (boolean)true);
                        continue;
                    }
                    MagicManager.spawnParticles((Level)boss.m_9236_(), (ParticleOptions)TravelopticsParticleHelper.SHORT_LIGHT_RED_GLOWING_ENCHANT, (double)particlePos.f_82479_, (double)particlePos.f_82480_, (double)particlePos.f_82481_, (int)0, (double)direction.f_82479_, (double)direction.f_82480_, (double)direction.f_82481_, (double)0.02, (boolean)true);
                }
                for (i = 0; i < 2; ++i) {
                    double offsetX = random.m_188500_() * 0.6 - 0.3;
                    double offsetY = random.m_188500_() * 0.6 - 0.3;
                    double offsetZ = random.m_188500_() * 0.6 - 0.3;
                    MagicManager.spawnParticles((Level)boss.m_9236_(), (ParticleOptions)TravelopticsParticleHelper.SHORT_LIGHT_GOLD_GLOWING_ENCHANT, (double)(particleCenter.f_82479_ + offsetX), (double)(particleCenter.f_82480_ + offsetY), (double)(particleCenter.f_82481_ + offsetZ), (int)0, (double)0.0, (double)0.0, (double)0.0, (double)0.01, (boolean)true);
                }
            }
            if (elapsed >= 221 && elapsed <= 290) {
                int spiralCount = 5;
                for (int spiral = 0; spiral < spiralCount; ++spiral) {
                    double spiralOffset = Math.PI * 2 / (double)spiralCount * (double)spiral;
                    double theta = (double)(elapsed - 221) * 0.22 + spiralOffset;
                    double progressFactor = (double)(elapsed - 221) / 70.0;
                    double radius = 4.0 - progressFactor * 2.0;
                    if (radius < 0.5) {
                        radius = 0.5;
                    }
                    double heightOffset = Math.sin((double)(elapsed - 221) * 0.15) * 0.7;
                    double xPos = particleCenter.f_82479_ + Math.cos(theta) * radius;
                    double yPos = particleCenter.f_82480_ + heightOffset;
                    double zPos = particleCenter.f_82481_ + Math.sin(theta) * radius;
                    Vec3 particlePos = new Vec3(xPos, yPos, zPos);
                    Vec3 direction = particleCenter.m_82546_(particlePos).m_82541_().m_82490_(0.1);
                    if (spiral % 3 == 0) {
                        MagicManager.spawnParticles((Level)boss.m_9236_(), (ParticleOptions)TravelopticsParticleHelper.LIGHT_PURPLE_GLOWING_ENCHANT, (double)xPos, (double)yPos, (double)zPos, (int)0, (double)direction.f_82479_, (double)direction.f_82480_, (double)direction.f_82481_, (double)0.015, (boolean)true);
                    } else if (spiral % 3 == 1) {
                        MagicManager.spawnParticles((Level)boss.m_9236_(), (ParticleOptions)TravelopticsParticleHelper.LIGHT_GOLD_GLOWING_ENCHANT, (double)xPos, (double)yPos, (double)zPos, (int)0, (double)direction.f_82479_, (double)direction.f_82480_, (double)direction.f_82481_, (double)0.015, (boolean)true);
                    } else {
                        MagicManager.spawnParticles((Level)boss.m_9236_(), (ParticleOptions)TravelopticsParticleHelper.LIGHT_RED_GLOWING_ENCHANT, (double)xPos, (double)yPos, (double)zPos, (int)0, (double)direction.f_82479_, (double)direction.f_82480_, (double)direction.f_82481_, (double)0.015, (boolean)true);
                    }
                    MagicManager.spawnParticles((Level)boss.m_9236_(), (ParticleOptions)TravelopticsParticleHelper.SHORT_LIGHT_GOLD_GLOWING_ENCHANT, (double)xPos, (double)yPos, (double)zPos, (int)0, (double)0.0, (double)0.0, (double)0.0, (double)0.01, (boolean)true);
                }
                if (random.m_188503_(5) < Math.min(4, (elapsed - 221) / 8)) {
                    double offset = 0.8;
                    double sparkX = particleCenter.f_82479_ + (random.m_188500_() * offset * 2.0 - offset);
                    double sparkY = particleCenter.f_82480_ + (random.m_188500_() * offset * 2.0 - offset);
                    double sparkZ = particleCenter.f_82481_ + (random.m_188500_() * offset * 2.0 - offset);
                    MagicManager.spawnParticles((Level)boss.m_9236_(), (ParticleOptions)TravelopticsParticleHelper.LIGHT_RED_GLOWING_ENCHANT, (double)sparkX, (double)sparkY, (double)sparkZ, (int)0, (double)0.0, (double)0.0, (double)0.0, (double)0.02, (boolean)true);
                }
            }
            if (elapsed >= 291 && elapsed <= 345) {
                int i;
                double pulsePhase = (double)(elapsed - 291) / 4.0;
                double pulseSize = 0.4 + 0.3 * Math.sin(pulsePhase);
                int particleCount = 12 + (elapsed - 291) / 8;
                for (i = 0; i < particleCount; ++i) {
                    double orbitSpeed = 0.4 + (double)(elapsed - 291) / 70.0;
                    double angle = (double)i * (Math.PI * 2 / (double)particleCount) + (double)elapsed * orbitSpeed * 0.06;
                    double shrinkFactor = Math.max(0.5, 1.0 - (double)(elapsed - 291) / 54.0 * 0.5);
                    double orbitRadius = 1.5 * shrinkFactor;
                    double xPos = particleCenter.f_82479_ + Math.cos(angle) * orbitRadius;
                    double yPos = particleCenter.f_82480_ + Math.sin(angle) * orbitRadius * 0.5;
                    double zPos = particleCenter.f_82481_ + Math.sin(angle) * orbitRadius;
                    double tangentX = -Math.sin(angle) * 0.07 * orbitSpeed;
                    double tangentY = Math.cos(angle) * 0.035 * orbitSpeed;
                    double tangentZ = Math.cos(angle) * 0.07 * orbitSpeed;
                    if (i % 3 == 0) {
                        MagicManager.spawnParticles((Level)boss.m_9236_(), (ParticleOptions)TravelopticsParticleHelper.LIGHT_PURPLE_GLOWING_ENCHANT, (double)xPos, (double)yPos, (double)zPos, (int)0, (double)tangentX, (double)tangentY, (double)tangentZ, (double)0.015, (boolean)true);
                        continue;
                    }
                    if (i % 3 == 1) {
                        MagicManager.spawnParticles((Level)boss.m_9236_(), (ParticleOptions)TravelopticsParticleHelper.LIGHT_GOLD_GLOWING_ENCHANT, (double)xPos, (double)yPos, (double)zPos, (int)0, (double)tangentX, (double)tangentY, (double)tangentZ, (double)0.015, (boolean)true);
                        continue;
                    }
                    MagicManager.spawnParticles((Level)boss.m_9236_(), (ParticleOptions)TravelopticsParticleHelper.LIGHT_RED_GLOWING_ENCHANT, (double)xPos, (double)yPos, (double)zPos, (int)0, (double)tangentX, (double)tangentY, (double)tangentZ, (double)0.015, (boolean)true);
                }
                for (i = 0; i < 3; ++i) {
                    double coreX = particleCenter.f_82479_ + (random.m_188500_() * pulseSize * 2.0 - pulseSize);
                    double coreY = particleCenter.f_82480_ + (random.m_188500_() * pulseSize * 2.0 - pulseSize);
                    double coreZ = particleCenter.f_82481_ + (random.m_188500_() * pulseSize * 2.0 - pulseSize);
                    if (i % 3 == 0) {
                        MagicManager.spawnParticles((Level)boss.m_9236_(), (ParticleOptions)TravelopticsParticleHelper.SHORT_LIGHT_PURPLE_GLOWING_ENCHANT, (double)coreX, (double)coreY, (double)coreZ, (int)0, (double)0.0, (double)0.0, (double)0.0, (double)0.015, (boolean)true);
                        continue;
                    }
                    if (i % 3 == 1) {
                        MagicManager.spawnParticles((Level)boss.m_9236_(), (ParticleOptions)TravelopticsParticleHelper.SHORT_LIGHT_GOLD_GLOWING_ENCHANT, (double)coreX, (double)coreY, (double)coreZ, (int)0, (double)0.0, (double)0.0, (double)0.0, (double)0.015, (boolean)true);
                        continue;
                    }
                    MagicManager.spawnParticles((Level)boss.m_9236_(), (ParticleOptions)TravelopticsParticleHelper.SHORT_LIGHT_RED_GLOWING_ENCHANT, (double)coreX, (double)coreY, (double)coreZ, (int)0, (double)0.0, (double)0.0, (double)0.0, (double)0.015, (boolean)true);
                }
                if (elapsed >= 310) {
                    int tendrilCount = Math.min(8, (elapsed - 310) / 4);
                    for (int i2 = 0; i2 < tendrilCount; ++i2) {
                        double theta = Math.PI * 2 * random.m_188500_();
                        double phi2 = Math.acos(2.0 * random.m_188500_() - 1.0);
                        double tendrilLength = 0.8 + random.m_188500_() * 0.6;
                        double xDir = Math.sin(phi2) * Math.cos(theta) * tendrilLength;
                        double yDir2 = Math.sin(phi2) * Math.sin(theta) * tendrilLength;
                        double zDir = Math.cos(phi2) * tendrilLength;
                        int segments = 6;
                        for (int seg = 0; seg < segments; ++seg) {
                            double factor = (double)seg / (double)segments;
                            double xPos = particleCenter.f_82479_ + xDir * factor;
                            double yPos = particleCenter.f_82480_ + yDir2 * factor;
                            double zPos = particleCenter.f_82481_ + zDir * factor;
                            if (i2 % 3 == 0) {
                                MagicManager.spawnParticles((Level)boss.m_9236_(), (ParticleOptions)TravelopticsParticleHelper.SHORT_LIGHT_PURPLE_GLOWING_ENCHANT, (double)xPos, (double)yPos, (double)zPos, (int)0, (double)0.0, (double)0.0, (double)0.0, (double)0.01, (boolean)true);
                                continue;
                            }
                            if (i2 % 3 == 1) {
                                MagicManager.spawnParticles((Level)boss.m_9236_(), (ParticleOptions)TravelopticsParticleHelper.SHORT_LIGHT_GOLD_GLOWING_ENCHANT, (double)xPos, (double)yPos, (double)zPos, (int)0, (double)0.0, (double)0.0, (double)0.0, (double)0.01, (boolean)true);
                                continue;
                            }
                            MagicManager.spawnParticles((Level)boss.m_9236_(), (ParticleOptions)TravelopticsParticleHelper.SHORT_LIGHT_RED_GLOWING_ENCHANT, (double)xPos, (double)yPos, (double)zPos, (int)0, (double)0.0, (double)0.0, (double)0.0, (double)0.01, (boolean)true);
                        }
                    }
                }
            }
            if (elapsed == 346) {
                for (int i = 0; i < 80; ++i) {
                    double theta = Math.PI * 2 * random.m_188500_();
                    phi = Math.acos(2.0 * random.m_188500_() - 1.0);
                    double xDir = Math.sin(phi) * Math.cos(theta);
                    yDir = Math.sin(phi) * Math.sin(theta);
                    double zDir = Math.cos(phi);
                    Vec3 dir = new Vec3(xDir, yDir, zDir).m_82541_().m_82490_(0.3);
                    if (i % 3 == 0) {
                        MagicManager.spawnParticles((Level)boss.m_9236_(), (ParticleOptions)TravelopticsParticleHelper.SHORT_LIGHT_PURPLE_GLOWING_ENCHANT, (double)particleCenter.f_82479_, (double)particleCenter.f_82480_, (double)particleCenter.f_82481_, (int)0, (double)dir.f_82479_, (double)dir.f_82480_, (double)dir.f_82481_, (double)0.05, (boolean)true);
                        continue;
                    }
                    if (i % 3 == 1) {
                        MagicManager.spawnParticles((Level)boss.m_9236_(), (ParticleOptions)TravelopticsParticleHelper.SHORT_LIGHT_GOLD_GLOWING_ENCHANT, (double)particleCenter.f_82479_, (double)particleCenter.f_82480_, (double)particleCenter.f_82481_, (int)0, (double)dir.f_82479_, (double)dir.f_82480_, (double)dir.f_82481_, (double)0.05, (boolean)true);
                        continue;
                    }
                    MagicManager.spawnParticles((Level)boss.m_9236_(), (ParticleOptions)TravelopticsParticleHelper.SHORT_LIGHT_RED_GLOWING_ENCHANT, (double)particleCenter.f_82479_, (double)particleCenter.f_82480_, (double)particleCenter.f_82481_, (int)0, (double)dir.f_82479_, (double)dir.f_82480_, (double)dir.f_82481_, (double)0.05, (boolean)true);
                }
            }
            if (elapsed == 355) {
                for (int i = 0; i < 100; ++i) {
                    double theta = Math.PI * 2 * random.m_188500_();
                    phi = Math.acos(2.0 * random.m_188500_() - 1.0);
                    double xDir = Math.sin(phi) * Math.cos(theta);
                    yDir = Math.sin(phi) * Math.sin(theta);
                    double zDir = Math.cos(phi);
                    Vec3 dir = new Vec3(xDir, yDir, zDir).m_82541_().m_82490_(0.4);
                    if (i % 3 == 0) {
                        MagicManager.spawnParticles((Level)boss.m_9236_(), (ParticleOptions)TravelopticsParticleHelper.SHORT_LIGHT_GOLD_GLOWING_ENCHANT, (double)particleCenter.f_82479_, (double)particleCenter.f_82480_, (double)particleCenter.f_82481_, (int)0, (double)dir.f_82479_, (double)dir.f_82480_, (double)dir.f_82481_, (double)0.05, (boolean)true);
                        continue;
                    }
                    if (i % 3 == 1) {
                        MagicManager.spawnParticles((Level)boss.m_9236_(), (ParticleOptions)TravelopticsParticleHelper.SHORT_LIGHT_PURPLE_GLOWING_ENCHANT, (double)particleCenter.f_82479_, (double)particleCenter.f_82480_, (double)particleCenter.f_82481_, (int)0, (double)dir.f_82479_, (double)dir.f_82480_, (double)dir.f_82481_, (double)0.05, (boolean)true);
                        continue;
                    }
                    MagicManager.spawnParticles((Level)boss.m_9236_(), (ParticleOptions)TravelopticsParticleHelper.SHORT_LIGHT_RED_GLOWING_ENCHANT, (double)particleCenter.f_82479_, (double)particleCenter.f_82480_, (double)particleCenter.f_82481_, (int)0, (double)dir.f_82479_, (double)dir.f_82480_, (double)dir.f_82481_, (double)0.05, (boolean)true);
                }
                int ringParticles = 50;
                for (int i = 0; i < ringParticles; ++i) {
                    double angle = (double)(i * 2) * Math.PI / (double)ringParticles;
                    double xDir = Math.cos(angle) * 0.45;
                    double yDir3 = 0.05;
                    double zDir = Math.sin(angle) * 0.45;
                    MagicManager.spawnParticles((Level)boss.m_9236_(), (ParticleOptions)TravelopticsParticleHelper.LIGHT_RED_GLOWING_ENCHANT, (double)particleCenter.f_82479_, (double)particleCenter.f_82480_, (double)particleCenter.f_82481_, (int)0, (double)xDir, (double)yDir3, (double)zDir, (double)0.05, (boolean)true);
                }
            }
        }
    }
}

