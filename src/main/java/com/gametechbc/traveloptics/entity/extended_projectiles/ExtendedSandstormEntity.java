/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.entity.effect.Sandstorm_Entity
 *  com.github.L_Ender.cataclysm.init.ModEffect
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 */
package com.gametechbc.traveloptics.entity.extended_projectiles;

import com.github.L_Ender.cataclysm.entity.effect.Sandstorm_Entity;
import com.github.L_Ender.cataclysm.init.ModEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ExtendedSandstormEntity
extends Sandstorm_Entity {
    private float customDamage = 8.0f;

    public ExtendedSandstormEntity(EntityType<?> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
    }

    public ExtendedSandstormEntity(Level worldIn, double x, double y, double z, int lifespan, float offset, LivingEntity casterIn) {
        super(worldIn, x, y, z, lifespan, offset, casterIn);
    }

    public void setCustomDamage(float damage) {
        this.customDamage = damage;
    }

    public float getCustomDamage() {
        return this.customDamage;
    }

    public void m_8119_() {
        LivingEntity owner = this.getCaster();
        for (LivingEntity entity : this.m_9236_().m_45976_(LivingEntity.class, this.m_20191_().m_82377_(0.2, 0.0, 0.2))) {
            boolean flag;
            if (entity instanceof Player && ((Player)entity).m_150110_().f_35934_ || entity == owner || !entity.m_6084_() || entity.m_20147_() || this.f_19797_ % 3 != 0) continue;
            if (owner == null) {
                flag = entity.m_6469_(this.m_269291_().m_269425_(), this.customDamage);
            } else {
                if (owner.m_7307_((Entity)entity)) continue;
                flag = entity.m_6469_(this.m_269291_().m_269104_((Entity)this, (Entity)owner), this.customDamage);
            }
            if (!flag) continue;
            MobEffectInstance effectInstance = new MobEffectInstance((MobEffect)ModEffect.EFFECTCURSE_OF_DESERT.get(), 200, 0);
            entity.m_7292_(effectInstance);
        }
        super.m_8119_();
    }

    public void customUpdateMotion() {
        LivingEntity owner = this.getCaster();
        if (owner != null) {
            Vec3 center = owner.m_20182_().m_82520_(0.0, 0.0, 0.0);
            float radius = 6.0f;
            float speed = (float)this.f_19797_ * 0.05f;
            float offset = this.getOffset();
            Vec3 orbit = new Vec3(center.f_82479_ + Math.cos(speed + offset) * (double)radius, center.f_82480_, center.f_82481_ + Math.sin(speed + offset) * (double)radius);
            this.m_20219_(orbit);
        }
    }
}

