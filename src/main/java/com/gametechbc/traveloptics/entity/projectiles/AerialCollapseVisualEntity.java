/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.api.magic.MagicData
 *  io.redspace.ironsspellbooks.api.registry.SchoolRegistry
 *  io.redspace.ironsspellbooks.api.spells.SchoolType
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible
 *  io.redspace.ironsspellbooks.entity.spells.AoeEntity
 *  io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.projectile.Projectile
 *  net.minecraft.world.level.Level
 */
package com.gametechbc.traveloptics.entity.projectiles;

import com.gametechbc.traveloptics.api.particle.AdvancedCylinderParticleManager;
import com.gametechbc.traveloptics.api.particle.ParticleDirection;
import com.gametechbc.traveloptics.init.TravelopticsEntities;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import io.redspace.ironsspellbooks.entity.spells.AoeEntity;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import java.util.Optional;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

public class AerialCollapseVisualEntity
extends AoeEntity
implements AntiMagicSusceptible {
    private int tickCounter;

    public AerialCollapseVisualEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public AerialCollapseVisualEntity(Level level) {
        this((EntityType<? extends Projectile>)((EntityType)TravelopticsEntities.AERIAL_COLLAPSE_VISUAL_ENTITY.get()), level);
    }

    public void m_8119_() {
        super.m_8119_();
        ++this.tickCounter;
        if (!this.m_9236_().f_46443_) {
            AdvancedCylinderParticleManager.spawnParticles(this.m_9236_(), this.m_20182_(), (int)this.getRadius(), (ParticleOptions)ParticleTypes.f_123776_, ParticleDirection.UPWARD, this.getRadius(), 17.0, 0.0, 0.0, 0.0, 1.0, false);
        }
        if (!this.m_9236_().f_46443_ && this.tickCounter % 13 == 0) {
            MagicManager.spawnParticles((Level)this.m_9236_(), (ParticleOptions)new BlastwaveParticleOptions(((SchoolType)SchoolRegistry.NATURE.get()).getTargetingColor(), this.getRadius()), (double)this.m_20185_(), (double)(this.m_20186_() + (double)0.165f), (double)this.m_20189_(), (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)false);
        }
        if (!this.m_9236_().f_46443_ && this.f_19797_ >= 55) {
            this.m_146870_();
        }
    }

    public void applyEffect(LivingEntity target) {
    }

    public float getParticleCount() {
        return 0.0f;
    }

    protected float particleYOffset() {
        return 0.25f;
    }

    protected float getParticleSpeedModifier() {
        return 1.4f;
    }

    public Optional<ParticleOptions> getParticle() {
        return Optional.empty();
    }

    public void onAntiMagic(MagicData magicData) {
    }
}

