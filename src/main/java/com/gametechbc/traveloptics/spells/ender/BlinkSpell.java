/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.api.config.DefaultConfig
 *  io.redspace.ironsspellbooks.api.magic.MagicData
 *  io.redspace.ironsspellbooks.api.registry.SchoolRegistry
 *  io.redspace.ironsspellbooks.api.spells.AbstractSpell
 *  io.redspace.ironsspellbooks.api.spells.AutoSpellConfig
 *  io.redspace.ironsspellbooks.api.spells.CastSource
 *  io.redspace.ironsspellbooks.api.spells.CastType
 *  io.redspace.ironsspellbooks.api.spells.SpellAnimations
 *  io.redspace.ironsspellbooks.api.spells.SpellRarity
 *  io.redspace.ironsspellbooks.api.util.AnimationHolder
 *  io.redspace.ironsspellbooks.api.util.Utils
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  javax.annotation.Nullable
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 */
package com.gametechbc.traveloptics.spells.ender;

import com.gametechbc.traveloptics.api.utils.MovementTracker;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.AutoSpellConfig;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellAnimations;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

@AutoSpellConfig
public class BlinkSpell
extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation("traveloptics", "blink");
    private final DefaultConfig defaultConfig = new DefaultConfig().setMinRarity(SpellRarity.UNCOMMON).setSchoolResource(SchoolRegistry.ENDER_RESOURCE).setMaxLevel(3).setCooldownSeconds(1.5).build();

    public BlinkSpell() {
        this.castTime = 3;
        this.baseSpellPower = 1;
        this.spellPowerPerLevel = 1;
        this.baseManaCost = 5;
        this.manaCostPerLevel = 2;
    }

    public CastType getCastType() {
        return CastType.LONG;
    }

    public DefaultConfig getDefaultConfig() {
        return this.defaultConfig;
    }

    public ResourceLocation getSpellResource() {
        return this.spellId;
    }

    public Optional<SoundEvent> getCastStartSound() {
        return Optional.empty();
    }

    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(SoundEvents.f_11852_);
    }

    public AnimationHolder getCastFinishAnimation() {
        return SpellAnimations.SLASH_ANIMATION;
    }

    public boolean canBeInterrupted(@Nullable Player player) {
        return false;
    }

    public int getEffectiveCastTime(int spellLevel, @Nullable LivingEntity entity) {
        return this.getCastTime(spellLevel);
    }

    public void onServerCastTick(Level level, int spellLevel, LivingEntity entity, @Nullable MagicData playerMagicData) {
        if (entity instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer)entity;
            MovementTracker.updateLastPositions(player);
        }
        super.onServerCastTick(level, spellLevel, entity, playerMagicData);
    }

    public void onCast(Level level, int spellLevel, LivingEntity caster, CastSource castSource, MagicData playerMagicData) {
        if (caster instanceof ServerPlayer) {
            double maxDistance;
            Vec3 finalDest;
            ServerPlayer player = (ServerPlayer)caster;
            Vec3 currentPos = player.m_20182_();
            Vec3 lastPos = MovementTracker.getLastPosition(player);
            Vec3 direction = new Vec3(currentPos.f_82479_ - lastPos.f_82479_, 0.0, currentPos.f_82481_ - lastPos.f_82481_).m_82541_();
            if (direction.m_82556_() < 0.001) {
                Vec3 lookDirection = player.m_20154_();
                direction = new Vec3(lookDirection.f_82479_, 0.0, lookDirection.f_82481_).m_82541_();
            }
            if ((finalDest = this.findSafeTeleportPosition(level, currentPos, direction, maxDistance = this.getDistance(spellLevel, caster), player)) != null) {
                BlinkSpell.particleCloud(level, player.m_20182_());
                player.m_6021_(finalDest.f_82479_, finalDest.f_82480_, finalDest.f_82481_);
                BlinkSpell.particleCloud(level, finalDest);
            }
            MovementTracker.cleanup(player);
        }
        super.onCast(level, spellLevel, caster, castSource, playerMagicData);
    }

    private Vec3 findSafeTeleportPosition(Level level, Vec3 startPos, Vec3 direction, double maxDistance, ServerPlayer player) {
        double stepSize = 0.1;
        int steps = (int)(maxDistance / stepSize);
        Vec3 currentCheckPos = startPos;
        Vec3 lastSafePos = startPos;
        for (int i = 1; i <= steps && this.isPositionSafe(level, currentCheckPos = startPos.m_82549_(direction.m_82490_((double)i * stepSize)), player); ++i) {
            lastSafePos = currentCheckPos;
        }
        if (lastSafePos.m_82554_(startPos) < 0.5) {
            return null;
        }
        return lastSafePos;
    }

    private boolean isPositionSafe(Level level, Vec3 pos, ServerPlayer player) {
        AABB playerBounds = player.m_20191_().m_82383_(pos.m_82546_(player.m_20182_()));
        BlockPos minPos = BlockPos.m_274561_((double)playerBounds.f_82288_, (double)playerBounds.f_82289_, (double)playerBounds.f_82290_);
        BlockPos maxPos = BlockPos.m_274561_((double)playerBounds.f_82291_, (double)playerBounds.f_82292_, (double)playerBounds.f_82293_);
        for (int x = minPos.m_123341_(); x <= maxPos.m_123341_(); ++x) {
            for (int y = minPos.m_123342_(); y <= maxPos.m_123342_(); ++y) {
                for (int z = minPos.m_123343_(); z <= maxPos.m_123343_(); ++z) {
                    BlockPos checkPos = new BlockPos(x, y, z);
                    if (level.m_8055_(checkPos).m_60795_() || level.m_8055_(checkPos).m_60812_((BlockGetter)level, checkPos).m_83281_()) continue;
                    return false;
                }
            }
        }
        return true;
    }

    public static void particleCloud(Level level, Vec3 pos) {
        double width = 0.5;
        float height = 1.0f;
        for (int i = 0; i < 30; ++i) {
            double x = pos.f_82479_ + Utils.random.m_188500_() * width * 2.0 - width;
            double y = pos.f_82480_ + (double)height + Utils.random.m_188500_() * (double)height * 1.2 * 2.0 - (double)height * 1.2;
            double z = pos.f_82481_ + Utils.random.m_188500_() * width * 2.0 - width;
            double dx = Utils.random.m_188500_() * 0.1 * (double)(Utils.random.m_188499_() ? 1 : -1);
            double dy = Utils.random.m_188500_() * 0.1 * (double)(Utils.random.m_188499_() ? 1 : -1);
            double dz = Utils.random.m_188500_() * 0.1 * (double)(Utils.random.m_188499_() ? 1 : -1);
            MagicManager.spawnParticles((Level)level, (ParticleOptions)ParticleTypes.f_123760_, (double)x, (double)y, (double)z, (int)1, (double)dx, (double)dy, (double)dz, (double)0.03, (boolean)false);
        }
    }

    private double getDistance(int spellLevel, LivingEntity caster) {
        return 3.0 + (double)this.getSpellPower(spellLevel, (Entity)caster) * 0.65;
    }

    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.m_237110_((String)"ui.traveloptics.blink_distance", (Object[])new Object[]{Utils.stringTruncation((double)this.getDistance(spellLevel, caster), (int)2)}), Component.m_237113_((String)"\u00a79T.O Magic 'n Extras"));
    }
}

