/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.api.config.DefaultConfig
 *  io.redspace.ironsspellbooks.api.magic.MagicData
 *  io.redspace.ironsspellbooks.api.spells.AbstractSpell
 *  io.redspace.ironsspellbooks.api.spells.AutoSpellConfig
 *  io.redspace.ironsspellbooks.api.spells.CastSource
 *  io.redspace.ironsspellbooks.api.spells.CastType
 *  io.redspace.ironsspellbooks.api.spells.SpellRarity
 *  io.redspace.ironsspellbooks.api.util.AnimationHolder
 *  io.redspace.ironsspellbooks.api.util.Utils
 *  javax.annotation.Nullable
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 */
package com.gametechbc.traveloptics.spells.aqua;

import com.gametechbc.traveloptics.api.init.TravelopticsSchools;
import com.gametechbc.traveloptics.api.particle.AdvancedLineParticleManager;
import com.gametechbc.traveloptics.api.particle.ParticleDirection;
import com.gametechbc.traveloptics.api.utils.TOGeneralUtils;
import com.gametechbc.traveloptics.entity.extended_projectiles.extended_wave.ExtendedWaveEntity;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import com.gametechbc.traveloptics.spells.TravelopticsSpellAnimations;
import com.gametechbc.traveloptics.util.TravelopticsParticleHelper;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.AutoSpellConfig;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

@AutoSpellConfig
public class TsunamiSpell
extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation("traveloptics", "tsunami");
    private final DefaultConfig defaultConfig = new DefaultConfig().setMinRarity(SpellRarity.RARE).setSchoolResource(TravelopticsSchools.AQUA_RESOURCE).setMaxLevel(4).setCooldownSeconds(25.0).build();

    public TsunamiSpell() {
        this.manaCostPerLevel = 55;
        this.baseSpellPower = 1;
        this.spellPowerPerLevel = 1;
        this.castTime = 15;
        this.baseManaCost = 80;
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
        return TravelopticsSpellAnimations.TSUNAMI_PREPARE;
    }

    public AnimationHolder getCastFinishAnimation() {
        return TravelopticsSpellAnimations.TSUNAMI_FINISH;
    }

    public Optional<SoundEvent> getCastStartSound() {
        return Optional.of((SoundEvent)TravelopticsSounds.TSUNAMI_CAST.get());
    }

    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.empty();
    }

    public boolean stopSoundOnCancel() {
        return true;
    }

    public int getEffectiveCastTime(int spellLevel, @Nullable LivingEntity entity) {
        return this.getCastTime(spellLevel);
    }

    public void onServerCastTick(Level level, int spellLevel, LivingEntity entity, @Nullable MagicData playerMagicData) {
        AdvancedLineParticleManager.spawnParticles(level, (Entity)entity, 12, TravelopticsParticleHelper.WATER_BUBBLE, ParticleDirection.UPWARD, 20.0, 6.0, 3.0, 2.5, false);
        AdvancedLineParticleManager.spawnParticles(level, (Entity)entity, 5, (ParticleOptions)ParticleTypes.f_123796_, ParticleDirection.UPWARD, 20.0, 6.0, 3.0, 2.5, false);
        super.onServerCastTick(level, spellLevel, entity, playerMagicData);
    }

    public void onCast(Level world, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        super.onCast(world, spellLevel, entity, castSource, playerMagicData);
        if (!world.f_46443_) {
            Vec3 lookDirection = entity.m_20154_();
            Vec3 horizontalLookDirection = new Vec3(lookDirection.f_82479_, 0.0, lookDirection.f_82481_).m_82541_();
            Vec3 perpendicularDirection = new Vec3(-horizontalLookDirection.f_82481_, 0.0, horizontalLookDirection.f_82479_).m_82541_();
            Vec3 centerPosition = entity.m_20182_().m_82549_(horizontalLookDirection.m_82490_(2.0));
            this.spawnWave(world, centerPosition, spellLevel, entity, horizontalLookDirection);
            Vec3 leftPosition = centerPosition.m_82549_(perpendicularDirection.m_82490_(-8.0));
            this.spawnWave(world, leftPosition, spellLevel, entity, horizontalLookDirection);
            Vec3 rightPosition = centerPosition.m_82549_(perpendicularDirection.m_82490_(8.0));
            this.spawnWave(world, rightPosition, spellLevel, entity, horizontalLookDirection);
        }
    }

    private void spawnWave(Level world, Vec3 position, int spellLevel, LivingEntity caster, Vec3 direction) {
        ExtendedWaveEntity waveEntity = new ExtendedWaveEntity(world, caster);
        waveEntity.m_6034_(position.f_82479_, caster.m_20186_(), position.f_82481_);
        waveEntity.setLifespan(20);
        waveEntity.setDamageAmount(this.getDamage(spellLevel, caster));
        waveEntity.setWetAmplifier(this.getWetAmplifier(spellLevel));
        waveEntity.setWaveScale(5.0f);
        float yaw = (float)Math.toDegrees(Math.atan2(direction.f_82481_, direction.f_82479_)) - 90.0f;
        waveEntity.m_146922_(yaw);
        world.m_7967_((Entity)waveEntity);
    }

    private float getDamage(int spellLevel, LivingEntity entity) {
        return 15.0f + this.getSpellPower(spellLevel, (Entity)entity) * 5.0f;
    }

    private int getWetAmplifier(int spellLevel) {
        return 2 + spellLevel;
    }

    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return TOGeneralUtils.buildAquaSpellInfo(this.getWetAmplifier(spellLevel), true, Component.m_237110_((String)"ui.traveloptics.single_hit_aoe_damage", (Object[])new Object[]{Utils.stringTruncation((double)this.getDamage(spellLevel, caster), (int)2)}));
    }
}

