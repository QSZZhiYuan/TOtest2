/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.entity.projectile.Wither_Homing_Missile_Entity
 *  io.redspace.ironsspellbooks.api.spells.AbstractSpell
 *  io.redspace.ironsspellbooks.damage.DamageSources
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.Level$ExplosionInteraction
 *  net.minecraft.world.phys.EntityHitResult
 *  net.minecraft.world.phys.Vec3
 */
package com.gametechbc.traveloptics.entity.extended_projectiles;

import com.gametechbc.traveloptics.init.TravelopticsSpells;
import com.github.L_Ender.cataclysm.entity.projectile.Wither_Homing_Missile_Entity;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.damage.DamageSources;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class ExtendedWitherHomingMissileEntity
extends Wither_Homing_Missile_Entity {
    private static final EntityDataAccessor<Float> EXTENDED_DAMAGE = SynchedEntityData.m_135353_(ExtendedWitherHomingMissileEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135029_);
    private static final EntityDataAccessor<Boolean> USE_MAGIC_DAMAGE = SynchedEntityData.m_135353_(ExtendedWitherHomingMissileEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);

    public ExtendedWitherHomingMissileEntity(EntityType<? extends Wither_Homing_Missile_Entity> entityType, Level level) {
        super(entityType, level);
    }

    public ExtendedWitherHomingMissileEntity(Level level, LivingEntity owner) {
        super(level, owner);
    }

    public ExtendedWitherHomingMissileEntity(EntityType<? extends Wither_Homing_Missile_Entity> type, double x, double y, double z, Vec3 vec3, Level level) {
        super(type, x, y, z, vec3, level);
    }

    public ExtendedWitherHomingMissileEntity(LivingEntity owner, Vec3 vec3, Level level, float damage, LivingEntity target) {
        super(owner, vec3, level, damage, target);
    }

    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(EXTENDED_DAMAGE, (Object)Float.valueOf(3.0f));
        this.f_19804_.m_135372_(USE_MAGIC_DAMAGE, (Object)true);
    }

    public float getExtendedHomingMissilesDamage() {
        return ((Float)this.f_19804_.m_135370_(EXTENDED_DAMAGE)).floatValue();
    }

    public void setExtendedHomingMissilesDamage(float damage) {
        this.f_19804_.m_135381_(EXTENDED_DAMAGE, (Object)Float.valueOf(damage));
    }

    public boolean shouldDealMagicDamage() {
        return (Boolean)this.f_19804_.m_135370_(USE_MAGIC_DAMAGE);
    }

    public void setShouldDealMagicDamage(boolean value) {
        this.f_19804_.m_135381_(USE_MAGIC_DAMAGE, (Object)value);
    }

    protected void m_5790_(EntityHitResult result) {
        super.m_5790_(result);
        if (!this.m_9236_().f_46443_) {
            Entity target = result.m_82443_();
            float totalDamage = this.getExtendedHomingMissilesDamage();
            if (target instanceof LivingEntity) {
                LivingEntity livingTarget = (LivingEntity)target;
                if (this.shouldDealMagicDamage()) {
                    target.m_6469_(this.m_269291_().m_269425_(), totalDamage);
                } else {
                    DamageSources.applyDamage((Entity)livingTarget, (float)totalDamage, (DamageSource)((AbstractSpell)TravelopticsSpells.RAPID_LASER_SPELL.get()).getDamageSource((Entity)this, this.m_19749_()));
                }
                livingTarget.m_7292_(new MobEffectInstance(MobEffects.f_19615_, 100, 0));
            }
            this.m_9236_().m_255391_((Entity)this, this.m_20185_(), this.m_20186_(), this.m_20189_(), 1.0f, false, Level.ExplosionInteraction.NONE);
            this.m_146870_();
        }
    }

    public void m_7380_(CompoundTag tag) {
        super.m_7380_(tag);
        tag.m_128350_("ExtendedHomingMissilesDamage", this.getExtendedHomingMissilesDamage());
        tag.m_128379_("ShouldDealMagicDamage", this.shouldDealMagicDamage());
    }

    public void m_7378_(CompoundTag tag) {
        super.m_7378_(tag);
        if (tag.m_128441_("ExtendedHomingMissilesDamage")) {
            this.setExtendedHomingMissilesDamage(tag.m_128457_("ExtendedHomingMissilesDamage"));
        }
        if (tag.m_128441_("ShouldDealMagicDamage")) {
            this.setShouldDealMagicDamage(tag.m_128471_("ShouldDealMagicDamage"));
        }
    }
}

