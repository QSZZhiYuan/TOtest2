/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Position
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.entity.ai.util.DefaultRandomPos
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.VoxelShape
 */
package com.gametechbc.traveloptics.api.entity.ai;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class InternalMeleeAttackGoal
extends Goal {
    protected LivingEntity target;
    protected final double speedModifier;
    protected final int attackIntervalMin;
    protected final int attackIntervalMax;
    protected float attackRadius;
    protected float attackRadiusSqr;
    protected boolean hasLineOfSight;
    protected int seeTime = 0;
    protected int strafeTime;
    protected boolean strafingClockwise;
    protected int attackTime = -1;
    protected boolean isFlying;
    protected boolean allowFleeing;
    protected int fleeCooldown;
    protected final PathfinderMob mob;
    protected float meleeRange;
    protected boolean wantsToMelee;
    protected int meleeTime;
    protected int meleeDecisionTime;
    protected float meleeBiasMin;
    protected float meleeBiasMax;
    protected float meleeMoveSpeedModifier;
    protected int meleeAttackIntervalMin;
    protected int meleeAttackIntervalMax;

    public InternalMeleeAttackGoal(PathfinderMob mob, double pSpeedModifier, int pAttackInterval, float meleeRange) {
        this(mob, pSpeedModifier, pAttackInterval, pAttackInterval, meleeRange);
    }

    public InternalMeleeAttackGoal(PathfinderMob mob, double pSpeedModifier, int pAttackIntervalMin, int pAttackIntervalMax, float meleeRange) {
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        this.mob = mob;
        this.speedModifier = pSpeedModifier;
        this.attackIntervalMin = pAttackIntervalMin;
        this.attackIntervalMax = pAttackIntervalMax;
        this.attackRadius = 20.0f;
        this.attackRadiusSqr = this.attackRadius * this.attackRadius;
        this.allowFleeing = false;
        this.meleeRange = meleeRange;
        this.meleeDecisionTime = mob.m_217043_().m_216332_(80, 200);
        this.meleeBiasMin = 0.25f;
        this.meleeBiasMax = 0.75f;
        this.meleeMoveSpeedModifier = (float)pSpeedModifier;
        this.meleeAttackIntervalMin = pAttackIntervalMin;
        this.meleeAttackIntervalMax = pAttackIntervalMax;
        this.wantsToMelee = true;
    }

    public InternalMeleeAttackGoal setMeleeBias(float meleeBiasMin, float meleeBiasMax) {
        this.meleeBiasMin = meleeBiasMin;
        this.meleeBiasMax = meleeBiasMax;
        return this;
    }

    public InternalMeleeAttackGoal setIsFlying() {
        this.isFlying = true;
        return this;
    }

    public InternalMeleeAttackGoal setAllowFleeing(boolean allowFleeing) {
        this.allowFleeing = allowFleeing;
        return this;
    }

    public InternalMeleeAttackGoal setMeleeMovespeedModifier(float meleeMovespeedModifier) {
        this.meleeMoveSpeedModifier = meleeMovespeedModifier;
        return this;
    }

    public InternalMeleeAttackGoal setMeleeAttackInterval(int min, int max) {
        this.meleeAttackIntervalMax = max;
        this.meleeAttackIntervalMin = min;
        return this;
    }

    public boolean m_8036_() {
        LivingEntity livingentity = this.mob.m_5448_();
        if (livingentity != null && livingentity.m_6084_()) {
            this.target = livingentity;
            return true;
        }
        return false;
    }

    public boolean m_8045_() {
        return this.m_8036_() || this.target.m_6084_() && !this.mob.m_21573_().m_26571_();
    }

    public void m_8041_() {
        this.target = null;
        this.seeTime = 0;
        this.attackTime = -1;
        this.mob.m_21561_(false);
        this.mob.m_21566_().m_24988_(0.0f, 0.0f);
    }

    public boolean m_183429_() {
        return true;
    }

    public void m_8037_() {
        if (this.target == null) {
            return;
        }
        double distanceSquared = this.mob.m_20275_(this.target.m_20185_(), this.target.m_20186_(), this.target.m_20189_());
        this.hasLineOfSight = this.mob.m_21574_().m_148306_((Entity)this.target);
        this.seeTime = this.hasLineOfSight ? ++this.seeTime : --this.seeTime;
        if (++this.meleeTime > this.meleeDecisionTime) {
            this.meleeTime = 0;
            this.wantsToMelee = this.mob.m_217043_().m_188501_() <= this.meleeBias();
            this.meleeDecisionTime = this.mob.m_217043_().m_216332_(60, 120);
        }
        this.doMovement(distanceSquared);
        if (this.mob.m_21213_() == this.mob.f_19797_ - 1) {
            int t;
            this.attackTime = t = (int)(Mth.m_14179_((float)0.6f, (float)this.attackTime, (float)0.0f) + 1.0f);
        }
        this.handleAttackLogic(distanceSquared);
    }

    protected float meleeBias() {
        return Mth.m_144920_((float)this.meleeBiasMin, (float)this.meleeBiasMax, (float)(this.mob.m_21223_() / this.mob.m_21233_()));
    }

    protected void handleAttackLogic(double distanceSquared) {
        if (this.seeTime < -50) {
            return;
        }
        if (!this.wantsToMelee || distanceSquared > (double)(this.meleeRange * this.meleeRange)) {
            if (--this.attackTime == 0) {
                this.resetAttackTimer(distanceSquared);
            } else if (this.attackTime < 0) {
                this.attackTime = Mth.m_14107_((double)Mth.m_14139_((double)(Math.sqrt(distanceSquared) / (double)this.attackRadius), (double)this.attackIntervalMin, (double)this.attackIntervalMax));
            }
        } else if (--this.attackTime == 0) {
            this.mob.m_6674_(InteractionHand.MAIN_HAND);
            this.doMeleeAction();
        }
    }

    protected void resetAttackTimer(double distanceSquared) {
        if (!this.wantsToMelee || distanceSquared > (double)(this.meleeRange * this.meleeRange)) {
            float f = (float)Math.sqrt(distanceSquared) / this.attackRadius;
            this.attackTime = Mth.m_14143_((float)(f * (float)(this.attackIntervalMax - this.attackIntervalMin) + (float)this.attackIntervalMin));
        } else {
            float f = (float)Math.sqrt(distanceSquared) / this.attackRadius;
            this.attackTime = Mth.m_14143_((float)(f * (float)(this.meleeAttackIntervalMax - this.meleeAttackIntervalMin) + (float)this.meleeAttackIntervalMin));
        }
    }

    protected void doMovement(double distanceSquared) {
        if (!this.wantsToMelee) {
            double speed = this.movementSpeed();
            this.mob.m_21391_((Entity)this.target, 30.0f, 30.0f);
            float fleeDist = 0.275f;
            if (this.allowFleeing && this.attackTime > 10 && --this.fleeCooldown <= 0 && distanceSquared < (double)(this.attackRadiusSqr * (fleeDist * fleeDist))) {
                Vec3 flee = DefaultRandomPos.m_148407_((PathfinderMob)this.mob, (int)16, (int)7, (Vec3)this.target.m_20182_());
                if (flee != null) {
                    this.mob.m_21573_().m_26519_(flee.f_82479_, flee.f_82480_, flee.f_82481_, speed * 1.5);
                } else {
                    this.mob.m_21566_().m_24988_(-((float)speed), (float)speed);
                }
            } else if (distanceSquared < (double)this.attackRadiusSqr && this.seeTime >= 5) {
                this.mob.m_21573_().m_26573_();
                if (++this.strafeTime > 25 && this.mob.m_217043_().m_188500_() < 0.1) {
                    this.strafingClockwise = !this.strafingClockwise;
                    this.strafeTime = 0;
                }
                float strafeForward = (distanceSquared * 6.0 < (double)this.attackRadiusSqr ? -1.0f : 0.5f) * 0.2f * (float)this.speedModifier;
                int strafeDir = this.strafingClockwise ? 1 : -1;
                this.mob.m_21566_().m_24988_(strafeForward, (float)speed * (float)strafeDir);
                if (this.mob.f_19862_ && this.mob.m_217043_().m_188501_() < 0.1f) {
                    this.tryJump();
                }
            } else if (this.mob.f_19797_ % 5 == 0) {
                if (this.isFlying) {
                    this.mob.m_21566_().m_6849_(this.target.m_20185_(), this.target.m_20186_() + 2.0, this.target.m_20189_(), this.speedModifier);
                } else {
                    this.mob.m_21573_().m_5624_((Entity)this.target, this.speedModifier);
                }
            }
            return;
        }
        if (this.target.m_21224_()) {
            this.mob.m_21573_().m_26573_();
        } else {
            this.mob.m_21391_((Entity)this.target, 30.0f, 30.0f);
            float strafeForwards = 0.0f;
            float speed = (float)this.movementSpeed();
            if (distanceSquared > (double)(this.meleeRange * this.meleeRange)) {
                if (this.mob.f_19797_ % 5 == 0) {
                    this.mob.m_21573_().m_5624_((Entity)this.target, (double)this.meleeMoveSpeedModifier);
                }
                this.mob.m_21566_().m_24988_(0.0f, 0.0f);
            } else {
                this.mob.m_21573_().m_26573_();
                strafeForwards = 0.25f * this.meleeMoveSpeedModifier * (4.0 * distanceSquared > (double)(this.meleeRange * this.meleeRange) ? 1.5f : -1.0f);
                if (++this.strafeTime > 25 && this.mob.m_217043_().m_188500_() < 0.1) {
                    this.strafingClockwise = !this.strafingClockwise;
                    this.strafeTime = 0;
                }
                float strafeDir = this.strafingClockwise ? 1.0f : -1.0f;
                this.mob.m_21566_().m_24988_(strafeForwards, speed * strafeDir);
            }
            this.mob.m_21563_().m_148051_((Entity)this.target);
        }
    }

    protected double movementSpeed() {
        return this.wantsToMelee ? (double)this.meleeMoveSpeedModifier * this.mob.m_21133_(Attributes.f_22279_) * 2.0 : this.speedModifier * this.mob.m_21133_(Attributes.f_22279_) * 2.0;
    }

    protected void tryJump() {
        Vec3 nextBlock = new Vec3((double)this.mob.f_20900_, 0.0, (double)this.mob.f_20902_).m_82541_();
        BlockPos blockpos = BlockPos.m_274446_((Position)this.mob.m_20182_().m_82549_(nextBlock));
        BlockState blockstate = this.mob.m_9236_().m_8055_(blockpos);
        VoxelShape voxelshape = blockstate.m_60812_((BlockGetter)this.mob.m_9236_(), blockpos);
        if (!(voxelshape.m_83281_() || blockstate.m_204336_(BlockTags.f_13103_) || blockstate.m_204336_(BlockTags.f_13039_))) {
            BlockPos blockposAbove = blockpos.m_7494_();
            BlockState blockstateAbove = this.mob.m_9236_().m_8055_(blockposAbove);
            VoxelShape voxelshapeAbove = blockstateAbove.m_60812_((BlockGetter)this.mob.m_9236_(), blockposAbove);
            if (voxelshapeAbove.m_83281_()) {
                this.mob.m_21569_().m_24901_();
                this.mob.m_21570_(this.mob.f_20900_ * 5.0f);
                this.mob.m_21564_(this.mob.f_20902_ * 5.0f);
            }
        }
    }

    protected void doMeleeAction() {
        double distanceSquared = this.mob.m_20275_(this.target.m_20185_(), this.target.m_20186_(), this.target.m_20189_());
        this.mob.m_7327_((Entity)this.target);
        this.resetAttackTimer(distanceSquared);
    }

    public void m_8056_() {
        super.m_8056_();
        this.mob.m_21561_(true);
    }
}

