/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.entity.projectile.Phantom_Arrow_Entity
 *  net.minecraft.core.Holder
 *  net.minecraft.core.Holder$Reference
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.damagesource.DamageType
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.projectile.AbstractArrow$Pickup
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.enchantment.EnchantmentHelper
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.EntityHitResult
 *  net.minecraftforge.network.PlayMessages$SpawnEntity
 */
package com.gametechbc.traveloptics.entity.extended_projectiles;

import com.gametechbc.traveloptics.init.TravelopticsEntities;
import com.gametechbc.traveloptics.util.TravelopticsDamageTypes;
import com.github.L_Ender.cataclysm.entity.projectile.Phantom_Arrow_Entity;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.PlayMessages;

public class ExtendedPhantomArrowEntity
extends Phantom_Arrow_Entity {
    private int customLife = 0;
    private Holder<DamageType> customDamageType;
    private float customDamage = -1.0f;

    public ExtendedPhantomArrowEntity(EntityType<? extends ExtendedPhantomArrowEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    public ExtendedPhantomArrowEntity(Level worldIn, LivingEntity shooter) {
        super((EntityType)TravelopticsEntities.EXTENDED_PHANTOM_ARROW.get(), shooter.m_20185_(), shooter.m_20188_() - (double)0.1f, shooter.m_20189_(), worldIn);
        this.m_5602_((Entity)shooter);
        this.f_36705_ = AbstractArrow.Pickup.DISALLOWED;
    }

    public ExtendedPhantomArrowEntity(Level worldIn, LivingEntity shooter, LivingEntity finalTarget) {
        super((EntityType)TravelopticsEntities.EXTENDED_PHANTOM_ARROW.get(), shooter.m_20185_(), shooter.m_20188_() - (double)0.1f, shooter.m_20189_(), worldIn);
        this.m_5602_((Entity)shooter);
        this.f_36705_ = AbstractArrow.Pickup.DISALLOWED;
    }

    public ExtendedPhantomArrowEntity(PlayMessages.SpawnEntity spawnEntity, Level level) {
        super((EntityType)TravelopticsEntities.EXTENDED_PHANTOM_ARROW.get(), level);
    }

    public void setCustomDamageType(Holder<DamageType> damageType) {
        this.customDamageType = damageType;
    }

    public void setCustomDamage(float damage) {
        this.customDamage = damage;
    }

    public float getCustomDamage() {
        return this.customDamage;
    }

    protected void m_5790_(EntityHitResult result) {
        boolean isEnderman;
        Entity hitEntity = result.m_82443_();
        Entity owner = this.m_19749_();
        Holder.Reference damageTypeHolder = this.customDamageType != null ? this.customDamageType : this.m_9236_().m_9598_().m_175515_(Registries.f_268580_).m_246971_(TravelopticsDamageTypes.NULLFLARE_FIRE);
        DamageSource damageSource = new DamageSource((Holder)damageTypeHolder);
        float damageAmount = this.customDamage >= 0.0f ? this.customDamage : (float)this.m_36789_();
        boolean bl = isEnderman = hitEntity.m_6095_() == EntityType.f_20566_;
        if (this.m_6060_() && !isEnderman) {
            hitEntity.m_20254_(5);
        }
        if (hitEntity.m_6469_(damageSource, damageAmount)) {
            if (isEnderman) {
                return;
            }
            if (hitEntity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)hitEntity;
                if (owner instanceof LivingEntity) {
                    EnchantmentHelper.m_44823_((LivingEntity)livingEntity, (Entity)owner);
                    EnchantmentHelper.m_44896_((LivingEntity)((LivingEntity)owner), (Entity)livingEntity);
                }
                this.m_7761_(livingEntity);
            }
        } else {
            this.m_20256_(this.m_20184_().m_82490_(-0.1));
            this.m_146922_(this.m_146908_() + 180.0f);
            this.f_19859_ += 180.0f;
            if (!this.m_9236_().f_46443_ && this.m_20184_().m_82556_() < 1.0E-7) {
                this.m_146870_();
            }
        }
        this.m_5496_(SoundEvents.f_11685_, 1.0f, 1.2f / (this.f_19796_.m_188501_() * 0.2f + 0.9f));
    }

    protected ItemStack m_7941_() {
        return ItemStack.f_41583_;
    }

    protected void m_6901_() {
        ++this.customLife;
        if (this.customLife >= 200) {
            this.m_146870_();
        }
    }
}

