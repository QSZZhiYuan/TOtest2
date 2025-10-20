/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
 */
package com.gametechbc.traveloptics.api.entity.ai;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;

public class AdvancedHurtByTargetGoal
extends HurtByTargetGoal {
    private final Map<LivingEntity, Integer> attackerMemory = new HashMap<LivingEntity, Integer>();
    private int forcedAggroTime;
    private float intensity;

    public AdvancedHurtByTargetGoal(PathfinderMob mob, Class<?> ... ToIgnoreDamage) {
        super(mob, (Class[])ToIgnoreDamage);
    }

    public void m_8037_() {
        super.m_8037_();
        LivingEntity lastAttacker = this.f_26135_.m_21188_();
        if (lastAttacker != null) {
            this.attackerMemory.put(lastAttacker, 100);
        }
        Iterator<Map.Entry<LivingEntity, Integer>> iterator = this.attackerMemory.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<LivingEntity, Integer> entry = iterator.next();
            int timeLeft = entry.getValue() - 1;
            if (timeLeft <= 0) {
                iterator.remove();
                continue;
            }
            entry.setValue(timeLeft);
        }
        if (this.f_26034_ != this.f_26135_.m_21213_()) {
            this.f_26034_ = this.f_26135_.m_21213_();
            if (lastAttacker != this.f_26137_) {
                this.forcedAggroTime -= 20;
            } else {
                this.forcedAggroTime += (int)(20.0f * this.intensity);
                this.intensity *= 0.8f;
            }
            LivingEntity newTarget = this.getMostRelevantAttacker();
            if (newTarget != null && newTarget != this.f_26135_.m_5448_()) {
                this.f_26135_.m_6710_(newTarget);
            }
        }
    }

    public void m_8056_() {
        super.m_8056_();
        this.forcedAggroTime = 40 + this.f_26135_.m_217043_().m_188503_(140);
        this.intensity = 1.0f;
    }

    public boolean m_8045_() {
        return --this.forcedAggroTime > 0 && super.m_8045_();
    }

    private LivingEntity getMostRelevantAttacker() {
        return this.attackerMemory.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(null);
    }
}

