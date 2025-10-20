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
 *  io.redspace.ironsspellbooks.api.spells.SpellRarity
 *  io.redspace.ironsspellbooks.api.util.AnimationHolder
 *  io.redspace.ironsspellbooks.api.util.Utils
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  io.redspace.ironsspellbooks.capabilities.magic.RecastInstance
 *  io.redspace.ironsspellbooks.damage.DamageSources
 *  io.redspace.ironsspellbooks.registries.SoundRegistry
 *  io.redspace.ironsspellbooks.util.ParticleHelper
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.projectile.Projectile
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.EntityHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.Nullable
 */
package com.gametechbc.traveloptics.spells.ender;

import com.gametechbc.traveloptics.spells.TravelopticsSpellAnimations;
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
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

@AutoSpellConfig
public class VoidEruptionSpell
extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation("traveloptics", "void_eruption");
    private final DefaultConfig defaultConfig = new DefaultConfig().setMinRarity(SpellRarity.EPIC).setSchoolResource(SchoolRegistry.ENDER_RESOURCE).setMaxLevel(3).setCooldownSeconds(25.0).build();

    public VoidEruptionSpell() {
        this.manaCostPerLevel = 55;
        this.baseSpellPower = 1;
        this.spellPowerPerLevel = 1;
        this.castTime = 8;
        this.baseManaCost = 70;
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
        return Optional.of((SoundEvent)SoundRegistry.ELDRITCH_BLAST.get());
    }

    public AnimationHolder getCastStartAnimation() {
        return TravelopticsSpellAnimations.SHOOT_WINDUP;
    }

    public AnimationHolder getCastFinishAnimation() {
        return TravelopticsSpellAnimations.SHOOT;
    }

    public int getRecastCount(int spellLevel, @Nullable LivingEntity entity) {
        return spellLevel;
    }

    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.m_237110_((String)"ui.traveloptics.aoe_damage", (Object[])new Object[]{Utils.stringTruncation((double)this.getDamage(spellLevel, caster), (int)2)}), Component.m_237110_((String)"ui.traveloptics.shot_count", (Object[])new Object[]{this.getRecastCount(spellLevel, caster)}), Component.m_237110_((String)"ui.traveloptics.falloff_distance", (Object[])new Object[]{Utils.stringTruncation((double)VoidEruptionSpell.getRange(spellLevel, caster), (int)1)}), Component.m_237113_((String)"\u00a79T.O Magic 'n Extras"));
    }

    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        Entity target;
        if (!playerMagicData.getPlayerRecasts().hasRecastForSpell(this.getSpellId())) {
            playerMagicData.getPlayerRecasts().addRecast(new RecastInstance(this.getSpellId(), spellLevel, this.getRecastCount(spellLevel, entity), 150, castSource, null), playerMagicData);
        }
        HitResult hitResult = Utils.raycastForEntity((Level)level, (Entity)entity, (float)VoidEruptionSpell.getRange(spellLevel, entity), (boolean)true, (float)0.15f);
        this.spawnVoidHowitzerProjectile(level, entity);
        if (hitResult.m_6662_() == HitResult.Type.ENTITY && (target = ((EntityHitResult)hitResult).m_82443_()) instanceof LivingEntity) {
            DamageSources.applyDamage((Entity)target, (float)this.getDamage(spellLevel, entity), (DamageSource)this.getDamageSource((Entity)entity));
            AABB areaOfEffect = new AABB(target.m_20183_()).m_82400_(5.0);
            List nearbyEntities = level.m_6443_(LivingEntity.class, areaOfEffect, e -> e != entity && e != target);
            for (LivingEntity nearbyEntity : nearbyEntities) {
                DamageSources.applyDamage((Entity)nearbyEntity, (float)this.getDamage(spellLevel, entity), (DamageSource)this.getDamageSource((Entity)entity));
            }
        }
        MagicManager.spawnParticles((Level)level, (ParticleOptions)ParticleHelper.UNSTABLE_ENDER, (double)hitResult.m_82450_().f_82479_, (double)hitResult.m_82450_().f_82480_, (double)hitResult.m_82450_().f_82481_, (int)50, (double)0.0, (double)0.0, (double)0.0, (double)0.3, (boolean)false);
        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private void spawnVoidHowitzerProjectile(Level level, LivingEntity entity) {
        Projectile projectile;
        Vec3 lookVec = entity.m_20154_();
        Vec3 spawnPos = entity.m_146892_().m_82520_(lookVec.f_82479_ * 2.0, lookVec.f_82480_ * 2.0, lookVec.f_82481_ * 2.0);
        ResourceLocation projectileType = new ResourceLocation("cataclysm:void_howitzer");
        EntityType entityType = (EntityType)BuiltInRegistries.f_256780_.m_7745_(projectileType);
        if (entityType != null && (projectile = (Projectile)entityType.m_20615_(level)) != null) {
            projectile.m_5602_((Entity)entity);
            projectile.m_6034_(spawnPos.f_82479_, spawnPos.f_82480_, spawnPos.f_82481_);
            projectile.m_6686_(lookVec.f_82479_, lookVec.f_82480_, lookVec.f_82481_, 1.8f, 1.0f);
            level.m_7967_((Entity)projectile);
        }
    }

    public static float getRange(int level, LivingEntity caster) {
        return 20.0f;
    }

    private float getDamage(int spellLevel, LivingEntity caster) {
        return 13.0f + this.getSpellPower(spellLevel, (Entity)caster) * 3.0f;
    }
}

