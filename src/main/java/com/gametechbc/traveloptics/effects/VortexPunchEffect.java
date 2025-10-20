/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity
 *  com.github.L_Ender.cataclysm.init.ModEntities
 *  com.github.L_Ender.cataclysm.init.ModSounds
 *  io.redspace.ironsspellbooks.api.spells.AbstractSpell
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  io.redspace.ironsspellbooks.damage.DamageSources
 *  io.redspace.ironsspellbooks.util.ParticleHelper
 *  net.minecraft.ChatFormatting
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.network.chat.Component
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectCategory
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 */
package com.gametechbc.traveloptics.effects;

import com.gametechbc.traveloptics.init.TravelopticsSpells;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModSounds;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class VortexPunchEffect
extends MobEffect {
    private float damage = 20.0f;

    public VortexPunchEffect() {
        super(MobEffectCategory.BENEFICIAL, 10423267);
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public void m_6742_(LivingEntity entity, int amplifier) {
        Level level = entity.m_9236_();
        MobEffectInstance effectInstance = entity.m_21124_((MobEffect)this);
        if (effectInstance != null) {
            Entity nearestVortex;
            int duration = effectInstance.m_19557_();
            int spellLevel = amplifier + 1;
            double range = 2.0 * (double)spellLevel;
            if (duration == 9) {
                nearestVortex = this.findNearestVortex(entity, level, range);
                if (nearestVortex != null) {
                    Vec3 targetPos = nearestVortex.m_20182_();
                    Vec3 offset = entity.m_20154_().m_82490_(-2.8);
                    Vec3 teleportPos = targetPos.m_82549_(offset);
                    entity.m_6021_(teleportPos.f_82479_, teleportPos.f_82480_ + 0.5, teleportPos.f_82481_);
                } else if (entity instanceof Player) {
                    Player player = (Player)entity;
                    player.m_5661_((Component)Component.m_237115_((String)"effect.traveloptics.void_vortex.warning").m_130940_(ChatFormatting.RED), true);
                }
            }
            if (duration == 2 && !level.m_5776_() && (nearestVortex = this.findNearestVortex(entity, level, 5.0)) != null) {
                for (LivingEntity nearbyEntity : level.m_45976_(LivingEntity.class, entity.m_20191_().m_82400_(3.0))) {
                    if (nearbyEntity == entity) continue;
                    DamageSources.applyDamage((Entity)nearbyEntity, (float)this.damage, (DamageSource)((AbstractSpell)TravelopticsSpells.VORTEX_PUNCH_SPELL.get()).getDamageSource((Entity)entity, (Entity)entity));
                    Vec3 currentVelocity = nearbyEntity.m_20184_();
                    Vec3 knockUpVelocity = new Vec3(currentVelocity.f_82479_, 1.0, currentVelocity.f_82481_);
                    nearbyEntity.m_20256_(knockUpVelocity);
                    nearbyEntity.f_19812_ = true;
                    MagicManager.spawnParticles((Level)level, (ParticleOptions)ParticleHelper.UNSTABLE_ENDER, (double)nearbyEntity.m_20185_(), (double)nearbyEntity.m_20186_(), (double)nearbyEntity.m_20189_(), (int)30, (double)0.0, (double)0.5, (double)0.0, (double)0.3, (boolean)false);
                    MagicManager.spawnParticles((Level)level, (ParticleOptions)ParticleTypes.f_123759_, (double)nearbyEntity.m_20185_(), (double)nearbyEntity.m_20186_(), (double)nearbyEntity.m_20189_(), (int)20, (double)0.0, (double)0.5, (double)0.0, (double)0.3, (boolean)false);
                }
            }
            if (duration == 2) {
                level.m_6263_(null, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), (SoundEvent)ModSounds.ENDER_GUARDIAN_FIST.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
                ScreenShake_Entity.ScreenShake((Level)level, (Vec3)entity.m_20182_(), (float)15.0f, (float)0.02f, (int)10, (int)20);
            }
        }
    }

    public boolean m_6584_(int duration, int amplifier) {
        return true;
    }

    private Entity findNearestVortex(LivingEntity entity, Level level, double range) {
        AABB searchArea = entity.m_20191_().m_82400_(range);
        for (Entity nearbyEntity : level.m_45933_((Entity)entity, searchArea)) {
            if (nearbyEntity.m_6095_() != ModEntities.VOID_VORTEX.get()) continue;
            return nearbyEntity;
        }
        return null;
    }
}

