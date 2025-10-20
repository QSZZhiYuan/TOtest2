/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan.Abyss_Blast_Portal_Entity
 *  com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity
 *  com.github.L_Ender.cataclysm.init.ModEffect
 *  com.github.L_Ender.cataclysm.init.ModSounds
 *  io.redspace.ironsspellbooks.api.registry.AttributeRegistry
 *  io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder
 *  io.redspace.ironsspellbooks.registries.SoundRegistry
 *  io.redspace.ironsspellbooks.util.ItemPropertiesHelper
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
 *  net.minecraft.network.chat.Component
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
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
 */
package com.gametechbc.traveloptics.item.bossweapon.the_obliterator;

import com.gametechbc.traveloptics.config.WeaponConfig;
import com.gametechbc.traveloptics.entity.item.the_obliterator.base.TheObliteratorRenderer;
import com.gametechbc.traveloptics.init.TravelopticsItems;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import com.gametechbc.traveloptics.item.UnbreakableGeoMagicSword;
import com.gametechbc.traveloptics.util.TravelopticsParticleHelper;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan.Abyss_Blast_Portal_Entity;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModSounds;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
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

public class TheObliteratorItem
extends UnbreakableGeoMagicSword {
    private static ItemDisplayContext transformType;

    public TheObliteratorItem(SpellDataRegistryHolder[] imbuedSpells) {
        super(new Tier(){

            public int m_6609_() {
                return (Integer)WeaponConfig.thornsOblivionDurability.get();
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
                return Ingredient.m_43927_((ItemStack[])new ItemStack[]{new ItemStack((ItemLike)TravelopticsItems.ABYSSAL_SPELLWEAVE_INGOT.get())});
            }
        }, (Double)WeaponConfig.theObliteratorDamage.get(), (Double)WeaponConfig.theObliteratorAttackSpeed.get(), imbuedSpells, Map.of((Attribute)AttributeRegistry.ENDER_SPELL_POWER.get(), new AttributeModifier(UUID.fromString("8ff095c4-a26d-4d28-bc06-1a908b3577aa"), "Weapon Modifier", ((Double)WeaponConfig.theObliteratorEnderSpellPower.get()).doubleValue(), AttributeModifier.Operation.MULTIPLY_BASE), (Attribute)AttributeRegistry.ELDRITCH_SPELL_POWER.get(), new AttributeModifier(UUID.fromString("8ff095c4-a26d-4d28-bc06-1a908b3577aa"), "Weapon Modifier", ((Double)WeaponConfig.theObliteratorEldritchSpellPower.get()).doubleValue(), AttributeModifier.Operation.MULTIPLY_BASE)), ItemPropertiesHelper.hidden((int)1).m_41497_(TravelopticsItems.RARITY_ABYSSAL));
    }

    public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        MobEffect abyssalCurseEffect;
        if (target != null && attacker.m_217043_().m_188501_() < 0.45f && (abyssalCurseEffect = (MobEffect)ModEffect.EFFECTABYSSAL_CURSE.get()) != null) {
            target.m_7292_(new MobEffectInstance(abyssalCurseEffect, 100, 1));
        }
        return super.m_7579_(stack, target, attacker);
    }

    public UseAnim m_6164_(ItemStack p_77661_1_) {
        return UseAnim.SPEAR;
    }

    public int m_8105_(ItemStack p_77626_1_) {
        return 72000;
    }

    public void m_5551_(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        if (entity instanceof Player) {
            Player player = (Player)entity;
            int chargeTime = this.m_8105_(stack) - timeLeft;
            if (chargeTime >= 40) {
                this.handleBlastPortalSpawns(level, (LivingEntity)player);
                if (!level.f_46443_) {
                    player.m_36335_().m_41524_((Item)this, 240);
                }
            }
        }
    }

    public void m_5929_(Level level, LivingEntity caster, ItemStack stack, int count) {
        int chargeTime = this.m_8105_(stack) - count;
        if (chargeTime == 14) {
            this.spawnChargeParticles(level, caster, 2.0f);
        }
        if (chargeTime == 28) {
            this.spawnChargeParticles(level, caster, 3.5f);
        }
        if (chargeTime == 40) {
            this.spawnChargeParticles(level, caster, 5.0f);
            caster.m_5496_((SoundEvent)ModSounds.LEVIATHAN_STUN_ROAR.get(), 0.9f, 1.0f);
        }
    }

    private void handleBlastPortalSpawns(Level level, LivingEntity caster) {
        ScreenShake_Entity.ScreenShake((Level)level, (Vec3)caster.m_20182_(), (float)30.0f, (float)0.1f, (int)0, (int)30);
        level.m_6263_(null, caster.m_20185_(), caster.m_20186_(), caster.m_20189_(), (SoundEvent)SoundRegistry.EARTHQUAKE_IMPACT.get(), SoundSource.BLOCKS, 1.2f, 1.0f);
        level.m_6263_(null, caster.m_20185_(), caster.m_20186_(), caster.m_20189_(), (SoundEvent)TravelopticsSounds.REFINED_ABYSS_BLAST_PORTAL.get(), SoundSource.BLOCKS, 1.5f, 1.0f);
        this.spawnAbyssBlastPortal(level, caster);
    }

    private void spawnAbyssBlastPortal(Level level, LivingEntity caster) {
        Vec3 lookVec = caster.m_20154_();
        Vec3 portalPos = caster.m_20182_().m_82549_(lookVec.m_82490_(2.5));
        if (!level.f_46443_) {
            Abyss_Blast_Portal_Entity portal = new Abyss_Blast_Portal_Entity(level, portalPos.f_82479_, caster.m_20186_(), portalPos.f_82481_, caster.m_146908_(), 0, (float)(15.0 * (Double)WeaponConfig.theObliteratorOblivionRayDamageMultiplier.get()), 0.0f, caster);
            level.m_7967_((Entity)portal);
        }
    }

    private void spawnChargeParticles(Level level, LivingEntity caster, float radius) {
        if (level.f_46443_) {
            for (int j = 0; j < 70; ++j) {
                float angle = (float)(Math.random() * 2.0 * Math.PI);
                double distance = Math.sqrt(Math.random()) * (double)radius;
                double extraX = caster.m_20185_() + distance * (double)Mth.m_14089_((float)angle);
                double extraY = caster.m_20186_() + (double)0.3f;
                double extraZ = caster.m_20189_() + distance * (double)Mth.m_14031_((float)angle);
                level.m_7106_(TravelopticsParticleHelper.ABYSS_SPIKE_PARTICLE, extraX, extraY, extraZ, 0.0, level.f_46441_.m_188583_() * 0.04, 0.0);
            }
        }
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand hand) {
        ItemStack otherHand;
        ItemStack itemstack = player.m_21120_(hand);
        ItemStack itemStack = otherHand = hand == InteractionHand.MAIN_HAND ? player.m_21120_(InteractionHand.OFF_HAND) : player.m_21120_(InteractionHand.MAIN_HAND);
        if (otherHand.m_150930_((Item)TravelopticsItems.THE_OBLITERATOR.get())) {
            player.m_6672_(hand);
            return InteractionResultHolder.m_19096_((Object)itemstack);
        }
        return InteractionResultHolder.m_19100_((Object)itemstack);
    }

    public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
        return true;
    }

    public void m_7373_(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add((Component)Component.m_237113_((String)""));
        tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.the_obliterator.tooltip").m_130940_(ChatFormatting.GREEN));
        tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.the_obliterator.tooltip0"));
        tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.the_obliterator.tooltip1"));
        if (Screen.m_96638_()) {
            tooltip.add((Component)Component.m_237113_((String)""));
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.weapons.evolution_benefits.tooltip"));
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.the_obliterator.evo_one.inactive.tooltip"));
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.the_obliterator.evo_two.inactive.tooltip"));
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.the_obliterator.evo_three.inactive.tooltip"));
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
        return new TheObliteratorRenderer();
    }
}

