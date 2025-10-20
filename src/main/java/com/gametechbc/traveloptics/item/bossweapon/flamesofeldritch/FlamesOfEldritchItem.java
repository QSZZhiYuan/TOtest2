/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.init.ModEffect
 *  com.github.L_Ender.cataclysm.init.ModItems
 *  io.redspace.ironsspellbooks.api.registry.AttributeRegistry
 *  io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder
 *  io.redspace.ironsspellbooks.util.ItemPropertiesHelper
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.Attribute
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation
 *  net.minecraft.world.item.ItemDisplayContext
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Tier
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package com.gametechbc.traveloptics.item.bossweapon.flamesofeldritch;

import com.gametechbc.traveloptics.config.WeaponConfig;
import com.gametechbc.traveloptics.entity.item.flames_of_eldritch.base.FlamesOfEldritchRenderer;
import com.gametechbc.traveloptics.init.TravelopticsItems;
import com.gametechbc.traveloptics.item.UnbreakableGeoMagicSword;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModItems;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FlamesOfEldritchItem
extends UnbreakableGeoMagicSword {
    private static ItemDisplayContext transformType;

    public FlamesOfEldritchItem(SpellDataRegistryHolder[] imbuedSpells) {
        super(new Tier(){

            public int m_6609_() {
                return (Integer)WeaponConfig.flamesOfEldritchDurability.get();
            }

            public float m_6624_() {
                return 2.0f;
            }

            public float m_6631_() {
                return -0.0f;
            }

            public int m_6604_() {
                return 1;
            }

            public int m_6601_() {
                return 20;
            }

            public Ingredient m_6282_() {
                return Ingredient.m_43927_((ItemStack[])new ItemStack[]{new ItemStack((ItemLike)ModItems.IGNITIUM_INGOT.get())});
            }
        }, (Double)WeaponConfig.flamesOfEldritchDamage.get(), (Double)WeaponConfig.flamesOfEldritchAttackSpeed.get(), imbuedSpells, Map.of((Attribute)AttributeRegistry.ELDRITCH_SPELL_POWER.get(), new AttributeModifier(UUID.fromString("412b5a66-2b43-4c18-ab05-6de0bb4d64d3"), "Weapon Modifier", ((Double)WeaponConfig.flamesOfEldritchEldritchSpellPower.get()).doubleValue(), AttributeModifier.Operation.MULTIPLY_BASE), (Attribute)AttributeRegistry.FIRE_SPELL_POWER.get(), new AttributeModifier(UUID.fromString("412b5a66-2b43-4c18-ab05-6de0bb4d64d3"), "Weapon Modifier", ((Double)WeaponConfig.flamesOfEldritchFireSpellPower.get()).doubleValue(), AttributeModifier.Operation.MULTIPLY_BASE)), ItemPropertiesHelper.hidden((int)1).m_41497_(TravelopticsItems.RARITY_IGNIS));
    }

    public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        MobEffect blazingBrandEffect;
        if (target != null && attacker.m_217043_().m_188501_() < 0.4f && (blazingBrandEffect = (MobEffect)ModEffect.EFFECTBLAZING_BRAND.get()) != null) {
            int currentAmplifier;
            MobEffectInstance currentEffect = target.m_21124_(blazingBrandEffect);
            int n = currentAmplifier = currentEffect != null ? currentEffect.m_19564_() : -1;
            if (currentAmplifier >= 0) {
                int newAmplifier = 0;
                target.m_7292_(new MobEffectInstance(blazingBrandEffect, 60, newAmplifier));
            } else {
                target.m_7292_(new MobEffectInstance(blazingBrandEffect, 60, 0));
            }
        }
        return super.m_7579_(stack, target, attacker);
    }

    public void m_7373_(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add((Component)Component.m_237113_((String)""));
        tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.flames_of_eldritch.tooltip").m_130940_(ChatFormatting.GREEN));
        tooltip.add((Component)Component.m_237115_((String)"ui.traveloptics.weapon.evolution_zero").m_130940_(ChatFormatting.YELLOW));
        tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.flames_of_eldritch.tooltip1"));
        if (Screen.m_96638_()) {
            tooltip.add((Component)Component.m_237113_((String)""));
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.weapons.evolution_benefits.tooltip"));
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.flames_of_eldritch.evo_one.inactive.tooltip"));
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.flames_of_eldritch.evo_two.inactive.tooltip"));
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.flames_of_eldritch.evo_three.inactive.tooltip"));
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
        return new FlamesOfEldritchRenderer();
    }
}

