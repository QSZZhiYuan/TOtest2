/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.sounds.AbstractTickableSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance$Attenuation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 */
package com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.misc;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public class NightwardenSoundInstance
extends AbstractTickableSoundInstance {
    private boolean starting = false;
    private boolean triggerEnd = false;
    private int transitionTicks;
    private static final int START_TRANSITION_TIME = 40;
    private static final int END_TRANSITION_TIME = 40;
    private static final float FADE_SPEED = 0.05f;
    private float targetVolume = 1.0f;

    protected NightwardenSoundInstance(SoundEvent soundEvent, SoundSource source, boolean loop) {
        super(soundEvent, source, SoundInstance.m_235150_());
        this.f_119580_ = SoundInstance.Attenuation.NONE;
        this.f_119578_ = loop;
        this.f_119579_ = 0;
        this.f_119573_ = 1.0f;
    }

    public void m_7788_() {
        if (this.transitionTicks > 0) {
            --this.transitionTicks;
        }
        if (this.starting) {
            this.f_119573_ = 1.0f - (float)this.transitionTicks / 40.0f;
            if (this.transitionTicks == 0) {
                this.starting = false;
            }
        }
        if (this.triggerEnd) {
            this.f_119573_ = (float)this.transitionTicks / 40.0f;
            if (this.transitionTicks == 0) {
                this.m_119609_();
            }
        }
        if (!this.starting && !this.triggerEnd) {
            this.f_119573_ = Math.abs(this.f_119573_ - this.targetVolume) > 0.01f ? (this.f_119573_ += (this.targetVolume - this.f_119573_) * 0.05f) : this.targetVolume;
        }
    }

    public boolean m_7784_() {
        return true;
    }

    public void triggerStop() {
        this.triggerEnd = true;
        this.transitionTicks = (int)(40.0f * this.f_119573_);
    }

    public void triggerStart() {
        this.triggerEnd = false;
        this.transitionTicks = (int)(40.0f * this.f_119573_);
        this.starting = true;
    }

    public void setTargetVolume(float volume) {
        this.targetVolume = volume;
    }

    public float getTargetVolume() {
        return this.targetVolume;
    }
}

