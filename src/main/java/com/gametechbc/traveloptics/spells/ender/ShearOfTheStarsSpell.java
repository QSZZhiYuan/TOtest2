/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity
 *  io.redspace.ironsspellbooks.api.config.DefaultConfig
 *  io.redspace.ironsspellbooks.api.magic.MagicData
 *  io.redspace.ironsspellbooks.api.registry.SchoolRegistry
 *  io.redspace.ironsspellbooks.api.spells.AutoSpellConfig
 *  io.redspace.ironsspellbooks.api.spells.CastSource
 *  io.redspace.ironsspellbooks.api.spells.CastType
 *  io.redspace.ironsspellbooks.api.spells.SpellRarity
 *  io.redspace.ironsspellbooks.api.util.AnimationHolder
 *  io.redspace.ironsspellbooks.api.util.Utils
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  javax.annotation.Nullable
 *  net.minecraft.ChatFormatting
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 */
package com.gametechbc.traveloptics.spells.ender;

import com.gametechbc.traveloptics.api.spells.AbstractUniqueSpell;
import com.gametechbc.traveloptics.entity.projectiles.dragon_spirit_spell_entity.DragonSpiritSpellEntity;
import com.gametechbc.traveloptics.init.TravelopticsItems;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import com.gametechbc.traveloptics.spells.TravelopticsSpellAnimations;
import com.gametechbc.traveloptics.util.TravelopticsParticleHelper;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AutoSpellConfig;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

@AutoSpellConfig
public class ShearOfTheStarsSpell
extends AbstractUniqueSpell {
    private final ResourceLocation spellId = new ResourceLocation("traveloptics", "shear_of_the_stars");
    private final DefaultConfig defaultConfig = new DefaultConfig().setMinRarity(SpellRarity.LEGENDARY).setSchoolResource(SchoolRegistry.ENDER_RESOURCE).setMaxLevel(1).setCooldownSeconds(60.0).build();

    public ShearOfTheStarsSpell() {
        this.manaCostPerLevel = 0;
        this.baseSpellPower = 1;
        this.spellPowerPerLevel = 1;
        this.castTime = 80;
        this.baseManaCost = 200;
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
        return TravelopticsSpellAnimations.SHEAR_OF_STARS_CHARGE;
    }

    public AnimationHolder getCastFinishAnimation() {
        return TravelopticsSpellAnimations.SHEAR_OF_STARS_CAST;
    }

    public Optional<SoundEvent> getCastStartSound() {
        return Optional.of((SoundEvent)TravelopticsSounds.DRAGON_SPIRIT_SPAWN.get());
    }

    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of((SoundEvent)TravelopticsSounds.NIGHTWARDEN_BIG_SLAM_CLONES.get());
    }

    public int getEffectiveCastTime(int spellLevel, @Nullable LivingEntity entity) {
        return this.getCastTime(spellLevel);
    }

    public boolean checkPreCastConditions(Level level, int spellLevel, LivingEntity entity, MagicData playerMagicData) {
        List<Item> allowedWeapons = List.of((Item)TravelopticsItems.STELLOTHORN.get(), (Item)TravelopticsItems.STELLOTHORN_LEVEL_ONE.get(), (Item)TravelopticsItems.STELLOTHORN_LEVEL_TWO.get(), (Item)TravelopticsItems.STELLOTHORN_LEVEL_THREE.get());
        if (allowedWeapons.contains(entity.m_21205_().m_41720_())) {
            return true;
        }
        if (entity instanceof Player) {
            Player player = (Player)entity;
            if (!level.m_5776_()) {
                player.m_5661_((Component)Component.m_237115_((String)"spell.traveloptics.shear_of_the_stars.warning").m_130940_(ChatFormatting.RED), true);
            }
        }
        return false;
    }

    public void onServerCastTick(Level level, int spellLevel, LivingEntity entity, @Nullable MagicData playerMagicData) {
        if (playerMagicData != null) {
            double totalCastTime = this.getCastTime(spellLevel);
            double remainingTime = playerMagicData.getCastDurationRemaining();
            double progress = 1.0 - remainingTime / totalCastTime;
            ShearOfTheStarsSpell.dragonSpiritChannelingParticles(entity, progress);
        }
        super.onServerCastTick(level, spellLevel, entity, playerMagicData);
    }

    public void onCast(Level world, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        Vec3 origin = entity.m_146892_();
        DragonSpiritSpellEntity spirit = new DragonSpiritSpellEntity(world, entity);
        spirit.setDamage(this.getDamage(spellLevel, entity));
        spirit.setStellarTrailDamage(this.getStellarTrailDamage(spellLevel, entity));
        double yOffset = 3.2;
        spirit.m_146884_(origin.m_82549_(entity.m_20156_()).m_82492_(0.0, (double)(spirit.m_20206_() / 2.0f) - yOffset, 0.0));
        spirit.shoot(entity.m_20154_());
        world.m_7967_((Entity)spirit);
        ScreenShake_Entity.ScreenShake((Level)world, (Vec3)entity.m_20182_(), (float)12.0f, (float)0.06f, (int)20, (int)30);
        super.onCast(world, spellLevel, entity, castSource, playerMagicData);
    }

    private float getDamage(int spellLevel, LivingEntity entity) {
        return (10.0f + this.getSpellPower(spellLevel, (Entity)entity) * 10.0f) * 0.3333f;
    }

    private float getStellarTrailDamage(int spellLevel, LivingEntity entity) {
        return (10.0f + this.getSpellPower(spellLevel, (Entity)entity) * 10.0f) * 0.25f;
    }

    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.m_237110_((String)"ui.traveloptics.dragon_spirit_iframe_damage", (Object[])new Object[]{Utils.stringTruncation((double)this.getDamage(spellLevel, caster), (int)2)}), Component.m_237110_((String)"ui.traveloptics.stellar_trail_damage", (Object[])new Object[]{Utils.stringTruncation((double)this.getStellarTrailDamage(spellLevel, caster), (int)2)}), Component.m_237113_((String)"\u00a79T.O Magic 'n Extras"));
    }

    public static void dragonSpiritChannelingParticles(LivingEntity caster, double progress) {
        int particleCount = 20;
        double maxHeight = 3.8;
        double radius = 2.5 + progress * 1.8;
        double yOffsetAdjustment = 0.2;
        ParticleOptions particle = progress < 0.5 ? TravelopticsParticleHelper.SHORT_LIGHT_PURPLE_GLOWING_ENCHANT : TravelopticsParticleHelper.SHORT_LIGHT_GOLD_GLOWING_ENCHANT;
        for (int i = 0; i < particleCount; ++i) {
            double t = (double)i / (double)particleCount;
            double angle = t * Math.PI * 6.0 + progress * Math.PI * 8.0;
            double spiralIntensity = 0.3 + 0.7 * Math.sin(progress * Math.PI * 3.0);
            double r = radius * (0.8 + 0.2 * Math.sin(t * Math.PI * 4.0)) * spiralIntensity;
            double x = r * Math.cos(angle);
            double z = r * Math.sin(angle);
            double riseY = yOffsetAdjustment + 0.5 + maxHeight * progress + 0.3 * Math.sin(t * Math.PI * 2.0 + progress * Math.PI * 4.0) + caster.m_217043_().m_188500_() * 0.4;
            double speed = 0.015 + 0.01 * caster.m_217043_().m_188500_();
            MagicManager.spawnParticles((Level)caster.m_9236_(), (ParticleOptions)particle, (double)(caster.m_20185_() + x), (double)(caster.m_20186_() + riseY), (double)(caster.m_20189_() + z), (int)1, (double)0.0, (double)speed, (double)0.0, (double)0.15, (boolean)false);
        }
    }

    public boolean stopSoundOnCancel() {
        return true;
    }
}

