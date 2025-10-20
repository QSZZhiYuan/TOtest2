/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.api.config.DefaultConfig
 *  io.redspace.ironsspellbooks.api.magic.MagicData
 *  io.redspace.ironsspellbooks.api.registry.AttributeRegistry
 *  io.redspace.ironsspellbooks.api.registry.SchoolRegistry
 *  io.redspace.ironsspellbooks.api.spells.AbstractSpell
 *  io.redspace.ironsspellbooks.api.spells.AutoSpellConfig
 *  io.redspace.ironsspellbooks.api.spells.CastSource
 *  io.redspace.ironsspellbooks.api.spells.CastType
 *  io.redspace.ironsspellbooks.api.spells.SchoolType
 *  io.redspace.ironsspellbooks.api.spells.SpellRarity
 *  io.redspace.ironsspellbooks.api.util.AnimationHolder
 *  io.redspace.ironsspellbooks.api.util.CameraShakeData
 *  io.redspace.ironsspellbooks.api.util.CameraShakeManager
 *  io.redspace.ironsspellbooks.api.util.Utils
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  io.redspace.ironsspellbooks.capabilities.magic.RecastInstance
 *  io.redspace.ironsspellbooks.damage.DamageSources
 *  io.redspace.ironsspellbooks.network.spell.ClientboundParticleShockwave
 *  io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions
 *  io.redspace.ironsspellbooks.registries.ParticleRegistry
 *  io.redspace.ironsspellbooks.setup.Messages
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleType
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.Attribute
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 */
package com.gametechbc.traveloptics.spells.holy;

import com.gametechbc.traveloptics.init.TravelopticsEffects;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import com.gametechbc.traveloptics.spells.TravelopticsSpellAnimations;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.AutoSpellConfig;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.CameraShakeData;
import io.redspace.ironsspellbooks.api.util.CameraShakeManager;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.capabilities.magic.RecastInstance;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.network.spell.ClientboundParticleShockwave;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import io.redspace.ironsspellbooks.setup.Messages;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

@AutoSpellConfig
public class NullflareSpell
extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation("traveloptics", "nullflare");
    private final DefaultConfig defaultConfig = new DefaultConfig().setMinRarity(SpellRarity.COMMON).setSchoolResource(SchoolRegistry.HOLY_RESOURCE).setMaxLevel(5).setCooldownSeconds(40.0).build();

    public NullflareSpell() {
        this.manaCostPerLevel = 30;
        this.baseSpellPower = 1;
        this.spellPowerPerLevel = 1;
        this.castTime = 16;
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

    public AnimationHolder getCastStartAnimation() {
        return TravelopticsSpellAnimations.NULLFLARE_START;
    }

    public AnimationHolder getCastFinishAnimation() {
        return TravelopticsSpellAnimations.NULLFLARE_FINISH;
    }

    public Optional<SoundEvent> getCastStartSound() {
        return Optional.of((SoundEvent)TravelopticsSounds.NULLFLARE_TWO.get());
    }

    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.empty();
    }

    public int getRecastCount(int spellLevel, LivingEntity caster) {
        return 5;
    }

    public boolean stopSoundOnCancel() {
        return true;
    }

    public void onCast(Level level, int spellLevel, LivingEntity caster, CastSource castSource, MagicData playerMagicData) {
        boolean isCrouching;
        float radius = this.getRange(spellLevel);
        int duration = (int)this.getEffectDuration();
        if (!playerMagicData.getPlayerRecasts().hasRecastForSpell(this.getSpellId())) {
            playerMagicData.getPlayerRecasts().addRecast(new RecastInstance(this.getSpellId(), spellLevel, this.getRecastCount(spellLevel, caster), 200, castSource, null), playerMagicData);
        }
        if (isCrouching = caster.m_6047_()) {
            MagicManager.spawnParticles((Level)level, (ParticleOptions)new BlastwaveParticleOptions(((SchoolType)SchoolRegistry.FIRE.get()).getTargetingColor(), radius), (double)caster.m_20185_(), (double)(caster.m_20186_() + (double)0.165f), (double)caster.m_20189_(), (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)true);
            Messages.sendToPlayersTrackingEntity((Object)new ClientboundParticleShockwave(new Vec3(caster.m_20185_(), caster.m_20186_() + (double)0.165f, caster.m_20189_()), radius, (ParticleType)ParticleRegistry.EMBER_PARTICLE.get()), (Entity)caster, (boolean)true);
            Messages.sendToPlayersTrackingEntity((Object)new ClientboundParticleShockwave(new Vec3(caster.m_20185_(), caster.m_20186_() + (double)0.165f, caster.m_20189_()), radius, (ParticleType)ParticleRegistry.FIRE_PARTICLE.get()), (Entity)caster, (boolean)true);
        } else {
            MagicManager.spawnParticles((Level)level, (ParticleOptions)new BlastwaveParticleOptions(((SchoolType)SchoolRegistry.ICE.get()).getTargetingColor(), radius), (double)caster.m_20185_(), (double)(caster.m_20186_() + (double)0.165f), (double)caster.m_20189_(), (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)true);
            Messages.sendToPlayersTrackingEntity((Object)new ClientboundParticleShockwave(new Vec3(caster.m_20185_(), caster.m_20186_() + (double)0.165f, caster.m_20189_()), radius, (ParticleType)ParticleRegistry.SNOWFLAKE_PARTICLE.get()), (Entity)caster, (boolean)true);
            Messages.sendToPlayersTrackingEntity((Object)new ClientboundParticleShockwave(new Vec3(caster.m_20185_(), caster.m_20186_() + (double)0.165f, caster.m_20189_()), radius, (ParticleType)ParticleRegistry.SNOW_DUST.get()), (Entity)caster, (boolean)true);
        }
        level.m_6249_((Entity)caster, caster.m_20191_().m_82377_((double)radius, 4.0, (double)radius), target -> !DamageSources.isFriendlyFireBetween((Entity)target, (Entity)caster)).forEach(target -> {
            LivingEntity livingEntity;
            if (target instanceof LivingEntity && (livingEntity = (LivingEntity)target).m_20280_((Entity)caster) < (double)(radius * radius)) {
                float baseDamage = this.getDamage(spellLevel, caster);
                if (isCrouching) {
                    MobEffectInstance fireEffect;
                    MobEffectInstance iceEffect = livingEntity.m_21124_((MobEffect)TravelopticsEffects.NULLFLARE_ICE.get());
                    if (iceEffect != null) {
                        CameraShakeManager.addCameraShake((CameraShakeData)new CameraShakeData(20, caster.m_20182_(), 6.0f));
                        MagicManager.spawnParticles((Level)level, (ParticleOptions)((ParticleOptions)ParticleTypes.f_123756_), (double)livingEntity.m_20185_(), (double)(livingEntity.m_20186_() + 1.0), (double)livingEntity.m_20189_(), (int)20, (double)0.0, (double)0.0, (double)0.0, (double)0.3, (boolean)false);
                        livingEntity.m_21195_((MobEffect)TravelopticsEffects.NULLFLARE_ICE.get());
                        int amplifier = iceEffect.m_19564_();
                        double fireSpellPower = caster.m_21133_((Attribute)AttributeRegistry.FIRE_SPELL_POWER.get());
                        double scaledDamage = (double)baseDamage * fireSpellPower * (1.0 + 0.75 * (double)amplifier);
                        DamageSources.applyDamage((Entity)livingEntity, (float)((float)scaledDamage), (DamageSource)this.getDamageSource((Entity)caster));
                    }
                    if ((fireEffect = livingEntity.m_21124_((MobEffect)TravelopticsEffects.NULLFLARE_FIRE.get())) != null) {
                        livingEntity.m_7292_(new MobEffectInstance((MobEffect)TravelopticsEffects.NULLFLARE_FIRE.get(), duration, fireEffect.m_19564_() + 1));
                    } else {
                        livingEntity.m_7292_(new MobEffectInstance((MobEffect)TravelopticsEffects.NULLFLARE_FIRE.get(), duration, 0));
                    }
                } else {
                    MobEffectInstance iceEffect;
                    MobEffectInstance fireEffect = livingEntity.m_21124_((MobEffect)TravelopticsEffects.NULLFLARE_FIRE.get());
                    if (fireEffect != null) {
                        CameraShakeManager.addCameraShake((CameraShakeData)new CameraShakeData(20, caster.m_20182_(), 6.0f));
                        MagicManager.spawnParticles((Level)level, (ParticleOptions)((ParticleOptions)ParticleTypes.f_123756_), (double)livingEntity.m_20185_(), (double)(livingEntity.m_20186_() + 1.0), (double)livingEntity.m_20189_(), (int)20, (double)0.0, (double)0.0, (double)0.0, (double)0.3, (boolean)false);
                        livingEntity.m_21195_((MobEffect)TravelopticsEffects.NULLFLARE_FIRE.get());
                        int amplifier = fireEffect.m_19564_();
                        double iceSpellPower = caster.m_21133_((Attribute)AttributeRegistry.ICE_SPELL_POWER.get());
                        double scaledDamage = (double)baseDamage * iceSpellPower * (1.0 + 0.75 * (double)amplifier);
                        DamageSources.applyDamage((Entity)livingEntity, (float)((float)scaledDamage), (DamageSource)this.getDamageSource((Entity)caster));
                    }
                    if ((iceEffect = livingEntity.m_21124_((MobEffect)TravelopticsEffects.NULLFLARE_ICE.get())) != null) {
                        livingEntity.m_7292_(new MobEffectInstance((MobEffect)TravelopticsEffects.NULLFLARE_ICE.get(), duration, iceEffect.m_19564_() + 1));
                    } else {
                        livingEntity.m_7292_(new MobEffectInstance((MobEffect)TravelopticsEffects.NULLFLARE_ICE.get(), duration, 0));
                    }
                }
            }
        });
        super.onCast(level, spellLevel, caster, castSource, playerMagicData);
    }

    private float getDamage(int spellLevel, LivingEntity caster) {
        float spellPower = this.getSpellPower(spellLevel, (Entity)caster);
        return 10.0f + spellPower * 5.0f;
    }

    private String getFireDamageText(int spellLevel, LivingEntity caster) {
        if (caster != null) {
            float baseDamage = this.getDamage(spellLevel, caster);
            double fireSpellPower = caster.m_21204_().m_22171_((Attribute)AttributeRegistry.FIRE_SPELL_POWER.get()) ? caster.m_21133_((Attribute)AttributeRegistry.FIRE_SPELL_POWER.get()) : 1.0;
            float finalDamage = (float)((double)baseDamage * fireSpellPower);
            String baseDamageText = Utils.stringTruncation((double)baseDamage, (int)1);
            String finalDamageText = Utils.stringTruncation((double)finalDamage, (int)1);
            if (fireSpellPower > 1.0) {
                return String.format("%s -> %s", baseDamageText, finalDamageText);
            }
            return baseDamageText;
        }
        return Utils.stringTruncation((double)this.getDamage(spellLevel, caster), (int)1);
    }

    private String getIceDamageText(int spellLevel, LivingEntity caster) {
        if (caster != null) {
            float baseDamage = this.getDamage(spellLevel, caster);
            double iceSpellPower = caster.m_21204_().m_22171_((Attribute)AttributeRegistry.ICE_SPELL_POWER.get()) ? caster.m_21133_((Attribute)AttributeRegistry.ICE_SPELL_POWER.get()) : 1.0;
            float finalDamage = (float)((double)baseDamage * iceSpellPower);
            String baseDamageText = Utils.stringTruncation((double)baseDamage, (int)1);
            String finalDamageText = Utils.stringTruncation((double)finalDamage, (int)1);
            if (iceSpellPower > 1.0) {
                return String.format("%s -> %s", baseDamageText, finalDamageText);
            }
            return baseDamageText;
        }
        return Utils.stringTruncation((double)this.getDamage(spellLevel, caster), (int)1);
    }

    private float getRange(int spellLevel) {
        return 2.0f + (float)spellLevel * 2.0f;
    }

    private float getEffectDuration() {
        return 200.0f;
    }

    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        double baseDamage = this.getDamage(spellLevel, caster);
        float range = this.getRange(spellLevel);
        return List.of(Component.m_237110_((String)"ui.traveloptics.nullflare_blast", (Object[])new Object[]{this.getFireDamageText(spellLevel, caster)}), Component.m_237110_((String)"ui.traveloptics.nullflare_blast_fire_damage", (Object[])new Object[]{this.getFireDamageText(spellLevel, caster)}), Component.m_237110_((String)"ui.traveloptics.nullflare_blast_ice_damage", (Object[])new Object[]{this.getIceDamageText(spellLevel, caster)}), Component.m_237110_((String)"ui.traveloptics.radius", (Object[])new Object[]{Utils.stringTruncation((double)range, (int)2)}), Component.m_237115_((String)"ui.traveloptics.nullflare_blast.stacks"), Component.m_237113_((String)"\u00a79T.O Magic 'n Extras"));
    }
}

