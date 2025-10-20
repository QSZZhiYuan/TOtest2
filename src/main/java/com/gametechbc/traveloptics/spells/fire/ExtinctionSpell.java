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
 *  io.redspace.ironsspellbooks.api.spells.ICastData
 *  io.redspace.ironsspellbooks.api.spells.SchoolType
 *  io.redspace.ironsspellbooks.api.spells.SpellRarity
 *  io.redspace.ironsspellbooks.api.util.AnimationHolder
 *  io.redspace.ironsspellbooks.api.util.Utils
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  io.redspace.ironsspellbooks.capabilities.magic.TargetEntityCastData
 *  io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions
 *  javax.annotation.Nullable
 *  net.minecraft.ChatFormatting
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 */
package com.gametechbc.traveloptics.spells.fire;

import com.gametechbc.traveloptics.api.spells.AbstractUniqueSpell;
import com.gametechbc.traveloptics.entity.misc.TOScreenShakeEntity;
import com.gametechbc.traveloptics.entity.projectiles.asteroid.AsteroidEntity;
import com.gametechbc.traveloptics.init.TravelopticsItems;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import com.gametechbc.traveloptics.spells.TravelopticsSpellAnimations;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.AutoSpellConfig;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.ICastData;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.capabilities.magic.TargetEntityCastData;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

@AutoSpellConfig
public class ExtinctionSpell
extends AbstractUniqueSpell {
    private final ResourceLocation spellId = new ResourceLocation("traveloptics", "extinction");
    private final DefaultConfig defaultConfig = new DefaultConfig().setMinRarity(SpellRarity.LEGENDARY).setSchoolResource(SchoolRegistry.FIRE_RESOURCE).setMaxLevel(1).setCooldownSeconds(120.0).build();

    public ExtinctionSpell() {
        this.baseSpellPower = 1;
        this.spellPowerPerLevel = 1;
        this.manaCostPerLevel = 0;
        this.castTime = 38;
        this.baseManaCost = 600;
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
        return TravelopticsSpellAnimations.TECTONIC_RIFT_CAST;
    }

    public AnimationHolder getCastFinishAnimation() {
        return AnimationHolder.pass();
    }

    public Optional<SoundEvent> getCastStartSound() {
        return Optional.of((SoundEvent)TravelopticsSounds.EXTINCTION_START.get());
    }

    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of((SoundEvent)TravelopticsSounds.EXTINCTION_FINISH.get());
    }

    public boolean canBeInterrupted(@Nullable Player player) {
        return false;
    }

    public int getEffectiveCastTime(int spellLevel, @Nullable LivingEntity entity) {
        return this.getCastTime(spellLevel);
    }

    public boolean checkPreCastConditions(Level level, int spellLevel, LivingEntity caster, MagicData playerMagicData) {
        List<Item> allowedWeapons = List.of((Item)TravelopticsItems.GAUNTLET_OF_EXTINCTION.get(), (Item)TravelopticsItems.GAUNTLET_OF_EXTINCTION_LEVEL_ONE.get(), (Item)TravelopticsItems.GAUNTLET_OF_EXTINCTION_LEVEL_TWO.get(), (Item)TravelopticsItems.GAUNTLET_OF_EXTINCTION_LEVEL_THREE.get());
        if (!allowedWeapons.contains(caster.m_21205_().m_41720_())) {
            if (caster instanceof Player) {
                Player player = (Player)caster;
                if (!level.m_5776_()) {
                    player.m_5661_((Component)Component.m_237115_((String)"spell.traveloptics.tectonic_rift.warning").m_130940_(ChatFormatting.RED), true);
                }
            }
            return false;
        }
        return Utils.preCastTargetHelper((Level)level, (LivingEntity)caster, (MagicData)playerMagicData, (AbstractSpell)this, (int)((int)this.getRange()), (float)0.5f);
    }

    public void onCast(Level level, int spellLevel, LivingEntity caster, CastSource castSource, MagicData playerMagicData) {
        ICastData iCastData = playerMagicData.getAdditionalCastData();
        if (iCastData instanceof TargetEntityCastData) {
            TargetEntityCastData targetData = (TargetEntityCastData)iCastData;
            LivingEntity targetEntity = targetData.getTarget((ServerLevel)level);
            TOScreenShakeEntity.createScreenShake(level, caster.m_20182_(), this.getShockwaveRadius(), 0.015f, 60, 10, 20, true);
            MagicManager.spawnParticles((Level)level, (ParticleOptions)new BlastwaveParticleOptions(((SchoolType)SchoolRegistry.FIRE.get()).getTargetingColor(), this.getShockwaveRadius()), (double)caster.m_20185_(), (double)(caster.m_20186_() + (double)0.165f), (double)caster.m_20189_(), (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)true);
            level.m_6269_(null, (Entity)caster, (SoundEvent)TravelopticsSounds.ASTEROID_LOOP.get(), SoundSource.AMBIENT, 4.0f, 1.0f);
            if (targetEntity != null) {
                Vec3 hitPosition = targetEntity.m_20182_();
                Vec3 spawnPosition = hitPosition.m_82520_(0.0, 120.0, 0.0);
                AsteroidEntity asteroid = new AsteroidEntity(level, caster);
                asteroid.m_146884_(spawnPosition);
                Vec3 direction = hitPosition.m_82546_(spawnPosition).m_82541_();
                asteroid.m_20256_(direction.m_82490_(1.8));
                asteroid.setExplosionRadius(this.getExplosionRadius());
                asteroid.setDamage(this.getImpactDamage(spellLevel, caster));
                asteroid.setCraterInnerRadius(5.0f);
                asteroid.setCraterDepth(this.getCraterDepth());
                asteroid.setCraterDamage(this.getEruptionDamage(spellLevel, caster));
                asteroid.setShockwaveRadius(this.getShockwaveRadius());
                asteroid.setShockwaveDamage(this.getShockwaveDamage(spellLevel, caster));
                asteroid.m_5602_((Entity)caster);
                asteroid.setTargetY(hitPosition.f_82480_);
                level.m_7967_((Entity)asteroid);
            }
        }
        super.onCast(level, spellLevel, caster, castSource, playerMagicData);
    }

    public float getRange() {
        return 32.0f;
    }

    private int getCraterDepth() {
        return 3;
    }

    private float getImpactDamage(int spellLevel, LivingEntity caster) {
        return 50.0f + this.getSpellPower(spellLevel, (Entity)caster) * 15.0f;
    }

    private float getShockwaveDamage(int spellLevel, LivingEntity caster) {
        return 25.0f + this.getSpellPower(spellLevel, (Entity)caster) * 7.5f;
    }

    private float getEruptionDamage(int spellLevel, LivingEntity caster) {
        return 5.5f + this.getSpellPower(spellLevel, (Entity)caster) * 2.5f;
    }

    private float getExplosionRadius() {
        return 15.0f;
    }

    private float getShockwaveRadius() {
        return 30.0f;
    }

    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.m_237110_((String)"ui.traveloptics.impact_damage", (Object[])new Object[]{Utils.stringTruncation((double)this.getImpactDamage(spellLevel, caster), (int)2)}), Component.m_237110_((String)"ui.traveloptics.impact_radius", (Object[])new Object[]{Utils.stringTruncation((double)this.getExplosionRadius(), (int)2)}), Component.m_237110_((String)"ui.traveloptics.shockwave_damage", (Object[])new Object[]{Utils.stringTruncation((double)this.getShockwaveDamage(spellLevel, caster), (int)2)}), Component.m_237110_((String)"ui.traveloptics.shockwave_radius", (Object[])new Object[]{Utils.stringTruncation((double)this.getShockwaveRadius(), (int)2)}), Component.m_237110_((String)"ui.traveloptics.eruption_damage", (Object[])new Object[]{Utils.stringTruncation((double)this.getEruptionDamage(spellLevel, caster), (int)2)}), Component.m_237113_((String)"\u00a79T.O Magic 'n Extras"));
    }

    public boolean stopSoundOnCancel() {
        return true;
    }
}

