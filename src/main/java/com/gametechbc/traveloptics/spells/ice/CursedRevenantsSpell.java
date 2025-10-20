/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.init.ModParticle
 *  com.github.L_Ender.cataclysm.init.ModSounds
 *  io.redspace.ironsspellbooks.api.config.DefaultConfig
 *  io.redspace.ironsspellbooks.api.magic.MagicData
 *  io.redspace.ironsspellbooks.api.registry.SchoolRegistry
 *  io.redspace.ironsspellbooks.api.spells.AbstractSpell
 *  io.redspace.ironsspellbooks.api.spells.AutoSpellConfig
 *  io.redspace.ironsspellbooks.api.spells.CastSource
 *  io.redspace.ironsspellbooks.api.spells.CastType
 *  io.redspace.ironsspellbooks.api.spells.SpellRarity
 *  io.redspace.ironsspellbooks.api.util.CameraShakeData
 *  io.redspace.ironsspellbooks.api.util.CameraShakeManager
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  javax.annotation.Nullable
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.ServerLevelAccessor
 */
package com.gametechbc.traveloptics.spells.ice;

import com.gametechbc.traveloptics.api.particle.CylinderParticleManager;
import com.gametechbc.traveloptics.api.particle.ParticleDirection;
import com.gametechbc.traveloptics.entity.summons.SummonedDraugr;
import com.gametechbc.traveloptics.entity.summons.SummonedEliteDraugr;
import com.gametechbc.traveloptics.entity.summons.SummonedRoyalDraugr;
import com.gametechbc.traveloptics.init.TravelopticsEffects;
import com.github.L_Ender.cataclysm.init.ModParticle;
import com.github.L_Ender.cataclysm.init.ModSounds;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.AutoSpellConfig;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.CameraShakeData;
import io.redspace.ironsspellbooks.api.util.CameraShakeManager;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

@AutoSpellConfig
public class CursedRevenantsSpell
extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation("traveloptics", "cursed_revenants");
    private final DefaultConfig defaultConfig = new DefaultConfig().setMinRarity(SpellRarity.COMMON).setSchoolResource(SchoolRegistry.ICE_RESOURCE).setMaxLevel(5).setCooldownSeconds(150.0).build();

    public CursedRevenantsSpell() {
        this.manaCostPerLevel = 30;
        this.baseSpellPower = 1;
        this.spellPowerPerLevel = 1;
        this.castTime = 45;
        this.baseManaCost = 40;
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
        return Optional.of((SoundEvent)ModSounds.DRAUGR_IDLE.get());
    }

    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of((SoundEvent)ModSounds.DRAUGR_HURT.get());
    }

    public void onServerCastTick(Level level, int spellLevel, LivingEntity entity, @Nullable MagicData playerMagicData) {
        CylinderParticleManager.spawnParticles(level, (Entity)entity, 3, (ParticleOptions)ModParticle.CURSED_FLAME.get(), ParticleDirection.UPWARD, 2.0, 2.0, -1.0);
        super.onServerCastTick(level, spellLevel, entity, playerMagicData);
    }

    public void onCast(Level world, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        int summonTime = 12000;
        int draugrCount = (int)this.getDraugrCount(spellLevel);
        double radius = 3.5;
        double angleIncrement = Math.PI * 2 / (double)draugrCount;
        block5: for (int i = 0; i < draugrCount; ++i) {
            double angle = (double)i * angleIncrement;
            double xOffset = radius * Math.cos(angle);
            double zOffset = radius * Math.sin(angle);
            int randomDraugrType = (int)(Math.random() * 3.0);
            switch (randomDraugrType) {
                case 0: {
                    SummonedDraugr draugr = new SummonedDraugr(world, entity);
                    draugr.m_6034_(entity.m_20185_() + xOffset, entity.m_20186_(), entity.m_20189_() + zOffset);
                    draugr.m_21204_().m_22146_(Attributes.f_22281_).m_22100_((double)this.getDraugrDamage(spellLevel, entity));
                    draugr.m_21204_().m_22146_(Attributes.f_22276_).m_22100_((double)this.getDraugrHealth(spellLevel));
                    draugr.m_21153_(draugr.m_21233_());
                    draugr.m_6518_((ServerLevelAccessor)((ServerLevel)world), world.m_6436_(draugr.m_20097_()), MobSpawnType.MOB_SUMMONED, null, null);
                    world.m_7967_((Entity)draugr);
                    draugr.m_7292_(new MobEffectInstance((MobEffect)TravelopticsEffects.CURSED_REVENANTS_TIMER.get(), summonTime, 0, false, false, false));
                    MagicManager.spawnParticles((Level)world, (ParticleOptions)((ParticleOptions)ModParticle.CURSED_FLAME.get()), (double)(entity.m_20185_() + xOffset), (double)entity.m_20186_(), (double)(entity.m_20189_() + zOffset), (int)20, (double)0.0, (double)0.0, (double)0.0, (double)0.03, (boolean)false);
                    continue block5;
                }
                case 1: {
                    SummonedEliteDraugr eliteDraugr = new SummonedEliteDraugr(world, entity);
                    eliteDraugr.m_6034_(entity.m_20185_() + xOffset, entity.m_20186_(), entity.m_20189_() + zOffset);
                    eliteDraugr.m_21204_().m_22146_(Attributes.f_22281_).m_22100_((double)(this.getDraugrDamage(spellLevel, entity) + 1.0f));
                    eliteDraugr.m_21204_().m_22146_(Attributes.f_22276_).m_22100_((double)(this.getDraugrHealth(spellLevel) + 4.0f));
                    eliteDraugr.m_21153_(eliteDraugr.m_21233_());
                    eliteDraugr.m_6518_((ServerLevelAccessor)((ServerLevel)world), world.m_6436_(eliteDraugr.m_20097_()), MobSpawnType.MOB_SUMMONED, null, null);
                    world.m_7967_((Entity)eliteDraugr);
                    eliteDraugr.m_7292_(new MobEffectInstance((MobEffect)TravelopticsEffects.CURSED_REVENANTS_TIMER.get(), summonTime, 0, false, false, false));
                    MagicManager.spawnParticles((Level)world, (ParticleOptions)((ParticleOptions)ModParticle.CURSED_FLAME.get()), (double)(entity.m_20185_() + xOffset), (double)entity.m_20186_(), (double)(entity.m_20189_() + zOffset), (int)20, (double)0.0, (double)0.0, (double)0.0, (double)0.03, (boolean)false);
                    continue block5;
                }
                case 2: {
                    SummonedRoyalDraugr royalDraugr = new SummonedRoyalDraugr(world, entity);
                    royalDraugr.m_6034_(entity.m_20185_() + xOffset, entity.m_20186_(), entity.m_20189_() + zOffset);
                    royalDraugr.m_21204_().m_22146_(Attributes.f_22281_).m_22100_((double)(this.getDraugrDamage(spellLevel, entity) + 1.0f));
                    royalDraugr.m_21204_().m_22146_(Attributes.f_22276_).m_22100_((double)(this.getDraugrHealth(spellLevel) + 2.0f));
                    royalDraugr.m_21153_(royalDraugr.m_21233_());
                    royalDraugr.m_6518_((ServerLevelAccessor)((ServerLevel)world), world.m_6436_(royalDraugr.m_20097_()), MobSpawnType.MOB_SUMMONED, null, null);
                    world.m_7967_((Entity)royalDraugr);
                    royalDraugr.m_7292_(new MobEffectInstance((MobEffect)TravelopticsEffects.CURSED_REVENANTS_TIMER.get(), summonTime, 0, false, false, false));
                    MagicManager.spawnParticles((Level)world, (ParticleOptions)((ParticleOptions)ModParticle.CURSED_FLAME.get()), (double)(entity.m_20185_() + xOffset), (double)entity.m_20186_(), (double)(entity.m_20189_() + zOffset), (int)20, (double)0.0, (double)0.0, (double)0.0, (double)0.03, (boolean)false);
                    continue block5;
                }
                default: {
                    throw new IllegalStateException("Unexpected value: " + randomDraugrType);
                }
            }
        }
        int effectAmplifier = 0;
        if (entity.m_21023_((MobEffect)TravelopticsEffects.CURSED_REVENANTS_TIMER.get())) {
            effectAmplifier += entity.m_21124_((MobEffect)TravelopticsEffects.CURSED_REVENANTS_TIMER.get()).m_19564_() + 1;
        }
        entity.m_7292_(new MobEffectInstance((MobEffect)TravelopticsEffects.CURSED_REVENANTS_TIMER.get(), summonTime, effectAmplifier, false, false, true));
        CameraShakeManager.addCameraShake((CameraShakeData)new CameraShakeData(30, entity.m_20182_(), 25.0f));
        super.onCast(world, spellLevel, entity, castSource, playerMagicData);
    }

    private float getDraugrCount(int spellLevel) {
        return spellLevel;
    }

    private float getDraugrHealth(int spellLevel) {
        return 3.0f + (float)spellLevel * 5.0f;
    }

    private float getDraugrDamage(int spellLevel, LivingEntity caster) {
        return this.getSpellPower(spellLevel, (Entity)caster) * 0.8f;
    }

    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.m_237110_((String)"ui.traveloptics.summon_count", (Object[])new Object[]{Float.valueOf(this.getDraugrCount(spellLevel))}), Component.m_237110_((String)"ui.traveloptics.hp", (Object[])new Object[]{Float.valueOf(this.getDraugrHealth(spellLevel))}), Component.m_237110_((String)"ui.traveloptics.damage", (Object[])new Object[]{Float.valueOf(this.getDraugrDamage(spellLevel, caster))}), Component.m_237113_((String)"\u00a79T.O Magic 'n Extras"));
    }
}

