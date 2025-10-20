/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity
 *  io.redspace.ironsspellbooks.api.registry.AttributeRegistry
 *  io.redspace.ironsspellbooks.api.util.CameraShakeData
 *  io.redspace.ironsspellbooks.api.util.CameraShakeManager
 *  io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMob
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.tags.DamageTypeTags
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.attributes.Attribute
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.ServerLevelAccessor
 *  net.minecraft.world.phys.Vec3
 *  software.bernie.geckolib.core.animatable.GeoAnimatable
 *  software.bernie.geckolib.core.animation.AnimatableManager$ControllerRegistrar
 *  software.bernie.geckolib.core.animation.AnimationController
 *  software.bernie.geckolib.core.animation.AnimationState
 *  software.bernie.geckolib.core.animation.RawAnimation
 *  software.bernie.geckolib.core.object.PlayState
 */
package com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.nightwarden_defeated;

import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.NightwardenBossEntity;
import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.animated_particle.DefeatedEntityRespawnCooldown;
import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.animated_particle.DefeatedEntitySpawnTrigger;
import com.gametechbc.traveloptics.init.TravelopticsEntities;
import com.gametechbc.traveloptics.init.TravelopticsItems;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import com.gametechbc.traveloptics.util.TravelopticsParticleHelper;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.util.CameraShakeData;
import io.redspace.ironsspellbooks.api.util.CameraShakeManager;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMob;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public class NightwardenDefeatedEntity
extends AbstractSpellCastingMob {
    private static final EntityDataAccessor<Boolean> TRIGGERED = SynchedEntityData.m_135353_(NightwardenDefeatedEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Integer> COOLDOWN_TICKS = SynchedEntityData.m_135353_(NightwardenDefeatedEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
    private int currentAnimTime;
    private static final int ANIM_DURATION = 105;
    private int maxCooldown = 0;
    private final RawAnimation idle = RawAnimation.begin().thenLoop("defeated_base_pose");
    private final RawAnimation rise = RawAnimation.begin().thenPlay("begin_again");

    public NightwardenDefeatedEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
        this.m_21530_();
    }

    public void m_7380_(CompoundTag tag) {
        super.m_7380_(tag);
        tag.m_128405_("MaxCooldown", this.maxCooldown);
        tag.m_128405_("CooldownTicks", this.getCooldown());
    }

    public void m_7378_(CompoundTag tag) {
        super.m_7378_(tag);
        this.maxCooldown = tag.m_128451_("MaxCooldown");
        if (tag.m_128441_("CooldownTicks")) {
            this.setCooldown(tag.m_128451_("CooldownTicks"));
        }
    }

    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(TRIGGERED, (Object)false);
        this.f_19804_.m_135372_(COOLDOWN_TICKS, (Object)0);
    }

    public boolean triggered() {
        return (Boolean)this.f_19804_.m_135370_(TRIGGERED);
    }

    private void trigger() {
        if (!this.triggered()) {
            if (!this.m_9236_().f_46443_) {
                CameraShakeManager.addCameraShake((CameraShakeData)new CameraShakeData(120, this.m_20182_(), 32.0f));
                ScreenShake_Entity.ScreenShake((Level)this.m_9236_(), (Vec3)this.m_20182_(), (float)15.0f, (float)0.1f, (int)15, (int)20);
            }
            this.f_19804_.m_135381_(TRIGGERED, (Object)true);
        }
    }

    public int getCooldown() {
        return (Integer)this.f_19804_.m_135370_(COOLDOWN_TICKS);
    }

    public void setCooldown(int ticks) {
        this.f_19804_.m_135381_(COOLDOWN_TICKS, (Object)ticks);
        if (ticks > this.maxCooldown) {
            this.maxCooldown = ticks;
        }
    }

    public boolean isOnCooldown() {
        return this.getCooldown() > 0;
    }

    public int getMaxCooldown() {
        return this.maxCooldown > 0 ? this.maxCooldown : 20;
    }

    private void tickCooldown() {
        if (this.getCooldown() > 0) {
            this.setCooldown(this.getCooldown() - 1);
        }
    }

    public boolean m_6469_(DamageSource source, float amount) {
        if (source.m_269533_(DamageTypeTags.f_268738_)) {
            this.m_146870_();
            return true;
        }
        return false;
    }

    protected InteractionResult m_6071_(Player player, InteractionHand hand) {
        if (!this.triggered()) {
            if (this.getCooldown() > 0) {
                player.m_5661_((Component)Component.m_237115_((String)"entity.traveloptics.message.defeated_cooldown_on"), true);
                return InteractionResult.SUCCESS;
            }
            if (hand == InteractionHand.MAIN_HAND && player.m_21205_().m_150930_((Item)TravelopticsItems.EXCRUCIS.get())) {
                this.trigger();
                player.m_21205_().m_41774_(1);
                return InteractionResult.SUCCESS;
            }
            player.m_5661_((Component)Component.m_237115_((String)"entity.traveloptics.message.defeated_no_item"), true);
            return InteractionResult.SUCCESS;
        }
        return super.m_6071_(player, hand);
    }

    public void m_8119_() {
        super.m_8119_();
        this.tickCooldown();
        if (this.triggered()) {
            ++this.currentAnimTime;
            if (this.currentAnimTime == 1) {
                this.m_5496_((SoundEvent)TravelopticsSounds.NIGHTWARDEN_DEFEATED_DEATH_STARE.get(), 1.5f, 1.0f);
            }
            if (this.currentAnimTime == 100) {
                this.m_5496_((SoundEvent)TravelopticsSounds.NIGHTWARDEN_BIG_SLAM_CLONES.get(), 1.5f, 1.2f);
                if (!this.m_9236_().f_46443_) {
                    ScreenShake_Entity.ScreenShake((Level)this.m_9236_(), (Vec3)this.m_20182_(), (float)15.0f, (float)0.1f, (int)15, (int)20);
                }
            }
            if (!this.m_9236_().f_46443_ && this.currentAnimTime > 105) {
                NightwardenBossEntity boss = new NightwardenBossEntity((EntityType<? extends AbstractSpellCastingMob>)((EntityType)TravelopticsEntities.NIGHTWARDEN_BOSS.get()), this.m_9236_());
                boss.m_20219_(this.m_20182_().m_82520_(0.0, 1.0, 0.0));
                boss.m_6518_((ServerLevelAccessor)((ServerLevel)this.m_9236_()), this.m_9236_().m_6436_(boss.m_20097_()), MobSpawnType.TRIGGERED, null, null);
                int playerCount = Math.max(this.m_9236_().m_45976_(Player.class, boss.m_20191_().m_82400_(32.0)).size(), 1);
                boss.m_21204_().m_22146_(Attributes.f_22276_).m_22125_(new AttributeModifier("Gank Health Bonus", (double)(playerCount - 1) * 0.5, AttributeModifier.Operation.MULTIPLY_BASE));
                boss.m_21153_(boss.m_21233_());
                boss.m_21204_().m_22146_(Attributes.f_22281_).m_22125_(new AttributeModifier("Gank Damage Bonus", (double)(playerCount - 1) * 0.25, AttributeModifier.Operation.MULTIPLY_BASE));
                boss.m_21204_().m_22146_((Attribute)AttributeRegistry.SPELL_RESIST.get()).m_22125_(new AttributeModifier("Gank Spell Resist Bonus", (double)(playerCount - 1) * 0.1, AttributeModifier.Operation.MULTIPLY_BASE));
                boss.m_21530_();
                this.m_9236_().m_7967_((Entity)boss);
                this.m_146870_();
            }
        }
        if (this.m_9236_().f_46443_) {
            if (!this.triggered()) {
                int particleCount = 5;
                boolean cooldown = this.isOnCooldown();
                for (int i = 0; i < particleCount; ++i) {
                    double offsetX = (this.f_19796_.m_188500_() - 0.5) * (double)this.m_20205_();
                    double offsetY = this.f_19796_.m_188500_() * (double)this.m_20206_();
                    double offsetZ = (this.f_19796_.m_188500_() - 0.5) * (double)this.m_20205_();
                    double velocityX = (this.f_19796_.m_188500_() - 0.5) * 0.04;
                    double velocityZ = (this.f_19796_.m_188500_() - 0.5) * 0.04;
                    double velocityY = 0.1 + this.f_19796_.m_188500_() * 0.1;
                    SimpleParticleType particle = cooldown ? ParticleTypes.f_123762_ : (this.f_19796_.m_188501_() < 0.94f ? ParticleTypes.f_123762_ : TravelopticsParticleHelper.ABYSS_SPIKE_PARTICLE);
                    this.m_9236_().m_7106_((ParticleOptions)particle, this.m_20185_() + offsetX, this.m_20186_() + offsetY, this.m_20189_() + offsetZ, velocityX, velocityY, velocityZ);
                }
                DefeatedEntityRespawnCooldown.drawCooldownRuneCircle(this, this.isOnCooldown(), this.getCooldown(), this.getMaxCooldown());
            } else {
                float animProgress = (float)this.currentAnimTime / 105.0f;
                animProgress = Mth.m_14036_((float)animProgress, (float)0.0f, (float)1.0f);
                DefeatedEntitySpawnTrigger.handleBeginAgainAnimatedParticles(this, animProgress, this.currentAnimTime);
            }
        }
    }

    public boolean m_6087_() {
        return true;
    }

    public boolean m_6094_() {
        return false;
    }

    public boolean m_5829_() {
        return true;
    }

    protected boolean m_8028_() {
        return false;
    }

    public boolean m_21532_() {
        return true;
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController[]{new AnimationController((GeoAnimatable)this, "idle", 0, this::animationPredicate)});
    }

    private PlayState animationPredicate(AnimationState<?> event) {
        if (this.triggered()) {
            event.getController().setAnimation(this.rise);
        } else {
            event.getController().setAnimation(this.idle);
        }
        return PlayState.CONTINUE;
    }

    public boolean shouldBeExtraAnimated() {
        return false;
    }

    public boolean shouldAlwaysAnimateHead() {
        return false;
    }
}

