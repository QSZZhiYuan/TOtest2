/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  net.minecraft.core.particles.DustParticleOptionsBase
 *  net.minecraft.core.particles.ParticleOptions$Deserializer
 *  net.minecraft.core.particles.ParticleType
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.util.ExtraCodecs
 *  org.jetbrains.annotations.NotNull
 *  org.joml.Vector3f
 */
package com.gametechbc.traveloptics.particle.reverse_blastwave;

import com.gametechbc.traveloptics.init.TravelopticsParticles;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.DustParticleOptionsBase;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class ReverseBlastwaveParticleOptions
extends DustParticleOptionsBase {
    private float scale;
    public static final Codec<ReverseBlastwaveParticleOptions> CODEC = RecordCodecBuilder.create(p_175793_ -> p_175793_.group((App)ExtraCodecs.f_252432_.fieldOf("color").forGetter(p_175797_ -> p_175797_.f_175800_), (App)Codec.FLOAT.fieldOf("scale").forGetter(p_175795_ -> Float.valueOf(p_175795_.scale))).apply((Applicative)p_175793_, ReverseBlastwaveParticleOptions::new));
    public static final ParticleOptions.Deserializer<ReverseBlastwaveParticleOptions> DESERIALIZER = new ParticleOptions.Deserializer<ReverseBlastwaveParticleOptions>(){

        @NotNull
        public ReverseBlastwaveParticleOptions fromCommand(@NotNull ParticleType<ReverseBlastwaveParticleOptions> p_123689_, @NotNull StringReader p_123690_) throws CommandSyntaxException {
            Vector3f vector3f = DustParticleOptionsBase.m_252853_((StringReader)p_123690_);
            p_123690_.expect(' ');
            float f = p_123690_.readFloat();
            return new ReverseBlastwaveParticleOptions(vector3f, f);
        }

        @NotNull
        public ReverseBlastwaveParticleOptions fromNetwork(@NotNull ParticleType<ReverseBlastwaveParticleOptions> p_123692_, @NotNull FriendlyByteBuf p_123693_) {
            return new ReverseBlastwaveParticleOptions(DustParticleOptionsBase.m_253064_((FriendlyByteBuf)p_123693_), p_123693_.readFloat());
        }
    };

    public ReverseBlastwaveParticleOptions(Vector3f color, float scale) {
        super(color, scale);
        this.scale = scale;
    }

    public float m_175813_() {
        return this.scale;
    }

    public void m_7711_(FriendlyByteBuf pBuffer) {
        pBuffer.writeFloat(this.f_175800_.x());
        pBuffer.writeFloat(this.f_175800_.y());
        pBuffer.writeFloat(this.f_175800_.z());
        pBuffer.writeFloat(this.scale);
    }

    @NotNull
    public ParticleType<ReverseBlastwaveParticleOptions> m_6012_() {
        return (ParticleType)TravelopticsParticles.REVERSE_BLASTWAVE.get();
    }
}

