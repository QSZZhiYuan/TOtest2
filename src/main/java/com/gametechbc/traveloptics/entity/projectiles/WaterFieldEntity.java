/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.entity.spells.AoeEntity
 *  io.redspace.ironsspellbooks.util.ParticleHelper
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.projectile.Projectile
 *  net.minecraft.world.level.Level
 */
package com.gametechbc.traveloptics.entity.projectiles;

import com.gametechbc.traveloptics.entity.extended_projectiles.ExtendedWaterBoltEntity;
import com.gametechbc.traveloptics.init.TravelopticsEntities;
import io.redspace.ironsspellbooks.entity.spells.AoeEntity;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import java.util.Optional;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

public class WaterFieldEntity
extends AoeEntity {
    private int tickCounter;
    private float directBoltDamage = 50.0f;
    private float aoeBoltDamage = 30.0f;

    public WaterFieldEntity(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    public WaterFieldEntity(Level level) {
        this((EntityType<? extends Projectile>)((EntityType)TravelopticsEntities.WATER_FIELD.get()), level);
    }

    public void m_8119_() {
        super.m_8119_();
        ++this.tickCounter;
        if (!this.m_9236_().f_46443_ && this.tickCounter % 15 == 0) {
            this.summonWaterBolt();
        }
    }

    private void summonWaterBolt() {
        double radius = this.getRadius();
        double angle = this.f_19796_.m_188500_() * Math.PI * 2.0;
        double distance = this.f_19796_.m_188500_() * radius;
        double offsetX = distance * Math.cos(angle);
        double offsetZ = distance * Math.sin(angle);
        double x = this.m_20185_() + offsetX;
        double y = this.m_20186_() + 10.0;
        double z = this.m_20189_() + offsetZ;
        ExtendedWaterBoltEntity waterBolt = new ExtendedWaterBoltEntity((EntityType<? extends WaterBoltEntity>)((EntityType)TravelopticsEntities.EXTENDED_WATER_BOLT.get()), this.m_9236_());
        waterBolt.m_6034_(x, y, z);
        waterBolt.m_5602_(this.m_19749_());
        if (this.m_19749_() != null && waterBolt.m_19749_() != null && waterBolt.m_19749_().equals((Object)this.m_19749_())) {
            return;
        }
        waterBolt.setDirectDamageAmount(this.directBoltDamage);
        waterBolt.setAoeRadius(2.5f);
        waterBolt.setAoeDamageAmount(this.aoeBoltDamage);
        this.m_9236_().m_7967_((Entity)waterBolt);
    }

    public void applyEffect(LivingEntity target) {
    }

    public float getParticleCount() {
        return 1.2f * this.getRadius();
    }

    protected float particleYOffset() {
        return 0.25f;
    }

    protected float getParticleSpeedModifier() {
        return 1.4f;
    }

    public Optional<ParticleOptions> getParticle() {
        return Optional.of(ParticleHelper.ACID);
    }

    public float getDirectWaterBoltDamage() {
        return this.directBoltDamage;
    }

    public void setDirectWaterBoltDamage(float waterBoltDamage) {
        this.directBoltDamage = waterBoltDamage;
    }

    public float getAoeWaterBoltDamage() {
        return this.aoeBoltDamage;
    }

    public void setAoeWaterBoltDamage(float aoeWaterBoltDamage) {
        this.aoeBoltDamage = aoeWaterBoltDamage;
    }
}

