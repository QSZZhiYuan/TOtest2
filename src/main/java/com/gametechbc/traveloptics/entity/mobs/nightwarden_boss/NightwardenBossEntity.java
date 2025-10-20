/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.client.particle.RingParticle$EnumRingBehavior
 *  com.github.L_Ender.cataclysm.client.particle.RingParticle$RingData
 *  io.redspace.ironsspellbooks.api.entity.IMagicEntity
 *  io.redspace.ironsspellbooks.api.magic.MagicData
 *  io.redspace.ironsspellbooks.api.network.IClientEventEntity
 *  io.redspace.ironsspellbooks.api.registry.AttributeRegistry
 *  io.redspace.ironsspellbooks.api.registry.SchoolRegistry
 *  io.redspace.ironsspellbooks.api.registry.SpellRegistry
 *  io.redspace.ironsspellbooks.api.spells.AbstractSpell
 *  io.redspace.ironsspellbooks.api.spells.SchoolType
 *  io.redspace.ironsspellbooks.api.spells.SpellData
 *  io.redspace.ironsspellbooks.api.util.CameraShakeData
 *  io.redspace.ironsspellbooks.api.util.CameraShakeManager
 *  io.redspace.ironsspellbooks.api.util.Utils
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible
 *  io.redspace.ironsspellbooks.entity.mobs.IAnimatedAttacker
 *  io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMob
 *  io.redspace.ironsspellbooks.entity.mobs.goals.PatrolNearLocationGoal
 *  io.redspace.ironsspellbooks.network.ClientboundEntityEvent
 *  io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions
 *  io.redspace.ironsspellbooks.setup.Messages
 *  javax.annotation.Nullable
 *  net.minecraft.ChatFormatting
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientGamePacketListener
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.util.Mth
 *  net.minecraft.world.BossEvent$BossBarColor
 *  net.minecraft.world.DifficultyInstance
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.damagesource.DamageTypes
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
 *  net.minecraft.world.entity.ai.control.LookControl
 *  net.minecraft.world.entity.ai.control.MoveControl
 *  net.minecraft.world.entity.ai.goal.FloatGoal
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.LookAtPlayerGoal
 *  net.minecraft.world.entity.ai.goal.WrappedGoal
 *  net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.monster.AbstractIllager
 *  net.minecraft.world.entity.monster.Enemy
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.ServerLevelAccessor
 *  net.minecraft.world.level.storage.loot.LootParams
 *  net.minecraft.world.level.storage.loot.LootParams$Builder
 *  net.minecraft.world.level.storage.loot.LootTable
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParams
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.network.NetworkHooks
 *  net.minecraftforge.network.PlayMessages$SpawnEntity
 *  software.bernie.geckolib.core.animatable.GeoAnimatable
 *  software.bernie.geckolib.core.animation.AnimatableManager$ControllerRegistrar
 *  software.bernie.geckolib.core.animation.AnimationController
 *  software.bernie.geckolib.core.animation.AnimationController$State
 *  software.bernie.geckolib.core.animation.AnimationState
 *  software.bernie.geckolib.core.animation.RawAnimation
 *  software.bernie.geckolib.core.object.PlayState
 */
package com.gametechbc.traveloptics.entity.mobs.nightwarden_boss;

import com.gametechbc.traveloptics.api.entity.TravelopticsBossInfo;
import com.gametechbc.traveloptics.api.entity.ai.ComboWizardAttackGoal;
import com.gametechbc.traveloptics.api.entity.ai.HurtByNearestTargetGoal;
import com.gametechbc.traveloptics.api.utils.TOEntityUtils;
import com.gametechbc.traveloptics.api.utils.TOGeneralUtils;
import com.gametechbc.traveloptics.config.EntityConfig;
import com.gametechbc.traveloptics.entity.misc.TOScreenShakeEntity;
import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.NightwardenAdaptRegistry;
import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.NightwardenAttackGoal;
import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.NightwardenReturnHomeHandler;
import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.animated_particle.NightwardenPhaseThreeSwapAnimatedParticle;
import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.anti_cheese.NightwardenAntiCCHandler;
import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.anti_cheese.NightwardenAntiCheeseTeleportHandler;
import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.anti_cheese.NightwardenAntiCheeseVoidTeleportHandler;
import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.anti_cheese.NightwardenSummonCheeseHandler;
import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.misc.NightwardenMusicManager;
import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.misc.NightwardenTargetVelocityTracker;
import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.nightwarden_defeated.NightwardenDefeatedEntity;
import com.gametechbc.traveloptics.entity.mobs.void_tome.VoidTomeEntity;
import com.gametechbc.traveloptics.entity.projectiles.supernova.dying_star.DyingStarEntity;
import com.gametechbc.traveloptics.init.TravelopticsEntities;
import com.gametechbc.traveloptics.init.TravelopticsMessages;
import com.gametechbc.traveloptics.init.TravelopticsParticles;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import com.gametechbc.traveloptics.init.TravelopticsSpells;
import com.gametechbc.traveloptics.util.TOColors;
import com.gametechbc.traveloptics.util.TravelopticsTags;
import com.github.L_Ender.cataclysm.client.particle.RingParticle;
import io.redspace.ironsspellbooks.api.entity.IMagicEntity;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.network.IClientEventEntity;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.api.spells.SpellData;
import io.redspace.ironsspellbooks.api.util.CameraShakeData;
import io.redspace.ironsspellbooks.api.util.CameraShakeManager;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import io.redspace.ironsspellbooks.entity.mobs.IAnimatedAttacker;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMob;
import io.redspace.ironsspellbooks.entity.mobs.goals.PatrolNearLocationGoal;
import io.redspace.ironsspellbooks.network.ClientboundEntityEvent;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import io.redspace.ironsspellbooks.setup.Messages;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
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
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public class NightwardenBossEntity
extends AbstractSpellCastingMob
implements Enemy,
IAnimatedAttacker,
IClientEventEntity,
AntiMagicSusceptible {
    private boolean collectedLoot = false;
    private final List<ItemStack> deathItems = new ArrayList<ItemStack>();
    public static final byte STOP_MUSIC = 0;
    public static final byte START_MUSIC = 1;
    private static final EntityDataAccessor<Integer> PHASE = SynchedEntityData.m_135353_(NightwardenBossEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<CompoundTag> DAMAGE_ADAPTATION = SynchedEntityData.m_135353_(NightwardenBossEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135042_);
    private static final EntityDataAccessor<BlockPos> HOME_POS = SynchedEntityData.m_135353_(NightwardenBossEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135038_);
    private static final EntityDataAccessor<Float> RESURGENCE = SynchedEntityData.m_135353_(NightwardenBossEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135029_);
    private static final EntityDataAccessor<Boolean> SHOULD_SHOW_WINGS = SynchedEntityData.m_135353_(NightwardenBossEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> SHOULD_SHOW_WEAPON = SynchedEntityData.m_135353_(NightwardenBossEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> HAS_PENDING_RESURGENCE = SynchedEntityData.m_135353_(NightwardenBossEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> SHOULD_SPAWN_RING_PARTICLE = SynchedEntityData.m_135353_(NightwardenBossEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private final TravelopticsBossInfo bossEvent = new TravelopticsBossInfo(this.m_5446_(), BossEvent.BossBarColor.PURPLE, true, 0);
    private final TravelopticsBossInfo bossEvent1 = new TravelopticsBossInfo((Component)Component.m_237115_((String)"entity.cataclysm.rage_meter"), BossEvent.BossBarColor.PURPLE, false, 1);
    private final NightwardenReturnHomeHandler returnHomeHandler = new NightwardenReturnHomeHandler();
    private int transitionElapsed = 0;
    private int transitionTime = 140;
    private static final int TRANSITION_ONE_DURATION = 257;
    private static final int TRANSITION_TWO_DURATION = 410;
    public boolean isMeleeing;
    private int wingVisibilityTimer = 0;
    private int weaponVisibilityTimer = 0;
    private int ringParticleTimer = 0;
    private int stuckCounter = 0;
    private static final int STUCK_TICKS_THRESHOLD = 60;
    private int destroyBlockDelay = 0;
    private int bookshelfSpawnCooldown = 0;
    private int resurgenceCooldown = 0;
    private static final int resurgenceIFrameDuration = 10;
    private boolean hasShownCounterspellHint = false;
    private int counterspellHintTicks = 0;
    private boolean lockRotation = false;
    private static final float ADAPTED_DAMAGE_REDUCTION = 0.2f;
    private static final float IMMUNE_DAMAGE_REDUCTION = 0.1f;
    private static final float PHASE_TWO_INVALID_REDUCTION = 0.1f;
    private final RawAnimation phase_transition_1_animation = RawAnimation.begin().thenPlay("nightwarden_phase_swap_melee_1");
    private final RawAnimation phase_transition_2_animation = RawAnimation.begin().thenPlay("nightwarden_phase_swap_magic");
    private final RawAnimation deathAnimation = RawAnimation.begin().thenPlay("nightwarden_death");
    private final AnimationController<NightwardenBossEntity> meleeController = new AnimationController((GeoAnimatable)this, "nightwarden_melee", 0, this::meleePredicate);
    private static final Set<String> NO_HEAD_ANIMATION_IDS = Set.of("nightwarden_teleport_spin", "nightwarden_scythe_throw", "nightwarden_scythe_right_swing", "nightwarden_scythe_left_swing", "nightwarden_scythe_ten_combo", "nightwarden_scythe_jump_combo", "nightwarden_scythe_big_slam", "nightwarden_scythe_ult", "nightwarden_scythe_spin_forward", "nightwarden_scythe_clone_slashes", "nightwarden_scythe_ground_slam_clone", "nightwarden_scythe_spinning_clone", "nightwarden_scythe_spin_forward_phase_three");
    RawAnimation animationToPlay = null;
    private String lastAnimationId = null;

    public NightwardenBossEntity(Level pLevel) {
        this((EntityType<? extends AbstractSpellCastingMob>)((EntityType)TravelopticsEntities.NIGHTWARDEN_BOSS.get()), pLevel);
        this.m_21530_();
        this.f_19811_ = true;
    }

    public NightwardenBossEntity(PlayMessages.SpawnEntity packet, Level world) {
        this((EntityType<? extends AbstractSpellCastingMob>)((EntityType)TravelopticsEntities.NIGHTWARDEN_BOSS.get()), world);
    }

    public NightwardenBossEntity(EntityType<? extends AbstractSpellCastingMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.m_21530_();
        this.f_21364_ = 60;
        this.m_274367_(2.0f);
        this.f_19811_ = true;
        this.f_21365_ = this.createLookControl();
        this.f_21342_ = this.createMoveControl();
    }

    public Packet<ClientGamePacketListener> m_5654_() {
        return NetworkHooks.getEntitySpawningPacket((Entity)this);
    }

    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(PHASE, (Object)Phase.FIRST.value);
        this.f_19804_.m_135372_(DAMAGE_ADAPTATION, (Object)new CompoundTag());
        this.f_19804_.m_135372_(HOME_POS, (Object)BlockPos.f_121853_);
        this.f_19804_.m_135372_(RESURGENCE, (Object)Float.valueOf(0.0f));
        this.f_19804_.m_135372_(SHOULD_SHOW_WINGS, (Object)false);
        this.f_19804_.m_135372_(SHOULD_SHOW_WEAPON, (Object)false);
        this.f_19804_.m_135372_(HAS_PENDING_RESURGENCE, (Object)false);
        this.f_19804_.m_135372_(SHOULD_SPAWN_RING_PARTICLE, (Object)false);
    }

    public void m_7380_(CompoundTag pCompound) {
        super.m_7380_(pCompound);
        pCompound.m_128405_("phase", ((Integer)this.f_19804_.m_135370_(PHASE)).intValue());
        pCompound.m_128405_("transitionTime", this.transitionTime);
        pCompound.m_128365_("DamageAdaptation", (Tag)this.f_19804_.m_135370_(DAMAGE_ADAPTATION));
        pCompound.m_128350_("ResurgenceCounter", this.getResurgenceCounter());
        pCompound.m_128379_("HasPendingResurgence", ((Boolean)this.f_19804_.m_135370_(HAS_PENDING_RESURGENCE)).booleanValue());
        BlockPos home = this.getHomePos();
        pCompound.m_128405_("HomePosX", home.m_123341_());
        pCompound.m_128405_("HomePosY", home.m_123342_());
        pCompound.m_128405_("HomePosZ", home.m_123343_());
        pCompound.m_128405_("BookshelfSpawnCooldown", this.bookshelfSpawnCooldown);
        pCompound.m_128379_("HasShownCounterspellHint", this.hasShownCounterspellHint);
    }

    public void m_7378_(CompoundTag pCompound) {
        super.m_7378_(pCompound);
        if (this.m_8077_()) {
            this.bossEvent.m_6456_(this.m_5446_());
        }
        int savedPhase = pCompound.m_128451_("phase");
        this.setPhase(Phase.values()[savedPhase]);
        if (this.isPhaseOneTransitioning() || this.isPhaseTwoTransitioning()) {
            this.transitionTime = pCompound.m_128451_("transitionTime");
        }
        if (this.isPhase(Phase.SECOND)) {
            this.setPhaseTwoGoals();
        } else if (this.isPhase(Phase.THIRD)) {
            this.setPhaseThreeGoals();
        }
        if (pCompound.m_128441_("DamageAdaptation")) {
            this.f_19804_.m_135381_(DAMAGE_ADAPTATION, (Object)pCompound.m_128469_("DamageAdaptation"));
        }
        if (pCompound.m_128425_("ResurgenceCounter", 5)) {
            this.setResurgenceCounter(pCompound.m_128457_("ResurgenceCounter"));
        }
        if (pCompound.m_128441_("HasPendingResurgence")) {
            this.f_19804_.m_135381_(HAS_PENDING_RESURGENCE, (Object)pCompound.m_128471_("HasPendingResurgence"));
        }
        int x = pCompound.m_128451_("HomePosX");
        int y = pCompound.m_128451_("HomePosY");
        int z = pCompound.m_128451_("HomePosZ");
        this.setHomePos(new BlockPos(x, y, z));
        this.bookshelfSpawnCooldown = pCompound.m_128451_("BookshelfSpawnCooldown");
        if (pCompound.m_128441_("HasShownCounterspellHint")) {
            this.hasShownCounterspellHint = pCompound.m_128471_("HasShownCounterspellHint");
        }
    }

    public boolean isPhaseOneTransitioning() {
        return this.isPhase(Phase.TRANSITION_1);
    }

    public boolean isPhaseTwoTransitioning() {
        return this.isPhase(Phase.TRANSITION_2);
    }

    public boolean isPhaseTransitioning() {
        return this.isPhaseOneTransitioning() || this.isPhaseTwoTransitioning() || this.isPhase(Phase.DEATH);
    }

    private void setPhase(int phase) {
        this.f_19804_.m_135381_(PHASE, (Object)phase);
    }

    private void setPhase(Phase phase) {
        this.f_19804_.m_135381_(PHASE, (Object)phase.value);
    }

    public boolean isPhase(Phase phase) {
        return (Integer)this.f_19804_.m_135370_(PHASE) == phase.value;
    }

    public Phase getCurrentPhase() {
        int phaseValue = (Integer)this.f_19804_.m_135370_(PHASE);
        for (Phase phase : Phase.values()) {
            if (phase.value != phaseValue) continue;
            return phase;
        }
        return Phase.FIRST;
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return LivingEntity.m_21183_().m_22268_(Attributes.f_22276_, 600.0).m_22268_(Attributes.f_22281_, 18.0).m_22268_(Attributes.f_22277_, 70.0).m_22268_(Attributes.f_22279_, 0.2).m_22268_(Attributes.f_22284_, 10.0).m_22268_(Attributes.f_22282_, 0.2).m_22268_((Attribute)AttributeRegistry.SPELL_POWER.get(), 1.5).m_22268_((Attribute)AttributeRegistry.SPELL_RESIST.get(), 1.0).m_22268_(Attributes.f_22278_, 1.0);
    }

    public void handleClientEvent(byte eventId) {
        switch (eventId) {
            case 0: {
                NightwardenMusicManager.stop(this);
                break;
            }
            case 1: {
                NightwardenMusicManager.createOrResumeInstance(this);
            }
        }
    }

    @Nullable
    public SpawnGroupData m_6518_(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag dataTag) {
        this.setHomePos(this.m_20183_());
        return super.m_6518_(level, difficulty, spawnType, spawnGroupData, dataTag);
    }

    public void setHomePos(BlockPos homePos) {
        this.f_19804_.m_135381_(HOME_POS, (Object)homePos);
    }

    public BlockPos getHomePos() {
        return (BlockPos)this.f_19804_.m_135370_(HOME_POS);
    }

    private float getAdaptiveCap() {
        return this.m_21233_() / 12.0f;
    }

    public float getResurgenceCounter() {
        return ((Float)this.f_19804_.m_135370_(RESURGENCE)).floatValue();
    }

    private float getMaxResurgenceCounter() {
        return Math.max(50.0f, this.m_21233_() * 0.3f);
    }

    public void setResurgenceCounter(float value) {
        this.f_19804_.m_135381_(RESURGENCE, (Object)Float.valueOf(value));
    }

    public void addResurgence(float amount, boolean applyCooldown, boolean showDebug) {
        float max = this.getMaxResurgenceCounter();
        float current = this.getResurgenceCounter();
        if (current < max) {
            float updated = Math.min(current + amount, max);
            this.setResurgenceCounter(updated);
            if (updated >= max) {
                this.f_19804_.m_135381_(HAS_PENDING_RESURGENCE, (Object)true);
            }
            if (applyCooldown) {
                this.resurgenceCooldown = 10;
            }
            if (showDebug) {
                System.out.printf("[Resurgence Debug] Added: %.2f | New: %.2f / %.2f | ApplyCooldown: %b | Cooldown: %d%n", Float.valueOf(amount), Float.valueOf(updated), Float.valueOf(max), applyCooldown, this.resurgenceCooldown);
            }
        }
    }

    public float getResurgenceScaledAttack() {
        float base = (float)this.m_21133_(Attributes.f_22281_);
        float progress = Mth.m_14036_((float)(this.getResurgenceCounter() / this.getMaxResurgenceCounter()), (float)0.0f, (float)1.0f);
        float bonusMultiplier = 1.0f + 0.3f * progress;
        return base * bonusMultiplier;
    }

    public float getResurgenceScaledLifesteal(float baseLifesteal) {
        float progress = Mth.m_14036_((float)(this.getResurgenceCounter() / this.getMaxResurgenceCounter()), (float)0.0f, (float)1.0f);
        float bonusMultiplier = 1.0f + 0.25f * progress;
        return baseLifesteal * bonusMultiplier;
    }

    public boolean hasPendingResurgence() {
        return (Boolean)this.f_19804_.m_135370_(HAS_PENDING_RESURGENCE);
    }

    public float getDamageCap() {
        return ((Double)EntityConfig.nightwardenDynamicDamageCap.get()).floatValue();
    }

    public void setShouldShowWings(boolean shouldShow, int durationTicks) {
        this.f_19804_.m_135381_(SHOULD_SHOW_WINGS, (Object)shouldShow);
        if (shouldShow) {
            this.wingVisibilityTimer = durationTicks;
        }
    }

    public boolean isShowingWings() {
        return (Boolean)this.f_19804_.m_135370_(SHOULD_SHOW_WINGS);
    }

    public void setShouldShowWeapon(boolean shouldShow, int durationTicks) {
        this.f_19804_.m_135381_(SHOULD_SHOW_WEAPON, (Object)shouldShow);
        if (shouldShow) {
            this.weaponVisibilityTimer = durationTicks;
        }
    }

    public boolean isShowingWeapon() {
        return (Boolean)this.f_19804_.m_135370_(SHOULD_SHOW_WEAPON);
    }

    public boolean shouldSpawnRingParticle() {
        return (Boolean)this.f_19804_.m_135370_(SHOULD_SPAWN_RING_PARTICLE);
    }

    public void setShouldSpawnRingParticle(boolean shouldSpawn, int durationTicks) {
        this.f_19804_.m_135381_(SHOULD_SPAWN_RING_PARTICLE, (Object)shouldSpawn);
        if (shouldSpawn) {
            this.ringParticleTimer = durationTicks;
        }
    }

    public void setLockRotation(boolean dashing) {
        this.lockRotation = dashing;
    }

    public boolean isRotationLocked() {
        return this.lockRotation;
    }

    private ComboWizardAttackGoal getMagicCombatGoal() {
        return new ComboWizardAttackGoal((IMagicEntity)this, 1.25, 40, 40).addCombo(1, List.of((AbstractSpell)SpellRegistry.MAGIC_MISSILE_SPELL.get(), (AbstractSpell)SpellRegistry.MAGIC_MISSILE_SPELL.get(), (AbstractSpell)SpellRegistry.MAGIC_ARROW_SPELL.get())).addCombo(3, List.of((AbstractSpell)SpellRegistry.MAGIC_ARROW_SPELL.get(), (AbstractSpell)TravelopticsSpells.VORTEX_PUNCH_SPELL.get())).addCombo(4, List.of((AbstractSpell)TravelopticsSpells.VORTEX_PUNCH_SPELL.get(), (AbstractSpell)SpellRegistry.STARFALL_SPELL.get())).setComboCooldown(80, 80).setSpellQuality(1.0f, 1.2f).setSingleUseSpell((AbstractSpell)TravelopticsSpells.ORBITAL_VOID_SPELL.get(), 200, 400, 3, 4);
    }

    private NightwardenAttackGoal getMeleeCombatGoal() {
        return (NightwardenAttackGoal)new NightwardenAttackGoal(this, 1.0, 50, 75, 35.0f).setComboChance(1.0f).setMeleeBias(1.0f, 1.0f).setMeleeAttackInverval(20, 30).setAllowFleeing(false).setSpellQuality(0.3f, 0.5f).setSpells(List.of(), List.of(), List.of(), List.of());
    }

    protected void m_8099_() {
        this.setPhaseOneGoals();
        this.f_21346_.m_25352_(1, (Goal)new HurtByNearestTargetGoal((PathfinderMob)this, new Class[0]));
        this.f_21346_.m_25352_(2, (Goal)new NearestAttackableTargetGoal((Mob)this, Player.class, true));
        this.f_21346_.m_25352_(5, (Goal)new NearestAttackableTargetGoal((Mob)this, AbstractIllager.class, true));
    }

    private void setPhaseOneGoals() {
        this.f_21345_.m_25386_().forEach(WrappedGoal::m_8041_);
        this.f_21345_.m_262460_(x -> true);
        this.f_21345_.m_25352_(1, (Goal)new FloatGoal((Mob)this));
        this.f_21345_.m_25352_(3, (Goal)this.getMagicCombatGoal());
        this.f_21345_.m_25352_(5, (Goal)new PatrolNearLocationGoal((PathfinderMob)this, 32.0f, (double)0.9f));
        this.f_21345_.m_25352_(8, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 8.0f));
    }

    private void setPhaseTwoGoals() {
        this.f_21345_.m_25386_().forEach(WrappedGoal::m_8041_);
        this.f_21345_.m_262460_(x -> true);
        this.f_21345_.m_25352_(0, (Goal)new FloatGoal((Mob)this));
        this.f_21345_.m_25352_(3, (Goal)this.getMeleeCombatGoal());
        this.f_21345_.m_25352_(5, (Goal)new PatrolNearLocationGoal((PathfinderMob)this, 32.0f, (double)0.9f));
        this.f_21345_.m_25352_(8, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 8.0f));
    }

    private void setPhaseThreeGoals() {
        this.f_21345_.m_25386_().forEach(WrappedGoal::m_8041_);
        this.f_21345_.m_262460_(x -> true);
        this.f_21345_.m_25352_(1, (Goal)new FloatGoal((Mob)this));
        this.f_21345_.m_25352_(3, (Goal)this.getMeleeCombatGoal());
        this.f_21345_.m_25352_(5, (Goal)new PatrolNearLocationGoal((PathfinderMob)this, 32.0f, (double)0.9f));
        this.f_21345_.m_25352_(8, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 8.0f));
        this.hasUsedSingleAttack = false;
    }

    public void m_6593_(@Nullable Component pName) {
        super.m_6593_(pName);
        this.bossEvent.m_6456_(this.m_5446_());
    }

    protected void triggerBreak(boolean doGoalStuff) {
        this.m_5496_((SoundEvent)TravelopticsSounds.BOSS_BREAK.get(), 1.5f, 1.0f);
        if (!this.m_9236_().f_46443_) {
            MagicManager.spawnParticles((Level)this.m_9236_(), (ParticleOptions)new BlastwaveParticleOptions(((SchoolType)SchoolRegistry.ENDER.get()).getTargetingColor(), 5.0f), (double)this.m_20185_(), (double)(this.m_20186_() + (double)1.8f), (double)this.m_20189_(), (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)false);
        }
        if (doGoalStuff) {
            this.castComplete();
            this.cancelCast();
            this.getMagicCombatGoal().m_8041_();
        }
    }

    public void m_8119_() {
        super.m_8119_();
        this.passiveTick();
        float phaseOneThreshold = this.m_21233_() * 0.8f;
        float phaseTwoThreshold = this.m_21233_() * 0.4f;
        if (!this.m_9236_().f_46443_) {
            if (this.isPhase(Phase.FIRST)) {
                if (this.m_21223_() <= phaseOneThreshold) {
                    if (!this.m_21224_()) {
                        this.m_21153_(phaseOneThreshold);
                    }
                    this.triggerBreak(true);
                    this.setPhase(Phase.TRANSITION_1);
                    this.transitionTime = 257;
                    this.transitionElapsed = 0;
                    this.m_20331_(true);
                    this.triggerAnim("nightwarden_transition", "phase_1_to_2");
                }
            } else if (this.isPhase(Phase.TRANSITION_1)) {
                ++this.transitionElapsed;
                if (this.m_5448_() != null) {
                    if (this.transitionElapsed <= 6 && this.transitionElapsed % 2 == 0) {
                        this.forceFaceTarget();
                    } else if (this.transitionElapsed >= 128 && this.transitionElapsed < 134 && this.transitionElapsed % 2 == 0) {
                        this.forceFaceTarget();
                    }
                }
                if (!this.m_9236_().f_46443_) {
                    this.createPhaseTransitionParticles(this.transitionElapsed);
                }
                this.handlePhaseOneToTwoTransition();
                if (this.transitionElapsed % 3 == 0 && this.transitionElapsed < 128) {
                    TravelopticsMessages.sendBossMessageToRange((Entity)this, (Component)Component.m_237115_((String)"entity.traveloptics.message.nightwarden.phase_two_warning.1"), TOColors.rgbToARGB(6619356, 0.5f), 100, 32.0, false);
                }
                if (--this.transitionTime <= 0) {
                    this.setPhase(Phase.SECOND);
                    this.setPhaseTwoGoals();
                    this.m_20331_(false);
                }
            } else if (this.isPhase(Phase.SECOND)) {
                if (this.m_21223_() <= phaseTwoThreshold) {
                    if (!this.m_21224_()) {
                        this.m_21153_(phaseTwoThreshold);
                    }
                    this.triggerBreak(false);
                    this.setPhase(Phase.TRANSITION_2);
                    this.transitionTime = 410;
                    this.transitionElapsed = 0;
                    this.m_20331_(true);
                    this.triggerAnim("nightwarden_transition", "phase_2_to_3");
                }
            } else if (this.isPhase(Phase.TRANSITION_2)) {
                this.setShouldShowWings(true, 2);
                ++this.transitionElapsed;
                if (this.m_5448_() != null) {
                    if (this.transitionElapsed <= 6 && this.transitionElapsed % 2 == 0) {
                        this.forceFaceTarget();
                    } else if (this.transitionElapsed >= 205 && this.transitionElapsed < 211 && this.transitionElapsed % 2 == 0) {
                        this.forceFaceTarget();
                    }
                }
                if (!this.m_9236_().f_46443_) {
                    this.createPhaseTransitionParticles(this.transitionElapsed);
                }
                this.handleSupernovaSequence();
                if (this.transitionElapsed % 3 == 0) {
                    float thirdDuration = 136.66667f;
                    if ((float)this.transitionElapsed < thirdDuration) {
                        TOGeneralUtils.notifyPlayersInRange((Entity)this, (Component)Component.m_237115_((String)"entity.traveloptics.message.nightwarden.phase_three_warning.1").m_130940_(ChatFormatting.RED), 32.0);
                    } else if ((float)this.transitionElapsed < 2.0f * thirdDuration) {
                        TravelopticsMessages.sendBossMessageToRange((Entity)this, (Component)Component.m_237115_((String)"entity.traveloptics.message.nightwarden.phase_three_warning.2"), TOColors.rgbToARGB(6619356, 0.5f), 60, 32.0, false);
                    } else {
                        TravelopticsMessages.sendBossMessageToRange((Entity)this, (Component)Component.m_237115_((String)"entity.traveloptics.message.nightwarden.phase_three_warning.3"), TOColors.rgbToARGB(6619356, 0.5f), 60, 32.0, false);
                    }
                }
                if (--this.transitionTime <= 0) {
                    this.setPhase(Phase.THIRD);
                    this.setPhaseThreeGoals();
                    this.m_20331_(false);
                }
            } else if (this.isPhase(Phase.THIRD)) {
                // empty if block
            }
        }
        if (this.m_9236_().f_46443_ && this.shouldSpawnRingParticle() && this.f_19797_ % 4 == 0) {
            double x = this.m_20185_();
            double y = this.m_20186_() + (double)(this.m_20206_() / 2.0f);
            double z = this.m_20189_();
            float yaw = (float)Math.toRadians(-this.m_146908_());
            float yaw2 = (float)Math.toRadians(-this.m_146908_() + 180.0f);
            float pitch = (float)Math.toRadians(-this.m_146909_());
            this.m_9236_().m_7106_((ParticleOptions)new RingParticle.RingData(yaw, pitch, 40, 0.631f, 0.325f, 0.996f, 1.0f, 50.0f, false, RingParticle.EnumRingBehavior.GROW_THEN_SHRINK), x, y, z, 0.0, 0.0, 0.0);
            this.m_9236_().m_7106_((ParticleOptions)new RingParticle.RingData(yaw2, pitch, 40, 0.631f, 0.325f, 0.996f, 1.0f, 50.0f, false, RingParticle.EnumRingBehavior.GROW_THEN_SHRINK), x, y, z, 0.0, 0.0, 0.0);
        }
    }

    private void passiveTick() {
        this.bossEvent.m_142711_(this.m_21223_() / this.m_21233_());
        this.bossEvent1.m_142711_(this.getResurgenceCounter() / this.getMaxResurgenceCounter());
        if (this.wingVisibilityTimer > 0) {
            --this.wingVisibilityTimer;
            if (this.wingVisibilityTimer == 0) {
                this.f_19804_.m_135381_(SHOULD_SHOW_WINGS, (Object)false);
            }
        }
        if (this.weaponVisibilityTimer > 0) {
            --this.weaponVisibilityTimer;
            if (this.weaponVisibilityTimer == 0) {
                this.f_19804_.m_135381_(SHOULD_SHOW_WEAPON, (Object)false);
            }
        }
        if (this.ringParticleTimer > 0) {
            --this.ringParticleTimer;
            if (this.ringParticleTimer == 0) {
                this.f_19804_.m_135381_(SHOULD_SPAWN_RING_PARTICLE, (Object)false);
            }
        }
        if (this.resurgenceCooldown > 0) {
            --this.resurgenceCooldown;
        }
        if (this.destroyBlockDelay > 0) {
            --this.destroyBlockDelay;
        }
        if (!this.m_9236_().f_46443_ && this.f_19797_ % 60 == 0) {
            this.addResurgence(1.0f, false, false);
        }
        if (!this.hasShownCounterspellHint && this.getResurgenceCounter() >= this.getMaxResurgenceCounter()) {
            this.hasShownCounterspellHint = true;
            this.counterspellHintTicks = 30;
        }
        if (this.counterspellHintTicks > 0) {
            --this.counterspellHintTicks;
            TravelopticsMessages.sendBossMessageToRange((Entity)this, (Component)Component.m_237115_((String)"entity.traveloptics.message.nightwarden.counterspell_hint"), TOColors.rgbToARGB(6619356, 0.5f), 100, 32.0, false);
        }
        if (!this.m_21525_() && this.m_5448_() != null && this.m_5448_().m_6084_()) {
            boolean targetIsHighGround;
            LivingEntity target = this.m_5448_();
            boolean bl = targetIsHighGround = target.m_20186_() > this.m_20186_() + 3.0;
            this.stuckCounter = targetIsHighGround ? ++this.stuckCounter : 0;
            if (this.stuckCounter >= 60) {
                NightwardenAntiCheeseTeleportHandler.tryTeleport(this, target, 50, true, true);
                this.stuckCounter = 0;
            }
        }
        if (!this.m_9236_().m_5776_()) {
            this.returnHomeHandler.tick(this, this.getHomePos());
        }
        NightwardenSummonCheeseHandler.tick(this, 8.0, 0.1f, 0.5f);
        NightwardenAntiCCHandler.tick(this);
    }

    protected void m_8024_() {
        super.m_8024_();
        if (this.m_5912_() && !this.m_9236_().f_46443_ && this.isPhase(Phase.FIRST) && this.bookshelfSpawnCooldown-- <= 0) {
            this.bookshelfSpawnCooldown = 400;
            this.spawnVoidTomes(this.m_9236_(), true);
            this.spawnVoidTomes(this.m_9236_(), false);
        }
    }

    protected void m_6153_() {
        ++this.f_20919_;
        if (this.f_20919_ == 1) {
            this.setPhase(Phase.DEATH);
            this.animationToPlay = this.deathAnimation;
        }
        if (this.f_20919_ == 10 && !this.m_9236_().f_46443_ && !this.collectedLoot) {
            this.populateDeathLootForNightwarden();
        }
        if (this.f_20919_ == 180 && !this.m_9236_().f_46443_) {
            BlockPos home = this.getHomePos();
            NightwardenDefeatedEntity defeated = new NightwardenDefeatedEntity((EntityType<? extends PathfinderMob>)((EntityType)TravelopticsEntities.NIGHTWARDEN_DEFEATED.get()), this.m_9236_());
            defeated.m_7678_((double)home.m_123341_() + 0.5, home.m_123342_(), (double)home.m_123343_() + 0.5, this.m_146908_(), this.m_146909_());
            defeated.setCooldown(3600);
            defeated.m_6518_((ServerLevelAccessor)((ServerLevel)this.m_9236_()), this.m_9236_().m_6436_(home), MobSpawnType.TRIGGERED, null, null);
            this.m_9236_().m_7967_((Entity)defeated);
            if (!this.deathItems.isEmpty()) {
                ItemStack currentStack = ItemStack.f_41583_;
                while (!this.deathItems.isEmpty() || !currentStack.m_41619_()) {
                    if (currentStack.m_41619_()) {
                        currentStack = this.deathItems.remove(0);
                    }
                    if (currentStack.m_41613_() <= 0) continue;
                    ItemStack one = currentStack.m_41777_();
                    one.m_41764_(1);
                    currentStack.m_41774_(1);
                    this.m_19983_(one);
                }
            }
            this.m_9236_().m_7605_((Entity)this, (byte)60);
        }
        if (this.f_20919_ >= 185) {
            this.m_142687_(Entity.RemovalReason.KILLED);
        }
    }

    public void onAntiMagic(MagicData magicData) {
        if (((Boolean)this.f_19804_.m_135370_(HAS_PENDING_RESURGENCE)).booleanValue()) {
            this.f_19804_.m_135381_(HAS_PENDING_RESURGENCE, (Object)false);
            this.setResurgenceCounter(0.0f);
            this.triggerResurgenceBlast();
        }
    }

    public boolean m_6469_(DamageSource source, float damage) {
        LivingEntity target;
        boolean shouldCountResurgence;
        NightwardenAntiCheeseVoidTeleportHandler.handleVoidDamage(this, source);
        if (source.m_276093_(DamageTypes.f_268612_) && this.destroyBlockDelay <= 0) {
            TOGeneralUtils.doMobBreakSuffocatingBlocks((LivingEntity)this);
            this.destroyBlockDelay = 40;
        }
        if (source.m_269533_(TravelopticsTags.BYPASSES_ADAPTATION)) {
            return super.m_6469_(source, damage);
        }
        Entity attacker = source.m_7639_();
        ResourceLocation damageType = this.m_9236_().m_9598_().m_175515_(Registries.f_268580_).m_7981_((Object)source.m_269415_());
        float baseDamage = Math.min(this.getDamageCap(), damage);
        boolean isFullyAdapted = false;
        boolean isAdaptable = false;
        if (this.isPhase(Phase.FIRST)) {
            isAdaptable = NightwardenAdaptRegistry.isMagicAdaptable(source, damageType);
        } else if (this.isPhase(Phase.THIRD)) {
            isAdaptable = NightwardenAdaptRegistry.isMagicAdaptable(source, damageType) || NightwardenAdaptRegistry.isAllowedInMeleePhase(source, damageType);
        }
        float maxAdaptation = this.getAdaptiveCap();
        if (this.isPhase(Phase.SECOND)) {
            if (!NightwardenAdaptRegistry.isAllowedInMeleePhase(source, damageType)) {
                baseDamage *= 0.1f;
                if (this.resurgenceCooldown == 0) {
                    this.addResurgence(baseDamage, true, false);
                }
                return super.m_6469_(source, baseDamage);
            }
            baseDamage = Math.min(baseDamage, this.getDamageCap() / 2.0f);
            return super.m_6469_(source, baseDamage);
        }
        CompoundTag adaptationData = (CompoundTag)this.f_19804_.m_135370_(DAMAGE_ADAPTATION);
        assert (damageType != null);
        String damageKey = damageType.toString();
        float currentAdaptation = adaptationData.m_128457_(damageKey);
        float adaptedDamage = baseDamage;
        if (isAdaptable) {
            float adaptationRatio = Mth.m_14036_((float)(currentAdaptation / maxAdaptation), (float)0.0f, (float)1.0f);
            float damageScale = 1.0f - 0.8f * adaptationRatio;
            adaptedDamage *= damageScale;
            if (adaptationRatio >= 1.0f) {
                isFullyAdapted = true;
                this.sendAdaptationMessage(damageType, 0.3f, attacker, false);
            }
        } else {
            adaptedDamage *= 0.1f;
            isFullyAdapted = true;
        }
        float preHealth = this.m_21223_();
        boolean result = super.m_6469_(source, adaptedDamage);
        float actualDamageTaken = preHealth - this.m_21223_();
        if (!result || actualDamageTaken <= 0.0f) {
            return result;
        }
        if (isAdaptable && currentAdaptation < maxAdaptation) {
            float newAdaptation = currentAdaptation + actualDamageTaken;
            if (newAdaptation >= maxAdaptation) {
                this.sendAdaptationMessage(damageType, 1.0f, attacker, true);
            }
            adaptationData.m_128350_(damageKey, Math.min(maxAdaptation, newAdaptation));
            this.f_19804_.m_135381_(DAMAGE_ADAPTATION, (Object)adaptationData);
        }
        boolean bl = shouldCountResurgence = !isAdaptable && !this.isPhase(Phase.SECOND) || isAdaptable && currentAdaptation > 0.0f || isFullyAdapted;
        if (shouldCountResurgence && this.resurgenceCooldown == 0) {
            this.addResurgence(actualDamageTaken, true, false);
        }
        if (this.m_5448_() != null && this.m_5448_().m_6084_() && !this.m_142582_((Entity)(target = this.m_5448_()))) {
            NightwardenAntiCheeseTeleportHandler.tryTeleport(this, target, 50, true, false);
        }
        return result;
    }

    private void triggerResurgenceBlast() {
        AABB area = this.m_20191_().m_82400_(64.0);
        for (LivingEntity entity : this.m_9236_().m_45976_(LivingEntity.class, area)) {
            entity.m_21219_();
        }
        CompoundTag adaptationData = (CompoundTag)this.f_19804_.m_135370_(DAMAGE_ADAPTATION);
        ArrayList keysToRemove = new ArrayList(adaptationData.m_128431_());
        for (String key : keysToRemove) {
            adaptationData.m_128473_(key);
        }
        this.f_19804_.m_135381_(DAMAGE_ADAPTATION, (Object)adaptationData);
        TravelopticsMessages.sendBossMessageToRange((Entity)this, (Component)Component.m_237115_((String)"entity.traveloptics.message.nightwarden_adaptation_wave"), TOColors.rgbToARGB(6619356, 0.5f), 60, 32.0, false);
        this.m_5496_((SoundEvent)TravelopticsSounds.BOSS_BREAK.get(), 1.5f, 1.0f);
        this.m_5496_((SoundEvent)TravelopticsSounds.NIGHTWARDEN_BIG_SLAM.get(), 1.5f, 1.0f);
        if (!this.m_9236_().f_46443_) {
            MagicManager.spawnParticles((Level)this.m_9236_(), (ParticleOptions)new BlastwaveParticleOptions(((SchoolType)SchoolRegistry.ENDER.get()).getTargetingColor(), 32.0f), (double)this.m_20185_(), (double)(this.m_20186_() + (double)0.165f), (double)this.m_20189_(), (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)true);
            CameraShakeManager.addCameraShake((CameraShakeData)new CameraShakeData(20, this.m_20182_(), 32.0f));
        }
    }

    protected void sendAdaptationMessage(ResourceLocation damageType, float chance, @Nullable Entity attacker, boolean showInFancy) {
        if (this.f_19796_.m_188501_() < chance && attacker instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer)attacker;
            String translationKey = "the_nightwarden_adaptation.damage_type." + damageType.m_135827_() + "." + damageType.m_135815_();
            MutableComponent message = Component.m_237110_((String)"entity.traveloptics.message.nightwarden_adapted", (Object[])new Object[]{Component.m_237115_((String)translationKey).m_130940_(ChatFormatting.RED)}).m_130940_(ChatFormatting.WHITE);
            if (showInFancy) {
                TravelopticsMessages.sendBossMessage(player, (Component)message, TOColors.rgbToARGB(6619356, 0.5f), 120, false);
            } else {
                player.m_5661_((Component)message, true);
            }
        }
    }

    public boolean isCastingAnimatedSpells(AbstractSpell ... spellsToCheck) {
        if (!this.isCasting()) {
            return false;
        }
        SpellData currentSpellData = this.getMagicData().getCastingSpell();
        if (currentSpellData == null) {
            return false;
        }
        AbstractSpell currentSpell = currentSpellData.getSpell();
        for (AbstractSpell spell : spellsToCheck) {
            if (!currentSpell.equals((Object)spell)) continue;
            return true;
        }
        return false;
    }

    public void m_146922_(float yRot) {
        if (!this.lockRotation) {
            super.m_146922_(yRot);
        }
    }

    public void m_146926_(float xRot) {
        if (!this.lockRotation) {
            super.m_146926_(xRot);
        }
    }

    private void spawnVoidTomes(Level world, boolean left) {
        float angle = (float)(left ? -90 : 90) * ((float)Math.PI / 180);
        Vec3 offset = this.m_20156_().m_82490_(6.0).m_82524_(angle);
        Vec3 eyePos = this.m_146892_();
        Vec3 dest = this.m_20182_().m_82549_(offset);
        Vec3 spawnPos = Utils.moveToRelativeGroundLevel((Level)this.m_9236_(), (Vec3)Utils.raycastForBlock((Level)this.m_9236_(), (Vec3)eyePos, (Vec3)dest, (ClipContext.Fluid)ClipContext.Fluid.NONE).m_82450_(), (int)4);
        this.spawnVoidTome(spawnPos, new Vec3(0.0, 1.0, 0.0));
    }

    private void spawnVoidTome(Vec3 basePos, Vec3 localOffset) {
        VoidTomeEntity tome = new VoidTomeEntity(this.m_9236_());
        Vec3 forward = this.m_20154_().m_82541_();
        Vec3 right = forward.m_82537_(new Vec3(0.0, 1.0, 0.0)).m_82541_();
        Vec3 up = new Vec3(0.0, 1.0, 0.0);
        Vec3 worldOffset = forward.m_82490_(localOffset.f_82481_).m_82549_(right.m_82490_(localOffset.f_82479_)).m_82549_(up.m_82490_(localOffset.f_82480_));
        Vec3 spawnPos = basePos.m_82549_(worldOffset);
        tome.m_146884_(spawnPos);
        tome.setIsSummoned();
        if (this.m_5448_() != null) {
            tome.m_6710_(this.m_5448_());
        }
        tome.m_6518_((ServerLevelAccessor)((ServerLevel)this.m_9236_()), this.m_9236_().m_6436_(this.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
        this.m_9236_().m_7967_((Entity)tome);
    }

    private void forceFaceTarget() {
        LivingEntity target = this.m_5448_();
        if (target != null) {
            double dx = target.m_20185_() - this.m_20185_();
            double dz = target.m_20189_() - this.m_20189_();
            float angle = (float)(Mth.m_14136_((double)dz, (double)dx) * 57.29577951308232) - 90.0f;
            this.m_5618_(angle);
            this.m_5616_(angle);
            this.m_146922_(angle);
        }
    }

    public boolean m_7301_(MobEffectInstance effectInstance) {
        return TravelopticsTags.EFFECTIVE_FOR_NIGHTWARDEN_LOOKUP.contains((Object)effectInstance.m_19544_()) && super.m_7301_(effectInstance);
    }

    public boolean m_7307_(Entity pEntity) {
        return super.m_7307_(pEntity) || pEntity.m_6095_().m_204039_(TravelopticsTags.TEAM_THE_NIGHTWARDEN);
    }

    public boolean m_142079_() {
        return false;
    }

    public boolean m_5825_() {
        return true;
    }

    public boolean m_6040_() {
        return true;
    }

    public boolean m_142535_(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false;
    }

    public boolean m_6785_(double p_21542_) {
        return false;
    }

    protected boolean m_8028_() {
        return false;
    }

    protected boolean m_7341_(Entity p_31508_) {
        return false;
    }

    protected boolean m_6107_() {
        return this.isPhaseTransitioning() || super.m_6107_();
    }

    private void populateDeathLootForNightwarden() {
        ResourceLocation resourcelocation = this.m_5743_();
        DamageSource damageSource = this.m_21225_();
        if (damageSource != null) {
            LootTable loottable = this.m_9236_().m_7654_().m_278653_().m_278676_(resourcelocation);
            LootParams.Builder lootparams$builder = new LootParams.Builder((ServerLevel)this.m_9236_()).m_287286_(LootContextParams.f_81455_, (Object)this).m_287286_(LootContextParams.f_81460_, (Object)this.m_20182_()).m_287286_(LootContextParams.f_81457_, (Object)damageSource).m_287289_(LootContextParams.f_81458_, (Object)damageSource.m_7639_()).m_287289_(LootContextParams.f_81459_, (Object)damageSource.m_7640_());
            if (this.f_20888_ != null) {
                lootparams$builder = lootparams$builder.m_287286_(LootContextParams.f_81456_, (Object)this.f_20888_).m_287239_(this.f_20888_.m_36336_());
            }
            LootParams lootparams = lootparams$builder.m_287235_(LootContextParamSets.f_81415_);
            loottable.m_287276_(lootparams, this.m_287233_(), this.deathItems::add);
        }
        this.collectedLoot = true;
    }

    public ItemEntity m_19983_(ItemStack stack) {
        ItemEntity itementity = this.m_5552_(stack, 0.0f);
        if (itementity != null) {
            itementity.m_20256_(itementity.m_20184_().m_82542_(0.0, 1.5, 0.0));
            itementity.m_146915_(true);
            itementity.m_32064_();
        }
        return itementity;
    }

    protected void m_7625_(DamageSource damageSource, boolean b) {
    }

    public void m_6457_(ServerPlayer pPlayer) {
        super.m_6457_(pPlayer);
        this.bossEvent.m_6543_(pPlayer);
        this.bossEvent1.m_6543_(pPlayer);
        Messages.sendToPlayer((Object)new ClientboundEntityEvent((Entity)this, 1), (ServerPlayer)pPlayer);
    }

    public void m_6452_(ServerPlayer pPlayer) {
        super.m_6452_(pPlayer);
        this.bossEvent.m_6539_(pPlayer);
        this.bossEvent1.m_6539_(pPlayer);
        Messages.sendToPlayer((Object)new ClientboundEntityEvent((Entity)this, 0), (ServerPlayer)pPlayer);
    }

    public void m_142687_(Entity.RemovalReason reason) {
        NightwardenTargetVelocityTracker.removeBossTracker(this);
        super.m_142687_(reason);
    }

    private void createPhaseTransitionParticles(int ticksElapsed) {
        if (ticksElapsed > 5) {
            return;
        }
        double centerX = this.m_20185_();
        double centerY = this.m_20186_() + (double)(this.m_20206_() / 2.0f);
        double centerZ = this.m_20189_();
        float intensity = (float)(6 - ticksElapsed) / 5.0f;
        int particleCount = (int)(15.0f * intensity);
        for (int i = 0; i < particleCount; ++i) {
            double angle = Math.PI * 2 * (double)i / (double)particleCount;
            double distance = 0.5 + (double)ticksElapsed * 0.8;
            double offsetX = Math.cos(angle) * distance;
            double offsetZ = Math.sin(angle) * distance;
            double offsetY = this.f_19796_.m_188583_() * 0.3 * (double)intensity;
            ((ServerLevel)this.m_9236_()).m_8767_((ParticleOptions)((SimpleParticleType)TravelopticsParticles.PURPLE_STAR_INWARD_PARTICLE.get()), centerX + offsetX, centerY + offsetY, centerZ + offsetZ, 1, 0.0, 0.0, 0.0, 0.0);
        }
        int spikeCount = (int)(8.0f * intensity);
        for (int i = 0; i < spikeCount; ++i) {
            double angle = Math.PI * 2 * (double)i / (double)spikeCount;
            double distance = 0.3 + (double)ticksElapsed * 0.6;
            double offsetX = Math.cos(angle) * distance;
            double offsetZ = Math.sin(angle) * distance;
            double offsetY = this.f_19796_.m_188583_() * 0.2 * (double)intensity;
            ((ServerLevel)this.m_9236_()).m_8767_((ParticleOptions)((SimpleParticleType)TravelopticsParticles.ABYSS_SPIKE_PARTICLE.get()), centerX + offsetX, centerY + offsetY, centerZ + offsetZ, 1, 0.0, 0.0, 0.0, 0.0);
        }
    }

    protected LookControl createLookControl() {
        return new LookControl((Mob)this){

            protected boolean m_8106_() {
                return !NightwardenBossEntity.this.isCasting();
            }
        };
    }

    protected MoveControl createMoveControl() {
        return new MoveControl((Mob)this){

            protected float m_24991_(float pSourceAngle, float pTargetAngle, float pMaximumChange) {
                double d1;
                double d0 = this.f_24975_ - this.f_24974_.m_20185_();
                if (d0 * d0 + (d1 = this.f_24977_ - this.f_24974_.m_20189_()) * d1 < 0.5) {
                    return pSourceAngle;
                }
                return super.m_24991_(pSourceAngle, pTargetAngle, pMaximumChange * 0.25f);
            }
        };
    }

    private void handlePhaseOneToTwoTransition() {
        if (this.isPhase(Phase.TRANSITION_1)) {
            this.phaseOneToTwoKeyframedMovement();
            this.phaseOneToTwoKeyframes();
        }
    }

    private void phaseOneToTwoKeyframedMovement() {
        float forwardForce;
        int elapsed = 257 - this.transitionTime;
        if (elapsed < 165) {
            forwardForce = 0.03f;
        } else if (elapsed < 193) {
            float t = ((float)elapsed - 165.0f) / 28.0f;
            forwardForce = 0.018f * (1.0f - t);
        } else {
            forwardForce = 0.0f;
        }
        float yawRad = this.m_146908_() * ((float)Math.PI / 180);
        double forceX = -Mth.m_14031_((float)yawRad) * forwardForce;
        double forceZ = Mth.m_14089_((float)yawRad) * forwardForce;
        this.m_20256_(this.m_20184_().m_82520_(forceX, 0.0, forceZ));
    }

    private void phaseOneToTwoKeyframes() {
        int elapsed = 257 - this.transitionTime;
        if (elapsed == 5 || elapsed == 30 || elapsed == 55 || elapsed == 80 || elapsed == 105 || elapsed == 130 || elapsed == 155 || elapsed == 167 || elapsed == 179 || elapsed == 193) {
            this.m_5496_((SoundEvent)TravelopticsSounds.NIGHTWARDEN_WALK.get(), 3.0f, 1.0f);
        }
        if (elapsed == 95) {
            this.m_5496_((SoundEvent)TravelopticsSounds.NIGHTWARDEN_SCYTHE_SPIN.get(), 1.0f, 1.0f);
        }
        if (elapsed == 240) {
            this.m_5496_((SoundEvent)TravelopticsSounds.NIGHTWARDEN_BIG_SLAM.get(), 2.0f, 1.0f);
            if (!this.m_9236_().m_5776_()) {
                CameraShakeManager.addCameraShake((CameraShakeData)new CameraShakeData(20, this.m_20182_(), 16.0f));
                float offset = 1.2f;
                float yawRad = this.m_146908_() * ((float)Math.PI / 180);
                double offsetX = -Mth.m_14031_((float)yawRad) * offset;
                double offsetZ = Mth.m_14089_((float)yawRad) * offset;
                MagicManager.spawnParticles((Level)this.m_9236_(), (ParticleOptions)new BlastwaveParticleOptions(((SchoolType)SchoolRegistry.ENDER.get()).getTargetingColor(), 16.0f), (double)(this.m_20185_() + offsetX), (double)(this.m_20186_() + (double)0.165f), (double)(this.m_20189_() + offsetZ), (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)true);
            }
            this.applyKnockbackToNearbyEntities(8.0);
        }
    }

    private void applyKnockbackToNearbyEntities(double radius) {
        for (LivingEntity entity : TOEntityUtils.getEntitiesInRange((Entity)this, LivingEntity.class, radius)) {
            double dz;
            double dx = entity.m_20185_() - this.m_20185_();
            double distance = Math.sqrt(dx * dx + (dz = entity.m_20189_() - this.m_20189_()) * dz);
            if (!(distance > 0.0)) continue;
            double knockbackStrength = 2.5;
            entity.m_20256_(entity.m_20184_().m_82520_(dx / distance * knockbackStrength, 0.4, dz / distance * knockbackStrength));
            entity.f_19864_ = true;
        }
    }

    private void handleSupernovaSequence() {
        if (this.isPhase(Phase.TRANSITION_2)) {
            int elapsed = 410 - this.transitionTime;
            if (elapsed == 105) {
                TOScreenShakeEntity.createScreenShake(this.m_9236_(), this.m_20182_(), 32.0f, 0.035f, 15, 0, 20, false);
                this.m_5496_((SoundEvent)TravelopticsSounds.NIGHTWARDEN_DEFEATED_DEATH_STARE.get(), 1.5f, 0.9f);
            }
            if (elapsed == 135) {
                this.m_5496_((SoundEvent)TravelopticsSounds.NOCTURNAL_UPLIFT.get(), 2.5f, 1.0f);
            }
            if (elapsed >= 135 && elapsed <= 385) {
                double currentY;
                double deltaY;
                double baseHoverHeight;
                double baseHoverOffset = 4.0;
                BlockPos groundPos = this.m_20183_().m_7495_();
                while (this.m_9236_().m_46859_(groundPos) && groundPos.m_123342_() > this.m_9236_().m_141937_()) {
                    groundPos = groundPos.m_7495_();
                }
                double groundHeight = groundPos.m_123342_() + 1;
                double hoverY = baseHoverHeight = groundHeight + baseHoverOffset;
                double amplitude = 0.12;
                double speed = 0.2;
                if (elapsed >= 175 && elapsed <= 337) {
                    hoverY += amplitude * Math.sin((double)(elapsed - 175) * speed);
                }
                if (elapsed == 355) {
                    hoverY += 0.35;
                }
                if (Math.abs(deltaY = hoverY - (currentY = this.m_20186_())) > 0.05) {
                    Vec3 motion = this.m_20184_();
                    this.m_20334_(motion.f_82479_, deltaY * 0.2, motion.f_82481_);
                }
                this.f_19789_ = 0.0f;
                this.m_6853_(false);
            }
            if (elapsed == 235 && !this.m_9236_().m_5776_()) {
                DyingStarEntity dyingStar = new DyingStarEntity(this.m_9236_());
                dyingStar.m_7678_(this.m_20185_(), this.m_20186_() + 8.0, this.m_20189_(), this.m_146908_(), 0.0f);
                dyingStar.m_5602_((Entity)this);
                dyingStar.setRadius(28.0f);
                dyingStar.setDamage(60.0f);
                dyingStar.setBlackholeDuration(640);
                dyingStar.setBlackholeRadius(28.0f);
                dyingStar.setBlackholeDamage(6.0f);
                dyingStar.setOwnerVoidDamagePercent(0.05f);
                this.m_9236_().m_7967_((Entity)dyingStar);
            }
            NightwardenPhaseThreeSwapAnimatedParticle.handlePhaseTwoToThreeAnimatedParticles(this, elapsed);
        }
    }

    public void playAnimation(String animationId) {
        try {
            NightwardenAttackGoal.AttackType attackType = NightwardenAttackGoal.AttackType.valueOf(animationId);
            this.animationToPlay = RawAnimation.begin().thenPlay(attackType.data.animationId);
            this.lastAnimationId = attackType.data.animationId;
        }
        catch (Exception ignored) {
            System.err.println("NightwardenBoss failed to play animation: " + animationId);
        }
    }

    private PlayState meleePredicate(AnimationState<NightwardenBossEntity> animationEvent) {
        AnimationController controller = animationEvent.getController();
        if (this.animationToPlay != null) {
            controller.forceAnimationReset();
            controller.setAnimation(this.animationToPlay);
            this.animationToPlay = null;
        }
        return PlayState.CONTINUE;
    }

    private PlayState extraIdlePredicate(AnimationState<NightwardenBossEntity> event) {
        event.getController().setAnimation(RawAnimation.begin().thenLoop("nightwarden_extra_idles"));
        return PlayState.CONTINUE;
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController[]{this.meleeController});
        controllerRegistrar.add(new AnimationController[]{new AnimationController((GeoAnimatable)this, "nightwarden_transition", 0, state -> PlayState.STOP).triggerableAnim("phase_1_to_2", this.phase_transition_1_animation).triggerableAnim("phase_2_to_3", this.phase_transition_2_animation)});
        controllerRegistrar.add(new AnimationController[]{new AnimationController((GeoAnimatable)this, "extra_idle_controller", 10, this::extraIdlePredicate)});
        super.registerControllers(controllerRegistrar);
    }

    public boolean shouldAlwaysAnimateHead() {
        return !this.isPhaseTransitioning() && !this.isCastingAnimatedSpells((AbstractSpell)SpellRegistry.STARFALL_SPELL.get()) && (this.lastAnimationId == null || !NO_HEAD_ANIMATION_IDS.contains(this.lastAnimationId));
    }

    public boolean isAnimating() {
        return this.isPhaseTransitioning() || this.meleeController.getAnimationState() == AnimationController.State.RUNNING || this.isCastingAnimatedSpells((AbstractSpell)SpellRegistry.STARFALL_SPELL.get()) || super.isAnimating();
    }

    public boolean shouldAlwaysAnimateLegs() {
        return !this.isPhaseTransitioning() && !this.isCastingAnimatedSpells((AbstractSpell)SpellRegistry.STARFALL_SPELL.get()) && this.meleeController.getAnimationState() != AnimationController.State.RUNNING;
    }

    public boolean shouldPointArmsWhileCasting() {
        return !this.isCastingAnimatedSpells((AbstractSpell)SpellRegistry.STARFALL_SPELL.get());
    }

    public static enum Phase {
        FIRST(0),
        TRANSITION_1(1),
        SECOND(2),
        TRANSITION_2(3),
        THIRD(4),
        DEATH(5);

        final int value;

        private Phase(int value) {
            this.value = value;
        }
    }
}

