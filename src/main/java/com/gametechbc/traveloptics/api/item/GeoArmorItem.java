/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap$Builder
 *  com.google.common.collect.ImmutableMultimap
 *  com.google.common.collect.ImmutableMultimap$Builder
 *  com.google.common.collect.Multimap
 *  net.minecraft.client.model.HumanoidModel
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.Attribute
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ArmorItem
 *  net.minecraft.world.item.ArmorItem$Type
 *  net.minecraft.world.item.ArmorMaterial
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.extensions.common.IClientItemExtensions
 *  org.jetbrains.annotations.NotNull
 *  software.bernie.geckolib.animatable.GeoItem
 *  software.bernie.geckolib.core.animatable.GeoAnimatable
 *  software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache
 *  software.bernie.geckolib.core.animation.AnimatableManager$ControllerRegistrar
 *  software.bernie.geckolib.core.animation.AnimationController
 *  software.bernie.geckolib.core.animation.AnimationState
 *  software.bernie.geckolib.core.animation.RawAnimation
 *  software.bernie.geckolib.core.object.PlayState
 *  software.bernie.geckolib.renderer.GeoArmorRenderer
 *  software.bernie.geckolib.util.GeckoLibUtil
 */
package com.gametechbc.traveloptics.api.item;

import com.gametechbc.traveloptics.api.item.armor.TravelopticsArmorMaterial;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

public abstract class GeoArmorItem
extends ArmorItem
implements GeoItem {
    private static final UUID[] ARMOR_MODIFIER_UUID_PER_SLOT = new UUID[]{UUID.fromString("674a93d4-a8d3-11ef-a83e-325096b39f47"), UUID.fromString("7326ac88-a8d3-11ef-b1d4-325096b39f47"), UUID.fromString("7743fa8c-a8d3-11ef-8e38-325096b39f47"), UUID.fromString("7b936974-a8d3-11ef-9507-325096b39f47")};
    private final Multimap<Attribute, AttributeModifier> ARMOR_ATTRIBUTES;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    private static final Map<ArmorMaterial, MobEffectInstance> MATERIAL_TO_EFFECT_MAP = new ImmutableMap.Builder().build();

    public GeoArmorItem(TravelopticsArmorMaterial material, ArmorItem.Type type, Item.Properties settings) {
        super((ArmorMaterial)material, type, settings);
        ImmutableMultimap.Builder builder = ImmutableMultimap.builder();
        float defense = material.m_7366_(type);
        float toughness = material.m_6651_();
        float knockbackResistance = material.m_6649_();
        UUID uuid = ARMOR_MODIFIER_UUID_PER_SLOT[type.m_266308_().m_20749_()];
        builder.put((Object)Attributes.f_22284_, (Object)new AttributeModifier(uuid, "Armor modifier", (double)defense, AttributeModifier.Operation.ADDITION));
        builder.put((Object)Attributes.f_22285_, (Object)new AttributeModifier(uuid, "Armor toughness", (double)toughness, AttributeModifier.Operation.ADDITION));
        if (knockbackResistance > 0.0f) {
            builder.put((Object)Attributes.f_22278_, (Object)new AttributeModifier(uuid, "Armor knockback resistance", (double)knockbackResistance, AttributeModifier.Operation.ADDITION));
        }
        for (Map.Entry<Attribute, AttributeModifier> modifierEntry : material.getAdditionalAttributes().entrySet()) {
            AttributeModifier atr = modifierEntry.getValue();
            atr = new AttributeModifier(uuid, atr.m_22214_(), atr.m_22218_(), atr.m_22217_());
            builder.put((Object)modifierEntry.getKey(), (Object)atr);
        }
        this.ARMOR_ATTRIBUTES = builder.build();
    }

    public Multimap<Attribute, AttributeModifier> m_7167_(EquipmentSlot pEquipmentSlot) {
        if (pEquipmentSlot == this.f_265916_.m_266308_()) {
            return this.ARMOR_ATTRIBUTES;
        }
        return ImmutableMultimap.of();
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController[]{new AnimationController((GeoAnimatable)this, "controller", 20, this::predicate)});
    }

    private PlayState predicate(AnimationState<GeoArmorItem> TravelopticsArmorItemAnimationState) {
        TravelopticsArmorItemAnimationState.getController().setAnimation(RawAnimation.begin().thenLoop("idle"));
        return PlayState.CONTINUE;
    }

    public void onArmorTick(ItemStack stack, Level world, Player player) {
        if (!world.m_5776_() && this.hasFullSuitOfArmorOn(player)) {
            this.evaluateArmorEffects(player);
        }
    }

    private void evaluateArmorEffects(Player player) {
        for (Map.Entry<ArmorMaterial, MobEffectInstance> entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
            ArmorMaterial mapArmorMaterial = entry.getKey();
            MobEffectInstance mapStatusEffect = entry.getValue();
            if (!this.hasCorrectArmorOn(mapArmorMaterial, player)) continue;
            this.addStatusEffectForMaterial(player, mapArmorMaterial, mapStatusEffect);
        }
    }

    private void addStatusEffectForMaterial(Player player, ArmorMaterial mapArmorMaterial, MobEffectInstance mapStatusEffect) {
        boolean hasPlayerEffect = player.m_21023_(mapStatusEffect.m_19544_());
        if (this.hasCorrectArmorOn(mapArmorMaterial, player) && !hasPlayerEffect) {
            player.m_7292_(new MobEffectInstance(mapStatusEffect.m_19544_(), mapStatusEffect.m_19557_(), mapStatusEffect.m_19564_()));
        }
    }

    private boolean hasFullSuitOfArmorOn(Player player) {
        ItemStack boots = player.m_150109_().m_36052_(0);
        ItemStack leggings = player.m_150109_().m_36052_(1);
        ItemStack breastplate = player.m_150109_().m_36052_(2);
        ItemStack helmet = player.m_150109_().m_36052_(3);
        return !helmet.m_41619_() && !breastplate.m_41619_() && !leggings.m_41619_() && !boots.m_41619_();
    }

    private boolean hasCorrectArmorOn(ArmorMaterial material, Player player) {
        for (ItemStack armorStack : player.m_150109_().f_35975_) {
            if (armorStack.m_41720_() instanceof ArmorItem) continue;
            return false;
        }
        ArmorItem boots = (ArmorItem)player.m_150109_().m_36052_(0).m_41720_();
        ArmorItem leggings = (ArmorItem)player.m_150109_().m_36052_(1).m_41720_();
        ArmorItem breastplate = (ArmorItem)player.m_150109_().m_36052_(2).m_41720_();
        ArmorItem helmet = (ArmorItem)player.m_150109_().m_36052_(3).m_41720_();
        return helmet.m_40401_() == material && breastplate.m_40401_() == material && leggings.m_40401_() == material && boots.m_40401_() == material;
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions(){
            private GeoArmorRenderer<?> renderer;

            @NotNull
            public HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null) {
                    this.renderer = GeoArmorItem.this.supplyRenderer();
                }
                this.renderer.prepForRender((Entity)livingEntity, itemStack, equipmentSlot, original);
                return this.renderer;
            }
        });
    }

    @OnlyIn(value=Dist.CLIENT)
    public abstract GeoArmorRenderer<?> supplyRenderer();
}

