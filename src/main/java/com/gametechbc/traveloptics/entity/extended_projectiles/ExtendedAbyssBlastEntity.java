/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.client.particle.LightningParticle$OrbData
 *  com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan.Abyss_Blast_Entity
 *  com.github.L_Ender.cataclysm.init.ModEffect
 *  com.github.L_Ender.cataclysm.init.ModTag
 *  com.github.L_Ender.cataclysm.util.CMDamageTypes
 *  io.redspace.ironsspellbooks.api.spells.AbstractSpell
 *  io.redspace.ironsspellbooks.damage.DamageSources
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.util.Mth
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.ClipContext
 *  net.minecraft.world.level.ClipContext$Block
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
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
import com.github.L_Ender.cataclysm.client.particle.LightningParticle;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan.Abyss_Blast_Entity;
import com.github.L_Ender.cataclysm.init.ModEffect;
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
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;

public class ExtendedAbyssBlastEntity
extends Abyss_Blast_Entity {
    public ExtendedAbyssBlastEntity(EntityType<? extends Abyss_Blast_Entity> type, Level world) {
        super(type, world);
    }

    public ExtendedAbyssBlastEntity(EntityType<? extends Abyss_Blast_Entity> type, Level world, LivingEntity caster, double x, double y, double z, float yaw, float pitch, int duration, float direction, float damage, float Hpdamage) {
        super(type, world, caster, x, y, z, yaw, pitch, duration, direction, damage, Hpdamage);
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
            this.renderYaw = (float)((double)(this.caster.f_20885_ + this.getBeamDirection()) * Math.PI / 180.0);
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
            this.calculateUpdatedEndPos();
            List<LivingEntity> hit = this.updatedRaytraceEntities((Level)this.m_9236_(), (Vec3)new Vec3((double)this.m_20185_(), (double)this.m_20186_(), (double)this.m_20189_()), (Vec3)new Vec3((double)this.endPosX, (double)this.endPosY, (double)this.endPosZ)).entities;
            if (this.blockSide != null) {
                this.spawnUpdatedExplosionParticles(3);
                if (((Boolean)SpellsConfig.abyssalBlastBlockBreaking.get()).booleanValue() && !this.m_9236_().f_46443_) {
                    for (BlockPos pos : BlockPos.m_121976_((int)Mth.m_14107_((double)(this.collidePosX - 0.5)), (int)Mth.m_14107_((double)(this.collidePosY - 0.5)), (int)Mth.m_14107_((double)(this.collidePosZ - 0.5)), (int)Mth.m_14107_((double)(this.collidePosX + 0.5)), (int)Mth.m_14107_((double)(this.collidePosY + 0.5)), (int)Mth.m_14107_((double)(this.collidePosZ + 0.5)))) {
                        BlockState block = this.m_9236_().m_8055_(pos);
                        if (block.m_60795_() || block.m_204336_(ModTag.LEVIATHAN_IMMUNE) || !ForgeEventFactory.getMobGriefingEvent((Level)this.m_9236_(), (Entity)this)) continue;
                        this.m_9236_().m_46961_(pos, false);
                    }
                }
            }
            if (!this.m_9236_().f_46443_) {
                for (LivingEntity target : hit) {
                    MobEffectInstance effectinstance;
                    int i;
                    MobEffectInstance effectinstance1;
                    boolean flag;
                    if (((Boolean)SpellsConfig.abyssalBlastDealMagicDamage.get()).booleanValue()) {
                        if (this.caster == null || this.caster.m_7307_((Entity)target) || target == this.caster || !(flag = target.m_6469_(CMDamageTypes.causeDeathLaserDamage((Entity)this, (LivingEntity)this.caster), (float)((double)this.getDamage() + Math.min((double)this.getDamage(), (double)(target.m_21233_() * this.getHpDamage()) * 0.01))))) continue;
                        effectinstance1 = target.m_21124_((MobEffect)ModEffect.EFFECTABYSSAL_BURN.get());
                        i = 1;
                        if (effectinstance1 != null) {
                            i += effectinstance1.m_19564_();
                            target.m_6234_((MobEffect)ModEffect.EFFECTABYSSAL_BURN.get());
                        } else {
                            --i;
                        }
                        i = Mth.m_14045_((int)i, (int)0, (int)3);
                        effectinstance = new MobEffectInstance((MobEffect)ModEffect.EFFECTABYSSAL_BURN.get(), 160, i, false, true, true);
                        target.m_7292_(effectinstance);
                        continue;
                    }
                    if (this.caster == null || this.caster.m_7307_((Entity)target) || target == this.caster || !(flag = DamageSources.applyDamage((Entity)target, (float)((float)((double)this.getDamage() + Math.min((double)this.getDamage(), (double)(target.m_21233_() * this.getHpDamage()) * 0.01))), (DamageSource)((AbstractSpell)TravelopticsSpells.ABYSSAL_BLAST_SPELL.get()).getDamageSource((Entity)this, (Entity)this.caster)))) continue;
                    effectinstance1 = target.m_21124_((MobEffect)ModEffect.EFFECTABYSSAL_BURN.get());
                    i = 1;
                    if (effectinstance1 != null) {
                        i += effectinstance1.m_19564_();
                        target.m_6234_((MobEffect)ModEffect.EFFECTABYSSAL_BURN.get());
                    } else {
                        --i;
                    }
                    i = Mth.m_14045_((int)i, (int)0, (int)3);
                    effectinstance = new MobEffectInstance((MobEffect)ModEffect.EFFECTABYSSAL_BURN.get(), 160, i, false, true, true);
                    target.m_7292_(effectinstance);
                }
            }
        }
        if (this.f_19797_ - 20 > this.getDuration()) {
            this.on = false;
        }
    }

    private void spawnUpdatedExplosionParticles(int amount) {
        for (int i = 0; i < amount; ++i) {
            float velocity = 1.0f;
            float yaw = (float)((double)(this.f_19796_.m_188501_() * 2.0f) * Math.PI);
            float motionY = this.f_19796_.m_188501_() * 0.08f;
            float motionX = 1.0f * Mth.m_14089_((float)yaw);
            float motionZ = 1.0f * Mth.m_14031_((float)yaw);
            this.m_9236_().m_7106_((ParticleOptions)new LightningParticle.OrbData(102, 26, 204), this.collidePosX, this.collidePosY + 0.1, this.collidePosZ, (double)motionX, (double)motionY, (double)motionZ);
        }
    }

    private void calculateUpdatedEndPos() {
        if (this.m_9236_().m_5776_()) {
            this.endPosX = this.m_20185_() + 50.0 * Math.cos(this.renderYaw) * Math.cos(this.renderPitch);
            this.endPosZ = this.m_20189_() + 50.0 * Math.sin(this.renderYaw) * Math.cos(this.renderPitch);
            this.endPosY = this.m_20186_() + 50.0 * Math.sin(this.renderPitch);
        } else {
            this.endPosX = this.m_20185_() + 50.0 * Math.cos(this.getYaw()) * Math.cos(this.getPitch());
            this.endPosZ = this.m_20189_() + 50.0 * Math.sin(this.getYaw()) * Math.cos(this.getPitch());
            this.endPosY = this.m_20186_() + 50.0 * Math.sin(this.getPitch());
        }
    }

    private void updateWithLivingEntity() {
        this.setYaw((float)((double)(this.caster.f_20885_ + 90.0f) * Math.PI / 180.0));
        this.setPitch((float)((double)(-this.caster.m_146909_()) * Math.PI / 180.0));
        double eyeOffset = 0.06;
        this.m_6034_(this.caster.m_20185_(), this.caster.m_20186_() + (double)this.caster.m_20192_() - eyeOffset, this.caster.m_20189_());
    }

    public AbyssLaserbeamHitResult updatedRaytraceEntities(Level world, Vec3 from, Vec3 to) {
        AbyssLaserbeamHitResult updatedResult = new AbyssLaserbeamHitResult();
        updatedResult.setBlockHit((HitResult)world.m_45547_(new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, (Entity)this)));
        if (updatedResult.blockHit != null) {
            Vec3 hitVec = updatedResult.blockHit.m_82450_();
            this.collidePosX = hitVec.f_82479_;
            this.collidePosY = hitVec.f_82480_;
            this.collidePosZ = hitVec.f_82481_;
            this.blockSide = updatedResult.blockHit.m_82434_();
        } else {
            this.collidePosX = this.endPosX;
            this.collidePosY = this.endPosY;
            this.collidePosZ = this.endPosZ;
            this.blockSide = null;
        }
        List entities = world.m_45976_(LivingEntity.class, new AABB(Math.min(this.m_20185_(), this.collidePosX), Math.min(this.m_20186_(), this.collidePosY), Math.min(this.m_20189_(), this.collidePosZ), Math.max(this.m_20185_(), this.collidePosX), Math.max(this.m_20186_(), this.collidePosY), Math.max(this.m_20189_(), this.collidePosZ)).m_82377_(2.0, 2.0, 2.0));
        for (LivingEntity entity : entities) {
            if (entity == this.caster) continue;
            float pad = entity.m_6143_() + 0.5f;
            AABB aabb = entity.m_20191_().m_82377_((double)pad, (double)pad, (double)pad);
            Optional hit = aabb.m_82371_(from, to);
            if (aabb.m_82390_(from)) {
                updatedResult.addEntityHit(entity);
                continue;
            }
            if (!hit.isPresent()) continue;
            updatedResult.addEntityHit(entity);
        }
        return updatedResult;
    }

    public static class AbyssLaserbeamHitResult {
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

