/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity
 *  io.redspace.ironsspellbooks.api.registry.SchoolRegistry
 *  io.redspace.ironsspellbooks.api.spells.SchoolType
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions
 *  io.redspace.ironsspellbooks.util.ParticleHelper
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectCategory
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 */
package com.gametechbc.traveloptics.effects;

import com.gametechbc.traveloptics.init.TravelopticsSounds;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class CrimsonDescendEffect
extends MobEffect {
    private static final double FALL_SPEED = -6.0;
    private static final double KNOCKBACK_STRENGTH = 1.2;
    private static final int DAMAGE_BASE = 0;
    private static final int RADIUS = 8;

    public CrimsonDescendEffect() {
        super(MobEffectCategory.BENEFICIAL, 4915330);
    }

    public void m_6742_(LivingEntity entity, int amplifier) {
        Level level;
        if (entity.m_20096_()) {
            entity.m_9236_().m_5594_(null, entity.m_20183_(), (SoundEvent)TravelopticsSounds.CRIMSON_DESCEND_IMPACT.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
        }
        if (!entity.m_9236_().f_46443_) {
            Vec3 velocity = entity.m_20184_();
            if (entity.m_21255_()) {
                entity.m_20256_(velocity.m_82520_(0.0, -6.0, 0.0));
            }
            if (entity.m_20096_()) {
                this.executeShockwave(entity, amplifier);
                MagicManager.spawnParticles((Level)entity.m_9236_(), (ParticleOptions)new BlastwaveParticleOptions(((SchoolType)SchoolRegistry.BLOOD.get()).getTargetingColor(), 8.0f), (double)entity.m_20185_(), (double)(entity.m_20186_() + (double)0.165f), (double)entity.m_20189_(), (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)true);
                ScreenShake_Entity.ScreenShake((Level)entity.m_9236_(), (Vec3)entity.m_20182_(), (float)8.0f, (float)0.03f, (int)10, (int)20);
                entity.m_21195_((MobEffect)this);
            }
        }
        if (!entity.m_9236_().m_5776_() && (level = entity.m_9236_()) instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)level;
            int particleCount = 5;
            for (int i = 0; i < particleCount; ++i) {
                double offsetX = (entity.m_217043_().m_188500_() - 0.5) * (double)entity.m_20205_();
                double offsetY = entity.m_217043_().m_188500_() * (double)entity.m_20206_();
                double offsetZ = (entity.m_217043_().m_188500_() - 0.5) * (double)entity.m_20205_();
                double velocityX = (entity.m_217043_().m_188500_() - 0.5) * 0.2;
                double velocityY = (entity.m_217043_().m_188500_() - 0.5) * 0.2;
                double velocityZ = (entity.m_217043_().m_188500_() - 0.5) * 0.2;
                serverLevel.m_8767_(ParticleHelper.BLOOD, entity.m_20185_() + offsetX, entity.m_20186_() + offsetY, entity.m_20189_() + offsetZ, 1, velocityX, velocityY, velocityZ, 0.0);
            }
        }
    }

    private void executeShockwave(LivingEntity entity, int amplifier) {
        Level level = entity.m_9236_();
        if (!(level instanceof ServerLevel)) {
            return;
        }
        ServerLevel level2 = (ServerLevel)level;
        BlockPos impactPos = entity.m_20183_();
        AABB area = new AABB(impactPos).m_82400_(8.0);
        double damage = 0 + amplifier;
        float healMultiplier = level2.m_46462_() ? 0.15f : 0.1f;
        float healing = 0.0f;
        for (LivingEntity target : level2.m_45976_(LivingEntity.class, area)) {
            if (target == entity) continue;
            Vec3 knockback = target.m_20182_().m_82546_(entity.m_20182_()).m_82541_().m_82490_(1.2);
            target.m_20256_(knockback);
            target.m_6469_(entity.m_269291_().m_269333_(entity), (float)damage);
            healing += entity.m_21233_() * healMultiplier;
        }
        entity.m_5634_(healing);
    }

    public boolean m_6584_(int duration, int amplifier) {
        return true;
    }
}

