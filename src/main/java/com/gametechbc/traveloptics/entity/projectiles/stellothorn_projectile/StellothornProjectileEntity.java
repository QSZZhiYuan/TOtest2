/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.api.registry.SchoolRegistry
 *  io.redspace.ironsspellbooks.api.spells.SchoolType
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientGamePacketListener
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.MobType
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.projectile.AbstractArrow
 *  net.minecraft.world.entity.projectile.AbstractArrow$Pickup
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.enchantment.EnchantmentHelper
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.EntityHitResult
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.network.NetworkHooks
 *  net.minecraftforge.network.PlayMessages$SpawnEntity
 *  software.bernie.geckolib.animatable.GeoEntity
 *  software.bernie.geckolib.core.animatable.GeoAnimatable
 *  software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache
 *  software.bernie.geckolib.core.animation.AnimatableManager$ControllerRegistrar
 *  software.bernie.geckolib.core.animation.AnimationController
 *  software.bernie.geckolib.core.animation.AnimationState
 *  software.bernie.geckolib.core.animation.RawAnimation
 *  software.bernie.geckolib.core.object.PlayState
 *  software.bernie.geckolib.util.GeckoLibUtil
 */
package com.gametechbc.traveloptics.entity.projectiles.stellothorn_projectile;

import com.gametechbc.traveloptics.api.particle.AdvancedSphereParticleManager;
import com.gametechbc.traveloptics.api.particle.ParticleDirection;
import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.nightwarden_clone.nightwarden_drop_slam_clone.NightwardenDropSlamCloneEntity;
import com.gametechbc.traveloptics.init.TravelopticsEntities;
import com.gametechbc.traveloptics.init.TravelopticsItems;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import com.gametechbc.traveloptics.util.TravelopticsParticleHelper;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class StellothornProjectileEntity
extends AbstractArrow
implements GeoEntity {
    private static final EntityDataAccessor<Boolean> RETURNING = SynchedEntityData.m_135353_(StellothornProjectileEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> ENABLE_HEAL_ON_RETURN = SynchedEntityData.m_135353_(StellothornProjectileEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Integer> ORIGINAL_SLOT = SynchedEntityData.m_135353_(StellothornProjectileEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    private int originalSlot = -1;
    private boolean healed = false;
    private final List<UUID> victims = new ArrayList<UUID>();
    private float tridentDamage = 10.0f;
    private boolean triggeredEruption = false;
    private float cloneDamage = 6.0f;
    private boolean triggerAoEOnReturn = false;
    private boolean aoeTriggered = false;
    private ItemStack spearItem = new ItemStack((ItemLike)TravelopticsItems.STELLOTHORN.get());
    private final RawAnimation ANIMATION_LOOP = RawAnimation.begin().thenLoop("stellothorn_projectile_idle");
    private final AnimationController controller = new AnimationController((GeoAnimatable)this, "scythe_controller", 0, this::animationPredicate);

    public StellothornProjectileEntity(EntityType entityType, Level level) {
        super(entityType, level);
    }

    public StellothornProjectileEntity(Level level, LivingEntity shooter, ItemStack itemStack) {
        super((EntityType)TravelopticsEntities.STELLOTHORN_PROJECTILE.get(), shooter, level);
        this.spearItem = itemStack.m_41777_();
    }

    public StellothornProjectileEntity(Level level, double x, double y, double z) {
        super((EntityType)TravelopticsEntities.STELLOTHORN_PROJECTILE.get(), x, y, z, level);
    }

    public StellothornProjectileEntity(PlayMessages.SpawnEntity spawnEntity, Level level) {
        this((EntityType)TravelopticsEntities.STELLOTHORN_PROJECTILE.get(), level);
        this.m_20011_(this.m_142242_());
    }

    public Packet<ClientGamePacketListener> m_5654_() {
        return NetworkHooks.getEntitySpawningPacket((Entity)this);
    }

    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(RETURNING, (Object)false);
        this.f_19804_.m_135372_(ENABLE_HEAL_ON_RETURN, (Object)false);
        this.f_19804_.m_135372_(ORIGINAL_SLOT, (Object)-1);
    }

    protected ItemStack m_7941_() {
        return this.spearItem;
    }

    public boolean isReturning() {
        return (Boolean)this.f_19804_.m_135370_(RETURNING);
    }

    public void setReturning(boolean returning) {
        this.f_19804_.m_135381_(RETURNING, (Object)returning);
    }

    public boolean isHealOnReturnEnabled() {
        return (Boolean)this.f_19804_.m_135370_(ENABLE_HEAL_ON_RETURN);
    }

    public void setHealOnReturnEnabled(boolean enabled) {
        this.f_19804_.m_135381_(ENABLE_HEAL_ON_RETURN, (Object)enabled);
    }

    public float getTridentDamage() {
        return this.tridentDamage;
    }

    public void setTridentDamage(float damage) {
        this.tridentDamage = damage;
    }

    public float getCloneDamage() {
        return this.cloneDamage;
    }

    public void setCloneDamage(float cloneDamage) {
        this.cloneDamage = cloneDamage;
    }

    public void setTriggerAoEOnReturn(boolean trigger) {
        this.triggerAoEOnReturn = trigger;
    }

    public boolean isTriggerAoEOnReturn() {
        return this.triggerAoEOnReturn;
    }

    public void setOriginalSlot(int slot) {
        this.originalSlot = slot;
        this.f_19804_.m_135381_(ORIGINAL_SLOT, (Object)slot);
    }

    public int getOriginalSlot() {
        return (Integer)this.f_19804_.m_135370_(ORIGINAL_SLOT);
    }

    protected float m_6882_() {
        return 0.99f;
    }

    public void m_8119_() {
        Entity owner;
        super.m_8119_();
        if (!this.isReturning() && this.f_19797_ >= 15) {
            this.beginReturn();
        }
        Entity entity = this.m_19749_();
        if ((this.f_36703_ || this.m_36797_()) && entity != null) {
            if (!this.isAcceptibleReturnOwner()) {
                if (!this.m_9236_().f_46443_ && this.f_36705_ == AbstractArrow.Pickup.ALLOWED) {
                    this.m_5552_(this.m_7941_(), 0.1f);
                }
                this.m_146870_();
            } else if (!this.isReturning()) {
                this.beginReturn();
            }
        }
        if (this.isReturning() && (owner = this.m_19749_()) != null && this.isAcceptibleReturnOwner()) {
            Vec3 vec3 = owner.m_146892_().m_82546_(this.m_20182_()).m_82541_();
            this.m_36790_(true);
            this.m_20256_(this.m_20184_().m_82490_(0.95).m_82549_(vec3.m_82490_(0.3)));
            if (!this.healed && this.isHealOnReturnEnabled() && !this.victims.isEmpty() && owner instanceof LivingEntity) {
                LivingEntity livingOwner = (LivingEntity)owner;
                float healAmount = livingOwner.m_21233_() * 0.15f;
                livingOwner.m_5634_(healAmount);
                this.healed = true;
            }
        }
        if (!this.m_9236_().f_46443_) {
            AABB boundingBox = this.m_20191_();
            for (Entity target : this.m_9236_().m_45933_((Entity)this, boundingBox)) {
                if (!this.m_5603_(target) || this.victims.contains(target.m_20148_())) continue;
                this.damageEntity(target);
            }
        }
        if (this.f_19797_ % 5 == 0) {
            float pitch = 0.9f + this.f_19796_.m_188501_() * 0.2f;
            this.m_5496_((SoundEvent)TravelopticsSounds.NIGHTWARDEN_SWING.get(), 0.7f, pitch);
        }
        this.spawnParticles();
    }

    private void beginReturn() {
        this.setReturning(true);
        this.m_36790_(true);
        this.f_36703_ = false;
        if (!this.aoeTriggered && this.triggerAoEOnReturn && !this.m_9236_().f_46443_) {
            this.aoeTriggered = true;
            float aoeRadius = 6.0f;
            float aoeDamage = this.getTridentDamage() * 0.5f;
            AABB area = new AABB(this.m_20185_() - (double)aoeRadius, this.m_20186_() - (double)aoeRadius, this.m_20189_() - (double)aoeRadius, this.m_20185_() + (double)aoeRadius, this.m_20186_() + (double)aoeRadius, this.m_20189_() + (double)aoeRadius);
            List targets = this.m_9236_().m_6443_(LivingEntity.class, area, e -> e.m_6084_() && e != this.m_19749_());
            for (LivingEntity target : targets) {
                target.m_6469_(this.m_269291_().m_269525_((Entity)this, this.m_19749_()), aoeDamage);
            }
            if (this.m_19749_() != null) {
                this.m_9236_().m_6263_(null, this.m_19749_().m_20185_(), this.m_19749_().m_20186_(), this.m_19749_().m_20189_(), (SoundEvent)TravelopticsSounds.END_ERUPTION_BLAST.get(), SoundSource.PLAYERS, 0.8f, 1.0f);
            }
            AdvancedSphereParticleManager.spawnParticles(this.m_9236_(), this.m_20185_(), this.m_20186_(), this.m_20189_(), 70, TravelopticsParticleHelper.ABYSS_SPIKE_PARTICLE, ParticleDirection.OUTWARD, 8.0, true);
            MagicManager.spawnParticles((Level)this.m_9236_(), (ParticleOptions)new BlastwaveParticleOptions(((SchoolType)SchoolRegistry.ENDER.get()).getTargetingColor(), aoeRadius), (double)this.m_20185_(), (double)(this.m_20186_() + (double)0.165f), (double)this.m_20189_(), (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)true);
        }
    }

    protected boolean m_142470_(Player player) {
        Entity entity = this.m_19749_();
        if (entity == null || entity.equals((Object)player)) {
            ItemStack currentItem;
            int originalSlot;
            if (this.f_36705_ == AbstractArrow.Pickup.CREATIVE_ONLY && (originalSlot = this.getOriginalSlot()) >= 0 && originalSlot < 9 && ItemStack.m_41656_((ItemStack)(currentItem = player.m_150109_().m_8020_(originalSlot)), (ItemStack)this.m_7941_())) {
                player.m_7938_((Entity)this, 1);
                this.m_146870_();
                return true;
            }
            originalSlot = this.getOriginalSlot();
            if (originalSlot >= 0 && originalSlot < 9) {
                currentItem = player.m_150109_().m_8020_(originalSlot);
                if (currentItem.m_41619_()) {
                    player.m_150109_().m_6836_(originalSlot, this.m_7941_());
                    player.m_7938_((Entity)this, 1);
                    this.m_146870_();
                    return true;
                }
                if (ItemStack.m_41656_((ItemStack)currentItem, (ItemStack)this.m_7941_()) && currentItem.m_41613_() < currentItem.m_41741_()) {
                    currentItem.m_41769_(1);
                    player.m_7938_((Entity)this, 1);
                    this.m_146870_();
                    return true;
                }
                ItemStack tridentItem = this.m_7941_();
                boolean movedItem = false;
                for (int i = 0; i < player.m_150109_().m_6643_(); ++i) {
                    if (i == originalSlot) continue;
                    ItemStack slotItem = player.m_150109_().m_8020_(i);
                    if (slotItem.m_41619_()) {
                        player.m_150109_().m_6836_(i, currentItem.m_41777_());
                        player.m_150109_().m_6836_(originalSlot, tridentItem);
                        player.m_7938_((Entity)this, 1);
                        this.m_146870_();
                        movedItem = true;
                        break;
                    }
                    if (!ItemStack.m_41656_((ItemStack)slotItem, (ItemStack)currentItem) || slotItem.m_41613_() + currentItem.m_41613_() > slotItem.m_41741_()) continue;
                    slotItem.m_41769_(currentItem.m_41613_());
                    player.m_150109_().m_6836_(originalSlot, tridentItem);
                    player.m_7938_((Entity)this, 1);
                    this.m_146870_();
                    movedItem = true;
                    break;
                }
                if (!movedItem) {
                    player.m_36176_(currentItem, false);
                    player.m_150109_().m_6836_(originalSlot, tridentItem);
                    player.m_7938_((Entity)this, 1);
                    this.m_146870_();
                }
                return true;
            }
            return super.m_142470_(player);
        }
        return false;
    }

    protected void m_8060_(BlockHitResult blockHitResult) {
        super.m_8060_(blockHitResult);
        if (!this.m_9236_().f_46443_) {
            // empty if block
        }
    }

    private boolean isAcceptibleReturnOwner() {
        Entity entity = this.m_19749_();
        if (entity != null && entity.m_6084_()) {
            return !(entity instanceof ServerPlayer) || !entity.m_5833_();
        }
        return false;
    }

    protected boolean m_5603_(Entity entity) {
        return entity != this.m_19749_() && super.m_5603_(entity);
    }

    private void damageEntity(Entity target) {
        if (this.victims.contains(target.m_20148_())) {
            return;
        }
        float baseDamage = this.getTridentDamage();
        if (target instanceof LivingEntity) {
            LivingEntity livingTarget = (LivingEntity)target;
            baseDamage += EnchantmentHelper.m_44833_((ItemStack)this.m_7941_(), (MobType)livingTarget.m_6336_());
        }
        Entity attacker = this.m_19749_();
        DamageSource damageSource = this.m_269291_().m_269525_((Entity)this, (Entity)(attacker == null ? this : attacker));
        if (target.m_6469_(damageSource, baseDamage)) {
            if (target.m_6095_() == EntityType.f_20566_) {
                return;
            }
            if (target instanceof LivingEntity) {
                LivingEntity livingTarget = (LivingEntity)target;
                if (attacker instanceof LivingEntity) {
                    LivingEntity livingAttacker = (LivingEntity)attacker;
                    EnchantmentHelper.m_44823_((LivingEntity)livingTarget, (Entity)livingAttacker);
                    EnchantmentHelper.m_44896_((LivingEntity)livingAttacker, (Entity)livingTarget);
                }
                this.m_7761_(livingTarget);
            }
            if (!this.triggeredEruption && attacker instanceof LivingEntity) {
                LivingEntity livingAttacker = (LivingEntity)attacker;
                this.triggeredEruption = true;
                NightwardenDropSlamCloneEntity clone = new NightwardenDropSlamCloneEntity(this.m_9236_(), livingAttacker);
                clone.m_7678_(target.m_20185_(), target.m_20186_() + 6.5, target.m_20189_(), target.m_146908_(), 0.0f);
                clone.setRadius(3.5f);
                clone.setDamage(this.getCloneDamage());
                clone.setHpBasedDamagePercent(0.0f);
                clone.setDownwardSpeed(-0.5f);
                clone.setShouldApplyEffect(true);
                this.m_9236_().m_7967_((Entity)clone);
            }
            this.victims.add(target.m_20148_());
        }
        this.m_5496_((SoundEvent)SoundEvents.f_12604_, 1.0f, 1.0f);
    }

    protected void m_5790_(EntityHitResult hitResult) {
    }

    public boolean m_20068_() {
        return true;
    }

    public void spawnParticles() {
        if (this.m_9236_().f_46443_) {
            float width = (float)this.m_20191_().m_82362_();
            float step = 0.25f;
            float radians = (float)Math.PI / 180 * this.m_146908_();
            float speed = 0.1f;
            int totalParticles = (int)(width / step);
            float rareChance = 0.2f;
            for (int i = 0; i < totalParticles; ++i) {
                double x = this.m_20185_();
                double y = this.m_20186_();
                double z = this.m_20189_();
                double offset = (double)step * ((double)i - (double)totalParticles / 2.0);
                double rotX = offset * Math.cos(radians);
                double rotZ = -offset * Math.sin(radians);
                double dx = Math.random() * (double)speed * 2.0 - (double)speed;
                double dy = Math.random() * (double)speed * 2.0 - (double)speed;
                double dz = Math.random() * (double)speed * 2.0 - (double)speed;
                if (this.f_19796_.m_188501_() < rareChance) {
                    this.m_9236_().m_7106_((ParticleOptions)ParticleTypes.f_123810_, x + rotX + dx, y + dy, z + rotZ + dz, dx, dy, dz);
                    continue;
                }
                this.m_9236_().m_7106_(TravelopticsParticleHelper.LIGHT_PURPLE_GLOWING_ENCHANT, x + rotX + dx, y + dy, z + rotZ + dz, dx, dy, dz);
            }
        }
    }

    protected SoundEvent m_7239_() {
        return SoundEvents.f_271165_;
    }

    public boolean m_6000_(double x, double y, double z) {
        return true;
    }

    public void m_7380_(CompoundTag compound) {
        super.m_7380_(compound);
        compound.m_128350_("TridentDamage", this.tridentDamage);
        compound.m_128365_("Trident", (Tag)this.spearItem.m_41739_(new CompoundTag()));
        compound.m_128350_("CloneDamage", this.cloneDamage);
        compound.m_128379_("HealOnReturn", this.isHealOnReturnEnabled());
        compound.m_128379_("HealedOnce", this.healed);
        compound.m_128405_("OriginalSlot", this.originalSlot);
        CompoundTag victimsTag = new CompoundTag();
        for (int i = 0; i < this.victims.size(); ++i) {
            victimsTag.m_128362_("victim_" + i, this.victims.get(i));
        }
        victimsTag.m_128405_("victimCount", this.victims.size());
        compound.m_128365_("Victims", (Tag)victimsTag);
    }

    public void m_7378_(CompoundTag compound) {
        super.m_7378_(compound);
        if (compound.m_128441_("TridentDamage")) {
            this.tridentDamage = compound.m_128457_("TridentDamage");
        }
        if (compound.m_128425_("Trident", 10)) {
            this.spearItem = ItemStack.m_41712_((CompoundTag)compound.m_128469_("Trident"));
        }
        if (compound.m_128441_("CloneDamage")) {
            this.cloneDamage = compound.m_128457_("CloneDamage");
        }
        if (compound.m_128441_("HealOnReturn")) {
            this.setHealOnReturnEnabled(compound.m_128471_("HealOnReturn"));
        }
        if (compound.m_128441_("HealedOnce")) {
            this.healed = compound.m_128471_("HealedOnce");
        }
        if (compound.m_128441_("OriginalSlot")) {
            this.originalSlot = compound.m_128451_("OriginalSlot");
        }
        if (compound.m_128441_("Victims")) {
            CompoundTag victimsTag = compound.m_128469_("Victims");
            int victimCount = victimsTag.m_128451_("victimCount");
            this.victims.clear();
            for (int i = 0; i < victimCount; ++i) {
                if (!victimsTag.m_128403_("victim_" + i)) continue;
                this.victims.add(victimsTag.m_128342_("victim_" + i));
            }
        }
    }

    private PlayState animationPredicate(AnimationState<?> event) {
        AnimationController controller = event.getController();
        controller.setAnimation(this.ANIMATION_LOOP);
        return PlayState.CONTINUE;
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController[]{this.controller});
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}

