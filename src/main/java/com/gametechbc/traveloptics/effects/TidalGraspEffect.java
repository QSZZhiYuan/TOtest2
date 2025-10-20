/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity
 *  io.redspace.ironsspellbooks.api.spells.AbstractSpell
 *  io.redspace.ironsspellbooks.damage.DamageSources
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectCategory
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 */
package com.gametechbc.traveloptics.effects;

import com.gametechbc.traveloptics.init.TravelopticsEffects;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import com.gametechbc.traveloptics.init.TravelopticsSpells;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.damage.DamageSources;
import java.util.List;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class TidalGraspEffect
extends MobEffect {
    public TidalGraspEffect() {
        super(MobEffectCategory.BENEFICIAL, 9636843);
    }

    public void m_6742_(LivingEntity entity, int amplifier) {
        int duration;
        MobEffectInstance effectInstance = entity.m_21124_((MobEffect)this);
        Level level = entity.m_9236_();
        if (effectInstance != null && (duration = effectInstance.m_19557_()) == 2) {
            level.m_6263_(null, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), (SoundEvent)TravelopticsSounds.TIDAL_GRASP_SMACK.get(), SoundSource.NEUTRAL, 2.0f, 1.0f);
            ScreenShake_Entity.ScreenShake((Level)level, (Vec3)entity.m_20182_(), (float)15.0f, (float)0.02f, (int)10, (int)20);
            AABB effectArea = entity.m_20191_().m_82400_(6.0);
            List nearbyEntities = entity.m_9236_().m_45976_(LivingEntity.class, effectArea);
            for (LivingEntity nearbyEntity : nearbyEntities) {
                Level level2;
                if (!nearbyEntity.m_21023_((MobEffect)TravelopticsEffects.TIDAL_GRASP_HELPER.get())) continue;
                nearbyEntity.m_7292_(new MobEffectInstance((MobEffect)MobEffects.f_19610_, 60, 0, false, true));
                nearbyEntity.m_7292_(new MobEffectInstance((MobEffect)TravelopticsEffects.WET.get(), 60, 2, false, true));
                DamageSources.applyDamage((Entity)nearbyEntity, (float)amplifier, (DamageSource)((AbstractSpell)TravelopticsSpells.TIDAL_GRASP_SPELL.get()).getDamageSource((Entity)entity, (Entity)entity));
                if (entity.m_9236_().m_5776_() || !((level2 = entity.m_9236_()) instanceof ServerLevel)) continue;
                ServerLevel serverLevel = (ServerLevel)level2;
                serverLevel.m_8767_((ParticleOptions)((SimpleParticleType)ParticleTypes.f_123795_), nearbyEntity.m_20185_(), nearbyEntity.m_20186_() + (double)0.7f, nearbyEntity.m_20189_(), 1, 0.0, 0.0, 0.0, 0.0);
            }
        }
    }

    public boolean m_6584_(int duration, int amplifier) {
        return true;
    }
}

