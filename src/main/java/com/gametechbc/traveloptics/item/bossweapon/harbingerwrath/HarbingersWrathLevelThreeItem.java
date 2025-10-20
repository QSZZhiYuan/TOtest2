/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.init.ModEffect
 *  io.redspace.ironsspellbooks.api.registry.AttributeRegistry
 *  io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder
 *  io.redspace.ironsspellbooks.util.ItemPropertiesHelper
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.TamableAnimal
 *  net.minecraft.world.entity.ai.attributes.Attribute
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Tier
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.scores.Team
 */
package com.gametechbc.traveloptics.item.bossweapon.harbingerwrath;

import com.gametechbc.traveloptics.api.item.weapons.MagicClaymoreItem;
import com.gametechbc.traveloptics.config.WeaponConfig;
import com.gametechbc.traveloptics.init.TravelopticsItems;
import com.github.L_Ender.cataclysm.init.ModEffect;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.scores.Team;

public class HarbingersWrathLevelThreeItem
extends MagicClaymoreItem {
    public HarbingersWrathLevelThreeItem(SpellDataRegistryHolder[] imbuedSpells) {
        super(new Tier(){

            public int m_6609_() {
                return (Integer)WeaponConfig.harbingersWrathDurability.get();
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
                return Ingredient.m_43927_((ItemStack[])new ItemStack[]{new ItemStack((ItemLike)TravelopticsItems.LIGHTNING_SPELLWEAVE_INGOT.get())});
            }
        }, (Double)WeaponConfig.harbingersWrathDamage.get(), (Double)WeaponConfig.harbingersWrathAttackSpeed.get(), imbuedSpells, Map.of((Attribute)AttributeRegistry.LIGHTNING_SPELL_POWER.get(), new AttributeModifier(UUID.fromString("3c5c8036-eaa9-401b-8c5a-01c8251d1db1"), "Weapon Modifier", ((Double)WeaponConfig.harbingersWrathLightningSpellPower.get()).doubleValue(), AttributeModifier.Operation.MULTIPLY_BASE)), ItemPropertiesHelper.hidden((int)1).m_41497_(TravelopticsItems.RARITY_MECHANIZED));
    }

    public boolean m_41465_() {
        return (Boolean)WeaponConfig.weaponsShouldBeBreakable.get();
    }

    public boolean isDamageable(ItemStack stack) {
        return (Boolean)WeaponConfig.weaponsShouldBeBreakable.get();
    }

    public boolean m_8120_(ItemStack stack) {
        return true;
    }

    public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        MobEffect stunEffect;
        if (target != null && attacker.m_217043_().m_188501_() < 0.2f && (stunEffect = (MobEffect)ModEffect.EFFECTSTUN.get()) != null) {
            double radius = 3.0;
            List nearbyEntities = target.m_9236_().m_6443_(LivingEntity.class, target.m_20191_().m_82400_(radius), entity -> entity != attacker && !this.isAllyOrTamed((LivingEntity)entity, attacker));
            for (LivingEntity entity2 : nearbyEntities) {
                MobEffectInstance currentEffect = entity2.m_21124_(stunEffect);
                if (currentEffect != null) continue;
                entity2.m_7292_(new MobEffectInstance(stunEffect, 60, 0));
            }
        }
        return super.m_7579_(stack, target, attacker);
    }

    private boolean isAllyOrTamed(LivingEntity entity, LivingEntity attacker) {
        Team attackerTeam = attacker.m_5647_();
        Team entityTeam = entity.m_5647_();
        boolean isAlly = attackerTeam != null && attackerTeam.m_83536_(entityTeam);
        boolean isTamed = entity instanceof TamableAnimal && ((TamableAnimal)entity).m_21830_(attacker);
        return isAlly || isTamed;
    }

    public void m_7373_(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add((Component)Component.m_237113_((String)"\u00a7ePassive Ability: Mini EM-Pulse"));
        tooltip.add((Component)Component.m_237115_((String)"ui.traveloptics.weapon.evolution_three").m_130940_(ChatFormatting.YELLOW));
        if (Screen.m_96638_()) {
            tooltip.add((Component)Component.m_237113_((String)"\u00a7fSuccessful hits on enemies has \u00a7b20% \u00a7fchance to stun everyone in a \u00a7b3 \u00a7fblock radius with a mini EM-Pulse, for \u00a7b3 sec."));
            tooltip.add((Component)Component.m_237113_((String)"\u00a7fAdditional procs refresh the duration."));
            tooltip.add((Component)Component.m_237113_((String)""));
            tooltip.add((Component)Component.m_237113_((String)"\u00a7eEvolution Benefits:"));
            tooltip.add((Component)Component.m_237113_((String)"\u00a7e\u2605 \u00a7f[Evo 1] Increases effect duration \u00a7b+1.5 sec."));
            tooltip.add((Component)Component.m_237113_((String)"\u00a7e\u2605 \u00a7f[Evo 2] Increases proc chance \u00a7b+10%."));
            tooltip.add((Component)Component.m_237113_((String)"\u00a7e\u2605 \u00a7f[Evo 3] EM-Pulse is now AoE, But its proc chance & duration is reduced \u00a7c-5% \u00a7f| \u00a7c-0.5 sec."));
        } else {
            tooltip.add((Component)Component.m_237113_((String)"\u00a7e\u2605\u2605\u2605"));
            tooltip.add((Component)Component.m_237113_((String)"\u00a78[Hold Shift for more info]"));
        }
        super.m_7373_(stack, world, tooltip, flag);
    }
}

