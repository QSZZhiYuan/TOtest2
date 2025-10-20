/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.entity.projectile.Water_Spear_Entity
 *  io.redspace.ironsspellbooks.api.spells.AbstractSpell
 *  io.redspace.ironsspellbooks.damage.DamageSources
 *  io.redspace.ironsspellbooks.damage.SpellDamageSource
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.EntityHitResult
 *  net.minecraft.world.phys.Vec3
 */
package com.gametechbc.traveloptics.entity.extended_projectiles;

import com.gametechbc.traveloptics.init.TravelopticsEffects;
import com.gametechbc.traveloptics.init.TravelopticsEntities;
import com.gametechbc.traveloptics.init.TravelopticsSpells;
import com.github.L_Ender.cataclysm.entity.projectile.Water_Spear_Entity;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.damage.SpellDamageSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class ExtendedWaterSpearEntity
extends Water_Spear_Entity {
    public ExtendedWaterSpearEntity(EntityType<? extends Water_Spear_Entity> type, Level level) {
        super(type, level);
        this.accelerationPower = 0.1;
    }

    public ExtendedWaterSpearEntity(EntityType<? extends Water_Spear_Entity> type, double getX, double gety, double getz, Vec3 vec3, Level level) {
        this(type, level);
        this.m_20343_(getX, gety, getz);
        this.m_146867_();
        this.setState(1);
        this.m_20090_();
        this.assignDirectionalMovement(vec3, this.accelerationPower);
    }

    public ExtendedWaterSpearEntity(LivingEntity owner, Vec3 vec3, Level level, float damage) {
        this((EntityType<? extends Water_Spear_Entity>)((EntityType)TravelopticsEntities.EXTENDED_WATER_SPEAR.get()), owner.m_20185_(), owner.m_20186_(), owner.m_20189_(), vec3, level);
        this.m_5602_((Entity)owner);
        this.setDamage(damage);
    }

    public ExtendedWaterSpearEntity(EntityType<? extends Water_Spear_Entity> type, LivingEntity owner, double getX, double gety, double getz, Vec3 vec3, float damage, Level level) {
        this(type, level);
        this.m_7678_(getX, gety, getz, this.m_146908_(), this.m_146909_());
        this.m_5602_((Entity)owner);
        this.setDamage(damage);
        this.m_20090_();
        this.assignDirectionalMovement(vec3, this.accelerationPower);
    }

    protected void m_5790_(EntityHitResult hitResult) {
        if (!this.m_9236_().f_46443_) {
            Entity entity = hitResult.m_82443_();
            boolean flag = false;
            Entity entity2 = this.m_19749_();
            if (entity2 instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)entity2;
                if (!(entity.m_7307_((Entity)livingentity) || livingentity.equals((Object)entity) || livingentity.m_7307_(entity))) {
                    SpellDamageSource spellDamageSource = ((AbstractSpell)TravelopticsSpells.THE_HOWLING_TEMPEST.get()).getDamageSource((Entity)this, (Entity)livingentity);
                    DamageSources.applyDamage((Entity)entity, (float)this.getDamage(), (DamageSource)spellDamageSource);
                    flag = true;
                }
            } else {
                DamageSource magicDamageSource = this.m_269291_().m_269425_();
                DamageSources.applyDamage((Entity)entity, (float)5.0f, (DamageSource)magicDamageSource);
                flag = true;
            }
            if (flag && entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)entity;
                MobEffectInstance existingWetEffect = livingEntity.m_21124_((MobEffect)TravelopticsEffects.WET.get());
                if (existingWetEffect != null) {
                    if (this.f_19796_.m_188499_()) {
                        int newDuration = existingWetEffect.m_19557_() + 40;
                        livingEntity.m_7292_(new MobEffectInstance((MobEffect)TravelopticsEffects.WET.get(), newDuration, existingWetEffect.m_19564_(), false, false, true));
                    } else {
                        int newAmplifier = Math.min(existingWetEffect.m_19564_() + 1, 10);
                        livingEntity.m_7292_(new MobEffectInstance((MobEffect)TravelopticsEffects.WET.get(), existingWetEffect.m_19557_(), newAmplifier, false, false, true));
                    }
                } else {
                    livingEntity.m_7292_(new MobEffectInstance((MobEffect)TravelopticsEffects.WET.get(), 40, 0, false, false, true));
                }
            }
        }
    }
}

