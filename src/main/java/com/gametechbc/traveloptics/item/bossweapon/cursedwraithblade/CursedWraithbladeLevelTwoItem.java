/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.api.item.weapons.MagicSwordItem
 *  io.redspace.ironsspellbooks.api.registry.AttributeRegistry
 *  io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  io.redspace.ironsspellbooks.item.UniqueItem
 *  io.redspace.ironsspellbooks.util.ItemPropertiesHelper
 *  net.minecraft.ChatFormatting
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.Attribute
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Tier
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.UseAnim
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 */
package com.gametechbc.traveloptics.item.bossweapon.cursedwraithblade;

import com.gametechbc.traveloptics.config.WeaponConfig;
import com.gametechbc.traveloptics.data_manager.SpiritPointsManager;
import com.gametechbc.traveloptics.data_manager.SwingCounterManager;
import com.gametechbc.traveloptics.init.TravelopticsEffects;
import com.gametechbc.traveloptics.init.TravelopticsItems;
import com.gametechbc.traveloptics.item.bossweapon.cursedwraithblade.CursedWraithbladeAbility;
import io.redspace.ironsspellbooks.api.item.weapons.MagicSwordItem;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.item.UniqueItem;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class CursedWraithbladeLevelTwoItem
extends MagicSwordItem
implements UniqueItem {
    private static final CursedWraithbladeAbility ABILITY = new CursedWraithbladeAbility();

    public CursedWraithbladeLevelTwoItem(SpellDataRegistryHolder[] imbuedSpells) {
        super(new Tier(){

            public int m_6609_() {
                return (Integer)WeaponConfig.cursedWraithbladeDurability.get();
            }

            public float m_6624_() {
                return 0.0f;
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
                return Ingredient.m_43927_((ItemStack[])new ItemStack[]{new ItemStack((ItemLike)TravelopticsItems.PYRO_SPELLWEAVE_INGOT.get())});
            }
        }, ((Double)WeaponConfig.cursedWraithbladeDamage.get()).doubleValue(), ((Double)WeaponConfig.cursedWraithbladeAttackSpeed.get()).doubleValue(), imbuedSpells, Map.of((Attribute)AttributeRegistry.ICE_SPELL_POWER.get(), new AttributeModifier(UUID.fromString("412b5a66-2b43-4c18-ab05-6de0bb4d64d3"), "Weapon Modifier", ((Double)WeaponConfig.cursedWraithbladeIceSpellPower.get()).doubleValue(), AttributeModifier.Operation.MULTIPLY_BASE), (Attribute)AttributeRegistry.ELDRITCH_SPELL_POWER.get(), new AttributeModifier(UUID.fromString("412b5a66-2b43-4c18-ab05-6de0bb4d64d3"), "Weapon Modifier", ((Double)WeaponConfig.cursedWraithbladeEldritchSpellPower.get()).doubleValue(), AttributeModifier.Operation.MULTIPLY_BASE)), ItemPropertiesHelper.hidden((int)1).m_41497_(TravelopticsItems.RARITY_CURSED));
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
        if (attacker instanceof Player) {
            Player player = (Player)attacker;
            int swingCounter = SwingCounterManager.getSwingCount(stack);
            SpiritPointsManager.addSpiritPoints(stack, player, switch (swingCounter) {
                case 1 -> 5;
                case 2 -> 10;
                case 3 -> 15;
                default -> 5;
            });
            SwingCounterManager.incrementSwingCount(stack, 3);
            MagicManager.spawnParticles((Level)target.m_9236_(), (ParticleOptions)ParticleTypes.f_235898_, (double)target.m_20185_(), (double)target.m_20186_(), (double)target.m_20189_(), (int)30, (double)0.0, (double)0.0, (double)0.0, (double)0.3, (boolean)false);
        }
        return super.m_7579_(stack, target, attacker);
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.m_21120_(hand);
        player.m_6672_(hand);
        return new InteractionResultHolder(InteractionResult.SUCCESS, (Object)stack);
    }

    public void m_5551_(ItemStack stack, Level world, LivingEntity entity, int timeLeft) {
        if (entity instanceof Player) {
            Player player = (Player)entity;
            int chargeDuration = this.m_8105_(stack) - timeLeft;
            float power = CursedWraithbladeLevelTwoItem.getPowerForTime(chargeDuration);
            if (power >= 1.0f) {
                this.applyBerserkEffect(player, stack);
                player.m_36335_().m_41524_((Item)this, 200);
                List<Item> itemsToCooldown = List.of((Item)TravelopticsItems.CURSED_WRAITHBLADE_LEVEL_THREE.get(), (Item)TravelopticsItems.CURSED_WRAITHBLADE_LEVEL_ONE.get());
                for (int i = 0; i < player.m_150109_().m_6643_(); ++i) {
                    ItemStack invStack = player.m_150109_().m_8020_(i);
                    if (invStack.m_41619_() || !itemsToCooldown.contains(invStack.m_41720_())) continue;
                    player.m_36335_().m_41524_(invStack.m_41720_(), 200);
                }
            }
        }
    }

    public UseAnim m_6164_(ItemStack stack) {
        return UseAnim.BOW;
    }

    public int m_8105_(ItemStack stack) {
        return 72000;
    }

    private static float getPowerForTime(int charge) {
        float f = (float)charge / 20.0f;
        if ((f = (f * f + f * 2.0f) / 3.0f) > 1.0f) {
            f = 1.0f;
        }
        return f;
    }

    private void applyBerserkEffect(Player player, ItemStack stack) {
        if (player instanceof ServerPlayer) {
            int spiritPoints = SpiritPointsManager.getSpiritPoints(stack);
            int amplifier = spiritPoints / 50;
            player.m_7292_(new MobEffectInstance((MobEffect)TravelopticsEffects.CONSUME.get(), 200, amplifier));
            SpiritPointsManager.setSpiritPoints(stack, 0);
            player.m_5661_((Component)Component.m_237113_((String)"\u2620 Soul Fragments: 0").m_130940_(ChatFormatting.DARK_AQUA), true);
        }
    }

    public void m_7373_(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag) {
        ABILITY.appendHoverText(stack, world, tooltip, flag, 2);
        super.m_7373_(stack, world, tooltip, flag);
    }
}

