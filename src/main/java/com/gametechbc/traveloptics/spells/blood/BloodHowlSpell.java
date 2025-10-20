/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.entity.projectile.Wither_Howitzer_Entity
 *  com.github.L_Ender.cataclysm.init.ModSounds
 *  io.redspace.ironsspellbooks.api.config.DefaultConfig
 *  io.redspace.ironsspellbooks.api.magic.MagicData
 *  io.redspace.ironsspellbooks.api.registry.SchoolRegistry
 *  io.redspace.ironsspellbooks.api.spells.AbstractSpell
 *  io.redspace.ironsspellbooks.api.spells.AutoSpellConfig
 *  io.redspace.ironsspellbooks.api.spells.CastSource
 *  io.redspace.ironsspellbooks.api.spells.CastType
 *  io.redspace.ironsspellbooks.api.spells.SpellRarity
 *  io.redspace.ironsspellbooks.api.util.AnimationHolder
 *  io.redspace.ironsspellbooks.api.util.Utils
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  io.redspace.ironsspellbooks.capabilities.magic.RecastInstance
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.Nullable
 */
package com.gametechbc.traveloptics.spells.blood;

import com.gametechbc.traveloptics.entity.extended_projectiles.ExtendedWitherHowitzerEntity;
import com.gametechbc.traveloptics.init.TravelopticsEntities;
import com.gametechbc.traveloptics.spells.TravelopticsSpellAnimations;
import com.github.L_Ender.cataclysm.entity.projectile.Wither_Howitzer_Entity;
import com.github.L_Ender.cataclysm.init.ModSounds;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.AutoSpellConfig;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.capabilities.magic.RecastInstance;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

@AutoSpellConfig
public class BloodHowlSpell
extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation("traveloptics", "blood_howl");
    private final DefaultConfig defaultConfig = new DefaultConfig().setMinRarity(SpellRarity.UNCOMMON).setSchoolResource(SchoolRegistry.BLOOD_RESOURCE).setMaxLevel(5).setCooldownSeconds(20.0).build();

    public BloodHowlSpell() {
        this.manaCostPerLevel = 20;
        this.baseSpellPower = 1;
        this.spellPowerPerLevel = 1;
        this.castTime = 0;
        this.baseManaCost = 70;
    }

    public CastType getCastType() {
        return CastType.INSTANT;
    }

    public DefaultConfig getDefaultConfig() {
        return this.defaultConfig;
    }

    public ResourceLocation getSpellResource() {
        return this.spellId;
    }

    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.empty();
    }

    public Optional<SoundEvent> getCastStartSound() {
        return Optional.of((SoundEvent)ModSounds.HARBINGER_HURT.get());
    }

    public AnimationHolder getCastStartAnimation() {
        return TravelopticsSpellAnimations.SHOOT;
    }

    public int getRecastCount(int spellLevel, @Nullable LivingEntity entity) {
        return 1 + spellLevel;
    }

    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.m_237110_((String)"ui.traveloptics.aoe_damage", (Object[])new Object[]{Utils.stringTruncation((double)this.getDamage(spellLevel, caster), (int)2)}), Component.m_237110_((String)"ui.traveloptics.shot_count", (Object[])new Object[]{this.getRecastCount(spellLevel, caster)}), Component.m_237113_((String)"\u00a79T.O Magic 'n Extras"));
    }

    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        if (!playerMagicData.getPlayerRecasts().hasRecastForSpell(this.getSpellId())) {
            playerMagicData.getPlayerRecasts().addRecast(new RecastInstance(this.getSpellId(), spellLevel, this.getRecastCount(spellLevel, entity), 80, castSource, null), playerMagicData);
        }
        this.spawnWitherHowitzerProjectile(level, entity, spellLevel);
        Vec3 lookVec = entity.m_20154_();
        double offsetDistance = 1.0;
        Vec3 handPosition = new Vec3(entity.m_20185_(), entity.m_20186_() + (double)entity.m_20192_() - 0.5, entity.m_20189_());
        Vec3 offsetPosition = handPosition.m_82549_(lookVec.m_82490_(offsetDistance));
        ParticleOptions particleType = (ParticleOptions)ParticleTypes.f_123756_;
        MagicManager.spawnParticles((Level)level, (ParticleOptions)particleType, (double)offsetPosition.m_7096_(), (double)offsetPosition.m_7098_(), (double)offsetPosition.m_7094_(), (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)true);
    }

    private float getDamage(int spellLevel, LivingEntity caster) {
        return 4.0f + this.getSpellPower(spellLevel, (Entity)caster) * 2.5f;
    }

    private void spawnWitherHowitzerProjectile(Level level, LivingEntity entity, int spellLevel) {
        float damage = this.getDamage(spellLevel, entity);
        Vec3 lookVec = entity.m_20154_();
        Vec3 spawnPos = entity.m_146892_().m_82520_(lookVec.f_82479_ * 2.0, lookVec.f_82480_ * 2.0, lookVec.f_82481_ * 2.0);
        EntityType entityType = (EntityType)TravelopticsEntities.EXTENDED_WITHER_HOWITZER.get();
        ExtendedWitherHowitzerEntity projectile = new ExtendedWitherHowitzerEntity((EntityType<? extends Wither_Howitzer_Entity>)entityType, level, entity, damage, damage / 2.0f);
        projectile.m_5602_((Entity)entity);
        projectile.m_6034_(spawnPos.f_82479_, spawnPos.f_82480_, spawnPos.f_82481_);
        projectile.m_6686_(lookVec.f_82479_, lookVec.f_82480_, lookVec.f_82481_, 1.8f, 1.0f);
        level.m_7967_((Entity)projectile);
    }
}

