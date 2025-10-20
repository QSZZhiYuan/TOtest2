/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.client.particle.RoarParticle$RoarData
 *  io.redspace.ironsspellbooks.api.config.DefaultConfig
 *  io.redspace.ironsspellbooks.api.magic.MagicData
 *  io.redspace.ironsspellbooks.api.spells.AutoSpellConfig
 *  io.redspace.ironsspellbooks.api.spells.CastSource
 *  io.redspace.ironsspellbooks.api.spells.CastType
 *  io.redspace.ironsspellbooks.api.spells.SpellRarity
 *  io.redspace.ironsspellbooks.api.util.AnimationHolder
 *  io.redspace.ironsspellbooks.api.util.Utils
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  io.redspace.ironsspellbooks.damage.SpellDamageSource
 *  javax.annotation.Nullable
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.VoxelShape
 */
package com.gametechbc.traveloptics.spells.aqua;

import com.gametechbc.traveloptics.api.init.TravelopticsSchools;
import com.gametechbc.traveloptics.api.spells.AbstractUniqueSpell;
import com.gametechbc.traveloptics.api.utils.TOGeneralUtils;
import com.gametechbc.traveloptics.entity.extended_projectiles.ExtendedStormSerpentEntity;
import com.gametechbc.traveloptics.entity.misc.TOScreenShakeEntity;
import com.gametechbc.traveloptics.init.TravelopticsParticles;
import com.gametechbc.traveloptics.spells.TravelopticsSpellAnimations;
import com.gametechbc.traveloptics.util.TravelopticsParticleHelper;
import com.github.L_Ender.cataclysm.client.particle.RoarParticle;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.AutoSpellConfig;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.SpellDamageSource;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

@AutoSpellConfig
public class SerpentideSpell
extends AbstractUniqueSpell {
    private final ResourceLocation spellId = new ResourceLocation("traveloptics", "serpentide");
    private final DefaultConfig defaultConfig = new DefaultConfig().setMinRarity(SpellRarity.RARE).setSchoolResource(TravelopticsSchools.AQUA_RESOURCE).setMaxLevel(4).setCooldownSeconds(35.0).build();

    public SerpentideSpell() {
        this.manaCostPerLevel = 50;
        this.baseSpellPower = 1;
        this.spellPowerPerLevel = 1;
        this.castTime = 20;
        this.baseManaCost = 45;
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

    public AnimationHolder getCastStartAnimation() {
        return TravelopticsSpellAnimations.SERPENTIDE_CAST;
    }

    public AnimationHolder getCastFinishAnimation() {
        return AnimationHolder.pass();
    }

    public int getEffectiveCastTime(int spellLevel, @Nullable LivingEntity entity) {
        return this.getCastTime(spellLevel);
    }

    public void onServerCastTick(Level world, int spellLevel, LivingEntity entity, @Nullable MagicData playerMagicData) {
        if (playerMagicData != null) {
            int castTimeRemaining = playerMagicData.getCastDurationRemaining();
            if ((castTimeRemaining + 1) % 3 == 0) {
                this.spawnCastingRoarParticles(world, entity);
            }
            if ((castTimeRemaining + 1) % 5 == 0) {
                TOScreenShakeEntity.createScreenShake(world, entity.m_20182_(), 8.0f, 0.005f, 6, 0, 0, true);
            }
            this.spawnCastingBubbles(world, entity);
        }
    }

    public void onCast(Level world, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        float firstSerpentDistance = -2.0f;
        float forwardSpacing = 3.0f;
        float sideDistance = 6.0f;
        float crossAngle = 30.0f;
        int emergenceDelay = 5;
        int serpentCount = this.getSerpentCount(spellLevel);
        float damage = this.getDamage(spellLevel, entity);
        Vec3 lookDirection = entity.m_20154_();
        Vec3 horizontalLook = new Vec3(lookDirection.f_82479_, 0.0, lookDirection.f_82481_).m_82541_();
        Vec3 rightDirection = new Vec3(-horizontalLook.f_82481_, 0.0, horizontalLook.f_82479_).m_82541_();
        Vec3 leftDirection = rightDirection.m_82490_(-1.0);
        float casterYaw = entity.m_6080_() * (float)Math.PI / 180.0f;
        float correctedYaw = casterYaw + 1.5707964f;
        for (int i = 0; i < serpentCount; ++i) {
            float serpentYaw;
            Vec3 serpentPos;
            boolean isRight = i % 2 == 0;
            float forwardDistance = firstSerpentDistance + (float)(i / 2) * forwardSpacing;
            if (isRight) {
                serpentPos = entity.m_20182_().m_82549_(horizontalLook.m_82490_((double)forwardDistance)).m_82549_(rightDirection.m_82490_((double)sideDistance));
                serpentYaw = correctedYaw - (float)Math.toRadians(crossAngle);
            } else {
                serpentPos = entity.m_20182_().m_82549_(horizontalLook.m_82490_((double)forwardDistance)).m_82549_(leftDirection.m_82490_((double)sideDistance));
                serpentYaw = correctedYaw + (float)Math.toRadians(crossAngle);
            }
            Vec3 groundPos = this.findGroundLevel(world, serpentPos);
            int warmupDelay = i * emergenceDelay;
            this.spawnSerpentCastCompletionEffects(world, groundPos);
            ExtendedStormSerpentEntity serpent = new ExtendedStormSerpentEntity(world, groundPos.f_82479_, groundPos.f_82480_, groundPos.f_82481_, serpentYaw, warmupDelay, entity, damage, null, isRight);
            serpent.setWetAmplifier(this.getWetAmplifier(spellLevel));
            world.m_7967_((Entity)serpent);
        }
        this.spawnCasterCastCompletionEffects(world, entity);
        TOScreenShakeEntity.createScreenShake(world, entity.m_20182_(), 8.0f, 0.02f, 15, 0, 10, true);
        super.onCast(world, spellLevel, entity, castSource, playerMagicData);
    }

    private Vec3 findGroundLevel(Level world, Vec3 position) {
        double casterHeadY = position.f_82480_ + 1.0;
        int standOnYPos = Mth.m_14107_((double)position.f_82480_) - 2;
        BlockPos pos = new BlockPos((int)position.f_82479_, (int)casterHeadY, (int)position.f_82481_);
        boolean foundGround = false;
        double heightOffset = 0.0;
        do {
            BlockState currentBlockState;
            VoxelShape voxelShape;
            BlockPos belowPos;
            BlockState blockState;
            if (!(blockState = world.m_8055_(belowPos = pos.m_7495_())).m_60783_((BlockGetter)world, belowPos, Direction.UP)) continue;
            if (!world.m_46859_(pos) && !(voxelShape = (currentBlockState = world.m_8055_(pos)).m_60812_((BlockGetter)world, pos)).m_83281_()) {
                heightOffset = voxelShape.m_83297_(Direction.Axis.Y);
            }
            foundGround = true;
            break;
        } while ((pos = pos.m_7495_()).m_123342_() >= Mth.m_14143_((float)standOnYPos) - 1);
        if (foundGround) {
            return new Vec3(position.f_82479_, (double)pos.m_123342_() + heightOffset, position.f_82481_);
        }
        return position;
    }

    private void spawnCastingRoarParticles(Level world, LivingEntity entity) {
        double headX = entity.m_20185_();
        double headY = entity.m_20186_() + (double)entity.m_20206_() * 0.8;
        double headZ = entity.m_20189_();
        RoarParticle.RoarData roarData = new RoarParticle.RoarData(15, 87, 172, 221, 0.6f, 0.7f, 0.1f, 2.0f);
        MagicManager.spawnParticles((Level)world, (ParticleOptions)roarData, (double)headX, (double)headY, (double)headZ, (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)true);
    }

    private void spawnCastingBubbles(Level world, LivingEntity entity) {
        double casterX = entity.m_20185_();
        double casterY = entity.m_20186_();
        double casterZ = entity.m_20189_();
        MagicManager.spawnParticles((Level)world, (ParticleOptions)((ParticleOptions)TravelopticsParticles.WATER_BUBBLE_PARTICLE.get()), (double)casterX, (double)(casterY + 0.5), (double)casterZ, (int)4, (double)1.0, (double)0.5, (double)1.0, (double)0.03, (boolean)false);
        MagicManager.spawnParticles((Level)world, (ParticleOptions)ParticleTypes.f_123769_, (double)casterX, (double)(casterY + 0.1), (double)casterZ, (int)2, (double)0.8, (double)0.1, (double)0.8, (double)0.02, (boolean)false);
    }

    private void spawnCasterCastCompletionEffects(Level world, LivingEntity entity) {
        double casterX = entity.m_20185_();
        double casterY = entity.m_20186_();
        double casterZ = entity.m_20189_();
        MagicManager.spawnParticles((Level)world, (ParticleOptions)ParticleTypes.f_123769_, (double)casterX, (double)(casterY + 0.2), (double)casterZ, (int)50, (double)2.0, (double)0.5, (double)2.0, (double)0.5, (boolean)false);
    }

    private void spawnSerpentCastCompletionEffects(Level world, Vec3 serpent) {
        double serpentX = serpent.m_7096_();
        double serpentY = serpent.m_7098_();
        double serpentZ = serpent.m_7094_();
        MagicManager.spawnParticles((Level)world, (ParticleOptions)TravelopticsParticleHelper.SCYLLA_FOG, (double)serpentX, (double)(serpentY + 0.1), (double)serpentZ, (int)8, (double)2.5, (double)0.0, (double)2.5, (double)0.01, (boolean)false);
    }

    private int getSerpentCount(int spellLevel) {
        return 2 + spellLevel * 2;
    }

    private float getDamage(int spellLevel, LivingEntity entity) {
        return this.getSpellPower(spellLevel, (Entity)entity) * 3.75f;
    }

    private int getWetAmplifier(int spellLevel) {
        return spellLevel / 2;
    }

    public SpellDamageSource getDamageSource(Entity projectile, Entity attacker) {
        return super.getDamageSource(projectile, attacker).setIFrames(0);
    }

    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return TOGeneralUtils.buildAquaSpellInfo(this.getWetAmplifier(spellLevel), true, Component.m_237110_((String)"ui.traveloptics.multi_hit_aoe_damage", (Object[])new Object[]{Utils.stringTruncation((double)this.getDamage(spellLevel, caster), (int)2)}), Component.m_237110_((String)"ui.traveloptics.serpent_count", (Object[])new Object[]{this.getSerpentCount(spellLevel)}));
    }
}

