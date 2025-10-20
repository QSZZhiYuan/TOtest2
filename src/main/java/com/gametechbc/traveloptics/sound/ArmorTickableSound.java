/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.sounds.AbstractTickableSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance$Attenuation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.ItemStack
 */
package com.gametechbc.traveloptics.sound;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public abstract class ArmorTickableSound
extends AbstractTickableSoundInstance {
    protected final LivingEntity user;

    public ArmorTickableSound(LivingEntity user, SoundEvent soundEvent) {
        super(soundEvent, SoundSource.PLAYERS, SoundInstance.m_235150_());
        this.user = user;
        this.f_119580_ = SoundInstance.Attenuation.LINEAR;
        this.f_119578_ = true;
        this.f_119575_ = user.m_20185_();
        this.f_119576_ = user.m_20186_();
        this.f_119577_ = user.m_20189_();
        this.f_119579_ = 0;
    }

    public boolean m_7767_() {
        return !this.user.m_20067_() && this.isValidArmor(this.user.m_6844_(EquipmentSlot.CHEST));
    }

    public void m_7788_() {
        ItemStack chestplate = this.user.m_6844_(EquipmentSlot.CHEST);
        if (this.user.m_6084_() && this.isValidArmor(chestplate)) {
            this.f_119575_ = this.user.m_20185_();
            this.f_119576_ = this.user.m_20186_();
            this.f_119577_ = this.user.m_20189_();
            this.tickVolume(chestplate);
        } else {
            this.m_119609_();
        }
    }

    protected abstract void tickVolume(ItemStack var1);

    public abstract boolean isValidArmor(ItemStack var1);

    public boolean m_7784_() {
        return true;
    }

    public boolean isSameEntity(LivingEntity user) {
        return this.user.m_6084_() && this.user.m_19879_() == user.m_19879_();
    }
}

