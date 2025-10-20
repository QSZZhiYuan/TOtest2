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
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package com.gametechbc.traveloptics.particle.glowing_enchantment;

import com.gametechbc.traveloptics.particle.glowing_enchantment.GlowingEnchantmentParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value=Dist.CLIENT)
public class GlowingEnchantmentParticle
extends TextureSheetParticle {
    private final double xStart;
    private final double yStart;
    private final double zStart;
    private final boolean fallAtEnd;

    public GlowingEnchantmentParticle(ClientLevel level, double x, double y, double z, SpriteSet sprite, double xd, double yd, double zd, GlowingEnchantmentParticleOptions options) {
        super(level, x, y, z);
        this.f_107215_ = xd;
        this.f_107216_ = yd;
        this.f_107217_ = zd;
        this.xStart = x;
        this.yStart = y;
        this.zStart = z;
        this.f_107209_ = x + xd;
        this.f_107210_ = y + yd;
        this.f_107211_ = z + zd;
        this.f_107212_ = this.f_107209_;
        this.f_107213_ = this.f_107210_;
        this.f_107214_ = this.f_107211_;
        this.f_107663_ = options.m_175813_();
        this.f_107227_ = options.m_252837_().x();
        this.f_107228_ = options.m_252837_().y();
        this.f_107229_ = options.m_252837_().z();
        this.fallAtEnd = options.shouldFallAtEnd();
        this.f_107219_ = false;
        this.f_107225_ = options.getLifespan();
        this.m_108335_(sprite);
    }

    public ParticleRenderType m_7556_() {
        return ParticleRenderType.f_107430_;
    }

    public void m_6257_(double x, double y, double z) {
        this.m_107259_(this.m_107277_().m_82386_(x, y, z));
        this.m_107275_();
    }

    public int m_6355_(float partialTick) {
        return 0xF000F0;
    }

    public void m_5989_() {
        this.f_107209_ = this.f_107212_;
        this.f_107210_ = this.f_107213_;
        this.f_107211_ = this.f_107214_;
        if (this.f_107224_++ >= this.f_107225_) {
            this.m_107274_();
        } else {
            float f = (float)this.f_107224_ / (float)this.f_107225_;
            f = 1.0f - f;
            float f1 = 1.0f - f;
            f1 *= f1;
            f1 *= f1;
            this.f_107212_ = this.xStart + this.f_107215_ * (double)f;
            this.f_107213_ = this.fallAtEnd ? this.yStart + this.f_107216_ * (double)f - (double)(f1 * 1.2f) : this.yStart + this.f_107216_ * (double)f;
            this.f_107214_ = this.zStart + this.f_107217_ * (double)f;
            this.m_107264_(this.f_107212_, this.f_107213_, this.f_107214_);
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    public static class Provider
    implements ParticleProvider<GlowingEnchantmentParticleOptions> {
        private final SpriteSet sprite;

        public Provider(SpriteSet sprite) {
            this.sprite = sprite;
        }

        public Particle createParticle(GlowingEnchantmentParticleOptions options, ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
            return new GlowingEnchantmentParticle(level, x, y, z, this.sprite, xd, yd, zd, options);
        }
    }
}

