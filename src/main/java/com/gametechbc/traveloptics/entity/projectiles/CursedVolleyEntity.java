/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.entity.projectile.Phantom_Arrow_Entity
 *  io.redspace.ironsspellbooks.api.util.Utils
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile
 *  io.redspace.ironsspellbooks.registries.SoundRegistry
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.projectile.Projectile
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 */
package com.gametechbc.traveloptics.entity.projectiles;

import com.github.L_Ender.cataclysm.entity.projectile.Phantom_Arrow_Entity;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import java.util.Optional;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class CursedVolleyEntity
extends AbstractMagicProjectile {
    int rows;
    int arrowsPerRow;
    int delay = 5;

    public CursedVolleyEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.m_20242_(true);
        this.f_19794_ = true;
    }

    public void m_8119_() {
        super.m_8119_();
        if (!this.m_9236_().f_46443_) {
            if (this.f_19797_ % this.delay == 0) {
                int arrows = this.arrowsPerRow;
                float speed = 0.85f;
                Vec3 motion = Vec3.m_82498_((float)(this.m_146909_() - (float)this.f_19797_ / 5.0f * 7.0f), (float)this.m_146908_()).m_82541_().m_82490_((double)speed);
                Vec3 orth = new Vec3((double)(-Mth.m_14089_((float)(-this.m_146908_() * ((float)Math.PI / 180) - (float)Math.PI))), 0.0, (double)Mth.m_14031_((float)(-this.m_146908_() * ((float)Math.PI / 180) - (float)Math.PI)));
                for (int i = 0; i < arrows; ++i) {
                    float distance = ((float)i - (float)arrows * 0.5f) * 0.7f;
                    Phantom_Arrow_Entity phantomArrow = new Phantom_Arrow_Entity(this.m_9236_(), (LivingEntity)this.m_19749_());
                    Vec3 spawn = this.m_20182_().m_82549_(orth.m_82490_((double)distance));
                    phantomArrow.m_146884_(spawn);
                    phantomArrow.m_6686_(motion.m_82549_(Utils.getRandomVec3((double)0.04f)).m_7096_(), motion.m_82549_(Utils.getRandomVec3((double)0.04f)).m_7098_(), motion.m_82549_(Utils.getRandomVec3((double)0.04f)).m_7094_(), 1.5f, 0.0f);
                    phantomArrow.m_5602_(this.m_19749_());
                    this.m_9236_().m_7967_((Entity)phantomArrow);
                    MagicManager.spawnParticles((Level)this.m_9236_(), (ParticleOptions)ParticleTypes.f_123815_, (double)spawn.f_82479_, (double)spawn.f_82480_, (double)spawn.f_82481_, (int)2, (double)0.1, (double)0.1, (double)0.1, (double)0.05, (boolean)false);
                }
                this.m_9236_().m_6263_(null, this.m_20182_().f_82479_, this.m_20182_().f_82480_, this.m_20182_().f_82481_, SoundEvents.f_11932_, SoundSource.NEUTRAL, 3.0f, 1.1f + Utils.random.m_188501_() * 0.3f);
                this.m_9236_().m_6263_(null, this.m_20182_().f_82479_, this.m_20182_().f_82480_, this.m_20182_().f_82481_, (SoundEvent)SoundRegistry.BOW_SHOOT.get(), SoundSource.NEUTRAL, 2.0f, (float)Utils.random.m_216332_(16, 20) * 0.1f);
            } else if (this.f_19797_ > this.rows * this.delay) {
                this.m_146870_();
            }
        }
    }

    protected void m_7380_(CompoundTag tag) {
        super.m_7380_(tag);
        tag.m_128405_("rows", this.rows);
        tag.m_128405_("arrowsPerRow", this.arrowsPerRow);
    }

    protected void m_7378_(CompoundTag tag) {
        super.m_7378_(tag);
        this.rows = tag.m_128451_("rows");
        this.arrowsPerRow = tag.m_128451_("arrowsPerRow");
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setArrowsPerRow(int arrowsPerRow) {
        this.arrowsPerRow = arrowsPerRow;
    }

    public void trailParticles() {
    }

    public void impactParticles(double x, double y, double z) {
    }

    public float getSpeed() {
        return 0.0f;
    }

    public Optional<SoundEvent> getImpactSound() {
        return Optional.empty();
    }
}

