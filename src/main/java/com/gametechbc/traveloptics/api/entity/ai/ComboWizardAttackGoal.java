/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.api.entity.IMagicEntity
 *  io.redspace.ironsspellbooks.api.magic.MagicData
 *  io.redspace.ironsspellbooks.api.registry.SpellRegistry
 *  io.redspace.ironsspellbooks.api.spells.AbstractSpell
 *  io.redspace.ironsspellbooks.api.spells.SpellData
 *  io.redspace.ironsspellbooks.api.util.Utils
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Position
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.entity.ai.util.DefaultRandomPos
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.VoxelShape
 */
package com.gametechbc.traveloptics.api.entity.ai;

import io.redspace.ironsspellbooks.api.entity.IMagicEntity;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.SpellData;
import io.redspace.ironsspellbooks.api.util.Utils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ComboWizardAttackGoal
extends Goal {
    protected LivingEntity target;
    protected final double speedModifier;
    protected final int attackIntervalMin;
    protected final int attackIntervalMax;
    protected float attackRadius;
    protected float attackRadiusSqr;
    protected boolean shortCircuitTemp = false;
    protected boolean hasLineOfSight;
    protected int seeTime = 0;
    protected int strafeTime;
    protected boolean strafingClockwise;
    protected int attackTime = -1;
    protected int projectileCount;
    protected AbstractSpell singleUseSpell = SpellRegistry.none();
    protected int singleUseDelay;
    protected int singleUseLevel;
    protected boolean isFlying;
    protected boolean allowFleeing;
    protected int fleeCooldown;
    protected final ArrayList<ArrayList<AbstractSpell>> comboSpells = new ArrayList();
    protected int currentComboIndex = 0;
    protected int currentSpellInComboIndex = 0;
    protected int comboCooldown = 0;
    protected int comboCooldownMin = 20;
    protected int comboCooldownMax = 60;
    protected float minSpellQuality = 0.1f;
    protected float maxSpellQuality = 0.4f;
    protected boolean drinksPotions;
    protected final PathfinderMob mob;
    protected final IMagicEntity spellCastingMob;

    public ComboWizardAttackGoal(IMagicEntity abstractSpellCastingMob, double pSpeedModifier, int pAttackInterval) {
        this(abstractSpellCastingMob, pSpeedModifier, pAttackInterval, pAttackInterval);
    }

    public ComboWizardAttackGoal(IMagicEntity abstractSpellCastingMob, double pSpeedModifier, int pAttackIntervalMin, int pAttackIntervalMax) {
        PathfinderMob m;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        this.spellCastingMob = abstractSpellCastingMob;
        if (!(abstractSpellCastingMob instanceof PathfinderMob)) {
            throw new IllegalStateException("Unable to add " + ((Object)((Object)this)).getClass().getSimpleName() + "to entity, must extend PathfinderMob.");
        }
        this.mob = m = (PathfinderMob)abstractSpellCastingMob;
        this.speedModifier = pSpeedModifier;
        this.attackIntervalMin = pAttackIntervalMin;
        this.attackIntervalMax = pAttackIntervalMax;
        this.attackRadius = 20.0f;
        this.attackRadiusSqr = this.attackRadius * this.attackRadius;
        this.allowFleeing = true;
        for (int i = 0; i < 10; ++i) {
            this.comboSpells.add(new ArrayList());
        }
    }

    public ComboWizardAttackGoal setComboSpells(List<List<AbstractSpell>> combos) {
        for (ArrayList<AbstractSpell> combo : this.comboSpells) {
            combo.clear();
        }
        for (int i = 0; i < Math.min(combos.size(), 10); ++i) {
            this.comboSpells.get(i).addAll((Collection<AbstractSpell>)combos.get(i));
        }
        return this;
    }

    public ComboWizardAttackGoal addCombo(int comboIndex, List<AbstractSpell> spells) {
        if (comboIndex >= 0 && comboIndex < 10) {
            this.comboSpells.get(comboIndex).clear();
            this.comboSpells.get(comboIndex).addAll(spells);
        }
        return this;
    }

    public ComboWizardAttackGoal setComboCooldown(int minCooldown, int maxCooldown) {
        this.comboCooldownMin = minCooldown;
        this.comboCooldownMax = maxCooldown;
        return this;
    }

    public ComboWizardAttackGoal setSpellQuality(float minSpellQuality, float maxSpellQuality) {
        this.minSpellQuality = minSpellQuality;
        this.maxSpellQuality = maxSpellQuality;
        return this;
    }

    public ComboWizardAttackGoal setSingleUseSpell(AbstractSpell abstractSpell, int minDelay, int maxDelay, int minLevel, int maxLevel) {
        this.singleUseSpell = abstractSpell;
        this.singleUseDelay = Utils.random.m_216332_(minDelay, maxDelay);
        this.singleUseLevel = Utils.random.m_216332_(minLevel, maxLevel);
        return this;
    }

    public ComboWizardAttackGoal setIsFlying() {
        this.isFlying = true;
        return this;
    }

    public ComboWizardAttackGoal setDrinksPotions() {
        this.drinksPotions = true;
        return this;
    }

    public ComboWizardAttackGoal setAllowFleeing(boolean allowFleeing) {
        this.allowFleeing = allowFleeing;
        return this;
    }

    public boolean m_8036_() {
        LivingEntity livingentity = this.mob.m_5448_();
        if (livingentity != null && livingentity.m_6084_()) {
            this.target = livingentity;
            return true;
        }
        return false;
    }

    public boolean m_8045_() {
        return this.m_8036_() || this.target.m_6084_() && !this.mob.m_21573_().m_26571_();
    }

    public void m_8041_() {
        this.target = null;
        this.seeTime = 0;
        this.attackTime = -1;
        this.mob.m_21561_(false);
        this.mob.m_21566_().m_24988_(0.0f, 0.0f);
    }

    public boolean m_183429_() {
        return true;
    }

    public void m_8037_() {
        if (this.target == null) {
            return;
        }
        double distanceSquared = this.mob.m_20275_(this.target.m_20185_(), this.target.m_20186_(), this.target.m_20189_());
        this.hasLineOfSight = this.mob.m_21574_().m_148306_((Entity)this.target);
        this.seeTime = this.hasLineOfSight ? ++this.seeTime : --this.seeTime;
        this.doMovement(distanceSquared);
        if (this.mob.m_21213_() == this.mob.f_19797_ - 1) {
            int t;
            this.attackTime = t = (int)(Mth.m_14179_((float)0.6f, (float)this.attackTime, (float)0.0f) + 1.0f);
        }
        this.handleAttackLogic(distanceSquared);
        if (this.comboCooldown > 0) {
            --this.comboCooldown;
        }
        --this.singleUseDelay;
    }

    protected void handleAttackLogic(double distanceSquared) {
        if (this.seeTime < -50) {
            return;
        }
        if (--this.attackTime == 0) {
            this.resetAttackTimer(distanceSquared);
            if (!this.spellCastingMob.isCasting() && !this.spellCastingMob.isDrinkingPotion()) {
                this.doSpellAction();
            }
        } else if (this.attackTime < 0) {
            this.attackTime = Mth.m_14107_((double)Mth.m_14139_((double)(Math.sqrt(distanceSquared) / (double)this.attackRadius), (double)this.attackIntervalMin, (double)this.attackIntervalMax));
        }
        if (this.spellCastingMob.isCasting()) {
            SpellData spellData = MagicData.getPlayerMagicData((LivingEntity)this.mob).getCastingSpell();
            if (this.target.m_21224_() || spellData.getSpell().shouldAIStopCasting(spellData.getLevel(), (Mob)this.mob, this.target)) {
                this.spellCastingMob.cancelCast();
            }
        }
    }

    protected void resetAttackTimer(double distanceSquared) {
        float f = (float)Math.sqrt(distanceSquared) / this.attackRadius;
        this.attackTime = Mth.m_14143_((float)(f * (float)(this.attackIntervalMax - this.attackIntervalMin) + (float)this.attackIntervalMin));
    }

    protected void doMovement(double distanceSquared) {
        double speed = (double)(this.spellCastingMob.isCasting() ? 0.75f : 1.0f) * this.movementSpeed();
        this.mob.m_21391_((Entity)this.target, 30.0f, 30.0f);
        float fleeDist = 0.275f;
        if (this.allowFleeing && !this.spellCastingMob.isCasting() && this.attackTime > 10 && --this.fleeCooldown <= 0 && distanceSquared < (double)(this.attackRadiusSqr * (fleeDist * fleeDist))) {
            Vec3 flee = DefaultRandomPos.m_148407_((PathfinderMob)this.mob, (int)16, (int)7, (Vec3)this.target.m_20182_());
            if (flee != null) {
                this.mob.m_21573_().m_26519_(flee.f_82479_, flee.f_82480_, flee.f_82481_, speed * 1.5);
            } else {
                this.mob.m_21566_().m_24988_(-((float)speed), (float)speed);
            }
        } else if (distanceSquared < (double)this.attackRadiusSqr && this.seeTime >= 5) {
            this.mob.m_21573_().m_26573_();
            if (++this.strafeTime > 25 && this.mob.m_217043_().m_188500_() < 0.1) {
                this.strafingClockwise = !this.strafingClockwise;
                this.strafeTime = 0;
            }
            float strafeForward = (distanceSquared * 6.0 < (double)this.attackRadiusSqr ? -1.0f : 0.5f) * 0.2f * (float)this.speedModifier;
            int strafeDir = this.strafingClockwise ? 1 : -1;
            this.mob.m_21566_().m_24988_(strafeForward, (float)speed * (float)strafeDir);
            if (this.mob.f_19862_ && this.mob.m_217043_().m_188501_() < 0.1f) {
                this.tryJump();
            }
        } else if (this.mob.f_19797_ % 5 == 0) {
            if (this.isFlying) {
                this.mob.m_21566_().m_6849_(this.target.m_20185_(), this.target.m_20186_() + 2.0, this.target.m_20189_(), this.speedModifier);
            } else {
                this.mob.m_21573_().m_5624_((Entity)this.target, this.speedModifier);
            }
        }
    }

    protected double movementSpeed() {
        return this.speedModifier * this.mob.m_21133_(Attributes.f_22279_) * 2.0;
    }

    protected void tryJump() {
        Vec3 nextBlock = new Vec3((double)this.mob.f_20900_, 0.0, (double)this.mob.f_20902_).m_82541_();
        BlockPos blockpos = BlockPos.m_274446_((Position)this.mob.m_20182_().m_82549_(nextBlock));
        BlockState blockstate = this.mob.m_9236_().m_8055_(blockpos);
        VoxelShape voxelshape = blockstate.m_60812_((BlockGetter)this.mob.m_9236_(), blockpos);
        if (!(voxelshape.m_83281_() || blockstate.m_204336_(BlockTags.f_13103_) || blockstate.m_204336_(BlockTags.f_13039_))) {
            BlockPos blockposAbove = blockpos.m_7494_();
            BlockState blockstateAbove = this.mob.m_9236_().m_8055_(blockposAbove);
            VoxelShape voxelshapeAbove = blockstateAbove.m_60812_((BlockGetter)this.mob.m_9236_(), blockposAbove);
            if (voxelshapeAbove.m_83281_()) {
                this.mob.m_21569_().m_24901_();
                this.mob.m_21570_(this.mob.f_20900_ * 5.0f);
                this.mob.m_21564_(this.mob.f_20902_ * 5.0f);
            }
        }
    }

    protected void doSpellAction() {
        if (!this.spellCastingMob.getHasUsedSingleAttack() && this.singleUseSpell != SpellRegistry.none() && this.singleUseDelay <= 0) {
            this.spellCastingMob.setHasUsedSingleAttack(true);
            this.spellCastingMob.initiateCastSpell(this.singleUseSpell, this.singleUseLevel);
            this.fleeCooldown = 7 + this.singleUseSpell.getCastTime(this.singleUseLevel);
            return;
        }
        if (this.drinksPotions && this.shouldDrinkPotion()) {
            this.spellCastingMob.startDrinkingPotion();
            return;
        }
        AbstractSpell spell = this.getNextComboSpell();
        if (spell != SpellRegistry.none()) {
            int spellLevel = this.calculateSpellLevel(spell);
            if (!spell.shouldAIStopCasting(spellLevel, (Mob)this.mob, this.target)) {
                this.spellCastingMob.initiateCastSpell(spell, spellLevel);
                this.fleeCooldown = 7 + spell.getCastTime(spellLevel);
            } else {
                this.attackTime = 5;
            }
        } else {
            this.attackTime = 5;
        }
    }

    protected int calculateSpellLevel(AbstractSpell spell) {
        int spellLevel = (int)((float)spell.getMaxLevel() * Mth.m_14179_((float)this.mob.m_217043_().m_188501_(), (float)this.minSpellQuality, (float)this.maxSpellQuality));
        return Math.max(spellLevel, 1);
    }

    protected boolean shouldDrinkPotion() {
        float health = this.mob.m_21223_() / this.mob.m_21233_();
        double distanceSquared = this.mob.m_20275_(this.target.m_20185_(), this.target.m_20186_(), this.target.m_20189_());
        double distancePercent = Mth.m_14008_((double)(distanceSquared / (double)this.attackRadiusSqr), (double)0.0, (double)1.0);
        return (double)this.mob.m_217043_().m_188501_() < (double)(0.3f * (1.0f - health)) * distancePercent;
    }

    protected AbstractSpell getNextComboSpell() {
        ArrayList<AbstractSpell> currentCombo;
        boolean hasValidCombos = false;
        for (ArrayList<AbstractSpell> combo : this.comboSpells) {
            if (combo.isEmpty()) continue;
            hasValidCombos = true;
            break;
        }
        if (!hasValidCombos) {
            return SpellRegistry.none();
        }
        if (this.comboCooldown > 0) {
            return SpellRegistry.none();
        }
        ArrayList<Integer> validComboIndices = new ArrayList<Integer>();
        for (int i = 0; i < this.comboSpells.size(); ++i) {
            if (this.comboSpells.get(i).isEmpty()) continue;
            validComboIndices.add(i);
        }
        if (validComboIndices.isEmpty()) {
            return SpellRegistry.none();
        }
        if (this.currentSpellInComboIndex > 0 && this.currentSpellInComboIndex < (currentCombo = this.comboSpells.get(this.currentComboIndex)).size()) {
            AbstractSpell spell = currentCombo.get(this.currentSpellInComboIndex);
            ++this.currentSpellInComboIndex;
            if (this.currentSpellInComboIndex >= currentCombo.size()) {
                this.currentSpellInComboIndex = 0;
                this.comboCooldown = Utils.random.m_216332_(this.comboCooldownMin, this.comboCooldownMax);
            }
            return spell;
        }
        this.currentComboIndex = (Integer)validComboIndices.get(this.mob.m_217043_().m_188503_(validComboIndices.size()));
        ArrayList<AbstractSpell> selectedCombo = this.comboSpells.get(this.currentComboIndex);
        if (selectedCombo.isEmpty()) {
            return SpellRegistry.none();
        }
        this.currentSpellInComboIndex = 1;
        return selectedCombo.get(0);
    }

    public void m_8056_() {
        super.m_8056_();
        this.mob.m_21561_(true);
    }
}

