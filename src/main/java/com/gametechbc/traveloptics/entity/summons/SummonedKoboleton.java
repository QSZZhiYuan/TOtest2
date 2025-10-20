/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.entity.AnimationMonster.Koboleton_Entity
 *  com.github.L_Ender.cataclysm.init.ModParticle
 *  io.redspace.ironsspellbooks.api.spells.AbstractSpell
 *  io.redspace.ironsspellbooks.api.util.Utils
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  io.redspace.ironsspellbooks.effect.SummonTimer
 *  io.redspace.ironsspellbooks.entity.mobs.goals.GenericCopyOwnerTargetGoal
 *  io.redspace.ironsspellbooks.entity.mobs.goals.GenericFollowOwnerGoal
 *  io.redspace.ironsspellbooks.entity.mobs.goals.GenericHurtByTargetGoal
 *  io.redspace.ironsspellbooks.entity.mobs.goals.GenericOwnerHurtByTargetGoal
 *  io.redspace.ironsspellbooks.entity.mobs.goals.GenericOwnerHurtTargetGoal
 *  io.redspace.ironsspellbooks.util.OwnerHelper
 *  javax.annotation.Nullable
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.tags.DamageTypeTags
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.goal.FloatGoal
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.LookAtPlayerGoal
 *  net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
 *  net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 */
package com.gametechbc.traveloptics.entity.summons;

import com.gametechbc.traveloptics.api.entity.mobs.MagicFossilSummon;
import com.gametechbc.traveloptics.init.TravelopticsEffects;
import com.gametechbc.traveloptics.init.TravelopticsEntities;
import com.gametechbc.traveloptics.init.TravelopticsSpells;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.Koboleton_Entity;
import com.github.L_Ender.cataclysm.init.ModParticle;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.effect.SummonTimer;
import io.redspace.ironsspellbooks.entity.mobs.goals.GenericCopyOwnerTargetGoal;
import io.redspace.ironsspellbooks.entity.mobs.goals.GenericFollowOwnerGoal;
import io.redspace.ironsspellbooks.entity.mobs.goals.GenericHurtByTargetGoal;
import io.redspace.ironsspellbooks.entity.mobs.goals.GenericOwnerHurtByTargetGoal;
import io.redspace.ironsspellbooks.entity.mobs.goals.GenericOwnerHurtTargetGoal;
import io.redspace.ironsspellbooks.util.OwnerHelper;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class SummonedKoboleton
extends Koboleton_Entity
implements MagicFossilSummon {
    protected LivingEntity cachedSummoner;
    protected UUID summonerUUID;

    public SummonedKoboleton(EntityType<? extends Koboleton_Entity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.f_21364_ = 0;
    }

    public SummonedKoboleton(Level level, LivingEntity owner) {
        this((EntityType<? extends Koboleton_Entity>)((EntityType)TravelopticsEntities.SUMMONED_KOBOLETON.get()), level);
        this.setSummoner(owner);
    }

    public void m_8099_() {
        this.f_21345_.m_148105_().removeIf(goal -> goal.m_26015_() instanceof HurtByTargetGoal || goal.m_26015_() instanceof NearestAttackableTargetGoal);
        this.f_21346_.m_25352_(1, (Goal)new GenericOwnerHurtByTargetGoal((Mob)this, this::getSummoner));
        this.f_21346_.m_25352_(2, (Goal)new GenericOwnerHurtTargetGoal((Mob)this, this::getSummoner));
        this.f_21346_.m_25352_(3, (Goal)new GenericCopyOwnerTargetGoal((PathfinderMob)this, this::getSummoner));
        this.f_21346_.m_25352_(4, (Goal)new GenericHurtByTargetGoal((PathfinderMob)this, entity -> entity == this.getSummoner()).setAlertOthers(new Class[0]));
        this.f_21345_.m_25352_(7, (Goal)new GenericFollowOwnerGoal((PathfinderMob)this, this::getSummoner, (double)0.9f, 15.0f, 5.0f, false, 25.0f));
        this.f_21345_.m_25352_(0, (Goal)new FloatGoal((Mob)this));
        this.f_21345_.m_25352_(9, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 3.0f, 1.0f));
        this.f_21345_.m_25352_(10, (Goal)new LookAtPlayerGoal((Mob)this, Mob.class, 8.0f));
        super.m_8099_();
    }

    public LivingEntity getSummoner() {
        return OwnerHelper.getAndCacheOwner((Level)this.m_9236_(), (LivingEntity)this.cachedSummoner, (UUID)this.summonerUUID);
    }

    public void setSummoner(@Nullable LivingEntity owner) {
        if (owner != null) {
            this.summonerUUID = owner.m_20148_();
            this.cachedSummoner = owner;
        }
    }

    public void m_142687_(Entity.RemovalReason pReason) {
        super.m_142687_(pReason);
    }

    public void m_6667_(DamageSource pDamageSource) {
        this.onDeathHelper();
        super.m_6667_(pDamageSource);
    }

    public void onRemovedFromWorld() {
        this.onRemovedHelper((Entity)this, (SummonTimer)TravelopticsEffects.DESERT_DWELLER_TIMER.get());
        super.onRemovedFromWorld();
    }

    public void m_7378_(CompoundTag compoundTag) {
        super.m_7378_(compoundTag);
        this.summonerUUID = OwnerHelper.deserializeOwner((CompoundTag)compoundTag);
    }

    public void m_7380_(CompoundTag compoundTag) {
        super.m_7380_(compoundTag);
        OwnerHelper.serializeOwner((CompoundTag)compoundTag, (UUID)this.summonerUUID);
    }

    public boolean m_7327_(Entity pEntity) {
        return Utils.doMeleeAttack((Mob)this, (Entity)pEntity, (DamageSource)((AbstractSpell)TravelopticsSpells.SUMMON_DESERT_DWELLERS.get()).getDamageSource((Entity)this, (Entity)this.getSummoner()));
    }

    public boolean m_7307_(Entity pEntity) {
        return super.m_7307_(pEntity) || this.isAlliedHelper(pEntity);
    }

    public void onUnSummon() {
        if (!this.m_9236_().f_46443_) {
            MagicManager.spawnParticles((Level)this.m_9236_(), (ParticleOptions)((ParticleOptions)ModParticle.SANDSTORM.get()), (double)this.m_20185_(), (double)(this.m_20186_() + 0.5), (double)this.m_20189_(), (int)25, (double)0.4, (double)0.8, (double)0.4, (double)0.03, (boolean)false);
            this.m_146870_();
        }
    }

    public boolean m_6469_(DamageSource pSource, float pAmount) {
        if (!pSource.m_269533_(DamageTypeTags.f_268738_) && this.shouldIgnoreDamage(pSource)) {
            return false;
        }
        return super.m_6469_(pSource, pAmount);
    }

    protected boolean m_8028_() {
        return false;
    }
}

