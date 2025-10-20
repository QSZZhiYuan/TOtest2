/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.entity.projectile.Phantom_Halberd_Entity
 *  io.redspace.ironsspellbooks.api.spells.AbstractSpell
 *  io.redspace.ironsspellbooks.damage.DamageSources
 *  io.redspace.ironsspellbooks.damage.SpellDamageSource
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 */
package com.gametechbc.traveloptics.entity.extended_projectiles;

import com.gametechbc.traveloptics.init.TravelopticsSpells;
import com.github.L_Ender.cataclysm.entity.projectile.Phantom_Halberd_Entity;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.damage.SpellDamageSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class ExtendedPhantomHalberdEntity
extends Phantom_Halberd_Entity {
    public ExtendedPhantomHalberdEntity(EntityType<? extends Phantom_Halberd_Entity> entityType, Level level) {
        super(entityType, level);
    }

    public ExtendedPhantomHalberdEntity(Level worldIn, double x, double y, double z, float rotation, int warmupDelay, LivingEntity caster, float damage) {
        super(worldIn, x, y, z, rotation, warmupDelay, caster, damage);
    }

    protected void damage(LivingEntity target) {
        LivingEntity caster = this.getCaster();
        if (target.m_6084_() && !target.m_20147_() && target != caster && this.f_19797_ % 5 == 0) {
            if (caster == null) {
                target.m_6469_(this.m_269291_().m_269425_(), this.getDamage());
            } else if (!caster.m_7307_((Entity)target) && !target.m_7307_((Entity)caster)) {
                SpellDamageSource spellDamageSource = ((AbstractSpell)TravelopticsSpells.HALBERD_HORIZON.get()).getDamageSource((Entity)this, (Entity)caster);
                DamageSources.ignoreNextKnockback((LivingEntity)target);
                target.m_6469_((DamageSource)spellDamageSource, this.getDamage());
            }
        }
    }
}

