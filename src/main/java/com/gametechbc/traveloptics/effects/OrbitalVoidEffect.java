/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan.Abyss_Orb_Entity
 *  com.github.L_Ender.cataclysm.init.ModEntities
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectCategory
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.projectile.Projectile
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 */
package com.gametechbc.traveloptics.effects;

import com.gametechbc.traveloptics.init.TravelopticsSounds;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan.Abyss_Orb_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import java.util.Random;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class OrbitalVoidEffect
extends MobEffect {
    private static final Random RANDOM = new Random();

    public OrbitalVoidEffect() {
        super(MobEffectCategory.BENEFICIAL, 0);
    }

    public void m_6742_(LivingEntity entity, int amplifier) {
        super.m_6742_(entity, amplifier);
        Level level = entity.m_9236_();
        if (level instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)level;
            if (entity.f_19797_ % 60 == 0) {
                int effectLevel = amplifier + 1;
                double totalOrbPower = effectLevel;
                int orbCount = (int)Math.ceil(totalOrbPower * 4.0);
                double angleBetween = Math.PI * 2 / (double)orbCount;
                for (int i = 0; i < orbCount; ++i) {
                    Projectile projectile;
                    double angle = angleBetween * (double)i;
                    double offsetX = Math.sin(angle) * 6.0;
                    double offsetZ = Math.cos(angle) * 6.0;
                    double motionScale = 3.0;
                    Vec3 motion = new Vec3(offsetX, 0.0, offsetZ).m_82541_().m_82490_(motionScale);
                    EntityType entityType = (EntityType)ModEntities.ABYSS_ORB.get();
                    if (entityType == null || (projectile = (Projectile)entityType.m_20615_((Level)serverLevel)) == null) continue;
                    projectile.m_7678_(entity.m_20185_(), entity.m_20186_() + 1.5, entity.m_20189_(), 0.0f, 0.0f);
                    projectile.m_20334_(motion.m_7096_(), motion.m_7098_(), motion.m_7094_());
                    if (projectile instanceof Abyss_Orb_Entity) {
                        Abyss_Orb_Entity abyssOrb = (Abyss_Orb_Entity)projectile;
                        abyssOrb.setDamage((float)(amplifier * 5));
                        abyssOrb.m_5602_((Entity)entity);
                    }
                    serverLevel.m_7967_((Entity)projectile);
                }
                if (entity instanceof Player) {
                    Player player = (Player)entity;
                    this.playSound(player, (SoundEvent)TravelopticsSounds.ORBITAL_VOID_PULSE.get());
                }
            }
        }
    }

    private void playSound(Player player, SoundEvent soundEvent) {
        if (soundEvent != null && !player.m_9236_().m_5776_()) {
            player.m_6330_(soundEvent, SoundSource.PLAYERS, 1.0f, 1.0f);
        }
    }

    public boolean m_6584_(int duration, int amplifier) {
        return true;
    }
}

