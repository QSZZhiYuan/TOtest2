/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.api.registry.SchoolRegistry
 *  io.redspace.ironsspellbooks.api.spells.SchoolType
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectCategory
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.pathfinder.Node
 *  net.minecraft.world.level.pathfinder.Path
 *  net.minecraft.world.phys.Vec3
 */
package com.gametechbc.traveloptics.effects;

import com.gametechbc.traveloptics.util.TravelopticsParticleHelper;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class AstralPathRevealEffect
extends MobEffect {
    public AstralPathRevealEffect() {
        super(MobEffectCategory.NEUTRAL, 11393254);
    }

    public void m_6742_(LivingEntity entity, int amplifier) {
        Mob mob;
        Path path;
        if (!entity.m_9236_().f_46443_ && entity instanceof Mob && (path = (mob = (Mob)entity).m_21573_().m_26570_()) != null && !path.m_77392_()) {
            ServerLevel level = (ServerLevel)entity.m_9236_();
            int lastNodeIndex = path.m_77398_() - 1;
            for (int i = path.m_77399_(); i < lastNodeIndex; ++i) {
                Node node = path.m_77375_(i);
                BlockPos pos = new BlockPos(node.f_77271_, node.f_77272_, node.f_77273_);
                Vec3 particlePos = Vec3.m_82539_((Vec3i)pos).m_82520_(0.0, 0.1, 0.0);
                MagicManager.spawnParticles((Level)level, (ParticleOptions)TravelopticsParticleHelper.ABYSS_SPIKE_PARTICLE, (double)particlePos.f_82479_, (double)(particlePos.f_82480_ + (double)0.165f), (double)particlePos.f_82481_, (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.01, (boolean)true);
            }
            if (lastNodeIndex >= 0) {
                Node lastNode = path.m_77375_(lastNodeIndex);
                Vec3 endPos = new Vec3((double)lastNode.f_77271_ + 0.5, (double)((float)lastNode.f_77272_ + 0.165f), (double)lastNode.f_77273_ + 0.5);
                MagicManager.spawnParticles((Level)level, (ParticleOptions)new BlastwaveParticleOptions(((SchoolType)SchoolRegistry.ENDER.get()).getTargetingColor(), 1.5f), (double)endPos.f_82479_, (double)endPos.f_82480_, (double)endPos.f_82481_, (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)true);
            }
        }
    }

    public boolean m_6584_(int duration, int amplifier) {
        return duration % 20 == 0;
    }
}

