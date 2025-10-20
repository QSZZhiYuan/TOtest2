/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.entity.effect.Boltstrike_Entity
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  io.redspace.ironsspellbooks.util.ParticleHelper
 *  javax.annotation.Nullable
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
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffect
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
package com.gametechbc.traveloptics.entity.projectiles.aqua_trident;

import com.gametechbc.traveloptics.entity.projectiles.maelstrom.MaelstromEntity;
import com.gametechbc.traveloptics.init.TravelopticsEffects;
import com.gametechbc.traveloptics.init.TravelopticsEntities;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import com.gametechbc.traveloptics.util.TravelopticsParticleHelper;
import com.github.L_Ender.cataclysm.entity.effect.Boltstrike_Entity;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import java.util.List;
import javax.annotation.Nullable;
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
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
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

public class EternalMaelstromTridentEntity
extends AbstractArrow
implements GeoEntity {
    private static final EntityDataAccessor<Integer> ORIGINAL_SLOT = SynchedEntityData.m_135353_(EternalMaelstromTridentEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    private int originalSlot = -1;
    private boolean dealtDamage;
    private float tridentDamage = 10.0f;
    private float boltstrikeDamage = 10.0f;
    private float aoeDamageMultiplier = 0.3f;
    private ItemStack spearItem = new ItemStack((ItemLike)ACItemRegistry.EXTINCTION_SPEAR.get());
    private static final EntityDataAccessor<Boolean> WIGGLING = SynchedEntityData.m_135353_(EternalMaelstromTridentEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private int ticksWiggling = 0;
    private final RawAnimation ANIMATION_LOOP = RawAnimation.begin().thenLoop("idle");
    private final AnimationController controller = new AnimationController((GeoAnimatable)this, "note_controller", 0, this::animationPredicate);

    public EternalMaelstromTridentEntity(EntityType entityType, Level level) {
        super(entityType, level);
    }

    public EternalMaelstromTridentEntity(Level level, LivingEntity shooter, ItemStack itemStack) {
        super((EntityType)TravelopticsEntities.ETERNAL_MAELSTROM_TRIDENT_ENTITY.get(), shooter, level);
        this.spearItem = itemStack.m_41777_();
    }

    public EternalMaelstromTridentEntity(Level level, double x, double y, double z) {
        super((EntityType)TravelopticsEntities.ETERNAL_MAELSTROM_TRIDENT_ENTITY.get(), x, y, z, level);
    }

    public EternalMaelstromTridentEntity(PlayMessages.SpawnEntity spawnEntity, Level level) {
        this((EntityType)TravelopticsEntities.ETERNAL_MAELSTROM_TRIDENT_ENTITY.get(), level);
        this.m_20011_(this.m_142242_());
    }

    public Packet<ClientGamePacketListener> m_5654_() {
        return NetworkHooks.getEntitySpawningPacket((Entity)this);
    }

    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(WIGGLING, (Object)false);
        this.f_19804_.m_135372_(ORIGINAL_SLOT, (Object)-1);
    }

    protected ItemStack m_7941_() {
        return this.spearItem;
    }

    @Nullable
    protected EntityHitResult m_6351_(Vec3 vec3, Vec3 vec31) {
        return this.dealtDamage ? null : super.m_6351_(vec3, vec31);
    }

    public float getTridentDamage() {
        return this.tridentDamage;
    }

    public void setTridentDamage(float damage) {
        this.tridentDamage = damage;
    }

    public float getBoltstrikeDamage() {
        return this.boltstrikeDamage;
    }

    public void setBoltstrikeDamage(float boltstrikeDamage) {
        this.boltstrikeDamage = boltstrikeDamage;
    }

    public float getAoeDamageMultiplier() {
        return this.aoeDamageMultiplier;
    }

    public void setAoeDamageMultiplier(float multiplier) {
        this.aoeDamageMultiplier = multiplier;
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
        super.m_8119_();
        Entity entity = this.m_19749_();
        if ((this.f_36703_ || this.m_36797_()) && entity != null) {
            if (!this.isAcceptibleReturnOwner()) {
                if (!this.m_9236_().f_46443_ && this.f_36705_ == AbstractArrow.Pickup.ALLOWED) {
                    this.m_5552_(this.m_7941_(), 0.1f);
                }
                this.m_146870_();
            } else if (this.isWiggling()) {
                if (this.ticksWiggling++ > 20) {
                    this.setWiggling(false);
                    if (!this.dealtDamage) {
                        this.explodeOnGround();
                    } else {
                        this.explode();
                    }
                }
            } else {
                Vec3 vec3 = entity.m_146892_().m_82546_(this.m_20182_());
                if (!this.m_36797_()) {
                    this.m_20256_(Vec3.f_82478_);
                }
                this.m_36790_(true);
                if (this.m_9236_().f_46443_) {
                    this.f_19791_ = this.m_20186_();
                }
                double d0 = 0.3;
                this.m_20256_(this.m_20184_().m_82490_(0.95).m_82549_(vec3.m_82541_().m_82490_(d0)));
            }
        }
        if (this.m_9236_().f_46443_ && !this.f_36703_) {
            AABB boundingBox = this.m_20191_();
            int particleCount = 4 + this.f_19796_.m_188503_(6);
            for (int i = 0; i < particleCount; ++i) {
                double x = boundingBox.f_82288_ + this.f_19796_.m_188500_() * (boundingBox.f_82291_ - boundingBox.f_82288_);
                double y = boundingBox.f_82289_ + this.f_19796_.m_188500_() * (boundingBox.f_82292_ - boundingBox.f_82289_);
                double z = boundingBox.f_82290_ + this.f_19796_.m_188500_() * (boundingBox.f_82293_ - boundingBox.f_82290_);
                Vec3 motion = this.m_20184_().m_82490_((double)-0.2f);
                if ((double)this.f_19796_.m_188501_() < 0.85) {
                    this.m_9236_().m_6485_(TravelopticsParticleHelper.WATER_BUBBLE, true, x, y, z, motion.f_82479_, motion.f_82480_, motion.f_82481_);
                    continue;
                }
                this.m_9236_().m_6485_((ParticleOptions)ParticleTypes.f_123810_, true, x, y, z, motion.f_82479_, motion.f_82480_, motion.f_82481_);
            }
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

    private void explode() {
        LivingEntity owner = this.m_19749_() instanceof LivingEntity ? (LivingEntity)this.m_19749_() : null;
        this.m_5496_((SoundEvent)TravelopticsSounds.MAELSTROM_TRIDENT_RETURN.get(), 2.5f, 1.0f);
    }

    private void explodeOnGround() {
        LivingEntity owner = this.m_19749_() instanceof LivingEntity ? (LivingEntity)this.m_19749_() : null;
        Boltstrike_Entity bolt = new Boltstrike_Entity(this.m_9236_(), this.m_20185_(), this.m_20186_(), this.m_20189_(), 0.0f, 10, this.getBoltstrikeDamage(), owner);
        bolt.setR(0);
        bolt.setG(66);
        bolt.setB(106);
        this.m_9236_().m_7967_((Entity)bolt);
        MaelstromEntity maelstrom = new MaelstromEntity(this.m_9236_());
        maelstrom.m_6027_(this.m_20185_(), this.m_20186_(), this.m_20189_());
        maelstrom.m_5602_((Entity)owner);
        maelstrom.setRadius(4.0f);
        maelstrom.setDuration(100);
        maelstrom.setPullScale(0.5);
        this.m_9236_().m_7967_((Entity)maelstrom);
        this.m_5496_((SoundEvent)TravelopticsSounds.MAELSTROM_TRIDENT_RETURN.get(), 2.5f, 1.0f);
        MagicManager.spawnParticles((Level)this.m_9236_(), (ParticleOptions)ParticleHelper.FOG, (double)this.m_20185_(), (double)(this.m_20186_() + 15.5), (double)this.m_20189_(), (int)3, (double)0.0, (double)0.0, (double)0.0, (double)0.1, (boolean)true);
    }

    protected void m_8060_(BlockHitResult blockHitResult) {
        super.m_8060_(blockHitResult);
        this.setWiggling(true);
        if (!this.m_9236_().f_46443_) {
            MagicManager.spawnParticles((Level)this.m_9236_(), (ParticleOptions)((ParticleOptions)ParticleTypes.f_123795_), (double)this.m_20185_(), (double)(this.m_20186_() + 0.2), (double)this.m_20189_(), (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)true);
        }
    }

    private boolean isAcceptibleReturnOwner() {
        Entity entity = this.m_19749_();
        if (entity != null && entity.m_6084_()) {
            return !(entity instanceof ServerPlayer) || !entity.m_5833_();
        }
        return false;
    }

    protected void m_5790_(EntityHitResult hitResult) {
        Entity target = hitResult.m_82443_();
        float baseDamage = this.getTridentDamage();
        if (target instanceof LivingEntity) {
            LivingEntity livingTarget = (LivingEntity)target;
            baseDamage += EnchantmentHelper.m_44833_((ItemStack)this.m_7941_(), (MobType)livingTarget.m_6336_());
        }
        Entity attacker = this.m_19749_();
        DamageSource damageSource = this.m_269291_().m_269525_((Entity)this, (Entity)(attacker == null ? this : attacker));
        this.dealtDamage = true;
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
                if (livingTarget.m_21023_((MobEffect)TravelopticsEffects.TIDAL_TORMENT.get())) {
                    double radius = 5.0;
                    float aoeDamage = baseDamage * this.getAoeDamageMultiplier();
                    List nearbyEntities = this.m_9236_().m_6443_(LivingEntity.class, livingTarget.m_20191_().m_82400_(radius), entity -> entity != livingTarget && entity.m_21023_((MobEffect)TravelopticsEffects.TIDAL_TORMENT.get()));
                    for (LivingEntity affectedEntity : nearbyEntities) {
                        MagicManager.spawnParticles((Level)this.m_9236_(), (ParticleOptions)TravelopticsParticleHelper.WATER_BUBBLE, (double)affectedEntity.m_20185_(), (double)(affectedEntity.m_20186_() + 2.5), (double)affectedEntity.m_20189_(), (int)15, (double)0.0, (double)0.0, (double)0.0, (double)0.3, (boolean)false);
                        affectedEntity.m_6469_(damageSource, aoeDamage);
                    }
                }
            }
        }
        this.m_20256_(Vec3.f_82478_);
        this.m_36790_(true);
        this.f_36703_ = true;
        this.m_5496_((SoundEvent)SoundEvents.f_12604_, 1.0f, 1.0f);
    }

    protected SoundEvent m_7239_() {
        return (SoundEvent)TravelopticsSounds.MAELSTROM_TRIDENT_IMPACT.get();
    }

    public boolean m_6000_(double x, double y, double z) {
        return true;
    }

    public boolean isWiggling() {
        return (Boolean)this.f_19804_.m_135370_(WIGGLING);
    }

    public void setWiggling(boolean wiggling) {
        this.f_19804_.m_135381_(WIGGLING, (Object)wiggling);
    }

    public void m_7380_(CompoundTag compound) {
        super.m_7380_(compound);
        compound.m_128350_("TridentDamage", this.tridentDamage);
        compound.m_128350_("BoltstrikeDamage", this.boltstrikeDamage);
        compound.m_128350_("AoeDamageMultiplier", this.aoeDamageMultiplier);
        compound.m_128365_("Trident", (Tag)this.spearItem.m_41739_(new CompoundTag()));
        compound.m_128379_("DealtDamage", this.dealtDamage);
        compound.m_128405_("OriginalSlot", this.originalSlot);
    }

    public void m_7378_(CompoundTag compound) {
        super.m_7378_(compound);
        if (compound.m_128441_("TridentDamage")) {
            this.tridentDamage = compound.m_128457_("TridentDamage");
        }
        if (compound.m_128441_("BoltstrikeDamage")) {
            this.boltstrikeDamage = compound.m_128457_("BoltstrikeDamage");
        }
        if (compound.m_128441_("AoeDamageMultiplier")) {
            this.aoeDamageMultiplier = compound.m_128457_("AoeDamageMultiplier");
        }
        if (compound.m_128425_("Trident", 10)) {
            this.spearItem = ItemStack.m_41712_((CompoundTag)compound.m_128469_("Trident"));
        }
        this.dealtDamage = compound.m_128471_("DealtDamage");
        if (compound.m_128441_("OriginalSlot")) {
            this.originalSlot = compound.m_128451_("OriginalSlot");
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

