/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.api.registry.AttributeRegistry
 *  io.redspace.ironsspellbooks.api.registry.SchoolRegistry
 *  io.redspace.ironsspellbooks.api.spells.SchoolType
 *  io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.KeyMapping
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.network.chat.Component
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.TamableAnimal
 *  net.minecraft.world.entity.ai.attributes.Attribute
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ArmorItem$Type
 *  net.minecraft.world.item.ElytraItem
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.levelgen.Heightmap$Types
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  software.bernie.geckolib.constant.DataTickets
 *  software.bernie.geckolib.core.animatable.GeoAnimatable
 *  software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache
 *  software.bernie.geckolib.core.animation.AnimatableManager$ControllerRegistrar
 *  software.bernie.geckolib.core.animation.Animation$LoopType
 *  software.bernie.geckolib.core.animation.AnimationController
 *  software.bernie.geckolib.core.animation.AnimationState
 *  software.bernie.geckolib.core.animation.RawAnimation
 *  software.bernie.geckolib.core.object.PlayState
 *  software.bernie.geckolib.renderer.GeoArmorRenderer
 *  software.bernie.geckolib.util.GeckoLibUtil
 */
package com.gametechbc.traveloptics.item.armor;

import com.gametechbc.traveloptics.api.utils.IKeybindArmor;
import com.gametechbc.traveloptics.api.utils.TOGeneralUtils;
import com.gametechbc.traveloptics.data_manager.CooldownsManager;
import com.gametechbc.traveloptics.data_manager.DarknessManager;
import com.gametechbc.traveloptics.data_manager.SwitchManager;
import com.gametechbc.traveloptics.entity.armor.forlorn_harbinger.ForlornHarbingerArmorModel;
import com.gametechbc.traveloptics.entity.armor.forlorn_harbinger.ForlornHarbingerArmorRenderer;
import com.gametechbc.traveloptics.init.TravelopticsEffects;
import com.gametechbc.traveloptics.init.TravelopticsKeybinds;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import com.gametechbc.traveloptics.item.TravelopticsArmorMaterials;
import com.gametechbc.traveloptics.item.UnbreakableImbueableArmor;
import com.gametechbc.traveloptics.util.TravelopticsKeybindManager;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ForlornHarbingerArmorItem
extends UnbreakableImbueableArmor
implements IKeybindArmor {
    public static final String ABILITY_NOCTURNAL_UPLIFT = "nocturnal_uplift";
    public static final String ABILITY_ECLIPSED_SIGHT = "eclipsed_sight";
    private static final String ABILITY_CRIMSON_DESCEND = "crimson_descend";
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);

    public ForlornHarbingerArmorItem(ArmorItem.Type slot, Item.Properties settings) {
        super(TravelopticsArmorMaterials.FORLORN_HARBINGER, slot, settings);
    }

    @Override
    protected Set<ArmorItem.Type> getImbuableArmorTypes() {
        return Set.of(ArmorItem.Type.CHESTPLATE);
    }

    @Override
    protected Map<ArmorItem.Type, Integer> getMaxSpellSlots() {
        return Map.of(ArmorItem.Type.CHESTPLATE, 1);
    }

    public void m_6883_(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        super.m_6883_(stack, level, entity, slot, selected);
        if (!(entity instanceof Player)) {
            return;
        }
        Player player = (Player)entity;
        if (!this.isWearingFullSet(player)) {
            return;
        }
        ItemStack chestplate = player.m_6844_(ArmorItem.Type.CHESTPLATE.m_266308_());
        if (stack != chestplate || chestplate.m_41720_() != this) {
            return;
        }
        int currentDarkness = DarknessManager.getDarkness(chestplate);
        if (level.f_46443_) {
            if (TravelopticsKeybindManager.getClientSidePlayer() == player) {
                if (TravelopticsKeybindManager.isKeyDown(TravelopticsKeybinds.KEY_X)) {
                    TravelopticsKeybindManager.sendArmorKeyPacket(EquipmentSlot.CHEST, TravelopticsKeybinds.KEY_X);
                    this.onKeyPacket(player, stack, TravelopticsKeybinds.KEY_X);
                }
                if (TravelopticsKeybindManager.isKeyDown(TravelopticsKeybinds.KEY_Z)) {
                    TravelopticsKeybindManager.sendArmorKeyPacket(EquipmentSlot.CHEST, TravelopticsKeybinds.KEY_Z);
                    this.onKeyPacket(player, stack, TravelopticsKeybinds.KEY_Z);
                }
                if (TravelopticsKeybindManager.isKeyDown(TravelopticsKeybinds.KEY_B)) {
                    TravelopticsKeybindManager.sendArmorKeyPacket(EquipmentSlot.CHEST, TravelopticsKeybinds.KEY_B);
                    this.onKeyPacket(player, stack, TravelopticsKeybinds.KEY_B);
                }
            }
            if (player.m_21255_() && player.f_19797_ % 30 == 0) {
                level.m_5594_(null, player.m_20183_(), (SoundEvent)TravelopticsSounds.FORLORN_FLAP.get(), SoundSource.PLAYERS, 0.7f, 1.0f);
            }
        }
        if (!level.f_46443_) {
            if (currentDarkness < 300) {
                boolean canRegenDarkness;
                boolean bl = canRegenDarkness = player.m_20096_() || player.m_20186_() - (double)player.m_9236_().m_5452_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, player.m_20183_()).m_123342_() <= 1.8;
                if (this.isWearingFullSet(player) && canRegenDarkness && player.f_19797_ % 2 == 0) {
                    DarknessManager.addDarkness(chestplate, player, 1);
                }
            }
            CooldownsManager.tickCooldown(chestplate);
            if (player.m_21255_() && DarknessManager.getDarkness(chestplate) > 0 && player.f_19797_ % 3 == 0) {
                DarknessManager.addDarkness(chestplate, player, -1);
                TOGeneralUtils.applyFlightBoost(player, 0.2, 2.0, true, true);
            }
            if (SwitchManager.isEnabled(chestplate, ABILITY_ECLIPSED_SIGHT) && DarknessManager.getDarkness(chestplate) > 0) {
                if (player.f_19797_ % 10 == 0) {
                    player.m_7292_(new MobEffectInstance(MobEffects.f_19611_, 240, 0, false, false, false));
                }
            } else {
                player.m_21195_(MobEffects.f_19611_);
            }
        }
    }

    @Override
    public void onKeyPacket(Player player, ItemStack itemStack, KeyMapping key) {
        int remainingCooldown;
        ItemStack chestplate = player.m_6844_(EquipmentSlot.CHEST);
        if (chestplate.m_41619_() || chestplate.m_41720_() != this) {
            return;
        }
        boolean isNight = player.m_9236_().m_46462_();
        int normalCooldown = 200;
        int nightCooldown = 160;
        if (key == TravelopticsKeybinds.KEY_X && player.m_20096_()) {
            this.activateNocturnalUplift(player, chestplate, isNight, nightCooldown, normalCooldown);
        }
        if (key == TravelopticsKeybinds.KEY_B) {
            remainingCooldown = CooldownsManager.getCooldown(chestplate, ABILITY_ECLIPSED_SIGHT) / 20;
            if (remainingCooldown > 0) {
                player.m_5661_((Component)Component.m_237110_((String)"item.traveloptics.message.forlorn_harbinger.ability_cooldown", (Object[])new Object[]{Component.m_237115_((String)"item.traveloptics.ability.eclipsed_sight"), remainingCooldown}).m_130940_(ChatFormatting.RED), true);
                return;
            }
            player.m_9236_().m_5594_(null, player.m_20183_(), (SoundEvent)ACSoundRegistry.FORSAKEN_BITE.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
            SwitchManager.toggleSwitch(chestplate, ABILITY_ECLIPSED_SIGHT);
            CooldownsManager.setCooldown(chestplate, ABILITY_ECLIPSED_SIGHT, 60, 60);
        }
        if (key == TravelopticsKeybinds.KEY_Z && player.m_21255_()) {
            remainingCooldown = CooldownsManager.getCooldown(chestplate, ABILITY_CRIMSON_DESCEND) / 20;
            if (remainingCooldown > 0) {
                player.m_5661_((Component)Component.m_237110_((String)"item.traveloptics.message.forlorn_harbinger.ability_cooldown", (Object[])new Object[]{Component.m_237115_((String)"item.traveloptics.ability.crimson_descend"), remainingCooldown}).m_130940_(ChatFormatting.RED), true);
                return;
            }
            double bloodSpellPower = player.m_21133_((Attribute)AttributeRegistry.BLOOD_SPELL_POWER.get());
            int amplifier = 10 + (int)(bloodSpellPower * 10.0);
            player.m_9236_().m_5594_(null, player.m_20183_(), (SoundEvent)TravelopticsSounds.CRIMSON_DESCEND.get(), SoundSource.PLAYERS, 2.0f, 1.0f);
            player.m_7292_(new MobEffectInstance((MobEffect)TravelopticsEffects.CRIMSON_DESCEND.get(), 12000, amplifier, false, false, false));
            CooldownsManager.setCooldown(chestplate, ABILITY_CRIMSON_DESCEND, isNight ? nightCooldown : normalCooldown, isNight ? nightCooldown : normalCooldown);
        }
    }

    private void activateNocturnalUplift(Player player, ItemStack chestplate, boolean isNight, int nightCooldown, int normalCooldown) {
        int remainingCooldown = CooldownsManager.getCooldown(chestplate, ABILITY_NOCTURNAL_UPLIFT) / 20;
        if (remainingCooldown > 0) {
            player.m_5661_((Component)Component.m_237110_((String)"item.traveloptics.message.forlorn_harbinger.ability_cooldown", (Object[])new Object[]{Component.m_237115_((String)"item.traveloptics.ability.nocturnal_uplift"), remainingCooldown}).m_130940_(ChatFormatting.RED), true);
            return;
        }
        player.m_9236_().m_7106_((ParticleOptions)new BlastwaveParticleOptions(((SchoolType)SchoolRegistry.BLOOD.get()).getTargetingColor(), 8.0f), player.m_20185_(), player.m_20186_() + (double)0.165f, player.m_20189_(), 0.0, 0.0, 0.0);
        player.m_9236_().m_5594_(null, player.m_20183_(), (SoundEvent)TravelopticsSounds.NOCTURNAL_UPLIFT.get(), SoundSource.PLAYERS, 1.5f, 1.0f);
        double launchPower = 2.0;
        Vec3 launchVector = new Vec3(player.m_20184_().f_82479_, launchPower, player.m_20184_().f_82481_);
        player.m_20256_(launchVector);
        double knockbackStrength = 1.5;
        double radius = 6.0;
        int darknessRestored = 0;
        int darknessPerHit = isNight ? 20 : 10;
        List nearbyEntities = player.m_9236_().m_6443_(LivingEntity.class, player.m_20191_().m_82400_(radius), entity -> {
            TamableAnimal tamable;
            return entity != player && !entity.m_7307_((Entity)player) && (!(entity instanceof TamableAnimal) || !(tamable = (TamableAnimal)entity).m_21824_());
        });
        for (LivingEntity entity2 : nearbyEntities) {
            Vec3 knockbackDirection = entity2.m_20182_().m_82546_(player.m_20182_()).m_82541_().m_82490_(knockbackStrength);
            entity2.m_20256_(entity2.m_20184_().m_82549_(knockbackDirection));
            if (DarknessManager.getDarkness(chestplate) >= 300) continue;
            darknessRestored += darknessPerHit;
        }
        if (darknessRestored > 0) {
            DarknessManager.addDarkness(chestplate, player, darknessRestored);
        }
        CooldownsManager.setCooldown(chestplate, ABILITY_NOCTURNAL_UPLIFT, isNight ? nightCooldown : normalCooldown, isNight ? nightCooldown : normalCooldown);
    }

    public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
        return true;
    }

    public boolean canElytraFly(ItemStack stack, LivingEntity entity) {
        return ElytraItem.m_41140_((ItemStack)stack);
    }

    public void m_7373_(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag) {
        super.m_7373_(stack, world, tooltip, flag);
        int darkness = DarknessManager.getDarkness(stack);
        int darknessPercentage = (int)((double)darkness / 300.0 * 100.0);
        tooltip.add((Component)Component.m_237113_((String)""));
        if (Screen.m_96638_()) {
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.forlorn_harbinger.tooltip.passive"));
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.forlorn_harbinger.tooltip.nocturnal_uplift.start").m_7220_(TravelopticsKeybinds.KEY_X.m_90863_()).m_7220_((Component)Component.m_237115_((String)"item.traveloptics.forlorn_harbinger.tooltip.nocturnal_uplift")));
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.forlorn_harbinger.tooltip.eclipsed_sight.start").m_7220_(TravelopticsKeybinds.KEY_B.m_90863_()).m_7220_((Component)Component.m_237115_((String)"item.traveloptics.forlorn_harbinger.tooltip.eclipsed_sight")));
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.forlorn_harbinger.tooltip.crimson_dive.start").m_7220_(TravelopticsKeybinds.KEY_Z.m_90863_()).m_7220_((Component)Component.m_237115_((String)"item.traveloptics.forlorn_harbinger.tooltip.crimson_dive")));
            tooltip.add((Component)Component.m_237113_((String)""));
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.forlorn_harbinger.tooltip2"));
        } else {
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.forlorn_harbinger.tooltip").m_130940_(ChatFormatting.GREEN));
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.forlorn_harbinger.tooltip1"));
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.shift_hold.advanced_tooltips"));
            if (this.f_265916_ == ArmorItem.Type.CHESTPLATE) {
                tooltip.add((Component)Component.m_237113_((String)""));
                tooltip.add((Component)Component.m_237110_((String)"item.traveloptics.forlorn_harbinger.tooltip.darkness", (Object[])new Object[]{darknessPercentage + "%"}).m_130940_(ChatFormatting.YELLOW));
            }
        }
        tooltip.add((Component)Component.m_237113_((String)""));
    }

    private boolean isWearingFullSet(Player player) {
        return player.m_6844_(ArmorItem.Type.HELMET.m_266308_()).m_41720_() instanceof ForlornHarbingerArmorItem && player.m_6844_(ArmorItem.Type.CHESTPLATE.m_266308_()).m_41720_() instanceof ForlornHarbingerArmorItem && player.m_6844_(ArmorItem.Type.LEGGINGS.m_266308_()).m_41720_() instanceof ForlornHarbingerArmorItem && player.m_6844_(ArmorItem.Type.BOOTS.m_266308_()).m_41720_() instanceof ForlornHarbingerArmorItem;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public GeoArmorRenderer<?> supplyRenderer() {
        return new ForlornHarbingerArmorRenderer(new ForlornHarbingerArmorModel());
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    private PlayState wings(AnimationState<ForlornHarbingerArmorItem> animationState) {
        Entity entity = (Entity)animationState.getData(DataTickets.ENTITY);
        if (entity instanceof Player) {
            Player player = (Player)entity;
            if (player.m_150110_().f_35935_ || player.m_21255_()) {
                animationState.getController().setAnimation(RawAnimation.begin().then("flying", Animation.LoopType.LOOP));
            } else if (player.m_20096_()) {
                animationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
            }
        } else {
            animationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController[]{new AnimationController((GeoAnimatable)this, "winged_armor", 15, this::wings)});
    }
}

