/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.api.entity.IMagicEntity
 *  io.redspace.ironsspellbooks.entity.mobs.goals.WizardAttackGoal
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.phys.Vec3
 */
package com.gametechbc.traveloptics.api.entity.ai;

import io.redspace.ironsspellbooks.api.entity.IMagicEntity;
import io.redspace.ironsspellbooks.entity.mobs.goals.WizardAttackGoal;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class FlyingWizardAttackGoal
extends WizardAttackGoal {
    private int strafeSwitchCooldown = 0;
    private Vec3 lastTargetVelocity = Vec3.f_82478_;

    public FlyingWizardAttackGoal(IMagicEntity spellCastingMob, double pSpeedModifier, int pAttackIntervalMin, int pAttackIntervalMax) {
        super(spellCastingMob, pSpeedModifier, pAttackIntervalMin, pAttackIntervalMax);
        this.setIsFlying();
    }

    protected void doMovement(double distanceSquared) {
        double speed = (double)(this.spellCastingMob.isCasting() ? 0.75f : 1.0f) * this.movementSpeed();
        this.mob.m_21391_((Entity)this.target, 30.0f, 30.0f);
        Vec3 predictedVelocity = Vec3.f_82478_;
        this.lastTargetVelocity = this.lastTargetVelocity.m_82490_(0.8).m_82549_(predictedVelocity.m_82490_(0.2));
        if (distanceSquared < (double)this.attackRadiusSqr && this.seeTime >= 5) {
            this.mob.m_21573_().m_26573_();
            if (this.strafeSwitchCooldown > 0) {
                --this.strafeSwitchCooldown;
            } else if (++this.strafeTime > 25 && this.mob.m_217043_().m_188500_() < 0.1) {
                this.strafingClockwise = !this.strafingClockwise;
                this.strafeTime = 0;
                this.strafeSwitchCooldown = 15;
            }
            Vec3 toTarget = new Vec3(this.target.m_20185_() - this.mob.m_20185_(), 0.0, this.target.m_20189_() - this.mob.m_20189_());
            if (toTarget.m_82556_() != 0.0) {
                toTarget = toTarget.m_82541_();
            }
            Vec3 perp = new Vec3(-toTarget.f_82481_, 0.0, toTarget.f_82479_);
            double currentDistance = new Vec3(this.target.m_20185_() - this.mob.m_20185_(), 0.0, this.target.m_20189_() - this.mob.m_20189_()).m_82553_();
            double desiredDistance = 10.0;
            Vec3 forwardOffset = toTarget.m_82490_(-(desiredDistance - currentDistance));
            float dynamicStrafeOffset = (distanceSquared < (double)this.attackRadiusSqr * 0.25 ? -1.0f : 0.5f) * 0.2f * (float)this.speedModifier;
            Vec3 strafeVec = perp.m_82490_((double)(dynamicStrafeOffset * 10.0f));
            Vec3 predictionOffset = this.lastTargetVelocity.m_82490_(5.0);
            Vec3 desiredPos = this.target.m_20182_().m_82549_(forwardOffset).m_82549_(strafeVec).m_82549_(predictionOffset).m_82520_(0.0, 2.0, 0.0);
            this.mob.m_21566_().m_6849_(desiredPos.f_82479_, desiredPos.f_82480_, desiredPos.f_82481_, this.speedModifier);
            if (this.mob.f_19862_ && this.mob.m_217043_().m_188501_() < 0.1f) {
                this.tryJump();
            }
        } else if (this.mob.f_19797_ % 5 == 0) {
            Vec3 predictedPos = this.target.m_20182_().m_82549_(this.lastTargetVelocity.m_82490_(3.0));
            this.mob.m_21566_().m_6849_(predictedPos.f_82479_, predictedPos.f_82480_ + 2.0, predictedPos.f_82481_, this.speedModifier);
        }
    }
}

