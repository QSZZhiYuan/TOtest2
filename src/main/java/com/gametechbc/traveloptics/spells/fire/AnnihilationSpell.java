/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.api.config.DefaultConfig
 *  io.redspace.ironsspellbooks.api.magic.MagicData
 *  io.redspace.ironsspellbooks.api.registry.SchoolRegistry
 *  io.redspace.ironsspellbooks.api.spells.AutoSpellConfig
 *  io.redspace.ironsspellbooks.api.spells.CastSource
 *  io.redspace.ironsspellbooks.api.spells.CastType
 *  io.redspace.ironsspellbooks.api.spells.SpellAnimations
 *  io.redspace.ironsspellbooks.api.spells.SpellRarity
 *  io.redspace.ironsspellbooks.api.util.AnimationHolder
 *  io.redspace.ironsspellbooks.api.util.Utils
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  io.redspace.ironsspellbooks.capabilities.magic.RecastInstance
 *  io.redspace.ironsspellbooks.damage.DamageSources
 *  io.redspace.ironsspellbooks.entity.spells.eldritch_blast.EldritchBlastVisualEntity
 *  io.redspace.ironsspellbooks.util.ParticleHelper
 *  net.minecraft.ChatFormatting
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.Level$ExplosionInteraction
 *  net.minecraft.world.phys.EntityHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  org.jetbrains.annotations.Nullable
 */
package com.gametechbc.traveloptics.spells.fire;

import com.gametechbc.traveloptics.api.spells.AbstractUniqueSpell;
import com.gametechbc.traveloptics.config.SpellsConfig;
import com.gametechbc.traveloptics.init.TravelopticsItems;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AutoSpellConfig;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellAnimations;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.capabilities.magic.RecastInstance;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.eldritch_blast.EldritchBlastVisualEntity;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

@AutoSpellConfig
public class AnnihilationSpell
extends AbstractUniqueSpell {
    private final ResourceLocation spellId = new ResourceLocation("traveloptics", "annihilation");
    private final DefaultConfig defaultConfig = new DefaultConfig().setMinRarity(SpellRarity.LEGENDARY).setSchoolResource(SchoolRegistry.FIRE_RESOURCE).setMaxLevel(3).setCooldownSeconds(90.0).build();

    public AnnihilationSpell() {
        this.baseSpellPower = 1;
        this.spellPowerPerLevel = 1;
        this.manaCostPerLevel = 100;
        this.castTime = 88;
        this.baseManaCost = 400;
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
        return SpellAnimations.CHARGE_ANIMATION;
    }

    public AnimationHolder getCastFinishAnimation() {
        return SpellAnimations.FINISH_ANIMATION;
    }

    public Optional<SoundEvent> getCastStartSound() {
        return Optional.of((SoundEvent)TravelopticsSounds.ANNIHILATION_CHARGE.get());
    }

    public int getRecastCount(int spellLevel, @Nullable LivingEntity entity) {
        List<Item> validItems;
        if (((Boolean)SpellsConfig.titanlordScepterGrantRecast.get()).booleanValue() && entity != null && ((validItems = List.of((Item)TravelopticsItems.TITANLORD_SCEPTER.get(), (Item)TravelopticsItems.TITANLORD_SCEPTER_RETRO.get(), (Item)TravelopticsItems.TITANLORD_SCEPTER_TECTONIC.get())).contains(entity.m_21205_().m_41720_()) || validItems.contains(entity.m_21206_().m_41720_()))) {
            return 2;
        }
        return 0;
    }

    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        if (!playerMagicData.getPlayerRecasts().hasRecastForSpell(this.getSpellId())) {
            playerMagicData.getPlayerRecasts().addRecast(new RecastInstance(this.getSpellId(), spellLevel, this.getRecastCount(spellLevel, entity), 400, castSource, null), playerMagicData);
        }
        float bbInflation = 0.5f;
        HitResult hitResult = Utils.raycastForEntity((Level)level, (Entity)entity, (float)this.getRange(spellLevel), (boolean)true, (float)bbInflation);
        if (hitResult.m_6662_() == HitResult.Type.ENTITY) {
            Entity target = ((EntityHitResult)hitResult).m_82443_();
            if (target instanceof LivingEntity) {
                LivingEntity livingTarget = (LivingEntity)target;
                float damage = this.getDamage(spellLevel, entity);
                DamageSources.applyDamage((Entity)target, (float)damage, (DamageSource)this.getDamageSource((Entity)entity));
                MagicManager.spawnParticles((Level)level, (ParticleOptions)ParticleHelper.UNSTABLE_ENDER, (double)hitResult.m_82450_().f_82479_, (double)hitResult.m_82450_().f_82480_, (double)hitResult.m_82450_().f_82481_, (int)50, (double)0.0, (double)0.0, (double)0.0, (double)0.3, (boolean)false);
                MagicManager.spawnParticles((Level)level, (ParticleOptions)ParticleTypes.f_123758_, (double)target.m_20185_(), (double)target.m_20186_(), (double)target.m_20189_(), (int)30, (double)2.0, (double)2.0, (double)2.0, (double)0.3, (boolean)true);
                // REMOVED AC dependency: irradiated effect - using vanilla poison instead
                MobEffect irradiatedEffect = (MobEffect)BuiltInRegistries.f_256974_.m_7745_(new ResourceLocation("minecraft:poison"));
                if (irradiatedEffect != null) {
                    livingTarget.m_7292_(new MobEffectInstance(irradiatedEffect, this.getEffectDuration(spellLevel), 0));
                }
                level.m_7967_((Entity)new EldritchBlastVisualEntity(level, entity.m_146892_().m_82492_(0.0, 0.75, 0.0), hitResult.m_82450_(), entity));
                this.applyAoEDamageAndExplosion(level, spellLevel, entity, livingTarget, damage, irradiatedEffect);
                if (entity instanceof Player) {
                    Player player = (Player)entity;
                    player.m_5661_((Component)Component.m_237113_((String)("Annihilation triggered: Target " + target.m_7755_().getString())), true);
                }
            }
        } else if (entity instanceof Player) {
            Player player = (Player)entity;
            player.m_5661_((Component)Component.m_237115_((String)"spell.traveloptics.annihilation.miss_warning").m_130940_(ChatFormatting.RED), true);
        }
        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private void applyAoEDamageAndExplosion(Level level, int spellLevel, LivingEntity caster, LivingEntity target, float damage, @Nullable MobEffect irradiatedEffect) {
        float radius = this.getAoERadius(spellLevel);
        List entities = level.m_45976_(LivingEntity.class, target.m_20191_().m_82400_((double)radius));
        for (LivingEntity nearbyEntity : entities) {
            if (nearbyEntity == target || DamageSources.isFriendlyFireBetween((Entity)nearbyEntity, (Entity)caster)) continue;
            if (irradiatedEffect != null) {
                nearbyEntity.m_7292_(new MobEffectInstance(irradiatedEffect, this.getEffectDuration(spellLevel), 0));
            }
            DamageSources.applyDamage((Entity)nearbyEntity, (float)damage, (DamageSource)this.getDamageSource((Entity)caster));
        }
        boolean shouldExplode = (Boolean)SpellsConfig.annihilationCauseExplode.get();
        boolean isCrouching = caster.m_6047_();
        if (shouldExplode && isCrouching) {
            float explosionPower = this.getExplosionPower(spellLevel);
            boolean causesFire = (Boolean)SpellsConfig.annihilationCauseFire.get();
            level.m_255391_((Entity)caster, target.m_20185_(), target.m_20186_(), target.m_20189_(), explosionPower, causesFire, Level.ExplosionInteraction.BLOCK);
        }
    }

    private float getExplosionPower(int spellLevel) {
        return 2.5f + (float)spellLevel * 3.0f;
    }

    public float getRange(int level) {
        return 25 + level * 5;
    }

    private int getEffectDuration(int spellLevel) {
        return spellLevel * 100;
    }

    private float getDamage(int spellLevel, LivingEntity caster) {
        return 30.0f + this.getSpellPower(spellLevel, (Entity)caster) * 30.0f;
    }

    private float getAoERadius(int spellLevel) {
        return 6 + spellLevel * 2;
    }

    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        float damage = this.getDamage(spellLevel, caster);
        float duration = this.getEffectDuration(spellLevel);
        return List.of(Component.m_237110_((String)"ui.traveloptics.aoe_damage", (Object[])new Object[]{Utils.stringTruncation((double)damage, (int)2)}), Component.m_237110_((String)"ui.traveloptics.aoe_radius", (Object[])new Object[]{Float.valueOf(this.getAoERadius(spellLevel))}), Component.m_237110_((String)"ui.traveloptics.irradiated", (Object[])new Object[]{Utils.timeFromTicks((float)duration, (int)2)}), Component.m_237110_((String)"ui.traveloptics.range", (Object[])new Object[]{Float.valueOf(this.getRange(spellLevel))}), Component.m_237115_((String)"ui.traveloptics.annihilation_note"), Component.m_237113_((String)"\u00a79T.O Magic 'n Extras"));
    }

    public boolean stopSoundOnCancel() {
        return true;
    }
}

