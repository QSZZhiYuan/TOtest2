/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.particle.Particle
 *  net.minecraft.client.particle.ParticleProvider
 *  net.minecraft.client.particle.ParticleRenderType
 *  net.minecraft.client.particle.SpriteSet
 *  net.minecraft.client.particle.TextureSheetParticle
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package com.gametechbc.traveloptics.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AbyssSpikeParticle
extends TextureSheetParticle {
    private final SpriteSet sprites;
    private final boolean mirrored;

    public AbyssSpikeParticle(ClientLevel level, double xCoord, double yCoord, double zCoord, SpriteSet spriteSet, double xd, double yd, double zd) {
        super(level, xCoord, yCoord, zCoord, xd, yd, zd);
        this.f_107215_ = xd;
        this.f_107216_ = yd;
        this.f_107217_ = zd;
        this.m_6569_(this.f_107223_.m_188501_() * 1.75f + 1.0f);
        this.f_107225_ = 4 + (int)(Math.random() * 11.0);
        this.sprites = spriteSet;
        this.m_108339_(spriteSet);
        this.f_107226_ = -0.1f;
        this.mirrored = this.f_107223_.m_188499_();
    }

    public void m_5989_() {
        super.m_5989_();
        this.f_107215_ += (double)(this.f_107223_.m_188501_() / 100.0f * (float)(this.f_107223_.m_188499_() ? 1 : -1));
        this.f_107216_ += (double)(this.f_107223_.m_188501_() / 100.0f);
        this.f_107217_ += (double)(this.f_107223_.m_188501_() / 100.0f * (float)(this.f_107223_.m_188499_() ? 1 : -1));
        this.m_108339_(this.sprites);
    }

    protected float m_5970_() {
        return this.mirrored ? super.m_5952_() : super.m_5970_();
    }

    protected float m_5952_() {
        return this.mirrored ? super.m_5970_() : super.m_5952_();
    }

    public ParticleRenderType m_7556_() {
        return ParticleRenderType.f_107430_;
    }

    public int m_6355_(float p_107564_) {
        return 0xF000F0;
    }

    @OnlyIn(value=Dist.CLIENT)
    public static class Provider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
            return new AbyssSpikeParticle(level, x, y, z, this.sprites, dx, dy, dz);
        }
    }
}

