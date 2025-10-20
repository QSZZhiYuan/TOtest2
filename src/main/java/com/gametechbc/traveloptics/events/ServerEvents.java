/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.init.ModEffect
 *  io.redspace.ironsspellbooks.damage.ISSDamageTypes
 *  io.redspace.ironsspellbooks.registries.MobEffectRegistry
 *  net.minecraft.commands.arguments.EntityAnchorArgument$Anchor
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.attributes.Attribute
 *  net.minecraft.world.entity.ai.attributes.AttributeInstance
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.event.entity.living.LivingAttackEvent
 *  net.minecraftforge.event.entity.living.LivingEvent$LivingTickEvent
 *  net.minecraftforge.event.entity.living.LivingFallEvent
 *  net.minecraftforge.event.entity.living.LivingHurtEvent
 *  net.minecraftforge.event.entity.living.MobEffectEvent$Remove
 *  net.minecraftforge.event.entity.living.MobSpawnEvent$FinalizeSpawn
 *  net.minecraftforge.event.entity.player.CriticalHitEvent
 *  net.minecraftforge.eventbus.api.Event$Result
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber$Bus
 *  top.theillusivec4.curios.api.CuriosApi
 */
package com.gametechbc.traveloptics.events;

import com.gametechbc.traveloptics.api.init.TravelopticsAttributes;
import com.gametechbc.traveloptics.api.utils.TOArmorUtils;
import com.gametechbc.traveloptics.api.utils.TOCurioUtils;
import com.gametechbc.traveloptics.effects.FloodgateEffect;
import com.gametechbc.traveloptics.entity.extended_projectiles.extended_wave.ExtendedWaveEntity;
import com.gametechbc.traveloptics.init.TravelopticsEffects;
import com.gametechbc.traveloptics.init.TravelopticsItems;
import com.gametechbc.traveloptics.item.armor.DeeplingMageArmorItem;
import com.gametechbc.traveloptics.item.bossweapon.gauntletofextinction.GauntletOfExtinctionItem;
import com.gametechbc.traveloptics.item.bossweapon.gauntletofextinction.GauntletOfExtinctionLevelThreeItem;
import com.gametechbc.traveloptics.item.bossweapon.gauntletofextinction.GauntletOfExtinctionLevelTwoItem;
import com.gametechbc.traveloptics.util.TravelopticsTags;
import com.gametechbc.traveloptics.util.WetEffectReactionHelper;
import com.github.L_Ender.cataclysm.init.ModEffect;
import io.redspace.ironsspellbooks.damage.ISSDamageTypes;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

@Mod.EventBusSubscriber(modid="traveloptics", bus=Mod.EventBusSubscriber.Bus.FORGE)
public class ServerEvents {
    private static final Map<UUID, Long> dinosaurSpiritCooldownMap = new HashMap<UUID, Long>();
    private static final int DINOSAUR_SPIRIT_COOLDOWN_TICKS = 50;
    private static final int WET_CHAIN_LIGHTNING_COOLDOWN_TICKS = 30;
    private static final Map<UUID, Long> wetChainLightningCooldownMap = new HashMap<UUID, Long>();
    private static final int WET_BLIZZARD_COOLDOWN_TICKS = 160;
    private static final Map<UUID, Long> wetBlizzardCooldownMap = new HashMap<UUID, Long>();

    @SubscribeEvent
    public static void handleResistanceAttributesOnSpawn(MobSpawnEvent.FinalizeSpawn event) {
        Mob mob = event.getEntity();
        if (mob.m_6095_().m_204039_(TravelopticsTags.ELEMENT_FIRE)) {
            ServerEvents.setIfNonNull((LivingEntity)mob, (Attribute)TravelopticsAttributes.AQUA_MAGIC_RESIST.get(), 0.5);
        }
    }

    private static void setIfNonNull(LivingEntity mob, Attribute attribute, double value) {
        AttributeInstance instance = mob.m_21204_().m_22146_(attribute);
        if (instance != null) {
            instance.m_22100_(value);
        }
    }

    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.m_21023_((MobEffect)TravelopticsEffects.CRIMSON_DESCEND.get())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onCriticalHit(CriticalHitEvent event) {
        Entity entity;
        ItemStack weapon = event.getEntity().m_21205_();
        if (!weapon.m_41619_() && (entity = event.getTarget()) instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity)entity;
            Set<Item> validWeapons = Set.of((Item)TravelopticsItems.THE_OBLITERATOR.get(), (Item)TravelopticsItems.THE_OBLITERATOR_LEVEL_ONE.get(), (Item)TravelopticsItems.THE_OBLITERATOR_LEVEL_TWO.get(), (Item)TravelopticsItems.THE_OBLITERATOR_LEVEL_THREE.get());
            if (validWeapons.contains(weapon.m_41720_())) {
                if (livingEntity.m_21023_((MobEffect)ModEffect.EFFECTABYSSAL_CURSE.get())) {
                    event.setResult(Event.Result.ALLOW);
                }
                event.setDamageModifier(2.0f);
            }
        }
    }

    @SubscribeEvent
    public static void offFieldDinosaurSpirit(LivingHurtEvent event) {
        // REMOVED: DinosaurSpirit functionality (Alex's Caves dependency)
        // This feature required ACEntityRegistry.DINOSAUR_SPIRIT which is no longer available
        // Players with Gauntlet of Extinction Level 2/3 will no longer spawn Tremorsaurus spirits
    }

    @SubscribeEvent
    public static void applyClimbOnCurio(LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        boolean hasClimbingCurio = CuriosApi.getCuriosInventory((LivingEntity)livingEntity).map(curios -> !curios.findCurios(item -> item != null && item.m_150930_((Item)TravelopticsItems.SIGIL_OF_THE_SPIDER_SORCERER.get())).isEmpty()).orElse(false);
        if (hasClimbingCurio && livingEntity.m_21023_((MobEffect)MobEffectRegistry.SPIDER_ASPECT.get())) {
            ServerEvents.doWallClimb(livingEntity);
        }
    }

    private static void doWallClimb(LivingEntity entity) {
        Vec3 motion = entity.m_20184_();
        if (entity.f_19862_ && !entity.m_20096_()) {
            entity.m_20334_(motion.f_82479_, 0.2, motion.f_82481_);
            entity.f_19789_ = 0.0f;
        }
    }

    @SubscribeEvent
    public static void applyPoisonOnCurio(LivingAttackEvent event) {
        LivingEntity targetEntity;
        LivingEntity attacker;
        Entity entity = event.getSource().m_7639_();
        if (entity instanceof LivingEntity && (attacker = (LivingEntity)entity).m_21023_((MobEffect)MobEffectRegistry.SPIDER_ASPECT.get()) && CuriosApi.getCuriosInventory((LivingEntity)attacker).map(curios -> !curios.findCurios(item -> item != null && item.m_150930_((Item)TravelopticsItems.SIGIL_OF_THE_SPIDER_SORCERER.get())).isEmpty()).orElse(false).booleanValue() && (targetEntity = event.getEntity()) instanceof LivingEntity) {
            LivingEntity target = targetEntity;
            if ((double)attacker.m_217043_().m_188501_() < 0.5) {
                target.m_7292_(new MobEffectInstance(MobEffects.f_19614_, 60, 0));
            }
        }
    }

    @SubscribeEvent
    public static void preventEffectRemoval(MobEffectEvent.Remove event) {
        MobEffectInstance effectInstance = event.getEffectInstance();
        if (effectInstance != null && effectInstance.m_19544_() == TravelopticsEffects.FROZEN_SIGHT.get()) {
            event.setCanceled(true);
        }
        if (effectInstance != null && effectInstance.m_19544_() == TravelopticsEffects.OVERLOADED_EFFECT.get()) {
            event.setCanceled(true);
        }
        if (effectInstance != null && effectInstance.m_19544_() == TravelopticsEffects.CASTING.get()) {
            event.setCanceled(true);
        }
        if (effectInstance != null && effectInstance.m_19544_() == TravelopticsEffects.AERIAL_COLLAPSE_HELPER.get()) {
            event.setCanceled(true);
        }
        if (effectInstance != null && effectInstance.m_19544_() == TravelopticsEffects.THIRD_PERSON_SWITCH.get()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onFloodgateEffect(LivingHurtEvent event) {
        Entity attacker;
        Player player;
        LivingEntity entity = event.getEntity();
        Level level = entity.m_9236_();
        if (entity.m_21023_((MobEffect)TravelopticsEffects.FLOODGATE_EFFECT.get())) {
            float originalDamage = event.getAmount();
            float modifiedDamage = FloodgateEffect.applyDamageCap(entity, originalDamage);
            event.setAmount(modifiedDamage);
            if (originalDamage > modifiedDamage && !level.f_46443_) {
                int waveCount = 6;
                double radius = 2.0;
                int lifespan = 10;
                int amplifier = entity.m_21124_((MobEffect)TravelopticsEffects.FLOODGATE_EFFECT.get()).m_19564_();
                Vec3 entityPos = entity.m_20182_();
                for (int i = 0; i < waveCount; ++i) {
                    double angle = Math.PI * 2 * (double)i / (double)waveCount;
                    double dx = Math.cos(angle) * radius;
                    double dz = Math.sin(angle) * radius;
                    Vec3 wavePos = entityPos.m_82520_(dx, 0.0, dz);
                    ExtendedWaveEntity waveEntity = new ExtendedWaveEntity(level, entity);
                    waveEntity.m_6034_(wavePos.f_82479_, wavePos.f_82480_, wavePos.f_82481_);
                    waveEntity.setLifespan(lifespan);
                    waveEntity.setDamageAmount(amplifier);
                    float rotation = (float)Math.toDegrees(angle) - 90.0f;
                    waveEntity.m_146922_(rotation);
                    level.m_7967_((Entity)waveEntity);
                }
            }
        }
        if (entity instanceof Player && TOArmorUtils.isWearingFullSet(player = (Player)entity, DeeplingMageArmorItem.class) && event.getSource().m_7639_() != null && event.getSource().m_7639_().m_6095_() == ACEntityRegistry.HULLBREAKER.get()) {
            event.setAmount(event.getAmount() * 0.7f);
        }
        if (entity.m_21023_((MobEffect)TravelopticsEffects.WET.get()) && (attacker = event.getSource().m_7639_()) instanceof LivingEntity) {
            LivingEntity caster = (LivingEntity)attacker;
            long currentTick = entity.m_9236_().m_46467_();
            UUID casterUUID = caster.m_20148_();
            if (event.getSource().m_276093_(ISSDamageTypes.LIGHTNING_MAGIC) && TOCurioUtils.getWearingCurio(caster, (Item)TravelopticsItems.HYDROCHARGE_BRACELET.get()) && (!wetChainLightningCooldownMap.containsKey(casterUUID) || currentTick - wetChainLightningCooldownMap.get(casterUUID) >= 30L)) {
                wetChainLightningCooldownMap.put(casterUUID, currentTick);
                WetEffectReactionHelper.triggerWetChainLightning(level, caster, entity);
            }
            if (event.getSource().m_276093_(ISSDamageTypes.ICE_MAGIC) && TOCurioUtils.getWearingCurio(caster, (Item)TravelopticsItems.CRYOSTORM_BRACELET.get()) && (!wetBlizzardCooldownMap.containsKey(casterUUID) || currentTick - wetBlizzardCooldownMap.get(casterUUID) >= 160L)) {
                wetBlizzardCooldownMap.put(casterUUID, currentTick);
                WetEffectReactionHelper.triggerWetBlizzard(level, caster, entity);
            }
        }
    }
}

