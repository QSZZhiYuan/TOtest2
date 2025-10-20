/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.math.Axis
 *  net.minecraft.Util
 *  net.minecraft.client.Camera
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.particle.Particle
 *  net.minecraft.client.particle.ParticleProvider
 *  net.minecraft.client.particle.ParticleRenderType
 *  net.minecraft.client.particle.SpriteSet
 *  net.minecraft.client.particle.TextureSheetParticle
 *  net.minecraft.util.Mth
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  org.jetbrains.annotations.NotNull
 *  org.joml.Quaternionf
 *  org.joml.Quaternionfc
 *  org.joml.Vector3f
 */
package com.gametechbc.traveloptics.particle.reverse_blastwave;

import com.gametechbc.traveloptics.particle.reverse_blastwave.ReverseBlastwaveParticleOptions;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.function.Consumer;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;

public class ReverseBlastwaveParticle
extends TextureSheetParticle {
    private static final Vector3f ROTATION_VECTOR = (Vector3f)Util.m_137469_((Object)new Vector3f(0.5f, 0.5f, 0.5f), Vector3f::normalize);
    private static final Vector3f TRANSFORM_VECTOR = new Vector3f(-1.0f, -1.0f, 0.0f);
    private static final float DEGREES_90 = 1.5707964f;
    private final float targetSize;
    private final SpriteSet sprites;

    ReverseBlastwaveParticle(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet spriteSet, double xd, double yd, double zd, ReverseBlastwaveParticleOptions options) {
        super(pLevel, pX, pY, pZ, 0.0, 0.0, 0.0);
        this.f_107215_ = xd;
        this.f_107216_ = yd;
        this.f_107217_ = zd;
        this.targetSize = options.m_175813_();
        this.f_107663_ = 1.0f;
        this.f_107225_ = 8;
        this.f_107226_ = 0.1f;
        this.sprites = spriteSet;
        float f = this.f_107223_.m_188501_() * 0.14f + 0.85f;
        this.f_107227_ = options.m_252837_().x() * f;
        this.f_107228_ = options.m_252837_().y() * f;
        this.f_107229_ = options.m_252837_().z() * f;
        this.f_172258_ = 1.0f;
    }

    public float m_5902_(float partialTick) {
        float f = (partialTick + (float)this.f_107224_) / (float)this.f_107225_;
        return this.f_107663_ * Mth.m_14179_((float)(1.0f - (1.0f - f) * (1.0f - f)), (float)this.targetSize, (float)(this.targetSize * 0.75f));
    }

    public void m_5989_() {
        this.f_107209_ = this.f_107212_;
        this.f_107210_ = this.f_107213_;
        this.f_107211_ = this.f_107214_;
        if (this.f_107224_++ >= this.f_107225_) {
            this.m_107274_();
        } else {
            this.m_108339_(this.sprites);
            this.m_6257_(this.f_107215_, this.f_107216_, this.f_107217_);
            this.f_107216_ *= (double)0.85f;
            this.f_107215_ *= (double)0.94f;
            this.f_107217_ *= (double)0.94f;
        }
    }

    public boolean shouldCull() {
        return false;
    }

    public void m_5744_(VertexConsumer buffer, Camera camera, float partialticks) {
        this.f_107230_ = 1.0f - Mth.m_14036_((float)(((float)this.f_107224_ + partialticks) / (float)this.f_107225_), (float)0.0f, (float)1.0f);
        this.renderRotatedParticle(buffer, camera, partialticks, p_234005_ -> {
            p_234005_.mul((Quaternionfc)Axis.f_252436_.m_252961_(0.0f));
            p_234005_.mul((Quaternionfc)Axis.f_252529_.m_252961_(-1.5707964f));
        });
        this.renderRotatedParticle(buffer, camera, partialticks, p_234000_ -> {
            p_234000_.mul((Quaternionfc)Axis.f_252436_.m_252961_((float)(-Math.PI)));
            p_234000_.mul((Quaternionfc)Axis.f_252529_.m_252961_(1.5707964f));
        });
    }

    private void renderRotatedParticle(VertexConsumer pConsumer, Camera camera, float partialTick, Consumer<Quaternionf> pQuaternion) {
        Vec3 vec3 = camera.m_90583_();
        float f = (float)(Mth.m_14139_((double)partialTick, (double)this.f_107209_, (double)this.f_107212_) - vec3.m_7096_());
        float f1 = (float)(Mth.m_14139_((double)partialTick, (double)this.f_107210_, (double)this.f_107213_) - vec3.m_7098_());
        float f2 = (float)(Mth.m_14139_((double)partialTick, (double)this.f_107211_, (double)this.f_107214_) - vec3.m_7094_());
        Quaternionf quaternion = new Quaternionf().setAngleAxis(0.0f, ROTATION_VECTOR.x(), ROTATION_VECTOR.y(), ROTATION_VECTOR.z());
        pQuaternion.accept(quaternion);
        quaternion.transform(TRANSFORM_VECTOR);
        Vector3f[] avector3f = new Vector3f[]{new Vector3f(-1.0f, -1.0f, 0.0f), new Vector3f(-1.0f, 1.0f, 0.0f), new Vector3f(1.0f, 1.0f, 0.0f), new Vector3f(1.0f, -1.0f, 0.0f)};
        float f3 = this.m_5902_(partialTick);
        for (int i = 0; i < 4; ++i) {
            Vector3f vector3f = avector3f[i];
            vector3f.rotate((Quaternionfc)quaternion);
            vector3f.mul(f3);
            vector3f.add(f, f1, f2);
        }
        int j = this.m_6355_(partialTick);
        this.makeCornerVertex(pConsumer, avector3f[0], this.m_5952_(), this.m_5950_(), j);
        this.makeCornerVertex(pConsumer, avector3f[1], this.m_5952_(), this.m_5951_(), j);
        this.makeCornerVertex(pConsumer, avector3f[2], this.m_5970_(), this.m_5951_(), j);
        this.makeCornerVertex(pConsumer, avector3f[3], this.m_5970_(), this.m_5950_(), j);
    }

    private void makeCornerVertex(VertexConsumer pConsumer, Vector3f pVec3f, float p_233996_, float p_233997_, int p_233998_) {
        pConsumer.m_5483_((double)pVec3f.x(), (double)(pVec3f.y() + 0.08f), (double)pVec3f.z()).m_7421_(p_233996_, p_233997_).m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_).m_85969_(p_233998_).m_5752_();
    }

    @NotNull
    public ParticleRenderType m_7556_() {
        return ParticleRenderType.f_107431_;
    }

    protected int m_6355_(float pPartialTick) {
        return 0xF000F0;
    }

    @OnlyIn(value=Dist.CLIENT)
    public static class Provider
    implements ParticleProvider<ReverseBlastwaveParticleOptions> {
        private final SpriteSet sprite;

        public Provider(SpriteSet pSprite) {
            this.sprite = pSprite;
        }

        public Particle createParticle(@NotNull ReverseBlastwaveParticleOptions options, @NotNull ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            ReverseBlastwaveParticle shriekparticle = new ReverseBlastwaveParticle(pLevel, pX, pY, pZ, this.sprite, pXSpeed, pYSpeed, pZSpeed, options);
            shriekparticle.m_108339_(this.sprite);
            shriekparticle.m_107271_(1.0f);
            return shriekparticle;
        }
    }
}

