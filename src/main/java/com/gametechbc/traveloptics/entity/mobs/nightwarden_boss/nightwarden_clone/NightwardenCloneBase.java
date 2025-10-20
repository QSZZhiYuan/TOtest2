/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.HumanoidArm
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.TamableAnimal
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.nightwarden_clone;

import java.util.Collections;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class NightwardenCloneBase
extends LivingEntity {
    private UUID summonerUUID;
    private LivingEntity cachedSummoner;

    public NightwardenCloneBase(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.m_20340_(false);
    }

    public void setSummoner(@Nullable LivingEntity owner) {
        if (owner != null) {
            this.summonerUUID = owner.m_20148_();
            this.cachedSummoner = owner;
        }
    }

    public LivingEntity getSummoner() {
        if (this.cachedSummoner != null && this.cachedSummoner.m_6084_()) {
            return this.cachedSummoner;
        }
        if (this.summonerUUID != null && this.m_9236_() instanceof ServerLevel) {
            Entity entity = ((ServerLevel)this.m_9236_()).m_8791_(this.summonerUUID);
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity;
                this.cachedSummoner = livingEntity = (LivingEntity)entity;
            }
            return this.cachedSummoner;
        }
        return null;
    }

    public Iterable<ItemStack> m_6168_() {
        return Collections.singleton(ItemStack.f_41583_);
    }

    public ItemStack m_6844_(EquipmentSlot pSlot) {
        return ItemStack.f_41583_;
    }

    public void m_8061_(EquipmentSlot pSlot, ItemStack pStack) {
    }

    public HumanoidArm m_5737_() {
        return HumanoidArm.RIGHT;
    }

    public boolean m_6094_() {
        return false;
    }

    public boolean m_5829_() {
        return true;
    }

    public boolean m_6087_() {
        return true;
    }

    public boolean m_7301_(MobEffectInstance effect) {
        return false;
    }

    public boolean m_6052_() {
        return false;
    }

    protected boolean isAlly(LivingEntity owner, LivingEntity target) {
        return owner.m_5647_() != null && owner.m_5647_().m_83536_(target.m_5647_());
    }

    protected boolean isTamed(LivingEntity target) {
        if (target instanceof TamableAnimal) {
            TamableAnimal tamableAnimal = (TamableAnimal)target;
            return tamableAnimal.m_21824_();
        }
        return false;
    }

    public void m_7380_(CompoundTag tag) {
        super.m_7380_(tag);
        if (this.summonerUUID != null) {
            tag.m_128362_("Summoner", this.summonerUUID);
        }
    }

    public void m_7378_(CompoundTag tag) {
        super.m_7378_(tag);
        if (tag.m_128403_("Summoner")) {
            this.summonerUUID = tag.m_128342_("Summoner");
        }
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return LivingEntity.m_21183_().m_22268_(Attributes.f_22281_, 0.0).m_22268_(Attributes.f_22276_, 1.0).m_22268_(Attributes.f_22277_, 0.0).m_22268_(Attributes.f_22278_, 100.0).m_22268_(Attributes.f_22279_, 0.0);
    }
}

