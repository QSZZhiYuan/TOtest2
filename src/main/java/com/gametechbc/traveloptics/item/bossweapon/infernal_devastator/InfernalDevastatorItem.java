/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.client.particle.LightningParticle$OrbData
 *  com.github.L_Ender.cataclysm.client.particle.RingParticle$EnumRingBehavior
 *  com.github.L_Ender.cataclysm.client.particle.RingParticle$RingData
 *  com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity
 *  com.github.L_Ender.cataclysm.init.ModItems
 *  com.github.L_Ender.cataclysm.init.ModSounds
 *  io.redspace.ironsspellbooks.api.magic.MagicData
 *  io.redspace.ironsspellbooks.api.registry.AttributeRegistry
 *  io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  io.redspace.ironsspellbooks.item.UniqueItem
 *  io.redspace.ironsspellbooks.util.ItemPropertiesHelper
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.Attribute
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemDisplayContext
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Tier
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.UseAnim
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  software.bernie.geckolib.animatable.GeoItem
 *  software.bernie.geckolib.animatable.SingletonGeoAnimatable
 *  software.bernie.geckolib.core.animatable.GeoAnimatable
 *  software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache
 *  software.bernie.geckolib.core.animation.AnimatableManager$ControllerRegistrar
 *  software.bernie.geckolib.core.animation.AnimationController
 *  software.bernie.geckolib.core.animation.RawAnimation
 *  software.bernie.geckolib.core.object.PlayState
 *  software.bernie.geckolib.util.GeckoLibUtil
 */
package com.gametechbc.traveloptics.item.bossweapon.infernal_devastator;

import com.gametechbc.traveloptics.config.WeaponConfig;
import com.gametechbc.traveloptics.entity.extended_projectiles.ExtendedFlareBombEntity;
import com.gametechbc.traveloptics.entity.item.infernal_devastator.InfernalDevastatorRenderer;
import com.gametechbc.traveloptics.init.TravelopticsEffects;
import com.gametechbc.traveloptics.init.TravelopticsItems;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import com.gametechbc.traveloptics.item.UnbreakableGeoMagicSword;
import com.github.L_Ender.cataclysm.client.particle.LightningParticle;
import com.github.L_Ender.cataclysm.client.particle.RingParticle;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModSounds;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.item.UniqueItem;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class InfernalDevastatorItem
extends UnbreakableGeoMagicSword
implements UniqueItem {
    private static ItemDisplayContext transformType;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    private static final RawAnimation DEVASTATOR_CANON_ANIMATION;
    private static final RawAnimation DEVASTATOR_BLADE_ANIMATION;

    public InfernalDevastatorItem(SpellDataRegistryHolder[] imbuedSpells) {
        super(new Tier(){

            public int m_6609_() {
                return (Integer)WeaponConfig.infernalDevastatorDurability.get();
            }

            public float m_6624_() {
                return 2.0f;
            }

            public float m_6631_() {
                return 0.0f;
            }

            public int m_6604_() {
                return 1;
            }

            public int m_6601_() {
                return 20;
            }

            public Ingredient m_6282_() {
                return Ingredient.m_43927_((ItemStack[])new ItemStack[]{new ItemStack((ItemLike)ModItems.ANCIENT_METAL_INGOT.get())});
            }
        }, (Double)WeaponConfig.infernalDevastatorDamage.get(), (Double)WeaponConfig.infernalDevastatorAttackSpeed.get(), imbuedSpells, Map.of((Attribute)AttributeRegistry.FIRE_SPELL_POWER.get(), new AttributeModifier(UUID.fromString("8ff095c4-a26d-4d28-bc06-1a908b3577aa"), "Weapon Modifier", ((Double)WeaponConfig.infernalDevastatorFireSpellPower.get()).doubleValue(), AttributeModifier.Operation.MULTIPLY_BASE)), ItemPropertiesHelper.hidden((int)1).m_41497_(TravelopticsItems.RARITY_MONSTROUS));
        SingletonGeoAnimatable.registerSyncedAnimatable((GeoAnimatable)this);
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
        if (hand == InteractionHand.MAIN_HAND) {
            MobEffectInstance overloadedEffect = player.m_21124_((MobEffect)TravelopticsEffects.OVERLOADED_EFFECT.get());
            if (overloadedEffect != null) {
                if (!world.f_46443_) {
                    player.m_5661_((Component)Component.m_237115_((String)"effect.traveloptics.overloaded.warning").m_130940_(ChatFormatting.RED), true);
                }
                return new InteractionResultHolder(InteractionResult.FAIL, (Object)player.m_21120_(hand));
            }
            ItemStack stack = player.m_21120_(hand);
            player.m_6672_(hand);
            return new InteractionResultHolder(InteractionResult.SUCCESS, (Object)stack);
        }
        return new InteractionResultHolder(InteractionResult.FAIL, (Object)player.m_21120_(hand));
    }

    public void m_5551_(ItemStack stack, Level world, LivingEntity entity, int timeLeft) {
        int chargeDuration;
        Player player;
        if (entity instanceof Player && (player = (Player)entity).m_7655_() == InteractionHand.MAIN_HAND && (chargeDuration = this.m_8105_(stack) - timeLeft) >= 7 && !world.f_46443_) {
            player.m_36335_().m_41524_((Item)this, 280);
        }
    }

    public UseAnim m_6164_(ItemStack stack) {
        return UseAnim.BOW;
    }

    public int m_8105_(ItemStack stack) {
        return 58;
    }

    public void m_6883_(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        super.m_6883_(stack, level, entity, slot, selected);
        if (entity instanceof Player) {
            Player player = (Player)entity;
            if (level instanceof ServerLevel) {
                ServerLevel serverLevel = (ServerLevel)level;
                MagicData magicData = MagicData.getPlayerMagicData((LivingEntity)player);
                if (magicData.isCasting() && "traveloptics:gyro_slash".equals(magicData.getCastingSpellId())) {
                    ItemStack mainHand = player.m_21205_();
                    this.triggerAnim((Entity)player, GeoItem.getOrAssignId((ItemStack)mainHand, (ServerLevel)serverLevel), "devastator", "devastator_blade");
                }
            }
        }
    }

    public void m_5929_(Level level, LivingEntity caster, ItemStack stack, int count) {
        Player player;
        if (caster instanceof Player && (player = (Player)caster).m_7655_() == InteractionHand.MAIN_HAND) {
            int chargeTime = this.m_8105_(stack) - count;
            ItemStack mainHand = player.m_21205_();
            if (chargeTime == 7) {
                caster.m_7292_(new MobEffectInstance((MobEffect)TravelopticsEffects.OVERLOADED_EFFECT.get(), 80, 0, false, false, false));
                caster.m_5496_((SoundEvent)TravelopticsSounds.DEVASTATOR_CANON_TRANSFORM.get(), 1.0f, 1.0f);
                caster.m_5496_((SoundEvent)TravelopticsSounds.DEVASTATOR_CANON_TRANSFORM_OVER.get(), 1.0f, 1.0f);
                if (level instanceof ServerLevel) {
                    ServerLevel serverLevel = (ServerLevel)level;
                    this.triggerAnim((Entity)player, GeoItem.getOrAssignId((ItemStack)mainHand, (ServerLevel)serverLevel), "devastator", "devastator_canon");
                }
            }
            if (chargeTime > 43) {
                this.makeChargeUpParticles(level, caster);
            }
            if (chargeTime == 44) {
                caster.m_5496_((SoundEvent)TravelopticsSounds.LASER_CHARGE.get(), 1.0f, 0.8f);
            }
            if (chargeTime == 57) {
                this.handleShooting(level, caster);
                player.m_36335_().m_41524_((Item)this, 160);
            }
        }
    }

    private void handleShooting(Level level, LivingEntity caster) {
        this.spawnFlareBomb(level, caster);
        caster.m_5496_((SoundEvent)ModSounds.MONSTROSITYSHOOT.get(), 1.2f, 1.0f);
        ScreenShake_Entity.ScreenShake((Level)level, (Vec3)caster.m_20182_(), (float)12.0f, (float)0.025f, (int)10, (int)20);
    }

    private void spawnFlareBomb(Level world, LivingEntity player) {
        Vec3 lookVec = player.m_20154_();
        int projectileCount = this.getFlareBombCount();
        double spreadAngle = Math.toRadians(this.getSpreadAngleDegrees());
        double angleBetween = spreadAngle / (double)(projectileCount - 1);
        Vec3 upVec = new Vec3(0.0, 1.0, 0.0);
        Vec3 rightVec = lookVec.m_82537_(upVec).m_82541_().m_82490_(0.2);
        for (int i = 0; i < projectileCount; ++i) {
            double angle = -spreadAngle / 2.0 + angleBetween * (double)i;
            double x = lookVec.f_82479_ * Math.cos(angle) - lookVec.f_82481_ * Math.sin(angle);
            double z = lookVec.f_82479_ * Math.sin(angle) + lookVec.f_82481_ * Math.cos(angle);
            Vec3 motion = new Vec3(x, lookVec.f_82480_, z).m_82541_().m_82490_(0.8);
            Vec3 spawnPos = player.m_146892_().m_82549_(lookVec.m_82490_(1.0)).m_82549_(rightVec);
            ExtendedFlareBombEntity flareBomb = new ExtendedFlareBombEntity(world, player);
            flareBomb.m_6034_(spawnPos.f_82479_, spawnPos.f_82480_, spawnPos.f_82481_);
            flareBomb.m_20256_(motion);
            flareBomb.setFlameJetDamage(this.calculateFlameDamage(player));
            world.m_7967_((Entity)flareBomb);
        }
        Vec3 spawnPos = player.m_146892_().m_82549_(lookVec.m_82490_(1.2)).m_82549_(rightVec.m_82490_(0.3));
        float yaw = (float)Math.atan2(-lookVec.f_82481_, lookVec.f_82479_) + 1.5707964f;
        float pitch = 0.0f;
        world.m_7106_((ParticleOptions)new RingParticle.RingData(yaw, pitch, 20, 0.902f, 0.3922f, 0.0627f, 1.0f, 20.0f, false, RingParticle.EnumRingBehavior.GROW_THEN_SHRINK), spawnPos.f_82479_, spawnPos.f_82480_, spawnPos.f_82481_, 0.0, 0.0, 0.0);
    }

    private void makeChargeUpParticles(Level level, LivingEntity caster) {
        if (!level.f_46443_) {
            Vec3 lookVec = caster.m_20154_();
            Vec3 upVec = new Vec3(0.0, 1.0, 0.0);
            Vec3 rightVec = lookVec.m_82537_(upVec).m_82541_().m_82490_(0.22);
            Vec3 particleSpawnPos = caster.m_146892_().m_82549_(lookVec.m_82490_(0.6)).m_82549_(rightVec);
            MagicManager.spawnParticles((Level)level, (ParticleOptions)new LightningParticle.OrbData(230, 100, 16), (double)particleSpawnPos.f_82479_, (double)particleSpawnPos.f_82480_, (double)particleSpawnPos.f_82481_, (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.2, (boolean)false);
        }
    }

    protected int getFlareBombCount() {
        return 3;
    }

    protected double getSpreadAngleDegrees() {
        return 50.0;
    }

    protected float calculateFlameDamage(LivingEntity player) {
        return (float)(20.0 * (Double)WeaponConfig.infernalDevastatorBlazingSalvoDamageMultiplier.get());
    }

    public void m_7373_(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add((Component)Component.m_237113_((String)""));
        tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.infernal_devastator.tooltip").m_130940_(ChatFormatting.GREEN));
        tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.infernal_devastator.tooltip1"));
        if (Screen.m_96638_()) {
            tooltip.add((Component)Component.m_237113_((String)""));
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.weapons.evolution_benefits.tooltip").m_130940_(ChatFormatting.YELLOW));
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.infernal_devastator.evo_one.inactive.tooltip"));
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.infernal_devastator.evo_two.inactive.tooltip"));
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.infernal_devastator.evo_three.inactive.tooltip"));
        } else {
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.weapons.evolution.stars_zero.tooltip"));
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.shift_hold.advanced_tooltips"));
        }
        tooltip.add((Component)Component.m_237113_((String)""));
        super.m_7373_(stack, world, tooltip, flag);
    }

    public void getTransformType(ItemDisplayContext type) {
        transformType = type;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    protected BlockEntityWithoutLevelRenderer getRenderer() {
        return new InfernalDevastatorRenderer();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        super.registerControllers(controllers);
        controllers.add(new AnimationController[]{new AnimationController((GeoAnimatable)this, "devastator", 0, state -> PlayState.STOP).triggerableAnim("devastator_canon", DEVASTATOR_CANON_ANIMATION).triggerableAnim("devastator_blade", DEVASTATOR_BLADE_ANIMATION)});
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    static {
        DEVASTATOR_CANON_ANIMATION = RawAnimation.begin().thenPlay("canon");
        DEVASTATOR_BLADE_ANIMATION = RawAnimation.begin().thenPlay("blade");
    }
}

