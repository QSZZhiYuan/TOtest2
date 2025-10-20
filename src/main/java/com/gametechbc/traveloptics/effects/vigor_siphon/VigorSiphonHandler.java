/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  io.redspace.ironsspellbooks.entity.mobs.MagicSummon
 *  io.redspace.ironsspellbooks.util.ParticleHelper
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.event.entity.living.LivingDeathEvent
 *  net.minecraftforge.event.entity.living.LivingHurtEvent
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 */
package com.gametechbc.traveloptics.effects.vigor_siphon;

import com.gametechbc.traveloptics.effects.vigor_siphon.VigorSiphonEffect;
import com.gametechbc.traveloptics.init.TravelopticsEffects;
import com.gametechbc.traveloptics.util.TravelopticsParticleHelper;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.entity.mobs.MagicSummon;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import java.util.Comparator;
import java.util.List;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class VigorSiphonHandler {
    private static final int COOLDOWN_TICKS = 50;

    @SubscribeEvent
    public static void onDamage(LivingHurtEvent event) {
        long lastHeal;
        MagicSummon summon;
        LivingEntity owner;
        LivingEntity dl;
        LivingEntity le;
        Entity direct = event.getSource().m_7640_();
        Entity source = event.getSource().m_7639_();
        LivingEntity sourceEntity = source instanceof LivingEntity ? (le = (LivingEntity)source) : (direct instanceof LivingEntity ? (dl = (LivingEntity)direct) : null);
        long gameTime = event.getEntity().m_9236_().m_46467_();
        if (sourceEntity instanceof MagicSummon && (owner = (summon = (MagicSummon)sourceEntity).getSummoner()) != null && owner.m_6084_() && owner.m_21023_((MobEffect)TravelopticsEffects.VIGOR_SIPHON.get()) && owner.m_20280_((Entity)summon) <= VigorSiphonEffect.getConnectionRange() * VigorSiphonEffect.getConnectionRange() && gameTime - (lastHeal = owner.getPersistentData().m_128454_("vigor_siphon_summon_to_owner")) >= 50L) {
            int amplifier = owner.m_21124_((MobEffect)TravelopticsEffects.VIGOR_SIPHON.get()).m_19564_();
            float healAmount = owner.m_21233_() * (float)(amplifier + 1) / 100.0f;
            owner.getPersistentData().m_128356_("vigor_siphon_summon_to_owner", gameTime);
            owner.m_5634_(healAmount);
        }
        if (sourceEntity != null && sourceEntity.m_21023_((MobEffect)TravelopticsEffects.VIGOR_SIPHON.get())) {
            Level level = sourceEntity.m_9236_();
            if (level.f_46443_) {
                return;
            }
            double radius = VigorSiphonEffect.getConnectionRange();
            AABB box = sourceEntity.m_20191_().m_82400_(radius);
            List summons = level.m_6443_(LivingEntity.class, box, entity -> {
                MagicSummon magicSummon;
                return entity instanceof MagicSummon && (magicSummon = (MagicSummon)entity).getSummoner() != null && magicSummon.getSummoner().m_20148_().equals(sourceEntity.m_20148_()) && entity.m_6084_();
            });
            summons.stream().min(Comparator.comparingDouble(arg_0 -> ((LivingEntity)sourceEntity).m_20280_(arg_0))).ifPresent(nearest -> {
                long lastHeal = nearest.getPersistentData().m_128454_("vigor_siphon_owner_to_summon");
                if (gameTime - lastHeal >= 50L) {
                    int amplifier = sourceEntity.m_21124_((MobEffect)TravelopticsEffects.VIGOR_SIPHON.get()).m_19564_();
                    float healAmount = nearest.m_21233_() * (float)(amplifier + 1) / 100.0f;
                    nearest.getPersistentData().m_128356_("vigor_siphon_owner_to_summon", gameTime);
                    nearest.m_5634_(healAmount);
                }
            });
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (!entity.m_21023_((MobEffect)TravelopticsEffects.VIGOR_SIPHON.get())) {
            return;
        }
        Level level = entity.m_9236_();
        if (level.f_46443_) {
            return;
        }
        double radius = VigorSiphonEffect.getConnectionRange();
        AABB searchBox = entity.m_20191_().m_82400_(radius);
        List summons = level.m_6443_(LivingEntity.class, searchBox, e -> {
            MagicSummon magicSummon;
            return e instanceof MagicSummon && (magicSummon = (MagicSummon)e).getSummoner() != null && magicSummon.getSummoner().m_20148_().equals(entity.m_20148_()) && e.m_6084_();
        });
        LivingEntity sacrifice = summons.stream().min(Comparator.comparingDouble(arg_0 -> ((LivingEntity)entity).m_20280_(arg_0))).orElse(null);
        if (sacrifice != null) {
            event.setCanceled(true);
            entity.m_21153_(8.0f);
            sacrifice.m_6469_(sacrifice.m_269291_().m_269425_(), Float.MAX_VALUE);
            VigorSiphonHandler.createDeathSwapParticleEffect(level, entity, sacrifice);
            entity.m_21195_((MobEffect)TravelopticsEffects.VIGOR_SIPHON.get());
        }
    }

    private static void createDeathSwapParticleEffect(Level level, LivingEntity owner, LivingEntity sacrifice) {
        Vec3 ownerPos = owner.m_20182_().m_82520_(0.0, (double)owner.m_20206_() * 0.5, 0.0);
        Vec3 sacrificePos = sacrifice.m_20182_().m_82520_(0.0, (double)sacrifice.m_20206_() * 0.5, 0.0);
        MagicManager.spawnParticles((Level)level, (ParticleOptions)ParticleHelper.BLOOD, (double)sacrificePos.f_82479_, (double)sacrificePos.f_82480_, (double)sacrificePos.f_82481_, (int)60, (double)2.0, (double)1.5, (double)2.0, (double)0.4, (boolean)false);
        MagicManager.spawnParticles((Level)level, (ParticleOptions)ParticleHelper.BLOOD, (double)sacrificePos.f_82479_, (double)(sacrificePos.f_82480_ + 0.8), (double)sacrificePos.f_82481_, (int)40, (double)0.3, (double)0.1, (double)0.3, (double)0.4, (boolean)false);
        MagicManager.spawnParticles((Level)level, (ParticleOptions)ParticleHelper.BLOOD, (double)sacrificePos.f_82479_, (double)(sacrificePos.f_82480_ - 0.5), (double)sacrificePos.f_82481_, (int)35, (double)1.8, (double)0.1, (double)1.8, (double)0.4, (boolean)false);
        MagicManager.spawnParticles((Level)level, (ParticleOptions)TravelopticsParticleHelper.WARNING_PATH_RED_GLOWING_ENCHANT, (double)sacrificePos.f_82479_, (double)sacrificePos.f_82480_, (double)sacrificePos.f_82481_, (int)45, (double)1.5, (double)1.5, (double)1.5, (double)0.4, (boolean)false);
        MagicManager.spawnParticles((Level)level, (ParticleOptions)TravelopticsParticleHelper.WARNING_PATH_RED_GLOWING_ENCHANT, (double)ownerPos.f_82479_, (double)ownerPos.f_82480_, (double)ownerPos.f_82481_, (int)50, (double)1.2, (double)1.2, (double)1.2, (double)0.4, (boolean)false);
        MagicManager.spawnParticles((Level)level, (ParticleOptions)ParticleHelper.BLOOD, (double)ownerPos.f_82479_, (double)ownerPos.f_82480_, (double)ownerPos.f_82481_, (int)25, (double)0.8, (double)0.8, (double)0.8, (double)0.4, (boolean)false);
        MagicManager.spawnParticles((Level)level, (ParticleOptions)TravelopticsParticleHelper.WARNING_PATH_RED_GLOWING_ENCHANT, (double)ownerPos.f_82479_, (double)(ownerPos.f_82480_ - 0.5), (double)ownerPos.f_82481_, (int)30, (double)0.8, (double)0.2, (double)0.8, (double)0.4, (boolean)false);
    }
}

