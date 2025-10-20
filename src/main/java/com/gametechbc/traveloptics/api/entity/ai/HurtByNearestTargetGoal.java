/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
 */
package com.gametechbc.traveloptics.api.entity.ai;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;

public class HurtByNearestTargetGoal
extends HurtByTargetGoal {
    public HurtByNearestTargetGoal(PathfinderMob creatureIn, Class<?> ... excludeReinforcementTypes) {
        super(creatureIn, (Class[])excludeReinforcementTypes);
    }

    public boolean m_8036_() {
        if (!super.m_8036_()) {
            LivingEntity lastTarget = this.f_26135_.m_21214_();
            if (lastTarget != null && this.f_26135_.m_21188_() == null) {
                this.f_26135_.m_6703_(lastTarget);
            }
            return false;
        }
        return true;
    }

    public boolean m_8045_() {
        if (!super.m_8045_()) {
            return false;
        }
        LivingEntity revengeTarget = this.f_26135_.m_21188_();
        if (super.m_8036_() && revengeTarget != this.f_26137_ && this.f_26137_ != null && revengeTarget != null && this.f_26135_.m_20280_((Entity)revengeTarget) < this.f_26135_.m_20280_((Entity)this.f_26137_)) {
            this.f_26135_.m_21335_((Entity)this.f_26137_);
            return false;
        }
        return true;
    }
}

