/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.api.entity.IMagicEntity
 *  io.redspace.ironsspellbooks.api.registry.AttributeRegistry
 *  io.redspace.ironsspellbooks.api.registry.SpellRegistry
 *  io.redspace.ironsspellbooks.api.spells.AbstractSpell
 *  io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMob
 *  javax.annotation.Nullable
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.util.Mth
 *  net.minecraft.world.DifficultyInstance
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.SpawnGroupData
 *  net.minecraft.world.entity.ai.attributes.Attribute
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.control.FlyingMoveControl
 *  net.minecraft.world.entity.ai.control.LookControl
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.LookAtPlayerGoal
 *  net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal
 *  net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
 *  net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
 *  net.minecraft.world.entity.ai.navigation.FlyingPathNavigation
 *  net.minecraft.world.entity.ai.navigation.PathNavigation
 *  net.minecraft.world.entity.monster.Enemy
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.ServerLevelAccessor
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.Vec3
 *  software.bernie.geckolib.core.animatable.GeoAnimatable
 *  software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache
 *  software.bernie.geckolib.core.animation.AnimatableManager$ControllerRegistrar
 *  software.bernie.geckolib.core.animation.AnimationController
 *  software.bernie.geckolib.core.animation.AnimationState
 *  software.bernie.geckolib.core.animation.RawAnimation
 *  software.bernie.geckolib.core.object.PlayState
 *  software.bernie.geckolib.util.GeckoLibUtil
 */
package com.gametechbc.traveloptics.entity.mobs.void_tome;

import com.gametechbc.traveloptics.api.particle.ParticleDirection;
import com.gametechbc.traveloptics.api.particle.SphereParticleManager;
import com.gametechbc.traveloptics.entity.mobs.void_tome.VoidTomeComboGoal;
import com.gametechbc.traveloptics.init.TravelopticsEntities;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import com.gametechbc.traveloptics.init.TravelopticsSpells;
import com.gametechbc.traveloptics.util.TravelopticsParticleHelper;
import com.gametechbc.traveloptics.util.TravelopticsTags;
import io.redspace.ironsspellbooks.api.entity.IMagicEntity;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMob;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class VoidTomeEntity
extends AbstractSpellCastingMob
implements Enemy {
    private int castingAnimationTimer = 0;
    private boolean hasPlayedFallSound = false;
    private static final EntityDataAccessor<Boolean> SUMMONED_ENTITY = SynchedEntityData.m_135353_(VoidTomeEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    private final AnimationController<VoidTomeEntity> animationController = new AnimationController((GeoAnimatable)this, "controller", 10, this::predicate);
    private final RawAnimation casting = RawAnimation.begin().thenPlay("flying_book_cast");
    private final RawAnimation casting_1 = RawAnimation.begin().thenPlay("flying_book_cast_1");
    private final RawAnimation casting_2 = RawAnimation.begin().thenPlay("flying_book_cast_2");
    private final RawAnimation casting_3 = RawAnimation.begin().thenPlay("flying_book_cast_3");
    private final RawAnimation death = RawAnimation.begin().thenPlay("flying_book_death");
    private RawAnimation animationToPlay = null;

    public VoidTomeEntity(Level pLevel) {
        this((EntityType<? extends AbstractSpellCastingMob>)((EntityType)TravelopticsEntities.VOID_TOME_ENTITY.get()), pLevel);
    }

    public VoidTomeEntity(EntityType<? extends AbstractSpellCastingMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.f_21364_ = 10;
        this.f_21365_ = this.createLookControl();
        this.f_21342_ = this.createMoveControl();
    }

    protected void m_8099_() {
        this.f_21345_.m_25352_(1, (Goal)new VoidTomeComboGoal((IMagicEntity)this, 1.25, 40, 40).addCombo(1, List.of((AbstractSpell)SpellRegistry.MAGIC_MISSILE_SPELL.get(), (AbstractSpell)SpellRegistry.MAGIC_MISSILE_SPELL.get(), (AbstractSpell)SpellRegistry.MAGIC_ARROW_SPELL.get())).setComboCooldown(80, 80).setSpellQuality(0.6f, 0.8f).setSingleUseSpell((AbstractSpell)TravelopticsSpells.ORBITAL_VOID_SPELL.get(), 200, 400, 1, 1));
        this.f_21345_.m_25352_(5, (Goal)new WaterAvoidingRandomFlyingGoal((PathfinderMob)this, 0.75));
        this.f_21345_.m_25352_(9, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 3.0f, 1.0f));
        this.f_21345_.m_25352_(10, (Goal)new LookAtPlayerGoal((Mob)this, Mob.class, 8.0f));
        this.f_21346_.m_25352_(1, (Goal)new HurtByTargetGoal((PathfinderMob)this, new Class[0]));
        this.f_21346_.m_25352_(2, (Goal)new NearestAttackableTargetGoal((Mob)this, Player.class, true));
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return LivingEntity.m_21183_().m_22268_(Attributes.f_22281_, 3.0).m_22268_(Attributes.f_22282_, 0.0).m_22268_(Attributes.f_22278_, 0.8).m_22268_(Attributes.f_22276_, 40.0).m_22268_(Attributes.f_22277_, 32.0).m_22268_(Attributes.f_22279_, 0.25).m_22268_(Attributes.f_22280_, 0.3).m_22268_((Attribute)AttributeRegistry.SPELL_POWER.get(), 1.0).m_22268_((Attribute)AttributeRegistry.SPELL_RESIST.get(), 1.0);
    }

    @Nullable
    public SpawnGroupData m_6518_(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        this.m_20242_(true);
        return super.m_6518_(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    public void m_8119_() {
        super.m_8119_();
        if (this.castingAnimationTimer > 0) {
            --this.castingAnimationTimer;
        }
        if (this.m_9236_().f_46443_) {
            if (this.m_21224_()) {
                this.spawnAdditionalParticles(0);
            } else if (this.isCasting()) {
                this.spawnAdditionalParticles(1);
            } else {
                this.spawnPassiveMagicParticles();
            }
        }
    }

    private void spawnPassiveMagicParticles() {
        float time = this.m_9236_().m_46467_() % 360L;
        float rotationSpeed = 6.0f;
        float radius = 1.5f;
        double extraYOffset = 0.5;
        float angle = time * rotationSpeed * ((float)Math.PI / 180);
        double offsetX = Math.cos(angle) * (double)radius;
        double offsetZ = Math.sin(angle) * (double)radius;
        double posY = this.m_20186_() + extraYOffset;
        this.m_9236_().m_7106_(TravelopticsParticleHelper.LIGHT_PURPLE_GLOWING_ENCHANT, this.m_20185_() + offsetX, posY, this.m_20189_() + offsetZ, 0.0, 0.0, 0.0);
    }

    private void spawnAdditionalParticles(int type) {
        double radius = 2.5;
        double height = 1.2;
        double angle = Math.random() * 2.0 * Math.PI;
        double distance = Math.random() * radius;
        double xOffset = Math.cos(angle) * distance;
        double zOffset = Math.sin(angle) * distance;
        double yOffset = (Math.random() - 0.5) * height;
        double fromX = this.m_20185_() + xOffset;
        double fromY = this.m_20186_() + 0.5 + yOffset;
        double fromZ = this.m_20189_() + zOffset;
        double toX = this.m_20185_() - fromX;
        double toY = this.m_20186_() + 0.5 - fromY;
        double toZ = this.m_20189_() - fromZ;
        double length = Math.sqrt(toX * toX + toY * toY + toZ * toZ);
        double speed = 0.15;
        toX = toX / length * speed;
        toY = toY / length * speed;
        toZ = toZ / length * speed;
        if (type == 0) {
            this.m_9236_().m_7106_(TravelopticsParticleHelper.LIGHT_PURPLE_GLOWING_ENCHANT, fromX, fromY, fromZ, toX, toY, toZ);
        }
        if (type == 1) {
            this.m_9236_().m_7106_(TravelopticsParticleHelper.LIGHT_RED_GLOWING_ENCHANT, fromX, fromY, fromZ, toX, toY, toZ);
        }
    }

    protected void m_8024_() {
        super.m_8024_();
        LivingEntity target = this.m_5448_();
        if (target != null) {
            Vec3 direction = target.m_20182_().m_82546_(this.m_20182_()).m_82541_();
            float targetYaw = (float)(Mth.m_14136_((double)direction.f_82481_, (double)direction.f_82479_) * 57.29577951308232) - 90.0f;
            this.m_146922_(Mth.m_14148_((float)this.m_146908_(), (float)targetYaw, (float)15.0f));
            this.f_20883_ = this.m_146908_();
            this.f_20885_ = this.m_146908_();
        }
        if (!this.m_20072_()) {
            BlockPos groundPos = this.m_20183_().m_7495_();
            while (this.m_9236_().m_46859_(groundPos) && groundPos.m_123342_() > this.m_9236_().m_141937_()) {
                groundPos = groundPos.m_7495_();
            }
            double groundY = groundPos.m_123342_() + 1;
            double baseHoverOffset = 3.5;
            double hoverTargetY = groundY + baseHoverOffset;
            double amplitude = 0.25;
            double speed = 0.05;
            double phaseOffset = (double)this.m_19879_() * 7.3;
            double deltaY = (hoverTargetY += amplitude * Math.sin(((double)this.f_19797_ + phaseOffset) * speed)) - this.m_20186_();
            if (Math.abs(deltaY) > 0.05) {
                Vec3 motion = this.m_20184_();
                this.m_20334_(motion.f_82479_, deltaY * 0.2, motion.f_82481_);
            }
            double maxVerticalSpeed = 0.08;
            this.m_20334_(this.m_20184_().f_82479_, Mth.m_14008_((double)this.m_20184_().f_82480_, (double)(-maxVerticalSpeed), (double)maxVerticalSpeed), this.m_20184_().f_82481_);
        }
    }

    protected void m_6153_() {
        ++this.f_20919_;
        if (!this.m_20096_()) {
            this.m_20256_(this.m_20184_().m_82542_(1.0, 0.9, 1.0).m_82520_(0.0, -0.4, 0.0));
        } else if (!this.hasPlayedFallSound) {
            this.m_5496_((SoundEvent)TravelopticsSounds.FLYING_BOOK_DEATH_FALL.get(), 0.6f, 0.8f + this.f_19796_.m_188501_() * 0.3f);
            this.hasPlayedFallSound = true;
        }
        switch (this.f_20919_) {
            case 50: {
                this.m_5496_((SoundEvent)TravelopticsSounds.FLYING_BOOK_PAGE_TURN.get(), 0.4f, 0.8f + this.f_19796_.m_188501_() * 0.3f);
                break;
            }
            case 56: {
                this.m_5496_((SoundEvent)TravelopticsSounds.FLYING_BOOK_PAGE_TURN.get(), 0.4f, 0.8f + this.f_19796_.m_188501_() * 0.3f);
                break;
            }
            case 61: {
                this.m_5496_((SoundEvent)TravelopticsSounds.FLYING_BOOK_PAGE_TURN.get(), 0.4f, 0.8f + this.f_19796_.m_188501_() * 0.3f);
                break;
            }
            case 68: {
                this.m_5496_((SoundEvent)TravelopticsSounds.FLYING_BOOK_BOOK_CLOSE.get(), 0.7f, 0.8f + this.f_19796_.m_188501_() * 0.3f);
            }
        }
        if (this.f_20919_ == 78) {
            this.m_142687_(Entity.RemovalReason.KILLED);
        }
    }

    public boolean m_6469_(DamageSource source, float amount) {
        boolean result = super.m_6469_(source, amount);
        if (result && this.m_6084_() && Math.random() < 0.4) {
            this.m_5496_((SoundEvent)TravelopticsSounds.FLYING_BOOK_HURT.get(), 0.6f, 0.8f + this.f_19796_.m_188501_() * 0.3f);
        }
        return result;
    }

    protected PathNavigation m_6037_(Level pLevel) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation((Mob)this, pLevel);
        flyingpathnavigation.m_26440_(false);
        flyingpathnavigation.m_7008_(true);
        flyingpathnavigation.m_26443_(true);
        return flyingpathnavigation;
    }

    protected LookControl createLookControl() {
        return new LookControl((Mob)this){

            protected float m_24956_(float pFrom, float pTo, float pMaxDelta) {
                return super.m_24956_(pFrom, pTo, pMaxDelta * 2.5f);
            }

            protected boolean m_8106_() {
                return !VoidTomeEntity.this.isCasting();
            }
        };
    }

    protected FlyingMoveControl createMoveControl() {
        return new FlyingMoveControl((Mob)this, 20, true){

            protected float m_24991_(float sourceAngle, float targetAngle, float maxChange) {
                double dz;
                double dx = this.f_24975_ - this.f_24974_.m_20185_();
                if (dx * dx + (dz = this.f_24977_ - this.f_24974_.m_20189_()) * dz < 0.5) {
                    return sourceAngle;
                }
                return super.m_24991_(sourceAngle, targetAngle, maxChange * 0.25f);
            }
        };
    }

    public void castComplete() {
        super.castComplete();
        int animIndex = this.m_217043_().m_188503_(4);
        String spellAnim = switch (animIndex) {
            default -> "casting";
            case 1 -> "casting_1";
            case 2 -> "casting_2";
            case 3 -> "casting_3";
        };
        this.triggerAnim("book_casting", spellAnim);
        this.castingAnimationTimer = 10;
        SphereParticleManager.spawnParticles(this.m_9236_(), (Entity)this, 10, (ParticleOptions)ParticleTypes.f_123810_, ParticleDirection.OUTWARD, 3.5);
    }

    public boolean isSummonedEntity() {
        return (Boolean)this.f_19804_.m_135370_(SUMMONED_ENTITY);
    }

    public void setIsSummoned() {
        this.f_19804_.m_135381_(SUMMONED_ENTITY, (Object)true);
    }

    protected boolean m_6125_() {
        return super.m_6125_() && !this.isSummonedEntity();
    }

    public boolean m_6149_() {
        return super.m_6149_() && !this.isSummonedEntity();
    }

    public boolean m_142079_() {
        return false;
    }

    public boolean m_5825_() {
        return false;
    }

    public boolean m_7301_(MobEffectInstance pEffectInstance) {
        return false;
    }

    public boolean m_213854_() {
        return true;
    }

    protected void m_7840_(double y, boolean onGround, BlockState state, BlockPos pos) {
    }

    protected void m_7355_(BlockPos pos, BlockState state) {
    }

    public boolean isDrinkingPotion() {
        return false;
    }

    public void startDrinkingPotion() {
    }

    public boolean m_7307_(Entity entityIn) {
        if (entityIn == this) {
            return true;
        }
        if (super.m_7307_(entityIn)) {
            return true;
        }
        if (entityIn.m_6095_().m_204039_(TravelopticsTags.TEAM_THE_NIGHTWARDEN)) {
            return this.m_5647_() == null && entityIn.m_5647_() == null;
        }
        return false;
    }

    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(SUMMONED_ENTITY, (Object)false);
    }

    public void m_7380_(CompoundTag tag) {
        super.m_7380_(tag);
        tag.m_128379_("HasPlayedFallSound", this.hasPlayedFallSound);
        if (this.isSummonedEntity()) {
            tag.m_128379_("summoned", true);
        }
    }

    public void m_7378_(CompoundTag tag) {
        super.m_7378_(tag);
        this.hasPlayedFallSound = tag.m_128471_("HasPlayedFallSound");
        if (tag.m_128471_("summoned")) {
            this.setIsSummoned();
        }
    }

    private PlayState predicate(AnimationState<VoidTomeEntity> event) {
        if (this.animationToPlay != null) {
            event.getController().forceAnimationReset();
            event.getController().setAnimation(this.animationToPlay);
            this.animationToPlay = null;
        }
        if (this.m_21224_()) {
            event.getController().setAnimation(this.death);
        } else if (event.isMoving()) {
            event.getController().setAnimation(RawAnimation.begin().thenLoop("flying_book_move"));
        } else {
            event.getController().setAnimation(RawAnimation.begin().thenLoop("flying_book_idle"));
        }
        return PlayState.CONTINUE;
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController[]{this.animationController});
        controllers.add(new AnimationController[]{new AnimationController((GeoAnimatable)this, "book_casting", 0, state -> PlayState.STOP).triggerableAnim("casting", this.casting).triggerableAnim("casting_1", this.casting_1).triggerableAnim("casting_2", this.casting_2).triggerableAnim("casting_3", this.casting_3)});
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}

