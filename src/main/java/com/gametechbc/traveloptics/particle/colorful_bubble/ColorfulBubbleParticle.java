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
package com.gametechbc.traveloptics.particle.colorful_bubble;

import com.gametechbc.traveloptics.particle.colorful_bubble.ColorfulBubbleParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value=Dist.CLIENT)
public class ColorfulBubbleParticle
extends TextureSheetParticle {
    private final SpriteSet sprites;
    private final boolean shouldGlow;

    public ColorfulBubbleParticle(ClientLevel level, double xCoord, double yCoord, double zCoord, SpriteSet spriteSet, double xd, double yd, double zd, ColorfulBubbleParticleOptions options) {
        super(level, xCoord, yCoord, zCoord, xd, yd, zd);
        this.f_107215_ = xd;
        this.f_107216_ = yd;
        this.f_107217_ = zd;
        this.f_107663_ *= options.getQuadSize() * 3.0f;
        this.f_107225_ = options.getLifetime();
        this.f_107227_ = options.m_252837_().x();
        this.f_107228_ = options.m_252837_().y();
        this.f_107229_ = options.m_252837_().z();
        this.shouldGlow = options.shouldGlow();
        this.sprites = spriteSet;
        this.f_107226_ = 0.35f;
        this.m_108339_(spriteSet);
    }

    public void m_5989_() {
        super.m_5989_();
        this.m_108339_(this.sprites);
    }

    public ParticleRenderType m_7556_() {
        return ParticleRenderType.f_107430_;
    }

    public int m_6355_(float partialTick) {
        if (this.shouldGlow) {
            return 0xF000F0;
        }
        return super.m_6355_(partialTick);
    }

    @OnlyIn(value=Dist.CLIENT)
    public static class Provider
    implements ParticleProvider<ColorfulBubbleParticleOptions> {
        private final SpriteSet sprites;

        public Provider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(ColorfulBubbleParticleOptions options, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
            return new ColorfulBubbleParticle(level, x, y, z, this.sprites, dx, dy, dz, options);
        }
    }
}

