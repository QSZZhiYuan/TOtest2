/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity
 *  com.github.L_Ender.cataclysm.init.ModSounds
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.util.Mth
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.VoxelShape
 */
package com.gametechbc.traveloptics.entity.mobs.nightwarden_boss;

import com.gametechbc.traveloptics.config.EntityConfig;
import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.NightwardenBossEntity;
import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.misc.EndEruptionEntity;
import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.nightwarden_clone.nightwarden_drop_slam_clone.NightwardenDropSlamCloneEntity;
import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.nightwarden_clone.nightwarden_explode_clone.NightwardenExplodeCloneEntity;
import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.nightwarden_clone.nightwarden_slam_clone.NightwardenSlamCloneEntity;
import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.nightwarden_clone.nightwarden_slash_clone.NightwardenSlashCloneEntity;
import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.nightwarden_clone.nightwarden_spin_clone.NightwardenSpinCloneEntity;
import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.nightwarden_slash_visual.NightwardenVisualSlashEntity;
import com.gametechbc.traveloptics.entity.projectiles.dimensional_spike.DimensionalSpikeEntity;
import com.gametechbc.traveloptics.entity.projectiles.dragon_spirit.DragonSpiritEntity;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import com.gametechbc.traveloptics.util.TravelopticsParticleHelper;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.init.ModSounds;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class NightwardenAttackHelper {
    private static final float cloneSlashesMultiplierFromBase = ((Double)EntityConfig.cloneSlashesMultiplierFromBase.get()).floatValue();
    private static final float cloneSlashesHpDamage = ((Double)EntityConfig.cloneSlashesHpDamage.get()).floatValue();
    private static final float dimensionalSpikeDamageMultiplierFromBase = ((Double)EntityConfig.dimensionalSpikeDamageMultiplierFromBase.get()).floatValue();
    private static final float spinCloneDamageMultiplierFromBase = ((Double)EntityConfig.spinCloneDamageMultiplierFromBase.get()).floatValue();
    private static final float spinClonesHpDamage = ((Double)EntityConfig.spinClonesHpDamage.get()).floatValue();
    private static final float dropCloneDamageMultiplierFromBase = ((Double)EntityConfig.dropCloneDamageMultiplierFromBase.get()).floatValue();
    private static final float dropClonesHpDamage = ((Double)EntityConfig.dropClonesHpDamage.get()).floatValue();
    private static final float endEruptionDamageMultiplierFromBase = ((Double)EntityConfig.endEruptionDamageMultiplierFromBase.get()).floatValue();
    private static final float explodeSpinCloneTrailDamageMultiplierFromBase = ((Double)EntityConfig.explodeSpinCloneTrailDamageMultiplierFromBase.get()).floatValue();
    private static final float explodeSpinCloneTrailHpDamage = ((Double)EntityConfig.explodeSpinCloneTrailHpDamage.get()).floatValue();
    private static final float dragonSpiritHpDamage = ((Double)EntityConfig.dragonSpiritHpDamage.get()).floatValue();

    public static void spawnSpikeRing(NightwardenBossEntity nightwarden, Vec3 center, double radius, int spikeCount, double minY, double maxY, int delay, LivingEntity owner) {
        for (int k = 0; k < spikeCount; ++k) {
            float angle = (float)k * (float)Math.PI * 2.0f / (float)spikeCount;
            double x = center.f_82479_ + (double)Mth.m_14089_((float)angle) * radius;
            double z = center.f_82481_ + (double)Mth.m_14031_((float)angle) * radius;
            float outwardRotation = (float)Math.toDegrees(angle) + 270.0f;
            NightwardenAttackHelper.spawnDimensionalSpike(nightwarden, x, z, minY, maxY, outwardRotation, delay, owner);
        }
    }

    public static void spawnDimensionalSpike(NightwardenBossEntity nightwarden, double x, double z, double minY, double maxY, float rotation, int delay, LivingEntity owner) {
        BlockPos pos = BlockPos.m_274561_((double)x, (double)maxY, (double)z);
        boolean foundGround = false;
        double yOffset = 0.0;
        float damage = nightwarden.getResurgenceScaledAttack() * dimensionalSpikeDamageMultiplierFromBase;
        do {
            VoxelShape shape;
            BlockPos below = pos.m_7495_();
            if (!nightwarden.m_9236_().m_8055_(below).m_60783_((BlockGetter)nightwarden.m_9236_(), below, Direction.UP)) continue;
            if (!nightwarden.m_9236_().m_46859_(pos) && !(shape = nightwarden.m_9236_().m_8055_(pos).m_60812_((BlockGetter)nightwarden.m_9236_(), pos)).m_83281_()) {
                yOffset = shape.m_83297_(Direction.Axis.Y);
            }
            foundGround = true;
            break;
        } while ((pos = pos.m_7495_()).m_123342_() >= Mth.m_14107_((double)minY) - 1);
        if (foundGround) {
            nightwarden.m_9236_().m_7967_((Entity)new DimensionalSpikeEntity(nightwarden.m_9236_(), x, (double)pos.m_123342_() + yOffset, z, rotation, delay, damage, owner));
        }
    }

    public static void playSound(NightwardenBossEntity nightwarden, int type, float volume) {
        if (type == 0) {
            nightwarden.m_5496_((SoundEvent)TravelopticsSounds.NIGHTWARDEN_SWING.get(), volume, (float)Mth.m_216287_((RandomSource)nightwarden.m_217043_(), (int)9, (int)13) * 0.1f);
        } else if (type == 1) {
            nightwarden.m_5496_((SoundEvent)TravelopticsSounds.NIGHTWARDEN_SWING_1.get(), volume, (float)Mth.m_216287_((RandomSource)nightwarden.m_217043_(), (int)9, (int)13) * 0.1f);
        } else if (type == 2) {
            nightwarden.m_5496_((SoundEvent)TravelopticsSounds.NIGHTWARDEN_SWING_HEAVY.get(), volume, 1.0f);
        } else if (type == 3) {
            nightwarden.m_5496_((SoundEvent)TravelopticsSounds.NIGHTWARDEN_FINGER_SNAP.get(), volume, 1.0f);
        } else if (type == 4) {
            nightwarden.m_5496_((SoundEvent)TravelopticsSounds.SPECTRAL_BLINK_SUCCESS.get(), volume, 1.0f);
        } else if (type == 5) {
            nightwarden.m_5496_((SoundEvent)ModSounds.ENDER_GUARDIAN_FIST.get(), volume, 1.0f);
        } else if (type == 6) {
            nightwarden.m_5496_((SoundEvent)TravelopticsSounds.NIGHTWARDEN_WALK.get(), volume, 1.0f);
        } else if (type == 7) {
            nightwarden.m_5496_((SoundEvent)TravelopticsSounds.NIGHTWARDEN_BIG_SLAM.get(), volume, 1.0f);
        } else if (type == 8) {
            nightwarden.m_5496_((SoundEvent)TravelopticsSounds.NIGHTWARDEN_BIG_SLAM_CLONES.get(), volume, 1.0f);
        } else if (type == 9) {
            nightwarden.m_5496_((SoundEvent)TravelopticsSounds.NOCTURNAL_UPLIFT.get(), volume, 1.0f);
        } else if (type == 10) {
            nightwarden.m_5496_((SoundEvent)TravelopticsSounds.NIGHTWARDEN_SUMMON_CLONE.get(), volume, 1.0f);
        } else if (type == 11) {
            nightwarden.m_5496_((SoundEvent)TravelopticsSounds.NIGHTWARDEN_SUMMON_CLONE_1.get(), volume, 1.0f);
        }
    }

    public static void spawnTeleportParticles(Level level, Vec3 pos) {
        if (!level.f_46443_) {
            double width = 1.6;
            float height = 2.5f;
            for (int i = 0; i < 50; ++i) {
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

    public static void spawnSlashVisual(NightwardenBossEntity nightwarden, float forwardOffset, float verticalOffset, boolean mirrored, boolean behind, boolean lockPitch, boolean ignorePitchPosition) {
        Vec3 forward = nightwarden.m_20154_().m_82541_();
        if (ignorePitchPosition) {
            forward = new Vec3(forward.f_82479_, 0.0, forward.f_82481_).m_82541_();
        }
        Vec3 spawnPos = nightwarden.m_20182_().m_82520_(0.0, (double)(nightwarden.m_20206_() * verticalOffset), 0.0).m_82549_(forward.m_82490_((double)forwardOffset));
        NightwardenVisualSlashEntity visual = new NightwardenVisualSlashEntity(nightwarden.m_9236_(), mirrored);
        visual.m_20219_(spawnPos);
        float yRot = behind ? nightwarden.m_146908_() + 180.0f : nightwarden.m_146908_();
        float xRot = lockPitch ? 0.0f : nightwarden.m_146909_();
        visual.m_146922_(yRot);
        visual.m_146926_(xRot);
        nightwarden.m_9236_().m_7967_((Entity)visual);
    }

    public static void spawnAdaptiveCloneSlash(NightwardenBossEntity nightwarden, boolean isLeft) {
        double distanceToTarget;
        LivingEntity target = nightwarden.m_5448_();
        if (target != null && !target.m_21224_() && (distanceToTarget = nightwarden.m_20182_().m_82554_(target.m_20182_())) > 15.0) {
            NightwardenAttackHelper.spawnTargetingCloneSlash(nightwarden, isLeft);
            return;
        }
        NightwardenAttackHelper.spawnCloneSlash(nightwarden, isLeft);
    }

    public static void spawnCloneSlash(NightwardenBossEntity nightwarden, boolean isLeft) {
        Vec3 look = nightwarden.m_20154_();
        Vec3 flatLook = new Vec3(look.f_82479_, 0.0, look.f_82481_).m_82541_();
        Vec3 side = flatLook.m_82537_(new Vec3(0.0, 1.0, 0.0)).m_82541_();
        if (isLeft) {
            side = side.m_82490_(-1.0);
        }
        Vec3 spawnPos = nightwarden.m_20182_().m_82549_(side.m_82490_(4.0)).m_82520_(0.0, 0.1, 0.0);
        NightwardenAttackHelper.spawnTeleportParticles(nightwarden.m_9236_(), spawnPos);
        ScreenShake_Entity.ScreenShake((Level)nightwarden.m_9236_(), (Vec3)nightwarden.m_20182_(), (float)15.0f, (float)0.04f, (int)6, (int)8);
        NightwardenAttackHelper.playSound(nightwarden, 3, 2.5f);
        NightwardenAttackHelper.playSound(nightwarden, 11, 2.5f);
        float yawOffset = isLeft ? 30.0f : -30.0f;
        NightwardenSlashCloneEntity clone = new NightwardenSlashCloneEntity(nightwarden.m_9236_(), (LivingEntity)nightwarden, isLeft, yawOffset);
        clone.m_146884_(spawnPos);
        clone.setMagicDamageMode(true);
        clone.setDamage(nightwarden.getResurgenceScaledAttack() * cloneSlashesMultiplierFromBase);
        clone.setHpBasedDamagePercent(cloneSlashesHpDamage);
        clone.setLifestealPercent(0.75f);
        nightwarden.m_9236_().m_7967_((Entity)clone);
    }

    public static void spawnTargetingCloneSlash(NightwardenBossEntity nightwarden, boolean isLeft) {
        LivingEntity target = nightwarden.m_5448_();
        if (target == null || target.m_21224_()) {
            NightwardenAttackHelper.spawnCloneSlash(nightwarden, isLeft);
            return;
        }
        Vec3 look = nightwarden.m_20154_();
        Vec3 flatLook = new Vec3(look.f_82479_, 0.0, look.f_82481_).m_82541_();
        Vec3 side = flatLook.m_82537_(new Vec3(0.0, 1.0, 0.0)).m_82541_();
        if (isLeft) {
            side = side.m_82490_(-1.0);
        }
        Vec3 spawnPos = nightwarden.m_20182_().m_82549_(side.m_82490_(4.0)).m_82520_(0.0, 0.1, 0.0);
        NightwardenAttackHelper.spawnTeleportParticles(nightwarden.m_9236_(), spawnPos);
        ScreenShake_Entity.ScreenShake((Level)nightwarden.m_9236_(), (Vec3)nightwarden.m_20182_(), (float)15.0f, (float)0.04f, (int)6, (int)8);
        NightwardenAttackHelper.playSound(nightwarden, 3, 2.5f);
        NightwardenAttackHelper.playSound(nightwarden, 11, 2.5f);
        float dynamicYawOffset = NightwardenAttackHelper.calculateCloneRotationToTarget(spawnPos, target.m_20182_(), nightwarden.m_20154_(), isLeft);
        NightwardenSlashCloneEntity clone = new NightwardenSlashCloneEntity(nightwarden.m_9236_(), (LivingEntity)nightwarden, isLeft, dynamicYawOffset);
        clone.m_146884_(spawnPos);
        clone.setMagicDamageMode(true);
        clone.setDamage(nightwarden.getResurgenceScaledAttack() * cloneSlashesMultiplierFromBase);
        clone.setHpBasedDamagePercent(cloneSlashesHpDamage);
        clone.setLifestealPercent(0.75f);
        nightwarden.m_9236_().m_7967_((Entity)clone);
    }

    private static float calculateCloneRotationToTarget(Vec3 clonePos, Vec3 targetPos, Vec3 bossLookAngle, boolean isLeft) {
        Vec3 cloneToTarget = targetPos.m_82546_(clonePos).m_82541_();
        Vec3 bossForward = new Vec3(bossLookAngle.f_82479_, 0.0, bossLookAngle.f_82481_).m_82541_();
        double dotProduct = bossForward.m_82526_(cloneToTarget);
        Vec3 crossProduct = bossForward.m_82537_(cloneToTarget);
        double angleRad = Math.atan2(crossProduct.f_82480_, dotProduct);
        float angleDeg = (float)Math.toDegrees(angleRad);
        float maxAngle = 60.0f;
        float minAngle = 15.0f;
        angleDeg = isLeft ? Math.max(minAngle, Math.min(maxAngle, Math.abs(angleDeg))) : -Math.max(minAngle, Math.min(maxAngle, Math.abs(angleDeg)));
        return angleDeg;
    }

    public static void spawnBigSlamClone(NightwardenBossEntity nightwarden, boolean isLeft) {
        Vec3 look = nightwarden.m_20154_();
        Vec3 flatLook = new Vec3(look.f_82479_, 0.0, look.f_82481_).m_82541_();
        Vec3 side = flatLook.m_82537_(new Vec3(0.0, 1.0, 0.0)).m_82541_();
        if (isLeft) {
            side = side.m_82490_(-1.0);
        }
        Vec3 spawnPos = nightwarden.m_20182_().m_82549_(side.m_82490_(5.0)).m_82520_(0.0, 0.1, 0.0);
        NightwardenAttackHelper.spawnTeleportParticles(nightwarden.m_9236_(), spawnPos);
        ScreenShake_Entity.ScreenShake((Level)nightwarden.m_9236_(), (Vec3)nightwarden.m_20182_(), (float)15.0f, (float)0.04f, (int)6, (int)8);
        NightwardenSlamCloneEntity slamCloneEntity = new NightwardenSlamCloneEntity(nightwarden.m_9236_(), (LivingEntity)nightwarden);
        slamCloneEntity.m_146884_(spawnPos);
        slamCloneEntity.setSpawnsRunes(true);
        slamCloneEntity.setLines(15.0f);
        slamCloneEntity.setDamage(nightwarden.getResurgenceScaledAttack() * dimensionalSpikeDamageMultiplierFromBase);
        nightwarden.m_9236_().m_7967_((Entity)slamCloneEntity);
    }

    public static void spawnDimensionalSpikeLine(NightwardenBossEntity boss, float initialOffset, float spacing, int count) {
        double dz;
        double yawRad = Math.toRadians(boss.m_146908_());
        double dx = -Mth.m_14031_((float)((float)yawRad));
        double px = dz = (double)Mth.m_14089_((float)((float)yawRad));
        double pz = -dx;
        float damage = boss.getResurgenceScaledAttack() * dimensionalSpikeDamageMultiplierFromBase;
        int lateralCount = 5;
        float lateralSpacing = 1.5f;
        int centerIndex = lateralCount / 2;
        for (int i = 0; i < count; ++i) {
            double distance = initialOffset + (float)i * spacing;
            for (int j = 0; j < lateralCount; ++j) {
                int offsetFromCenter = Math.abs(j - centerIndex);
                int laneDelay = offsetFromCenter * 4;
                int warmup = i * 2 + laneDelay;
                double x = boss.m_20185_() + dx * distance + px * (double)(j - centerIndex) * (double)lateralSpacing;
                double z = boss.m_20189_() + dz * distance + pz * (double)(j - centerIndex) * (double)lateralSpacing;
                double y = boss.m_20186_();
                NightwardenAttackHelper.spawnDimensionalSpikes(x, y, z, boss.m_146908_(), warmup, boss.m_9236_(), (LivingEntity)boss, damage);
            }
        }
    }

    private static boolean spawnDimensionalSpikes(double x, double y, double z, float yRot, int warmupDelayTicks, Level world, LivingEntity summoner, float damage) {
        BlockPos blockpos = BlockPos.m_274561_((double)x, (double)y, (double)z);
        boolean foundGround = false;
        double surfaceY = 0.0;
        while (blockpos.m_123342_() > world.m_141937_()) {
            BlockPos below = blockpos.m_7495_();
            BlockState stateBelow = world.m_8055_(below);
            if (stateBelow.m_60783_((BlockGetter)world, below, Direction.UP)) {
                BlockState stateAt;
                VoxelShape shape;
                if (!world.m_46859_(blockpos) && !(shape = (stateAt = world.m_8055_(blockpos)).m_60812_((BlockGetter)world, blockpos)).m_83281_()) {
                    surfaceY = shape.m_83297_(Direction.Axis.Y);
                }
                foundGround = true;
                break;
            }
            blockpos = below;
        }
        if (foundGround) {
            double finalY = (double)blockpos.m_123342_() + surfaceY;
            DimensionalSpikeEntity spike = new DimensionalSpikeEntity(world, x, finalY, z, yRot, warmupDelayTicks, damage, summoner);
            world.m_7967_((Entity)spike);
            return true;
        }
        return false;
    }

    public static void applyHovering(LivingEntity entity, double preferredY, double bobDistance, double bobFrequency, boolean onGround) {
        double time;
        double bobbing;
        Level level = entity.m_9236_();
        BlockPos groundPos = entity.m_20183_().m_7495_();
        while (level.m_46859_(groundPos) && groundPos.m_123342_() > level.m_141937_()) {
            groundPos = groundPos.m_7495_();
        }
        double groundY = groundPos.m_123342_() + 1;
        double baseHoverY = groundY + preferredY;
        double targetY = baseHoverY + (bobbing = bobDistance * Math.sin(time = (double)entity.f_19797_ * bobFrequency));
        double deltaY = targetY - entity.m_20186_();
        if (Math.abs(deltaY) > 0.05) {
            Vec3 motion = entity.m_20184_();
            entity.m_20334_(motion.f_82479_, deltaY * 0.2, motion.f_82481_);
        }
        entity.f_19789_ = 0.0f;
        entity.m_6853_(onGround);
    }

    public static void spawnSpinCloneToSide(NightwardenBossEntity nightwarden, boolean isLeft) {
        Vec3 look = nightwarden.m_20154_();
        Vec3 flatLook = new Vec3(look.f_82479_, 0.0, look.f_82481_).m_82541_();
        Vec3 side = flatLook.m_82537_(new Vec3(0.0, 1.0, 0.0)).m_82541_();
        if (isLeft) {
            side = side.m_82490_(-1.0);
        }
        Vec3 spawnPos = nightwarden.m_20182_().m_82549_(side.m_82490_(4.0)).m_82520_(0.0, 0.1, 0.0);
        NightwardenAttackHelper.spawnTeleportParticles(nightwarden.m_9236_(), spawnPos);
        ScreenShake_Entity.ScreenShake((Level)nightwarden.m_9236_(), (Vec3)nightwarden.m_20182_(), (float)15.0f, (float)0.04f, (int)6, (int)8);
        NightwardenAttackHelper.playSound(nightwarden, 4, 2.5f);
        float yawOffset = isLeft ? 20.0f : -20.0f;
        NightwardenSpinCloneEntity clone = new NightwardenSpinCloneEntity(nightwarden.m_9236_(), (LivingEntity)nightwarden, yawOffset);
        clone.m_146884_(spawnPos);
        clone.setRadius(2.1f);
        clone.setMovementSpeed(0.65f);
        clone.setMagicDamageMode(true);
        clone.setDamage(nightwarden.getResurgenceScaledAttack() * spinCloneDamageMultiplierFromBase);
        clone.setHpBasedDamagePercent(spinClonesHpDamage);
        nightwarden.m_9236_().m_7967_((Entity)clone);
    }

    public static void showSpinCloneWarningPath(NightwardenBossEntity nightwarden, boolean isLeft) {
        Vec3 look = nightwarden.m_20154_();
        Vec3 flatLook = new Vec3(look.f_82479_, 0.0, look.f_82481_).m_82541_();
        Vec3 side = flatLook.m_82537_(new Vec3(0.0, 1.0, 0.0)).m_82541_();
        if (isLeft) {
            side = side.m_82490_(-1.0);
        }
        Vec3 spawnPos = nightwarden.m_20182_().m_82549_(side.m_82490_(4.0)).m_82520_(0.0, 0.1, 0.0);
        float yawOffset = isLeft ? 20.0f : -20.0f;
        float totalYaw = nightwarden.m_146908_() + yawOffset;
        float radians = (float)Math.toRadians(totalYaw);
        Vec3 moveDirection = new Vec3(-Math.sin(radians), 0.0, Math.cos(radians)).m_82541_();
        int pathLength = 30;
        float aoRadius = 2.0f;
        int particlesPerBlock = 5;
        int particlesAcrossWidth = 15;
        for (int i = 0; i < pathLength * particlesPerBlock; ++i) {
            float distance = (float)i / (float)particlesPerBlock;
            Vec3 pathCenter = spawnPos.m_82549_(moveDirection.m_82490_((double)distance));
            for (int w = 0; w < particlesAcrossWidth; ++w) {
                float widthOffset = ((float)w - (float)(particlesAcrossWidth - 1) / 2.0f) * (aoRadius * 2.0f / (float)(particlesAcrossWidth - 1));
                Vec3 perpendicular = new Vec3(-moveDirection.f_82481_, 0.0, moveDirection.f_82479_).m_82541_();
                Vec3 basePos = pathCenter.m_82549_(perpendicular.m_82490_((double)widthOffset));
                RandomSource random = nightwarden.m_9236_().f_46441_;
                float randomX = (random.m_188501_() - 0.5f) * 0.8f;
                float randomY = (random.m_188501_() - 0.5f) * 0.3f;
                float randomZ = (random.m_188501_() - 0.5f) * 0.8f;
                Vec3 particlePos = basePos.m_82520_((double)randomX, (double)randomY, (double)randomZ);
                MagicManager.spawnParticles((Level)nightwarden.m_9236_(), (ParticleOptions)TravelopticsParticleHelper.WARNING_PATH_RED_GLOWING_ENCHANT, (double)particlePos.f_82479_, (double)particlePos.f_82480_, (double)particlePos.f_82481_, (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.3, (boolean)false);
            }
        }
    }

    public static void spawnPredictiveDropSlamClone(Level level, NightwardenBossEntity boss, LivingEntity target, Vec3 offset, Vec3 trackedVelocity) {
        if (target == null || !target.m_6084_()) {
            return;
        }
        Vec3 predictedTargetPos = NightwardenAttackHelper.calculateSimplePrediction(target, trackedVelocity);
        Vec3 finalTargetPos = predictedTargetPos.m_82520_(offset.f_82479_, 0.0, offset.f_82481_);
        Vec3 clonePos = new Vec3(finalTargetPos.f_82479_, target.m_20186_() + 8.0 + offset.f_82480_, finalTargetPos.f_82481_);
        Vec3 bossPos = boss.m_20182_();
        NightwardenAttackHelper.spawnSummoningParticles(level, bossPos, finalTargetPos, clonePos);
        NightwardenDropSlamCloneEntity clone = new NightwardenDropSlamCloneEntity(level, (LivingEntity)boss);
        clone.m_7678_(clonePos.f_82479_, clonePos.f_82480_, clonePos.f_82481_, boss.m_146908_(), boss.m_146909_());
        clone.setRadius(2.0f);
        clone.setDownwardSpeed(-0.7f);
        clone.setShouldApplyEffect(true);
        clone.setMagicDamageMode(true);
        clone.setDamage(boss.getResurgenceScaledAttack() * dropCloneDamageMultiplierFromBase);
        clone.setHpBasedDamagePercent(dropClonesHpDamage);
        level.m_7967_((Entity)clone);
    }

    private static Vec3 calculateSimplePrediction(LivingEntity target, Vec3 trackedVelocity) {
        Vec3 currentPos = target.m_20182_();
        double speed = trackedVelocity.m_165924_();
        if (speed < 0.02) {
            return currentPos;
        }
        double predictionDistance = speed < 0.1 ? 1.0 : (speed < 0.2 ? 2.0 : 3.0);
        Vec3 direction = trackedVelocity.m_82541_();
        Vec3 prediction = direction.m_82542_(predictionDistance, 0.0, predictionDistance);
        return currentPos.m_82549_(prediction);
    }

    public static void spawnDropSlamCloneOffsetWithParticles(Level level, NightwardenBossEntity boss, LivingEntity target, Vec3 offset) {
        if (target == null || !target.m_6084_()) {
            return;
        }
        Vec3 clonePos = new Vec3(target.m_20185_() + offset.f_82479_, target.m_20186_() + 8.0 + offset.f_82480_, target.m_20189_() + offset.f_82481_);
        Vec3 targetPos = target.m_20182_().m_82520_(offset.f_82479_, 0.0, offset.f_82481_);
        Vec3 bossPos = boss.m_20182_();
        NightwardenAttackHelper.spawnSummoningParticles(level, bossPos, targetPos, clonePos);
        NightwardenDropSlamCloneEntity clone = new NightwardenDropSlamCloneEntity(level, (LivingEntity)boss);
        clone.m_7678_(clonePos.f_82479_, clonePos.f_82480_, clonePos.f_82481_, boss.m_146908_(), boss.m_146909_());
        clone.setRadius(1.8f);
        clone.setDownwardSpeed(-0.7f);
        clone.setShouldApplyEffect(true);
        clone.setMagicDamageMode(true);
        clone.setDamage(boss.getResurgenceScaledAttack() * dropCloneDamageMultiplierFromBase);
        clone.setHpBasedDamagePercent(dropClonesHpDamage);
        level.m_7967_((Entity)clone);
    }

    private static void spawnSummoningParticles(Level level, Vec3 bossPos, Vec3 targetPos, Vec3 clonePos) {
        NightwardenAttackHelper.spawnBossChargeParticles(level, bossPos);
        NightwardenAttackHelper.spawnTargetLocationParticles(level, targetPos, 40);
        NightwardenAttackHelper.spawnCloneSummoningParticles(level, clonePos);
    }

    private static void spawnBossChargeParticles(Level level, Vec3 bossPos) {
        double radius;
        double angle;
        int i;
        for (i = 0; i < 20; ++i) {
            angle = (double)i * Math.PI * 2.0 / 10.0;
            radius = 1.5 + (double)i * 0.1;
            double height = (double)i * 0.15;
            double x = bossPos.f_82479_ + Math.cos(angle) * radius;
            double y = bossPos.f_82480_ + 1.0 + height;
            double z = bossPos.f_82481_ + Math.sin(angle) * radius;
            double velX = -Math.cos(angle) * 0.1 + (Math.random() - 0.5) * 0.05;
            double velY = 0.15 + Math.random() * 0.1;
            double velZ = -Math.sin(angle) * 0.1 + (Math.random() - 0.5) * 0.05;
            MagicManager.spawnParticles((Level)level, (ParticleOptions)TravelopticsParticleHelper.ABYSS_SPIKE_PARTICLE, (double)x, (double)y, (double)z, (int)1, (double)velX, (double)velY, (double)velZ, (double)0.3, (boolean)true);
        }
        for (i = 0; i < 8; ++i) {
            angle = Math.random() * Math.PI * 2.0;
            radius = 0.8 + Math.random() * 0.7;
            double x = bossPos.f_82479_ + Math.cos(angle) * radius;
            double y = bossPos.f_82480_ + 1.2 + Math.random() * 0.8;
            double z = bossPos.f_82481_ + Math.sin(angle) * radius;
            MagicManager.spawnParticles((Level)level, (ParticleOptions)TravelopticsParticleHelper.SHORT_LIGHT_PURPLE_GLOWING_ENCHANT, (double)x, (double)y, (double)z, (int)1, (double)0.0, (double)0.1, (double)0.0, (double)0.3, (boolean)true);
        }
    }

    private static void spawnTargetLocationParticles(Level level, Vec3 targetPos, int count) {
        double radius = 1.8;
        for (int i = 0; i < count; ++i) {
            double angle = (double)i * Math.PI * 2.0 / (double)count;
            double x = targetPos.f_82479_ + Math.cos(angle) * radius;
            double y = targetPos.f_82480_ + 0.1;
            double z = targetPos.f_82481_ + Math.sin(angle) * radius;
            MagicManager.spawnParticles((Level)level, (ParticleOptions)TravelopticsParticleHelper.WARNING_PATH_RED_GLOWING_ENCHANT, (double)x, (double)y, (double)z, (int)1, (double)0.0, (double)0.05, (double)0.0, (double)0.3, (boolean)false);
        }
    }

    private static void spawnCloneSummoningParticles(Level level, Vec3 clonePos) {
        int i;
        for (int ring = 0; ring < 3; ++ring) {
            double ringRadius = (double)(ring + 1) * 1.2;
            int particlesInRing = 12 + ring * 4;
            for (int i2 = 0; i2 < particlesInRing; ++i2) {
                double angle = (double)i2 * Math.PI * 2.0 / (double)particlesInRing;
                double x = clonePos.f_82479_ + Math.cos(angle) * ringRadius;
                double y = clonePos.f_82480_ - (double)ring * 0.5;
                double z = clonePos.f_82481_ + Math.sin(angle) * ringRadius;
                double velX = -Math.cos(angle) * (0.15 - (double)ring * 0.03);
                double velY = 0.2 + (double)ring * 0.1;
                double velZ = -Math.sin(angle) * (0.15 - (double)ring * 0.03);
                MagicManager.spawnParticles((Level)level, (ParticleOptions)TravelopticsParticleHelper.ABYSS_SPIKE_PARTICLE, (double)x, (double)y, (double)z, (int)1, (double)velX, (double)velY, (double)velZ, (double)0.3, (boolean)true);
            }
        }
        for (i = 0; i < 15; ++i) {
            double height = (double)i * 0.4;
            double spread = 0.3 + Math.random() * 0.4;
            double x = clonePos.f_82479_ + (Math.random() - 0.5) * spread;
            double y = clonePos.f_82480_ - 2.0 + height;
            double z = clonePos.f_82481_ + (Math.random() - 0.5) * spread;
            MagicManager.spawnParticles((Level)level, (ParticleOptions)TravelopticsParticleHelper.SHORT_LIGHT_PURPLE_GLOWING_ENCHANT, (double)x, (double)y, (double)z, (int)1, (double)0.0, (double)(0.15 + Math.random() * 0.1), (double)0.0, (double)0.3, (boolean)true);
        }
        for (i = 0; i < 25; ++i) {
            double t = (double)i / 25.0;
            double angle = t * Math.PI * 4.0;
            double radius = 2.0 * (1.0 - t);
            double height = t * 3.0;
            double x = clonePos.f_82479_ + Math.cos(angle) * radius;
            double y = clonePos.f_82480_ - 1.5 + height;
            double z = clonePos.f_82481_ + Math.sin(angle) * radius;
            double velX = -Math.sin(angle) * 0.2 - Math.cos(angle) * 0.1;
            double velY = 0.3;
            double velZ = Math.cos(angle) * 0.2 - Math.sin(angle) * 0.1;
            MagicManager.spawnParticles((Level)level, (ParticleOptions)TravelopticsParticleHelper.ABYSS_SPIKE_PARTICLE, (double)x, (double)y, (double)z, (int)1, (double)velX, (double)velY, (double)velZ, (double)0.3, (boolean)true);
        }
        for (i = 0; i < 12; ++i) {
            double angle = (double)i * Math.PI * 2.0 / 12.0;
            double speed = 0.3 + Math.random() * 0.2;
            double velX = Math.cos(angle) * speed;
            double velY = 0.1 + Math.random() * 0.15;
            double velZ = Math.sin(angle) * speed;
            MagicManager.spawnParticles((Level)level, (ParticleOptions)TravelopticsParticleHelper.SHORT_LIGHT_PURPLE_GLOWING_ENCHANT, (double)clonePos.f_82479_, (double)clonePos.f_82480_, (double)clonePos.f_82481_, (int)1, (double)velX, (double)velY, (double)velZ, (double)0.3, (boolean)true);
        }
    }

    public static void spawnEruptionAt(NightwardenBossEntity nightwarden, Vec3 pos) {
        EndEruptionEntity eruption = new EndEruptionEntity(nightwarden.m_9236_());
        eruption.m_7678_(pos.f_82479_, pos.f_82480_, pos.f_82481_, 0.0f, 0.0f);
        eruption.setWindupDuration(60);
        eruption.setDamage(nightwarden.getResurgenceScaledAttack() * endEruptionDamageMultiplierFromBase);
        nightwarden.m_9236_().m_7967_((Entity)eruption);
    }

    public static void spawnRandomEruptionAroundTarget(Level level, NightwardenBossEntity boss, LivingEntity target, double minDistance, double maxDistance) {
        if (target == null || !target.m_6084_()) {
            return;
        }
        RandomSource random = level.m_213780_();
        double angle = random.m_188500_() * 2.0 * Math.PI;
        double distance = minDistance + random.m_188500_() * (maxDistance - minDistance);
        double offsetX = Math.cos(angle) * distance;
        double offsetZ = Math.sin(angle) * distance;
        Vec3 spawnPos = new Vec3(target.m_20185_() + offsetX, target.m_20186_(), target.m_20189_() + offsetZ);
        EndEruptionEntity eruption = new EndEruptionEntity(level);
        eruption.m_7678_(spawnPos.f_82479_, spawnPos.f_82480_, spawnPos.f_82481_, 0.0f, 0.0f);
        eruption.setWindupDuration(40);
        eruption.setDamage(boss.getResurgenceScaledAttack() * endEruptionDamageMultiplierFromBase);
        level.m_7967_((Entity)eruption);
    }

    public static void spawnExplodeSpinCloneTrail(NightwardenBossEntity nightwarden, float yawOffset, int explodeDelayTicks) {
        Vec3 backOffset = nightwarden.m_20154_().m_82541_().m_82490_(-2.0);
        Vec3 spawnPos = nightwarden.m_20182_().m_82549_(backOffset);
        NightwardenExplodeCloneEntity clone = new NightwardenExplodeCloneEntity(nightwarden.m_9236_(), (LivingEntity)nightwarden, yawOffset);
        clone.m_146884_(spawnPos);
        clone.setRadius(6.0f);
        clone.setMagicDamage(true);
        clone.setDamage(nightwarden.getResurgenceScaledAttack() * explodeSpinCloneTrailDamageMultiplierFromBase);
        clone.setHpBasedDamagePercent(explodeSpinCloneTrailHpDamage);
        clone.setSpinClone(true);
        clone.setExplodeDelayTicks(explodeDelayTicks);
        nightwarden.m_9236_().m_7967_((Entity)clone);
    }

    public static void spawnDragonSpiritProjectile(NightwardenBossEntity nightwarden, double yOffset) {
        Vec3 origin = nightwarden.m_146892_().m_82520_(0.0, yOffset, 0.0);
        DragonSpiritEntity spirit = new DragonSpiritEntity(nightwarden.m_9236_(), (LivingEntity)nightwarden);
        spirit.setMagicDamageMode(true);
        spirit.setDamage(nightwarden.getResurgenceScaledAttack() * 0.3333f);
        spirit.setHpBasedDamagePercent(dragonSpiritHpDamage * 0.3333f);
        spirit.m_146884_(origin.m_82549_(nightwarden.m_20156_()).m_82492_(0.0, (double)(spirit.m_20206_() / 2.0f), 0.0));
        spirit.shoot(nightwarden.m_20154_());
        nightwarden.m_9236_().m_7967_((Entity)spirit);
    }
}

