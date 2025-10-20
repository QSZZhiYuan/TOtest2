/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.entity.effect.Flame_Strike_Entity
 *  com.github.L_Ender.cataclysm.init.ModEffect
 *  io.redspace.ironsspellbooks.api.spells.AbstractSpell
 *  io.redspace.ironsspellbooks.damage.DamageSources
 *  net.minecraft.util.Mth
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 */
package com.gametechbc.traveloptics.entity.extended_projectiles;

import com.gametechbc.traveloptics.init.TravelopticsSpells;
import com.github.L_Ender.cataclysm.entity.effect.Flame_Strike_Entity;
import com.github.L_Ender.cataclysm.init.ModEffect;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.damage.DamageSources;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class ExtendedFlameStrikeEntity
extends Flame_Strike_Entity {
    public ExtendedFlameStrikeEntity(EntityType<? extends Flame_Strike_Entity> entityType, Level level) {
        super(entityType, level);
    }

    public ExtendedFlameStrikeEntity(Level level, double x, double y, double z, float rotation, int duration, int wait, int delay, float radius, float damage, float hpDamage, boolean soul, LivingEntity caster) {
        super(level, x, y, z, rotation, duration, wait, delay, radius, damage, hpDamage, soul, caster);
    }

    protected void damage(LivingEntity target) {
        LivingEntity caster = this.getOwner();
        if (target.m_6084_() && !target.m_20147_() && target != caster && this.f_19797_ % 2 == 0) {
            DamageSource spellDamageSource;
            if (caster == null) {
                spellDamageSource = this.m_269291_().m_269425_();
            } else if (!caster.m_7307_((Entity)target) && !target.m_7307_((Entity)caster)) {
                spellDamageSource = ((AbstractSpell)TravelopticsSpells.BURNING_JUDGMENT.get()).getDamageSource((Entity)this, (Entity)caster);
            } else {
                return;
            }
            float totalDamage = this.getDamage() + target.m_21233_() * 0.01f * this.getHpDamage();
            boolean flag = DamageSources.applyDamage((Entity)target, (float)totalDamage, (DamageSource)spellDamageSource);
            if (flag) {
                MobEffectInstance existingEffect = target.m_21124_((MobEffect)ModEffect.EFFECTBLAZING_BRAND.get());
                int amplifier = 1;
                if (existingEffect != null) {
                    amplifier += existingEffect.m_19564_();
                    target.m_6234_((MobEffect)ModEffect.EFFECTBLAZING_BRAND.get());
                } else {
                    --amplifier;
                }
                amplifier = Mth.m_14045_((int)amplifier, (int)0, (int)4);
                MobEffectInstance newEffect = new MobEffectInstance((MobEffect)ModEffect.EFFECTBLAZING_BRAND.get(), 200, amplifier, false, false, true);
                target.m_7292_(newEffect);
            }
        }
    }
}

