/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.CameraType
 *  net.minecraft.client.Minecraft
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectCategory
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.fml.loading.FMLEnvironment
 */
package com.gametechbc.traveloptics.effects;

import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class ThirdPersonSwitchEffect
extends MobEffect {
    public ThirdPersonSwitchEffect() {
        super(MobEffectCategory.NEUTRAL, 0);
    }

    public void m_6742_(LivingEntity entity, int amplifier) {
        if (FMLEnvironment.dist == Dist.CLIENT && entity.m_9236_().m_5776_() && entity == Minecraft.m_91087_().f_91074_) {
            int duration = entity.m_21124_((MobEffect)this).m_19557_();
            if (duration > 2) {
                Minecraft.m_91087_().f_91066_.m_92157_(CameraType.THIRD_PERSON_BACK);
            }
            if (duration <= 2) {
                Minecraft.m_91087_().f_91066_.m_92157_(CameraType.FIRST_PERSON);
            }
        }
        super.m_6742_(entity, amplifier);
    }

    public boolean m_6584_(int duration, int amplifier) {
        return true;
    }
}

