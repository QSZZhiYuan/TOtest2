/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.config.CMConfig
 *  com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Harbinger_Entity
 *  com.github.L_Ender.cataclysm.entity.projectile.Wither_Howitzer_Entity
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.Level$ExplosionInteraction
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.EntityHitResult
 *  net.minecraft.world.phys.HitResult
 */
package com.gametechbc.traveloptics.entity.extended_projectiles;

import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Harbinger_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Wither_Howitzer_Entity;
import java.util.List;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class ExtendedWitherHowitzerEntity
extends Wither_Howitzer_Entity {
    private float entityHitDamage;
    private float aoeEntityDamage;

    public ExtendedWitherHowitzerEntity(EntityType<? extends Wither_Howitzer_Entity> type, Level world) {
        super(type, world);
        this.entityHitDamage = 2.0f;
        this.aoeEntityDamage = 1.0f;
    }

    public ExtendedWitherHowitzerEntity(EntityType<? extends Wither_Howitzer_Entity> type, Level world, LivingEntity thrower, float entityHitDamage, float aoeEntityDamage) {
        super(type, world, thrower);
        this.entityHitDamage = entityHitDamage;
        this.aoeEntityDamage = aoeEntityDamage;
    }

    protected void m_5790_(EntityHitResult hitResult) {
        super.m_5790_(hitResult);
        if (!this.m_9236_().f_46443_) {
            boolean successfulHit;
            Entity hitEntity = hitResult.m_82443_();
            Entity owner = this.m_19749_();
            if (owner instanceof LivingEntity) {
                LivingEntity livingOwner = (LivingEntity)owner;
                successfulHit = hitEntity.m_6469_(this.m_269291_().m_269299_((Entity)this, livingOwner), this.entityHitDamage);
                if (successfulHit) {
                    if (hitEntity.m_6084_()) {
                        this.m_19970_(livingOwner, hitEntity);
                    } else if (owner instanceof The_Harbinger_Entity) {
                        livingOwner.m_5634_(5.0f * (float)CMConfig.HarbingerHealingMultiplier);
                    } else {
                        livingOwner.m_5634_(5.0f);
                    }
                }
            } else {
                successfulHit = hitEntity.m_6469_(this.m_269291_().m_269425_(), this.entityHitDamage);
            }
            if (successfulHit) {
                this.applyAoeDamage(hitEntity.m_20185_(), hitEntity.m_20186_(), hitEntity.m_20189_(), this.aoeEntityDamage, this.getRadius());
            }
        }
    }

    protected void m_6532_(HitResult hitResult) {
        super.m_6532_(hitResult);
        if (!this.m_9236_().f_46443_) {
            this.m_9236_().m_255391_((Entity)this, this.m_20185_(), this.m_20186_(), this.m_20189_(), 2.0f, false, Level.ExplosionInteraction.NONE);
            this.applyAoeDamage(this.m_20185_(), this.m_20186_(), this.m_20189_(), this.aoeEntityDamage, this.getRadius());
            this.m_146870_();
        }
    }

    private void applyAoeDamage(double x, double y, double z, float damage, float radius) {
        List nearbyEntities = this.m_9236_().m_45976_(LivingEntity.class, new AABB(x - (double)radius, y - (double)radius, z - (double)radius, x + (double)radius, y + (double)radius, z + (double)radius));
        for (LivingEntity entity : nearbyEntities) {
            if (!(this.m_20280_((Entity)entity) <= (double)(radius * radius))) continue;
            entity.m_6469_(this.m_269291_().m_269425_(), damage);
        }
    }

    public void setEntityHitDamage(float entityHitDamage) {
        this.entityHitDamage = entityHitDamage;
    }

    public void setAoeEntityDamage(float aoeEntityDamage) {
        this.aoeEntityDamage = aoeEntityDamage;
    }

    public float getEntityHitDamage() {
        return this.entityHitDamage;
    }

    public float getAoeEntityDamage() {
        return this.aoeEntityDamage;
    }
}

