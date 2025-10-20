/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.api.registry.SchoolRegistry
 *  io.redspace.ironsspellbooks.api.spells.AbstractSpell
 *  io.redspace.ironsspellbooks.api.spells.SchoolType
 *  io.redspace.ironsspellbooks.api.util.Utils
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  io.redspace.ironsspellbooks.damage.DamageSources
 *  io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile
 *  io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Position
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.projectile.Projectile
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.Vec3
 */
package com.gametechbc.traveloptics.entity.projectiles.asteroid;

import com.gametechbc.traveloptics.api.particle.AdvancedCylinderParticleManager;
import com.gametechbc.traveloptics.api.particle.ParticleDirection;
import com.gametechbc.traveloptics.entity.misc.TOScreenFlashEntity;
import com.gametechbc.traveloptics.entity.misc.TOScreenShakeEntity;
import com.gametechbc.traveloptics.entity.projectiles.asteroid.AsteroidImpactCraterEntity;
import com.gametechbc.traveloptics.init.TravelopticsEntities;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import com.gametechbc.traveloptics.init.TravelopticsSpells;
import com.gametechbc.traveloptics.util.TravelopticsTags;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class AsteroidEntity
extends AbstractMagicProjectile {
    private int tickCounter = 0;
    private float craterDamage = 5.0f;
    private float craterDepth = 1.0f;
    private float craterInnerRadius = 6.0f;
    private float shockwaveDamage = 10.0f;
    private float shockwaveRadius = 15.0f;
    private double targetY = 60.0;
    private boolean hasImpacted = false;

    public AsteroidEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.m_20242_(true);
    }

    public AsteroidEntity(Level pLevel, LivingEntity pCaster) {
        this((EntityType<? extends Projectile>)((EntityType)TravelopticsEntities.ASTEROID.get()), pLevel);
        this.m_5602_((Entity)pCaster);
    }

    public float getCraterDamage() {
        return this.craterDamage;
    }

    public void setCraterDamage(float craterDamage) {
        this.craterDamage = craterDamage;
    }

    public float getCraterDepth() {
        return this.craterDepth;
    }

    public void setCraterDepth(float craterDepth) {
        this.craterDepth = craterDepth;
    }

    public float getCraterInnerRadius() {
        return this.craterInnerRadius;
    }

    public void setCraterInnerRadius(float craterInnerRadius) {
        this.craterInnerRadius = craterInnerRadius;
    }

    public float getShockwaveDamage() {
        return this.shockwaveDamage;
    }

    public void setShockwaveDamage(float shockwaveDamage) {
        this.shockwaveDamage = shockwaveDamage;
    }

    public float getShockwaveRadius() {
        return this.shockwaveRadius;
    }

    public void setShockwaveRadius(float shockwaveRadius) {
        this.shockwaveRadius = shockwaveRadius;
    }

    public double getTargetY() {
        return this.targetY;
    }

    public void setTargetY(double targetY) {
        this.targetY = targetY;
    }

    public void m_8119_() {
        super.m_8119_();
        ++this.tickCounter;
        if (this.tickCounter >= 20) {
            this.m_9236_().m_6269_(null, (Entity)this, (SoundEvent)TravelopticsSounds.ASTEROID_LOOP.get(), SoundSource.AMBIENT, 4.0f, 1.0f);
            this.tickCounter = 0;
        }
        if (!this.hasImpacted && this.m_20186_() <= this.targetY) {
            this.hasImpacted = true;
            this.performImpact();
        }
    }

    protected void m_6532_(HitResult hitResult) {
    }

    private void performImpact() {
        Vec3 impactLocation = this.m_20182_();
        BlockHitResult fakeHitResult = new BlockHitResult(impactLocation, Direction.DOWN, BlockPos.m_274446_((Position)impactLocation), false);
        if (!this.m_9236_().f_46443_) {
            this.spawnAsteroidCraterEntity((HitResult)fakeHitResult);
            this.handleExplosionParticle();
            this.applyImpactDamage((HitResult)fakeHitResult);
            this.applyShockwaveDamage((HitResult)fakeHitResult);
            this.handleBlockBreakingAndReplacing((HitResult)fakeHitResult);
            TOScreenShakeEntity.createScreenShake(this.m_9236_(), this.m_20182_(), this.getShockwaveRadius(), 0.1f, 10, 0, 20, false);
            TOScreenFlashEntity.createWhiteFlash(this.m_9236_(), this.m_20182_(), this.getShockwaveRadius(), 1.0f, 10, 20, 20, false);
            this.m_5496_((SoundEvent)ACSoundRegistry.NUCLEAR_EXPLOSION.get(), 4.0f, 1.0f);
            this.m_146870_();
        }
    }

    public void spawnAsteroidCraterEntity(HitResult hitResult) {
        float explosionRadius = this.getExplosionRadius();
        BlockPos hitPos = BlockPos.m_274446_((Position)hitResult.m_82450_());
        for (int angle = 0; angle < 360; angle += 10) {
            double radians = Math.toRadians(angle);
            int step = (int)this.getCraterInnerRadius();
            while ((float)step <= explosionRadius - 2.0f) {
                int x = hitPos.m_123341_() + (int)((double)step * Math.cos(radians));
                int z = hitPos.m_123343_() + (int)((double)step * Math.sin(radians));
                BlockPos surfacePos = this.findTopSolidBlock(new BlockPos(x, hitPos.m_123342_(), z));
                int depth = 1;
                while ((float)depth < this.getCraterDepth()) {
                    BlockPos blockPos = surfacePos.m_6625_(depth);
                    ++depth;
                }
                ++step;
            }
        }
        AsteroidImpactCraterEntity asteroidImpactCrater = new AsteroidImpactCraterEntity(this.m_9236_());
        asteroidImpactCrater.m_5602_(this.m_19749_());
        asteroidImpactCrater.setDamage(this.getCraterDamage());
        asteroidImpactCrater.setDuration(200);
        asteroidImpactCrater.setRadius(explosionRadius);
        asteroidImpactCrater.setCircular();
        asteroidImpactCrater.m_20219_(hitResult.m_82450_().m_82520_(0.0, -1.8, 0.0));
        this.m_9236_().m_7967_((Entity)asteroidImpactCrater);
    }

    public void handleBlockBreakingAndReplacing(HitResult hitResult) {
        float explosionRadius = this.getExplosionRadius();
        BlockPos hitPos = BlockPos.m_274446_((Position)hitResult.m_82450_());
        Random random = new Random();
        int innerRadius = (int)this.getCraterInnerRadius();
        int edgeMargin = 2;
        int depth = (int)this.getCraterDepth();
        for (int angle = 0; angle < 360; angle += 10) {
            double radians = Math.toRadians(angle);
            int step = innerRadius;
            while ((float)step <= explosionRadius - (float)edgeMargin) {
                for (int thickness = -1; thickness <= 0; ++thickness) {
                    int x = hitPos.m_123341_() + (int)((double)step * Math.cos(radians)) + random.nextInt(3) - 1 + thickness;
                    int z = hitPos.m_123343_() + (int)((double)step * Math.sin(radians)) + random.nextInt(3) - 1 + thickness;
                    BlockPos targetPos = this.findTopSolidBlock(new BlockPos(x, hitPos.m_123342_(), z));
                    BlockState targetBlockState = this.m_9236_().m_8055_(targetPos);
                    if (!targetBlockState.m_280296_() || targetBlockState.m_60713_((Block)Blocks.f_50067_) || targetBlockState.m_204336_(TravelopticsTags.EXTINCTION_BLOCK_BLACKLIST)) continue;
                    for (int d = 0; d < depth - 1; ++d) {
                        this.m_9236_().m_7731_(targetPos.m_6625_(d), Blocks.f_50016_.m_49966_(), 3);
                    }
                    boolean isActive = random.nextInt(100) < 60;
                    BlockState primalMagmaBlockState = (BlockState)((BlockState)((Block)Blocks.f_50067_).m_49966_().m_61124_((Property)PrimalMagmaBlock.ACTIVE, (Comparable)Boolean.valueOf(isActive))).m_61124_((Property)PrimalMagmaBlock.PERMANENT, (Comparable)Boolean.valueOf(true));
                    this.m_9236_().m_7731_(targetPos.m_6625_(depth - 1), primalMagmaBlockState, 3);
                }
                ++step;
            }
        }
    }

    private BlockPos findTopSolidBlock(BlockPos pos) {
        BlockPos.MutableBlockPos mutablePos = pos.m_122032_();
        while (this.m_9236_().m_8055_((BlockPos)mutablePos).m_280296_() && mutablePos.m_123342_() < this.m_9236_().m_151558_()) {
            mutablePos.m_122184_(0, 1, 0);
        }
        return mutablePos.m_7495_();
    }

    public void applyImpactDamage(HitResult hitResult) {
        float explosionRadius = this.getExplosionRadius();
        float explosionRadiusSqr = explosionRadius * explosionRadius;
        List entities = this.m_9236_().m_45933_((Entity)this, this.m_20191_().m_82400_((double)explosionRadius));
        Vec3 losPoint = Utils.raycastForBlock((Level)this.m_9236_(), (Vec3)this.m_20182_(), (Vec3)this.m_20182_().m_82520_(0.0, 2.0, 0.0), (ClipContext.Fluid)ClipContext.Fluid.NONE).m_82450_();
        for (Entity entity : entities) {
            double distanceSqr = entity.m_20238_(hitResult.m_82450_());
            if (!(distanceSqr < (double)explosionRadiusSqr) || !this.m_5603_(entity) || !Utils.hasLineOfSight((Level)this.m_9236_(), (Vec3)losPoint, (Vec3)entity.m_20191_().m_82399_(), (boolean)false)) continue;
            double p = 1.0 - distanceSqr / (double)explosionRadiusSqr;
            float damage = (float)((double)this.damage * p);
            DamageSources.applyDamage((Entity)entity, (float)damage, (DamageSource)((AbstractSpell)TravelopticsSpells.EXTINCTION_SPELL.get()).getDamageSource((Entity)this, this.m_19749_()));
        }
    }

    public void applyShockwaveDamage(HitResult hitResult) {
        float shockwaveDamage = this.getShockwaveDamage();
        float shockwaveRadius = this.getShockwaveRadius();
        float explosionRadius = this.getExplosionRadius();
        double explosionRadiusSqr = explosionRadius * explosionRadius;
        double shockwaveRadiusSqr = shockwaveRadius * shockwaveRadius;
        List entities = this.m_9236_().m_45933_((Entity)this, this.m_20191_().m_82400_((double)shockwaveRadius));
        Vec3 losPoint = Utils.raycastForBlock((Level)this.m_9236_(), (Vec3)this.m_20182_(), (Vec3)this.m_20182_().m_82520_(0.0, 2.0, 0.0), (ClipContext.Fluid)ClipContext.Fluid.NONE).m_82450_();
        for (Entity entity : entities) {
            double distanceSqr = entity.m_20238_(hitResult.m_82450_());
            if (!(distanceSqr >= explosionRadiusSqr) || !(distanceSqr < shockwaveRadiusSqr) || !this.m_5603_(entity) || !Utils.hasLineOfSight((Level)this.m_9236_(), (Vec3)losPoint, (Vec3)entity.m_20191_().m_82399_(), (boolean)false)) continue;
            double p = 1.0 - (distanceSqr - explosionRadiusSqr) / (shockwaveRadiusSqr - explosionRadiusSqr);
            float damage = (float)((double)shockwaveDamage * p);
            DamageSources.applyDamage((Entity)entity, (float)damage, (DamageSource)((AbstractSpell)TravelopticsSpells.EXTINCTION_SPELL.get()).getDamageSource((Entity)this, this.m_19749_()));
        }
        MagicManager.spawnParticles((Level)this.m_9236_(), (ParticleOptions)new BlastwaveParticleOptions(((SchoolType)SchoolRegistry.FIRE.get()).getTargetingColor(), shockwaveRadius), (double)this.m_20182_().f_82479_, (double)(this.m_20182_().f_82480_ + (double)0.165f), (double)this.m_20182_().f_82481_, (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)true);
    }

    public void m_7380_(CompoundTag compound) {
        super.m_7380_(compound);
        compound.m_128350_("CraterDamage", this.craterDamage);
        compound.m_128350_("CraterDepth", this.craterDepth);
        compound.m_128350_("CraterInnerRadius", this.craterInnerRadius);
        compound.m_128350_("ShockwaveDamage", this.shockwaveDamage);
        compound.m_128350_("ShockwaveRadius", this.shockwaveRadius);
        compound.m_128405_("CraterEntityDuration", 200);
        compound.m_128347_("TargetY", this.targetY);
        compound.m_128379_("HasImpacted", this.hasImpacted);
    }

    public void m_7378_(CompoundTag compound) {
        super.m_7378_(compound);
        if (compound.m_128441_("CraterDamage")) {
            this.craterDamage = compound.m_128457_("CraterDamage");
        }
        if (compound.m_128441_("CraterDepth")) {
            this.craterDepth = compound.m_128457_("CraterDepth");
        }
        if (compound.m_128441_("CraterInnerRadius")) {
            this.craterInnerRadius = compound.m_128457_("CraterInnerRadius");
        }
        if (compound.m_128441_("ShockwaveDamage")) {
            this.shockwaveDamage = compound.m_128457_("ShockwaveDamage");
        }
        if (compound.m_128441_("ShockwaveRadius")) {
            this.shockwaveRadius = compound.m_128457_("ShockwaveRadius");
        }
        if (compound.m_128441_("TargetY")) {
            this.targetY = compound.m_128459_("TargetY");
        }
        if (compound.m_128441_("HasImpacted")) {
            this.hasImpacted = compound.m_128471_("HasImpacted");
        }
    }

    public float getSpeed() {
        return 1.2f;
    }

    public Optional<SoundEvent> getImpactSound() {
        return Optional.empty();
    }

    public void trailParticles() {
        Vec3 movement = this.m_20184_();
        double x = this.m_20185_();
        double y = this.m_20186_();
        double z = this.m_20189_();
        double speed = 0.2;
        for (int i = 0; i < 10; ++i) {
            SimpleParticleType particle = switch (this.m_9236_().f_46441_.m_188503_(2)) {
                case 0 -> (SimpleParticleType)ParticleTypes.f_123756_;
                case 1 -> (SimpleParticleType)ParticleTypes.f_123756_;
                default -> (SimpleParticleType)ParticleTypes.f_123756_;
            };
            double xOffset = (this.f_19796_.m_188500_() - 0.5) * (double)this.m_20205_();
            double yOffset = (this.f_19796_.m_188500_() - 0.5) * (double)this.m_20206_();
            double zOffset = (this.f_19796_.m_188500_() - 0.5) * (double)this.m_20205_();
            this.m_9236_().m_6485_((ParticleOptions)particle, true, x + xOffset, y + yOffset, z + zOffset, -movement.f_82479_ * speed + (this.f_19796_.m_188500_() - 0.5) * 0.05, -movement.f_82480_ * speed + (this.f_19796_.m_188500_() - 0.5) * 0.05, -movement.f_82481_ * speed + (this.f_19796_.m_188500_() - 0.5) * 0.05);
        }
    }

    public void handleExplosionParticle() {
        AdvancedCylinderParticleManager.spawnParticles(this.m_9236_(), this.m_20182_(), 80, (ParticleOptions)ParticleTypes.f_123777_, ParticleDirection.OUTWARD, 8.0, 8.0, 0.0, 0.0, 0.0, 1.5, true);
        AdvancedCylinderParticleManager.spawnParticles(this.m_9236_(), this.m_20182_(), 40, (ParticleOptions)ParticleTypes.f_123756_, ParticleDirection.OUTWARD, 8.0, 8.0, 0.0, 0.0, 0.0, 2.2, true);
        AdvancedCylinderParticleManager.spawnParticles(this.m_9236_(), this.m_20182_(), 40, (ParticleOptions)ParticleTypes.f_123756_, ParticleDirection.OUTWARD, 10.0, 10.0, 0.0, 0.0, 0.0, 2.0, true);
    }

    public void impactParticles(double x, double y, double z) {
    }
}

