/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.init.ModEffect
 *  io.redspace.ironsspellbooks.api.registry.AttributeRegistry
 *  io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder
 *  io.redspace.ironsspellbooks.util.ItemPropertiesHelper
 *  net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
 *  net.minecraft.network.chat.Component
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
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
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package com.gametechbc.traveloptics.item.bossweapon.abyssaltidecaller;

import com.gametechbc.traveloptics.config.WeaponConfig;
import com.gametechbc.traveloptics.data_manager.WeaponFormManager;
import com.gametechbc.traveloptics.entity.item.abyssal_tidecaller.evo_three.AbyssalTidecallerLevelThreeRenderer;
import com.gametechbc.traveloptics.init.TravelopticsItems;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import com.gametechbc.traveloptics.item.UnbreakableGeoMagicSword;
import com.gametechbc.traveloptics.item.bossweapon.abyssaltidecaller.AbyssalTidecallerAbility;
import com.github.L_Ender.cataclysm.init.ModEffect;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AbyssalTidecallerLevelThreeItem
extends UnbreakableGeoMagicSword {
    private static ItemDisplayContext transformType;
    private static final int FORM_ABYSSAL_FEAR = 0;
    private static final int FORM_ABYSSAL_CURSE = 1;
    private static final AbyssalTidecallerAbility ABILITY;

    public AbyssalTidecallerLevelThreeItem(SpellDataRegistryHolder[] imbuedSpells) {
        super(new Tier(){

            public int m_6609_() {
                return (Integer)WeaponConfig.abyssalTidecallerDurability.get();
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
        }, (Double)WeaponConfig.abyssalTidecallerDamage.get(), (Double)WeaponConfig.abyssalTidecallerAttackSpeed.get(), imbuedSpells, Map.of((Attribute)AttributeRegistry.ELDRITCH_SPELL_POWER.get(), new AttributeModifier(UUID.fromString("57f9a563-4e2c-4c4b-871e-c49c38edf14f"), "Weapon Modifier", ((Double)WeaponConfig.abyssalTidecallerEldritchSpellPower.get()).doubleValue(), AttributeModifier.Operation.MULTIPLY_BASE), (Attribute)AttributeRegistry.ENDER_SPELL_POWER.get(), new AttributeModifier(UUID.fromString("57f9a563-4e2c-4c4b-871e-c49c38edf14f"), "Weapon Modifier", ((Double)WeaponConfig.abyssalTidecallerEnderSpellPower.get()).doubleValue(), AttributeModifier.Operation.MULTIPLY_BASE)), ItemPropertiesHelper.hidden((int)1).m_41497_(TravelopticsItems.RARITY_ABYSSAL));
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.m_21120_(hand);
        if (player.m_6047_()) {
            WeaponFormManager.cycleForm(stack, 2);
            int newForm = WeaponFormManager.getForm(stack);
            if (newForm == 1) {
                player.m_5661_((Component)Component.m_237113_((String)"\u00a7dManifestation: Abyssal Curse"), true);
            } else {
                player.m_5661_((Component)Component.m_237113_((String)"\u00a7dManifestation: Abyssal Fear"), true);
            }
            if (!level.m_5776_()) {
                level.m_6263_(null, player.m_20185_(), player.m_20186_(), player.m_20189_(), (SoundEvent)TravelopticsSounds.MANIFESTATION_CHANGE.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
                player.f_36095_.m_38946_();
            }
            player.m_36335_().m_41524_((Item)this, 300);
            return InteractionResultHolder.m_19092_((Object)stack, (boolean)level.m_5776_());
        }
        return InteractionResultHolder.m_19098_((Object)stack);
    }

    public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        int currentForm = WeaponFormManager.getForm(stack);
        if (attacker.m_217043_().m_188501_() < 0.6f) {
            MobEffect effectToApply;
            MobEffect mobEffect = effectToApply = currentForm == 1 ? (MobEffect)ModEffect.EFFECTABYSSAL_CURSE.get() : (MobEffect)ModEffect.EFFECTABYSSAL_FEAR.get();
            if (effectToApply != null) {
                int currentAmplifier;
                MobEffectInstance currentEffect = target.m_21124_(effectToApply);
                int n = currentAmplifier = currentEffect != null ? currentEffect.m_19564_() : -1;
                if (currentAmplifier >= 0) {
                    int newAmplifier = Math.min(currentAmplifier + 1, 4);
                    target.m_7292_(new MobEffectInstance(effectToApply, 100, newAmplifier));
                } else {
                    target.m_7292_(new MobEffectInstance(effectToApply, 100, 0));
                }
            }
        }
        return super.m_7579_(stack, target, attacker);
    }

    public void m_7373_(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag) {
        ABILITY.appendHoverText(stack, world, tooltip, flag, 3);
        super.m_7373_(stack, world, tooltip, flag);
    }

    public void getTransformType(ItemDisplayContext type) {
        transformType = type;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    protected BlockEntityWithoutLevelRenderer getRenderer() {
        return new AbyssalTidecallerLevelThreeRenderer();
    }

    static {
        ABILITY = new AbyssalTidecallerAbility();
    }
}

