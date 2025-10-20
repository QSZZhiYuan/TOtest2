/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.entity.projectile.Flare_Bomb_Entity
 *  com.github.L_Ender.cataclysm.init.ModParticle
 *  io.redspace.ironsspellbooks.api.registry.SchoolRegistry
 *  io.redspace.ironsspellbooks.api.spells.SchoolType
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  io.redspace.ironsspellbooks.particle.ShockwaveParticleOptions
 *  io.redspace.ironsspellbooks.util.ParticleHelper
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectCategory
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.TamableAnimal
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 *  top.theillusivec4.curios.api.CuriosApi
 */
package com.gametechbc.traveloptics.effects;

import com.gametechbc.traveloptics.api.utils.TOCurioUtils;
import com.gametechbc.traveloptics.entity.extended_projectiles.ExtendedFlareBombEntity;
import com.gametechbc.traveloptics.entity.extended_projectiles.ExtendedIgnisFireballEntity;
import com.gametechbc.traveloptics.entity.mobs.EnragedDeadKingBoss;
import com.gametechbc.traveloptics.init.TravelopticsEntities;
import com.gametechbc.traveloptics.init.TravelopticsItems;
import com.github.L_Ender.cataclysm.entity.projectile.Flare_Bomb_Entity;
import com.github.L_Ender.cataclysm.init.ModParticle;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.particle.ShockwaveParticleOptions;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.CuriosApi;

public class MeteorStormEffect
extends MobEffect {
    private int ticksSinceLastSummon = 0;
    private double outerRadius = 25.0;

    public MeteorStormEffect() {
        super(MobEffectCategory.BENEFICIAL, 0);
    }

    public void setOuterRadius(double radius) {
        this.outerRadius = radius;
    }

    public void m_6742_(LivingEntity entity, int amplifier) {
        Level level;
        if (!entity.m_9236_().f_46443_ && (level = entity.m_9236_()) instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)level;
            ++this.ticksSinceLastSummon;
            if (this.ticksSinceLastSummon >= 15) {
                if (TOCurioUtils.getWearingCurio(entity, (Item)TravelopticsItems.FIRESTORM_RING.get()) || entity instanceof EnragedDeadKingBoss) {
                    this.summonFlareBombs(serverLevel, entity);
                } else {
                    this.summonProjectiles(serverLevel, entity);
                }
                this.ticksSinceLastSummon = 0;
            }
        }
        super.m_6742_(entity, amplifier);
    }

    private void summonProjectiles(ServerLevel serverLevel, LivingEntity target) {
        RandomSource random = target.m_217043_();
        double innerRadius = 8.0;
        List nearbyEntities = serverLevel.m_6249_((Entity)target, target.m_20191_().m_82400_(this.outerRadius), entity -> {
            LivingEntity targetEntity;
            return entity instanceof LivingEntity && !this.isTeammate(target, targetEntity = (LivingEntity)entity) && !this.isTamedCreature(target, targetEntity);
        });
        Collections.shuffle(nearbyEntities, new Random(random.m_188505_()));
        int numEntitiesToTarget = Math.min(nearbyEntities.size(), 2);
        MobEffectInstance effect = target.m_21124_((MobEffect)this);
        int amplifier = effect != null ? effect.m_19564_() : 0;
        for (int i = 0; i < 3; ++i) {
            Vec3 spawnPosition;
            if (i < numEntitiesToTarget) {
                Entity targetEntity = (Entity)nearbyEntities.get(i);
                Vec3 motion = targetEntity.m_20184_();
                double predictionTime = 1.0;
                Vec3 predictedPosition = targetEntity.m_20182_().m_82549_(motion.m_82490_(predictionTime));
                spawnPosition = new Vec3(predictedPosition.f_82479_, predictedPosition.f_82480_ + 30.0, predictedPosition.f_82481_);
                MagicManager.spawnParticles((Level)serverLevel, (ParticleOptions)new ShockwaveParticleOptions(((SchoolType)SchoolRegistry.FIRE.get()).getTargetingColor(), -1.5f, true), (double)predictedPosition.f_82479_, (double)predictedPosition.f_82480_, (double)predictedPosition.f_82481_, (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)true);
            } else {
                double angle = random.m_188500_() * Math.PI * 2.0;
                double distance = innerRadius + random.m_188500_() * (this.outerRadius - innerRadius);
                double xOffset = Math.cos(angle) * distance;
                double zOffset = Math.sin(angle) * distance;
                double yOffset = 30.0;
                spawnPosition = new Vec3(target.m_20185_() + xOffset, target.m_20186_() + yOffset, target.m_20189_() + zOffset);
            }
            int adjustedAmplifier = amplifier;
            if (CuriosApi.getCuriosInventory((LivingEntity)target).map(curios -> !curios.findCurios(item -> item != null && item.m_150930_((Item)TravelopticsItems.AZURE_IGNITION_BRACELET.get())).isEmpty()).orElse(false).booleanValue()) {
                ++adjustedAmplifier;
            }
            ExtendedIgnisFireballEntity fireball = new ExtendedIgnisFireballEntity((EntityType<? extends ExtendedIgnisFireballEntity>)((EntityType)TravelopticsEntities.EXTENDED_IGNIS_FIREBALL.get()), (Level)serverLevel);
            fireball.m_146884_(spawnPosition);
            fireball.m_6686_(0.0, -1.0, 0.0, 2.5f, 0.0f);
            fireball.setSoul(target.m_21223_() < target.m_21233_() * 0.5f || CuriosApi.getCuriosInventory((LivingEntity)target).map(curios -> !curios.findCurios(item -> item != null && item.m_150930_((Item)TravelopticsItems.AZURE_IGNITION_BRACELET.get())).isEmpty()).orElse(false) != false);
            fireball.setCustomDamage(adjustedAmplifier);
            serverLevel.m_7967_((Entity)fireball);
            MagicManager.spawnParticles((Level)serverLevel, (ParticleOptions)((ParticleOptions)ModParticle.TRAP_FLAME.get()), (double)spawnPosition.f_82479_, (double)spawnPosition.f_82480_, (double)spawnPosition.f_82481_, (int)1, (double)0.0, (double)-1.0, (double)0.0, (double)0.0, (boolean)true);
            MagicManager.spawnParticles((Level)serverLevel, (ParticleOptions)ParticleHelper.SUNBEAM, (double)spawnPosition.f_82479_, (double)spawnPosition.f_82480_, (double)spawnPosition.f_82481_, (int)1, (double)1.0, (double)1.0, (double)1.0, (double)1.0, (boolean)false);
            MagicManager.spawnParticles((Level)serverLevel, (ParticleOptions)ParticleHelper.SUNBEAM, (double)spawnPosition.f_82479_, (double)spawnPosition.f_82480_, (double)spawnPosition.f_82481_, (int)1, (double)1.0, (double)1.0, (double)1.0, (double)1.0, (boolean)true);
        }
    }

    private void summonFlareBombs(ServerLevel serverLevel, LivingEntity entity) {
        MobEffectInstance effect = entity.m_21124_((MobEffect)this);
        int amplifier = effect != null ? effect.m_19564_() : 0;
        RandomSource random = entity.m_217043_();
        double innerRadius = 4.0;
        for (int i = 0; i < 3; ++i) {
            double angle = random.m_188500_() * Math.PI * 2.0;
            double distance = innerRadius + random.m_188500_() * (this.outerRadius - innerRadius);
            double xOffset = Math.cos(angle) * distance;
            double zOffset = Math.sin(angle) * distance;
            double yOffset = 30.0;
            Vec3 spawnPosition = new Vec3(entity.m_20185_() + xOffset, entity.m_20186_() + yOffset, entity.m_20189_() + zOffset);
            int adjustedAmplifier = amplifier;
            if (CuriosApi.getCuriosInventory((LivingEntity)entity).map(curios -> !curios.findCurios(item -> item != null && item.m_150930_((Item)TravelopticsItems.AZURE_IGNITION_BRACELET.get())).isEmpty()).orElse(false).booleanValue()) {
                ++adjustedAmplifier;
            }
            ExtendedFlareBombEntity flareBomb = new ExtendedFlareBombEntity((EntityType<? extends Flare_Bomb_Entity>)((EntityType)TravelopticsEntities.EXTENDED_FLARE_BOMB.get()), (Level)serverLevel);
            flareBomb.m_146884_(spawnPosition);
            flareBomb.m_20334_(0.0, -1.0, 0.0);
            flareBomb.setFlameJetDamage(adjustedAmplifier);
            flareBomb.m_5602_((Entity)entity);
            serverLevel.m_7967_((Entity)flareBomb);
            MagicManager.spawnParticles((Level)serverLevel, (ParticleOptions)((ParticleOptions)ModParticle.TRAP_FLAME.get()), (double)spawnPosition.f_82479_, (double)spawnPosition.f_82480_, (double)spawnPosition.f_82481_, (int)1, (double)0.0, (double)-1.0, (double)0.0, (double)0.0, (boolean)true);
            MagicManager.spawnParticles((Level)serverLevel, (ParticleOptions)ParticleHelper.SUNBEAM, (double)spawnPosition.f_82479_, (double)spawnPosition.f_82480_, (double)spawnPosition.f_82481_, (int)1, (double)1.0, (double)1.0, (double)1.0, (double)1.0, (boolean)false);
            MagicManager.spawnParticles((Level)serverLevel, (ParticleOptions)ParticleHelper.SUNBEAM, (double)spawnPosition.f_82479_, (double)spawnPosition.f_82480_, (double)spawnPosition.f_82481_, (int)1, (double)1.0, (double)1.0, (double)1.0, (double)1.0, (boolean)true);
        }
    }

    private boolean isTeammate(LivingEntity caster, LivingEntity target) {
        return caster.m_7307_((Entity)target);
    }

    private boolean isTamedCreature(LivingEntity caster, LivingEntity target) {
        if (target instanceof TamableAnimal) {
            TamableAnimal tamedAnimal = (TamableAnimal)target;
            return tamedAnimal.m_21824_() && tamedAnimal.m_269323_() == caster;
        }
        return false;
    }

    public boolean m_6584_(int duration, int amplifier) {
        return true;
    }
}

