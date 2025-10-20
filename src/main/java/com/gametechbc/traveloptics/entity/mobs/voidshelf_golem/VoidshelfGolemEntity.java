/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.api.registry.AttributeRegistry
 *  io.redspace.ironsspellbooks.api.spells.AbstractSpell
 *  io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMob
 *  io.redspace.ironsspellbooks.util.ParticleHelper
 *  javax.annotation.Nullable
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.attributes.Attribute
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
 *  net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
 *  net.minecraft.world.entity.monster.Enemy
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
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
package com.gametechbc.traveloptics.entity.mobs.voidshelf_golem;

import com.gametechbc.traveloptics.entity.mobs.void_tome.VoidTomeEntity;
import com.gametechbc.traveloptics.init.TravelopticsEntities;
import com.gametechbc.traveloptics.util.TravelopticsParticleHelper;
import com.gametechbc.traveloptics.util.TravelopticsTags;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMob;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class VoidshelfGolemEntity
extends AbstractSpellCastingMob
implements Enemy {
    private static final EntityDataAccessor<Boolean> SUMMONED_ENTITY = SynchedEntityData.m_135353_(VoidshelfGolemEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> CAMOUFLAGE_ENTITY = SynchedEntityData.m_135353_(VoidshelfGolemEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> ACTIVATED = SynchedEntityData.m_135353_(VoidshelfGolemEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> SHOW_CANDLE = SynchedEntityData.m_135353_(VoidshelfGolemEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private int deactivationDelay = 0;
    private boolean hasPlayedFallSound = false;
    private int tomeSpawnCooldown = 200;
    private int tomeSpawnWindup = -1;
    private static final int MAX_VOID_TOMES = 5;
    private static final int VOID_TOME_DETECTION_RADIUS = 28;
    private static final double TOME_LAUNCH_SPEED = 0.4;
    private int totalVoidTomesSpawned = 0;
    private int selfDestructDelay = -1;
    private static final int MAX_VOID_TOMES_SUMMONED = 5;
    private boolean earlySpawnDone = false;
    private int earlySpawnTimer = -1;
    private boolean allowEarlyFirstSpawn = false;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    private final AnimationController<VoidshelfGolemEntity> animationController = new AnimationController((GeoAnimatable)this, "controller", 10, this::predicate);
    private final RawAnimation idle = RawAnimation.begin().thenLoop("blank");
    private final RawAnimation spawnVoidTome = RawAnimation.begin().thenPlay("spawn_void_tomes");
    private final RawAnimation death = RawAnimation.begin().thenPlay("flying_book_death");
    private RawAnimation animationToPlay = null;

    public VoidshelfGolemEntity(Level pLevel) {
        this((EntityType<? extends AbstractSpellCastingMob>)((EntityType)TravelopticsEntities.VOIDSHELF_GOLEM_ENTITY.get()), pLevel);
        this.m_21530_();
    }

    public VoidshelfGolemEntity(EntityType<? extends AbstractSpellCastingMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.m_21530_();
        this.f_21364_ = 10;
    }

    public void initiateCastSpell(AbstractSpell spell, int spellLevel) {
    }

    public boolean isSummonedEntity() {
        return (Boolean)this.f_19804_.m_135370_(SUMMONED_ENTITY);
    }

    public void setIsSummoned() {
        this.f_19804_.m_135381_(SUMMONED_ENTITY, (Object)true);
    }

    public boolean isCamouflage() {
        return (Boolean)this.f_19804_.m_135370_(CAMOUFLAGE_ENTITY);
    }

    public void setCamouflage(boolean value) {
        this.f_19804_.m_135381_(CAMOUFLAGE_ENTITY, (Object)value);
    }

    public boolean isActivated() {
        return (Boolean)this.f_19804_.m_135370_(ACTIVATED);
    }

    public void setActivated(boolean value) {
        this.f_19804_.m_135381_(ACTIVATED, (Object)value);
    }

    public boolean shouldShowCandle() {
        return (Boolean)this.f_19804_.m_135370_(SHOW_CANDLE);
    }

    public void setShouldShowCandle(boolean value) {
        this.f_19804_.m_135381_(SHOW_CANDLE, (Object)value);
    }

    public void setAllowEarlyFirstSpawn(boolean value) {
        this.allowEarlyFirstSpawn = value;
    }

    public boolean isEarlyFirstSpawnAllowed() {
        return this.allowEarlyFirstSpawn;
    }

    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(SUMMONED_ENTITY, (Object)false);
        this.f_19804_.m_135372_(CAMOUFLAGE_ENTITY, (Object)true);
        this.f_19804_.m_135372_(ACTIVATED, (Object)false);
        this.f_19804_.m_135372_(SHOW_CANDLE, (Object)true);
    }

    public void m_7380_(CompoundTag tag) {
        super.m_7380_(tag);
        tag.m_128405_("TomeCooldown", this.tomeSpawnCooldown);
        tag.m_128405_("TomeWindup", this.tomeSpawnWindup);
        tag.m_128379_("HasPlayedFallSound", this.hasPlayedFallSound);
        if (this.isSummonedEntity()) {
            tag.m_128379_("summoned", true);
        }
        tag.m_128379_("camouflage", this.isCamouflage());
        tag.m_128379_("ShowCandle", this.shouldShowCandle());
        tag.m_128405_("TotalVoidTomes", this.totalVoidTomesSpawned);
        tag.m_128405_("SelfDestructDelay", this.selfDestructDelay);
        tag.m_128379_("Activated", this.isActivated());
        tag.m_128379_("AllowEarlyFirstSpawn", this.allowEarlyFirstSpawn);
    }

    public void m_7378_(CompoundTag tag) {
        super.m_7378_(tag);
        this.tomeSpawnCooldown = tag.m_128451_("TomeCooldown");
        this.tomeSpawnWindup = tag.m_128451_("TomeWindup");
        this.hasPlayedFallSound = tag.m_128471_("HasPlayedFallSound");
        if (tag.m_128471_("summoned")) {
            this.setIsSummoned();
        }
        if (tag.m_128441_("camouflage")) {
            this.setCamouflage(tag.m_128471_("camouflage"));
        }
        if (tag.m_128441_("ShowCandle")) {
            this.setShouldShowCandle(tag.m_128471_("ShowCandle"));
        }
        this.totalVoidTomesSpawned = tag.m_128451_("TotalVoidTomes");
        this.selfDestructDelay = tag.m_128451_("SelfDestructDelay");
        this.setActivated(tag.m_128471_("Activated"));
        if (tag.m_128441_("AllowEarlyFirstSpawn")) {
            this.allowEarlyFirstSpawn = tag.m_128471_("AllowEarlyFirstSpawn");
        }
    }

    protected void m_8099_() {
        this.f_21346_.m_25352_(1, (Goal)new HurtByTargetGoal((PathfinderMob)this, new Class[0]));
        this.f_21346_.m_25352_(2, (Goal)new NearestAttackableTargetGoal((Mob)this, Player.class, true));
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return LivingEntity.m_21183_().m_22268_(Attributes.f_22281_, 3.0).m_22268_(Attributes.f_22282_, 0.0).m_22268_(Attributes.f_22278_, 100.0).m_22268_(Attributes.f_22276_, 60.0).m_22268_(Attributes.f_22277_, 18.0).m_22268_(Attributes.f_22279_, 0.25).m_22268_(Attributes.f_22280_, 0.3).m_22268_((Attribute)AttributeRegistry.SPELL_POWER.get(), 1.0).m_22268_((Attribute)AttributeRegistry.SPELL_RESIST.get(), 1.0);
    }

    private AABB createVoidTomeDetectionAABB() {
        Vec3 pos = this.m_20182_();
        double halfSize = 14.0;
        return new AABB(pos.f_82479_ - halfSize, pos.f_82480_ - halfSize, pos.f_82481_ - halfSize, pos.f_82479_ + halfSize, pos.f_82480_ + halfSize, pos.f_82481_ + halfSize);
    }

    public void m_8119_() {
        LivingEntity target;
        boolean hasValidTarget;
        super.m_8119_();
        if (this.f_19797_ % 3 == 0) {
            this.spawnCandleFlames();
        }
        boolean bl = hasValidTarget = (target = this.m_5448_()) != null && target.m_6084_();
        if (hasValidTarget) {
            if (!this.earlySpawnDone) {
                if (this.allowEarlyFirstSpawn) {
                    if (this.earlySpawnTimer < 0) {
                        this.earlySpawnTimer = 60;
                    } else {
                        --this.earlySpawnTimer;
                        if (this.earlySpawnTimer == 0) {
                            this.triggerAnim("shelf_casting", "spawn_void_tomes");
                            this.tomeSpawnWindup = 15;
                            this.earlySpawnDone = true;
                        }
                    }
                } else {
                    this.earlySpawnDone = true;
                }
            }
            long tomeCount = this.m_9236_().m_45976_(VoidTomeEntity.class, this.createVoidTomeDetectionAABB()).stream().filter(LivingEntity::m_6084_).filter(tome -> ((Object)tome).getClass() == VoidTomeEntity.class).count();
            if (this.isSummonedEntity() && this.totalVoidTomesSpawned >= 5 && this.selfDestructDelay < 0) {
                this.selfDestructDelay = 60;
            }
            if (tomeCount < 5L) {
                if (this.tomeSpawnWindup >= 0) {
                    --this.tomeSpawnWindup;
                    if (this.tomeSpawnWindup == 0) {
                        this.spawnVoidTome(new Vec3(0.0, 1.45, 1.5));
                        if (this.isSummonedEntity()) {
                            ++this.totalVoidTomesSpawned;
                            if (this.totalVoidTomesSpawned >= 5) {
                                this.selfDestructDelay = 60;
                            }
                        }
                    }
                } else if (this.earlySpawnDone) {
                    --this.tomeSpawnCooldown;
                    if (this.tomeSpawnCooldown <= 0) {
                        this.triggerAnim("shelf_casting", "spawn_void_tomes");
                        this.tomeSpawnWindup = 15;
                        int nearbyPlayers = this.getNearbyPlayerCount();
                        int extraPlayers = Math.max(0, nearbyPlayers - 1);
                        int cooldownReduction = extraPlayers * 20;
                        int scaledCooldown = 200 - cooldownReduction;
                        this.tomeSpawnCooldown = Math.max(40, scaledCooldown);
                    }
                }
            }
        }
        if (this.isSummonedEntity() && this.selfDestructDelay >= 0) {
            --this.selfDestructDelay;
            if (this.selfDestructDelay == 0) {
                this.m_146870_();
            }
        }
    }

    protected void m_8024_() {
        boolean hasValidTarget;
        super.m_8024_();
        LivingEntity target = this.m_5448_();
        boolean bl = hasValidTarget = target != null && target.m_6084_();
        if (hasValidTarget) {
            this.deactivationDelay = 10;
            if (!this.isActivated()) {
                this.setActivated(true);
            }
        } else if (this.deactivationDelay > 0) {
            --this.deactivationDelay;
        } else if (this.isActivated()) {
            this.setActivated(false);
            this.earlySpawnDone = false;
            this.earlySpawnTimer = -1;
        }
    }

    private void spawnVoidTome(Vec3 localOffset) {
        VoidTomeEntity tome = new VoidTomeEntity(this.m_9236_());
        Vec3 forward = this.m_20154_().m_82541_();
        Vec3 right = forward.m_82537_(new Vec3(0.0, 1.0, 0.0)).m_82541_();
        Vec3 up = new Vec3(0.0, 1.0, 0.0);
        Vec3 worldOffset = forward.m_82490_(localOffset.f_82481_).m_82549_(right.m_82490_(localOffset.f_82479_)).m_82549_(up.m_82490_(localOffset.f_82480_));
        Vec3 spawnPos = this.m_20182_().m_82549_(worldOffset);
        tome.m_146884_(spawnPos);
        tome.m_20256_(forward.m_82490_(0.4));
        if (this.isSummonedEntity()) {
            tome.setIsSummoned();
            tome.m_6710_(this.m_5448_());
        }
        this.m_9236_().m_7967_((Entity)tome);
    }

    public void spawnCandleFlames() {
        if (!this.shouldShowCandle()) {
            return;
        }
        Vec3 rootOffset = new Vec3(-0.2, 0.0, 0.0);
        double[][] candleOffsets = new double[][]{{0.25, 2.3, -0.1}, {-0.25, 2.33, -0.1}, {0.2, 2.36, 0.25}, {-0.2, 2.39, 0.25}};
        int index = this.f_19797_ / 2 % candleOffsets.length;
        double[] offset = candleOffsets[index];
        Vec3 pos = this.m_20182_().m_82549_(this.m_20154_().m_82524_((float)Math.PI).m_82541_().m_82490_(-0.1)).m_82549_(rootOffset).m_82520_(offset[0], offset[1], offset[2]);
        ParticleOptions particle = this.isCamouflage() ? ParticleHelper.EMBERS : TravelopticsParticleHelper.ABYSS_SPIKE_PARTICLE;
        this.m_9236_().m_6493_(particle, true, pos.f_82479_, pos.f_82480_, pos.f_82481_, 0.0, 0.0, 0.0);
    }

    public boolean m_6469_(DamageSource source, float amount) {
        return super.m_6469_(source, amount);
    }

    private int getNearbyPlayerCount() {
        double radius = this.m_21133_(Attributes.f_22277_);
        return this.m_9236_().m_6443_(Player.class, this.m_20191_().m_82400_(radius), player -> player.m_6084_() && !player.m_5833_()).size();
    }

    protected boolean m_6125_() {
        return super.m_6125_() && !this.isSummonedEntity();
    }

    public boolean m_6149_() {
        return super.m_6149_() && !this.isSummonedEntity();
    }

    public boolean m_6094_() {
        return false;
    }

    public boolean m_5829_() {
        return true;
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

    protected void m_7355_(BlockPos pos, BlockState state) {
    }

    @Nullable
    protected SoundEvent m_7975_(DamageSource p_21239_) {
        return SoundEvents.f_12634_;
    }

    @Nullable
    protected SoundEvent m_5592_() {
        return SoundEvents.f_12630_;
    }

    public boolean isDrinkingPotion() {
        return false;
    }

    public void startDrinkingPotion() {
    }

    public boolean m_142535_(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false;
    }

    public boolean m_6040_() {
        return true;
    }

    public boolean m_6785_(double p_21542_) {
        return false;
    }

    public boolean m_7307_(Entity pEntity) {
        return super.m_7307_(pEntity) || pEntity.m_6095_().m_204039_(TravelopticsTags.TEAM_THE_NIGHTWARDEN);
    }

    private PlayState predicate(AnimationState<VoidshelfGolemEntity> event) {
        if (this.animationToPlay != null) {
            event.getController().forceAnimationReset();
            event.getController().setAnimation(this.animationToPlay);
            this.animationToPlay = null;
        }
        if (this.m_21224_()) {
            event.getController().setAnimation(this.death);
        } else {
            event.getController().setAnimation(this.idle);
        }
        return PlayState.CONTINUE;
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController[]{this.animationController});
        controllers.add(new AnimationController[]{new AnimationController((GeoAnimatable)this, "shelf_casting", 5, state -> PlayState.STOP).triggerableAnim("spawn_void_tomes", this.spawnVoidTome)});
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}

