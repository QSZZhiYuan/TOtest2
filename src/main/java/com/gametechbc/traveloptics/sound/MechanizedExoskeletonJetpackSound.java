/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.ItemStack
 */
package com.gametechbc.traveloptics.sound;

import com.gametechbc.traveloptics.init.TravelopticsSounds;
import com.gametechbc.traveloptics.item.armor.MechanizedExoskeletonArmorItem;
import com.gametechbc.traveloptics.sound.ArmorTickableSound;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class MechanizedExoskeletonJetpackSound
extends ArmorTickableSound {
    public MechanizedExoskeletonJetpackSound(LivingEntity user) {
        super(user, (SoundEvent)TravelopticsSounds.JETPACK_THRUSTER_LOOP.get());
    }

    @Override
    protected void tickVolume(ItemStack chestplate) {
        this.f_119573_ = 1.0f;
        this.f_119574_ = 1.0f;
    }

    @Override
    public boolean isValidArmor(ItemStack chestplate) {
        return !chestplate.m_41619_() && chestplate.m_41720_() instanceof MechanizedExoskeletonArmorItem;
    }

    public void stopSound() {
        this.m_119609_();
    }
}

