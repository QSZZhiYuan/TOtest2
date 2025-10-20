/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.particle.Particle
 *  net.minecraft.client.particle.ParticleProvider
 *  net.minecraft.client.particle.PortalParticle
 *  net.minecraft.client.particle.SpriteSet
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package com.gametechbc.traveloptics.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.PortalParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value=Dist.CLIENT)
public class GlowingReversePortalParticle
extends PortalParticle {
    GlowingReversePortalParticle(ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
        super(level, x, y, z, dx, dy, dz);
        this.f_107663_ *= 1.5f;
        this.f_107225_ = (int)(Math.random() * 2.0) + 60;
    }

    public float m_5902_(float partialTicks) {
        float f = 1.0f - ((float)this.f_107224_ + partialTicks) / ((float)this.f_107225_ * 1.5f);
        return this.f_107663_ * f;
    }

    public void m_5989_() {
        this.f_107209_ = this.f_107212_;
        this.f_107210_ = this.f_107213_;
        this.f_107211_ = this.f_107214_;
        if (this.f_107224_++ >= this.f_107225_) {
            this.m_107274_();
        } else {
            float progress = (float)this.f_107224_ / (float)this.f_107225_;
            this.f_107212_ += this.f_107215_ * (double)progress;
            this.f_107213_ += this.f_107216_ * (double)progress;
            this.f_107214_ += this.f_107217_ * (double)progress;
            this.m_107264_(this.f_107212_, this.f_107213_, this.f_107214_);
        }
    }

    public int m_6355_(float partialTick) {
        return 0xF000F0;
    }

    @OnlyIn(value=Dist.CLIENT)
    public static class GlowingReversePortalProvider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public GlowingReversePortalProvider(SpriteSet sprite) {
            this.sprite = sprite;
        }

        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
            GlowingReversePortalParticle particle = new GlowingReversePortalParticle(level, x, y, z, dx, dy, dz);
            particle.m_108335_(this.sprite);
            return particle;
        }
    }
}

