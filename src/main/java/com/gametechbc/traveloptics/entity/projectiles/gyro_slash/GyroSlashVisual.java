/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.entity.spells.AoeEntity
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientGamePacketListener
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.projectile.Projectile
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.network.NetworkHooks
 */
package com.gametechbc.traveloptics.entity.projectiles.gyro_slash;

import com.gametechbc.traveloptics.init.TravelopticsEntities;
import io.redspace.ironsspellbooks.entity.spells.AoeEntity;
import java.util.Optional;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

public class GyroSlashVisual
extends AoeEntity {
    private static final EntityDataAccessor<Boolean> DATA_MIRRORED = SynchedEntityData.m_135353_(GyroSlashVisual.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    LivingEntity target;
    public final int ticksPerFrame = 2;
    public final int deathTime = 8;

    public GyroSlashVisual(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public GyroSlashVisual(Level level, boolean mirrored) {
        this((EntityType<? extends Projectile>)((EntityType)TravelopticsEntities.GYRO_SLASH_VISUAL.get()), level);
        if (mirrored) {
            this.m_20088_().m_135381_(DATA_MIRRORED, (Object)true);
        }
    }

    public void applyEffect(LivingEntity target) {
    }

    public void m_8119_() {
        if (!this.f_19803_) {
            this.f_19803_ = true;
        }
        if (this.f_19797_ >= 8) {
            this.m_146870_();
        }
    }

    protected void m_8097_() {
        super.m_8097_();
        this.m_20088_().m_135372_(DATA_MIRRORED, (Object)false);
    }

    public boolean isMirrored() {
        return (Boolean)this.m_20088_().m_135370_(DATA_MIRRORED);
    }

    public boolean m_142391_() {
        return false;
    }

    public void m_6210_() {
    }

    public void ambientParticles() {
    }

    public float getParticleCount() {
        return 0.0f;
    }

    public Optional<ParticleOptions> getParticle() {
        return Optional.empty();
    }

    public Packet<ClientGamePacketListener> m_5654_() {
        return NetworkHooks.getEntitySpawningPacket((Entity)this);
    }
}

