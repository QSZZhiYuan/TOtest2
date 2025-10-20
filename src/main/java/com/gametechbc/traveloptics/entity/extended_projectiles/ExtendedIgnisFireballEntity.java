/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.entity.projectile.Ignis_Fireball_Entity
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.Level$ExplosionInteraction
 *  net.minecraft.world.phys.EntityHitResult
 */
package com.gametechbc.traveloptics.entity.extended_projectiles;

import com.gametechbc.traveloptics.init.TravelopticsEntities;
import com.github.L_Ender.cataclysm.entity.projectile.Ignis_Fireball_Entity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class ExtendedIgnisFireballEntity
extends Ignis_Fireball_Entity {
    private float customDamage;

    public ExtendedIgnisFireballEntity(EntityType<? extends ExtendedIgnisFireballEntity> type, Level level) {
        super(type, level);
    }

    public ExtendedIgnisFireballEntity(Level level, LivingEntity entity, double x, double y, double z) {
        super(level, entity, x, y, z);
        this.customDamage = 1.0f;
    }

    public ExtendedIgnisFireballEntity(Level worldIn, LivingEntity entity) {
        this((EntityType<? extends ExtendedIgnisFireballEntity>)((EntityType)TravelopticsEntities.EXTENDED_IGNIS_FIREBALL.get()), worldIn);
        this.m_5602_((Entity)entity);
        this.customDamage = 1.0f;
    }

    public void setCustomDamage(float damage) {
        this.customDamage = damage;
    }

    public float getCustomDamage() {
        return this.customDamage;
    }

    protected void m_5790_(EntityHitResult result) {
        super.m_5790_(result);
        Entity shooter = this.m_19749_();
        if (!this.m_9236_().f_46443_ && this.getFired() && !(result.m_82443_() instanceof Ignis_Fireball_Entity)) {
            Entity entity = result.m_82443_();
            if (shooter instanceof LivingEntity) {
                boolean flag;
                float damage;
                LivingEntity owner = (LivingEntity)shooter;
                float f = damage = this.isSoul() ? this.customDamage + 2.0f : this.customDamage;
                if (entity instanceof LivingEntity) {
                    LivingEntity target = (LivingEntity)entity;
                    flag = entity.m_6469_(this.m_269291_().m_269299_((Entity)this, owner), damage + target.m_21233_() * 0.07f);
                } else {
                    flag = entity.m_6469_(this.m_269291_().m_269299_((Entity)this, owner), damage);
                }
                if (flag) {
                    this.m_19970_(owner, entity);
                    owner.m_5634_(5.0f);
                }
            } else {
                boolean flag = entity.m_6469_(this.m_269291_().m_269425_(), this.customDamage);
            }
            this.m_9236_().m_255391_((Entity)this, this.m_20185_(), this.m_20186_(), this.m_20189_(), 1.0f, true, Level.ExplosionInteraction.NONE);
            this.m_146870_();
        }
    }

    public void m_8119_() {
        super.m_8119_();
    }
}

