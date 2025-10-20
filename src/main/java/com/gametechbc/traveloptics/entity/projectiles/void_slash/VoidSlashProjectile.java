/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.client.particle.LightTrailParticle$OrbData
 *  com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity
 *  io.redspace.ironsspellbooks.api.magic.MagicData
 *  io.redspace.ironsspellbooks.api.registry.SchoolRegistry
 *  io.redspace.ironsspellbooks.api.spells.AbstractSpell
 *  io.redspace.ironsspellbooks.api.spells.SchoolType
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  io.redspace.ironsspellbooks.damage.SpellDamageSource
 *  io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible
 *  io.redspace.ironsspellbooks.entity.spells.AbstractShieldEntity
 *  io.redspace.ironsspellbooks.entity.spells.ShieldPart
 *  io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions
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
package com.gametechbc.traveloptics.entity.projectiles.void_slash;

import com.gametechbc.traveloptics.api.particle.AdvancedSphereParticleManager;
import com.gametechbc.traveloptics.api.particle.ParticleDirection;
import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.NightwardenBossEntity;
import com.gametechbc.traveloptics.init.TravelopticsEffects;
import com.gametechbc.traveloptics.init.TravelopticsEntities;
import com.gametechbc.traveloptics.init.TravelopticsParticles;
import com.gametechbc.traveloptics.init.TravelopticsSpells;
import com.gametechbc.traveloptics.util.TravelopticsParticleHelper;
import com.github.L_Ender.cataclysm.client.particle.LightTrailParticle;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.SpellDamageSource;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import io.redspace.ironsspellbooks.entity.spells.AbstractShieldEntity;
import io.redspace.ironsspellbooks.entity.spells.ShieldPart;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
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

public class VoidSlashProjectile
extends Projectile
implements AntiMagicSusceptible {
    private static final EntityDataAccessor<Float> DATA_RADIUS = SynchedEntityData.m_135353_(VoidSlashProjectile.class, (EntityDataSerializer)EntityDataSerializers.f_135029_);
    private static final EntityDataAccessor<Boolean> DATA_CROSS = SynchedEntityData.m_135353_(VoidSlashProjectile.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> MAGIC_DAMAGE_MODE = SynchedEntityData.m_135353_(VoidSlashProjectile.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private float lifestealPercent = 1.0f;
    private static final double SPEED = 1.0;
    private static final int EXPIRE_TIME = 80;
    private final float maxRadius;
    public AABB oldBB;
    private int age;
    private float damage;
    public int animationTime;
    private List<Entity> victims;

    public VoidSlashProjectile(EntityType<? extends VoidSlashProjectile> entityType, Level level) {
        super(entityType, level);
        this.setRadius(0.6f);
        this.maxRadius = 3.0f;
        this.oldBB = this.m_20191_();
        this.victims = new ArrayList<Entity>();
        this.m_20242_(true);
    }

    public VoidSlashProjectile(EntityType<? extends VoidSlashProjectile> entityType, Level levelIn, LivingEntity shooter) {
        this(entityType, levelIn);
        this.m_5602_((Entity)shooter);
        this.m_146922_(shooter.m_146908_());
        this.m_146926_(shooter.m_146909_());
    }

    public VoidSlashProjectile(Level levelIn, LivingEntity shooter) {
        this((EntityType<? extends VoidSlashProjectile>)((EntityType)TravelopticsEntities.VOID_SLASH_PROJECTILE.get()), levelIn, shooter);
    }

    protected void m_8097_() {
        this.m_20088_().m_135372_(DATA_RADIUS, (Object)Float.valueOf(0.5f));
        this.m_20088_().m_135372_(DATA_CROSS, (Object)false);
        this.f_19804_.m_135372_(MAGIC_DAMAGE_MODE, (Object)false);
    }

    protected void m_7380_(CompoundTag tag) {
        super.m_7380_(tag);
        tag.m_128350_("Damage", this.damage);
        tag.m_128379_("Cross", ((Boolean)this.m_20088_().m_135370_(DATA_CROSS)).booleanValue());
        tag.m_128379_("MagicDamageMode", this.isMagicDamageMode());
        tag.m_128350_("LifestealPercent", this.lifestealPercent);
    }

    protected void m_7378_(CompoundTag tag) {
        super.m_7378_(tag);
        this.damage = tag.m_128457_("Damage");
        if (tag.m_128441_("Cross")) {
            this.m_20088_().m_135381_(DATA_CROSS, (Object)tag.m_128471_("Cross"));
        }
        if (tag.m_128441_("MagicDamageMode")) {
            this.setMagicDamageMode(tag.m_128471_("MagicDamageMode"));
        }
        if (tag.m_128441_("LifestealPercent")) {
            this.lifestealPercent = tag.m_128457_("LifestealPercent");
        }
    }

    public void shoot(Vec3 rotation) {
        this.m_20256_(rotation.m_82490_(1.0));
    }

    public boolean isCross() {
        return (Boolean)this.m_20088_().m_135370_(DATA_CROSS);
    }

    public void setCross(boolean value) {
        this.m_20088_().m_135381_(DATA_CROSS, (Object)value);
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public void setRadius(float newRadius) {
        if (newRadius <= this.maxRadius && !this.m_9236_().f_46443_) {
            this.m_20088_().m_135381_(DATA_RADIUS, (Object)Float.valueOf(Mth.m_14036_((float)newRadius, (float)0.0f, (float)this.maxRadius)));
        }
    }

    public float getRadius() {
        return ((Float)this.m_20088_().m_135370_(DATA_RADIUS)).floatValue();
    }

    public boolean isMagicDamageMode() {
        return (Boolean)this.f_19804_.m_135370_(MAGIC_DAMAGE_MODE);
    }

    public void setMagicDamageMode(boolean value) {
        this.f_19804_.m_135381_(MAGIC_DAMAGE_MODE, (Object)value);
    }

    public void setLifestealPercent(float percent) {
        this.lifestealPercent = percent;
    }

    public float getLifestealPercent() {
        return this.lifestealPercent;
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
            float variation = 0.01f;
            float r = 0.6313726f + (this.f_19796_.m_188501_() - 0.5f) * variation;
            float g = 0.3254902f + (this.f_19796_.m_188501_() - 0.5f) * variation;
            float b = 0.99607843f + (this.f_19796_.m_188501_() - 0.5f) * variation;
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
        if (!this.m_9236_().f_46443_ && this.isCross()) {
            this.doCrossBehavior((Entity)this);
        } else {
            this.m_146870_();
        }
    }

    private void damageEntity(Entity entity) {
        int effectDuration = 60;
        float splashDamage = this.damage * 0.5f;
        if (!this.victims.contains(entity)) {
            Entity entity2;
            SpellDamageSource source;
            if (this.isMagicDamageMode()) {
                Entity entity3 = this.m_19749_();
                if (entity3 instanceof LivingEntity) {
                    LivingEntity owner = (LivingEntity)entity3;
                    source = this.m_269291_().m_269104_((Entity)this, (Entity)owner);
                } else {
                    source = this.m_269291_().m_269425_();
                }
            } else {
                source = ((AbstractSpell)TravelopticsSpells.GYRO_SLASH_SPELL.get()).getDamageSource((Entity)this, this.m_19749_());
            }
            boolean hit = entity.m_6469_((DamageSource)source, this.damage);
            this.victims.add(entity);
            if (hit && (entity2 = this.m_19749_()) instanceof NightwardenBossEntity) {
                NightwardenBossEntity nightwarden = (NightwardenBossEntity)entity2;
                float lifestealAmount = this.damage * this.lifestealPercent;
                if (lifestealAmount > 0.0f) {
                    nightwarden.m_5634_(lifestealAmount);
                }
            }
            if (this.isCross()) {
                if (entity instanceof LivingEntity) {
                    LivingEntity victim = (LivingEntity)entity;
                    victim.m_7292_(new MobEffectInstance((MobEffect)TravelopticsEffects.VOID_COLLAPSE_EFFECT.get(), effectDuration, (int)splashDamage));
                }
                this.doCrossBehavior(entity);
            }
        }
    }

    private void doCrossBehavior(Entity triggerSource) {
        int effectDuration = 60;
        float splashDamage = this.damage * 0.5f;
        AABB explosionArea = triggerSource.m_20191_().m_82400_(4.0);
        for (LivingEntity nearby : this.m_9236_().m_45976_(LivingEntity.class, explosionArea)) {
            Entity entity;
            SpellDamageSource source;
            if (nearby == this.m_19749_() || this.victims.contains(nearby)) continue;
            if (this.isMagicDamageMode()) {
                Entity entity2 = this.m_19749_();
                if (entity2 instanceof LivingEntity) {
                    LivingEntity owner = (LivingEntity)entity2;
                    source = this.m_269291_().m_269104_((Entity)this, (Entity)owner);
                } else {
                    source = this.m_269291_().m_269425_();
                }
            } else {
                source = ((AbstractSpell)TravelopticsSpells.GYRO_SLASH_SPELL.get()).getDamageSource((Entity)this, this.m_19749_());
            }
            boolean hit = nearby.m_6469_((DamageSource)source, splashDamage);
            if (hit && (entity = this.m_19749_()) instanceof NightwardenBossEntity) {
                NightwardenBossEntity nightwarden = (NightwardenBossEntity)entity;
                float lifestealAmount = splashDamage * this.lifestealPercent;
                if (lifestealAmount > 0.0f) {
                    nightwarden.m_5634_(lifestealAmount);
                }
            }
            nearby.m_7292_(new MobEffectInstance((MobEffect)TravelopticsEffects.VOID_COLLAPSE_EFFECT.get(), effectDuration, (int)splashDamage));
            this.victims.add((Entity)nearby);
        }
        ScreenShake_Entity.ScreenShake((Level)this.m_9236_(), (Vec3)this.m_20182_(), (float)6.0f, (float)0.04f, (int)8, (int)10);
        MagicManager.spawnParticles((Level)this.m_9236_(), (ParticleOptions)new BlastwaveParticleOptions(((SchoolType)SchoolRegistry.ENDER.get()).getTargetingColor(), 4.0f), (double)this.m_20185_(), (double)this.m_20186_(), (double)this.m_20189_(), (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)false);
        AdvancedSphereParticleManager.spawnParticles(this.m_9236_(), this.m_20185_(), this.m_20186_(), this.m_20189_(), 70, (ParticleOptions)TravelopticsParticles.PURPLE_STAR_OUTWARD_PARTICLE.get(), ParticleDirection.OUTWARD, 6.0, false);
        this.m_146870_();
    }

    protected boolean m_5603_(Entity entity) {
        return entity != this.m_19749_() && super.m_5603_(entity);
    }

    public void onAntiMagic(MagicData playerMagicData) {
        this.m_146870_();
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
                this.m_9236_().m_7106_(TravelopticsParticleHelper.LIGHT_PURPLE_GLOWING_ENCHANT, x + rotX + dx, y + dy, z + rotZ + dz, dx, dy, dz);
                ++i;
            }
        }
    }
}

