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
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  javax.annotation.Nullable
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectCategory
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 */
package com.gametechbc.traveloptics.spells.holy;

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
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

@AutoSpellConfig
public class BanishSpell
extends AbstractSpell {
    private static final Random RANDOM = new Random();
    private final ResourceLocation spellId = new ResourceLocation("traveloptics", "banish");
    private final DefaultConfig defaultConfig = new DefaultConfig().setMinRarity(SpellRarity.RARE).setSchoolResource(SchoolRegistry.HOLY_RESOURCE).setMaxLevel(5).setCooldownSeconds(12.0).build();
    private final Set<ResourceLocation> EFFECT_BLACKLIST = this.parseBlacklist("minecraft:blindness");

    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of();
    }

    public BanishSpell() {
        this.manaCostPerLevel = 15;
        this.baseSpellPower = 4;
        this.spellPowerPerLevel = 2;
        this.castTime = 40;
        this.baseManaCost = 90;
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
        return Optional.of((SoundEvent)ModSounds.HARBINGER_LASER.get());
    }

    public void onServerCastTick(Level level, int spellLevel, LivingEntity entity, @Nullable MagicData playerMagicData) {
        if (!level.f_46443_) {
            double radius = 3.0;
            int particleCount = 15;
            double centerY = entity.m_20186_() + (double)entity.m_20206_() * 0.5;
            for (int i = 0; i < particleCount; ++i) {
                double theta = Math.PI * 2 * RANDOM.nextDouble();
                double phi = Math.acos(2.0 * RANDOM.nextDouble() - 1.0);
                double xOffset = radius * Math.sin(phi) * Math.cos(theta);
                double yOffset = radius * Math.sin(phi) * Math.sin(theta);
                double zOffset = radius * Math.cos(phi);
                Vec3 direction = new Vec3(entity.m_20185_() - (entity.m_20185_() + xOffset), centerY - (centerY + yOffset), entity.m_20189_() - (entity.m_20189_() + zOffset)).m_82541_();
                MagicManager.spawnParticles((Level)level, (ParticleOptions)((ParticleOptions)ModParticle.CURSED_FLAME.get()), (double)(entity.m_20185_() + xOffset), (double)(centerY + yOffset), (double)(entity.m_20189_() + zOffset), (int)0, (double)direction.f_82479_, (double)direction.f_82480_, (double)direction.f_82481_, (double)0.1, (boolean)true);
            }
        }
    }

    public void onCast(Level level, int spellLevel, LivingEntity caster, CastSource castSource, MagicData playerMagicData) {
        super.onCast(level, spellLevel, caster, castSource, playerMagicData);
        if (!level.f_46443_) {
            double radius = 12.0;
            this.removeNegativeEffects(caster);
            List nearbyEntities = level.m_45976_(LivingEntity.class, caster.m_20191_().m_82400_(radius));
            for (LivingEntity target : nearbyEntities) {
                if (target == caster) continue;
                this.removePositiveEffects(target);
            }
        }
    }

    private Set<ResourceLocation> parseBlacklist(String blacklist) {
        return Arrays.stream(blacklist.split(",")).map(String::trim).map(ResourceLocation::new).collect(Collectors.toSet());
    }

    private void removeNegativeEffects(LivingEntity entity) {
        List<MobEffect> negativeEffects = entity.m_21220_().stream().map(MobEffectInstance::m_19544_).filter(effect -> effect.m_19483_() == MobEffectCategory.HARMFUL).filter(effect -> !this.EFFECT_BLACKLIST.contains(BuiltInRegistries.f_256974_.m_7981_(effect))).toList();
        negativeEffects.forEach(arg_0 -> ((LivingEntity)entity).m_21195_(arg_0));
    }

    private void removePositiveEffects(LivingEntity entity) {
        List<MobEffect> positiveEffects = entity.m_21220_().stream().map(MobEffectInstance::m_19544_).filter(effect -> effect.m_19483_() == MobEffectCategory.BENEFICIAL).filter(effect -> !this.EFFECT_BLACKLIST.contains(BuiltInRegistries.f_256974_.m_7981_(effect))).toList();
        positiveEffects.forEach(arg_0 -> ((LivingEntity)entity).m_21195_(arg_0));
    }
}

