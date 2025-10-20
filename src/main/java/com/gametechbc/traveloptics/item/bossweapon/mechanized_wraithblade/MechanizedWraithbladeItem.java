/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.client.particle.LightningParticle$OrbData
 *  com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity
 *  com.github.L_Ender.cataclysm.entity.projectile.Laser_Beam_Entity
 *  com.github.L_Ender.cataclysm.init.ModEntities
 *  io.redspace.ironsspellbooks.api.registry.AttributeRegistry
 *  io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  io.redspace.ironsspellbooks.util.ItemPropertiesHelper
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.Mth
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.Attribute
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemDisplayContext
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Tier
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.UseAnim
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.gameevent.GameEvent
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
package com.gametechbc.traveloptics.item.bossweapon.mechanized_wraithblade;

import com.gametechbc.traveloptics.config.WeaponConfig;
import com.gametechbc.traveloptics.data_manager.PlasmaCoreManager;
import com.gametechbc.traveloptics.data_manager.SwingCounterManager;
import com.gametechbc.traveloptics.entity.item.mechanized_wraithblade.MechanizedWraithbladeItemRenderer;
import com.gametechbc.traveloptics.init.TravelopticsItems;
import com.gametechbc.traveloptics.init.TravelopticsMessages;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import com.gametechbc.traveloptics.item.UnbreakableGeoMagicSword;
import com.github.L_Ender.cataclysm.client.particle.LightningParticle;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Laser_Beam_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
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
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
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

public class MechanizedWraithbladeItem
extends UnbreakableGeoMagicSword {
    private static ItemDisplayContext transformType;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    private static final RawAnimation SHOOT_ANIMATION;
    private static final RawAnimation CHARGE_ANIMATION;
    public String externalAnimation = "empty";
    private ItemStack currentStack;

    public MechanizedWraithbladeItem(SpellDataRegistryHolder[] imbuedSpells) {
        super(new Tier(){

            public int m_6609_() {
                return (Integer)WeaponConfig.mechanizedWraithbladeDurability.get();
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
                return Ingredient.f_43901_;
            }
        }, (Double)WeaponConfig.mechanizedWraithbladeDamage.get(), (Double)WeaponConfig.mechanizedWraithbladeAttackSpeed.get(), imbuedSpells, Map.of((Attribute)AttributeRegistry.LIGHTNING_SPELL_POWER.get(), new AttributeModifier(UUID.fromString("57f9a563-4e2c-4c4b-871e-c49c38edf14f"), "Weapon Modifier", ((Double)WeaponConfig.mechanizedWraithbladeLightningSpellPower.get()).doubleValue(), AttributeModifier.Operation.MULTIPLY_BASE)), ItemPropertiesHelper.hidden((int)1).m_41497_(TravelopticsItems.RARITY_MECHANIZED));
        SingletonGeoAnimatable.registerSyncedAnimatable((GeoAnimatable)this);
    }

    public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof Player) {
            Player player = (Player)attacker;
            int swingCounter = SwingCounterManager.getSwingCount(stack);
            PlasmaCoreManager.addPlasmaCore(stack, player, this.getPlasmaPointsForSwing(swingCounter));
            SwingCounterManager.incrementSwingCount(stack, 3);
            for (int i = 0; i < 20; ++i) {
                double offsetX = (target.m_217043_().m_188500_() - 0.5) * (double)target.m_20205_();
                double offsetY = target.m_217043_().m_188500_() * (double)target.m_20206_();
                double offsetZ = (target.m_217043_().m_188500_() - 0.5) * (double)target.m_20205_();
                MagicManager.spawnParticles((Level)target.m_9236_(), (ParticleOptions)new LightningParticle.OrbData(255, 26, 0), (double)(target.m_20185_() + offsetX), (double)(target.m_20186_() + offsetY), (double)(target.m_20189_() + offsetZ), (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.3, (boolean)false);
            }
        }
        return super.m_7579_(stack, target, attacker);
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (handIn == InteractionHand.MAIN_HAND) {
            playerIn.m_6672_(handIn);
            return InteractionResultHolder.m_19096_((Object)playerIn.m_21120_(handIn));
        }
        return InteractionResultHolder.m_19098_((Object)playerIn.m_21120_(handIn));
    }

    public UseAnim m_6164_(ItemStack stack) {
        return UseAnim.BOW;
    }

    public int m_8105_(ItemStack stack) {
        return 72000;
    }

    public void m_5551_(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        if (entity instanceof Player) {
            Player player = (Player)entity;
            if (!level.f_46443_ && player instanceof ServerPlayer) {
                ServerPlayer serverPlayer = (ServerPlayer)player;
                int plasmaCore = PlasmaCoreManager.getPlasmaCore(stack);
                float currentDamage = this.getLaserBaseDamage(plasmaCore);
                TravelopticsMessages.sendMechanizedWraithbladeChargingState(serverPlayer, false, 0, currentDamage);
            }
        }
        super.m_5551_(stack, level, entity, timeLeft);
    }

    public void m_5929_(Level level, LivingEntity entity, ItemStack stack, int count) {
        Player player;
        if (entity instanceof Player && (player = (Player)entity).m_21205_() == stack) {
            ServerLevel serverLevel;
            int chargeTime;
            boolean charging;
            int plasmaCore = PlasmaCoreManager.getPlasmaCore(stack);
            if (plasmaCore <= 0) {
                player.m_5810_();
                if (!level.f_46443_ && player instanceof ServerPlayer) {
                    ServerPlayer serverPlayer = (ServerPlayer)player;
                    TravelopticsMessages.sendMechanizedWraithbladeChargingState(serverPlayer, false, 0, 0.0f);
                }
                if (!level.f_46443_) {
                    player.m_5661_((Component)Component.m_237113_((String)"\u26a1 Plasma Core depleted!").m_130938_(style -> style.m_178520_(11412276)), true);
                }
                return;
            }
            if (!level.f_46443_ && plasmaCore <= 5) {
                this.consumePowerCellForRefuel(player, stack);
            }
            boolean bl = charging = (chargeTime = this.m_8105_(stack) - count) < 8;
            if (!level.f_46443_ && player instanceof ServerPlayer) {
                ServerPlayer serverPlayer = (ServerPlayer)player;
                float currentDamage = this.getLaserBaseDamage(plasmaCore);
                TravelopticsMessages.sendMechanizedWraithbladeChargingState(serverPlayer, charging, chargeTime, currentDamage);
            }
            if (chargeTime < 8) {
                if (!level.f_46443_ && chargeTime == 1) {
                    player.m_5661_((Component)Component.m_237113_((String)"\u26a1 Charging.").m_130938_(style -> style.m_178520_(16476957)), true);
                }
                if (!level.f_46443_ && chargeTime == 3) {
                    player.m_5661_((Component)Component.m_237113_((String)"\u26a1 Charging..").m_130938_(style -> style.m_178520_(16476957)), true);
                }
                if (!level.f_46443_ && chargeTime == 6) {
                    player.m_5661_((Component)Component.m_237113_((String)"\u26a1 Charging...").m_130938_(style -> style.m_178520_(16476957)), true);
                }
                if (!level.f_46443_ && chargeTime == 2) {
                    level.m_5594_(null, entity.m_20183_(), (SoundEvent)TravelopticsSounds.LASER_CHARGE.get(), SoundSource.PLAYERS, 0.8f, 1.2f);
                }
                if (level instanceof ServerLevel) {
                    serverLevel = (ServerLevel)level;
                    if (chargeTime == 2) {
                        this.triggerAnim((Entity)player, GeoItem.getOrAssignId((ItemStack)stack, (ServerLevel)serverLevel), "shoot", "charge_animation");
                    }
                }
                return;
            }
            if (count % 3 == 0) {
                if (level instanceof ServerLevel) {
                    serverLevel = (ServerLevel)level;
                    this.triggerAnim((Entity)player, GeoItem.getOrAssignId((ItemStack)stack, (ServerLevel)serverLevel), "shoot", "shoot_animation");
                }
                if (!level.f_46443_) {
                    float baseDamage = this.getLaserBaseDamage(plasmaCore);
                    float finalDamage = this.calculateFinalLaserDamage(baseDamage, entity);
                    this.spawnLaserBeam(level, entity, finalDamage);
                    float pitch = plasmaCore < 5 ? 1.2f : (plasmaCore < 15 ? 1.1f : 1.0f);
                    level.m_5594_(null, entity.m_20183_(), (SoundEvent)TravelopticsSounds.LASER_SHOOT.get(), SoundSource.PLAYERS, 0.7f, pitch);
                    player.m_5661_((Component)Component.m_237113_((String)("\u26a1 Plasma Core: " + (plasmaCore - 1))).m_130938_(style -> style.m_178520_(11582917)), true);
                }
                entity.m_146850_(GameEvent.f_223698_);
                PlasmaCoreManager.setPlasmaCore(stack, plasmaCore - 1);
            }
        }
    }

    private void consumePowerCellForRefuel(Player player, ItemStack stack) {
        Inventory inventory = player.m_150109_();
        for (int i = 0; i < inventory.m_6643_(); ++i) {
            ItemStack invStack = inventory.m_8020_(i);
            if (invStack.m_41720_() != TravelopticsItems.PLASMA_POWER_CELL.get()) continue;
            invStack.m_41774_(1);
            int currentPlasma = PlasmaCoreManager.getPlasmaCore(stack);
            int maxPlasma = 250;
            int newPlasma = Math.min(currentPlasma + maxPlasma / 2, maxPlasma);
            PlasmaCoreManager.setPlasmaCore(stack, newPlasma);
            player.m_5661_((Component)Component.m_237113_((String)"\u26a1 Plasma Core recharged by 50%!").m_130938_(style -> style.m_178520_(6220159)), true);
            player.m_9236_().m_5594_(null, player.m_20183_(), (SoundEvent)TravelopticsSounds.LASER_CHARGE.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
            return;
        }
    }

    private void spawnLaserBeam(Level level, LivingEntity caster, float damage) {
        Vec3 lookVec = caster.m_20252_(1.0f).m_82541_();
        Vec3 upVec = new Vec3(0.0, 1.0, 0.0);
        Vec3 rightVec = lookVec.m_82537_(upVec).m_82541_().m_82490_(0.22);
        Vec3 laserSpawnPos = caster.m_146892_().m_82549_(lookVec.m_82490_(1.0)).m_82549_(rightVec);
        Vec3 particleSpawnPos = caster.m_146892_().m_82549_(lookVec.m_82490_(0.6)).m_82549_(rightVec);
        RandomSource randomSource = level.m_213780_();
        double spread = this.getLaserSpread();
        double offsetX = (randomSource.m_188500_() - 0.5) * spread;
        double offsetY = (randomSource.m_188500_() - 0.5) * spread;
        double offsetZ = (randomSource.m_188500_() - 0.5) * spread;
        Vec3 spreadVec = lookVec.m_82520_(offsetX, offsetY, offsetZ).m_82541_().m_82490_((double)this.getLaserVelocity());
        float yRot = (float)(Mth.m_14136_((double)lookVec.f_82481_, (double)lookVec.f_82479_) * 57.29577951308232) + 90.0f;
        float xRot = (float)(-(Mth.m_14136_((double)lookVec.f_82480_, (double)Math.sqrt(lookVec.f_82479_ * lookVec.f_82479_ + lookVec.f_82481_ * lookVec.f_82481_)) * 57.29577951308232));
        Laser_Beam_Entity laserBeam = new Laser_Beam_Entity((EntityType)ModEntities.LASER_BEAM.get(), caster, laserSpawnPos.f_82479_, laserSpawnPos.f_82480_ - 0.21, laserSpawnPos.f_82481_, spreadVec.f_82479_, spreadVec.f_82480_, spreadVec.f_82481_, (float)((double)damage * (Double)WeaponConfig.mechanizedWraithbladePlasmaOverdriveDamageMultiplier.get()), level);
        laserBeam.m_146922_(yRot);
        laserBeam.m_146926_(xRot);
        level.m_7967_((Entity)laserBeam);
        MagicManager.spawnParticles((Level)level, (ParticleOptions)new LightningParticle.OrbData(255, 26, 0), (double)particleSpawnPos.f_82479_, (double)particleSpawnPos.f_82480_, (double)particleSpawnPos.f_82481_, (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.2, (boolean)false);
        ScreenShake_Entity.ScreenShake((Level)level, (Vec3)caster.m_20182_(), (float)8.0f, (float)0.012f, (int)2, (int)3);
    }

    protected int getPlasmaPointsForSwing(int swingCounter) {
        return switch (swingCounter) {
            case 1 -> 5;
            case 2 -> 10;
            case 3 -> 15;
            default -> 5;
        };
    }

    protected double getLaserSpread() {
        return 0.1;
    }

    protected float getLaserVelocity() {
        return 1.0f;
    }

    protected float calculateFinalLaserDamage(float baseDamage, LivingEntity entity) {
        return baseDamage;
    }

    protected float getLaserBaseDamage(int plasmaCore) {
        if (plasmaCore > 200) {
            return 14.0f;
        }
        if (plasmaCore > 100) {
            return 12.0f;
        }
        if (plasmaCore > 50) {
            return 10.0f;
        }
        return 8.0f;
    }

    public void m_7373_(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add((Component)Component.m_237113_((String)""));
        tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.mechanized_wraithblade.tooltip").m_130940_(ChatFormatting.GREEN));
        tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.mechanized_wraithblade.tooltip1"));
        int plasmaCoreValue = PlasmaCoreManager.getPlasmaCore(stack);
        tooltip.add((Component)Component.m_237113_((String)("\u26a1 Plasma Core: " + plasmaCoreValue)).m_130940_(ChatFormatting.GOLD));
        if (Screen.m_96638_()) {
            tooltip.add((Component)Component.m_237113_((String)""));
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.weapons.evolution_benefits.tooltip"));
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.mechanized_wraithblade.evo_one.inactive.tooltip"));
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.mechanized_wraithblade.evo_two.inactive.tooltip"));
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.mechanized_wraithblade.evo_three.inactive.tooltip"));
            tooltip.add((Component)Component.m_237113_((String)""));
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.mechanized_wraithblade.tooltip5"));
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
        return new MechanizedWraithbladeItemRenderer();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        super.registerControllers(controllers);
        controllers.add(new AnimationController[]{new AnimationController((GeoAnimatable)this, "shoot", 0, state -> PlayState.STOP).triggerableAnim("shoot_animation", SHOOT_ANIMATION).triggerableAnim("charge_animation", CHARGE_ANIMATION)});
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public void setStack(ItemStack stack) {
        this.currentStack = stack;
    }

    public ItemStack getStack() {
        return this.currentStack;
    }

    static {
        SHOOT_ANIMATION = RawAnimation.begin().thenPlay("attack");
        CHARGE_ANIMATION = RawAnimation.begin().thenPlay("charge");
    }
}

