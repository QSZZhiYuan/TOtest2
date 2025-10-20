/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.entity.projectile.Storm_Serpent_Entity
 *  io.redspace.ironsspellbooks.api.spells.AbstractSpell
 *  io.redspace.ironsspellbooks.damage.DamageSources
 *  io.redspace.ironsspellbooks.damage.SpellDamageSource
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 */
package com.gametechbc.traveloptics.entity.extended_projectiles;

import com.gametechbc.traveloptics.init.TravelopticsEffects;
import com.gametechbc.traveloptics.init.TravelopticsSpells;
import com.github.L_Ender.cataclysm.entity.projectile.Storm_Serpent_Entity;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.damage.SpellDamageSource;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class ExtendedStormSerpentEntity
extends Storm_Serpent_Entity {
    private int wetAmplifier = 4;
    private float aoeRadius = 3.0f;
    private boolean hasHitTarget = false;

    public ExtendedStormSerpentEntity(EntityType<? extends Storm_Serpent_Entity> entityType, Level level) {
        super(entityType, level);
    }

    public ExtendedStormSerpentEntity(Level worldIn, double x, double y, double z, float rotation, int warmupDelay, LivingEntity caster, float damage, LivingEntity finalTarget, boolean right) {
        super(worldIn, x, y, z, rotation, warmupDelay, caster, damage, finalTarget, right);
    }

    public int getWetAmplifier() {
        return this.wetAmplifier;
    }

    public void setWetAmplifier(int amplifier) {
        this.wetAmplifier = amplifier;
    }

    public float getAoeRadius() {
        return this.aoeRadius;
    }

    public void setAoeRadius(float radius) {
        this.aoeRadius = radius;
    }

    public void m_8119_() {
        boolean hitSomething;
        super.m_8119_();
        if (!(this.m_9236_().f_46443_ || this.hasHitTarget || this.lifeTicks != 52 && this.lifeTicks != 53 && this.lifeTicks != 54 && this.lifeTicks != 55 || !(hitSomething = this.triggerAOE()))) {
            this.hasHitTarget = true;
        }
    }

    private boolean triggerAOE() {
        LivingEntity caster = this.getCaster();
        if (caster == null) {
            return false;
        }
        double theta = Math.toRadians(this.m_146908_());
        double vecX = this.m_20185_() + Math.cos(theta) * 8.0;
        double vecZ = this.m_20189_() + Math.sin(theta) * 8.0;
        AABB aoeBounds = new AABB(vecX - (double)this.aoeRadius, this.m_20186_() - 2.0, vecZ - (double)this.aoeRadius, vecX + (double)this.aoeRadius, this.m_20186_() + (double)this.m_20206_(), vecZ + (double)this.aoeRadius);
        List entitiesInRange = this.m_9236_().m_45976_(LivingEntity.class, aoeBounds);
        boolean hitAtLeastOne = false;
        for (LivingEntity entity : entitiesInRange) {
            if (!entity.m_6084_() || entity.m_20147_() || entity == caster || caster.m_7307_((Entity)entity) || entity.m_7307_((Entity)caster)) continue;
            SpellDamageSource spellDamageSource = ((AbstractSpell)TravelopticsSpells.SERPENTIDE_SPELL.get()).getDamageSource((Entity)this, (Entity)caster);
            DamageSources.applyDamage((Entity)entity, (float)this.getDamage(), (DamageSource)spellDamageSource);
            MobEffectInstance wetEffect = new MobEffectInstance((MobEffect)TravelopticsEffects.WET.get(), 100, this.getWetAmplifier(), false, false, true);
            entity.m_7292_(wetEffect);
            hitAtLeastOne = true;
        }
        return hitAtLeastOne;
    }

    protected void damage(LivingEntity target) {
    }

    protected void m_7378_(CompoundTag compound) {
        super.m_7378_(compound);
        if (compound.m_128441_("WetAmplifier")) {
            this.wetAmplifier = compound.m_128451_("WetAmplifier");
        }
        if (compound.m_128441_("AoeRadius")) {
            this.aoeRadius = compound.m_128457_("AoeRadius");
        }
        if (compound.m_128441_("HasHitTarget")) {
            this.hasHitTarget = compound.m_128471_("HasHitTarget");
        }
    }

    protected void m_7380_(CompoundTag compound) {
        super.m_7380_(compound);
        compound.m_128405_("WetAmplifier", this.wetAmplifier);
        compound.m_128350_("AoeRadius", this.aoeRadius);
        compound.m_128379_("HasHitTarget", this.hasHitTarget);
    }
}

