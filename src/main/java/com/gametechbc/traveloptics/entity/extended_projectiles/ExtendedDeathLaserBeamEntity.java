/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.blocks.EMP_Block
 *  com.github.L_Ender.cataclysm.client.particle.LightningParticle$OrbData
 *  com.github.L_Ender.cataclysm.config.CMConfig
 *  com.github.L_Ender.cataclysm.entity.projectile.Death_Laser_Beam_Entity
 *  com.github.L_Ender.cataclysm.init.ModBlocks
 *  com.github.L_Ender.cataclysm.init.ModTag
 *  com.github.L_Ender.cataclysm.util.CMDamageTypes
 *  io.redspace.ironsspellbooks.api.spells.AbstractSpell
 *  io.redspace.ironsspellbooks.damage.DamageSources
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.util.Mth
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ClipContext
 *  net.minecraft.world.level.ClipContext$Block
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.BaseFireBlock
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.event.ForgeEventFactory
 */
package com.gametechbc.traveloptics.entity.extended_projectiles;

import com.gametechbc.traveloptics.config.SpellsConfig;
import com.gametechbc.traveloptics.init.TravelopticsSpells;
import com.github.L_Ender.cataclysm.blocks.EMP_Block;
import com.github.L_Ender.cataclysm.client.particle.LightningParticle;
import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.projectile.Death_Laser_Beam_Entity;
import com.github.L_Ender.cataclysm.init.ModBlocks;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.cataclysm.util.CMDamageTypes;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.damage.DamageSources;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;

public class ExtendedDeathLaserBeamEntity
extends Death_Laser_Beam_Entity {
    public ExtendedDeathLaserBeamEntity(EntityType<? extends Death_Laser_Beam_Entity> type, Level world) {
        super(type, world);
    }

    public ExtendedDeathLaserBeamEntity(EntityType<? extends Death_Laser_Beam_Entity> type, Level world, LivingEntity caster, double x, double y, double z, float yaw, float pitch, int duration, float damage, float hpDamage) {
        super(type, world, caster, x, y, z, yaw, pitch, duration, damage, hpDamage);
    }

    public void m_8119_() {
        this.prevCollidePosX = this.collidePosX;
        this.prevCollidePosY = this.collidePosY;
        this.prevCollidePosZ = this.collidePosZ;
        this.prevYaw = this.renderYaw;
        this.prevPitch = this.renderPitch;
        this.f_19854_ = this.m_20185_();
        this.f_19855_ = this.m_20186_();
        this.f_19856_ = this.m_20189_();
        if (this.f_19797_ == 1 && this.m_9236_().f_46443_) {
            this.caster = (LivingEntity)this.m_9236_().m_6815_(this.getCasterID());
        }
        if (!this.m_9236_().f_46443_ && this.caster != null) {
            this.updateWithLivingEntity();
        }
        if (this.caster != null) {
            this.renderYaw = (float)(((double)this.caster.f_20885_ + 90.0) * Math.PI / 180.0);
            this.renderPitch = (float)((double)(-this.caster.m_146909_()) * Math.PI / 180.0);
        }
        if (!this.on && this.appear.getTimer() == 0) {
            this.m_146870_();
        }
        if (this.on && this.f_19797_ > 20) {
            this.appear.increaseTimer();
        } else {
            this.appear.decreaseTimer();
        }
        if (this.caster != null && !this.caster.m_6084_()) {
            this.m_146870_();
        }
        if (this.f_19797_ > 20) {
            this.updatedCalculateEndPos();
            List<LivingEntity> hit = this.updatedRaytraceEntities((Level)this.m_9236_(), (Vec3)new Vec3((double)this.m_20185_(), (double)this.m_20186_(), (double)this.m_20189_()), (Vec3)new Vec3((double)this.endPosX, (double)this.endPosY, (double)this.endPosZ), (boolean)false, (boolean)true, (boolean)true).entities;
            if (this.blockSide != null) {
                this.spawnUpdatedExplosionParticles(5);
                if (!this.m_9236_().f_46443_) {
                    BlockState block;
                    if (((Boolean)SpellsConfig.deathLaserBlockBreaking.get()).booleanValue()) {
                        for (BlockPos pos : BlockPos.m_121976_((int)Mth.m_14107_((double)(this.collidePosX - 0.5)), (int)Mth.m_14107_((double)(this.collidePosY - 0.5)), (int)Mth.m_14107_((double)(this.collidePosZ - 0.5)), (int)Mth.m_14107_((double)(this.collidePosX + 0.5)), (int)Mth.m_14107_((double)(this.collidePosY + 0.5)), (int)Mth.m_14107_((double)(this.collidePosZ + 0.5)))) {
                            block = this.m_9236_().m_8055_(pos);
                            if (block.m_60795_() || !block.m_204336_(ModTag.CM_GLASS) || !ForgeEventFactory.getMobGriefingEvent((Level)this.m_9236_(), (Entity)this)) continue;
                            this.m_9236_().m_46961_(pos, true);
                        }
                    }
                    for (BlockPos pos : BlockPos.m_121976_((int)Mth.m_14107_((double)(this.collidePosX - 2.5)), (int)Mth.m_14107_((double)(this.collidePosY - 2.5)), (int)Mth.m_14107_((double)(this.collidePosZ - 2.5)), (int)Mth.m_14107_((double)(this.collidePosX + 2.5)), (int)Mth.m_14107_((double)(this.collidePosY + 2.5)), (int)Mth.m_14107_((double)(this.collidePosZ + 2.5)))) {
                        block = this.m_9236_().m_8055_(pos);
                        if (!block.m_60713_((Block)ModBlocks.EMP.get()) || ((Boolean)block.m_61143_((Property)EMP_Block.POWERED)).booleanValue() || !((Boolean)block.m_61143_((Property)EMP_Block.OVERLOAD)).booleanValue()) continue;
                        this.m_9236_().m_46597_(pos, (BlockState)block.m_61124_((Property)EMP_Block.OVERLOAD, (Comparable)Boolean.valueOf(false)));
                    }
                    if (this.getFire()) {
                        BlockPos blockpos1 = BlockPos.m_274561_((double)this.collidePosX, (double)this.collidePosY, (double)this.collidePosZ);
                        if (CMConfig.HarbingerLightFire) {
                            if (this.m_9236_().m_46859_(blockpos1)) {
                                this.m_9236_().m_46597_(blockpos1, BaseFireBlock.m_49245_((BlockGetter)this.m_9236_(), (BlockPos)blockpos1));
                            }
                        } else if (ForgeEventFactory.getMobGriefingEvent((Level)this.m_9236_(), (Entity)this) && this.m_9236_().m_46859_(blockpos1)) {
                            this.m_9236_().m_46597_(blockpos1, BaseFireBlock.m_49245_((BlockGetter)this.m_9236_(), (BlockPos)blockpos1));
                        }
                    }
                }
            }
            if (!this.m_9236_().f_46443_) {
                for (LivingEntity target : hit) {
                    boolean flag;
                    if (((Boolean)SpellsConfig.deathLaserDealMagicDamage.get()).booleanValue()) {
                        if (this.caster == null || this.caster.m_7307_((Entity)target) || target == this.caster) continue;
                        flag = target.m_6469_(CMDamageTypes.causeDeathLaserDamage((Entity)this, (LivingEntity)this.caster), (float)((double)this.getDamage() + Math.min((double)this.getDamage(), (double)(target.m_21233_() * this.getHpDamage()) * 0.01)));
                        if (!this.getFire() || !flag) continue;
                        target.m_20254_(5);
                        continue;
                    }
                    if (this.caster == null || this.caster.m_7307_((Entity)target) || target == this.caster) continue;
                    flag = DamageSources.applyDamage((Entity)target, (float)((float)((double)this.getDamage() + Math.min((double)this.getDamage(), (double)(target.m_21233_() * this.getHpDamage()) * 0.01))), (DamageSource)((AbstractSpell)TravelopticsSpells.DEATH_LASER_SPELL.get()).getDamageSource((Entity)this, (Entity)this.caster));
                    if (!this.getFire() || !flag) continue;
                    target.m_20254_(5);
                }
            }
        }
        if (this.f_19797_ - 20 > this.getDuration()) {
            this.on = false;
        }
    }

    protected void spawnUpdatedExplosionParticles(int amount) {
        for (int i = 0; i < amount; ++i) {
            float velocity = 1.5f;
            float yaw = (float)((double)(this.f_19796_.m_188501_() * 2.0f) * Math.PI);
            float motionY = this.f_19796_.m_188501_() * 0.8f;
            float motionX = 1.5f * Mth.m_14089_((float)yaw);
            float motionZ = 1.5f * Mth.m_14031_((float)yaw);
            this.m_9236_().m_7106_((ParticleOptions)new LightningParticle.OrbData(255, 26, 0), this.collidePosX, this.collidePosY + 0.1, this.collidePosZ, (double)motionX, (double)motionY, (double)motionZ);
        }
    }

    private void updateWithLivingEntity() {
        this.setYaw((float)((double)(this.caster.f_20885_ + 90.0f) * Math.PI / 180.0));
        this.setPitch((float)((double)(-this.caster.m_146909_()) * Math.PI / 180.0));
        double eyeOffset = 0.06;
        this.m_6034_(this.caster.m_20185_(), this.caster.m_20186_() + (double)this.caster.m_20192_() - eyeOffset, this.caster.m_20189_());
    }

    private void updatedCalculateEndPos() {
        if (this.m_9236_().m_5776_()) {
            this.endPosX = this.m_20185_() + 30.0 * Math.cos(this.renderYaw) * Math.cos(this.renderPitch);
            this.endPosZ = this.m_20189_() + 30.0 * Math.sin(this.renderYaw) * Math.cos(this.renderPitch);
            this.endPosY = this.m_20186_() + 30.0 * Math.sin(this.renderPitch);
        } else {
            this.endPosX = this.m_20185_() + 30.0 * Math.cos(this.getYaw()) * Math.cos(this.getPitch());
            this.endPosZ = this.m_20189_() + 30.0 * Math.sin(this.getYaw()) * Math.cos(this.getPitch());
            this.endPosY = this.m_20186_() + 30.0 * Math.sin(this.getPitch());
        }
    }

    public DeathLaserbeamHitResult updatedRaytraceEntities(Level world, Vec3 from, Vec3 to, boolean stopOnLiquid, boolean ignoreBlockWithoutBoundingBox, boolean returnLastUncollidableBlock) {
        DeathLaserbeamHitResult result = new DeathLaserbeamHitResult();
        result.setBlockHit((HitResult)world.m_45547_(new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, (Entity)this)));
        if (result.blockHit != null) {
            Vec3 hitVec = result.blockHit.m_82450_();
            this.collidePosX = hitVec.f_82479_;
            this.collidePosY = hitVec.f_82480_;
            this.collidePosZ = hitVec.f_82481_;
            this.blockSide = result.blockHit.m_82434_();
        } else {
            this.collidePosX = this.endPosX;
            this.collidePosY = this.endPosY;
            this.collidePosZ = this.endPosZ;
            this.blockSide = null;
        }
        List entities = world.m_45976_(LivingEntity.class, new AABB(Math.min(this.m_20185_(), this.collidePosX), Math.min(this.m_20186_(), this.collidePosY), Math.min(this.m_20189_(), this.collidePosZ), Math.max(this.m_20185_(), this.collidePosX), Math.max(this.m_20186_(), this.collidePosY), Math.max(this.m_20189_(), this.collidePosZ)).m_82377_(1.0, 1.0, 1.0));
        for (LivingEntity entity : entities) {
            if (entity == this.caster) continue;
            float pad = entity.m_6143_() + 0.5f;
            AABB aabb = entity.m_20191_().m_82377_((double)pad, (double)pad, (double)pad);
            Optional hit = aabb.m_82371_(from, to);
            if (aabb.m_82390_(from)) {
                result.addEntityHit(entity);
                continue;
            }
            if (!hit.isPresent()) continue;
            result.addEntityHit(entity);
        }
        return result;
    }

    public static class DeathLaserbeamHitResult {
        private BlockHitResult blockHit;
        private final List<LivingEntity> entities = new ArrayList<LivingEntity>();

        public BlockHitResult getBlockHit() {
            return this.blockHit;
        }

        public void setBlockHit(HitResult rayTraceResult) {
            if (rayTraceResult.m_6662_() == HitResult.Type.BLOCK) {
                this.blockHit = (BlockHitResult)rayTraceResult;
            }
        }

        public void addEntityHit(LivingEntity entity) {
            this.entities.add(entity);
        }
    }
}

