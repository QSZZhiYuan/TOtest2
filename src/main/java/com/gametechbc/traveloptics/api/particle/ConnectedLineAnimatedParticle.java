/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 *  org.joml.Vector3f
 */
package com.gametechbc.traveloptics.api.particle;

import com.gametechbc.traveloptics.particle.glowing_enchantment.GlowingEnchantmentParticleOptions;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class ConnectedLineAnimatedParticle {
    public static void createParticleLineTo(Level level, LivingEntity startEntity, LivingEntity targetEntity, AnimationType animationType, int tickCount, boolean useMagicManager, ParticleColor primaryColor, ParticleColor secondaryColor) {
        Vec3 start = startEntity.m_20182_().m_82520_(0.0, (double)startEntity.m_20206_() * 0.5, 0.0);
        Vec3 end = targetEntity.m_20182_().m_82520_(0.0, (double)targetEntity.m_20206_() * 0.5, 0.0);
        switch (animationType) {
            case SPIRAL_CONVERGE: {
                ConnectedLineAnimatedParticle.createSpiralConverge(level, start, end, tickCount, useMagicManager, primaryColor, secondaryColor);
                break;
            }
            case SPIRAL_HELIX: {
                ConnectedLineAnimatedParticle.createSpiralHelix(level, start, end, tickCount, useMagicManager, primaryColor, secondaryColor);
                break;
            }
            case PULSING_LASER: {
                ConnectedLineAnimatedParticle.createPulsingLaser(level, start, end, tickCount, useMagicManager, primaryColor, secondaryColor);
                break;
            }
            case DUAL_BEAM: {
                ConnectedLineAnimatedParticle.createDualBeam(level, start, end, tickCount, useMagicManager, primaryColor, secondaryColor);
            }
        }
    }

    public static void createParticleLineTo(Level level, LivingEntity startEntity, LivingEntity targetEntity, AnimationType animationType, int tickCount, boolean useMagicManager, ParticleColor color) {
        ConnectedLineAnimatedParticle.createParticleLineTo(level, startEntity, targetEntity, animationType, tickCount, useMagicManager, color, color);
    }

    public static void createParticleLineTo(Level level, LivingEntity startEntity, LivingEntity targetEntity, int animationType, int tickCount, boolean useMagicManager, ParticleColor primaryColor, ParticleColor secondaryColor) {
        AnimationType type = switch (animationType) {
            case 1 -> AnimationType.SPIRAL_CONVERGE;
            case 2 -> AnimationType.SPIRAL_HELIX;
            case 3 -> AnimationType.PULSING_LASER;
            case 4 -> AnimationType.DUAL_BEAM;
            default -> AnimationType.SPIRAL_CONVERGE;
        };
        ConnectedLineAnimatedParticle.createParticleLineTo(level, startEntity, targetEntity, type, tickCount, useMagicManager, primaryColor, secondaryColor);
    }

    public static void createParticleLineTo(Level level, LivingEntity startEntity, LivingEntity targetEntity, int animationType, int tickCount, boolean useMagicManager, ParticleColor color) {
        ConnectedLineAnimatedParticle.createParticleLineTo(level, startEntity, targetEntity, animationType, tickCount, useMagicManager, color, color);
    }

    public static void createParticleLineTo(Level level, LivingEntity startEntity, LivingEntity targetEntity, AnimationType animationType, int tickCount) {
        ConnectedLineAnimatedParticle.createParticleLineTo(level, startEntity, targetEntity, animationType, tickCount, false, ParticleColor.GOLD, ParticleColor.PURPLE);
    }

    public static void createParticleLineTo(Level level, LivingEntity startEntity, LivingEntity targetEntity, int animationType, int tickCount) {
        ConnectedLineAnimatedParticle.createParticleLineTo(level, startEntity, targetEntity, animationType, tickCount, false, ParticleColor.GOLD, ParticleColor.PURPLE);
    }

    public static void createParticleLineTo(Level level, LivingEntity startEntity, LivingEntity targetEntity, AnimationType animationType, int tickCount, boolean useMagicManager) {
        ConnectedLineAnimatedParticle.createParticleLineTo(level, startEntity, targetEntity, animationType, tickCount, useMagicManager, ParticleColor.GOLD, ParticleColor.PURPLE);
    }

    public static void createParticleLineTo(Level level, LivingEntity startEntity, LivingEntity targetEntity, int animationType, int tickCount, boolean useMagicManager) {
        ConnectedLineAnimatedParticle.createParticleLineTo(level, startEntity, targetEntity, animationType, tickCount, useMagicManager, ParticleColor.GOLD, ParticleColor.PURPLE);
    }

    private static void createSpiralConverge(Level level, Vec3 start, Vec3 end, int tickCount, boolean useMagicManager, ParticleColor primaryColor, ParticleColor secondaryColor) {
        double distance = start.m_82554_(end);
        int particleCount = (int)Math.max(12.0, distance * 4.0);
        double globalTime = (double)tickCount * 0.2;
        Vec3 direction = end.m_82546_(start).m_82541_();
        Vec3 perpendicular1 = new Vec3(-direction.f_82481_, 0.0, direction.f_82479_).m_82541_();
        Vec3 perpendicular2 = direction.m_82537_(perpendicular1).m_82541_();
        for (int i = 0; i < particleCount; ++i) {
            ParticleOptions particle;
            double progress = (double)i / (double)(particleCount - 1);
            Vec3 basePos = start.m_165921_(end, progress);
            double spiralAngle = progress * Math.PI * 6.0 + globalTime * 2.0;
            double spiralRadius = 0.4 * (1.0 - progress) * (0.8 + 0.2 * Math.sin(globalTime + (double)i * 0.5));
            Vec3 spiralOffset = perpendicular1.m_82490_(Math.cos(spiralAngle) * spiralRadius).m_82549_(perpendicular2.m_82490_(Math.sin(spiralAngle) * spiralRadius));
            Vec3 particlePos = basePos.m_82549_(spiralOffset);
            double travelWave = (globalTime * 1.5 + progress * Math.PI * 4.0) % (Math.PI * 2);
            if (!(Math.sin(travelWave + progress * Math.PI) > -0.5)) continue;
            boolean useSecondary = progress > 0.7 || (i + tickCount / 4) % 6 < 3;
            ParticleOptions particleOptions = particle = useSecondary ? secondaryColor.toParticleOptions() : primaryColor.toParticleOptions();
            if (useMagicManager) {
                MagicManager.spawnParticles((Level)level, (ParticleOptions)particle, (double)particlePos.f_82479_, (double)particlePos.f_82480_, (double)particlePos.f_82481_, (int)0, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)false);
                continue;
            }
            level.m_7106_(particle, particlePos.f_82479_, particlePos.f_82480_, particlePos.f_82481_, 0.0, 0.0, 0.0);
        }
    }

    private static void createSpiralHelix(Level level, Vec3 start, Vec3 end, int tickCount, boolean useMagicManager, ParticleColor primaryColor, ParticleColor secondaryColor) {
        double distance = start.m_82554_(end);
        int particleCount = (int)Math.max(15.0, distance * 6.0);
        double globalTime = (double)tickCount * 0.1;
        Vec3 direction = end.m_82546_(start).m_82541_();
        Vec3 perpendicular1 = new Vec3(-direction.f_82481_, 0.0, direction.f_82479_).m_82541_();
        Vec3 perpendicular2 = direction.m_82537_(perpendicular1).m_82541_();
        for (int i = 0; i < particleCount; ++i) {
            ParticleOptions particle;
            double progress = (double)i / (double)(particleCount - 1);
            Vec3 basePos = start.m_165921_(end, progress);
            double helixAngle = progress * Math.PI * 4.0 + globalTime;
            double radius = 0.2 * (1.0 - Math.abs(progress - 0.5) * 2.0);
            Vec3 helixOffset = perpendicular1.m_82490_(Math.cos(helixAngle) * radius).m_82549_(perpendicular2.m_82490_(Math.sin(helixAngle) * radius));
            Vec3 particlePos = basePos.m_82549_(helixOffset);
            boolean useSecondary = (i + tickCount / 2) % 8 < 4;
            ParticleOptions particleOptions = particle = useSecondary ? secondaryColor.toParticleOptions() : primaryColor.toParticleOptions();
            if (useMagicManager) {
                MagicManager.spawnParticles((Level)level, (ParticleOptions)particle, (double)particlePos.f_82479_, (double)particlePos.f_82480_, (double)particlePos.f_82481_, (int)0, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)false);
                continue;
            }
            level.m_7106_(particle, particlePos.f_82479_, particlePos.f_82480_, particlePos.f_82481_, 0.0, 0.0, 0.0);
        }
    }

    private static void createPulsingLaser(Level level, Vec3 start, Vec3 end, int tickCount, boolean useMagicManager, ParticleColor primaryColor, ParticleColor secondaryColor) {
        double distance = start.m_82554_(end);
        int particleCount = (int)Math.max(8.0, distance * 4.0);
        double globalTime = (double)tickCount * 0.3;
        double pulse = Math.abs(Math.sin(globalTime));
        for (int i = 0; i < particleCount; ++i) {
            double progress = (double)i / (double)(particleCount - 1);
            Vec3 particlePos = start.m_165921_(end, progress);
            double beamPulse = pulse * (1.0 - Math.abs(progress - 0.5) * 0.5);
            if (!(beamPulse > 0.3)) continue;
            if (useMagicManager) {
                MagicManager.spawnParticles((Level)level, (ParticleOptions)primaryColor.toParticleOptions(), (double)particlePos.f_82479_, (double)particlePos.f_82480_, (double)particlePos.f_82481_, (int)0, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)false);
            } else {
                level.m_7106_(primaryColor.toParticleOptions(), particlePos.f_82479_, particlePos.f_82480_, particlePos.f_82481_, 0.0, 0.0, 0.0);
            }
            if (!(beamPulse > 0.7)) continue;
            Vec3 direction = end.m_82546_(start).m_82541_();
            Vec3 perpendicular = new Vec3(-direction.f_82481_, 0.0, direction.f_82479_).m_82541_();
            for (int j = 0; j < 4; ++j) {
                double angle = (double)j * Math.PI * 0.5 + globalTime;
                Vec3 glowPos = particlePos.m_82549_(perpendicular.m_82490_(Math.cos(angle) * 0.1)).m_82520_(0.0, Math.sin(angle) * 0.1, 0.0);
                if (useMagicManager) {
                    MagicManager.spawnParticles((Level)level, (ParticleOptions)secondaryColor.toParticleOptions(), (double)glowPos.f_82479_, (double)glowPos.f_82480_, (double)glowPos.f_82481_, (int)0, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)false);
                    continue;
                }
                level.m_7106_(secondaryColor.toParticleOptions(), glowPos.f_82479_, glowPos.f_82480_, glowPos.f_82481_, 0.0, 0.0, 0.0);
            }
        }
    }

    private static void createDualBeam(Level level, Vec3 start, Vec3 end, int tickCount, boolean useMagicManager, ParticleColor primaryColor, ParticleColor secondaryColor) {
        double distance = start.m_82554_(end);
        int particleCount = (int)Math.max(8.0, distance * 4.0);
        double globalTime = (double)tickCount * 0.2;
        Vec3 direction = end.m_82546_(start).m_82541_();
        Vec3 perpendicular = new Vec3(-direction.f_82481_, 0.0, direction.f_82479_).m_82541_();
        double beamSeparation = 0.2;
        for (int beam = 0; beam < 2; ++beam) {
            double beamOffset = beam == 0 ? -beamSeparation : beamSeparation;
            Vec3 beamStart = start.m_82549_(perpendicular.m_82490_(beamOffset));
            Vec3 beamEnd = end.m_82549_(perpendicular.m_82490_(beamOffset));
            for (int i = 0; i < particleCount; ++i) {
                ParticleOptions particle;
                double progress = (double)i / (double)(particleCount - 1);
                Vec3 particlePos = beamStart.m_165921_(beamEnd, progress);
                double travelWave = (globalTime + progress * Math.PI * 3.0 + (double)beam * Math.PI) % (Math.PI * 2);
                if (!(Math.sin(travelWave) > 0.0)) continue;
                ParticleOptions particleOptions = particle = beam == 0 ? primaryColor.toParticleOptions() : secondaryColor.toParticleOptions();
                if (useMagicManager) {
                    MagicManager.spawnParticles((Level)level, (ParticleOptions)particle, (double)particlePos.f_82479_, (double)particlePos.f_82480_, (double)particlePos.f_82481_, (int)0, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)false);
                    continue;
                }
                level.m_7106_(particle, particlePos.f_82479_, particlePos.f_82480_, particlePos.f_82481_, 0.0, 0.0, 0.0);
            }
        }
    }

    public static enum AnimationType {
        SPIRAL_CONVERGE(1),
        SPIRAL_HELIX(2),
        PULSING_LASER(3),
        DUAL_BEAM(4);

        private final int id;

        private AnimationType(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }
    }

    public static class ParticleColor {
        private final Vector3f color;
        private final float scale;
        private final boolean hasPhysics;
        private final int lifespan;
        public static final ParticleColor PURPLE = new ParticleColor(0.631373f, 0.32549f, 0.996078f, 0.06f, false, 1);
        public static final ParticleColor GOLD = new ParticleColor(0.996f, 0.843f, 0.325f, 0.06f, false, 1);
        public static final ParticleColor RED = new ParticleColor(0.996f, 0.325f, 0.325f, 0.06f, false, 1);
        public static final ParticleColor BLUE = new ParticleColor(0.325f, 0.325f, 0.996f, 0.06f, false, 1);
        public static final ParticleColor GREEN = new ParticleColor(0.325f, 0.996f, 0.325f, 0.06f, false, 1);
        public static final ParticleColor WHITE = new ParticleColor(1.0f, 1.0f, 1.0f, 0.06f, false, 1);
        public static final ParticleColor BLOOD_RED = new ParticleColor(0.545f, 0.0f, 0.0f, 0.06f, false, 1);
        public static final ParticleColor DARK_ORANGE = new ParticleColor(1.0f, 0.549f, 0.0f, 0.06f, false, 1);

        public ParticleColor(Vector3f color, float scale, boolean hasPhysics, int lifespan) {
            this.color = color;
            this.scale = scale;
            this.hasPhysics = hasPhysics;
            this.lifespan = lifespan;
        }

        public ParticleColor(float r, float g, float b, float scale, boolean hasPhysics, int lifespan) {
            this(new Vector3f(r, g, b), scale, hasPhysics, lifespan);
        }

        public ParticleOptions toParticleOptions() {
            return new GlowingEnchantmentParticleOptions(this.color, this.scale, this.hasPhysics, this.lifespan);
        }
    }
}

