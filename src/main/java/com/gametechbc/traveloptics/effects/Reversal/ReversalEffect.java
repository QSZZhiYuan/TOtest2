/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Holder
 *  net.minecraft.core.Holder$Reference
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectCategory
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.AttributeMap
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.projectile.Projectile
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.EntityHitResult
 *  net.minecraft.world.phys.Vec3
 *  top.theillusivec4.curios.api.CuriosApi
 */
package com.gametechbc.traveloptics.effects.Reversal;

import com.gametechbc.traveloptics.effects.Reversal.ReversalEffectHandler;
import com.gametechbc.traveloptics.entity.projectiles.reversal.ReversalEntity;
import com.gametechbc.traveloptics.init.TravelopticsEffects;
import com.gametechbc.traveloptics.init.TravelopticsItems;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import com.gametechbc.traveloptics.util.TravelopticsDamageTypes;
import java.util.function.Predicate;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.CuriosApi;

public class ReversalEffect
extends MobEffect {
    private float damageMultiplier = 1.0f;

    public ReversalEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFF0000);
    }

    public void m_6742_(LivingEntity entity, int amplifier) {
        if (!entity.m_9236_().f_46443_) {
            float storedDamage;
            int duration = entity.m_21124_((MobEffect)this).m_19557_();
            if (duration > 8) {
                entity.m_21195_((MobEffect)this);
                entity.m_7292_(new MobEffectInstance((MobEffect)this, 8, amplifier, false, true));
            }
            if (duration == 2 && (storedDamage = ReversalEffectHandler.getStoredDamage(entity)) > 0.0f) {
                this.performRaycastAndApplyDamage(entity, storedDamage * this.damageMultiplier);
                ReversalEffectHandler.clearStoredDamage(entity);
            }
        }
    }

    public void m_6386_(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
        Projectile lastProjectile;
        super.m_6386_(entity, attributeMap, amplifier);
        if (!entity.m_9236_().f_46443_ && (lastProjectile = ReversalEffectHandler.getLastProjectileHit(entity)) != null) {
            this.reflectProjectile(entity, lastProjectile);
            ReversalEffectHandler.clearLastProjectileHit(entity);
            if (CuriosApi.getCuriosHelper().findEquippedCurio((Item)TravelopticsItems.NIGHTSTALKERS_BAND.get(), entity).isPresent()) {
                entity.m_7292_(new MobEffectInstance((MobEffect)TravelopticsEffects.ASSASSIN.get(), 100, 6));
            }
            this.playSound((Entity)entity, (SoundEvent)TravelopticsSounds.REVERSAL_TRIGGER.get());
        }
    }

    private void reflectProjectile(LivingEntity entity, Projectile originalProjectile) {
        Level level = entity.m_9236_();
        Vec3 lookDirection = entity.m_20154_();
        Entity newProjectile = originalProjectile.m_6095_().m_20615_(level);
        if (newProjectile instanceof Projectile) {
            Projectile projectile = (Projectile)newProjectile;
            projectile.m_146884_(entity.m_20299_(1.0f));
            projectile.m_6686_(lookDirection.f_82479_, lookDirection.f_82480_, lookDirection.f_82481_, 1.5f, 0.0f);
            level.m_7967_((Entity)projectile);
            entity.m_21220_().stream().map(MobEffectInstance::m_19544_).filter(effect -> effect.m_19483_() == MobEffectCategory.HARMFUL).forEach(arg_0 -> ((LivingEntity)entity).m_21195_(arg_0));
            ReversalEntity reversal = new ReversalEntity(level, false);
            reversal.m_20219_(entity.m_20182_());
            reversal.m_146922_(entity.m_146908_());
            reversal.m_146926_(entity.m_146909_());
            level.m_7967_((Entity)reversal);
            if (entity instanceof Player) {
                Player player = (Player)entity;
                player.m_5661_((Component)Component.m_237115_((String)"effect.traveloptics.reversal.projectile.feedback"), true);
            }
        }
    }

    private void performRaycastAndApplyDamage(LivingEntity entity, float adjustedDamage) {
        Entity entity2;
        Vec3 start = entity.m_20299_(1.0f);
        Vec3 end = start.m_82549_(entity.m_20154_().m_82490_(5.0));
        Level level = entity.m_9236_();
        EntityHitResult entityHitResult = this.raycastForEntities(level, entity, start, end, targetEntity -> targetEntity != entity && targetEntity.m_6084_());
        if (entityHitResult != null && (entity2 = entityHitResult.m_82443_()) instanceof LivingEntity) {
            LivingEntity targetEntity2 = (LivingEntity)entity2;
            Holder.Reference damageTypeHolder = level.m_9598_().m_175515_(Registries.f_268580_).m_246971_(TravelopticsDamageTypes.REVERSAL);
            DamageSource damageSource = new DamageSource((Holder)damageTypeHolder);
            targetEntity2.m_6469_(damageSource, adjustedDamage);
            entity.m_21220_().stream().map(MobEffectInstance::m_19544_).filter(effect -> effect.m_19483_() == MobEffectCategory.HARMFUL).forEach(arg_0 -> ((LivingEntity)entity).m_21195_(arg_0));
            Vec3 targetPosition = targetEntity2.m_20182_().m_82520_(0.0, 0.8, 0.0);
            ReversalEntity reversal = new ReversalEntity(level, false);
            reversal.m_20219_(targetPosition);
            reversal.m_146922_(entity.m_146908_());
            reversal.m_146926_(entity.m_146909_());
            level.m_7967_((Entity)reversal);
            if (entity instanceof Player) {
                Player player = (Player)entity;
                player.m_5661_((Component)Component.m_237113_((String)("Reversed " + String.format("%.0f", Float.valueOf(adjustedDamage)) + " damage!")), true);
            }
            this.playSound((Entity)entity, (SoundEvent)TravelopticsSounds.REVERSAL_TRIGGER.get());
        } else {
            System.out.println("Reversal Missed!");
        }
    }

    private EntityHitResult raycastForEntities(Level level, LivingEntity entity, Vec3 start, Vec3 end, Predicate<LivingEntity> predicate) {
        double distance = 5.0;
        Vec3 direction = end.m_82546_(start).m_82541_();
        Vec3 rayEnd = start.m_82549_(direction.m_82490_(distance));
        double raycastInflation = 4.0;
        EntityHitResult entityHitResult = null;
        double closestDistance = distance;
        for (LivingEntity target : level.m_45976_(LivingEntity.class, entity.m_20191_().m_82369_(direction.m_82490_(distance)).m_82400_(raycastInflation))) {
            Vec3 entityPos;
            double distanceToEntity;
            if (!predicate.test(target) || !((distanceToEntity = start.m_82554_(entityPos = target.m_20182_())) < closestDistance)) continue;
            closestDistance = distanceToEntity;
            entityHitResult = new EntityHitResult((Entity)target);
        }
        return entityHitResult;
    }

    private void playSound(Entity entity, SoundEvent sound) {
        entity.m_9236_().m_6263_(null, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), sound, SoundSource.NEUTRAL, 1.0f, 1.0f);
    }

    public boolean m_6584_(int duration, int amplifier) {
        return true;
    }

    public void setDamageMultiplier(float multiplier) {
        this.damageMultiplier = multiplier;
    }
}

