/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity
 *  io.redspace.ironsspellbooks.api.entity.IMagicEntity
 *  io.redspace.ironsspellbooks.api.registry.SchoolRegistry
 *  io.redspace.ironsspellbooks.api.spells.SchoolType
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  io.redspace.ironsspellbooks.entity.mobs.goals.AttackAnimationData
 *  io.redspace.ironsspellbooks.network.ClientboundSyncAnimation
 *  io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions
 *  io.redspace.ironsspellbooks.setup.Messages
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.network.chat.Component
 *  net.minecraft.util.Mth
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 */
package com.gametechbc.traveloptics.entity.mobs.nightwarden_boss;

import com.gametechbc.traveloptics.api.entity.ai.WarlockAttackGoal;
import com.gametechbc.traveloptics.api.particle.AdvancedSphereParticleManager;
import com.gametechbc.traveloptics.api.particle.CircleParticleManager;
import com.gametechbc.traveloptics.api.particle.ParticleDirection;
import com.gametechbc.traveloptics.api.utils.TOGeneralUtils;
import com.gametechbc.traveloptics.entity.misc.TOScreenShakeEntity;
import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.NightwardenAttackHelper;
import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.NightwardenBossEntity;
import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.animated_particle.NightwardenCloneGroundSlam;
import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.animated_particle.NightwardenDragonSurgeAnimatedParticle;
import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.misc.NightwardenScytheSlamAnimatedParticle;
import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.misc.NightwardenTargetVelocityTracker;
import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.nightwarden_clone.nightwarden_explode_clone.NightwardenExplodeCloneEntity;
import com.gametechbc.traveloptics.entity.projectiles.void_slash.VoidSlashProjectile;
import com.gametechbc.traveloptics.init.TravelopticsMessages;
import com.gametechbc.traveloptics.init.TravelopticsParticles;
import com.gametechbc.traveloptics.util.TOColors;
import com.gametechbc.traveloptics.util.TravelopticsParticleHelper;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import io.redspace.ironsspellbooks.api.entity.IMagicEntity;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.entity.mobs.goals.AttackAnimationData;
import io.redspace.ironsspellbooks.network.ClientboundSyncAnimation;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import io.redspace.ironsspellbooks.setup.Messages;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class NightwardenAttackGoal
extends WarlockAttackGoal {
    final NightwardenBossEntity nightwarden;
    private int scytheUltimateCooldown = 0;
    private static final int ultimateDragonCooldownDuration = 2400;
    private int bigSlamCooldown = 0;
    private int bigSlamClonesCooldown = 0;
    private static final int bigSlamCooldownDuration = 500;
    private int scytheGroundSlamCloneCooldown = 0;
    private static final int scytheGroundSlamCloneCooldownDuration = 400;
    private int meleeAnimTimer = -1;
    private AttackType currentAttack;
    private AttackType nextAttack;
    private AttackType queueCombo;
    private float comboChance = 0.3f;

    public NightwardenAttackGoal(NightwardenBossEntity boss, double speedModifier, int minAttackInterval, int maxAttackInterval, float meleeRange) {
        super((IMagicEntity)boss, speedModifier, minAttackInterval, maxAttackInterval, meleeRange);
        this.nightwarden = boss;
        this.nextAttack = this.randomizeNextAttack();
        this.wantsToMelee = true;
    }

    private void tickAttackCooldowns() {
        if (this.bigSlamCooldown > 0) {
            --this.bigSlamCooldown;
        }
        if (this.bigSlamClonesCooldown > 0) {
            --this.bigSlamClonesCooldown;
        }
        if (this.scytheUltimateCooldown > 0) {
            --this.scytheUltimateCooldown;
        }
        if (this.scytheGroundSlamCloneCooldown > 0) {
            --this.scytheGroundSlamCloneCooldown;
        }
    }

    @Override
    protected void handleAttackLogic(double distanceSquared) {
        this.tickAttackCooldowns();
        if (this.meleeAnimTimer < 0 && (!this.wantsToMelee || distanceSquared > (double)(this.meleeRange * this.meleeRange) || this.spellCastingMob.isCasting())) {
            super.handleAttackLogic(distanceSquared);
            return;
        }
        if (this.meleeAnimTimer > 0) {
            this.nightwarden.isMeleeing = true;
            if (this.currentAttack != AttackType.SCYTHE_THROW) {
                this.mob.m_21563_().m_148051_((Entity)this.target);
                this.forceFaceTarget();
            }
            if (this.currentAttack == AttackType.RIGHT_SWING || this.currentAttack == AttackType.LEFT_SWING) {
                this.handleBasicSwingProjectiles();
            }
            if (this.currentAttack == AttackType.TELEPORT_COMBO) {
                this.handleTeleportCombo();
            }
            if (this.currentAttack == AttackType.TEN_COMBO) {
                this.handleTenComboExtras();
            }
            if (this.currentAttack == AttackType.JUMP_COMBO) {
                this.handleJumpComboExtras();
            }
            if (this.currentAttack == AttackType.BIG_SLAM) {
                this.handleBigSlamExtras();
            }
            if (this.currentAttack == AttackType.BIG_SLAM_CLONES) {
                this.handleBigSlamCloneExtras();
            }
            if (this.currentAttack == AttackType.SCYTHE_SPIN_FORWARD || this.currentAttack == AttackType.SCYTHE_SPIN_FORWARD_PHASE_THREE) {
                this.handleScytheSpinForward();
            }
            if (this.currentAttack == AttackType.SHEAR_OF_THE_STARS) {
                this.handleScytheUltimate();
            }
            if (this.currentAttack == AttackType.SCYTHE_CLONE_SLASHES) {
                this.handleCloneSlashes();
            }
            if (this.currentAttack == AttackType.SCYTHE_GROUND_SLAM_CLONE) {
                this.handleScytheGroundSlamClone();
            }
            if (this.currentAttack == AttackType.SCYTHE_SPINNING_CLONE) {
                this.handleSpinningClone();
            }
            --this.meleeAnimTimer;
            this.handleHitFrameSounds();
            this.handleSlashVisuals();
            if (this.currentAttack.data.isHitFrame(this.meleeAnimTimer)) {
                this.performMeleeAttack(distanceSquared);
                this.applyDashImpulse();
            }
        } else if (this.queueCombo != null && this.target != null && !this.target.m_21224_()) {
            this.nextAttack = this.queueCombo;
            this.queueCombo = null;
            this.doMeleeAction();
        } else if (this.meleeAnimTimer == 0) {
            this.nextAttack = this.randomizeNextAttack();
            this.resetAttackTimer(distanceSquared);
            this.meleeAnimTimer = -1;
        } else if (distanceSquared < (double)(this.meleeRange * this.meleeRange) * 1.44) {
            if (--this.attackTime == 0) {
                this.doMeleeAction();
            } else if (this.attackTime < 0) {
                this.resetAttackTimer(distanceSquared);
            }
        }
    }

    private void handleBasicSwingProjectiles() {
        if (this.nightwarden.isPhase(NightwardenBossEntity.Phase.THIRD) && this.meleeAnimTimer == 17) {
            VoidSlashProjectile slash = new VoidSlashProjectile(this.nightwarden.m_9236_(), (LivingEntity)this.nightwarden);
            Vec3 origin = this.nightwarden.m_146892_();
            slash.m_146884_(origin);
            slash.shoot(this.nightwarden.m_20154_());
            slash.setDamage((float)this.mob.m_21133_(Attributes.f_22281_));
            this.nightwarden.m_9236_().m_7967_((Entity)slash);
        }
    }

    private void handleCloneSlashes() {
        if (this.meleeAnimTimer <= 55 && this.meleeAnimTimer >= 5) {
            this.mob.m_21573_().m_26573_();
            this.mob.m_20256_(Vec3.f_82478_);
        }
        if (this.meleeAnimTimer == 18) {
            ScreenShake_Entity.ScreenShake((Level)this.mob.m_9236_(), (Vec3)this.mob.m_20182_(), (float)15.0f, (float)0.06f, (int)8, (int)10);
            VoidSlashProjectile slash = new VoidSlashProjectile(this.nightwarden.m_9236_(), (LivingEntity)this.nightwarden);
            Vec3 origin = this.nightwarden.m_146892_();
            slash.m_146884_(origin);
            slash.setCross(true);
            slash.shoot(this.nightwarden.m_20154_());
            slash.setDamage((float)this.mob.m_21133_(Attributes.f_22281_));
            slash.setLifestealPercent(0.75f);
            this.nightwarden.m_9236_().m_7967_((Entity)slash);
        }
        if (this.meleeAnimTimer == 128) {
            NightwardenAttackHelper.spawnAdaptiveCloneSlash(this.nightwarden, false);
        }
        if (this.meleeAnimTimer == 112) {
            NightwardenAttackHelper.spawnAdaptiveCloneSlash(this.nightwarden, true);
        }
        if (this.meleeAnimTimer == 95) {
            NightwardenAttackHelper.spawnAdaptiveCloneSlash(this.nightwarden, false);
        }
        if (this.meleeAnimTimer == 79) {
            NightwardenAttackHelper.spawnAdaptiveCloneSlash(this.nightwarden, true);
        }
        if (this.meleeAnimTimer == 162 || this.meleeAnimTimer == 137 || this.meleeAnimTimer == 112 || this.meleeAnimTimer == 86 || this.meleeAnimTimer == 59) {
            NightwardenAttackHelper.playSound(this.nightwarden, 6, 1.5f);
        }
    }

    private void handleTeleportCombo() {
        if (this.meleeAnimTimer > 108) {
            this.mob.m_21573_().m_26573_();
            this.mob.m_20256_(Vec3.f_82478_);
        }
        if (this.meleeAnimTimer == 112) {
            Player player;
            Vec3 behind = this.target.m_20154_().m_82541_().m_82490_(-1.2);
            Vec3 from = this.nightwarden.m_20182_();
            Vec3 to = this.target.m_20182_().m_82549_(behind).m_82520_(0.0, 0.5, 0.0);
            NightwardenExplodeCloneEntity clone = new NightwardenExplodeCloneEntity(this.nightwarden.m_9236_(), (LivingEntity)this.nightwarden, 0.0f);
            clone.setRadius(8.0f);
            clone.setDamage((float)this.mob.m_21133_(Attributes.f_22281_));
            clone.setHpBasedDamagePercent(0.1f);
            this.nightwarden.m_9236_().m_7967_((Entity)clone);
            NightwardenAttackHelper.spawnTeleportParticles(this.nightwarden.m_9236_(), from);
            this.nightwarden.m_6021_(to.f_82479_, to.f_82480_, to.f_82481_);
            this.target.m_7292_(new MobEffectInstance(MobEffects.f_216964_, 60, 0, false, false, true));
            LivingEntity livingEntity = this.target;
            if (livingEntity instanceof Player && (player = (Player)livingEntity).m_21254_()) {
                player.m_36384_(true);
            }
            ScreenShake_Entity.ScreenShake((Level)this.mob.m_9236_(), (Vec3)this.mob.m_20182_(), (float)15.0f, (float)0.04f, (int)6, (int)8);
            NightwardenAttackHelper.spawnTeleportParticles(this.nightwarden.m_9236_(), to);
            NightwardenAttackHelper.playSound(this.nightwarden, 4, 1.5f);
        } else if (this.meleeAnimTimer == 45) {
            Vec3 from = this.nightwarden.m_20182_();
            Vec3 toNightwarden = this.nightwarden.m_20182_().m_82546_(this.target.m_20182_()).m_82541_();
            Vec3 targetLook = this.target.m_20154_().m_82541_();
            boolean isLookingAtBoss = targetLook.m_82526_(toNightwarden) > 0.25;
            Vec3 teleportOffset = isLookingAtBoss ? targetLook.m_82490_(-1.3) : targetLook.m_82490_(3.0);
            Vec3 to = this.target.m_20182_().m_82549_(teleportOffset).m_82520_(0.0, 0.5, 0.0);
            NightwardenAttackHelper.spawnTeleportParticles(this.nightwarden.m_9236_(), from);
            this.nightwarden.m_7678_(to.f_82479_, to.f_82480_, to.f_82481_, this.nightwarden.m_146908_(), this.nightwarden.m_146909_());
            NightwardenAttackHelper.spawnTeleportParticles(this.nightwarden.m_9236_(), to);
            ScreenShake_Entity.ScreenShake((Level)this.mob.m_9236_(), (Vec3)this.mob.m_20182_(), (float)15.0f, (float)0.04f, (int)6, (int)8);
            NightwardenAttackHelper.playSound(this.nightwarden, 4, 1.5f);
        }
        if (this.meleeAnimTimer <= 71 && this.meleeAnimTimer >= 51 || this.meleeAnimTimer <= 38 && this.meleeAnimTimer >= 16) {
            float HAND_HEIGHT = 2.0f;
            float SPIN_RADIUS = 2.5f;
            boolean isFirstSpin = this.meleeAnimTimer >= 51;
            float spinProgress = isFirstSpin ? (float)(71 - this.meleeAnimTimer) / 20.0f : (float)(38 - this.meleeAnimTimer) / 22.0f;
            float spinAngle = spinProgress * 2.0f * (float)Math.PI;
            int numParticles = 3;
            for (int i = 0; i < numParticles; ++i) {
                float angle = spinAngle + (float)i / (float)numParticles * 2.0f * (float)Math.PI;
                float offsetX = SPIN_RADIUS * (float)Math.cos(angle);
                float offsetZ = SPIN_RADIUS * (float)Math.sin(angle);
                MagicManager.spawnParticles((Level)this.nightwarden.m_9236_(), (ParticleOptions)((ParticleOptions)TravelopticsParticles.PURPLE_STAR_OUTWARD_PARTICLE.get()), (double)(this.nightwarden.m_20185_() + (double)offsetX), (double)(this.nightwarden.m_20186_() + (double)HAND_HEIGHT), (double)(this.nightwarden.m_20189_() + (double)offsetZ), (int)1, (double)0.02, (double)0.02, (double)0.02, (double)0.1, (boolean)false);
            }
        }
    }

    private void handleScytheUltimate() {
        this.mob.m_21573_().m_26573_();
        this.mob.m_20256_(Vec3.f_82478_);
        if (this.meleeAnimTimer == 180) {
            BlockPos home = this.nightwarden.getHomePos();
            Vec3 to = new Vec3((double)home.m_123341_() + 0.5, (double)home.m_123342_(), (double)home.m_123343_() + 0.5);
            NightwardenExplodeCloneEntity clone = new NightwardenExplodeCloneEntity(this.nightwarden.m_9236_(), (LivingEntity)this.nightwarden, 0.0f);
            clone.setRadius(6.0f);
            clone.setDamage((float)this.mob.m_21133_(Attributes.f_22281_));
            clone.setHpBasedDamagePercent(0.1f);
            this.nightwarden.m_9236_().m_7967_((Entity)clone);
            TravelopticsMessages.sendBossMessageToRange((Entity)this.nightwarden, (Component)Component.m_237115_((String)"entity.traveloptics.message.nightwarden.ultimate_hint"), TOColors.rgbToARGB(6619356, 0.5f), 100, 32.0, false);
            ScreenShake_Entity.ScreenShake((Level)this.mob.m_9236_(), (Vec3)this.mob.m_20182_(), (float)15.0f, (float)0.06f, (int)6, (int)8);
            NightwardenAttackHelper.playSound(this.nightwarden, 3, 2.5f);
            NightwardenAttackHelper.playSound(this.nightwarden, 4, 1.5f);
            NightwardenAttackHelper.spawnTeleportParticles(this.nightwarden.m_9236_(), this.nightwarden.m_20182_());
            this.nightwarden.m_6021_(to.f_82479_, to.f_82480_, to.f_82481_);
            NightwardenAttackHelper.spawnTeleportParticles(this.nightwarden.m_9236_(), to);
        }
        if (this.meleeAnimTimer <= 143 && this.meleeAnimTimer > 66 && this.meleeAnimTimer % 2 == 0) {
            double progress = (double)(143 - this.meleeAnimTimer) / 77.0;
            NightwardenDragonSurgeAnimatedParticle.dragonSurgeChargingAnimation(this.nightwarden, progress, this.meleeAnimTimer);
        }
        if (this.meleeAnimTimer == 65) {
            NightwardenAttackHelper.playSound(this.nightwarden, 8, 5.0f);
            TOScreenShakeEntity.createScreenShake(this.mob.m_9236_(), this.mob.m_20182_(), 50.0f, 0.08f, 10, 0, 15, false);
        }
        if (this.meleeAnimTimer == 63) {
            NightwardenAttackHelper.spawnDragonSpiritProjectile(this.nightwarden, 1.0);
        }
    }

    private void handleTenComboExtras() {
        if (this.meleeAnimTimer == 35 || this.meleeAnimTimer == 17) {
            float radius = 2.0f;
            List nearby = this.mob.m_9236_().m_6443_(LivingEntity.class, this.mob.m_20191_().m_82400_((double)radius), e -> e != this.mob && e.m_6084_() && this.mob.m_142582_((Entity)e));
            for (LivingEntity entity : nearby) {
                Player player;
                if (!(entity instanceof Player) || !(player = (Player)entity).m_21254_()) continue;
                player.m_36384_(true);
            }
            for (LivingEntity entity : nearby) {
                if (entity == this.target) continue;
                double aoeBase = this.mob.m_21133_(Attributes.f_22281_);
                float aoeDamage = (float)(aoeBase * (double)this.currentAttack.damageMultiplier) + entity.m_21233_() * this.currentAttack.hpBasedDamage;
                entity.m_6469_(this.mob.m_269291_().m_269333_((LivingEntity)this.mob), aoeDamage);
            }
            Vec3 forward = this.nightwarden.m_20154_().m_82542_(1.0, 0.0, 1.0).m_82541_();
            Vec3 right = forward.m_82537_(new Vec3(0.0, 1.0, 0.0)).m_82541_();
            Vec3 left = right.m_82490_(-1.0);
            Vec3 back = forward.m_82490_(-1.0);
            for (Vec3 dir : List.of(forward, right, left, back)) {
                VoidSlashProjectile slash = new VoidSlashProjectile(this.nightwarden.m_9236_(), (LivingEntity)this.nightwarden);
                slash.setDamage((float)this.mob.m_21133_(Attributes.f_22281_));
                slash.m_146884_(this.nightwarden.m_146892_().m_82520_(0.0, -0.4, 0.0));
                slash.shoot(dir);
                this.nightwarden.m_9236_().m_7967_((Entity)slash);
            }
        }
    }

    private void handleScytheSpinForward() {
        if (this.meleeAnimTimer >= 41) {
            this.nightwarden.setLockRotation(false);
            this.mob.m_21573_().m_26573_();
            this.mob.m_20256_(Vec3.f_82478_);
            return;
        }
        if (this.meleeAnimTimer == 37) {
            Vec3 back = this.mob.m_20154_().m_82541_().m_82490_(-3.0);
            Vec3 from = this.mob.m_20182_();
            Vec3 to = from.m_82549_(back);
            ScreenShake_Entity.ScreenShake((Level)this.mob.m_9236_(), (Vec3)this.mob.m_20182_(), (float)20.0f, (float)0.05f, (int)6, (int)8);
            NightwardenAttackHelper.spawnTeleportParticles(this.nightwarden.m_9236_(), this.nightwarden.m_20182_());
            this.nightwarden.m_6021_(to.f_82479_, to.f_82480_, to.f_82481_);
            NightwardenAttackHelper.spawnTeleportParticles(this.nightwarden.m_9236_(), to);
            NightwardenAttackHelper.playSound(this.nightwarden, 4, 1.5f);
        }
        if (this.meleeAnimTimer <= 36 && this.meleeAnimTimer >= 7) {
            this.nightwarden.setLockRotation(true);
            Vec3 forward = this.mob.m_20154_().m_82541_().m_82490_(0.75);
            Vec3 newMotion = new Vec3(forward.f_82479_, this.mob.m_20184_().f_82480_, forward.f_82481_);
            this.mob.m_20256_(newMotion);
            this.mob.f_19812_ = true;
            if (this.mob.f_19797_ % 4 == 0) {
                this.nightwarden.setShouldSpawnRingParticle(true, 5);
            }
        } else {
            this.nightwarden.setLockRotation(false);
        }
        if (this.nightwarden.isPhase(NightwardenBossEntity.Phase.THIRD) && this.currentAttack == AttackType.SCYTHE_SPIN_FORWARD_PHASE_THREE && (this.meleeAnimTimer == 30 || this.meleeAnimTimer == 25 || this.meleeAnimTimer == 20 || this.meleeAnimTimer == 15 || this.meleeAnimTimer == 10)) {
            int index = (30 - this.meleeAnimTimer) / 5;
            float yawOffset = (float)index * 72.0f;
            int spawnTick = 70 - this.meleeAnimTimer;
            int delayToExplode = 90 - spawnTick;
            NightwardenAttackHelper.spawnExplodeSpinCloneTrail(this.nightwarden, yawOffset, delayToExplode);
            float radius = 3.0f;
            List nearby = this.mob.m_9236_().m_6443_(LivingEntity.class, this.mob.m_20191_().m_82400_((double)radius), e -> e != this.mob && e.m_6084_() && this.mob.m_142582_((Entity)e));
            for (LivingEntity entity : nearby) {
                Player player;
                if (!(entity instanceof Player) || !(player = (Player)entity).m_21254_()) continue;
                player.m_36384_(true);
            }
            for (LivingEntity entity : nearby) {
                if (entity == this.target) continue;
                double aoeBase = this.mob.m_21133_(Attributes.f_22281_);
                float aoeDamage = (float)(aoeBase * (double)this.currentAttack.damageMultiplier) + entity.m_21233_() * this.currentAttack.hpBasedDamage;
                entity.m_6469_(this.mob.m_269291_().m_269333_((LivingEntity)this.mob), aoeDamage);
            }
        }
    }

    private void handleJumpComboExtras() {
        if (this.meleeAnimTimer == 62) {
            Vec3 look = this.mob.m_20154_().m_82541_();
            Vec3 motion = new Vec3(look.f_82479_ * 0.75, 0.4, look.f_82481_ * 0.75);
            this.mob.m_20256_(motion);
            this.mob.f_19812_ = true;
        }
        if (this.meleeAnimTimer <= 37 && this.meleeAnimTimer >= 6) {
            this.mob.m_21573_().m_26573_();
            this.mob.m_20256_(Vec3.f_82478_);
        }
        if (this.meleeAnimTimer == 54) {
            ScreenShake_Entity.ScreenShake((Level)this.mob.m_9236_(), (Vec3)this.mob.m_20182_(), (float)8.0f, (float)0.04f, (int)8, (int)10);
            NightwardenAttackHelper.playSound(this.nightwarden, 7, 1.0f);
            Vec3 forwardOffset = this.mob.m_20154_().m_82541_().m_82490_(2.0);
            float radius = 2.0f;
            Vec3 blastPos = this.mob.m_20182_().m_82549_(this.mob.m_20154_().m_82541_().m_82490_(3.0));
            MagicManager.spawnParticles((Level)this.mob.m_9236_(), (ParticleOptions)new BlastwaveParticleOptions(((SchoolType)SchoolRegistry.ENDER.get()).getTargetingColor(), radius), (double)blastPos.f_82479_, (double)(this.mob.m_20186_() + (double)0.165f), (double)blastPos.f_82481_, (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)true);
            AdvancedSphereParticleManager.spawnParticles(this.mob.m_9236_(), blastPos.f_82479_, this.mob.m_20186_() + (double)0.165f, blastPos.f_82481_, 50, (ParticleOptions)TravelopticsParticles.PURPLE_STAR_OUTWARD_PARTICLE.get(), ParticleDirection.OUTWARD, 4.0, false);
            List nearby = this.mob.m_9236_().m_6443_(LivingEntity.class, this.mob.m_20191_().m_82383_(forwardOffset).m_82400_((double)radius), e -> e != this.mob && e.m_6084_() && this.mob.m_142582_((Entity)e));
            for (LivingEntity entity : nearby) {
                Player player;
                if (entity instanceof Player && (player = (Player)entity).m_21254_()) {
                    player.m_36384_(true);
                }
                if (entity == this.target) continue;
                double baseDamage = this.mob.m_21133_(Attributes.f_22281_);
                float damage = (float)(baseDamage * (double)this.currentAttack.damageMultiplier) + entity.m_21233_() * this.currentAttack.hpBasedDamage;
                entity.m_6469_(this.mob.m_269291_().m_269333_((LivingEntity)this.mob), damage);
            }
        }
    }

    private void handleBigSlamExtras() {
        if (this.meleeAnimTimer <= 60 && this.meleeAnimTimer >= 15) {
            this.mob.m_21573_().m_26573_();
            this.mob.m_20256_(Vec3.f_82478_);
        }
        if (this.meleeAnimTimer == 40 && this.target != null && this.target.m_6084_()) {
            Vec3 look = this.target.m_20154_().m_82541_();
            Vec3 flatLook = new Vec3(look.f_82479_, 0.0, look.f_82481_).m_82541_();
            Vec3 side = flatLook.m_82537_(new Vec3(0.0, 1.0, 0.0)).m_82541_();
            double offsetFromTarget = 8.0;
            Vec3 leftPos = this.target.m_20182_().m_82549_(side.m_82490_(offsetFromTarget));
            Vec3 rightPos = this.target.m_20182_().m_82549_(side.m_82490_(-offsetFromTarget));
            Vec3 behindPos = this.target.m_20182_().m_82549_(flatLook.m_82490_(-offsetFromTarget));
            NightwardenAttackHelper.spawnEruptionAt(this.nightwarden, leftPos);
            NightwardenAttackHelper.spawnEruptionAt(this.nightwarden, rightPos);
            NightwardenAttackHelper.spawnEruptionAt(this.nightwarden, behindPos);
        }
        NightwardenScytheSlamAnimatedParticle.spawnScytheSwingParticlesReverse((LivingEntity)this.nightwarden, this.meleeAnimTimer, 29, 22);
        if (this.meleeAnimTimer == 22) {
            Vec3 center = this.mob.m_20182_().m_82549_(this.mob.m_20154_().m_82541_().m_82490_(6.0));
            NightwardenAttackHelper.playSound(this.nightwarden, 7, 1.5f);
            ScreenShake_Entity.ScreenShake((Level)this.mob.m_9236_(), (Vec3)center, (float)20.0f, (float)0.1f, (int)15, (int)20);
            double y = center.f_82480_;
            double minY = this.mob.m_20186_();
            PathfinderMob owner = this.mob;
            NightwardenAttackHelper.spawnSpikeRing(this.nightwarden, center, 2.5, 6, minY, y + 1.0, 0, (LivingEntity)owner);
            NightwardenAttackHelper.spawnSpikeRing(this.nightwarden, center, 3.5, 11, minY, y + 1.0, 2, (LivingEntity)owner);
            NightwardenAttackHelper.spawnSpikeRing(this.nightwarden, center, 4.5, 14, minY, y + 1.0, 4, (LivingEntity)owner);
            NightwardenAttackHelper.spawnSpikeRing(this.nightwarden, center, 5.5, 19, minY, y + 1.0, 6, (LivingEntity)owner);
            NightwardenAttackHelper.spawnSpikeRing(this.nightwarden, center, 6.5, 26, minY, y + 1.0, 8, (LivingEntity)owner);
            CircleParticleManager.spawnParticles(this.mob.m_9236_(), new Vec3(center.f_82479_, this.mob.m_20186_() + 0.2, center.f_82481_), 50, (ParticleOptions)ParticleTypes.f_123777_, ParticleDirection.OUTWARD, 5.5, true);
        }
    }

    private void handleBigSlamCloneExtras() {
        if (this.meleeAnimTimer <= 60 && this.meleeAnimTimer >= 15) {
            this.mob.m_21573_().m_26573_();
            this.mob.m_20256_(Vec3.f_82478_);
        }
        NightwardenScytheSlamAnimatedParticle.spawnScytheSwingParticlesReverse((LivingEntity)this.nightwarden, this.meleeAnimTimer, 29, 22);
        if (this.meleeAnimTimer == 55) {
            NightwardenAttackHelper.playSound(this.nightwarden, 3, 2.5f);
            NightwardenAttackHelper.playSound(this.nightwarden, 4, 2.5f);
            NightwardenAttackHelper.playSound(this.nightwarden, 11, 2.5f);
            NightwardenAttackHelper.spawnBigSlamClone(this.nightwarden, true);
            NightwardenAttackHelper.spawnBigSlamClone(this.nightwarden, false);
        }
        if (this.nightwarden.isPhase(NightwardenBossEntity.Phase.THIRD) && this.meleeAnimTimer == 40 && this.target != null && this.target.m_6084_()) {
            Vec3 look = this.nightwarden.m_20154_().m_82541_();
            Vec3 flatLook = new Vec3(look.f_82479_, 0.0, look.f_82481_).m_82541_();
            double forwardOffset = 22.0;
            Vec3 forwardPos = new Vec3(this.nightwarden.m_20185_() + flatLook.f_82479_ * forwardOffset, this.nightwarden.m_20186_(), this.nightwarden.m_20189_() + flatLook.f_82481_ * forwardOffset);
            NightwardenAttackHelper.spawnEruptionAt(this.nightwarden, forwardPos);
        }
        if (this.meleeAnimTimer == 22) {
            NightwardenAttackHelper.playSound(this.nightwarden, 8, 1.5f);
            ScreenShake_Entity.ScreenShake((Level)this.mob.m_9236_(), (Vec3)this.mob.m_20182_(), (float)20.0f, (float)0.1f, (int)15, (int)20);
            NightwardenAttackHelper.spawnDimensionalSpikeLine(this.nightwarden, 6.0f, 1.2f, 10);
            Vec3 forwardFlat = new Vec3(this.nightwarden.m_20154_().f_82479_, 0.0, this.nightwarden.m_20154_().f_82481_).m_82541_();
            Vec3 center = new Vec3(this.nightwarden.m_20185_(), this.nightwarden.m_20186_() + 0.2, this.nightwarden.m_20189_()).m_82549_(forwardFlat.m_82490_(6.0));
            CircleParticleManager.spawnParticles(this.mob.m_9236_(), center, 50, (ParticleOptions)ParticleTypes.f_123777_, ParticleDirection.OUTWARD, 5.5, false);
        }
    }

    private void handleScytheGroundSlamClone() {
        NightwardenTargetVelocityTracker.trackTargetMovement(this.nightwarden);
        if (this.meleeAnimTimer <= 210 && this.meleeAnimTimer >= 25) {
            this.mob.m_21573_().m_26573_();
            this.mob.m_20256_(Vec3.f_82478_);
            NightwardenAttackHelper.applyHovering((LivingEntity)this.nightwarden, 5.0, 0.12, 0.15, false);
            NightwardenCloneGroundSlam.updateBossAuraParticles(this.nightwarden.m_9236_(), (LivingEntity)this.nightwarden, this.meleeAnimTimer);
        }
        if (this.meleeAnimTimer == 215) {
            NightwardenAttackHelper.playSound(this.nightwarden, 9, 1.5f);
            this.nightwarden.setShouldShowWings(true, 215);
        }
        if (this.meleeAnimTimer == 182) {
            NightwardenAttackHelper.spawnPredictiveDropSlamClone(this.nightwarden.m_9236_(), this.nightwarden, this.target, new Vec3(0.0, 0.0, 0.0), NightwardenTargetVelocityTracker.getAverageTargetVelocity(this.nightwarden));
            NightwardenAttackHelper.playSound(this.nightwarden, 10, 1.5f);
        }
        if (this.meleeAnimTimer == 162) {
            NightwardenAttackHelper.spawnPredictiveDropSlamClone(this.nightwarden.m_9236_(), this.nightwarden, this.target, new Vec3(0.0, 0.0, 0.0), NightwardenTargetVelocityTracker.getAverageTargetVelocity(this.nightwarden));
            NightwardenAttackHelper.spawnRandomEruptionAroundTarget(this.nightwarden.m_9236_(), this.nightwarden, this.target, 6.0, 9.0);
            NightwardenAttackHelper.playSound(this.nightwarden, 10, 1.5f);
        }
        if (this.meleeAnimTimer == 142) {
            NightwardenAttackHelper.spawnPredictiveDropSlamClone(this.nightwarden.m_9236_(), this.nightwarden, this.target, new Vec3(0.0, 0.0, 0.0), NightwardenTargetVelocityTracker.getAverageTargetVelocity(this.nightwarden));
            NightwardenAttackHelper.playSound(this.nightwarden, 10, 1.5f);
        }
        if (this.meleeAnimTimer == 123) {
            NightwardenAttackHelper.spawnPredictiveDropSlamClone(this.nightwarden.m_9236_(), this.nightwarden, this.target, new Vec3(0.0, 0.0, 0.0), NightwardenTargetVelocityTracker.getAverageTargetVelocity(this.nightwarden));
            NightwardenAttackHelper.spawnRandomEruptionAroundTarget(this.nightwarden.m_9236_(), this.nightwarden, this.target, 6.0, 9.0);
            NightwardenAttackHelper.playSound(this.nightwarden, 10, 1.5f);
        }
        if (this.meleeAnimTimer == 93) {
            NightwardenAttackHelper.spawnPredictiveDropSlamClone(this.nightwarden.m_9236_(), this.nightwarden, this.target, new Vec3(0.0, 0.0, 0.0), NightwardenTargetVelocityTracker.getAverageTargetVelocity(this.nightwarden));
            NightwardenAttackHelper.spawnPredictiveDropSlamClone(this.nightwarden.m_9236_(), this.nightwarden, this.target, new Vec3(4.0, 0.0, 0.0), NightwardenTargetVelocityTracker.getAverageTargetVelocity(this.nightwarden));
            NightwardenAttackHelper.spawnPredictiveDropSlamClone(this.nightwarden.m_9236_(), this.nightwarden, this.target, new Vec3(-4.0, 0.0, 0.0), NightwardenTargetVelocityTracker.getAverageTargetVelocity(this.nightwarden));
            NightwardenAttackHelper.playSound(this.nightwarden, 11, 1.5f);
        }
        if (this.meleeAnimTimer == 68) {
            NightwardenAttackHelper.spawnPredictiveDropSlamClone(this.nightwarden.m_9236_(), this.nightwarden, this.target, new Vec3(0.0, 0.0, 0.0), NightwardenTargetVelocityTracker.getAverageTargetVelocity(this.nightwarden));
            NightwardenAttackHelper.spawnPredictiveDropSlamClone(this.nightwarden.m_9236_(), this.nightwarden, this.target, new Vec3(0.0, 0.0, 4.0), NightwardenTargetVelocityTracker.getAverageTargetVelocity(this.nightwarden));
            NightwardenAttackHelper.spawnPredictiveDropSlamClone(this.nightwarden.m_9236_(), this.nightwarden, this.target, new Vec3(0.0, 0.0, -4.0), NightwardenTargetVelocityTracker.getAverageTargetVelocity(this.nightwarden));
            NightwardenAttackHelper.spawnRandomEruptionAroundTarget(this.nightwarden.m_9236_(), this.nightwarden, this.target, 6.0, 9.0);
            NightwardenAttackHelper.playSound(this.nightwarden, 11, 1.5f);
        }
        if (this.meleeAnimTimer == 25) {
            Player player;
            Vec3 behind = this.target.m_20154_().m_82541_().m_82490_(-1.5);
            Vec3 from = this.nightwarden.m_20182_();
            Vec3 to = this.target.m_20182_().m_82549_(behind).m_82520_(0.0, 0.5, 0.0);
            NightwardenAttackHelper.spawnTeleportParticles(this.nightwarden.m_9236_(), from);
            this.nightwarden.m_6021_(to.f_82479_, to.f_82480_, to.f_82481_);
            LivingEntity livingEntity = this.target;
            if (livingEntity instanceof Player && (player = (Player)livingEntity).m_21254_()) {
                player.m_36384_(true);
            }
            ScreenShake_Entity.ScreenShake((Level)this.nightwarden.m_9236_(), (Vec3)this.nightwarden.m_20182_(), (float)6.0f, (float)0.025f, (int)6, (int)8);
            NightwardenAttackHelper.spawnTeleportParticles(this.nightwarden.m_9236_(), to);
            NightwardenAttackHelper.playSound(this.nightwarden, 4, 1.5f);
        }
        if (this.meleeAnimTimer == 10) {
            ScreenShake_Entity.ScreenShake((Level)this.mob.m_9236_(), (Vec3)this.mob.m_20182_(), (float)12.0f, (float)0.04f, (int)8, (int)10);
            NightwardenAttackHelper.playSound(this.nightwarden, 7, 1.0f);
            Vec3 forwardOffset = this.mob.m_20154_().m_82541_().m_82490_(2.0);
            float radius = 2.0f;
            Vec3 blastPos = this.mob.m_20182_().m_82549_(this.mob.m_20154_().m_82541_().m_82490_(3.0));
            MagicManager.spawnParticles((Level)this.mob.m_9236_(), (ParticleOptions)new BlastwaveParticleOptions(((SchoolType)SchoolRegistry.ENDER.get()).getTargetingColor(), radius), (double)blastPos.f_82479_, (double)(this.mob.m_20186_() + (double)0.165f), (double)blastPos.f_82481_, (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)true);
            AdvancedSphereParticleManager.spawnParticles(this.mob.m_9236_(), blastPos.f_82479_, this.mob.m_20186_() + (double)0.165f, blastPos.f_82481_, 50, TravelopticsParticleHelper.ABYSS_SPIKE_PARTICLE, ParticleDirection.OUTWARD, 4.0, false);
            this.mob.m_9236_().m_6443_(LivingEntity.class, this.mob.m_20191_().m_82383_(forwardOffset).m_82400_((double)radius), e -> e != this.mob && e.m_6084_() && this.mob.m_142582_((Entity)e)).forEach(extraTarget -> {
                Player player;
                double baseDamage = this.mob.m_21133_(Attributes.f_22281_);
                float damage = (float)(baseDamage * (double)this.currentAttack.damageMultiplier) + extraTarget.m_21233_() * this.currentAttack.hpBasedDamage;
                extraTarget.m_6469_(this.mob.m_269291_().m_269333_((LivingEntity)this.mob), damage);
                if (extraTarget instanceof Player && (player = (Player)extraTarget).m_21254_()) {
                    player.m_36384_(true);
                }
            });
        }
        if (this.meleeAnimTimer == 1) {
            NightwardenTargetVelocityTracker.clearHistory(this.nightwarden);
        }
    }

    private void handleSpinningClone() {
        if (this.meleeAnimTimer <= 175 && this.meleeAnimTimer >= 75) {
            this.mob.m_21573_().m_26573_();
            this.mob.m_20256_(Vec3.f_82478_);
        }
        if (this.meleeAnimTimer == 179) {
            NightwardenAttackHelper.showSpinCloneWarningPath(this.nightwarden, false);
        }
        if (this.meleeAnimTimer == 170) {
            NightwardenAttackHelper.playSound(this.nightwarden, 4, 1.5f);
            NightwardenAttackHelper.spawnSpinCloneToSide(this.nightwarden, false);
        }
        if (this.meleeAnimTimer == 155) {
            NightwardenAttackHelper.showSpinCloneWarningPath(this.nightwarden, true);
        }
        if (this.meleeAnimTimer == 145) {
            NightwardenAttackHelper.playSound(this.nightwarden, 4, 1.5f);
            NightwardenAttackHelper.spawnSpinCloneToSide(this.nightwarden, true);
        }
        if (this.meleeAnimTimer == 115) {
            NightwardenAttackHelper.playSound(this.nightwarden, 4, 1.5f);
            NightwardenAttackHelper.spawnDropSlamCloneOffsetWithParticles(this.nightwarden.m_9236_(), this.nightwarden, this.target, new Vec3(0.0, 0.0, 0.0));
        }
        if (this.meleeAnimTimer == 73 || this.meleeAnimTimer == 43) {
            Vec3 toNightwarden = this.nightwarden.m_20182_().m_82546_(this.target.m_20182_()).m_82541_();
            Vec3 targetLook = this.target.m_20154_().m_82541_();
            boolean isLookingAtBoss = targetLook.m_82526_(toNightwarden) > 0.25;
            Vec3 teleportOffset = isLookingAtBoss ? targetLook.m_82490_(-1.5) : targetLook.m_82490_(3.0);
            Vec3 from = this.nightwarden.m_20182_();
            Vec3 to = this.target.m_20182_().m_82549_(teleportOffset).m_82520_(0.0, 0.5, 0.0);
            NightwardenAttackHelper.playSound(this.nightwarden, 4, 1.5f);
            NightwardenAttackHelper.spawnTeleportParticles(this.nightwarden.m_9236_(), from);
            this.nightwarden.m_7678_(to.f_82479_, to.f_82480_, to.f_82481_, this.nightwarden.m_146908_(), this.nightwarden.m_146909_());
            NightwardenAttackHelper.spawnTeleportParticles(this.nightwarden.m_9236_(), to);
            ScreenShake_Entity.ScreenShake((Level)this.mob.m_9236_(), (Vec3)this.mob.m_20182_(), (float)8.0f, (float)0.03f, (int)6, (int)8);
            NightwardenAttackHelper.playSound(this.nightwarden, 4, 1.5f);
        }
    }

    private void handleHitFrameSounds() {
        int animTick = this.currentAttack.data.lengthInTicks - this.meleeAnimTimer;
        int preHitFrame = animTick + 6;
        if (this.currentAttack == AttackType.TEN_COMBO) {
            if (preHitFrame == 125 || preHitFrame == 141) {
                NightwardenAttackHelper.playSound(this.nightwarden, 2, 1.0f);
            } else if (preHitFrame == 9 || preHitFrame == 24 || preHitFrame == 38 || preHitFrame == 53 || preHitFrame == 67 || preHitFrame == 82 || preHitFrame == 96 || preHitFrame == 111) {
                NightwardenAttackHelper.playSound(this.nightwarden, 1, 1.0f);
            }
        } else if (this.currentAttack == AttackType.TELEPORT_COMBO) {
            if (preHitFrame == 80 || preHitFrame == 110) {
                NightwardenAttackHelper.playSound(this.nightwarden, 2, 1.0f);
            } else if (preHitFrame == 90 || preHitFrame == 120) {
                NightwardenAttackHelper.playSound(this.nightwarden, 0, 1.0f);
            }
        } else if (this.currentAttack.data.isHitFrame(this.meleeAnimTimer - 6)) {
            NightwardenAttackHelper.playSound(this.nightwarden, 0, 1.0f);
        }
    }

    private void handleSlashVisuals() {
        int tick = this.meleeAnimTimer;
        if (this.currentAttack == AttackType.TEN_COMBO) {
            if (tick == 140 || tick == 109 || tick == 80 || tick == 51) {
                NightwardenAttackHelper.spawnSlashVisual(this.nightwarden, 1.4f, 0.32f, true, false, false, true);
            } else if (tick == 155 || tick == 125 || tick == 97 || tick == 66) {
                NightwardenAttackHelper.spawnSlashVisual(this.nightwarden, 1.4f, 0.32f, false, false, false, true);
            } else if (tick == 33) {
                NightwardenAttackHelper.spawnSlashVisual(this.nightwarden, 2.0f, 0.32f, false, false, true, true);
                NightwardenAttackHelper.spawnSlashVisual(this.nightwarden, -2.0f, 0.32f, false, true, true, true);
            } else if (tick == 17) {
                NightwardenAttackHelper.spawnSlashVisual(this.nightwarden, 2.0f, 0.32f, true, false, true, true);
                NightwardenAttackHelper.spawnSlashVisual(this.nightwarden, -2.0f, 0.32f, true, true, true, true);
            }
        }
        if ((this.currentAttack == AttackType.RIGHT_SWING || this.currentAttack == AttackType.LEFT_SWING) && tick == 15) {
            boolean mirrored = this.currentAttack == AttackType.LEFT_SWING;
            NightwardenAttackHelper.spawnSlashVisual(this.nightwarden, 1.4f, 0.32f, mirrored, false, false, true);
        }
        if (this.currentAttack == AttackType.TELEPORT_COMBO) {
            if (tick == 64) {
                NightwardenAttackHelper.spawnSlashVisual(this.nightwarden, 2.0f, 0.3f, false, false, true, true);
                NightwardenAttackHelper.spawnSlashVisual(this.nightwarden, -2.0f, 0.3f, false, true, true, true);
            } else if (tick == 34) {
                NightwardenAttackHelper.spawnSlashVisual(this.nightwarden, 2.0f, 0.3f, true, false, true, true);
                NightwardenAttackHelper.spawnSlashVisual(this.nightwarden, -2.0f, 0.3f, true, true, true, true);
            }
        }
    }

    private void applyDashImpulse() {
        if (this.currentAttack == AttackType.TEN_COMBO || this.currentAttack == AttackType.TELEPORT_COMBO || this.currentAttack == AttackType.SCYTHE_SPINNING_CLONE || this.currentAttack == AttackType.LEFT_SWING || this.currentAttack == AttackType.RIGHT_SWING) {
            Vec3 look = this.mob.m_20154_().m_82541_();
            this.mob.m_20256_(look.m_82490_(0.65));
            this.mob.f_19812_ = true;
        }
    }

    private void performMeleeAttack(double distanceSquared) {
        if (this.target != null && !this.target.m_21224_()) {
            float range;
            double maxAttackRange;
            int hitFrameIndex = -1;
            for (int i = 0; i < this.currentAttack.data.attackTimestamps.length; ++i) {
                if (this.meleeAnimTimer != this.currentAttack.data.attackTimestamps[i]) continue;
                hitFrameIndex = i;
                break;
            }
            if (distanceSquared <= (maxAttackRange = (double)((range = this.currentAttack.getRangeForHitFrame(hitFrameIndex)) * range))) {
                float effectiveChance;
                float comboRoll;
                float lifestealAmount;
                double baseDamage = this.mob.m_21133_(Attributes.f_22281_);
                float finalDamage = (float)(baseDamage * (double)this.currentAttack.damageMultiplier);
                float extraHPDamage = this.target.m_21233_() * this.currentAttack.hpBasedDamage;
                float totalDamage = finalDamage + extraHPDamage;
                if (!TOGeneralUtils.isPlayerActuallyBlocking(this.target, (LivingEntity)this.mob) && (lifestealAmount = totalDamage * this.nightwarden.getResurgenceScaledLifesteal(this.currentAttack.lifesteal)) > 0.0f) {
                    this.mob.m_5634_(lifestealAmount);
                }
                boolean hit = this.target.m_6469_(this.mob.m_269291_().m_269333_((LivingEntity)this.mob), totalDamage);
                this.target.f_19802_ = 0;
                if (hit && this.currentAttack.data.isSingleHit() && (comboRoll = this.mob.m_217043_().m_188501_()) < (effectiveChance = this.comboChance * (this.target.m_21254_() ? 2.0f : 1.0f))) {
                    this.queueCombo = this.randomizeNextAttack();
                }
            }
        }
    }

    private void forceFaceTarget() {
        double dx = this.target.m_20185_() - this.mob.m_20185_();
        double dz = this.target.m_20189_() - this.mob.m_20189_();
        float angle = (float)(Mth.m_14136_((double)dz, (double)dx) * 57.29577951308232) - 90.0f;
        this.mob.m_5618_(angle);
        this.mob.m_5616_(angle);
        this.mob.m_146922_(angle);
    }

    private AttackType randomizeNextAttack() {
        if (this.target != null && !this.target.m_21224_()) {
            double distance = this.mob.m_20270_((Entity)this.target);
            RandomSource rand = this.mob.m_217043_();
            if (this.nightwarden.isPhase(NightwardenBossEntity.Phase.THIRD)) {
                AttackType chosen;
                if (this.scytheUltimateCooldown <= 0) {
                    this.scytheUltimateCooldown = 2400;
                    return AttackType.SHEAR_OF_THE_STARS;
                }
                if (distance > 4.0 && distance <= 6.0 && rand.m_188501_() < 0.8f) {
                    List<AttackType> group = List.of(AttackType.SCYTHE_CLONE_SLASHES, AttackType.JUMP_COMBO, AttackType.TEN_COMBO);
                    return group.get(rand.m_188503_(group.size()));
                }
                if (distance > 6.0 && distance <= 10.0 && rand.m_188501_() < 0.8f) {
                    AttackType chosen2;
                    ArrayList<AttackType> group = new ArrayList<AttackType>(List.of(AttackType.SCYTHE_SPINNING_CLONE, AttackType.SCYTHE_CLONE_SLASHES));
                    if (this.bigSlamClonesCooldown <= 0) {
                        group.add(AttackType.BIG_SLAM_CLONES);
                    }
                    if ((chosen2 = (AttackType)((Object)group.get(rand.m_188503_(group.size())))) == AttackType.BIG_SLAM_CLONES) {
                        this.bigSlamClonesCooldown = 500;
                    }
                    return chosen2;
                }
                if (distance > 10.0 && rand.m_188501_() < 0.8f) {
                    AttackType chosen3;
                    ArrayList<AttackType> group = new ArrayList<AttackType>(List.of(AttackType.TELEPORT_COMBO, AttackType.SCYTHE_SPIN_FORWARD_PHASE_THREE));
                    if (this.bigSlamClonesCooldown <= 0) {
                        group.add(AttackType.BIG_SLAM_CLONES);
                    }
                    if (this.scytheGroundSlamCloneCooldown <= 0) {
                        group.add(AttackType.SCYTHE_GROUND_SLAM_CLONE);
                    }
                    if ((chosen3 = (AttackType)((Object)group.get(rand.m_188503_(group.size())))) == AttackType.BIG_SLAM_CLONES) {
                        this.bigSlamClonesCooldown = 500;
                    }
                    if (chosen3 == AttackType.SCYTHE_GROUND_SLAM_CLONE) {
                        this.scytheGroundSlamCloneCooldown = 400;
                    }
                    return chosen3;
                }
                ArrayList<AttackType> fallbackPhase3 = new ArrayList<AttackType>(List.of(AttackType.RIGHT_SWING, AttackType.LEFT_SWING, AttackType.TEN_COMBO, AttackType.TELEPORT_COMBO, AttackType.JUMP_COMBO, AttackType.SCYTHE_SPIN_FORWARD_PHASE_THREE, AttackType.SCYTHE_CLONE_SLASHES, AttackType.SCYTHE_SPINNING_CLONE, AttackType.BIG_SLAM_CLONES));
                if (this.scytheGroundSlamCloneCooldown <= 0) {
                    fallbackPhase3.add(AttackType.SCYTHE_GROUND_SLAM_CLONE);
                }
                if ((chosen = (AttackType)((Object)fallbackPhase3.get(rand.m_188503_(fallbackPhase3.size())))) == AttackType.SCYTHE_GROUND_SLAM_CLONE) {
                    this.scytheGroundSlamCloneCooldown = 400;
                }
                return chosen;
            }
            if (this.scytheUltimateCooldown <= 0) {
                this.scytheUltimateCooldown = 2400;
                return AttackType.SHEAR_OF_THE_STARS;
            }
            if (distance > 4.0 && distance <= 6.0 && rand.m_188501_() < 0.8f) {
                List<AttackType> group = List.of(AttackType.SCYTHE_THROW, AttackType.SCYTHE_CLONE_SLASHES, AttackType.JUMP_COMBO, AttackType.TEN_COMBO);
                return group.get(rand.m_188503_(group.size()));
            }
            if (distance > 6.0 && distance <= 10.0 && rand.m_188501_() < 0.8f) {
                AttackType chosen;
                ArrayList<AttackType> group = new ArrayList<AttackType>(List.of(AttackType.SCYTHE_CLONE_SLASHES));
                if (this.bigSlamCooldown <= 0) {
                    group.add(AttackType.BIG_SLAM);
                }
                if (this.bigSlamClonesCooldown <= 0) {
                    group.add(AttackType.BIG_SLAM_CLONES);
                }
                if ((chosen = (AttackType)((Object)group.get(rand.m_188503_(group.size())))) == AttackType.BIG_SLAM) {
                    this.bigSlamCooldown = 500;
                }
                if (chosen == AttackType.BIG_SLAM_CLONES) {
                    this.bigSlamClonesCooldown = 500;
                }
                return chosen;
            }
            if (distance > 10.0 && rand.m_188501_() < 0.8f) {
                AttackType chosen;
                ArrayList<AttackType> group = new ArrayList<AttackType>(List.of(AttackType.TELEPORT_COMBO, AttackType.SCYTHE_SPIN_FORWARD));
                if (this.bigSlamClonesCooldown <= 0) {
                    group.add(AttackType.BIG_SLAM_CLONES);
                }
                if ((chosen = (AttackType)((Object)group.get(rand.m_188503_(group.size())))) == AttackType.BIG_SLAM_CLONES) {
                    this.bigSlamClonesCooldown = 500;
                }
                return chosen;
            }
            List<AttackType> fullyRandomAttacks = List.of(AttackType.RIGHT_SWING, AttackType.LEFT_SWING, AttackType.TEN_COMBO, AttackType.SCYTHE_THROW, AttackType.TELEPORT_COMBO, AttackType.JUMP_COMBO, AttackType.BIG_SLAM, AttackType.SCYTHE_SPIN_FORWARD, AttackType.SCYTHE_CLONE_SLASHES, AttackType.BIG_SLAM_CLONES);
            return fullyRandomAttacks.get(rand.m_188503_(fullyRandomAttacks.size()));
        }
        return AttackType.RIGHT_SWING;
    }

    @Override
    protected void doMeleeAction() {
        this.currentAttack = this.nextAttack;
        if (this.currentAttack != null) {
            this.mob.m_6674_(InteractionHand.MAIN_HAND);
            this.meleeAnimTimer = this.currentAttack.data.lengthInTicks;
            Messages.sendToPlayersTrackingEntity((Object)new ClientboundSyncAnimation(this.currentAttack.toString(), (Entity)this.nightwarden), (Entity)this.nightwarden);
        }
    }

    public void stopMeleeAction() {
        if (this.currentAttack != null) {
            this.meleeAnimTimer = 0;
            Messages.sendToPlayersTrackingEntity((Object)new ClientboundSyncAnimation("", (Entity)this.nightwarden), (Entity)this.nightwarden);
        }
    }

    @Override
    protected void doMovement(double distanceSquared) {
        if (this.target == null || this.target.m_21224_()) {
            this.mob.m_21573_().m_26573_();
            return;
        }
        this.mob.m_21391_((Entity)this.target, 30.0f, 30.0f);
        double baseSpeed = (double)(this.nightwarden.isCasting() ? 0.75f : 1.0f) * this.speedModifier;
        if (this.meleeAnimTimer > 0 && this.currentAttack != null) {
            baseSpeed *= (double)this.currentAttack.movementSlowdown;
        }
        float strafeMultiplier = 1.0f;
        float meleeRangeSqr = this.meleeRange * this.meleeRange;
        float maxStrafeRange = this.meleeRange * 3.0f;
        if (distanceSquared < (double)(meleeRangeSqr * 4.0f) && this.seeTime >= 5) {
            this.mob.m_21573_().m_26573_();
            if (++this.strafeTime > 40 && this.mob.m_217043_().m_188500_() < 0.08) {
                this.strafingClockwise = !this.strafingClockwise;
                this.strafeTime = 0;
            }
            float strafeForward = (float)this.speedModifier;
            if (distanceSquared > (double)(maxStrafeRange * maxStrafeRange)) {
                strafeForward *= 2.0f;
            } else if (distanceSquared > (double)(meleeRangeSqr * 1.3f)) {
                strafeForward *= 1.3f;
            } else if (distanceSquared < 2.0) {
                strafeForward *= -0.7f;
            }
            int strafeDir = this.strafingClockwise ? 1 : -1;
            this.mob.m_21566_().m_24988_(strafeForward * strafeMultiplier, (float)baseSpeed * (float)strafeDir * strafeMultiplier);
        } else {
            BlockPos targetPos;
            if (this.mob.f_19797_ % 5 == 0) {
                this.mob.m_21570_(0.0f);
                this.mob.m_21573_().m_5624_((Entity)this.target, baseSpeed);
            }
            if ((targetPos = this.mob.m_21573_().m_26567_()) != null && this.mob.m_20096_() && this.mob.m_146904_() + 1 < targetPos.m_123342_() && this.mob.m_21573_().m_26571_() && this.mob.f_19862_) {
                this.mob.m_21569_().m_24901_();
            }
        }
    }

    public NightwardenAttackGoal setComboChance(float chance) {
        this.comboChance = chance;
        return this;
    }

    public boolean m_8045_() {
        return super.m_8045_() || this.meleeAnimTimer > 0;
    }

    @Override
    public void m_8037_() {
        super.m_8037_();
    }

    public void m_8041_() {
        super.m_8041_();
        this.meleeAnimTimer = -1;
        this.queueCombo = null;
    }

    public static enum AttackType {
        RIGHT_SWING(26, "nightwarden_scythe_right_swing", 0.2f, 1.0f, 0.05f, 3.8f, 0.05f, null, 9),
        LEFT_SWING(26, "nightwarden_scythe_left_swing", 0.2f, 1.0f, 0.05f, 3.8f, 0.05f, null, 9),
        TEN_COMBO(160, "nightwarden_scythe_ten_combo", 0.01f, 1.0f, 0.02f, 4.0f, 0.1f, null, 9, 24, 38, 53, 67, 82, 96, 111, 125, 127, 141, 143),
        SCYTHE_THROW(50, "nightwarden_scythe_throw", 0.0f, 1.0f, 0.02f, 6.0f, 0.1f, null, 15, 20, 25, 30),
        TELEPORT_COMBO(145, "nightwarden_teleport_spin", 0.0f, 1.2f, 0.03f, 4.5f, 0.25f, null, 80, 85, 90, 110, 115, 120),
        JUMP_COMBO(90, "nightwarden_scythe_jump_combo", 0.01f, 1.2f, 0.02f, 5.0f, 0.15f, null, 9, 36, 66, 71, 77),
        BIG_SLAM(70, "nightwarden_scythe_big_slam", 0.0f, 2.35f, 0.03f, 7.0f, 0.2f, null, 48),
        SHEAR_OF_THE_STARS(220, "nightwarden_scythe_ult", 0.0f, 5.0f, 0.5f, 6.5f, 2.0f, null, 157),
        SCYTHE_SPIN_FORWARD(70, "nightwarden_scythe_spin_forward", 0.01f, 1.35f, 0.03f, 3.2f, 0.25f, null, 40, 45, 50, 55, 60),
        SCYTHE_CLONE_SLASHES(165, "nightwarden_scythe_clone_slashes", 0.01f, 1.5f, 0.05f, 4.0f, 0.2f, null, 147),
        BIG_SLAM_CLONES(70, "nightwarden_scythe_big_slam", 0.0f, 2.85f, 0.03f, 7.0f, 0.2f, null, 48),
        SCYTHE_GROUND_SLAM_CLONE(220, "nightwarden_scythe_ground_slam_clone", 0.0f, 1.4f, 0.04f, 4.2f, 0.25f, null, 210),
        SCYTHE_SPINNING_CLONE(180, "nightwarden_scythe_spinning_clone", 0.02f, 0.8f, 0.03f, 4.0f, 0.1f, null, 115, 120, 125, 150, 155, 160),
        SCYTHE_SPIN_FORWARD_PHASE_THREE(70, "nightwarden_scythe_spin_forward_phase_three", 0.01f, 1.5f, 0.03f, 3.2f, 0.25f, null, 40, 45, 50, 55, 60);

        public final AttackAnimationData data;
        public final float movementSlowdown;
        public final float damageMultiplier;
        public final float hpBasedDamage;
        public final float attackRange;
        public final float lifesteal;
        public final float[] perHitRange;

        private AttackType(int lengthInTicks, String animationId, float movementSlowdown, float damageMultiplier, float hpBasedDamage, float attackRange, float lifesteal, float[] perHitRange, int ... attackTimestamps) {
            this.data = new AttackAnimationData(lengthInTicks, animationId, attackTimestamps);
            this.movementSlowdown = movementSlowdown;
            this.damageMultiplier = damageMultiplier;
            this.hpBasedDamage = hpBasedDamage;
            this.attackRange = attackRange;
            this.lifesteal = lifesteal;
            this.perHitRange = perHitRange;
        }

        public float getRangeForHitFrame(int hitIndex) {
            if (this.perHitRange != null && hitIndex >= 0 && hitIndex < this.perHitRange.length) {
                return this.perHitRange[hitIndex];
            }
            return this.attackRange;
        }
    }
}

