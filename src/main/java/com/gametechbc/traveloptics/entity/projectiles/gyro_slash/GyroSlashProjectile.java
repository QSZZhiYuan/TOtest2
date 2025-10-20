/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.client.particle.LightTrailParticle$OrbData
 *  io.redspace.ironsspellbooks.api.magic.MagicData
 *  io.redspace.ironsspellbooks.api.spells.AbstractSpell
 *  io.redspace.ironsspellbooks.damage.DamageSources
 *  io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible
 *  io.redspace.ironsspellbooks.entity.spells.AbstractShieldEntity
 *  io.redspace.ironsspellbooks.entity.spells.ShieldPart
 *  io.redspace.ironsspellbooks.util.ParticleHelper
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.util.Mth
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityDimensions
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.entity.projectile.Projectile
 *  net.minecraft.world.entity.projectile.ProjectileUtil
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.Vec3
 */
package com.gametechbc.traveloptics.entity.projectiles.gyro_slash;

import com.gametechbc.traveloptics.init.TravelopticsEffects;
import com.gametechbc.traveloptics.init.TravelopticsEntities;
import com.gametechbc.traveloptics.init.TravelopticsSpells;
import com.github.L_Ender.cataclysm.client.particle.LightTrailParticle;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import io.redspace.ironsspellbooks.entity.spells.AbstractShieldEntity;
import io.redspace.ironsspellbooks.entity.spells.ShieldPart;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class GyroSlashProjectile
extends Projectile
implements AntiMagicSusceptible {
    private static final EntityDataAccessor<Float> DATA_RADIUS = SynchedEntityData.m_135353_(GyroSlashProjectile.class, (EntityDataSerializer)EntityDataSerializers.f_135029_);
    private static final double SPEED = 1.0;
    private static final int EXPIRE_TIME = 80;
    private final float maxRadius;
    public AABB oldBB;
    private int age;
    private float damage;
    private int effectAmplifier = 1;
    private int effectDuration = 40;
    public int animationTime;
    private List<Entity> victims;

    public GyroSlashProjectile(EntityType<? extends GyroSlashProjectile> entityType, Level level) {
        super(entityType, level);
        this.setRadius(0.6f);
        this.maxRadius = 3.0f;
        this.oldBB = this.m_20191_();
        this.victims = new ArrayList<Entity>();
        this.m_20242_(true);
    }

    public GyroSlashProjectile(EntityType<? extends GyroSlashProjectile> entityType, Level levelIn, LivingEntity shooter) {
        this(entityType, levelIn);
        this.m_5602_((Entity)shooter);
        this.m_146922_(shooter.m_146908_());
        this.m_146926_(shooter.m_146909_());
    }

    public GyroSlashProjectile(Level levelIn, LivingEntity shooter) {
        this((EntityType<? extends GyroSlashProjectile>)((EntityType)TravelopticsEntities.GYRO_SLASH_PROJECTILE.get()), levelIn, shooter);
    }

    public void shoot(Vec3 rotation) {
        this.m_20256_(rotation.m_82490_(1.0));
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public int getEffectAmplifier() {
        return this.effectAmplifier;
    }

    public void setEffectAmplifier(int effectAmplifier) {
        this.effectAmplifier = effectAmplifier;
    }

    public int getEffectDuration() {
        return this.effectDuration;
    }

    public void setEffectDuration(int effectDuration) {
        this.effectDuration = effectDuration;
    }

    protected void m_8097_() {
        this.m_20088_().m_135372_(DATA_RADIUS, (Object)Float.valueOf(0.5f));
    }

    public void setRadius(float newRadius) {
        if (newRadius <= this.maxRadius && !this.m_9236_().f_46443_) {
            this.m_20088_().m_135381_(DATA_RADIUS, (Object)Float.valueOf(Mth.m_14036_((float)newRadius, (float)0.0f, (float)this.maxRadius)));
        }
    }

    public float getRadius() {
        return ((Float)this.m_20088_().m_135370_(DATA_RADIUS)).floatValue();
    }

    public void m_6210_() {
        double d0 = this.m_20185_();
        double d1 = this.m_20186_();
        double d2 = this.m_20189_();
        super.m_6210_();
        this.m_6034_(d0, d1, d2);
    }

    public void m_8119_() {
        super.m_8119_();
        if (++this.age > 80) {
            this.m_146870_();
            return;
        }
        this.oldBB = this.m_20191_();
        this.setRadius(this.getRadius() + 0.12f);
        if (!this.m_9236_().f_46443_) {
            HitResult hitresult = ProjectileUtil.m_278158_((Entity)this, this::m_5603_);
            if (hitresult.m_6662_() == HitResult.Type.BLOCK) {
                this.m_8060_((BlockHitResult)hitresult);
            }
            for (Entity entity : this.m_9236_().m_45933_((Entity)this, this.m_20191_()).stream().filter(target -> this.m_5603_((Entity)target) && !this.victims.contains(target)).collect(Collectors.toSet())) {
                this.damageEntity(entity);
                if (!(entity instanceof ShieldPart) && !(entity instanceof AbstractShieldEntity)) continue;
                this.m_146870_();
                return;
            }
        }
        if (this.m_9236_().f_46443_) {
            float ran = 0.04f;
            float r = 0.7647059f + this.f_19796_.m_188501_() * ran * 1.5f;
            float g = 0.37254903f + this.f_19796_.m_188501_() * ran;
            float b = 0.011764706f + this.f_19796_.m_188501_() * ran;
            this.m_9236_().m_7106_((ParticleOptions)new LightTrailParticle.OrbData(r, g, b, 2.5f, this.m_20206_() / 2.0f, this.m_19879_()), this.m_20185_(), this.m_20186_(), this.m_20189_(), 0.0, 0.0, 0.0);
        }
        this.m_146884_(this.m_20182_().m_82549_(this.m_20184_()));
        this.spawnParticles();
    }

    public EntityDimensions m_6972_(Pose p_19721_) {
        this.m_20191_();
        return EntityDimensions.m_20395_((float)(this.getRadius() * 2.0f), (float)0.5f);
    }

    public void m_7350_(EntityDataAccessor<?> p_19729_) {
        if (DATA_RADIUS.equals(p_19729_)) {
            this.m_6210_();
        }
        super.m_7350_(p_19729_);
    }

    protected void m_8060_(BlockHitResult blockHitResult) {
        super.m_8060_(blockHitResult);
        this.m_146870_();
    }

    private void damageEntity(Entity entity) {
        if (!this.victims.contains(entity)) {
            DamageSources.applyDamage((Entity)entity, (float)this.damage, (DamageSource)((AbstractSpell)TravelopticsSpells.GYRO_SLASH_SPELL.get()).getDamageSource((Entity)this, this.m_19749_()));
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)entity;
                livingEntity.m_7292_(new MobEffectInstance((MobEffect)TravelopticsEffects.FLARE_VACUUM_EFFECT.get(), this.effectDuration, this.effectAmplifier));
            }
            this.victims.add(entity);
        }
    }

    protected boolean m_5603_(Entity entity) {
        return entity != this.m_19749_() && super.m_5603_(entity);
    }

    public void onAntiMagic(MagicData playerMagicData) {
        this.m_146870_();
    }

    protected void m_7380_(CompoundTag pCompound) {
        super.m_7380_(pCompound);
        pCompound.m_128350_("Damage", this.damage);
        pCompound.m_128405_("EffectAmplifier", this.effectAmplifier);
        pCompound.m_128405_("EffectDuration", this.effectDuration);
    }

    protected void m_7378_(CompoundTag pCompound) {
        super.m_7378_(pCompound);
        this.damage = pCompound.m_128457_("Damage");
        this.effectAmplifier = pCompound.m_128451_("EffectAmplifier");
        this.effectDuration = pCompound.m_128451_("EffectDuration");
    }

    public void spawnParticles() {
        if (this.m_9236_().f_46443_) {
            float width = (float)this.m_20191_().m_82362_();
            float step = 0.25f;
            float radians = (float)Math.PI / 180 * this.m_146908_();
            float speed = 0.1f;
            int i = 0;
            while ((float)i < width / step) {
                double x = this.m_20185_();
                double y = this.m_20186_();
                double z = this.m_20189_();
                double offset = step * ((float)i - width / step / 2.0f);
                double rotX = offset * Math.cos(radians);
                double rotZ = -offset * Math.sin(radians);
                double dx = Math.random() * (double)speed * 2.0 - (double)speed;
                double dy = Math.random() * (double)speed * 2.0 - (double)speed;
                double dz = Math.random() * (double)speed * 2.0 - (double)speed;
                this.m_9236_().m_6493_(ParticleHelper.EMBERS, false, x + rotX + dx, y + dy, z + rotZ + dz, dx, dy, dz);
                ++i;
            }
        }
    }
}

