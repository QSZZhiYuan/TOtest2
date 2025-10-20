/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.damagesource.DamageTypes
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 */
package com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.anti_cheese;

import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.NightwardenBossEntity;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import com.gametechbc.traveloptics.util.TravelopticsParticleHelper;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class NightwardenAntiCheeseVoidTeleportHandler {
    public static void handleVoidDamage(NightwardenBossEntity boss, DamageSource source) {
        BlockPos home;
        if (!boss.m_9236_().f_46443_ && NightwardenAntiCheeseVoidTeleportHandler.isVoidDamage(source) && (home = boss.getHomePos()) != null) {
            boss.m_7678_((double)home.m_123341_() + 0.5, home.m_123342_(), (double)home.m_123343_() + 0.5, boss.m_146908_(), boss.m_146909_());
            boss.m_20256_(Vec3.f_82478_);
            boss.f_19789_ = 0.0f;
            boss.m_5496_((SoundEvent)TravelopticsSounds.SPECTRAL_BLINK_SUCCESS.get(), 2.5f, 1.0f);
            MagicManager.spawnParticles((Level)boss.m_9236_(), (ParticleOptions)TravelopticsParticleHelper.LIGHT_PURPLE_GLOWING_ENCHANT, (double)boss.m_20185_(), (double)(boss.m_20186_() + 1.0), (double)boss.m_20189_(), (int)20, (double)0.1, (double)0.1, (double)0.1, (double)0.05, (boolean)true);
        }
    }

    private static boolean isVoidDamage(DamageSource source) {
        return source.m_276093_(DamageTypes.f_268724_);
    }
}

